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
 * The utility for the quartz local service. This utility wraps {@link com.liferay.portal.service.impl.QuartzLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.QuartzLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see QuartzLocalService
 * @see com.liferay.portal.service.base.QuartzLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.QuartzLocalServiceImpl
 * @generated
 */
public class QuartzLocalServiceUtil {
	public static void checkQuartzTables()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().checkQuartzTables();
	}

	public static QuartzLocalService getService() {
		if (_service == null) {
			_service = (QuartzLocalService)PortalBeanLocatorUtil.locate(QuartzLocalService.class.getName());
		}

		return _service;
	}

	public void setService(QuartzLocalService service) {
		_service = service;
	}

	private static QuartzLocalService _service;
}