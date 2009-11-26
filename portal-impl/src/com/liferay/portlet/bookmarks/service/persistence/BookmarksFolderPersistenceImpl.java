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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="BookmarksFolderPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksFolderPersistence
 * @see       BookmarksFolderUtil
 * @generated
 */
public class BookmarksFolderPersistenceImpl extends BasePersistenceImpl<BookmarksFolder>
	implements BookmarksFolderPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BookmarksFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(BookmarksFolder bookmarksFolder) {
		EntityCacheUtil.putResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey(),
			bookmarksFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksFolder.getUuid(),
				new Long(bookmarksFolder.getGroupId())
			}, bookmarksFolder);
	}

	public void cacheResult(List<BookmarksFolder> bookmarksFolders) {
		for (BookmarksFolder bookmarksFolder : bookmarksFolders) {
			if (EntityCacheUtil.getResult(
						BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
						BookmarksFolderImpl.class,
						bookmarksFolder.getPrimaryKey(), this) == null) {
				cacheResult(bookmarksFolder);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(BookmarksFolderImpl.class.getName());
		EntityCacheUtil.clearCache(BookmarksFolderImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public BookmarksFolder create(long folderId) {
		BookmarksFolder bookmarksFolder = new BookmarksFolderImpl();

		bookmarksFolder.setNew(true);
		bookmarksFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		bookmarksFolder.setUuid(uuid);

		return bookmarksFolder;
	}

	public BookmarksFolder remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public BookmarksFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksFolder bookmarksFolder = (BookmarksFolder)session.get(BookmarksFolderImpl.class,
					new Long(folderId));

			if (bookmarksFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BookmarksFolder exists with the primary key " +
						folderId);
				}

				throw new NoSuchFolderException(
					"No BookmarksFolder exists with the primary key " +
					folderId);
			}

			return remove(bookmarksFolder);
		}
		catch (NoSuchFolderException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BookmarksFolder remove(BookmarksFolder bookmarksFolder)
		throws SystemException {
		for (ModelListener<BookmarksFolder> listener : listeners) {
			listener.onBeforeRemove(bookmarksFolder);
		}

		bookmarksFolder = removeImpl(bookmarksFolder);

		for (ModelListener<BookmarksFolder> listener : listeners) {
			listener.onAfterRemove(bookmarksFolder);
		}

		return bookmarksFolder;
	}

	protected BookmarksFolder removeImpl(BookmarksFolder bookmarksFolder)
		throws SystemException {
		bookmarksFolder = toUnwrappedModel(bookmarksFolder);

		Session session = null;

		try {
			session = openSession();

			if (bookmarksFolder.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BookmarksFolderImpl.class,
						bookmarksFolder.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(bookmarksFolder);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BookmarksFolderModelImpl bookmarksFolderModelImpl = (BookmarksFolderModelImpl)bookmarksFolder;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksFolderModelImpl.getOriginalUuid(),
				new Long(bookmarksFolderModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey());

		return bookmarksFolder;
	}

	public BookmarksFolder updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder,
		boolean merge) throws SystemException {
		bookmarksFolder = toUnwrappedModel(bookmarksFolder);

		boolean isNew = bookmarksFolder.isNew();

		BookmarksFolderModelImpl bookmarksFolderModelImpl = (BookmarksFolderModelImpl)bookmarksFolder;

		if (Validator.isNull(bookmarksFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			bookmarksFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, bookmarksFolder, merge);

			bookmarksFolder.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey(),
			bookmarksFolder);

		if (!isNew &&
				(!Validator.equals(bookmarksFolder.getUuid(),
					bookmarksFolderModelImpl.getOriginalUuid()) ||
				(bookmarksFolder.getGroupId() != bookmarksFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					bookmarksFolderModelImpl.getOriginalUuid(),
					new Long(bookmarksFolderModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(bookmarksFolder.getUuid(),
					bookmarksFolderModelImpl.getOriginalUuid()) ||
				(bookmarksFolder.getGroupId() != bookmarksFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					bookmarksFolder.getUuid(),
					new Long(bookmarksFolder.getGroupId())
				}, bookmarksFolder);
		}

		return bookmarksFolder;
	}

	protected BookmarksFolder toUnwrappedModel(BookmarksFolder bookmarksFolder) {
		if (bookmarksFolder instanceof BookmarksFolderImpl) {
			return bookmarksFolder;
		}

		BookmarksFolderImpl bookmarksFolderImpl = new BookmarksFolderImpl();

		bookmarksFolderImpl.setNew(bookmarksFolder.isNew());
		bookmarksFolderImpl.setPrimaryKey(bookmarksFolder.getPrimaryKey());

		bookmarksFolderImpl.setUuid(bookmarksFolder.getUuid());
		bookmarksFolderImpl.setFolderId(bookmarksFolder.getFolderId());
		bookmarksFolderImpl.setGroupId(bookmarksFolder.getGroupId());
		bookmarksFolderImpl.setCompanyId(bookmarksFolder.getCompanyId());
		bookmarksFolderImpl.setUserId(bookmarksFolder.getUserId());
		bookmarksFolderImpl.setCreateDate(bookmarksFolder.getCreateDate());
		bookmarksFolderImpl.setModifiedDate(bookmarksFolder.getModifiedDate());
		bookmarksFolderImpl.setParentFolderId(bookmarksFolder.getParentFolderId());
		bookmarksFolderImpl.setName(bookmarksFolder.getName());
		bookmarksFolderImpl.setDescription(bookmarksFolder.getDescription());

		return bookmarksFolderImpl;
	}

	public BookmarksFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BookmarksFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = fetchByPrimaryKey(folderId);

		if (bookmarksFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BookmarksFolder exists with the primary key " +
					folderId);
			}

			throw new NoSuchFolderException(
				"No BookmarksFolder exists with the primary key " + folderId);
		}

		return bookmarksFolder;
	}

	public BookmarksFolder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BookmarksFolder fetchByPrimaryKey(long folderId)
		throws SystemException {
		BookmarksFolder bookmarksFolder = (BookmarksFolder)EntityCacheUtil.getResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
				BookmarksFolderImpl.class, folderId, this);

		if (bookmarksFolder == null) {
			Session session = null;

			try {
				session = openSession();

				bookmarksFolder = (BookmarksFolder)session.get(BookmarksFolderImpl.class,
						new Long(folderId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (bookmarksFolder != null) {
					cacheResult(bookmarksFolder);
				}

				closeSession(session);
			}
		}

		return bookmarksFolder;
	}

	public List<BookmarksFolder> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				if (uuid == null) {
					query.append("bookmarksFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(bookmarksFolder.uuid IS NULL OR ");
					}

					query.append("bookmarksFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");

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
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<BookmarksFolder> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				if (uuid == null) {
					query.append("bookmarksFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(bookmarksFolder.uuid IS NULL OR ");
					}

					query.append("bookmarksFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("bookmarksFolder.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("bookmarksFolder.parentFolderId ASC, ");
					query.append("bookmarksFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksFolder findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByUuid(uuid);

		List<BookmarksFolder> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			if (uuid == null) {
				query.append("bookmarksFolder.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(bookmarksFolder.uuid IS NULL OR ");
				}

				query.append("bookmarksFolder.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("bookmarksFolder.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksFolder);

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = (BookmarksFolder)objArray[0];
			array[1] = (BookmarksFolder)objArray[1];
			array[2] = (BookmarksFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BookmarksFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = fetchByUUID_G(uuid, groupId);

		if (bookmarksFolder == null) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return bookmarksFolder;
	}

	public BookmarksFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public BookmarksFolder fetchByUUID_G(String uuid, long groupId,
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

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				if (uuid == null) {
					query.append("bookmarksFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(bookmarksFolder.uuid IS NULL OR ");
					}

					query.append("bookmarksFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("bookmarksFolder.groupId = ?");

				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<BookmarksFolder> list = q.list();

				result = list;

				BookmarksFolder bookmarksFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					bookmarksFolder = list.get(0);

					cacheResult(bookmarksFolder);

					if ((bookmarksFolder.getUuid() == null) ||
							!bookmarksFolder.getUuid().equals(uuid) ||
							(bookmarksFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, bookmarksFolder);
					}
				}

				return bookmarksFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<BookmarksFolder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (BookmarksFolder)result;
			}
		}
	}

	public List<BookmarksFolder> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.groupId = ?");

				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");

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
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BookmarksFolder> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.groupId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("bookmarksFolder.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("bookmarksFolder.parentFolderId ASC, ");
					query.append("bookmarksFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksFolder findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder findByGroupId_Last(long groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);

		List<BookmarksFolder> list = findByGroupId(groupId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder[] findByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append("bookmarksFolder.groupId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("bookmarksFolder.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksFolder);

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = (BookmarksFolder)objArray[0];
			array[1] = (BookmarksFolder)objArray[1];
			array[2] = (BookmarksFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BookmarksFolder> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.companyId = ?");

				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksFolder> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<BookmarksFolder> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.companyId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("bookmarksFolder.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("bookmarksFolder.parentFolderId ASC, ");
					query.append("bookmarksFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksFolder findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByCompanyId(companyId);

		List<BookmarksFolder> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append("bookmarksFolder.companyId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("bookmarksFolder.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksFolder);

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = (BookmarksFolder)objArray[0];
			array[1] = (BookmarksFolder)objArray[1];
			array[2] = (BookmarksFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BookmarksFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId)
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.groupId = ?");

				query.append(" AND ");

				query.append("bookmarksFolder.parentFolderId = ?");

				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BookmarksFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	public List<BookmarksFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.groupId = ?");

				query.append(" AND ");

				query.append("bookmarksFolder.parentFolderId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("bookmarksFolder.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("bookmarksFolder.parentFolderId ASC, ");
					query.append("bookmarksFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BookmarksFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByG_P(groupId, parentFolderId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<BookmarksFolder> list = findByG_P(groupId, parentFolderId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BookmarksFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BookmarksFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		int count = countByG_P(groupId, parentFolderId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append("bookmarksFolder.groupId = ?");

			query.append(" AND ");

			query.append("bookmarksFolder.parentFolderId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("bookmarksFolder.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("bookmarksFolder.parentFolderId ASC, ");
				query.append("bookmarksFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					bookmarksFolder);

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = (BookmarksFolder)objArray[0];
			array[1] = (BookmarksFolder)objArray[1];
			array[2] = (BookmarksFolder)objArray[2];

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

	public List<BookmarksFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BookmarksFolder> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BookmarksFolder> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BOOKMARKSFOLDER);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("bookmarksFolder.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("bookmarksFolder.parentFolderId ASC, ");
					query.append("bookmarksFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<BookmarksFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BookmarksFolder>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BookmarksFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByUuid(uuid)) {
			remove(bookmarksFolder);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByUUID_G(uuid, groupId);

		remove(bookmarksFolder);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByGroupId(groupId)) {
			remove(bookmarksFolder);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByCompanyId(companyId)) {
			remove(bookmarksFolder);
		}
	}

	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByG_P(groupId, parentFolderId)) {
			remove(bookmarksFolder);
		}
	}

	public void removeAll() throws SystemException {
		for (BookmarksFolder bookmarksFolder : findAll()) {
			remove(bookmarksFolder);
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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

				if (uuid == null) {
					query.append("bookmarksFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(bookmarksFolder.uuid IS NULL OR ");
					}

					query.append("bookmarksFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

				if (uuid == null) {
					query.append("bookmarksFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(bookmarksFolder.uuid IS NULL OR ");
					}

					query.append("bookmarksFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("bookmarksFolder.groupId = ?");

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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.groupId = ?");

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.companyId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

				query.append("bookmarksFolder.groupId = ?");

				query.append(" AND ");

				query.append("bookmarksFolder.parentFolderId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_BOOKMARKSFOLDER);

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
						"value.object.listener.com.liferay.portlet.bookmarks.model.BookmarksFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BookmarksFolder>> listenersList = new ArrayList<ModelListener<BookmarksFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BookmarksFolder>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence bookmarksFolderPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_BOOKMARKSFOLDER = "SELECT bookmarksFolder FROM BookmarksFolder bookmarksFolder";
	private static final String _SQL_SELECT_BOOKMARKSFOLDER_WHERE = "SELECT bookmarksFolder FROM BookmarksFolder bookmarksFolder WHERE ";
	private static final String _SQL_COUNT_BOOKMARKSFOLDER = "SELECT COUNT(bookmarksFolder) FROM BookmarksFolder bookmarksFolder";
	private static final String _SQL_COUNT_BOOKMARKSFOLDER_WHERE = "SELECT COUNT(bookmarksFolder) FROM BookmarksFolder bookmarksFolder WHERE ";
	private static Log _log = LogFactoryUtil.getLog(BookmarksFolderPersistenceImpl.class);
}