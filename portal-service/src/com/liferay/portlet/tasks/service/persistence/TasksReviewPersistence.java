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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.tasks.model.TasksReview;

/**
 * <a href="TasksReviewPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewPersistenceImpl
 * @see       TasksReviewUtil
 * @generated
 */
public interface TasksReviewPersistence extends BasePersistence<TasksReview> {
	public void cacheResult(
		com.liferay.portlet.tasks.model.TasksReview tasksReview);

	public void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksReview> tasksReviews);

	public com.liferay.portlet.tasks.model.TasksReview create(long reviewId);

	public com.liferay.portlet.tasks.model.TasksReview remove(long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview fetchByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByUserId_PrevAndNext(
		long reviewId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByProposalId_First(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByProposalId_Last(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByProposalId_PrevAndNext(
		long reviewId, long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByU_P(long userId,
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview fetchByU_P(long userId,
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview fetchByU_P(long userId,
		long proposalId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_First(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_Last(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_PrevAndNext(
		long reviewId, long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_First(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_Last(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_First(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_Last(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_R_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		boolean rejected, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public void removeByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByProposalId(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_P(long userId, long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_S(long proposalId, int stage)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}