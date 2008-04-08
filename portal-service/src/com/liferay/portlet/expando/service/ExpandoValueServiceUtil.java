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
 * <a href="ExpandoValueServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoValueService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.expando.service.ExpandoValueServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoValueService
 * @see com.liferay.portlet.expando.service.ExpandoValueServiceFactory
 *
 */
public class ExpandoValueServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long columnId, long classPK, long rowId, java.lang.String data)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.addValue(columnId, classPK, rowId, data);
	}

	public static void deleteColumnValues(long columnId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		expandoValueService.deleteColumnValues(columnId);
	}

	public static void deleteRowValues(long rowId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		expandoValueService.deleteRowValues(rowId);
	}

	public static void deleteTableValues(long tableId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		expandoValueService.deleteTableValues(tableId);
	}

	public static void deleteValue(long valueId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		expandoValueService.deleteValue(valueId);
	}

	public static void deleteValues(java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		expandoValueService.deleteValues(className, classPK);
	}

	public static void deleteValues(long classNameId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		expandoValueService.deleteValues(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long columnId, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getColumnValues(columnId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getColumnValues(className, tableName,
			columnName, begin, end);
	}

	public static int getColumnValuesCount(long columnId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getColumnValuesCount(columnId);
	}

	public static int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getColumnValuesCount(className, tableName,
			columnName);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		java.lang.String className, java.lang.String columnName, int begin,
		int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getDefaultTableColumnValues(className,
			columnName, begin, end);
	}

	public static int getDefaultTableColumnValuesCount(
		java.lang.String className, java.lang.String columnName)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getDefaultTableColumnValuesCount(className,
			columnName);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getRowValues(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getRowValues(rowId, begin, end);
	}

	public static int getRowValuesCount(long rowId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getRowValuesCount(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long valueId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getValue(valueId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long columnId, long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getValue(columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String name, long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoValueService expandoValueService = ExpandoValueServiceFactory.getService();

		return expandoValueService.getValue(className, tableName, name, rowId);
	}
}