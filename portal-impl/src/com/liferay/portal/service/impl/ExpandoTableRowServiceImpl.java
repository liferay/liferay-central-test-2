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
import com.liferay.portal.model.ExpandoTableRow;
import com.liferay.portal.service.base.ExpandoTableRowServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoTableRowServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableRowServiceImpl extends ExpandoTableRowServiceBaseImpl {

	public ExpandoTableRow addRow(long tableId)
		throws PortalException, SystemException {

		return expandoTableRowLocalService.addRow(tableId);
	}

	public void deleteRow(long rowId)
		throws PortalException, SystemException {

		expandoTableRowLocalService.deleteRow(rowId);
	}

	public void deleteRows(long tableId)
		throws PortalException, SystemException {

		expandoTableRowLocalService.deleteRows(tableId);
	}

	public void deleteRows(long tableId, long[] rowIds)
		throws PortalException, SystemException {

		expandoTableRowLocalService.deleteRows(tableId, rowIds);
	}

	public ExpandoTableRow getRow(long rowId)
		throws PortalException, SystemException {

		return expandoTableRowLocalService.getRow(rowId);
	}

	public List<ExpandoTableRow> getRows(long tableId)
		throws PortalException, SystemException {

		return expandoTableRowLocalService.getRows(tableId);
	}

	public List<ExpandoTableRow> getRows(
			long tableId, int begin, int end)
		throws PortalException, SystemException {

		return expandoTableRowLocalService.getRows(tableId, begin, end);
	}

	public int getRowsCount(long tableId)
		throws PortalException, SystemException {

		return expandoTableRowLocalService.getRowsCount(tableId);
	}

	public ExpandoTableRow setRow(long tableId, long rowId)
		throws PortalException, SystemException {

		return expandoTableRowLocalService.setRow(tableId, rowId);
	}

}