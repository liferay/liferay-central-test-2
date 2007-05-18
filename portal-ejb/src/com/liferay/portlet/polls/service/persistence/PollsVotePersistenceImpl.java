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

import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsVotePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsVotePersistenceImpl extends BasePersistence
	implements PollsVotePersistence {
	public PollsVote create(PollsVotePK pollsVotePK) {
		PollsVote pollsVote = new PollsVoteImpl();
		pollsVote.setNew(true);
		pollsVote.setPrimaryKey(pollsVotePK);

		return pollsVote;
	}

	public PollsVote remove(PollsVotePK pollsVotePK)
		throws NoSuchVoteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsVote pollsVote = (PollsVote)session.get(PollsVoteImpl.class,
					pollsVotePK);

			if (pollsVote == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsVote exists with the primary key " +
						pollsVotePK);
				}

				throw new NoSuchVoteException(
					"No PollsVote exists with the primary key " + pollsVotePK);
			}

			return remove(pollsVote);
		}
		catch (NoSuchVoteException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsVote remove(PollsVote pollsVote) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(pollsVote);
			session.flush();

			return pollsVote;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.polls.model.PollsVote update(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws SystemException {
		return update(pollsVote, false);
	}

	public com.liferay.portlet.polls.model.PollsVote update(
		com.liferay.portlet.polls.model.PollsVote pollsVote,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(pollsVote);
			}
			else {
				if (pollsVote.isNew()) {
					session.save(pollsVote);
				}
			}

			session.flush();
			pollsVote.setNew(false);

			return pollsVote;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsVote findByPrimaryKey(PollsVotePK pollsVotePK)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = fetchByPrimaryKey(pollsVotePK);

		if (pollsVote == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsVote exists with the primary key " +
					pollsVotePK);
			}

			throw new NoSuchVoteException(
				"No PollsVote exists with the primary key " + pollsVotePK);
		}

		return pollsVote;
	}

	public PollsVote fetchByPrimaryKey(PollsVotePK pollsVotePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PollsVote)session.get(PollsVoteImpl.class, pollsVotePK);
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
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	public PollsVote findByQuestionId_First(long questionId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		List list = findByQuestionId(questionId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsVote exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote findByQuestionId_Last(long questionId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		int count = countByQuestionId(questionId);
		List list = findByQuestionId(questionId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsVote exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote[] findByQuestionId_PrevAndNext(PollsVotePK pollsVotePK,
		long questionId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByPrimaryKey(pollsVotePK);
		int count = countByQuestionId(questionId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsVote);
			PollsVote[] array = new PollsVoteImpl[3];
			array[0] = (PollsVote)objArray[0];
			array[1] = (PollsVote)objArray[1];
			array[2] = (PollsVote)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQ_C(long questionId, String choiceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQ_C(long questionId, String choiceId, int begin, int end)
		throws SystemException {
		return findByQ_C(questionId, choiceId, begin, end, null);
	}

	public List findByQ_C(long questionId, String choiceId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsVote findByQ_C_First(long questionId, String choiceId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		List list = findByQ_C(questionId, choiceId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsVote exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(", ");
			msg.append("choiceId=");
			msg.append(choiceId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote findByQ_C_Last(long questionId, String choiceId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		int count = countByQ_C(questionId, choiceId);
		List list = findByQ_C(questionId, choiceId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PollsVote exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("questionId=");
			msg.append(questionId);
			msg.append(", ");
			msg.append("choiceId=");
			msg.append(choiceId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchVoteException(msg.toString());
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote[] findByQ_C_PrevAndNext(PollsVotePK pollsVotePK,
		long questionId, String choiceId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByPrimaryKey(pollsVotePK);
		int count = countByQ_C(questionId, choiceId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsVote);
			PollsVote[] array = new PollsVoteImpl[3];
			array[0] = (PollsVote)objArray[0];
			array[1] = (PollsVote)objArray[1];
			array[2] = (PollsVote)objArray[2];

			return array;
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
			query.append("FROM com.liferay.portlet.polls.model.PollsVote ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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
			PollsVote pollsVote = (PollsVote)itr.next();
			remove(pollsVote);
		}
	}

	public void removeByQ_C(long questionId, String choiceId)
		throws SystemException {
		Iterator itr = findByQ_C(questionId, choiceId).iterator();

		while (itr.hasNext()) {
			PollsVote pollsVote = (PollsVote)itr.next();
			remove(pollsVote);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((PollsVote)itr.next());
		}
	}

	public int countByQuestionId(long questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	public int countByQ_C(long questionId, String choiceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");
			query.append("questionId = ?");
			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, questionId);

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
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
			query.append("FROM com.liferay.portlet.polls.model.PollsVote");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	private static Log _log = LogFactory.getLog(PollsVotePersistenceImpl.class);
}