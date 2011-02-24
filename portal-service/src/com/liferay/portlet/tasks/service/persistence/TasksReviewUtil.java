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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.tasks.model.TasksReview;

import java.util.List;

/**
 * The persistence utility for the tasks review service. This utility wraps {@link TasksReviewPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TasksReviewPersistence
 * @see TasksReviewPersistenceImpl
 * @generated
 */
public class TasksReviewUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(TasksReview tasksReview) {
		getPersistence().clearCache(tasksReview);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TasksReview> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TasksReview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TasksReview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static TasksReview remove(TasksReview tasksReview)
		throws SystemException {
		return getPersistence().remove(tasksReview);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static TasksReview update(TasksReview tasksReview, boolean merge)
		throws SystemException {
		return getPersistence().update(tasksReview, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static TasksReview update(TasksReview tasksReview, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(tasksReview, merge, serviceContext);
	}

	/**
	* Caches the tasks review in the entity cache if it is enabled.
	*
	* @param tasksReview the tasks review to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.tasks.model.TasksReview tasksReview) {
		getPersistence().cacheResult(tasksReview);
	}

	/**
	* Caches the tasks reviews in the entity cache if it is enabled.
	*
	* @param tasksReviews the tasks reviews to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksReview> tasksReviews) {
		getPersistence().cacheResult(tasksReviews);
	}

	/**
	* Creates a new tasks review with the primary key. Does not add the tasks review to the database.
	*
	* @param reviewId the primary key for the new tasks review
	* @return the new tasks review
	*/
	public static com.liferay.portlet.tasks.model.TasksReview create(
		long reviewId) {
		return getPersistence().create(reviewId);
	}

	/**
	* Removes the tasks review with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param reviewId the primary key of the tasks review to remove
	* @return the tasks review that was removed
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview remove(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().remove(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(tasksReview, merge);
	}

	/**
	* Finds the tasks review with the primary key or throws a {@link com.liferay.portlet.tasks.NoSuchReviewException} if it could not be found.
	*
	* @param reviewId the primary key of the tasks review to find
	* @return the tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByPrimaryKey(reviewId);
	}

	/**
	* Finds the tasks review with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param reviewId the primary key of the tasks review to find
	* @return the tasks review, or <code>null</code> if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview fetchByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(reviewId);
	}

	/**
	* Finds all the tasks reviews where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Finds a range of all the tasks reviews where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @return the range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Finds an ordered range of all the tasks reviews where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Finds the first tasks review in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Finds the last tasks review in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview[] findByUserId_PrevAndNext(
		long reviewId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByUserId_PrevAndNext(reviewId, userId, orderByComparator);
	}

	/**
	* Finds all the tasks reviews where proposalId = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProposalId(proposalId);
	}

	/**
	* Finds a range of all the tasks reviews where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @return the range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProposalId(proposalId, start, end);
	}

	/**
	* Finds an ordered range of all the tasks reviews where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByProposalId(proposalId, start, end, orderByComparator);
	}

	/**
	* Finds the first tasks review in the ordered set where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByProposalId_First(
		long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByProposalId_First(proposalId, orderByComparator);
	}

	/**
	* Finds the last tasks review in the ordered set where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByProposalId_Last(
		long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByProposalId_Last(proposalId, orderByComparator);
	}

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param proposalId the proposal ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview[] findByProposalId_PrevAndNext(
		long reviewId, long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByProposalId_PrevAndNext(reviewId, proposalId,
			orderByComparator);
	}

	/**
	* Finds the tasks review where userId = &#63; and proposalId = &#63; or throws a {@link com.liferay.portlet.tasks.NoSuchReviewException} if it could not be found.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByU_P(
		long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByU_P(userId, proposalId);
	}

	/**
	* Finds the tasks review where userId = &#63; and proposalId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the matching tasks review, or <code>null</code> if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview fetchByU_P(
		long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_P(userId, proposalId);
	}

	/**
	* Finds the tasks review where userId = &#63; and proposalId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the matching tasks review, or <code>null</code> if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview fetchByU_P(
		long userId, long proposalId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_P(userId, proposalId, retrieveFromCache);
	}

	/**
	* Finds all the tasks reviews where proposalId = &#63; and stage = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S(proposalId, stage);
	}

	/**
	* Finds a range of all the tasks reviews where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @return the range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S(proposalId, stage, start, end);
	}

	/**
	* Finds an ordered range of all the tasks reviews where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S(proposalId, stage, start, end, orderByComparator);
	}

	/**
	* Finds the first tasks review in the ordered set where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_First(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_First(proposalId, stage, orderByComparator);
	}

	/**
	* Finds the last tasks review in the ordered set where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_Last(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_Last(proposalId, stage, orderByComparator);
	}

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_PrevAndNext(
		long reviewId, long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_PrevAndNext(reviewId, proposalId, stage,
			orderByComparator);
	}

	/**
	* Finds all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S_C(proposalId, stage, completed);
	}

	/**
	* Finds a range of all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @return the range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C(proposalId, stage, completed, start, end);
	}

	/**
	* Finds an ordered range of all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C(proposalId, stage, completed, start, end,
			orderByComparator);
	}

	/**
	* Finds the first tasks review in the ordered set where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_First(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_First(proposalId, stage, completed,
			orderByComparator);
	}

	/**
	* Finds the last tasks review in the ordered set where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_Last(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_Last(proposalId, stage, completed,
			orderByComparator);
	}

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_PrevAndNext(reviewId, proposalId, stage,
			completed, orderByComparator);
	}

	/**
	* Finds all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected);
	}

	/**
	* Finds a range of all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @return the range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected,
			start, end);
	}

	/**
	* Finds an ordered range of all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected,
			start, end, orderByComparator);
	}

	/**
	* Finds the first tasks review in the ordered set where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_First(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_First(proposalId, stage, completed, rejected,
			orderByComparator);
	}

	/**
	* Finds the last tasks review in the ordered set where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_Last(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_Last(proposalId, stage, completed, rejected,
			orderByComparator);
	}

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_R_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_PrevAndNext(reviewId, proposalId, stage,
			completed, rejected, orderByComparator);
	}

	/**
	* Finds all the tasks reviews.
	*
	* @return the tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the tasks reviews.
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
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the tasks reviews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the tasks reviews where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the tasks reviews where proposalId = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByProposalId(proposalId);
	}

	/**
	* Removes the tasks review where userId = &#63; and proposalId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		getPersistence().removeByU_P(userId, proposalId);
	}

	/**
	* Removes all the tasks reviews where proposalId = &#63; and stage = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_S(proposalId, stage);
	}

	/**
	* Removes all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP_S_C(long proposalId, int stage,
		boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_S_C(proposalId, stage, completed);
	}

	/**
	* Removes all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_S_C_R(proposalId, stage, completed, rejected);
	}

	/**
	* Removes all the tasks reviews from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the tasks reviews where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Counts all the tasks reviews where proposalId = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByProposalId(proposalId);
	}

	/**
	* Counts all the tasks reviews where userId = &#63; and proposalId = &#63;.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_P(userId, proposalId);
	}

	/**
	* Counts all the tasks reviews where proposalId = &#63; and stage = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_S(proposalId, stage);
	}

	/**
	* Counts all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_S_C(proposalId, stage, completed);
	}

	/**
	* Counts all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByP_S_C_R(proposalId, stage, completed, rejected);
	}

	/**
	* Counts all the tasks reviews.
	*
	* @return the number of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static TasksReviewPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TasksReviewPersistence)PortalBeanLocatorUtil.locate(TasksReviewPersistence.class.getName());

			ReferenceRegistry.registerReference(TasksReviewUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(TasksReviewPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(TasksReviewUtil.class,
			"_persistence");
	}

	private static TasksReviewPersistence _persistence;
}