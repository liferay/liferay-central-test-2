/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.social;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

import org.json.JSONObject;

/**
 * <a href="BlogsActivityInterpreter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		int activityType = activity.getType();

		JSONObject extraData = null;

		if (Validator.isNotNull(activity.getExtraData())) {
			extraData = new JSONObject(activity.getExtraData());
		}

		// Title

		String title = StringPool.BLANK;

		if (activityType == BlogsActivityKeys.ADD_COMMENT) {
			title = themeDisplay.translate(
				"activity-blogs-add-comment",
				new Object[] {creatorUserName, receiverUserName});
		}
		else if (activityType == BlogsActivityKeys.ADD_ENTRY) {
			title = themeDisplay.translate(
				"activity-blogs-add-entry", creatorUserName);
		}

		// Body

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(
			activity.getClassPK());

		String entryURL =
			themeDisplay.getURLPortal() + themeDisplay.getPathMain() +
				"/blogs/find_entry?entryId=" + activity.getClassPK();

		StringMaker sm = new StringMaker();

		sm.append("<a href=\"");
		sm.append(entryURL);
		sm.append("\">");

		if (activityType == BlogsActivityKeys.ADD_COMMENT) {
			long messageId = extraData.getInt("messageId");

			MBMessage message = MBMessageLocalServiceUtil.getMessage(messageId);

			sm.append(cleanContent(message.getBody()));
		}
		else if (activityType == BlogsActivityKeys.ADD_ENTRY) {
			sm.append(entry.getTitle());
		}

		sm.append("</a><br />");

		if (activityType == BlogsActivityKeys.ADD_ENTRY) {
			sm.append(cleanContent(entry.getContent()));
		}

		String body = sm.toString();

		return new SocialActivityFeedEntry(title, body, entryURL);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		BlogsEntry.class.getName()
	};

}