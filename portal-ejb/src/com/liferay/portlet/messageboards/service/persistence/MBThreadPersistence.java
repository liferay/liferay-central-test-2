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
import com.liferay.portlet.messageboards.model.MBThread;

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
 * <a href="MBThreadPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBThreadPersistence extends BasePersistence {
	public MBThread create(String threadId) {
		MBThread mbThread = new MBThread();
		mbThread.setNew(true);
		mbThread.setPrimaryKey(threadId);

		return mbThread;
	}

	public MBThread remove(String threadId)
		throws NoSuchThreadException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBThread mbThread = (MBThread)session.get(MBThread.class, threadId);

			if (mbThread == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBThread exists with the primary key " +
						threadId.toString());
				}

				throw new NoSuchThreadException(
					"No MBThread exists with the primary key " +
					threadId.toString());
			}

			return remove(mbThread);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBThread remove(MBThread mbThread) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(mbThread);
			session.flush();

			return mbThread;
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
		return update(mbThread, false);
	}

	public com.liferay.portlet.messageboards.model.MBThread update(
		com.liferay.portlet.messageboards.model.MBThread mbThread,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(mbThread);
			}
			else {
				if (mbThread.isNew()) {
					session.save(mbThread);
				}
			}

			session.flush();
			mbThread.setNew(false);

			return mbThread;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBThread findByPrimaryKey(String threadId)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = fetchByPrimaryKey(threadId);

		if (mbThread == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBThread exists with the primary key " +
					threadId.toString());
			}

			throw new NoSuchThreadException(
				"No MBThread exists with the primary key " +
				threadId.toString());
		}

		return mbThread;
	}

	public MBThread fetchByPrimaryKey(String threadId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBThread)session.get(MBThread.class, threadId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBThread WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("priority DESC").append(", ");
			query.append("lastPostDate DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public List findByCategoryId(String categoryId, int begin, int end)
		throws SystemException {
		return findByCategoryId(categoryId, begin, end, null);
	}

	public List findByCategoryId(String categoryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBThread WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority DESC").append(", ");
				query.append("lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public MBThread findByCategoryId_First(String categoryId,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		List list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBThread exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchThreadException(msg);
		}
		else {
			return (MBThread)list.get(0);
		}
	}

	public MBThread findByCategoryId_Last(String categoryId,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		int count = countByCategoryId(categoryId);
		List list = findByCategoryId(categoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBThread exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchThreadException(msg);
		}
		else {
			return (MBThread)list.get(0);
		}
	}

	public MBThread[] findByCategoryId_PrevAndNext(String threadId,
		String categoryId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = findByPrimaryKey(threadId);
		int count = countByCategoryId(categoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBThread WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority DESC").append(", ");
				query.append("lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbThread);
			MBThread[] array = new MBThread[3];
			array[0] = (MBThread)objArray[0];
			array[1] = (MBThread)objArray[1];
			array[2] = (MBThread)objArray[2];

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
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBThread ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority DESC").append(", ");
				query.append("lastPostDate DESC");
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

	public void removeByCategoryId(String categoryId) throws SystemException {
		Iterator itr = findByCategoryId(categoryId).iterator();

		while (itr.hasNext()) {
			MBThread mbThread = (MBThread)itr.next();
			remove(mbThread);
		}
	}

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBThread WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	private static Log _log = LogFactory.getLog(MBThreadPersistence.class);
}