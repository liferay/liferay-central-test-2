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

package com.liferay.portlet.deletion.service.persistence;

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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.deletion.NoSuchEntryException;
import com.liferay.portlet.deletion.model.DeletionEntry;
import com.liferay.portlet.deletion.model.impl.DeletionEntryImpl;
import com.liferay.portlet.deletion.model.impl.DeletionEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the deletion entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DeletionEntryPersistence
 * @see DeletionEntryUtil
 * @generated
 */
public class DeletionEntryPersistenceImpl extends BasePersistenceImpl<DeletionEntry>
	implements DeletionEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DeletionEntryUtil} to access the deletion entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DeletionEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_C = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C_C",
			new String[] {
				Long.class.getName(), Date.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_C",
			new String[] {
				Long.class.getName(), Date.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_P = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_P = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_C_P = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C_C_P",
			new String[] {
				Long.class.getName(), Date.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_P = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_C_P",
			new String[] {
				Long.class.getName(), Date.class.getName(), Long.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the deletion entry in the entity cache if it is enabled.
	 *
	 * @param deletionEntry the deletion entry to cache
	 */
	public void cacheResult(DeletionEntry deletionEntry) {
		EntityCacheUtil.putResult(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryImpl.class, deletionEntry.getPrimaryKey(),
			deletionEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(deletionEntry.getClassNameId()),
				new Long(deletionEntry.getClassPK())
			}, deletionEntry);
	}

	/**
	 * Caches the deletion entries in the entity cache if it is enabled.
	 *
	 * @param deletionEntries the deletion entries to cache
	 */
	public void cacheResult(List<DeletionEntry> deletionEntries) {
		for (DeletionEntry deletionEntry : deletionEntries) {
			if (EntityCacheUtil.getResult(
						DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
						DeletionEntryImpl.class, deletionEntry.getPrimaryKey(),
						this) == null) {
				cacheResult(deletionEntry);
			}
		}
	}

	/**
	 * Clears the cache for all deletion entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(DeletionEntryImpl.class.getName());
		EntityCacheUtil.clearCache(DeletionEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the deletion entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DeletionEntry deletionEntry) {
		EntityCacheUtil.removeResult(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryImpl.class, deletionEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(deletionEntry.getClassNameId()),
				new Long(deletionEntry.getClassPK())
			});
	}

	/**
	 * Creates a new deletion entry with the primary key. Does not add the deletion entry to the database.
	 *
	 * @param entryId the primary key for the new deletion entry
	 * @return the new deletion entry
	 */
	public DeletionEntry create(long entryId) {
		DeletionEntry deletionEntry = new DeletionEntryImpl();

		deletionEntry.setNew(true);
		deletionEntry.setPrimaryKey(entryId);

		return deletionEntry;
	}

	/**
	 * Removes the deletion entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the deletion entry to remove
	 * @return the deletion entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the deletion entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the deletion entry to remove
	 * @return the deletion entry that was removed
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DeletionEntry deletionEntry = (DeletionEntry)session.get(DeletionEntryImpl.class,
					new Long(entryId));

			if (deletionEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					entryId);
			}

			return deletionEntryPersistence.remove(deletionEntry);
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

	/**
	 * Removes the deletion entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param the deletion entry to remove
	 * @return the deletion entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry remove(DeletionEntry deletionEntry)
		throws SystemException {
		return super.remove(deletionEntry);
	}

	protected DeletionEntry removeImpl(DeletionEntry deletionEntry)
		throws SystemException {
		deletionEntry = toUnwrappedModel(deletionEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, deletionEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DeletionEntryModelImpl deletionEntryModelImpl = (DeletionEntryModelImpl)deletionEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(deletionEntryModelImpl.getOriginalClassNameId()),
				new Long(deletionEntryModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryImpl.class, deletionEntry.getPrimaryKey());

		return deletionEntry;
	}

	public DeletionEntry updateImpl(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry,
		boolean merge) throws SystemException {
		deletionEntry = toUnwrappedModel(deletionEntry);

		boolean isNew = deletionEntry.isNew();

		DeletionEntryModelImpl deletionEntryModelImpl = (DeletionEntryModelImpl)deletionEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, deletionEntry, merge);

			deletionEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
			DeletionEntryImpl.class, deletionEntry.getPrimaryKey(),
			deletionEntry);

		if (!isNew &&
				((deletionEntry.getClassNameId() != deletionEntryModelImpl.getOriginalClassNameId()) ||
				(deletionEntry.getClassPK() != deletionEntryModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(deletionEntryModelImpl.getOriginalClassNameId()),
					new Long(deletionEntryModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((deletionEntry.getClassNameId() != deletionEntryModelImpl.getOriginalClassNameId()) ||
				(deletionEntry.getClassPK() != deletionEntryModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(deletionEntry.getClassNameId()),
					new Long(deletionEntry.getClassPK())
				}, deletionEntry);
		}

		return deletionEntry;
	}

	protected DeletionEntry toUnwrappedModel(DeletionEntry deletionEntry) {
		if (deletionEntry instanceof DeletionEntryImpl) {
			return deletionEntry;
		}

		DeletionEntryImpl deletionEntryImpl = new DeletionEntryImpl();

		deletionEntryImpl.setNew(deletionEntry.isNew());
		deletionEntryImpl.setPrimaryKey(deletionEntry.getPrimaryKey());

		deletionEntryImpl.setEntryId(deletionEntry.getEntryId());
		deletionEntryImpl.setGroupId(deletionEntry.getGroupId());
		deletionEntryImpl.setCompanyId(deletionEntry.getCompanyId());
		deletionEntryImpl.setCreateDate(deletionEntry.getCreateDate());
		deletionEntryImpl.setClassNameId(deletionEntry.getClassNameId());
		deletionEntryImpl.setClassPK(deletionEntry.getClassPK());
		deletionEntryImpl.setClassUuid(deletionEntry.getClassUuid());
		deletionEntryImpl.setParentId(deletionEntry.getParentId());

		return deletionEntryImpl;
	}

	/**
	 * Finds the deletion entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the deletion entry to find
	 * @return the deletion entry
	 * @throws com.liferay.portal.NoSuchModelException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the deletion entry with the primary key or throws a {@link com.liferay.portlet.deletion.NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the deletion entry to find
	 * @return the deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = fetchByPrimaryKey(entryId);

		if (deletionEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return deletionEntry;
	}

	/**
	 * Finds the deletion entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the deletion entry to find
	 * @return the deletion entry, or <code>null</code> if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the deletion entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the deletion entry to find
	 * @return the deletion entry, or <code>null</code> if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		DeletionEntry deletionEntry = (DeletionEntry)EntityCacheUtil.getResult(DeletionEntryModelImpl.ENTITY_CACHE_ENABLED,
				DeletionEntryImpl.class, entryId, this);

		if (deletionEntry == null) {
			Session session = null;

			try {
				session = openSession();

				deletionEntry = (DeletionEntry)session.get(DeletionEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (deletionEntry != null) {
					cacheResult(deletionEntry);
				}

				closeSession(session);
			}
		}

		return deletionEntry;
	}

	/**
	 * Finds all the deletion entries where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the deletion entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
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

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

				qPos.add(companyId);

				list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first deletion entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<DeletionEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last deletion entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<DeletionEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the deletion entries before and after the current deletion entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current deletion entry
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			DeletionEntry[] array = new DeletionEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, deletionEntry,
					companyId, orderByComparator, true);

			array[1] = deletionEntry;

			array[2] = getByCompanyId_PrevAndNext(session, deletionEntry,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DeletionEntry getByCompanyId_PrevAndNext(Session session,
		DeletionEntry deletionEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deletionEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeletionEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the deletion entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the deletion entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

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

				list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first deletion entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<DeletionEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last deletion entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<DeletionEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current deletion entry
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry[] findByGroupId_PrevAndNext(long entryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			DeletionEntry[] array = new DeletionEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, deletionEntry,
					groupId, orderByComparator, true);

			array[1] = deletionEntry;

			array[2] = getByGroupId_PrevAndNext(session, deletionEntry,
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

	protected DeletionEntry getByGroupId_PrevAndNext(Session session,
		DeletionEntry deletionEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(deletionEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeletionEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the deletion entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @return the matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C(long groupId, long classNameId)
		throws SystemException {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the deletion entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

				qPos.add(classNameId);

				list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<DeletionEntry> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_C(groupId, classNameId);

		List<DeletionEntry> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current deletion entry
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry[] findByG_C_PrevAndNext(long entryId, long groupId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			DeletionEntry[] array = new DeletionEntryImpl[3];

			array[0] = getByG_C_PrevAndNext(session, deletionEntry, groupId,
					classNameId, orderByComparator, true);

			array[1] = deletionEntry;

			array[2] = getByG_C_PrevAndNext(session, deletionEntry, groupId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DeletionEntry getByG_C_PrevAndNext(Session session,
		DeletionEntry deletionEntry, long groupId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deletionEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeletionEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the deletion entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.deletion.NoSuchEntryException} if it could not be found.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = fetchByC_C(classNameId, classPK);

		if (deletionEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return deletionEntry;
	}

	/**
	 * Finds the deletion entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the matching deletion entry, or <code>null</code> if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Finds the deletion entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the matching deletion entry, or <code>null</code> if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<DeletionEntry> list = q.list();

				result = list;

				DeletionEntry deletionEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					deletionEntry = list.get(0);

					cacheResult(deletionEntry);

					if ((deletionEntry.getClassNameId() != classNameId) ||
							(deletionEntry.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, deletionEntry);
					}
				}

				return deletionEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
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
				return (DeletionEntry)result;
			}
		}
	}

	/**
	 * Finds all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @return the matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_C(long groupId, Date createDate,
		long classNameId) throws SystemException {
		return findByG_C_C(groupId, createDate, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_C(long groupId, Date createDate,
		long classNameId, int start, int end) throws SystemException {
		return findByG_C_C(groupId, createDate, classNameId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_C(long groupId, Date createDate,
		long classNameId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, createDate, classNameId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_C,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			if (createDate == null) {
				query.append(_FINDER_COLUMN_G_C_C_CREATEDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_C_C_CREATEDATE_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

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

				if (createDate != null) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				qPos.add(classNameId);

				list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C_C,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_C,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_C_First(long groupId, Date createDate,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<DeletionEntry> list = findByG_C_C(groupId, createDate,
				classNameId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", createDate=");
			msg.append(createDate);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_C_Last(long groupId, Date createDate,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_C_C(groupId, createDate, classNameId);

		List<DeletionEntry> list = findByG_C_C(groupId, createDate,
				classNameId, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", createDate=");
			msg.append(createDate);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current deletion entry
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry[] findByG_C_C_PrevAndNext(long entryId, long groupId,
		Date createDate, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			DeletionEntry[] array = new DeletionEntryImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, deletionEntry, groupId,
					createDate, classNameId, orderByComparator, true);

			array[1] = deletionEntry;

			array[2] = getByG_C_C_PrevAndNext(session, deletionEntry, groupId,
					createDate, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DeletionEntry getByG_C_C_PrevAndNext(Session session,
		DeletionEntry deletionEntry, long groupId, Date createDate,
		long classNameId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		if (createDate == null) {
			query.append(_FINDER_COLUMN_G_C_C_CREATEDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_C_C_CREATEDATE_2);
		}

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

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

		if (createDate != null) {
			qPos.add(CalendarUtil.getTimestamp(createDate));
		}

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deletionEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeletionEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @return the matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_P(long groupId, long classNameId,
		long parentId) throws SystemException {
		return findByG_C_P(groupId, classNameId, parentId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_P(long groupId, long classNameId,
		long parentId, int start, int end) throws SystemException {
		return findByG_C_P(groupId, classNameId, parentId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_P(long groupId, long classNameId,
		long parentId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, parentId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_P_PARENTID_2);

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

				qPos.add(classNameId);

				qPos.add(parentId);

				list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_P_First(long groupId, long classNameId,
		long parentId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<DeletionEntry> list = findByG_C_P(groupId, classNameId, parentId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", parentId=");
			msg.append(parentId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_P_Last(long groupId, long classNameId,
		long parentId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_C_P(groupId, classNameId, parentId);

		List<DeletionEntry> list = findByG_C_P(groupId, classNameId, parentId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", parentId=");
			msg.append(parentId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current deletion entry
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry[] findByG_C_P_PrevAndNext(long entryId, long groupId,
		long classNameId, long parentId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			DeletionEntry[] array = new DeletionEntryImpl[3];

			array[0] = getByG_C_P_PrevAndNext(session, deletionEntry, groupId,
					classNameId, parentId, orderByComparator, true);

			array[1] = deletionEntry;

			array[2] = getByG_C_P_PrevAndNext(session, deletionEntry, groupId,
					classNameId, parentId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DeletionEntry getByG_C_P_PrevAndNext(Session session,
		DeletionEntry deletionEntry, long groupId, long classNameId,
		long parentId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_P_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_P_PARENTID_2);

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

		qPos.add(classNameId);

		qPos.add(parentId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deletionEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeletionEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @return the matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_C_P(long groupId, Date createDate,
		long classNameId, long parentId) throws SystemException {
		return findByG_C_C_P(groupId, createDate, classNameId, parentId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_C_P(long groupId, Date createDate,
		long classNameId, long parentId, int start, int end)
		throws SystemException {
		return findByG_C_C_P(groupId, createDate, classNameId, parentId, start,
			end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findByG_C_C_P(long groupId, Date createDate,
		long classNameId, long parentId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, createDate, classNameId, parentId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_C_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_P_GROUPID_2);

			if (createDate == null) {
				query.append(_FINDER_COLUMN_G_C_C_P_CREATEDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_C_C_P_CREATEDATE_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_P_PARENTID_2);

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

				if (createDate != null) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				qPos.add(classNameId);

				qPos.add(parentId);

				list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_C_C_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_C_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_C_P_First(long groupId, Date createDate,
		long classNameId, long parentId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<DeletionEntry> list = findByG_C_C_P(groupId, createDate,
				classNameId, parentId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", createDate=");
			msg.append(createDate);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", parentId=");
			msg.append(parentId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry findByG_C_C_P_Last(long groupId, Date createDate,
		long classNameId, long parentId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_C_C_P(groupId, createDate, classNameId, parentId);

		List<DeletionEntry> list = findByG_C_C_P(groupId, createDate,
				classNameId, parentId, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", createDate=");
			msg.append(createDate);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", parentId=");
			msg.append(parentId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current deletion entry
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next deletion entry
	 * @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeletionEntry[] findByG_C_C_P_PrevAndNext(long entryId,
		long groupId, Date createDate, long classNameId, long parentId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			DeletionEntry[] array = new DeletionEntryImpl[3];

			array[0] = getByG_C_C_P_PrevAndNext(session, deletionEntry,
					groupId, createDate, classNameId, parentId,
					orderByComparator, true);

			array[1] = deletionEntry;

			array[2] = getByG_C_C_P_PrevAndNext(session, deletionEntry,
					groupId, createDate, classNameId, parentId,
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

	protected DeletionEntry getByG_C_C_P_PrevAndNext(Session session,
		DeletionEntry deletionEntry, long groupId, Date createDate,
		long classNameId, long parentId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELETIONENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_P_GROUPID_2);

		if (createDate == null) {
			query.append(_FINDER_COLUMN_G_C_C_P_CREATEDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_C_C_P_CREATEDATE_2);
		}

		query.append(_FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_P_PARENTID_2);

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

		if (createDate != null) {
			qPos.add(CalendarUtil.getTimestamp(createDate));
		}

		qPos.add(classNameId);

		qPos.add(parentId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deletionEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeletionEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the deletion entries.
	 *
	 * @return the deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the deletion entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @return the range of deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the deletion entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of deletion entries to return
	 * @param end the upper bound of the range of deletion entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeletionEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeletionEntry> list = (List<DeletionEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DELETIONENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DELETIONENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DeletionEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the deletion entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (DeletionEntry deletionEntry : findByCompanyId(companyId)) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Removes all the deletion entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DeletionEntry deletionEntry : findByGroupId(groupId)) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Removes all the deletion entries where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (DeletionEntry deletionEntry : findByG_C(groupId, classNameId)) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Removes the deletion entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		DeletionEntry deletionEntry = findByC_C(classNameId, classPK);

		deletionEntryPersistence.remove(deletionEntry);
	}

	/**
	 * Removes all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_C(long groupId, Date createDate, long classNameId)
		throws SystemException {
		for (DeletionEntry deletionEntry : findByG_C_C(groupId, createDate,
				classNameId)) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Removes all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_P(long groupId, long classNameId, long parentId)
		throws SystemException {
		for (DeletionEntry deletionEntry : findByG_C_P(groupId, classNameId,
				parentId)) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Removes all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_C_P(long groupId, Date createDate,
		long classNameId, long parentId) throws SystemException {
		for (DeletionEntry deletionEntry : findByG_C_C_P(groupId, createDate,
				classNameId, parentId)) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Removes all the deletion entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DeletionEntry deletionEntry : findAll()) {
			deletionEntryPersistence.remove(deletionEntry);
		}
	}

	/**
	 * Counts all the deletion entries where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the deletion entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

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
	 * Counts all the deletion entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the deletion entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_C(long groupId, Date createDate, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, createDate, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			if (createDate == null) {
				query.append(_FINDER_COLUMN_G_C_C_CREATEDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_C_C_CREATEDATE_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (createDate != null) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_P(long groupId, long classNameId, long parentId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, parentId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_P_PARENTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(parentId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param createDate the create date to search with
	 * @param classNameId the class name ID to search with
	 * @param parentId the parent ID to search with
	 * @return the number of matching deletion entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_C_P(long groupId, Date createDate, long classNameId,
		long parentId) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, createDate, classNameId, parentId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DELETIONENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_P_GROUPID_2);

			if (createDate == null) {
				query.append(_FINDER_COLUMN_G_C_C_P_CREATEDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_C_C_P_CREATEDATE_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_P_PARENTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (createDate != null) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				qPos.add(classNameId);

				qPos.add(parentId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the deletion entries.
	 *
	 * @return the number of deletion entries
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

				Query q = session.createQuery(_SQL_COUNT_DELETIONENTRY);

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
	 * Initializes the deletion entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.deletion.model.DeletionEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DeletionEntry>> listenersList = new ArrayList<ModelListener<DeletionEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DeletionEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DeletionEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DeletionEntryPersistence.class)
	protected DeletionEntryPersistence deletionEntryPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DELETIONENTRY = "SELECT deletionEntry FROM DeletionEntry deletionEntry";
	private static final String _SQL_SELECT_DELETIONENTRY_WHERE = "SELECT deletionEntry FROM DeletionEntry deletionEntry WHERE ";
	private static final String _SQL_COUNT_DELETIONENTRY = "SELECT COUNT(deletionEntry) FROM DeletionEntry deletionEntry";
	private static final String _SQL_COUNT_DELETIONENTRY_WHERE = "SELECT COUNT(deletionEntry) FROM DeletionEntry deletionEntry WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "deletionEntry.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "deletionEntry.groupId = ?";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "deletionEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "deletionEntry.classNameId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "deletionEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "deletionEntry.classPK = ?";
	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "deletionEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CREATEDATE_1 = "deletionEntry.createDate >= NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_CREATEDATE_2 = "deletionEntry.createDate >= ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "deletionEntry.classNameId = ?";
	private static final String _FINDER_COLUMN_G_C_P_GROUPID_2 = "deletionEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_P_CLASSNAMEID_2 = "deletionEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_P_PARENTID_2 = "deletionEntry.parentId = ?";
	private static final String _FINDER_COLUMN_G_C_C_P_GROUPID_2 = "deletionEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_P_CREATEDATE_1 = "deletionEntry.createDate >= NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_P_CREATEDATE_2 = "deletionEntry.createDate >= ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2 = "deletionEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_P_PARENTID_2 = "deletionEntry.parentId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "deletionEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DeletionEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DeletionEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DeletionEntryPersistenceImpl.class);
}