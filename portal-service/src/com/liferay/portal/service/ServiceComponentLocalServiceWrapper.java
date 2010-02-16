/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="ServiceComponentLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ServiceComponentLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentLocalService
 * @generated
 */
public class ServiceComponentLocalServiceWrapper
	implements ServiceComponentLocalService {
	public ServiceComponentLocalServiceWrapper(
		ServiceComponentLocalService serviceComponentLocalService) {
		_serviceComponentLocalService = serviceComponentLocalService;
	}

	public com.liferay.portal.model.ServiceComponent addServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.addServiceComponent(serviceComponent);
	}

	public com.liferay.portal.model.ServiceComponent createServiceComponent(
		long serviceComponentId) {
		return _serviceComponentLocalService.createServiceComponent(serviceComponentId);
	}

	public void deleteServiceComponent(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_serviceComponentLocalService.deleteServiceComponent(serviceComponentId);
	}

	public void deleteServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.kernel.exception.SystemException {
		_serviceComponentLocalService.deleteServiceComponent(serviceComponent);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portal.model.ServiceComponent getServiceComponent(
		long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.getServiceComponent(serviceComponentId);
	}

	public java.util.List<com.liferay.portal.model.ServiceComponent> getServiceComponents(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.getServiceComponents(start, end);
	}

	public int getServiceComponentsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.getServiceComponentsCount();
	}

	public com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.updateServiceComponent(serviceComponent);
	}

	public com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.updateServiceComponent(serviceComponent,
			merge);
	}

	public void destroyServiceComponent(
		javax.servlet.ServletContext servletContext,
		java.lang.ClassLoader classLoader)
		throws com.liferay.portal.kernel.exception.SystemException {
		_serviceComponentLocalService.destroyServiceComponent(servletContext,
			classLoader);
	}

	public com.liferay.portal.model.ServiceComponent initServiceComponent(
		javax.servlet.ServletContext servletContext,
		java.lang.ClassLoader classLoader, java.lang.String buildNamespace,
		long buildNumber, long buildDate, boolean buildAutoUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _serviceComponentLocalService.initServiceComponent(servletContext,
			classLoader, buildNamespace, buildNumber, buildDate,
			buildAutoUpgrade);
	}

	public void upgradeDB(java.lang.ClassLoader classLoader,
		java.lang.String buildNamespace, long buildNumber,
		boolean buildAutoUpgrade,
		com.liferay.portal.model.ServiceComponent previousServiceComponent,
		java.lang.String tablesSQL, java.lang.String sequencesSQL,
		java.lang.String indexesSQL) throws java.lang.Exception {
		_serviceComponentLocalService.upgradeDB(classLoader, buildNamespace,
			buildNumber, buildAutoUpgrade, previousServiceComponent, tablesSQL,
			sequencesSQL, indexesSQL);
	}

	public void verifyDB()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_serviceComponentLocalService.verifyDB();
	}

	public ServiceComponentLocalService getWrappedServiceComponentLocalService() {
		return _serviceComponentLocalService;
	}

	private ServiceComponentLocalService _serviceComponentLocalService;
}