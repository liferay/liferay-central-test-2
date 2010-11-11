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

import com.liferay.portal.LayoutSetBranchNameException;
import com.liferay.portal.NoSuchLayoutSetBranchException;
import com.liferay.portal.RequiredLayoutSetBranchException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetBranchConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutSetBranchLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutSetBranchLocalServiceImpl
	extends LayoutSetBranchLocalServiceBaseImpl {

	public LayoutSetBranch addLayoutSetBranch(
			long userId, long groupId, boolean privateLayout, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout branch

		User user = userLocalService.getUserById(userId);
		Date now = new Date();

		validate(groupId, privateLayout, name);

		long layoutSetBranchId = counterLocalService.increment();

		LayoutSetBranch layoutSetBranch = layoutSetBranchPersistence.create(
			layoutSetBranchId);

		layoutSetBranch.setGroupId(groupId);
		layoutSetBranch.setCompanyId(user.getCompanyId());
		layoutSetBranch.setUserId(user.getUserId());
		layoutSetBranch.setUserName(user.getFullName());
		layoutSetBranch.setCreateDate(serviceContext.getCreateDate(now));
		layoutSetBranch.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutSetBranch.setPrivateLayout(privateLayout);
		layoutSetBranch.setName(name);
		layoutSetBranch.setDescription(description);

		layoutSetBranchPersistence.update(layoutSetBranch, false);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), layoutSetBranch.getGroupId(), user.getUserId(),
			LayoutSetBranch.class.getName(),
			layoutSetBranch.getLayoutSetBranchId(), false, true, false);

		// Revisions

		if (layoutSetBranch.isMaster()) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				layoutSetBranch.getGroupId(),
				layoutSetBranch.getPrivateLayout());

			for (Layout layout : layouts) {
				layoutRevisionLocalService.addLayoutRevision(
					userId, layoutSetBranchId,
					LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
					true, layout.getPlid(), layout.getName(), layout.getTitle(),
					layout.getDescription(), layout.getTypeSettings(),
					layout.isIconImage(), layout.getIconImageId(),
					layout.getThemeId(), layout.getColorSchemeId(),
					layout.getWapThemeId(), layout.getWapColorSchemeId(),
					layout.getCss(), serviceContext);
			}
		}

		return layoutSetBranch;
	}

	public void deleteLayoutSetBranch(LayoutSetBranch layoutSetBranch)
		throws PortalException, SystemException {

		deleteLayoutSetBranch(layoutSetBranch, false);
	}

	public void deleteLayoutSetBranch(
			LayoutSetBranch layoutSetBranch, boolean includeMaster)
		throws PortalException, SystemException {

		// Layout branch

		if (!includeMaster && layoutSetBranch.isMaster()) {
			throw new RequiredLayoutSetBranchException();
		}

		layoutSetBranchPersistence.remove(layoutSetBranch);

		// Resources

		resourceLocalService.deleteResource(
			layoutSetBranch.getCompanyId(), LayoutSetBranch.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutSetBranch.getLayoutSetBranchId());

		// Revisions

		layoutRevisionLocalService.deleteLayoutSetBranchLayoutRevisions(
			layoutSetBranch.getLayoutSetBranchId());
	}

	public void deleteLayoutSetBranch(long layoutSetBranchId)
		throws PortalException, SystemException {

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		deleteLayoutSetBranch(layoutSetBranch);
	}

	public void deleteLayoutSetBranches(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		deleteLayoutSetBranches(groupId, privateLayout, false);
	}

	public void deleteLayoutSetBranches(
			long groupId, boolean privateLayout, boolean includeMaster)
		throws PortalException, SystemException {

		List<LayoutSetBranch> layoutSetBranches =
			layoutSetBranchPersistence.findByG_P(groupId, privateLayout);

		for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
			deleteLayoutSetBranch(layoutSetBranch, includeMaster);
		}
	}

	public LayoutSetBranch getLayoutSetBranch(
			long groupId, boolean privateLayout, String name)
		throws PortalException, SystemException {

		return layoutSetBranchPersistence.findByG_P_N(
			groupId, privateLayout, name);
	}

	public List<LayoutSetBranch> getLayoutSetBranches(
			long groupId, boolean privateLayout)
		throws SystemException {

		return layoutSetBranchPersistence.findByG_P(groupId, privateLayout);
	}

	public LayoutSetBranch getMasterLayoutSetBranch(
			long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		return layoutSetBranchPersistence.findByG_P_N(
			groupId, privateLayout,
			LayoutSetBranchConstants.MASTER_BRANCH_NAME);
	}

	public LayoutSetBranch updateLayoutSetBranch(
			long layoutSetBranchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		layoutSetBranch.setName(name);
		layoutSetBranch.setDescription(description);

		layoutSetBranchPersistence.update(layoutSetBranch, false);

		return layoutSetBranch;
	}

	protected void validate(long groupId, boolean privateLayout, String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name) || (name.length() < 4)) {
			throw new LayoutSetBranchNameException(
				LayoutSetBranchNameException.TOO_SHORT);
		}

		if (name.length() > 100) {
			throw new LayoutSetBranchNameException(
				LayoutSetBranchNameException.TOO_LONG);
		}

		try {
			layoutSetBranchPersistence.findByG_P_N(
				groupId, privateLayout, name);

			throw new LayoutSetBranchNameException(
				LayoutSetBranchNameException.DUPLICATE);
		}
		catch (NoSuchLayoutSetBranchException nsbe) {
		}
	}

}