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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * <a href="ExpandoUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 */
public class ExpandoConverterUtil {
	public static Serializable getAttributeFromString(
		int type, String attribute) {

		if (attribute == null) {
			return null;
		}

		try {
			if (type == ExpandoColumnConstants.BOOLEAN) {
				return Boolean.parseBoolean(attribute);
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				return Double.parseDouble(attribute);
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				return Float.parseFloat(attribute);
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				return Integer.parseInt(attribute);
			}
			else if (type == ExpandoColumnConstants.LONG) {
				return Long.parseLong(attribute);
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				return  Long.parseLong(attribute);
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Boolean[] parsed = new Boolean[vals.length];

				for (int i = 0; i < vals.length; i++) {
					parsed[i] = Boolean.parseBoolean(vals[i]);
				}

				return parsed;
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Double[] parsed = new Double[vals.length];

				for (int i = 0; i < vals.length; i++) {
					parsed[i] = Double.parseDouble(vals[i]);
				}

				return parsed;
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Float[] parsed = new Float[vals.length];

				for (int i = 0; i < vals.length; i++) {
					parsed[i] = Float.parseFloat(vals[i]);
				}

				return parsed;
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Integer[] parsed = new Integer[vals.length];

				for (int i = 0; i < vals.length; i++) {
					parsed[i] = Integer.parseInt(vals[i]);
				}

				return parsed;
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Long[] parsed = new Long[vals.length];

				for (int i = 0; i < vals.length; i++) {
					parsed[i] = Long.parseLong(vals[i]);
				}

				return parsed;
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Short[] parsed = new Short[vals.length];

				for (int i = 0; i < vals.length; i++) {
					parsed[i] = Short.parseShort(vals[i]);
				}

				return parsed;
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return attribute.split(StringPool.COMMA);
			}
			else if (type == ExpandoColumnConstants.DATE) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(
					_DEFAULT_DATE_FORMAT);
				return dateFormatter.parse(attribute);
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				String[] vals = attribute.split(StringPool.COMMA);
				Date[] parsed = new Date[vals.length];

				for (int i = 0; i < vals.length; i++) {
					SimpleDateFormat dateFormatter = new SimpleDateFormat(
						_DEFAULT_DATE_FORMAT);
					parsed[i] = dateFormatter.parse(vals[i]);
				}

				return parsed;
			}
			else {
				return attribute;
			}
		} catch (Exception e) {
			_log.warn(e);

			return null;
		}
	}

	public static String getStringFromAttribute(
			int type, Serializable attribute) {

		if (attribute == null) {
			return StringPool.BLANK;
		}

		if (type == ExpandoColumnConstants.BOOLEAN ||
			type == ExpandoColumnConstants.DOUBLE ||
			type == ExpandoColumnConstants.FLOAT ||
			type == ExpandoColumnConstants.INTEGER ||
			type == ExpandoColumnConstants.LONG ||
			type == ExpandoColumnConstants.SHORT) {

			return String.valueOf(attribute);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY ||
			type == ExpandoColumnConstants.DOUBLE_ARRAY ||
			type == ExpandoColumnConstants.FLOAT_ARRAY ||
			type == ExpandoColumnConstants.INTEGER_ARRAY ||
			type == ExpandoColumnConstants.LONG_ARRAY ||
			type == ExpandoColumnConstants.SHORT_ARRAY ||
			type == ExpandoColumnConstants.STRING_ARRAY) {

			return _convertToArray((Object[]) attribute);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(
				_DEFAULT_DATE_FORMAT);
			return dateFormatter.format((Date) attribute);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			Date[] vals = (Date[]) attribute;

			StringBuilder sb = new StringBuilder();

			if (vals.length > 0) {
				int i;
				for (i = 0; i < vals.length - 1; i ++) {
					SimpleDateFormat dateFormatter = new SimpleDateFormat(
						_DEFAULT_DATE_FORMAT);
					sb.append(dateFormatter.format(vals[i]));
					sb.append(StringPool.COMMA);
				}

				sb.append(vals[i]);
			}

			return sb.toString();
		}
		else {
			return attribute.toString();
		}
	}

	private static String _convertToArray(Object[] vals) {
		StringBuilder sb = new StringBuilder();

		if (vals.length > 0) {
			int i;
			for (i = 0; i < vals.length - 1; i ++) {
				sb.append(String.valueOf(vals[i]));
				sb.append(StringPool.COMMA);
			}

			sb.append(vals[i]);
		}

		return sb.toString();
	}

	private static final String _DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";
	private static Log _log = LogFactoryUtil.getLog(ExpandoConverterUtil.class);
}