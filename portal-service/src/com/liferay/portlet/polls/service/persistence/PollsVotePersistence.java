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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.polls.model.PollsVote;

/**
 * <a href="PollsVotePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVotePersistenceImpl
 * @see       PollsVoteUtil
 * @generated
 */
public interface PollsVotePersistence extends BasePersistence<PollsVote> {
	public void cacheResult(com.liferay.portlet.polls.model.PollsVote pollsVote);

	public void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsVote> pollsVotes);

	public com.liferay.portlet.polls.model.PollsVote create(long voteId);

	public com.liferay.portlet.polls.model.PollsVote remove(long voteId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote updateImpl(
		com.liferay.portlet.polls.model.PollsVote pollsVote, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.polls.model.PollsVote findByPrimaryKey(
		long voteId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote fetchByPrimaryKey(
		long voteId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.polls.model.PollsVote findByQuestionId_First(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote findByQuestionId_Last(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote[] findByQuestionId_PrevAndNext(
		long voteId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.polls.model.PollsVote findByChoiceId_First(
		long choiceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote findByChoiceId_Last(
		long choiceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote[] findByChoiceId_PrevAndNext(
		long voteId, long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote findByQ_U(
		long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public com.liferay.portlet.polls.model.PollsVote fetchByQ_U(
		long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.polls.model.PollsVote fetchByQ_U(
		long questionId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByChoiceId(long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByQ_U(long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByChoiceId(long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByQ_U(long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}