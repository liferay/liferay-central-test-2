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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;
import com.liferay.portlet.documentlibrary.model.impl.DLDocumentMetadataSetImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLDocumentMetadataSetModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLinkPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d l document metadata set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentMetadataSetPersistence
 * @see DLDocumentMetadataSetUtil
 * @generated
 */
public class DLDocumentMetadataSetPersistenceImpl extends BasePersistenceImpl<DLDocumentMetadataSet>
	implements DLDocumentMetadataSetPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLDocumentMetadataSetUtil} to access the d l document metadata set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLDocumentMetadataSetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_DOCUMENTTYPEID = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByDocumentTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_DOCUMENTTYPEID = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByDocumentTypeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_FILEVERSIONID = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFileVersionId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_D_F = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByD_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByD_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the d l document metadata set in the entity cache if it is enabled.
	 *
	 * @param dlDocumentMetadataSet the d l document metadata set
	 */
	public void cacheResult(DLDocumentMetadataSet dlDocumentMetadataSet) {
		EntityCacheUtil.putResult(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetImpl.class,
			dlDocumentMetadataSet.getPrimaryKey(), dlDocumentMetadataSet);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
			new Object[] {
				Long.valueOf(dlDocumentMetadataSet.getDDMStructureId()),
				Long.valueOf(dlDocumentMetadataSet.getFileVersionId())
			}, dlDocumentMetadataSet);

		dlDocumentMetadataSet.resetOriginalValues();
	}

	/**
	 * Caches the d l document metadata sets in the entity cache if it is enabled.
	 *
	 * @param dlDocumentMetadataSets the d l document metadata sets
	 */
	public void cacheResult(List<DLDocumentMetadataSet> dlDocumentMetadataSets) {
		for (DLDocumentMetadataSet dlDocumentMetadataSet : dlDocumentMetadataSets) {
			if (EntityCacheUtil.getResult(
						DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
						DLDocumentMetadataSetImpl.class,
						dlDocumentMetadataSet.getPrimaryKey(), this) == null) {
				cacheResult(dlDocumentMetadataSet);
			}
		}
	}

	/**
	 * Clears the cache for all d l document metadata sets.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLDocumentMetadataSetImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLDocumentMetadataSetImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d l document metadata set.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DLDocumentMetadataSet dlDocumentMetadataSet) {
		EntityCacheUtil.removeResult(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetImpl.class,
			dlDocumentMetadataSet.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F,
			new Object[] {
				Long.valueOf(dlDocumentMetadataSet.getDDMStructureId()),
				Long.valueOf(dlDocumentMetadataSet.getFileVersionId())
			});
	}

	/**
	 * Creates a new d l document metadata set with the primary key. Does not add the d l document metadata set to the database.
	 *
	 * @param documentMetadataSetId the primary key for the new d l document metadata set
	 * @return the new d l document metadata set
	 */
	public DLDocumentMetadataSet create(long documentMetadataSetId) {
		DLDocumentMetadataSet dlDocumentMetadataSet = new DLDocumentMetadataSetImpl();

		dlDocumentMetadataSet.setNew(true);
		dlDocumentMetadataSet.setPrimaryKey(documentMetadataSetId);

		String uuid = PortalUUIDUtil.generate();

		dlDocumentMetadataSet.setUuid(uuid);

		return dlDocumentMetadataSet;
	}

	/**
	 * Removes the d l document metadata set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l document metadata set
	 * @return the d l document metadata set that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d l document metadata set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param documentMetadataSetId the primary key of the d l document metadata set
	 * @return the d l document metadata set that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet remove(long documentMetadataSetId)
		throws NoSuchDocumentMetadataSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLDocumentMetadataSet dlDocumentMetadataSet = (DLDocumentMetadataSet)session.get(DLDocumentMetadataSetImpl.class,
					Long.valueOf(documentMetadataSetId));

			if (dlDocumentMetadataSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						documentMetadataSetId);
				}

				throw new NoSuchDocumentMetadataSetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					documentMetadataSetId);
			}

			return dlDocumentMetadataSetPersistence.remove(dlDocumentMetadataSet);
		}
		catch (NoSuchDocumentMetadataSetException nsee) {
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
	 * Removes the d l document metadata set from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlDocumentMetadataSet the d l document metadata set
	 * @return the d l document metadata set that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet remove(
		DLDocumentMetadataSet dlDocumentMetadataSet) throws SystemException {
		return super.remove(dlDocumentMetadataSet);
	}

	protected DLDocumentMetadataSet removeImpl(
		DLDocumentMetadataSet dlDocumentMetadataSet) throws SystemException {
		dlDocumentMetadataSet = toUnwrappedModel(dlDocumentMetadataSet);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlDocumentMetadataSet);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLDocumentMetadataSetModelImpl dlDocumentMetadataSetModelImpl = (DLDocumentMetadataSetModelImpl)dlDocumentMetadataSet;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F,
			new Object[] {
				Long.valueOf(dlDocumentMetadataSetModelImpl.getDDMStructureId()),
				Long.valueOf(dlDocumentMetadataSetModelImpl.getFileVersionId())
			});

		EntityCacheUtil.removeResult(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetImpl.class,
			dlDocumentMetadataSet.getPrimaryKey());

		return dlDocumentMetadataSet;
	}

	public DLDocumentMetadataSet updateImpl(
		com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet dlDocumentMetadataSet,
		boolean merge) throws SystemException {
		dlDocumentMetadataSet = toUnwrappedModel(dlDocumentMetadataSet);

		boolean isNew = dlDocumentMetadataSet.isNew();

		DLDocumentMetadataSetModelImpl dlDocumentMetadataSetModelImpl = (DLDocumentMetadataSetModelImpl)dlDocumentMetadataSet;

		if (Validator.isNull(dlDocumentMetadataSet.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlDocumentMetadataSet.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlDocumentMetadataSet, merge);

			dlDocumentMetadataSet.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentMetadataSetImpl.class,
			dlDocumentMetadataSet.getPrimaryKey(), dlDocumentMetadataSet);

		if (!isNew &&
				((dlDocumentMetadataSet.getDDMStructureId() != dlDocumentMetadataSetModelImpl.getOriginalDDMStructureId()) ||
				(dlDocumentMetadataSet.getFileVersionId() != dlDocumentMetadataSetModelImpl.getOriginalFileVersionId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F,
				new Object[] {
					Long.valueOf(
						dlDocumentMetadataSetModelImpl.getOriginalDDMStructureId()),
					Long.valueOf(
						dlDocumentMetadataSetModelImpl.getOriginalFileVersionId())
				});
		}

		if (isNew ||
				((dlDocumentMetadataSet.getDDMStructureId() != dlDocumentMetadataSetModelImpl.getOriginalDDMStructureId()) ||
				(dlDocumentMetadataSet.getFileVersionId() != dlDocumentMetadataSetModelImpl.getOriginalFileVersionId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
				new Object[] {
					Long.valueOf(dlDocumentMetadataSet.getDDMStructureId()),
					Long.valueOf(dlDocumentMetadataSet.getFileVersionId())
				}, dlDocumentMetadataSet);
		}

		return dlDocumentMetadataSet;
	}

	protected DLDocumentMetadataSet toUnwrappedModel(
		DLDocumentMetadataSet dlDocumentMetadataSet) {
		if (dlDocumentMetadataSet instanceof DLDocumentMetadataSetImpl) {
			return dlDocumentMetadataSet;
		}

		DLDocumentMetadataSetImpl dlDocumentMetadataSetImpl = new DLDocumentMetadataSetImpl();

		dlDocumentMetadataSetImpl.setNew(dlDocumentMetadataSet.isNew());
		dlDocumentMetadataSetImpl.setPrimaryKey(dlDocumentMetadataSet.getPrimaryKey());

		dlDocumentMetadataSetImpl.setUuid(dlDocumentMetadataSet.getUuid());
		dlDocumentMetadataSetImpl.setDocumentMetadataSetId(dlDocumentMetadataSet.getDocumentMetadataSetId());
		dlDocumentMetadataSetImpl.setClassNameId(dlDocumentMetadataSet.getClassNameId());
		dlDocumentMetadataSetImpl.setClassPK(dlDocumentMetadataSet.getClassPK());
		dlDocumentMetadataSetImpl.setDDMStructureId(dlDocumentMetadataSet.getDDMStructureId());
		dlDocumentMetadataSetImpl.setDocumentTypeId(dlDocumentMetadataSet.getDocumentTypeId());
		dlDocumentMetadataSetImpl.setFileVersionId(dlDocumentMetadataSet.getFileVersionId());

		return dlDocumentMetadataSetImpl;
	}

	/**
	 * Returns the d l document metadata set with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l document metadata set
	 * @return the d l document metadata set
	 * @throws com.liferay.portal.NoSuchModelException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d l document metadata set with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException} if it could not be found.
	 *
	 * @param documentMetadataSetId the primary key of the d l document metadata set
	 * @return the d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByPrimaryKey(long documentMetadataSetId)
		throws NoSuchDocumentMetadataSetException, SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = fetchByPrimaryKey(documentMetadataSetId);

		if (dlDocumentMetadataSet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					documentMetadataSetId);
			}

			throw new NoSuchDocumentMetadataSetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				documentMetadataSetId);
		}

		return dlDocumentMetadataSet;
	}

	/**
	 * Returns the d l document metadata set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l document metadata set
	 * @return the d l document metadata set, or <code>null</code> if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d l document metadata set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param documentMetadataSetId the primary key of the d l document metadata set
	 * @return the d l document metadata set, or <code>null</code> if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet fetchByPrimaryKey(long documentMetadataSetId)
		throws SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = (DLDocumentMetadataSet)EntityCacheUtil.getResult(DLDocumentMetadataSetModelImpl.ENTITY_CACHE_ENABLED,
				DLDocumentMetadataSetImpl.class, documentMetadataSetId, this);

		if (dlDocumentMetadataSet == null) {
			Session session = null;

			try {
				session = openSession();

				dlDocumentMetadataSet = (DLDocumentMetadataSet)session.get(DLDocumentMetadataSetImpl.class,
						Long.valueOf(documentMetadataSetId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlDocumentMetadataSet != null) {
					cacheResult(dlDocumentMetadataSet);
				}

				closeSession(session);
			}
		}

		return dlDocumentMetadataSet;
	}

	/**
	 * Returns all the d l document metadata sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l document metadata sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @return the range of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByUuid(String uuid, int start,
		int end) throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l document metadata sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLDocumentMetadataSet> list = (List<DLDocumentMetadataSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

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

				list = (List<DLDocumentMetadataSet>)QueryUtil.list(q,
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
	 * Returns the first d l document metadata set in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		List<DLDocumentMetadataSet> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d l document metadata set in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		int count = countByUuid(uuid);

		List<DLDocumentMetadataSet> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d l document metadata sets before and after the current d l document metadata set in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentMetadataSetId the primary key of the current d l document metadata set
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet[] findByUuid_PrevAndNext(
		long documentMetadataSetId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = findByPrimaryKey(documentMetadataSetId);

		Session session = null;

		try {
			session = openSession();

			DLDocumentMetadataSet[] array = new DLDocumentMetadataSetImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlDocumentMetadataSet,
					uuid, orderByComparator, true);

			array[1] = dlDocumentMetadataSet;

			array[2] = getByUuid_PrevAndNext(session, dlDocumentMetadataSet,
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

	protected DLDocumentMetadataSet getByUuid_PrevAndNext(Session session,
		DLDocumentMetadataSet dlDocumentMetadataSet, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(dlDocumentMetadataSet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLDocumentMetadataSet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d l document metadata sets where documentTypeId = &#63;.
	 *
	 * @param documentTypeId the document type ID
	 * @return the matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByDocumentTypeId(long documentTypeId)
		throws SystemException {
		return findByDocumentTypeId(documentTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l document metadata sets where documentTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentTypeId the document type ID
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @return the range of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId, int start, int end) throws SystemException {
		return findByDocumentTypeId(documentTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l document metadata sets where documentTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentTypeId the document type ID
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				documentTypeId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLDocumentMetadataSet> list = (List<DLDocumentMetadataSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_DOCUMENTTYPEID,
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

			query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

			query.append(_FINDER_COLUMN_DOCUMENTTYPEID_DOCUMENTTYPEID_2);

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

				qPos.add(documentTypeId);

				list = (List<DLDocumentMetadataSet>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_DOCUMENTTYPEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_DOCUMENTTYPEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d l document metadata set in the ordered set where documentTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentTypeId the document type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByDocumentTypeId_First(
		long documentTypeId, OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		List<DLDocumentMetadataSet> list = findByDocumentTypeId(documentTypeId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("documentTypeId=");
			msg.append(documentTypeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d l document metadata set in the ordered set where documentTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentTypeId the document type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByDocumentTypeId_Last(
		long documentTypeId, OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		int count = countByDocumentTypeId(documentTypeId);

		List<DLDocumentMetadataSet> list = findByDocumentTypeId(documentTypeId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("documentTypeId=");
			msg.append(documentTypeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d l document metadata sets before and after the current d l document metadata set in the ordered set where documentTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentMetadataSetId the primary key of the current d l document metadata set
	 * @param documentTypeId the document type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet[] findByDocumentTypeId_PrevAndNext(
		long documentMetadataSetId, long documentTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = findByPrimaryKey(documentMetadataSetId);

		Session session = null;

		try {
			session = openSession();

			DLDocumentMetadataSet[] array = new DLDocumentMetadataSetImpl[3];

			array[0] = getByDocumentTypeId_PrevAndNext(session,
					dlDocumentMetadataSet, documentTypeId, orderByComparator,
					true);

			array[1] = dlDocumentMetadataSet;

			array[2] = getByDocumentTypeId_PrevAndNext(session,
					dlDocumentMetadataSet, documentTypeId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLDocumentMetadataSet getByDocumentTypeId_PrevAndNext(
		Session session, DLDocumentMetadataSet dlDocumentMetadataSet,
		long documentTypeId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

		query.append(_FINDER_COLUMN_DOCUMENTTYPEID_DOCUMENTTYPEID_2);

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

		qPos.add(documentTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlDocumentMetadataSet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLDocumentMetadataSet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d l document metadata sets where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByFileVersionId(long fileVersionId)
		throws SystemException {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l document metadata sets where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @return the range of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByFileVersionId(long fileVersionId,
		int start, int end) throws SystemException {
		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l document metadata sets where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findByFileVersionId(long fileVersionId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				fileVersionId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLDocumentMetadataSet> list = (List<DLDocumentMetadataSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FILEVERSIONID,
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

			query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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

				qPos.add(fileVersionId);

				list = (List<DLDocumentMetadataSet>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_FILEVERSIONID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FILEVERSIONID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d l document metadata set in the ordered set where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByFileVersionId_First(long fileVersionId,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		List<DLDocumentMetadataSet> list = findByFileVersionId(fileVersionId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d l document metadata set in the ordered set where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByFileVersionId_Last(long fileVersionId,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		int count = countByFileVersionId(fileVersionId);

		List<DLDocumentMetadataSet> list = findByFileVersionId(fileVersionId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d l document metadata sets before and after the current d l document metadata set in the ordered set where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentMetadataSetId the primary key of the current d l document metadata set
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet[] findByFileVersionId_PrevAndNext(
		long documentMetadataSetId, long fileVersionId,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentMetadataSetException, SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = findByPrimaryKey(documentMetadataSetId);

		Session session = null;

		try {
			session = openSession();

			DLDocumentMetadataSet[] array = new DLDocumentMetadataSetImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session,
					dlDocumentMetadataSet, fileVersionId, orderByComparator,
					true);

			array[1] = dlDocumentMetadataSet;

			array[2] = getByFileVersionId_PrevAndNext(session,
					dlDocumentMetadataSet, fileVersionId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLDocumentMetadataSet getByFileVersionId_PrevAndNext(
		Session session, DLDocumentMetadataSet dlDocumentMetadataSet,
		long fileVersionId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlDocumentMetadataSet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLDocumentMetadataSet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException} if it could not be found.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching d l document metadata set
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet findByD_F(long DDMStructureId,
		long fileVersionId)
		throws NoSuchDocumentMetadataSetException, SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = fetchByD_F(DDMStructureId,
				fileVersionId);

		if (dlDocumentMetadataSet == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("DDMStructureId=");
			msg.append(DDMStructureId);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDocumentMetadataSetException(msg.toString());
		}

		return dlDocumentMetadataSet;
	}

	/**
	 * Returns the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching d l document metadata set, or <code>null</code> if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet fetchByD_F(long DDMStructureId,
		long fileVersionId) throws SystemException {
		return fetchByD_F(DDMStructureId, fileVersionId, true);
	}

	/**
	 * Returns the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching d l document metadata set, or <code>null</code> if a matching d l document metadata set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentMetadataSet fetchByD_F(long DDMStructureId,
		long fileVersionId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { DDMStructureId, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_D_F,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLDOCUMENTMETADATASET_WHERE);

			query.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			query.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(DDMStructureId);

				qPos.add(fileVersionId);

				List<DLDocumentMetadataSet> list = q.list();

				result = list;

				DLDocumentMetadataSet dlDocumentMetadataSet = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
						finderArgs, list);
				}
				else {
					dlDocumentMetadataSet = list.get(0);

					cacheResult(dlDocumentMetadataSet);

					if ((dlDocumentMetadataSet.getDDMStructureId() != DDMStructureId) ||
							(dlDocumentMetadataSet.getFileVersionId() != fileVersionId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
							finderArgs, dlDocumentMetadataSet);
					}
				}

				return dlDocumentMetadataSet;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F,
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
				return (DLDocumentMetadataSet)result;
			}
		}
	}

	/**
	 * Returns all the d l document metadata sets.
	 *
	 * @return the d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l document metadata sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @return the range of d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l document metadata sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l document metadata sets
	 * @param end the upper bound of the range of d l document metadata sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentMetadataSet> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLDocumentMetadataSet> list = (List<DLDocumentMetadataSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLDOCUMENTMETADATASET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLDOCUMENTMETADATASET;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLDocumentMetadataSet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLDocumentMetadataSet>)QueryUtil.list(q,
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
	 * Removes all the d l document metadata sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLDocumentMetadataSet dlDocumentMetadataSet : findByUuid(uuid)) {
			dlDocumentMetadataSetPersistence.remove(dlDocumentMetadataSet);
		}
	}

	/**
	 * Removes all the d l document metadata sets where documentTypeId = &#63; from the database.
	 *
	 * @param documentTypeId the document type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByDocumentTypeId(long documentTypeId)
		throws SystemException {
		for (DLDocumentMetadataSet dlDocumentMetadataSet : findByDocumentTypeId(
				documentTypeId)) {
			dlDocumentMetadataSetPersistence.remove(dlDocumentMetadataSet);
		}
	}

	/**
	 * Removes all the d l document metadata sets where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFileVersionId(long fileVersionId)
		throws SystemException {
		for (DLDocumentMetadataSet dlDocumentMetadataSet : findByFileVersionId(
				fileVersionId)) {
			dlDocumentMetadataSetPersistence.remove(dlDocumentMetadataSet);
		}
	}

	/**
	 * Removes the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByD_F(long DDMStructureId, long fileVersionId)
		throws NoSuchDocumentMetadataSetException, SystemException {
		DLDocumentMetadataSet dlDocumentMetadataSet = findByD_F(DDMStructureId,
				fileVersionId);

		dlDocumentMetadataSetPersistence.remove(dlDocumentMetadataSet);
	}

	/**
	 * Removes all the d l document metadata sets from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLDocumentMetadataSet dlDocumentMetadataSet : findAll()) {
			dlDocumentMetadataSetPersistence.remove(dlDocumentMetadataSet);
		}
	}

	/**
	 * Returns the number of d l document metadata sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLDOCUMENTMETADATASET_WHERE);

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
	 * Returns the number of d l document metadata sets where documentTypeId = &#63;.
	 *
	 * @param documentTypeId the document type ID
	 * @return the number of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByDocumentTypeId(long documentTypeId)
		throws SystemException {
		Object[] finderArgs = new Object[] { documentTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DOCUMENTTYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLDOCUMENTMETADATASET_WHERE);

			query.append(_FINDER_COLUMN_DOCUMENTTYPEID_DOCUMENTTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(documentTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DOCUMENTTYPEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d l document metadata sets where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFileVersionId(long fileVersionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLDOCUMENTMETADATASET_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d l document metadata sets where DDMStructureId = &#63; and fileVersionId = &#63;.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the number of matching d l document metadata sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByD_F(long DDMStructureId, long fileVersionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { DDMStructureId, fileVersionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_D_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLDOCUMENTMETADATASET_WHERE);

			query.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			query.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(DDMStructureId);

				qPos.add(fileVersionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_D_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d l document metadata sets.
	 *
	 * @return the number of d l document metadata sets
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

				Query q = session.createQuery(_SQL_COUNT_DLDOCUMENTMETADATASET);

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
	 * Initializes the d l document metadata set persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLDocumentMetadataSet>> listenersList = new ArrayList<ModelListener<DLDocumentMetadataSet>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLDocumentMetadataSet>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLDocumentMetadataSetImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DLContentPersistence.class)
	protected DLContentPersistence dlContentPersistence;
	@BeanReference(type = DLDocumentMetadataSetPersistence.class)
	protected DLDocumentMetadataSetPersistence dlDocumentMetadataSetPersistence;
	@BeanReference(type = DLDocumentTypePersistence.class)
	protected DLDocumentTypePersistence dlDocumentTypePersistence;
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
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	private static final String _SQL_SELECT_DLDOCUMENTMETADATASET = "SELECT dlDocumentMetadataSet FROM DLDocumentMetadataSet dlDocumentMetadataSet";
	private static final String _SQL_SELECT_DLDOCUMENTMETADATASET_WHERE = "SELECT dlDocumentMetadataSet FROM DLDocumentMetadataSet dlDocumentMetadataSet WHERE ";
	private static final String _SQL_COUNT_DLDOCUMENTMETADATASET = "SELECT COUNT(dlDocumentMetadataSet) FROM DLDocumentMetadataSet dlDocumentMetadataSet";
	private static final String _SQL_COUNT_DLDOCUMENTMETADATASET_WHERE = "SELECT COUNT(dlDocumentMetadataSet) FROM DLDocumentMetadataSet dlDocumentMetadataSet WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlDocumentMetadataSet.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlDocumentMetadataSet.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlDocumentMetadataSet.uuid IS NULL OR dlDocumentMetadataSet.uuid = ?)";
	private static final String _FINDER_COLUMN_DOCUMENTTYPEID_DOCUMENTTYPEID_2 = "dlDocumentMetadataSet.documentTypeId = ?";
	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "dlDocumentMetadataSet.fileVersionId = ?";
	private static final String _FINDER_COLUMN_D_F_DDMSTRUCTUREID_2 = "dlDocumentMetadataSet.DDMStructureId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_FILEVERSIONID_2 = "dlDocumentMetadataSet.fileVersionId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlDocumentMetadataSet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLDocumentMetadataSet exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLDocumentMetadataSet exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLDocumentMetadataSetPersistenceImpl.class);
}