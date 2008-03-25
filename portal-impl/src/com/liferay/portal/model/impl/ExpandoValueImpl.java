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

import com.liferay.portal.ExpandoValueValueException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.model.ExpandoValue;
import com.liferay.portal.service.ExpandoColumnLocalServiceUtil;

import java.util.Date;

/**
 * <a href="ExpandoValueImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueImpl extends ExpandoValueModelImpl
	implements ExpandoValue {
	public ExpandoValueImpl() {
	}

	public boolean getBoolean() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.BOOLEAN) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not boolean");
		}

		return GetterUtil.getBoolean(getValue());
	}

	public void setBoolean(boolean value)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.BOOLEAN) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is boolean");
		}

		setValue(String.valueOf(value));
	}

	public boolean[] getBooleanValues()
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.BOOLEAN_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not boolean[]");
		}

		return GetterUtil.getBooleanValues(StringUtil.split(getValue()));
	}

	public void setBooleanValues(boolean[] values)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.BOOLEAN_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is boolean[]");
		}

		setValue(StringUtil.merge(values));
	}

	public Date getDate() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.DATE) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not Date");
		}

		return new Date(GetterUtil.getLong(getValue()));
	}

	public void setDate(Date value) throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.DATE) {
			throw new ExpandoValueValueException("ExpandoColumn type is Date");
		}

		setValue(
			String.valueOf(value.getTime()));
	}

	public double getDouble() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.DOUBLE) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not double");
		}

		return GetterUtil.getDouble(getValue());
	}

	public void setDouble(double value)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.DOUBLE) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is double");
		}

		setValue(String.valueOf(value));
	}

	public double[] getDoubleValues() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.DOUBLE_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not double[]");
		}

		return GetterUtil.getDoubleValues(StringUtil.split(getValue()));
	}

	public void setDoubleValues(double[] values)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.DOUBLE_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is double[]");
		}

		setValue(StringUtil.merge(values));
	}

	public float getFloat() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.FLOAT) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not float");
		}

		return GetterUtil.getFloat(getValue());
	}

	public void setFloat(float value) throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.FLOAT) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is float");
		}

		setValue(String.valueOf(value));
	}

	public float[] getFloatValues() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.FLOAT_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not float[]");
		}

		return GetterUtil.getFloatValues(StringUtil.split(getValue()));
	}

	public void setFloatValues(float[] values)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.FLOAT_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is float[]");
		}

		setValue(StringUtil.merge(values));
	}

	public int getInteger() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.INTEGER) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not int");
		}

		return GetterUtil.getInteger(getValue());
	}

	public void setInteger(int value) throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.INTEGER) {
			throw new ExpandoValueValueException("ExpandoColumn type is int");
		}

		setValue(String.valueOf(value));
	}

	public int[] getIntegerValues() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.INTEGER_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not int[]");
		}

		return GetterUtil.getIntegerValues(StringUtil.split(getValue()));
	}

	public void setIntegerValues(int[] values)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.INTEGER_ARRAY) {
			throw new ExpandoValueValueException("ExpandoColumn type is int[]");
		}

		setValue(StringUtil.merge(values));
	}

	public long getLong() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.LONG) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not long");
		}

		return GetterUtil.getLong(getValue());
	}

	public void setLong(long value) throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.LONG) {
			throw new ExpandoValueValueException("ExpandoColumn type is long");
		}

		setValue(String.valueOf(value));
	}

	public long[] getLongValues() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.LONG_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not long[]");
		}

		return GetterUtil.getLongValues(StringUtil.split(getValue()));
	}

	public void setLongValues(long[] values)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.LONG_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is long[]");
		}

		setValue(StringUtil.merge(values));
	}

	public short getShort() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.SHORT) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not short");
		}

		return GetterUtil.getShort(getValue());
	}

	public void setShort(short value) throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.SHORT) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is short");
		}

		setValue(String.valueOf(value));
	}

	public short[] getShortValues() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.SHORT_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not short[]");
		}

		return GetterUtil.getShortValues(StringUtil.split(getValue()));
	}

	public void setShortValues(short[] values)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.SHORT_ARRAY) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is short[]");
		}

		setValue(StringUtil.merge(values));
	}

	public String getString() throws PortalException, SystemException {
		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.STRING) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is not String");
		}

		return getValue();
	}

	public void setString(String value)
		throws PortalException, SystemException {

		ExpandoColumn column =
			ExpandoColumnLocalServiceUtil.getColumn(getColumnId());

		if (column.getType() != ExpandoColumnImpl.STRING) {
			throw new ExpandoValueValueException(
				"ExpandoColumn type is String");
		}

		setValue(value);
	}

}