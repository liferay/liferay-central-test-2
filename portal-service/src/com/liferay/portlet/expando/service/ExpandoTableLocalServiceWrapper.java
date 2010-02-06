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
		throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.addExpandoTable(expandoTable);
	}

	public com.liferay.portlet.expando.model.ExpandoTable createExpandoTable(
		long tableId) {
		return _expandoTableLocalService.createExpandoTable(tableId);
	}

	public void deleteExpandoTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteExpandoTable(tableId);
	}

	public void deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteExpandoTable(expandoTable);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.getExpandoTable(tableId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end) throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.getExpandoTables(start, end);
	}

	public int getExpandoTablesCount()
		throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.getExpandoTablesCount();
	}

	public com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.updateExpandoTable(expandoTable);
	}

	public com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge) throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.updateExpandoTable(expandoTable, merge);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.addDefaultTable(classNameId);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.addDefaultTable(className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.addTable(classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.addTable(className, name);
	}

	public void deleteTable(
		com.liferay.portlet.expando.model.ExpandoTable table)
		throws com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteTable(table);
	}

	public void deleteTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteTable(tableId);
	}

	public void deleteTable(long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteTable(classNameId, name);
	}

	public void deleteTable(java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteTable(className, name);
	}

	public void deleteTables(long classNameId)
		throws com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteTables(classNameId);
	}

	public void deleteTables(java.lang.String className)
		throws com.liferay.portal.SystemException {
		_expandoTableLocalService.deleteTables(className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.getDefaultTable(classNameId);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.getDefaultTable(className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.getTable(tableId);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.getTable(classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.getTable(className, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long classNameId) throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.getTables(classNameId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		java.lang.String className) throws com.liferay.portal.SystemException {
		return _expandoTableLocalService.getTables(className);
	}

	public com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _expandoTableLocalService.updateTable(tableId, name);
	}

	public ExpandoTableLocalService getWrappedExpandoTableLocalService() {
		return _expandoTableLocalService;
	}

	private ExpandoTableLocalService _expandoTableLocalService;
}