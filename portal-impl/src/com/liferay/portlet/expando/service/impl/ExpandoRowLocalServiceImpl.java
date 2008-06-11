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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.base.ExpandoRowLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoRowLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowLocalServiceImpl extends ExpandoRowLocalServiceBaseImpl {

	public ExpandoRow addRow(long tableId, long classPK)
		throws SystemException {

		long rowId = counterLocalService.increment();

		ExpandoRow row = expandoRowPersistence.create(rowId);

		row.setTableId(tableId);
		row.setClassPK(classPK);

		expandoRowPersistence.update(row, false);

		return row;
	}

	public void deleteRow(long rowId)
		throws PortalException, SystemException {

		// Values

		expandoValueLocalService.deleteRowValues(rowId);

		// Row

		expandoRowPersistence.remove(rowId);
	}

	public void deleteRow(long tableId, long classPK)
		throws PortalException, SystemException {

		ExpandoRow row = expandoRowPersistence.findByT_C(tableId, classPK);

		deleteRow(row.getRowId());
	}

	public void deleteRow(String className, String tableName, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteRow(classNameId, tableName, classPK);
	}

	public void deleteRow(long classNameId, String tableName, long classPK)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			classNameId, tableName);

		deleteRow(table.getTableId(), classPK);
	}

	public List<ExpandoRow> getDefaultTableRows(
			String className, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDefaultTableRows(classNameId, start, end);
	}

	public List<ExpandoRow> getDefaultTableRows(
			long classNameId, int start, int end)
		throws SystemException {

		return expandoRowFinder.findByTC_TN(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME, start, end);
	}

	public int getDefaultTableRowsCount(String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDefaultTableRowsCount(classNameId);
	}

	public int getDefaultTableRowsCount(long classNameId)
		throws SystemException {

		return expandoRowFinder.countByTC_TN(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoRow getRow(long rowId)
		throws PortalException, SystemException {

		return expandoRowPersistence.findByPrimaryKey(rowId);
	}

	public ExpandoRow getRow(long tableId, long classPK)
		throws PortalException, SystemException {

		return expandoRowPersistence.findByT_C(tableId, classPK);
	}

	public ExpandoRow getRow(String className, String tableName, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getRow(classNameId, tableName, classPK);
	}

	public ExpandoRow getRow(long classNameId, String tableName, long classPK)
		throws SystemException {

		return expandoRowFinder.fetchByTC_TN_C(classNameId, tableName, classPK);
	}

	public List<ExpandoRow> getRows(long tableId, int start, int end)
		throws SystemException {

		return expandoRowPersistence.findByTableId(tableId, start, end);
	}

	public List<ExpandoRow> getRows(
			String className, String tableName, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getRows(classNameId, tableName, start, end);
	}

	public List<ExpandoRow> getRows(
			long classNameId, String tableName, int start, int end)
		throws SystemException {

		return expandoRowFinder.findByTC_TN(
			classNameId, tableName, start, end);
	}

	public int getRowsCount(long tableId) throws SystemException {
		return expandoRowPersistence.countByTableId(tableId);
	}

	public int getRowsCount(String className, String tableName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getRowsCount(classNameId, tableName);
	}

	public int getRowsCount(long classNameId, String tableName)
		throws SystemException {

		return expandoRowFinder.countByTC_TN(classNameId, tableName);
	}

}