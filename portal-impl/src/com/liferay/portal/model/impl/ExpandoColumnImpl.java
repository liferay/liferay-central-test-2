/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SafeProperties;
import com.liferay.portal.model.ExpandoColumn;

import java.io.IOException;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ExpandoColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnImpl extends ExpandoColumnModelImpl
	implements ExpandoColumn {

	public static final int BOOLEAN = 0;

	public static final int BOOLEAN_ARRAY = 1;

	public static final int DATE = 2;

	public static final int DOUBLE = 3;

	public static final int DOUBLE_ARRAY = 4;

	public static final int FLOAT = 5;

	public static final int FLOAT_ARRAY = 6;

	public static final int INTEGER = 7;

	public static final int INTEGER_ARRAY = 8;

	public static final int LONG = 9;

	public static final int LONG_ARRAY = 10;

	public static final int SHORT = 11;

	public static final int SHORT_ARRAY = 12;

	public static final int STRING = 13;

	public static final int[] TYPES = new int[] {
		BOOLEAN, BOOLEAN_ARRAY, DATE, DOUBLE, DOUBLE_ARRAY, FLOAT, FLOAT_ARRAY,
		INTEGER, INTEGER_ARRAY, LONG, LONG_ARRAY, SHORT, SHORT_ARRAY, STRING
	};

	public ExpandoColumnImpl() {
	}

	public Properties getSettingsProperties() {
		if (_settingsProperties == null) {
			_settingsProperties = new SafeProperties();

			try {
				PropertiesUtil.load(
					_settingsProperties, super.getSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		return _settingsProperties;
	}

	public void setSettingsProperties(Properties settingsProperties) {
		_settingsProperties = settingsProperties;

		if (_settingsProperties != null) {
			super.setSettings(PropertiesUtil.toString(_settingsProperties));
		}
	}

	private static Log _log = LogFactory.getLog(ExpandoColumnImpl.class);

	private Properties _settingsProperties = null;

}