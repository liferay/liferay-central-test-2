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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class ParameterMapSettings extends BaseSettings {

	public static final String PREFERENCES_PREFIX = "preferences--";

	public static final String SETTINGS_PREFIX = "settings--";

	public ParameterMapSettings(
		Map<String, String[]> parameterMap, Settings defaultSettings) {

		_defaultSettings = defaultSettings;
		_parameterMap = parameterMap;
	}

	@Override
	public Settings getDefaultSettings() {
		return _defaultSettings;
	}

	@Override
	public Collection<String> getKeys() {
		Set<String> keys = new HashSet<String>();

		for (String key : _parameterMap.keySet()) {
			if (!key.endsWith(StringPool.DOUBLE_DASH)) {
				continue;
			}

			String name = null;

			if (key.startsWith(PREFERENCES_PREFIX)) {
				name = key.substring(
					PREFERENCES_PREFIX.length(), key.length() - 2);
			}
			else if (key.startsWith(SETTINGS_PREFIX)) {
				name = key.substring(
					PREFERENCES_PREFIX.length(), key.length() - 2);
			}

			if (name != null) {
				keys.add(name);
			}
		}

		keys.addAll(_defaultSettings.getKeys());

		return keys;
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String[] values = getParameterValue(key);

		if (values != null) {
			return values[0];
		}

		return _defaultSettings.getValue(key, defaultValue);
	}

	@Override
	public String[] getValues(String key, String[] defaultValue) {
		String[] values = getParameterValue(key);

		if (values != null) {
			return values;
		}

		return _defaultSettings.getValues(key, defaultValue);
	}

	@Override
	public void reset(String key) {
		_defaultSettings.reset(key);
	}

	@Override
	public Settings setValue(String key, String value) {
		return _defaultSettings.setValue(key, value);
	}

	@Override
	public Settings setValues(String key, String[] values) {
		return _defaultSettings.setValues(key, values);
	}

	@Override
	public void store() throws IOException, ValidatorException {
		_defaultSettings.store();
	}

	protected String[] getParameterValue(String key) {
		String[] values = _parameterMap.get(key);

		if (values == null) {
			values = _parameterMap.get(
				PREFERENCES_PREFIX + key + StringPool.DOUBLE_DASH);
		}

		if (values == null) {
			values = _parameterMap.get(
				SETTINGS_PREFIX + key + StringPool.DOUBLE_DASH);
		}

		return values;
	}

	private Settings _defaultSettings;
	private Map<String, String[]> _parameterMap;

}