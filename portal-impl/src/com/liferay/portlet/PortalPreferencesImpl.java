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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="PortalPreferencesImpl.java.html"><b><i>View Source</i></b></a>
 *
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