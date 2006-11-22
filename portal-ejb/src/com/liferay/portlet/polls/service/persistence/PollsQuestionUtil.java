/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="PollsQuestionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsQuestionUtil {
	public static com.liferay.portlet.polls.model.PollsQuestion create(
		java.lang.String questionId) {
		return getPersistence().create(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion remove(
		java.lang.String questionId)
		throws com.liferay.portlet.polls.NoSuchQuestionException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(questionId));
		}

		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion = getPersistence()
																		  .remove(questionId);

		if (listener != null) {
			listener.onAfterRemove(pollsQuestion);
		}

		return pollsQuestion;
	}

	public static com.liferay.portlet.polls.model.PollsQuestion remove(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(pollsQuestion);
		}

		pollsQuestion = getPersistence().remove(pollsQuestion);

		if (listener != null) {
			listener.onAfterRemove(pollsQuestion);
		}

		return pollsQuestion;
	}

	public static com.liferay.portlet.polls.model.PollsQuestion update(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = pollsQuestion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pollsQuestion);
			}
			else {
				listener.onBeforeUpdate(pollsQuestion);
			}
		}

		pollsQuestion = getPersistence().update(pollsQuestion);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pollsQuestion);
			}
			else {
				listener.onAfterUpdate(pollsQuestion);
			}
		}

		return pollsQuestion;
	}

	public static com.liferay.portlet.polls.model.PollsQuestion update(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = pollsQuestion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pollsQuestion);
			}
			else {
				listener.onBeforeUpdate(pollsQuestion);
			}
		}

		pollsQuestion = getPersistence().update(pollsQuestion, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pollsQuestion);
			}
			else {
				listener.onAfterUpdate(pollsQuestion);
			}
		}

		return pollsQuestion;
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByPrimaryKey(
		java.lang.String questionId)
		throws com.liferay.portlet.polls.NoSuchQuestionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion fetchByPrimaryKey(
		java.lang.String questionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(questionId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.polls.NoSuchQuestionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.polls.NoSuchQuestionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion[] findByGroupId_PrevAndNext(
		java.lang.String questionId, java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.polls.NoSuchQuestionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(questionId, groupId,
			obc);
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PollsQuestionPersistence getPersistence() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (PollsQuestionUtil)ctx.getBean(_UTIL);
		}

		return _util._persistence;
	}

	public void setPersistence(PollsQuestionPersistence persistence) {
		_persistence = persistence;
	}

	private static final String _UTIL = PollsQuestionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.polls.model.PollsQuestion"));
	private static Log _log = LogFactory.getLog(PollsQuestionUtil.class);
	private static PollsQuestionUtil _util;
	private PollsQuestionPersistence _persistence;
}