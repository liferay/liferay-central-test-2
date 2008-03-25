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

import com.liferay.portal.NoSuchExpandoColumnException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExpandoColumnImpl;
import com.liferay.portal.model.impl.ExpandoColumnModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoColumnPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnPersistenceImpl extends BasePersistence
	implements ExpandoColumnPersistence {
	public ExpandoColumn create(long columnId) {
		ExpandoColumn expandoColumn = new ExpandoColumnImpl();

		expandoColumn.setNew(true);
		expandoColumn.setPrimaryKey(columnId);

		return expandoColumn;
	}

	public ExpandoColumn remove(long columnId)
		throws NoSuchExpandoColumnException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoColumn expandoColumn = (ExpandoColumn)session.get(ExpandoColumnImpl.class,
					new Long(columnId));

			if (expandoColumn == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoColumn exists with the primary key " +
						columnId);
				}

				throw new NoSuchExpandoColumnException(
					"No ExpandoColumn exists with the primary key " + columnId);
			}

			return remove(expandoColumn);
		}
		catch (NoSuchExpandoColumnException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(expandoColumn);
		}

		expandoColumn = removeImpl(expandoColumn);

		if (listener != null) {
			listener.onAfterRemove(expandoColumn);
		}

		return expandoColumn;
	}

	protected ExpandoColumn removeImpl(ExpandoColumn expandoColumn)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoColumn);

			session.flush();

			return expandoColumn;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoColumn.class.getName());
		}
	}

	public ExpandoColumn update(ExpandoColumn expandoColumn)
		throws SystemException {
		return update(expandoColumn, false);
	}

	public ExpandoColumn update(ExpandoColumn expandoColumn, boolean merge)
		throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = expandoColumn.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(expandoColumn);
			}
			else {
				listener.onBeforeUpdate(expandoColumn);
			}
		}

		expandoColumn = updateImpl(expandoColumn, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(expandoColumn);
			}
			else {
				listener.onAfterUpdate(expandoColumn);
			}
		}

		return expandoColumn;
	}

	public ExpandoColumn updateImpl(
		com.liferay.portal.model.ExpandoColumn expandoColumn, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoColumn);
			}
			else {
				if (expandoColumn.isNew()) {
					session.save(expandoColumn);
				}
			}

			session.flush();

			expandoColumn.setNew(false);

			return expandoColumn;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoColumn.class.getName());
		}
	}

	public ExpandoColumn findByPrimaryKey(long columnId)
		throws NoSuchExpandoColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByPrimaryKey(columnId);

		if (expandoColumn == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoColumn exists with the primary key " +
					columnId);
			}

			throw new NoSuchExpandoColumnException(
				"No ExpandoColumn exists with the primary key " + columnId);
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByPrimaryKey(long columnId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoColumn)session.get(ExpandoColumnImpl.class,
				new Long(columnId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoColumn> findByClassNameId(long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
					"FROM com.liferay.portal.model.ExpandoColumn WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				List<ExpandoColumn> list = q.list();

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
			return (List<ExpandoColumn>)result;
		}
	}

	public List<ExpandoColumn> findByClassNameId(long classNameId, int begin,
		int end) throws SystemException {
		return findByClassNameId(classNameId, begin, end, null);
	}

	public List<ExpandoColumn> findByClassNameId(long classNameId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
					"FROM com.liferay.portal.model.ExpandoColumn WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				List<ExpandoColumn> list = (List<ExpandoColumn>)QueryUtil.list(q,
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
			return (List<ExpandoColumn>)result;
		}
	}

	public ExpandoColumn findByClassNameId_First(long classNameId,
		OrderByComparator obc)
		throws NoSuchExpandoColumnException, SystemException {
		List<ExpandoColumn> list = findByClassNameId(classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn findByClassNameId_Last(long classNameId,
		OrderByComparator obc)
		throws NoSuchExpandoColumnException, SystemException {
		int count = countByClassNameId(classNameId);

		List<ExpandoColumn> list = findByClassNameId(classNameId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn[] findByClassNameId_PrevAndNext(long columnId,
		long classNameId, OrderByComparator obc)
		throws NoSuchExpandoColumnException, SystemException {
		ExpandoColumn expandoColumn = findByPrimaryKey(columnId);

		int count = countByClassNameId(classNameId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ExpandoColumn WHERE ");

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
					expandoColumn);

			ExpandoColumn[] array = new ExpandoColumnImpl[3];

			array[0] = (ExpandoColumn)objArray[0];
			array[1] = (ExpandoColumn)objArray[1];
			array[2] = (ExpandoColumn)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn findByC_N(long classNameId, String name)
		throws NoSuchExpandoColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByC_N(classNameId, name);

		if (expandoColumn == null) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchExpandoColumnException(msg.toString());
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByC_N(long classNameId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
					"FROM com.liferay.portal.model.ExpandoColumn WHERE ");

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

				List<ExpandoColumn> list = q.list();

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
			List<ExpandoColumn> list = (List<ExpandoColumn>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ExpandoColumn> findWithDynamicQuery(
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

	public List<ExpandoColumn> findWithDynamicQuery(
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

	public List<ExpandoColumn> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoColumn> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoColumn> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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

				query.append("FROM com.liferay.portal.model.ExpandoColumn ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoColumn> list = (List<ExpandoColumn>)QueryUtil.list(q,
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
			return (List<ExpandoColumn>)result;
		}
	}

	public void removeByClassNameId(long classNameId) throws SystemException {
		for (ExpandoColumn expandoColumn : findByClassNameId(classNameId)) {
			remove(expandoColumn);
		}
	}

	public void removeByC_N(long classNameId, String name)
		throws NoSuchExpandoColumnException, SystemException {
		ExpandoColumn expandoColumn = findByC_N(classNameId, name);

		remove(expandoColumn);
	}

	public void removeAll() throws SystemException {
		for (ExpandoColumn expandoColumn : findAll()) {
			remove(expandoColumn);
		}
	}

	public int countByClassNameId(long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
					"FROM com.liferay.portal.model.ExpandoColumn WHERE ");

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
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
					"FROM com.liferay.portal.model.ExpandoColumn WHERE ");

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
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.ExpandoColumn");

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

	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.ExpandoColumn"));
	private static Log _log = LogFactory.getLog(ExpandoColumnPersistenceImpl.class);
}