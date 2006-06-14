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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.bookmarks.NoSuchEntryException;

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
 * <a href="BookmarksEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryPersistence extends BasePersistence {
	public com.liferay.portlet.bookmarks.model.BookmarksEntry create(
		String entryId) {
		BookmarksEntryHBM bookmarksEntryHBM = new BookmarksEntryHBM();
		bookmarksEntryHBM.setNew(true);
		bookmarksEntryHBM.setPrimaryKey(entryId);

		return BookmarksEntryHBMUtil.model(bookmarksEntryHBM);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		String entryId) throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)session.get(BookmarksEntryHBM.class,
					entryId);

			if (bookmarksEntryHBM == null) {
				_log.warn("No BookmarksEntry exists with the primary key " +
					entryId.toString());
				throw new NoSuchEntryException(
					"No BookmarksEntry exists with the primary key " +
					entryId.toString());
			}

			session.delete(bookmarksEntryHBM);
			session.flush();

			return BookmarksEntryHBMUtil.model(bookmarksEntryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry update(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws SystemException {
		Session session = null;

		try {
			if (bookmarksEntry.isNew() || bookmarksEntry.isModified()) {
				session = openSession();

				if (bookmarksEntry.isNew()) {
					BookmarksEntryHBM bookmarksEntryHBM = new BookmarksEntryHBM();
					bookmarksEntryHBM.setEntryId(bookmarksEntry.getEntryId());
					bookmarksEntryHBM.setCompanyId(bookmarksEntry.getCompanyId());
					bookmarksEntryHBM.setUserId(bookmarksEntry.getUserId());
					bookmarksEntryHBM.setCreateDate(bookmarksEntry.getCreateDate());
					bookmarksEntryHBM.setModifiedDate(bookmarksEntry.getModifiedDate());
					bookmarksEntryHBM.setFolderId(bookmarksEntry.getFolderId());
					bookmarksEntryHBM.setName(bookmarksEntry.getName());
					bookmarksEntryHBM.setUrl(bookmarksEntry.getUrl());
					bookmarksEntryHBM.setComments(bookmarksEntry.getComments());
					bookmarksEntryHBM.setVisits(bookmarksEntry.getVisits());
					session.save(bookmarksEntryHBM);
					session.flush();
				}
				else {
					BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)session.get(BookmarksEntryHBM.class,
							bookmarksEntry.getPrimaryKey());

					if (bookmarksEntryHBM != null) {
						bookmarksEntryHBM.setCompanyId(bookmarksEntry.getCompanyId());
						bookmarksEntryHBM.setUserId(bookmarksEntry.getUserId());
						bookmarksEntryHBM.setCreateDate(bookmarksEntry.getCreateDate());
						bookmarksEntryHBM.setModifiedDate(bookmarksEntry.getModifiedDate());
						bookmarksEntryHBM.setFolderId(bookmarksEntry.getFolderId());
						bookmarksEntryHBM.setName(bookmarksEntry.getName());
						bookmarksEntryHBM.setUrl(bookmarksEntry.getUrl());
						bookmarksEntryHBM.setComments(bookmarksEntry.getComments());
						bookmarksEntryHBM.setVisits(bookmarksEntry.getVisits());
						session.flush();
					}
					else {
						bookmarksEntryHBM = new BookmarksEntryHBM();
						bookmarksEntryHBM.setEntryId(bookmarksEntry.getEntryId());
						bookmarksEntryHBM.setCompanyId(bookmarksEntry.getCompanyId());
						bookmarksEntryHBM.setUserId(bookmarksEntry.getUserId());
						bookmarksEntryHBM.setCreateDate(bookmarksEntry.getCreateDate());
						bookmarksEntryHBM.setModifiedDate(bookmarksEntry.getModifiedDate());
						bookmarksEntryHBM.setFolderId(bookmarksEntry.getFolderId());
						bookmarksEntryHBM.setName(bookmarksEntry.getName());
						bookmarksEntryHBM.setUrl(bookmarksEntry.getUrl());
						bookmarksEntryHBM.setComments(bookmarksEntry.getComments());
						bookmarksEntryHBM.setVisits(bookmarksEntry.getVisits());
						session.save(bookmarksEntryHBM);
						session.flush();
					}
				}

				bookmarksEntry.setNew(false);
				bookmarksEntry.setModified(false);
			}

			return bookmarksEntry;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByPrimaryKey(
		String entryId) throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)session.get(BookmarksEntryHBM.class,
					entryId);

			if (bookmarksEntryHBM == null) {
				_log.warn("No BookmarksEntry exists with the primary key " +
					entryId.toString());
				throw new NoSuchEntryException(
					"No BookmarksEntry exists with the primary key " +
					entryId.toString());
			}

			return BookmarksEntryHBMUtil.model(bookmarksEntryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BookmarksEntry IN CLASS com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("folderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)itr.next();
				list.add(BookmarksEntryHBMUtil.model(bookmarksEntryHBM));
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

	public List findByFolderId(String folderId, int begin, int end)
		throws SystemException {
		return findByFolderId(folderId, begin, end, null);
	}

	public List findByFolderId(String folderId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BookmarksEntry IN CLASS com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("folderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)itr.next();
				list.add(BookmarksEntryHBMUtil.model(bookmarksEntryHBM));
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

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByFolderId_First(
		String folderId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List list = findByFolderId(folderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BookmarksEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.bookmarks.model.BookmarksEntry)list.get(0);
		}
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByFolderId_Last(
		String folderId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByFolderId(folderId);
		List list = findByFolderId(folderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No BookmarksEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.bookmarks.model.BookmarksEntry)list.get(0);
		}
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByFolderId_PrevAndNext(
		String entryId, String folderId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry = findByPrimaryKey(entryId);
		int count = countByFolderId(folderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BookmarksEntry IN CLASS com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("folderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksEntry, BookmarksEntryHBMUtil.getInstance());
			com.liferay.portlet.bookmarks.model.BookmarksEntry[] array = new com.liferay.portlet.bookmarks.model.BookmarksEntry[3];
			array[0] = (com.liferay.portlet.bookmarks.model.BookmarksEntry)objArray[0];
			array[1] = (com.liferay.portlet.bookmarks.model.BookmarksEntry)objArray[1];
			array[2] = (com.liferay.portlet.bookmarks.model.BookmarksEntry)objArray[2];

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
				"FROM BookmarksEntry IN CLASS com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryHBM ");
			query.append("ORDER BY ");
			query.append("folderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)itr.next();
				list.add(BookmarksEntryHBMUtil.model(bookmarksEntryHBM));
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

	public void removeByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BookmarksEntry IN CLASS com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("folderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BookmarksEntryHBM bookmarksEntryHBM = (BookmarksEntryHBM)itr.next();
				session.delete(bookmarksEntryHBM);
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

	public int countByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM BookmarksEntry IN CLASS com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

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

	private static Log _log = LogFactory.getLog(BookmarksEntryPersistence.class);
}