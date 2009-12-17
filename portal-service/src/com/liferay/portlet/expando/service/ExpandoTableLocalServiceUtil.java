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
		throws com.liferay.portal.SystemException {
		return getService().addExpandoTable(expandoTable);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable createExpandoTable(
		long tableId) {
		return getService().createExpandoTable(tableId);
	}

	public static void deleteExpandoTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteExpandoTable(tableId);
	}

	public static void deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		getService().deleteExpandoTable(expandoTable);
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

	public static com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getExpandoTable(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getExpandoTables(start, end);
	}

	public static int getExpandoTablesCount()
		throws com.liferay.portal.SystemException {
		return getService().getExpandoTablesCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		return getService().updateExpandoTable(expandoTable);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateExpandoTable(expandoTable, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addDefaultTable(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addDefaultTable(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addTable(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addTable(className, name);
	}

	public static void deleteTable(
		com.liferay.portlet.expando.model.ExpandoTable table)
		throws com.liferay.portal.SystemException {
		getService().deleteTable(table);
	}

	public static void deleteTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTable(tableId);
	}

	public static void deleteTable(long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTable(classNameId, name);
	}

	public static void deleteTable(java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTable(className, name);
	}

	public static void deleteTables(long classNameId)
		throws com.liferay.portal.SystemException {
		getService().deleteTables(classNameId);
	}

	public static void deleteTables(java.lang.String className)
		throws com.liferay.portal.SystemException {
		getService().deleteTables(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDefaultTable(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDefaultTable(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTable(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTable(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTable(className, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long classNameId) throws com.liferay.portal.SystemException {
		return getService().getTables(classNameId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getService().getTables(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
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