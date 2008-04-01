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
 * <a href="ExpandoRowLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoRowLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoRowLocalService
 * @see com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory
 *
 */
public class ExpandoRowLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.addExpandoRow(expandoRow);
	}

	public static void deleteExpandoRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteExpandoRow(rowId);
	}

	public static void deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.updateExpandoRow(expandoRow);
	}

	public static com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence getExpandoColumnPersistence() {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getExpandoColumnPersistence();
	}

	public static void setExpandoColumnPersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence) {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.setExpandoColumnPersistence(expandoColumnPersistence);
	}

	public static com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence getExpandoRowPersistence() {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getExpandoRowPersistence();
	}

	public static void setExpandoRowPersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence) {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.setExpandoRowPersistence(expandoRowPersistence);
	}

	public static com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence getExpandoTablePersistence() {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getExpandoTablePersistence();
	}

	public static void setExpandoTablePersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence) {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.setExpandoTablePersistence(expandoTablePersistence);
	}

	public static com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence getExpandoValuePersistence() {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getExpandoValuePersistence();
	}

	public static void setExpandoValuePersistence(
		com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence) {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.setExpandoValuePersistence(expandoValuePersistence);
	}

	public static void afterPropertiesSet() {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.expando.model.ExpandoRow addRow(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.addRow(tableId);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteRow(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int begin, int end)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRows(tableId, begin, end);
	}

	public static int getRowsCount(long tableId)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRowsCount(tableId);
	}
}