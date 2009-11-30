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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="BlogsStatsUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserPersistence
 * @see       BlogsStatsUserUtil
 * @generated
 */
public class BlogsStatsUserPersistenceImpl extends BasePersistenceImpl<BlogsStatsUser>
	implements BlogsStatsUserPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsStatsUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_E",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_E",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_E",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_E",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_E",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_E = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_E",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(BlogsStatsUser blogsStatsUser) {
		EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(blogsStatsUser.getGroupId()),
				new Long(blogsStatsUser.getUserId())
			}, blogsStatsUser);
	}

	public void cacheResult(List<BlogsStatsUser> blogsStatsUsers) {
		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			if (EntityCacheUtil.getResult(
						BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKey(), this) == null) {
				cacheResult(blogsStatsUser);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(BlogsStatsUserImpl.class.getName());
		EntityCacheUtil.clearCache(BlogsStatsUserImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public BlogsStatsUser create(long statsUserId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setNew(true);
		blogsStatsUser.setPrimaryKey(statsUserId);

		return blogsStatsUser;
	}

	public BlogsStatsUser remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public BlogsStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
					new Long(statsUserId));

			if (blogsStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BlogsStatsUser exists with the primary key " +
						statsUserId);
				}

				throw new NoSuchStatsUserException(
					"No BlogsStatsUser exists with the primary key " +
					statsUserId);
			}

			return remove(blogsStatsUser);
		}
		catch (NoSuchStatsUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsStatsUser remove(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		for (ModelListener<BlogsStatsUser> listener : listeners) {
			listener.onBeforeRemove(blogsStatsUser);
		}

		blogsStatsUser = removeImpl(blogsStatsUser);

		for (ModelListener<BlogsStatsUser> listener : listeners) {
			listener.onAfterRemove(blogsStatsUser);
		}

		return blogsStatsUser;
	}

	protected BlogsStatsUser removeImpl(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		Session session = null;

		try {
			session = openSession();

			if (blogsStatsUser.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(blogsStatsUser);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BlogsStatsUserModelImpl blogsStatsUserModelImpl = (BlogsStatsUserModelImpl)blogsStatsUser;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(blogsStatsUserModelImpl.getOriginalGroupId()),
				new Long(blogsStatsUserModelImpl.getOriginalUserId())
			});

		EntityCacheUtil.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

		return blogsStatsUser;
	}

	public BlogsStatsUser updateImpl(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge) throws SystemException {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		boolean isNew = blogsStatsUser.isNew();

		BlogsStatsUserModelImpl blogsStatsUserModelImpl = (BlogsStatsUserModelImpl)blogsStatsUser;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, blogsStatsUser, merge);

			blogsStatsUser.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		if (!isNew &&
				((blogsStatsUser.getGroupId() != blogsStatsUserModelImpl.getOriginalGroupId()) ||
				(blogsStatsUser.getUserId() != blogsStatsUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(blogsStatsUserModelImpl.getOriginalGroupId()),
					new Long(blogsStatsUserModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((blogsStatsUser.getGroupId() != blogsStatsUserModelImpl.getOriginalGroupId()) ||
				(blogsStatsUser.getUserId() != blogsStatsUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(blogsStatsUser.getGroupId()),
					new Long(blogsStatsUser.getUserId())
				}, blogsStatsUser);
		}

		return blogsStatsUser;
	}

	protected BlogsStatsUser toUnwrappedModel(BlogsStatsUser blogsStatsUser) {
		if (blogsStatsUser instanceof BlogsStatsUserImpl) {
			return blogsStatsUser;
		}

		BlogsStatsUserImpl blogsStatsUserImpl = new BlogsStatsUserImpl();

		blogsStatsUserImpl.setNew(blogsStatsUser.isNew());
		blogsStatsUserImpl.setPrimaryKey(blogsStatsUser.getPrimaryKey());

		blogsStatsUserImpl.setStatsUserId(blogsStatsUser.getStatsUserId());
		blogsStatsUserImpl.setGroupId(blogsStatsUser.getGroupId());
		blogsStatsUserImpl.setCompanyId(blogsStatsUser.getCompanyId());
		blogsStatsUserImpl.setUserId(blogsStatsUser.getUserId());
		blogsStatsUserImpl.setEntryCount(blogsStatsUser.getEntryCount());
		blogsStatsUserImpl.setLastPostDate(blogsStatsUser.getLastPostDate());
		blogsStatsUserImpl.setRatingsTotalEntries(blogsStatsUser.getRatingsTotalEntries());
		blogsStatsUserImpl.setRatingsTotalScore(blogsStatsUser.getRatingsTotalScore());
		blogsStatsUserImpl.setRatingsAverageScore(blogsStatsUser.getRatingsAverageScore());

		return blogsStatsUserImpl;
	}

	public BlogsStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BlogsStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByPrimaryKey(statsUserId);

		if (blogsStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BlogsStatsUser exists with the primary key " +
					statsUserId);
			}

			throw new NoSuchStatsUserException(
				"No BlogsStatsUser exists with the primary key " + statsUserId);
		}

		return blogsStatsUser;
	}

	public BlogsStatsUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BlogsStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		BlogsStatsUser blogsStatsUser = (BlogsStatsUser)EntityCacheUtil.getResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				BlogsStatsUserImpl.class, statsUserId, this);

		if (blogsStatsUser == null) {
			Session session = null;

			try {
				session = openSession();

				blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
						new Long(statsUserId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (blogsStatsUser != null) {
					cacheResult(blogsStatsUser);
				}

				closeSession(session);
			}
		}

		return blogsStatsUser;
	}

	public List<BlogsStatsUser> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(" ORDER BY ");

				query.append("blogsStatsUser.entryCount DESC");

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
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("blogsStatsUser.");
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

					query.append("blogsStatsUser.entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByGroupId(groupId);

		List<BlogsStatsUser> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("blogsStatsUser.");
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

				query.append("blogsStatsUser.entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsStatsUser> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(" ORDER BY ");

				query.append("blogsStatsUser.entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<BlogsStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("blogsStatsUser.");
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

					query.append("blogsStatsUser.entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByUserId(userId);

		List<BlogsStatsUser> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("blogsStatsUser.");
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

				query.append("blogsStatsUser.entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByG_U(groupId, userId);

		if (blogsStatsUser == null) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsUserException(msg.toString());
		}

		return blogsStatsUser;
	}

	public BlogsStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	public BlogsStatsUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				query.append(" ORDER BY ");

				query.append("blogsStatsUser.entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<BlogsStatsUser> list = q.list();

				result = list;

				BlogsStatsUser blogsStatsUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					blogsStatsUser = list.get(0);

					cacheResult(blogsStatsUser);

					if ((blogsStatsUser.getGroupId() != groupId) ||
							(blogsStatsUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, blogsStatsUser);
					}
				}

				return blogsStatsUser;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, new ArrayList<BlogsStatsUser>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (BlogsStatsUser)result;
			}
		}
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_E,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_E_GROUPID_2);

				query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

				query.append(" ORDER BY ");

				query.append("blogsStatsUser.entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_E, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount,
		int start, int end) throws SystemException {
		return findByG_E(groupId, entryCount, start, end, null);
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_E,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_E_GROUPID_2);

				query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("blogsStatsUser.");
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

					query.append("blogsStatsUser.entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_E,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByG_E_First(long groupId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByG_E(groupId, entryCount, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByG_E_Last(long groupId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		int count = countByG_E(groupId, entryCount);

		List<BlogsStatsUser> list = findByG_E(groupId, entryCount, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByG_E_PrevAndNext(long statsUserId,
		long groupId, int entryCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByG_E(groupId, entryCount);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_E_GROUPID_2);

			query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("blogsStatsUser.");
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

				query.append("blogsStatsUser.entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(entryCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_E,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

				query.append(" ORDER BY ");

				query.append("blogsStatsUser.entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_E, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount,
		int start, int end) throws SystemException {
		return findByC_E(companyId, entryCount, start, end, null);
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_E,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("blogsStatsUser.");
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

					query.append("blogsStatsUser.entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_E,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsStatsUser findByC_E_First(long companyId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByC_E(companyId, entryCount, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByC_E_Last(long companyId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		int count = countByC_E(companyId, entryCount);

		List<BlogsStatsUser> list = findByC_E(companyId, entryCount, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByC_E_PrevAndNext(long statsUserId,
		long companyId, int entryCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByC_E(companyId, entryCount);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("blogsStatsUser.");
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

				query.append("blogsStatsUser.entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(entryCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

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

	public List<BlogsStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BlogsStatsUser> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_BLOGSSTATSUSER);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("blogsStatsUser.");
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

					query.append("blogsStatsUser.entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsStatsUser>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByGroupId(groupId)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByUserId(userId)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByG_U(groupId, userId);

		remove(blogsStatsUser);
	}

	public void removeByG_E(long groupId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByG_E(groupId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByC_E(long companyId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByC_E(companyId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	public void removeAll() throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findAll()) {
			remove(blogsStatsUser);
		}
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

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

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

	public int countByG_E(long groupId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_E,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_G_E_GROUPID_2);

				query.append(_FINDER_COLUMN_G_E_ENTRYCOUNT_2);

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_E, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_E(long companyId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_E,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

				query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_E_ENTRYCOUNT_2);

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_E, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_BLOGSSTATSUSER);

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
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsStatsUser>> listenersList = new ArrayList<ModelListener<BlogsStatsUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsStatsUser>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsStatsUser.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "blogsStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "blogsStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_E_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_E_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	private static final String _FINDER_COLUMN_C_E_COMPANYID_2 = "blogsStatsUser.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_E_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	private static final String _SQL_SELECT_BLOGSSTATSUSER = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_SELECT_BLOGSSTATSUSER_WHERE = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _SQL_COUNT_BLOGSSTATSUSER = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_COUNT_BLOGSSTATSUSER_WHERE = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static Log _log = LogFactoryUtil.getLog(BlogsStatsUserPersistenceImpl.class);
}