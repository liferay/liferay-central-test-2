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
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the revision remote service. This utility wraps {@link com.liferay.portal.service.impl.RevisionServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RevisionServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RevisionService
 * @see com.liferay.portal.service.base.RevisionServiceBaseImpl
 * @see com.liferay.portal.service.impl.RevisionServiceImpl
 * @generated
 */
public class RevisionServiceUtil {
	public static RevisionService getService() {
		if (_service == null) {
			_service = (RevisionService)PortalBeanLocatorUtil.locate(RevisionService.class.getName());

			ReferenceRegistry.registerReference(RevisionServiceUtil.class,
				"_service");
			MethodCache.remove(RevisionService.class);
		}

		return _service;
	}

	public void setService(RevisionService service) {
		MethodCache.remove(RevisionService.class);

		_service = service;

		ReferenceRegistry.registerReference(RevisionServiceUtil.class,
			"_service");
		MethodCache.remove(RevisionService.class);
	}

	private static RevisionService _service;
}