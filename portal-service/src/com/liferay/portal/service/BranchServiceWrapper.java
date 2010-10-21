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

/**
 * <p>
 * This class is a wrapper for {@link BranchService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BranchService
 * @generated
 */
public class BranchServiceWrapper implements BranchService {
	public BranchServiceWrapper(BranchService branchService) {
		_branchService = branchService;
	}

	public com.liferay.portal.model.Branch addBranch(long groupId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchService.addBranch(groupId, name, description,
			serviceContext);
	}

	public void deleteBranch(long groupId, long branchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_branchService.deleteBranch(groupId, branchId);
	}

	public java.util.List<com.liferay.portal.model.Branch> getBranches(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchService.getBranches(groupId);
	}

	public com.liferay.portal.model.Branch updateBranch(long groupId,
		long branchId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchService.updateBranch(groupId, branchId, name,
			description, serviceContext);
	}

	public BranchService getWrappedBranchService() {
		return _branchService;
	}

	private BranchService _branchService;
}