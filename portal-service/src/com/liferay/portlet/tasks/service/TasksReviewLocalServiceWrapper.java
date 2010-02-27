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
 * <a href="TasksReviewLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link TasksReviewLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewLocalService
 * @generated
 */
public class TasksReviewLocalServiceWrapper implements TasksReviewLocalService {
	public TasksReviewLocalServiceWrapper(
		TasksReviewLocalService tasksReviewLocalService) {
		_tasksReviewLocalService = tasksReviewLocalService;
	}

	public com.liferay.portlet.tasks.model.TasksReview addTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.addTasksReview(tasksReview);
	}

	public com.liferay.portlet.tasks.model.TasksReview createTasksReview(
		long reviewId) {
		return _tasksReviewLocalService.createTasksReview(reviewId);
	}

	public void deleteTasksReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteTasksReview(reviewId);
	}

	public void deleteTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteTasksReview(tasksReview);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.tasks.model.TasksReview getTasksReview(
		long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getTasksReview(reviewId);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> getTasksReviews(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getTasksReviews(start, end);
	}

	public int getTasksReviewsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getTasksReviewsCount();
	}

	public com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.updateTasksReview(tasksReview);
	}

	public com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.updateTasksReview(tasksReview, merge);
	}

	public com.liferay.portlet.tasks.model.TasksReview addReview(long userId,
		long proposalId, long assignedByUserId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.addReview(userId, proposalId,
			assignedByUserId, stage);
	}

	public com.liferay.portlet.tasks.model.TasksReview approveReview(
		long userId, long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.approveReview(userId, proposalId, stage);
	}

	public void deleteReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteReview(reviewId);
	}

	public void deleteReview(com.liferay.portlet.tasks.model.TasksReview review)
		throws com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteReview(review);
	}

	public void deleteReviews(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteReviews(proposalId);
	}

	public com.liferay.portlet.tasks.model.TasksReview getReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getReview(reviewId);
	}

	public com.liferay.portlet.tasks.model.TasksReview getReview(long userId,
		long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getReview(userId, proposalId);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getReviews(proposalId);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getReviews(proposalId, stage);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getReviews(proposalId, stage, completed);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getReviews(proposalId, stage,
			completed, rejected);
	}

	public com.liferay.portlet.tasks.model.TasksReview rejectReview(
		long userId, long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.rejectReview(userId, proposalId, stage);
	}

	public void updateReviews(long proposalId, long assignedByUserId,
		long[][] userIdsPerStage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.updateReviews(proposalId, assignedByUserId,
			userIdsPerStage);
	}

	public TasksReviewLocalService getWrappedTasksReviewLocalService() {
		return _tasksReviewLocalService;
	}

	private TasksReviewLocalService _tasksReviewLocalService;
}