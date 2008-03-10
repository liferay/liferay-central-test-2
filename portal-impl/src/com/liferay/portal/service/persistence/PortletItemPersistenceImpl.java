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
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemImpl;
import com.liferay.portal.model.impl.PortletItemModelImpl;
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
 * <a href="PortletItemPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletItemPersistenceImpl extends BasePersistence
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
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletItem remove(PortletItem portletItem)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(portletItem);
		}

		portletItem = removeImpl(portletItem);

		if (listener != null) {
			listener.onAfterRemove(portletItem);
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
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(PortletItem.class.getName());
		}
	}

	public PortletItem update(PortletItem portletItem)
		throws SystemException {
		return update(portletItem, false);
	}

	public PortletItem update(PortletItem portletItem, boolean merge)
		throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = portletItem.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(portletItem);
			}
			else {
				listener.onBeforeUpdate(portletItem);
			}
		}

		portletItem = updateImpl(portletItem, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(portletItem);
			}
			else {
				listener.onAfterUpdate(portletItem);
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
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(PortletItem.class.getName());
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
			throw HibernateUtil.processException(e);
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

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

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				if (portletId != null) {
					q.setString(queryPos++, portletId);
				}

				q.setLong(queryPos++, classNameId);

				List<PortletItem> list = q.list();

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
			return (List<PortletItem>)result;
		}
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int begin, int end) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, begin, end, null);
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int begin, int end, OrderByComparator obc)
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

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				if (portletId != null) {
					q.setString(queryPos++, portletId);
				}

				q.setLong(queryPos++, classNameId);

				List<PortletItem> list = (List<PortletItem>)QueryUtil.list(q,
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
			return (List<PortletItem>)result;
		}
	}

	public PortletItem findByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

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
			StringMaker msg = new StringMaker();

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

			StringMaker query = new StringMaker();

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

			int queryPos = 0;

			q.setLong(queryPos++, groupId);

			if (portletId != null) {
				q.setString(queryPos++, portletId);
			}

			q.setLong(queryPos++, classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletItem);

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = (PortletItem)objArray[0];
			array[1] = (PortletItem)objArray[1];
			array[2] = (PortletItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletItem findByG_P_C_N(long groupId, String portletId,
		long classNameId, String name)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_P_C_N(groupId, portletId,
				classNameId, name);

		if (portletItem == null) {
			StringMaker msg = new StringMaker();

			msg.append("No PortletItem exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletItemException(msg.toString());
		}

		return portletItem;
	}

	public PortletItem fetchByG_P_C_N(long groupId, String portletId,
		long classNameId, String name) throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "fetchByG_P_C_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId),
				
				name
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

				q.setLong(queryPos++, groupId);

				if (portletId != null) {
					q.setString(queryPos++, portletId);
				}

				q.setLong(queryPos++, classNameId);

				if (name != null) {
					q.setString(queryPos++, name);
				}

				List<PortletItem> list = q.list();

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
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletItem> findWithDynamicQuery(
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

	public List<PortletItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PortletItem> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<PortletItem> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
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

				query.append("FROM com.liferay.portal.model.PortletItem ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<PortletItem> list = (List<PortletItem>)QueryUtil.list(q,
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
			return (List<PortletItem>)result;
		}
	}

	public void removeByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_P_C(groupId, portletId,
				classNameId)) {
			remove(portletItem);
		}
	}

	public void removeByG_P_C_N(long groupId, String portletId,
		long classNameId, String name)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByG_P_C_N(groupId, portletId,
				classNameId, name);

		remove(portletItem);
	}

	public void removeAll() throws SystemException {
		for (PortletItem portletItem : findAll()) {
			remove(portletItem);
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

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

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				if (portletId != null) {
					q.setString(queryPos++, portletId);
				}

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

	public int countByG_P_C_N(long groupId, String portletId, long classNameId,
		String name) throws SystemException {
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
		String finderMethodName = "countByG_P_C_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId),
				
				name
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

				q.setLong(queryPos++, groupId);

				if (portletId != null) {
					q.setString(queryPos++, portletId);
				}

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
		boolean finderClassNameCacheEnabled = PortletItemModelImpl.CACHE_ENABLED;
		String finderClassName = PortletItem.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.PortletItem");

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
				"value.object.listener.com.liferay.portal.model.PortletItem"));
	private static Log _log = LogFactory.getLog(PortletItemPersistenceImpl.class);
}