/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PortalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PortalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortalService
 * @generated
 */
public class PortalServiceUtil {
	public static java.lang.String getAutoDeployDirectory()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAutoDeployDirectory();
	}

	public static int getBuildNumber() {
		return getService().getBuildNumber();
	}

	public static void test() {
		getService().test();
	}

	public static void testCounterRollback()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().testCounterRollback();
	}

	public static PortalService getService() {
		if (_service == null) {
			_service = (PortalService)PortalBeanLocatorUtil.locate(PortalService.class.getName());
		}

		return _service;
	}

	public void setService(PortalService service) {
		_service = service;
	}

	private static PortalService _service;
}