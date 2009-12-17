/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
		throws com.liferay.portal.SystemException {
		return getService().addExpandoColumn(expandoColumn);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn createExpandoColumn(
		long columnId) {
		return getService().createExpandoColumn(columnId);
	}

	public static void deleteExpandoColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteExpandoColumn(columnId);
	}

	public static void deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		getService().deleteExpandoColumn(expandoColumn);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getExpandoColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getExpandoColumn(columnId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getExpandoColumns(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getExpandoColumns(start, end);
	}

	public static int getExpandoColumnsCount()
		throws com.liferay.portal.SystemException {
		return getService().getExpandoColumnsCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		return getService().updateExpandoColumn(expandoColumn);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateExpandoColumn(expandoColumn, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addColumn(tableId, name, type);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addColumn(tableId, name, type, defaultData);
	}

	public static void deleteColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column)
		throws com.liferay.portal.SystemException {
		getService().deleteColumn(column);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumn(columnId);
	}

	public static void deleteColumn(long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumn(tableId, name);
	}

	public static void deleteColumn(long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumn(classNameId, tableName, name);
	}

	public static void deleteColumn(java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumn(className, tableName, name);
	}

	public static void deleteColumns(long tableId)
		throws com.liferay.portal.SystemException {
		getService().deleteColumns(tableId);
	}

	public static void deleteColumns(long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumns(classNameId, tableName);
	}

	public static void deleteColumns(java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteColumns(className, tableName);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getColumn(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getColumn(tableId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long classNameId, java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().getColumn(classNameId, tableName, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		java.lang.String className, java.lang.String tableName,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getService().getColumn(className, tableName, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId) throws com.liferay.portal.SystemException {
		return getService().getColumns(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		return getService().getColumns(classNameId, tableName);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		return getService().getColumns(className, tableName);
	}

	public static int getColumnsCount(long tableId)
		throws com.liferay.portal.SystemException {
		return getService().getColumnsCount(tableId);
	}

	public static int getColumnsCount(long classNameId,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		return getService().getColumnsCount(classNameId, tableName);
	}

	public static int getColumnsCount(java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		return getService().getColumnsCount(className, tableName);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().getDefaultTableColumn(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().getDefaultTableColumn(className, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long classNameId) throws com.liferay.portal.SystemException {
		return getService().getDefaultTableColumns(classNameId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getService().getDefaultTableColumns(className);
	}

	public static int getDefaultTableColumnsCount(long classNameId)
		throws com.liferay.portal.SystemException {
		return getService().getDefaultTableColumnsCount(classNameId);
	}

	public static int getDefaultTableColumnsCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		return getService().getDefaultTableColumnsCount(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateColumn(columnId, name, type);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateColumn(columnId, name, type, defaultData);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
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