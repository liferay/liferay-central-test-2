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
 * The utility for the branch remote service. This utility wraps {@link com.liferay.portal.service.impl.BranchServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.BranchServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BranchService
 * @see com.liferay.portal.service.base.BranchServiceBaseImpl
 * @see com.liferay.portal.service.impl.BranchServiceImpl
 * @generated
 */
public class BranchServiceUtil {
	public static com.liferay.portal.model.Branch addBranch(long groupId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addBranch(groupId, name, description, serviceContext);
	}

	public static void deleteBranch(long groupId, long branchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteBranch(groupId, branchId);
	}

	public static java.util.List<com.liferay.portal.model.Branch> getBranches(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBranches(groupId);
	}

	public static com.liferay.portal.model.Branch updateBranch(long groupId,
		long branchId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateBranch(groupId, branchId, name, description,
			serviceContext);
	}

	public static BranchService getService() {
		if (_service == null) {
			_service = (BranchService)PortalBeanLocatorUtil.locate(BranchService.class.getName());

			ReferenceRegistry.registerReference(BranchServiceUtil.class,
				"_service");
			MethodCache.remove(BranchService.class);
		}

		return _service;
	}

	public void setService(BranchService service) {
		MethodCache.remove(BranchService.class);

		_service = service;

		ReferenceRegistry.registerReference(BranchServiceUtil.class, "_service");
		MethodCache.remove(BranchService.class);
	}

	private static BranchService _service;
}