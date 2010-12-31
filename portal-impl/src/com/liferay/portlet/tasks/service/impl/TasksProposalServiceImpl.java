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

package com.liferay.portlet.tasks.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.base.TasksProposalServiceBaseImpl;
import com.liferay.portlet.tasks.service.permission.TasksProposalPermission;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class TasksProposalServiceImpl extends TasksProposalServiceBaseImpl {

	public TasksProposal addProposal(
			long groupId, String className, String classPK, String name,
			String description, long reviewUserId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException{

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS) &&
			(name.equals(Layout.class.getName()) &&
			 !LayoutPermissionUtil.contains(
				permissionChecker, GetterUtil.getLong(classPK),
				ActionKeys.UPDATE))) {

			throw new PrincipalException();
		}

		return tasksProposalLocalService.addProposal(
			getUserId(), groupId, className, classPK, name, description,
			reviewUserId, addCommunityPermissions, addGuestPermissions);
	}

	public TasksProposal addProposal(
			long groupId, String className, String classPK, String name,
			String description, long reviewUserId,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException{

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS) &&
			(name.equals(Layout.class.getName()) &&
			 !LayoutPermissionUtil.contains(
				permissionChecker, GetterUtil.getLong(classPK),
				ActionKeys.UPDATE))) {

			throw new PrincipalException();
		}

		return tasksProposalLocalService.addProposal(
			getUserId(), groupId, className, classPK, name, description,
			reviewUserId, communityPermissions, guestPermissions);
	}

	public void deleteProposal(long proposalId)
		throws PortalException, SystemException{

		PermissionChecker permissionChecker = getPermissionChecker();

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		long groupId = proposal.getGroupId();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_REVIEWER) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING) &&
			!TasksProposalPermission.contains(
				permissionChecker, proposalId, ActionKeys.DELETE)) {

			throw new PrincipalException();
		}

		tasksProposalLocalService.deleteProposal(proposalId);
	}

	public TasksProposal updateProposal(
			long proposalId, String description, int dueDateMonth,
			int dueDateDay, int dueDateYear, int dueDateHour, int dueDateMinute)
		throws PortalException, SystemException{

		PermissionChecker permissionChecker = getPermissionChecker();

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		long groupId = proposal.getGroupId();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_REVIEWER) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING) &&
			!TasksProposalPermission.contains(
				permissionChecker, proposalId, ActionKeys.UPDATE)) {

			throw new PrincipalException();
		}

		return tasksProposalLocalService.updateProposal(
			getUserId(), proposalId, description, dueDateMonth, dueDateDay,
			dueDateYear, dueDateHour, dueDateMinute);
	}

}