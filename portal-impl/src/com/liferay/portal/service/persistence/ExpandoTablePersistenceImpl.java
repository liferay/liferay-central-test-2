/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchExpandoTableException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoTable;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExpandoTableImpl;
import com.liferay.portal.model.impl.ExpandoTableModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoTablePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTablePersistenceImpl extends BasePersistence
	implements ExpandoTablePersistence {
	public ExpandoTable create(long tableId) {
		ExpandoTable expandoTable = new ExpandoTableImpl();

		expandoTable.setNew(true);
		expandoTable.setPrimaryKey(tableId);

		return expandoTable;
	}

	public ExpandoTable remove(long tableId)
		throws NoSuchExpandoTableException, SystemException {
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

				throw new NoSuchExpandoTableException(
					"No ExpandoTable exists with the primary key " + tableId);
			}

			return remove(expandoTable);
		}
		catch (NoSuchExpandoTableException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoTable remove(ExpandoTable expandoTable)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(expandoTable);
		}

		expandoTable = removeImpl(expandoTable);

		if (listener != null) {
			listener.onAfterRemove(expandoTable);
		}

		return expandoTable;
	}

	protected ExpandoTable removeImpl(ExpandoTable expandoTable)
		throws SystemException {
		try {
			clearExpandoColumns.clear(expandoTable.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}

		Session session = null;

		try {
			session = openSession();

			session.delete(expandoTable);

			session.flush();

			return expandoTable;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoTable.class.getName());
		}
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
		ModelListener listener = _getListener();

		boolean isNew = expandoTable.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(expandoTable);
			}
			else {
				listener.onBeforeUpdate(expandoTable);
			}
		}

		expandoTable = updateImpl(expandoTable, merge);

		if (listener != null) {
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
		com.liferay.portal.model.ExpandoTable expandoTable, boolean merge)
		throws SystemException {
		FinderCache.clearCache("ExpandoTables_ExpandoColumns");

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoTable);
			}
			else {
				if (expandoTable.isNew()) {
					session.save(expandoTable);
				}
			}

			session.flush();

			expandoTable.setNew(false);

			return expandoTable;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoTable.class.getName());
		}
	}

	public ExpandoTable findByPrimaryKey(long tableId)
		throws NoSuchExpandoTableException, SystemException {
		ExpandoTable expandoTable = fetchByPrimaryKey(tableId);

		if (expandoTable == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoTable exists with the primary key " +
					tableId);
			}

			throw new NoSuchExpandoTableException(
				"No ExpandoTable exists with the primary key " + tableId);
		}

		return expandoTable;
	}

	public ExpandoTable fetchByPrimaryKey(long tableId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoTable)session.get(ExpandoTableImpl.class,
				new Long(tableId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoTable> findByClassNameId(long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "findByClassNameId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(classNameId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ExpandoTable WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				List<ExpandoTable> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoTable>)result;
		}
	}

	public List<ExpandoTable> findByClassNameId(long classNameId, int begin,
		int end) throws SystemException {
		return findByClassNameId(classNameId, begin, end, null);
	}

	public List<ExpandoTable> findByClassNameId(long classNameId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "findByClassNameId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ExpandoTable WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				List<ExpandoTable> list = (List<ExpandoTable>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoTable>)result;
		}
	}

	public ExpandoTable findByClassNameId_First(long classNameId,
		OrderByComparator obc)
		throws NoSuchExpandoTableException, SystemException {
		List<ExpandoTable> list = findByClassNameId(classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoTable exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoTableException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTable findByClassNameId_Last(long classNameId,
		OrderByComparator obc)
		throws NoSuchExpandoTableException, SystemException {
		int count = countByClassNameId(classNameId);

		List<ExpandoTable> list = findByClassNameId(classNameId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoTable exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoTableException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTable[] findByClassNameId_PrevAndNext(long tableId,
		long classNameId, OrderByComparator obc)
		throws NoSuchExpandoTableException, SystemException {
		ExpandoTable expandoTable = findByPrimaryKey(tableId);

		int count = countByClassNameId(classNameId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ExpandoTable WHERE ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoTable);

			ExpandoTable[] array = new ExpandoTableImpl[3];

			array[0] = (ExpandoTable)objArray[0];
			array[1] = (ExpandoTable)objArray[1];
			array[2] = (ExpandoTable)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoTable findByC_N(long classNameId, String name)
		throws NoSuchExpandoTableException, SystemException {
		ExpandoTable expandoTable = fetchByC_N(classNameId, name);

		if (expandoTable == null) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoTable exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchExpandoTableException(msg.toString());
		}

		return expandoTable;
	}

	public ExpandoTable fetchByC_N(long classNameId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "fetchByC_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(classNameId), name };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ExpandoTable WHERE ");

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

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				if (name != null) {
					q.setString(queryPos++, name);
				}

				List<ExpandoTable> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ExpandoTable> list = (List<ExpandoTable>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ExpandoTable> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoTable> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoTable> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoTable> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoTable> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.ExpandoTable ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoTable> list = (List<ExpandoTable>)QueryUtil.list(q,
						getDialect(), begin, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoTable>)result;
		}
	}

	public void removeByClassNameId(long classNameId) throws SystemException {
		for (ExpandoTable expandoTable : findByClassNameId(classNameId)) {
			remove(expandoTable);
		}
	}

	public void removeByC_N(long classNameId, String name)
		throws NoSuchExpandoTableException, SystemException {
		ExpandoTable expandoTable = findByC_N(classNameId, name);

		remove(expandoTable);
	}

	public void removeAll() throws SystemException {
		for (ExpandoTable expandoTable : findAll()) {
			remove(expandoTable);
		}
	}

	public int countByClassNameId(long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "countByClassNameId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(classNameId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ExpandoTable WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_N(long classNameId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "countByC_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(classNameId), name };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ExpandoTable WHERE ");

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

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				if (name != null) {
					q.setString(queryPos++, name);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTable.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.ExpandoTable");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public List<com.liferay.portal.model.ExpandoColumn> getExpandoColumns(
		long pk) throws NoSuchExpandoTableException, SystemException {
		return getExpandoColumns(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.ExpandoColumn> getExpandoColumns(
		long pk, int begin, int end)
		throws NoSuchExpandoTableException, SystemException {
		return getExpandoColumns(pk, begin, end, null);
	}

	public List<com.liferay.portal.model.ExpandoColumn> getExpandoColumns(
		long pk, int begin, int end, OrderByComparator obc)
		throws NoSuchExpandoTableException, SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED_EXPANDOTABLES_EXPANDOCOLUMNS;

		String finderClassName = "ExpandoTables_ExpandoColumns";

		String finderMethodName = "getExpandoColumns";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(begin), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = HibernateUtil.openSession();

				StringMaker sm = new StringMaker();

				sm.append(_SQL_GETEXPANDOCOLUMNS);

				if (obc != null) {
					sm.append("ORDER BY ");
					sm.append(obc.getOrderBy());
				}

				String sql = sm.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("ExpandoColumn",
					com.liferay.portal.model.impl.ExpandoColumnImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.ExpandoColumn> list = (List<com.liferay.portal.model.ExpandoColumn>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portal.model.ExpandoColumn>)result;
		}
	}

	public int getExpandoColumnsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED_EXPANDOTABLES_EXPANDOCOLUMNS;

		String finderClassName = "ExpandoTables_ExpandoColumns";

		String finderMethodName = "getExpandoColumnsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETEXPANDOCOLUMNSSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsExpandoColumn(long pk, long expandoColumnPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableModelImpl.CACHE_ENABLED_EXPANDOTABLES_EXPANDOCOLUMNS;

		String finderClassName = "ExpandoTables_ExpandoColumns";

		String finderMethodName = "containsExpandoColumns";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(expandoColumnPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsExpandoColumn.contains(
							pk, expandoColumnPK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsExpandoColumns(long pk) throws SystemException {
		if (getExpandoColumnsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addExpandoColumn(long pk, long expandoColumnPK)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			addExpandoColumn.add(pk, expandoColumnPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void addExpandoColumn(long pk,
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			addExpandoColumn.add(pk, expandoColumn.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void addExpandoColumns(long pk, long[] expandoColumnPKs)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			for (int i = 0; i < expandoColumnPKs.length; i++) {
				addExpandoColumn.add(pk, expandoColumnPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void addExpandoColumns(long pk,
		List<com.liferay.portal.model.ExpandoColumn> expandoColumns)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			for (int i = 0; i < expandoColumns.size(); i++) {
				com.liferay.portal.model.ExpandoColumn expandoColumn = expandoColumns.get(i);

				addExpandoColumn.add(pk, expandoColumn.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void clearExpandoColumns(long pk)
		throws NoSuchExpandoTableException, SystemException {
		try {
			clearExpandoColumns.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void removeExpandoColumn(long pk, long expandoColumnPK)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			removeExpandoColumn.remove(pk, expandoColumnPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void removeExpandoColumn(long pk,
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			removeExpandoColumn.remove(pk, expandoColumn.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void removeExpandoColumns(long pk, long[] expandoColumnPKs)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			for (int i = 0; i < expandoColumnPKs.length; i++) {
				removeExpandoColumn.remove(pk, expandoColumnPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void removeExpandoColumns(long pk,
		List<com.liferay.portal.model.ExpandoColumn> expandoColumns)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			for (int i = 0; i < expandoColumns.size(); i++) {
				com.liferay.portal.model.ExpandoColumn expandoColumn = expandoColumns.get(i);

				removeExpandoColumn.remove(pk, expandoColumn.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void setExpandoColumns(long pk, long[] expandoColumnPKs)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			clearExpandoColumns.clear(pk);

			for (int i = 0; i < expandoColumnPKs.length; i++) {
				addExpandoColumn.add(pk, expandoColumnPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	public void setExpandoColumns(long pk,
		List<com.liferay.portal.model.ExpandoColumn> expandoColumns)
		throws NoSuchExpandoTableException,
			com.liferay.portal.NoSuchExpandoColumnException, SystemException {
		try {
			clearExpandoColumns.clear(pk);

			for (int i = 0; i < expandoColumns.size(); i++) {
				com.liferay.portal.model.ExpandoColumn expandoColumn = expandoColumns.get(i);

				addExpandoColumn.add(pk, expandoColumn.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTables_ExpandoColumns");
		}
	}

	protected void initDao() {
		containsExpandoColumn = new ContainsExpandoColumn(this);

		addExpandoColumn = new AddExpandoColumn(this);
		clearExpandoColumns = new ClearExpandoColumns(this);
		removeExpandoColumn = new RemoveExpandoColumn(this);
	}

	protected ContainsExpandoColumn containsExpandoColumn;
	protected AddExpandoColumn addExpandoColumn;
	protected ClearExpandoColumns clearExpandoColumns;
	protected RemoveExpandoColumn removeExpandoColumn;

	protected class ContainsExpandoColumn extends MappingSqlQuery {
		protected ContainsExpandoColumn(
			ExpandoTablePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSEXPANDOCOLUMN);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long tableId, long columnId) {
			List<Integer> results = execute(new Object[] {
						new Long(tableId), new Long(columnId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddExpandoColumn extends SqlUpdate {
		protected AddExpandoColumn(ExpandoTablePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO ExpandoTables_ExpandoColumns (tableId, columnId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long tableId, long columnId) {
			if (!_persistenceImpl.containsExpandoColumn.contains(tableId,
						columnId)) {
				update(new Object[] { new Long(tableId), new Long(columnId) });
			}
		}

		private ExpandoTablePersistenceImpl _persistenceImpl;
	}

	protected class ClearExpandoColumns extends SqlUpdate {
		protected ClearExpandoColumns(
			ExpandoTablePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM ExpandoTables_ExpandoColumns WHERE tableId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long tableId) {
			update(new Object[] { new Long(tableId) });
		}
	}

	protected class RemoveExpandoColumn extends SqlUpdate {
		protected RemoveExpandoColumn(
			ExpandoTablePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM ExpandoTables_ExpandoColumns WHERE tableId = ? AND columnId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long tableId, long columnId) {
			update(new Object[] { new Long(tableId), new Long(columnId) });
		}
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _SQL_GETEXPANDOCOLUMNS = "SELECT {ExpandoColumn.*} FROM ExpandoColumn INNER JOIN ExpandoTables_ExpandoColumns ON (ExpandoTables_ExpandoColumns.columnId = ExpandoColumn.columnId) WHERE (ExpandoTables_ExpandoColumns.tableId = ?)";
	private static final String _SQL_GETEXPANDOCOLUMNSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ExpandoTables_ExpandoColumns WHERE tableId = ?";
	private static final String _SQL_CONTAINSEXPANDOCOLUMN = "SELECT COUNT(*) AS COUNT_VALUE FROM ExpandoTables_ExpandoColumns WHERE tableId = ? AND columnId = ?";
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.ExpandoTable"));
	private static Log _log = LogFactory.getLog(ExpandoTablePersistenceImpl.class);
}