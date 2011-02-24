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

package com.liferay.portlet.tasks.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the tasks review local service. This utility wraps {@link com.liferay.portlet.tasks.service.impl.TasksReviewLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TasksReviewLocalService
 * @see com.liferay.portlet.tasks.service.base.TasksReviewLocalServiceBaseImpl
 * @see com.liferay.portlet.tasks.service.impl.TasksReviewLocalServiceImpl
 * @generated
 */
public class TasksReviewLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.tasks.service.impl.TasksReviewLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the tasks review to the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to add
	* @return the tasks review that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview addTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTasksReview(tasksReview);
	}

	/**
	* Creates a new tasks review with the primary key. Does not add the tasks review to the database.
	*
	* @param reviewId the primary key for the new tasks review
	* @return the new tasks review
	*/
	public static com.liferay.portlet.tasks.model.TasksReview createTasksReview(
		long reviewId) {
		return getService().createTasksReview(reviewId);
	}

	/**
	* Deletes the tasks review with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param reviewId the primary key of the tasks review to delete
	* @throws PortalException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTasksReview(long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTasksReview(reviewId);
	}

	/**
	* Deletes the tasks review from the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTasksReview(tasksReview);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the tasks review with the primary key.
	*
	* @param reviewId the primary key of the tasks review to get
	* @return the tasks review
	* @throws PortalException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview getTasksReview(
		long reviewId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTasksReview(reviewId);
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
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> getTasksReviews(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTasksReviews(start, end);
	}

	/**
	* Gets the number of tasks reviews.
	*
	* @return the number of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int getTasksReviewsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTasksReviewsCount();
	}

	/**
	* Updates the tasks review in the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to update
	* @return the tasks review that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTasksReview(tasksReview);
	}

	/**
	* Updates the tasks review in the database. Also notifies the appropriate model listeners.
	*
	* @param tasksReview the tasks review to update
	* @param merge whether to merge the tasks review with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the tasks review that was updated
	* @throws SystemException if a system exception occurred
	*/
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

			ReferenceRegistry.registerReference(TasksReviewLocalServiceUtil.class,
				"_service");
			MethodCache.remove(TasksReviewLocalService.class);
		}

		return _service;
	}

	public void setService(TasksReviewLocalService service) {
		MethodCache.remove(TasksReviewLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(TasksReviewLocalServiceUtil.class,
			"_service");
		MethodCache.remove(TasksReviewLocalService.class);
	}

	private static TasksReviewLocalService _service;
}