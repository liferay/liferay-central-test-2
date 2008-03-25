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

package com.liferay.portal.service;


/**
 * <a href="ExpandoColumnServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ExpandoColumnService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ExpandoColumnServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ExpandoColumnService
 * @see com.liferay.portal.service.ExpandoColumnServiceFactory
 *
 */
public class ExpandoColumnServiceUtil {
	public static com.liferay.portal.model.ExpandoColumn addColumn(
		long classNameId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.addColumn(classNameId, name, type);
	}

	public static com.liferay.portal.model.ExpandoColumn addColumn(
		long classNameId, java.lang.String name, int type,
		java.util.Properties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.addColumn(classNameId, name, type,
			properties);
	}

	public static void addTableColumns(long tableId, long[] columnIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.addTableColumns(tableId, columnIds);
	}

	public static void addTableColumns(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoColumn> columns)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.addTableColumns(tableId, columns);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.deleteColumn(columnId);
	}

	public static void deleteTableColumns(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.deleteTableColumns(tableId);
	}

	public static void deleteTableColumns(long tableId, long[] columnIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.deleteTableColumns(tableId, columnIds);
	}

	public static void deleteTableColumns(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoColumn> columns)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.deleteTableColumns(tableId, columns);
	}

	public static com.liferay.portal.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumn(columnId);
	}

	public static com.liferay.portal.model.ExpandoColumn getColumn(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumn(classNameId, name);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getColumns(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumns(classNameId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getColumns(
		long classNameId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumns(classNameId, begin, end);
	}

	public static int getColumnsCount(long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumnsCount(classNameId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getTableColumns(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getTableColumns(tableId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getTableColumns(
		long tableId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getTableColumns(tableId, begin, end);
	}

	public static int getTableColumnsCount(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getTableColumnsCount(tableId);
	}

	public static com.liferay.portal.model.ExpandoColumn setColumn(
		long classNameId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.setColumn(classNameId, name, type);
	}

	public static com.liferay.portal.model.ExpandoColumn setColumn(
		long classNameId, java.lang.String name, int type,
		java.util.Properties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.setColumn(classNameId, name, type,
			properties);
	}

	public static com.liferay.portal.model.ExpandoColumn updateColumnType(
		long columnId, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.updateColumnType(columnId, type);
	}

	public static com.liferay.portal.model.ExpandoColumn updateColumnName(
		long columnId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.updateColumnName(columnId, name);
	}

	public static com.liferay.portal.model.ExpandoColumn updateColumnProperties(
		long columnId, java.util.Properties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.updateColumnProperties(columnId, properties);
	}
}