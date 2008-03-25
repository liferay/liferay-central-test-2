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
import com.liferay.portal.model.ExpandoValue;
import com.liferay.portal.service.base.ExpandoValueServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoValueServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueServiceImpl extends ExpandoValueServiceBaseImpl {

	public ExpandoValue addValue(
			long classPK, long columnId, String value)
		throws PortalException, SystemException {

		return expandoValueLocalService.addValue(classPK, columnId, value);
	}

	public ExpandoValue getValue(long classPK, long columnId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValue(classPK, columnId);
	}

	public void deleteValue(long valueId)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteValue(valueId);
	}

	public void deleteValue(long classPK, long columnId)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteValue(classPK, columnId);
	}

	public void deleteValues(long classPK)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteValues(classPK);
	}

	public void deleteColumnValues(long columnId)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteColumnValues(columnId);
	}

	public void deleteRowValues(long rowId)
		throws PortalException, SystemException {

		expandoValueLocalService.deleteRowValues(rowId);
	}

	public List<ExpandoValue> getColumnValues(long columnId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getColumnValues(columnId);
	}

	public List<ExpandoValue> getColumnValues(long columnId, int begin, int end)
		throws PortalException, SystemException {

		return expandoValueLocalService.getColumnValues(columnId, begin, end);
	}

	public int getColumnValuesCount(long columnId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getColumnValuesCount(columnId);
	}

	public List<ExpandoValue> getRowValues(long rowId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getRowValues(rowId);
	}

	public List<ExpandoValue> getRowValues(long rowId, int begin, int end)
		throws PortalException, SystemException {

		return expandoValueLocalService.getRowValues(rowId, begin, end);
	}

	public int getRowValuesCount(long rowId)
		throws PortalException, SystemException {

		return expandoValueLocalService.getRowValuesCount(rowId);
	}

	public List<ExpandoValue> getValues(long classPK)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValues(classPK);
	}

	public List<ExpandoValue> getValues(long classPK, int begin, int end)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValues(classPK, begin, end);
	}

	public int getValuesCount(long classPK)
		throws PortalException, SystemException {

		return expandoValueLocalService.getValuesCount(classPK);
	}

	public ExpandoValue setValue(
			long classPK, long columnId, String value)
		throws PortalException, SystemException {

		return expandoValueLocalService.setValue(classPK, columnId, value);
	}

	public long setRowValues(
			long tableId, ExpandoValue[] expandoValues)
		throws PortalException, SystemException {

		return expandoValueLocalService.setRowValues(tableId, 0, expandoValues);
	}

	public long setRowValues(
			long tableId, List<ExpandoValue> expandoValues)
		throws PortalException, SystemException {

		return expandoValueLocalService.setRowValues(tableId, 0, expandoValues);
	}

	public long setRowValues(
			long tableId, long rowId, ExpandoValue[] expandoValues)
		throws PortalException, SystemException {

		return expandoValueLocalService.setRowValues(
			tableId, rowId, expandoValues);
	}

	public long setRowValues(
			long tableId, long rowId, List<ExpandoValue> expandoValues)
		throws PortalException, SystemException {

		return expandoValueLocalService.setRowValues(
			tableId, rowId, expandoValues);
	}

}