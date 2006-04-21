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

import com.liferay.portal.NoSuchUserTrackerPathException;
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
 * <a href="UserTrackerPathPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserTrackerPathPersistence extends BasePersistence {
	public com.liferay.portal.model.UserTrackerPath create(
		String userTrackerPathId) {
		UserTrackerPathHBM userTrackerPathHBM = new UserTrackerPathHBM();
		userTrackerPathHBM.setNew(true);
		userTrackerPathHBM.setPrimaryKey(userTrackerPathId);

		return UserTrackerPathHBMUtil.model(userTrackerPathHBM);
	}

	public com.liferay.portal.model.UserTrackerPath remove(
		String userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)session.get(UserTrackerPathHBM.class,
					userTrackerPathId);

			if (userTrackerPathHBM == null) {
				_log.warn("No UserTrackerPath exists with the primary key " +
					userTrackerPathId.toString());
				throw new NoSuchUserTrackerPathException(
					"No UserTrackerPath exists with the primary key " +
					userTrackerPathId.toString());
			}

			session.delete(userTrackerPathHBM);
			session.flush();

			return UserTrackerPathHBMUtil.model(userTrackerPathHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws SystemException {
		Session session = null;

		try {
			if (userTrackerPath.isNew() || userTrackerPath.isModified()) {
				session = openSession();

				if (userTrackerPath.isNew()) {
					UserTrackerPathHBM userTrackerPathHBM = new UserTrackerPathHBM();
					userTrackerPathHBM.setUserTrackerPathId(userTrackerPath.getUserTrackerPathId());
					userTrackerPathHBM.setUserTrackerId(userTrackerPath.getUserTrackerId());
					userTrackerPathHBM.setPath(userTrackerPath.getPath());
					userTrackerPathHBM.setPathDate(userTrackerPath.getPathDate());
					session.save(userTrackerPathHBM);
					session.flush();
				}
				else {
					UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)session.get(UserTrackerPathHBM.class,
							userTrackerPath.getPrimaryKey());

					if (userTrackerPathHBM != null) {
						userTrackerPathHBM.setUserTrackerId(userTrackerPath.getUserTrackerId());
						userTrackerPathHBM.setPath(userTrackerPath.getPath());
						userTrackerPathHBM.setPathDate(userTrackerPath.getPathDate());
						session.flush();
					}
					else {
						userTrackerPathHBM = new UserTrackerPathHBM();
						userTrackerPathHBM.setUserTrackerPathId(userTrackerPath.getUserTrackerPathId());
						userTrackerPathHBM.setUserTrackerId(userTrackerPath.getUserTrackerId());
						userTrackerPathHBM.setPath(userTrackerPath.getPath());
						userTrackerPathHBM.setPathDate(userTrackerPath.getPathDate());
						session.save(userTrackerPathHBM);
						session.flush();
					}
				}

				userTrackerPath.setNew(false);
				userTrackerPath.setModified(false);
			}

			return userTrackerPath;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserTrackerPath findByPrimaryKey(
		String userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)session.get(UserTrackerPathHBM.class,
					userTrackerPathId);

			if (userTrackerPathHBM == null) {
				_log.warn("No UserTrackerPath exists with the primary key " +
					userTrackerPathId.toString());
				throw new NoSuchUserTrackerPathException(
					"No UserTrackerPath exists with the primary key " +
					userTrackerPathId.toString());
			}

			return UserTrackerPathHBMUtil.model(userTrackerPathHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserTrackerId(String userTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTrackerPath IN CLASS com.liferay.portal.service.persistence.UserTrackerPathHBM WHERE ");
			query.append("userTrackerId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userTrackerId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)itr.next();
				list.add(UserTrackerPathHBMUtil.model(userTrackerPathHBM));
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

	public List findByUserTrackerId(String userTrackerId, int begin, int end)
		throws SystemException {
		return findByUserTrackerId(userTrackerId, begin, end, null);
	}

	public List findByUserTrackerId(String userTrackerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTrackerPath IN CLASS com.liferay.portal.service.persistence.UserTrackerPathHBM WHERE ");
			query.append("userTrackerId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userTrackerId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)itr.next();
				list.add(UserTrackerPathHBMUtil.model(userTrackerPathHBM));
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

	public com.liferay.portal.model.UserTrackerPath findByUserTrackerId_First(
		String userTrackerId, OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		List list = findByUserTrackerId(userTrackerId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No UserTrackerPath exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userTrackerId=";
			msg += userTrackerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserTrackerPathException(msg);
		}
		else {
			return (com.liferay.portal.model.UserTrackerPath)list.get(0);
		}
	}

	public com.liferay.portal.model.UserTrackerPath findByUserTrackerId_Last(
		String userTrackerId, OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		int count = countByUserTrackerId(userTrackerId);
		List list = findByUserTrackerId(userTrackerId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No UserTrackerPath exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userTrackerId=";
			msg += userTrackerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserTrackerPathException(msg);
		}
		else {
			return (com.liferay.portal.model.UserTrackerPath)list.get(0);
		}
	}

	public com.liferay.portal.model.UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		String userTrackerPathId, String userTrackerId, OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		com.liferay.portal.model.UserTrackerPath userTrackerPath = findByPrimaryKey(userTrackerPathId);
		int count = countByUserTrackerId(userTrackerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTrackerPath IN CLASS com.liferay.portal.service.persistence.UserTrackerPathHBM WHERE ");
			query.append("userTrackerId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userTrackerId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTrackerPath, UserTrackerPathHBMUtil.getInstance());
			com.liferay.portal.model.UserTrackerPath[] array = new com.liferay.portal.model.UserTrackerPath[3];
			array[0] = (com.liferay.portal.model.UserTrackerPath)objArray[0];
			array[1] = (com.liferay.portal.model.UserTrackerPath)objArray[1];
			array[2] = (com.liferay.portal.model.UserTrackerPath)objArray[2];

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
				"FROM UserTrackerPath IN CLASS com.liferay.portal.service.persistence.UserTrackerPathHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)itr.next();
				list.add(UserTrackerPathHBMUtil.model(userTrackerPathHBM));
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

	public void removeByUserTrackerId(String userTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTrackerPath IN CLASS com.liferay.portal.service.persistence.UserTrackerPathHBM WHERE ");
			query.append("userTrackerId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userTrackerId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserTrackerPathHBM userTrackerPathHBM = (UserTrackerPathHBM)itr.next();
				session.delete(userTrackerPathHBM);
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

	public int countByUserTrackerId(String userTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM UserTrackerPath IN CLASS com.liferay.portal.service.persistence.UserTrackerPathHBM WHERE ");
			query.append("userTrackerId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userTrackerId);

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

	private static Log _log = LogFactory.getLog(UserTrackerPathPersistence.class);
}