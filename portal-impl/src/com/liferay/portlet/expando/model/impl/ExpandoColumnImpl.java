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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoValue;

/**
 * <a href="ExpandoColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnImpl
	extends ExpandoColumnModelImpl implements ExpandoColumn {

	public ExpandoColumnImpl() {
	}

	public Object getDefaultValue() {
		ExpandoValue value = new ExpandoValueImpl();
		value.setColumnId(getColumnId());
		value.setData(getDefaultData());

		try {
			switch (getType()) {
				case ExpandoColumnConstants.BOOLEAN: {
					return value.getBoolean();
				}
				case ExpandoColumnConstants.BOOLEAN_ARRAY: {
					return value.getBooleanArray();
				}
				case ExpandoColumnConstants.DATE: {
					return value.getDate();
				}
				case ExpandoColumnConstants.DATE_ARRAY: {
					return value.getDateArray();
				}
				case ExpandoColumnConstants.DOUBLE: {
					return value.getDouble();
				}
				case ExpandoColumnConstants.DOUBLE_ARRAY: {
					return value.getDoubleArray();
				}
				case ExpandoColumnConstants.FLOAT: {
					return value.getFloat();
				}
				case ExpandoColumnConstants.FLOAT_ARRAY: {
					return value.getFloatArray();
				}
				case ExpandoColumnConstants.INTEGER: {
					return value.getInteger();
				}
				case ExpandoColumnConstants.INTEGER_ARRAY: {
					return value.getIntegerArray();
				}
				case ExpandoColumnConstants.LONG: {
					return value.getLong();
				}
				case ExpandoColumnConstants.LONG_ARRAY: {
					return value.getLongArray();
				}
				case ExpandoColumnConstants.SHORT: {
					return value.getShort();
				}
				case ExpandoColumnConstants.SHORT_ARRAY: {
					return value.getShortArray();
				}
				case ExpandoColumnConstants.STRING_ARRAY: {
					return value.getStringArray();
				}
				default: {
					return value.getString();
				}
			}
		}
		catch (Exception e) {
			return null;
		}
	}

}