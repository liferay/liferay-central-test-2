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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DLContentFinderUtil {
	public static java.util.List<java.lang.String> findNamesByC_R_P(
		long companyId, long repositoryId, java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findNamesByC_R_P(companyId, repositoryId, path);
	}

	public static long findSizeByC_R_P(long companyId, long repositoryId,
		java.lang.String path)
		throws com.liferay.portal.NoSuchDLContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findSizeByC_R_P(companyId, repositoryId, path);
	}

	public static void updateByC_R_P_N_1(long companyId, long repositoryId,
		java.lang.String path, long newRepositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getFinder()
			.updateByC_R_P_N_1(companyId, repositoryId, path, newRepositoryId);
	}

	public static void updateByC_R_P_N_2(long companyId, long repositoryId,
		java.lang.String path, java.lang.String newPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		getFinder().updateByC_R_P_N_2(companyId, repositoryId, path, newPath);
	}

	public static DLContentFinder getFinder() {
		if (_finder == null) {
			_finder = (DLContentFinder)PortalBeanLocatorUtil.locate(DLContentFinder.class.getName());

			ReferenceRegistry.registerReference(DLContentFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLContentFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLContentFinderUtil.class, "_finder");
	}

	private static DLContentFinder _finder;
}