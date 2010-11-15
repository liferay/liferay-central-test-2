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

	/**
	* Adds the tasks review to the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to add
	* @return the tasks review that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview addTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.addTasksReview(tasksReview);
	}

	/**
	* Creates a new tasks review with the primary key. Does not add the tasks review to the database.
	*
	* @param reviewId the primary key for the new tasks review
	* @return the new tasks review
	*/
	public com.liferay.portlet.tasks.model.TasksReview createTasksReview(
		long reviewId) {
		return _tasksReviewLocalService.createTasksReview(reviewId);
	}

	/**
	* Deletes the tasks review with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param reviewId the primary key of the tasks review to delete
	* @throws PortalException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTasksReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteTasksReview(reviewId);
	}

	/**
	* Deletes the tasks review from the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		_tasksReviewLocalService.deleteTasksReview(tasksReview);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the tasks review with the primary key.
	*
	* @param reviewId the primary key of the tasks review to get
	* @return the tasks review
	* @throws PortalException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview getTasksReview(
		long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getTasksReview(reviewId);
	}

	/**
	* Gets a range of all the tasks reviews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @return the range of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> getTasksReviews(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getTasksReviews(start, end);
	}

	/**
	* Gets the number of tasks reviews.
	*
	* @return the number of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int getTasksReviewsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.getTasksReviewsCount();
	}

	/**
	* Updates the tasks review in the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to update
	* @return the tasks review that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReviewLocalService.updateTasksReview(tasksReview);
	}

	/**
	* Updates the tasks review in the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to update
	* @param merge whether to merge the tasks review with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the tasks review that was updated
	* @throws SystemException if a system exception occurred
	*/
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

	public void setWrappedTasksReviewLocalService(
		TasksReviewLocalService tasksReviewLocalService) {
		_tasksReviewLocalService = tasksReviewLocalService;
	}

	private TasksReviewLocalService _tasksReviewLocalService;
}