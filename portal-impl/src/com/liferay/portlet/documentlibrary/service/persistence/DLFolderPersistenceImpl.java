/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d l folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderPersistence
 * @see DLFolderUtil
 * @generated
 */
public class DLFolderPersistenceImpl extends BasePersistenceImpl<DLFolder>
	implements DLFolderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFolderUtil} to access the d l folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the d l folder in the entity cache if it is enabled.
	 *
	 * @param dlFolder the d l folder to cache
	 */
	public void cacheResult(DLFolder dlFolder) {
		EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey(), dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFolder.getUuid(), new Long(dlFolder.getGroupId()) },
			dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(dlFolder.getGroupId()),
				new Long(dlFolder.getParentFolderId()),
				
			dlFolder.getName()
			}, dlFolder);
	}

	/**
	 * Caches the d l folders in the entity cache if it is enabled.
	 *
	 * @param dlFolders the d l folders to cache
	 */
	public void cacheResult(List<DLFolder> dlFolders) {
		for (DLFolder dlFolder : dlFolders) {
			if (EntityCacheUtil.getResult(
						DLFolderModelImpl.ENTITY_CACHE_ENABLED,
						DLFolderImpl.class, dlFolder.getPrimaryKey(), this) == null) {
				cacheResult(dlFolder);
			}
		}
	}

	/**
	 * Clears the cache for all d l folders.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(DLFolderImpl.class.getName());
		EntityCacheUtil.clearCache(DLFolderImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d l folder.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DLFolder dlFolder) {
		EntityCacheUtil.removeResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFolder.getUuid(), new Long(dlFolder.getGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(dlFolder.getGroupId()),
				new Long(dlFolder.getParentFolderId()),
				
			dlFolder.getName()
			});
	}

	/**
	 * Creates a new d l folder with the primary key. Does not add the d l folder to the database.
	 *
	 * @param folderId the primary key for the new d l folder
	 * @return the new d l folder
	 */
	public DLFolder create(long folderId) {
		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setNew(true);
		dlFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		dlFolder.setUuid(uuid);

		return dlFolder;
	}

	/**
	 * Removes the d l folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l folder to remove
	 * @return the d l folder that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d l folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the d l folder to remove
	 * @return the d l folder that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFolder dlFolder = (DLFolder)session.get(DLFolderImpl.class,
					new Long(folderId));

			if (dlFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + folderId);
				}

				throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					folderId);
			}

			return remove(dlFolder);
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

	protected DLFolder removeImpl(DLFolder dlFolder) throws SystemException {
		dlFolder = toUnwrappedModel(dlFolder);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlFolder);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFolderModelImpl dlFolderModelImpl = (DLFolderModelImpl)dlFolder;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFolderModelImpl.getOriginalUuid(),
				new Long(dlFolderModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(dlFolderModelImpl.getOriginalGroupId()),
				new Long(dlFolderModelImpl.getOriginalParentFolderId()),
				
			dlFolderModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey());

		return dlFolder;
	}

	public DLFolder updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean merge) throws SystemException {
		dlFolder = toUnwrappedModel(dlFolder);

		boolean isNew = dlFolder.isNew();

		DLFolderModelImpl dlFolderModelImpl = (DLFolderModelImpl)dlFolder;

		if (Validator.isNull(dlFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFolder, merge);

			dlFolder.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey(), dlFolder);

		if (!isNew &&
				(!Validator.equals(dlFolder.getUuid(),
					dlFolderModelImpl.getOriginalUuid()) ||
				(dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFolderModelImpl.getOriginalUuid(),
					new Long(dlFolderModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(dlFolder.getUuid(),
					dlFolderModelImpl.getOriginalUuid()) ||
				(dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] { dlFolder.getUuid(), new Long(
						dlFolder.getGroupId()) }, dlFolder);
		}

		if (!isNew &&
				((dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()) ||
				(dlFolder.getParentFolderId() != dlFolderModelImpl.getOriginalParentFolderId()) ||
				!Validator.equals(dlFolder.getName(),
					dlFolderModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					new Long(dlFolderModelImpl.getOriginalGroupId()),
					new Long(dlFolderModelImpl.getOriginalParentFolderId()),
					
				dlFolderModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()) ||
				(dlFolder.getParentFolderId() != dlFolderModelImpl.getOriginalParentFolderId()) ||
				!Validator.equals(dlFolder.getName(),
					dlFolderModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					new Long(dlFolder.getGroupId()),
					new Long(dlFolder.getParentFolderId()),
					
				dlFolder.getName()
				}, dlFolder);
		}

		return dlFolder;
	}

	protected DLFolder toUnwrappedModel(DLFolder dlFolder) {
		if (dlFolder instanceof DLFolderImpl) {
			return dlFolder;
		}

		DLFolderImpl dlFolderImpl = new DLFolderImpl();

		dlFolderImpl.setNew(dlFolder.isNew());
		dlFolderImpl.setPrimaryKey(dlFolder.getPrimaryKey());

		dlFolderImpl.setUuid(dlFolder.getUuid());
		dlFolderImpl.setFolderId(dlFolder.getFolderId());
		dlFolderImpl.setGroupId(dlFolder.getGroupId());
		dlFolderImpl.setCompanyId(dlFolder.getCompanyId());
		dlFolderImpl.setUserId(dlFolder.getUserId());
		dlFolderImpl.setUserName(dlFolder.getUserName());
		dlFolderImpl.setCreateDate(dlFolder.getCreateDate());
		dlFolderImpl.setModifiedDate(dlFolder.getModifiedDate());
		dlFolderImpl.setParentFolderId(dlFolder.getParentFolderId());
		dlFolderImpl.setName(dlFolder.getName());
		dlFolderImpl.setDescription(dlFolder.getDescription());
		dlFolderImpl.setLastPostDate(dlFolder.getLastPostDate());

		return dlFolderImpl;
	}

	/**
	 * Finds the d l folder with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l folder to find
	 * @return the d l folder
	 * @throws com.liferay.portal.NoSuchModelException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l folder with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param folderId the primary key of the d l folder to find
	 * @return the d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByPrimaryKey(folderId);

		if (dlFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + folderId);
			}

			throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				folderId);
		}

		return dlFolder;
	}

	/**
	 * Finds the d l folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l folder to find
	 * @return the d l folder, or <code>null</code> if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the d l folder to find
	 * @return the d l folder, or <code>null</code> if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByPrimaryKey(long folderId) throws SystemException {
		DLFolder dlFolder = (DLFolder)EntityCacheUtil.getResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
				DLFolderImpl.class, folderId, this);

		if (dlFolder == null) {
			Session session = null;

			try {
				session = openSession();

				dlFolder = (DLFolder)session.get(DLFolderImpl.class,
						new Long(folderId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFolder != null) {
					cacheResult(dlFolder);
				}

				closeSession(session);
			}
		}

		return dlFolder;
	}

	/**
	 * Finds all the d l folders where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Finds the first d l folder in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l folder in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByUuid(uuid);

		List<DLFolder> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l folders before and after the current d l folder in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current d l folder
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFolder, uuid,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByUuid_PrevAndNext(session, dlFolder, uuid,
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

	protected DLFolder getByUuid_PrevAndNext(Session session,
		DLFolder dlFolder, String uuid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

		else {
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d l folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUUID_G(uuid, groupId);

		if (dlFolder == null) {
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

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Finds the d l folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching d l folder, or <code>null</code> if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the d l folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching d l folder, or <code>null</code> if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

			query.append(DLFolderModelImpl.ORDER_BY_JPQL);

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

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getUuid() == null) ||
							!dlFolder.getUuid().equals(uuid) ||
							(dlFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
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
				return (DLFolder)result;
			}
		}
	}

	/**
	 * Finds all the d l folders where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Finds the first d l folder in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l folder in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);

		List<DLFolder> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l folders before and after the current d l folder in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current d l folder
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByGroupId_PrevAndNext(long folderId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlFolder, groupId,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByGroupId_PrevAndNext(session, dlFolder, groupId,
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

	protected DLFolder getByGroupId_PrevAndNext(Session session,
		DLFolder dlFolder, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

		else {
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l folders where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the d l folders where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the d l folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Finds the first d l folder in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l folder in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByCompanyId(companyId);

		List<DLFolder> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l folders before and after the current d l folder in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current d l folder
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFolder, companyId,
					orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByCompanyId_PrevAndNext(session, dlFolder, companyId,
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

	protected DLFolder getByCompanyId_PrevAndNext(Session session,
		DLFolder dlFolder, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

		else {
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @return the matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return findByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, parentFolderId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByG_P(groupId, parentFolderId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<DLFolder> list = findByG_P(groupId, parentFolderId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l folders before and after the current d l folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current d l folder
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_P_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_P_PrevAndNext(session, dlFolder, groupId,
					parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_P_PrevAndNext(Session session, DLFolder dlFolder,
		long groupId, long parentFolderId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

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

		else {
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @return the matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentFolderId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the d l folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @return the matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByP_N(long parentFolderId, String name)
		throws SystemException {
		return findByP_N(parentFolderId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByP_N(long parentFolderId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentFolderId, name, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findByP_N(long parentFolderId, String name,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				parentFolderId, name,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_N,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_P_N,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_N,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByP_N_First(long parentFolderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByP_N(parentFolderId, name, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentFolderId=");
			msg.append(parentFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByP_N_Last(long parentFolderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByP_N(parentFolderId, name);

		List<DLFolder> list = findByP_N(parentFolderId, name, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentFolderId=");
			msg.append(parentFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l folders before and after the current d l folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current d l folder
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a d l folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder[] findByP_N_PrevAndNext(long folderId, long parentFolderId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByP_N_PrevAndNext(session, dlFolder, parentFolderId,
					name, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByP_N_PrevAndNext(session, dlFolder, parentFolderId,
					name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByP_N_PrevAndNext(Session session, DLFolder dlFolder,
		long parentFolderId, String name, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_P_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_P_N_NAME_2);
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

		else {
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentFolderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d l folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @return the matching d l folder
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder findByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_N(groupId, parentFolderId, name);

		if (dlFolder == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Finds the d l folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @return the matching d l folder, or <code>null</code> if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	/**
	 * Finds the d l folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @return the matching d l folder, or <code>null</code> if a matching d l folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFolder fetchByG_P_N(long groupId, long parentFolderId,
		String name, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_N,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_N_NAME_2);
				}
			}

			query.append(DLFolderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getGroupId() != groupId) ||
							(dlFolder.getParentFolderId() != parentFolderId) ||
							(dlFolder.getName() == null) ||
							!dlFolder.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
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
				return (DLFolder)result;
			}
		}
	}

	/**
	 * Finds all the d l folders.
	 *
	 * @return the d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @return the range of d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l folders to return
	 * @param end the upper bound of the range of d l folders to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFolder> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFOLDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFOLDER.concat(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFolder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFolder>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d l folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFolder dlFolder : findByUuid(uuid)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes the d l folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByUUID_G(uuid, groupId);

		remove(dlFolder);
	}

	/**
	 * Removes all the d l folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFolder dlFolder : findByGroupId(groupId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the d l folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFolder dlFolder : findByCompanyId(companyId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the d l folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (DLFolder dlFolder : findByG_P(groupId, parentFolderId)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes all the d l folders where parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_N(long parentFolderId, String name)
		throws SystemException {
		for (DLFolder dlFolder : findByP_N(parentFolderId, name)) {
			remove(dlFolder);
		}
	}

	/**
	 * Removes the d l folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByG_P_N(groupId, parentFolderId, name);

		remove(dlFolder);
	}

	/**
	 * Removes all the d l folders from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFolder dlFolder : findAll()) {
			remove(dlFolder);
		}
	}

	/**
	 * Counts all the d l folders where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Counts all the d l folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Counts all the d l folders where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Filters by the user's permissions and counts all the d l folders where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Counts all the d l folders where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
	 * Counts all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Filters by the user's permissions and counts all the d l folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @return the number of matching d l folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, long parentFolderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentFolderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFolder.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Counts all the d l folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_N(long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { parentFolderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param parentFolderId the parent folder ID to search with
	 * @param name the name to search with
	 * @return the number of matching d l folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l folders.
	 *
	 * @return the number of d l folders
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

				Query q = session.createQuery(_SQL_COUNT_DLFOLDER);

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
	 * Initializes the d l folder persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFolder>> listenersList = new ArrayList<ModelListener<DLFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFolder>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFolderImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DLFOLDER = "SELECT dlFolder FROM DLFolder dlFolder";
	private static final String _SQL_SELECT_DLFOLDER_WHERE = "SELECT dlFolder FROM DLFolder dlFolder WHERE ";
	private static final String _SQL_COUNT_DLFOLDER = "SELECT COUNT(dlFolder) FROM DLFolder dlFolder";
	private static final String _SQL_COUNT_DLFOLDER_WHERE = "SELECT COUNT(dlFolder) FROM DLFolder dlFolder WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFolder.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFolder.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFolder.uuid IS NULL OR dlFolder.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFolder.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFolder.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFolder.uuid IS NULL OR dlFolder.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFolder.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFolder.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFolder.companyId = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ?";
	private static final String _FINDER_COLUMN_P_N_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_P_N_NAME_1 = "dlFolder.name IS NULL";
	private static final String _FINDER_COLUMN_P_N_NAME_2 = "dlFolder.name = ?";
	private static final String _FINDER_COLUMN_P_N_NAME_3 = "(dlFolder.name IS NULL OR dlFolder.name = ?)";
	private static final String _FINDER_COLUMN_G_P_N_GROUPID_2 = "dlFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_PARENTFOLDERID_2 = "dlFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_NAME_1 = "dlFolder.name IS NULL";
	private static final String _FINDER_COLUMN_G_P_N_NAME_2 = "dlFolder.name = ?";
	private static final String _FINDER_COLUMN_G_P_N_NAME_3 = "(dlFolder.name IS NULL OR dlFolder.name = ?)";
	private static final String _FILTER_SQL_SELECT_DLFOLDER_WHERE = "SELECT DISTINCT {dlFolder.*} FROM DLFolder dlFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFolder.*} FROM (SELECT DISTINCT dlFolder.folderId FROM DLFolder dlFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFolder ON TEMP_TABLE.folderId = DLFolder.folderId";
	private static final String _FILTER_SQL_COUNT_DLFOLDER_WHERE = "SELECT COUNT(DISTINCT dlFolder.folderId) AS COUNT_VALUE FROM DLFolder dlFolder WHERE ";
	private static final String _FILTER_COLUMN_PK = "dlFolder.folderId";
	private static final String _FILTER_COLUMN_USERID = "dlFolder.userId";
	private static final String _FILTER_ENTITY_ALIAS = "dlFolder";
	private static final String _FILTER_ENTITY_TABLE = "DLFolder";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFolder.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFolder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFolder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFolder exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFolderPersistenceImpl.class);
}