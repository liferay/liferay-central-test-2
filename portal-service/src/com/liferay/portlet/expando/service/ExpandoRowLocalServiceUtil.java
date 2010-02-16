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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ExpandoRowLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ExpandoRowLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRowLocalService
 * @generated
 */
public class ExpandoRowLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addExpandoRow(expandoRow);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId) {
		return getService().createExpandoRow(rowId);
	}

	public static void deleteExpandoRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoRow(rowId);
	}

	public static void deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExpandoRow(expandoRow);
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

	public static com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoRow(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoRows(start, end);
	}

	public static int getExpandoRowsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoRowsCount();
	}

	public static com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoRow(expandoRow);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoRow(expandoRow, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow addRow(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addRow(tableId, classPK);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRow(rowId);
	}

	public static void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRow(tableId, classPK);
	}

	public static void deleteRow(long classNameId, java.lang.String tableName,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRow(classNameId, tableName, classPK);
	}

	public static void deleteRow(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRow(className, tableName, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableRows(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableRows(className, start, end);
	}

	public static int getDefaultTableRowsCount(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableRowsCount(classNameId);
	}

	public static int getDefaultTableRowsCount(java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTableRowsCount(className);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRow(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRow(tableId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRow(classNameId, tableName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRow(className, tableName, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRows(tableId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long classNameId, java.lang.String tableName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRows(classNameId, tableName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRows(className, tableName, start, end);
	}

	public static int getRowsCount(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowsCount(tableId);
	}

	public static int getRowsCount(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowsCount(classNameId, tableName);
	}

	public static int getRowsCount(java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRowsCount(className, tableName);
	}

	public static ExpandoRowLocalService getService() {
		if (_service == null) {
			_service = (ExpandoRowLocalService)PortalBeanLocatorUtil.locate(ExpandoRowLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ExpandoRowLocalService service) {
		_service = service;
	}

	private static ExpandoRowLocalService _service;
}