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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.ProposalDueDateException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.base.TasksProposalLocalServiceBaseImpl;
import com.liferay.portlet.tasks.social.TasksActivityKeys;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class TasksProposalLocalServiceImpl
	extends TasksProposalLocalServiceBaseImpl {

	public TasksProposal addProposal(
			long userId, long groupId, String className, String classPK,
			String name, String description, long reviewUserId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addProposal(
			userId, groupId, className, classPK, name, description,
			reviewUserId, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public TasksProposal addProposal(
			long userId, long groupId, String className, String classPK,
			String name, String description, long reviewUserId,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Proposal

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long proposalId = counterLocalService.increment();

		TasksProposal proposal = tasksProposalPersistence.create(proposalId);

		proposal.setGroupId(groupId);
		proposal.setCompanyId(user.getCompanyId());
		proposal.setUserId(user.getUserId());
		proposal.setUserName(user.getFullName());
		proposal.setCreateDate(now);
		proposal.setModifiedDate(now);
		proposal.setClassNameId(classNameId);
		proposal.setClassPK(classPK);
		proposal.setName(name);
		proposal.setDescription(description);

		proposal = tasksProposalPersistence.update(proposal, false);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addProposalResources(
				proposal, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addProposalResources(
				proposal, communityPermissions, guestPermissions);
		}

		// Review

		long assignedByUserId = userId;
		int stage = 1;

		tasksReviewLocalService.addReview(
			reviewUserId, proposal.getProposalId(), assignedByUserId, stage);

		// Message boards

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		WorkflowThreadLocal.setEnabled(false);

		try {
			mbMessageLocalService.addDiscussionMessage(
				userId, proposal.getUserName(), groupId,
				TasksProposal.class.getName(), proposalId,
				WorkflowConstants.ACTION_PUBLISH);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}

		// Social

		socialActivityLocalService.addActivity(
			userId, groupId, TasksProposal.class.getName(), proposalId,
			TasksActivityKeys.ADD_PROPOSAL, StringPool.BLANK, 0);

		return proposal;
	}

	public TasksProposal addProposal(
			long userId, long groupId, String className, String classPK,
			String name, String description, long reviewUserId,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addProposal(
			userId, groupId, className, classPK, name, description,
			reviewUserId, null, null, communityPermissions, guestPermissions);
	}

	public void addProposalResources(
			long proposalId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		addProposalResources(
			proposal, addCommunityPermissions, addGuestPermissions);
	}

	public void addProposalResources(
			long proposalId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		addProposalResources(proposal, communityPermissions, guestPermissions);
	}

	public void addProposalResources(
			TasksProposal proposal, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			proposal.getCompanyId(), proposal.getGroupId(),
			proposal.getUserId(), TasksProposal.class.getName(),
			proposal.getProposalId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addProposalResources(
			TasksProposal proposal, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			proposal.getCompanyId(), proposal.getGroupId(),
			proposal.getUserId(), TasksProposal.class.getName(),
			proposal.getProposalId(), communityPermissions, guestPermissions);
	}

	public void deleteProposal(long proposalId)
		throws PortalException, SystemException {

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		deleteProposal(proposal);
	}

	public void deleteProposal(long classNameId, String classPK)
		throws PortalException, SystemException {

		try {
			TasksProposal proposal = getProposal(classNameId, classPK);

			deleteProposal(proposal);
		}
		catch (NoSuchProposalException nspe) {
		}
	}

	public void deleteProposal(String className, String classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteProposal(classNameId, classPK);
	}

	public void deleteProposal(TasksProposal proposal)
		throws PortalException, SystemException {

		// Proposal

		tasksProposalPersistence.remove(proposal);

		// Resources

		resourceLocalService.deleteResource(
			proposal.getCompanyId(), TasksProposal.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, proposal.getProposalId());

		// Reviews

		tasksReviewLocalService.deleteReviews(proposal.getProposalId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			TasksProposal.class.getName(), proposal.getProposalId());

		// Social

		socialActivityLocalService.deleteActivities(
			TasksProposal.class.getName(), proposal.getProposalId());
	}

	public void deleteProposals(long groupId)
		throws PortalException, SystemException {

		List<TasksProposal> proposals = tasksProposalPersistence.findByGroupId(
			groupId);

		for (TasksProposal proposal : proposals) {
			deleteProposal(proposal);
		}
	}

	public TasksProposal getProposal(long proposalId)
		throws PortalException, SystemException {

		return tasksProposalPersistence.findByPrimaryKey(proposalId);
	}

	public TasksProposal getProposal(long classNameId, String classPK)
		throws PortalException, SystemException {

		return tasksProposalPersistence.findByC_C(classNameId, classPK);
	}

	public TasksProposal getProposal(String className, String classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getProposal(classNameId, classPK);
	}

	public List<TasksProposal> getProposals(long groupId, int start, int end)
		throws SystemException {

		return tasksProposalPersistence.findByGroupId(groupId, start, end);
	}

	public int getProposalsCount(long groupId) throws SystemException {
		return tasksProposalPersistence.countByGroupId(groupId);
	}

	public List<TasksProposal> getReviewProposals(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return tasksProposalFinder.findByG_U(groupId, userId, start, end);
	}

	public int getReviewProposalsCount(long groupId, long userId)
		throws SystemException {

		return tasksProposalFinder.countByG_U(groupId, userId);
	}

	public List<TasksProposal> getUserProposals(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return tasksProposalPersistence.findByG_U(groupId, userId, start, end);
	}

	public int getUserProposalsCount(long groupId, long userId)
		throws SystemException {

		return tasksProposalPersistence.countByG_U(groupId, userId);
	}

	public TasksProposal updateProposal(
			long userId, long proposalId, String description, int dueDateMonth,
			int dueDateDay, int dueDateYear, int dueDateHour, int dueDateMinute)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date dueDate = PortalUtil.getDate(
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour, dueDateMinute,
			user.getTimeZone(), new ProposalDueDateException());

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		proposal.setModifiedDate(new Date());
		proposal.setDescription(description);
		proposal.setDueDate(dueDate);

		tasksProposalPersistence.update(proposal, false);

		return proposal;
	}

}