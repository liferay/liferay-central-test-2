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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the repository remote service. This utility wraps {@link com.liferay.portal.service.impl.RepositoryServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryService
 * @see com.liferay.portal.service.base.RepositoryServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryServiceImpl
 * @generated
 */
public class RepositoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static long addRepository(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId, int type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRepository(groupId, parentFolderId, name, description,
			portletId, type, typeSettingsProperties);
	}

	public static void checkRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().checkRepository(repositoryId);
	}

	public static void deleteRepositories(long groupId, int purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRepositories(groupId, purge);
	}

	public static void deleteRepository(long repositoryId, boolean purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRepository(repositoryId, purge);
	}

	public static com.liferay.portal.model.Repository getRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepository(repositoryId);
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTypeSettingsProperties(repositoryId);
	}

	public static void updateRepository(long repositoryId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateRepository(repositoryId, name, description,
			typeSettingsProperties);
	}

	public static RepositoryService getService() {
		if (_service == null) {
			_service = (RepositoryService)PortalBeanLocatorUtil.locate(RepositoryService.class.getName());

			ReferenceRegistry.registerReference(RepositoryServiceUtil.class,
				"_service");
			MethodCache.remove(RepositoryService.class);
		}

		return _service;
	}

	public void setService(RepositoryService service) {
		MethodCache.remove(RepositoryService.class);

		_service = service;

		ReferenceRegistry.registerReference(RepositoryServiceUtil.class,
			"_service");
		MethodCache.remove(RepositoryService.class);
	}

	private static RepositoryService _service;
}