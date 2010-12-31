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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class IGImageFinderUtil {
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByAnyImageId(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().fetchByAnyImageId(imageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByAnyImageId(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getFinder().findByAnyImageId(imageId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByNoAssets()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByNoAssets();
	}

	public static IGImageFinder getFinder() {
		if (_finder == null) {
			_finder = (IGImageFinder)PortalBeanLocatorUtil.locate(IGImageFinder.class.getName());

			ReferenceRegistry.registerReference(IGImageFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(IGImageFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(IGImageFinderUtil.class, "_finder");
	}

	private static IGImageFinder _finder;
}