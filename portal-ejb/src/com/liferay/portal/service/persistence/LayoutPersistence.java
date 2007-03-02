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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringMaker;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="LayoutPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutPersistence extends BasePersistence {
	public Layout create(LayoutPK layoutPK) {
		Layout layout = new LayoutImpl();
		layout.setNew(true);
		layout.setPrimaryKey(layoutPK);

		return layout;
	}

	public Layout remove(LayoutPK layoutPK)
		throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Layout layout = (Layout)session.get(LayoutImpl.class, layoutPK);

			if (layout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Layout exists with the primary key " +
						layoutPK);
				}

				throw new NoSuchLayoutException(
					"No Layout exists with the primary key " + layoutPK);
			}

			return remove(layout);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout remove(Layout layout) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(layout);
			session.flush();

			return layout;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout) throws SystemException {
		return update(layout, false);
	}

	public com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(layout);
			}
			else {
				if (layout.isNew()) {
					session.save(layout);
				}
			}

			session.flush();
			layout.setNew(false);

			return layout;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByPrimaryKey(LayoutPK layoutPK)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByPrimaryKey(layoutPK);

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Layout exists with the primary key " + layoutPK);
			}

			throw new NoSuchLayoutException(
				"No Layout exists with the primary key " + layoutPK);
		}

		return layout;
	}

	public Layout fetchByPrimaryKey(LayoutPK layoutPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Layout)session.get(LayoutImpl.class, layoutPK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public List findByOwnerId(String ownerId, int begin, int end)
		throws SystemException {
		return findByOwnerId(ownerId, begin, end, null);
	}

	public List findByOwnerId(String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public Layout findByOwnerId_First(String ownerId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List list = findByOwnerId(ownerId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return (Layout)list.get(0);
		}
	}

	public Layout findByOwnerId_Last(String ownerId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByOwnerId(ownerId);
		List list = findByOwnerId(ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return (Layout)list.get(0);
		}
	}

	public Layout[] findByOwnerId_PrevAndNext(LayoutPK layoutPK,
		String ownerId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(layoutPK);
		int count = countByOwnerId(ownerId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);
			Layout[] array = new LayoutImpl[3];
			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByO_P(String ownerId, String parentLayoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (parentLayoutId == null) {
				query.append("parentLayoutId IS NULL");
			}
			else {
				query.append("parentLayoutId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (parentLayoutId != null) {
				q.setString(queryPos++, parentLayoutId);
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

	public List findByO_P(String ownerId, String parentLayoutId, int begin,
		int end) throws SystemException {
		return findByO_P(ownerId, parentLayoutId, begin, end, null);
	}

	public List findByO_P(String ownerId, String parentLayoutId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (parentLayoutId == null) {
				query.append("parentLayoutId IS NULL");
			}
			else {
				query.append("parentLayoutId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (parentLayoutId != null) {
				q.setString(queryPos++, parentLayoutId);
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

	public Layout findByO_P_First(String ownerId, String parentLayoutId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		List list = findByO_P(ownerId, parentLayoutId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("parentLayoutId=");
			msg.append(parentLayoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return (Layout)list.get(0);
		}
	}

	public Layout findByO_P_Last(String ownerId, String parentLayoutId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		int count = countByO_P(ownerId, parentLayoutId);
		List list = findByO_P(ownerId, parentLayoutId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("parentLayoutId=");
			msg.append(parentLayoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return (Layout)list.get(0);
		}
	}

	public Layout[] findByO_P_PrevAndNext(LayoutPK layoutPK, String ownerId,
		String parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(layoutPK);
		int count = countByO_P(ownerId, parentLayoutId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (parentLayoutId == null) {
				query.append("parentLayoutId IS NULL");
			}
			else {
				query.append("parentLayoutId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (parentLayoutId != null) {
				q.setString(queryPos++, parentLayoutId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);
			Layout[] array = new LayoutImpl[3];
			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByO_F(String ownerId, String friendlyURL)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByO_F(ownerId, friendlyURL);

		if (layout == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("friendlyURL=");
			msg.append(friendlyURL);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	public Layout fetchByO_F(String ownerId, String friendlyURL)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL IS NULL");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (friendlyURL != null) {
				q.setString(queryPos++, friendlyURL);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Layout layout = (Layout)list.get(0);

			return layout;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
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

	public void removeByOwnerId(String ownerId) throws SystemException {
		Iterator itr = findByOwnerId(ownerId).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();
			remove(layout);
		}
	}

	public void removeByO_P(String ownerId, String parentLayoutId)
		throws SystemException {
		Iterator itr = findByO_P(ownerId, parentLayoutId).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();
			remove(layout);
		}
	}

	public void removeByO_F(String ownerId, String friendlyURL)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByO_F(ownerId, friendlyURL);
		remove(layout);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Layout)itr.next());
		}
	}

	public int countByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public int countByO_P(String ownerId, String parentLayoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (parentLayoutId == null) {
				query.append("parentLayoutId IS NULL");
			}
			else {
				query.append("parentLayoutId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (parentLayoutId != null) {
				q.setString(queryPos++, parentLayoutId);
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

	public int countByO_F(String ownerId, String friendlyURL)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL IS NULL");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (friendlyURL != null) {
				q.setString(queryPos++, friendlyURL);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout");

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(LayoutPersistence.class);
}