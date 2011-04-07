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

package com.liferay.portlet.dynamicdatalists.service.persistence;

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

import com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException;
import com.liferay.portlet.dynamicdatalists.model.DDLEntryItem;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLEntryItemImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLEntryItemModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLinkPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d l entry item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLEntryItemPersistence
 * @see DDLEntryItemUtil
 * @generated
 */
public class DDLEntryItemPersistenceImpl extends BasePersistenceImpl<DDLEntryItem>
	implements DDLEntryItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDLEntryItemUtil} to access the d d l entry item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDLEntryItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ENTRYID = new FinderPath(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByEntryId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the d d l entry item in the entity cache if it is enabled.
	 *
	 * @param ddlEntryItem the d d l entry item to cache
	 */
	public void cacheResult(DDLEntryItem ddlEntryItem) {
		EntityCacheUtil.putResult(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemImpl.class, ddlEntryItem.getPrimaryKey(), ddlEntryItem);

		ddlEntryItem.resetOriginalValues();
	}

	/**
	 * Caches the d d l entry items in the entity cache if it is enabled.
	 *
	 * @param ddlEntryItems the d d l entry items to cache
	 */
	public void cacheResult(List<DDLEntryItem> ddlEntryItems) {
		for (DDLEntryItem ddlEntryItem : ddlEntryItems) {
			if (EntityCacheUtil.getResult(
						DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
						DDLEntryItemImpl.class, ddlEntryItem.getPrimaryKey(),
						this) == null) {
				cacheResult(ddlEntryItem);
			}
		}
	}

	/**
	 * Clears the cache for all d d l entry items.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDLEntryItemImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDLEntryItemImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d d l entry item.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DDLEntryItem ddlEntryItem) {
		EntityCacheUtil.removeResult(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemImpl.class, ddlEntryItem.getPrimaryKey());
	}

	/**
	 * Creates a new d d l entry item with the primary key. Does not add the d d l entry item to the database.
	 *
	 * @param entryItemId the primary key for the new d d l entry item
	 * @return the new d d l entry item
	 */
	public DDLEntryItem create(long entryItemId) {
		DDLEntryItem ddlEntryItem = new DDLEntryItemImpl();

		ddlEntryItem.setNew(true);
		ddlEntryItem.setPrimaryKey(entryItemId);

		String uuid = PortalUUIDUtil.generate();

		ddlEntryItem.setUuid(uuid);

		return ddlEntryItem;
	}

	/**
	 * Removes the d d l entry item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d l entry item to remove
	 * @return the d d l entry item that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d d l entry item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryItemId the primary key of the d d l entry item to remove
	 * @return the d d l entry item that was removed
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem remove(long entryItemId)
		throws NoSuchEntryItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDLEntryItem ddlEntryItem = (DDLEntryItem)session.get(DDLEntryItemImpl.class,
					Long.valueOf(entryItemId));

			if (ddlEntryItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryItemId);
				}

				throw new NoSuchEntryItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					entryItemId);
			}

			return ddlEntryItemPersistence.remove(ddlEntryItem);
		}
		catch (NoSuchEntryItemException nsee) {
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
	 * Removes the d d l entry item from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlEntryItem the d d l entry item to remove
	 * @return the d d l entry item that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem remove(DDLEntryItem ddlEntryItem)
		throws SystemException {
		return super.remove(ddlEntryItem);
	}

	protected DDLEntryItem removeImpl(DDLEntryItem ddlEntryItem)
		throws SystemException {
		ddlEntryItem = toUnwrappedModel(ddlEntryItem);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ddlEntryItem);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemImpl.class, ddlEntryItem.getPrimaryKey());

		return ddlEntryItem;
	}

	public DDLEntryItem updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem,
		boolean merge) throws SystemException {
		ddlEntryItem = toUnwrappedModel(ddlEntryItem);

		if (Validator.isNull(ddlEntryItem.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddlEntryItem.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ddlEntryItem, merge);

			ddlEntryItem.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
			DDLEntryItemImpl.class, ddlEntryItem.getPrimaryKey(), ddlEntryItem);

		return ddlEntryItem;
	}

	protected DDLEntryItem toUnwrappedModel(DDLEntryItem ddlEntryItem) {
		if (ddlEntryItem instanceof DDLEntryItemImpl) {
			return ddlEntryItem;
		}

		DDLEntryItemImpl ddlEntryItemImpl = new DDLEntryItemImpl();

		ddlEntryItemImpl.setNew(ddlEntryItem.isNew());
		ddlEntryItemImpl.setPrimaryKey(ddlEntryItem.getPrimaryKey());

		ddlEntryItemImpl.setUuid(ddlEntryItem.getUuid());
		ddlEntryItemImpl.setEntryItemId(ddlEntryItem.getEntryItemId());
		ddlEntryItemImpl.setClassNameId(ddlEntryItem.getClassNameId());
		ddlEntryItemImpl.setClassPK(ddlEntryItem.getClassPK());
		ddlEntryItemImpl.setEntryId(ddlEntryItem.getEntryId());

		return ddlEntryItemImpl;
	}

	/**
	 * Finds the d d l entry item with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d l entry item to find
	 * @return the d d l entry item
	 * @throws com.liferay.portal.NoSuchModelException if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d l entry item with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException} if it could not be found.
	 *
	 * @param entryItemId the primary key of the d d l entry item to find
	 * @return the d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem findByPrimaryKey(long entryItemId)
		throws NoSuchEntryItemException, SystemException {
		DDLEntryItem ddlEntryItem = fetchByPrimaryKey(entryItemId);

		if (ddlEntryItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryItemId);
			}

			throw new NoSuchEntryItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryItemId);
		}

		return ddlEntryItem;
	}

	/**
	 * Finds the d d l entry item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d l entry item to find
	 * @return the d d l entry item, or <code>null</code> if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d l entry item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryItemId the primary key of the d d l entry item to find
	 * @return the d d l entry item, or <code>null</code> if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem fetchByPrimaryKey(long entryItemId)
		throws SystemException {
		DDLEntryItem ddlEntryItem = (DDLEntryItem)EntityCacheUtil.getResult(DDLEntryItemModelImpl.ENTITY_CACHE_ENABLED,
				DDLEntryItemImpl.class, entryItemId, this);

		if (ddlEntryItem == null) {
			Session session = null;

			try {
				session = openSession();

				ddlEntryItem = (DDLEntryItem)session.get(DDLEntryItemImpl.class,
						Long.valueOf(entryItemId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ddlEntryItem != null) {
					cacheResult(ddlEntryItem);
				}

				closeSession(session);
			}
		}

		return ddlEntryItem;
	}

	/**
	 * Finds all the d d l entry items where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d l entry items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d d l entry items to return
	 * @param end the upper bound of the range of d d l entry items to return (not inclusive)
	 * @return the range of matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d l entry items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d d l entry items to return
	 * @param end the upper bound of the range of d d l entry items to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDLEntryItem> list = (List<DDLEntryItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_DDLENTRYITEM_WHERE);

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

				list = (List<DDLEntryItem>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first d d l entry item in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryItemException, SystemException {
		List<DDLEntryItem> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d l entry item in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryItemException, SystemException {
		int count = countByUuid(uuid);

		List<DDLEntryItem> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d l entry items before and after the current d d l entry item in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryItemId the primary key of the current d d l entry item
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem[] findByUuid_PrevAndNext(long entryItemId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryItemException, SystemException {
		DDLEntryItem ddlEntryItem = findByPrimaryKey(entryItemId);

		Session session = null;

		try {
			session = openSession();

			DDLEntryItem[] array = new DDLEntryItemImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddlEntryItem, uuid,
					orderByComparator, true);

			array[1] = ddlEntryItem;

			array[2] = getByUuid_PrevAndNext(session, ddlEntryItem, uuid,
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

	protected DDLEntryItem getByUuid_PrevAndNext(Session session,
		DDLEntryItem ddlEntryItem, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLENTRYITEM_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(ddlEntryItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLEntryItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d d l entry items where entryId = &#63;.
	 *
	 * @param entryId the entry ID to search with
	 * @return the matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findByEntryId(long entryId)
		throws SystemException {
		return findByEntryId(entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d l entry items where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the entry ID to search with
	 * @param start the lower bound of the range of d d l entry items to return
	 * @param end the upper bound of the range of d d l entry items to return (not inclusive)
	 * @return the range of matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findByEntryId(long entryId, int start, int end)
		throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d l entry items where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the entry ID to search with
	 * @param start the lower bound of the range of d d l entry items to return
	 * @param end the upper bound of the range of d d l entry items to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findByEntryId(long entryId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				entryId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDLEntryItem> list = (List<DDLEntryItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ENTRYID,
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

			query.append(_SQL_SELECT_DDLENTRYITEM_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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

				qPos.add(entryId);

				list = (List<DDLEntryItem>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_ENTRYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ENTRYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d d l entry item in the ordered set where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the entry ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem findByEntryId_First(long entryId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryItemException, SystemException {
		List<DDLEntryItem> list = findByEntryId(entryId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d l entry item in the ordered set where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the entry ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem findByEntryId_Last(long entryId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryItemException, SystemException {
		int count = countByEntryId(entryId);

		List<DDLEntryItem> list = findByEntryId(entryId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d l entry items before and after the current d d l entry item in the ordered set where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryItemId the primary key of the current d d l entry item
	 * @param entryId the entry ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l entry item
	 * @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDLEntryItem[] findByEntryId_PrevAndNext(long entryItemId,
		long entryId, OrderByComparator orderByComparator)
		throws NoSuchEntryItemException, SystemException {
		DDLEntryItem ddlEntryItem = findByPrimaryKey(entryItemId);

		Session session = null;

		try {
			session = openSession();

			DDLEntryItem[] array = new DDLEntryItemImpl[3];

			array[0] = getByEntryId_PrevAndNext(session, ddlEntryItem, entryId,
					orderByComparator, true);

			array[1] = ddlEntryItem;

			array[2] = getByEntryId_PrevAndNext(session, ddlEntryItem, entryId,
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

	protected DDLEntryItem getByEntryId_PrevAndNext(Session session,
		DDLEntryItem ddlEntryItem, long entryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLENTRYITEM_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddlEntryItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLEntryItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d d l entry items.
	 *
	 * @return the d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d l entry items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l entry items to return
	 * @param end the upper bound of the range of d d l entry items to return (not inclusive)
	 * @return the range of d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d l entry items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l entry items to return
	 * @param end the upper bound of the range of d d l entry items to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDLEntryItem> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDLEntryItem> list = (List<DDLEntryItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDLENTRYITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDLENTRYITEM;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDLEntryItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDLEntryItem>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the d d l entry items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDLEntryItem ddlEntryItem : findByUuid(uuid)) {
			ddlEntryItemPersistence.remove(ddlEntryItem);
		}
	}

	/**
	 * Removes all the d d l entry items where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByEntryId(long entryId) throws SystemException {
		for (DDLEntryItem ddlEntryItem : findByEntryId(entryId)) {
			ddlEntryItemPersistence.remove(ddlEntryItem);
		}
	}

	/**
	 * Removes all the d d l entry items from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDLEntryItem ddlEntryItem : findAll()) {
			ddlEntryItemPersistence.remove(ddlEntryItem);
		}
	}

	/**
	 * Counts all the d d l entry items where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLENTRYITEM_WHERE);

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
	 * Counts all the d d l entry items where entryId = &#63;.
	 *
	 * @param entryId the entry ID to search with
	 * @return the number of matching d d l entry items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByEntryId(long entryId) throws SystemException {
		Object[] finderArgs = new Object[] { entryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLENTRYITEM_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d d l entry items.
	 *
	 * @return the number of d d l entry items
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

				Query q = session.createQuery(_SQL_COUNT_DDLENTRYITEM);

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
	 * Initializes the d d l entry item persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatalists.model.DDLEntryItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDLEntryItem>> listenersList = new ArrayList<ModelListener<DDLEntryItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDLEntryItem>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDLEntryItemImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DDLEntryPersistence.class)
	protected DDLEntryPersistence ddlEntryPersistence;
	@BeanReference(type = DDLEntryItemPersistence.class)
	protected DDLEntryItemPersistence ddlEntryItemPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	private static final String _SQL_SELECT_DDLENTRYITEM = "SELECT ddlEntryItem FROM DDLEntryItem ddlEntryItem";
	private static final String _SQL_SELECT_DDLENTRYITEM_WHERE = "SELECT ddlEntryItem FROM DDLEntryItem ddlEntryItem WHERE ";
	private static final String _SQL_COUNT_DDLENTRYITEM = "SELECT COUNT(ddlEntryItem) FROM DDLEntryItem ddlEntryItem";
	private static final String _SQL_COUNT_DDLENTRYITEM_WHERE = "SELECT COUNT(ddlEntryItem) FROM DDLEntryItem ddlEntryItem WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddlEntryItem.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddlEntryItem.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddlEntryItem.uuid IS NULL OR ddlEntryItem.uuid = ?)";
	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 = "ddlEntryItem.entryId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlEntryItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDLEntryItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDLEntryItem exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDLEntryItemPersistenceImpl.class);
}