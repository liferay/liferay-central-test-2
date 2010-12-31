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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * <p>
 * This class is used to localize values stored in XML and is often used to add
 * localization behavior to value objects.
 * </p>
 *
 * <p>
 * Caching of the localized values is done in this class rather than in the
 * value object since value objects get flushed from cache fairly quickly.
 * Though lookups performed on a key based on an XML file is slower than lookups
 * done at the value object level in general, the value object will get flushed
 * at a rate which works against the performance gain. The cache is a soft hash
 * map which prevents memory leaks within the system while enabling the cache to
 * live longer than in a weak hash map.
 * </p>
 *
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Mauro Mariuzzo
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class LocalizationUtil {

	public static Object deserialize(JSONObject jsonObject) {
		return getLocalization().deserialize(jsonObject);
	}

	public static String[] getAvailableLocales(String xml) {
		return getLocalization().getAvailableLocales(xml);
	}

	public static String getDefaultLocale(String xml) {
		return getLocalization().getDefaultLocale(xml);
	}

	public static Localization getLocalization() {
		return _localization;
	}

	public static String getLocalization(
		String xml, String requestedLanguageId) {

		return getLocalization().getLocalization(xml, requestedLanguageId);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault) {

		return getLocalization().getLocalization(
			xml, requestedLanguageId, useDefault);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter) {

		return getLocalization().getLocalizationMap(portletRequest, parameter);
	}

	public static Map<Locale, String> getLocalizationMap(String xml) {
		return getLocalization().getLocalizationMap(xml);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter) {

		return getLocalization().getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter);
	}

	/**
	 * @deprecated Use <code>getLocalizationMap</code>.
	 */
	public static Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter) {

		return getLocalization().getLocalizedParameter(
			portletRequest, parameter);
	}

	public static String getPreferencesKey(String key, String languageId) {
		return getLocalization().getPreferencesKey(key, languageId);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId) {

		return getLocalization().getPreferencesValue(
			preferences, key, languageId);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		return getLocalization().getPreferencesValue(
			preferences, key, languageId, useDefault);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId) {

		return getLocalization().getPreferencesValues(
			preferences, key, languageId);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		return getLocalization().getPreferencesValues(
			preferences, key, languageId, useDefault);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId) {

		return getLocalization().removeLocalization(
			xml, key, requestedLanguageId);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata) {

		return getLocalization().removeLocalization(
			xml, key, requestedLanguageId, cdata);
	}

	public static void setLocalizedPreferencesValues (
			PortletRequest portletRequest, PortletPreferences preferences,
			String parameter)
		throws Exception {

		getLocalization().setLocalizedPreferencesValues(
			portletRequest, preferences, parameter);
	}

	public static void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception {

		getLocalization().setPreferencesValue(
			preferences, key, languageId, value);
	}

	public static void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception {

		getLocalization().setPreferencesValues(
			preferences, key, languageId, values);
	}

	public static String updateLocalization(
		String xml, String key, String value) {

		return getLocalization().updateLocalization(xml, key, value);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, cdata);
	}

	public void setLocalization(Localization localization) {
		_localization = localization;
	}

	private static Localization _localization;

}