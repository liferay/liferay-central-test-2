/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ServiceComponentLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ServiceComponentLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentLocalService
 * @generated
 */
public class ServiceComponentLocalServiceUtil {
	public static com.liferay.portal.model.ServiceComponent addServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		return getService().addServiceComponent(serviceComponent);
	}

	public static com.liferay.portal.model.ServiceComponent createServiceComponent(
		long serviceComponentId) {
		return getService().createServiceComponent(serviceComponentId);
	}

	public static void deleteServiceComponent(long serviceComponentId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteServiceComponent(serviceComponentId);
	}

	public static void deleteServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		getService().deleteServiceComponent(serviceComponent);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.ServiceComponent getServiceComponent(
		long serviceComponentId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getServiceComponent(serviceComponentId);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> getServiceComponents(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getServiceComponents(start, end);
	}

	public static int getServiceComponentsCount()
		throws com.liferay.portal.SystemException {
		return getService().getServiceComponentsCount();
	}

	public static com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		return getService().updateServiceComponent(serviceComponent);
	}

	public static com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateServiceComponent(serviceComponent, merge);
	}

	public static void destroyServiceComponent(
		javax.servlet.ServletContext servletContext,
		java.lang.ClassLoader classLoader)
		throws com.liferay.portal.SystemException {
		getService().destroyServiceComponent(servletContext, classLoader);
	}

	public static com.liferay.portal.model.ServiceComponent initServiceComponent(
		javax.servlet.ServletContext servletContext,
		java.lang.ClassLoader classLoader, java.lang.String buildNamespace,
		long buildNumber, long buildDate, boolean buildAutoUpgrade)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .initServiceComponent(servletContext, classLoader,
			buildNamespace, buildNumber, buildDate, buildAutoUpgrade);
	}

	public static void upgradeDB(java.lang.ClassLoader classLoader,
		java.lang.String buildNamespace, long buildNumber,
		boolean buildAutoUpgrade,
		com.liferay.portal.model.ServiceComponent previousServiceComponent,
		java.lang.String tablesSQL, java.lang.String sequencesSQL,
		java.lang.String indexesSQL) throws java.lang.Exception {
		getService()
			.upgradeDB(classLoader, buildNamespace, buildNumber,
			buildAutoUpgrade, previousServiceComponent, tablesSQL,
			sequencesSQL, indexesSQL);
	}

	public static void verifyDB()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().verifyDB();
	}

	public static ServiceComponentLocalService getService() {
		if (_service == null) {
			_service = (ServiceComponentLocalService)PortalBeanLocatorUtil.locate(ServiceComponentLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ServiceComponentLocalService service) {
		_service = service;
	}

	private static ServiceComponentLocalService _service;
}