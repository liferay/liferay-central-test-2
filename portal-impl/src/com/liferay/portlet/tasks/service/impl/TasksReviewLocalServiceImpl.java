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

package com.liferay.portlet.tasks.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.tasks.DuplicateReviewUserIdException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.base.TasksReviewLocalServiceBaseImpl;
import com.liferay.portlet.tasks.social.TasksActivityKeys;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class TasksReviewLocalServiceImpl
	extends TasksReviewLocalServiceBaseImpl {

	public TasksReview addReview(
			long userId, long proposalId, long assignedByUserId, int stage)
		throws PortalException, SystemException {

		// Review

		User user = userPersistence.findByPrimaryKey(userId);
		TasksProposal proposal = tasksProposalPersistence.findByPrimaryKey(
			proposalId);
		User assignedByUser = userPersistence.findByPrimaryKey(
			assignedByUserId);
		Date now = new Date();

		long reviewId = counterLocalService.increment();

		TasksReview review = tasksReviewPersistence.create(reviewId);

		review.setGroupId(proposal.getGroupId());
		review.setCompanyId(user.getCompanyId());
		review.setUserId(user.getUserId());
		review.setUserName(user.getFullName());
		review.setCreateDate(now);
		review.setModifiedDate(now);
		review.setProposalId(proposalId);
		review.setAssignedByUserId(assignedByUserId);
		review.setAssignedByUserName(assignedByUser.getFullName());
		review.setStage(stage);
		review.setCompleted(false);
		review.setRejected(false);

		tasksReviewPersistence.update(review, false);

		// Social

		JSONObject extraData = JSONFactoryUtil.createJSONObject();

		extraData.put("stage", review.getStage());
		extraData.put("completed", review.getCompleted());
		extraData.put("rejected", review.getRejected());

		socialActivityLocalService.addActivity(
			assignedByUserId, proposal.getGroupId(),
			TasksProposal.class.getName(), proposalId,
			TasksActivityKeys.ASSIGN_PROPOSAL, extraData.toString(), userId);

		return review;
	}

	public TasksReview approveReview(
			long userId, long proposalId, int stage)
		throws PortalException, SystemException {

		return updateReview(userId, proposalId, stage, false);
	}

	public void deleteReview(long reviewId)
		throws PortalException, SystemException {

		TasksReview review = tasksReviewPersistence.findByPrimaryKey(
			reviewId);

		deleteReview(review);
	}

	public void deleteReview(TasksReview review) throws SystemException {
		tasksReviewPersistence.remove(review);
	}

	public void deleteReviews(long proposalId) throws SystemException {
		List<TasksReview> reviews = tasksReviewPersistence.findByProposalId(
			proposalId);

		for (TasksReview review : reviews) {
			deleteReview(review);
		}
	}

	public TasksReview getReview(long reviewId)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByPrimaryKey(reviewId);
	}

	public TasksReview getReview(long userId, long proposalId)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByU_P(userId, proposalId);
	}

	public List<TasksReview> getReviews(long proposalId)
		throws SystemException {

		return tasksReviewPersistence.findByProposalId(proposalId);
	}

	public List<TasksReview> getReviews(long proposalId, int stage)
		throws SystemException {

		return tasksReviewPersistence.findByP_S(proposalId, stage);
	}

	public List<TasksReview> getReviews(
			long proposalId, int stage, boolean completed)
		throws SystemException {

		return tasksReviewPersistence.findByP_S_C(proposalId, stage, completed);
	}

	public List<TasksReview> getReviews(
			long proposalId, int stage, boolean completed, boolean rejected)
		throws SystemException {

		return tasksReviewPersistence.findByP_S_C_R(
			proposalId, stage, completed, rejected);
	}

	public TasksReview rejectReview(
			long userId, long proposalId, int stage)
		throws PortalException, SystemException {

		return updateReview(userId, proposalId, stage, true);
	}

	public void updateReviews(
			long proposalId, long assignedByUserId, long[][] userIdsPerStage)
		throws PortalException, SystemException {

		Set<Long> assignedUserIds = new HashSet<Long>();

		for (int i = 0; i < userIdsPerStage.length; i++) {
			long[] userIds = userIdsPerStage[i];

			for (long userId : userIds) {
				if (assignedUserIds.contains(userId)) {
					throw new DuplicateReviewUserIdException();
				}
				else {
					assignedUserIds.add(userId);
				}
			}
		}

		for (int i = 0; i < userIdsPerStage.length; i++) {
			Set<Long> userIds = SetUtil.fromArray(userIdsPerStage[i]);

			updateReviews(proposalId, assignedByUserId, i + 2, userIds);
		}
	}

	protected TasksReview updateReview(
			long userId, long proposalId, int stage, boolean rejected)
		throws PortalException, SystemException {

		TasksReview review = tasksReviewPersistence.findByU_P(
			userId, proposalId);

		review.setModifiedDate(new Date());
		review.setCompleted(true);
		review.setRejected(rejected);

		tasksReviewPersistence.update(review, false);

		// Social

		JSONObject extraData = JSONFactoryUtil.createJSONObject();

		extraData.put("stage", review.getStage());
		extraData.put("completed", review.getCompleted());
		extraData.put("rejected", review.getRejected());

		socialActivityLocalService.addActivity(
			userId, review.getGroupId(), TasksProposal.class.getName(),
			proposalId, TasksActivityKeys.REVIEW_PROPOSAL, extraData.toString(),
			review.getAssignedByUserId());

		return review;
	}

	protected void updateReviews(
			long proposalId, long assignedByUserId, int stage,
			Set<Long> userIds)
		throws PortalException, SystemException {

		Set<Long> reviewUserIds = new HashSet<Long>();

		List<TasksReview> reviews = tasksReviewPersistence.findByP_S(
			proposalId, stage);

		for (TasksReview review : reviews) {
			if (!userIds.contains(review.getUserId())) {
				deleteReview(review);
			}
			else {
				reviewUserIds.add(review.getUserId());
			}
		}

		for (long userId : userIds) {
			if (!reviewUserIds.contains(userId)) {
				addReview(userId, proposalId, assignedByUserId, stage);
			}
		}
	}

}