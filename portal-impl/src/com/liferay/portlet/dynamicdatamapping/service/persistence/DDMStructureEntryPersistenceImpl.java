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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureEntryImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m structure entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryPersistence
 * @see DDMStructureEntryUtil
 * @generated
 */
public class DDMStructureEntryPersistenceImpl extends BasePersistenceImpl<DDMStructureEntry>
	implements DDMStructureEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureEntryUtil} to access the d d m structure entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_S = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the d d m structure entry in the entity cache if it is enabled.
	 *
	 * @param ddmStructureEntry the d d m structure entry to cache
	 */
	public void cacheResult(DDMStructureEntry ddmStructureEntry) {
		EntityCacheUtil.putResult(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryImpl.class, ddmStructureEntry.getPrimaryKey(),
			ddmStructureEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmStructureEntry.getUuid(),
				Long.valueOf(ddmStructureEntry.getGroupId())
			}, ddmStructureEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				Long.valueOf(ddmStructureEntry.getGroupId()),
				
			ddmStructureEntry.getStructureId()
			}, ddmStructureEntry);
	}

	/**
	 * Caches the d d m structure entries in the entity cache if it is enabled.
	 *
	 * @param ddmStructureEntries the d d m structure entries to cache
	 */
	public void cacheResult(List<DDMStructureEntry> ddmStructureEntries) {
		for (DDMStructureEntry ddmStructureEntry : ddmStructureEntries) {
			if (EntityCacheUtil.getResult(
						DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureEntryImpl.class,
						ddmStructureEntry.getPrimaryKey(), this) == null) {
				cacheResult(ddmStructureEntry);
			}
		}
	}

	/**
	 * Clears the cache for all d d m structure entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMStructureEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMStructureEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d d m structure entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DDMStructureEntry ddmStructureEntry) {
		EntityCacheUtil.removeResult(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryImpl.class, ddmStructureEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmStructureEntry.getUuid(),
				Long.valueOf(ddmStructureEntry.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				Long.valueOf(ddmStructureEntry.getGroupId()),
				
			ddmStructureEntry.getStructureId()
			});
	}

	/**
	 * Creates a new d d m structure entry with the primary key. Does not add the d d m structure entry to the database.
	 *
	 * @param structureEntryId the primary key for the new d d m structure entry
	 * @return the new d d m structure entry
	 */
	public DDMStructureEntry create(long structureEntryId) {
		DDMStructureEntry ddmStructureEntry = new DDMStructureEntryImpl();

		ddmStructureEntry.setNew(true);
		ddmStructureEntry.setPrimaryKey(structureEntryId);

		String uuid = PortalUUIDUtil.generate();

		ddmStructureEntry.setUuid(uuid);

		return ddmStructureEntry;
	}

	/**
	 * Removes the d d m structure entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure entry to remove
	 * @return the d d m structure entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d d m structure entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureEntryId the primary key of the d d m structure entry to remove
	 * @return the d d m structure entry that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry remove(long structureEntryId)
		throws NoSuchStructureEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMStructureEntry ddmStructureEntry = (DDMStructureEntry)session.get(DDMStructureEntryImpl.class,
					Long.valueOf(structureEntryId));

			if (ddmStructureEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						structureEntryId);
				}

				throw new NoSuchStructureEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					structureEntryId);
			}

			return ddmStructureEntryPersistence.remove(ddmStructureEntry);
		}
		catch (NoSuchStructureEntryException nsee) {
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
	 * Removes the d d m structure entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureEntry the d d m structure entry to remove
	 * @return the d d m structure entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry remove(DDMStructureEntry ddmStructureEntry)
		throws SystemException {
		return super.remove(ddmStructureEntry);
	}

	protected DDMStructureEntry removeImpl(DDMStructureEntry ddmStructureEntry)
		throws SystemException {
		ddmStructureEntry = toUnwrappedModel(ddmStructureEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ddmStructureEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DDMStructureEntryModelImpl ddmStructureEntryModelImpl = (DDMStructureEntryModelImpl)ddmStructureEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmStructureEntryModelImpl.getUuid(),
				Long.valueOf(ddmStructureEntryModelImpl.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				Long.valueOf(ddmStructureEntryModelImpl.getGroupId()),
				
			ddmStructureEntryModelImpl.getStructureId()
			});

		EntityCacheUtil.removeResult(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryImpl.class, ddmStructureEntry.getPrimaryKey());

		return ddmStructureEntry;
	}

	public DDMStructureEntry updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry ddmStructureEntry,
		boolean merge) throws SystemException {
		ddmStructureEntry = toUnwrappedModel(ddmStructureEntry);

		boolean isNew = ddmStructureEntry.isNew();

		DDMStructureEntryModelImpl ddmStructureEntryModelImpl = (DDMStructureEntryModelImpl)ddmStructureEntry;

		if (Validator.isNull(ddmStructureEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStructureEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ddmStructureEntry, merge);

			ddmStructureEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryImpl.class, ddmStructureEntry.getPrimaryKey(),
			ddmStructureEntry);

		if (!isNew &&
				(!Validator.equals(ddmStructureEntry.getUuid(),
					ddmStructureEntryModelImpl.getOriginalUuid()) ||
				(ddmStructureEntry.getGroupId() != ddmStructureEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					ddmStructureEntryModelImpl.getOriginalUuid(),
					Long.valueOf(
						ddmStructureEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(ddmStructureEntry.getUuid(),
					ddmStructureEntryModelImpl.getOriginalUuid()) ||
				(ddmStructureEntry.getGroupId() != ddmStructureEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					ddmStructureEntry.getUuid(),
					Long.valueOf(ddmStructureEntry.getGroupId())
				}, ddmStructureEntry);
		}

		if (!isNew &&
				((ddmStructureEntry.getGroupId() != ddmStructureEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(ddmStructureEntry.getStructureId(),
					ddmStructureEntryModelImpl.getOriginalStructureId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					Long.valueOf(
						ddmStructureEntryModelImpl.getOriginalGroupId()),
					
				ddmStructureEntryModelImpl.getOriginalStructureId()
				});
		}

		if (isNew ||
				((ddmStructureEntry.getGroupId() != ddmStructureEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(ddmStructureEntry.getStructureId(),
					ddmStructureEntryModelImpl.getOriginalStructureId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					Long.valueOf(ddmStructureEntry.getGroupId()),
					
				ddmStructureEntry.getStructureId()
				}, ddmStructureEntry);
		}

		return ddmStructureEntry;
	}

	protected DDMStructureEntry toUnwrappedModel(
		DDMStructureEntry ddmStructureEntry) {
		if (ddmStructureEntry instanceof DDMStructureEntryImpl) {
			return ddmStructureEntry;
		}

		DDMStructureEntryImpl ddmStructureEntryImpl = new DDMStructureEntryImpl();

		ddmStructureEntryImpl.setNew(ddmStructureEntry.isNew());
		ddmStructureEntryImpl.setPrimaryKey(ddmStructureEntry.getPrimaryKey());

		ddmStructureEntryImpl.setUuid(ddmStructureEntry.getUuid());
		ddmStructureEntryImpl.setStructureEntryId(ddmStructureEntry.getStructureEntryId());
		ddmStructureEntryImpl.setGroupId(ddmStructureEntry.getGroupId());
		ddmStructureEntryImpl.setCompanyId(ddmStructureEntry.getCompanyId());
		ddmStructureEntryImpl.setUserId(ddmStructureEntry.getUserId());
		ddmStructureEntryImpl.setUserName(ddmStructureEntry.getUserName());
		ddmStructureEntryImpl.setCreateDate(ddmStructureEntry.getCreateDate());
		ddmStructureEntryImpl.setModifiedDate(ddmStructureEntry.getModifiedDate());
		ddmStructureEntryImpl.setStructureId(ddmStructureEntry.getStructureId());
		ddmStructureEntryImpl.setName(ddmStructureEntry.getName());
		ddmStructureEntryImpl.setDescription(ddmStructureEntry.getDescription());
		ddmStructureEntryImpl.setXsd(ddmStructureEntry.getXsd());

		return ddmStructureEntryImpl;
	}

	/**
	 * Finds the d d m structure entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure entry to find
	 * @return the d d m structure entry
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d m structure entry with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException} if it could not be found.
	 *
	 * @param structureEntryId the primary key of the d d m structure entry to find
	 * @return the d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByPrimaryKey(long structureEntryId)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = fetchByPrimaryKey(structureEntryId);

		if (ddmStructureEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + structureEntryId);
			}

			throw new NoSuchStructureEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				structureEntryId);
		}

		return ddmStructureEntry;
	}

	/**
	 * Finds the d d m structure entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure entry to find
	 * @return the d d m structure entry, or <code>null</code> if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d m structure entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureEntryId the primary key of the d d m structure entry to find
	 * @return the d d m structure entry, or <code>null</code> if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry fetchByPrimaryKey(long structureEntryId)
		throws SystemException {
		DDMStructureEntry ddmStructureEntry = (DDMStructureEntry)EntityCacheUtil.getResult(DDMStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureEntryImpl.class, structureEntryId, this);

		if (ddmStructureEntry == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStructureEntry = (DDMStructureEntry)session.get(DDMStructureEntryImpl.class,
						Long.valueOf(structureEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ddmStructureEntry != null) {
					cacheResult(ddmStructureEntry);
				}

				closeSession(session);
			}
		}

		return ddmStructureEntry;
	}

	/**
	 * Finds all the d d m structure entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m structure entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d d m structure entries to return
	 * @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	 * @return the range of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m structure entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d d m structure entries to return
	 * @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureEntry> list = (List<DDMStructureEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_DDMSTRUCTUREENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

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

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DDMStructureEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d d m structure entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		List<DDMStructureEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d m structure entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		int count = countByUuid(uuid);

		List<DDMStructureEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d m structure entries before and after the current d d m structure entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureEntryId the primary key of the current d d m structure entry
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry[] findByUuid_PrevAndNext(long structureEntryId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = findByPrimaryKey(structureEntryId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureEntry[] array = new DDMStructureEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmStructureEntry, uuid,
					orderByComparator, true);

			array[1] = ddmStructureEntry;

			array[2] = getByUuid_PrevAndNext(session, ddmStructureEntry, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureEntry getByUuid_PrevAndNext(Session session,
		DDMStructureEntry ddmStructureEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTUREENTRY_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d d m structure entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = fetchByUUID_G(uuid, groupId);

		if (ddmStructureEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureEntryException(msg.toString());
		}

		return ddmStructureEntry;
	}

	/**
	 * Finds the d d m structure entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching d d m structure entry, or <code>null</code> if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the d d m structure entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching d d m structure entry, or <code>null</code> if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMSTRUCTUREENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DDMStructureEntry> list = q.list();

				result = list;

				DDMStructureEntry ddmStructureEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					ddmStructureEntry = list.get(0);

					cacheResult(ddmStructureEntry);

					if ((ddmStructureEntry.getUuid() == null) ||
							!ddmStructureEntry.getUuid().equals(uuid) ||
							(ddmStructureEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmStructureEntry);
					}
				}

				return ddmStructureEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (DDMStructureEntry)result;
			}
		}
	}

	/**
	 * Finds all the d d m structure entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m structure entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d d m structure entries to return
	 * @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	 * @return the range of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m structure entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d d m structure entries to return
	 * @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureEntry> list = (List<DDMStructureEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_DDMSTRUCTUREENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<DDMStructureEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d d m structure entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		List<DDMStructureEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d m structure entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<DDMStructureEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d m structure entries before and after the current d d m structure entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureEntryId the primary key of the current d d m structure entry
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a d d m structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry[] findByGroupId_PrevAndNext(
		long structureEntryId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = findByPrimaryKey(structureEntryId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureEntry[] array = new DDMStructureEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, ddmStructureEntry,
					groupId, orderByComparator, true);

			array[1] = ddmStructureEntry;

			array[2] = getByGroupId_PrevAndNext(session, ddmStructureEntry,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureEntry getByGroupId_PrevAndNext(Session session,
		DDMStructureEntry ddmStructureEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTUREENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d d m structure entry where groupId = &#63; and structureId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException} if it could not be found.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching d d m structure entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry findByG_S(long groupId, String structureId)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = fetchByG_S(groupId, structureId);

		if (ddmStructureEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureEntryException(msg.toString());
		}

		return ddmStructureEntry;
	}

	/**
	 * Finds the d d m structure entry where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching d d m structure entry, or <code>null</code> if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry fetchByG_S(long groupId, String structureId)
		throws SystemException {
		return fetchByG_S(groupId, structureId, true);
	}

	/**
	 * Finds the d d m structure entry where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching d d m structure entry, or <code>null</code> if a matching d d m structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntry fetchByG_S(long groupId, String structureId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_S,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMSTRUCTUREENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<DDMStructureEntry> list = q.list();

				result = list;

				DDMStructureEntry ddmStructureEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
						finderArgs, list);
				}
				else {
					ddmStructureEntry = list.get(0);

					cacheResult(ddmStructureEntry);

					if ((ddmStructureEntry.getGroupId() != groupId) ||
							(ddmStructureEntry.getStructureId() == null) ||
							!ddmStructureEntry.getStructureId()
												  .equals(structureId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
							finderArgs, ddmStructureEntry);
					}
				}

				return ddmStructureEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
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
				return (DDMStructureEntry)result;
			}
		}
	}

	/**
	 * Finds all the d d m structure entries.
	 *
	 * @return the d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m structure entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure entries to return
	 * @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	 * @return the range of d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m structure entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure entries to return
	 * @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureEntry> list = (List<DDMStructureEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTUREENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTUREENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMStructureEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMStructureEntry>)QueryUtil.list(q,
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
	 * Removes all the d d m structure entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDMStructureEntry ddmStructureEntry : findByUuid(uuid)) {
			ddmStructureEntryPersistence.remove(ddmStructureEntry);
		}
	}

	/**
	 * Removes the d d m structure entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = findByUUID_G(uuid, groupId);

		ddmStructureEntryPersistence.remove(ddmStructureEntry);
	}

	/**
	 * Removes all the d d m structure entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DDMStructureEntry ddmStructureEntry : findByGroupId(groupId)) {
			ddmStructureEntryPersistence.remove(ddmStructureEntry);
		}
	}

	/**
	 * Removes the d d m structure entry where groupId = &#63; and structureId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, String structureId)
		throws NoSuchStructureEntryException, SystemException {
		DDMStructureEntry ddmStructureEntry = findByG_S(groupId, structureId);

		ddmStructureEntryPersistence.remove(ddmStructureEntry);
	}

	/**
	 * Removes all the d d m structure entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMStructureEntry ddmStructureEntry : findAll()) {
			ddmStructureEntryPersistence.remove(ddmStructureEntry);
		}
	}

	/**
	 * Counts all the d d m structure entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTUREENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the d d m structure entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the number of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTUREENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the d d m structure entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTUREENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the d d m structure entries where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the number of matching d d m structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTUREENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d d m structure entries.
	 *
	 * @return the number of d d m structure entries
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

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTUREENTRY);

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
	 * Initializes the d d m structure entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMStructureEntry>> listenersList = new ArrayList<ModelListener<DDMStructureEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMStructureEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDMStructureEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DDMStructureEntryPersistence.class)
	protected DDMStructureEntryPersistence ddmStructureEntryPersistence;
	@BeanReference(type = DDMStructureEntryLinkPersistence.class)
	protected DDMStructureEntryLinkPersistence ddmStructureEntryLinkPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DDMSTRUCTUREENTRY = "SELECT ddmStructureEntry FROM DDMStructureEntry ddmStructureEntry";
	private static final String _SQL_SELECT_DDMSTRUCTUREENTRY_WHERE = "SELECT ddmStructureEntry FROM DDMStructureEntry ddmStructureEntry WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTUREENTRY = "SELECT COUNT(ddmStructureEntry) FROM DDMStructureEntry ddmStructureEntry";
	private static final String _SQL_COUNT_DDMSTRUCTUREENTRY_WHERE = "SELECT COUNT(ddmStructureEntry) FROM DDMStructureEntry ddmStructureEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmStructureEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmStructureEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmStructureEntry.uuid IS NULL OR ddmStructureEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmStructureEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmStructureEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmStructureEntry.uuid IS NULL OR ddmStructureEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmStructureEntry.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmStructureEntry.groupId = ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "ddmStructureEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "ddmStructureEntry.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "ddmStructureEntry.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(ddmStructureEntry.structureId IS NULL OR ddmStructureEntry.structureId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructureEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructureEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMStructureEntryPersistenceImpl.class);
}