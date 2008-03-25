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

import com.liferay.portal.NoSuchExpandoRowValuesException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoRowValues;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExpandoRowValuesImpl;
import com.liferay.portal.model.impl.ExpandoRowValuesModelImpl;
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
 * <a href="ExpandoRowValuesPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowValuesPersistenceImpl extends BasePersistence
	implements ExpandoRowValuesPersistence {
	public ExpandoRowValues create(ExpandoRowValuesPK expandoRowValuesPK) {
		ExpandoRowValues expandoRowValues = new ExpandoRowValuesImpl();

		expandoRowValues.setNew(true);
		expandoRowValues.setPrimaryKey(expandoRowValuesPK);

		return expandoRowValues;
	}

	public ExpandoRowValues remove(ExpandoRowValuesPK expandoRowValuesPK)
		throws NoSuchExpandoRowValuesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoRowValues expandoRowValues = (ExpandoRowValues)session.get(ExpandoRowValuesImpl.class,
					expandoRowValuesPK);

			if (expandoRowValues == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ExpandoRowValues exists with the primary key " +
						expandoRowValuesPK);
				}

				throw new NoSuchExpandoRowValuesException(
					"No ExpandoRowValues exists with the primary key " +
					expandoRowValuesPK);
			}

			return remove(expandoRowValues);
		}
		catch (NoSuchExpandoRowValuesException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoRowValues remove(ExpandoRowValues expandoRowValues)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(expandoRowValues);
		}

		expandoRowValues = removeImpl(expandoRowValues);

		if (listener != null) {
			listener.onAfterRemove(expandoRowValues);
		}

		return expandoRowValues;
	}

	protected ExpandoRowValues removeImpl(ExpandoRowValues expandoRowValues)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoRowValues);

			session.flush();

			return expandoRowValues;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoRowValues.class.getName());
		}
	}

	public ExpandoRowValues update(ExpandoRowValues expandoRowValues)
		throws SystemException {
		return update(expandoRowValues, false);
	}

	public ExpandoRowValues update(ExpandoRowValues expandoRowValues,
		boolean merge) throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = expandoRowValues.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(expandoRowValues);
			}
			else {
				listener.onBeforeUpdate(expandoRowValues);
			}
		}

		expandoRowValues = updateImpl(expandoRowValues, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(expandoRowValues);
			}
			else {
				listener.onAfterUpdate(expandoRowValues);
			}
		}

		return expandoRowValues;
	}

	public ExpandoRowValues updateImpl(
		com.liferay.portal.model.ExpandoRowValues expandoRowValues,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoRowValues);
			}
			else {
				if (expandoRowValues.isNew()) {
					session.save(expandoRowValues);
				}
			}

			session.flush();

			expandoRowValues.setNew(false);

			return expandoRowValues;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoRowValues.class.getName());
		}
	}

	public ExpandoRowValues findByPrimaryKey(
		ExpandoRowValuesPK expandoRowValuesPK)
		throws NoSuchExpandoRowValuesException, SystemException {
		ExpandoRowValues expandoRowValues = fetchByPrimaryKey(expandoRowValuesPK);

		if (expandoRowValues == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoRowValues exists with the primary key " +
					expandoRowValuesPK);
			}

			throw new NoSuchExpandoRowValuesException(
				"No ExpandoRowValues exists with the primary key " +
				expandoRowValuesPK);
		}

		return expandoRowValues;
	}

	public ExpandoRowValues fetchByPrimaryKey(
		ExpandoRowValuesPK expandoRowValuesPK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoRowValues)session.get(ExpandoRowValuesImpl.class,
				expandoRowValuesPK);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoRowValues> findWithDynamicQuery(
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

	public List<ExpandoRowValues> findWithDynamicQuery(
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

	public List<ExpandoRowValues> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoRowValues> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoRowValues> findAll(int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowValuesModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRowValues.class.getName();
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

				query.append("FROM com.liferay.portal.model.ExpandoRowValues ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoRowValues> list = (List<ExpandoRowValues>)QueryUtil.list(q,
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
			return (List<ExpandoRowValues>)result;
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoRowValues expandoRowValues : findAll()) {
			remove(expandoRowValues);
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowValuesModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRowValues.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.ExpandoRowValues");

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
				"value.object.listener.com.liferay.portal.model.ExpandoRowValues"));
	private static Log _log = LogFactory.getLog(ExpandoRowValuesPersistenceImpl.class);
}