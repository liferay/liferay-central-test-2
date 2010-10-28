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
 * This class is a wrapper for {@link LayoutBranchService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutBranchService
 * @generated
 */
public class LayoutBranchServiceWrapper implements LayoutBranchService {
	public LayoutBranchServiceWrapper(LayoutBranchService layoutBranchService) {
		_layoutBranchService = layoutBranchService;
	}

	public com.liferay.portal.model.LayoutBranch addLayoutBranch(long groupId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutBranchService.addLayoutBranch(groupId, name, description,
			serviceContext);
	}

	public void deleteLayoutBranch(long groupId, long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutBranchService.deleteLayoutBranch(groupId, layoutBranchId);
	}

	public java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutBranches(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutBranchService.getLayoutBranches(groupId);
	}

	public com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		long groupId, long layoutBranchId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutBranchService.updateLayoutBranch(groupId, layoutBranchId,
			name, description, serviceContext);
	}

	public LayoutBranchService getWrappedLayoutBranchService() {
		return _layoutBranchService;
	}

	private LayoutBranchService _layoutBranchService;
}