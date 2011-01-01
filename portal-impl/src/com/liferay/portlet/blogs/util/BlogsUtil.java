/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PropsUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class BlogsUtil {

	public static String getEmailEntryAddedBody(
		PortletPreferences preferences) {

		String emailEntryAddedBody = preferences.getValue(
			"emailEntryAddedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryAddedBody)) {
			return emailEntryAddedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY));
		}
	}

	public static boolean getEmailEntryAddedEnabled(
		PortletPreferences preferences) {

		String emailEntryAddedEnabled = preferences.getValue(
			"emailEntryAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryAddedEnabled)) {
			return GetterUtil.getBoolean(emailEntryAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_ENABLED));
		}
	}

	public static String getEmailEntryAddedSubject(
		PortletPreferences preferences) {

		String emailEntryAddedSubject = preferences.getValue(
			"emailEntryAddedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryAddedSubject)) {
			return emailEntryAddedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT));
		}
	}

	public static String getEmailEntryUpdatedBody(
		PortletPreferences preferences) {

		String emailEntryUpdatedBody = preferences.getValue(
			"emailEntryUpdatedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryUpdatedBody)) {
			return emailEntryUpdatedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY));
		}
	}

	public static boolean getEmailEntryUpdatedEnabled(
		PortletPreferences preferences) {

		String emailEntryUpdatedEnabled = preferences.getValue(
			"emailEntryUpdatedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailEntryUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_ENABLED));
		}
	}

	public static String getEmailEntryUpdatedSubject(
		PortletPreferences preferences) {

		String emailEntryUpdatedSubject = preferences.getValue(
			"emailEntryUpdatedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryUpdatedSubject)) {
			return emailEntryUpdatedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT));
		}
	}

	public static String getEmailFromAddress(PortletPreferences preferences) {
		String emailFromAddress = PropsUtil.get(
			PropsKeys.BLOGS_EMAIL_FROM_ADDRESS);

		return preferences.getValue("emailFromAddress", emailFromAddress);
	}

	public static String getEmailFromName(PortletPreferences preferences) {
		String emailFromName = PropsUtil.get(PropsKeys.BLOGS_EMAIL_FROM_NAME);

		return preferences.getValue("emailFromName", emailFromName);
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