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


/**
 * <a href="ExpandoValueLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoValueLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoValueLocalService
 * @generated
 */
public class ExpandoValueLocalServiceWrapper implements ExpandoValueLocalService {
	public ExpandoValueLocalServiceWrapper(
		ExpandoValueLocalService expandoValueLocalService) {
		_expandoValueLocalService = expandoValueLocalService;
	}

	public com.liferay.portlet.expando.model.ExpandoValue addExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addExpandoValue(expandoValue);
	}

	public com.liferay.portlet.expando.model.ExpandoValue createExpandoValue(
		long valueId) {
		return _expandoValueLocalService.createExpandoValue(valueId);
	}

	public void deleteExpandoValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteExpandoValue(valueId);
	}

	public void deleteExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteExpandoValue(expandoValue);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.expando.model.ExpandoValue getExpandoValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getExpandoValue(valueId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getExpandoValues(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getExpandoValues(start, end);
	}

	public int getExpandoValuesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getExpandoValuesCount();
	}

	public com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.updateExpandoValue(expandoValue);
	}

	public com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.updateExpandoValue(expandoValue, merge);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long classNameId, long tableId, long columnId, long classPK,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(classNameId, tableId,
			columnId, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	public void addValues(long classNameId, long tableId,
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> columns,
		long classPK, java.util.Map<String, String> data)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.addValues(classNameId, tableId, columns,
			classPK, data);
	}

	public void deleteColumnValues(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteColumnValues(columnId);
	}

	public void deleteRowValues(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteRowValues(rowId);
	}

	public void deleteTableValues(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteTableValues(tableId);
	}

	public void deleteValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteValue(valueId);
	}

	public void deleteValue(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteValue(columnId, rowId);
	}

	public void deleteValue(long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteValue(classNameId, tableName,
			columnName, classPK);
	}

	public void deleteValue(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteValue(className, tableName, columnName,
			classPK);
	}

	public void deleteValues(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteValues(classNameId, classPK);
	}

	public void deleteValues(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValueLocalService.deleteValues(className, classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValues(columnId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValues(classNameId,
			tableName, columnName, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValues(classNameId,
			tableName, columnName, data, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValues(className, tableName,
			columnName, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValues(className, tableName,
			columnName, data, start, end);
	}

	public int getColumnValuesCount(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValuesCount(columnId);
	}

	public int getColumnValuesCount(long classNameId,
		java.lang.String tableName, java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValuesCount(classNameId,
			tableName, columnName);
	}

	public int getColumnValuesCount(long classNameId,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValuesCount(classNameId,
			tableName, columnName, data);
	}

	public int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValuesCount(className,
			tableName, columnName);
	}

	public int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getColumnValuesCount(className,
			tableName, columnName, data);
	}

	public java.io.Serializable getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK);
	}

	public boolean getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public boolean[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public java.util.Date getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public java.util.Date[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public double getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public double[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public float getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public float[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public int getData(java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public int[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public long getData(java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public long[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public short getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public short[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public java.lang.String getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public java.lang.String[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		long classNameId, java.lang.String columnName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getDefaultTableColumnValues(classNameId,
			columnName, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		java.lang.String className, java.lang.String columnName, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getDefaultTableColumnValues(className,
			columnName, start, end);
	}

	public int getDefaultTableColumnValuesCount(long classNameId,
		java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getDefaultTableColumnValuesCount(classNameId,
			columnName);
	}

	public int getDefaultTableColumnValuesCount(java.lang.String className,
		java.lang.String columnName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getDefaultTableColumnValuesCount(className,
			columnName);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValues(rowId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValues(rowId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long classNameId, java.lang.String tableName, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValues(classNameId, tableName,
			classPK, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		java.lang.String className, java.lang.String tableName, long classPK,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValues(className, tableName,
			classPK, start, end);
	}

	public int getRowValuesCount(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValuesCount(rowId);
	}

	public int getRowValuesCount(long classNameId, java.lang.String tableName,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValuesCount(classNameId,
			tableName, classPK);
	}

	public int getRowValuesCount(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getRowValuesCount(className,
			tableName, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoValue getValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getValue(valueId);
	}

	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getValue(columnId, rowId);
	}

	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getValue(tableId, columnId, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getValue(classNameId, tableName,
			columnName, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueLocalService.getValue(className, tableName,
			columnName, classPK);
	}

	public ExpandoValueLocalService getWrappedExpandoValueLocalService() {
		return _expandoValueLocalService;
	}

	private ExpandoValueLocalService _expandoValueLocalService;
}