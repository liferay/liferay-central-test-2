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
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.model.impl.DLDocumentTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLDocumentTypeModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the d l document type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentTypePersistence
 * @see DLDocumentTypeUtil
 * @generated
 */
public class DLDocumentTypePersistenceImpl extends BasePersistenceImpl<DLDocumentType>
	implements DLDocumentTypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLDocumentTypeUtil} to access the d l document type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLDocumentTypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the d l document type in the entity cache if it is enabled.
	 *
	 * @param dlDocumentType the d l document type to cache
	 */
	public void cacheResult(DLDocumentType dlDocumentType) {
		EntityCacheUtil.putResult(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeImpl.class, dlDocumentType.getPrimaryKey(),
			dlDocumentType);

		dlDocumentType.resetOriginalValues();
	}

	/**
	 * Caches the d l document types in the entity cache if it is enabled.
	 *
	 * @param dlDocumentTypes the d l document types to cache
	 */
	public void cacheResult(List<DLDocumentType> dlDocumentTypes) {
		for (DLDocumentType dlDocumentType : dlDocumentTypes) {
			if (EntityCacheUtil.getResult(
						DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
						DLDocumentTypeImpl.class,
						dlDocumentType.getPrimaryKey(), this) == null) {
				cacheResult(dlDocumentType);
			}
		}
	}

	/**
	 * Clears the cache for all d l document types.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLDocumentTypeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLDocumentTypeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d l document type.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DLDocumentType dlDocumentType) {
		EntityCacheUtil.removeResult(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeImpl.class, dlDocumentType.getPrimaryKey());
	}

	/**
	 * Creates a new d l document type with the primary key. Does not add the d l document type to the database.
	 *
	 * @param documentTypeId the primary key for the new d l document type
	 * @return the new d l document type
	 */
	public DLDocumentType create(long documentTypeId) {
		DLDocumentType dlDocumentType = new DLDocumentTypeImpl();

		dlDocumentType.setNew(true);
		dlDocumentType.setPrimaryKey(documentTypeId);

		return dlDocumentType;
	}

	/**
	 * Removes the d l document type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l document type to remove
	 * @return the d l document type that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d l document type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param documentTypeId the primary key of the d l document type to remove
	 * @return the d l document type that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType remove(long documentTypeId)
		throws NoSuchDocumentTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLDocumentType dlDocumentType = (DLDocumentType)session.get(DLDocumentTypeImpl.class,
					Long.valueOf(documentTypeId));

			if (dlDocumentType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						documentTypeId);
				}

				throw new NoSuchDocumentTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					documentTypeId);
			}

			return dlDocumentTypePersistence.remove(dlDocumentType);
		}
		catch (NoSuchDocumentTypeException nsee) {
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
	 * Removes the d l document type from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlDocumentType the d l document type to remove
	 * @return the d l document type that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType remove(DLDocumentType dlDocumentType)
		throws SystemException {
		return super.remove(dlDocumentType);
	}

	protected DLDocumentType removeImpl(DLDocumentType dlDocumentType)
		throws SystemException {
		dlDocumentType = toUnwrappedModel(dlDocumentType);

		try {
			clearDDMStructures.clear(dlDocumentType.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlDocumentType);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeImpl.class, dlDocumentType.getPrimaryKey());

		return dlDocumentType;
	}

	public DLDocumentType updateImpl(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType,
		boolean merge) throws SystemException {
		dlDocumentType = toUnwrappedModel(dlDocumentType);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlDocumentType, merge);

			dlDocumentType.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeImpl.class, dlDocumentType.getPrimaryKey(),
			dlDocumentType);

		return dlDocumentType;
	}

	protected DLDocumentType toUnwrappedModel(DLDocumentType dlDocumentType) {
		if (dlDocumentType instanceof DLDocumentTypeImpl) {
			return dlDocumentType;
		}

		DLDocumentTypeImpl dlDocumentTypeImpl = new DLDocumentTypeImpl();

		dlDocumentTypeImpl.setNew(dlDocumentType.isNew());
		dlDocumentTypeImpl.setPrimaryKey(dlDocumentType.getPrimaryKey());

		dlDocumentTypeImpl.setDocumentTypeId(dlDocumentType.getDocumentTypeId());
		dlDocumentTypeImpl.setGroupId(dlDocumentType.getGroupId());
		dlDocumentTypeImpl.setCompanyId(dlDocumentType.getCompanyId());
		dlDocumentTypeImpl.setUserId(dlDocumentType.getUserId());
		dlDocumentTypeImpl.setUserName(dlDocumentType.getUserName());
		dlDocumentTypeImpl.setCreateDate(dlDocumentType.getCreateDate());
		dlDocumentTypeImpl.setModifiedDate(dlDocumentType.getModifiedDate());
		dlDocumentTypeImpl.setName(dlDocumentType.getName());
		dlDocumentTypeImpl.setDescription(dlDocumentType.getDescription());

		return dlDocumentTypeImpl;
	}

	/**
	 * Finds the d l document type with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l document type to find
	 * @return the d l document type
	 * @throws com.liferay.portal.NoSuchModelException if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l document type with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException} if it could not be found.
	 *
	 * @param documentTypeId the primary key of the d l document type to find
	 * @return the d l document type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType findByPrimaryKey(long documentTypeId)
		throws NoSuchDocumentTypeException, SystemException {
		DLDocumentType dlDocumentType = fetchByPrimaryKey(documentTypeId);

		if (dlDocumentType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + documentTypeId);
			}

			throw new NoSuchDocumentTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				documentTypeId);
		}

		return dlDocumentType;
	}

	/**
	 * Finds the d l document type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l document type to find
	 * @return the d l document type, or <code>null</code> if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l document type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param documentTypeId the primary key of the d l document type to find
	 * @return the d l document type, or <code>null</code> if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType fetchByPrimaryKey(long documentTypeId)
		throws SystemException {
		DLDocumentType dlDocumentType = (DLDocumentType)EntityCacheUtil.getResult(DLDocumentTypeModelImpl.ENTITY_CACHE_ENABLED,
				DLDocumentTypeImpl.class, documentTypeId, this);

		if (dlDocumentType == null) {
			Session session = null;

			try {
				session = openSession();

				dlDocumentType = (DLDocumentType)session.get(DLDocumentTypeImpl.class,
						Long.valueOf(documentTypeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlDocumentType != null) {
					cacheResult(dlDocumentType);
				}

				closeSession(session);
			}
		}

		return dlDocumentType;
	}

	/**
	 * Finds all the d l document types where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l document types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @return the range of matching d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l document types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLDocumentType> list = (List<DLDocumentType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_DLDOCUMENTTYPE_WHERE);

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

				list = (List<DLDocumentType>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first d l document type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l document type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a matching d l document type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentTypeException, SystemException {
		List<DLDocumentType> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l document type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l document type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a matching d l document type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchDocumentTypeException, SystemException {
		int count = countByGroupId(groupId);

		List<DLDocumentType> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDocumentTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l document types before and after the current d l document type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentTypeId the primary key of the current d l document type
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l document type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType[] findByGroupId_PrevAndNext(long documentTypeId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchDocumentTypeException, SystemException {
		DLDocumentType dlDocumentType = findByPrimaryKey(documentTypeId);

		Session session = null;

		try {
			session = openSession();

			DLDocumentType[] array = new DLDocumentTypeImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlDocumentType,
					groupId, orderByComparator, true);

			array[1] = dlDocumentType;

			array[2] = getByGroupId_PrevAndNext(session, dlDocumentType,
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

	protected DLDocumentType getByGroupId_PrevAndNext(Session session,
		DLDocumentType dlDocumentType, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLDOCUMENTTYPE_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(dlDocumentType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLDocumentType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the d l document types where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching d l document types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the d l document types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @return the range of matching d l document types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the d l document types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l document types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLDOCUMENTTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLDOCUMENTTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLDOCUMENTTYPE_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLDocumentType.class.getName(), _FILTER_COLUMN_PK, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLDocumentTypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLDocumentTypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLDocumentType>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Filters the d l document types before and after the current d l document type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param documentTypeId the primary key of the current d l document type
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l document type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLDocumentType[] filterFindByGroupId_PrevAndNext(
		long documentTypeId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchDocumentTypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(documentTypeId, groupId,
				orderByComparator);
		}

		DLDocumentType dlDocumentType = findByPrimaryKey(documentTypeId);

		Session session = null;

		try {
			session = openSession();

			DLDocumentType[] array = new DLDocumentTypeImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, dlDocumentType,
					groupId, orderByComparator, true);

			array[1] = dlDocumentType;

			array[2] = filterGetByGroupId_PrevAndNext(session, dlDocumentType,
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

	protected DLDocumentType filterGetByGroupId_PrevAndNext(Session session,
		DLDocumentType dlDocumentType, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLDOCUMENTTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLDOCUMENTTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLDOCUMENTTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLDocumentType.class.getName(), _FILTER_COLUMN_PK, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLDocumentTypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLDocumentTypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlDocumentType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLDocumentType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l document types.
	 *
	 * @return the d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l document types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @return the range of d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l document types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLDocumentType> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLDocumentType> list = (List<DLDocumentType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLDOCUMENTTYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLDOCUMENTTYPE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLDocumentType>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLDocumentType>)QueryUtil.list(q,
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
	 * Removes all the d l document types where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DLDocumentType dlDocumentType : findByGroupId(groupId)) {
			dlDocumentTypePersistence.remove(dlDocumentType);
		}
	}

	/**
	 * Removes all the d l document types from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLDocumentType dlDocumentType : findAll()) {
			dlDocumentTypePersistence.remove(dlDocumentType);
		}
	}

	/**
	 * Counts all the d l document types where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching d l document types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLDOCUMENTTYPE_WHERE);

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
	 * Filters by the user's permissions and counts all the d l document types where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching d l document types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLDOCUMENTTYPE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLDocumentType.class.getName(), _FILTER_COLUMN_PK, groupId);

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
	 * Counts all the d l document types.
	 *
	 * @return the number of d l document types
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

				Query q = session.createQuery(_SQL_COUNT_DLDOCUMENTTYPE);

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
	 * Gets all the d d m structures associated with the d l document type.
	 *
	 * @param pk the primary key of the d l document type to get the associated d d m structures for
	 * @return the d d m structures associated with the d l document type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk) throws SystemException {
		return getDDMStructures(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Gets a range of all the d d m structures associated with the d l document type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the d l document type to get the associated d d m structures for
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @return the range of d d m structures associated with the d l document type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end) throws SystemException {
		return getDDMStructures(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_DDMSTRUCTURES = new FinderPath(com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED_DLDOCUMENTTYPE_DDMSTRUCTURE,
			DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME,
			"getDDMStructures",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Gets an ordered range of all the d d m structures associated with the d l document type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the d l document type to get the associated d d m structures for
	 * @param start the lower bound of the range of d l document types to return
	 * @param end the upper bound of the range of d l document types to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structures associated with the d l document type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				pk, String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> list = (List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)FinderCacheUtil.getResult(FINDER_PATH_GET_DDMSTRUCTURES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETDDMSTRUCTURES.concat(ORDER_BY_CLAUSE)
											   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETDDMSTRUCTURES;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("DDMStructure",
					com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_DDMSTRUCTURES,
						finderArgs);
				}
				else {
					ddmStructurePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_DDMSTRUCTURES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_DDMSTRUCTURES_SIZE = new FinderPath(com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED_DLDOCUMENTTYPE_DDMSTRUCTURE,
			DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME,
			"getDDMStructuresSize", new String[] { Long.class.getName() });

	/**
	 * Gets the number of d d m structures associated with the d l document type.
	 *
	 * @param pk the primary key of the d l document type to get the number of associated d d m structures for
	 * @return the number of d d m structures associated with the d l document type
	 * @throws SystemException if a system exception occurred
	 */
	public int getDDMStructuresSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_DDMSTRUCTURES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETDDMSTRUCTURESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_DDMSTRUCTURES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_DDMSTRUCTURE = new FinderPath(com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DLDocumentTypeModelImpl.FINDER_CACHE_ENABLED_DLDOCUMENTTYPE_DDMSTRUCTURE,
			DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME,
			"containsDDMStructure",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Determines if the d d m structure is associated with the d l document type.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructurePK the primary key of the d d m structure
	 * @return <code>true</code> if the d d m structure is associated with the d l document type; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDDMStructure(long pk, long ddmStructurePK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, ddmStructurePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_DDMSTRUCTURE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsDDMStructure.contains(pk,
							ddmStructurePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_DDMSTRUCTURE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Determines if the d l document type has any d d m structures associated with it.
	 *
	 * @param pk the primary key of the d l document type to check for associations with d d m structures
	 * @return <code>true</code> if the d l document type has any d d m structures associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDDMStructures(long pk) throws SystemException {
		if (getDDMStructuresSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructurePK the primary key of the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructure(long pk, long ddmStructurePK)
		throws SystemException {
		try {
			addDDMStructure.add(pk, ddmStructurePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Adds an association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructure the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws SystemException {
		try {
			addDDMStructure.add(pk, ddmStructure.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Adds an association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructurePKs the primary keys of the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructures(long pk, long[] ddmStructurePKs)
		throws SystemException {
		try {
			for (long ddmStructurePK : ddmStructurePKs) {
				addDDMStructure.add(pk, ddmStructurePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Adds an association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructures the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructures(long pk,
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws SystemException {
		try {
			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
				addDDMStructure.add(pk, ddmStructure.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Clears all associations between the d l document type and its d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type to clear the associated d d m structures from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearDDMStructures(long pk) throws SystemException {
		try {
			clearDDMStructures.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Removes the association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructurePK the primary key of the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructure(long pk, long ddmStructurePK)
		throws SystemException {
		try {
			removeDDMStructure.remove(pk, ddmStructurePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Removes the association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructure the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws SystemException {
		try {
			removeDDMStructure.remove(pk, ddmStructure.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Removes the association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructurePKs the primary keys of the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructures(long pk, long[] ddmStructurePKs)
		throws SystemException {
		try {
			for (long ddmStructurePK : ddmStructurePKs) {
				removeDDMStructure.remove(pk, ddmStructurePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Removes the association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type
	 * @param ddmStructures the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructures(long pk,
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws SystemException {
		try {
			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
				removeDDMStructure.remove(pk, ddmStructure.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Sets the d d m structures associated with the d l document type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type to set the associations for
	 * @param ddmStructurePKs the primary keys of the d d m structures to be associated with the d l document type
	 * @throws SystemException if a system exception occurred
	 */
	public void setDDMStructures(long pk, long[] ddmStructurePKs)
		throws SystemException {
		try {
			Set<Long> ddmStructurePKSet = SetUtil.fromArray(ddmStructurePKs);

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures =
				getDDMStructures(pk);

			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
				if (!ddmStructurePKSet.remove(ddmStructure.getPrimaryKey())) {
					removeDDMStructure.remove(pk, ddmStructure.getPrimaryKey());
				}
			}

			for (Long ddmStructurePK : ddmStructurePKSet) {
				addDDMStructure.add(pk, ddmStructurePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Sets the d d m structures associated with the d l document type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d l document type to set the associations for
	 * @param ddmStructures the d d m structures to be associated with the d l document type
	 * @throws SystemException if a system exception occurred
	 */
	public void setDDMStructures(long pk,
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws SystemException {
		try {
			long[] ddmStructurePKs = new long[ddmStructures.size()];

			for (int i = 0; i < ddmStructures.size(); i++) {
				com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
					ddmStructures.get(i);

				ddmStructurePKs[i] = ddmStructure.getPrimaryKey();
			}

			setDDMStructures(pk, ddmStructurePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLDocumentTypeModelImpl.MAPPING_TABLE_DLDOCUMENTTYPE_DDMSTRUCTURE_NAME);
		}
	}

	/**
	 * Initializes the d l document type persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLDocumentType")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLDocumentType>> listenersList = new ArrayList<ModelListener<DLDocumentType>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLDocumentType>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsDDMStructure = new ContainsDDMStructure(this);

		addDDMStructure = new AddDDMStructure(this);
		clearDDMStructures = new ClearDDMStructures(this);
		removeDDMStructure = new RemoveDDMStructure(this);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLDocumentTypeImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

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
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	protected ContainsDDMStructure containsDDMStructure;
	protected AddDDMStructure addDDMStructure;
	protected ClearDDMStructures clearDDMStructures;
	protected RemoveDDMStructure removeDDMStructure;

	protected class ContainsDDMStructure {
		protected ContainsDDMStructure(
			DLDocumentTypePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSDDMSTRUCTURE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long documentTypeId, long structureId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(documentTypeId), new Long(structureId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddDDMStructure {
		protected AddDDMStructure(DLDocumentTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO DLDocumentType_DDMStructure (documentTypeId, structureId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long documentTypeId, long structureId)
			throws SystemException {
			if (!_persistenceImpl.containsDDMStructure.contains(
						documentTypeId, structureId)) {
				ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>[] ddmStructureListeners =
					ddmStructurePersistence.getListeners();

				for (ModelListener<DLDocumentType> listener : listeners) {
					listener.onBeforeAddAssociation(documentTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onBeforeAddAssociation(structureId,
						DLDocumentType.class.getName(), documentTypeId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(documentTypeId), new Long(structureId)
					});

				for (ModelListener<DLDocumentType> listener : listeners) {
					listener.onAfterAddAssociation(documentTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onAfterAddAssociation(structureId,
						DLDocumentType.class.getName(), documentTypeId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private DLDocumentTypePersistenceImpl _persistenceImpl;
	}

	protected class ClearDDMStructures {
		protected ClearDDMStructures(
			DLDocumentTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLDocumentType_DDMStructure WHERE documentTypeId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long documentTypeId) throws SystemException {
			ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>[] ddmStructureListeners =
				ddmStructurePersistence.getListeners();

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures =
				null;

			if ((listeners.length > 0) || (ddmStructureListeners.length > 0)) {
				ddmStructures = getDDMStructures(documentTypeId);

				for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
					for (ModelListener<DLDocumentType> listener : listeners) {
						listener.onBeforeRemoveAssociation(documentTypeId,
							com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
							ddmStructure.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
						listener.onBeforeRemoveAssociation(ddmStructure.getPrimaryKey(),
							DLDocumentType.class.getName(), documentTypeId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(documentTypeId) });

			if ((listeners.length > 0) || (ddmStructureListeners.length > 0)) {
				for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
					for (ModelListener<DLDocumentType> listener : listeners) {
						listener.onAfterRemoveAssociation(documentTypeId,
							com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
							ddmStructure.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
						listener.onAfterRemoveAssociation(ddmStructure.getPrimaryKey(),
							DLDocumentType.class.getName(), documentTypeId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveDDMStructure {
		protected RemoveDDMStructure(
			DLDocumentTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLDocumentType_DDMStructure WHERE documentTypeId = ? AND structureId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long documentTypeId, long structureId)
			throws SystemException {
			if (_persistenceImpl.containsDDMStructure.contains(documentTypeId,
						structureId)) {
				ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>[] ddmStructureListeners =
					ddmStructurePersistence.getListeners();

				for (ModelListener<DLDocumentType> listener : listeners) {
					listener.onBeforeRemoveAssociation(documentTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onBeforeRemoveAssociation(structureId,
						DLDocumentType.class.getName(), documentTypeId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(documentTypeId), new Long(structureId)
					});

				for (ModelListener<DLDocumentType> listener : listeners) {
					listener.onAfterRemoveAssociation(documentTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onAfterRemoveAssociation(structureId,
						DLDocumentType.class.getName(), documentTypeId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private DLDocumentTypePersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_DLDOCUMENTTYPE = "SELECT dlDocumentType FROM DLDocumentType dlDocumentType";
	private static final String _SQL_SELECT_DLDOCUMENTTYPE_WHERE = "SELECT dlDocumentType FROM DLDocumentType dlDocumentType WHERE ";
	private static final String _SQL_COUNT_DLDOCUMENTTYPE = "SELECT COUNT(dlDocumentType) FROM DLDocumentType dlDocumentType";
	private static final String _SQL_COUNT_DLDOCUMENTTYPE_WHERE = "SELECT COUNT(dlDocumentType) FROM DLDocumentType dlDocumentType WHERE ";
	private static final String _SQL_GETDDMSTRUCTURES = "SELECT {DDMStructure.*} FROM DDMStructure INNER JOIN DLDocumentType_DDMStructure ON (DLDocumentType_DDMStructure.structureId = DDMStructure.structureId) WHERE (DLDocumentType_DDMStructure.documentTypeId = ?)";
	private static final String _SQL_GETDDMSTRUCTURESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLDocumentType_DDMStructure WHERE documentTypeId = ?";
	private static final String _SQL_CONTAINSDDMSTRUCTURE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLDocumentType_DDMStructure WHERE documentTypeId = ? AND structureId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlDocumentType.groupId = ?";
	private static final String _FILTER_SQL_SELECT_DLDOCUMENTTYPE_WHERE = "SELECT DISTINCT {dlDocumentType.*} FROM DLDocumentType dlDocumentType WHERE ";
	private static final String _FILTER_SQL_SELECT_DLDOCUMENTTYPE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLDocumentType.*} FROM (SELECT DISTINCT dlDocumentType.documentTypeId FROM DLDocumentType dlDocumentType WHERE ";
	private static final String _FILTER_SQL_SELECT_DLDOCUMENTTYPE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLDocumentType ON TEMP_TABLE.documentTypeId = DLDocumentType.documentTypeId";
	private static final String _FILTER_SQL_COUNT_DLDOCUMENTTYPE_WHERE = "SELECT COUNT(DISTINCT dlDocumentType.documentTypeId) AS COUNT_VALUE FROM DLDocumentType dlDocumentType WHERE ";
	private static final String _FILTER_COLUMN_PK = "dlDocumentType.documentTypeId";
	private static final String _FILTER_ENTITY_ALIAS = "dlDocumentType";
	private static final String _FILTER_ENTITY_TABLE = "DLDocumentType";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlDocumentType.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLDocumentType.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLDocumentType exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLDocumentType exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLDocumentTypePersistenceImpl.class);
}