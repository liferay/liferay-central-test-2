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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="WikiPageResourcePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WikiPageResourcePersistence extends BasePersistence {
	public WikiPageResource create(long resourcePrimKey) {
		WikiPageResource wikiPageResource = new WikiPageResourceImpl();
		wikiPageResource.setNew(true);
		wikiPageResource.setPrimaryKey(resourcePrimKey);

		return wikiPageResource;
	}

	public WikiPageResource remove(long resourcePrimKey)
		throws NoSuchPageResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiPageResource wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
					new Long(resourcePrimKey));

			if (wikiPageResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No WikiPageResource exists with the primary key " +
						resourcePrimKey);
				}

				throw new NoSuchPageResourceException(
					"No WikiPageResource exists with the primary key " +
					resourcePrimKey);
			}

			return remove(wikiPageResource);
		}
		catch (NoSuchPageResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPageResource remove(WikiPageResource wikiPageResource)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(wikiPageResource);
			session.flush();

			return wikiPageResource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.wiki.model.WikiPageResource update(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws SystemException {
		return update(wikiPageResource, false);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource update(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(wikiPageResource);
			}
			else {
				if (wikiPageResource.isNew()) {
					session.save(wikiPageResource);
				}
			}

			session.flush();
			wikiPageResource.setNew(false);

			return wikiPageResource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByPrimaryKey(resourcePrimKey);

		if (wikiPageResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No WikiPageResource exists with the primary key " +
					resourcePrimKey);
			}

			throw new NoSuchPageResourceException(
				"No WikiPageResource exists with the primary key " +
				resourcePrimKey);
		}

		return wikiPageResource;
	}

	public WikiPageResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (WikiPageResource)session.get(WikiPageResourceImpl.class,
				new Long(resourcePrimKey));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPageResource findByN_T(long nodeId, String title)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByN_T(nodeId, title);

		if (wikiPageResource == null) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPageResource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("title=");
			msg.append(title);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageResourceException(msg.toString());
		}

		return wikiPageResource;
	}

	public WikiPageResource fetchByN_T(long nodeId, String title)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.wiki.model.WikiPageResource WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			WikiPageResource wikiPageResource = (WikiPageResource)list.get(0);

			return wikiPageResource;
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
			query.append(
				"FROM com.liferay.portlet.wiki.model.WikiPageResource ");

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

	public void removeByN_T(long nodeId, String title)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = findByN_T(nodeId, title);
		remove(wikiPageResource);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((WikiPageResource)itr.next());
		}
	}

	public int countByN_T(long nodeId, String title) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.wiki.model.WikiPageResource WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
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
			query.append("FROM com.liferay.portlet.wiki.model.WikiPageResource");

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

	private static Log _log = LogFactory.getLog(WikiPageResourcePersistence.class);
}