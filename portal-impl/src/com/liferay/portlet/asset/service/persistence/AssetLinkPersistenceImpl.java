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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.impl.AssetLinkImpl;
import com.liferay.portlet.asset.model.impl.AssetLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetLinkPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkPersistence
 * @see       AssetLinkUtil
 * @generated
 */
public class AssetLinkPersistenceImpl extends BasePersistenceImpl<AssetLink>
	implements AssetLinkPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE1", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE1",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_E1 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE1", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE2", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE2",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_E2 = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE2", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE_E",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_E_E = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE_E",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_E1_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_E2_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_E_E_T = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByE_E_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(AssetLink assetLink) {
		EntityCacheUtil.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey(), assetLink);
	}

	public void cacheResult(List<AssetLink> assetLinks) {
		for (AssetLink assetLink : assetLinks) {
			if (EntityCacheUtil.getResult(
						AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
						AssetLinkImpl.class, assetLink.getPrimaryKey(), this) == null) {
				cacheResult(assetLink);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetLinkImpl.class.getName());
		EntityCacheUtil.clearCache(AssetLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetLink create(long linkId) {
		AssetLink assetLink = new AssetLinkImpl();

		assetLink.setNew(true);
		assetLink.setPrimaryKey(linkId);

		return assetLink;
	}

	public AssetLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AssetLink remove(long linkId)
		throws NoSuchLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetLink assetLink = (AssetLink)session.get(AssetLinkImpl.class,
					new Long(linkId));

			if (assetLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + linkId);
				}

				throw new NoSuchLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					linkId);
			}

			return remove(assetLink);
		}
		catch (NoSuchLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetLink remove(AssetLink assetLink) throws SystemException {
		for (ModelListener<AssetLink> listener : listeners) {
			listener.onBeforeRemove(assetLink);
		}

		assetLink = removeImpl(assetLink);

		for (ModelListener<AssetLink> listener : listeners) {
			listener.onAfterRemove(assetLink);
		}

		return assetLink;
	}

	protected AssetLink removeImpl(AssetLink assetLink)
		throws SystemException {
		assetLink = toUnwrappedModel(assetLink);

		Session session = null;

		try {
			session = openSession();

			if (assetLink.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetLinkImpl.class,
						assetLink.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetLink);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey());

		return assetLink;
	}

	public AssetLink updateImpl(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws SystemException {
		assetLink = toUnwrappedModel(assetLink);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetLink, merge);

			assetLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
			AssetLinkImpl.class, assetLink.getPrimaryKey(), assetLink);

		return assetLink;
	}

	protected AssetLink toUnwrappedModel(AssetLink assetLink) {
		if (assetLink instanceof AssetLinkImpl) {
			return assetLink;
		}

		AssetLinkImpl assetLinkImpl = new AssetLinkImpl();

		assetLinkImpl.setNew(assetLink.isNew());
		assetLinkImpl.setPrimaryKey(assetLink.getPrimaryKey());

		assetLinkImpl.setLinkId(assetLink.getLinkId());
		assetLinkImpl.setCompanyId(assetLink.getCompanyId());
		assetLinkImpl.setUserId(assetLink.getUserId());
		assetLinkImpl.setUserName(assetLink.getUserName());
		assetLinkImpl.setCreateDate(assetLink.getCreateDate());
		assetLinkImpl.setEntryId1(assetLink.getEntryId1());
		assetLinkImpl.setEntryId2(assetLink.getEntryId2());
		assetLinkImpl.setType(assetLink.getType());
		assetLinkImpl.setWeight(assetLink.getWeight());

		return assetLinkImpl;
	}

	public AssetLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetLink findByPrimaryKey(long linkId)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = fetchByPrimaryKey(linkId);

		if (assetLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + linkId);
			}

			throw new NoSuchLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				linkId);
		}

		return assetLink;
	}

	public AssetLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetLink fetchByPrimaryKey(long linkId) throws SystemException {
		AssetLink assetLink = (AssetLink)EntityCacheUtil.getResult(AssetLinkModelImpl.ENTITY_CACHE_ENABLED,
				AssetLinkImpl.class, linkId, this);

		if (assetLink == null) {
			Session session = null;

			try {
				session = openSession();

				assetLink = (AssetLink)session.get(AssetLinkImpl.class,
						new Long(linkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetLink != null) {
					cacheResult(assetLink);
				}

				closeSession(session);
			}
		}

		return assetLink;
	}

	public List<AssetLink> findByE1(long entryId1) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId1) };

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_E1,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_E1, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetLink> findByE1(long entryId1, int start, int end)
		throws SystemException {
		return findByE1(entryId1, start, end, null);
	}

	public List<AssetLink> findByE1(long entryId1, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_E1,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_E1,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetLink findByE1_First(long entryId1,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		List<AssetLink> list = findByE1(entryId1, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink findByE1_Last(long entryId1,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		int count = countByE1(entryId1);

		List<AssetLink> list = findByE1(entryId1, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink[] findByE1_PrevAndNext(long linkId, long entryId1,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		int count = countByE1(entryId1);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId1);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetLink);

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = (AssetLink)objArray[0];
			array[1] = (AssetLink)objArray[1];
			array[2] = (AssetLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetLink> findByE2(long entryId2) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId2) };

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_E2,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_E2, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetLink> findByE2(long entryId2, int start, int end)
		throws SystemException {
		return findByE2(entryId2, start, end, null);
	}

	public List<AssetLink> findByE2(long entryId2, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId2),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_E2,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_E2,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetLink findByE2_First(long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		List<AssetLink> list = findByE2(entryId2, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId2=");
			msg.append(entryId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink findByE2_Last(long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		int count = countByE2(entryId2);

		List<AssetLink> list = findByE2(entryId2, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId2=");
			msg.append(entryId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink[] findByE2_PrevAndNext(long linkId, long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		int count = countByE2(entryId2);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId2);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetLink);

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = (AssetLink)objArray[0];
			array[1] = (AssetLink)objArray[1];
			array[2] = (AssetLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetLink> findByE_E(long entryId1, long entryId2)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Long(entryId2)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_E_E,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_E_E, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end) throws SystemException {
		return findByE_E(entryId1, entryId2, start, end, null);
	}

	public List<AssetLink> findByE_E(long entryId1, long entryId2, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Long(entryId2),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_E_E,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_E_E,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetLink findByE_E_First(long entryId1, long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		List<AssetLink> list = findByE_E(entryId1, entryId2, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", entryId2=");
			msg.append(entryId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink findByE_E_Last(long entryId1, long entryId2,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		int count = countByE_E(entryId1, entryId2);

		List<AssetLink> list = findByE_E(entryId1, entryId2, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", entryId2=");
			msg.append(entryId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink[] findByE_E_PrevAndNext(long linkId, long entryId1,
		long entryId2, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		int count = countByE_E(entryId1, entryId2);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId1);

			qPos.add(entryId2);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetLink);

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = (AssetLink)objArray[0];
			array[1] = (AssetLink)objArray[1];
			array[2] = (AssetLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetLink> findByE1_T(long entryId1, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId1), new Integer(type) };

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_E1_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E1_T_TYPE_2);

				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_E1_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end) throws SystemException {
		return findByE1_T(entryId1, type, start, end, null);
	}

	public List<AssetLink> findByE1_T(long entryId1, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_E1_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E1_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_E1_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetLink findByE1_T_First(long entryId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		List<AssetLink> list = findByE1_T(entryId1, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink findByE1_T_Last(long entryId1, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		int count = countByE1_T(entryId1, type);

		List<AssetLink> list = findByE1_T(entryId1, type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink[] findByE1_T_PrevAndNext(long linkId, long entryId1,
		int type, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		int count = countByE1_T(entryId1, type);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E1_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId1);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetLink);

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = (AssetLink)objArray[0];
			array[1] = (AssetLink)objArray[1];
			array[2] = (AssetLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetLink> findByE2_T(long entryId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId2), new Integer(type) };

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_E2_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

				query.append(_FINDER_COLUMN_E2_T_TYPE_2);

				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_E2_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end) throws SystemException {
		return findByE2_T(entryId2, type, start, end, null);
	}

	public List<AssetLink> findByE2_T(long entryId2, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId2), new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_E2_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

				query.append(_FINDER_COLUMN_E2_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_E2_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetLink findByE2_T_First(long entryId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		List<AssetLink> list = findByE2_T(entryId2, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId2=");
			msg.append(entryId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink findByE2_T_Last(long entryId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		int count = countByE2_T(entryId2, type);

		List<AssetLink> list = findByE2_T(entryId2, type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId2=");
			msg.append(entryId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink[] findByE2_T_PrevAndNext(long linkId, long entryId2,
		int type, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		int count = countByE2_T(entryId2, type);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E2_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId2);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetLink);

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = (AssetLink)objArray[0];
			array[1] = (AssetLink)objArray[1];
			array[2] = (AssetLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetLink> findByE_E_T(long entryId1, long entryId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Long(entryId2), new Integer(type)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_E_E_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

				query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_E_E_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetLink> findByE_E_T(long entryId1, long entryId2, int type,
		int start, int end) throws SystemException {
		return findByE_E_T(entryId1, entryId2, type, start, end, null);
	}

	public List<AssetLink> findByE_E_T(long entryId1, long entryId2, int type,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Long(entryId2), new Integer(type),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_E_E_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

				query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				list = (List<AssetLink>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_E_E_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetLink findByE_E_T_First(long entryId1, long entryId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		List<AssetLink> list = findByE_E_T(entryId1, entryId2, type, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", entryId2=");
			msg.append(entryId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink findByE_E_T_Last(long entryId1, long entryId2, int type,
		OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		int count = countByE_E_T(entryId1, entryId2, type);

		List<AssetLink> list = findByE_E_T(entryId1, entryId2, type, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId1=");
			msg.append(entryId1);

			msg.append(", entryId2=");
			msg.append(entryId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetLink[] findByE_E_T_PrevAndNext(long linkId, long entryId1,
		long entryId2, int type, OrderByComparator orderByComparator)
		throws NoSuchLinkException, SystemException {
		AssetLink assetLink = findByPrimaryKey(linkId);

		int count = countByE_E_T(entryId1, entryId2, type);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_ASSETLINK_WHERE);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

			query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

			query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId1);

			qPos.add(entryId2);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetLink);

			AssetLink[] array = new AssetLinkImpl[3];

			array[0] = (AssetLink)objArray[0];
			array[1] = (AssetLink)objArray[1];
			array[2] = (AssetLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetLink> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetLink> list = (List<AssetLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_ASSETLINK);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ASSETLINK.concat(AssetLinkModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByE1(long entryId1) throws SystemException {
		for (AssetLink assetLink : findByE1(entryId1)) {
			remove(assetLink);
		}
	}

	public void removeByE2(long entryId2) throws SystemException {
		for (AssetLink assetLink : findByE2(entryId2)) {
			remove(assetLink);
		}
	}

	public void removeByE_E(long entryId1, long entryId2)
		throws SystemException {
		for (AssetLink assetLink : findByE_E(entryId1, entryId2)) {
			remove(assetLink);
		}
	}

	public void removeByE1_T(long entryId1, int type) throws SystemException {
		for (AssetLink assetLink : findByE1_T(entryId1, type)) {
			remove(assetLink);
		}
	}

	public void removeByE2_T(long entryId2, int type) throws SystemException {
		for (AssetLink assetLink : findByE2_T(entryId2, type)) {
			remove(assetLink);
		}
	}

	public void removeByE_E_T(long entryId1, long entryId2, int type)
		throws SystemException {
		for (AssetLink assetLink : findByE_E_T(entryId1, entryId2, type)) {
			remove(assetLink);
		}
	}

	public void removeAll() throws SystemException {
		for (AssetLink assetLink : findAll()) {
			remove(assetLink);
		}
	}

	public int countByE1(long entryId1) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId1) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E1,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E1_ENTRYID1_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E1, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByE2(long entryId2) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId2) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E2,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E2_ENTRYID2_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E2, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByE_E(long entryId1, long entryId2)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Long(entryId2)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E_E,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E_E_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E_E_ENTRYID2_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E_E, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByE1_T(long entryId1, int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId1), new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E1_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E1_T_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E1_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E1_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByE2_T(long entryId2, int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId2), new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E2_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E2_T_ENTRYID2_2);

				query.append(_FINDER_COLUMN_E2_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E2_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByE_E_T(long entryId1, long entryId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId1), new Long(entryId2), new Integer(type)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_E_E_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_ASSETLINK_WHERE);

				query.append(_FINDER_COLUMN_E_E_T_ENTRYID1_2);

				query.append(_FINDER_COLUMN_E_E_T_ENTRYID2_2);

				query.append(_FINDER_COLUMN_E_E_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId1);

				qPos.add(entryId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_E_E_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETLINK);

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetLink>> listenersList = new ArrayList<ModelListener<AssetLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetLink>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETLINK = "SELECT assetLink FROM AssetLink assetLink";
	private static final String _SQL_SELECT_ASSETLINK_WHERE = "SELECT assetLink FROM AssetLink assetLink WHERE ";
	private static final String _SQL_COUNT_ASSETLINK = "SELECT COUNT(assetLink) FROM AssetLink assetLink";
	private static final String _SQL_COUNT_ASSETLINK_WHERE = "SELECT COUNT(assetLink) FROM AssetLink assetLink WHERE ";
	private static final String _FINDER_COLUMN_E1_ENTRYID1_2 = "assetLink.entryId1 = ?";
	private static final String _FINDER_COLUMN_E2_ENTRYID2_2 = "assetLink.entryId2 = ?";
	private static final String _FINDER_COLUMN_E_E_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_ENTRYID2_2 = "assetLink.entryId2 = ?";
	private static final String _FINDER_COLUMN_E1_T_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E1_T_TYPE_2 = "assetLink.type = ?";
	private static final String _FINDER_COLUMN_E2_T_ENTRYID2_2 = "assetLink.entryId2 = ? AND ";
	private static final String _FINDER_COLUMN_E2_T_TYPE_2 = "assetLink.type = ?";
	private static final String _FINDER_COLUMN_E_E_T_ENTRYID1_2 = "assetLink.entryId1 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_T_ENTRYID2_2 = "assetLink.entryId2 = ? AND ";
	private static final String _FINDER_COLUMN_E_E_T_TYPE_2 = "assetLink.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetLink exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AssetLinkPersistenceImpl.class);
}