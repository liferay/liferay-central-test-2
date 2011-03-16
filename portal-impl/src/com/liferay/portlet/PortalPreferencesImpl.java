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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalPreferencesImpl implements PortalPreferences {

	public PortalPreferencesImpl(
		PortletPreferencesImpl preferences, boolean signedIn) {

		_preferences = preferences;
		_signedIn = signedIn;
	}

	public String getValue(String namespace, String key) {
		return getValue(namespace, key, null);
	}

	public String getValue(String namespace, String key, String defaultValue) {
		key = _encodeKey(namespace, key);

		return _preferences.getValue(key, defaultValue);
	}

	public String[] getValues(String namespace, String key) {
		return getValues(namespace, key, null);
	}

	public String[] getValues(
		String namespace, String key, String[] defaultValue) {

		key = _encodeKey(namespace, key);

		return _preferences.getValues(key, defaultValue);
	}

	public void resetValues(String namespace) {
		try {
			Map<String, Preference> preferences = _preferences.getPreferences();

			for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
				String key = entry.getKey();

				if (key.startsWith(namespace) &&
					!_preferences.isReadOnly(key)) {

					_preferences.reset(key);
				}
			}

			_preferences.store();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setValue(String namespace, String key, String value) {
		if (Validator.isNull(key) || (key.equals(_RANDOM_KEY))) {
			return;
		}

		key = _encodeKey(namespace, key);

		try {
			if (value != null) {
				_preferences.setValue(key, value);
			}
			else {
				_preferences.reset(key);
			}

			if (_signedIn) {
				_preferences.store();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setValues(String namespace, String key, String[] values) {
		if (Validator.isNull(key) || (key.equals(_RANDOM_KEY))) {
			return;
		}

		key = _encodeKey(namespace, key);

		try {
			if (values != null) {
				_preferences.setValues(key, values);
			}
			else {
				_preferences.reset(key);
			}

			if (_signedIn) {
				_preferences.store();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private String _encodeKey(String namespace, String key) {
		return namespace + StringPool.POUND + key;
	}

	private static final String _RANDOM_KEY = "r";

	private static Log _log = LogFactoryUtil.getLog(PortalPreferences.class);

	private PortletPreferencesImpl _preferences;
	private boolean _signedIn;

}