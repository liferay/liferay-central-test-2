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