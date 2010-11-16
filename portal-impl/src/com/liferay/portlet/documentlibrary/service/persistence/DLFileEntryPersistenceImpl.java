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
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d l file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPersistence
 * @see DLFileEntryUtil
 * @generated
 */
public class DLFileEntryPersistenceImpl extends BasePersistenceImpl<DLFileEntry>
	implements DLFileEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryUtil} to access the d l file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the d l file entry in the entity cache if it is enabled.
	 *
	 * @param dlFileEntry the d l file entry to cache
	 */
	public void cacheResult(DLFileEntry dlFileEntry) {
		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getName()
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getTitle()
			}, dlFileEntry);
	}

	/**
	 * Caches the d l file entries in the entity cache if it is enabled.
	 *
	 * @param dlFileEntries the d l file entries to cache
	 */
	public void cacheResult(List<DLFileEntry> dlFileEntries) {
		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (EntityCacheUtil.getResult(
						DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), this) == null) {
				cacheResult(dlFileEntry);
			}
		}
	}

	/**
	 * Clears the cache for all d l file entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(DLFileEntryImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d l file entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DLFileEntry dlFileEntry) {
		EntityCacheUtil.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getTitle()
			});
	}

	/**
	 * Creates a new d l file entry with the primary key. Does not add the d l file entry to the database.
	 *
	 * @param fileEntryId the primary key for the new d l file entry
	 * @return the new d l file entry
	 */
	public DLFileEntry create(long fileEntryId) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(fileEntryId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntry.setUuid(uuid);

		return dlFileEntry;
	}

	/**
	 * Removes the d l file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l file entry to remove
	 * @return the d l file entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d l file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryId the primary key of the d l file entry to remove
	 * @return the d l file entry that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry remove(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
					new Long(fileEntryId));

			if (dlFileEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileEntryId);
				}

				throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileEntryId);
			}

			return remove(dlFileEntry);
		}
		catch (NoSuchFileEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry removeImpl(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlFileEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntryModelImpl.getOriginalUuid(),
				new Long(dlFileEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntryModelImpl.getOriginalGroupId()),
				new Long(dlFileEntryModelImpl.getOriginalFolderId()),
				
			dlFileEntryModelImpl.getOriginalName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				new Long(dlFileEntryModelImpl.getOriginalGroupId()),
				new Long(dlFileEntryModelImpl.getOriginalFolderId()),
				
			dlFileEntryModelImpl.getOriginalTitle()
			});

		EntityCacheUtil.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

		return dlFileEntry;
	}

	public DLFileEntry updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean merge) throws SystemException {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		boolean isNew = dlFileEntry.isNew();

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		if (Validator.isNull(dlFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileEntry, merge);

			dlFileEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		if (!isNew &&
				(!Validator.equals(dlFileEntry.getUuid(),
					dlFileEntryModelImpl.getOriginalUuid()) ||
				(dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntryModelImpl.getOriginalUuid(),
					new Long(dlFileEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(dlFileEntry.getUuid(),
					dlFileEntryModelImpl.getOriginalUuid()) ||
				(dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
				}, dlFileEntry);
		}

		if (!isNew &&
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getName(),
					dlFileEntryModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					new Long(dlFileEntryModelImpl.getOriginalGroupId()),
					new Long(dlFileEntryModelImpl.getOriginalFolderId()),
					
				dlFileEntryModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getName(),
					dlFileEntryModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					new Long(dlFileEntry.getGroupId()),
					new Long(dlFileEntry.getFolderId()),
					
				dlFileEntry.getName()
				}, dlFileEntry);
		}

		if (!isNew &&
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getTitle(),
					dlFileEntryModelImpl.getOriginalTitle()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
				new Object[] {
					new Long(dlFileEntryModelImpl.getOriginalGroupId()),
					new Long(dlFileEntryModelImpl.getOriginalFolderId()),
					
				dlFileEntryModelImpl.getOriginalTitle()
				});
		}

		if (isNew ||
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getTitle(),
					dlFileEntryModelImpl.getOriginalTitle()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
				new Object[] {
					new Long(dlFileEntry.getGroupId()),
					new Long(dlFileEntry.getFolderId()),
					
				dlFileEntry.getTitle()
				}, dlFileEntry);
		}

		return dlFileEntry;
	}

	protected DLFileEntry toUnwrappedModel(DLFileEntry dlFileEntry) {
		if (dlFileEntry instanceof DLFileEntryImpl) {
			return dlFileEntry;
		}

		DLFileEntryImpl dlFileEntryImpl = new DLFileEntryImpl();

		dlFileEntryImpl.setNew(dlFileEntry.isNew());
		dlFileEntryImpl.setPrimaryKey(dlFileEntry.getPrimaryKey());

		dlFileEntryImpl.setUuid(dlFileEntry.getUuid());
		dlFileEntryImpl.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileEntryImpl.setGroupId(dlFileEntry.getGroupId());
		dlFileEntryImpl.setCompanyId(dlFileEntry.getCompanyId());
		dlFileEntryImpl.setUserId(dlFileEntry.getUserId());
		dlFileEntryImpl.setUserName(dlFileEntry.getUserName());
		dlFileEntryImpl.setVersionUserId(dlFileEntry.getVersionUserId());
		dlFileEntryImpl.setVersionUserName(dlFileEntry.getVersionUserName());
		dlFileEntryImpl.setCreateDate(dlFileEntry.getCreateDate());
		dlFileEntryImpl.setModifiedDate(dlFileEntry.getModifiedDate());
		dlFileEntryImpl.setFolderId(dlFileEntry.getFolderId());
		dlFileEntryImpl.setName(dlFileEntry.getName());
		dlFileEntryImpl.setExtension(dlFileEntry.getExtension());
		dlFileEntryImpl.setTitle(dlFileEntry.getTitle());
		dlFileEntryImpl.setDescription(dlFileEntry.getDescription());
		dlFileEntryImpl.setExtraSettings(dlFileEntry.getExtraSettings());
		dlFileEntryImpl.setVersion(dlFileEntry.getVersion());
		dlFileEntryImpl.setSize(dlFileEntry.getSize());
		dlFileEntryImpl.setReadCount(dlFileEntry.getReadCount());

		return dlFileEntryImpl;
	}

	/**
	 * Finds the d l file entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l file entry to find
	 * @return the d l file entry
	 * @throws com.liferay.portal.NoSuchModelException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l file entry with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param fileEntryId the primary key of the d l file entry to find
	 * @return the d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByPrimaryKey(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByPrimaryKey(fileEntryId);

		if (dlFileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileEntryId);
			}

			throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileEntryId);
		}

		return dlFileEntry;
	}

	/**
	 * Finds the d l file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l file entry to find
	 * @return the d l file entry, or <code>null</code> if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryId the primary key of the d l file entry to find
	 * @return the d l file entry, or <code>null</code> if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByPrimaryKey(long fileEntryId)
		throws SystemException {
		DLFileEntry dlFileEntry = (DLFileEntry)EntityCacheUtil.getResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, fileEntryId, this);

		if (dlFileEntry == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						new Long(fileEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileEntry != null) {
					cacheResult(dlFileEntry);
				}

				closeSession(session);
			}
		}

		return dlFileEntry;
	}

	/**
	 * Finds all the d l file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first d l file entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		int count = countByUuid(uuid);

		List<DLFileEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file entries before and after the current d l file entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the primary key of the current d l file entry
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByUuid_PrevAndNext(long fileEntryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileEntry, uuid,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByUuid_PrevAndNext(session, dlFileEntry, uuid,
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

	protected DLFileEntry getByUuid_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d l file entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group id to search with
	 * @return the matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUUID_G(uuid, groupId);

		if (dlFileEntry == null) {
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

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Finds the d l file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group id to search with
	 * @return the matching d l file entry, or <code>null</code> if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the d l file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group id to search with
	 * @return the matching d l file entry, or <code>null</code> if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getUuid() == null) ||
							!dlFileEntry.getUuid().equals(uuid) ||
							(dlFileEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
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
				return (DLFileEntry)result;
			}
		}
	}

	/**
	 * Finds all the d l file entries where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first d l file entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<DLFileEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file entries before and after the current d l file entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the primary key of the current d l file entry
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlFileEntry, groupId,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByGroupId_PrevAndNext(session, dlFileEntry, groupId,
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

	protected DLFileEntry getByGroupId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l file entries where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the d l file entries where companyId = &#63;.
	 *
	 * @param companyId the company id to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the d l file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first d l file entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<DLFileEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file entries before and after the current d l file entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the primary key of the current d l file entry
	 * @param companyId the company id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByCompanyId_PrevAndNext(long fileEntryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFileEntry,
					companyId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByCompanyId_PrevAndNext(session, dlFileEntry,
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

	protected DLFileEntry getByCompanyId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<DLFileEntry> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file entries before and after the current d l file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the primary key of the current d l file entry
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_U_PrevAndNext(long fileEntryId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_U_PrevAndNext(session, dlFileEntry, groupId,
					userId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_U_PrevAndNext(session, dlFileEntry, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_U_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @return the matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId,
		int start, int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long folderId)
		throws SystemException {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, folderId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_First(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_Last(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		int count = countByG_F(groupId, folderId);

		List<DLFileEntry> list = findByG_F(groupId, folderId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file entries before and after the current d l file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the primary key of the current d l file entry
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_F_PrevAndNext(long fileEntryId, long groupId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds)
		throws SystemException {
		return findByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end) throws SystemException {
		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Filters by the user's permissions and finds all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @return the matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId)
		throws SystemException {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @return the matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds)
		throws SystemException {
		return filterFindByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId) throws SystemException {
		return findByG_U_F(groupId, userId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end) throws SystemException {
		return findByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, folderId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_F,
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(folderId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_F_First(long groupId, long userId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_U_F(groupId, userId, folderId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_U_F_Last(long groupId, long userId,
		long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		int count = countByG_U_F(groupId, userId, folderId);

		List<DLFileEntry> list = findByG_U_F(groupId, userId, folderId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file entries before and after the current d l file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the primary key of the current d l file entry
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry[] findByG_U_F_PrevAndNext(long fileEntryId,
		long groupId, long userId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_U_F_PrevAndNext(session, dlFileEntry, groupId,
					userId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_U_F_PrevAndNext(session, dlFileEntry, groupId,
					userId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_U_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId, long folderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @return the matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds) throws SystemException {
		return findByG_U_F(groupId, userId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end) throws SystemException {
		return findByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, StringUtil.merge(folderIds),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_F,
				finderArgs, this);

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

			conjunctionable = true;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_USERID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (folderIds != null) {
					qPos.add(folderIds);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Filters by the user's permissions and finds all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @return the matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId, int start, int end) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(groupId, userId, folderId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @return the matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end) throws SystemException {
		return filterFindByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(groupId, userId, folderIds, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

		conjunctionable = true;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_USERID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds the d l file entry where groupId = &#63; and folderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_N(groupId, folderId, name);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Finds the d l file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file entry, or <code>null</code> if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		return fetchByG_F_N(groupId, folderId, name, true);
	}

	/**
	 * Finds the d l file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file entry, or <code>null</code> if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_N,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getName() == null) ||
							!dlFileEntry.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
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
				return (DLFileEntry)result;
			}
		}
	}

	/**
	 * Finds the d l file entry where groupId = &#63; and folderId = &#63; and title = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param title the title to search with
	 * @return the matching d l file entry
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry findByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_T(groupId, folderId, title);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Finds the d l file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param title the title to search with
	 * @return the matching d l file entry, or <code>null</code> if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		return fetchByG_F_T(groupId, folderId, title, true);
	}

	/**
	 * Finds the d l file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param title the title to search with
	 * @return the matching d l file entry, or <code>null</code> if a matching d l file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, title };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_T,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
				}
			}

			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getTitle() == null) ||
							!dlFileEntry.getTitle().equals(title)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
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
				return (DLFileEntry)result;
			}
		}
	}

	/**
	 * Finds all the d l file entries.
	 *
	 * @return the d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @return the range of d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file entries to return
	 * @param end the upper bound of the range of d l file entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRY.concat(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d l file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileEntry dlFileEntry : findByUuid(uuid)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes the d l file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByUUID_G(uuid, groupId);

		remove(dlFileEntry);
	}

	/**
	 * Removes all the d l file entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByGroupId(groupId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the d l file entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByCompanyId(companyId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the d l file entries where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_U(groupId, userId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the d l file entries where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_F(groupId, folderId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_F(long groupId, long userId, long folderId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_U_F(groupId, userId, folderId)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Removes the d l file entry where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_N(groupId, folderId, name);

		remove(dlFileEntry);
	}

	/**
	 * Removes the d l file entry where groupId = &#63; and folderId = &#63; and title = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param title the title to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_T(groupId, folderId, title);

		remove(dlFileEntry);
	}

	/**
	 * Removes all the d l file entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileEntry dlFileEntry : findAll()) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Counts all the d l file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Counts all the d l file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group id to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Counts all the d l file entries where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Filters by the user's permissions and counts all the d l file entries where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
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
	 * Counts all the d l file entries where companyId = &#63;.
	 *
	 * @param companyId the company id to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Counts all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Filters by the user's permissions and counts all the d l file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @return the number of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

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
	 * Counts all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long[] folderIds)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_GROUPID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (folderIds != null) {
					qPos.add(folderIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the d l file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @return the number of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long folderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

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
	 * Filters by the user's permissions and counts all the d l file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderIds the folder ids to search with
	 * @return the number of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long[] folderIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

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
	 * Counts all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_F(long groupId, long userId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_F(long groupId, long userId, long[] folderIds)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, StringUtil.merge(folderIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

			conjunctionable = true;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_U_F_USERID_5);

			conjunctionable = true;

			if ((folderIds == null) || (folderIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < folderIds.length; i++) {
					query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

					if ((i + 1) < folderIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (folderIds != null) {
					qPos.add(folderIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the d l file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @return the number of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_F(long groupId, long userId, long folderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(folderId);

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
	 * Filters by the user's permissions and counts all the d l file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param folderIds the folder ids to search with
	 * @return the number of matching d l file entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_F(long groupId, long userId, long[] folderIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_5);

		conjunctionable = true;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_U_F_USERID_5);

		conjunctionable = true;

		if ((folderIds == null) || (folderIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < folderIds.length; i++) {
				query.append(_FINDER_COLUMN_G_U_F_FOLDERID_5);

				if ((i + 1) < folderIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (folderIds != null) {
				qPos.add(folderIds);
			}

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
	 * Counts all the d l file entries where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Counts all the d l file entries where groupId = &#63; and folderId = &#63; and title = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param folderId the folder id to search with
	 * @param title the title to search with
	 * @return the number of matching d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, title };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
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

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file entries.
	 *
	 * @return the number of d l file entries
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

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRY);

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
	 * Initializes the d l file entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileEntry>> listenersList = new ArrayList<ModelListener<DLFileEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileEntryImpl.class.getName());
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
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = RatingsEntryPersistence.class)
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_DLFILEENTRY = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry";
	private static final String _SQL_SELECT_DLFILEENTRY_WHERE = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRY = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry";
	private static final String _SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileEntry.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFileEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFileEntry.companyId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileEntry.userId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_F_FOLDERID_2) + ")";
	private static final String _FINDER_COLUMN_G_U_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_F_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_U_F_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_U_F_USERID_2 = "dlFileEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_F_USERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_U_F_USERID_2) + ")";
	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_U_F_FOLDERID_2) + ")";
	private static final String _FINDER_COLUMN_G_F_N_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_NAME_1 = "dlFileEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_NAME_2 = "dlFileEntry.name = ?";
	private static final String _FINDER_COLUMN_G_F_N_NAME_3 = "(dlFileEntry.name IS NULL OR dlFileEntry.name = ?)";
	private static final String _FINDER_COLUMN_G_F_T_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_1 = "dlFileEntry.title IS NULL";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_2 = "dlFileEntry.title = ?";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_3 = "(dlFileEntry.title IS NULL OR dlFileEntry.title = ?)";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_WHERE = "SELECT DISTINCT {dlFileEntry.*} FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE =
		"SELECT {dlFileEntry.*} FROM (SELECT DISTINCT fileEntryId FROM DLFileEntry) dlFileEntry2 INNER JOIN DLFileEntry dlFileEntry ON (dlFileEntry2.fileEntryId = dlFileEntry.fileEntryId) WHERE ";
	private static final String _FILTER_SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(DISTINCT dlFileEntry.fileEntryId) AS COUNT_VALUE FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_COLUMN_PK = "dlFileEntry.fileEntryId";
	private static final String _FILTER_COLUMN_USERID = "dlFileEntry.userId";
	private static final String _FILTER_ENTITY_ALIAS = "dlFileEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceImpl.class);
}