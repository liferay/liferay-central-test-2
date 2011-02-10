/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryEntryModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the repository entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryPersistence
 * @see RepositoryEntryUtil
 * @generated
 */
public class RepositoryEntryPersistenceImpl extends BasePersistenceImpl<RepositoryEntry>
	implements RepositoryEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RepositoryEntryUtil} to access the repository entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RepositoryEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_REPOSITORYID = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRepositoryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_REPOSITORYID = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRepositoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_R_M = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByR_M",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_R_M = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_M",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the repository entry in the entity cache if it is enabled.
	 *
	 * @param repositoryEntry the repository entry to cache
	 */
	public void cacheResult(RepositoryEntry repositoryEntry) {
		EntityCacheUtil.putResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey(),
			repositoryEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
			new Object[] {
				new Long(repositoryEntry.getRepositoryId()),
				
			repositoryEntry.getMappedId()
			}, repositoryEntry);
	}

	/**
	 * Caches the repository entries in the entity cache if it is enabled.
	 *
	 * @param repositoryEntries the repository entries to cache
	 */
	public void cacheResult(List<RepositoryEntry> repositoryEntries) {
		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			if (EntityCacheUtil.getResult(
						RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryEntryImpl.class,
						repositoryEntry.getPrimaryKey(), this) == null) {
				cacheResult(repositoryEntry);
			}
		}
	}

	/**
	 * Clears the cache for all repository entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(RepositoryEntryImpl.class.getName());
		EntityCacheUtil.clearCache(RepositoryEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the repository entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(RepositoryEntry repositoryEntry) {
		EntityCacheUtil.removeResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M,
			new Object[] {
				new Long(repositoryEntry.getRepositoryId()),
				
			repositoryEntry.getMappedId()
			});
	}

	/**
	 * Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	 *
	 * @param repositoryEntryId the primary key for the new repository entry
	 * @return the new repository entry
	 */
	public RepositoryEntry create(long repositoryEntryId) {
		RepositoryEntry repositoryEntry = new RepositoryEntryImpl();

		repositoryEntry.setNew(true);
		repositoryEntry.setPrimaryKey(repositoryEntryId);

		return repositoryEntry;
	}

	/**
	 * Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository entry to remove
	 * @return the repository entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param repositoryEntryId the primary key of the repository entry to remove
	 * @return the repository entry that was removed
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry remove(long repositoryEntryId)
		throws NoSuchRepositoryEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RepositoryEntry repositoryEntry = (RepositoryEntry)session.get(RepositoryEntryImpl.class,
					new Long(repositoryEntryId));

			if (repositoryEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						repositoryEntryId);
				}

				throw new NoSuchRepositoryEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					repositoryEntryId);
			}

			return repositoryEntryPersistence.remove(repositoryEntry);
		}
		catch (NoSuchRepositoryEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the repository entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param repositoryEntry the repository entry to remove
	 * @return the repository entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry remove(RepositoryEntry repositoryEntry)
		throws SystemException {
		return super.remove(repositoryEntry);
	}

	protected RepositoryEntry removeImpl(RepositoryEntry repositoryEntry)
		throws SystemException {
		repositoryEntry = toUnwrappedModel(repositoryEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, repositoryEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		RepositoryEntryModelImpl repositoryEntryModelImpl = (RepositoryEntryModelImpl)repositoryEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M,
			new Object[] {
				new Long(repositoryEntryModelImpl.getOriginalRepositoryId()),
				
			repositoryEntryModelImpl.getOriginalMappedId()
			});

		EntityCacheUtil.removeResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey());

		return repositoryEntry;
	}

	public RepositoryEntry updateImpl(
		com.liferay.portal.model.RepositoryEntry repositoryEntry, boolean merge)
		throws SystemException {
		repositoryEntry = toUnwrappedModel(repositoryEntry);

		boolean isNew = repositoryEntry.isNew();

		RepositoryEntryModelImpl repositoryEntryModelImpl = (RepositoryEntryModelImpl)repositoryEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, repositoryEntry, merge);

			repositoryEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey(),
			repositoryEntry);

		if (!isNew &&
				((repositoryEntry.getRepositoryId() != repositoryEntryModelImpl.getOriginalRepositoryId()) ||
				!Validator.equals(repositoryEntry.getMappedId(),
					repositoryEntryModelImpl.getOriginalMappedId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M,
				new Object[] {
					new Long(repositoryEntryModelImpl.getOriginalRepositoryId()),
					
				repositoryEntryModelImpl.getOriginalMappedId()
				});
		}

		if (isNew ||
				((repositoryEntry.getRepositoryId() != repositoryEntryModelImpl.getOriginalRepositoryId()) ||
				!Validator.equals(repositoryEntry.getMappedId(),
					repositoryEntryModelImpl.getOriginalMappedId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
				new Object[] {
					new Long(repositoryEntry.getRepositoryId()),
					
				repositoryEntry.getMappedId()
				}, repositoryEntry);
		}

		return repositoryEntry;
	}

	protected RepositoryEntry toUnwrappedModel(RepositoryEntry repositoryEntry) {
		if (repositoryEntry instanceof RepositoryEntryImpl) {
			return repositoryEntry;
		}

		RepositoryEntryImpl repositoryEntryImpl = new RepositoryEntryImpl();

		repositoryEntryImpl.setNew(repositoryEntry.isNew());
		repositoryEntryImpl.setPrimaryKey(repositoryEntry.getPrimaryKey());

		repositoryEntryImpl.setRepositoryEntryId(repositoryEntry.getRepositoryEntryId());
		repositoryEntryImpl.setRepositoryId(repositoryEntry.getRepositoryId());
		repositoryEntryImpl.setMappedId(repositoryEntry.getMappedId());

		return repositoryEntryImpl;
	}

	/**
	 * Finds the repository entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository entry to find
	 * @return the repository entry
	 * @throws com.liferay.portal.NoSuchModelException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the repository entry with the primary key or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	 *
	 * @param repositoryEntryId the primary key of the repository entry to find
	 * @return the repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByPrimaryKey(long repositoryEntryId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByPrimaryKey(repositoryEntryId);

		if (repositoryEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + repositoryEntryId);
			}

			throw new NoSuchRepositoryEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				repositoryEntryId);
		}

		return repositoryEntry;
	}

	/**
	 * Finds the repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository entry to find
	 * @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param repositoryEntryId the primary key of the repository entry to find
	 * @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByPrimaryKey(long repositoryEntryId)
		throws SystemException {
		RepositoryEntry repositoryEntry = (RepositoryEntry)EntityCacheUtil.getResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryEntryImpl.class, repositoryEntryId, this);

		if (repositoryEntry == null) {
			Session session = null;

			try {
				session = openSession();

				repositoryEntry = (RepositoryEntry)session.get(RepositoryEntryImpl.class,
						new Long(repositoryEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (repositoryEntry != null) {
					cacheResult(repositoryEntry);
				}

				closeSession(session);
			}
		}

		return repositoryEntry;
	}

	/**
	 * Finds all the repository entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID to search with
	 * @return the matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByRepositoryId(long repositoryId)
		throws SystemException {
		return findByRepositoryId(repositoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryId the repository ID to search with
	 * @param start the lower bound of the range of repository entries to return
	 * @param end the upper bound of the range of repository entries to return (not inclusive)
	 * @return the range of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end) throws SystemException {
		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryId the repository ID to search with
	 * @param start the lower bound of the range of repository entries to return
	 * @param end the upper bound of the range of repository entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				repositoryId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<RepositoryEntry> list = (List<RepositoryEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_REPOSITORYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				list = (List<RepositoryEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_REPOSITORYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_REPOSITORYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryId the repository ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByRepositoryId_First(long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		List<RepositoryEntry> list = findByRepositoryId(repositoryId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repositoryId=");
			msg.append(repositoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRepositoryEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryId the repository ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByRepositoryId_Last(long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		int count = countByRepositoryId(repositoryId);

		List<RepositoryEntry> list = findByRepositoryId(repositoryId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repositoryId=");
			msg.append(repositoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRepositoryEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the repository entries before and after the current repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryEntryId the primary key of the current repository entry
	 * @param repositoryId the repository ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry[] findByRepositoryId_PrevAndNext(
		long repositoryEntryId, long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = findByPrimaryKey(repositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry[] array = new RepositoryEntryImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(session, repositoryEntry,
					repositoryId, orderByComparator, true);

			array[1] = repositoryEntry;

			array[2] = getByRepositoryId_PrevAndNext(session, repositoryEntry,
					repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RepositoryEntry getByRepositoryId_PrevAndNext(Session session,
		RepositoryEntry repositoryEntry, long repositoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

		query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(repositoryEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RepositoryEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the repository entry where repositoryId = &#63; and mappedId = &#63; or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	 *
	 * @param repositoryId the repository ID to search with
	 * @param mappedId the mapped ID to search with
	 * @return the matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByR_M(repositoryId, mappedId);

		if (repositoryEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repositoryId=");
			msg.append(repositoryId);

			msg.append(", mappedId=");
			msg.append(mappedId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRepositoryEntryException(msg.toString());
		}

		return repositoryEntry;
	}

	/**
	 * Finds the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param repositoryId the repository ID to search with
	 * @param mappedId the mapped ID to search with
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId)
		throws SystemException {
		return fetchByR_M(repositoryId, mappedId, true);
	}

	/**
	 * Finds the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param repositoryId the repository ID to search with
	 * @param mappedId the mapped ID to search with
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId, mappedId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_R_M,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			if (mappedId == null) {
				query.append(_FINDER_COLUMN_R_M_MAPPEDID_1);
			}
			else {
				if (mappedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				if (mappedId != null) {
					qPos.add(mappedId);
				}

				List<RepositoryEntry> list = q.list();

				result = list;

				RepositoryEntry repositoryEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
						finderArgs, list);
				}
				else {
					repositoryEntry = list.get(0);

					cacheResult(repositoryEntry);

					if ((repositoryEntry.getRepositoryId() != repositoryId) ||
							(repositoryEntry.getMappedId() == null) ||
							!repositoryEntry.getMappedId().equals(mappedId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
							finderArgs, repositoryEntry);
					}
				}

				return repositoryEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (RepositoryEntry)result;
			}
		}
	}

	/**
	 * Finds all the repository entries.
	 *
	 * @return the repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries to return
	 * @param end the upper bound of the range of repository entries to return (not inclusive)
	 * @return the range of repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries to return
	 * @param end the upper bound of the range of repository entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<RepositoryEntry> list = (List<RepositoryEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_REPOSITORYENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<RepositoryEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<RepositoryEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the repository entries where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRepositoryId(long repositoryId)
		throws SystemException {
		for (RepositoryEntry repositoryEntry : findByRepositoryId(repositoryId)) {
			repositoryEntryPersistence.remove(repositoryEntry);
		}
	}

	/**
	 * Removes the repository entry where repositoryId = &#63; and mappedId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID to search with
	 * @param mappedId the mapped ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = findByR_M(repositoryId, mappedId);

		repositoryEntryPersistence.remove(repositoryEntry);
	}

	/**
	 * Removes all the repository entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (RepositoryEntry repositoryEntry : findAll()) {
			repositoryEntryPersistence.remove(repositoryEntry);
		}
	}

	/**
	 * Counts all the repository entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID to search with
	 * @return the number of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRepositoryId(long repositoryId) throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the repository entries where repositoryId = &#63; and mappedId = &#63;.
	 *
	 * @param repositoryId the repository ID to search with
	 * @param mappedId the mapped ID to search with
	 * @return the number of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_M(long repositoryId, String mappedId)
		throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId, mappedId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			if (mappedId == null) {
				query.append(_FINDER_COLUMN_R_M_MAPPEDID_1);
			}
			else {
				if (mappedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				if (mappedId != null) {
					qPos.add(mappedId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_M, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the repository entries.
	 *
	 * @return the number of repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REPOSITORYENTRY);

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

	/**
	 * Initializes the repository entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.RepositoryEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<RepositoryEntry>> listenersList = new ArrayList<ModelListener<RepositoryEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<RepositoryEntry>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(RepositoryEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_REPOSITORYENTRY = "SELECT repositoryEntry FROM RepositoryEntry repositoryEntry";
	private static final String _SQL_SELECT_REPOSITORYENTRY_WHERE = "SELECT repositoryEntry FROM RepositoryEntry repositoryEntry WHERE ";
	private static final String _SQL_COUNT_REPOSITORYENTRY = "SELECT COUNT(repositoryEntry) FROM RepositoryEntry repositoryEntry";
	private static final String _SQL_COUNT_REPOSITORYENTRY_WHERE = "SELECT COUNT(repositoryEntry) FROM RepositoryEntry repositoryEntry WHERE ";
	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 = "repositoryEntry.repositoryId = ?";
	private static final String _FINDER_COLUMN_R_M_REPOSITORYID_2 = "repositoryEntry.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_R_M_MAPPEDID_1 = "repositoryEntry.mappedId IS NULL";
	private static final String _FINDER_COLUMN_R_M_MAPPEDID_2 = "repositoryEntry.mappedId = ?";
	private static final String _FINDER_COLUMN_R_M_MAPPEDID_3 = "(repositoryEntry.mappedId IS NULL OR repositoryEntry.mappedId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RepositoryEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RepositoryEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(RepositoryEntryPersistenceImpl.class);
}