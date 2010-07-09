/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

/**
 * @author Brian Wing Shun Chan
 */
public class GetterUtil_IW {
	public static GetterUtil_IW getInstance() {
		return _instance;
	}

	public boolean get(java.io.Serializable value, boolean defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public java.util.Date get(java.io.Serializable value,
		java.text.DateFormat dateFormat, java.util.Date defaultValue) {
		return GetterUtil.get(value, dateFormat, defaultValue);
	}

	public double get(java.io.Serializable value, double defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public float get(java.io.Serializable value, float defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public int get(java.io.Serializable value, int defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public long get(java.io.Serializable value, long defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public short get(java.io.Serializable value, short defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public java.lang.String get(java.io.Serializable value,
		java.lang.String defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public boolean get(java.lang.String value, boolean defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public java.util.Date get(java.lang.String value,
		java.text.DateFormat dateFormat, java.util.Date defaultValue) {
		return GetterUtil.get(value, dateFormat, defaultValue);
	}

	public double get(java.lang.String value, double defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public float get(java.lang.String value, float defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public int get(java.lang.String value, int defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public long get(java.lang.String value, long defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public short get(java.lang.String value, short defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public java.lang.String get(java.lang.String value,
		java.lang.String defaultValue) {
		return GetterUtil.get(value, defaultValue);
	}

	public boolean getBoolean(java.io.Serializable value) {
		return GetterUtil.getBoolean(value);
	}

	public boolean getBoolean(java.io.Serializable value, boolean defaultValue) {
		return GetterUtil.getBoolean(value, defaultValue);
	}

	public boolean getBoolean(java.lang.String value) {
		return GetterUtil.getBoolean(value);
	}

	public boolean getBoolean(java.lang.String value, boolean defaultValue) {
		return GetterUtil.getBoolean(value, defaultValue);
	}

	public boolean[] getBooleanValues(java.io.Serializable value) {
		return GetterUtil.getBooleanValues(value);
	}

	public boolean[] getBooleanValues(java.io.Serializable value,
		boolean[] defaultValue) {
		return GetterUtil.getBooleanValues(value, defaultValue);
	}

	public boolean[] getBooleanValues(java.lang.String[] values) {
		return GetterUtil.getBooleanValues(values);
	}

	public boolean[] getBooleanValues(java.lang.String[] values,
		boolean[] defaultValue) {
		return GetterUtil.getBooleanValues(values, defaultValue);
	}

	public java.util.Date getDate(java.io.Serializable value,
		java.text.DateFormat dateFormat) {
		return GetterUtil.getDate(value, dateFormat);
	}

	public java.util.Date getDate(java.io.Serializable value,
		java.text.DateFormat dateFormat, java.util.Date defaultValue) {
		return GetterUtil.getDate(value, dateFormat, defaultValue);
	}

	public java.util.Date getDate(java.lang.String value,
		java.text.DateFormat dateFormat) {
		return GetterUtil.getDate(value, dateFormat);
	}

	public java.util.Date getDate(java.lang.String value,
		java.text.DateFormat dateFormat, java.util.Date defaultValue) {
		return GetterUtil.getDate(value, dateFormat, defaultValue);
	}

	public java.util.Date[] getDateValues(java.io.Serializable value,
		java.text.DateFormat dateFormat) {
		return GetterUtil.getDateValues(value, dateFormat);
	}

	public java.util.Date[] getDateValues(java.io.Serializable value,
		java.text.DateFormat dateFormat, java.util.Date[] defaultValue) {
		return GetterUtil.getDateValues(value, dateFormat, defaultValue);
	}

	public java.util.Date[] getDateValues(java.lang.String[] values,
		java.text.DateFormat dateFormat) {
		return GetterUtil.getDateValues(values, dateFormat);
	}

	public java.util.Date[] getDateValues(java.lang.String[] values,
		java.text.DateFormat dateFormat, java.util.Date[] defaultValue) {
		return GetterUtil.getDateValues(values, dateFormat, defaultValue);
	}

	public double getDouble(java.io.Serializable value) {
		return GetterUtil.getDouble(value);
	}

	public double getDouble(java.io.Serializable value, double defaultValue) {
		return GetterUtil.getDouble(value, defaultValue);
	}

	public double getDouble(java.lang.String value) {
		return GetterUtil.getDouble(value);
	}

	public double getDouble(java.lang.String value, double defaultValue) {
		return GetterUtil.getDouble(value, defaultValue);
	}

	public double[] getDoubleValues(java.io.Serializable value) {
		return GetterUtil.getDoubleValues(value);
	}

	public double[] getDoubleValues(java.io.Serializable value,
		double[] defaultValue) {
		return GetterUtil.getDoubleValues(value, defaultValue);
	}

	public double[] getDoubleValues(java.lang.String[] values) {
		return GetterUtil.getDoubleValues(values);
	}

	public double[] getDoubleValues(java.lang.String[] values,
		double[] defaultValue) {
		return GetterUtil.getDoubleValues(values, defaultValue);
	}

	public float getFloat(java.io.Serializable value) {
		return GetterUtil.getFloat(value);
	}

	public float getFloat(java.io.Serializable value, float defaultValue) {
		return GetterUtil.getFloat(value, defaultValue);
	}

	public float getFloat(java.lang.String value) {
		return GetterUtil.getFloat(value);
	}

	public float getFloat(java.lang.String value, float defaultValue) {
		return GetterUtil.getFloat(value, defaultValue);
	}

	public float[] getFloatValues(java.io.Serializable value) {
		return GetterUtil.getFloatValues(value);
	}

	public float[] getFloatValues(java.io.Serializable value,
		float[] defaultValue) {
		return GetterUtil.getFloatValues(value, defaultValue);
	}

	public float[] getFloatValues(java.lang.String[] values) {
		return GetterUtil.getFloatValues(values);
	}

	public float[] getFloatValues(java.lang.String[] values,
		float[] defaultValue) {
		return GetterUtil.getFloatValues(values, defaultValue);
	}

	public int getInteger(java.io.Serializable value) {
		return GetterUtil.getInteger(value);
	}

	public int getInteger(java.io.Serializable value, int defaultValue) {
		return GetterUtil.getInteger(value, defaultValue);
	}

	public int getInteger(java.lang.String value) {
		return GetterUtil.getInteger(value);
	}

	public int getInteger(java.lang.String value, int defaultValue) {
		return GetterUtil.getInteger(value, defaultValue);
	}

	public int[] getIntegerValues(java.io.Serializable value) {
		return GetterUtil.getIntegerValues(value);
	}

	public int[] getIntegerValues(java.io.Serializable value, int[] defaultValue) {
		return GetterUtil.getIntegerValues(value, defaultValue);
	}

	public int[] getIntegerValues(java.lang.String[] values) {
		return GetterUtil.getIntegerValues(values);
	}

	public int[] getIntegerValues(java.lang.String[] values, int[] defaultValue) {
		return GetterUtil.getIntegerValues(values, defaultValue);
	}

	public long getLong(java.io.Serializable value) {
		return GetterUtil.getLong(value);
	}

	public long getLong(java.io.Serializable value, long defaultValue) {
		return GetterUtil.getLong(value, defaultValue);
	}

	public long getLong(java.lang.String value) {
		return GetterUtil.getLong(value);
	}

	public long getLong(java.lang.String value, long defaultValue) {
		return GetterUtil.getLong(value, defaultValue);
	}

	public long[] getLongValues(java.io.Serializable value) {
		return GetterUtil.getLongValues(value);
	}

	public long[] getLongValues(java.io.Serializable value, long[] defaultValue) {
		return GetterUtil.getLongValues(value, defaultValue);
	}

	public long[] getLongValues(java.lang.String[] values) {
		return GetterUtil.getLongValues(values);
	}

	public long[] getLongValues(java.lang.String[] values, long[] defaultValue) {
		return GetterUtil.getLongValues(values, defaultValue);
	}

	public short getShort(java.io.Serializable value) {
		return GetterUtil.getShort(value);
	}

	public short getShort(java.io.Serializable value, short defaultValue) {
		return GetterUtil.getShort(value, defaultValue);
	}

	public short getShort(java.lang.String value) {
		return GetterUtil.getShort(value);
	}

	public short getShort(java.lang.String value, short defaultValue) {
		return GetterUtil.getShort(value, defaultValue);
	}

	public short[] getShortValues(java.io.Serializable value) {
		return GetterUtil.getShortValues(value);
	}

	public short[] getShortValues(java.io.Serializable value,
		short[] defaultValue) {
		return GetterUtil.getShortValues(value, defaultValue);
	}

	public short[] getShortValues(java.lang.String[] values) {
		return GetterUtil.getShortValues(values);
	}

	public short[] getShortValues(java.lang.String[] values,
		short[] defaultValue) {
		return GetterUtil.getShortValues(values, defaultValue);
	}

	public java.lang.String getString(java.io.Serializable value) {
		return GetterUtil.getString(value);
	}

	public java.lang.String getString(java.io.Serializable value,
		java.lang.String defaultValue) {
		return GetterUtil.getString(value, defaultValue);
	}

	public java.lang.String getString(java.lang.String value) {
		return GetterUtil.getString(value);
	}

	public java.lang.String getString(java.lang.String value,
		java.lang.String defaultValue) {
		return GetterUtil.getString(value, defaultValue);
	}

	private GetterUtil_IW() {
	}

	private static GetterUtil_IW _instance = new GetterUtil_IW();
}