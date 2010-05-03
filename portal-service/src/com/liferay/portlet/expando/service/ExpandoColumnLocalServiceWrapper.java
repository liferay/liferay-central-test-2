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
 * <a href="ExpandoColumnLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoColumnLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnLocalService
 * @generated
 */
public class ExpandoColumnLocalServiceWrapper
	implements ExpandoColumnLocalService {
	public ExpandoColumnLocalServiceWrapper(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.addExpandoColumn(expandoColumn);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn createExpandoColumn(
		long columnId) {
		return _expandoColumnLocalService.createExpandoColumn(columnId);
	}

	public void deleteExpandoColumn(long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteExpandoColumn(columnId);
	}

	public void deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteExpandoColumn(expandoColumn);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getExpandoColumn(columnId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getExpandoColumns(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getExpandoColumns(start, end);
	}

	public int getExpandoColumnsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getExpandoColumnsCount();
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn,
			merge);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.addColumn(tableId, name, type,
			defaultData);
	}

	public void deleteColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(column);
	}

	public void deleteColumn(long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(columnId);
	}

	public void deleteColumn(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(companyId, classNameId,
			tableName, name);
	}

	public void deleteColumn(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(tableId, name);
	}

	public void deleteColumn(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(companyId, className,
			tableName, name);
	}

	public void deleteColumns(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumns(tableId);
	}

	public void deleteColumns(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumns(companyId, classNameId,
			tableName);
	}

	public void deleteColumns(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumns(companyId, className, tableName);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(columnId);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(companyId, classNameId,
			tableName, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(tableId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(companyId, className,
			tableName, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(tableId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, long classNameId, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(companyId, classNameId,
			tableName);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(companyId, className,
			tableName);
	}

	public int getColumnsCount(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumnsCount(tableId);
	}

	public int getColumnsCount(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumnsCount(companyId,
			classNameId, tableName);
	}

	public int getColumnsCount(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumnsCount(companyId, className,
			tableName);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumn(companyId,
			classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumn(companyId,
			className, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumns(companyId,
			classNameId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumns(companyId,
			className);
	}

	public int getDefaultTableColumnsCount(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(companyId,
			classNameId);
	}

	public int getDefaultTableColumnsCount(long companyId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(companyId,
			className);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type,
			defaultData);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateTypeSettings(columnId,
			typeSettings);
	}

	public ExpandoColumnLocalService getWrappedExpandoColumnLocalService() {
		return _expandoColumnLocalService;
	}

	private ExpandoColumnLocalService _expandoColumnLocalService;
}