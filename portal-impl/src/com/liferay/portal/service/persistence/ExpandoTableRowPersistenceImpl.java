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

import com.liferay.portal.NoSuchExpandoTableRowException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoTableRow;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExpandoTableRowImpl;
import com.liferay.portal.model.impl.ExpandoTableRowModelImpl;
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
 * <a href="ExpandoTableRowPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableRowPersistenceImpl extends BasePersistence
	implements ExpandoTableRowPersistence {
	public ExpandoTableRow create(long rowId) {
		ExpandoTableRow expandoTableRow = new ExpandoTableRowImpl();

		expandoTableRow.setNew(true);
		expandoTableRow.setPrimaryKey(rowId);

		return expandoTableRow;
	}

	public ExpandoTableRow remove(long rowId)
		throws NoSuchExpandoTableRowException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoTableRow expandoTableRow = (ExpandoTableRow)session.get(ExpandoTableRowImpl.class,
					new Long(rowId));

			if (expandoTableRow == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoTableRow exists with the primary key " +
						rowId);
				}

				throw new NoSuchExpandoTableRowException(
					"No ExpandoTableRow exists with the primary key " + rowId);
			}

			return remove(expandoTableRow);
		}
		catch (NoSuchExpandoTableRowException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoTableRow remove(ExpandoTableRow expandoTableRow)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(expandoTableRow);
		}

		expandoTableRow = removeImpl(expandoTableRow);

		if (listener != null) {
			listener.onAfterRemove(expandoTableRow);
		}

		return expandoTableRow;
	}

	protected ExpandoTableRow removeImpl(ExpandoTableRow expandoTableRow)
		throws SystemException {
		try {
			clearExpandoValues.clear(expandoTableRow.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}

		Session session = null;

		try {
			session = openSession();

			session.delete(expandoTableRow);

			session.flush();

			return expandoTableRow;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoTableRow.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ExpandoTableRow expandoTableRow, boolean merge)</code>.
	 */
	public ExpandoTableRow update(ExpandoTableRow expandoTableRow)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoTableRow expandoTableRow) method. To improve performance, call update(ExpandoTableRow expandoTableRow, boolean merge).");
		}

		return update(expandoTableRow, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoTableRow the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoTableRow is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoTableRow update(ExpandoTableRow expandoTableRow, boolean merge)
		throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = expandoTableRow.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(expandoTableRow);
			}
			else {
				listener.onBeforeUpdate(expandoTableRow);
			}
		}

		expandoTableRow = updateImpl(expandoTableRow, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(expandoTableRow);
			}
			else {
				listener.onAfterUpdate(expandoTableRow);
			}
		}

		return expandoTableRow;
	}

	public ExpandoTableRow updateImpl(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow, boolean merge)
		throws SystemException {
		FinderCache.clearCache("ExpandoTableRows_ExpandoValues");

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoTableRow);
			}
			else {
				if (expandoTableRow.isNew()) {
					session.save(expandoTableRow);
				}
			}

			session.flush();

			expandoTableRow.setNew(false);

			return expandoTableRow;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoTableRow.class.getName());
		}
	}

	public ExpandoTableRow findByPrimaryKey(long rowId)
		throws NoSuchExpandoTableRowException, SystemException {
		ExpandoTableRow expandoTableRow = fetchByPrimaryKey(rowId);

		if (expandoTableRow == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoTableRow exists with the primary key " +
					rowId);
			}

			throw new NoSuchExpandoTableRowException(
				"No ExpandoTableRow exists with the primary key " + rowId);
		}

		return expandoTableRow;
	}

	public ExpandoTableRow fetchByPrimaryKey(long rowId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoTableRow)session.get(ExpandoTableRowImpl.class,
				new Long(rowId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoTableRow> findByTableId(long tableId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableRow.class.getName();
		String finderMethodName = "findByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

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
					"FROM com.liferay.portal.model.ExpandoTableRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, tableId);

				List<ExpandoTableRow> list = q.list();

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
			return (List<ExpandoTableRow>)result;
		}
	}

	public List<ExpandoTableRow> findByTableId(long tableId, int begin, int end)
		throws SystemException {
		return findByTableId(tableId, begin, end, null);
	}

	public List<ExpandoTableRow> findByTableId(long tableId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableRow.class.getName();
		String finderMethodName = "findByTableId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
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
					"FROM com.liferay.portal.model.ExpandoTableRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, tableId);

				List<ExpandoTableRow> list = (List<ExpandoTableRow>)QueryUtil.list(q,
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
			return (List<ExpandoTableRow>)result;
		}
	}

	public ExpandoTableRow findByTableId_First(long tableId,
		OrderByComparator obc)
		throws NoSuchExpandoTableRowException, SystemException {
		List<ExpandoTableRow> list = findByTableId(tableId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoTableRow exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoTableRowException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTableRow findByTableId_Last(long tableId,
		OrderByComparator obc)
		throws NoSuchExpandoTableRowException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoTableRow> list = findByTableId(tableId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoTableRow exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoTableRowException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoTableRow[] findByTableId_PrevAndNext(long rowId,
		long tableId, OrderByComparator obc)
		throws NoSuchExpandoTableRowException, SystemException {
		ExpandoTableRow expandoTableRow = findByPrimaryKey(rowId);

		int count = countByTableId(tableId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ExpandoTableRow WHERE ");

			query.append("tableId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoTableRow);

			ExpandoTableRow[] array = new ExpandoTableRowImpl[3];

			array[0] = (ExpandoTableRow)objArray[0];
			array[1] = (ExpandoTableRow)objArray[1];
			array[2] = (ExpandoTableRow)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoTableRow> findWithDynamicQuery(
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

	public List<ExpandoTableRow> findWithDynamicQuery(
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

	public List<ExpandoTableRow> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoTableRow> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoTableRow> findAll(int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableRow.class.getName();
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

				query.append("FROM com.liferay.portal.model.ExpandoTableRow ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoTableRow> list = (List<ExpandoTableRow>)QueryUtil.list(q,
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
			return (List<ExpandoTableRow>)result;
		}
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoTableRow expandoTableRow : findByTableId(tableId)) {
			remove(expandoTableRow);
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoTableRow expandoTableRow : findAll()) {
			remove(expandoTableRow);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableRow.class.getName();
		String finderMethodName = "countByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

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
					"FROM com.liferay.portal.model.ExpandoTableRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, tableId);

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
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableRow.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.ExpandoTableRow");

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

	public List<com.liferay.portal.model.ExpandoValue> getExpandoValues(long pk)
		throws NoSuchExpandoTableRowException, SystemException {
		return getExpandoValues(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk, int begin, int end)
		throws NoSuchExpandoTableRowException, SystemException {
		return getExpandoValues(pk, begin, end, null);
	}

	public List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk, int begin, int end, OrderByComparator obc)
		throws NoSuchExpandoTableRowException, SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED_EXPANDOTABLEROWS_EXPANDOVALUES;

		String finderClassName = "ExpandoTableRows_ExpandoValues";

		String finderMethodName = "getExpandoValues";
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

				sm.append(_SQL_GETEXPANDOVALUES);

				if (obc != null) {
					sm.append("ORDER BY ");
					sm.append(obc.getOrderBy());
				}

				String sql = sm.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("ExpandoValue",
					com.liferay.portal.model.impl.ExpandoValueImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.ExpandoValue> list = (List<com.liferay.portal.model.ExpandoValue>)QueryUtil.list(q,
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
			return (List<com.liferay.portal.model.ExpandoValue>)result;
		}
	}

	public int getExpandoValuesSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED_EXPANDOTABLEROWS_EXPANDOVALUES;

		String finderClassName = "ExpandoTableRows_ExpandoValues";

		String finderMethodName = "getExpandoValuesSize";
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

				SQLQuery q = session.createSQLQuery(_SQL_GETEXPANDOVALUESSIZE);

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

	public boolean containsExpandoValue(long pk, long expandoValuePK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableRowModelImpl.CACHE_ENABLED_EXPANDOTABLEROWS_EXPANDOVALUES;

		String finderClassName = "ExpandoTableRows_ExpandoValues";

		String finderMethodName = "containsExpandoValues";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(expandoValuePK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsExpandoValue.contains(
							pk, expandoValuePK));

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

	public boolean containsExpandoValues(long pk) throws SystemException {
		if (getExpandoValuesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addExpandoValue(long pk, long expandoValuePK)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			addExpandoValue.add(pk, expandoValuePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void addExpandoValue(long pk,
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			addExpandoValue.add(pk, expandoValue.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void addExpandoValues(long pk, long[] expandoValuePKs)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			for (int i = 0; i < expandoValuePKs.length; i++) {
				addExpandoValue.add(pk, expandoValuePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void addExpandoValues(long pk,
		List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			for (int i = 0; i < expandoValues.size(); i++) {
				com.liferay.portal.model.ExpandoValue expandoValue = expandoValues.get(i);

				addExpandoValue.add(pk, expandoValue.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void clearExpandoValues(long pk)
		throws NoSuchExpandoTableRowException, SystemException {
		try {
			clearExpandoValues.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void removeExpandoValue(long pk, long expandoValuePK)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			removeExpandoValue.remove(pk, expandoValuePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void removeExpandoValue(long pk,
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			removeExpandoValue.remove(pk, expandoValue.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void removeExpandoValues(long pk, long[] expandoValuePKs)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			for (int i = 0; i < expandoValuePKs.length; i++) {
				removeExpandoValue.remove(pk, expandoValuePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void removeExpandoValues(long pk,
		List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			for (int i = 0; i < expandoValues.size(); i++) {
				com.liferay.portal.model.ExpandoValue expandoValue = expandoValues.get(i);

				removeExpandoValue.remove(pk, expandoValue.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void setExpandoValues(long pk, long[] expandoValuePKs)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			clearExpandoValues.clear(pk);

			for (int i = 0; i < expandoValuePKs.length; i++) {
				addExpandoValue.add(pk, expandoValuePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	public void setExpandoValues(long pk,
		List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws NoSuchExpandoTableRowException,
			com.liferay.portal.NoSuchExpandoValueException, SystemException {
		try {
			clearExpandoValues.clear(pk);

			for (int i = 0; i < expandoValues.size(); i++) {
				com.liferay.portal.model.ExpandoValue expandoValue = expandoValues.get(i);

				addExpandoValue.add(pk, expandoValue.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("ExpandoTableRows_ExpandoValues");
		}
	}

	protected void initDao() {
		containsExpandoValue = new ContainsExpandoValue(this);

		addExpandoValue = new AddExpandoValue(this);
		clearExpandoValues = new ClearExpandoValues(this);
		removeExpandoValue = new RemoveExpandoValue(this);
	}

	protected ContainsExpandoValue containsExpandoValue;
	protected AddExpandoValue addExpandoValue;
	protected ClearExpandoValues clearExpandoValues;
	protected RemoveExpandoValue removeExpandoValue;

	protected class ContainsExpandoValue extends MappingSqlQuery {
		protected ContainsExpandoValue(
			ExpandoTableRowPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSEXPANDOVALUE);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long rowId, long valueId) {
			List<Integer> results = execute(new Object[] {
						new Long(rowId), new Long(valueId)
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

	protected class AddExpandoValue extends SqlUpdate {
		protected AddExpandoValue(
			ExpandoTableRowPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO ExpandoTableRows_ExpandoValues (rowId, valueId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long rowId, long valueId) {
			if (!_persistenceImpl.containsExpandoValue.contains(rowId, valueId)) {
				update(new Object[] { new Long(rowId), new Long(valueId) });
			}
		}

		private ExpandoTableRowPersistenceImpl _persistenceImpl;
	}

	protected class ClearExpandoValues extends SqlUpdate {
		protected ClearExpandoValues(
			ExpandoTableRowPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM ExpandoTableRows_ExpandoValues WHERE rowId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long rowId) {
			update(new Object[] { new Long(rowId) });
		}
	}

	protected class RemoveExpandoValue extends SqlUpdate {
		protected RemoveExpandoValue(
			ExpandoTableRowPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM ExpandoTableRows_ExpandoValues WHERE rowId = ? AND valueId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long rowId, long valueId) {
			update(new Object[] { new Long(rowId), new Long(valueId) });
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

	private static final String _SQL_GETEXPANDOVALUES = "SELECT {ExpandoValue.*} FROM ExpandoValue INNER JOIN ExpandoTableRows_ExpandoValues ON (ExpandoTableRows_ExpandoValues.valueId = ExpandoValue.valueId) WHERE (ExpandoTableRows_ExpandoValues.rowId = ?)";
	private static final String _SQL_GETEXPANDOVALUESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ExpandoTableRows_ExpandoValues WHERE rowId = ?";
	private static final String _SQL_CONTAINSEXPANDOVALUE = "SELECT COUNT(*) AS COUNT_VALUE FROM ExpandoTableRows_ExpandoValues WHERE rowId = ? AND valueId = ?";
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.ExpandoTableRow"));
	private static Log _log = LogFactory.getLog(ExpandoTableRowPersistenceImpl.class);
}