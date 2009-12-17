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
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.addExpandoColumn(expandoColumn);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn createExpandoColumn(
		long columnId) {
		return _expandoColumnLocalService.createExpandoColumn(columnId);
	}

	public void deleteExpandoColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteExpandoColumn(columnId);
	}

	public void deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteExpandoColumn(expandoColumn);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getExpandoColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getExpandoColumn(columnId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getExpandoColumns(
		int start, int end) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getExpandoColumns(start, end);
	}

	public int getExpandoColumnsCount()
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getExpandoColumnsCount();
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn,
			merge);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.addColumn(tableId, name, type,
			defaultData);
	}

	public void deleteColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column)
		throws com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumn(column);
	}

	public void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumn(columnId);
	}

	public void deleteColumn(long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumn(tableId, name);
	}

	public void deleteColumn(long classNameId, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumn(classNameId, tableName, name);
	}

	public void deleteColumn(java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumn(className, tableName, name);
	}

	public void deleteColumns(long tableId)
		throws com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumns(tableId);
	}

	public void deleteColumns(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumns(classNameId, tableName);
	}

	public void deleteColumns(java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoColumnLocalService.deleteColumns(className, tableName);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumn(columnId);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumn(tableId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long classNameId, java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumn(classNameId, tableName, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		java.lang.String className, java.lang.String tableName,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumn(className, tableName, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumns(tableId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumns(classNameId, tableName);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumns(className, tableName);
	}

	public int getColumnsCount(long tableId)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumnsCount(tableId);
	}

	public int getColumnsCount(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumnsCount(classNameId, tableName);
	}

	public int getColumnsCount(java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getColumnsCount(className, tableName);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumn(classNameId,
			name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumn(className, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long classNameId) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumns(classNameId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		java.lang.String className) throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumns(className);
	}

	public int getDefaultTableColumnsCount(long classNameId)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(classNameId);
	}

	public int getDefaultTableColumnsCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(className);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type,
			defaultData);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoColumnLocalService.updateTypeSettings(columnId,
			typeSettings);
	}

	public ExpandoColumnLocalService getWrappedExpandoColumnLocalService() {
		return _expandoColumnLocalService;
	}

	private ExpandoColumnLocalService _expandoColumnLocalService;
}