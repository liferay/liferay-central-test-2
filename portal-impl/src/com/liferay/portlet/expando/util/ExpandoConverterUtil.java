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

package com.liferay.portlet.expando.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * <a href="ExpandoConverterUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ExpandoConverterUtil {

	public static Serializable getAttributeFromString(
		int type, String attribute) {

		if (attribute == null) {
			return null;
		}

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return GetterUtil.getBoolean(attribute);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return GetterUtil.getBooleanValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return GetterUtil.getDate(attribute, _getDateFormat());
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return GetterUtil.getDateValues(
				StringUtil.split(attribute), _getDateFormat());
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return GetterUtil.getDouble(attribute);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return GetterUtil.getDoubleValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return GetterUtil.getFloat(attribute);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return GetterUtil.getFloatValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return GetterUtil.getInteger(attribute);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return GetterUtil.getIntegerValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return GetterUtil.getLong(attribute);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return GetterUtil.getLongValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return GetterUtil.getShort(attribute);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return GetterUtil.getShortValues(StringUtil.split(attribute));
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return StringUtil.split(attribute);
		}
		else {
			return attribute;
		}
	}

	public static String getStringFromAttribute(
		int type, Serializable attribute) {

		if (attribute == null) {
			return StringPool.BLANK;
		}

		if ((type == ExpandoColumnConstants.BOOLEAN) ||
			(type == ExpandoColumnConstants.DOUBLE) ||
			(type == ExpandoColumnConstants.FLOAT) ||
			(type == ExpandoColumnConstants.INTEGER) ||
			(type == ExpandoColumnConstants.LONG) ||
			(type == ExpandoColumnConstants.SHORT)) {

			return String.valueOf(attribute);
		}
		else if ((type == ExpandoColumnConstants.BOOLEAN_ARRAY) ||
				 (type == ExpandoColumnConstants.DOUBLE_ARRAY) ||
				 (type == ExpandoColumnConstants.FLOAT_ARRAY) ||
				 (type == ExpandoColumnConstants.INTEGER_ARRAY) ||
				 (type == ExpandoColumnConstants.LONG_ARRAY) ||
				 (type == ExpandoColumnConstants.SHORT_ARRAY) ||
				 (type == ExpandoColumnConstants.STRING_ARRAY)) {

			return StringUtil.merge(
				ArrayUtil.toStringArray((Object[])attribute));
		}
		else if (type == ExpandoColumnConstants.DATE) {
			DateFormat dateFormat = _getDateFormat();

			return dateFormat.format((Date)attribute);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return StringUtil.merge(
				ArrayUtil.toStringArray((Date[])attribute, _getDateFormat()));
		}
		else {
			return attribute.toString();
		}
	}

	private static DateFormat _getDateFormat() {
		return new SimpleDateFormat(DateUtil.ISO_8601_PATTERN);
	}

}