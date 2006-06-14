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

import com.liferay.portlet.polls.NoSuchQuestionException;

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
 * <a href="PollsQuestionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsQuestionPersistence extends BasePersistence {
	public com.liferay.portlet.polls.model.PollsQuestion create(
		String questionId) {
		PollsQuestionHBM pollsQuestionHBM = new PollsQuestionHBM();
		pollsQuestionHBM.setNew(true);
		pollsQuestionHBM.setPrimaryKey(questionId);

		return PollsQuestionHBMUtil.model(pollsQuestionHBM);
	}

	public com.liferay.portlet.polls.model.PollsQuestion remove(
		String questionId) throws NoSuchQuestionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)session.get(PollsQuestionHBM.class,
					questionId);

			if (pollsQuestionHBM == null) {
				_log.warn("No PollsQuestion exists with the primary key " +
					questionId.toString());
				throw new NoSuchQuestionException(
					"No PollsQuestion exists with the primary key " +
					questionId.toString());
			}

			session.delete(pollsQuestionHBM);
			session.flush();

			return PollsQuestionHBMUtil.model(pollsQuestionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.polls.model.PollsQuestion update(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion)
		throws SystemException {
		Session session = null;

		try {
			if (pollsQuestion.isNew() || pollsQuestion.isModified()) {
				session = openSession();

				if (pollsQuestion.isNew()) {
					PollsQuestionHBM pollsQuestionHBM = new PollsQuestionHBM();
					pollsQuestionHBM.setQuestionId(pollsQuestion.getQuestionId());
					pollsQuestionHBM.setGroupId(pollsQuestion.getGroupId());
					pollsQuestionHBM.setCompanyId(pollsQuestion.getCompanyId());
					pollsQuestionHBM.setUserId(pollsQuestion.getUserId());
					pollsQuestionHBM.setUserName(pollsQuestion.getUserName());
					pollsQuestionHBM.setCreateDate(pollsQuestion.getCreateDate());
					pollsQuestionHBM.setModifiedDate(pollsQuestion.getModifiedDate());
					pollsQuestionHBM.setTitle(pollsQuestion.getTitle());
					pollsQuestionHBM.setDescription(pollsQuestion.getDescription());
					pollsQuestionHBM.setExpirationDate(pollsQuestion.getExpirationDate());
					pollsQuestionHBM.setLastVoteDate(pollsQuestion.getLastVoteDate());
					session.save(pollsQuestionHBM);
					session.flush();
				}
				else {
					PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)session.get(PollsQuestionHBM.class,
							pollsQuestion.getPrimaryKey());

					if (pollsQuestionHBM != null) {
						pollsQuestionHBM.setGroupId(pollsQuestion.getGroupId());
						pollsQuestionHBM.setCompanyId(pollsQuestion.getCompanyId());
						pollsQuestionHBM.setUserId(pollsQuestion.getUserId());
						pollsQuestionHBM.setUserName(pollsQuestion.getUserName());
						pollsQuestionHBM.setCreateDate(pollsQuestion.getCreateDate());
						pollsQuestionHBM.setModifiedDate(pollsQuestion.getModifiedDate());
						pollsQuestionHBM.setTitle(pollsQuestion.getTitle());
						pollsQuestionHBM.setDescription(pollsQuestion.getDescription());
						pollsQuestionHBM.setExpirationDate(pollsQuestion.getExpirationDate());
						pollsQuestionHBM.setLastVoteDate(pollsQuestion.getLastVoteDate());
						session.flush();
					}
					else {
						pollsQuestionHBM = new PollsQuestionHBM();
						pollsQuestionHBM.setQuestionId(pollsQuestion.getQuestionId());
						pollsQuestionHBM.setGroupId(pollsQuestion.getGroupId());
						pollsQuestionHBM.setCompanyId(pollsQuestion.getCompanyId());
						pollsQuestionHBM.setUserId(pollsQuestion.getUserId());
						pollsQuestionHBM.setUserName(pollsQuestion.getUserName());
						pollsQuestionHBM.setCreateDate(pollsQuestion.getCreateDate());
						pollsQuestionHBM.setModifiedDate(pollsQuestion.getModifiedDate());
						pollsQuestionHBM.setTitle(pollsQuestion.getTitle());
						pollsQuestionHBM.setDescription(pollsQuestion.getDescription());
						pollsQuestionHBM.setExpirationDate(pollsQuestion.getExpirationDate());
						pollsQuestionHBM.setLastVoteDate(pollsQuestion.getLastVoteDate());
						session.save(pollsQuestionHBM);
						session.flush();
					}
				}

				pollsQuestion.setNew(false);
				pollsQuestion.setModified(false);
			}

			return pollsQuestion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.polls.model.PollsQuestion findByPrimaryKey(
		String questionId) throws NoSuchQuestionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)session.get(PollsQuestionHBM.class,
					questionId);

			if (pollsQuestionHBM == null) {
				_log.warn("No PollsQuestion exists with the primary key " +
					questionId.toString());
				throw new NoSuchQuestionException(
					"No PollsQuestion exists with the primary key " +
					questionId.toString());
			}

			return PollsQuestionHBMUtil.model(pollsQuestionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PollsQuestion IN CLASS com.liferay.portlet.polls.service.persistence.PollsQuestionHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)itr.next();
				list.add(PollsQuestionHBMUtil.model(pollsQuestionHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PollsQuestion IN CLASS com.liferay.portlet.polls.service.persistence.PollsQuestionHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)itr.next();
				list.add(PollsQuestionHBMUtil.model(pollsQuestionHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.polls.model.PollsQuestion findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PollsQuestion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchQuestionException(msg);
		}
		else {
			return (com.liferay.portlet.polls.model.PollsQuestion)list.get(0);
		}
	}

	public com.liferay.portlet.polls.model.PollsQuestion findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PollsQuestion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchQuestionException(msg);
		}
		else {
			return (com.liferay.portlet.polls.model.PollsQuestion)list.get(0);
		}
	}

	public com.liferay.portlet.polls.model.PollsQuestion[] findByGroupId_PrevAndNext(
		String questionId, String groupId, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion = findByPrimaryKey(questionId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PollsQuestion IN CLASS com.liferay.portlet.polls.service.persistence.PollsQuestionHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsQuestion, PollsQuestionHBMUtil.getInstance());
			com.liferay.portlet.polls.model.PollsQuestion[] array = new com.liferay.portlet.polls.model.PollsQuestion[3];
			array[0] = (com.liferay.portlet.polls.model.PollsQuestion)objArray[0];
			array[1] = (com.liferay.portlet.polls.model.PollsQuestion)objArray[1];
			array[2] = (com.liferay.portlet.polls.model.PollsQuestion)objArray[2];

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
			query.append(
				"FROM PollsQuestion IN CLASS com.liferay.portlet.polls.service.persistence.PollsQuestionHBM ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)itr.next();
				list.add(PollsQuestionHBMUtil.model(pollsQuestionHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PollsQuestion IN CLASS com.liferay.portlet.polls.service.persistence.PollsQuestionHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PollsQuestionHBM pollsQuestionHBM = (PollsQuestionHBM)itr.next();
				session.delete(pollsQuestionHBM);
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

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM PollsQuestion IN CLASS com.liferay.portlet.polls.service.persistence.PollsQuestionHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

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

	private static Log _log = LogFactory.getLog(PollsQuestionPersistence.class);
}