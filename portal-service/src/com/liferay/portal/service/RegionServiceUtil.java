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
 * <a href="RegionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link RegionService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RegionService
 * @generated
 */
public class RegionServiceUtil {
	public static com.liferay.portal.model.Region addRegion(long countryId,
		java.lang.String regionCode, java.lang.String name, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addRegion(countryId, regionCode, name, active);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRegions();
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRegions(countryId);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRegions(active);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRegions(countryId, active);
	}

	public static com.liferay.portal.model.Region getRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRegion(regionId);
	}

	public static RegionService getService() {
		if (_service == null) {
			_service = (RegionService)PortalBeanLocatorUtil.locate(RegionService.class.getName());
		}

		return _service;
	}

	public void setService(RegionService service) {
		_service = service;
	}

	private static RegionService _service;
}