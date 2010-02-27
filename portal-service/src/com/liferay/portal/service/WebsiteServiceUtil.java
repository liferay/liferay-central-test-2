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
 * <a href="WebsiteServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WebsiteService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebsiteService
 * @generated
 */
public class WebsiteServiceUtil {
	public static com.liferay.portal.model.Website addWebsite(
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addWebsite(className, classPK, url, typeId, primary);
	}

	public static void deleteWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWebsite(websiteId);
	}

	public static com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWebsite(websiteId);
	}

	public static java.util.List<com.liferay.portal.model.Website> getWebsites(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWebsites(className, classPK);
	}

	public static com.liferay.portal.model.Website updateWebsite(
		long websiteId, java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWebsite(websiteId, url, typeId, primary);
	}

	public static WebsiteService getService() {
		if (_service == null) {
			_service = (WebsiteService)PortalBeanLocatorUtil.locate(WebsiteService.class.getName());
		}

		return _service;
	}

	public void setService(WebsiteService service) {
		_service = service;
	}

	private static WebsiteService _service;
}