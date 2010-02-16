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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="TasksReviewLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link TasksReviewLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewLocalService
 * @generated
 */
public class TasksReviewLocalServiceUtil {
	public static com.liferay.portlet.tasks.model.TasksReview addTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTasksReview(tasksReview);
	}

	public static com.liferay.portlet.tasks.model.TasksReview createTasksReview(
		long reviewId) {
		return getService().createTasksReview(reviewId);
	}

	public static void deleteTasksReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTasksReview(reviewId);
	}

	public static void deleteTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTasksReview(tasksReview);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.tasks.model.TasksReview getTasksReview(
		long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTasksReview(reviewId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> getTasksReviews(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTasksReviews(start, end);
	}

	public static int getTasksReviewsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTasksReviewsCount();
	}

	public static com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTasksReview(tasksReview);
	}

	public static com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTasksReview(tasksReview, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksReview addReview(
		long userId, long proposalId, long assignedByUserId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addReview(userId, proposalId, assignedByUserId, stage);
	}

	public static com.liferay.portlet.tasks.model.TasksReview approveReview(
		long userId, long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().approveReview(userId, proposalId, stage);
	}

	public static void deleteReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteReview(reviewId);
	}

	public static void deleteReview(
		com.liferay.portlet.tasks.model.TasksReview review)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteReview(review);
	}

	public static void deleteReviews(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteReviews(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview getReview(
		long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getReview(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview getReview(
		long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getReview(userId, proposalId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReviews(proposalId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReviews(proposalId, stage);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReviews(proposalId, stage, completed);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> getReviews(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReviews(proposalId, stage, completed, rejected);
	}

	public static com.liferay.portlet.tasks.model.TasksReview rejectReview(
		long userId, long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().rejectReview(userId, proposalId, stage);
	}

	public static void updateReviews(long proposalId, long assignedByUserId,
		long[][] userIdsPerStage)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateReviews(proposalId, assignedByUserId, userIdsPerStage);
	}

	public static TasksReviewLocalService getService() {
		if (_service == null) {
			_service = (TasksReviewLocalService)PortalBeanLocatorUtil.locate(TasksReviewLocalService.class.getName());
		}

		return _service;
	}

	public void setService(TasksReviewLocalService service) {
		_service = service;
	}

	private static TasksReviewLocalService _service;
}