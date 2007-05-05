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

import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="WikiPagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WikiPagePersistence extends BasePersistence {
	public WikiPage create(long pageId) {
		WikiPage wikiPage = new WikiPageImpl();
		wikiPage.setNew(true);
		wikiPage.setPrimaryKey(pageId);

		return wikiPage;
	}

	public WikiPage remove(long pageId)
		throws NoSuchPageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiPage wikiPage = (WikiPage)session.get(WikiPageImpl.class,
					new Long(pageId));

			if (wikiPage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No WikiPage exists with the primary key " +
						pageId);
				}

				throw new NoSuchPageException(
					"No WikiPage exists with the primary key " + pageId);
			}

			return remove(wikiPage);
		}
		catch (NoSuchPageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage remove(WikiPage wikiPage) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(wikiPage);
			session.flush();

			return wikiPage;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.wiki.model.WikiPage update(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws SystemException {
		return update(wikiPage, false);
	}

	public com.liferay.portlet.wiki.model.WikiPage update(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(wikiPage);
			}
			else {
				if (wikiPage.isNew()) {
					session.save(wikiPage);
				}
			}

			session.flush();
			wikiPage.setNew(false);

			return wikiPage;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByPrimaryKey(long pageId)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByPrimaryKey(pageId);

		if (wikiPage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No WikiPage exists with the primary key " + pageId);
			}

			throw new NoSuchPageException(
				"No WikiPage exists with the primary key " + pageId);
		}

		return wikiPage;
	}

	public WikiPage fetchByPrimaryKey(long pageId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (WikiPage)session.get(WikiPageImpl.class, new Long(pageId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByNodeId(long nodeId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("nodeId ASC").append(", ");
			query.append("title ASC").append(", ");
			query.append("version ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByNodeId(long nodeId, int begin, int end)
		throws SystemException {
		return findByNodeId(nodeId, begin, end, null);
	}

	public List findByNodeId(long nodeId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByNodeId_First(long nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List list = findByNodeId(nodeId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByNodeId_Last(long nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		int count = countByNodeId(nodeId);
		List list = findByNodeId(nodeId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByNodeId_PrevAndNext(long pageId, long nodeId,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);
		int count = countByNodeId(nodeId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);
			WikiPage[] array = new WikiPageImpl[3];
			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T(long nodeId, String title) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("nodeId ASC").append(", ");
			query.append("title ASC").append(", ");
			query.append("version ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T(long nodeId, String title, int begin, int end)
		throws SystemException {
		return findByN_T(nodeId, title, begin, end, null);
	}

	public List findByN_T(long nodeId, String title, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_T_First(long nodeId, String title,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List list = findByN_T(nodeId, title, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("title=");
			msg.append(title);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByN_T_Last(long nodeId, String title,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_T(nodeId, title);
		List list = findByN_T(nodeId, title, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("title=");
			msg.append(title);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByN_T_PrevAndNext(long pageId, long nodeId,
		String title, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);
		int count = countByN_T(nodeId, title);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);
			WikiPage[] array = new WikiPageImpl[3];
			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_H(long nodeId, boolean head) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("nodeId ASC").append(", ");
			query.append("title ASC").append(", ");
			query.append("version ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);
			q.setBoolean(queryPos++, head);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_H(long nodeId, boolean head, int begin, int end)
		throws SystemException {
		return findByN_H(nodeId, head, begin, end, null);
	}

	public List findByN_H(long nodeId, boolean head, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);
			q.setBoolean(queryPos++, head);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_H_First(long nodeId, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List list = findByN_H(nodeId, head, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("head=");
			msg.append(head);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByN_H_Last(long nodeId, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_H(nodeId, head);
		List list = findByN_H(nodeId, head, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("head=");
			msg.append(head);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByN_H_PrevAndNext(long pageId, long nodeId,
		boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);
		int count = countByN_H(nodeId, head);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);
			q.setBoolean(queryPos++, head);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);
			WikiPage[] array = new WikiPageImpl[3];
			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByN_T_V(nodeId, title, version);

		if (wikiPage == null) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("title=");
			msg.append(title);
			msg.append(", ");
			msg.append("version=");
			msg.append(version);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageException(msg.toString());
		}

		return wikiPage;
	}

	public WikiPage fetchByN_T_V(long nodeId, String title, double version)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" AND ");
			query.append("version = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("nodeId ASC").append(", ");
			query.append("title ASC").append(", ");
			query.append("version ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setDouble(queryPos++, version);

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			WikiPage wikiPage = (WikiPage)list.get(0);

			return wikiPage;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T_H(long nodeId, String title, boolean head)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("nodeId ASC").append(", ");
			query.append("title ASC").append(", ");
			query.append("version ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setBoolean(queryPos++, head);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T_H(long nodeId, String title, boolean head, int begin,
		int end) throws SystemException {
		return findByN_T_H(nodeId, title, head, begin, end, null);
	}

	public List findByN_T_H(long nodeId, String title, boolean head, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setBoolean(queryPos++, head);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_T_H_First(long nodeId, String title, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List list = findByN_T_H(nodeId, title, head, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("title=");
			msg.append(title);
			msg.append(", ");
			msg.append("head=");
			msg.append(head);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByN_T_H_Last(long nodeId, String title, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_T_H(nodeId, title, head);
		List list = findByN_T_H(nodeId, title, head, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No WikiPage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("nodeId=");
			msg.append(nodeId);
			msg.append(", ");
			msg.append("title=");
			msg.append(title);
			msg.append(", ");
			msg.append("head=");
			msg.append(head);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPageException(msg.toString());
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByN_T_H_PrevAndNext(long pageId, long nodeId,
		String title, boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);
		int count = countByN_T_H(nodeId, title, head);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setBoolean(queryPos++, head);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);
			WikiPage[] array = new WikiPageImpl[3];
			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
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
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("nodeId ASC").append(", ");
				query.append("title ASC").append(", ");
				query.append("version ASC");
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

	public void removeByNodeId(long nodeId) throws SystemException {
		Iterator itr = findByNodeId(nodeId).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeByN_T(long nodeId, String title)
		throws SystemException {
		Iterator itr = findByN_T(nodeId, title).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeByN_H(long nodeId, boolean head)
		throws SystemException {
		Iterator itr = findByN_H(nodeId, head).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByN_T_V(nodeId, title, version);
		remove(wikiPage);
	}

	public void removeByN_T_H(long nodeId, String title, boolean head)
		throws SystemException {
		Iterator itr = findByN_T_H(nodeId, title, head).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((WikiPage)itr.next());
		}
	}

	public int countByNodeId(long nodeId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

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

	public int countByN_T(long nodeId, String title) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
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

	public int countByN_H(long nodeId, boolean head) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);
			q.setBoolean(queryPos++, head);

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

	public int countByN_T_V(long nodeId, String title, double version)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" AND ");
			query.append("version = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setDouble(queryPos++, version);

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

	public int countByN_T_H(long nodeId, String title, boolean head)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");
			query.append("nodeId = ?");
			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, nodeId);

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setBoolean(queryPos++, head);

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
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage");

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

	private static Log _log = LogFactory.getLog(WikiPagePersistence.class);
}