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

import com.liferay.portal.NoSuchLayoutSetPrototypeException;
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
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="LayoutSetPrototypePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototypePersistence
 * @see       LayoutSetPrototypeUtil
 * @generated
 */
public class LayoutSetPrototypePersistenceImpl extends BasePersistenceImpl<LayoutSetPrototype>
	implements LayoutSetPrototypePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetPrototypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(LayoutSetPrototype layoutSetPrototype) {
		EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey(),
			layoutSetPrototype);
	}

	public void cacheResult(List<LayoutSetPrototype> layoutSetPrototypes) {
		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			if (EntityCacheUtil.getResult(
						LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetPrototypeImpl.class,
						layoutSetPrototype.getPrimaryKey(), this) == null) {
				cacheResult(layoutSetPrototype);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(LayoutSetPrototypeImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutSetPrototypeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(LayoutSetPrototype layoutSetPrototype) {
		EntityCacheUtil.removeResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey());
	}

	public LayoutSetPrototype create(long layoutSetPrototypeId) {
		LayoutSetPrototype layoutSetPrototype = new LayoutSetPrototypeImpl();

		layoutSetPrototype.setNew(true);
		layoutSetPrototype.setPrimaryKey(layoutSetPrototypeId);

		return layoutSetPrototype;
	}

	public LayoutSetPrototype remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public LayoutSetPrototype remove(long layoutSetPrototypeId)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
					new Long(layoutSetPrototypeId));

			if (layoutSetPrototype == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						layoutSetPrototypeId);
				}

				throw new NoSuchLayoutSetPrototypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					layoutSetPrototypeId);
			}

			return remove(layoutSetPrototype);
		}
		catch (NoSuchLayoutSetPrototypeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSetPrototype remove(LayoutSetPrototype layoutSetPrototype)
		throws SystemException {
		for (ModelListener<LayoutSetPrototype> listener : listeners) {
			listener.onBeforeRemove(layoutSetPrototype);
		}

		layoutSetPrototype = removeImpl(layoutSetPrototype);

		for (ModelListener<LayoutSetPrototype> listener : listeners) {
			listener.onAfterRemove(layoutSetPrototype);
		}

		return layoutSetPrototype;
	}

	protected LayoutSetPrototype removeImpl(
		LayoutSetPrototype layoutSetPrototype) throws SystemException {
		layoutSetPrototype = toUnwrappedModel(layoutSetPrototype);

		Session session = null;

		try {
			session = openSession();

			if (layoutSetPrototype.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LayoutSetPrototypeImpl.class,
						layoutSetPrototype.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(layoutSetPrototype);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey());

		return layoutSetPrototype;
	}

	public LayoutSetPrototype updateImpl(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype,
		boolean merge) throws SystemException {
		layoutSetPrototype = toUnwrappedModel(layoutSetPrototype);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutSetPrototype, merge);

			layoutSetPrototype.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey(),
			layoutSetPrototype);

		return layoutSetPrototype;
	}

	protected LayoutSetPrototype toUnwrappedModel(
		LayoutSetPrototype layoutSetPrototype) {
		if (layoutSetPrototype instanceof LayoutSetPrototypeImpl) {
			return layoutSetPrototype;
		}

		LayoutSetPrototypeImpl layoutSetPrototypeImpl = new LayoutSetPrototypeImpl();

		layoutSetPrototypeImpl.setNew(layoutSetPrototype.isNew());
		layoutSetPrototypeImpl.setPrimaryKey(layoutSetPrototype.getPrimaryKey());

		layoutSetPrototypeImpl.setLayoutSetPrototypeId(layoutSetPrototype.getLayoutSetPrototypeId());
		layoutSetPrototypeImpl.setCompanyId(layoutSetPrototype.getCompanyId());
		layoutSetPrototypeImpl.setName(layoutSetPrototype.getName());
		layoutSetPrototypeImpl.setDescription(layoutSetPrototype.getDescription());
		layoutSetPrototypeImpl.setSettings(layoutSetPrototype.getSettings());
		layoutSetPrototypeImpl.setActive(layoutSetPrototype.isActive());

		return layoutSetPrototypeImpl;
	}

	public LayoutSetPrototype findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public LayoutSetPrototype findByPrimaryKey(long layoutSetPrototypeId)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByPrimaryKey(layoutSetPrototypeId);

		if (layoutSetPrototype == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					layoutSetPrototypeId);
			}

			throw new NoSuchLayoutSetPrototypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				layoutSetPrototypeId);
		}

		return layoutSetPrototype;
	}

	public LayoutSetPrototype fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public LayoutSetPrototype fetchByPrimaryKey(long layoutSetPrototypeId)
		throws SystemException {
		LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)EntityCacheUtil.getResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetPrototypeImpl.class, layoutSetPrototypeId, this);

		if (layoutSetPrototype == null) {
			Session session = null;

			try {
				session = openSession();

				layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
						new Long(layoutSetPrototypeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutSetPrototype != null) {
					cacheResult(layoutSetPrototype);
				}

				closeSession(session);
			}
		}

		return layoutSetPrototype;
	}

	public List<LayoutSetPrototype> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<LayoutSetPrototype> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<LayoutSetPrototype> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<LayoutSetPrototype>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutSetPrototype findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		List<LayoutSetPrototype> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		int count = countByCompanyId(companyId);

		List<LayoutSetPrototype> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype[] findByCompanyId_PrevAndNext(
		long layoutSetPrototypeId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, layoutSetPrototype,
					companyId, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = getByCompanyId_PrevAndNext(session, layoutSetPrototype,
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

	protected LayoutSetPrototype getByCompanyId_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

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

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active)
		throws SystemException {
		return findByC_A(companyId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active,
		int start, int end) throws SystemException {
		return findByC_A(companyId, active, start, end, null);
	}

	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_A,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

				query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				list = (List<LayoutSetPrototype>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutSetPrototype findByC_A_First(long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		List<LayoutSetPrototype> list = findByC_A(companyId, active, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype findByC_A_Last(long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		int count = countByC_A(companyId, active);

		List<LayoutSetPrototype> list = findByC_A(companyId, active, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSetPrototype[] findByC_A_PrevAndNext(
		long layoutSetPrototypeId, long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = getByC_A_PrevAndNext(session, layoutSetPrototype,
					companyId, active, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = getByC_A_PrevAndNext(session, layoutSetPrototype,
					companyId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetPrototype getByC_A_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, long companyId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<LayoutSetPrototype> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<LayoutSetPrototype> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<LayoutSetPrototype> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_LAYOUTSETPROTOTYPE;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSetPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByCompanyId(companyId)) {
			remove(layoutSetPrototype);
		}
	}

	public void removeByC_A(long companyId, boolean active)
		throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByC_A(companyId, active)) {
			remove(layoutSetPrototype);
		}
	}

	public void removeAll() throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findAll()) {
			remove(layoutSetPrototype);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

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

	public int countByC_A(long companyId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

				query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_A, finderArgs,
					count);

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

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSETPROTOTYPE);

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
						"value.object.listener.com.liferay.portal.model.LayoutSetPrototype")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutSetPrototype>> listenersList = new ArrayList<ModelListener<LayoutSetPrototype>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutSetPrototype>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
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
	private static final String _SQL_SELECT_LAYOUTSETPROTOTYPE = "SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype";
	private static final String _SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE = "SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _SQL_COUNT_LAYOUTSETPROTOTYPE = "SELECT COUNT(layoutSetPrototype) FROM LayoutSetPrototype layoutSetPrototype";
	private static final String _SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE = "SELECT COUNT(layoutSetPrototype) FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "layoutSetPrototype.companyId = ?";
	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 = "layoutSetPrototype.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 = "layoutSetPrototype.active = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSetPrototype.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSetPrototype exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSetPrototype exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LayoutSetPrototypePersistenceImpl.class);
}