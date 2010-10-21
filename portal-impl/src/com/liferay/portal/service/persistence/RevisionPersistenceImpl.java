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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchRevisionException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Revision;
import com.liferay.portal.model.impl.RevisionImpl;
import com.liferay.portal.model.impl.RevisionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the revision service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link RevisionUtil} to access the revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RevisionPersistence
 * @see RevisionUtil
 * @generated
 */
public class RevisionPersistenceImpl extends BasePersistenceImpl<Revision>
	implements RevisionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = RevisionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_BRANCHID = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByBranchId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_BRANCHID = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByBranchId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PLID = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByPlid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByPlid", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_B_P = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByB_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_B_P = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByB_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_B_P_H = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByB_P_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_B_P_H = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByB_P_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_B_P_S = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByB_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_B_P_S = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByB_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the revision in the entity cache if it is enabled.
	 *
	 * @param revision the revision to cache
	 */
	public void cacheResult(Revision revision) {
		EntityCacheUtil.putResult(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionImpl.class, revision.getPrimaryKey(), revision);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
			new Object[] {
				new Long(revision.getBranchId()), new Long(revision.getPlid()),
				Boolean.valueOf(revision.getHead())
			}, revision);
	}

	/**
	 * Caches the revisions in the entity cache if it is enabled.
	 *
	 * @param revisions the revisions to cache
	 */
	public void cacheResult(List<Revision> revisions) {
		for (Revision revision : revisions) {
			if (EntityCacheUtil.getResult(
						RevisionModelImpl.ENTITY_CACHE_ENABLED,
						RevisionImpl.class, revision.getPrimaryKey(), this) == null) {
				cacheResult(revision);
			}
		}
	}

	/**
	 * Clears the cache for all revisions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(RevisionImpl.class.getName());
		EntityCacheUtil.clearCache(RevisionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the revision.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(Revision revision) {
		EntityCacheUtil.removeResult(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionImpl.class, revision.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_B_P_H,
			new Object[] {
				new Long(revision.getBranchId()), new Long(revision.getPlid()),
				Boolean.valueOf(revision.getHead())
			});
	}

	/**
	 * Creates a new revision with the primary key. Does not add the revision to the database.
	 *
	 * @param revisionId the primary key for the new revision
	 * @return the new revision
	 */
	public Revision create(long revisionId) {
		Revision revision = new RevisionImpl();

		revision.setNew(true);
		revision.setPrimaryKey(revisionId);

		return revision;
	}

	/**
	 * Removes the revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the revision to remove
	 * @return the revision that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param revisionId the primary key of the revision to remove
	 * @return the revision that was removed
	 * @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision remove(long revisionId)
		throws NoSuchRevisionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Revision revision = (Revision)session.get(RevisionImpl.class,
					new Long(revisionId));

			if (revision == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + revisionId);
				}

				throw new NoSuchRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					revisionId);
			}

			return remove(revision);
		}
		catch (NoSuchRevisionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Revision removeImpl(Revision revision) throws SystemException {
		revision = toUnwrappedModel(revision);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, revision);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		RevisionModelImpl revisionModelImpl = (RevisionModelImpl)revision;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_B_P_H,
			new Object[] {
				new Long(revisionModelImpl.getOriginalBranchId()),
				new Long(revisionModelImpl.getOriginalPlid()),
				Boolean.valueOf(revisionModelImpl.getOriginalHead())
			});

		EntityCacheUtil.removeResult(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionImpl.class, revision.getPrimaryKey());

		return revision;
	}

	public Revision updateImpl(com.liferay.portal.model.Revision revision,
		boolean merge) throws SystemException {
		revision = toUnwrappedModel(revision);

		boolean isNew = revision.isNew();

		RevisionModelImpl revisionModelImpl = (RevisionModelImpl)revision;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, revision, merge);

			revision.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(RevisionModelImpl.ENTITY_CACHE_ENABLED,
			RevisionImpl.class, revision.getPrimaryKey(), revision);

		if (!isNew &&
				((revision.getBranchId() != revisionModelImpl.getOriginalBranchId()) ||
				(revision.getPlid() != revisionModelImpl.getOriginalPlid()) ||
				(revision.getHead() != revisionModelImpl.getOriginalHead()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_B_P_H,
				new Object[] {
					new Long(revisionModelImpl.getOriginalBranchId()),
					new Long(revisionModelImpl.getOriginalPlid()),
					Boolean.valueOf(revisionModelImpl.getOriginalHead())
				});
		}

		if (isNew ||
				((revision.getBranchId() != revisionModelImpl.getOriginalBranchId()) ||
				(revision.getPlid() != revisionModelImpl.getOriginalPlid()) ||
				(revision.getHead() != revisionModelImpl.getOriginalHead()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
				new Object[] {
					new Long(revision.getBranchId()),
					new Long(revision.getPlid()),
					Boolean.valueOf(revision.getHead())
				}, revision);
		}

		return revision;
	}

	protected Revision toUnwrappedModel(Revision revision) {
		if (revision instanceof RevisionImpl) {
			return revision;
		}

		RevisionImpl revisionImpl = new RevisionImpl();

		revisionImpl.setNew(revision.isNew());
		revisionImpl.setPrimaryKey(revision.getPrimaryKey());

		revisionImpl.setRevisionId(revision.getRevisionId());
		revisionImpl.setGroupId(revision.getGroupId());
		revisionImpl.setCompanyId(revision.getCompanyId());
		revisionImpl.setUserId(revision.getUserId());
		revisionImpl.setUserName(revision.getUserName());
		revisionImpl.setCreateDate(revision.getCreateDate());
		revisionImpl.setModifiedDate(revision.getModifiedDate());
		revisionImpl.setBranchId(revision.getBranchId());
		revisionImpl.setPlid(revision.getPlid());
		revisionImpl.setParentRevisionId(revision.getParentRevisionId());
		revisionImpl.setHead(revision.isHead());
		revisionImpl.setName(revision.getName());
		revisionImpl.setTitle(revision.getTitle());
		revisionImpl.setDescription(revision.getDescription());
		revisionImpl.setTypeSettings(revision.getTypeSettings());
		revisionImpl.setIconImage(revision.isIconImage());
		revisionImpl.setIconImageId(revision.getIconImageId());
		revisionImpl.setThemeId(revision.getThemeId());
		revisionImpl.setColorSchemeId(revision.getColorSchemeId());
		revisionImpl.setWapThemeId(revision.getWapThemeId());
		revisionImpl.setWapColorSchemeId(revision.getWapColorSchemeId());
		revisionImpl.setCss(revision.getCss());
		revisionImpl.setStatus(revision.getStatus());
		revisionImpl.setStatusByUserId(revision.getStatusByUserId());
		revisionImpl.setStatusByUserName(revision.getStatusByUserName());
		revisionImpl.setStatusDate(revision.getStatusDate());

		return revisionImpl;
	}

	/**
	 * Finds the revision with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the revision to find
	 * @return the revision
	 * @throws com.liferay.portal.NoSuchModelException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the revision with the primary key or throws a {@link com.liferay.portal.NoSuchRevisionException} if it could not be found.
	 *
	 * @param revisionId the primary key of the revision to find
	 * @return the revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByPrimaryKey(long revisionId)
		throws NoSuchRevisionException, SystemException {
		Revision revision = fetchByPrimaryKey(revisionId);

		if (revision == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + revisionId);
			}

			throw new NoSuchRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				revisionId);
		}

		return revision;
	}

	/**
	 * Finds the revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the revision to find
	 * @return the revision, or <code>null</code> if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param revisionId the primary key of the revision to find
	 * @return the revision, or <code>null</code> if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision fetchByPrimaryKey(long revisionId)
		throws SystemException {
		Revision revision = (Revision)EntityCacheUtil.getResult(RevisionModelImpl.ENTITY_CACHE_ENABLED,
				RevisionImpl.class, revisionId, this);

		if (revision == null) {
			Session session = null;

			try {
				session = openSession();

				revision = (Revision)session.get(RevisionImpl.class,
						new Long(revisionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (revision != null) {
					cacheResult(revision);
				}

				closeSession(session);
			}
		}

		return revision;
	}

	/**
	 * Finds all the revisions where branchId = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @return the matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByBranchId(long branchId)
		throws SystemException {
		return findByBranchId(branchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the revisions where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @return the range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByBranchId(long branchId, int start, int end)
		throws SystemException {
		return findByBranchId(branchId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the revisions where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByBranchId(long branchId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				branchId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Revision> list = (List<Revision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_BRANCHID,
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

			query.append(_SQL_SELECT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_BRANCHID_BRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				list = (List<Revision>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Revision>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_BRANCHID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first revision in the ordered set where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByBranchId_First(long branchId,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		List<Revision> list = findByBranchId(branchId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last revision in the ordered set where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByBranchId_Last(long branchId,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		int count = countByBranchId(branchId);

		List<Revision> list = findByBranchId(branchId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the revisions before and after the current revision in the ordered set where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current revision
	 * @param branchId the branch id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision[] findByBranchId_PrevAndNext(long revisionId,
		long branchId, OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		Revision revision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			Revision[] array = new RevisionImpl[3];

			array[0] = getByBranchId_PrevAndNext(session, revision, branchId,
					orderByComparator, true);

			array[1] = revision;

			array[2] = getByBranchId_PrevAndNext(session, revision, branchId,
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

	protected Revision getByBranchId_PrevAndNext(Session session,
		Revision revision, long branchId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REVISION_WHERE);

		query.append(_FINDER_COLUMN_BRANCHID_BRANCHID_2);

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
			query.append(RevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(branchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(revision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Revision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the revisions where plid = &#63;.
	 *
	 * @param plid the plid to search with
	 * @return the matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByPlid(long plid) throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @return the range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByPlid(long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				plid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Revision> list = (List<Revision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PLID,
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

			query.append(_SQL_SELECT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<Revision>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Revision>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PLID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first revision in the ordered set where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		List<Revision> list = findByPlid(plid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last revision in the ordered set where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		int count = countByPlid(plid);

		List<Revision> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the revisions before and after the current revision in the ordered set where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current revision
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision[] findByPlid_PrevAndNext(long revisionId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		Revision revision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			Revision[] array = new RevisionImpl[3];

			array[0] = getByPlid_PrevAndNext(session, revision, plid,
					orderByComparator, true);

			array[1] = revision;

			array[2] = getByPlid_PrevAndNext(session, revision, plid,
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

	protected Revision getByPlid_PrevAndNext(Session session,
		Revision revision, long plid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REVISION_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

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
			query.append(RevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(revision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Revision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the revisions where branchId = &#63; and plid = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @return the matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByB_P(long branchId, long plid)
		throws SystemException {
		return findByB_P(branchId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the revisions where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @return the range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByB_P(long branchId, long plid, int start, int end)
		throws SystemException {
		return findByB_P(branchId, plid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the revisions where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByB_P(long branchId, long plid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				branchId, plid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Revision> list = (List<Revision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_B_P,
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

			query.append(_SQL_SELECT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				list = (List<Revision>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Revision>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_B_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first revision in the ordered set where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByB_P_First(long branchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		List<Revision> list = findByB_P(branchId, plid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last revision in the ordered set where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByB_P_Last(long branchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		int count = countByB_P(branchId, plid);

		List<Revision> list = findByB_P(branchId, plid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the revisions before and after the current revision in the ordered set where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current revision
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision[] findByB_P_PrevAndNext(long revisionId, long branchId,
		long plid, OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		Revision revision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			Revision[] array = new RevisionImpl[3];

			array[0] = getByB_P_PrevAndNext(session, revision, branchId, plid,
					orderByComparator, true);

			array[1] = revision;

			array[2] = getByB_P_PrevAndNext(session, revision, branchId, plid,
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

	protected Revision getByB_P_PrevAndNext(Session session, Revision revision,
		long branchId, long plid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REVISION_WHERE);

		query.append(_FINDER_COLUMN_B_P_BRANCHID_2);

		query.append(_FINDER_COLUMN_B_P_PLID_2);

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
			query.append(RevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(branchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(revision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Revision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the revision where branchId = &#63; and plid = &#63; and head = &#63; or throws a {@link com.liferay.portal.NoSuchRevisionException} if it could not be found.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByB_P_H(long branchId, long plid, boolean head)
		throws NoSuchRevisionException, SystemException {
		Revision revision = fetchByB_P_H(branchId, plid, head);

		if (revision == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", head=");
			msg.append(head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRevisionException(msg.toString());
		}

		return revision;
	}

	/**
	 * Finds the revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the matching revision, or <code>null</code> if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision fetchByB_P_H(long branchId, long plid, boolean head)
		throws SystemException {
		return fetchByB_P_H(branchId, plid, head, true);
	}

	/**
	 * Finds the revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the matching revision, or <code>null</code> if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision fetchByB_P_H(long branchId, long plid, boolean head,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid, head };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_B_P_H,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_H_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_H_PLID_2);

			query.append(_FINDER_COLUMN_B_P_H_HEAD_2);

			query.append(RevisionModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				qPos.add(head);

				List<Revision> list = q.list();

				result = list;

				Revision revision = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
						finderArgs, list);
				}
				else {
					revision = list.get(0);

					cacheResult(revision);

					if ((revision.getBranchId() != branchId) ||
							(revision.getPlid() != plid) ||
							(revision.getHead() != head)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
							finderArgs, revision);
					}
				}

				return revision;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
						finderArgs, new ArrayList<Revision>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Revision)result;
			}
		}
	}

	/**
	 * Finds all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @return the matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByB_P_S(long branchId, long plid, int status)
		throws SystemException {
		return findByB_P_S(branchId, plid, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @return the range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByB_P_S(long branchId, long plid, int status,
		int start, int end) throws SystemException {
		return findByB_P_S(branchId, plid, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findByB_P_S(long branchId, long plid, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				branchId, plid, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Revision> list = (List<Revision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_B_P_S,
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

			query.append(_SQL_SELECT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_S_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_S_PLID_2);

			query.append(_FINDER_COLUMN_B_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				qPos.add(status);

				list = (List<Revision>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Revision>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_B_P_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByB_P_S_First(long branchId, long plid, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		List<Revision> list = findByB_P_S(branchId, plid, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision findByB_P_S_Last(long branchId, long plid, int status,
		OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		int count = countByB_P_S(branchId, plid, status);

		List<Revision> list = findByB_P_S(branchId, plid, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the revisions before and after the current revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current revision
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next revision
	 * @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Revision[] findByB_P_S_PrevAndNext(long revisionId, long branchId,
		long plid, int status, OrderByComparator orderByComparator)
		throws NoSuchRevisionException, SystemException {
		Revision revision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			Revision[] array = new RevisionImpl[3];

			array[0] = getByB_P_S_PrevAndNext(session, revision, branchId,
					plid, status, orderByComparator, true);

			array[1] = revision;

			array[2] = getByB_P_S_PrevAndNext(session, revision, branchId,
					plid, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Revision getByB_P_S_PrevAndNext(Session session,
		Revision revision, long branchId, long plid, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REVISION_WHERE);

		query.append(_FINDER_COLUMN_B_P_S_BRANCHID_2);

		query.append(_FINDER_COLUMN_B_P_S_PLID_2);

		query.append(_FINDER_COLUMN_B_P_S_STATUS_2);

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
			query.append(RevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(branchId);

		qPos.add(plid);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(revision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Revision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the revisions.
	 *
	 * @return the revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @return the range of revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of revisions to return
	 * @param end the upper bound of the range of revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Revision> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Revision> list = (List<Revision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_REVISION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REVISION.concat(RevisionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Revision>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Revision>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Revision>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the revisions where branchId = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByBranchId(long branchId) throws SystemException {
		for (Revision revision : findByBranchId(branchId)) {
			remove(revision);
		}
	}

	/**
	 * Removes all the revisions where plid = &#63; from the database.
	 *
	 * @param plid the plid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPlid(long plid) throws SystemException {
		for (Revision revision : findByPlid(plid)) {
			remove(revision);
		}
	}

	/**
	 * Removes all the revisions where branchId = &#63; and plid = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByB_P(long branchId, long plid) throws SystemException {
		for (Revision revision : findByB_P(branchId, plid)) {
			remove(revision);
		}
	}

	/**
	 * Removes the revision where branchId = &#63; and plid = &#63; and head = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByB_P_H(long branchId, long plid, boolean head)
		throws NoSuchRevisionException, SystemException {
		Revision revision = findByB_P_H(branchId, plid, head);

		remove(revision);
	}

	/**
	 * Removes all the revisions where branchId = &#63; and plid = &#63; and status = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByB_P_S(long branchId, long plid, int status)
		throws SystemException {
		for (Revision revision : findByB_P_S(branchId, plid, status)) {
			remove(revision);
		}
	}

	/**
	 * Removes all the revisions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Revision revision : findAll()) {
			remove(revision);
		}
	}

	/**
	 * Counts all the revisions where branchId = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @return the number of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByBranchId(long branchId) throws SystemException {
		Object[] finderArgs = new Object[] { branchId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_BRANCHID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_BRANCHID_BRANCHID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BRANCHID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the revisions where plid = &#63;.
	 *
	 * @param plid the plid to search with
	 * @return the number of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPlid(long plid) throws SystemException {
		Object[] finderArgs = new Object[] { plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PLID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PLID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the revisions where branchId = &#63; and plid = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @return the number of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByB_P(long branchId, long plid) throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_B_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_B_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the revisions where branchId = &#63; and plid = &#63; and head = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the number of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByB_P_H(long branchId, long plid, boolean head)
		throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid, head };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_B_P_H,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_H_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_H_PLID_2);

			query.append(_FINDER_COLUMN_B_P_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				qPos.add(head);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_B_P_H,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @return the number of matching revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByB_P_S(long branchId, long plid, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_B_P_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_S_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_S_PLID_2);

			query.append(_FINDER_COLUMN_B_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_B_P_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the revisions.
	 *
	 * @return the number of revisions
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

				Query q = session.createQuery(_SQL_COUNT_REVISION);

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
	 * Initializes the revision persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Revision")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Revision>> listenersList = new ArrayList<ModelListener<Revision>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Revision>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(RevisionImpl.class.getName());
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
	private static final String _SQL_SELECT_REVISION = "SELECT revision FROM Revision revision";
	private static final String _SQL_SELECT_REVISION_WHERE = "SELECT revision FROM Revision revision WHERE ";
	private static final String _SQL_COUNT_REVISION = "SELECT COUNT(revision) FROM Revision revision";
	private static final String _SQL_COUNT_REVISION_WHERE = "SELECT COUNT(revision) FROM Revision revision WHERE ";
	private static final String _FINDER_COLUMN_BRANCHID_BRANCHID_2 = "revision.branchId = ?";
	private static final String _FINDER_COLUMN_PLID_PLID_2 = "revision.plid = ?";
	private static final String _FINDER_COLUMN_B_P_BRANCHID_2 = "revision.branchId = ? AND ";
	private static final String _FINDER_COLUMN_B_P_PLID_2 = "revision.plid = ?";
	private static final String _FINDER_COLUMN_B_P_H_BRANCHID_2 = "revision.branchId = ? AND ";
	private static final String _FINDER_COLUMN_B_P_H_PLID_2 = "revision.plid = ? AND ";
	private static final String _FINDER_COLUMN_B_P_H_HEAD_2 = "revision.head = ?";
	private static final String _FINDER_COLUMN_B_P_S_BRANCHID_2 = "revision.branchId = ? AND ";
	private static final String _FINDER_COLUMN_B_P_S_PLID_2 = "revision.plid = ? AND ";
	private static final String _FINDER_COLUMN_B_P_S_STATUS_2 = "revision.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "revision.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Revision exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Revision exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(RevisionPersistenceImpl.class);
}