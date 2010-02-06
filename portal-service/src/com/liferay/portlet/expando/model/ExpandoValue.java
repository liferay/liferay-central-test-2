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

package com.liferay.portlet.expando.model;


/**
 * <a href="ExpandoValue.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ExpandoValue table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.expando.model.impl.ExpandoValueImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoValueModel
 * @see       com.liferay.portlet.expando.model.impl.ExpandoValueImpl
 * @see       com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl
 * @generated
 */
public interface ExpandoValue extends ExpandoValueModel {
	public boolean getBoolean()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public boolean[] getBooleanArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.Date getDate()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.Date[] getDateArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public double getDouble()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public double[] getDoubleArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public float getFloat()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public float[] getFloatArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int getInteger()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int[] getIntegerArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long getLong()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long[] getLongArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public short getShort()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public short[] getShortArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.lang.String getString()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.lang.String[] getStringArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setBoolean(boolean data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setDate(java.util.Date data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setDateArray(java.util.Date[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setDouble(double data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setDoubleArray(double[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setFloat(float data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setFloatArray(float[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setInteger(int data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setIntegerArray(int[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setLong(long data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setLongArray(long[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setShort(short data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setShortArray(short[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setString(java.lang.String data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setStringArray(java.lang.String[] data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}