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
 * <a href="ExpandoTableLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoTableLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTableLocalService
 * @generated
 */
public class ExpandoTableLocalServiceWrapper implements ExpandoTableLocalService {
	public ExpandoTableLocalServiceWrapper(
		ExpandoTableLocalService expandoTableLocalService) {
		_expandoTableLocalService = expandoTableLocalService;
	}

	public com.liferay.portlet.expando.model.ExpandoTable addExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addExpandoTable(expandoTable);
	}

	public com.liferay.portlet.expando.model.ExpandoTable createExpandoTable(
		long tableId) {
		return _expandoTableLocalService.createExpandoTable(tableId);
	}

	public void deleteExpandoTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteExpandoTable(tableId);
	}

	public void deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteExpandoTable(expandoTable);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getExpandoTable(tableId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getExpandoTables(start, end);
	}

	public int getExpandoTablesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getExpandoTablesCount();
	}

	public com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.updateExpandoTable(expandoTable);
	}

	public com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.updateExpandoTable(expandoTable, merge);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addDefaultTable(companyId, classNameId);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addDefaultTable(companyId, className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addTable(companyId, classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addTable(classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addTable(companyId, className, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.addTable(className, name);
	}

	public void deleteTable(
		com.liferay.portlet.expando.model.ExpandoTable table)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteTable(table);
	}

	public void deleteTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteTable(tableId);
	}

	public void deleteTable(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteTable(companyId, classNameId, name);
	}

	public void deleteTable(long companyId, java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteTable(companyId, className, name);
	}

	public void deleteTables(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteTables(companyId, classNameId);
	}

	public void deleteTables(long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoTableLocalService.deleteTables(companyId, className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getDefaultTable(companyId, classNameId);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getDefaultTable(companyId, className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTable(tableId);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTable(companyId, classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTable(classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTable(companyId, className, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTable(className, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTables(companyId, classNameId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.getTables(companyId, className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoTableLocalService.updateTable(tableId, name);
	}

	public ExpandoTableLocalService getWrappedExpandoTableLocalService() {
		return _expandoTableLocalService;
	}

	private ExpandoTableLocalService _expandoTableLocalService;
}