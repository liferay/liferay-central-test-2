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
 * <a href="ExpandoTableRowServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ExpandoTableRowService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ExpandoTableRowServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ExpandoTableRowService
 * @see com.liferay.portal.service.ExpandoTableRowServiceFactory
 *
 */
public class ExpandoTableRowServiceUtil {
	public static com.liferay.portal.model.ExpandoTableRow addRow(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		return expandoTableRowService.addRow(tableId);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		expandoTableRowService.deleteRow(rowId);
	}

	public static void deleteRows(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		expandoTableRowService.deleteRows(tableId);
	}

	public static void deleteRows(long tableId, long[] rowIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		expandoTableRowService.deleteRows(tableId, rowIds);
	}

	public static com.liferay.portal.model.ExpandoTableRow getRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		return expandoTableRowService.getRow(rowId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> getRows(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		return expandoTableRowService.getRows(tableId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> getRows(
		long tableId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		return expandoTableRowService.getRows(tableId, begin, end);
	}

	public static int getRowsCount(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		return expandoTableRowService.getRowsCount(tableId);
	}

	public static com.liferay.portal.model.ExpandoTableRow setRow(
		long tableId, long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		ExpandoTableRowService expandoTableRowService = ExpandoTableRowServiceFactory.getService();

		return expandoTableRowService.setRow(tableId, rowId);
	}
}