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

package com.liferay.portlet.shopping.service.persistence;

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

import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl;

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
 * <a href="ShoppingOrderPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingOrderPersistenceImpl extends BasePersistence
	implements ShoppingOrderPersistence {
	public ShoppingOrder create(long orderId) {
		ShoppingOrder shoppingOrder = new ShoppingOrderImpl();

		shoppingOrder.setNew(true);
		shoppingOrder.setPrimaryKey(orderId);

		return shoppingOrder;
	}

	public ShoppingOrder remove(long orderId)
		throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrder shoppingOrder = (ShoppingOrder)session.get(ShoppingOrderImpl.class,
					new Long(orderId));

			if (shoppingOrder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingOrder exists with the primary key " +
						orderId);
				}

				throw new NoSuchOrderException(
					"No ShoppingOrder exists with the primary key " + orderId);
			}

			return remove(shoppingOrder);
		}
		catch (NoSuchOrderException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrder remove(ShoppingOrder shoppingOrder)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(shoppingOrder);
			}
		}

		shoppingOrder = removeImpl(shoppingOrder);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(shoppingOrder);
			}
		}

		return shoppingOrder;
	}

	protected ShoppingOrder removeImpl(ShoppingOrder shoppingOrder)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(shoppingOrder);

			session.flush();

			return shoppingOrder;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ShoppingOrder.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ShoppingOrder shoppingOrder, boolean merge)</code>.
	 */
	public ShoppingOrder update(ShoppingOrder shoppingOrder)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingOrder shoppingOrder) method. Use update(ShoppingOrder shoppingOrder, boolean merge) instead.");
		}

		return update(shoppingOrder, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingOrder the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingOrder is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ShoppingOrder update(ShoppingOrder shoppingOrder, boolean merge)
		throws SystemException {
		boolean isNew = shoppingOrder.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(shoppingOrder);
				}
				else {
					listener.onBeforeUpdate(shoppingOrder);
				}
			}
		}

		shoppingOrder = updateImpl(shoppingOrder, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(shoppingOrder);
				}
				else {
					listener.onAfterUpdate(shoppingOrder);
				}
			}
		}

		return shoppingOrder;
	}

	public ShoppingOrder updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(shoppingOrder);
			}
			else {
				if (shoppingOrder.isNew()) {
					session.save(shoppingOrder);
				}
			}

			session.flush();

			shoppingOrder.setNew(false);

			return shoppingOrder;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ShoppingOrder.class.getName());
		}
	}

	public ShoppingOrder findByPrimaryKey(long orderId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByPrimaryKey(orderId);

		if (shoppingOrder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingOrder exists with the primary key " +
					orderId);
			}

			throw new NoSuchOrderException(
				"No ShoppingOrder exists with the primary key " + orderId);
		}

		return shoppingOrder;
	}

	public ShoppingOrder fetchByPrimaryKey(long orderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingOrder)session.get(ShoppingOrderImpl.class,
				new Long(orderId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingOrder> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				List<ShoppingOrder> list = q.list();

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
			return (List<ShoppingOrder>)result;
		}
	}

	public List<ShoppingOrder> findByGroupId(long groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List<ShoppingOrder> findByGroupId(long groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				List<ShoppingOrder> list = (List<ShoppingOrder>)QueryUtil.list(q,
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
			return (List<ShoppingOrder>)result;
		}
	}

	public ShoppingOrder findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		List<ShoppingOrder> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ShoppingOrder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingOrder> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ShoppingOrder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder[] findByGroupId_PrevAndNext(long orderId,
		long groupId, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrder);

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = (ShoppingOrder)objArray[0];
			array[1] = (ShoppingOrder)objArray[1];
			array[2] = (ShoppingOrder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrder findByNumber(String number)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByNumber(number);

		if (shoppingOrder == null) {
			StringMaker msg = new StringMaker();

			msg.append("No ShoppingOrder exists with the key {");

			msg.append("number=" + number);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	public ShoppingOrder fetchByNumber(String number) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "fetchByNumber";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { number };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				if (number == null) {
					query.append("number_ IS NULL");
				}
				else {
					query.append("number_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				if (number != null) {
					q.setString(queryPos++, number);
				}

				List<ShoppingOrder> list = q.list();

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
			List<ShoppingOrder> list = (List<ShoppingOrder>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "findByG_U_PPPS";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				ppPaymentStatus
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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (ppPaymentStatus == null) {
					query.append("ppPaymentStatus IS NULL");
				}
				else {
					query.append("ppPaymentStatus = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				q.setLong(queryPos++, userId);

				if (ppPaymentStatus != null) {
					q.setString(queryPos++, ppPaymentStatus);
				}

				List<ShoppingOrder> list = q.list();

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
			return (List<ShoppingOrder>)result;
		}
	}

	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int begin, int end) throws SystemException {
		return findByG_U_PPPS(groupId, userId, ppPaymentStatus, begin, end, null);
	}

	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "findByG_U_PPPS";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				ppPaymentStatus,
				
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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (ppPaymentStatus == null) {
					query.append("ppPaymentStatus IS NULL");
				}
				else {
					query.append("ppPaymentStatus = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				q.setLong(queryPos++, userId);

				if (ppPaymentStatus != null) {
					q.setString(queryPos++, ppPaymentStatus);
				}

				List<ShoppingOrder> list = (List<ShoppingOrder>)QueryUtil.list(q,
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
			return (List<ShoppingOrder>)result;
		}
	}

	public ShoppingOrder findByG_U_PPPS_First(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		List<ShoppingOrder> list = findByG_U_PPPS(groupId, userId,
				ppPaymentStatus, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ShoppingOrder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("ppPaymentStatus=" + ppPaymentStatus);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder findByG_U_PPPS_Last(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);

		List<ShoppingOrder> list = findByG_U_PPPS(groupId, userId,
				ppPaymentStatus, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ShoppingOrder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("ppPaymentStatus=" + ppPaymentStatus);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder[] findByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("userId = ?");

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus IS NULL");
			}
			else {
				query.append("ppPaymentStatus = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, groupId);

			q.setLong(queryPos++, userId);

			if (ppPaymentStatus != null) {
				q.setString(queryPos++, ppPaymentStatus);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrder);

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = (ShoppingOrder)objArray[0];
			array[1] = (ShoppingOrder)objArray[1];
			array[2] = (ShoppingOrder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingOrder> findWithDynamicQuery(
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

	public List<ShoppingOrder> findWithDynamicQuery(
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

	public List<ShoppingOrder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingOrder> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<ShoppingOrder> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				List<ShoppingOrder> list = (List<ShoppingOrder>)QueryUtil.list(q,
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
			return (List<ShoppingOrder>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingOrder shoppingOrder : findByGroupId(groupId)) {
			remove(shoppingOrder);
		}
	}

	public void removeByNumber(String number)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByNumber(number);

		remove(shoppingOrder);
	}

	public void removeByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		for (ShoppingOrder shoppingOrder : findByG_U_PPPS(groupId, userId,
				ppPaymentStatus)) {
			remove(shoppingOrder);
		}
	}

	public void removeAll() throws SystemException {
		for (ShoppingOrder shoppingOrder : findAll()) {
			remove(shoppingOrder);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

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

	public int countByNumber(String number) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "countByNumber";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { number };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				if (number == null) {
					query.append("number_ IS NULL");
				}
				else {
					query.append("number_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				if (number != null) {
					q.setString(queryPos++, number);
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

	public int countByG_U_PPPS(long groupId, long userId, String ppPaymentStatus)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
		String finderMethodName = "countByG_U_PPPS";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				ppPaymentStatus
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
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (ppPaymentStatus == null) {
					query.append("ppPaymentStatus IS NULL");
				}
				else {
					query.append("ppPaymentStatus = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, groupId);

				q.setLong(queryPos++, userId);

				if (ppPaymentStatus != null) {
					q.setString(queryPos++, ppPaymentStatus);
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
		boolean finderClassNameCacheEnabled = ShoppingOrderModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingOrder.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.shopping.model.ShoppingOrder");

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingOrder")));

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

	private static Log _log = LogFactory.getLog(ShoppingOrderPersistenceImpl.class);
	private ModelListener[] _listeners;
}