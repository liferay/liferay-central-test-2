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
 * <a href="ExpandoColumnServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoColumnService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.expando.service.ExpandoColumnServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoColumnService
 * @see com.liferay.portlet.expando.service.ExpandoColumnServiceFactory
 *
 */
public class ExpandoColumnServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.addColumn(tableId, name, type);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		expandoColumnService.deleteColumn(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumn(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumn(tableId, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumns(tableId);
	}

	public static int getColumnsCount(long tableId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.getColumnsCount(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoColumnService expandoColumnService = ExpandoColumnServiceFactory.getService();

		return expandoColumnService.updateColumn(columnId, name, type);
	}
}