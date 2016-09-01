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

package com.liferay.portal.kernel.util;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;

import java.util.Date;
import java.util.Locale;

/**
 * Provides utility methods for reading values as various types.
 *
 * @author Brian Wing Shun Chan
 */
public class GetterUtil {

	/**
	 * String values that can be converted to <code>true<code/> booleans
	 */
	public static final String[] BOOLEANS = {"true", "t", "y", "on", "1"};

	/**
	 * The default boolean value is {@value #DEFAULT_BOOLEAN}
	 */
	public static final boolean DEFAULT_BOOLEAN = false;

	/**
	 * The default boolean array value is an empty boolean array
	 */
	public static final boolean[] DEFAULT_BOOLEAN_VALUES = new boolean[0];

	/**
	 * The default byte value is {@value #DEFAULT_BYTE}
	 */
	public static final byte DEFAULT_BYTE = 0;

	/**
	 * The default byte array value is an empty byte array
	 */
	public static final byte[] DEFAULT_BYTE_VALUES = new byte[0];

	/**
	 * The default Date array value is an empty Date array
	 */
	public static final Date[] DEFAULT_DATE_VALUES = new Date[0];

	/**
	 * The default double value is {@value #DEFAULT_DOUBLE}
	 */
	public static final double DEFAULT_DOUBLE = 0.0;

	/**
	 * The default double array value is an empty double array
	 */
	public static final double[] DEFAULT_DOUBLE_VALUES = new double[0];

	/**
	 * The default float value is {@value #DEFAULT_FLOAT}
	 */
	public static final float DEFAULT_FLOAT = 0;

	/**
	 * The default float array value is an empty float array
	 */
	public static final float[] DEFAULT_FLOAT_VALUES = new float[0];

	/**
	 * The default integer value is {@value #DEFAULT_INTEGER}
	 */
	public static final int DEFAULT_INTEGER = 0;

	/**
	 * The default integer array value is an empty integer array
	 */
	public static final int[] DEFAULT_INTEGER_VALUES = new int[0];

	/**
	 * The default long value is {@value #DEFAULT_LONG}
	 */
	public static final long DEFAULT_LONG = 0;

	/**
	 * The default long array value is an empty long array
	 */
	public static final long[] DEFAULT_LONG_VALUES = new long[0];

	/**
	 * The default number value is {@value #DEFAULT_NUMBER}
	 */
	public static final Number DEFAULT_NUMBER = 0;

	/**
	 * The default Number array value is an empty Number array
	 */
	public static final Number[] DEFAULT_NUMBER_VALUES = new Number[0];

	/**
	 * The default Object value is <code>null<code/>
	 */
	public static final Number DEFAULT_OBJECT = null;

	/**
	 * The default short value is {@value #DEFAULT_SHORT}
	 */
	public static final short DEFAULT_SHORT = 0;

	/**
	 * The default short array value is an empty short array
	 */
	public static final short[] DEFAULT_SHORT_VALUES = new short[0];

	/**
	 * The default String value is {@value #DEFAULT_STRING}
	 */
	public static final String DEFAULT_STRING = StringPool.BLANK;

	/**
	 * The default String array value is an empty String array
	 */
	public static final String[] DEFAULT_STRING_VALUES = new String[0];

	/**
	 * Returns the Object value as a boolean. If the value is <code>null</code>,
	 * the default value is returned. If the value does not match a {@link
	 * #BOOLEANS} value, <code>false</code> is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a boolean
	 */
	public static boolean get(Object value, boolean defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		if (value instanceof Boolean) {
			return (Boolean)value;
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a Date. If the value is <code>null</code> or
	 * not convertible to a Date, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue a default value
	 * @return the value as a Date
	 */
	public static Date get(
		Object value, DateFormat dateFormat, Date defaultValue) {

		if (value instanceof String) {
			return get((String)value, dateFormat, defaultValue);
		}

		if (value instanceof Date) {
			return (Date)value;
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a double. If the value is <code>null</code>
	 * or not convertible to a double, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a double
	 */
	public static double get(Object value, double defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		if (value instanceof Double) {
			return (Double)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.doubleValue();
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a float. If the value is <code>null</code> or
	 * not convertible to a float, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a float
	 */
	public static float get(Object value, float defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		if (value instanceof Float) {
			return (Float)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.floatValue();
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as an integer. If the value is <code>null</code>
	 * or not convertible to an integer, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as an integer
	 */
	public static int get(Object value, int defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		if (value instanceof Integer) {
			return (Integer)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.intValue();
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a long. If the value is <code>null</code> or
	 * not convertible to a long, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a long
	 */
	public static long get(Object value, long defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		if (value instanceof Long) {
			return (Long)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.longValue();
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a Number. If the value is <code>null</code>
	 * or not convertible to a Number, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a Number
	 */
	public static Number get(Object value, Number defaultValue) {
		if (value instanceof String) {
			String valueString = (String)value;

			if (Validator.isNull(valueString)) {
				return defaultValue;
			}

			try {
				return new BigDecimal(valueString.trim());
			}
			catch (NumberFormatException nfe) {
				return defaultValue;
			}
		}

		if (value instanceof Byte) {
			return (Byte)value;
		}

		if (value instanceof Double) {
			return (Double)value;
		}

		if (value instanceof Float) {
			return (Float)value;
		}

		if (value instanceof Integer) {
			return (Integer)value;
		}

		if (value instanceof Long) {
			return (Long)value;
		}

		if (value instanceof Short) {
			return (Short)value;
		}

		if (value instanceof Number) {
			return (Number)value;
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a short. If the value is <code>null</code> or
	 * not convertible to a short, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a short
	 */
	public static short get(Object value, short defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		if (value instanceof Short) {
			return (Short)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.shortValue();
		}

		return defaultValue;
	}

	/**
	 * Returns the Object value as a String. If the value is <code>null</code>
	 * or not convertible to a String, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a String
	 */
	public static String get(Object value, String defaultValue) {
		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		return defaultValue;
	}

	/**
	 * Returns the String value as a boolean. If the value is <code>null</code>,
	 * the default value is returned. If the value does not match a {@link
	 * #BOOLEANS} value, <code>false</code> is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a boolean
	 */
	public static boolean get(String value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		value = value.trim();

		value = _toLowerCase(value);

		if (value.equals(BOOLEANS[0]) || value.equals(BOOLEANS[1]) ||
			value.equals(BOOLEANS[2]) || value.equals(BOOLEANS[3]) ||
			value.equals(BOOLEANS[4])) {

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns the String value as a Date. If the value is <code>null</code> or
	 * not convertible to a Date, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue a default value
	 * @return the value as a Date
	 */
	public static Date get(
		String value, DateFormat dateFormat, Date defaultValue) {

		if (value == null) {
			return defaultValue;
		}

		try {
			Date date = dateFormat.parse(value.trim());

			if (date != null) {
				return date;
			}
		}
		catch (Exception e) {
		}

		return defaultValue;
	}

	/**
	 * Returns the String value as a double. If the value is <code>null</code>
	 * or not convertible to a double, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a double
	 */
	public static double get(String value, double defaultValue) {
		return get(value, defaultValue, null);
	}

	/**
	 * Returns the String value as a double. If the value is <code>null</code>
	 * or not convertible to a double, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @param  locale the locale used to parse the double value
	 * @return the value as a double
	 */
	public static double get(String value, double defaultValue, Locale locale) {
		if (value == null) {
			return defaultValue;
		}

		value = value.trim();

		if (locale == null) {
			try {
				return Double.parseDouble(value);
			}
			catch (Exception e) {
			}
		}
		else {
			NumberFormat numberFormat = NumberFormat.getInstance(locale);

			try {
				ParsePosition parsePosition = new ParsePosition(0);

				Number number = numberFormat.parse(value, parsePosition);

				if (parsePosition.getIndex() == value.length()) {
					return number.doubleValue();
				}
			}
			catch (Exception e) {
			}
		}

		return defaultValue;
	}

	/**
	 * Returns the String value as a float. If the value is <code>null</code> or
	 * not convertible to a float, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a float
	 */
	public static float get(String value, float defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		try {
			return Float.parseFloat(value.trim());
		}
		catch (Exception e) {
		}

		return defaultValue;
	}

	/**
	 * Returns the String value as an integer. If the value is <code>null</code>
	 * or not convertible to an integer, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as an integer
	 */
	public static int get(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return _parseInt(value.trim(), defaultValue);
	}

	/**
	 * Returns the String value as a long. If the value is <code>null</code> or
	 * not convertible to a long, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a long
	 */
	public static long get(String value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return _parseLong(value.trim(), defaultValue);
	}

	/**
	 * Returns the String value as a short. If the value is <code>null</code> or
	 * not convertible to a short, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a short
	 */
	public static short get(String value, short defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return _parseShort(value.trim(), defaultValue);
	}

	/**
	 * Returns the String value as a String. If the value is <code>null</code>
	 * or not convertible to a String, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a String
	 */
	public static String get(String value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		value = value.trim();

		if (value.indexOf(CharPool.RETURN) != -1) {
			value = value.replaceAll(
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
		}

		return value;
	}

	/**
	 * Returns the Object value as a boolean. If the value is <code>null</code>,
	 * the {@link #DEFAULT_BOOLEAN} is returned. If the value does not match a
	 * {@link #BOOLEANS} value, <code>false</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a boolean
	 */
	public static boolean getBoolean(Object value) {
		return getBoolean(value, DEFAULT_BOOLEAN);
	}

	/**
	 * Returns the Object value as a boolean. If the value is <code>null</code>,
	 * the default value is returned. If the value does not match a {@link
	 * #BOOLEANS} value, <code>false</code> is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a boolean
	 */
	public static boolean getBoolean(Object value, boolean defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a boolean. If the value is <code>null</code>,
	 * the {@link #DEFAULT_BOOLEAN} is returned. If the value does not match a
	 * {@link #BOOLEANS} value, <code>false</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a boolean
	 */
	public static boolean getBoolean(String value) {
		return getBoolean(value, DEFAULT_BOOLEAN);
	}

	/**
	 * Returns the String value as a boolean. If the value is <code>null</code>,
	 * the default value is returned. If the value does not match a {@link
	 * #BOOLEANS} value, <code>false</code> is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a boolean
	 */
	public static boolean getBoolean(String value, boolean defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the Object value as a boolean array. If the value is
	 * <code>null</code> the {@link #DEFAULT_BOOLEAN_VALUES} is returned.
	 * In the returned array, each value element that is <code>null</code>
	 * is replaced by a {@link #DEFAULT_BOOLEAN}, and each value element that
	 * does not match a {@link #BOOLEANS} value is replaced by a
	 * <code>false</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a boolean array
	 */
	public static boolean[] getBooleanValues(Object value) {
		return getBooleanValues(value, DEFAULT_BOOLEAN_VALUES);
	}

	/**
	 * Returns the Object value as a boolean array. If the value is
	 * <code>null</code> the default value is returned.
	 * In the returned array, each value element that is <code>null</code>
	 * is replaced by a default value, and each value element that
	 * does not match a {@link #BOOLEANS} value is replaced by a
	 * <code>false</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a boolean array
	 */
	public static boolean[] getBooleanValues(
		Object value, boolean[] defaultValue) {

		if (value instanceof String[]) {
			return getBooleanValues((String[])value, defaultValue);
		}

		if (value instanceof boolean[]) {
			return (boolean[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String value sas a boolean array. If the value is
	 * <code>null</code> the {@link #DEFAULT_BOOLEAN_VALUES} is returned.
	 * In the returned array, each value element that is <code>null</code>
	 * is replaced by a {@link #DEFAULT_BOOLEAN}, and each value element that
	 * does not match a {@link #BOOLEANS} value is replaced by a
	 * <code>false</code>.
	 *
	 * @param  values the value to convert
	 * @return the value as a boolean array
	 */
	public static boolean[] getBooleanValues(String[] values) {
		return getBooleanValues(values, DEFAULT_BOOLEAN_VALUES);
	}

	/**
	 * Returns the String values as a boolean array. If the value is
	 * <code>null</code> the default value is returned.
	 * In the returned array, each value element that is <code>null</code>
	 * is replaced by a default value, and each value element that
	 * does not match a {@link #BOOLEANS} value is replaced by a
	 * <code>false</code>.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a boolean array
	 */
	public static boolean[] getBooleanValues(
		String[] values, boolean[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		boolean[] booleanValues = new boolean[values.length];

		for (int i = 0; i < values.length; i++) {
			booleanValues[i] = getBoolean(values[i]);
		}

		return booleanValues;
	}

	/**
	 * Returns the Object value as a Date. If the value is <code>null</code> or
	 * not convertible to a Date, the current date is returned.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @return the value as a Date
	 */
	public static Date getDate(Object value, DateFormat dateFormat) {
		return getDate(value, dateFormat, new Date());
	}

	/**
	 * Returns the Object value as a Date. If the value is <code>null</code> or
	 * not convertible to a Date, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue a default value
	 * @return the value as a Date
	 */
	public static Date getDate(
		Object value, DateFormat dateFormat, Date defaultValue) {

		return get(value, dateFormat, defaultValue);
	}

	/**
	 * Returns the String value as a Date. If the value is <code>null</code> or
	 * not convertible to a Date, the current date is returned.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @return the value as a Date
	 */
	public static Date getDate(String value, DateFormat dateFormat) {
		return getDate(value, dateFormat, new Date());
	}

	/**
	 * Returns the String value as a Date. If the value is <code>null</code> or
	 * not convertible to a Date, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue a default value
	 * @return the value as a Date
	 */
	public static Date getDate(
		String value, DateFormat dateFormat, Date defaultValue) {

		return get(value, dateFormat, defaultValue);
	}

	/**
	 * Returns the Object value as a Date array. If the value is <code>null</code>
	 * the {@link #DEFAULT_DATE_VALUES} is returned. In the returned array, each
	 * value element not convertible to a Date is replaced by the current date.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @return the value as a Date array
	 */
	public static Date[] getDateValues(Object value, DateFormat dateFormat) {
		return getDateValues(value, dateFormat, DEFAULT_DATE_VALUES);
	}

	/**
	 * Returns the Object value as a Date array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each
	 * value element not convertible to a Date is replaced by the current date.
	 *
	 * @param  value the value to convert
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue a default value
	 * @return the value as a Date array
	 */
	public static Date[] getDateValues(
		Object value, DateFormat dateFormat, Date[] defaultValue) {

		if (value instanceof String[]) {
			return getDateValues((String[])value, dateFormat, defaultValue);
		}

		if (value instanceof Date[]) {
			return (Date[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String value as a Date array. If the value is <code>null</code>
	 * the {@link #DEFAULT_DATE_VALUES} is returned. In the returned array, each
	 * value element not convertible to a Date is replaced by the current date.
	 *
	 * @param  values the values to convert
	 * @param  dateFormat the format used to parse the date
	 * @return the value as a Date array
	 */
	public static Date[] getDateValues(String[] values, DateFormat dateFormat) {
		return getDateValues(values, dateFormat, DEFAULT_DATE_VALUES);
	}

	/**
	 * Returns the String value as a Date array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each
	 * value element not convertible to a Date is replaced by the current date.
	 *
	 * @param  values the values to convert
	 * @param  dateFormat the format used to parse the date
	 * @param  defaultValue a default value
	 * @return the value as a Date array
	 */
	public static Date[] getDateValues(
		String[] values, DateFormat dateFormat, Date[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Date[] dateValues = new Date[values.length];

		for (int i = 0; i < values.length; i++) {
			dateValues[i] = getDate(values[i], dateFormat);
		}

		return dateValues;
	}

	/**
	 * Returns the Object value as a double. If the value is <code>null</code>
	 * or not convertible to a double, <code>{@link #DEFAULT_DOUBLE}</code>
	 * is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a double
	 */
	public static double getDouble(Object value) {
		return getDouble(value, DEFAULT_DOUBLE);
	}

	/**
	 * Returns the Object value as a double. If the value is <code>null</code>
	 * or not convertible to a double, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a double
	 */
	public static double getDouble(Object value, double defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a double. If the value is <code>null</code>
	 * or not convertible to a double, <code>{@link #DEFAULT_DOUBLE}</code>
	 * is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a double
	 */
	public static double getDouble(String value) {
		return getDouble(value, DEFAULT_DOUBLE);
	}

	/**
	 * Returns the String value as a double. If the value is <code>null</code>
	 * or not convertible to a double, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a double
	 */
	public static double getDouble(String value, double defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a double. If the value is <code>null</code>
	 * or not convertible to a double, <code>{@link #DEFAULT_DOUBLE}</code>
	 * is returned.
	 *
	 * @param  value the value to convert
	 * @param  locale the locale used to parse the double value
	 * @return the value as a double
	 */
	public static double getDouble(String value, Locale locale) {
		return get(value, DEFAULT_DOUBLE, locale);
	}

	/**
	 * Returns the Object value as a double array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each
	 * value element not convertible to a double is replaced by
	 * <code>{@link #DEFAULT_DOUBLE}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a double array
	 */
	public static double[] getDoubleValues(Object value) {
		return getDoubleValues(value, DEFAULT_DOUBLE_VALUES);
	}

	/**
	 * Returns the Object values as a double array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each
	 * value element not convertible to a double is replaced by
	 * <code>{@link #DEFAULT_DOUBLE}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a double array
	 */
	public static double[] getDoubleValues(
		Object value, double[] defaultValue) {

		if (value instanceof String[]) {
			return getDoubleValues((String[])value, defaultValue);
		}

		if (value instanceof double[]) {
			return (double[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String values as a double array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a double is replaced by <code>{@link #DEFAULT_DOUBLE}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as a double array
	 */
	public static double[] getDoubleValues(String[] values) {
		return getDoubleValues(values, DEFAULT_DOUBLE_VALUES);
	}

	/**
	 * Returns the String values as a double array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a double is replaced by the default value.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a double array
	 */
	public static double[] getDoubleValues(
		String[] values, double[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		double[] doubleValues = new double[values.length];

		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = getDouble(values[i]);
		}

		return doubleValues;
	}

	/**
	 * Returns the Object value as a float. If the value is <code>null</code> or
	 * not convertible to a float, <code>{@link #DEFAULT_FLOAT}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a float
	 */
	public static float getFloat(Object value) {
		return getFloat(value, DEFAULT_FLOAT);
	}

	/**
	 * Returns the Object value as a float. If the value is <code>null</code> or
	 * not convertible to a float, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a float
	 */
	public static float getFloat(Object value, float defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a float. If the value is <code>null</code> or
	 * not convertible to a float, <code>{@link #DEFAULT_FLOAT}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a float
	 */
	public static float getFloat(String value) {
		return getFloat(value, DEFAULT_FLOAT);
	}

	/**
	 * Returns the String value as a float. If the value is <code>null</code> or
	 * not convertible to a float, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a float
	 */
	public static float getFloat(String value, float defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the Object value as a float array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_FLOAT_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a float is replaced by
	 * <code>{@link #DEFAULT_FLOAT}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a float array
	 */
	public static float[] getFloatValues(Object value) {
		return getFloatValues(value, DEFAULT_FLOAT_VALUES);
	}

	/**
	 * Returns the Object value as a float array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a float is replaced by <code>{@link #DEFAULT_FLOAT}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a float array
	 */
	public static float[] getFloatValues(Object value, float[] defaultValue) {
		if (value instanceof String[]) {
			return getFloatValues((String[])value, defaultValue);
		}

		if (value instanceof float[]) {
			return (float[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String values as a float array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_FLOAT_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a float is replaced by
	 * <code>{@link #DEFAULT_FLOAT}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as a float array
	 */
	public static float[] getFloatValues(String[] values) {
		return getFloatValues(values, DEFAULT_FLOAT_VALUES);
	}

	/**
	 * Returns the String values as a float array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_FLOAT_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a float is replaced by
	 * default value.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a float array
	 */
	public static float[] getFloatValues(
		String[] values, float[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		float[] floatValues = new float[values.length];

		for (int i = 0; i < values.length; i++) {
			floatValues[i] = getFloat(values[i]);
		}

		return floatValues;
	}

	/**
	 * Returns the Object value as an integer. If the value is <code>null</code>
	 * or not convertible to an integer, <code>{@link #DEFAULT_INTEGER}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as an integer
	 */
	public static int getInteger(Object value) {
		return getInteger(value, DEFAULT_INTEGER);
	}

	/**
	 * Returns the Object value as an integer. If the value is <code>null</code>
	 * or not convertible to an integer, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as an integer
	 */
	public static int getInteger(Object value, int defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as an integer. If the value is <code>null</code>
	 * or not convertible to an integer, <code>{@link #DEFAULT_INTEGER}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as an integer
	 */
	public static int getInteger(String value) {
		return getInteger(value, DEFAULT_INTEGER);
	}

	/**
	 * Returns the String value as an integer. If the value is <code>null</code>
	 * or not convertible to an integer, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as an integer
	 */
	public static int getInteger(String value, int defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as an integer. If the value is not convertible
	 * to an integer, a <code>NumberFormatException</code> is thrown.
	 *
	 * @param  value the value to convert
	 * @return the value as an integer
	 * @throws NumberFormatException when value is not convertible to Integer
	 */
	public static int getIntegerStrict(String value) {
		int length = value.length();

		if (length <= 0) {
			throw new NumberFormatException("Unable to parse " + value);
		}

		int index = 0;
		int limit = -Integer.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Integer.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			if (length == 1) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			index++;
		}

		int smallLimit = limit / 10;

		int result = 0;

		while (index < length) {
			if (result < smallLimit) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			c = value.charAt(index++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			result -= number;
		}

		if (negative) {
			return result;
		}
		else {
			return -result;
		}
	}

	/**
	 * Returns the Object value as a integer array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_INTEGER_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a integer is replaced by
	 * <code>{@link #DEFAULT_INTEGER}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as an integer array
	 */
	public static int[] getIntegerValues(Object value) {
		return getIntegerValues(value, DEFAULT_INTEGER_VALUES);
	}

	/**
	 * Returns the Object value as a integer array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a integer is replaced by <code>{@link #DEFAULT_INTEGER}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as an integer array
	 */
	public static int[] getIntegerValues(Object value, int[] defaultValue) {
		if (value instanceof String[]) {
			return getIntegerValues((String[])value, defaultValue);
		}

		if (value instanceof int[]) {
			return (int[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String array values as a integer array. If values is <code>null</code>
	 * the <code>{@link #DEFAULT_INTEGER_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a integer is replaced by
	 * <code>{@link #DEFAULT_INTEGER}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as an integer array
	 */
	public static int[] getIntegerValues(String[] values) {
		return getIntegerValues(values, DEFAULT_INTEGER_VALUES);
	}

	/**
	 * Returns the String array values as a integer array. If values is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a integer is replaced by <code>{@link #DEFAULT_INTEGER}</code>.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as an integer array
	 */
	public static int[] getIntegerValues(String[] values, int[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		int[] intValues = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			intValues[i] = getInteger(values[i]);
		}

		return intValues;
	}

	/**
	 * Returns the Object value as a long. If the value is <code>null</code> or
	 * not convertible to a long, <code>{@link #DEFAULT_LONG}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a long
	 */
	public static long getLong(Object value) {
		return getLong(value, DEFAULT_LONG);
	}

	/**
	 * Returns the Object value as a long. If the value is <code>null</code> or
	 * not convertible to a long, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a long
	 */
	public static long getLong(Object value, long defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a long. If the value is <code>null</code> or
	 * not convertible to a long, <code>{@link #DEFAULT_LONG}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a long
	 */
	public static long getLong(String value) {
		return getLong(value, DEFAULT_LONG);
	}

	/**
	 * Returns the String value as a long. If the value is <code>null</code> or
	 * not convertible to a long, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a long
	 */
	public static long getLong(String value, long defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a long. If the value is not convertible
	 * to a long, a <code>NumberFormatException</code> is thrown.
	 *
	 * @param  value the value to convert
	 * @return the value as an long
	 * @throws NumberFormatException when value is not convertible to Integer
	 */
	public static long getLongStrict(String value) {
		int length = value.length();

		if (length <= 0) {
			throw new NumberFormatException("Unable to parse " + value);
		}

		int index = 0;
		long limit = -Long.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Long.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			if (length == 1) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			index++;
		}

		long smallLimit = limit / 10;

		long result = 0;

		while (index < length) {
			if (result < smallLimit) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			c = value.charAt(index++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				throw new NumberFormatException("Unable to parse " + value);
			}

			result -= number;
		}

		if (negative) {
			return result;
		}
		else {
			return -result;
		}
	}

	/**
	 * Returns the Object value as a long array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_LONG_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a long is replaced by
	 * <code>{@link #DEFAULT_LONG}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a long array
	 */
	public static long[] getLongValues(Object value) {
		return getLongValues(value, DEFAULT_LONG_VALUES);
	}

	/**
	 * Returns the Object value as a long array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a long is replaced by <code>{@link #DEFAULT_LONG}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a long array
	 */
	public static long[] getLongValues(Object value, long[] defaultValue) {
		if (value instanceof String[]) {
			return getLongValues((String[])value, defaultValue);
		}

		if (value instanceof long[]) {
			return (long[])value;
		}

		if (value instanceof Number[]) {
			Number[] numbers = (Number[])value;

			long[] values = new long[numbers.length];

			for (int i = 0; i < values.length; i++) {
				values[i] = numbers[i].longValue();
			}

			return values;
		}

		return defaultValue;
	}

	/**
	 * Returns the String array values as a long array. If values is <code>null</code>
	 * the <code>{@link #DEFAULT_LONG_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a long is replaced by
	 * <code>{@link #DEFAULT_LONG}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as a long array
	 */
	public static long[] getLongValues(String[] values) {
		return getLongValues(values, DEFAULT_LONG_VALUES);
	}

	/**
	 * Returns the String array values as a long array. If values is <code>null</code>
	 * the default value is returned. In the returned array, each value element not
	 * convertible to a long is replaced by <code>{@link #DEFAULT_LONG}</code>.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a long array
	 */
	public static long[] getLongValues(String[] values, long[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		long[] longValues = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			longValues[i] = getLong(values[i]);
		}

		return longValues;
	}

	/**
	 * Returns the Object value as a Number. If the value is <code>null</code>
	 * or not convertible to a Number, <code>{@link #DEFAULT_NUMBER}</code>
	 * is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a Number
	 */
	public static Number getNumber(Object value) {
		return getNumber(value, DEFAULT_NUMBER);
	}

	/**
	 * Returns the Object value as a Number. If the value is <code>null</code>
	 * or not convertible to a Number, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a Number
	 */
	public static Number getNumber(Object value, Number defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a Number. If the value is <code>null</code>
	 * or not convertible to a Number, <code>{@link #DEFAULT_NUMBER}</code> is
	 * returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a Number
	 */
	public static Number getNumber(String value) {
		return getNumber(value, DEFAULT_NUMBER);
	}

	/**
	 * Returns the String value as a Number. If the value is <code>null</code>
	 * or not convertible to a Number, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a Number
	 */
	public static Number getNumber(String value, Number defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the Object value as a Number array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_NUMBER_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a Number is
	 * replaced by <code>{@link #DEFAULT_NUMBER}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a Number array
	 */
	public static Number[] getNumberValues(Object value) {
		return getNumberValues(value, DEFAULT_NUMBER_VALUES);
	}

	/**
	 * Returns the Object value as a Number array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a Number is replaced by <code>{@link #DEFAULT_NUMBER}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a Number array
	 */
	public static Number[] getNumberValues(
		Object value, Number[] defaultValue) {

		if (value instanceof String[]) {
			return getNumberValues((String[])value, defaultValue);
		}

		if (value instanceof Number[]) {
			return (Number[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String array values as a Number array. If the value is
	 * <code>null</code> the <code>{@link #DEFAULT_NUMBER_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a Number is
	 * replaced by <code>{@link #DEFAULT_NUMBER}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as a Number array
	 */
	public static Number[] getNumberValues(String[] values) {
		return getNumberValues(values, DEFAULT_NUMBER_VALUES);
	}

	/**
	 * Returns the String array values as a Number array. If the value is
	 * <code>null</code> default value is returned. In the returned array,
	 * each value element not convertible to a Number is replaced by
	 * <code>{@link #DEFAULT_NUMBER}</code>.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a Number array
	 */
	public static Number[] getNumberValues(
		String[] values, Number[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Number[] numberValues = new Number[values.length];

		for (int i = 0; i < values.length; i++) {
			numberValues[i] = getNumber(values[i]);
		}

		return numberValues;
	}

	/**
	 * Returns the Object value. If the value is  <code>null</code>,
	 * <code>{@link #DEFAULT_OBJECT}</code> is returned
	 *
	 * @param  value the value
	 * @return the value
	 */
	public static Object getObject(Object value) {
		return getObject(value, DEFAULT_OBJECT);
	}

	/**
	 * Returns the Object value. If the value is <code>null</code>, the default
	 * value is returned.
	 *
	 * @param  value the value
	 * @param  defaultValue a default value
	 * @return the value
	 */
	public static Object getObject(Object value, Object defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return value;
	}

	/**
	 * Returns the Object value as a short. If the value is <code>null</code> or
	 * not convertible to a short, {<code>{@link #DEFAULT_SHORT}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a short
	 */
	public static short getShort(Object value) {
		return getShort(value, DEFAULT_SHORT);
	}

	/**
	 * Returns the Object value as a short. If the value is <code>null</code> or
	 * not convertible to a short, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a short
	 */
	public static short getShort(Object value, short defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as a short. If the value is <code>null</code> or
	 * not convertible to a short, <code>{@link #DEFAULT_SHORT}</code> is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a short
	 */
	public static short getShort(String value) {
		return getShort(value, DEFAULT_SHORT);
	}

	/**
	 * Returns the String value as a short. If the value is <code>null</code> or
	 * not convertible to a short, the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a short
	 */
	public static short getShort(String value, short defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value as an short. If the value is not convertible
	 * to a long, a <code>NumberFormatException</code> is thrown.
	 *
	 * @param  value the value to convert
	 * @return the value as a short
	 * @throws NumberFormatException when value is not convertible to Integer
	 */
	public static short getShortStrict(String value) {
		int i = getIntegerStrict(value);

		if ((i < Short.MIN_VALUE) || (i > Short.MAX_VALUE)) {
			throw new NumberFormatException("Out of range value " + value);
		}

		return (short)i;
	}

	/**
	 * Returns the Object value as a short array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_SHORT_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a short is
	 * replaced by <code>{@link #DEFAULT_SHORT}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a short array
	 */
	public static short[] getShortValues(Object value) {
		return getShortValues(value, DEFAULT_SHORT_VALUES);
	}

	/**
	 * Returns the Object value as a short array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a short is replaced by <code>{@link #DEFAULT_SHORT}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a short array
	 */
	public static short[] getShortValues(Object value, short[] defaultValue) {
		if (value instanceof String[]) {
			return getShortValues((String[])value, defaultValue);
		}

		if (value instanceof short[]) {
			return (short[])value;
		}

		return defaultValue;
	}

	/**
	 * Returns the String array values as a short array. If the value is
	 * <code>null</code> the <code>{@link #DEFAULT_SHORT_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a short is
	 * replaced by <code>{@link #DEFAULT_NUMBER}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as a short array
	 */
	public static short[] getShortValues(String[] values) {
		return getShortValues(values, DEFAULT_SHORT_VALUES);
	}

	/**
	 * Returns the String array values as a short array. If the value is
	 * <code>null</code> the default value is returned. In the returned array,
	 * each value element not convertible to a short is replaced by
	 * <code>{@link #DEFAULT_NUMBER}</code>.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a short array
	 */
	public static short[] getShortValues(
		String[] values, short[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		short[] shortValues = new short[values.length];

		for (int i = 0; i < values.length; i++) {
			shortValues[i] = getShort(values[i]);
		}

		return shortValues;
	}

	/**
	 * Returns the Object value as a String. If the value is <code>null</code>,
	 * <code>{@link #DEFAULT_STRING}</code> String is returned.
	 *
	 * @param  value the value to convert
	 * @return the value as a String
	 */
	public static String getString(Object value) {
		return getString(value, DEFAULT_STRING);
	}

	/**
	 * Returns the Object value as a String. If the value is <code>null</code>,
	 * the default value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a String
	 */
	public static String getString(Object value, String defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the String value. If the value is <code>null</code>,
	 * <code>{@link #DEFAULT_STRING}</code> is returned.
	 *
	 * @param  value the value
	 * @return the value
	 */
	public static String getString(String value) {
		return getString(value, DEFAULT_STRING);
	}

	/**
	 * Returns the String value. If the value is <code>null</code>, the default
	 * value is returned.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value
	 */
	public static String getString(String value, String defaultValue) {
		return get(value, defaultValue);
	}

	/**
	 * Returns the Object value as a String array. If the value is <code>null</code>
	 * the <code>{@link #DEFAULT_STRING_VALUES}</code> is returned.
	 * In the returned array, each value element not convertible to a String is
	 * replaced by <code>{@link #DEFAULT_STRING}</code>.
	 *
	 * @param  value the value to convert
	 * @return the value as a String array
	 */
	public static String[] getStringValues(Object value) {
		return getStringValues(value, DEFAULT_STRING_VALUES);
	}

	/**
	 * Returns the Object value as a String array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a String is replaced by <code>{@link #DEFAULT_STRING}</code>.
	 *
	 * @param  value the value to convert
	 * @param  defaultValue a default value
	 * @return the value as a String array
	 */
	public static String[] getStringValues(
		Object value, String[] defaultValue) {

		if (value instanceof String[]) {
			return getStringValues((String[])value, defaultValue);
		}

		return defaultValue;
	}

	/**
	 * Returns the String array values as a String array. If the value is <code>null</code>
	 * the default vauie is returned. In the returned array, each value element
	 * not convertible to a String is replaced by <code>{@link #DEFAULT_STRING}</code>.
	 *
	 * @param  values the values to convert
	 * @param  defaultValue a default value
	 * @return the value as a String array
	 */
	public static String[] getStringValues(
		Object[] values, String[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		String[] stringValues = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			stringValues[i] = String.valueOf(values[i]);
		}

		return stringValues;
	}

	/**
	 * Returns the String array values as a String array. If the value is <code>null</code>
	 * the default value is returned. In the returned array, each value element
	 * not convertible to a String is replaced by <code>{@link #DEFAULT_STRING}</code>.
	 *
	 * @param  values the values to convert
	 * @return the value as a String array
	 */
	public static String[] getStringValues(String[] values) {
		return getStringValues(values, DEFAULT_STRING_VALUES);
	}

	private static int _parseInt(String value, int defaultValue) {
		int length = value.length();

		if (length <= 0) {
			return defaultValue;
		}

		int pos = 0;
		int limit = -Integer.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Integer.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				return defaultValue;
			}

			if (length == 1) {
				return defaultValue;
			}

			pos++;
		}

		int smallLimit = limit / 10;

		int result = 0;

		while (pos < length) {
			if (result < smallLimit) {
				return defaultValue;
			}

			c = value.charAt(pos++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				return defaultValue;
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				return defaultValue;
			}

			result -= number;
		}

		if (negative) {
			return result;
		}
		else {
			return -result;
		}
	}

	private static long _parseLong(String value, long defaultValue) {
		int length = value.length();

		if (length <= 0) {
			return defaultValue;
		}

		int pos = 0;
		long limit = -Long.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Long.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				return defaultValue;
			}

			if (length == 1) {
				return defaultValue;
			}

			pos++;
		}

		long smallLimit = limit / 10;

		long result = 0;

		while (pos < length) {
			if (result < smallLimit) {
				return defaultValue;
			}

			c = value.charAt(pos++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				return defaultValue;
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				return defaultValue;
			}

			result -= number;
		}

		if (negative) {
			return result;
		}
		else {
			return -result;
		}
	}

	private static short _parseShort(String value, short defaultValue) {
		int i = _parseInt(value, defaultValue);

		if ((i < Short.MIN_VALUE) || (i > Short.MAX_VALUE)) {
			return defaultValue;
		}

		return (short)i;
	}

	/**
	 * @see StringUtil#toLowerCase
	 */
	private static String _toLowerCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = null;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c > 127) {

				// Found non-ascii char, fallback to the slow unicode detection

				return s.toLowerCase(LocaleUtil.getDefault());
			}

			if ((c >= 'A') && (c <= 'Z')) {
				if (sb == null) {
					sb = new StringBuilder(s);
				}

				sb.setCharAt(i, (char)(c + 32));
			}
		}

		if (sb == null) {
			return s;
		}

		return sb.toString();
	}

}