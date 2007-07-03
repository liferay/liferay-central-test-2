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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsChoicePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsChoicePersistenceImpl extends BasePersistence
	implements PollsChoicePersistence {
	public PollsChoice create(long choiceId) {
		PollsChoice pollsChoice = new PollsChoiceImpl();
		pollsChoice.setNew(true);
		pollsChoice.setPrimaryKey(choiceId);

		return pollsChoice;
	}

	public PollsChoice remove(long choiceId)
		throws NoSuchChoiceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsChoice pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
					new Long(choiceId));

			if (pollsChoice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsChoice exists with the primary key " +
						choiceId);
				}

				throw new NoSuchChoiceException(
					"No PollsChoice exists with the primary key " + choiceId);
			}

			return remove(pollsChoice);
		}
		catch (NoSuchChoiceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByPrimaryKey(long choiceId)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByPrimaryKey(choiceId);

		if (pollsChoice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsChoice exists with the primary key " +
					choiceId);
			}

			throw new NoSuchChoiceException(
				"No PollsChoice exists with the primary key " + choiceId);
		}

		return pollsChoice;
	}

	public PollsChoice fetchByPrimaryKey(long choiceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PollsChoice)session.get(PollsChoiceImpl.class,
				new Long(choiceId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQuestionId(long questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");
			query.append("questionId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("questionId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQuestionId(long questionId, int begin, int end)
		throws SystemException {
		return findByQuestionId(questionId, begin, end, null);
	}

	public List findByQuestionId(long questionId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("questionId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByQuestionId_First(long questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		List list = findByQuestionId(questionId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsChoice exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return (PollsChoice)list.get(0);
		}
	}

	public PollsChoice findByQuestionId_Last(long questionId,
		OrderByComparator obc) throws NoSuchChoiceException, SystemException {
		int count = countByQuestionId(questionId);
		List list = findByQuestionId(questionId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsChoice exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchChoiceException(msg.toString());
		}
		else {
			return (PollsChoice)list.get(0);
		}
	}

	public PollsChoice[] findByQuestionId_PrevAndNext(long choiceId,
		long questionId, OrderByComparator obc)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);
		int count = countByQuestionId(questionId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("questionId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsChoice);
			PollsChoice[] array = new PollsChoiceImpl[3];
			array[0] = (PollsChoice)objArray[0];
			array[1] = (PollsChoice)objArray[1];
			array[2] = (PollsChoice)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsChoice findByQ_N(long questionId, String name)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = fetchByQ_N(questionId, name);

		if (pollsChoice == null) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsChoice exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(", ");
			msg.append("name=");
			msg.append(name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchChoiceException(msg.toString());
		}

		return pollsChoice;
	}

	public PollsChoice fetchByQ_N(long questionId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");
			query.append("questionId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("questionId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			if (name != null) {
				q.setString(queryPos++, name);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			PollsChoice pollsChoice = (PollsChoice)list.get(0);

			return pollsChoice;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.polls.model.PollsChoice ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("questionId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByQuestionId(long questionId) throws SystemException {
		Iterator itr = findByQuestionId(questionId).iterator();

		while (itr.hasNext()) {
			PollsChoice pollsChoice = (PollsChoice)itr.next();
			remove(pollsChoice);
		}
	}

	public void removeByQ_N(long questionId, String name)
		throws NoSuchChoiceException, SystemException {
		PollsChoice pollsChoice = findByQ_N(questionId, name);
		remove(pollsChoice);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((PollsChoice)itr.next());
		}
	}

	public int countByQuestionId(long questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByQ_N(long questionId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsChoice WHERE ");
			query.append("questionId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			if (name != null) {
				q.setString(queryPos++, name);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.polls.model.PollsChoice");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(PollsChoicePersistenceImpl.class);
}