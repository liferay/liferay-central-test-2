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

package com.liferay.portlet.shorturl.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the short u r l remote service. This utility wraps {@link com.liferay.portlet.shorturl.service.impl.ShortURLServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShortURLService
 * @see com.liferay.portlet.shorturl.service.base.ShortURLServiceBaseImpl
 * @see com.liferay.portlet.shorturl.service.impl.ShortURLServiceImpl
 * @generated
 */
public class ShortURLServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.shorturl.service.impl.ShortURLServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.shorturl.model.ShortURL addShortURL(
		java.lang.String url)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShortURL(url);
	}

	public static ShortURLService getService() {
		if (_service == null) {
			_service = (ShortURLService)PortalBeanLocatorUtil.locate(ShortURLService.class.getName());

			ReferenceRegistry.registerReference(ShortURLServiceUtil.class,
				"_service");
			MethodCache.remove(ShortURLService.class);
		}

		return _service;
	}

	public void setService(ShortURLService service) {
		MethodCache.remove(ShortURLService.class);

		_service = service;

		ReferenceRegistry.registerReference(ShortURLServiceUtil.class,
			"_service");
		MethodCache.remove(ShortURLService.class);
	}

	private static ShortURLService _service;
}