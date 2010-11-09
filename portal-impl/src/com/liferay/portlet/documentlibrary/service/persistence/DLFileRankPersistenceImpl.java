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
import com.liferay.portal.kernel.annotation.BeanReference;
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

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d l file rank service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRankPersistence
 * @see DLFileRankUtil
 * @generated
 */
public class DLFileRankPersistenceImpl extends BasePersistenceImpl<DLFileRank>
	implements DLFileRankPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileRankUtil} to access the d l file rank persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileRankImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByF_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByF_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_U_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the d l file rank in the entity cache if it is enabled.
	 *
	 * @param dlFileRank the d l file rank to cache
	 */
	public void cacheResult(DLFileRank dlFileRank) {
		EntityCacheUtil.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey(), dlFileRank);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
			new Object[] {
				new Long(dlFileRank.getCompanyId()),
				new Long(dlFileRank.getUserId()),
				new Long(dlFileRank.getFolderId()),
				
			dlFileRank.getName()
			}, dlFileRank);
	}

	/**
	 * Caches the d l file ranks in the entity cache if it is enabled.
	 *
	 * @param dlFileRanks the d l file ranks to cache
	 */
	public void cacheResult(List<DLFileRank> dlFileRanks) {
		for (DLFileRank dlFileRank : dlFileRanks) {
			if (EntityCacheUtil.getResult(
						DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
						DLFileRankImpl.class, dlFileRank.getPrimaryKey(), this) == null) {
				cacheResult(dlFileRank);
			}
		}
	}

	/**
	 * Clears the cache for all d l file ranks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(DLFileRankImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileRankImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d l file rank.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DLFileRank dlFileRank) {
		EntityCacheUtil.removeResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_F_N,
			new Object[] {
				new Long(dlFileRank.getCompanyId()),
				new Long(dlFileRank.getUserId()),
				new Long(dlFileRank.getFolderId()),
				
			dlFileRank.getName()
			});
	}

	/**
	 * Creates a new d l file rank with the primary key. Does not add the d l file rank to the database.
	 *
	 * @param fileRankId the primary key for the new d l file rank
	 * @return the new d l file rank
	 */
	public DLFileRank create(long fileRankId) {
		DLFileRank dlFileRank = new DLFileRankImpl();

		dlFileRank.setNew(true);
		dlFileRank.setPrimaryKey(fileRankId);

		return dlFileRank;
	}

	/**
	 * Removes the d l file rank with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l file rank to remove
	 * @return the d l file rank that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d l file rank with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileRankId the primary key of the d l file rank to remove
	 * @return the d l file rank that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank remove(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileRank dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
					new Long(fileRankId));

			if (dlFileRank == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileRankId);
				}

				throw new NoSuchFileRankException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileRankId);
			}

			return remove(dlFileRank);
		}
		catch (NoSuchFileRankException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileRank removeImpl(DLFileRank dlFileRank)
		throws SystemException {
		dlFileRank = toUnwrappedModel(dlFileRank);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlFileRank);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileRankModelImpl dlFileRankModelImpl = (DLFileRankModelImpl)dlFileRank;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_F_N,
			new Object[] {
				new Long(dlFileRankModelImpl.getOriginalCompanyId()),
				new Long(dlFileRankModelImpl.getOriginalUserId()),
				new Long(dlFileRankModelImpl.getOriginalFolderId()),
				
			dlFileRankModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey());

		return dlFileRank;
	}

	public DLFileRank updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge) throws SystemException {
		dlFileRank = toUnwrappedModel(dlFileRank);

		boolean isNew = dlFileRank.isNew();

		DLFileRankModelImpl dlFileRankModelImpl = (DLFileRankModelImpl)dlFileRank;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileRank, merge);

			dlFileRank.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey(), dlFileRank);

		if (!isNew &&
				((dlFileRank.getCompanyId() != dlFileRankModelImpl.getOriginalCompanyId()) ||
				(dlFileRank.getUserId() != dlFileRankModelImpl.getOriginalUserId()) ||
				(dlFileRank.getFolderId() != dlFileRankModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileRank.getName(),
					dlFileRankModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_F_N,
				new Object[] {
					new Long(dlFileRankModelImpl.getOriginalCompanyId()),
					new Long(dlFileRankModelImpl.getOriginalUserId()),
					new Long(dlFileRankModelImpl.getOriginalFolderId()),
					
				dlFileRankModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFileRank.getCompanyId() != dlFileRankModelImpl.getOriginalCompanyId()) ||
				(dlFileRank.getUserId() != dlFileRankModelImpl.getOriginalUserId()) ||
				(dlFileRank.getFolderId() != dlFileRankModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileRank.getName(),
					dlFileRankModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
				new Object[] {
					new Long(dlFileRank.getCompanyId()),
					new Long(dlFileRank.getUserId()),
					new Long(dlFileRank.getFolderId()),
					
				dlFileRank.getName()
				}, dlFileRank);
		}

		return dlFileRank;
	}

	protected DLFileRank toUnwrappedModel(DLFileRank dlFileRank) {
		if (dlFileRank instanceof DLFileRankImpl) {
			return dlFileRank;
		}

		DLFileRankImpl dlFileRankImpl = new DLFileRankImpl();

		dlFileRankImpl.setNew(dlFileRank.isNew());
		dlFileRankImpl.setPrimaryKey(dlFileRank.getPrimaryKey());

		dlFileRankImpl.setFileRankId(dlFileRank.getFileRankId());
		dlFileRankImpl.setGroupId(dlFileRank.getGroupId());
		dlFileRankImpl.setCompanyId(dlFileRank.getCompanyId());
		dlFileRankImpl.setUserId(dlFileRank.getUserId());
		dlFileRankImpl.setCreateDate(dlFileRank.getCreateDate());
		dlFileRankImpl.setFolderId(dlFileRank.getFolderId());
		dlFileRankImpl.setName(dlFileRank.getName());

		return dlFileRankImpl;
	}

	/**
	 * Finds the d l file rank with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l file rank to find
	 * @return the d l file rank
	 * @throws com.liferay.portal.NoSuchModelException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l file rank with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileRankException} if it could not be found.
	 *
	 * @param fileRankId the primary key of the d l file rank to find
	 * @return the d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByPrimaryKey(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByPrimaryKey(fileRankId);

		if (dlFileRank == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileRankId);
			}

			throw new NoSuchFileRankException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileRankId);
		}

		return dlFileRank;
	}

	/**
	 * Finds the d l file rank with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l file rank to find
	 * @return the d l file rank, or <code>null</code> if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d l file rank with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileRankId the primary key of the d l file rank to find
	 * @return the d l file rank, or <code>null</code> if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank fetchByPrimaryKey(long fileRankId)
		throws SystemException {
		DLFileRank dlFileRank = (DLFileRank)EntityCacheUtil.getResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
				DLFileRankImpl.class, fileRankId, this);

		if (dlFileRank == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
						new Long(fileRankId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileRank != null) {
					cacheResult(dlFileRank);
				}

				closeSession(session);
			}
		}

		return dlFileRank;
	}

	/**
	 * Finds all the d l file ranks where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByUserId(long userId) throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @return the range of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<DLFileRank>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file rank in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file rank in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		int count = countByUserId(userId);

		List<DLFileRank> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file ranks before and after the current d l file rank in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileRankId the primary key of the current d l file rank
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank[] findByUserId_PrevAndNext(long fileRankId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByUserId_PrevAndNext(session, dlFileRank, userId,
					orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByUserId_PrevAndNext(session, dlFileRank, userId,
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

	protected DLFileRank getByUserId_PrevAndNext(Session session,
		DLFileRank dlFileRank, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @return the matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the d l file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @return the range of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<DLFileRank>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Finds the first d l file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		int count = countByG_U(groupId, userId);

		List<DLFileRank> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file ranks before and after the current d l file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileRankId the primary key of the current d l file rank
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank[] findByG_U_PrevAndNext(long fileRankId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByG_U_PrevAndNext(session, dlFileRank, groupId,
					userId, orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByG_U_PrevAndNext(session, dlFileRank, groupId,
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

	protected DLFileRank getByG_U_PrevAndNext(Session session,
		DLFileRank dlFileRank, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d l file ranks where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByF_N(long folderId, String name)
		throws SystemException {
		return findByF_N(folderId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the d l file ranks where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @return the range of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByF_N(long folderId, String name, int start,
		int end) throws SystemException {
		return findByF_N(folderId, name, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file ranks where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				folderId, name,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_F_N,
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

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFileRank>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_F_N,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_F_N,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d l file rank in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByF_N_First(long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByF_N(folderId, name, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d l file rank in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByF_N_Last(long folderId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		int count = countByF_N(folderId, name);

		List<DLFileRank> list = findByF_N(folderId, name, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d l file ranks before and after the current d l file rank in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileRankId the primary key of the current d l file rank
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank[] findByF_N_PrevAndNext(long fileRankId, long folderId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		Session session = null;

		try {
			session = openSession();

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = getByF_N_PrevAndNext(session, dlFileRank, folderId,
					name, orderByComparator, true);

			array[1] = dlFileRank;

			array[2] = getByF_N_PrevAndNext(session, dlFileRank, folderId,
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

	protected DLFileRank getByF_N_PrevAndNext(Session session,
		DLFileRank dlFileRank, long folderId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILERANK_WHERE);

		query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_F_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_F_N_NAME_2);
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
			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(folderId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileRank);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileRank> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the d l file rank where companyId = &#63; and userId = &#63; and folderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileRankException} if it could not be found.
	 *
	 * @param companyId the company id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file rank
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank findByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByC_U_F_N(companyId, userId, folderId, name);

		if (dlFileRank == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileRankException(msg.toString());
		}

		return dlFileRank;
	}

	/**
	 * Finds the d l file rank where companyId = &#63; and userId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file rank, or <code>null</code> if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank fetchByC_U_F_N(long companyId, long userId,
		long folderId, String name) throws SystemException {
		return fetchByC_U_F_N(companyId, userId, folderId, name, true);
	}

	/**
	 * Finds the d l file rank where companyId = &#63; and userId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the matching d l file rank, or <code>null</code> if a matching d l file rank could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileRank fetchByC_U_F_N(long companyId, long userId,
		long folderId, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId, folderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_U_F_N,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_C_U_F_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_F_N_USERID_2);

			query.append(_FINDER_COLUMN_C_U_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_U_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_U_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_U_F_N_NAME_2);
				}
			}

			query.append(DLFileRankModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileRank> list = q.list();

				result = list;

				DLFileRank dlFileRank = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
						finderArgs, list);
				}
				else {
					dlFileRank = list.get(0);

					cacheResult(dlFileRank);

					if ((dlFileRank.getCompanyId() != companyId) ||
							(dlFileRank.getUserId() != userId) ||
							(dlFileRank.getFolderId() != folderId) ||
							(dlFileRank.getName() == null) ||
							!dlFileRank.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
							finderArgs, dlFileRank);
					}
				}

				return dlFileRank;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_F_N,
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
				return (DLFileRank)result;
			}
		}
	}

	/**
	 * Finds all the d l file ranks.
	 *
	 * @return the d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d l file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @return the range of d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d l file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file ranks to return
	 * @param end the upper bound of the range of d l file ranks to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileRank> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILERANK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILERANK.concat(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d l file ranks where userId = &#63; from the database.
	 *
	 * @param userId the user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (DLFileRank dlFileRank : findByUserId(userId)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Removes all the d l file ranks where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (DLFileRank dlFileRank : findByG_U(groupId, userId)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Removes all the d l file ranks where folderId = &#63; and name = &#63; from the database.
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByF_N(long folderId, String name)
		throws SystemException {
		for (DLFileRank dlFileRank : findByF_N(folderId, name)) {
			remove(dlFileRank);
		}
	}

	/**
	 * Removes the d l file rank where companyId = &#63; and userId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByC_U_F_N(companyId, userId, folderId, name);

		remove(dlFileRank);
	}

	/**
	 * Removes all the d l file ranks from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileRank dlFileRank : findAll()) {
			remove(dlFileRank);
		}
	}

	/**
	 * Counts all the d l file ranks where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the number of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param userId the user id to search with
	 * @return the number of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

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
	 * Counts all the d l file ranks where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the number of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByF_N(long folderId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file ranks where companyId = &#63; and userId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param companyId the company id to search with
	 * @param userId the user id to search with
	 * @param folderId the folder id to search with
	 * @param name the name to search with
	 * @return the number of matching d l file ranks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId, folderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U_F_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_C_U_F_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_F_N_USERID_2);

			query.append(_FINDER_COLUMN_C_U_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_U_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_U_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_U_F_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d l file ranks.
	 *
	 * @return the number of d l file ranks
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

				Query q = session.createQuery(_SQL_COUNT_DLFILERANK);

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
	 * Initializes the d l file rank persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileRank")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileRank>> listenersList = new ArrayList<ModelListener<DLFileRank>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileRank>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileRankImpl.class.getName());
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
	private static final String _SQL_SELECT_DLFILERANK = "SELECT dlFileRank FROM DLFileRank dlFileRank";
	private static final String _SQL_SELECT_DLFILERANK_WHERE = "SELECT dlFileRank FROM DLFileRank dlFileRank WHERE ";
	private static final String _SQL_COUNT_DLFILERANK = "SELECT COUNT(dlFileRank) FROM DLFileRank dlFileRank";
	private static final String _SQL_COUNT_DLFILERANK_WHERE = "SELECT COUNT(dlFileRank) FROM DLFileRank dlFileRank WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "dlFileRank.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileRank.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileRank.userId = ?";
	private static final String _FINDER_COLUMN_F_N_FOLDERID_2 = "dlFileRank.folderId = ? AND ";
	private static final String _FINDER_COLUMN_F_N_NAME_1 = "dlFileRank.name IS NULL";
	private static final String _FINDER_COLUMN_F_N_NAME_2 = "dlFileRank.name = ?";
	private static final String _FINDER_COLUMN_F_N_NAME_3 = "(dlFileRank.name IS NULL OR dlFileRank.name = ?)";
	private static final String _FINDER_COLUMN_C_U_F_N_COMPANYID_2 = "dlFileRank.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_N_USERID_2 = "dlFileRank.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_N_FOLDERID_2 = "dlFileRank.folderId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_N_NAME_1 = "dlFileRank.name IS NULL";
	private static final String _FINDER_COLUMN_C_U_F_N_NAME_2 = "dlFileRank.name = ?";
	private static final String _FINDER_COLUMN_C_U_F_N_NAME_3 = "(dlFileRank.name IS NULL OR dlFileRank.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileRank.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileRank exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileRank exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFileRankPersistenceImpl.class);
}