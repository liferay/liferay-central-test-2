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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBBanPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBBanPersistenceImpl extends BasePersistence
	implements MBBanPersistence {
	public MBBan create(long banId) {
		MBBan mbBan = new MBBanImpl();
		mbBan.setNew(true);
		mbBan.setPrimaryKey(banId);

		return mbBan;
	}

	public MBBan remove(long banId) throws NoSuchBanException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBBan mbBan = (MBBan)session.get(MBBanImpl.class, new Long(banId));

			if (mbBan == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBBan exists with the primary key " + banId);
				}

				throw new NoSuchBanException(
					"No MBBan exists with the primary key " + banId);
			}

			return remove(mbBan);
		}
		catch (NoSuchBanException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan remove(MBBan mbBan) throws SystemException {
		FinderCache.clearCache(MBBan.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(mbBan);
			session.flush();

			return mbBan;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan update(com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws SystemException {
		return update(mbBan, false);
	}

	public MBBan update(com.liferay.portlet.messageboards.model.MBBan mbBan,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(MBBan.class.getName());

		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(mbBan);
			}
			else {
				if (mbBan.isNew()) {
					session.save(mbBan);
				}
			}

			session.flush();
			mbBan.setNew(false);

			return mbBan;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByPrimaryKey(long banId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByPrimaryKey(banId);

		if (mbBan == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBBan exists with the primary key " + banId);
			}

			throw new NoSuchBanException(
				"No MBBan exists with the primary key " + banId);
		}

		return mbBan;
	}

	public MBBan fetchByPrimaryKey(long banId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBBan)session.get(MBBanImpl.class, new Long(banId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(long groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchBanException(msg.toString());
		}
		else {
			return (MBBan)list.get(0);
		}
	}

	public MBBan findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchBanException(msg.toString());
		}
		else {
			return (MBBan)list.get(0);
		}
	}

	public MBBan[] findByGroupId_PrevAndNext(long banId, long groupId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);
			MBBan[] array = new MBBanImpl[3];
			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchBanException(msg.toString());
		}
		else {
			return (MBBan)list.get(0);
		}
	}

	public MBBan findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchBanException(msg.toString());
		}
		else {
			return (MBBan)list.get(0);
		}
	}

	public MBBan[] findByUserId_PrevAndNext(long banId, long userId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);
			MBBan[] array = new MBBanImpl[3];
			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByBanUserId(long banUserId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("banUserId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, banUserId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByBanUserId(long banUserId, int begin, int end)
		throws SystemException {
		return findByBanUserId(banUserId, begin, end, null);
	}

	public List findByBanUserId(long banUserId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("banUserId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, banUserId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByBanUserId_First(long banUserId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List list = findByBanUserId(banUserId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("banUserId=");
			msg.append(banUserId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchBanException(msg.toString());
		}
		else {
			return (MBBan)list.get(0);
		}
	}

	public MBBan findByBanUserId_Last(long banUserId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByBanUserId(banUserId);
		List list = findByBanUserId(banUserId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("banUserId=");
			msg.append(banUserId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchBanException(msg.toString());
		}
		else {
			return (MBBan)list.get(0);
		}
	}

	public MBBan[] findByBanUserId_PrevAndNext(long banId, long banUserId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);
		int count = countByBanUserId(banUserId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("banUserId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, banUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);
			MBBan[] array = new MBBanImpl[3];
			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByG_B(groupId, banUserId);

		if (mbBan == null) {
			StringMaker msg = new StringMaker();
			msg.append("No MBBan exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("banUserId=");
			msg.append(banUserId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBanException(msg.toString());
		}

		return mbBan;
	}

	public MBBan fetchByG_B(long groupId, long banUserId)
		throws SystemException {
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "fetchByG_B";
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(banUserId)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
				query.append("groupId = ?");
				query.append(" AND ");
				query.append("banUserId = ?");
				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, groupId);
				q.setLong(queryPos++, banUserId);

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				MBBan mbBan = (MBBan)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, mbBan);

				return mbBan;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (MBBan)result;
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
			query.append("FROM com.liferay.portlet.messageboards.model.MBBan ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			MBBan mbBan = (MBBan)itr.next();
			remove(mbBan);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			MBBan mbBan = (MBBan)itr.next();
			remove(mbBan);
		}
	}

	public void removeByBanUserId(long banUserId) throws SystemException {
		Iterator itr = findByBanUserId(banUserId).iterator();

		while (itr.hasNext()) {
			MBBan mbBan = (MBBan)itr.next();
			remove(mbBan);
		}
	}

	public void removeByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByG_B(groupId, banUserId);
		remove(mbBan);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((MBBan)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

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

	public int countByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

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

	public int countByBanUserId(long banUserId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("banUserId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, banUserId);

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

	public int countByG_B(long groupId, long banUserId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("banUserId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setLong(queryPos++, banUserId);

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
			query.append("FROM com.liferay.portlet.messageboards.model.MBBan");

			Query q = session.createQuery(query.toString());
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

	private static Log _log = LogFactory.getLog(MBBanPersistenceImpl.class);
}