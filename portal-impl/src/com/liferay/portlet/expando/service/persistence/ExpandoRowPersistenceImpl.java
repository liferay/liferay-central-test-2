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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.impl.ExpandoRowImpl;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoRowPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowPersistenceImpl extends BasePersistence
	implements ExpandoRowPersistence {
	public ExpandoRow create(long rowId) {
		ExpandoRow expandoRow = new ExpandoRowImpl();

		expandoRow.setNew(true);
		expandoRow.setPrimaryKey(rowId);

		return expandoRow;
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
					_log.warn("No ExpandoRow exists with the primary key " +
						rowId);
				}

				throw new NoSuchRowException(
					"No ExpandoRow exists with the primary key " + rowId);
			}

			return remove(expandoRow);
		}
		catch (NoSuchRowException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoRow remove(ExpandoRow expandoRow) throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(expandoRow);
			}
		}

		expandoRow = removeImpl(expandoRow);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(expandoRow);
			}
		}

		return expandoRow;
	}

	protected ExpandoRow removeImpl(ExpandoRow expandoRow)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoRow);

			session.flush();

			return expandoRow;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoRow.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ExpandoRow expandoRow, boolean merge)</code>.
	 */
	public ExpandoRow update(ExpandoRow expandoRow) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoRow expandoRow) method. Use update(ExpandoRow expandoRow, boolean merge) instead.");
		}

		return update(expandoRow, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoRow the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoRow is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoRow update(ExpandoRow expandoRow, boolean merge)
		throws SystemException {
		boolean isNew = expandoRow.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(expandoRow);
				}
				else {
					listener.onBeforeUpdate(expandoRow);
				}
			}
		}

		expandoRow = updateImpl(expandoRow, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(expandoRow);
				}
				else {
					listener.onAfterUpdate(expandoRow);
				}
			}
		}

		return expandoRow;
	}

	public ExpandoRow updateImpl(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoRow);
			}
			else {
				if (expandoRow.isNew()) {
					session.save(expandoRow);
				}
			}

			session.flush();

			expandoRow.setNew(false);

			return expandoRow;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoRow.class.getName());
		}
	}

	public ExpandoRow findByPrimaryKey(long rowId)
		throws NoSuchRowException, SystemException {
		ExpandoRow expandoRow = fetchByPrimaryKey(rowId);

		if (expandoRow == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoRow exists with the primary key " + rowId);
			}

			throw new NoSuchRowException(
				"No ExpandoRow exists with the primary key " + rowId);
		}

		return expandoRow;
	}

	public ExpandoRow fetchByPrimaryKey(long rowId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoRow)session.get(ExpandoRowImpl.class, new Long(rowId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoRow> findByTableId(long tableId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
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
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, tableId);

				List<ExpandoRow> list = q.list();

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
			return (List<ExpandoRow>)result;
		}
	}

	public List<ExpandoRow> findByTableId(long tableId, int begin, int end)
		throws SystemException {
		return findByTableId(tableId, begin, end, null);
	}

	public List<ExpandoRow> findByTableId(long tableId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
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
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, tableId);

				List<ExpandoRow> list = (List<ExpandoRow>)QueryUtil.list(q,
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
			return (List<ExpandoRow>)result;
		}
	}

	public ExpandoRow findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchRowException, SystemException {
		List<ExpandoRow> list = findByTableId(tableId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoRow exists with the key {");

			msg.append("tableId=" + tableId);

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

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoRow exists with the key {");

			msg.append("tableId=" + tableId);

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

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

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
					expandoRow);

			ExpandoRow[] array = new ExpandoRowImpl[3];

			array[0] = (ExpandoRow)objArray[0];
			array[1] = (ExpandoRow)objArray[1];
			array[2] = (ExpandoRow)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoRow> findWithDynamicQuery(
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

	public List<ExpandoRow> findWithDynamicQuery(
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

	public List<ExpandoRow> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoRow> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoRow> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
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

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoRow> list = (List<ExpandoRow>)QueryUtil.list(q,
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
			return (List<ExpandoRow>)result;
		}
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoRow expandoRow : findByTableId(tableId)) {
			remove(expandoRow);
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoRow expandoRow : findAll()) {
			remove(expandoRow);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
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
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

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
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.expando.model.ExpandoRow");

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

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoRow")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(ExpandoRowPersistenceImpl.class);
	private ModelListener[] _listeners;
}