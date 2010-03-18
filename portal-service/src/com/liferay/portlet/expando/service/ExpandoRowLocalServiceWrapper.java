/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.addExpandoRow(expandoRow);
	}

	public com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId) {
		return _expandoRowLocalService.createExpandoRow(rowId);
	}

	public void deleteExpandoRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteExpandoRow(rowId);
	}

	public void deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getExpandoRow(rowId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getExpandoRows(start, end);
	}

	public int getExpandoRowsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getExpandoRowsCount();
	}

	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.updateExpandoRow(expandoRow);
	}

	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.updateExpandoRow(expandoRow, merge);
	}

	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.addRow(tableId, classPK);
	}

	public void deleteRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(rowId);
	}

	public void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(tableId, classPK);
	}

	public void deleteRow(long companyId, long classNameId,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(companyId, classNameId, tableName,
			classPK);
	}

	public void deleteRow(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(companyId, className, tableName,
			classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRows(companyId,
			classNameId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, java.lang.String className, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRows(companyId,
			className, start, end);
	}

	public int getDefaultTableRowsCount(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRowsCount(companyId,
			classNameId);
	}

	public int getDefaultTableRowsCount(long companyId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRowsCount(companyId,
			className);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(rowId);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long tableId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(tableId, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(companyId, classNameId,
			tableName, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(companyId, className, tableName,
			classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(tableId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, long classNameId, java.lang.String tableName,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(companyId, classNameId,
			tableName, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, java.lang.String className, java.lang.String tableName,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(companyId, className, tableName,
			start, end);
	}

	public int getRowsCount(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(tableId);
	}

	public int getRowsCount(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(companyId, classNameId,
			tableName);
	}

	public int getRowsCount(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(companyId, className,
			tableName);
	}

	public ExpandoRowLocalService getWrappedExpandoRowLocalService() {
		return _expandoRowLocalService;
	}

	private ExpandoRowLocalService _expandoRowLocalService;
}