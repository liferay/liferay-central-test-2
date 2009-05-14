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

package com.liferay.portlet.blogs.service.persistence;

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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <a href="BlogsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsEntryPersistenceImpl extends BasePersistenceImpl
	implements BlogsEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_D_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_D_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_D_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_D_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_D_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_D_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_D_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_D_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_D_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_D_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(BlogsEntry blogsEntry) {
		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { blogsEntry.getUuid(), new Long(
					blogsEntry.getGroupId()) }, blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntry.getGroupId()),
				
			blogsEntry.getUrlTitle()
			}, blogsEntry);
	}

	public void cacheResult(List<BlogsEntry> blogsEntries) {
		for (BlogsEntry blogsEntry : blogsEntries) {
			if (EntityCacheUtil.getResult(
						BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
						BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), this) == null) {
				cacheResult(blogsEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(BlogsEntryImpl.class.getName());
		EntityCacheUtil.clearCache(BlogsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public BlogsEntry create(long entryId) {
		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setNew(true);
		blogsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		blogsEntry.setUuid(uuid);

		return blogsEntry;
	}

	public BlogsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsEntry blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
					new Long(entryId));

			if (blogsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BlogsEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No BlogsEntry exists with the primary key " + entryId);
			}

			return remove(blogsEntry);
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

	public BlogsEntry remove(BlogsEntry blogsEntry) throws SystemException {
		for (ModelListener<BlogsEntry> listener : listeners) {
			listener.onBeforeRemove(blogsEntry);
		}

		blogsEntry = removeImpl(blogsEntry);

		for (ModelListener<BlogsEntry> listener : listeners) {
			listener.onAfterRemove(blogsEntry);
		}

		return blogsEntry;
	}

	protected BlogsEntry removeImpl(BlogsEntry blogsEntry)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (blogsEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BlogsEntryImpl.class,
						blogsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(blogsEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				blogsEntryModelImpl.getOriginalUuid(),
				new Long(blogsEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntryModelImpl.getOriginalGroupId()),
				
			blogsEntryModelImpl.getOriginalUrlTitle()
			});

		EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey());

		return blogsEntry;
	}

	/**
	 * @deprecated Use <code>update(BlogsEntry blogsEntry, boolean merge)</code>.
	 */
	public BlogsEntry update(BlogsEntry blogsEntry) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(BlogsEntry blogsEntry) method. Use update(BlogsEntry blogsEntry, boolean merge) instead.");
		}

		return update(blogsEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        blogsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when blogsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public BlogsEntry update(BlogsEntry blogsEntry, boolean merge)
		throws SystemException {
		boolean isNew = blogsEntry.isNew();

		for (ModelListener<BlogsEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(blogsEntry);
			}
			else {
				listener.onBeforeUpdate(blogsEntry);
			}
		}

		blogsEntry = updateImpl(blogsEntry, merge);

		for (ModelListener<BlogsEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(blogsEntry);
			}
			else {
				listener.onAfterUpdate(blogsEntry);
			}
		}

		return blogsEntry;
	}

	public BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws SystemException {
		boolean isNew = blogsEntry.isNew();

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		if (Validator.isNull(blogsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			blogsEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, blogsEntry, merge);

			blogsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		if (!isNew &&
				(!Validator.equals(blogsEntry.getUuid(),
					blogsEntryModelImpl.getOriginalUuid()) ||
				(blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					blogsEntryModelImpl.getOriginalUuid(),
					new Long(blogsEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!blogsEntry.getUuid()
								.equals(blogsEntryModelImpl.getOriginalUuid()) ||
				(blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					blogsEntry.getUuid(), new Long(blogsEntry.getGroupId())
				}, blogsEntry);
		}

		if (!isNew &&
				((blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(blogsEntry.getUrlTitle(),
					blogsEntryModelImpl.getOriginalUrlTitle()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
				new Object[] {
					new Long(blogsEntryModelImpl.getOriginalGroupId()),
					
				blogsEntryModelImpl.getOriginalUrlTitle()
				});
		}

		if (isNew ||
				((blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()) ||
				!blogsEntry.getUrlTitle()
							   .equals(blogsEntryModelImpl.getOriginalUrlTitle()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
				new Object[] {
					new Long(blogsEntry.getGroupId()),
					
				blogsEntry.getUrlTitle()
				}, blogsEntry);
		}

		return blogsEntry;
	}

	public BlogsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByPrimaryKey(entryId);

		if (blogsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BlogsEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No BlogsEntry exists with the primary key " + entryId);
		}

		return blogsEntry;
	}

	public BlogsEntry fetchByPrimaryKey(long entryId) throws SystemException {
		BlogsEntry blogsEntry = (BlogsEntry)EntityCacheUtil.getResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlogsEntryImpl.class, entryId, this);

		if (blogsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (blogsEntry != null) {
					cacheResult(blogsEntry);
				}

				closeSession(session);
			}
		}

		return blogsEntry;
	}

	public List<BlogsEntry> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<BlogsEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByUuid(uuid);

		List<BlogsEntry> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByUuid_PrevAndNext(long entryId, String uuid,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByUUID_G(uuid, groupId);

		if (blogsEntry == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	public BlogsEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public BlogsEntry fetchByUUID_G(String uuid, long groupId,
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
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

				query.append("displayDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<BlogsEntry> list = q.list();

				result = list;

				BlogsEntry blogsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					blogsEntry = list.get(0);

					cacheResult(blogsEntry);

					if ((blogsEntry.getUuid() == null) ||
							!blogsEntry.getUuid().equals(uuid) ||
							(blogsEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, blogsEntry);
					}
				}

				return blogsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<BlogsEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (BlogsEntry)result;
			}
		}
	}

	public List<BlogsEntry> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BlogsEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<BlogsEntry> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsEntry> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<BlogsEntry> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<BlogsEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

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

	public BlogsEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<BlogsEntry> list = findByG_U(groupId, userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

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

	public BlogsEntry[] findByG_U_PrevAndNext(long entryId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsEntry findByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByG_UT(groupId, urlTitle);

		if (blogsEntry == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("urlTitle=" + urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	public BlogsEntry fetchByG_UT(long groupId, String urlTitle)
		throws SystemException {
		return fetchByG_UT(groupId, urlTitle, true);
	}

	public BlogsEntry fetchByG_UT(long groupId, String urlTitle,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_UT,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("urlTitle IS NULL");
				}
				else {
					query.append("urlTitle = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				List<BlogsEntry> list = q.list();

				result = list;

				BlogsEntry blogsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, list);
				}
				else {
					blogsEntry = list.get(0);

					cacheResult(blogsEntry);

					if ((blogsEntry.getGroupId() != groupId) ||
							(blogsEntry.getUrlTitle() == null) ||
							!blogsEntry.getUrlTitle().equals(urlTitle)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
							finderArgs, blogsEntry);
					}
				}

				return blogsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, new ArrayList<BlogsEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (BlogsEntry)result;
			}
		}
	}

	public List<BlogsEntry> findByG_D_D(long groupId, Date displayDate,
		boolean draft) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate, Boolean.valueOf(draft)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_D_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_D_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_D_D(long groupId, Date displayDate,
		boolean draft, int start, int end) throws SystemException {
		return findByG_D_D(groupId, displayDate, draft, start, end, null);
	}

	public List<BlogsEntry> findByG_D_D(long groupId, Date displayDate,
		boolean draft, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate, Boolean.valueOf(draft),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_D_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_D_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_D_D_First(long groupId, Date displayDate,
		boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_D_D(groupId, displayDate, draft, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("displayDate=" + displayDate);

			msg.append(", ");
			msg.append("draft=" + draft);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_D_D_Last(long groupId, Date displayDate,
		boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByG_D_D(groupId, displayDate, draft);

		List<BlogsEntry> list = findByG_D_D(groupId, displayDate, draft,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("displayDate=" + displayDate);

			msg.append(", ");
			msg.append("draft=" + draft);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_D_D_PrevAndNext(long entryId, long groupId,
		Date displayDate, boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByG_D_D(groupId, displayDate, draft);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			if (displayDate == null) {
				query.append("displayDate < null");
			}
			else {
				query.append("displayDate < ?");
			}

			query.append(" AND ");

			query.append("draft = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(draft);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsEntry> findByC_D_D(long companyId, Date displayDate,
		boolean draft) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate, Boolean.valueOf(draft)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_D_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_D_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByC_D_D(long companyId, Date displayDate,
		boolean draft, int start, int end) throws SystemException {
		return findByC_D_D(companyId, displayDate, draft, start, end, null);
	}

	public List<BlogsEntry> findByC_D_D(long companyId, Date displayDate,
		boolean draft, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate, Boolean.valueOf(draft),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_D_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_D_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByC_D_D_First(long companyId, Date displayDate,
		boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_D_D(companyId, displayDate, draft, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("displayDate=" + displayDate);

			msg.append(", ");
			msg.append("draft=" + draft);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByC_D_D_Last(long companyId, Date displayDate,
		boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByC_D_D(companyId, displayDate, draft);

		List<BlogsEntry> list = findByC_D_D(companyId, displayDate, draft,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("displayDate=" + displayDate);

			msg.append(", ");
			msg.append("draft=" + draft);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByC_D_D_PrevAndNext(long entryId, long companyId,
		Date displayDate, boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByC_D_D(companyId, displayDate, draft);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			if (displayDate == null) {
				query.append("displayDate < null");
			}
			else {
				query.append("displayDate < ?");
			}

			query.append(" AND ");

			query.append("draft = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(draft);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsEntry> findByG_U_D_D(long groupId, long userId,
		Date displayDate, boolean draft) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate, Boolean.valueOf(draft)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_D_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("displayDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_D_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_U_D_D(long groupId, long userId,
		Date displayDate, boolean draft, int start, int end)
		throws SystemException {
		return findByG_U_D_D(groupId, userId, displayDate, draft, start, end,
			null);
	}

	public List<BlogsEntry> findByG_U_D_D(long groupId, long userId,
		Date displayDate, boolean draft, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate, Boolean.valueOf(draft),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U_D_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U_D_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_U_D_D_First(long groupId, long userId,
		Date displayDate, boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_D_D(groupId, userId, displayDate,
				draft, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("displayDate=" + displayDate);

			msg.append(", ");
			msg.append("draft=" + draft);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_U_D_D_Last(long groupId, long userId,
		Date displayDate, boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_D_D(groupId, userId, displayDate, draft);

		List<BlogsEntry> list = findByG_U_D_D(groupId, userId, displayDate,
				draft, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("displayDate=" + displayDate);

			msg.append(", ");
			msg.append("draft=" + draft);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_U_D_D_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, boolean draft, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		int count = countByG_U_D_D(groupId, userId, displayDate, draft);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("userId = ?");

			query.append(" AND ");

			if (displayDate == null) {
				query.append("displayDate < null");
			}
			else {
				query.append("displayDate < ?");
			}

			query.append(" AND ");

			query.append("draft = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(draft);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry);

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = (BlogsEntry)objArray[0];
			array[1] = (BlogsEntry)objArray[1];
			array[2] = (BlogsEntry)objArray[2];

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

	public List<BlogsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BlogsEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portlet.blogs.model.BlogsEntry ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("displayDate DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (BlogsEntry blogsEntry : findByUuid(uuid)) {
			remove(blogsEntry);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByUUID_G(uuid, groupId);

		remove(blogsEntry);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsEntry blogsEntry : findByGroupId(groupId)) {
			remove(blogsEntry);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (BlogsEntry blogsEntry : findByCompanyId(companyId)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U(groupId, userId)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByG_UT(groupId, urlTitle);

		remove(blogsEntry);
	}

	public void removeByG_D_D(long groupId, Date displayDate, boolean draft)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_D_D(groupId, displayDate, draft)) {
			remove(blogsEntry);
		}
	}

	public void removeByC_D_D(long companyId, Date displayDate, boolean draft)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_D_D(companyId, displayDate, draft)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_U_D_D(long groupId, long userId, Date displayDate,
		boolean draft) throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_D_D(groupId, userId,
				displayDate, draft)) {
			remove(blogsEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (BlogsEntry blogsEntry : findAll()) {
			remove(blogsEntry);
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
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("companyId = ?");

				query.append(" ");

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
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

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

	public int countByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("urlTitle IS NULL");
				}
				else {
					query.append("urlTitle = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_D_D(long groupId, Date displayDate, boolean draft)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate, Boolean.valueOf(draft)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_D_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_D_D,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_D_D(long companyId, Date displayDate, boolean draft)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate, Boolean.valueOf(draft)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_D_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_D_D,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U_D_D(long groupId, long userId, Date displayDate,
		boolean draft) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate, Boolean.valueOf(draft)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_D_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (displayDate == null) {
					query.append("displayDate < null");
				}
				else {
					query.append("displayDate < ?");
				}

				query.append(" AND ");

				query.append("draft = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(draft);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_D_D,
					finderArgs, count);

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
						"SELECT COUNT(*) FROM com.liferay.portlet.blogs.model.BlogsEntry");

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
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsEntry>> listenersList = new ArrayList<ModelListener<BlogsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence.impl")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	private static Log _log = LogFactoryUtil.getLog(BlogsEntryPersistenceImpl.class);
}