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

import com.liferay.portlet.messageboards.NoSuchStatsUserException;

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
 * <a href="MBStatsUserPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBStatsUserPersistence extends BasePersistence {
	public com.liferay.portlet.messageboards.model.MBStatsUser create(
		MBStatsUserPK mbStatsUserPK) {
		MBStatsUserHBM mbStatsUserHBM = new MBStatsUserHBM();
		mbStatsUserHBM.setNew(true);
		mbStatsUserHBM.setPrimaryKey(mbStatsUserPK);

		return MBStatsUserHBMUtil.model(mbStatsUserHBM);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser remove(
		MBStatsUserPK mbStatsUserPK)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)session.get(MBStatsUserHBM.class,
					mbStatsUserPK);

			if (mbStatsUserHBM == null) {
				_log.warn("No MBStatsUser exists with the primary key " +
					mbStatsUserPK.toString());
				throw new NoSuchStatsUserException(
					"No MBStatsUser exists with the primary key " +
					mbStatsUserPK.toString());
			}

			session.delete(mbStatsUserHBM);
			session.flush();

			return MBStatsUserHBMUtil.model(mbStatsUserHBM);
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
		Session session = null;

		try {
			if (mbStatsUser.isNew() || mbStatsUser.isModified()) {
				session = openSession();

				if (mbStatsUser.isNew()) {
					MBStatsUserHBM mbStatsUserHBM = new MBStatsUserHBM();
					mbStatsUserHBM.setGroupId(mbStatsUser.getGroupId());
					mbStatsUserHBM.setUserId(mbStatsUser.getUserId());
					mbStatsUserHBM.setMessageCount(mbStatsUser.getMessageCount());
					mbStatsUserHBM.setLastPostDate(mbStatsUser.getLastPostDate());
					session.save(mbStatsUserHBM);
					session.flush();
				}
				else {
					MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)session.get(MBStatsUserHBM.class,
							mbStatsUser.getPrimaryKey());

					if (mbStatsUserHBM != null) {
						mbStatsUserHBM.setMessageCount(mbStatsUser.getMessageCount());
						mbStatsUserHBM.setLastPostDate(mbStatsUser.getLastPostDate());
						session.flush();
					}
					else {
						mbStatsUserHBM = new MBStatsUserHBM();
						mbStatsUserHBM.setGroupId(mbStatsUser.getGroupId());
						mbStatsUserHBM.setUserId(mbStatsUser.getUserId());
						mbStatsUserHBM.setMessageCount(mbStatsUser.getMessageCount());
						mbStatsUserHBM.setLastPostDate(mbStatsUser.getLastPostDate());
						session.save(mbStatsUserHBM);
						session.flush();
					}
				}

				mbStatsUser.setNew(false);
				mbStatsUser.setModified(false);
			}

			return mbStatsUser;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser findByPrimaryKey(
		MBStatsUserPK mbStatsUserPK)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)session.get(MBStatsUserHBM.class,
					mbStatsUserPK);

			if (mbStatsUserHBM == null) {
				_log.warn("No MBStatsUser exists with the primary key " +
					mbStatsUserPK.toString());
				throw new NoSuchStatsUserException(
					"No MBStatsUser exists with the primary key " +
					mbStatsUserPK.toString());
			}

			return MBStatsUserHBMUtil.model(mbStatsUserHBM);
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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

	public com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_First(
		String groupId, OrderByComparator obc)
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
			return (com.liferay.portlet.messageboards.model.MBStatsUser)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_Last(
		String groupId, OrderByComparator obc)
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
			return (com.liferay.portlet.messageboards.model.MBStatsUser)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser[] findByGroupId_PrevAndNext(
		MBStatsUserPK mbStatsUserPK, String groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser = findByPrimaryKey(mbStatsUserPK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser, MBStatsUserHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBStatsUser[] array = new com.liferay.portlet.messageboards.model.MBStatsUser[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[2];

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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

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
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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

	public com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_First(
		String userId, OrderByComparator obc)
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
			return (com.liferay.portlet.messageboards.model.MBStatsUser)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_Last(
		String userId, OrderByComparator obc)
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
			return (com.liferay.portlet.messageboards.model.MBStatsUser)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser[] findByUserId_PrevAndNext(
		MBStatsUserPK mbStatsUserPK, String userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser = findByPrimaryKey(mbStatsUserPK);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

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
			else {
				query.append("ORDER BY ");
				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser, MBStatsUserHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBStatsUser[] array = new com.liferay.portlet.messageboards.model.MBStatsUser[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[2];

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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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

	public com.liferay.portlet.messageboards.model.MBStatsUser findByG_M_First(
		String groupId, int messageCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
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
			return (com.liferay.portlet.messageboards.model.MBStatsUser)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser findByG_M_Last(
		String groupId, int messageCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
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
			return (com.liferay.portlet.messageboards.model.MBStatsUser)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser[] findByG_M_PrevAndNext(
		MBStatsUserPK mbStatsUserPK, String groupId, int messageCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser = findByPrimaryKey(mbStatsUserPK);
		int count = countByG_M(groupId, messageCount);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser, MBStatsUserHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBStatsUser[] array = new com.liferay.portlet.messageboards.model.MBStatsUser[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBStatsUser)objArray[2];

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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				list.add(MBStatsUserHBMUtil.model(mbStatsUserHBM));
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				session.delete(mbStatsUserHBM);
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("messageCount DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				session.delete(mbStatsUserHBM);
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

	public void removeByG_M(String groupId, int messageCount)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setInteger(queryPos++, messageCount);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBStatsUserHBM mbStatsUserHBM = (MBStatsUserHBM)itr.next();
				session.delete(mbStatsUserHBM);
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

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

	public int countByG_M(String groupId, int messageCount)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBStatsUser IN CLASS com.liferay.portlet.messageboards.service.persistence.MBStatsUserHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("messageCount != ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	private static Log _log = LogFactory.getLog(MBStatsUserPersistence.class);
}