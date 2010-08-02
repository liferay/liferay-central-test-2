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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * The utility for the d l file version remote service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLFileVersionServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileVersionServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileVersionServiceImpl
 * @generated
 */
public class DLFileVersionServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateDescription(
		long fileVersionId, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDescription(fileVersionId, description);
	}

	public static DLFileVersionService getService() {
		if (_service == null) {
			_service = (DLFileVersionService)PortalBeanLocatorUtil.locate(DLFileVersionService.class.getName());
		}

		return _service;
	}

	public void setService(DLFileVersionService service) {
		_service = service;
	}

	private static DLFileVersionService _service;
}