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
 * <a href="ExpandoTableLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ExpandoTableLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTableLocalService
 * @generated
 */
public class ExpandoTableLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoTable addExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addExpandoTable(expandoTable);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable createExpandoTable(
		long tableId) {
		return getService().createExpandoTable(tableId);
	}

	public static void deleteExpandoTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoTable(tableId);
	}

	public static void deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoTable(expandoTable);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoTable(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoTables(start, end);
	}

	public static int getExpandoTablesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoTablesCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoTable(expandoTable);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoTable(expandoTable, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addDefaultTable(companyId, classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addDefaultTable(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(companyId, classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(companyId, className, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(className, name);
	}

	public static void deleteTable(
		com.liferay.portlet.expando.model.ExpandoTable table)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(table);
	}

	public static void deleteTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(tableId);
	}

	public static void deleteTable(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(companyId, classNameId, name);
	}

	public static void deleteTable(long companyId, java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(companyId, className, name);
	}

	public static void deleteTables(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTables(companyId, classNameId);
	}

	public static void deleteTables(long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTables(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTable(companyId, classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTable(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(companyId, classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(companyId, className, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(className, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTables(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTables(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTable(tableId, name);
	}

	public static ExpandoTableLocalService getService() {
		if (_service == null) {
			_service = (ExpandoTableLocalService)PortalBeanLocatorUtil.locate(ExpandoTableLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ExpandoTableLocalService service) {
		_service = service;
	}

	private static ExpandoTableLocalService _service;
}