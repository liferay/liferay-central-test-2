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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.tasks.model.TasksReview;

import java.util.List;

/**
 * <a href="TasksReviewUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewPersistence
 * @see       TasksReviewPersistenceImpl
 * @generated
 */
public class TasksReviewUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
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

	public static void cacheResult(
		com.liferay.portlet.tasks.model.TasksReview tasksReview) {
		getPersistence().cacheResult(tasksReview);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksReview> tasksReviews) {
		getPersistence().cacheResult(tasksReviews);
	}

	public static com.liferay.portlet.tasks.model.TasksReview create(
		long reviewId) {
		return getPersistence().create(reviewId);
	}

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

	public static com.liferay.portlet.tasks.model.TasksReview findByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByPrimaryKey(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview fetchByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(reviewId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByUserId_PrevAndNext(
		long reviewId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_PrevAndNext(reviewId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProposalId(proposalId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProposalId(proposalId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProposalId(proposalId, start, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByProposalId_First(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByProposalId_First(proposalId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByProposalId_Last(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByProposalId_Last(proposalId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByProposalId_PrevAndNext(
		long reviewId, long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByProposalId_PrevAndNext(reviewId, proposalId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByU_P(
		long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByU_P(userId, proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview fetchByU_P(
		long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_P(userId, proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview fetchByU_P(
		long userId, long proposalId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_P(userId, proposalId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S(proposalId, stage);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S(proposalId, stage, start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S(proposalId, stage, start, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_First(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByP_S_First(proposalId, stage, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_Last(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByP_S_Last(proposalId, stage, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_PrevAndNext(
		long reviewId, long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_PrevAndNext(reviewId, proposalId, stage, obc);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_S_C(proposalId, stage, completed);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C(proposalId, stage, completed, start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C(proposalId, stage, completed, start, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_First(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_First(proposalId, stage, completed, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_Last(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_Last(proposalId, stage, completed, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_PrevAndNext(reviewId, proposalId, stage,
			completed, obc);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected,
			start, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_First(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_First(proposalId, stage, completed, rejected,
			obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_Last(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_Last(proposalId, stage, completed, rejected,
			obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_R_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		boolean rejected, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_PrevAndNext(reviewId, proposalId, stage,
			completed, rejected, obc);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByProposalId(proposalId);
	}

	public static void removeByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		getPersistence().removeByU_P(userId, proposalId);
	}

	public static void removeByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_S(proposalId, stage);
	}

	public static void removeByP_S_C(long proposalId, int stage,
		boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_S_C(proposalId, stage, completed);
	}

	public static void removeByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_S_C_R(proposalId, stage, completed, rejected);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByProposalId(proposalId);
	}

	public static int countByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_P(userId, proposalId);
	}

	public static int countByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_S(proposalId, stage);
	}

	public static int countByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_S_C(proposalId, stage, completed);
	}

	public static int countByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByP_S_C_R(proposalId, stage, completed, rejected);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static TasksReviewPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TasksReviewPersistence)PortalBeanLocatorUtil.locate(TasksReviewPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(TasksReviewPersistence persistence) {
		_persistence = persistence;
	}

	private static TasksReviewPersistence _persistence;
}