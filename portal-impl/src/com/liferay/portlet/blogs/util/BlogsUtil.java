/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletPreferences;

/**
 * <a href="BlogsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class BlogsUtil {

	public static final String POP_PORTLET_PREFIX = "blogs.";

	public static String getEmailBlogsAddedBody(
		PortletPreferences preferences) {

		String emailBlogsAddedBody = preferences.getValue(
			"email-blogs-added-body", StringPool.BLANK);

		if (Validator.isNotNull(emailBlogsAddedBody)) {
			return emailBlogsAddedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY));
		}
	}

	public static boolean getEmailBlogsAddedEnabled(
		PortletPreferences preferences) {

		String emailBlogsAddedEnabled = preferences.getValue(
			"email-blogs-added-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailBlogsAddedEnabled)) {
			return GetterUtil.getBoolean(emailBlogsAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_ENABLED));
		}
	}

	public static String getEmailBlogsAddedSubject(
		PortletPreferences preferences) {

		String emailBlogsAddedSubject = preferences.getValue(
			"email-blogs-added-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailBlogsAddedSubject)) {
			return emailBlogsAddedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT));
		}
	}

	public static String getEmailBlogsUpdatedBody(
		PortletPreferences preferences) {

		String emailBlogsUpdatedBody = preferences.getValue(
			"email-blogs-updated-body", StringPool.BLANK);

		if (Validator.isNotNull(emailBlogsUpdatedBody)) {
			return emailBlogsUpdatedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY));
		}
	}

	public static boolean getEmailBlogsUpdatedEnabled(
		PortletPreferences preferences) {

		String emailBlogsUpdatedEnabled = preferences.getValue(
			"email-blogs-updated-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailBlogsUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailBlogsUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_ENABLED));
		}
	}

	public static String getEmailBlogsUpdatedSubject(
		PortletPreferences preferences) {

		String emailBlogsUpdatedSubject = preferences.getValue(
			"email-blogs-updated-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailBlogsUpdatedSubject)) {
			return emailBlogsUpdatedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT));
		}
	}

	public static String getEmailFromAddress(PortletPreferences preferences) {
		String emailFromAddress = PropsUtil.get(
			PropsKeys.BLOGS_EMAIL_FROM_ADDRESS);

		return preferences.getValue("email-from-address", emailFromAddress);
	}

	public static String getEmailFromName(PortletPreferences preferences) {
		String emailFromName = PropsUtil.get(PropsKeys.BLOGS_EMAIL_FROM_NAME);

		return preferences.getValue("email-from-name", emailFromName);
	}

	public static String getMailId(String mx, long entryId) {
		StringBuilder sb = new StringBuilder();

		sb.append(StringPool.LESS_THAN);
		sb.append(POP_PORTLET_PREFIX);
		sb.append(entryId);
		sb.append(StringPool.AT);
		sb.append(PropsValues.POP_SERVER_SUBDOMAIN);
		sb.append(StringPool.PERIOD);
		sb.append(mx);
		sb.append(StringPool.GREATER_THAN);

		return sb.toString();
	}

	public static String getUrlTitle(long entryId, String title) {
		title = title.trim().toLowerCase();

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			return String.valueOf(entryId);
		}
		else {
			return FriendlyURLNormalizer.normalize(
				title, _URL_TITLE_REPLACE_CHARS);
		}
	}

	private static final char[] _URL_TITLE_REPLACE_CHARS = new char[] {
		'.', '/'
	};

}