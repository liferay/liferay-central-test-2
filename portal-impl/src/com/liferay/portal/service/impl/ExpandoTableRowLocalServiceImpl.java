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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ExpandoTable;
import com.liferay.portal.model.ExpandoTableRow;
import com.liferay.portal.service.base.ExpandoTableRowLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoTableRowLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableRowLocalServiceImpl
	extends ExpandoTableRowLocalServiceBaseImpl {

	public ExpandoTableRow addRow(long tableId)
		throws PortalException, SystemException {

		long rowId = counterLocalService.increment();

		ExpandoTableRow row = expandoTableRowPersistence.create(rowId);

		row.setTableId(tableId);

		expandoTableRowPersistence.update(row);

		return row;
	}

	public void deleteRow(long rowId)
		throws PortalException, SystemException {

		// ExpandoValues

		expandoValueLocalService.deleteRowValues(rowId);

		// ExpandoTableRow

		expandoTableRowPersistence.remove(rowId);
	}

	public void deleteRows(long tableId)
		throws PortalException, SystemException {

		List<ExpandoTableRow> rows =
			expandoTableRowPersistence.findByTableId(tableId);

		for (ExpandoTableRow row : rows) {
			deleteRow(row.getRowId());
		}
	}

	public void deleteRows(long tableId, long[] rowIds)
		throws PortalException, SystemException {

		for (int i = 0; i < rowIds.length; i++) {
			deleteRow(rowIds[i]);
		}
	}

	public ExpandoTableRow getRow(long rowId)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.findByPrimaryKey(rowId);
	}

	public List<ExpandoTableRow> getRows(long tableId)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.findByTableId(tableId);
	}

	public List<ExpandoTableRow> getRows(
			long tableId, int begin, int end)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.findByTableId(tableId, begin, end);
	}

	public int getRowsCount(long tableId)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.countByTableId(tableId);
	}

	public ExpandoTableRow setRow(long tableId, long rowId)
		throws PortalException, SystemException {

		ExpandoTableRow row =
			expandoTableRowPersistence.fetchByPrimaryKey(rowId);

		if (row == null) {
			rowId = counterLocalService.increment();

			row = expandoTableRowPersistence.create(rowId);

			row.setTableId(tableId);

			expandoTableRowPersistence.update(row);
		}

		return row;
	}

}