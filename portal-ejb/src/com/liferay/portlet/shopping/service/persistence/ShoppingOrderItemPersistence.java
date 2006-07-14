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

import com.liferay.portlet.shopping.NoSuchOrderItemException;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;

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
 * <a href="ShoppingOrderItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderItemPersistence extends BasePersistence {
	public ShoppingOrderItem create(ShoppingOrderItemPK shoppingOrderItemPK) {
		ShoppingOrderItem shoppingOrderItem = new ShoppingOrderItem();
		shoppingOrderItem.setNew(true);
		shoppingOrderItem.setPrimaryKey(shoppingOrderItemPK);

		return shoppingOrderItem;
	}

	public ShoppingOrderItem remove(ShoppingOrderItemPK shoppingOrderItemPK)
		throws NoSuchOrderItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)session.get(ShoppingOrderItem.class,
					shoppingOrderItemPK);

			if (shoppingOrderItem == null) {
				_log.warn("No ShoppingOrderItem exists with the primary key " +
					shoppingOrderItemPK.toString());
				throw new NoSuchOrderItemException(
					"No ShoppingOrderItem exists with the primary key " +
					shoppingOrderItemPK.toString());
			}

			session.delete(shoppingOrderItem);
			session.flush();

			return shoppingOrderItem;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem update(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingOrderItem.isNew() || shoppingOrderItem.isModified()) {
				session = openSession();

				if (shoppingOrderItem.isNew()) {
					ShoppingOrderItem shoppingOrderItemModel = new ShoppingOrderItem();
					shoppingOrderItemModel.setOrderId(shoppingOrderItem.getOrderId());
					shoppingOrderItemModel.setItemId(shoppingOrderItem.getItemId());
					shoppingOrderItemModel.setSku(shoppingOrderItem.getSku());
					shoppingOrderItemModel.setName(shoppingOrderItem.getName());
					shoppingOrderItemModel.setDescription(shoppingOrderItem.getDescription());
					shoppingOrderItemModel.setProperties(shoppingOrderItem.getProperties());
					shoppingOrderItemModel.setPrice(shoppingOrderItem.getPrice());
					shoppingOrderItemModel.setQuantity(shoppingOrderItem.getQuantity());
					shoppingOrderItemModel.setShippedDate(shoppingOrderItem.getShippedDate());
					session.save(shoppingOrderItemModel);
					session.flush();
				}
				else {
					ShoppingOrderItem shoppingOrderItemModel = (ShoppingOrderItem)session.get(ShoppingOrderItem.class,
							shoppingOrderItem.getPrimaryKey());

					if (shoppingOrderItemModel != null) {
						shoppingOrderItemModel.setSku(shoppingOrderItem.getSku());
						shoppingOrderItemModel.setName(shoppingOrderItem.getName());
						shoppingOrderItemModel.setDescription(shoppingOrderItem.getDescription());
						shoppingOrderItemModel.setProperties(shoppingOrderItem.getProperties());
						shoppingOrderItemModel.setPrice(shoppingOrderItem.getPrice());
						shoppingOrderItemModel.setQuantity(shoppingOrderItem.getQuantity());
						shoppingOrderItemModel.setShippedDate(shoppingOrderItem.getShippedDate());
						session.flush();
					}
					else {
						shoppingOrderItemModel = new ShoppingOrderItem();
						shoppingOrderItemModel.setOrderId(shoppingOrderItem.getOrderId());
						shoppingOrderItemModel.setItemId(shoppingOrderItem.getItemId());
						shoppingOrderItemModel.setSku(shoppingOrderItem.getSku());
						shoppingOrderItemModel.setName(shoppingOrderItem.getName());
						shoppingOrderItemModel.setDescription(shoppingOrderItem.getDescription());
						shoppingOrderItemModel.setProperties(shoppingOrderItem.getProperties());
						shoppingOrderItemModel.setPrice(shoppingOrderItem.getPrice());
						shoppingOrderItemModel.setQuantity(shoppingOrderItem.getQuantity());
						shoppingOrderItemModel.setShippedDate(shoppingOrderItem.getShippedDate());
						session.save(shoppingOrderItemModel);
						session.flush();
					}
				}

				shoppingOrderItem.setNew(false);
				shoppingOrderItem.setModified(false);
			}

			return shoppingOrderItem;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrderItem findByPrimaryKey(
		ShoppingOrderItemPK shoppingOrderItemPK)
		throws NoSuchOrderItemException, SystemException {
		return findByPrimaryKey(shoppingOrderItemPK, true);
	}

	public ShoppingOrderItem findByPrimaryKey(
		ShoppingOrderItemPK shoppingOrderItemPK,
		boolean throwNoSuchObjectException)
		throws NoSuchOrderItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)session.get(ShoppingOrderItem.class,
					shoppingOrderItemPK);

			if (shoppingOrderItem == null) {
				_log.warn("No ShoppingOrderItem exists with the primary key " +
					shoppingOrderItemPK.toString());

				if (throwNoSuchObjectException) {
					throw new NoSuchOrderItemException(
						"No ShoppingOrderItem exists with the primary key " +
						shoppingOrderItemPK.toString());
				}
			}

			return shoppingOrderItem;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOrderId(String orderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrderItem WHERE ");

			if (orderId == null) {
				query.append("orderId IS NULL");
			}
			else {
				query.append("orderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC").append(", ");
			query.append("description ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (orderId != null) {
				q.setString(queryPos++, orderId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOrderId(String orderId, int begin, int end)
		throws SystemException {
		return findByOrderId(orderId, begin, end, null);
	}

	public List findByOrderId(String orderId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrderItem WHERE ");

			if (orderId == null) {
				query.append("orderId IS NULL");
			}
			else {
				query.append("orderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC").append(", ");
				query.append("description ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (orderId != null) {
				q.setString(queryPos++, orderId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrderItem findByOrderId_First(String orderId,
		OrderByComparator obc) throws NoSuchOrderItemException, SystemException {
		List list = findByOrderId(orderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingOrderItem exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "orderId=";
			msg += orderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrderItemException(msg);
		}
		else {
			return (ShoppingOrderItem)list.get(0);
		}
	}

	public ShoppingOrderItem findByOrderId_Last(String orderId,
		OrderByComparator obc) throws NoSuchOrderItemException, SystemException {
		int count = countByOrderId(orderId);
		List list = findByOrderId(orderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingOrderItem exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "orderId=";
			msg += orderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrderItemException(msg);
		}
		else {
			return (ShoppingOrderItem)list.get(0);
		}
	}

	public ShoppingOrderItem[] findByOrderId_PrevAndNext(
		ShoppingOrderItemPK shoppingOrderItemPK, String orderId,
		OrderByComparator obc) throws NoSuchOrderItemException, SystemException {
		ShoppingOrderItem shoppingOrderItem = findByPrimaryKey(shoppingOrderItemPK);
		int count = countByOrderId(orderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrderItem WHERE ");

			if (orderId == null) {
				query.append("orderId IS NULL");
			}
			else {
				query.append("orderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC").append(", ");
				query.append("description ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (orderId != null) {
				q.setString(queryPos++, orderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrderItem);
			ShoppingOrderItem[] array = new ShoppingOrderItem[3];
			array[0] = (ShoppingOrderItem)objArray[0];
			array[1] = (ShoppingOrderItem)objArray[1];
			array[2] = (ShoppingOrderItem)objArray[2];

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
				"FROM com.liferay.portlet.shopping.model.ShoppingOrderItem ");
			query.append("ORDER BY ");
			query.append("name ASC").append(", ");
			query.append("description ASC");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByOrderId(String orderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrderItem WHERE ");

			if (orderId == null) {
				query.append("orderId IS NULL");
			}
			else {
				query.append("orderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC").append(", ");
			query.append("description ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (orderId != null) {
				q.setString(queryPos++, orderId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)itr.next();
				session.delete(shoppingOrderItem);
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

	public int countByOrderId(String orderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrderItem WHERE ");

			if (orderId == null) {
				query.append("orderId IS NULL");
			}
			else {
				query.append("orderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (orderId != null) {
				q.setString(queryPos++, orderId);
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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ShoppingOrderItemPersistence.class);
}