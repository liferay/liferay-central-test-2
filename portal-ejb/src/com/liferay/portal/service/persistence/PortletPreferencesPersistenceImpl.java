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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PortletPreferencesPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPreferencesPersistenceImpl extends BasePersistence
	implements PortletPreferencesPersistence {
	public PortletPreferences create(long portletPreferencesId) {
		PortletPreferences portletPreferences = new PortletPreferencesImpl();
		portletPreferences.setNew(true);
		portletPreferences.setPrimaryKey(portletPreferencesId);

		return portletPreferences;
	}

	public PortletPreferences remove(long portletPreferencesId)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferences portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
					new Long(portletPreferencesId));

			if (portletPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No PortletPreferences exists with the primary key " +
						portletPreferencesId);
				}

				throw new NoSuchPortletPreferencesException(
					"No PortletPreferences exists with the primary key " +
					portletPreferencesId);
			}

			return remove(portletPreferences);
		}
		catch (NoSuchPortletPreferencesException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences remove(PortletPreferences portletPreferences)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(portletPreferences);
			session.flush();

			return portletPreferences;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PortletPreferences update(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws SystemException {
		return update(portletPreferences, false);
	}

	public com.liferay.portal.model.PortletPreferences update(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(portletPreferences);
			}
			else {
				if (portletPreferences.isNew()) {
					session.save(portletPreferences);
				}
			}

			session.flush();
			portletPreferences.setNew(false);

			return portletPreferences;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByPrimaryKey(long portletPreferencesId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = fetchByPrimaryKey(portletPreferencesId);

		if (portletPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PortletPreferences exists with the primary key " +
					portletPreferencesId);
			}

			throw new NoSuchPortletPreferencesException(
				"No PortletPreferences exists with the primary key " +
				portletPreferencesId);
		}

		return portletPreferences;
	}

	public PortletPreferences fetchByPrimaryKey(long portletPreferencesId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PortletPreferences)session.get(PortletPreferencesImpl.class,
				new Long(portletPreferencesId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByPlid(long plid) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("plid = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, plid);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByPlid(long plid, int begin, int end)
		throws SystemException {
		return findByPlid(plid, begin, end, null);
	}

	public List findByPlid(long plid, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("plid = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, plid);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByPlid_First(long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List list = findByPlid(plid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PortletPreferences exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("plid=");
			msg.append(plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences findByPlid_Last(long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByPlid(plid);
		List list = findByPlid(plid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PortletPreferences exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("plid=");
			msg.append(plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);
		int count = countByPlid(plid);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("plid = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, plid);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);
			PortletPreferences[] array = new PortletPreferencesImpl[3];
			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("ownerType = ?");
			query.append(" AND ");
			query.append("plid = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, ownerId);
			q.setInteger(queryPos++, ownerType);
			q.setLong(queryPos++, plid);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByO_O_P(long ownerId, int ownerType, long plid, int begin,
		int end) throws SystemException {
		return findByO_O_P(ownerId, ownerType, plid, begin, end, null);
	}

	public List findByO_O_P(long ownerId, int ownerType, long plid, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("ownerType = ?");
			query.append(" AND ");
			query.append("plid = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, ownerId);
			q.setInteger(queryPos++, ownerType);
			q.setLong(queryPos++, plid);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByO_O_P_First(long ownerId, int ownerType,
		long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List list = findByO_O_P(ownerId, ownerType, plid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PortletPreferences exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("ownerType=");
			msg.append(ownerType);
			msg.append(", ");
			msg.append("plid=");
			msg.append(plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences findByO_O_P_Last(long ownerId, int ownerType,
		long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByO_O_P(ownerId, ownerType, plid);
		List list = findByO_O_P(ownerId, ownerType, plid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PortletPreferences exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("ownerType=");
			msg.append(ownerType);
			msg.append(", ");
			msg.append("plid=");
			msg.append(plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);
		int count = countByO_O_P(ownerId, ownerType, plid);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("ownerType = ?");
			query.append(" AND ");
			query.append("plid = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, ownerId);
			q.setInteger(queryPos++, ownerType);
			q.setLong(queryPos++, plid);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);
			PortletPreferences[] array = new PortletPreferencesImpl[3];
			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = fetchByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		if (portletPreferences == null) {
			StringMaker msg = new StringMaker();
			msg.append("No PortletPreferences exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("ownerType=");
			msg.append(ownerType);
			msg.append(", ");
			msg.append("plid=");
			msg.append(plid);
			msg.append(", ");
			msg.append("portletId=");
			msg.append(portletId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletPreferencesException(msg.toString());
		}

		return portletPreferences;
	}

	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("ownerType = ?");
			query.append(" AND ");
			query.append("plid = ?");
			query.append(" AND ");

			if (portletId == null) {
				query.append("portletId IS NULL");
			}
			else {
				query.append("portletId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, ownerId);
			q.setInteger(queryPos++, ownerType);
			q.setLong(queryPos++, plid);

			if (portletId != null) {
				q.setString(queryPos++, portletId);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			PortletPreferences portletPreferences = (PortletPreferences)list.get(0);

			return portletPreferences;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
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
			query.append("FROM com.liferay.portal.model.PortletPreferences ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByPlid(long plid) throws SystemException {
		Iterator itr = findByPlid(plid).iterator();

		while (itr.hasNext()) {
			PortletPreferences portletPreferences = (PortletPreferences)itr.next();
			remove(portletPreferences);
		}
	}

	public void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		Iterator itr = findByO_O_P(ownerId, ownerType, plid).iterator();

		while (itr.hasNext()) {
			PortletPreferences portletPreferences = (PortletPreferences)itr.next();
			remove(portletPreferences);
		}
	}

	public void removeByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByO_O_P_P(ownerId,
				ownerType, plid, portletId);
		remove(portletPreferences);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((PortletPreferences)itr.next());
		}
	}

	public int countByPlid(long plid) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("plid = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, plid);

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

	public int countByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("ownerType = ?");
			query.append(" AND ");
			query.append("plid = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, ownerId);
			q.setInteger(queryPos++, ownerType);
			q.setLong(queryPos++, plid);

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

	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("ownerType = ?");
			query.append(" AND ");
			query.append("plid = ?");
			query.append(" AND ");

			if (portletId == null) {
				query.append("portletId IS NULL");
			}
			else {
				query.append("portletId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, ownerId);
			q.setInteger(queryPos++, ownerType);
			q.setLong(queryPos++, plid);

			if (portletId != null) {
				q.setString(queryPos++, portletId);
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
			query.append("FROM com.liferay.portal.model.PortletPreferences");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	private static Log _log = LogFactory.getLog(PortletPreferencesPersistenceImpl.class);
}