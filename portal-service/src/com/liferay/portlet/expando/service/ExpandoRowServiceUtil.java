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
 * <a href="ExpandoRowServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoRowService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.expando.service.ExpandoRowServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoRowService
 * @see com.liferay.portlet.expando.service.ExpandoRowServiceFactory
 *
 */
public class ExpandoRowServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoRow addRow(
		long tableId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.addRow(tableId, classPK);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		expandoRowService.deleteRow(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		java.lang.String className, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getDefaultTableRows(className, begin, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long classNameId, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getDefaultTableRows(classNameId, begin, end);
	}

	public static int getDefaultTableRowsCount(java.lang.String className)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getDefaultTableRowsCount(className);
	}

	public static int getDefaultTableRowsCount(long classNameId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getDefaultTableRowsCount(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRow(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long tableId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRow(tableId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRow(className, tableName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRow(classNameId, tableName, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRows(tableId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int begin,
		int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRows(className, tableName, begin, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long classNameId, java.lang.String tableName, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRows(classNameId, tableName, begin, end);
	}

	public static int getRowsCount(long tableId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRowsCount(tableId);
	}

	public static int getRowsCount(java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRowsCount(className, tableName);
	}

	public static int getRowsCount(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoRowService expandoRowService = ExpandoRowServiceFactory.getService();

		return expandoRowService.getRowsCount(classNameId, tableName);
	}
}