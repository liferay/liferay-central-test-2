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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ExpandoColumnLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ExpandoColumnLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnLocalService
 * @generated
 */
public class ExpandoColumnLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoColumn addExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addExpandoColumn(expandoColumn);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn createExpandoColumn(
		long columnId) {
		return getService().createExpandoColumn(columnId);
	}

	public static void deleteExpandoColumn(long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoColumn(columnId);
	}

	public static void deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoColumn(expandoColumn);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoColumn(columnId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getExpandoColumns(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoColumns(start, end);
	}

	public static int getExpandoColumnsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoColumnsCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoColumn(expandoColumn);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoColumn(expandoColumn, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addColumn(tableId, name, type);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addColumn(tableId, name, type, defaultData);
	}

	public static void deleteColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumn(column);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumn(columnId);
	}

	public static void deleteColumn(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumn(companyId, classNameId, tableName, name);
	}

	public static void deleteColumn(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumn(tableId, name);
	}

	public static void deleteColumn(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumn(companyId, className, tableName, name);
	}

	public static void deleteColumns(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumns(tableId);
	}

	public static void deleteColumns(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumns(companyId, classNameId, tableName);
	}

	public static void deleteColumns(long companyId,
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteColumns(companyId, className, tableName);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumn(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumn(companyId, classNameId, tableName, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumn(tableId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumn(companyId, className, tableName, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumns(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, long classNameId, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumns(companyId, classNameId, tableName);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumns(companyId, className, tableName);
	}

	public static int getColumnsCount(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumnsCount(tableId);
	}

	public static int getColumnsCount(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumnsCount(companyId, classNameId, tableName);
	}

	public static int getColumnsCount(long companyId,
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getColumnsCount(companyId, className, tableName);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableColumn(companyId, classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableColumn(companyId, className, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableColumns(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableColumns(companyId, className);
	}

	public static int getDefaultTableColumnsCount(long companyId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableColumnsCount(companyId, classNameId);
	}

	public static int getDefaultTableColumnsCount(long companyId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableColumnsCount(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateColumn(columnId, name, type);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateColumn(columnId, name, type, defaultData);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTypeSettings(columnId, typeSettings);
	}

	public static ExpandoColumnLocalService getService() {
		if (_service == null) {
			_service = (ExpandoColumnLocalService)PortalBeanLocatorUtil.locate(ExpandoColumnLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ExpandoColumnLocalService service) {
		_service = service;
	}

	private static ExpandoColumnLocalService _service;
}