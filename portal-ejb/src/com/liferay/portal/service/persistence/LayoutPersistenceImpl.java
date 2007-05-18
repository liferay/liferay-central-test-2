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
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutImpl;
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
 * <a href="LayoutPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutPersistenceImpl extends BasePersistence
	implements LayoutPersistence {
	public Layout create(long plid) {
		Layout layout = new LayoutImpl();
		layout.setNew(true);
		layout.setPrimaryKey(plid);

		return layout;
	}

	public Layout remove(long plid)
		throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Layout layout = (Layout)session.get(LayoutImpl.class, new Long(plid));

			if (layout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Layout exists with the primary key " + plid);
				}

				throw new NoSuchLayoutException(
					"No Layout exists with the primary key " + plid);
			}

			return remove(layout);
		}
		catch (NoSuchLayoutException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByPrimaryKey(long plid)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByPrimaryKey(plid);

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Layout exists with the primary key " + plid);
			}

			throw new NoSuchLayoutException(
				"No Layout exists with the primary key " + plid);
		}

		return layout;
	}

	public Layout fetchByPrimaryKey(long plid) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Layout)session.get(LayoutImpl.class, new Long(plid));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(long groupId, boolean privateLayout, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, privateLayout, begin, end, null);
	}

	public List findByG_P(long groupId, boolean privateLayout, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		List list = findByG_P(groupId, privateLayout, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return (Layout)list.get(0);
		}
	}

	public Layout findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		int count = countByG_P(groupId, privateLayout);
		List list = findByG_P(groupId, privateLayout, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return (Layout)list.get(0);
		}
	}

	public Layout[] findByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);
		int count = countByG_P(groupId, privateLayout);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);
			Layout[] array = new LayoutImpl[3];
			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByG_P_L(groupId, privateLayout, layoutId);

		if (layout == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(", ");
			msg.append("layoutId=");
			msg.append(layoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	public Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Layout layout = (Layout)list.get(0);

			return layout;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, parentLayoutId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int begin, int end) throws SystemException {
		return findByG_P_P(groupId, privateLayout, parentLayoutId, begin, end,
			null);
	}

	public List findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, parentLayoutId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByG_P_P_First(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List list = findByG_P_P(groupId, privateLayout, parentLayoutId, 0, 1,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
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

	public Layout findByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByG_P_P(groupId, privateLayout, parentLayoutId);
		List list = findByG_P_P(groupId, privateLayout, parentLayoutId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
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

	public Layout[] findByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);
		int count = countByG_P_P(groupId, privateLayout, parentLayoutId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, parentLayoutId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);
			Layout[] array = new LayoutImpl[3];
			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByG_P_F(groupId, privateLayout, friendlyURL);

		if (layout == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Layout exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
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

	public Layout fetchByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Iterator itr = findByG_P(groupId, privateLayout).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();
			remove(layout);
		}
	}

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByG_P_L(groupId, privateLayout, layoutId);
		remove(layout);
	}

	public void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws SystemException {
		Iterator itr = findByG_P_P(groupId, privateLayout, parentLayoutId)
						   .iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();
			remove(layout);
		}
	}

	public void removeByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws NoSuchLayoutException, SystemException {
		Layout layout = findByG_P_F(groupId, privateLayout, friendlyURL);
		remove(layout);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Layout)itr.next());
		}
	}

	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

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

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

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

	public int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, parentLayoutId);

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

	public int countByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Layout WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(LayoutPersistenceImpl.class);
}