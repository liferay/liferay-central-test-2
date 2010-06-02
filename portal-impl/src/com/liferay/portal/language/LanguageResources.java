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

package com.liferay.portal.language;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="LanguageResources.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class LanguageResources {

	public static String getMessage(Locale locale, String key) {
		if (locale == null) {
			return null;
		}

		Map<String, String> languageMap = _localeLanguageMap.get(locale);
		if (languageMap == null) {
			languageMap = loadLocale(locale);
		}

		String value = languageMap.get(key);
		if (value == null) {
			return getMessage(getSuperLocale(locale), key);
		}
		else {
			return value;
		}
	}

	public static Map<String, String> putLanguageMap(
		Locale locale, Map<String, String> languageMap) {
		return _localeLanguageMap.put(locale, languageMap);
	}

	public void setConfig(String config) {
		_config = config;
	}

	private static Locale getSuperLocale(Locale locale) {
		if (!locale.getVariant().equals(StringPool.BLANK)) {
			return new Locale(locale.getLanguage(), locale.getCountry());
		}

		if (!locale.getCountry().equals(StringPool.BLANK)) {
			return new Locale(locale.getLanguage());
		}

		if (!locale.getLanguage().equals(StringPool.BLANK)) {
			return new Locale(StringPool.BLANK);
		}

		return null;
	}

	private static Map<String, String> loadLocale(Locale locale) {

		String[] names = StringUtil.split(
			_config.replace(StringPool.PERIOD, StringPool.SLASH));
		Map<String, String> languageMap = null;
		if (names.length > 0) {
			String localeName = locale.toString();
			languageMap = new HashMap<String, String>();
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				StringBundler sb = new StringBundler(4);
				sb.append(name);
				if (localeName.length() > 0) {
					sb.append("_");
					sb.append(localeName);
				}
				sb.append(".properties");

				Properties properties = loadProperties(sb.toString());
				for(Map.Entry<Object, Object> entry : properties.entrySet()) {
					languageMap.put(
						(String)entry.getKey(), (String)entry.getValue());
				}
			}
		}
		else {
			languageMap = Collections.emptyMap();
		}
		_localeLanguageMap.put(locale, languageMap);
		return languageMap;
	}

	private static Properties loadProperties(String name) {

		Properties properties = new Properties();

		try {
			ClassLoader classLoader =
				LanguageResources.class.getClassLoader();
			URL url = classLoader.getResource(name);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Attempting to load " + name);
			}

			if (url != null) {
				InputStream is = url.openStream();

				properties.load(is);

				is.close();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Loading " + url + " with " + properties.size() +
							" values");
				}
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return properties;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LanguageResources.class);

	private static String _config;

	private static Map<Locale, Map<String, String>> _localeLanguageMap =
		new ConcurrentHashMap<Locale, Map<String, String>>(64);

}