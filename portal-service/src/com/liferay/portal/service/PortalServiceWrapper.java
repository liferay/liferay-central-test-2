/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link PortalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortalService
 * @generated
 */
public class PortalServiceWrapper implements PortalService {
	public PortalServiceWrapper(PortalService portalService) {
		_portalService = portalService;
	}

	public java.lang.String getAutoDeployDirectory()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portalService.getAutoDeployDirectory();
	}

	public int getBuildNumber() {
		return _portalService.getBuildNumber();
	}

	public void test() {
		_portalService.test();
	}

	public void testCounterRollback()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testCounterRollback();
	}

	public void testClassName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testClassName(name);
	}

	public void testClassNameRollback(java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testClassNameRollback(className);
	}

	public PortalService getWrappedPortalService() {
		return _portalService;
	}

	public void setWrappedPortalService(PortalService portalService) {
		_portalService = portalService;
	}

	private PortalService _portalService;
}