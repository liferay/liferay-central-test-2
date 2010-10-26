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
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the layout branch local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.service.LayoutBranchLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.portal.service.LayoutBranchLocalServiceUtil} to access the layout branch local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl
 * @see com.liferay.portal.service.LayoutBranchLocalServiceUtil
 */
public class LayoutBranchLocalServiceImpl
	extends LayoutBranchLocalServiceBaseImpl {

	public LayoutBranch addBranch(
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(serviceContext.getUserId());

		validate(serviceContext.getScopeGroupId(), name);

		long branchId = counterLocalService.increment();

		LayoutBranch branch =
			layoutBranchPersistence.create(branchId);

		Date now = new Date();

		branch.setGroupId(serviceContext.getScopeGroupId());
		branch.setCompanyId(user.getCompanyId());
		branch.setUserId(user.getUserId());
		branch.setUserName(user.getFullName());
		branch.setCreateDate(serviceContext.getCreateDate(now));
		branch.setModifiedDate(serviceContext.getModifiedDate(now));
		branch.setName(name);
		branch.setDescription(description);

		layoutBranchPersistence.update(branch, false);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), branch.getGroupId(), user.getUserId(),
			LayoutBranch.class.getName(), branch.getBranchId(), false, true, false);

		// When creating the initial branch we checkout each existing Layout,
		// creating a revision for each

		if (branch.getName().equals(LayoutBranchConstants.MASTER_BRANCH_NAME)) {
			for (Layout layout : layoutLocalService.getLayouts(
					branch.getGroupId(), false)) {

//				revisionLocalService.addRevision(
//					branch.getBranchId(), layout.getPlid(), layout.getGroupId(),
//					layout.getName(), layout.getTitle(),
//					layout.getDescription(), layout.getTypeSettings(),
//					layout.getIconImage(), layout.getIconImageId(),
//					layout.getThemeId(), layout.getColorSchemeId(),
//					layout.getWapThemeId(), layout.getWapColorSchemeId(),
//					layout.getCss(), true, serviceContext);
			}

			for (Layout layout : layoutLocalService.getLayouts(
					branch.getGroupId(), true)) {

//				revisionLocalService.addRevision(
//					branch.getBranchId(), layout.getPlid(), layout.getGroupId(),
//					layout.getName(), layout.getTitle(),
//					layout.getDescription(), layout.getTypeSettings(),
//					layout.getIconImage(), layout.getIconImageId(),
//					layout.getThemeId(), layout.getColorSchemeId(),
//					layout.getWapThemeId(), layout.getWapColorSchemeId(),
//					layout.getCss(), true, serviceContext);
			}
		}

		return branch;
	}

	public void deleteBranch(long branchId)
		throws PortalException, SystemException {

		LayoutBranch branch =
			layoutBranchPersistence.findByPrimaryKey(branchId);

		deleteBranch(branch);
	}

	public void deleteBranch(LayoutBranch branch)
		throws PortalException, SystemException {

		// Revisions

		//layoutRevisionLocalService.deleteRevisionsByBranch(branch.getBranchId());

		// Resources

		resourceLocalService.deleteResource(
			branch.getCompanyId(), LayoutBranch.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, branch.getBranchId());

		layoutBranchPersistence.remove(branch);
	}

	public void deleteBranches(long groupId)
		throws PortalException, SystemException {

		List<LayoutBranch> branches = layoutBranchPersistence.findByG(groupId);

		for (LayoutBranch branch : branches) {
			deleteBranch(branch);
		}
	}

	public LayoutBranch getMasterBranch(long groupId)
		throws PortalException, SystemException {

		return layoutBranchPersistence.findByG_N(
			groupId, LayoutBranchConstants.MASTER_BRANCH_NAME);
	}

	public List<LayoutBranch> getBranches(long groupId)
		throws PortalException, SystemException {

		return layoutBranchPersistence.findByG(groupId);
	}

	public LayoutBranch updateBranch(
			long branchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranch branch =
			layoutBranchPersistence.fetchByPrimaryKey(branchId);

		branch.setName(name);
		branch.setDescription(description);

		layoutBranchPersistence.update(branch, false);

		return branch;
	}

	protected void validate(long groupId, String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name) || name.length() < 4) {
			throw new LayoutBranchNameException("name-is-too-short");
		}

		if (name.length() > 100) {
			throw new LayoutBranchNameException(
				"name-is-too-long-try-less-than-100-characters");
		}

		try {
			layoutBranchPersistence.findByG_N(groupId, name);

			throw new LayoutBranchNameException("name-is-already-used");
		}
		catch (NoSuchLayoutBranchException nsbe) {
		}
	}

}