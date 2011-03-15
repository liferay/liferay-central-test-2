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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureEntryLinkImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureEntryLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m structure entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryLinkPersistence
 * @see DDMStructureEntryLinkUtil
 * @generated
 */
public class DDMStructureEntryLinkPersistenceImpl extends BasePersistenceImpl<DDMStructureEntryLink>
	implements DDMStructureEntryLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureEntryLinkUtil} to access the d d m structure entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureEntryLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_STRUCTUREID = new FinderPath(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByStructureId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByStructureId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_S_C_C = new FinderPath(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByS_C_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_S_C_C = new FinderPath(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByS_C_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the d d m structure entry link in the entity cache if it is enabled.
	 *
	 * @param ddmStructureEntryLink the d d m structure entry link to cache
	 */
	public void cacheResult(DDMStructureEntryLink ddmStructureEntryLink) {
		EntityCacheUtil.putResult(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkImpl.class,
			ddmStructureEntryLink.getPrimaryKey(), ddmStructureEntryLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_C_C,
			new Object[] {
				ddmStructureEntryLink.getStructureId(),
				
			ddmStructureEntryLink.getClassName(),
				Long.valueOf(ddmStructureEntryLink.getClassPK())
			}, ddmStructureEntryLink);
	}

	/**
	 * Caches the d d m structure entry links in the entity cache if it is enabled.
	 *
	 * @param ddmStructureEntryLinks the d d m structure entry links to cache
	 */
	public void cacheResult(List<DDMStructureEntryLink> ddmStructureEntryLinks) {
		for (DDMStructureEntryLink ddmStructureEntryLink : ddmStructureEntryLinks) {
			if (EntityCacheUtil.getResult(
						DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureEntryLinkImpl.class,
						ddmStructureEntryLink.getPrimaryKey(), this) == null) {
				cacheResult(ddmStructureEntryLink);
			}
		}
	}

	/**
	 * Clears the cache for all d d m structure entry links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMStructureEntryLinkImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMStructureEntryLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d d m structure entry link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DDMStructureEntryLink ddmStructureEntryLink) {
		EntityCacheUtil.removeResult(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkImpl.class,
			ddmStructureEntryLink.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_C_C,
			new Object[] {
				ddmStructureEntryLink.getStructureId(),
				
			ddmStructureEntryLink.getClassName(),
				Long.valueOf(ddmStructureEntryLink.getClassPK())
			});
	}

	/**
	 * Creates a new d d m structure entry link with the primary key. Does not add the d d m structure entry link to the database.
	 *
	 * @param structureEntryLinkId the primary key for the new d d m structure entry link
	 * @return the new d d m structure entry link
	 */
	public DDMStructureEntryLink create(long structureEntryLinkId) {
		DDMStructureEntryLink ddmStructureEntryLink = new DDMStructureEntryLinkImpl();

		ddmStructureEntryLink.setNew(true);
		ddmStructureEntryLink.setPrimaryKey(structureEntryLinkId);

		return ddmStructureEntryLink;
	}

	/**
	 * Removes the d d m structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure entry link to remove
	 * @return the d d m structure entry link that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d d m structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureEntryLinkId the primary key of the d d m structure entry link to remove
	 * @return the d d m structure entry link that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink remove(long structureEntryLinkId)
		throws NoSuchStructureEntryLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMStructureEntryLink ddmStructureEntryLink = (DDMStructureEntryLink)session.get(DDMStructureEntryLinkImpl.class,
					Long.valueOf(structureEntryLinkId));

			if (ddmStructureEntryLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						structureEntryLinkId);
				}

				throw new NoSuchStructureEntryLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					structureEntryLinkId);
			}

			return ddmStructureEntryLinkPersistence.remove(ddmStructureEntryLink);
		}
		catch (NoSuchStructureEntryLinkException nsee) {
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
	 * Removes the d d m structure entry link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureEntryLink the d d m structure entry link to remove
	 * @return the d d m structure entry link that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink remove(
		DDMStructureEntryLink ddmStructureEntryLink) throws SystemException {
		return super.remove(ddmStructureEntryLink);
	}

	protected DDMStructureEntryLink removeImpl(
		DDMStructureEntryLink ddmStructureEntryLink) throws SystemException {
		ddmStructureEntryLink = toUnwrappedModel(ddmStructureEntryLink);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ddmStructureEntryLink);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DDMStructureEntryLinkModelImpl ddmStructureEntryLinkModelImpl = (DDMStructureEntryLinkModelImpl)ddmStructureEntryLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_C_C,
			new Object[] {
				ddmStructureEntryLinkModelImpl.getStructureId(),
				
			ddmStructureEntryLinkModelImpl.getClassName(),
				Long.valueOf(ddmStructureEntryLinkModelImpl.getClassPK())
			});

		EntityCacheUtil.removeResult(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkImpl.class,
			ddmStructureEntryLink.getPrimaryKey());

		return ddmStructureEntryLink;
	}

	public DDMStructureEntryLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink,
		boolean merge) throws SystemException {
		ddmStructureEntryLink = toUnwrappedModel(ddmStructureEntryLink);

		boolean isNew = ddmStructureEntryLink.isNew();

		DDMStructureEntryLinkModelImpl ddmStructureEntryLinkModelImpl = (DDMStructureEntryLinkModelImpl)ddmStructureEntryLink;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ddmStructureEntryLink, merge);

			ddmStructureEntryLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureEntryLinkImpl.class,
			ddmStructureEntryLink.getPrimaryKey(), ddmStructureEntryLink);

		if (!isNew &&
				(!Validator.equals(ddmStructureEntryLink.getStructureId(),
					ddmStructureEntryLinkModelImpl.getOriginalStructureId()) ||
				!Validator.equals(ddmStructureEntryLink.getClassName(),
					ddmStructureEntryLinkModelImpl.getOriginalClassName()) ||
				(ddmStructureEntryLink.getClassPK() != ddmStructureEntryLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_C_C,
				new Object[] {
					ddmStructureEntryLinkModelImpl.getOriginalStructureId(),
					
				ddmStructureEntryLinkModelImpl.getOriginalClassName(),
					Long.valueOf(
						ddmStructureEntryLinkModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				(!Validator.equals(ddmStructureEntryLink.getStructureId(),
					ddmStructureEntryLinkModelImpl.getOriginalStructureId()) ||
				!Validator.equals(ddmStructureEntryLink.getClassName(),
					ddmStructureEntryLinkModelImpl.getOriginalClassName()) ||
				(ddmStructureEntryLink.getClassPK() != ddmStructureEntryLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_C_C,
				new Object[] {
					ddmStructureEntryLink.getStructureId(),
					
				ddmStructureEntryLink.getClassName(),
					Long.valueOf(ddmStructureEntryLink.getClassPK())
				}, ddmStructureEntryLink);
		}

		return ddmStructureEntryLink;
	}

	protected DDMStructureEntryLink toUnwrappedModel(
		DDMStructureEntryLink ddmStructureEntryLink) {
		if (ddmStructureEntryLink instanceof DDMStructureEntryLinkImpl) {
			return ddmStructureEntryLink;
		}

		DDMStructureEntryLinkImpl ddmStructureEntryLinkImpl = new DDMStructureEntryLinkImpl();

		ddmStructureEntryLinkImpl.setNew(ddmStructureEntryLink.isNew());
		ddmStructureEntryLinkImpl.setPrimaryKey(ddmStructureEntryLink.getPrimaryKey());

		ddmStructureEntryLinkImpl.setStructureEntryLinkId(ddmStructureEntryLink.getStructureEntryLinkId());
		ddmStructureEntryLinkImpl.setStructureId(ddmStructureEntryLink.getStructureId());
		ddmStructureEntryLinkImpl.setClassName(ddmStructureEntryLink.getClassName());
		ddmStructureEntryLinkImpl.setClassPK(ddmStructureEntryLink.getClassPK());

		return ddmStructureEntryLinkImpl;
	}

	/**
	 * Finds the d d m structure entry link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure entry link to find
	 * @return the d d m structure entry link
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d m structure entry link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException} if it could not be found.
	 *
	 * @param structureEntryLinkId the primary key of the d d m structure entry link to find
	 * @return the d d m structure entry link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink findByPrimaryKey(long structureEntryLinkId)
		throws NoSuchStructureEntryLinkException, SystemException {
		DDMStructureEntryLink ddmStructureEntryLink = fetchByPrimaryKey(structureEntryLinkId);

		if (ddmStructureEntryLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					structureEntryLinkId);
			}

			throw new NoSuchStructureEntryLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				structureEntryLinkId);
		}

		return ddmStructureEntryLink;
	}

	/**
	 * Finds the d d m structure entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure entry link to find
	 * @return the d d m structure entry link, or <code>null</code> if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d m structure entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureEntryLinkId the primary key of the d d m structure entry link to find
	 * @return the d d m structure entry link, or <code>null</code> if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink fetchByPrimaryKey(long structureEntryLinkId)
		throws SystemException {
		DDMStructureEntryLink ddmStructureEntryLink = (DDMStructureEntryLink)EntityCacheUtil.getResult(DDMStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureEntryLinkImpl.class, structureEntryLinkId, this);

		if (ddmStructureEntryLink == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStructureEntryLink = (DDMStructureEntryLink)session.get(DDMStructureEntryLinkImpl.class,
						Long.valueOf(structureEntryLinkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ddmStructureEntryLink != null) {
					cacheResult(ddmStructureEntryLink);
				}

				closeSession(session);
			}
		}

		return ddmStructureEntryLink;
	}

	/**
	 * Finds all the d d m structure entry links where structureId = &#63;.
	 *
	 * @param structureId the structure ID to search with
	 * @return the matching d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntryLink> findByStructureId(String structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m structure entry links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID to search with
	 * @param start the lower bound of the range of d d m structure entry links to return
	 * @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	 * @return the range of matching d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntryLink> findByStructureId(String structureId,
		int start, int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m structure entry links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID to search with
	 * @param start the lower bound of the range of d d m structure entry links to return
	 * @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntryLink> findByStructureId(String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				structureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureEntryLink> list = (List<DDMStructureEntryLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_STRUCTUREID,
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

			query.append(_SQL_SELECT_DDMSTRUCTUREENTRYLINK_WHERE);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
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

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<DDMStructureEntryLink>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_STRUCTUREID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_STRUCTUREID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d d m structure entry link in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure entry link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink findByStructureId_First(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		List<DDMStructureEntryLink> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d m structure entry link in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure entry link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink findByStructureId_Last(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		int count = countByStructureId(structureId);

		List<DDMStructureEntryLink> list = findByStructureId(structureId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d m structure entry links before and after the current d d m structure entry link in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureEntryLinkId the primary key of the current d d m structure entry link
	 * @param structureId the structure ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure entry link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink[] findByStructureId_PrevAndNext(
		long structureEntryLinkId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		DDMStructureEntryLink ddmStructureEntryLink = findByPrimaryKey(structureEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureEntryLink[] array = new DDMStructureEntryLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(session,
					ddmStructureEntryLink, structureId, orderByComparator, true);

			array[1] = ddmStructureEntryLink;

			array[2] = getByStructureId_PrevAndNext(session,
					ddmStructureEntryLink, structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureEntryLink getByStructureId_PrevAndNext(
		Session session, DDMStructureEntryLink ddmStructureEntryLink,
		String structureId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTUREENTRYLINK_WHERE);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
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

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureEntryLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureEntryLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException} if it could not be found.
	 *
	 * @param structureId the structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching d d m structure entry link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink findByS_C_C(String structureId,
		String className, long classPK)
		throws NoSuchStructureEntryLinkException, SystemException {
		DDMStructureEntryLink ddmStructureEntryLink = fetchByS_C_C(structureId,
				className, classPK);

		if (ddmStructureEntryLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(", className=");
			msg.append(className);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}

		return ddmStructureEntryLink;
	}

	/**
	 * Finds the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureId the structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching d d m structure entry link, or <code>null</code> if a matching d d m structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink fetchByS_C_C(String structureId,
		String className, long classPK) throws SystemException {
		return fetchByS_C_C(structureId, className, classPK, true);
	}

	/**
	 * Finds the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureId the structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching d d m structure entry link, or <code>null</code> if a matching d d m structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureEntryLink fetchByS_C_C(String structureId,
		String className, long classPK, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { structureId, className, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_S_C_C,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMSTRUCTUREENTRYLINK_WHERE);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_S_C_C_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_C_C_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_C_C_STRUCTUREID_2);
				}
			}

			if (className == null) {
				query.append(_FINDER_COLUMN_S_C_C_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_C_C_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_C_C_CLASSNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_S_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
				}

				if (className != null) {
					qPos.add(className);
				}

				qPos.add(classPK);

				List<DDMStructureEntryLink> list = q.list();

				result = list;

				DDMStructureEntryLink ddmStructureEntryLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_C_C,
						finderArgs, list);
				}
				else {
					ddmStructureEntryLink = list.get(0);

					cacheResult(ddmStructureEntryLink);

					if ((ddmStructureEntryLink.getStructureId() == null) ||
							!ddmStructureEntryLink.getStructureId()
													  .equals(structureId) ||
							(ddmStructureEntryLink.getClassName() == null) ||
							!ddmStructureEntryLink.getClassName()
													  .equals(className) ||
							(ddmStructureEntryLink.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_C_C,
							finderArgs, ddmStructureEntryLink);
					}
				}

				return ddmStructureEntryLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_C_C,
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
				return (DDMStructureEntryLink)result;
			}
		}
	}

	/**
	 * Finds all the d d m structure entry links.
	 *
	 * @return the d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntryLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m structure entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure entry links to return
	 * @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	 * @return the range of d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntryLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m structure entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure entry links to return
	 * @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureEntryLink> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureEntryLink> list = (List<DDMStructureEntryLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTUREENTRYLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTUREENTRYLINK;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMStructureEntryLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMStructureEntryLink>)QueryUtil.list(q,
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
	 * Removes all the d d m structure entry links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStructureId(String structureId)
		throws SystemException {
		for (DDMStructureEntryLink ddmStructureEntryLink : findByStructureId(
				structureId)) {
			ddmStructureEntryLinkPersistence.remove(ddmStructureEntryLink);
		}
	}

	/**
	 * Removes the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; from the database.
	 *
	 * @param structureId the structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByS_C_C(String structureId, String className, long classPK)
		throws NoSuchStructureEntryLinkException, SystemException {
		DDMStructureEntryLink ddmStructureEntryLink = findByS_C_C(structureId,
				className, classPK);

		ddmStructureEntryLinkPersistence.remove(ddmStructureEntryLink);
	}

	/**
	 * Removes all the d d m structure entry links from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMStructureEntryLink ddmStructureEntryLink : findAll()) {
			ddmStructureEntryLinkPersistence.remove(ddmStructureEntryLink);
		}
	}

	/**
	 * Counts all the d d m structure entry links where structureId = &#63;.
	 *
	 * @param structureId the structure ID to search with
	 * @return the number of matching d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStructureId(String structureId) throws SystemException {
		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTUREENTRYLINK_WHERE);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d d m structure entry links where structureId = &#63; and className = &#63; and classPK = &#63;.
	 *
	 * @param structureId the structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the number of matching d d m structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByS_C_C(String structureId, String className, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { structureId, className, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_S_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMSTRUCTUREENTRYLINK_WHERE);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_S_C_C_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_C_C_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_C_C_STRUCTUREID_2);
				}
			}

			if (className == null) {
				query.append(_FINDER_COLUMN_S_C_C_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_S_C_C_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_S_C_C_CLASSNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_S_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
				}

				if (className != null) {
					qPos.add(className);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_S_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d d m structure entry links.
	 *
	 * @return the number of d d m structure entry links
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

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTUREENTRYLINK);

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
	 * Initializes the d d m structure entry link persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMStructureEntryLink>> listenersList = new ArrayList<ModelListener<DDMStructureEntryLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMStructureEntryLink>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDMStructureEntryLinkImpl.class.getName());
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
	private static final String _SQL_SELECT_DDMSTRUCTUREENTRYLINK = "SELECT ddmStructureEntryLink FROM DDMStructureEntryLink ddmStructureEntryLink";
	private static final String _SQL_SELECT_DDMSTRUCTUREENTRYLINK_WHERE = "SELECT ddmStructureEntryLink FROM DDMStructureEntryLink ddmStructureEntryLink WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTUREENTRYLINK = "SELECT COUNT(ddmStructureEntryLink) FROM DDMStructureEntryLink ddmStructureEntryLink";
	private static final String _SQL_COUNT_DDMSTRUCTUREENTRYLINK_WHERE = "SELECT COUNT(ddmStructureEntryLink) FROM DDMStructureEntryLink ddmStructureEntryLink WHERE ";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1 = "ddmStructureEntryLink.structureId IS NULL";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmStructureEntryLink.structureId = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3 = "(ddmStructureEntryLink.structureId IS NULL OR ddmStructureEntryLink.structureId = ?)";
	private static final String _FINDER_COLUMN_S_C_C_STRUCTUREID_1 = "ddmStructureEntryLink.structureId IS NULL AND ";
	private static final String _FINDER_COLUMN_S_C_C_STRUCTUREID_2 = "ddmStructureEntryLink.structureId = ? AND ";
	private static final String _FINDER_COLUMN_S_C_C_STRUCTUREID_3 = "(ddmStructureEntryLink.structureId IS NULL OR ddmStructureEntryLink.structureId = ?) AND ";
	private static final String _FINDER_COLUMN_S_C_C_CLASSNAME_1 = "ddmStructureEntryLink.className IS NULL AND ";
	private static final String _FINDER_COLUMN_S_C_C_CLASSNAME_2 = "ddmStructureEntryLink.className = ? AND ";
	private static final String _FINDER_COLUMN_S_C_C_CLASSNAME_3 = "(ddmStructureEntryLink.className IS NULL OR ddmStructureEntryLink.className = ?) AND ";
	private static final String _FINDER_COLUMN_S_C_C_CLASSPK_2 = "ddmStructureEntryLink.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureEntryLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructureEntryLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructureEntryLink exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMStructureEntryLinkPersistenceImpl.class);
}