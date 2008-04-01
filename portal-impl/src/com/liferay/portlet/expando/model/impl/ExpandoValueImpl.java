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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;

import java.util.Date;

/**
 * <a href="ExpandoValueImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueImpl
	extends ExpandoValueModelImpl implements ExpandoValue {

	public ExpandoValueImpl() {
	}

	public boolean getBoolean() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.BOOLEAN);

		return GetterUtil.getBoolean(getData());
	}

	public void setBoolean(boolean data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.BOOLEAN);

		setData(String.valueOf(data));
	}

	public boolean[] getBooleanArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.BOOLEAN_ARRAY);

		return GetterUtil.getBooleanValues(StringUtil.split(getData()));
	}

	public void setBooleanArray(boolean[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.BOOLEAN_ARRAY);

		setData(StringUtil.merge(data));
	}

	public Date getDate() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.DATE);

		return new Date(GetterUtil.getLong(getData()));
	}

	public void setDate(Date data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.DATE);

		setData(String.valueOf(data.getTime()));
	}

	public Date[] getDateArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.DATE_ARRAY);

		String[] data = StringUtil.split(getData());

		Date[] dateArray = new Date[data.length];

		for (int i = 0; i < data.length; i++) {
			dateArray[i] = new Date(GetterUtil.getLong(data[i]));
		}

		return dateArray;
	}

	public void setDateArray(Date[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.DATE_ARRAY);

		setData(StringUtil.merge(data));
	}

	public double getDouble() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.DOUBLE);

		return GetterUtil.getDouble(getData());
	}

	public void setDouble(double data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.DOUBLE);

		setData(String.valueOf(data));
	}

	public double[] getDoubleArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.DOUBLE_ARRAY);

		return GetterUtil.getDoubleValues(StringUtil.split(getData()));
	}

	public void setDoubleArray(double[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.DOUBLE_ARRAY);

		setData(StringUtil.merge(data));
	}

	public float getFloat() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.FLOAT);

		return GetterUtil.getFloat(getData());
	}

	public void setFloat(float data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.FLOAT);

		setData(String.valueOf(data));
	}

	public float[] getFloatArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.FLOAT_ARRAY);

		return GetterUtil.getFloatValues(StringUtil.split(getData()));
	}

	public void setFloatArray(float[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.FLOAT_ARRAY);

		setData(StringUtil.merge(data));
	}

	public int getInteger() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.INTEGER);

		return GetterUtil.getInteger(getData());
	}

	public void setInteger(int data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.INTEGER);

		setData(String.valueOf(data));
	}

	public int[] getIntegerArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.INTEGER_ARRAY);

		return GetterUtil.getIntegerValues(StringUtil.split(getData()));
	}

	public void setIntegerArray(int[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.INTEGER_ARRAY);

		setData(StringUtil.merge(data));
	}

	public long getLong() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.LONG);

		return GetterUtil.getLong(getData());
	}

	public void setLong(long data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.LONG);

		setData(String.valueOf(data));
	}

	public long[] getLongArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.LONG_ARRAY);

		return GetterUtil.getLongValues(StringUtil.split(getData()));
	}

	public void setLongArray(long[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.LONG_ARRAY);

		setData(StringUtil.merge(data));
	}

	public short getShort() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.SHORT);

		return GetterUtil.getShort(getData());
	}

	public void setShort(short data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.SHORT);

		setData(String.valueOf(data));
	}

	public short[] getShortArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.SHORT_ARRAY);

		return GetterUtil.getShortValues(StringUtil.split(getData()));
	}

	public void setShortArray(short[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.SHORT_ARRAY);

		setData(StringUtil.merge(data));
	}

	public String getString() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.STRING);

		return getData();
	}

	public void setString(String data) throws PortalException, SystemException {
		validate(ExpandoColumnImpl.STRING);

		setData(data);
	}

	public String[] getStringArray() throws PortalException, SystemException {
		validate(ExpandoColumnImpl.STRING_ARRAY);

		return StringUtil.split(getData());
	}

	public void setString(String[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnImpl.STRING_ARRAY);

		setData(StringUtil.merge(data));
	}

	protected void validate(int type) throws PortalException, SystemException {
		ExpandoColumn column = ExpandoColumnLocalServiceUtil.getColumn(
			getColumnId());

		if (column.getType() != type) {
			throw new ValueDataException(
				"Column " + getColumnId() + " has type " +
					ExpandoColumnImpl.getTypeLabel(column.getType()) +
						" and is not compatbile with type " +
							ExpandoColumnImpl.getTypeLabel(type));
		}
	}

}