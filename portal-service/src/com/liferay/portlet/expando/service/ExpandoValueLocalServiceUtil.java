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

package com.liferay.portlet.expando.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ExpandoValueLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ExpandoValueLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoValueLocalService
 * @generated
 */
public class ExpandoValueLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoValue addExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addExpandoValue(expandoValue);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue createExpandoValue(
		long valueId) {
		return getService().createExpandoValue(valueId);
	}

	public static void deleteExpandoValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoValue(valueId);
	}

	public static void deleteExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoValue(expandoValue);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getExpandoValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoValue(valueId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getExpandoValues(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoValues(start, end);
	}

	public static int getExpandoValuesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoValuesCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoValue(expandoValue);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoValue(expandoValue, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long classNameId, long tableId, long columnId, long classPK,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(classNameId, tableId, columnId, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static void addValues(long classNameId, long tableId,
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> columns,
		long classPK, java.util.Map<String, String> data)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addValues(classNameId, tableId, columns, classPK, data);
	}

	public static void deleteColumnValues(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumnValues(columnId);
	}

	public static void deleteRowValues(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRowValues(rowId);
	}

	public static void deleteTableValues(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTableValues(tableId);
	}

	public static void deleteValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteValue(valueId);
	}

	public static void deleteValue(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteValue(columnId, rowId);
	}

	public static void deleteValue(long classNameId,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteValue(classNameId, tableName, columnName, classPK);
	}

	public static void deleteValue(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteValue(className, tableName, columnName, classPK);
	}

	public static void deleteValues(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteValues(classNameId, classPK);
	}

	public static void deleteValues(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteValues(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumnValues(columnId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValues(classNameId, tableName, columnName, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValues(classNameId, tableName, columnName, data,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValues(className, tableName, columnName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValues(className, tableName, columnName, data,
			start, end);
	}

	public static int getColumnValuesCount(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumnValuesCount(columnId);
	}

	public static int getColumnValuesCount(long classNameId,
		java.lang.String tableName, java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValuesCount(classNameId, tableName, columnName);
	}

	public static int getColumnValuesCount(long classNameId,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValuesCount(classNameId, tableName, columnName,
			data);
	}

	public static int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValuesCount(className, tableName, columnName);
	}

	public static int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColumnValuesCount(className, tableName, columnName, data);
	}

	public static java.io.Serializable getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getData(className, tableName, columnName, classPK);
	}

	public static boolean getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static boolean[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static java.util.Date getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static java.util.Date[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static double getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static double[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static float getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static float[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static int getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static int[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static long getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static long[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static short getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static short[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static java.lang.String getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static java.lang.String[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		long classNameId, java.lang.String columnName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDefaultTableColumnValues(classNameId, columnName, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		java.lang.String className, java.lang.String columnName, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDefaultTableColumnValues(className, columnName, start,
			end);
	}

	public static int getDefaultTableColumnValuesCount(long classNameId,
		java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDefaultTableColumnValuesCount(classNameId, columnName);
	}

	public static int getDefaultTableColumnValuesCount(
		java.lang.String className, java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDefaultTableColumnValuesCount(className, columnName);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowValues(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowValues(rowId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long classNameId, java.lang.String tableName, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRowValues(classNameId, tableName, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		java.lang.String className, java.lang.String tableName, long classPK,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRowValues(className, tableName, classPK, start, end);
	}

	public static int getRowValuesCount(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowValuesCount(rowId);
	}

	public static int getRowValuesCount(long classNameId,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowValuesCount(classNameId, tableName, classPK);
	}

	public static int getRowValuesCount(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowValuesCount(className, tableName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getValue(valueId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getValue(columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getValue(tableId, columnId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getValue(classNameId, tableName, columnName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getValue(className, tableName, columnName, classPK);
	}

	public static ExpandoValueLocalService getService() {
		if (_service == null) {
			_service = (ExpandoValueLocalService)PortalBeanLocatorUtil.locate(ExpandoValueLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ExpandoValueLocalService service) {
		_service = service;
	}

	private static ExpandoValueLocalService _service;
}