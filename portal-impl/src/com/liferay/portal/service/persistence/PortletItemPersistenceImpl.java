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

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.hibernate.Query;
import com.liferay.portal.kernel.dao.hibernate.QueryPos;
import com.liferay.portal.kernel.dao.hibernate.QueryUtil;
import com.liferay.portal.kernel.dao.hibernate.Session;
import com.liferay.portal.kernel.dao.search.DynamicQuery;
import com.liferay.portal.kernel.dao.search.DynamicQueryInitializer;
import com.liferay.portal.kernel.spring.hibernate.FinderCacheUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemImpl;
import com.liferay.portal.model.impl.PortletItemModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PortletItemPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletItemPersistenceImpl extends BasePersistenceImpl
	implements PortletItemPersistence {
	public PortletItem create(long portletItemId) {
		PortletItem portletItem = new PortletItemImpl();

		portletItem.setNew(true);
		portletItem.setPrimaryKey(portletItemId);

		return portletItem;
	}

	public PortletItem remove(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletItem portletItem = (PortletItem)session.get(PortletItemImpl.class,
					new Long(portletItemId));

			if (portletItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PortletItem exists with the primary key " +
						portletItemId);
				}

				throw new NoSuchPortletItemException(
					"No PortletItem exists with the primary key " +
					portletItemId);
			}

			return remove(portletItem);
		}
		catch (NoSuchPortletItemException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletItem remove(PortletItem portletItem)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(portletItem);
			}
		}

		portletItem = removeImpl(portletItem);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(portletItem);
			}
		}

		return portletItem;
	}

	protected PortletItem removeImpl(PortletItem portletItem)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(portletItem);

			session.flush();

			return portletItem;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(PortletItem.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(PortletItem portletItem, boolean merge)</code>.
	 */
	public PortletItem update(PortletItem portletItem)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(PortletItem portletItem) method. Use update(PortletItem portletItem, boolean merge) instead.");
		}

		return update(portletItem, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        portletItem the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when portletItem is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public PortletItem update(PortletItem portletItem, boolean merge)
		throws SystemException {
		boolean isNew = portletItem.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(portletItem);
				}
				else {
					listener.onBeforeUpdate(portletItem);
				}
			}
		}

		portletItem = updateImpl(portletItem, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(portletItem);
				}
				else {
					listener.onAfterUpdate(portletItem);
				}
			}
		}

		return portletItem;
	}

	public PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(portletItem);
			}
			else {
				if (portletItem.isNew()) {
					session.save(portletItem);
				}
			}

			session.flush();

			portletItem.setNew(false);

			return portletItem;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(PortletItem.class.getName());
		}
	}

	public PortletItem findByPrimaryKey(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByPrimaryKey(portletItemId);

		if (portletItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PortletItem exists with the primary key " +
					portletItemId);
			}

			throw new NoSuchPortletItemException(
				"No PortletItem exists with the primary key " + portletItemId);
		}

		return portletItem;
	}

	public PortletItem fetchByPrimaryKey(long portletItemId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PortletItem)session.get(PortletItemImpl.class,
				new Long(portletItemId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletItem> findByG_C(long groupId, long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "findByG_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(classNameId)
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

				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				List<PortletItem> list = q.list();

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
			return (List<PortletItem>)result;
		}
	}

	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "findByG_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(classNameId),
				
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

				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				List<PortletItem> list = (List<PortletItem>)QueryUtil.list(q,
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
			return (List<PortletItem>)result;
		}
	}

	public PortletItem findByG_C_First(long groupId, long classNameId,
		OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		List<PortletItem> list = findByG_C(groupId, classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PortletItem exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem findByG_C_Last(long groupId, long classNameId,
		OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		int count = countByG_C(groupId, classNameId);

		List<PortletItem> list = findByG_C(groupId, classNameId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PortletItem exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem[] findByG_C_PrevAndNext(long portletItemId,
		long groupId, long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		int count = countByG_C(groupId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletItem);

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = (PortletItem)objArray[0];
			array[1] = (PortletItem)objArray[1];
			array[2] = (PortletItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "findByG_P_C";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId)
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

				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				List<PortletItem> list = q.list();

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
			return (List<PortletItem>)result;
		}
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, start, end, null);
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "findByG_P_C";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId),
				
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

				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				List<PortletItem> list = (List<PortletItem>)QueryUtil.list(q,
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
			return (List<PortletItem>)result;
		}
	}

	public PortletItem findByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PortletItem exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem findByG_P_C_Last(long groupId, String portletId,
		long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		int count = countByG_P_C(groupId, portletId, classNameId);

		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PortletItem exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem[] findByG_P_C_PrevAndNext(long portletItemId,
		long groupId, String portletId, long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		int count = countByG_P_C(groupId, portletId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			if (portletId == null) {
				query.append("portletId IS NULL");
			}
			else {
				query.append("portletId = ?");
			}

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (portletId != null) {
				qPos.add(portletId);
			}

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletItem);

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = (PortletItem)objArray[0];
			array[1] = (PortletItem)objArray[1];
			array[2] = (PortletItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletItem findByG_N_P_C(long groupId, String name,
		String portletId, long classNameId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_N_P_C(groupId, name, portletId,
				classNameId);

		if (portletItem == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PortletItem exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletItemException(msg.toString());
		}

		return portletItem;
	}

	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "fetchByG_N_P_C";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				name,
				
				portletId, new Long(classNameId)
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

				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("lower(name) = ?");
				}

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				List<PortletItem> list = q.list();

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
			List<PortletItem> list = (List<PortletItem>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<PortletItem> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletItem> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PortletItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PortletItem> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
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

				query.append("FROM com.liferay.portal.model.PortletItem ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<PortletItem> list = (List<PortletItem>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
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
			return (List<PortletItem>)result;
		}
	}

	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_C(groupId, classNameId)) {
			remove(portletItem);
		}
	}

	public void removeByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_P_C(groupId, portletId,
				classNameId)) {
			remove(portletItem);
		}
	}

	public void removeByG_N_P_C(long groupId, String name, String portletId,
		long classNameId) throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByG_N_P_C(groupId, name, portletId,
				classNameId);

		remove(portletItem);
	}

	public void removeAll() throws SystemException {
		for (PortletItem portletItem : findAll()) {
			remove(portletItem);
		}
	}

	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "countByG_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(classNameId)
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

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

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

	public int countByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "countByG_P_C";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId)
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

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

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

	public int countByG_N_P_C(long groupId, String name, String portletId,
		long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "countByG_N_P_C";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				name,
				
				portletId, new Long(classNameId)
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

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.PortletItem WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("lower(name) = ?");
				}

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

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
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.PortletItem");

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

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PortletItem")));

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

	private static Log _log = LogFactory.getLog(PortletItemPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}