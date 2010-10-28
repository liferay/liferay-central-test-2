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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutBranchServiceBaseImpl;
import com.liferay.portal.service.permission.LayoutBranchPermissionUtil;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutBranchServiceImpl extends LayoutBranchServiceBaseImpl {

	public LayoutBranch addLayoutBranch(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranchPermissionUtil.check(
			getPermissionChecker(), groupId, 0, ActionKeys.ADD_LAYOUT_BRANCH);

		return layoutBranchLocalService.addLayoutBranch(
			getUserId(), groupId, name, description, serviceContext);
	}

	public void deleteLayoutBranch(long groupId, long layoutBranchId)
		throws PortalException, SystemException {

		LayoutBranchPermissionUtil.check(
			getPermissionChecker(), groupId, layoutBranchId, ActionKeys.DELETE);

		layoutBranchLocalService.deleteLayoutBranch(layoutBranchId);
	}

	public List<LayoutBranch> getLayoutBranches(long groupId)
		throws SystemException {

		return layoutBranchLocalService.getLayoutBranches(groupId);
	}

	public LayoutBranch updateLayoutBranch(
			long groupId, long layoutBranchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranchPermissionUtil.check(
			getPermissionChecker(), groupId, layoutBranchId, ActionKeys.UPDATE);

		return layoutBranchLocalService.updateLayoutBranch(
			layoutBranchId, name, description, serviceContext);
	}

}