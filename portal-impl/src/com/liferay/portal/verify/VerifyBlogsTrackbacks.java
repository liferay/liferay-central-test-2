/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

/**
 * <p>
 * This class looks at every blog comment to see if it is a trackback and
 * verifies that the source URL is a valid URL. Do not run this unless you want
 * to do this.
 * </p>
 *
 * @author Alexander Chow
 */
public class VerifyBlogsTrackbacks extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<MBDiscussion> mbDiscussions =
			MBMessageLocalServiceUtil.getDiscussions(
				BlogsEntry.class.getName());

		for (MBDiscussion mbDiscussion : mbDiscussions) {
			try {
				BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(
					mbDiscussion.getClassPK());

				List<MBMessage> mbMessages =
					MBMessageLocalServiceUtil.getThreadMessages(
						mbDiscussion.getThreadId(),
						WorkflowConstants.STATUS_APPROVED);

				for (MBMessage mbMessage : mbMessages) {
					_verifyPost(entry, mbMessage);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private void _verifyPost(BlogsEntry entry, MBMessage mbMessage)
		throws PortalException {

		String entryURL =
			Portal.FRIENDLY_URL_SEPARATOR + "blogs/" + entry.getUrlTitle();
		String body = mbMessage.getBody();
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
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				mbMessage.getCompanyId());

			if (mbMessage.getUserId() == defaultUserId) {
				_linkbackConsumer.verifyTrackback(
					mbMessage.getMessageId(), url, entryURL);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyBlogsTrackbacks.class);

	private final LinkbackConsumer _linkbackConsumer =
		LinkbackConsumerUtil.getLinkbackConsumer();

}