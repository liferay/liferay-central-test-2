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

package com.liferay.portal.model;


/**
 * <a href="ExpandoValue.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>ExpandoValue</code> table
 * in the database.
 * </p>
 *
 * <p>
 * Customize <code>com.liferay.portal.service.model.impl.ExpandoValueImpl</code>
 * and rerun the ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.ExpandoValueModel
 * @see com.liferay.portal.service.model.impl.ExpandoValueImpl
 * @see com.liferay.portal.service.model.impl.ExpandoValueModelImpl
 *
 */
public interface ExpandoValue extends ExpandoValueModel {
	public boolean getBoolean()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setBoolean(boolean value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public boolean[] getBooleanValues()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setBooleanValues(boolean[] values)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.Date getDate()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDate(java.util.Date value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public double getDouble()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDouble(double value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public double[] getDoubleValues()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDoubleValues(double[] values)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public float getFloat()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setFloat(float value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public float[] getFloatValues()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setFloatValues(float[] values)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getInteger()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setInteger(int value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int[] getIntegerValues()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setIntegerValues(int[] values)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public long getLong()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setLong(long value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public long[] getLongValues()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setLongValues(long[] values)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public short getShort()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setShort(short value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public short[] getShortValues()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setShortValues(short[] values)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.lang.String getString()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setString(java.lang.String value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}