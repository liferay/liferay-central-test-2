/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="BookmarksEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksEntryPersistenceImpl extends BasePersistenceImpl
	implements BookmarksEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BookmarksEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_FOLDERID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFolderId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_FOLDERID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFolderId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_FOLDERID = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFolderId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(BookmarksEntry bookmarksEntry) {
		EntityCacheUtil.putResult(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryImpl.class, bookmarksEntry.getPrimaryKey(),
			bookmarksEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksEntry.getUuid(), new Long(bookmarksEntry.getGroupId())
			}, bookmarksEntry);
	}

	public void cacheResult(List<BookmarksEntry> bookmarksEntries) {
		for (BookmarksEntry bookmarksEntry : bookmarksEntries) {
			if (EntityCacheUtil.getResult(
						BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
						BookmarksEntryImpl.class,
						bookmarksEntry.getPrimaryKey(), this) == null) {
				cacheResult(bookmarksEntry);
			}
		}
	}

	public BookmarksEntry create(long entryId) {
		BookmarksEntry bookmarksEntry = new BookmarksEntryImpl();

		bookmarksEntry.setNew(true);
		bookmarksEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		bookmarksEntry.setUuid(uuid);

		return bookmarksEntry;
	}

	public BookmarksEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksEntry bookmarksEntry = (BookmarksEntry)session.get(BookmarksEntryImpl.class,
					new Long(entryId));

			if (bookmarksEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BookmarksEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No BookmarksEntry exists with the primary key " + entryId);
			}

			return remove(bookmarksEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BookmarksEntry remove(BookmarksEntry bookmarksEntry)
		throws SystemException {
		for (ModelListener<BookmarksEntry> listener : listeners) {
			listener.onBeforeRemove(bookmarksEntry);
		}

		bookmarksEntry = removeImpl(bookmarksEntry);

		for (ModelListener<BookmarksEntry> listener : listeners) {
			listener.onAfterRemove(bookmarksEntry);
		}

		return bookmarksEntry;
	}

	protected BookmarksEntry removeImpl(BookmarksEntry bookmarksEntry)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (bookmarksEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BookmarksEntryImpl.class,
						bookmarksEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(bookmarksEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BookmarksEntryModelImpl bookmarksEntryModelImpl = (BookmarksEntryModelImpl)bookmarksEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksEntryModelImpl.getOriginalUuid(),
				new Long(bookmarksEntryModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryImpl.class, bookmarksEntry.getPrimaryKey());

		return bookmarksEntry;
	}

	/**
	 * @deprecated Use <code>update(BookmarksEntry bookmarksEntry, boolean merge)</code>.
	 */
	public BookmarksEntry update(BookmarksEntry bookmarksEntry)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(BookmarksEntry bookmarksEntry) method. Use update(BookmarksEntry bookmarksEntry, boolean merge) instead.");
		}

		return update(bookmarksEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        bookmarksEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when bookmarksEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public BookmarksEntry update(BookmarksEntry bookmarksEntry, boolean merge)
		throws SystemException {
		boolean isNew = bookmarksEntry.isNew();

		for (ModelListener<BookmarksEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(bookmarksEntry);
			}
			else {
				listener.onBeforeUpdate(bookmarksEntry);
			}
		}

		bookmarksEntry = updateImpl(bookmarksEntry, merge);

		for (ModelListener<BookmarksEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(bookmarksEntry);
			}
			else {
				listener.onAfterUpdate(bookmarksEntry);
			}
		}

		return bookmarksEntry;
	}

	public BookmarksEntry updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge) throws SystemException {
		boolean isNew = bookmarksEntry.isNew();

		BookmarksEntryModelImpl bookmarksEntryModelImpl = (BookmarksEntryModelImpl)bookmarksEntry;

		if (Validator.isNull(bookmarksEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			bookmarksEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, bookmarksEntry, merge);

			bookmarksEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksEntryImpl.class, bookmarksEntry.getPrimaryKey(),
			bookmarksEntry);

		if (!isNew &&
				(!bookmarksEntry.getUuid()
									.equals(bookmarksEntryModelImpl.getOriginalUuid()) ||
				(bookmarksEntry.getGroupId() != bookmarksEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					bookmarksEntryModelImpl.getOriginalUuid(),
					new Long(bookmarksEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!bookmarksEntry.getUuid()
									.equals(bookmarksEntryModelImpl.getOriginalUuid()) ||
				(bookmarksEntry.getGroupId() != bookmarksEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					bookmarksEntry.getUuid(),
					new Long(bookmarksEntry.getGroupId())
				}, bookmarksEntry);
		}

		return bookmarksEntry;
	}

	public BookmarksEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = fetchByPrimaryKey(entryId);

		if (bookmarksEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BookmarksEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No BookmarksEntry exists with the primary key " + entryId);
		}

		return bookmarksEntry;
	}

	public BookmarksEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		BookmarksEntry bookmarksEntry = (BookmarksEntry)EntityCacheUtil.getResult(BookmarksEntryModelImpl.ENTITY_CACHE_ENABLED,
				BookmarksEntryImpl.class, entryId, this);

		if (bookmarksEntry == null) {
			Session session = null;

			try {
				session = openSession();

				bookmarksEntry = (BookmarksEntry)session.get(BookmarksEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (bookmarksEntry != null) {
					cacheResult(bookmarksEntry);
				}

				closeSession(session);
			}
		}

		return bookmarksEntry;
	}

	public List<BookmarksEntry> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<BookmarksEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("folderId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<BookmarksEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksEntry findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<BookmarksEntry> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByUuid(uuid);

		List<BookmarksEntry> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry[] findByUuid_PrevAndNext(long entryId, String uuid,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = findByPrimaryKey(entryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksEntry);

			BookmarksEntry[] array = new BookmarksEntryImpl[3];

			array[0] = (BookmarksEntry)objArray[0];
			array[1] = (BookmarksEntry)objArray[1];
			array[2] = (BookmarksEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BookmarksEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = fetchByUUID_G(uuid, groupId);

		if (bookmarksEntry == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return bookmarksEntry;
	}

	public BookmarksEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public BookmarksEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<BookmarksEntry> list = q.list();

				result = list;

				BookmarksEntry bookmarksEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					bookmarksEntry = list.get(0);

					cacheResult(bookmarksEntry);
				}

				return bookmarksEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<BookmarksEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (BookmarksEntry)result;
			}
		}
	}

	public List<BookmarksEntry> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BookmarksEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("folderId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BookmarksEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksEntry findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<BookmarksEntry> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<BookmarksEntry> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry[] findByGroupId_PrevAndNext(long entryId,
		long groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = findByPrimaryKey(entryId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksEntry);

			BookmarksEntry[] array = new BookmarksEntryImpl[3];

			array[0] = (BookmarksEntry)objArray[0];
			array[1] = (BookmarksEntry)objArray[1];
			array[2] = (BookmarksEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BookmarksEntry> findByFolderId(long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId) };

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FOLDERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FOLDERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksEntry> findByFolderId(long folderId, int start, int end)
		throws SystemException {
		return findByFolderId(folderId, start, end, null);
	}

	public List<BookmarksEntry> findByFolderId(long folderId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_FOLDERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("folderId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				list = (List<BookmarksEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_FOLDERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksEntry findByFolderId_First(long folderId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<BookmarksEntry> list = findByFolderId(folderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry findByFolderId_Last(long folderId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByFolderId(folderId);

		List<BookmarksEntry> list = findByFolderId(folderId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry[] findByFolderId_PrevAndNext(long entryId,
		long folderId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = findByPrimaryKey(entryId);

		int count = countByFolderId(folderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

			query.append("folderId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksEntry);

			BookmarksEntry[] array = new BookmarksEntryImpl[3];

			array[0] = (BookmarksEntry)objArray[0];
			array[1] = (BookmarksEntry)objArray[1];
			array[2] = (BookmarksEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BookmarksEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<BookmarksEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("folderId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<BookmarksEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksEntry findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<BookmarksEntry> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<BookmarksEntry> list = findByG_U(groupId, userId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BookmarksEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksEntry[] findByG_U_PrevAndNext(long entryId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = findByPrimaryKey(entryId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("folderId ASC, ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksEntry);

			BookmarksEntry[] array = new BookmarksEntryImpl[3];

			array[0] = (BookmarksEntry)objArray[0];
			array[1] = (BookmarksEntry)objArray[1];
			array[2] = (BookmarksEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BookmarksEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BookmarksEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BookmarksEntry> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksEntry> list = (List<BookmarksEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("folderId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<BookmarksEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BookmarksEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (BookmarksEntry bookmarksEntry : findByUuid(uuid)) {
			remove(bookmarksEntry);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BookmarksEntry bookmarksEntry = findByUUID_G(uuid, groupId);

		remove(bookmarksEntry);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BookmarksEntry bookmarksEntry : findByGroupId(groupId)) {
			remove(bookmarksEntry);
		}
	}

	public void removeByFolderId(long folderId) throws SystemException {
		for (BookmarksEntry bookmarksEntry : findByFolderId(folderId)) {
			remove(bookmarksEntry);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (BookmarksEntry bookmarksEntry : findByG_U(groupId, userId)) {
			remove(bookmarksEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (BookmarksEntry bookmarksEntry : findAll()) {
			remove(bookmarksEntry);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByFolderId(long folderId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FOLDERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FOLDERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.bookmarks.model.BookmarksEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.bookmarks.model.BookmarksEntry");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.bookmarks.model.BookmarksEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BookmarksEntry>> listenersList = new ArrayList<ModelListener<BookmarksEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BookmarksEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence.impl")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence.impl")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence bookmarksFolderPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	private static Log _log = LogFactoryUtil.getLog(BookmarksEntryPersistenceImpl.class);
}