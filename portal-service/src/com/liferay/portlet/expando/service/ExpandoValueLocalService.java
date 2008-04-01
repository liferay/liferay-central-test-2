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
 * <a href="ExpandoValueLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl</code>.
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
 * @see com.liferay.portlet.expando.service.ExpandoValueLocalServiceFactory
 * @see com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil
 *
 */
public interface ExpandoValueLocalService {
	public com.liferay.portlet.expando.model.ExpandoValue addExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException;

	public void deleteExpandoValue(long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence getExpandoColumnPersistence();

	public void setExpandoColumnPersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence);

	public com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence getExpandoRowPersistence();

	public void setExpandoRowPersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence);

	public com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence getExpandoTablePersistence();

	public void setExpandoTablePersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence);

	public com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence getExpandoValuePersistence();

	public void setExpandoValuePersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence);

	public void afterPropertiesSet();

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long columnId, long rowId, java.lang.String data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteColumnValues(long columnId)
		throws com.liferay.portal.SystemException;

	public void deleteRowValues(long rowId)
		throws com.liferay.portal.SystemException;

	public void deleteTableValues(long tableId)
		throws com.liferay.portal.SystemException;

	public void deleteValue(long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteValues(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteValues(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long columnId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getColumnValuesCount(long columnId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getRowValuesCount(long rowId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getTableValues(
		long tableId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getTableValuesCount(long tableId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue getValue(long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long columnId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}