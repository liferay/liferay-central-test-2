/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PollsVoteUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsVoteUtil {
	public static com.liferay.portlet.polls.model.PollsVote create(
		com.liferay.portlet.polls.service.persistence.PollsVotePK pollsVotePK) {
		return getPersistence().create(pollsVotePK);
	}

	public static com.liferay.portlet.polls.model.PollsVote remove(
		com.liferay.portlet.polls.service.persistence.PollsVotePK pollsVotePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(pollsVotePK));
		}

		com.liferay.portlet.polls.model.PollsVote pollsVote = getPersistence()
																  .remove(pollsVotePK);

		if (listener != null) {
			listener.onAfterRemove(pollsVote);
		}

		return pollsVote;
	}

	public static com.liferay.portlet.polls.model.PollsVote remove(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(pollsVote);
		}

		pollsVote = getPersistence().remove(pollsVote);

		if (listener != null) {
			listener.onAfterRemove(pollsVote);
		}

		return pollsVote;
	}

	public static com.liferay.portlet.polls.model.PollsVote update(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = pollsVote.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pollsVote);
			}
			else {
				listener.onBeforeUpdate(pollsVote);
			}
		}

		pollsVote = getPersistence().update(pollsVote);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pollsVote);
			}
			else {
				listener.onAfterUpdate(pollsVote);
			}
		}

		return pollsVote;
	}

	public static com.liferay.portlet.polls.model.PollsVote update(
		com.liferay.portlet.polls.model.PollsVote pollsVote,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = pollsVote.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pollsVote);
			}
			else {
				listener.onBeforeUpdate(pollsVote);
			}
		}

		pollsVote = getPersistence().update(pollsVote, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pollsVote);
			}
			else {
				listener.onAfterUpdate(pollsVote);
			}
		}

		return pollsVote;
	}

	public static com.liferay.portlet.polls.model.PollsVote findByPrimaryKey(
		com.liferay.portlet.polls.service.persistence.PollsVotePK pollsVotePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByPrimaryKey(pollsVotePK);
	}

	public static com.liferay.portlet.polls.model.PollsVote fetchByPrimaryKey(
		com.liferay.portlet.polls.service.persistence.PollsVotePK pollsVotePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(pollsVotePK);
	}

	public static java.util.List findByQuestionId(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId);
	}

	public static java.util.List findByQuestionId(java.lang.String questionId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId, begin, end);
	}

	public static java.util.List findByQuestionId(java.lang.String questionId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId, begin, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQuestionId_First(
		java.lang.String questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQuestionId_First(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQuestionId_Last(
		java.lang.String questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQuestionId_Last(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote[] findByQuestionId_PrevAndNext(
		com.liferay.portlet.polls.service.persistence.PollsVotePK pollsVotePK,
		java.lang.String questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQuestionId_PrevAndNext(pollsVotePK,
			questionId, obc);
	}

	public static java.util.List findByQ_C(java.lang.String questionId,
		java.lang.String choiceId) throws com.liferay.portal.SystemException {
		return getPersistence().findByQ_C(questionId, choiceId);
	}

	public static java.util.List findByQ_C(java.lang.String questionId,
		java.lang.String choiceId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQ_C(questionId, choiceId, begin, end);
	}

	public static java.util.List findByQ_C(java.lang.String questionId,
		java.lang.String choiceId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQ_C(questionId, choiceId, begin, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQ_C_First(
		java.lang.String questionId, java.lang.String choiceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQ_C_First(questionId, choiceId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote findByQ_C_Last(
		java.lang.String questionId, java.lang.String choiceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQ_C_Last(questionId, choiceId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsVote[] findByQ_C_PrevAndNext(
		com.liferay.portlet.polls.service.persistence.PollsVotePK pollsVotePK,
		java.lang.String questionId, java.lang.String choiceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQ_C_PrevAndNext(pollsVotePK, questionId,
			choiceId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByQuestionId(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	public static void removeByQ_C(java.lang.String questionId,
		java.lang.String choiceId) throws com.liferay.portal.SystemException {
		getPersistence().removeByQ_C(questionId, choiceId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByQuestionId(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	public static int countByQ_C(java.lang.String questionId,
		java.lang.String choiceId) throws com.liferay.portal.SystemException {
		return getPersistence().countByQ_C(questionId, choiceId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PollsVotePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PollsVotePersistence persistence) {
		_persistence = persistence;
	}

	private static PollsVoteUtil _getUtil() {
		if (_util == null) {
			_util = (PollsVoteUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = PollsVoteUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.polls.model.PollsVote"));
	private static Log _log = LogFactory.getLog(PollsVoteUtil.class);
	private static PollsVoteUtil _util;
	private PollsVotePersistence _persistence;
}