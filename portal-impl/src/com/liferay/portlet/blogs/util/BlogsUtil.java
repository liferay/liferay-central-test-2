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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.ContentUtil;

import javax.portlet.PortletPreferences;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 * @author Juan Fernández
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

	public static Map<Locale, String> getEmailEntryAddedBodyLocalized(
		PortletPreferences preferences) {

		return getLocalizedParameter("emailEntryAddedBody", preferences);
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

	public static Map<Locale, String> getEmailEntryAddedSubjectLocalized(
		PortletPreferences preferences) {

		return getLocalizedParameter("emailEntryAddedSubject", preferences);
	}

	public static Map<Locale, String> getEmailEntryUpdatedBodyLocalized(
		PortletPreferences preferences) {

		return getLocalizedParameter("emailEntryUpdatedSubject", preferences);
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

	public static Map<Locale, String> getEmailEntryUpdatedSubjectLocalized(
		PortletPreferences preferences) {

		return getLocalizedParameter("emailEntryUpdatedSubject", preferences);
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

	private static Map<Locale, String> getLocalizedParameter(
		String parameter, PortletPreferences preferences) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localeParameter =
				parameter + StringPool.UNDERLINE + languageId;

			map.put(
				locale, preferences.getValue(
					localeParameter, StringPool.BLANK));
		}

		return map;
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