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

import com.liferay.portal.NoSuchExpandoValueException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoValue;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ExpandoValueImpl;
import com.liferay.portal.model.impl.ExpandoValueModelImpl;
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
 * <a href="ExpandoValuePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValuePersistenceImpl extends BasePersistence
	implements ExpandoValuePersistence {
	public ExpandoValue create(long valueId) {
		ExpandoValue expandoValue = new ExpandoValueImpl();

		expandoValue.setNew(true);
		expandoValue.setPrimaryKey(valueId);

		return expandoValue;
	}

	public ExpandoValue remove(long valueId)
		throws NoSuchExpandoValueException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoValue expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
					new Long(valueId));

			if (expandoValue == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoValue exists with the primary key " +
						valueId);
				}

				throw new NoSuchExpandoValueException(
					"No ExpandoValue exists with the primary key " + valueId);
			}

			return remove(expandoValue);
		}
		catch (NoSuchExpandoValueException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue remove(ExpandoValue expandoValue)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(expandoValue);
		}

		expandoValue = removeImpl(expandoValue);

		if (listener != null) {
			listener.onAfterRemove(expandoValue);
		}

		return expandoValue;
	}

	protected ExpandoValue removeImpl(ExpandoValue expandoValue)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoValue);

			session.flush();

			return expandoValue;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoValue.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ExpandoValue expandoValue, boolean merge)</code>.
	 */
	public ExpandoValue update(ExpandoValue expandoValue)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoValue expandoValue) method. Use update(ExpandoValue expandoValue, boolean merge) instead.");
		}

		return update(expandoValue, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoValue the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoValue is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoValue update(ExpandoValue expandoValue, boolean merge)
		throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = expandoValue.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(expandoValue);
			}
			else {
				listener.onBeforeUpdate(expandoValue);
			}
		}

		expandoValue = updateImpl(expandoValue, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(expandoValue);
			}
			else {
				listener.onAfterUpdate(expandoValue);
			}
		}

		return expandoValue;
	}

	public ExpandoValue updateImpl(
		com.liferay.portal.model.ExpandoValue expandoValue, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoValue);
			}
			else {
				if (expandoValue.isNew()) {
					session.save(expandoValue);
				}
			}

			session.flush();

			expandoValue.setNew(false);

			return expandoValue;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ExpandoValue.class.getName());
		}
	}

	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchExpandoValueException, SystemException {
		ExpandoValue expandoValue = fetchByPrimaryKey(valueId);

		if (expandoValue == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoValue exists with the primary key " +
					valueId);
			}

			throw new NoSuchExpandoValueException(
				"No ExpandoValue exists with the primary key " + valueId);
		}

		return expandoValue;
	}

	public ExpandoValue fetchByPrimaryKey(long valueId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoValue)session.get(ExpandoValueImpl.class,
				new Long(valueId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByC_C(long classPK, long columnId)
		throws NoSuchExpandoValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_C(classPK, columnId);

		if (expandoValue == null) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchExpandoValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByC_C(long classPK, long columnId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "fetchByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(classPK), new Long(columnId) };

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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classPK);

				q.setLong(queryPos++, columnId);

				List<ExpandoValue> list = q.list();

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
			List<ExpandoValue> list = (List<ExpandoValue>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ExpandoValue> findByClassPK(long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByClassPK";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(classPK) };

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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classPK);

				List<ExpandoValue> list = q.list();

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
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByClassPK(long classPK, int begin, int end)
		throws SystemException {
		return findByClassPK(classPK, begin, end, null);
	}

	public List<ExpandoValue> findByClassPK(long classPK, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByClassPK";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(classPK),
				
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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classPK);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
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
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByClassPK_First(long classPK, OrderByComparator obc)
		throws NoSuchExpandoValueException, SystemException {
		List<ExpandoValue> list = findByClassPK(classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByClassPK_Last(long classPK, OrderByComparator obc)
		throws NoSuchExpandoValueException, SystemException {
		int count = countByClassPK(classPK);

		List<ExpandoValue> list = findByClassPK(classPK, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByClassPK_PrevAndNext(long valueId, long classPK,
		OrderByComparator obc)
		throws NoSuchExpandoValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByClassPK(classPK);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ExpandoValue WHERE ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByColumnId(long columnId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByColumnId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(columnId) };

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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, columnId);

				List<ExpandoValue> list = q.list();

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
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByColumnId(long columnId, int begin, int end)
		throws SystemException {
		return findByColumnId(columnId, begin, end, null);
	}

	public List<ExpandoValue> findByColumnId(long columnId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByColumnId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(columnId),
				
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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, columnId);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
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
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByColumnId_First(long columnId,
		OrderByComparator obc)
		throws NoSuchExpandoValueException, SystemException {
		List<ExpandoValue> list = findByColumnId(columnId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByColumnId_Last(long columnId, OrderByComparator obc)
		throws NoSuchExpandoValueException, SystemException {
		int count = countByColumnId(columnId);

		List<ExpandoValue> list = findByColumnId(columnId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchExpandoValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByColumnId_PrevAndNext(long valueId,
		long columnId, OrderByComparator obc)
		throws NoSuchExpandoValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByColumnId(columnId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ExpandoValue WHERE ");

			query.append("columnId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, columnId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findWithDynamicQuery(
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

	public List<ExpandoValue> findWithDynamicQuery(
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

	public List<ExpandoValue> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoValue> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ExpandoValue> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
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

				query.append("FROM com.liferay.portal.model.ExpandoValue ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
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
			return (List<ExpandoValue>)result;
		}
	}

	public void removeByC_C(long classPK, long columnId)
		throws NoSuchExpandoValueException, SystemException {
		ExpandoValue expandoValue = findByC_C(classPK, columnId);

		remove(expandoValue);
	}

	public void removeByClassPK(long classPK) throws SystemException {
		for (ExpandoValue expandoValue : findByClassPK(classPK)) {
			remove(expandoValue);
		}
	}

	public void removeByColumnId(long columnId) throws SystemException {
		for (ExpandoValue expandoValue : findByColumnId(columnId)) {
			remove(expandoValue);
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoValue expandoValue : findAll()) {
			remove(expandoValue);
		}
	}

	public int countByC_C(long classPK, long columnId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(classPK), new Long(columnId) };

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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classPK);

				q.setLong(queryPos++, columnId);

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

	public int countByClassPK(long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByClassPK";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(classPK) };

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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classPK);

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

	public int countByColumnId(long columnId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByColumnId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(columnId) };

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
					"FROM com.liferay.portal.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, columnId);

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
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.ExpandoValue");

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
				"value.object.listener.com.liferay.portal.model.ExpandoValue"));
	private static Log _log = LogFactory.getLog(ExpandoValuePersistenceImpl.class);
}