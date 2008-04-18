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
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.service.base.ExpandoRowServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoRowServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowServiceImpl extends ExpandoRowServiceBaseImpl {

	public ExpandoRow addRow(long tableId, long classPK)
		throws PortalException, SystemException {

		return expandoRowLocalService.addRow(tableId, classPK);
	}

	public void deleteRow(long rowId)
		throws PortalException, SystemException {

		expandoRowLocalService.deleteRow(rowId);
	}

	public List<ExpandoRow> getDefaultTableRows(
			String className, int begin, int end)
		throws SystemException {

		return expandoRowLocalService.getDefaultTableRows(
			className, begin, end);
	}

	public List<ExpandoRow> getDefaultTableRows(
			long classNameId, int begin, int end)
		throws SystemException {

		return expandoRowLocalService.getDefaultTableRows(
			classNameId, begin, end);
	}

	public int getDefaultTableRowsCount(String className)
		throws SystemException {

		return expandoRowLocalService.getDefaultTableRowsCount(className);
	}

	public int getDefaultTableRowsCount(long classNameId)
		throws SystemException {

		return expandoRowLocalService.getDefaultTableRowsCount(classNameId);
	}

	public ExpandoRow getRow(long rowId)
		throws PortalException, SystemException {

		return expandoRowLocalService.getRow(rowId);
	}

	public ExpandoRow getRow(long tableId, long classPK)
		throws PortalException, SystemException {

		return expandoRowLocalService.getRow(tableId, classPK);
	}

	public ExpandoRow getRow(String className, String tableName, long classPK)
		throws PortalException, SystemException {

		return expandoRowLocalService.getRow(className, tableName, classPK);
	}

	public ExpandoRow getRow(long classNameId, String tablename, long classPK)
		throws PortalException, SystemException {

		return expandoRowLocalService.getRow(classNameId, tablename, classPK);
	}

	public List<ExpandoRow> getRows(long tableId, int begin, int end)
		throws SystemException {

		return expandoRowLocalService.getRows(tableId, begin, end);
	}

	public List<ExpandoRow> getRows(
			String className, String tableName, int begin, int end)
		throws SystemException {

		return expandoRowLocalService.getRows(className, tableName, begin, end);
	}

	public List<ExpandoRow> getRows(
			long classNameId, String tableName, int begin, int end)
		throws SystemException {

		return expandoRowLocalService.getRows(
			classNameId, tableName, begin, end);
	}

	public int getRowsCount(long tableId) throws SystemException {
		return expandoRowLocalService.getRowsCount(tableId);
	}

	public int getRowsCount(String className, String tableName)
		throws SystemException {

		return expandoRowLocalService.getRowsCount(className, tableName);
	}

	public int getRowsCount(long classNameId, String tableName)
		throws SystemException {

		return expandoRowLocalService.getRowsCount(classNameId, tableName);
	}

}