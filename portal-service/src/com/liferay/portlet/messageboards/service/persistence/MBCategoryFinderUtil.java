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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MBCategoryFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBCategoryFinderUtil {
	public static int countByS_G_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByS_G_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByS_G_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByS_G_U(groupId, userId, start, end);
	}

	public static MBCategoryFinder getFinder() {
		if (_finder == null) {
			_finder = (MBCategoryFinder)PortalBeanLocatorUtil.locate(MBCategoryFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(MBCategoryFinder finder) {
		_finder = finder;
	}

	private static MBCategoryFinder _finder;
}