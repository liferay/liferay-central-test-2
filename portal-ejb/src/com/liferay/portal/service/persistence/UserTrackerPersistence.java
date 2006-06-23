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

import com.liferay.portal.NoSuchUserTrackerException;
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
 * <a href="UserTrackerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserTrackerPersistence extends BasePersistence {
	public com.liferay.portal.model.UserTracker create(String userTrackerId) {
		UserTrackerHBM userTrackerHBM = new UserTrackerHBM();
		userTrackerHBM.setNew(true);
		userTrackerHBM.setPrimaryKey(userTrackerId);

		return UserTrackerHBMUtil.model(userTrackerHBM);
	}

	public com.liferay.portal.model.UserTracker remove(String userTrackerId)
		throws NoSuchUserTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerHBM userTrackerHBM = (UserTrackerHBM)session.get(UserTrackerHBM.class,
					userTrackerId);

			if (userTrackerHBM == null) {
				_log.warn("No UserTracker exists with the primary key " +
					userTrackerId.toString());
				throw new NoSuchUserTrackerException(
					"No UserTracker exists with the primary key " +
					userTrackerId.toString());
			}

			session.delete(userTrackerHBM);
			session.flush();

			return UserTrackerHBMUtil.model(userTrackerHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserTracker update(
		com.liferay.portal.model.UserTracker userTracker)
		throws SystemException {
		Session session = null;

		try {
			if (userTracker.isNew() || userTracker.isModified()) {
				session = openSession();

				if (userTracker.isNew()) {
					UserTrackerHBM userTrackerHBM = new UserTrackerHBM();
					userTrackerHBM.setUserTrackerId(userTracker.getUserTrackerId());
					userTrackerHBM.setCompanyId(userTracker.getCompanyId());
					userTrackerHBM.setUserId(userTracker.getUserId());
					userTrackerHBM.setModifiedDate(userTracker.getModifiedDate());
					userTrackerHBM.setRemoteAddr(userTracker.getRemoteAddr());
					userTrackerHBM.setRemoteHost(userTracker.getRemoteHost());
					userTrackerHBM.setUserAgent(userTracker.getUserAgent());
					session.save(userTrackerHBM);
					session.flush();
				}
				else {
					UserTrackerHBM userTrackerHBM = (UserTrackerHBM)session.get(UserTrackerHBM.class,
							userTracker.getPrimaryKey());

					if (userTrackerHBM != null) {
						userTrackerHBM.setCompanyId(userTracker.getCompanyId());
						userTrackerHBM.setUserId(userTracker.getUserId());
						userTrackerHBM.setModifiedDate(userTracker.getModifiedDate());
						userTrackerHBM.setRemoteAddr(userTracker.getRemoteAddr());
						userTrackerHBM.setRemoteHost(userTracker.getRemoteHost());
						userTrackerHBM.setUserAgent(userTracker.getUserAgent());
						session.flush();
					}
					else {
						userTrackerHBM = new UserTrackerHBM();
						userTrackerHBM.setUserTrackerId(userTracker.getUserTrackerId());
						userTrackerHBM.setCompanyId(userTracker.getCompanyId());
						userTrackerHBM.setUserId(userTracker.getUserId());
						userTrackerHBM.setModifiedDate(userTracker.getModifiedDate());
						userTrackerHBM.setRemoteAddr(userTracker.getRemoteAddr());
						userTrackerHBM.setRemoteHost(userTracker.getRemoteHost());
						userTrackerHBM.setUserAgent(userTracker.getUserAgent());
						session.save(userTrackerHBM);
						session.flush();
					}
				}

				userTracker.setNew(false);
				userTracker.setModified(false);
			}

			return userTracker;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserTracker findByPrimaryKey(
		String userTrackerId)
		throws NoSuchUserTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerHBM userTrackerHBM = (UserTrackerHBM)session.get(UserTrackerHBM.class,
					userTrackerId);

			if (userTrackerHBM == null) {
				_log.warn("No UserTracker exists with the primary key " +
					userTrackerId.toString());
				throw new NoSuchUserTrackerException(
					"No UserTracker exists with the primary key " +
					userTrackerId.toString());
			}

			return UserTrackerHBMUtil.model(userTrackerHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				list.add(UserTrackerHBMUtil.model(userTrackerHBM));
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

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				list.add(UserTrackerHBMUtil.model(userTrackerHBM));
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

	public com.liferay.portal.model.UserTracker findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No UserTracker exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserTrackerException(msg);
		}
		else {
			return (com.liferay.portal.model.UserTracker)list.get(0);
		}
	}

	public com.liferay.portal.model.UserTracker findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No UserTracker exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserTrackerException(msg);
		}
		else {
			return (com.liferay.portal.model.UserTracker)list.get(0);
		}
	}

	public com.liferay.portal.model.UserTracker[] findByCompanyId_PrevAndNext(
		String userTrackerId, String companyId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		com.liferay.portal.model.UserTracker userTracker = findByPrimaryKey(userTrackerId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker, UserTrackerHBMUtil.getInstance());
			com.liferay.portal.model.UserTracker[] array = new com.liferay.portal.model.UserTracker[3];
			array[0] = (com.liferay.portal.model.UserTracker)objArray[0];
			array[1] = (com.liferay.portal.model.UserTracker)objArray[1];
			array[2] = (com.liferay.portal.model.UserTracker)objArray[2];

			return array;
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
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				list.add(UserTrackerHBMUtil.model(userTrackerHBM));
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
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				list.add(UserTrackerHBMUtil.model(userTrackerHBM));
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

	public com.liferay.portal.model.UserTracker findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No UserTracker exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserTrackerException(msg);
		}
		else {
			return (com.liferay.portal.model.UserTracker)list.get(0);
		}
	}

	public com.liferay.portal.model.UserTracker findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No UserTracker exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserTrackerException(msg);
		}
		else {
			return (com.liferay.portal.model.UserTracker)list.get(0);
		}
	}

	public com.liferay.portal.model.UserTracker[] findByUserId_PrevAndNext(
		String userTrackerId, String userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		com.liferay.portal.model.UserTracker userTracker = findByPrimaryKey(userTrackerId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker, UserTrackerHBMUtil.getInstance());
			com.liferay.portal.model.UserTracker[] array = new com.liferay.portal.model.UserTracker[3];
			array[0] = (com.liferay.portal.model.UserTracker)objArray[0];
			array[1] = (com.liferay.portal.model.UserTracker)objArray[1];
			array[2] = (com.liferay.portal.model.UserTracker)objArray[2];

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
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				list.add(UserTrackerHBMUtil.model(userTrackerHBM));
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				session.delete(userTrackerHBM);
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

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserTrackerHBM userTrackerHBM = (UserTrackerHBM)itr.next();
				session.delete(userTrackerHBM);
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM UserTracker IN CLASS com.liferay.portal.service.persistence.UserTrackerHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	private static Log _log = LogFactory.getLog(UserTrackerPersistence.class);
}