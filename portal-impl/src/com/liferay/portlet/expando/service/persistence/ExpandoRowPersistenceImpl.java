/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.impl.ExpandoRowImpl;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ExpandoRowPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRowPersistence
 * @see       ExpandoRowUtil
 * @generated
 */
public class ExpandoRowPersistenceImpl extends BasePersistenceImpl<ExpandoRow>
	implements ExpandoRowPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoRowImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TABLEID = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByTableId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TABLEID = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByTableId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TABLEID = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByTableId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_C = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ExpandoRow expandoRow) {
		EntityCacheUtil.putResult(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowImpl.class, expandoRow.getPrimaryKey(), expandoRow);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				new Long(expandoRow.getTableId()),
				new Long(expandoRow.getClassPK())
			}, expandoRow);
	}

	public void cacheResult(List<ExpandoRow> expandoRows) {
		for (ExpandoRow expandoRow : expandoRows) {
			if (EntityCacheUtil.getResult(
						ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoRowImpl.class, expandoRow.getPrimaryKey(), this) == null) {
				cacheResult(expandoRow);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ExpandoRowImpl.class.getName());
		EntityCacheUtil.clearCache(ExpandoRowImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ExpandoRow create(long rowId) {
		ExpandoRow expandoRow = new ExpandoRowImpl();

		expandoRow.setNew(true);
		expandoRow.setPrimaryKey(rowId);

		return expandoRow;
	}

	public ExpandoRow remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ExpandoRow remove(long rowId)
		throws NoSuchRowException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoRow expandoRow = (ExpandoRow)session.get(ExpandoRowImpl.class,
					new Long(rowId));

			if (expandoRow == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + rowId);
				}

				throw new NoSuchRowException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					rowId);
			}

			return remove(expandoRow);
		}
		catch (NoSuchRowException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoRow remove(ExpandoRow expandoRow) throws SystemException {
		for (ModelListener<ExpandoRow> listener : listeners) {
			listener.onBeforeRemove(expandoRow);
		}

		expandoRow = removeImpl(expandoRow);

		for (ModelListener<ExpandoRow> listener : listeners) {
			listener.onAfterRemove(expandoRow);
		}

		return expandoRow;
	}

	protected ExpandoRow removeImpl(ExpandoRow expandoRow)
		throws SystemException {
		expandoRow = toUnwrappedModel(expandoRow);

		Session session = null;

		try {
			session = openSession();

			if (expandoRow.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoRowImpl.class,
						expandoRow.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoRow);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoRowModelImpl expandoRowModelImpl = (ExpandoRowModelImpl)expandoRow;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				new Long(expandoRowModelImpl.getOriginalTableId()),
				new Long(expandoRowModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowImpl.class, expandoRow.getPrimaryKey());

		return expandoRow;
	}

	public ExpandoRow updateImpl(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws SystemException {
		expandoRow = toUnwrappedModel(expandoRow);

		boolean isNew = expandoRow.isNew();

		ExpandoRowModelImpl expandoRowModelImpl = (ExpandoRowModelImpl)expandoRow;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, expandoRow, merge);

			expandoRow.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoRowImpl.class, expandoRow.getPrimaryKey(), expandoRow);

		if (!isNew &&
				((expandoRow.getTableId() != expandoRowModelImpl.getOriginalTableId()) ||
				(expandoRow.getClassPK() != expandoRowModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
				new Object[] {
					new Long(expandoRowModelImpl.getOriginalTableId()),
					new Long(expandoRowModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((expandoRow.getTableId() != expandoRowModelImpl.getOriginalTableId()) ||
				(expandoRow.getClassPK() != expandoRowModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
				new Object[] {
					new Long(expandoRow.getTableId()),
					new Long(expandoRow.getClassPK())
				}, expandoRow);
		}

		return expandoRow;
	}

	protected ExpandoRow toUnwrappedModel(ExpandoRow expandoRow) {
		if (expandoRow instanceof ExpandoRowImpl) {
			return expandoRow;
		}

		ExpandoRowImpl expandoRowImpl = new ExpandoRowImpl();

		expandoRowImpl.setNew(expandoRow.isNew());
		expandoRowImpl.setPrimaryKey(expandoRow.getPrimaryKey());

		expandoRowImpl.setRowId(expandoRow.getRowId());
		expandoRowImpl.setCompanyId(expandoRow.getCompanyId());
		expandoRowImpl.setTableId(expandoRow.getTableId());
		expandoRowImpl.setClassPK(expandoRow.getClassPK());

		return expandoRowImpl;
	}

	public ExpandoRow findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoRow findByPrimaryKey(long rowId)
		throws NoSuchRowException, SystemException {
		ExpandoRow expandoRow = fetchByPrimaryKey(rowId);

		if (expandoRow == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + rowId);
			}

			throw new NoSuchRowException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				rowId);
		}

		return expandoRow;
	}

	public ExpandoRow fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoRow fetchByPrimaryKey(long rowId) throws SystemException {
		ExpandoRow expandoRow = (ExpandoRow)EntityCacheUtil.getResult(ExpandoRowModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoRowImpl.class, rowId, this);

		if (expandoRow == null) {
			Session session = null;

			try {
				session = openSession();

				expandoRow = (ExpandoRow)session.get(ExpandoRowImpl.class,
						new Long(rowId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (expandoRow != null) {
					cacheResult(expandoRow);
				}

				closeSession(session);
			}
		}

		return expandoRow;
	}

	public List<ExpandoRow> findByTableId(long tableId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		List<ExpandoRow> list = (List<ExpandoRow>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TABLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_EXPANDOROW_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoRow>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoRow> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoRow> findByTableId(long tableId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoRow> list = (List<ExpandoRow>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_EXPANDOROW_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = (List<ExpandoRow>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoRow>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoRow findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchRowException, SystemException {
		List<ExpandoRow> list = findByTableId(tableId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRowException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoRow findByTableId_Last(long tableId, OrderByComparator obc)
		throws NoSuchRowException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoRow> list = findByTableId(tableId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRowException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoRow[] findByTableId_PrevAndNext(long rowId, long tableId,
		OrderByComparator obc) throws NoSuchRowException, SystemException {
		ExpandoRow expandoRow = findByPrimaryKey(rowId);

		int count = countByTableId(tableId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_EXPANDOROW_WHERE);

			query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoRow);

			ExpandoRow[] array = new ExpandoRowImpl[3];

			array[0] = (ExpandoRow)objArray[0];
			array[1] = (ExpandoRow)objArray[1];
			array[2] = (ExpandoRow)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoRow findByT_C(long tableId, long classPK)
		throws NoSuchRowException, SystemException {
		ExpandoRow expandoRow = fetchByT_C(tableId, classPK);

		if (expandoRow == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRowException(msg.toString());
		}

		return expandoRow;
	}

	public ExpandoRow fetchByT_C(long tableId, long classPK)
		throws SystemException {
		return fetchByT_C(tableId, classPK, true);
	}

	public ExpandoRow fetchByT_C(long tableId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_EXPANDOROW_WHERE);

				query.append(_FINDER_COLUMN_T_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				List<ExpandoRow> list = q.list();

				result = list;

				ExpandoRow expandoRow = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
						finderArgs, list);
				}
				else {
					expandoRow = list.get(0);

					cacheResult(expandoRow);

					if ((expandoRow.getTableId() != tableId) ||
							(expandoRow.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
							finderArgs, expandoRow);
					}
				}

				return expandoRow;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
						finderArgs, new ArrayList<ExpandoRow>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ExpandoRow)result;
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoRow> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoRow> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoRow> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoRow> list = (List<ExpandoRow>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_EXPANDOROW);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_EXPANDOROW;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<ExpandoRow>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoRow>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoRow>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoRow expandoRow : findByTableId(tableId)) {
			remove(expandoRow);
		}
	}

	public void removeByT_C(long tableId, long classPK)
		throws NoSuchRowException, SystemException {
		ExpandoRow expandoRow = findByT_C(tableId, classPK);

		remove(expandoRow);
	}

	public void removeAll() throws SystemException {
		for (ExpandoRow expandoRow : findAll()) {
			remove(expandoRow);
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

				query.append(_SQL_COUNT_EXPANDOROW_WHERE);

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

	public int countByT_C(long tableId, long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOROW_WHERE);

				query.append(_FINDER_COLUMN_T_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_EXPANDOROW);

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
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoRow")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoRow>> listenersList = new ArrayList<ModelListener<ExpandoRow>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoRow>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_EXPANDOROW = "SELECT expandoRow FROM ExpandoRow expandoRow";
	private static final String _SQL_SELECT_EXPANDOROW_WHERE = "SELECT expandoRow FROM ExpandoRow expandoRow WHERE ";
	private static final String _SQL_COUNT_EXPANDOROW = "SELECT COUNT(expandoRow) FROM ExpandoRow expandoRow";
	private static final String _SQL_COUNT_EXPANDOROW_WHERE = "SELECT COUNT(expandoRow) FROM ExpandoRow expandoRow WHERE ";
	private static final String _FINDER_COLUMN_TABLEID_TABLEID_2 = "expandoRow.tableId = ?";
	private static final String _FINDER_COLUMN_T_C_TABLEID_2 = "expandoRow.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_CLASSPK_2 = "expandoRow.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoRow.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ExpandoRow exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ExpandoRow exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ExpandoRowPersistenceImpl.class);
}