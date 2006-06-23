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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.NoSuchThreadException;

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
 * <a href="MBThreadPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBThreadPersistence extends BasePersistence {
	public com.liferay.portlet.messageboards.model.MBThread create(
		String threadId) {
		MBThreadHBM mbThreadHBM = new MBThreadHBM();
		mbThreadHBM.setNew(true);
		mbThreadHBM.setPrimaryKey(threadId);

		return MBThreadHBMUtil.model(mbThreadHBM);
	}

	public com.liferay.portlet.messageboards.model.MBThread remove(
		String threadId) throws NoSuchThreadException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBThreadHBM mbThreadHBM = (MBThreadHBM)session.get(MBThreadHBM.class,
					threadId);

			if (mbThreadHBM == null) {
				_log.warn("No MBThread exists with the primary key " +
					threadId.toString());
				throw new NoSuchThreadException(
					"No MBThread exists with the primary key " +
					threadId.toString());
			}

			session.delete(mbThreadHBM);
			session.flush();

			return MBThreadHBMUtil.model(mbThreadHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBThread update(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws SystemException {
		Session session = null;

		try {
			if (mbThread.isNew() || mbThread.isModified()) {
				session = openSession();

				if (mbThread.isNew()) {
					MBThreadHBM mbThreadHBM = new MBThreadHBM();
					mbThreadHBM.setThreadId(mbThread.getThreadId());
					mbThreadHBM.setRootMessageId(mbThread.getRootMessageId());
					mbThreadHBM.setTopicId(mbThread.getTopicId());
					mbThreadHBM.setMessageCount(mbThread.getMessageCount());
					mbThreadHBM.setLastPostDate(mbThread.getLastPostDate());
					session.save(mbThreadHBM);
					session.flush();
				}
				else {
					MBThreadHBM mbThreadHBM = (MBThreadHBM)session.get(MBThreadHBM.class,
							mbThread.getPrimaryKey());

					if (mbThreadHBM != null) {
						mbThreadHBM.setRootMessageId(mbThread.getRootMessageId());
						mbThreadHBM.setTopicId(mbThread.getTopicId());
						mbThreadHBM.setMessageCount(mbThread.getMessageCount());
						mbThreadHBM.setLastPostDate(mbThread.getLastPostDate());
						session.flush();
					}
					else {
						mbThreadHBM = new MBThreadHBM();
						mbThreadHBM.setThreadId(mbThread.getThreadId());
						mbThreadHBM.setRootMessageId(mbThread.getRootMessageId());
						mbThreadHBM.setTopicId(mbThread.getTopicId());
						mbThreadHBM.setMessageCount(mbThread.getMessageCount());
						mbThreadHBM.setLastPostDate(mbThread.getLastPostDate());
						session.save(mbThreadHBM);
						session.flush();
					}
				}

				mbThread.setNew(false);
				mbThread.setModified(false);
			}

			return mbThread;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBThread findByPrimaryKey(
		String threadId) throws NoSuchThreadException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBThreadHBM mbThreadHBM = (MBThreadHBM)session.get(MBThreadHBM.class,
					threadId);

			if (mbThreadHBM == null) {
				_log.warn("No MBThread exists with the primary key " +
					threadId.toString());
				throw new NoSuchThreadException(
					"No MBThread exists with the primary key " +
					threadId.toString());
			}

			return MBThreadHBMUtil.model(mbThreadHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByTopicId(String topicId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBThread IN CLASS com.liferay.portlet.messageboards.service.persistence.MBThreadHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("lastPostDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBThreadHBM mbThreadHBM = (MBThreadHBM)itr.next();
				list.add(MBThreadHBMUtil.model(mbThreadHBM));
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

	public List findByTopicId(String topicId, int begin, int end)
		throws SystemException {
		return findByTopicId(topicId, begin, end, null);
	}

	public List findByTopicId(String topicId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBThread IN CLASS com.liferay.portlet.messageboards.service.persistence.MBThreadHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBThreadHBM mbThreadHBM = (MBThreadHBM)itr.next();
				list.add(MBThreadHBMUtil.model(mbThreadHBM));
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

	public com.liferay.portlet.messageboards.model.MBThread findByTopicId_First(
		String topicId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		List list = findByTopicId(topicId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBThread exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchThreadException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBThread)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBThread findByTopicId_Last(
		String topicId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		int count = countByTopicId(topicId);
		List list = findByTopicId(topicId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBThread exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchThreadException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBThread)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBThread[] findByTopicId_PrevAndNext(
		String threadId, String topicId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		com.liferay.portlet.messageboards.model.MBThread mbThread = findByPrimaryKey(threadId);
		int count = countByTopicId(topicId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBThread IN CLASS com.liferay.portlet.messageboards.service.persistence.MBThreadHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbThread, MBThreadHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBThread[] array = new com.liferay.portlet.messageboards.model.MBThread[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBThread)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBThread)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBThread)objArray[2];

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
				"FROM MBThread IN CLASS com.liferay.portlet.messageboards.service.persistence.MBThreadHBM ");
			query.append("ORDER BY ");
			query.append("lastPostDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBThreadHBM mbThreadHBM = (MBThreadHBM)itr.next();
				list.add(MBThreadHBMUtil.model(mbThreadHBM));
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

	public void removeByTopicId(String topicId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBThread IN CLASS com.liferay.portlet.messageboards.service.persistence.MBThreadHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("lastPostDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBThreadHBM mbThreadHBM = (MBThreadHBM)itr.next();
				session.delete(mbThreadHBM);
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

	public int countByTopicId(String topicId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBThread IN CLASS com.liferay.portlet.messageboards.service.persistence.MBThreadHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
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

	private static Log _log = LogFactory.getLog(MBThreadPersistence.class);
}