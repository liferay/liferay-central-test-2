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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPasswordTrackerException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

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
 * <a href="PasswordTrackerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PasswordTrackerPersistence extends BasePersistence {
	public com.liferay.portal.model.PasswordTracker create(
		String passwordTrackerId) {
		PasswordTrackerHBM passwordTrackerHBM = new PasswordTrackerHBM();
		passwordTrackerHBM.setNew(true);
		passwordTrackerHBM.setPrimaryKey(passwordTrackerId);

		return PasswordTrackerHBMUtil.model(passwordTrackerHBM);
	}

	public com.liferay.portal.model.PasswordTracker remove(
		String passwordTrackerId)
		throws NoSuchPasswordTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)session.get(PasswordTrackerHBM.class,
					passwordTrackerId);

			if (passwordTrackerHBM == null) {
				_log.warn("No PasswordTracker exists with the primary key " +
					passwordTrackerId.toString());
				throw new NoSuchPasswordTrackerException(
					"No PasswordTracker exists with the primary key " +
					passwordTrackerId.toString());
			}

			session.delete(passwordTrackerHBM);
			session.flush();

			return PasswordTrackerHBMUtil.model(passwordTrackerHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PasswordTracker update(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws SystemException {
		Session session = null;

		try {
			if (passwordTracker.isNew() || passwordTracker.isModified()) {
				session = openSession();

				if (passwordTracker.isNew()) {
					PasswordTrackerHBM passwordTrackerHBM = new PasswordTrackerHBM();
					passwordTrackerHBM.setPasswordTrackerId(passwordTracker.getPasswordTrackerId());
					passwordTrackerHBM.setUserId(passwordTracker.getUserId());
					passwordTrackerHBM.setCreateDate(passwordTracker.getCreateDate());
					passwordTrackerHBM.setPassword(passwordTracker.getPassword());
					session.save(passwordTrackerHBM);
					session.flush();
				}
				else {
					PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)session.get(PasswordTrackerHBM.class,
							passwordTracker.getPrimaryKey());

					if (passwordTrackerHBM != null) {
						passwordTrackerHBM.setUserId(passwordTracker.getUserId());
						passwordTrackerHBM.setCreateDate(passwordTracker.getCreateDate());
						passwordTrackerHBM.setPassword(passwordTracker.getPassword());
						session.flush();
					}
					else {
						passwordTrackerHBM = new PasswordTrackerHBM();
						passwordTrackerHBM.setPasswordTrackerId(passwordTracker.getPasswordTrackerId());
						passwordTrackerHBM.setUserId(passwordTracker.getUserId());
						passwordTrackerHBM.setCreateDate(passwordTracker.getCreateDate());
						passwordTrackerHBM.setPassword(passwordTracker.getPassword());
						session.save(passwordTrackerHBM);
						session.flush();
					}
				}

				passwordTracker.setNew(false);
				passwordTracker.setModified(false);
			}

			return passwordTracker;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PasswordTracker findByPrimaryKey(
		String passwordTrackerId)
		throws NoSuchPasswordTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)session.get(PasswordTrackerHBM.class,
					passwordTrackerId);

			if (passwordTrackerHBM == null) {
				_log.warn("No PasswordTracker exists with the primary key " +
					passwordTrackerId.toString());
				throw new NoSuchPasswordTrackerException(
					"No PasswordTracker exists with the primary key " +
					passwordTrackerId.toString());
			}

			return PasswordTrackerHBMUtil.model(passwordTrackerHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PasswordTracker IN CLASS com.liferay.portal.service.persistence.PasswordTrackerHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("userId DESC").append(", ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)itr.next();
				list.add(PasswordTrackerHBMUtil.model(passwordTrackerHBM));
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

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PasswordTracker IN CLASS com.liferay.portal.service.persistence.PasswordTrackerHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("userId DESC").append(", ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)itr.next();
				list.add(PasswordTrackerHBMUtil.model(passwordTrackerHBM));
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

	public com.liferay.portal.model.PasswordTracker findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchPasswordTrackerException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PasswordTracker exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPasswordTrackerException(msg);
		}
		else {
			return (com.liferay.portal.model.PasswordTracker)list.get(0);
		}
	}

	public com.liferay.portal.model.PasswordTracker findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchPasswordTrackerException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PasswordTracker exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPasswordTrackerException(msg);
		}
		else {
			return (com.liferay.portal.model.PasswordTracker)list.get(0);
		}
	}

	public com.liferay.portal.model.PasswordTracker[] findByUserId_PrevAndNext(
		String passwordTrackerId, String userId, OrderByComparator obc)
		throws NoSuchPasswordTrackerException, SystemException {
		com.liferay.portal.model.PasswordTracker passwordTracker = findByPrimaryKey(passwordTrackerId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PasswordTracker IN CLASS com.liferay.portal.service.persistence.PasswordTrackerHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("userId DESC").append(", ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					passwordTracker, PasswordTrackerHBMUtil.getInstance());
			com.liferay.portal.model.PasswordTracker[] array = new com.liferay.portal.model.PasswordTracker[3];
			array[0] = (com.liferay.portal.model.PasswordTracker)objArray[0];
			array[1] = (com.liferay.portal.model.PasswordTracker)objArray[1];
			array[2] = (com.liferay.portal.model.PasswordTracker)objArray[2];

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
				"FROM PasswordTracker IN CLASS com.liferay.portal.service.persistence.PasswordTrackerHBM ");
			query.append("ORDER BY ");
			query.append("userId DESC").append(", ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)itr.next();
				list.add(PasswordTrackerHBMUtil.model(passwordTrackerHBM));
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

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PasswordTracker IN CLASS com.liferay.portal.service.persistence.PasswordTrackerHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("userId DESC").append(", ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PasswordTrackerHBM passwordTrackerHBM = (PasswordTrackerHBM)itr.next();
				session.delete(passwordTrackerHBM);
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

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM PasswordTracker IN CLASS com.liferay.portal.service.persistence.PasswordTrackerHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	private static Log _log = LogFactory.getLog(PasswordTrackerPersistence.class);
}