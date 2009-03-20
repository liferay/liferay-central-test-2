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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.impl.ExpandoRowImpl;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;

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
public class ExpandoRowPersistenceImpl extends BasePersistenceImpl
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
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoRowImpl.class,
						expandoRow.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoRow);

			session.flush();

			return expandoRow;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ExpandoRow.class.getName());
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

		for (ModelListener<ExpandoRow> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(expandoRow);
			}
			else {
				listener.onBeforeUpdate(expandoRow);
			}
		}

		expandoRow = updateImpl(expandoRow, merge);

		for (ModelListener<ExpandoRow> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(expandoRow);
			}
			else {
				listener.onAfterUpdate(expandoRow);
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

			BatchSessionUtil.update(session, expandoRow, merge);

			expandoRow.setNew(false);

			return expandoRow;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ExpandoRow.class.getName());
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
			throw processException(e);
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoRow> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoRow>)result;
		}
	}

	public List<ExpandoRow> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoRow> findByTableId(long tableId, int start, int end,
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
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoRow> list = (List<ExpandoRow>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
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
			StringBuilder msg = new StringBuilder();

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
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

			query.append("tableId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

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
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoRow exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

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
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
		String finderMethodName = "fetchByT_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				List<ExpandoRow> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
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
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ExpandoRow> list = (List<ExpandoRow>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
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
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoRow> list = null;

				if (obc == null) {
					list = (List<ExpandoRow>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoRow>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
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
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
		String finderMethodName = "countByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByT_C(long tableId, long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoRowModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoRow.class.getName();
		String finderMethodName = "countByT_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoRow WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
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

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
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

	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(ExpandoRowPersistenceImpl.class);
}