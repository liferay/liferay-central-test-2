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

import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="BookmarksFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksFolderPersistence extends BasePersistence {
	public BookmarksFolder create(String folderId) {
		BookmarksFolder bookmarksFolder = new BookmarksFolder();
		bookmarksFolder.setNew(true);
		bookmarksFolder.setPrimaryKey(folderId);

		return bookmarksFolder;
	}

	public BookmarksFolder remove(String folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksFolder bookmarksFolder = (BookmarksFolder)session.get(BookmarksFolder.class,
					folderId);

			if (bookmarksFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BookmarksFolder exists with the primary key " +
						folderId.toString());
				}

				throw new NoSuchFolderException(
					"No BookmarksFolder exists with the primary key " +
					folderId.toString());
			}

			session.delete(bookmarksFolder);
			session.flush();

			return bookmarksFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder update(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws SystemException {
		Session session = null;

		try {
			if (bookmarksFolder.isNew() || bookmarksFolder.isModified()) {
				session = openSession();

				if (bookmarksFolder.isNew()) {
					BookmarksFolder bookmarksFolderModel = new BookmarksFolder();
					bookmarksFolderModel.setFolderId(bookmarksFolder.getFolderId());
					bookmarksFolderModel.setGroupId(bookmarksFolder.getGroupId());
					bookmarksFolderModel.setCompanyId(bookmarksFolder.getCompanyId());
					bookmarksFolderModel.setUserId(bookmarksFolder.getUserId());
					bookmarksFolderModel.setCreateDate(bookmarksFolder.getCreateDate());
					bookmarksFolderModel.setModifiedDate(bookmarksFolder.getModifiedDate());
					bookmarksFolderModel.setParentFolderId(bookmarksFolder.getParentFolderId());
					bookmarksFolderModel.setName(bookmarksFolder.getName());
					bookmarksFolderModel.setDescription(bookmarksFolder.getDescription());
					session.save(bookmarksFolderModel);
					session.flush();
				}
				else {
					BookmarksFolder bookmarksFolderModel = (BookmarksFolder)session.get(BookmarksFolder.class,
							bookmarksFolder.getPrimaryKey());

					if (bookmarksFolderModel != null) {
						bookmarksFolderModel.setGroupId(bookmarksFolder.getGroupId());
						bookmarksFolderModel.setCompanyId(bookmarksFolder.getCompanyId());
						bookmarksFolderModel.setUserId(bookmarksFolder.getUserId());
						bookmarksFolderModel.setCreateDate(bookmarksFolder.getCreateDate());
						bookmarksFolderModel.setModifiedDate(bookmarksFolder.getModifiedDate());
						bookmarksFolderModel.setParentFolderId(bookmarksFolder.getParentFolderId());
						bookmarksFolderModel.setName(bookmarksFolder.getName());
						bookmarksFolderModel.setDescription(bookmarksFolder.getDescription());
						session.flush();
					}
					else {
						bookmarksFolderModel = new BookmarksFolder();
						bookmarksFolderModel.setFolderId(bookmarksFolder.getFolderId());
						bookmarksFolderModel.setGroupId(bookmarksFolder.getGroupId());
						bookmarksFolderModel.setCompanyId(bookmarksFolder.getCompanyId());
						bookmarksFolderModel.setUserId(bookmarksFolder.getUserId());
						bookmarksFolderModel.setCreateDate(bookmarksFolder.getCreateDate());
						bookmarksFolderModel.setModifiedDate(bookmarksFolder.getModifiedDate());
						bookmarksFolderModel.setParentFolderId(bookmarksFolder.getParentFolderId());
						bookmarksFolderModel.setName(bookmarksFolder.getName());
						bookmarksFolderModel.setDescription(bookmarksFolder.getDescription());
						session.save(bookmarksFolderModel);
						session.flush();
					}
				}

				bookmarksFolder.setNew(false);
				bookmarksFolder.setModified(false);
			}

			return bookmarksFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public BookmarksFolder findByPrimaryKey(String folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksFolder bookmarksFolder = (BookmarksFolder)session.get(BookmarksFolder.class,
					folderId);

			if (bookmarksFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BookmarksFolder exists with the primary key " +
						folderId.toString());
					throw new NoSuchFolderException(
						"No BookmarksFolder exists with the primary key " +
						folderId.toString());
				}
			}

			return bookmarksFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public BookmarksFolder fetchByPrimaryKey(String folderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (BookmarksFolder)session.get(BookmarksFolder.class, folderId);
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
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
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
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

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
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
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

	public BookmarksFolder findByGroupId_First(String groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BookmarksFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (BookmarksFolder)list.get(0);
		}
	}

	public BookmarksFolder findByGroupId_Last(String groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No BookmarksFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (BookmarksFolder)list.get(0);
		}
	}

	public BookmarksFolder[] findByGroupId_PrevAndNext(String folderId,
		String groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

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
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksFolder);
			BookmarksFolder[] array = new BookmarksFolder[3];
			array[0] = (BookmarksFolder)objArray[0];
			array[1] = (BookmarksFolder)objArray[1];
			array[2] = (BookmarksFolder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	public List findByG_P(String groupId, String parentFolderId, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, begin, end, null);
	}

	public List findByG_P(String groupId, String parentFolderId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	public BookmarksFolder findByG_P_First(String groupId,
		String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List list = findByG_P(groupId, parentFolderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BookmarksFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentFolderId=";
			msg += parentFolderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (BookmarksFolder)list.get(0);
		}
	}

	public BookmarksFolder findByG_P_Last(String groupId,
		String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);
		List list = findByG_P(groupId, parentFolderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No BookmarksFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentFolderId=";
			msg += parentFolderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (BookmarksFolder)list.get(0);
		}
	}

	public BookmarksFolder[] findByG_P_PrevAndNext(String folderId,
		String groupId, String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);
		int count = countByG_P(groupId, parentFolderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksFolder);
			BookmarksFolder[] array = new BookmarksFolder[3];
			array[0] = (BookmarksFolder)objArray[0];
			array[1] = (BookmarksFolder)objArray[1];
			array[2] = (BookmarksFolder)objArray[2];

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
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());

			return q.list();
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
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BookmarksFolder bookmarksFolder = (BookmarksFolder)itr.next();
				session.delete(bookmarksFolder);
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

	public void removeByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BookmarksFolder bookmarksFolder = (BookmarksFolder)itr.next();
				session.delete(bookmarksFolder);
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
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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

	public int countByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksFolder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(BookmarksFolderPersistence.class);
}