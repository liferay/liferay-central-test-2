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

import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsVote;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsVotePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsVotePersistence extends BasePersistence {
	public PollsVote create(PollsVotePK pollsVotePK) {
		PollsVote pollsVote = new PollsVote();
		pollsVote.setNew(true);
		pollsVote.setPrimaryKey(pollsVotePK);

		return pollsVote;
	}

	public PollsVote remove(PollsVotePK pollsVotePK)
		throws NoSuchVoteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsVote pollsVote = (PollsVote)session.get(PollsVote.class,
					pollsVotePK);

			if (pollsVote == null) {
				_log.warn("No PollsVote exists with the primary key " +
					pollsVotePK.toString());
				throw new NoSuchVoteException(
					"No PollsVote exists with the primary key " +
					pollsVotePK.toString());
			}

			session.delete(pollsVote);
			session.flush();

			return pollsVote;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.polls.model.PollsVote update(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws SystemException {
		Session session = null;

		try {
			if (pollsVote.isNew() || pollsVote.isModified()) {
				session = openSession();

				if (pollsVote.isNew()) {
					PollsVote pollsVoteModel = new PollsVote();
					pollsVoteModel.setQuestionId(pollsVote.getQuestionId());
					pollsVoteModel.setUserId(pollsVote.getUserId());
					pollsVoteModel.setChoiceId(pollsVote.getChoiceId());
					pollsVoteModel.setVoteDate(pollsVote.getVoteDate());
					session.save(pollsVoteModel);
					session.flush();
				}
				else {
					PollsVote pollsVoteModel = (PollsVote)session.get(PollsVote.class,
							pollsVote.getPrimaryKey());

					if (pollsVoteModel != null) {
						pollsVoteModel.setChoiceId(pollsVote.getChoiceId());
						pollsVoteModel.setVoteDate(pollsVote.getVoteDate());
						session.flush();
					}
					else {
						pollsVoteModel = new PollsVote();
						pollsVoteModel.setQuestionId(pollsVote.getQuestionId());
						pollsVoteModel.setUserId(pollsVote.getUserId());
						pollsVoteModel.setChoiceId(pollsVote.getChoiceId());
						pollsVoteModel.setVoteDate(pollsVote.getVoteDate());
						session.save(pollsVoteModel);
						session.flush();
					}
				}

				pollsVote.setNew(false);
				pollsVote.setModified(false);
			}

			return pollsVote;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsVote findByPrimaryKey(PollsVotePK pollsVotePK)
		throws NoSuchVoteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsVote pollsVote = (PollsVote)session.get(PollsVote.class,
					pollsVotePK);

			if (pollsVote == null) {
				_log.warn("No PollsVote exists with the primary key " +
					pollsVotePK.toString());
				throw new NoSuchVoteException(
					"No PollsVote exists with the primary key " +
					pollsVotePK.toString());
			}

			return pollsVote;
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
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			List list = q.list();

			return list;
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
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

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

			Query q = session.createQuery(query.toString());
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

	public PollsVote findByQuestionId_First(String questionId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		List list = findByQuestionId(questionId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PollsVote exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "questionId=";
			msg += questionId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchVoteException(msg);
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote findByQuestionId_Last(String questionId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		int count = countByQuestionId(questionId);
		List list = findByQuestionId(questionId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PollsVote exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "questionId=";
			msg += questionId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchVoteException(msg);
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote[] findByQuestionId_PrevAndNext(PollsVotePK pollsVotePK,
		String questionId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByPrimaryKey(pollsVotePK);
		int count = countByQuestionId(questionId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

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

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsVote);
			PollsVote[] array = new PollsVote[3];
			array[0] = (PollsVote)objArray[0];
			array[1] = (PollsVote)objArray[1];
			array[2] = (PollsVote)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQ_C(String questionId, String choiceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
			}

			List list = q.list();

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByQ_C(String questionId, String choiceId, int begin, int end)
		throws SystemException {
		return findByQ_C(questionId, choiceId, begin, end, null);
	}

	public List findByQ_C(String questionId, String choiceId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
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

	public PollsVote findByQ_C_First(String questionId, String choiceId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		List list = findByQ_C(questionId, choiceId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PollsVote exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "questionId=";
			msg += questionId;
			msg += ", ";
			msg += "choiceId=";
			msg += choiceId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchVoteException(msg);
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote findByQ_C_Last(String questionId, String choiceId,
		OrderByComparator obc) throws NoSuchVoteException, SystemException {
		int count = countByQ_C(questionId, choiceId);
		List list = findByQ_C(questionId, choiceId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PollsVote exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "questionId=";
			msg += questionId;
			msg += ", ";
			msg += "choiceId=";
			msg += choiceId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchVoteException(msg);
		}
		else {
			return (PollsVote)list.get(0);
		}
	}

	public PollsVote[] findByQ_C_PrevAndNext(PollsVotePK pollsVotePK,
		String questionId, String choiceId, OrderByComparator obc)
		throws NoSuchVoteException, SystemException {
		PollsVote pollsVote = findByPrimaryKey(pollsVotePK);
		int count = countByQ_C(questionId, choiceId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsVote);
			PollsVote[] array = new PollsVote[3];
			array[0] = (PollsVote)objArray[0];
			array[1] = (PollsVote)objArray[1];
			array[2] = (PollsVote)objArray[2];

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
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.polls.model.PollsVote ");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByQuestionId(String questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PollsVote pollsVote = (PollsVote)itr.next();
				session.delete(pollsVote);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByQ_C(String questionId, String choiceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

			if (choiceId != null) {
				q.setString(queryPos++, choiceId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PollsVote pollsVote = (PollsVote)itr.next();
				session.delete(pollsVote);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByQuestionId(String questionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	public int countByQ_C(String questionId, String choiceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.polls.model.PollsVote WHERE ");

			if (questionId == null) {
				query.append("questionId IS NULL");
			}
			else {
				query.append("questionId = ?");
			}

			query.append(" AND ");

			if (choiceId == null) {
				query.append("choiceId IS NULL");
			}
			else {
				query.append("choiceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (questionId != null) {
				q.setString(queryPos++, questionId);
			}

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(PollsVotePersistence.class);
}