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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shopping.NoSuchCartException;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.impl.ShoppingCartImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingCartPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCartPersistenceImpl extends BasePersistenceImpl
	implements ShoppingCartPersistence {
	public ShoppingCart create(long cartId) {
		ShoppingCart shoppingCart = new ShoppingCartImpl();

		shoppingCart.setNew(true);
		shoppingCart.setPrimaryKey(cartId);

		return shoppingCart;
	}

	public ShoppingCart remove(long cartId)
		throws NoSuchCartException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCart shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
					new Long(cartId));

			if (shoppingCart == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingCart exists with the primary key " +
						cartId);
				}

				throw new NoSuchCartException(
					"No ShoppingCart exists with the primary key " + cartId);
			}

			return remove(shoppingCart);
		}
		catch (NoSuchCartException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCart remove(ShoppingCart shoppingCart)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(shoppingCart);
			}
		}

		shoppingCart = removeImpl(shoppingCart);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(shoppingCart);
			}
		}

		return shoppingCart;
	}

	protected ShoppingCart removeImpl(ShoppingCart shoppingCart)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(shoppingCart);

			session.flush();

			return shoppingCart;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingCart.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ShoppingCart shoppingCart, boolean merge)</code>.
	 */
	public ShoppingCart update(ShoppingCart shoppingCart)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingCart shoppingCart) method. Use update(ShoppingCart shoppingCart, boolean merge) instead.");
		}

		return update(shoppingCart, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingCart the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingCart is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ShoppingCart update(ShoppingCart shoppingCart, boolean merge)
		throws SystemException {
		boolean isNew = shoppingCart.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(shoppingCart);
				}
				else {
					listener.onBeforeUpdate(shoppingCart);
				}
			}
		}

		shoppingCart = updateImpl(shoppingCart, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(shoppingCart);
				}
				else {
					listener.onAfterUpdate(shoppingCart);
				}
			}
		}

		return shoppingCart;
	}

	public ShoppingCart updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(shoppingCart);
			}
			else {
				if (shoppingCart.isNew()) {
					session.save(shoppingCart);
				}
			}

			session.flush();

			shoppingCart.setNew(false);

			return shoppingCart;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingCart.class.getName());
		}
	}

	public ShoppingCart findByPrimaryKey(long cartId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByPrimaryKey(cartId);

		if (shoppingCart == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingCart exists with the primary key " +
					cartId);
			}

			throw new NoSuchCartException(
				"No ShoppingCart exists with the primary key " + cartId);
		}

		return shoppingCart;
	}

	public ShoppingCart fetchByPrimaryKey(long cartId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingCart)session.get(ShoppingCartImpl.class,
				new Long(cartId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingCart> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<ShoppingCart> list = q.list();

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
			return (List<ShoppingCart>)result;
		}
	}

	public List<ShoppingCart> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<ShoppingCart> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<ShoppingCart> list = (List<ShoppingCart>)QueryUtil.list(q,
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
			return (List<ShoppingCart>)result;
		}
	}

	public ShoppingCart findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		List<ShoppingCart> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingCart> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart[] findByGroupId_PrevAndNext(long cartId, long groupId,
		OrderByComparator obc) throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCart);

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = (ShoppingCart)objArray[0];
			array[1] = (ShoppingCart)objArray[1];
			array[2] = (ShoppingCart)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingCart> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<ShoppingCart> list = q.list();

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
			return (List<ShoppingCart>)result;
		}
	}

	public List<ShoppingCart> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<ShoppingCart> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<ShoppingCart> list = (List<ShoppingCart>)QueryUtil.list(q,
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
			return (List<ShoppingCart>)result;
		}
	}

	public ShoppingCart findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		List<ShoppingCart> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		int count = countByUserId(userId);

		List<ShoppingCart> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart[] findByUserId_PrevAndNext(long cartId, long userId,
		OrderByComparator obc) throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCart);

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = (ShoppingCart)objArray[0];
			array[1] = (ShoppingCart)objArray[1];
			array[2] = (ShoppingCart)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCart findByG_U(long groupId, long userId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByG_U(groupId, userId);

		if (shoppingCart == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCartException(msg.toString());
		}

		return shoppingCart;
	}

	public ShoppingCart fetchByG_U(long groupId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "fetchByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<ShoppingCart> list = q.list();

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
			List<ShoppingCart> list = (List<ShoppingCart>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ShoppingCart> findWithDynamicQuery(
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

	public List<ShoppingCart> findWithDynamicQuery(
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

	public List<ShoppingCart> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingCart> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingCart> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ShoppingCart> list = (List<ShoppingCart>)QueryUtil.list(q,
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
			return (List<ShoppingCart>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingCart shoppingCart : findByGroupId(groupId)) {
			remove(shoppingCart);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (ShoppingCart shoppingCart : findByUserId(userId)) {
			remove(shoppingCart);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByG_U(groupId, userId);

		remove(shoppingCart);
	}

	public void removeAll() throws SystemException {
		for (ShoppingCart shoppingCart : findAll()) {
			remove(shoppingCart);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
		String finderMethodName = "countByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingCart WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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
		boolean finderClassNameCacheEnabled = ShoppingCartModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCart.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.shopping.model.ShoppingCart");

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCart")));

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

	private static Log _log = LogFactory.getLog(ShoppingCartPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}