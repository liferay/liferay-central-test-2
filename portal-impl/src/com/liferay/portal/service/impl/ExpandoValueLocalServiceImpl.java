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

import com.liferay.portal.ExpandoTableRowException;
import com.liferay.portal.ExpandoValueClassPKException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.model.ExpandoTableRow;
import com.liferay.portal.model.ExpandoValue;
import com.liferay.portal.service.base.ExpandoValueLocalServiceBaseImpl;
import com.liferay.util.ListUtil;

import java.util.List;

/**
 * <a href="ExpandoValueLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueLocalServiceImpl
	extends ExpandoValueLocalServiceBaseImpl {

	public ExpandoValue addValue(
			long classPK, long columnId)
		throws PortalException, SystemException {

		return addValue(classPK, columnId, null);
	}

	public ExpandoValue addValue(
			long classPK, long columnId, String value)
		throws PortalException, SystemException {

		validate(classPK);

		long expandoValueId = counterLocalService.increment();

		ExpandoValue expandoValue =
			expandoValuePersistence.create(expandoValueId);

		expandoValue.setClassPK(classPK);
		expandoValue.setColumnId(columnId);
		expandoValue.setValue(value);

		return expandoValuePersistence.update(expandoValue, false);
	}

	public ExpandoValue getValue(long classPK, long columnId)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByC_C(classPK, columnId);
	}

	public void deleteValue(long valueId)
		throws PortalException, SystemException {

		expandoValuePersistence.remove(valueId);
	}

	public void deleteValue(long classPK, long columnId)
		throws PortalException, SystemException {

		expandoValuePersistence.removeByC_C(classPK, columnId);
	}

	public void deleteValues(long classPK)
		throws PortalException, SystemException {

		expandoValuePersistence.removeByClassPK(classPK);
	}

	public void deleteColumnValues(long columnId)
		throws PortalException, SystemException {

		expandoValuePersistence.removeByColumnId(columnId);
	}

	public void deleteRowValues(long rowId)
		throws PortalException, SystemException {

		List<ExpandoValue> values = getRowValues(rowId);

		for (ExpandoValue value : values) {
			deleteValue(value.getValueId());
		}
	}

	public List<ExpandoValue> getColumnValues(long columnId)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByColumnId(columnId);
	}

	public List<ExpandoValue> getColumnValues(long columnId, int begin, int end)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByColumnId(columnId, begin, end);
	}

	public int getColumnValuesCount(long columnId)
		throws PortalException, SystemException {

		return expandoValuePersistence.countByColumnId(columnId);
	}

	public List<ExpandoValue> getRowValues(long rowId)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.getExpandoValues(rowId);
	}

	public List<ExpandoValue> getRowValues(long rowId, int begin, int end)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.getExpandoValues(rowId, begin, end);
	}

	public int getRowValuesCount(long rowId)
		throws PortalException, SystemException {

		return expandoTableRowPersistence.getExpandoValuesSize(rowId);
	}

	public List<ExpandoValue> getValues(long classPK)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByClassPK(classPK);
	}

	public List<ExpandoValue> getValues(long classPK, int begin, int end)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByClassPK(classPK, begin, end);
	}

	public int getValuesCount(long classPK)
		throws PortalException, SystemException {

		return expandoValuePersistence.countByClassPK(classPK);
	}

	public ExpandoValue setValue(
			long classPK, long columnId, String value)
		throws PortalException, SystemException {

		validate(classPK);

		ExpandoValue expandoValue =
			expandoValuePersistence.fetchByC_C(classPK, columnId);

		if (expandoValue == null) {
			long expandoValueId = counterLocalService.increment();

			expandoValue = expandoValuePersistence.create(expandoValueId);

			expandoValue.setClassPK(classPK);
			expandoValue.setColumnId(columnId);
		}

		expandoValue.setValue(value);

		return expandoValuePersistence.update(expandoValue, false);
	}

	public long setRowValues(
			long tableId, ExpandoValue[] expandoValues)
		throws PortalException, SystemException {

		return setRowValues(tableId, 0, expandoValues);
	}

	public long setRowValues(
			long tableId, List<ExpandoValue> expandoValues)
		throws PortalException, SystemException {

		return setRowValues(tableId, 0, expandoValues);
	}

	public long setRowValues(
			long tableId, long rowId, ExpandoValue[] expandoValues)
		throws PortalException, SystemException {

		List<ExpandoValue> list =
			(List<ExpandoValue>)ListUtil.fromArray(expandoValues);

		return setRowValues(tableId, rowId, list);
	}

	public long setRowValues(
			long tableId, long rowId, List<ExpandoValue> expandoValues)
		throws PortalException, SystemException {

		List<ExpandoColumn> columns =
			expandoColumnLocalService.getTableColumns(tableId);

		if (expandoValues.size() > columns.size()) {
			throw new ExpandoTableRowException(
				"Too many ExpandoValues for ExpandoTableRow.");
		}

		for (ExpandoValue expandoValue : expandoValues) {
			boolean found = false;

			for (ExpandoColumn column : columns) {
				if (expandoValue.getColumnId() == column.getColumnId()) {
					found = true;
				}
			}

			if (!found) {
				throw new ExpandoTableRowException(
					"ExpandoValue {" + expandoValue.getValueId() + ", "
					+ expandoValue.getClassPK() + ", "
					+ expandoValue.getColumnId() + ", "
					+ expandoValue.getValue() + "} doesn't match an "
					+ "ExpandoColumn in the ExpandoTable {" + tableId + "}");
			}
		}

		ExpandoTableRow expandoValueRow =
			expandoTableRowLocalService.setRow(tableId, rowId);

		for (ExpandoValue expandoValue : expandoValues) {
			setValue(
				rowId, expandoValue.getColumnId(), expandoValue.getValue());
		}

		expandoTableRowPersistence.setExpandoValues(
			expandoValueRow.getRowId(), expandoValues);

		return rowId;
	}

	protected void validate(long classPK)
		throws PortalException, SystemException {

		if (classPK <= 0) {
			throw new ExpandoValueClassPKException();
		}
	}

}