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

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsChoicePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsChoicePersistence extends BasePersistence {
	public PollsChoice create(PollsChoicePK pollsChoicePK) {
		PollsChoice pollsChoice = new PollsChoice();
		pollsChoice.setNew(true);
		pollsChoice.setPrimaryKey(pollsChoicePK);

		return pollsChoice;
	}

	public PollsChoice remove(PollsChoicePK pollsChoicePK)
		throws NoSuchChoiceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsChoice pollsChoice = (PollsChoice)session.get(PollsChoice.class,
					pollsChoicePK);

			if (pollsChoice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsChoice exists with the primary key " +
						pollsChoicePK);
				}

				throw new NoSuchChoiceException(
					"No PollsChoice exists with the primary key " +
					pollsChoicePK);
			}

			return remove(pollsChoice);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice remove(PollsChoice pollsChoice)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(pollsChoice);
			session.flush();

			return pollsChoice;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.polls.model.PollsChoice update(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws SystemException {
		return update(pollsChoice, false);
	}

	public com.liferay.portlet.polls.model.PollsChoice update(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(pollsChoice);
			}
			else {
				if (pollsChoice.isNew()) {
					session.save(pollsChoice);
				}
			}

			session.flush();
			pollsChoice.setNew(false);

			return pollsChoice;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByPrimaryKey(PollsChoicePK pollsChoicePK)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByPrimaryKey(pollsChoicePK);

		if (pollsChoice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsChoice exists with the primary key " +
					pollsChoicePK);
			}

			throw new NoSuchChoiceException(
				"No PollsChoice exists with the primary key " + pollsChoicePK);
		}

		return pollsChoice;
	}

	public PollsChoice fetchByPrimaryKey(PollsChoicePK pollsChoicePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PollsChoice)session.get(PollsChoice.class, pollsChoicePK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQuestionId(String questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("choiceId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQuestionId(String questionId, int begin, int end)
		throws SystemException {
		return findByQuestionId(questionId, begin, end, null);
	}

	public List findByQuestionId(String questionId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("choiceId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByQuestionId_First(String questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		List list = findByQuestionId(questionId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PollsChoice exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "questionId=";
			msg += questionId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchChoiceException(msg);
		}
		else {
			return (PollsChoice)list.get(0);
		}
	}

	public PollsChoice findByQuestionId_Last(String questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		int count = countByQuestionId(questionId);
		List list = findByQuestionId(questionId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PollsChoice exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "questionId=";
			msg += questionId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchChoiceException(msg);
		}
		else {
			return (PollsChoice)list.get(0);
		}
	}

	public PollsChoice[] findByQuestionId_PrevAndNext(
		PollsChoicePK pollsChoicePK, String questionId, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByPrimaryKey(pollsChoicePK);
		int count = countByQuestionId(questionId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("choiceId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsChoice);
			PollsChoice[] array = new PollsChoice[3];
			array[0] = (PollsChoice)objArray[0];
			array[1] = (PollsChoice)objArray[1];
			array[2] = (PollsChoice)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.polls.model.PollsChoice ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("choiceId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByQuestionId(String questionId) throws SystemException {
		Iterator itr = findByQuestionId(questionId).iterator();

		while (itr.hasNext()) {
			PollsChoice pollsChoice = (PollsChoice)itr.next();
			remove(pollsChoice);
		}
	}

	public int countByQuestionId(String questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(PollsChoicePersistence.class);
}