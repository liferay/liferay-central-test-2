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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
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
	public WikiPage create(WikiPagePK wikiPagePK) {
		WikiPage wikiPage = new WikiPageImpl();
		wikiPage.setNew(true);
		wikiPage.setPrimaryKey(wikiPagePK);

		return wikiPage;
	}

	public WikiPage remove(WikiPagePK wikiPagePK)
		throws NoSuchPageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiPage wikiPage = (WikiPage)session.get(WikiPageImpl.class,
					wikiPagePK);

			if (wikiPage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No WikiPage exists with the primary key " +
						wikiPagePK);
				}

				throw new NoSuchPageException(
					"No WikiPage exists with the primary key " + wikiPagePK);
			}

			return remove(wikiPage);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByPrimaryKey(WikiPagePK wikiPagePK)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByPrimaryKey(wikiPagePK);

		if (wikiPage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No WikiPage exists with the primary key " +
					wikiPagePK);
			}

			throw new NoSuchPageException(
				"No WikiPage exists with the primary key " + wikiPagePK);
		}

		return wikiPage;
	}

	public WikiPage fetchByPrimaryKey(WikiPagePK wikiPagePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (WikiPage)session.get(WikiPageImpl.class, wikiPagePK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByNodeId(String nodeId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("nodeId ASC").append(", ");
			query.append("title ASC").append(", ");
			query.append("version ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
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

	public List findByNodeId(String nodeId, int begin, int end)
		throws SystemException {
		return findByNodeId(nodeId, begin, end, null);
	}

	public List findByNodeId(String nodeId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
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

	public WikiPage findByNodeId_First(String nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List list = findByNodeId(nodeId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByNodeId_Last(String nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		int count = countByNodeId(nodeId);
		List list = findByNodeId(nodeId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByNodeId_PrevAndNext(WikiPagePK wikiPagePK,
		String nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(wikiPagePK);
		int count = countByNodeId(nodeId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);
			WikiPage[] array = new WikiPageImpl[3];
			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T(String nodeId, String title)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			if (title != null) {
				q.setString(queryPos++, title);
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

	public List findByN_T(String nodeId, String title, int begin, int end)
		throws SystemException {
		return findByN_T(nodeId, title, begin, end, null);
	}

	public List findByN_T(String nodeId, String title, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			if (title != null) {
				q.setString(queryPos++, title);
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

	public WikiPage findByN_T_First(String nodeId, String title,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List list = findByN_T(nodeId, title, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += ", ";
			msg += "title=";
			msg += title;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByN_T_Last(String nodeId, String title,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_T(nodeId, title);
		List list = findByN_T(nodeId, title, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += ", ";
			msg += "title=";
			msg += title;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByN_T_PrevAndNext(WikiPagePK wikiPagePK,
		String nodeId, String title, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(wikiPagePK);
		int count = countByN_T(nodeId, title);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" AND ");

			if (title == null) {
				query.append("title IS NULL");
			}
			else {
				query.append("title = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_H(String nodeId, boolean head)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			q.setBoolean(queryPos++, head);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_H(String nodeId, boolean head, int begin, int end)
		throws SystemException {
		return findByN_H(nodeId, head, begin, end, null);
	}

	public List findByN_H(String nodeId, boolean head, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			q.setBoolean(queryPos++, head);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_H_First(String nodeId, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List list = findByN_H(nodeId, head, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += ", ";
			msg += "head=";
			msg += head;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByN_H_Last(String nodeId, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_H(nodeId, head);
		List list = findByN_H(nodeId, head, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += ", ";
			msg += "head=";
			msg += head;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByN_H_PrevAndNext(WikiPagePK wikiPagePK,
		String nodeId, boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(wikiPagePK);
		int count = countByN_H(nodeId, head);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			q.setBoolean(queryPos++, head);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);
			WikiPage[] array = new WikiPageImpl[3];
			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T_H(String nodeId, String title, boolean head)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setBoolean(queryPos++, head);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByN_T_H(String nodeId, String title, boolean head,
		int begin, int end) throws SystemException {
		return findByN_T_H(nodeId, title, head, begin, end, null);
	}

	public List findByN_T_H(String nodeId, String title, boolean head,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

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
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

			if (title != null) {
				q.setString(queryPos++, title);
			}

			q.setBoolean(queryPos++, head);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_T_H_First(String nodeId, String title,
		boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List list = findByN_T_H(nodeId, title, head, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += ", ";
			msg += "title=";
			msg += title;
			msg += ", ";
			msg += "head=";
			msg += head;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage findByN_T_H_Last(String nodeId, String title, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_T_H(nodeId, title, head);
		List list = findByN_T_H(nodeId, title, head, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No WikiPage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "nodeId=";
			msg += nodeId;
			msg += ", ";
			msg += "title=";
			msg += title;
			msg += ", ";
			msg += "head=";
			msg += head;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPageException(msg);
		}
		else {
			return (WikiPage)list.get(0);
		}
	}

	public WikiPage[] findByN_T_H_PrevAndNext(WikiPagePK wikiPagePK,
		String nodeId, String title, boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(wikiPagePK);
		int count = countByN_T_H(nodeId, title, head);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

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
				query.append("ORDER BY " + obc.getOrderBy());
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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

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
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByNodeId(String nodeId) throws SystemException {
		Iterator itr = findByNodeId(nodeId).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeByN_T(String nodeId, String title)
		throws SystemException {
		Iterator itr = findByN_T(nodeId, title).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeByN_H(String nodeId, boolean head)
		throws SystemException {
		Iterator itr = findByN_H(nodeId, head).iterator();

		while (itr.hasNext()) {
			WikiPage wikiPage = (WikiPage)itr.next();
			remove(wikiPage);
		}
	}

	public void removeByN_T_H(String nodeId, String title, boolean head)
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

	public int countByNodeId(String nodeId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
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

	public int countByN_T(String nodeId, String title)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByN_H(String nodeId, boolean head)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

			query.append(" AND ");
			query.append("head = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByN_T_H(String nodeId, String title, boolean head)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.wiki.model.WikiPage WHERE ");

			if (nodeId == null) {
				query.append("nodeId IS NULL");
			}
			else {
				query.append("nodeId = ?");
			}

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

			if (nodeId != null) {
				q.setString(queryPos++, nodeId);
			}

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

			StringBuffer query = new StringBuffer();
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(WikiPagePersistence.class);
}