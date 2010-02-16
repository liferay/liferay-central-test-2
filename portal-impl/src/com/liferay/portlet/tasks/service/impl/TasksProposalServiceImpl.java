/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * <a href="TasksProposalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
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