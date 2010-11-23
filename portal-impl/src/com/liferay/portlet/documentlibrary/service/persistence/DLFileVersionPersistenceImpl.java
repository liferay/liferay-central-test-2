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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d l file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPersistence
 * @see DLFileVersionUtil
 * @generated
 */
public class DLFileVersionPersistenceImpl extends BasePersistenceImpl<DLFileVersion>
	implements DLFileVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileVersionUtil} to access the d l file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_G_F_N = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_N_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_F_N_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the d l file version in the entity cache if it is enabled.
	 *
	 * @param dlFileVersion the d l file version to cache
	 */
	public void cacheResult(DLFileVersion dlFileVersion) {
		EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N_V,
			new Object[] {
				new Long(dlFileVersion.getGroupId()),
				new Long(dlFileVersion.getFolderId()),
				
			dlFileVersion.getName(),
				
			dlFileVersion.getVersion()
			}, dlFileVersion);
	}

	/**
	 * Caches the d l file versions in the entity cache if it is enabled.
	 *
	 * @param dlFileVersions the d l file versions to cache
	 */
	public void cacheResult(List<DLFileVersion> dlFileVersions) {
		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (EntityCacheUtil.getResult(
						DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
						DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
						this) == null) {
				cacheResult(dlFileVersion);
			}
		}
	}

	/**
	 * Clears the cache for all d l file versions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(DLFileVersionImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileVersionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d l file version.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DLFileVersion dlFileVersion) {
		EntityCacheUtil.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N_V,
			new Object[] {
				new Long(dlFileVersion.getGroupId()),
				new Long(dlFileVersion.getFolderId()),
				
			dlFileVersion.getName(),
				
			dlFileVersion.getVersion()
			});
	}

	/**
	 * Creates a new d l file version with the primary key. Does not add the d l file version to the database.
	 *
	 * @param fileVersionId the primary key for the new d l file version
	 * @return the new d l file version
	 */
	public DLFileVersion create(long fileVersionId) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(fileVersionId);

		return dlFileVersion;
	}

	/**
	 * Removes the d l file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l file version to remove
	 * @return the d l file version that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d l file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileVersionId the primary key of the d l file version to remove
	 * @return the d l file version that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersion dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
					new Long(fileVersionId));

			if (dlFileVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileVersionId);
				}

				throw new NoSuchFileVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileVersionId);
			}

			return remove(dlFileVersion);
		}
		catch (NoSuchFileVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion removeImpl(DLFileVersion dlFileVersion)
		throws SystemException {
		dlFileVersion = toUnwrappedModel(dlFileVersion);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlFileVersion);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileVersionModelImpl dlFileVersionModelImpl = (DLFileVersionModelImpl)dlFileVersion;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N_V,
			new Object[] {
				new Long(dlFileVersionModelImpl.getOriginalGroupId()),
				new Long(dlFileVersionModelImpl.getOriginalFolderId()),
				
			dlFileVersionModelImpl.getOriginalName(),
				
			dlFileVersionModelImpl.getOriginalVersion()
			});

		EntityCacheUtil.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

		return dlFileVersion;
	}

	public DLFileVersion updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge) throws SystemException {
		dlFileVersion = toUnwrappedModel(dlFileVersion);

		boolean isNew = dlFileVersion.isNew();

		DLFileVersionModelImpl dlFileVersionModelImpl = (DLFileVersionModelImpl)dlFileVersion;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileVersion, merge);

			dlFileVersion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		if (!isNew &&
				((dlFileVersion.getGroupId() != dlFileVersionModelImpl.getOriginalGroupId()) ||
				(dlFileVersion.getFolderId() != dlFileVersionModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileVersion.getName(),
					dlFileVersionModelImpl.getOriginalName()) ||
				!Validator.equals(dlFileVersion.getVersion(),
					dlFileVersionModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N_V,
				new Object[] {
					new Long(dlFileVersionModelImpl.getOriginalGroupId()),
					new Long(dlFileVersionModelImpl.getOriginalFolderId()),
					
				dlFileVersionModelImpl.getOriginalName(),
					
				dlFileVersionModelImpl.getOriginalVersion()
				});
		}

		if (isNew ||
				((dlFileVersion.getGroupId() != dlFileVersionModelImpl.getOriginalGroupId()) ||
				(dlFileVersion.getFolderId() != dlFileVersionModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileVersion.getName(),
					dlFileVersionModelImpl.getOriginalName()) ||
				!Validator.equals(dlFileVersion.getVersion(),
					dlFileVersionModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N_V,
				new Object[] {
					new Long(dlFileVersion.getGroupId()),
					new Long(dlFileVersion.getFolderId()),
					
				dlFileVersion.getName(),
					
				dlFileVersion.getVersion()
				}, dlFileVersion);
		}

		return dlFileVersion;
	}

	protected DLFileVersion toUnwrappedModel(DLFileVersion dlFileVersion) {
		if (dlFileVersion instanceof DLFileVersionImpl) {
			return dlFileVersion;
		}

		DLFileVersionImpl dlFileVersionImpl = new DLFileVersionImpl();

		dlFileVersionImpl.setNew(dlFileVersion.isNew());
		dlFileVersionImpl.setPrimaryKey(dlFileVersion.getPrimaryKey());

		dlFileVersionImpl.setFileVersionId(dlFileVersion.getFileVersionId());
		dlFileVersionImpl.setGroupId(dlFileVersion.getGroupId());
		dlFileVersionImpl.setCompanyId(dlFileVersion.getCompanyId());
		dlFileVersionImpl.setUserId(dlFileVersion.getUserId());
		dlFileVersionImpl.setUserName(dlFileVersion.getUserName());
		dlFileVersionImpl.setCreateDate(dlFileVersion.getCreateDate());
		dlFileVersionImpl.setFolderId(dlFileVersion.getFolderId());
		dlFileVersionImpl.setName(dlFileVersion.getName());
		dlFileVersionImpl.setExtension(dlFileVersion.getExtension());
		dlFileVersionImpl.setTitle(dlFileVersion.getTitle());
		dlFileVersionImpl.setDescription(dlFileVersion.getDescription());
		dlFileVersionImpl.setChangeLog(dlFileVersion.getChangeLog());
		dlFileVersionImpl.setExtraSettings(dlFileVersion.getExtraSettings());
		dlFileVersionImpl.setVersion(dlFileVersion.getVersion());
		dlFileVersionImpl.setSize(dlFileVersion.getSize());
		dlFileVersionImpl.setStatus(dlFileVersion.getStatus());
		dlFileVersionImpl.setStatusByUserId(dlFileVersion.getStatusByUserId());
		dlFileVersionImpl.setStatusByUserName(dlFileVersion.getStatusByUserName());
		dlFileVersionImpl.setStatusDate(dlFileVersion.getStatusDate());

		return dlFileVersionImpl;
	}

	/**
	 * Finds the d l file version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l file version to find
	 * @return the d l file version
	 * @throws com.liferay.portal.NoSuchModelException if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l file version with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	 *
	 * @param fileVersionId the primary key of the d l file version to find
	 * @return the d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByPrimaryKey(fileVersionId);

		if (dlFileVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileVersionId);
			}

			throw new NoSuchFileVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileVersionId);
		}

		return dlFileVersion;
	}

	/**
	 * Finds the d l file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l file version to find
	 * @return the d l file version, or <code>null</code> if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the d l file version to find
	 * @return the d l file version, or <code>null</code> if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByPrimaryKey(long fileVersionId)
		throws SystemException {
		DLFileVersion dlFileVersion = (DLFileVersion)EntityCacheUtil.getResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, fileVersionId, this);

		if (dlFileVersion == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
						new Long(fileVersionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileVersion != null) {
					cacheResult(dlFileVersion);
				}

				closeSession(session);
			}
		}

		return dlFileVersion;
	}

	/**
	 * Finds all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_N(long groupId, long folderId,
		String name) throws SystemException {
		return findByG_F_N(groupId, folderId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param start the lower bound of the range of d l file versions to return
	 * @param end the upper bound of the range of d l file versions to return (not inclusive)
	 * @return the range of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_N(long groupId, long folderId,
		String name, int start, int end) throws SystemException {
		return findByG_F_N(groupId, folderId, name, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param start the lower bound of the range of d l file versions to return
	 * @param end the upper bound of the range of d l file versions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_N(long groupId, long folderId,
		String name, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, folderId, name,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F_N,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F_N,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F_N,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file version in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_N_First(long groupId, long folderId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		List<DLFileVersion> list = findByG_F_N(groupId, folderId, name, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file version in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_N_Last(long groupId, long folderId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		int count = countByG_F_N(groupId, folderId, name);

		List<DLFileVersion> list = findByG_F_N(groupId, folderId, name,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file versions before and after the current d l file version in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the primary key of the current d l file version
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByG_F_N_PrevAndNext(long fileVersionId,
		long groupId, long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_N_PrevAndNext(session, dlFileVersion, groupId,
					folderId, name, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_N_PrevAndNext(session, dlFileVersion, groupId,
					folderId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByG_F_N_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long groupId, long folderId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d l file version where groupId = &#63; and folderId = &#63; and name = &#63; and version = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param version the version to search with
	 * @return the matching d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_N_V(long groupId, long folderId,
		String name, String version)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByG_F_N_V(groupId, folderId, name,
				version);

		if (dlFileVersion == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Finds the d l file version where groupId = &#63; and folderId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param version the version to search with
	 * @return the matching d l file version, or <code>null</code> if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByG_F_N_V(long groupId, long folderId,
		String name, String version) throws SystemException {
		return fetchByG_F_N_V(groupId, folderId, name, version, true);
	}

	/**
	 * Finds the d l file version where groupId = &#63; and folderId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param version the version to search with
	 * @return the matching d l file version, or <code>null</code> if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByG_F_N_V(long groupId, long folderId,
		String name, String version, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_N_V,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_V_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_V_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_V_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_V_NAME_2);
				}
			}

			if (version == null) {
				query.append(_FINDER_COLUMN_G_F_N_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_V_VERSION_2);
				}
			}

			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				if (version != null) {
					qPos.add(version);
				}

				List<DLFileVersion> list = q.list();

				result = list;

				DLFileVersion dlFileVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N_V,
						finderArgs, list);
				}
				else {
					dlFileVersion = list.get(0);

					cacheResult(dlFileVersion);

					if ((dlFileVersion.getGroupId() != groupId) ||
							(dlFileVersion.getFolderId() != folderId) ||
							(dlFileVersion.getName() == null) ||
							!dlFileVersion.getName().equals(name) ||
							(dlFileVersion.getVersion() == null) ||
							!dlFileVersion.getVersion().equals(version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N_V,
							finderArgs, dlFileVersion);
					}
				}

				return dlFileVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N_V,
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
				return (DLFileVersion)result;
			}
		}
	}

	/**
	 * Finds all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @return the matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_N_S(long groupId, long folderId,
		String name, int status) throws SystemException {
		return findByG_F_N_S(groupId, folderId, name, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of d l file versions to return
	 * @param end the upper bound of the range of d l file versions to return (not inclusive)
	 * @return the range of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_N_S(long groupId, long folderId,
		String name, int status, int start, int end) throws SystemException {
		return findByG_F_N_S(groupId, folderId, name, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of d l file versions to return
	 * @param end the upper bound of the range of d l file versions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_N_S(long groupId, long folderId,
		String name, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, folderId, name, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F_N_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_S_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_S_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_S_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_S_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_F_N_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(status);

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F_N_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F_N_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file version in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_N_S_First(long groupId, long folderId,
		String name, int status, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		List<DLFileVersion> list = findByG_F_N_S(groupId, folderId, name,
				status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file version in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_N_S_Last(long groupId, long folderId,
		String name, int status, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		int count = countByG_F_N_S(groupId, folderId, name, status);

		List<DLFileVersion> list = findByG_F_N_S(groupId, folderId, name,
				status, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file versions before and after the current d l file version in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the primary key of the current d l file version
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByG_F_N_S_PrevAndNext(long fileVersionId,
		long groupId, long folderId, String name, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_N_S_PrevAndNext(session, dlFileVersion,
					groupId, folderId, name, status, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_N_S_PrevAndNext(session, dlFileVersion,
					groupId, folderId, name, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByG_F_N_S_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long groupId, long folderId, String name,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_N_S_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_F_N_S_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_S_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_N_S_NAME_2);
			}
		}

		query.append(_FINDER_COLUMN_G_F_N_S_STATUS_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (name != null) {
			qPos.add(name);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l file versions.
	 *
	 * @return the d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file versions to return
	 * @param end the upper bound of the range of d l file versions to return (not inclusive)
	 * @return the range of d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file versions to return
	 * @param end the upper bound of the range of d l file versions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEVERSION.concat(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		for (DLFileVersion dlFileVersion : findByG_F_N(groupId, folderId, name)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes the d l file version where groupId = &#63; and folderId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param version the version to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_N_V(long groupId, long folderId, String name,
		String version) throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByG_F_N_V(groupId, folderId, name,
				version);

		remove(dlFileVersion);
	}

	/**
	 * Removes all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_N_S(long groupId, long folderId, String name,
		int status) throws SystemException {
		for (DLFileVersion dlFileVersion : findByG_F_N_S(groupId, folderId,
				name, status)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes all the d l file versions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileVersion dlFileVersion : findAll()) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Counts all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the number of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param version the version to search with
	 * @return the number of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_N_V(long groupId, long folderId, String name,
		String version) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name, version };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_V_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_V_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_V_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_V_NAME_2);
				}
			}

			if (version == null) {
				query.append(_FINDER_COLUMN_G_F_N_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_V_VERSION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				if (version != null) {
					qPos.add(version);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file versions where groupId = &#63; and folderId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param status the status to search with
	 * @return the number of matching d l file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_N_S(long groupId, long folderId, String name,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_S_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_S_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_N_S_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_N_S_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_F_N_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file versions.
	 *
	 * @return the number of d l file versions
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

				Query q = session.createQuery(_SQL_COUNT_DLFILEVERSION);

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
	 * Initializes the d l file version persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileVersion>> listenersList = new ArrayList<ModelListener<DLFileVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileVersion>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileVersionImpl.class.getName());
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
	private static final String _SQL_SELECT_DLFILEVERSION = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion";
	private static final String _SQL_SELECT_DLFILEVERSION_WHERE = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ";
	private static final String _SQL_COUNT_DLFILEVERSION = "SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion";
	private static final String _SQL_COUNT_DLFILEVERSION_WHERE = "SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion WHERE ";
	private static final String _FINDER_COLUMN_G_F_N_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_NAME_1 = "dlFileVersion.name IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_NAME_2 = "dlFileVersion.name = ?";
	private static final String _FINDER_COLUMN_G_F_N_NAME_3 = "(dlFileVersion.name IS NULL OR dlFileVersion.name = ?)";
	private static final String _FINDER_COLUMN_G_F_N_V_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_V_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_V_NAME_1 = "dlFileVersion.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_F_N_V_NAME_2 = "dlFileVersion.name = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_V_NAME_3 = "(dlFileVersion.name IS NULL OR dlFileVersion.name = ?) AND ";
	private static final String _FINDER_COLUMN_G_F_N_V_VERSION_1 = "dlFileVersion.version IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_V_VERSION_2 = "dlFileVersion.version = ?";
	private static final String _FINDER_COLUMN_G_F_N_V_VERSION_3 = "(dlFileVersion.version IS NULL OR dlFileVersion.version = ?)";
	private static final String _FINDER_COLUMN_G_F_N_S_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_S_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_S_NAME_1 = "dlFileVersion.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_F_N_S_NAME_2 = "dlFileVersion.name = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_S_NAME_3 = "(dlFileVersion.name IS NULL OR dlFileVersion.name = ?) AND ";
	private static final String _FINDER_COLUMN_G_F_N_S_STATUS_2 = "dlFileVersion.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileVersion exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFileVersionPersistenceImpl.class);
}