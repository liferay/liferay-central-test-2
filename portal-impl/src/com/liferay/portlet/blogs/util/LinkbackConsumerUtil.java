/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="LinkbackConsumerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class LinkbackConsumerUtil {

	public static void addNewTrackback(
		long messageId, String remoteIp, String url, String entryUrl,
		boolean moderateTrackbacks) {

		_trackbacks.add(
			new Tuple(messageId, remoteIp, url, entryUrl, moderateTrackbacks));
	}

	public static void verifyNewTrackbacks() {
		Tuple tuple = null;

		while (!_trackbacks.isEmpty()) {
			synchronized (_trackbacks) {
				tuple = _trackbacks.remove(0);
			}

			long messageId = (Long)tuple.getObject(0);
			String remoteIp = (String)tuple.getObject(1);
			String url = (String)tuple.getObject(2);
			String entryUrl = (String)tuple.getObject(3);
			boolean moderateTrackbacks = (Boolean)tuple.getObject(4);

			_verifyTrackback(
				messageId, remoteIp, url, entryUrl, moderateTrackbacks);
		}
	}

	public static void verifyPost(BlogsEntry entry, MBMessage message)
		throws Exception {

		long messageId = message.getMessageId();
		String entryURL =
			Portal.FRIENDLY_URL_SEPARATOR + "blogs/" + entry.getUrlTitle();
		String body = message.getBody();
		String url = null;

		int start = body.indexOf("[url=");

		if (start > -1) {
			start += "[url=".length();

			int end = body.indexOf("]", start);

			if (end > -1) {
				url = body.substring(start, end);
			}
		}

		if (Validator.isNotNull(url)) {
			long companyId = message.getCompanyId();
			long userId = message.getUserId();
			int status = message.getStatus();
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			if ((userId == defaultUserId) &&
				(status == StatusConstants.PENDING)) {

				_verifyTrackback(messageId, null, url, entryURL, true);
			}
		}
	}

	private static void _updateStatus(long messageId, boolean moderate)
		throws Exception {

		if (moderate) {
			return;
		}

		MBMessage message = MBMessageLocalServiceUtil.getMessage(messageId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setStatus(StatusConstants.APPROVED);

		MBMessageLocalServiceUtil.updateStatus(
			message.getUserId(), messageId, serviceContext);
	}

	private static void _verifyTrackback(
		long messageId, String remoteIp, String url, String entryURL,
		boolean moderateTrackbacks) {

		try {
			String trackbackIp = HttpUtil.getIpAddress(url);

			if (!remoteIp.equals(trackbackIp)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Remote IP " + remoteIp +
							" does not match trackback URL's IP " +
							trackbackIp);
				}

				_updateStatus(messageId, moderateTrackbacks);

				return;
			}
		}
		catch (Exception e) {
		}

		try {
			String content = HttpUtil.URLtoString(url);

			if (!content.contains(entryURL)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Remote content from " + url +
							" does not contain entry URL " +
							entryURL);
				}

				_updateStatus(messageId, moderateTrackbacks);

				return;
			}
		}
		catch (Exception e) {
		}

		try {
			MBMessageLocalServiceUtil.deleteDiscussionMessage(messageId);
		}
		catch (Exception e) {
			_log.error(
				"Error trying to delete trackback message " + messageId, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LinkbackConsumerUtil.class);

	private static List<Tuple> _trackbacks =
		Collections.synchronizedList(new ArrayList<Tuple>());

}