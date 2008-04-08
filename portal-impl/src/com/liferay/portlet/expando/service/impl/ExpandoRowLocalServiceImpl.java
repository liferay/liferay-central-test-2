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
import com.liferay.portlet.expando.model.impl.ExpandoTableImpl;
import com.liferay.portlet.expando.service.base.ExpandoRowLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoRowLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowLocalServiceImpl extends ExpandoRowLocalServiceBaseImpl {

	public ExpandoRow addRow(long tableId)
		throws PortalException, SystemException {

		long rowId = counterLocalService.increment();

		ExpandoRow row = expandoRowPersistence.create(rowId);

		row.setTableId(tableId);

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

	public List<ExpandoRow> getDefaultTableRows(
			String className, int begin, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDefaultTableRows(classNameId, begin, end);
	}

	public List<ExpandoRow> getDefaultTableRows(
			long classNameId, int begin, int end)
		throws SystemException {

		return expandoRowFinder.findByTC_TN(
			classNameId, ExpandoTableImpl.DEFAULT_TABLE_NAME, begin, end);
	}

	public int getDefaultTableRowsCount(String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDefaultTableRowsCount(classNameId);
	}

	public int getDefaultTableRowsCount(long classNameId)
		throws SystemException {

		return expandoRowFinder.countByTC_TN(
			classNameId, ExpandoTableImpl.DEFAULT_TABLE_NAME);
	}

	public List<ExpandoRow> getRows(long tableId, int begin, int end)
		throws SystemException {

		return expandoRowPersistence.findByTableId(tableId, begin, end);
	}

	public List<ExpandoRow> getRows(
			String className, String tableName, int begin, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getRows(classNameId, tableName, begin, end);
	}

	public List<ExpandoRow> getRows(
			long classNameId, String tableName, int begin, int end)
		throws SystemException {

		return expandoRowFinder.findByTC_TN(
			classNameId, tableName, begin, end);
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