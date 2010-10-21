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

import com.liferay.portal.NoSuchBranchException;
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
import com.liferay.portal.model.Branch;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.BranchImpl;
import com.liferay.portal.model.impl.BranchModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the branch service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link BranchUtil} to access the branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BranchPersistence
 * @see BranchUtil
 * @generated
 */
public class BranchPersistenceImpl extends BasePersistenceImpl<Branch>
	implements BranchPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_G = new FinderPath(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G = new FinderPath(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the branch in the entity cache if it is enabled.
	 *
	 * @param branch the branch to cache
	 */
	public void cacheResult(Branch branch) {
		EntityCacheUtil.putResult(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchImpl.class, branch.getPrimaryKey(), branch);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] { new Long(branch.getGroupId()), branch.getName() },
			branch);
	}

	/**
	 * Caches the branchs in the entity cache if it is enabled.
	 *
	 * @param branchs the branchs to cache
	 */
	public void cacheResult(List<Branch> branchs) {
		for (Branch branch : branchs) {
			if (EntityCacheUtil.getResult(
						BranchModelImpl.ENTITY_CACHE_ENABLED, BranchImpl.class,
						branch.getPrimaryKey(), this) == null) {
				cacheResult(branch);
			}
		}
	}

	/**
	 * Clears the cache for all branchs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(BranchImpl.class.getName());
		EntityCacheUtil.clearCache(BranchImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the branch.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(Branch branch) {
		EntityCacheUtil.removeResult(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchImpl.class, branch.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] { new Long(branch.getGroupId()), branch.getName() });
	}

	/**
	 * Creates a new branch with the primary key. Does not add the branch to the database.
	 *
	 * @param branchId the primary key for the new branch
	 * @return the new branch
	 */
	public Branch create(long branchId) {
		Branch branch = new BranchImpl();

		branch.setNew(true);
		branch.setPrimaryKey(branchId);

		return branch;
	}

	/**
	 * Removes the branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the branch to remove
	 * @return the branch that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param branchId the primary key of the branch to remove
	 * @return the branch that was removed
	 * @throws com.liferay.portal.NoSuchBranchException if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch remove(long branchId)
		throws NoSuchBranchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Branch branch = (Branch)session.get(BranchImpl.class,
					new Long(branchId));

			if (branch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + branchId);
				}

				throw new NoSuchBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					branchId);
			}

			return remove(branch);
		}
		catch (NoSuchBranchException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Branch removeImpl(Branch branch) throws SystemException {
		branch = toUnwrappedModel(branch);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, branch);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BranchModelImpl branchModelImpl = (BranchModelImpl)branch;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(branchModelImpl.getOriginalGroupId()),
				
			branchModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchImpl.class, branch.getPrimaryKey());

		return branch;
	}

	public Branch updateImpl(com.liferay.portal.model.Branch branch,
		boolean merge) throws SystemException {
		branch = toUnwrappedModel(branch);

		boolean isNew = branch.isNew();

		BranchModelImpl branchModelImpl = (BranchModelImpl)branch;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, branch, merge);

			branch.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BranchModelImpl.ENTITY_CACHE_ENABLED,
			BranchImpl.class, branch.getPrimaryKey(), branch);

		if (!isNew &&
				((branch.getGroupId() != branchModelImpl.getOriginalGroupId()) ||
				!Validator.equals(branch.getName(),
					branchModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(branchModelImpl.getOriginalGroupId()),
					
				branchModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((branch.getGroupId() != branchModelImpl.getOriginalGroupId()) ||
				!Validator.equals(branch.getName(),
					branchModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] { new Long(branch.getGroupId()), branch.getName() },
				branch);
		}

		return branch;
	}

	protected Branch toUnwrappedModel(Branch branch) {
		if (branch instanceof BranchImpl) {
			return branch;
		}

		BranchImpl branchImpl = new BranchImpl();

		branchImpl.setNew(branch.isNew());
		branchImpl.setPrimaryKey(branch.getPrimaryKey());

		branchImpl.setBranchId(branch.getBranchId());
		branchImpl.setGroupId(branch.getGroupId());
		branchImpl.setCompanyId(branch.getCompanyId());
		branchImpl.setUserId(branch.getUserId());
		branchImpl.setUserName(branch.getUserName());
		branchImpl.setCreateDate(branch.getCreateDate());
		branchImpl.setModifiedDate(branch.getModifiedDate());
		branchImpl.setName(branch.getName());
		branchImpl.setDescription(branch.getDescription());

		return branchImpl;
	}

	/**
	 * Finds the branch with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the branch to find
	 * @return the branch
	 * @throws com.liferay.portal.NoSuchModelException if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the branch with the primary key or throws a {@link com.liferay.portal.NoSuchBranchException} if it could not be found.
	 *
	 * @param branchId the primary key of the branch to find
	 * @return the branch
	 * @throws com.liferay.portal.NoSuchBranchException if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch findByPrimaryKey(long branchId)
		throws NoSuchBranchException, SystemException {
		Branch branch = fetchByPrimaryKey(branchId);

		if (branch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + branchId);
			}

			throw new NoSuchBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				branchId);
		}

		return branch;
	}

	/**
	 * Finds the branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the branch to find
	 * @return the branch, or <code>null</code> if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param branchId the primary key of the branch to find
	 * @return the branch, or <code>null</code> if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch fetchByPrimaryKey(long branchId) throws SystemException {
		Branch branch = (Branch)EntityCacheUtil.getResult(BranchModelImpl.ENTITY_CACHE_ENABLED,
				BranchImpl.class, branchId, this);

		if (branch == null) {
			Session session = null;

			try {
				session = openSession();

				branch = (Branch)session.get(BranchImpl.class,
						new Long(branchId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (branch != null) {
					cacheResult(branch);
				}

				closeSession(session);
			}
		}

		return branch;
	}

	/**
	 * Finds all the branchs where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<Branch> findByG(long groupId) throws SystemException {
		return findByG(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of branchs to return
	 * @param end the upper bound of the range of branchs to return (not inclusive)
	 * @return the range of matching branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<Branch> findByG(long groupId, int start, int end)
		throws SystemException {
		return findByG(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of branchs to return
	 * @param end the upper bound of the range of branchs to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<Branch> findByG(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Branch> list = (List<Branch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G,
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

			query.append(_SQL_SELECT_BRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<Branch>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Branch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first branch in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching branch
	 * @throws com.liferay.portal.NoSuchBranchException if a matching branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch findByG_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchBranchException, SystemException {
		List<Branch> list = findByG(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last branch in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching branch
	 * @throws com.liferay.portal.NoSuchBranchException if a matching branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch findByG_Last(long groupId, OrderByComparator orderByComparator)
		throws NoSuchBranchException, SystemException {
		int count = countByG(groupId);

		List<Branch> list = findByG(groupId, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBranchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the branchs before and after the current branch in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the primary key of the current branch
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next branch
	 * @throws com.liferay.portal.NoSuchBranchException if a branch with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch[] findByG_PrevAndNext(long branchId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchBranchException, SystemException {
		Branch branch = findByPrimaryKey(branchId);

		Session session = null;

		try {
			session = openSession();

			Branch[] array = new BranchImpl[3];

			array[0] = getByG_PrevAndNext(session, branch, groupId,
					orderByComparator, true);

			array[1] = branch;

			array[2] = getByG_PrevAndNext(session, branch, groupId,
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

	protected Branch getByG_PrevAndNext(Session session, Branch branch,
		long groupId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BRANCH_WHERE);

		query.append(_FINDER_COLUMN_G_GROUPID_2);

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
			query.append(BranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(branch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Branch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the branch where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchBranchException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the matching branch
	 * @throws com.liferay.portal.NoSuchBranchException if a matching branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch findByG_N(long groupId, String name)
		throws NoSuchBranchException, SystemException {
		Branch branch = fetchByG_N(groupId, name);

		if (branch == null) {
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

			throw new NoSuchBranchException(msg.toString());
		}

		return branch;
	}

	/**
	 * Finds the branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the matching branch, or <code>null</code> if a matching branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Finds the branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the matching branch, or <code>null</code> if a matching branch could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Branch fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BRANCH_WHERE);

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

			query.append(BranchModelImpl.ORDER_BY_JPQL);

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

				List<Branch> list = q.list();

				result = list;

				Branch branch = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					branch = list.get(0);

					cacheResult(branch);

					if ((branch.getGroupId() != groupId) ||
							(branch.getName() == null) ||
							!branch.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, branch);
					}
				}

				return branch;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, new ArrayList<Branch>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Branch)result;
			}
		}
	}

	/**
	 * Finds all the branchs.
	 *
	 * @return the branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<Branch> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of branchs to return
	 * @param end the upper bound of the range of branchs to return (not inclusive)
	 * @return the range of branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<Branch> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of branchs to return
	 * @param end the upper bound of the range of branchs to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of branchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<Branch> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Branch> list = (List<Branch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BRANCH.concat(BranchModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Branch>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Branch>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Branch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the branchs where groupId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG(long groupId) throws SystemException {
		for (Branch branch : findByG(groupId)) {
			remove(branch);
		}
	}

	/**
	 * Removes the branch where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_N(long groupId, String name)
		throws NoSuchBranchException, SystemException {
		Branch branch = findByG_N(groupId, name);

		remove(branch);
	}

	/**
	 * Removes all the branchs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Branch branch : findAll()) {
			remove(branch);
		}
	}

	/**
	 * Counts all the branchs where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_GROUPID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the branchs where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param name the name to search with
	 * @return the number of matching branchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BRANCH_WHERE);

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
	 * Counts all the branchs.
	 *
	 * @return the number of branchs
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

				Query q = session.createQuery(_SQL_COUNT_BRANCH);

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
	 * Initializes the branch persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Branch")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Branch>> listenersList = new ArrayList<ModelListener<Branch>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Branch>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(BranchImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BranchPersistence.class)
	protected BranchPersistence branchPersistence;
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
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
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
	@BeanReference(type = RevisionPersistence.class)
	protected RevisionPersistence revisionPersistence;
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
	private static final String _SQL_SELECT_BRANCH = "SELECT branch FROM Branch branch";
	private static final String _SQL_SELECT_BRANCH_WHERE = "SELECT branch FROM Branch branch WHERE ";
	private static final String _SQL_COUNT_BRANCH = "SELECT COUNT(branch) FROM Branch branch";
	private static final String _SQL_COUNT_BRANCH_WHERE = "SELECT COUNT(branch) FROM Branch branch WHERE ";
	private static final String _FINDER_COLUMN_G_GROUPID_2 = "branch.groupId = ?";
	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "branch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "branch.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "branch.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(branch.name IS NULL OR branch.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "branch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Branch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Branch exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(BranchPersistenceImpl.class);
}