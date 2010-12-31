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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.base.TasksReviewServiceBaseImpl;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class TasksReviewServiceImpl extends TasksReviewServiceBaseImpl {

	public TasksReview approveReview(long proposalId, int stage)
		throws PortalException, SystemException {

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		GroupPermissionUtil.check(
			getPermissionChecker(), proposal.getGroupId(),
			ActionKeys.APPROVE_PROPOSAL);

		return tasksReviewLocalService.approveReview(
			getUserId(), proposalId, stage);
	}

	public TasksReview rejectReview(long proposalId, int stage)
		throws PortalException, SystemException {

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		GroupPermissionUtil.check(
			getPermissionChecker(), proposal.getGroupId(),
			ActionKeys.APPROVE_PROPOSAL);

		return tasksReviewLocalService.rejectReview(
			getUserId(), proposalId, stage);
	}

	public void updateReviews(long proposalId, long[][] userIdsPerStage)
		throws PortalException, SystemException {

		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);

		GroupPermissionUtil.check(
			getPermissionChecker(), proposal.getGroupId(),
			ActionKeys.ASSIGN_REVIEWER);

		tasksReviewLocalService.updateReviews(
			proposalId, getUserId(), userIdsPerStage);
	}

}