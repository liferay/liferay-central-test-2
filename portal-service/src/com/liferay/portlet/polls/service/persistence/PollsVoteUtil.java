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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.polls.model.PollsVote;

import java.util.List;

/**
 * <a href="PollsVoteUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVotePersistence
 * @see       PollsVotePersistenceImpl
 * @generated
 */
public class PollsVoteUtil {
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
	public static PollsVote remove(PollsVote pollsVote)
		throws SystemException {
		return getPersistence().remove(pollsVote);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PollsVote update(PollsVote pollsVote, boolean merge)
		throws SystemException {
		return getPersistence().update(pollsVote, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.polls.model.PollsVote pollsVote) {
		getPersistence().cacheResult(pollsVote);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsVote> pollsVotes) {
		getPersistence().cacheResult(pollsVotes);
	}

	public static com.liferay.portlet.polls.model.PollsVote create(long voteId) {
		return getPersistence().create(voteId);
	}

	public static com.liferay.portlet.polls.model.PollsVote remove(long voteId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().remove(voteId);
	}

	public static com.liferay.portlet.polls.model.PollsVote updateImpl(
		com.liferay.portlet.polls.model.PollsVote pollsVote, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(pollsVote, merge);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByPrimaryKey(
		long voteId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByPrimaryKey(voteId);
	}

	public static com.liferay.portlet.polls.model.PollsVote fetchByPrimaryKey(
		long voteId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(voteId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId) throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQuestionId_First(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQuestionId_First(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQuestionId_Last(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQuestionId_Last(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote[] findByQuestionId_PrevAndNext(
		long voteId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence()
				   .findByQuestionId_PrevAndNext(voteId, questionId, obc);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId) throws com.liferay.portal.SystemException {
		return getPersistence().findByChoiceId(choiceId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByChoiceId(choiceId, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByChoiceId(choiceId, start, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByChoiceId_First(
		long choiceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByChoiceId_First(choiceId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByChoiceId_Last(
		long choiceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByChoiceId_Last(choiceId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote[] findByChoiceId_PrevAndNext(
		long voteId, long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByChoiceId_PrevAndNext(voteId, choiceId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQ_U(
		long questionId, long userId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQ_U(questionId, userId);
	}

	public static com.liferay.portlet.polls.model.PollsVote fetchByQ_U(
		long questionId, long userId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByQ_U(questionId, userId);
	}

	public static com.liferay.portlet.polls.model.PollsVote fetchByQ_U(
		long questionId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByQ_U(questionId, userId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByQuestionId(long questionId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	public static void removeByChoiceId(long choiceId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByChoiceId(choiceId);
	}

	public static void removeByQ_U(long questionId, long userId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		getPersistence().removeByQ_U(questionId, userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByQuestionId(long questionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	public static int countByChoiceId(long choiceId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByChoiceId(choiceId);
	}

	public static int countByQ_U(long questionId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByQ_U(questionId, userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static PollsVotePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PollsVotePersistence)PortalBeanLocatorUtil.locate(PollsVotePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PollsVotePersistence persistence) {
		_persistence = persistence;
	}

	private static PollsVotePersistence _persistence;
}