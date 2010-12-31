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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.tasks.model.TasksReview;

/**
 * The persistence interface for the tasks review service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TasksReviewPersistenceImpl
 * @see TasksReviewUtil
 * @generated
 */
public interface TasksReviewPersistence extends BasePersistence<TasksReview> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TasksReviewUtil} to access the tasks review persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the tasks review in the entity cache if it is enabled.
	*
	* @param tasksReview the tasks review to cache
	*/
	public void cacheResult(
		com.liferay.portlet.tasks.model.TasksReview tasksReview);

	/**
	* Caches the tasks reviews in the entity cache if it is enabled.
	*
	* @param tasksReviews the tasks reviews to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksReview> tasksReviews);

	/**
	* Creates a new tasks review with the primary key. Does not add the tasks review to the database.
	*
	* @param reviewId the primary key for the new tasks review
	* @return the new tasks review
	*/
	public com.liferay.portlet.tasks.model.TasksReview create(long reviewId);

	/**
	* Removes the tasks review with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param reviewId the primary key of the tasks review to remove
	* @return the tasks review that was removed
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview remove(long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the tasks review with the primary key or throws a {@link com.liferay.portlet.tasks.NoSuchReviewException} if it could not be found.
	*
	* @param reviewId the primary key of the tasks review to find
	* @return the tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the tasks review with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param reviewId the primary key of the tasks review to find
	* @return the tasks review, or <code>null</code> if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview fetchByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the tasks reviews where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first tasks review in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the last tasks review in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview[] findByUserId_PrevAndNext(
		long reviewId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds all the tasks reviews where proposalId = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first tasks review in the ordered set where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByProposalId_First(
		long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the last tasks review in the ordered set where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByProposalId_Last(
		long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the tasks reviews before and after the current tasks review in the ordered set where proposalId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param reviewId the primary key of the current tasks review
	* @param proposalId the proposal ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview[] findByProposalId_PrevAndNext(
		long reviewId, long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the tasks review where userId = &#63; and proposalId = &#63; or throws a {@link com.liferay.portlet.tasks.NoSuchReviewException} if it could not be found.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByU_P(long userId,
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the tasks review where userId = &#63; and proposalId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the matching tasks review, or <code>null</code> if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview fetchByU_P(long userId,
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the tasks review where userId = &#63; and proposalId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the matching tasks review, or <code>null</code> if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview fetchByU_P(long userId,
		long proposalId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the tasks reviews where proposalId = &#63; and stage = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first tasks review in the ordered set where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByP_S_First(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds the last tasks review in the ordered set where proposalId = &#63; and stage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByP_S_Last(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_PrevAndNext(
		long reviewId, long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @return the matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_First(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_Last(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_First(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a matching tasks review could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_Last(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

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
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks review
	* @throws com.liferay.portlet.tasks.NoSuchReviewException if a tasks review with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_R_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Finds all the tasks reviews.
	*
	* @return the tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the tasks reviews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of tasks reviews to return
	* @param end the upper bound of the range of tasks reviews to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks reviews where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks reviews where proposalId = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the tasks review where userId = &#63; and proposalId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	/**
	* Removes all the tasks reviews where proposalId = &#63; and stage = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63; and rejected = &#63; from the database.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @param rejected the rejected to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks reviews from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks reviews where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks reviews where proposalId = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int countByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks reviews where userId = &#63; and proposalId = &#63;.
	*
	* @param userId the user ID to search with
	* @param proposalId the proposal ID to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks reviews where proposalId = &#63; and stage = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int countByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks reviews where proposalId = &#63; and stage = &#63; and completed = &#63;.
	*
	* @param proposalId the proposal ID to search with
	* @param stage the stage to search with
	* @param completed the completed to search with
	* @return the number of matching tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int countByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public int countByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks reviews.
	*
	* @return the number of tasks reviews
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}