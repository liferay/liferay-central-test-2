/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.base.TasksReviewServiceBaseImpl;

/**
 * <a href="TasksReviewServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksReviewServiceImpl extends TasksReviewServiceBaseImpl {

	public TasksReview addReview(
			long userId, long groupId, long assigningUserId,
			String assigningUserName, long proposalId, int stage,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_REVIEWER);

		return tasksReviewLocalService.addReview(
			userId, groupId, assigningUserId, assigningUserName, proposalId,
			stage, addCommunityPermissions, addGuestPermissions);
	}

	public TasksReview addReview(
			long userId, long groupId, long assigningUserId,
			String assigningUserName, long proposalId, int stage,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_REVIEWER);

		return tasksReviewLocalService.addReview(
			userId, groupId, assigningUserId, assigningUserName, proposalId,
			stage, communityPermissions, guestPermissions);
	}

	public TasksReview rejectReview(
			long groupId, long proposalId, int stage, boolean rejected)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.APPROVE_PROPOSAL);

		return tasksReviewLocalService.rejectReview(
			getUserId(), proposalId, stage, rejected);
	}

	public void deleteReview(long groupId, long reviewId)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_REVIEWER);

		tasksReviewLocalService.deleteReview(reviewId);
	}

	public void updateReviewers(
			long groupId, long proposalId, int stage, long[] reviewerIds,
			long[] removeReviewerIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_REVIEWER);

		tasksReviewLocalService.updateReviewers(
			getUserId(), groupId, proposalId, stage, reviewerIds,
			removeReviewerIds);
	}

}