/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.NoSuchStatsUserException;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBStatsUserPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBStatsUserPersistence extends BasePersistence {
	public MBStatsUser create(MBStatsUserPK mbStatsUserPK) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();
		mbStatsUser.setNew(true);
		mbStatsUser.setPrimaryKey(mbStatsUserPK);

		return mbStatsUser;
	}

	public MBStatsUser remove(MBStatsUserPK mbStatsUserPK)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBStatsUser mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
					mbStatsUserPK);

			if (mbStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBStatsUser exists with the primary key " +
						mbStatsUserPK);
				}

				throw new NoSuchStatsUserException(
					"No MBStatsUser exists with the primary key " +
					mbStatsUserPK);
			}

			return remove(mbStatsUser);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBStatsUser remove(MBStatsUser mbStatsUser)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(mbStatsUser);
			session.flush();

			return mbStatsUser;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser update(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws SystemException {
		return update(mbStatsUser, false);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser update(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(mbStatsUser);
			}
			else {
				if (mbStatsUser.isNew()) {
					session.save(mbStatsUser);
				}
			}

			session.flush();
			mbStatsUser.setNew(false);

			return mbStatsUser;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBStatsUser findByPrimaryKey(MBStatsUserPK mbStatsUserPK)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByPrimaryKey(mbStatsUserPK);

		if (mbStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBStatsUser exists with the primary key " +
					mbStatsUserPK);
			}

			throw new NoSuchStatsUserException(
				"No MBStatsUser exists with the primary key " + mbStatsUserPK);
		}

		return mbStatsUser;
	}

	public MBStatsUser fetchByPrimaryKey(MBStatsUserPK mbStatsUserPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBStatsUser)session.get(MBStatsUserImpl.class, mbStatsUserPK);
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
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public MBStatsUser findByGroupId_First(String groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBStatsUser exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStatsUserException(msg);
		}
		else {
			return (MBStatsUser)list.get(0);
		}
	}

	public MBStatsUser findByGroupId_Last(String groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBStatsUser exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStatsUserException(msg);
		}
		else {
			return (MBStatsUser)list.get(0);
		}
	}

	public MBStatsUser[] findByGroupId_PrevAndNext(
		MBStatsUserPK mbStatsUserPK, String groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(mbStatsUserPK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser);
			MBStatsUser[] array = new MBStatsUserImpl[3];
			array[0] = (MBStatsUser)objArray[0];
			array[1] = (MBStatsUser)objArray[1];
			array[2] = (MBStatsUser)objArray[2];

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
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public MBStatsUser findByUserId_First(String userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBStatsUser exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStatsUserException(msg);
		}
		else {
			return (MBStatsUser)list.get(0);
		}
	}

	public MBStatsUser findByUserId_Last(String userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBStatsUser exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStatsUserException(msg);
		}
		else {
			return (MBStatsUser)list.get(0);
		}
	}

	public MBStatsUser[] findByUserId_PrevAndNext(MBStatsUserPK mbStatsUserPK,
		String userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(mbStatsUserPK);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser);
			MBStatsUser[] array = new MBStatsUserImpl[3];
			array[0] = (MBStatsUser)objArray[0];
			array[1] = (MBStatsUser)objArray[1];
			array[2] = (MBStatsUser)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_M(String groupId, int messageCount)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("messageCount != ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_M(String groupId, int messageCount, int begin, int end)
		throws SystemException {
		return findByG_M(groupId, messageCount, begin, end, null);
	}

	public List findByG_M(String groupId, int messageCount, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("messageCount != ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBStatsUser findByG_M_First(String groupId, int messageCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List list = findByG_M(groupId, messageCount, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBStatsUser exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "messageCount=";
			msg += messageCount;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStatsUserException(msg);
		}
		else {
			return (MBStatsUser)list.get(0);
		}
	}

	public MBStatsUser findByG_M_Last(String groupId, int messageCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		int count = countByG_M(groupId, messageCount);
		List list = findByG_M(groupId, messageCount, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBStatsUser exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "messageCount=";
			msg += messageCount;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStatsUserException(msg);
		}
		else {
			return (MBStatsUser)list.get(0);
		}
	}

	public MBStatsUser[] findByG_M_PrevAndNext(MBStatsUserPK mbStatsUserPK,
		String groupId, int messageCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(mbStatsUserPK);
		int count = countByG_M(groupId, messageCount);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("messageCount != ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser);
			MBStatsUser[] array = new MBStatsUserImpl[3];
			array[0] = (MBStatsUser)objArray[0];
			array[1] = (MBStatsUser)objArray[1];
			array[2] = (MBStatsUser)objArray[2];

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
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
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

	public void removeByGroupId(String groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			MBStatsUser mbStatsUser = (MBStatsUser)itr.next();
			remove(mbStatsUser);
		}
	}

	public void removeByUserId(String userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			MBStatsUser mbStatsUser = (MBStatsUser)itr.next();
			remove(mbStatsUser);
		}
	}

	public void removeByG_M(String groupId, int messageCount)
		throws SystemException {
		Iterator itr = findByG_M(groupId, messageCount).iterator();

		while (itr.hasNext()) {
			MBStatsUser mbStatsUser = (MBStatsUser)itr.next();
			remove(mbStatsUser);
		}
	}

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	public int countByG_M(String groupId, int messageCount)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("messageCount != ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

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

	private static Log _log = LogFactory.getLog(MBStatsUserPersistence.class);
}