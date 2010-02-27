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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetTagPropertyServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AssetTagPropertyService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyService
 * @generated
 */
public class AssetTagPropertyServiceUtil {
	public static com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTagProperty(tagId, key, value);
	}

	public static void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTagProperty(tagPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTagProperties(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTagPropertyValues(companyId, key);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTagProperty(tagPropertyId, key, value);
	}

	public static AssetTagPropertyService getService() {
		if (_service == null) {
			_service = (AssetTagPropertyService)PortalBeanLocatorUtil.locate(AssetTagPropertyService.class.getName());
		}

		return _service;
	}

	public void setService(AssetTagPropertyService service) {
		_service = service;
	}

	private static AssetTagPropertyService _service;
}