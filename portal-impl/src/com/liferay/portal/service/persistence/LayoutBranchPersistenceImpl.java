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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
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
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutBranchImpl;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the layout branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchPersistence
 * @see LayoutBranchUtil
 * @generated
 */
public class LayoutBranchPersistenceImpl extends BasePersistenceImpl<LayoutBranch>
	implements LayoutBranchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutBranchUtil} to access the layout branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutBranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the layout branch in the entity cache if it is enabled.
	 *
	 * @param layoutBranch the layout branch to cache
	 */
	public void cacheResult(LayoutBranch layoutBranch) {
		EntityCacheUtil.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(layoutBranch.getGroupId()),
				
			layoutBranch.getName()
			}, layoutBranch);
	}

	/**
	 * Caches the layout branchs in the entity cache if it is enabled.
	 *
	 * @param layoutBranchs the layout branchs to cache
	 */
	public void cacheResult(List<LayoutBranch> layoutBranchs) {
		for (LayoutBranch layoutBranch : layoutBranchs) {
			if (EntityCacheUtil.getResult(
						LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						LayoutBranchImpl.class, layoutBranch.getPrimaryKey(),
						this) == null) {
				cacheResult(layoutBranch);
			}
		}
	}

	/**
	 * Clears the cache for all layout branchs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(LayoutBranchImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutBranchImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the layout branch.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(LayoutBranch layoutBranch) {
		EntityCacheUtil.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(layoutBranch.getGroupId()),
				
			layoutBranch.getName()
			});
	}

	/**
	 * Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	 *
	 * @param layoutBranchId the primary key for the new layout branch
	 * @return the new layout branch
	 */
	public LayoutBranch create(long layoutBranchId) {
		LayoutBranch layoutBranch = new LayoutBranchImpl();

		layoutBranch.setNew(true);
		layoutBranch.setPrimaryKey(layoutBranchId);

		return layoutBranch;
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout branch to remove
	 * @return the layout branch that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutBranchId the primary key of the layout branch to remove
	 * @return the layout branch that was removed
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch remove(long layoutBranchId)
		throws NoSuchLayoutBranchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutBranch layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
					new Long(layoutBranchId));

			if (layoutBranch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						layoutBranchId);
				}

				throw new NoSuchLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					layoutBranchId);
			}

			return remove(layoutBranch);
		}
		catch (NoSuchLayoutBranchException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutBranch removeImpl(LayoutBranch layoutBranch)
		throws SystemException {
		layoutBranch = toUnwrappedModel(layoutBranch);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, layoutBranch);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LayoutBranchModelImpl layoutBranchModelImpl = (LayoutBranchModelImpl)layoutBranch;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(layoutBranchModelImpl.getOriginalGroupId()),
				
			layoutBranchModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey());

		return layoutBranch;
	}

	public LayoutBranch updateImpl(
		com.liferay.portal.model.LayoutBranch layoutBranch, boolean merge)
		throws SystemException {
		layoutBranch = toUnwrappedModel(layoutBranch);

		boolean isNew = layoutBranch.isNew();

		LayoutBranchModelImpl layoutBranchModelImpl = (LayoutBranchModelImpl)layoutBranch;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutBranch, merge);

			layoutBranch.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutBranchImpl.class, layoutBranch.getPrimaryKey(), layoutBranch);

		if (!isNew &&
				((layoutBranch.getGroupId() != layoutBranchModelImpl.getOriginalGroupId()) ||
				!Validator.equals(layoutBranch.getName(),
					layoutBranchModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(layoutBranchModelImpl.getOriginalGroupId()),
					
				layoutBranchModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((layoutBranch.getGroupId() != layoutBranchModelImpl.getOriginalGroupId()) ||
				!Validator.equals(layoutBranch.getName(),
					layoutBranchModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(layoutBranch.getGroupId()),
					
				layoutBranch.getName()
				}, layoutBranch);
		}

		return layoutBranch;
	}

	protected LayoutBranch toUnwrappedModel(LayoutBranch layoutBranch) {
		if (layoutBranch instanceof LayoutBranchImpl) {
			return layoutBranch;
		}

		LayoutBranchImpl layoutBranchImpl = new LayoutBranchImpl();

		layoutBranchImpl.setNew(layoutBranch.isNew());
		layoutBranchImpl.setPrimaryKey(layoutBranch.getPrimaryKey());

		layoutBranchImpl.setLayoutBranchId(layoutBranch.getLayoutBranchId());
		layoutBranchImpl.setGroupId(layoutBranch.getGroupId());
		layoutBranchImpl.setCompanyId(layoutBranch.getCompanyId());
		layoutBranchImpl.setUserId(layoutBranch.getUserId());
		layoutBranchImpl.setUserName(layoutBranch.getUserName());
		layoutBranchImpl.setCreateDate(layoutBranch.getCreateDate());
		layoutBranchImpl.setModifiedDate(layoutBranch.getModifiedDate());
		layoutBranchImpl.setName(layoutBranch.getName());
		layoutBranchImpl.setDescription(layoutBranch.getDescription());

		return layoutBranchImpl;
	}

	/**
	 * Finds the layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch to find
	 * @return the layout branch
	 * @throws com.liferay.portal.NoSuchModelException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutBranchException} if it could not be found.
	 *
	 * @param layoutBranchId the primary key of the layout branch to find
	 * @return the layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByPrimaryKey(long layoutBranchId)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = fetchByPrimaryKey(layoutBranchId);

		if (layoutBranch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + layoutBranchId);
			}

			throw new NoSuchLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				layoutBranchId);
		}

		return layoutBranch;
	}

	/**
	 * Finds the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout branch to find
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutBranchId the primary key of the layout branch to find
	 * @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByPrimaryKey(long layoutBranchId)
		throws SystemException {
		LayoutBranch layoutBranch = (LayoutBranch)EntityCacheUtil.getResult(LayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutBranchImpl.class, layoutBranchId, this);

		if (layoutBranch == null) {
			Session session = null;

			try {
				session = openSession();

				layoutBranch = (LayoutBranch)session.get(LayoutBranchImpl.class,
						new Long(layoutBranchId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutBranch != null) {
					cacheResult(layoutBranch);
				}

				closeSession(session);
			}
		}

		return layoutBranch;
	}

	/**
	 * Finds all the layout branchs where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of layout branchs to return
	 * @param end the upper bound of the range of layout branchs to return (not inclusive)
	 * @return the range of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of layout branchs to return
	 * @param end the upper bound of the range of layout branchs to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutBranch> list = (List<LayoutBranch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first layout branch in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		List<LayoutBranch> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last layout branch in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		int count = countByGroupId(groupId);

		List<LayoutBranch> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the layout branchs before and after the current layout branch in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutBranchId the primary key of the current layout branch
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch[] findByGroupId_PrevAndNext(long layoutBranchId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = findByPrimaryKey(layoutBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutBranch[] array = new LayoutBranchImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layoutBranch, groupId,
					orderByComparator, true);

			array[1] = layoutBranch;

			array[2] = getByGroupId_PrevAndNext(session, layoutBranch, groupId,
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

	protected LayoutBranch getByGroupId_PrevAndNext(Session session,
		LayoutBranch layoutBranch, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

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
			query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the layout branchs where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching layout branchs that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of layout branchs to return
	 * @param end the upper bound of the range of layout branchs to return (not inclusive)
	 * @return the range of matching layout branchs that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of layout branchs to return
	 * @param end the upper bound of the range of layout branchs to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout branchs that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_LAYOUTBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTBRANCH_NO_INLINE_DISTINCT_WHERE);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutBranch.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutBranchImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<LayoutBranch>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds the layout branch where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutBranchException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the matching layout branch
	 * @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch findByG_N(long groupId, String name)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = fetchByG_N(groupId, name);

		if (layoutBranch == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutBranchException(msg.toString());
		}

		return layoutBranch;
	}

	/**
	 * Finds the layout branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Finds the layout branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutBranch fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			query.append(LayoutBranchModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				List<LayoutBranch> list = q.list();

				result = list;

				LayoutBranch layoutBranch = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					layoutBranch = list.get(0);

					cacheResult(layoutBranch);

					if ((layoutBranch.getGroupId() != groupId) ||
							(layoutBranch.getName() == null) ||
							!layoutBranch.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, layoutBranch);
					}
				}

				return layoutBranch;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
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
				return (LayoutBranch)result;
			}
		}
	}

	/**
	 * Finds all the layout branchs.
	 *
	 * @return the layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs to return
	 * @param end the upper bound of the range of layout branchs to return (not inclusive)
	 * @return the range of layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout branchs to return
	 * @param end the upper bound of the range of layout branchs to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutBranch> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutBranch> list = (List<LayoutBranch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTBRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTBRANCH.concat(LayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutBranch>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the layout branchs where groupId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (LayoutBranch layoutBranch : findByGroupId(groupId)) {
			remove(layoutBranch);
		}
	}

	/**
	 * Removes the layout branch where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_N(long groupId, String name)
		throws NoSuchLayoutBranchException, SystemException {
		LayoutBranch layoutBranch = findByG_N(groupId, name);

		remove(layoutBranch);
	}

	/**
	 * Removes all the layout branchs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LayoutBranch layoutBranch : findAll()) {
			remove(layoutBranch);
		}
	}

	/**
	 * Counts all the layout branchs where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

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
	 * Filters by the user's permissions and counts all the layout branchs where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching layout branchs that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutBranch.class.getName(), _FILTER_COLUMN_PK,
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
	 * Counts all the layout branchs where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the number of matching layout branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the layout branchs.
	 *
	 * @return the number of layout branchs
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

				Query q = session.createQuery(_SQL_COUNT_LAYOUTBRANCH);

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
	 * Initializes the layout branch persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutBranch")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutBranch>> listenersList = new ArrayList<ModelListener<LayoutBranch>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutBranch>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LayoutBranchImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_LAYOUTBRANCH = "SELECT layoutBranch FROM LayoutBranch layoutBranch";
	private static final String _SQL_SELECT_LAYOUTBRANCH_WHERE = "SELECT layoutBranch FROM LayoutBranch layoutBranch WHERE ";
	private static final String _SQL_COUNT_LAYOUTBRANCH = "SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch";
	private static final String _SQL_COUNT_LAYOUTBRANCH_WHERE = "SELECT COUNT(layoutBranch) FROM LayoutBranch layoutBranch WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutBranch.groupId = ?";
	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "layoutBranch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "layoutBranch.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "layoutBranch.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(layoutBranch.name IS NULL OR layoutBranch.name = ?)";
	private static final String _FILTER_SQL_SELECT_LAYOUTBRANCH_WHERE = "SELECT DISTINCT {layoutBranch.*} FROM LayoutBranch layoutBranch WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTBRANCH_NO_INLINE_DISTINCT_WHERE =
		"SELECT {layoutBranch.*} FROM (SELECT DISTINCT layoutBranchId FROM LayoutBranch) layoutBranch2 INNER JOIN LayoutBranch layoutBranch ON (layoutBranch2.layoutBranchId = layoutBranch.layoutBranchId) WHERE ";
	private static final String _FILTER_SQL_COUNT_LAYOUTBRANCH_WHERE = "SELECT COUNT(DISTINCT layoutBranch.layoutBranchId) AS COUNT_VALUE FROM LayoutBranch layoutBranch WHERE ";
	private static final String _FILTER_COLUMN_PK = "layoutBranch.layoutBranchId";
	private static final String _FILTER_COLUMN_USERID = "layoutBranch.userId";
	private static final String _FILTER_ENTITY_ALIAS = "layoutBranch";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutBranch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutBranch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutBranch exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LayoutBranchPersistenceImpl.class);
}