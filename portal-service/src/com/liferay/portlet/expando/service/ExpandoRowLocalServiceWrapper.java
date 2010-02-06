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
 * <a href="ExpandoRowLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoRowLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRowLocalService
 * @generated
 */
public class ExpandoRowLocalServiceWrapper implements ExpandoRowLocalService {
	public ExpandoRowLocalServiceWrapper(
		ExpandoRowLocalService expandoRowLocalService) {
		_expandoRowLocalService = expandoRowLocalService;
	}

	public com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.addExpandoRow(expandoRow);
	}

	public com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId) {
		return _expandoRowLocalService.createExpandoRow(rowId);
	}

	public void deleteExpandoRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoRowLocalService.deleteExpandoRow(rowId);
	}

	public void deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		_expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoRowLocalService.getExpandoRow(rowId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end) throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getExpandoRows(start, end);
	}

	public int getExpandoRowsCount() throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getExpandoRowsCount();
	}

	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.updateExpandoRow(expandoRow);
	}

	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.updateExpandoRow(expandoRow, merge);
	}

	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoRowLocalService.addRow(tableId, classPK);
	}

	public void deleteRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoRowLocalService.deleteRow(rowId);
	}

	public void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoRowLocalService.deleteRow(tableId, classPK);
	}

	public void deleteRow(long classNameId, java.lang.String tableName,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoRowLocalService.deleteRow(classNameId, tableName, classPK);
	}

	public void deleteRow(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoRowLocalService.deleteRow(className, tableName, classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getDefaultTableRows(classNameId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getDefaultTableRows(className, start, end);
	}

	public int getDefaultTableRowsCount(long classNameId)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getDefaultTableRowsCount(classNameId);
	}

	public int getDefaultTableRowsCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getDefaultTableRowsCount(className);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRow(rowId);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long tableId,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRow(tableId, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRow(classNameId, tableName, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRow(className, tableName, classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRows(tableId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long classNameId, java.lang.String tableName, int start, int end)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRows(classNameId, tableName, start,
			end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRows(className, tableName, start, end);
	}

	public int getRowsCount(long tableId)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRowsCount(tableId);
	}

	public int getRowsCount(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRowsCount(classNameId, tableName);
	}

	public int getRowsCount(java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		return _expandoRowLocalService.getRowsCount(className, tableName);
	}

	public ExpandoRowLocalService getWrappedExpandoRowLocalService() {
		return _expandoRowLocalService;
	}

	private ExpandoRowLocalService _expandoRowLocalService;
}