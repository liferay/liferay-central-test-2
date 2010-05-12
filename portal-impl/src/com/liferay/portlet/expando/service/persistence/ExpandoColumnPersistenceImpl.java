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

package com.liferay.portlet.expando.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.impl.ExpandoColumnImpl;
import com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ExpandoColumnPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnPersistence
 * @see       ExpandoColumnUtil
 * @generated
 */
public class ExpandoColumnPersistenceImpl extends BasePersistenceImpl<ExpandoColumn>
	implements ExpandoColumnPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoColumnImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TABLEID = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTableId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TABLEID = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTableId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_N = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_N = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByT_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ExpandoColumn expandoColumn) {
		EntityCacheUtil.putResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey(),
			expandoColumn);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
			new Object[] {
				new Long(expandoColumn.getTableId()),
				
			expandoColumn.getName()
			}, expandoColumn);
	}

	public void cacheResult(List<ExpandoColumn> expandoColumns) {
		for (ExpandoColumn expandoColumn : expandoColumns) {
			if (EntityCacheUtil.getResult(
						ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoColumnImpl.class, expandoColumn.getPrimaryKey(),
						this) == null) {
				cacheResult(expandoColumn);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ExpandoColumnImpl.class.getName());
		EntityCacheUtil.clearCache(ExpandoColumnImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(ExpandoColumn expandoColumn) {
		EntityCacheUtil.removeResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_N,
			new Object[] {
				new Long(expandoColumn.getTableId()),
				
			expandoColumn.getName()
			});
	}

	public ExpandoColumn create(long columnId) {
		ExpandoColumn expandoColumn = new ExpandoColumnImpl();

		expandoColumn.setNew(true);
		expandoColumn.setPrimaryKey(columnId);

		return expandoColumn;
	}

	public ExpandoColumn remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ExpandoColumn remove(long columnId)
		throws NoSuchColumnException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoColumn expandoColumn = (ExpandoColumn)session.get(ExpandoColumnImpl.class,
					new Long(columnId));

			if (expandoColumn == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + columnId);
				}

				throw new NoSuchColumnException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					columnId);
			}

			return remove(expandoColumn);
		}
		catch (NoSuchColumnException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException {
		for (ModelListener<ExpandoColumn> listener : listeners) {
			listener.onBeforeRemove(expandoColumn);
		}

		expandoColumn = removeImpl(expandoColumn);

		for (ModelListener<ExpandoColumn> listener : listeners) {
			listener.onAfterRemove(expandoColumn);
		}

		return expandoColumn;
	}

	protected ExpandoColumn removeImpl(ExpandoColumn expandoColumn)
		throws SystemException {
		expandoColumn = toUnwrappedModel(expandoColumn);

		Session session = null;

		try {
			session = openSession();

			if (expandoColumn.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoColumnImpl.class,
						expandoColumn.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoColumn);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoColumnModelImpl expandoColumnModelImpl = (ExpandoColumnModelImpl)expandoColumn;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_N,
			new Object[] {
				new Long(expandoColumnModelImpl.getOriginalTableId()),
				
			expandoColumnModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey());

		return expandoColumn;
	}

	public ExpandoColumn updateImpl(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge) throws SystemException {
		expandoColumn = toUnwrappedModel(expandoColumn);

		boolean isNew = expandoColumn.isNew();

		ExpandoColumnModelImpl expandoColumnModelImpl = (ExpandoColumnModelImpl)expandoColumn;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, expandoColumn, merge);

			expandoColumn.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey(),
			expandoColumn);

		if (!isNew &&
				((expandoColumn.getTableId() != expandoColumnModelImpl.getOriginalTableId()) ||
				!Validator.equals(expandoColumn.getName(),
					expandoColumnModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_N,
				new Object[] {
					new Long(expandoColumnModelImpl.getOriginalTableId()),
					
				expandoColumnModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((expandoColumn.getTableId() != expandoColumnModelImpl.getOriginalTableId()) ||
				!Validator.equals(expandoColumn.getName(),
					expandoColumnModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
				new Object[] {
					new Long(expandoColumn.getTableId()),
					
				expandoColumn.getName()
				}, expandoColumn);
		}

		return expandoColumn;
	}

	protected ExpandoColumn toUnwrappedModel(ExpandoColumn expandoColumn) {
		if (expandoColumn instanceof ExpandoColumnImpl) {
			return expandoColumn;
		}

		ExpandoColumnImpl expandoColumnImpl = new ExpandoColumnImpl();

		expandoColumnImpl.setNew(expandoColumn.isNew());
		expandoColumnImpl.setPrimaryKey(expandoColumn.getPrimaryKey());

		expandoColumnImpl.setColumnId(expandoColumn.getColumnId());
		expandoColumnImpl.setCompanyId(expandoColumn.getCompanyId());
		expandoColumnImpl.setTableId(expandoColumn.getTableId());
		expandoColumnImpl.setName(expandoColumn.getName());
		expandoColumnImpl.setType(expandoColumn.getType());
		expandoColumnImpl.setDefaultData(expandoColumn.getDefaultData());
		expandoColumnImpl.setTypeSettings(expandoColumn.getTypeSettings());

		return expandoColumnImpl;
	}

	public ExpandoColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoColumn findByPrimaryKey(long columnId)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByPrimaryKey(columnId);

		if (expandoColumn == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + columnId);
			}

			throw new NoSuchColumnException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				columnId);
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoColumn fetchByPrimaryKey(long columnId)
		throws SystemException {
		ExpandoColumn expandoColumn = (ExpandoColumn)EntityCacheUtil.getResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoColumnImpl.class, columnId, this);

		if (expandoColumn == null) {
			Session session = null;

			try {
				session = openSession();

				expandoColumn = (ExpandoColumn)session.get(ExpandoColumnImpl.class,
						new Long(columnId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (expandoColumn != null) {
					cacheResult(expandoColumn);
				}

				closeSession(session);
			}
		}

		return expandoColumn;
	}

	public List<ExpandoColumn> findByTableId(long tableId)
		throws SystemException {
		return findByTableId(tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoColumn> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoColumn> findByTableId(long tableId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ExpandoColumn> list = (List<ExpandoColumn>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TABLEID,
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

				query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = (List<ExpandoColumn>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoColumn>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoColumn findByTableId_First(long tableId,
		OrderByComparator orderByComparator)
		throws NoSuchColumnException, SystemException {
		List<ExpandoColumn> list = findByTableId(tableId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn findByTableId_Last(long tableId,
		OrderByComparator orderByComparator)
		throws NoSuchColumnException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoColumn> list = findByTableId(tableId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn[] findByTableId_PrevAndNext(long columnId,
		long tableId, OrderByComparator orderByComparator)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = findByPrimaryKey(columnId);

		Session session = null;

		try {
			session = openSession();

			ExpandoColumn[] array = new ExpandoColumnImpl[3];

			array[0] = getByTableId_PrevAndNext(session, expandoColumn,
					tableId, orderByComparator, true);

			array[1] = expandoColumn;

			array[2] = getByTableId_PrevAndNext(session, expandoColumn,
					tableId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoColumn getByTableId_PrevAndNext(Session session,
		ExpandoColumn expandoColumn, long tableId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

		query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

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

		else {
			query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(expandoColumn);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoColumn> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public ExpandoColumn findByT_N(long tableId, String name)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByT_N(tableId, name);

		if (expandoColumn == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchColumnException(msg.toString());
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByT_N(long tableId, String name)
		throws SystemException {
		return fetchByT_N(tableId, name, true);
	}

	public ExpandoColumn fetchByT_N(long tableId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

				query.append(_FINDER_COLUMN_T_N_TABLEID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_T_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_N_NAME_2);
					}
				}

				query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (name != null) {
					qPos.add(name);
				}

				List<ExpandoColumn> list = q.list();

				result = list;

				ExpandoColumn expandoColumn = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
						finderArgs, list);
				}
				else {
					expandoColumn = list.get(0);

					cacheResult(expandoColumn);

					if ((expandoColumn.getTableId() != tableId) ||
							(expandoColumn.getName() == null) ||
							!expandoColumn.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
							finderArgs, expandoColumn);
					}
				}

				return expandoColumn;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
						finderArgs, new ArrayList<ExpandoColumn>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ExpandoColumn)result;
			}
		}
	}

	public List<ExpandoColumn> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoColumn> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoColumn> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ExpandoColumn> list = (List<ExpandoColumn>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_EXPANDOCOLUMN);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_EXPANDOCOLUMN.concat(ExpandoColumnModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ExpandoColumn>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoColumn>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoColumn>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoColumn expandoColumn : findByTableId(tableId)) {
			remove(expandoColumn);
		}
	}

	public void removeByT_N(long tableId, String name)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = findByT_N(tableId, name);

		remove(expandoColumn);
	}

	public void removeAll() throws SystemException {
		for (ExpandoColumn expandoColumn : findAll()) {
			remove(expandoColumn);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TABLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_EXPANDOCOLUMN_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TABLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_N(long tableId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOCOLUMN_WHERE);

				query.append(_FINDER_COLUMN_T_N_TABLEID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_T_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_N, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_EXPANDOCOLUMN);

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
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoColumn")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoColumn>> listenersList = new ArrayList<ModelListener<ExpandoColumn>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoColumn>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = ExpandoColumnPersistence.class)
	protected ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(type = ExpandoTablePersistence.class)
	protected ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_EXPANDOCOLUMN = "SELECT expandoColumn FROM ExpandoColumn expandoColumn";
	private static final String _SQL_SELECT_EXPANDOCOLUMN_WHERE = "SELECT expandoColumn FROM ExpandoColumn expandoColumn WHERE ";
	private static final String _SQL_COUNT_EXPANDOCOLUMN = "SELECT COUNT(expandoColumn) FROM ExpandoColumn expandoColumn";
	private static final String _SQL_COUNT_EXPANDOCOLUMN_WHERE = "SELECT COUNT(expandoColumn) FROM ExpandoColumn expandoColumn WHERE ";
	private static final String _FINDER_COLUMN_TABLEID_TABLEID_2 = "expandoColumn.tableId = ?";
	private static final String _FINDER_COLUMN_T_N_TABLEID_2 = "expandoColumn.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_N_NAME_1 = "expandoColumn.name IS NULL";
	private static final String _FINDER_COLUMN_T_N_NAME_2 = "expandoColumn.name = ?";
	private static final String _FINDER_COLUMN_T_N_NAME_3 = "(expandoColumn.name IS NULL OR expandoColumn.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoColumn.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ExpandoColumn exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ExpandoColumn exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ExpandoColumnPersistenceImpl.class);
}