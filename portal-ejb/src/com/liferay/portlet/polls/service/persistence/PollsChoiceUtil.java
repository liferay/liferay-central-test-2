/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
 * <a href="PollsChoiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsChoiceUtil {
	public static final String CLASS_NAME = PollsChoiceUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.polls.model.PollsChoice"));

	public static com.liferay.portlet.polls.model.PollsChoice create(
		com.liferay.portlet.polls.service.persistence.PollsChoicePK pollsChoicePK) {
		return getPersistence().create(pollsChoicePK);
	}

	public static com.liferay.portlet.polls.model.PollsChoice remove(
		com.liferay.portlet.polls.service.persistence.PollsChoicePK pollsChoicePK)
		throws com.liferay.portlet.polls.NoSuchChoiceException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(pollsChoicePK));
		}

		com.liferay.portlet.polls.model.PollsChoice pollsChoice = getPersistence()
																	  .remove(pollsChoicePK);

		if (listener != null) {
			listener.onAfterRemove(pollsChoice);
		}

		return pollsChoice;
	}

	public static com.liferay.portlet.polls.model.PollsChoice update(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = pollsChoice.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pollsChoice);
			}
			else {
				listener.onBeforeUpdate(pollsChoice);
			}
		}

		pollsChoice = getPersistence().update(pollsChoice);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pollsChoice);
			}
			else {
				listener.onAfterUpdate(pollsChoice);
			}
		}

		return pollsChoice;
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByPrimaryKey(
		com.liferay.portlet.polls.service.persistence.PollsChoicePK pollsChoicePK)
		throws com.liferay.portlet.polls.NoSuchChoiceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(pollsChoicePK);
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
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId(questionId, begin, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByQuestionId_First(
		java.lang.String questionId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.polls.NoSuchChoiceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId_First(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByQuestionId_Last(
		java.lang.String questionId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.polls.NoSuchChoiceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId_Last(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice[] findByQuestionId_PrevAndNext(
		com.liferay.portlet.polls.service.persistence.PollsChoicePK pollsChoicePK,
		java.lang.String questionId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.polls.NoSuchChoiceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByQuestionId_PrevAndNext(pollsChoicePK,
			questionId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByQuestionId(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	public static int countByQuestionId(java.lang.String questionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	public static PollsChoicePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		PollsChoiceUtil util = (PollsChoiceUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(PollsChoicePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(PollsChoiceUtil.class);
	private PollsChoicePersistence _persistence;
}