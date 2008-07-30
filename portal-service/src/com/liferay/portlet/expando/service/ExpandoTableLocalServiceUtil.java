/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoTableLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoTableLocalService
 *
 */
public class ExpandoTableLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoTable addExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		return _service.addExpandoTable(expandoTable);
	}

	public static void deleteExpandoTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteExpandoTable(tableId);
	}

	public static void deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		_service.deleteExpandoTable(expandoTable);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getExpandoTable(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getExpandoTables(start, end);
	}

	public static int getExpandoTablesCount()
		throws com.liferay.portal.SystemException {
		return _service.getExpandoTablesCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		return _service.updateExpandoTable(expandoTable);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addDefaultTable(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addDefaultTable(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addTable(className, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addTable(classNameId, name);
	}

	public static void deleteTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTable(tableId);
	}

	public static void deleteTable(java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTable(className, name);
	}

	public static void deleteTable(long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTable(classNameId, name);
	}

	public static void deleteTables(java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTables(className);
	}

	public static void deleteTables(long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTables(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getDefaultTable(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getDefaultTable(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getTable(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getTable(className, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getTable(classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		java.lang.String className) throws com.liferay.portal.SystemException {
		return _service.getTables(className);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long classNameId) throws com.liferay.portal.SystemException {
		return _service.getTables(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateTable(tableId, name);
	}

	public static ExpandoTableLocalService getService() {
		return _service;
	}

	public void setService(ExpandoTableLocalService service) {
		_service = service;
	}

	private static ExpandoTableLocalService _service;
}