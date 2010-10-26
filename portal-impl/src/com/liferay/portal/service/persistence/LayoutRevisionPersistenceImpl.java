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

import com.liferay.portal.NoSuchLayoutRevisionException;
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
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutRevisionImpl;
import com.liferay.portal.model.impl.LayoutRevisionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the layout revision service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link LayoutRevisionUtil} to access the layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistence
 * @see LayoutRevisionUtil
 * @generated
 */
public class LayoutRevisionPersistenceImpl extends BasePersistenceImpl<LayoutRevision>
	implements LayoutRevisionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutRevisionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_BRANCHID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByBranchId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_BRANCHID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByBranchId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PLID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByPlid",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_B_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByB_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_B_P = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByB_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_B_P_H = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByB_P_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_B_P_H = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByB_P_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_B_P_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByB_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_B_P_S = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByB_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the layout revision in the entity cache if it is enabled.
	 *
	 * @param layoutRevision the layout revision to cache
	 */
	public void cacheResult(LayoutRevision layoutRevision) {
		EntityCacheUtil.putResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey(),
			layoutRevision);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
			new Object[] {
				new Long(layoutRevision.getBranchId()),
				new Long(layoutRevision.getPlid()),
				Boolean.valueOf(layoutRevision.getHead())
			}, layoutRevision);
	}

	/**
	 * Caches the layout revisions in the entity cache if it is enabled.
	 *
	 * @param layoutRevisions the layout revisions to cache
	 */
	public void cacheResult(List<LayoutRevision> layoutRevisions) {
		for (LayoutRevision layoutRevision : layoutRevisions) {
			if (EntityCacheUtil.getResult(
						LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutRevisionImpl.class,
						layoutRevision.getPrimaryKey(), this) == null) {
				cacheResult(layoutRevision);
			}
		}
	}

	/**
	 * Clears the cache for all layout revisions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(LayoutRevisionImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutRevisionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the layout revision.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(LayoutRevision layoutRevision) {
		EntityCacheUtil.removeResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_B_P_H,
			new Object[] {
				new Long(layoutRevision.getBranchId()),
				new Long(layoutRevision.getPlid()),
				Boolean.valueOf(layoutRevision.getHead())
			});
	}

	/**
	 * Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	 *
	 * @param revisionId the primary key for the new layout revision
	 * @return the new layout revision
	 */
	public LayoutRevision create(long revisionId) {
		LayoutRevision layoutRevision = new LayoutRevisionImpl();

		layoutRevision.setNew(true);
		layoutRevision.setPrimaryKey(revisionId);

		return layoutRevision;
	}

	/**
	 * Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout revision to remove
	 * @return the layout revision that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param revisionId the primary key of the layout revision to remove
	 * @return the layout revision that was removed
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision remove(long revisionId)
		throws NoSuchLayoutRevisionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutRevision layoutRevision = (LayoutRevision)session.get(LayoutRevisionImpl.class,
					new Long(revisionId));

			if (layoutRevision == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + revisionId);
				}

				throw new NoSuchLayoutRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					revisionId);
			}

			return remove(layoutRevision);
		}
		catch (NoSuchLayoutRevisionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision removeImpl(LayoutRevision layoutRevision)
		throws SystemException {
		layoutRevision = toUnwrappedModel(layoutRevision);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, layoutRevision);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LayoutRevisionModelImpl layoutRevisionModelImpl = (LayoutRevisionModelImpl)layoutRevision;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_B_P_H,
			new Object[] {
				new Long(layoutRevisionModelImpl.getOriginalBranchId()),
				new Long(layoutRevisionModelImpl.getOriginalPlid()),
				Boolean.valueOf(layoutRevisionModelImpl.getOriginalHead())
			});

		EntityCacheUtil.removeResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey());

		return layoutRevision;
	}

	public LayoutRevision updateImpl(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws SystemException {
		layoutRevision = toUnwrappedModel(layoutRevision);

		boolean isNew = layoutRevision.isNew();

		LayoutRevisionModelImpl layoutRevisionModelImpl = (LayoutRevisionModelImpl)layoutRevision;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutRevision, merge);

			layoutRevision.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey(),
			layoutRevision);

		if (!isNew &&
				((layoutRevision.getBranchId() != layoutRevisionModelImpl.getOriginalBranchId()) ||
				(layoutRevision.getPlid() != layoutRevisionModelImpl.getOriginalPlid()) ||
				(layoutRevision.getHead() != layoutRevisionModelImpl.getOriginalHead()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_B_P_H,
				new Object[] {
					new Long(layoutRevisionModelImpl.getOriginalBranchId()),
					new Long(layoutRevisionModelImpl.getOriginalPlid()),
					Boolean.valueOf(layoutRevisionModelImpl.getOriginalHead())
				});
		}

		if (isNew ||
				((layoutRevision.getBranchId() != layoutRevisionModelImpl.getOriginalBranchId()) ||
				(layoutRevision.getPlid() != layoutRevisionModelImpl.getOriginalPlid()) ||
				(layoutRevision.getHead() != layoutRevisionModelImpl.getOriginalHead()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
				new Object[] {
					new Long(layoutRevision.getBranchId()),
					new Long(layoutRevision.getPlid()),
					Boolean.valueOf(layoutRevision.getHead())
				}, layoutRevision);
		}

		return layoutRevision;
	}

	protected LayoutRevision toUnwrappedModel(LayoutRevision layoutRevision) {
		if (layoutRevision instanceof LayoutRevisionImpl) {
			return layoutRevision;
		}

		LayoutRevisionImpl layoutRevisionImpl = new LayoutRevisionImpl();

		layoutRevisionImpl.setNew(layoutRevision.isNew());
		layoutRevisionImpl.setPrimaryKey(layoutRevision.getPrimaryKey());

		layoutRevisionImpl.setRevisionId(layoutRevision.getRevisionId());
		layoutRevisionImpl.setGroupId(layoutRevision.getGroupId());
		layoutRevisionImpl.setCompanyId(layoutRevision.getCompanyId());
		layoutRevisionImpl.setUserId(layoutRevision.getUserId());
		layoutRevisionImpl.setUserName(layoutRevision.getUserName());
		layoutRevisionImpl.setCreateDate(layoutRevision.getCreateDate());
		layoutRevisionImpl.setModifiedDate(layoutRevision.getModifiedDate());
		layoutRevisionImpl.setBranchId(layoutRevision.getBranchId());
		layoutRevisionImpl.setPlid(layoutRevision.getPlid());
		layoutRevisionImpl.setParentRevisionId(layoutRevision.getParentRevisionId());
		layoutRevisionImpl.setHead(layoutRevision.isHead());
		layoutRevisionImpl.setName(layoutRevision.getName());
		layoutRevisionImpl.setTitle(layoutRevision.getTitle());
		layoutRevisionImpl.setDescription(layoutRevision.getDescription());
		layoutRevisionImpl.setTypeSettings(layoutRevision.getTypeSettings());
		layoutRevisionImpl.setIconImage(layoutRevision.isIconImage());
		layoutRevisionImpl.setIconImageId(layoutRevision.getIconImageId());
		layoutRevisionImpl.setThemeId(layoutRevision.getThemeId());
		layoutRevisionImpl.setColorSchemeId(layoutRevision.getColorSchemeId());
		layoutRevisionImpl.setWapThemeId(layoutRevision.getWapThemeId());
		layoutRevisionImpl.setWapColorSchemeId(layoutRevision.getWapColorSchemeId());
		layoutRevisionImpl.setCss(layoutRevision.getCss());
		layoutRevisionImpl.setStatus(layoutRevision.getStatus());
		layoutRevisionImpl.setStatusByUserId(layoutRevision.getStatusByUserId());
		layoutRevisionImpl.setStatusByUserName(layoutRevision.getStatusByUserName());
		layoutRevisionImpl.setStatusDate(layoutRevision.getStatusDate());

		return layoutRevisionImpl;
	}

	/**
	 * Finds the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout revision to find
	 * @return the layout revision
	 * @throws com.liferay.portal.NoSuchModelException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	 *
	 * @param revisionId the primary key of the layout revision to find
	 * @return the layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPrimaryKey(long revisionId)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByPrimaryKey(revisionId);

		if (layoutRevision == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + revisionId);
			}

			throw new NoSuchLayoutRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				revisionId);
		}

		return layoutRevision;
	}

	/**
	 * Finds the layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout revision to find
	 * @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param revisionId the primary key of the layout revision to find
	 * @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByPrimaryKey(long revisionId)
		throws SystemException {
		LayoutRevision layoutRevision = (LayoutRevision)EntityCacheUtil.getResult(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutRevisionImpl.class, revisionId, this);

		if (layoutRevision == null) {
			Session session = null;

			try {
				session = openSession();

				layoutRevision = (LayoutRevision)session.get(LayoutRevisionImpl.class,
						new Long(revisionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutRevision != null) {
					cacheResult(layoutRevision);
				}

				closeSession(session);
			}
		}

		return layoutRevision;
	}

	/**
	 * Finds all the layout revisions where branchId = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByBranchId(long branchId)
		throws SystemException {
		return findByBranchId(branchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the layout revisions where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByBranchId(long branchId, int start, int end)
		throws SystemException {
		return findByBranchId(branchId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout revisions where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByBranchId(long branchId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				branchId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_BRANCHID,
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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_BRANCHID_BRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutRevision>();
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
	 * Finds the first layout revision in the ordered set where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByBranchId_First(long branchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		List<LayoutRevision> list = findByBranchId(branchId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last layout revision in the ordered set where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByBranchId_Last(long branchId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		int count = countByBranchId(branchId);

		List<LayoutRevision> list = findByBranchId(branchId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the layout revisions before and after the current layout revision in the ordered set where branchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current layout revision
	 * @param branchId the branch id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByBranchId_PrevAndNext(long revisionId,
		long branchId, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByBranchId_PrevAndNext(session, layoutRevision,
					branchId, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByBranchId_PrevAndNext(session, layoutRevision,
					branchId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByBranchId_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long branchId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(branchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the layout revisions where plid = &#63;.
	 *
	 * @param plid the plid to search with
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByPlid(long plid) throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByPlid(long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				plid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PLID,
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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutRevision>();
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
	 * Finds the first layout revision in the ordered set where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		List<LayoutRevision> list = findByPlid(plid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last layout revision in the ordered set where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		int count = countByPlid(plid);

		List<LayoutRevision> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current layout revision
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByPlid_PrevAndNext(long revisionId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByPlid_PrevAndNext(session, layoutRevision, plid,
					orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByPlid_PrevAndNext(session, layoutRevision, plid,
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

	protected LayoutRevision getByPlid_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the layout revisions where branchId = &#63; and plid = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByB_P(long branchId, long plid)
		throws SystemException {
		return findByB_P(branchId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the layout revisions where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByB_P(long branchId, long plid, int start,
		int end) throws SystemException {
		return findByB_P(branchId, plid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout revisions where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByB_P(long branchId, long plid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				branchId, plid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_B_P,
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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutRevision>();
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
	 * Finds the first layout revision in the ordered set where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByB_P_First(long branchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		List<LayoutRevision> list = findByB_P(branchId, plid, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last layout revision in the ordered set where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByB_P_Last(long branchId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		int count = countByB_P(branchId, plid);

		List<LayoutRevision> list = findByB_P(branchId, plid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("branchId=");
			msg.append(branchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the layout revisions before and after the current layout revision in the ordered set where branchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current layout revision
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByB_P_PrevAndNext(long revisionId,
		long branchId, long plid, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByB_P_PrevAndNext(session, layoutRevision, branchId,
					plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByB_P_PrevAndNext(session, layoutRevision, branchId,
					plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByB_P_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long branchId, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(branchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByB_P_H(long branchId, long plid, boolean head)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = fetchByB_P_H(branchId, plid, head);

		if (layoutRevision == null) {
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

			throw new NoSuchLayoutRevisionException(msg.toString());
		}

		return layoutRevision;
	}

	/**
	 * Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByB_P_H(long branchId, long plid, boolean head)
		throws SystemException {
		return fetchByB_P_H(branchId, plid, head, true);
	}

	/**
	 * Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision fetchByB_P_H(long branchId, long plid, boolean head,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid, head };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_B_P_H,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_H_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_H_PLID_2);

			query.append(_FINDER_COLUMN_B_P_H_HEAD_2);

			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(branchId);

				qPos.add(plid);

				qPos.add(head);

				List<LayoutRevision> list = q.list();

				result = list;

				LayoutRevision layoutRevision = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
						finderArgs, list);
				}
				else {
					layoutRevision = list.get(0);

					cacheResult(layoutRevision);

					if ((layoutRevision.getBranchId() != branchId) ||
							(layoutRevision.getPlid() != plid) ||
							(layoutRevision.getHead() != head)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
							finderArgs, layoutRevision);
					}
				}

				return layoutRevision;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_B_P_H,
						finderArgs, new ArrayList<LayoutRevision>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (LayoutRevision)result;
			}
		}
	}

	/**
	 * Finds all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @return the matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByB_P_S(long branchId, long plid, int status)
		throws SystemException {
		return findByB_P_S(branchId, plid, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @return the range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByB_P_S(long branchId, long plid,
		int status, int start, int end) throws SystemException {
		return findByB_P_S(branchId, plid, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findByB_P_S(long branchId, long plid,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				branchId, plid, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_B_P_S,
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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_B_P_S_BRANCHID_2);

			query.append(_FINDER_COLUMN_B_P_S_PLID_2);

			query.append(_FINDER_COLUMN_B_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutRevision>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutRevision>();
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
	 * Finds the first layout revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByB_P_S_First(long branchId, long plid,
		int status, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		List<LayoutRevision> list = findByB_P_S(branchId, plid, status, 0, 1,
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

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last layout revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision findByB_P_S_Last(long branchId, long plid,
		int status, OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		int count = countByB_P_S(branchId, plid, status);

		List<LayoutRevision> list = findByB_P_S(branchId, plid, status,
				count - 1, count, orderByComparator);

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

			throw new NoSuchLayoutRevisionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the layout revisions before and after the current layout revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param revisionId the primary key of the current layout revision
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next layout revision
	 * @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutRevision[] findByB_P_S_PrevAndNext(long revisionId,
		long branchId, long plid, int status,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByPrimaryKey(revisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByB_P_S_PrevAndNext(session, layoutRevision,
					branchId, plid, status, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByB_P_S_PrevAndNext(session, layoutRevision,
					branchId, plid, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByB_P_S_PrevAndNext(Session session,
		LayoutRevision layoutRevision, long branchId, long plid, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(layoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the layout revisions.
	 *
	 * @return the layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @return the range of layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions to return
	 * @param end the upper bound of the range of layout revisions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutRevision> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutRevision> list = (List<LayoutRevision>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTREVISION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTREVISION.concat(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutRevision>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout revisions where branchId = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByBranchId(long branchId) throws SystemException {
		for (LayoutRevision layoutRevision : findByBranchId(branchId)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where plid = &#63; from the database.
	 *
	 * @param plid the plid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPlid(long plid) throws SystemException {
		for (LayoutRevision layoutRevision : findByPlid(plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions where branchId = &#63; and plid = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByB_P(long branchId, long plid) throws SystemException {
		for (LayoutRevision layoutRevision : findByB_P(branchId, plid)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes the layout revision where branchId = &#63; and plid = &#63; and head = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByB_P_H(long branchId, long plid, boolean head)
		throws NoSuchLayoutRevisionException, SystemException {
		LayoutRevision layoutRevision = findByB_P_H(branchId, plid, head);

		remove(layoutRevision);
	}

	/**
	 * Removes all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63; from the database.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByB_P_S(long branchId, long plid, int status)
		throws SystemException {
		for (LayoutRevision layoutRevision : findByB_P_S(branchId, plid, status)) {
			remove(layoutRevision);
		}
	}

	/**
	 * Removes all the layout revisions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LayoutRevision layoutRevision : findAll()) {
			remove(layoutRevision);
		}
	}

	/**
	 * Counts all the layout revisions where branchId = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByBranchId(long branchId) throws SystemException {
		Object[] finderArgs = new Object[] { branchId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_BRANCHID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Counts all the layout revisions where plid = &#63;.
	 *
	 * @param plid the plid to search with
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPlid(long plid) throws SystemException {
		Object[] finderArgs = new Object[] { plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PLID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Counts all the layout revisions where branchId = &#63; and plid = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByB_P(long branchId, long plid) throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_B_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Counts all the layout revisions where branchId = &#63; and plid = &#63; and head = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param head the head to search with
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByB_P_H(long branchId, long plid, boolean head)
		throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid, head };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_B_P_H,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Counts all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param branchId the branch id to search with
	 * @param plid the plid to search with
	 * @param status the status to search with
	 * @return the number of matching layout revisions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByB_P_S(long branchId, long plid, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { branchId, plid, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_B_P_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
	 * Counts all the layout revisions.
	 *
	 * @return the number of layout revisions
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

				Query q = session.createQuery(_SQL_COUNT_LAYOUTREVISION);

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
	 * Initializes the layout revision persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutRevision")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutRevision>> listenersList = new ArrayList<ModelListener<LayoutRevision>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutRevision>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LayoutRevisionImpl.class.getName());
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
	private static final String _SQL_SELECT_LAYOUTREVISION = "SELECT layoutRevision FROM LayoutRevision layoutRevision";
	private static final String _SQL_SELECT_LAYOUTREVISION_WHERE = "SELECT layoutRevision FROM LayoutRevision layoutRevision WHERE ";
	private static final String _SQL_COUNT_LAYOUTREVISION = "SELECT COUNT(layoutRevision) FROM LayoutRevision layoutRevision";
	private static final String _SQL_COUNT_LAYOUTREVISION_WHERE = "SELECT COUNT(layoutRevision) FROM LayoutRevision layoutRevision WHERE ";
	private static final String _FINDER_COLUMN_BRANCHID_BRANCHID_2 = "layoutRevision.branchId = ?";
	private static final String _FINDER_COLUMN_PLID_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_B_P_BRANCHID_2 = "layoutRevision.branchId = ? AND ";
	private static final String _FINDER_COLUMN_B_P_PLID_2 = "layoutRevision.plid = ?";
	private static final String _FINDER_COLUMN_B_P_H_BRANCHID_2 = "layoutRevision.branchId = ? AND ";
	private static final String _FINDER_COLUMN_B_P_H_PLID_2 = "layoutRevision.plid = ? AND ";
	private static final String _FINDER_COLUMN_B_P_H_HEAD_2 = "layoutRevision.head = ?";
	private static final String _FINDER_COLUMN_B_P_S_BRANCHID_2 = "layoutRevision.branchId = ? AND ";
	private static final String _FINDER_COLUMN_B_P_S_PLID_2 = "layoutRevision.plid = ? AND ";
	private static final String _FINDER_COLUMN_B_P_S_STATUS_2 = "layoutRevision.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutRevision.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutRevision exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutRevision exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LayoutRevisionPersistenceImpl.class);
}