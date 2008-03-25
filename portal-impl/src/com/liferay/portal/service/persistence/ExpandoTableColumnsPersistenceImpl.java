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

import com.liferay.portal.NoSuchExpandoTableColumnsException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoTableColumns;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExpandoTableColumnsImpl;
import com.liferay.portal.model.impl.ExpandoTableColumnsModelImpl;
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
 * <a href="ExpandoTableColumnsPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableColumnsPersistenceImpl extends BasePersistence
	implements ExpandoTableColumnsPersistence {
	public ExpandoTableColumns create(
		ExpandoTableColumnsPK expandoTableColumnsPK) {
		ExpandoTableColumns expandoTableColumns = new ExpandoTableColumnsImpl();

		expandoTableColumns.setNew(true);
		expandoTableColumns.setPrimaryKey(expandoTableColumnsPK);

		return expandoTableColumns;
	}

	public ExpandoTableColumns remove(
		ExpandoTableColumnsPK expandoTableColumnsPK)
		throws NoSuchExpandoTableColumnsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoTableColumns expandoTableColumns = (ExpandoTableColumns)session.get(ExpandoTableColumnsImpl.class,
					expandoTableColumnsPK);

			if (expandoTableColumns == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ExpandoTableColumns exists with the primary key " +
						expandoTableColumnsPK);
				}

				throw new NoSuchExpandoTableColumnsException(
					"No ExpandoTableColumns exists with the primary key " +
					expandoTableColumnsPK);
			}

			return remove(expandoTableColumns);
		}
		catch (NoSuchExpandoTableColumnsException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoTableColumns remove(ExpandoTableColumns expandoTableColumns)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(expandoTableColumns);
		}

		expandoTableColumns = removeImpl(expandoTableColumns);

		if (listener != null) {
			listener.onAfterRemove(expandoTableColumns);
		}

		return expandoTableColumns;
	}

	protected ExpandoTableColumns removeImpl(
		ExpandoTableColumns expandoTableColumns) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoTableColumns);

			session.flush();

			return expandoTableColumns;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoTableColumns.class.getName());
		}
	}

	public ExpandoTableColumns update(ExpandoTableColumns expandoTableColumns)
		throws SystemException {
		return update(expandoTableColumns, false);
	}

	public ExpandoTableColumns update(ExpandoTableColumns expandoTableColumns,
		boolean merge) throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = expandoTableColumns.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(expandoTableColumns);
			}
			else {
				listener.onBeforeUpdate(expandoTableColumns);
			}
		}

		expandoTableColumns = updateImpl(expandoTableColumns, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(expandoTableColumns);
			}
			else {
				listener.onAfterUpdate(expandoTableColumns);
			}
		}

		return expandoTableColumns;
	}

	public ExpandoTableColumns updateImpl(
		com.liferay.portal.model.ExpandoTableColumns expandoTableColumns,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoTableColumns);
			}
			else {
				if (expandoTableColumns.isNew()) {
					session.save(expandoTableColumns);
				}
			}

			session.flush();

			expandoTableColumns.setNew(false);

			return expandoTableColumns;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoTableColumns.class.getName());
		}
	}

	public ExpandoTableColumns findByPrimaryKey(
		ExpandoTableColumnsPK expandoTableColumnsPK)
		throws NoSuchExpandoTableColumnsException, SystemException {
		ExpandoTableColumns expandoTableColumns = fetchByPrimaryKey(expandoTableColumnsPK);

		if (expandoTableColumns == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoTableColumns exists with the primary key " +
					expandoTableColumnsPK);
			}

			throw new NoSuchExpandoTableColumnsException(
				"No ExpandoTableColumns exists with the primary key " +
				expandoTableColumnsPK);
		}

		return expandoTableColumns;
	}

	public ExpandoTableColumns fetchByPrimaryKey(
		ExpandoTableColumnsPK expandoTableColumnsPK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoTableColumns)session.get(ExpandoTableColumnsImpl.class,
				expandoTableColumnsPK);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoTableColumns> findWithDynamicQuery(
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

	public List<ExpandoTableColumns> findWithDynamicQuery(
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

	public List<ExpandoTableColumns> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoTableColumns> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoTableColumns> findAll(int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableColumnsModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableColumns.class.getName();
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
					"FROM com.liferay.portal.model.ExpandoTableColumns ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoTableColumns> list = (List<ExpandoTableColumns>)QueryUtil.list(q,
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
			return (List<ExpandoTableColumns>)result;
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoTableColumns expandoTableColumns : findAll()) {
			remove(expandoTableColumns);
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoTableColumnsModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoTableColumns.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.ExpandoTableColumns");

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
				"value.object.listener.com.liferay.portal.model.ExpandoTableColumns"));
	private static Log _log = LogFactory.getLog(ExpandoTableColumnsPersistenceImpl.class);
}