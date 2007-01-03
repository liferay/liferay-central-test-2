/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortalPreferences.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortalPreferences {

	public static final String RANDOM_KEY = "r";

	public PortalPreferences(PortletPreferencesImpl prefs, boolean signedIn) {
		_prefs = prefs;
		_signedIn = signedIn;
	}

	public String getValue(String namespace, String key) {
		return getValue(namespace, key, null);
	}

	public String getValue(String namespace, String key, String def) {
		key = _encodeKey(namespace, key);

		return _prefs.getValue(key, def);
	}

	public void setValue(String namespace, String key, String value) {
		if (Validator.isNull(key) || (key.equals(RANDOM_KEY))) {
			return;
		}

		key = _encodeKey(namespace, key);

		try {
			if (value != null) {
				_prefs.setValue(key, value);
			}
			else {
				_prefs.reset(key);
			}

			if (_signedIn) {
				_prefs.store();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private String _encodeKey(String namespace, String key) {
		return namespace + StringPool.POUND + key;
	}

	private static Log _log = LogFactory.getLog(PortalPreferences.class);

	private PortletPreferencesImpl _prefs;
	private boolean _signedIn;

}