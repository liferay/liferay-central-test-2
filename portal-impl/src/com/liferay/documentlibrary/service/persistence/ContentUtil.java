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

package com.liferay.documentlibrary.service.persistence;

import com.liferay.documentlibrary.model.Content;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Shuyang Zhou
 */
public class ContentUtil {

	public static int countByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		return getPersistence().countByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public static Content findByC_R_P(
			long companyId, long repositoryId, String path)
		throws SystemException {

		return getPersistence().findByC_P_N(companyId, repositoryId, path);
	}

	public static Content findByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		return getPersistence().findByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public static ContentPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ContentPersistence)PortalBeanLocatorUtil.locate(
				ContentPersistence.class.getName());

			ReferenceRegistry.registerReference(
				ContentUtil.class, "_persistence");
		}

		return _persistence;
	}

	public static boolean removeByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		return getPersistence().removeByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public static void update(Content content) throws SystemException {
		getPersistence().update(content);
	}

	public void setPersistence(ContentPersistence persistence) {
		_persistence = persistence;
	}

	private static ContentPersistence _persistence;

}