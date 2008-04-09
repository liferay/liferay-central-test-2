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
 * <a href="ExpandoRowService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.expando.service.impl.ExpandoRowServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoRowServiceFactory
 * @see com.liferay.portlet.expando.service.ExpandoRowServiceUtil
 *
 */
public interface ExpandoRowService {
	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteRow(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		java.lang.String className, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long classNameId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public int getDefaultTableRowsCount(java.lang.String className)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public int getDefaultTableRowsCount(long classNameId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int begin,
		int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long classNameId, java.lang.String tableName, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public int getRowsCount(long tableId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public int getRowsCount(java.lang.String className,
		java.lang.String tableName)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;

	public int getRowsCount(long classNameId, java.lang.String tableName)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException;
}