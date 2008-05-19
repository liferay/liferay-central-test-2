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
 * <a href="ExpandoRowLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.expando.service.impl.ExpandoRowLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory
 * @see com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil
 *
 */
public interface ExpandoRowLocalService {
	public com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException;

	public void deleteExpandoRow(long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId,
		long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteRow(long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteRow(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteRow(long classNameId, java.lang.String tableName,
		long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException;

	public int getDefaultTableRowsCount(java.lang.String className)
		throws com.liferay.portal.SystemException;

	public int getDefaultTableRowsCount(long classNameId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long tableId,
		long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.expando.model.ExpandoRow getRow(
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.expando.model.ExpandoRow getRow(
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long classNameId, java.lang.String tableName, int start, int end)
		throws com.liferay.portal.SystemException;

	public int getRowsCount(long tableId)
		throws com.liferay.portal.SystemException;

	public int getRowsCount(java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException;

	public int getRowsCount(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException;
}