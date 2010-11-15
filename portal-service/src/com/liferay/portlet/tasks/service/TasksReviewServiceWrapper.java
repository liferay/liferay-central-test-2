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
 * This class is a wrapper for {@link TasksReviewService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewService
 * @generated
 */
public class TasksReviewServiceWrapper implements TasksReviewService {
	public TasksReviewServiceWrapper(TasksReviewService tasksReviewService) {
		_tasksReviewService = tasksReviewService;
	}

	public com.liferay.portlet.tasks.model.TasksReview approveReview(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewService.approveReview(proposalId, stage);
	}

	public com.liferay.portlet.tasks.model.TasksReview rejectReview(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewService.rejectReview(proposalId, stage);
	}

	public void updateReviews(long proposalId, long[][] userIdsPerStage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewService.updateReviews(proposalId, userIdsPerStage);
	}

	public TasksReviewService getWrappedTasksReviewService() {
		return _tasksReviewService;
	}

	public void setWrappedTasksReviewService(
		TasksReviewService tasksReviewService) {
		_tasksReviewService = tasksReviewService;
	}

	private TasksReviewService _tasksReviewService;
}