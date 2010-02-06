/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TrackbackVerifierUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class TrackbackVerifierUtil {

	public static void addNewPost(
		long messageId, String url, String entryUrl) {

		_trackbacks.add(new Tuple(messageId, url, entryUrl));
	}

	public static void verifyNewPosts() {
		Tuple tuple = null;

		while (!_trackbacks.isEmpty()) {
			synchronized (_trackbacks) {
				tuple = _trackbacks.remove(0);
			}

			long messageId = (Long)tuple.getObject(0);
			String url = (String)tuple.getObject(1);
			String entryUrl = (String)tuple.getObject(2);

			_verifyPost(messageId, url, entryUrl);
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
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			if (userId == defaultUserId) {
				_verifyPost(messageId, url, entryURL);
			}
		}
	}

	private static void _verifyPost(
		long messageId, String url, String entryURL) {

		try {
			String result = HttpUtil.URLtoString(url);

			if (result.contains(entryURL)) {
				return;
			}
		}
		catch (Exception e) {
		}

		try {
			MBMessageLocalServiceUtil.deleteDiscussionMessage(
				messageId);
		}
		catch (Exception e) {
			_log.error(
				"Error trying to delete trackback message " + messageId, e);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(TrackbackVerifierUtil.class);

	private static List<Tuple> _trackbacks =
		Collections.synchronizedList(new ArrayList<Tuple>());

}