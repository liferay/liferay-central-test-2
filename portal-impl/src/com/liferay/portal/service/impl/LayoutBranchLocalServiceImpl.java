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

import com.liferay.portal.LayoutBranchNameException;
import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutBranchConstants;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutBranchLocalServiceImpl
	extends LayoutBranchLocalServiceBaseImpl {

	public LayoutBranch addLayoutBranch(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout branch

		User user = userLocalService.getUserById(userId);
		Date now = new Date();

		validate(groupId, name);

		long layoutBranchId = counterLocalService.increment();

		LayoutBranch layoutBranch = layoutBranchPersistence.create(
			layoutBranchId);

		layoutBranch.setGroupId(groupId);
		layoutBranch.setCompanyId(user.getCompanyId());
		layoutBranch.setUserId(user.getUserId());
		layoutBranch.setUserName(user.getFullName());
		layoutBranch.setCreateDate(serviceContext.getCreateDate(now));
		layoutBranch.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutBranch.setName(name);
		layoutBranch.setDescription(description);

		layoutBranchPersistence.update(layoutBranch, false);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), layoutBranch.getGroupId(), user.getUserId(),
			LayoutBranch.class.getName(), layoutBranch.getLayoutBranchId(),
			false, true, false);

		// Revisions

		if (layoutBranch.isMaster()) {
			List<Layout> layouts = layoutPersistence.findByGroupId(
				layoutBranch.getGroupId());

			for (Layout layout : layouts) {
				layoutRevisionLocalService.addLayoutRevision(
					userId, layoutBranchId,
					LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
					true, layout.getPlid(), layout.getName(), layout.getTitle(),
					layout.getDescription(), layout.getTypeSettings(),
					layout.isIconImage(), layout.getIconImageId(),
					layout.getThemeId(), layout.getColorSchemeId(),
					layout.getWapThemeId(), layout.getWapColorSchemeId(),
					layout.getCss(), serviceContext);
			}
		}

		return layoutBranch;
	}

	public void deleteLayoutBranch(LayoutBranch layoutBranch)
		throws PortalException, SystemException {

		// Layout branch

		layoutBranchPersistence.remove(layoutBranch);

		// Resources

		resourceLocalService.deleteResource(
			layoutBranch.getCompanyId(), LayoutBranch.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutBranch.getLayoutBranchId());

		// Revisions

		layoutRevisionLocalService.deleteLayoutBranchLayoutRevisions(
			layoutBranch.getLayoutBranchId());
	}

	public void deleteLayoutBranch(long layoutBranchId)
		throws PortalException, SystemException {

		LayoutBranch layoutBranch = layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);

		deleteLayoutBranch(layoutBranch);
	}

	public void deleteLayoutBranches(long groupId)
		throws PortalException, SystemException {

		List<LayoutBranch> layoutBranches =
			layoutBranchPersistence.findByGroupId(groupId);

		for (LayoutBranch layoutBranch : layoutBranches) {
			deleteLayoutBranch(layoutBranch);
		}
	}

	public List<LayoutBranch> getLayoutBranches(long groupId)
		throws SystemException {

		return layoutBranchPersistence.findByGroupId(groupId);
	}

	public LayoutBranch getMasterLayoutBranch(long groupId)
		throws PortalException, SystemException {

		return layoutBranchPersistence.findByG_N(
			groupId, LayoutBranchConstants.MASTER_BRANCH_NAME);
	}

	public LayoutBranch updateLayoutBranch(
			long layoutBranchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranch layoutBranch = layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);

		layoutBranch.setName(name);
		layoutBranch.setDescription(description);

		layoutBranchPersistence.update(layoutBranch, false);

		return layoutBranch;
	}

	protected void validate(long groupId, String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name) || (name.length() < 4)) {
			throw new LayoutBranchNameException(
				LayoutBranchNameException.TOO_SHORT);
		}

		if (name.length() > 100) {
			throw new LayoutBranchNameException(
				LayoutBranchNameException.TOO_LONG);
		}

		try {
			layoutBranchPersistence.findByG_N(groupId, name);

			throw new LayoutBranchNameException(
				LayoutBranchNameException.DUPLICATE);
		}
		catch (NoSuchLayoutBranchException nsbe) {
		}
	}

}