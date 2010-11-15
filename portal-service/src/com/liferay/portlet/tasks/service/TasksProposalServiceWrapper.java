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

package com.liferay.portlet.tasks.service;

/**
 * <p>
 * This class is a wrapper for {@link TasksProposalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalService
 * @generated
 */
public class TasksProposalServiceWrapper implements TasksProposalService {
	public TasksProposalServiceWrapper(
		TasksProposalService tasksProposalService) {
		_tasksProposalService = tasksProposalService;
	}

	public com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalService.addProposal(groupId, className, classPK,
			name, description, reviewUserId, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalService.addProposal(groupId, className, classPK,
			name, description, reviewUserId, communityPermissions,
			guestPermissions);
	}

	public void deleteProposal(long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalService.deleteProposal(proposalId);
	}

	public com.liferay.portlet.tasks.model.TasksProposal updateProposal(
		long proposalId, java.lang.String description, int dueDateMonth,
		int dueDateDay, int dueDateYear, int dueDateHour, int dueDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalService.updateProposal(proposalId, description,
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour, dueDateMinute);
	}

	public TasksProposalService getWrappedTasksProposalService() {
		return _tasksProposalService;
	}

	public void setWrappedTasksProposalService(
		TasksProposalService tasksProposalService) {
		_tasksProposalService = tasksProposalService;
	}

	private TasksProposalService _tasksProposalService;
}