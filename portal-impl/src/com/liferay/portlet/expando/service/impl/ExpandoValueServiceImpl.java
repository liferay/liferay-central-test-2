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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.base.ExpandoValueServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="ExpandoValueServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueServiceImpl extends ExpandoValueServiceBaseImpl {

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			boolean data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			boolean[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Date data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Date[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			double data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			double[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			float data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			float[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			int data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			int[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			long data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			long[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			short data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			short[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			String data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			String[] data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			className, tableName, columnName, classPK, data);
	}

	public ExpandoValue addValue(
			long classNameId, long tableId, long columnId, long classPK,
			String data)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(
			classNameId, tableId, columnId, classPK, data);
	}

	public void deleteColumnValues(long columnId) throws SystemException {
		expandoValuePersistence.removeByColumnId(columnId);

		expandoValueLocalService.deleteColumnValues(columnId);
	}

	public void deleteRowValues(long rowId) throws SystemException {
		expandoValueLocalService.deleteRowValues(rowId);
	}

	public void deleteTableValues(long tableId) throws SystemException {
		expandoValueLocalService.deleteTableValues(tableId);
	}

	public void deleteValue(long valueId)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteValue(valueId);
	}

	public void deleteValues(String className, long classPK)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteValues(className, classPK);
	}

	public void deleteValues(long classNameId, long classPK)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteValues(classNameId, classPK);
	}

	public List<ExpandoValue> getColumnValues(long columnId, int begin, int end)
		throws SystemException {

		return expandoValueLocalService.getColumnValues(columnId, begin, end);
	}

	public List<ExpandoValue> getColumnValues(
			String className, String tableName, String columnName, int begin,
			int end)
		throws SystemException {

		return expandoValueLocalService.getColumnValues(
			className, tableName, columnName, begin, end);
	}

	public List<ExpandoValue> getColumnValues(
			long classNameId, String tableName, String columnName, int begin,
			int end)
		throws SystemException {

		return expandoValueLocalService.getColumnValues(
			classNameId, tableName, columnName, begin, end);
	}

	public int getColumnValuesCount(long columnId) throws SystemException {
		return expandoValueLocalService.getColumnValuesCount(columnId);
	}

	public int getColumnValuesCount(
			String className, String tableName, String columnName)
		throws SystemException {

		return expandoValueLocalService.getColumnValuesCount(
			className, tableName, columnName);
	}

	public int getColumnValuesCount(
			long classNameId, String tableName, String columnName)
		throws SystemException {

		return expandoValueLocalService.getColumnValuesCount(
			classNameId, tableName, columnName);
	}

	public List<ExpandoValue> getDefaultTableColumnValues(
			String className, String columnName, int begin, int end)
		throws SystemException {

		return expandoValueLocalService.getDefaultTableColumnValues(
			className, columnName, begin, end);
	}

	public List<ExpandoValue> getDefaultTableColumnValues(
			long classNameId, String columnName, int begin, int end)
		throws SystemException {

		return expandoValueLocalService.getDefaultTableColumnValues(
			classNameId, columnName, begin, end);
	}

	public int getDefaultTableColumnValuesCount(
			String className, String columnName)
		throws SystemException {

		return expandoValueLocalService.getDefaultTableColumnValuesCount(
			className, columnName);
	}

	public int getDefaultTableColumnValuesCount(
			long classNameId, String columnName)
		throws SystemException {

		return expandoValueLocalService.getDefaultTableColumnValuesCount(
			classNameId, columnName);
	}

	public boolean getData(
			String className, String tableName, String columnName, long classPK,
			boolean defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public boolean[] getData(
			String className, String tableName, String columnName, long classPK,
			boolean[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public Date getData(
			String className, String tableName, String columnName, long classPK,
			Date defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public Date[] getData(
			String className, String tableName, String columnName, long classPK,
			Date[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public double getData(
			String className, String tableName, String columnName, long classPK,
			double defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public double[] getData(
			String className, String tableName, String columnName, long classPK,
			double[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public float getData(
			String className, String tableName, String columnName, long classPK,
			float defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public float[] getData(
			String className, String tableName, String columnName, long classPK,
			float[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public int getData(
			String className, String tableName, String columnName, long classPK,
			int defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public int[] getData(
			String className, String tableName, String columnName, long classPK,
			int[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public long getData(
			String className, String tableName, String columnName, long classPK,
			long defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public long[] getData(
			String className, String tableName, String columnName, long classPK,
			long[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public short getData(
			String className, String tableName, String columnName, long classPK,
			short defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public short[] getData(
			String className, String tableName, String columnName, long classPK,
			short[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public String getData(
			String className, String tableName, String columnName, long classPK,
			String defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public String[] getData(
			String className, String tableName, String columnName, long classPK,
			String[] defaultData)
		throws PortalException, SystemException {

		return expandoValueLocalService.getData(
			className, tableName, columnName, classPK, defaultData);
	}

	public List<ExpandoValue> getRowValues(long rowId) throws SystemException {
		return expandoValueLocalService.getRowValues(rowId);
	}

	public List<ExpandoValue> getRowValues(long rowId, int begin, int end)
		throws SystemException {

		return expandoValueLocalService.getRowValues(rowId, begin, end);
	}

	public List<ExpandoValue> getRowValues(
			String className, String tableName, long classPK, int begin,
			int end)
		throws SystemException {

		return expandoValueLocalService.getRowValues(
			className, tableName, classPK, begin, end);
	}

	public List<ExpandoValue> getRowValues(
			long classNameId, String tableName, long classPK, int begin,
			int end)
		throws SystemException {

		return expandoValueLocalService.getRowValues(
			classNameId, tableName, classPK, begin, end);
	}

	public int getRowValuesCount(long rowId) throws SystemException {
		return expandoValueLocalService.getRowValuesCount(rowId);
	}

	public int getRowValuesCount(
			String className, String tableName, long classPK)
		throws SystemException {

		return expandoValueLocalService.getRowValuesCount(
			className, tableName, classPK);
	}

	public int getRowValuesCount(
			long classNameId, String tableName, long classPK)
		throws SystemException {

		return expandoValueLocalService.getRowValuesCount(
			classNameId, tableName, classPK);
	}

	public ExpandoValue getValue(long valueId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValue(valueId);
	}

	public ExpandoValue getValue(long columnId, long rowId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValue(columnId, rowId);
	}

	public ExpandoValue getValue(
			String className, String tableName, String columnName, long rowId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValue(
			className, tableName, columnName, rowId);
	}

	public ExpandoValue getValue(
			long classNameId, String tableName, String columnName, long rowId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValue(
			classNameId, tableName, columnName, rowId);
	}

}