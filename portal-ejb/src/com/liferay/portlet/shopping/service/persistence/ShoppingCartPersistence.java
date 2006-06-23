/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.NoSuchCartException;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingCartPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartPersistence extends BasePersistence {
	public com.liferay.portlet.shopping.model.ShoppingCart create(String cartId) {
		ShoppingCartHBM shoppingCartHBM = new ShoppingCartHBM();
		shoppingCartHBM.setNew(true);
		shoppingCartHBM.setPrimaryKey(cartId);

		return ShoppingCartHBMUtil.model(shoppingCartHBM);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart remove(String cartId)
		throws NoSuchCartException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)session.get(ShoppingCartHBM.class,
					cartId);

			if (shoppingCartHBM == null) {
				_log.warn("No ShoppingCart exists with the primary key " +
					cartId.toString());
				throw new NoSuchCartException(
					"No ShoppingCart exists with the primary key " +
					cartId.toString());
			}

			session.delete(shoppingCartHBM);
			session.flush();

			return ShoppingCartHBMUtil.model(shoppingCartHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart update(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingCart.isNew() || shoppingCart.isModified()) {
				session = openSession();

				if (shoppingCart.isNew()) {
					ShoppingCartHBM shoppingCartHBM = new ShoppingCartHBM();
					shoppingCartHBM.setCartId(shoppingCart.getCartId());
					shoppingCartHBM.setGroupId(shoppingCart.getGroupId());
					shoppingCartHBM.setCompanyId(shoppingCart.getCompanyId());
					shoppingCartHBM.setUserId(shoppingCart.getUserId());
					shoppingCartHBM.setUserName(shoppingCart.getUserName());
					shoppingCartHBM.setCreateDate(shoppingCart.getCreateDate());
					shoppingCartHBM.setModifiedDate(shoppingCart.getModifiedDate());
					shoppingCartHBM.setItemIds(shoppingCart.getItemIds());
					shoppingCartHBM.setCouponIds(shoppingCart.getCouponIds());
					shoppingCartHBM.setAltShipping(shoppingCart.getAltShipping());
					shoppingCartHBM.setInsure(shoppingCart.getInsure());
					session.save(shoppingCartHBM);
					session.flush();
				}
				else {
					ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)session.get(ShoppingCartHBM.class,
							shoppingCart.getPrimaryKey());

					if (shoppingCartHBM != null) {
						shoppingCartHBM.setGroupId(shoppingCart.getGroupId());
						shoppingCartHBM.setCompanyId(shoppingCart.getCompanyId());
						shoppingCartHBM.setUserId(shoppingCart.getUserId());
						shoppingCartHBM.setUserName(shoppingCart.getUserName());
						shoppingCartHBM.setCreateDate(shoppingCart.getCreateDate());
						shoppingCartHBM.setModifiedDate(shoppingCart.getModifiedDate());
						shoppingCartHBM.setItemIds(shoppingCart.getItemIds());
						shoppingCartHBM.setCouponIds(shoppingCart.getCouponIds());
						shoppingCartHBM.setAltShipping(shoppingCart.getAltShipping());
						shoppingCartHBM.setInsure(shoppingCart.getInsure());
						session.flush();
					}
					else {
						shoppingCartHBM = new ShoppingCartHBM();
						shoppingCartHBM.setCartId(shoppingCart.getCartId());
						shoppingCartHBM.setGroupId(shoppingCart.getGroupId());
						shoppingCartHBM.setCompanyId(shoppingCart.getCompanyId());
						shoppingCartHBM.setUserId(shoppingCart.getUserId());
						shoppingCartHBM.setUserName(shoppingCart.getUserName());
						shoppingCartHBM.setCreateDate(shoppingCart.getCreateDate());
						shoppingCartHBM.setModifiedDate(shoppingCart.getModifiedDate());
						shoppingCartHBM.setItemIds(shoppingCart.getItemIds());
						shoppingCartHBM.setCouponIds(shoppingCart.getCouponIds());
						shoppingCartHBM.setAltShipping(shoppingCart.getAltShipping());
						shoppingCartHBM.setInsure(shoppingCart.getInsure());
						session.save(shoppingCartHBM);
						session.flush();
					}
				}

				shoppingCart.setNew(false);
				shoppingCart.setModified(false);
			}

			return shoppingCart;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart findByPrimaryKey(
		String cartId) throws NoSuchCartException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)session.get(ShoppingCartHBM.class,
					cartId);

			if (shoppingCartHBM == null) {
				_log.warn("No ShoppingCart exists with the primary key " +
					cartId.toString());
				throw new NoSuchCartException(
					"No ShoppingCart exists with the primary key " +
					cartId.toString());
			}

			return ShoppingCartHBMUtil.model(shoppingCartHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				list.add(ShoppingCartHBMUtil.model(shoppingCartHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				list.add(ShoppingCartHBMUtil.model(shoppingCartHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCart exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCartException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingCart)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCart exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCartException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingCart)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart[] findByGroupId_PrevAndNext(
		String cartId, String groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart = findByPrimaryKey(cartId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCart, ShoppingCartHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingCart[] array = new com.liferay.portlet.shopping.model.ShoppingCart[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingCart)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingCart)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingCart)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				list.add(ShoppingCartHBMUtil.model(shoppingCartHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				list.add(ShoppingCartHBMUtil.model(shoppingCartHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCart exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCartException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingCart)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCart exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCartException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingCart)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCart[] findByUserId_PrevAndNext(
		String cartId, String userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart = findByPrimaryKey(cartId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCart, ShoppingCartHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingCart[] array = new com.liferay.portlet.shopping.model.ShoppingCart[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingCart)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingCart)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingCart)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				list.add(ShoppingCartHBMUtil.model(shoppingCartHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				session.delete(shoppingCartHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingCartHBM shoppingCartHBM = (ShoppingCartHBM)itr.next();
				session.delete(shoppingCartHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM ShoppingCart IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCartHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	private static Log _log = LogFactory.getLog(ShoppingCartPersistence.class);
}