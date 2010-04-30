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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * <a href="Localization.java.html"><b><i>View Source</i></b></a>
 *
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
public interface Localization {

	public Object deserialize(JSONObject jsonObject);

	public String[] getAvailableLocales(String xml);

	public String getDefaultLocale(String xml);

	public String getLocalization(String xml, String requestedLanguageId);

	public String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault);

	public Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter);

	public Map<Locale, String> getLocalizationMap(String xml);

	/**
	 * @deprecated Use <code>getLocalizationMap</code>.
	 */
	public Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter);

	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId);

	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault);

	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId);

	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault);

	public String removeLocalization(
		String xml, String key, String requestedLanguageId);

	public String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata);

	public void setLocalizedPreferencesValues (
			ActionRequest actionRequest, PortletPreferences preferences,
			String parameter)
		throws Exception;

	public void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception;

	public void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception;

	public String updateLocalization(String xml, String key, String value);

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId);

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId);

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata);

}