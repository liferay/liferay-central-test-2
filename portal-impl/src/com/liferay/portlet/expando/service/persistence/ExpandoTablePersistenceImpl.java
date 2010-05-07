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

import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.impl.ExpandoTableImpl;
import com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ExpandoTablePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTablePersistence
 * @see       ExpandoTableUtil
 * @generated
 */
public class ExpandoTablePersistenceImpl extends BasePersistenceImpl<ExpandoTable>
	implements ExpandoTablePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoTableImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_N = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_N = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ExpandoTable expandoTable) {
		EntityCacheUtil.putResult(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableImpl.class, expandoTable.getPrimaryKey(), expandoTable);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_N,
			new Object[] {
				new Long(expandoTable.getCompanyId()),
				new Long(expandoTable.getClassNameId()),
				
			expandoTable.getName()
			}, expandoTable);
	}

	public void cacheResult(List<ExpandoTable> expandoTables) {
		for (ExpandoTable expandoTable : expandoTables) {
			if (EntityCacheUtil.getResult(
						ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoTableImpl.class, expandoTable.getPrimaryKey(),
						this) == null) {
				cacheResult(expandoTable);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ExpandoTableImpl.class.getName());
		EntityCacheUtil.clearCache(ExpandoTableImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(ExpandoTable expandoTable) {
		EntityCacheUtil.removeResult(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableImpl.class, expandoTable.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_N,
			new Object[] {
				new Long(expandoTable.getCompanyId()),
				new Long(expandoTable.getClassNameId()),
				
			expandoTable.getName()
			});
	}

	public ExpandoTable create(long tableId) {
		ExpandoTable expandoTable = new ExpandoTableImpl();

		expandoTable.setNew(true);
		expandoTable.setPrimaryKey(tableId);

		return expandoTable;
	}

	public ExpandoTable remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ExpandoTable remove(long tableId)
		throws NoSuchTableException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoTable expandoTable = (ExpandoTable)session.get(ExpandoTableImpl.class,
					new Long(tableId));

			if (expandoTable == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tableId);
				}

				throw new NoSuchTableException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					tableId);
			}

			return remove(expandoTable);
		}
		catch (NoSuchTableException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoTable remove(ExpandoTable expandoTable)
		throws SystemException {
		for (ModelListener<ExpandoTable> listener : listeners) {
			listener.onBeforeRemove(expandoTable);
		}

		expandoTable = removeImpl(expandoTable);

		for (ModelListener<ExpandoTable> listener : listeners) {
			listener.onAfterRemove(expandoTable);
		}

		return expandoTable;
	}

	protected ExpandoTable removeImpl(ExpandoTable expandoTable)
		throws SystemException {
		expandoTable = toUnwrappedModel(expandoTable);

		Session session = null;

		try {
			session = openSession();

			if (expandoTable.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoTableImpl.class,
						expandoTable.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoTable);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoTableModelImpl expandoTableModelImpl = (ExpandoTableModelImpl)expandoTable;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_N,
			new Object[] {
				new Long(expandoTableModelImpl.getOriginalCompanyId()),
				new Long(expandoTableModelImpl.getOriginalClassNameId()),
				
			expandoTableModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableImpl.class, expandoTable.getPrimaryKey());

		return expandoTable;
	}

	public ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge) throws SystemException {
		expandoTable = toUnwrappedModel(expandoTable);

		boolean isNew = expandoTable.isNew();

		ExpandoTableModelImpl expandoTableModelImpl = (ExpandoTableModelImpl)expandoTable;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, expandoTable, merge);

			expandoTable.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoTableImpl.class, expandoTable.getPrimaryKey(), expandoTable);

		if (!isNew &&
				((expandoTable.getCompanyId() != expandoTableModelImpl.getOriginalCompanyId()) ||
				(expandoTable.getClassNameId() != expandoTableModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(expandoTable.getName(),
					expandoTableModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_N,
				new Object[] {
					new Long(expandoTableModelImpl.getOriginalCompanyId()),
					new Long(expandoTableModelImpl.getOriginalClassNameId()),
					
				expandoTableModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((expandoTable.getCompanyId() != expandoTableModelImpl.getOriginalCompanyId()) ||
				(expandoTable.getClassNameId() != expandoTableModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(expandoTable.getName(),
					expandoTableModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_N,
				new Object[] {
					new Long(expandoTable.getCompanyId()),
					new Long(expandoTable.getClassNameId()),
					
				expandoTable.getName()
				}, expandoTable);
		}

		return expandoTable;
	}

	protected ExpandoTable toUnwrappedModel(ExpandoTable expandoTable) {
		if (expandoTable instanceof ExpandoTableImpl) {
			return expandoTable;
		}

		ExpandoTableImpl expandoTableImpl = new ExpandoTableImpl();

		expandoTableImpl.setNew(expandoTable.isNew());
		expandoTableImpl.setPrimaryKey(expandoTable.getPrimaryKey());

		expandoTableImpl.setTableId(expandoTable.getTableId());
		expandoTableImpl.setCompanyId(expandoTable.getCompanyId());
		expandoTableImpl.setClassNameId(expandoTable.getClassNameId());
		expandoTableImpl.setName(expandoTable.getName());

		return expandoTableImpl;
	}

	public ExpandoTable findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoTable findByPrimaryKey(long tableId)
		throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = fetchByPrimaryKey(tableId);

		if (expandoTable == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tableId);
			}

			throw new NoSuchTableException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				tableId);
		}

		return expandoTable;
	}

	public ExpandoTable fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoTable fetchByPrimaryKey(long tableId)
		throws SystemException {
		ExpandoTable expandoTable = (ExpandoTable)EntityCacheUtil.getResult(ExpandoTableModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoTableImpl.class, tableId, this);

		if (expandoTable == null) {
			Session session = null;

			try {
				session = openSession();

				expandoTable = (ExpandoTable)session.get(ExpandoTableImpl.class,
						new Long(tableId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (expandoTable != null) {
					cacheResult(expandoTable);
				}

				closeSession(session);
			}
		}

		return expandoTable;
	}

	public List<ExpandoTable> findByC_C(long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
			};

		List<ExpandoTable> list = (List<ExpandoTable>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoTable>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoTable> findByC_C(long companyId, long classNameId,
		int start, int end) throws SystemException {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	public List<ExpandoTable> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ExpandoTable> list = (List<ExpandoTable>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
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

				query.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				list = (List<ExpandoTable>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoTable>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoTable findByC_C_First(long companyId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchTableException, SystemException {
		List<ExpandoTable> list = findByC_C(companyId, classNameId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTableException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTable findByC_C_Last(long companyId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchTableException, SystemException {
		int count = countByC_C(companyId, classNameId);

		List<ExpandoTable> list = findByC_C(companyId, classNameId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTableException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTable[] findByC_C_PrevAndNext(long tableId, long companyId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = findByPrimaryKey(tableId);

		Session session = null;

		try {
			session = openSession();

			ExpandoTable[] array = new ExpandoTableImpl[3];

			array[0] = getByC_C_PrevAndNext(session, expandoTable, companyId,
					classNameId, orderByComparator, true);

			array[1] = expandoTable;

			array[2] = getByC_C_PrevAndNext(session, expandoTable, companyId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoTable getByC_C_PrevAndNext(Session session,
		ExpandoTable expandoTable, long companyId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(expandoTable);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoTable> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public ExpandoTable findByC_C_N(long companyId, long classNameId,
		String name) throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = fetchByC_C_N(companyId, classNameId, name);

		if (expandoTable == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTableException(msg.toString());
		}

		return expandoTable;
	}

	public ExpandoTable fetchByC_C_N(long companyId, long classNameId,
		String name) throws SystemException {
		return fetchByC_C_N(companyId, classNameId, name, true);
	}

	public ExpandoTable fetchByC_C_N(long companyId, long classNameId,
		String name, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_N_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_N_CLASSNAMEID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_C_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_C_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_C_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				if (name != null) {
					qPos.add(name);
				}

				List<ExpandoTable> list = q.list();

				result = list;

				ExpandoTable expandoTable = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_N,
						finderArgs, list);
				}
				else {
					expandoTable = list.get(0);

					cacheResult(expandoTable);

					if ((expandoTable.getCompanyId() != companyId) ||
							(expandoTable.getClassNameId() != classNameId) ||
							(expandoTable.getName() == null) ||
							!expandoTable.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_N,
							finderArgs, expandoTable);
					}
				}

				return expandoTable;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_N,
						finderArgs, new ArrayList<ExpandoTable>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ExpandoTable)result;
			}
		}
	}

	public List<ExpandoTable> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoTable> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoTable> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ExpandoTable> list = (List<ExpandoTable>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_EXPANDOTABLE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_EXPANDOTABLE;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ExpandoTable>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoTable>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoTable>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		for (ExpandoTable expandoTable : findByC_C(companyId, classNameId)) {
			remove(expandoTable);
		}
	}

	public void removeByC_C_N(long companyId, long classNameId, String name)
		throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = findByC_C_N(companyId, classNameId, name);

		remove(expandoTable);
	}

	public void removeAll() throws SystemException {
		for (ExpandoTable expandoTable : findAll()) {
			remove(expandoTable);
		}
	}

	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOTABLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_N(long companyId, long classNameId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_EXPANDOTABLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_N_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_N_CLASSNAMEID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_C_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_C_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_C_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_N,
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

				Query q = session.createQuery(_SQL_COUNT_EXPANDOTABLE);

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
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoTable")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoTable>> listenersList = new ArrayList<ModelListener<ExpandoTable>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoTable>)Class.forName(
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
	private static final String _SQL_SELECT_EXPANDOTABLE = "SELECT expandoTable FROM ExpandoTable expandoTable";
	private static final String _SQL_SELECT_EXPANDOTABLE_WHERE = "SELECT expandoTable FROM ExpandoTable expandoTable WHERE ";
	private static final String _SQL_COUNT_EXPANDOTABLE = "SELECT COUNT(expandoTable) FROM ExpandoTable expandoTable";
	private static final String _SQL_COUNT_EXPANDOTABLE_WHERE = "SELECT COUNT(expandoTable) FROM ExpandoTable expandoTable WHERE ";
	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "expandoTable.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "expandoTable.classNameId = ?";
	private static final String _FINDER_COLUMN_C_C_N_COMPANYID_2 = "expandoTable.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_N_CLASSNAMEID_2 = "expandoTable.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_N_NAME_1 = "expandoTable.name IS NULL";
	private static final String _FINDER_COLUMN_C_C_N_NAME_2 = "expandoTable.name = ?";
	private static final String _FINDER_COLUMN_C_C_N_NAME_3 = "(expandoTable.name IS NULL OR expandoTable.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoTable.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ExpandoTable exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ExpandoTable exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ExpandoTablePersistenceImpl.class);
}