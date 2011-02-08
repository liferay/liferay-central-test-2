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

package com.liferay.portlet.forms.service.persistence;

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

import com.liferay.portlet.forms.NoSuchStructureEntryException;
import com.liferay.portlet.forms.model.FormsStructureEntry;
import com.liferay.portlet.forms.model.impl.FormsStructureEntryImpl;
import com.liferay.portlet.forms.model.impl.FormsStructureEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the forms structure entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryPersistence
 * @see FormsStructureEntryUtil
 * @generated
 */
public class FormsStructureEntryPersistenceImpl extends BasePersistenceImpl<FormsStructureEntry>
	implements FormsStructureEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FormsStructureEntryUtil} to access the forms structure entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FormsStructureEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_S = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the forms structure entry in the entity cache if it is enabled.
	 *
	 * @param formsStructureEntry the forms structure entry to cache
	 */
	public void cacheResult(FormsStructureEntry formsStructureEntry) {
		EntityCacheUtil.putResult(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryImpl.class, formsStructureEntry.getPrimaryKey(),
			formsStructureEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				formsStructureEntry.getUuid(),
				new Long(formsStructureEntry.getGroupId())
			}, formsStructureEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				new Long(formsStructureEntry.getGroupId()),
				
			formsStructureEntry.getStructureId()
			}, formsStructureEntry);
	}

	/**
	 * Caches the forms structure entries in the entity cache if it is enabled.
	 *
	 * @param formsStructureEntries the forms structure entries to cache
	 */
	public void cacheResult(List<FormsStructureEntry> formsStructureEntries) {
		for (FormsStructureEntry formsStructureEntry : formsStructureEntries) {
			if (EntityCacheUtil.getResult(
						FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
						FormsStructureEntryImpl.class,
						formsStructureEntry.getPrimaryKey(), this) == null) {
				cacheResult(formsStructureEntry);
			}
		}
	}

	/**
	 * Clears the cache for all forms structure entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(FormsStructureEntryImpl.class.getName());
		EntityCacheUtil.clearCache(FormsStructureEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the forms structure entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(FormsStructureEntry formsStructureEntry) {
		EntityCacheUtil.removeResult(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryImpl.class, formsStructureEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				formsStructureEntry.getUuid(),
				new Long(formsStructureEntry.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				new Long(formsStructureEntry.getGroupId()),
				
			formsStructureEntry.getStructureId()
			});
	}

	/**
	 * Creates a new forms structure entry with the primary key. Does not add the forms structure entry to the database.
	 *
	 * @param structureEntryId the primary key for the new forms structure entry
	 * @return the new forms structure entry
	 */
	public FormsStructureEntry create(long structureEntryId) {
		FormsStructureEntry formsStructureEntry = new FormsStructureEntryImpl();

		formsStructureEntry.setNew(true);
		formsStructureEntry.setPrimaryKey(structureEntryId);

		String uuid = PortalUUIDUtil.generate();

		formsStructureEntry.setUuid(uuid);

		return formsStructureEntry;
	}

	/**
	 * Removes the forms structure entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the forms structure entry to remove
	 * @return the forms structure entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the forms structure entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureEntryId the primary key of the forms structure entry to remove
	 * @return the forms structure entry that was removed
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry remove(long structureEntryId)
		throws NoSuchStructureEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			FormsStructureEntry formsStructureEntry = (FormsStructureEntry)session.get(FormsStructureEntryImpl.class,
					new Long(structureEntryId));

			if (formsStructureEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						structureEntryId);
				}

				throw new NoSuchStructureEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					structureEntryId);
			}

			return formsStructureEntryPersistence.remove(formsStructureEntry);
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
	 * Removes the forms structure entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formsStructureEntry the forms structure entry to remove
	 * @return the forms structure entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry remove(FormsStructureEntry formsStructureEntry)
		throws SystemException {
		return super.remove(formsStructureEntry);
	}

	protected FormsStructureEntry removeImpl(
		FormsStructureEntry formsStructureEntry) throws SystemException {
		formsStructureEntry = toUnwrappedModel(formsStructureEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, formsStructureEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		FormsStructureEntryModelImpl formsStructureEntryModelImpl = (FormsStructureEntryModelImpl)formsStructureEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				formsStructureEntryModelImpl.getOriginalUuid(),
				new Long(formsStructureEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				new Long(formsStructureEntryModelImpl.getOriginalGroupId()),
				
			formsStructureEntryModelImpl.getOriginalStructureId()
			});

		EntityCacheUtil.removeResult(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryImpl.class, formsStructureEntry.getPrimaryKey());

		return formsStructureEntry;
	}

	public FormsStructureEntry updateImpl(
		com.liferay.portlet.forms.model.FormsStructureEntry formsStructureEntry,
		boolean merge) throws SystemException {
		formsStructureEntry = toUnwrappedModel(formsStructureEntry);

		boolean isNew = formsStructureEntry.isNew();

		FormsStructureEntryModelImpl formsStructureEntryModelImpl = (FormsStructureEntryModelImpl)formsStructureEntry;

		if (Validator.isNull(formsStructureEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			formsStructureEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, formsStructureEntry, merge);

			formsStructureEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryImpl.class, formsStructureEntry.getPrimaryKey(),
			formsStructureEntry);

		if (!isNew &&
				(!Validator.equals(formsStructureEntry.getUuid(),
					formsStructureEntryModelImpl.getOriginalUuid()) ||
				(formsStructureEntry.getGroupId() != formsStructureEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					formsStructureEntryModelImpl.getOriginalUuid(),
					new Long(formsStructureEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(formsStructureEntry.getUuid(),
					formsStructureEntryModelImpl.getOriginalUuid()) ||
				(formsStructureEntry.getGroupId() != formsStructureEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					formsStructureEntry.getUuid(),
					new Long(formsStructureEntry.getGroupId())
				}, formsStructureEntry);
		}

		if (!isNew &&
				((formsStructureEntry.getGroupId() != formsStructureEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(formsStructureEntry.getStructureId(),
					formsStructureEntryModelImpl.getOriginalStructureId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					new Long(formsStructureEntryModelImpl.getOriginalGroupId()),
					
				formsStructureEntryModelImpl.getOriginalStructureId()
				});
		}

		if (isNew ||
				((formsStructureEntry.getGroupId() != formsStructureEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(formsStructureEntry.getStructureId(),
					formsStructureEntryModelImpl.getOriginalStructureId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					new Long(formsStructureEntry.getGroupId()),
					
				formsStructureEntry.getStructureId()
				}, formsStructureEntry);
		}

		return formsStructureEntry;
	}

	protected FormsStructureEntry toUnwrappedModel(
		FormsStructureEntry formsStructureEntry) {
		if (formsStructureEntry instanceof FormsStructureEntryImpl) {
			return formsStructureEntry;
		}

		FormsStructureEntryImpl formsStructureEntryImpl = new FormsStructureEntryImpl();

		formsStructureEntryImpl.setNew(formsStructureEntry.isNew());
		formsStructureEntryImpl.setPrimaryKey(formsStructureEntry.getPrimaryKey());

		formsStructureEntryImpl.setUuid(formsStructureEntry.getUuid());
		formsStructureEntryImpl.setStructureEntryId(formsStructureEntry.getStructureEntryId());
		formsStructureEntryImpl.setGroupId(formsStructureEntry.getGroupId());
		formsStructureEntryImpl.setCompanyId(formsStructureEntry.getCompanyId());
		formsStructureEntryImpl.setUserId(formsStructureEntry.getUserId());
		formsStructureEntryImpl.setUserName(formsStructureEntry.getUserName());
		formsStructureEntryImpl.setCreateDate(formsStructureEntry.getCreateDate());
		formsStructureEntryImpl.setModifiedDate(formsStructureEntry.getModifiedDate());
		formsStructureEntryImpl.setStructureId(formsStructureEntry.getStructureId());
		formsStructureEntryImpl.setName(formsStructureEntry.getName());
		formsStructureEntryImpl.setDescription(formsStructureEntry.getDescription());
		formsStructureEntryImpl.setXsd(formsStructureEntry.getXsd());

		return formsStructureEntryImpl;
	}

	/**
	 * Finds the forms structure entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the forms structure entry to find
	 * @return the forms structure entry
	 * @throws com.liferay.portal.NoSuchModelException if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the forms structure entry with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryException} if it could not be found.
	 *
	 * @param structureEntryId the primary key of the forms structure entry to find
	 * @return the forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByPrimaryKey(long structureEntryId)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = fetchByPrimaryKey(structureEntryId);

		if (formsStructureEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + structureEntryId);
			}

			throw new NoSuchStructureEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				structureEntryId);
		}

		return formsStructureEntry;
	}

	/**
	 * Finds the forms structure entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the forms structure entry to find
	 * @return the forms structure entry, or <code>null</code> if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the forms structure entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureEntryId the primary key of the forms structure entry to find
	 * @return the forms structure entry, or <code>null</code> if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry fetchByPrimaryKey(long structureEntryId)
		throws SystemException {
		FormsStructureEntry formsStructureEntry = (FormsStructureEntry)EntityCacheUtil.getResult(FormsStructureEntryModelImpl.ENTITY_CACHE_ENABLED,
				FormsStructureEntryImpl.class, structureEntryId, this);

		if (formsStructureEntry == null) {
			Session session = null;

			try {
				session = openSession();

				formsStructureEntry = (FormsStructureEntry)session.get(FormsStructureEntryImpl.class,
						new Long(structureEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (formsStructureEntry != null) {
					cacheResult(formsStructureEntry);
				}

				closeSession(session);
			}
		}

		return formsStructureEntry;
	}

	/**
	 * Finds all the forms structure entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the forms structure entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of forms structure entries to return
	 * @param end the upper bound of the range of forms structure entries to return (not inclusive)
	 * @return the range of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the forms structure entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of forms structure entries to return
	 * @param end the upper bound of the range of forms structure entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormsStructureEntry> list = (List<FormsStructureEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE);

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

				list = (List<FormsStructureEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Finds the first forms structure entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		List<FormsStructureEntry> list = findByUuid(uuid, 0, 1,
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
	 * Finds the last forms structure entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		int count = countByUuid(uuid);

		List<FormsStructureEntry> list = findByUuid(uuid, count - 1, count,
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
	 * Finds the forms structure entries before and after the current forms structure entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureEntryId the primary key of the current forms structure entry
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry[] findByUuid_PrevAndNext(long structureEntryId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = findByPrimaryKey(structureEntryId);

		Session session = null;

		try {
			session = openSession();

			FormsStructureEntry[] array = new FormsStructureEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, formsStructureEntry,
					uuid, orderByComparator, true);

			array[1] = formsStructureEntry;

			array[2] = getByUuid_PrevAndNext(session, formsStructureEntry,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormsStructureEntry getByUuid_PrevAndNext(Session session,
		FormsStructureEntry formsStructureEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(formsStructureEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormsStructureEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the forms structure entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = fetchByUUID_G(uuid, groupId);

		if (formsStructureEntry == null) {
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

		return formsStructureEntry;
	}

	/**
	 * Finds the forms structure entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching forms structure entry, or <code>null</code> if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the forms structure entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching forms structure entry, or <code>null</code> if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE);

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

				List<FormsStructureEntry> list = q.list();

				result = list;

				FormsStructureEntry formsStructureEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					formsStructureEntry = list.get(0);

					cacheResult(formsStructureEntry);

					if ((formsStructureEntry.getUuid() == null) ||
							!formsStructureEntry.getUuid().equals(uuid) ||
							(formsStructureEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, formsStructureEntry);
					}
				}

				return formsStructureEntry;
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
				return (FormsStructureEntry)result;
			}
		}
	}

	/**
	 * Finds all the forms structure entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the forms structure entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of forms structure entries to return
	 * @param end the upper bound of the range of forms structure entries to return (not inclusive)
	 * @return the range of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the forms structure entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of forms structure entries to return
	 * @param end the upper bound of the range of forms structure entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormsStructureEntry> list = (List<FormsStructureEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE);

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

				list = (List<FormsStructureEntry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Finds the first forms structure entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		List<FormsStructureEntry> list = findByGroupId(groupId, 0, 1,
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
	 * Finds the last forms structure entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<FormsStructureEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

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
	 * Finds the forms structure entries before and after the current forms structure entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureEntryId the primary key of the current forms structure entry
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a forms structure entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry[] findByGroupId_PrevAndNext(
		long structureEntryId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = findByPrimaryKey(structureEntryId);

		Session session = null;

		try {
			session = openSession();

			FormsStructureEntry[] array = new FormsStructureEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, formsStructureEntry,
					groupId, orderByComparator, true);

			array[1] = formsStructureEntry;

			array[2] = getByGroupId_PrevAndNext(session, formsStructureEntry,
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

	protected FormsStructureEntry getByGroupId_PrevAndNext(Session session,
		FormsStructureEntry formsStructureEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(formsStructureEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormsStructureEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the forms structure entry where groupId = &#63; and structureId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryException} if it could not be found.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching forms structure entry
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryException if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry findByG_S(long groupId, String structureId)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = fetchByG_S(groupId,
				structureId);

		if (formsStructureEntry == null) {
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

		return formsStructureEntry;
	}

	/**
	 * Finds the forms structure entry where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching forms structure entry, or <code>null</code> if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry fetchByG_S(long groupId, String structureId)
		throws SystemException {
		return fetchByG_S(groupId, structureId, true);
	}

	/**
	 * Finds the forms structure entry where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching forms structure entry, or <code>null</code> if a matching forms structure entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntry fetchByG_S(long groupId, String structureId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_S,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE);

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

				List<FormsStructureEntry> list = q.list();

				result = list;

				FormsStructureEntry formsStructureEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
						finderArgs, list);
				}
				else {
					formsStructureEntry = list.get(0);

					cacheResult(formsStructureEntry);

					if ((formsStructureEntry.getGroupId() != groupId) ||
							(formsStructureEntry.getStructureId() == null) ||
							!formsStructureEntry.getStructureId()
													.equals(structureId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
							finderArgs, formsStructureEntry);
					}
				}

				return formsStructureEntry;
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
				return (FormsStructureEntry)result;
			}
		}
	}

	/**
	 * Finds all the forms structure entries.
	 *
	 * @return the forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the forms structure entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of forms structure entries to return
	 * @param end the upper bound of the range of forms structure entries to return (not inclusive)
	 * @return the range of forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the forms structure entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of forms structure entries to return
	 * @param end the upper bound of the range of forms structure entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormsStructureEntry> list = (List<FormsStructureEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_FORMSSTRUCTUREENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FORMSSTRUCTUREENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<FormsStructureEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<FormsStructureEntry>)QueryUtil.list(q,
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
	 * Removes all the forms structure entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (FormsStructureEntry formsStructureEntry : findByUuid(uuid)) {
			formsStructureEntryPersistence.remove(formsStructureEntry);
		}
	}

	/**
	 * Removes the forms structure entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = findByUUID_G(uuid, groupId);

		formsStructureEntryPersistence.remove(formsStructureEntry);
	}

	/**
	 * Removes all the forms structure entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (FormsStructureEntry formsStructureEntry : findByGroupId(groupId)) {
			formsStructureEntryPersistence.remove(formsStructureEntry);
		}
	}

	/**
	 * Removes the forms structure entry where groupId = &#63; and structureId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, String structureId)
		throws NoSuchStructureEntryException, SystemException {
		FormsStructureEntry formsStructureEntry = findByG_S(groupId, structureId);

		formsStructureEntryPersistence.remove(formsStructureEntry);
	}

	/**
	 * Removes all the forms structure entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (FormsStructureEntry formsStructureEntry : findAll()) {
			formsStructureEntryPersistence.remove(formsStructureEntry);
		}
	}

	/**
	 * Counts all the forms structure entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRY_WHERE);

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
	 * Counts all the forms structure entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the number of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRY_WHERE);

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
	 * Counts all the forms structure entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRY_WHERE);

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
	 * Counts all the forms structure entries where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the number of matching forms structure entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRY_WHERE);

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
	 * Counts all the forms structure entries.
	 *
	 * @return the number of forms structure entries
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

				Query q = session.createQuery(_SQL_COUNT_FORMSSTRUCTUREENTRY);

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
	 * Initializes the forms structure entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.forms.model.FormsStructureEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<FormsStructureEntry>> listenersList = new ArrayList<ModelListener<FormsStructureEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<FormsStructureEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(FormsStructureEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = FormsStructureEntryPersistence.class)
	protected FormsStructureEntryPersistence formsStructureEntryPersistence;
	@BeanReference(type = FormsStructureEntryLinkPersistence.class)
	protected FormsStructureEntryLinkPersistence formsStructureEntryLinkPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_FORMSSTRUCTUREENTRY = "SELECT formsStructureEntry FROM FormsStructureEntry formsStructureEntry";
	private static final String _SQL_SELECT_FORMSSTRUCTUREENTRY_WHERE = "SELECT formsStructureEntry FROM FormsStructureEntry formsStructureEntry WHERE ";
	private static final String _SQL_COUNT_FORMSSTRUCTUREENTRY = "SELECT COUNT(formsStructureEntry) FROM FormsStructureEntry formsStructureEntry";
	private static final String _SQL_COUNT_FORMSSTRUCTUREENTRY_WHERE = "SELECT COUNT(formsStructureEntry) FROM FormsStructureEntry formsStructureEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "formsStructureEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "formsStructureEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(formsStructureEntry.uuid IS NULL OR formsStructureEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "formsStructureEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "formsStructureEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(formsStructureEntry.uuid IS NULL OR formsStructureEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "formsStructureEntry.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "formsStructureEntry.groupId = ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "formsStructureEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "formsStructureEntry.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "formsStructureEntry.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(formsStructureEntry.structureId IS NULL OR formsStructureEntry.structureId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "formsStructureEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormsStructureEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FormsStructureEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(FormsStructureEntryPersistenceImpl.class);
}