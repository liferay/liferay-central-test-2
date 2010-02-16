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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.base.TasksReviewServiceBaseImpl;

/**
 * <a href="TasksReviewServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
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