/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.marketplace.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for App. This utility wraps
 * {@link com.liferay.marketplace.service.impl.AppServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Ryan Park
 * @see AppService
 * @see com.liferay.marketplace.service.base.AppServiceBaseImpl
 * @see com.liferay.marketplace.service.impl.AppServiceImpl
 * @generated
 */
@ProviderType
public class AppServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.marketplace.service.impl.AppServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.marketplace.model.App deleteApp(long appId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteApp(appId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static void installApp(long remoteAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().installApp(remoteAppId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void uninstallApp(long remoteAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().uninstallApp(remoteAppId);
	}

	public static com.liferay.marketplace.model.App updateApp(java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateApp(file);
	}

	public static AppService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(AppService service) {
	}

	private static ServiceTracker<AppService, AppService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AppServiceUtil.class);

		_serviceTracker = new ServiceTracker<AppService, AppService>(bundle.getBundleContext(),
				AppService.class, null);

		_serviceTracker.open();
	}
}