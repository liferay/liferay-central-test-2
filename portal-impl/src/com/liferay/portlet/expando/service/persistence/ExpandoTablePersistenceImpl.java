/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.impl.ExpandoTableImpl;
import com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ExpandoTablePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTablePersistenceImpl extends BasePersistenceImpl
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

	public ExpandoTable create(long tableId) {
		ExpandoTable expandoTable = new ExpandoTableImpl();

		expandoTable.setNew(true);
		expandoTable.setPrimaryKey(tableId);

		return expandoTable;
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
					_log.warn("No ExpandoTable exists with the primary key " +
						tableId);
				}

				throw new NoSuchTableException(
					"No ExpandoTable exists with the primary key " + tableId);
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

	/**
	 * @deprecated Use <code>update(ExpandoTable expandoTable, boolean merge)</code>.
	 */
	public ExpandoTable update(ExpandoTable expandoTable)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoTable expandoTable) method. Use update(ExpandoTable expandoTable, boolean merge) instead.");
		}

		return update(expandoTable, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoTable the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoTable is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoTable update(ExpandoTable expandoTable, boolean merge)
		throws SystemException {
		boolean isNew = expandoTable.isNew();

		for (ModelListener<ExpandoTable> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(expandoTable);
			}
			else {
				listener.onBeforeUpdate(expandoTable);
			}
		}

		expandoTable = updateImpl(expandoTable, merge);

		for (ModelListener<ExpandoTable> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(expandoTable);
			}
			else {
				listener.onAfterUpdate(expandoTable);
			}
		}

		return expandoTable;
	}

	public ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge) throws SystemException {
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
				!expandoTable.getName()
								 .equals(expandoTableModelImpl.getOriginalName()))) {
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
				!expandoTable.getName()
								 .equals(expandoTableModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_N,
				new Object[] {
					new Long(expandoTable.getCompanyId()),
					new Long(expandoTable.getClassNameId()),
					
				expandoTable.getName()
				}, expandoTable);
		}

		return expandoTable;
	}

	public ExpandoTable findByPrimaryKey(long tableId)
		throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = fetchByPrimaryKey(tableId);

		if (expandoTable == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoTable exists with the primary key " +
					tableId);
			}

			throw new NoSuchTableException(
				"No ExpandoTable exists with the primary key " + tableId);
		}

		return expandoTable;
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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoTable WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoTable> list = (List<ExpandoTable>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoTable WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

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
		OrderByComparator obc) throws NoSuchTableException, SystemException {
		List<ExpandoTable> list = findByC_C(companyId, classNameId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoTable exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTableException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTable findByC_C_Last(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchTableException, SystemException {
		int count = countByC_C(companyId, classNameId);

		List<ExpandoTable> list = findByC_C(companyId, classNameId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoTable exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTableException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTable[] findByC_C_PrevAndNext(long tableId, long companyId,
		long classNameId, OrderByComparator obc)
		throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = findByPrimaryKey(tableId);

		int count = countByC_C(companyId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoTable WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoTable);

			ExpandoTable[] array = new ExpandoTableImpl[3];

			array[0] = (ExpandoTable)objArray[0];
			array[1] = (ExpandoTable)objArray[1];
			array[2] = (ExpandoTable)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoTable findByC_C_N(long companyId, long classNameId,
		String name) throws NoSuchTableException, SystemException {
		ExpandoTable expandoTable = fetchByC_C_N(companyId, classNameId, name);

		if (expandoTable == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoTable exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("name=" + name);

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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoTable WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
			if (result instanceof List) {
				return null;
			}
			else {
				return (ExpandoTable)result;
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

	public List<ExpandoTable> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoTable> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoTable> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoTable> list = (List<ExpandoTable>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoTable ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoTable WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoTable WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.expando.model.ExpandoTable");

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

	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(ExpandoTablePersistenceImpl.class);
}