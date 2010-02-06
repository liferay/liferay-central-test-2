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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoValue;

import java.io.IOException;
import java.io.Serializable;

/**
 * <a href="ExpandoColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnImpl
	extends ExpandoColumnModelImpl implements ExpandoColumn {

	public ExpandoColumnImpl() {
	}

	public Serializable getDefaultValue() {
		try {
			ExpandoValue value = new ExpandoValueImpl();

			value.setColumnId(getColumnId());
			value.setData(getDefaultData());

			int type = getType();

			if (type == ExpandoColumnConstants.BOOLEAN) {
				return value.getBoolean();
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				return value.getBooleanArray();
			}
			else if (type == ExpandoColumnConstants.DATE) {
				return value.getDate();
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				return value.getDateArray();
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				return value.getDouble();
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				return value.getDoubleArray();
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				return value.getFloat();
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				return value.getFloatArray();
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				return value.getInteger();
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				return value.getIntegerArray();
			}
			else if (type == ExpandoColumnConstants.LONG) {
				return value.getLong();
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				return value.getLongArray();
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				return value.getShort();
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				return value.getShortArray();
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return value.getStringArray();
			}
			else {
				return value.getString();
			}
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}
		else {
			return _typeSettingsProperties.toString();
		}
	}

	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			try {
				_typeSettingsProperties.load(super.getTypeSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _typeSettingsProperties;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoColumnImpl.class);

	private UnicodeProperties _typeSettingsProperties = null;

}