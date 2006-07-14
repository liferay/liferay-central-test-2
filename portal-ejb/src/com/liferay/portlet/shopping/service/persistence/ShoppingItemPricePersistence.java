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

import com.liferay.portlet.shopping.NoSuchItemPriceException;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingItemPricePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPricePersistence extends BasePersistence {
	public ShoppingItemPrice create(String itemPriceId) {
		ShoppingItemPrice shoppingItemPrice = new ShoppingItemPrice();
		shoppingItemPrice.setNew(true);
		shoppingItemPrice.setPrimaryKey(itemPriceId);

		return shoppingItemPrice;
	}

	public ShoppingItemPrice remove(String itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPrice.class,
					itemPriceId);

			if (shoppingItemPrice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						itemPriceId.toString());
				}

				throw new NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					itemPriceId.toString());
			}

			session.delete(shoppingItemPrice);
			session.flush();

			return shoppingItemPrice;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice update(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingItemPrice.isNew() || shoppingItemPrice.isModified()) {
				session = openSession();

				if (shoppingItemPrice.isNew()) {
					ShoppingItemPrice shoppingItemPriceModel = new ShoppingItemPrice();
					shoppingItemPriceModel.setItemPriceId(shoppingItemPrice.getItemPriceId());
					shoppingItemPriceModel.setItemId(shoppingItemPrice.getItemId());
					shoppingItemPriceModel.setMinQuantity(shoppingItemPrice.getMinQuantity());
					shoppingItemPriceModel.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
					shoppingItemPriceModel.setPrice(shoppingItemPrice.getPrice());
					shoppingItemPriceModel.setDiscount(shoppingItemPrice.getDiscount());
					shoppingItemPriceModel.setTaxable(shoppingItemPrice.getTaxable());
					shoppingItemPriceModel.setShipping(shoppingItemPrice.getShipping());
					shoppingItemPriceModel.setUseShippingFormula(shoppingItemPrice.getUseShippingFormula());
					shoppingItemPriceModel.setStatus(shoppingItemPrice.getStatus());
					session.save(shoppingItemPriceModel);
					session.flush();
				}
				else {
					ShoppingItemPrice shoppingItemPriceModel = (ShoppingItemPrice)session.get(ShoppingItemPrice.class,
							shoppingItemPrice.getPrimaryKey());

					if (shoppingItemPriceModel != null) {
						shoppingItemPriceModel.setItemId(shoppingItemPrice.getItemId());
						shoppingItemPriceModel.setMinQuantity(shoppingItemPrice.getMinQuantity());
						shoppingItemPriceModel.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
						shoppingItemPriceModel.setPrice(shoppingItemPrice.getPrice());
						shoppingItemPriceModel.setDiscount(shoppingItemPrice.getDiscount());
						shoppingItemPriceModel.setTaxable(shoppingItemPrice.getTaxable());
						shoppingItemPriceModel.setShipping(shoppingItemPrice.getShipping());
						shoppingItemPriceModel.setUseShippingFormula(shoppingItemPrice.getUseShippingFormula());
						shoppingItemPriceModel.setStatus(shoppingItemPrice.getStatus());
						session.flush();
					}
					else {
						shoppingItemPriceModel = new ShoppingItemPrice();
						shoppingItemPriceModel.setItemPriceId(shoppingItemPrice.getItemPriceId());
						shoppingItemPriceModel.setItemId(shoppingItemPrice.getItemId());
						shoppingItemPriceModel.setMinQuantity(shoppingItemPrice.getMinQuantity());
						shoppingItemPriceModel.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
						shoppingItemPriceModel.setPrice(shoppingItemPrice.getPrice());
						shoppingItemPriceModel.setDiscount(shoppingItemPrice.getDiscount());
						shoppingItemPriceModel.setTaxable(shoppingItemPrice.getTaxable());
						shoppingItemPriceModel.setShipping(shoppingItemPrice.getShipping());
						shoppingItemPriceModel.setUseShippingFormula(shoppingItemPrice.getUseShippingFormula());
						shoppingItemPriceModel.setStatus(shoppingItemPrice.getStatus());
						session.save(shoppingItemPriceModel);
						session.flush();
					}
				}

				shoppingItemPrice.setNew(false);
				shoppingItemPrice.setModified(false);
			}

			return shoppingItemPrice;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItemPrice findByPrimaryKey(String itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPrice.class,
					itemPriceId);

			if (shoppingItemPrice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						itemPriceId.toString());
					throw new NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						itemPriceId.toString());
				}
			}

			return shoppingItemPrice;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItemPrice fetchByPrimaryKey(String itemPriceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingItemPrice)session.get(ShoppingItemPrice.class,
				itemPriceId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice WHERE ");

			if (itemId == null) {
				query.append("itemId IS NULL");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("itemPriceId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
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

	public List findByItemId(String itemId, int begin, int end)
		throws SystemException {
		return findByItemId(itemId, begin, end, null);
	}

	public List findByItemId(String itemId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice WHERE ");

			if (itemId == null) {
				query.append("itemId IS NULL");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC").append(", ");
				query.append("itemPriceId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
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

	public ShoppingItemPrice findByItemId_First(String itemId,
		OrderByComparator obc) throws NoSuchItemPriceException, SystemException {
		List list = findByItemId(itemId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingItemPrice exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "itemId=";
			msg += itemId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchItemPriceException(msg);
		}
		else {
			return (ShoppingItemPrice)list.get(0);
		}
	}

	public ShoppingItemPrice findByItemId_Last(String itemId,
		OrderByComparator obc) throws NoSuchItemPriceException, SystemException {
		int count = countByItemId(itemId);
		List list = findByItemId(itemId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingItemPrice exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "itemId=";
			msg += itemId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchItemPriceException(msg);
		}
		else {
			return (ShoppingItemPrice)list.get(0);
		}
	}

	public ShoppingItemPrice[] findByItemId_PrevAndNext(String itemPriceId,
		String itemId, OrderByComparator obc)
		throws NoSuchItemPriceException, SystemException {
		ShoppingItemPrice shoppingItemPrice = findByPrimaryKey(itemPriceId);
		int count = countByItemId(itemId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice WHERE ");

			if (itemId == null) {
				query.append("itemId IS NULL");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC").append(", ");
				query.append("itemPriceId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItemPrice);
			ShoppingItemPrice[] array = new ShoppingItemPrice[3];
			array[0] = (ShoppingItemPrice)objArray[0];
			array[1] = (ShoppingItemPrice)objArray[1];
			array[2] = (ShoppingItemPrice)objArray[2];

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
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("itemPriceId ASC");

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

	public void removeByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice WHERE ");

			if (itemId == null) {
				query.append("itemId IS NULL");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("itemPriceId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)itr.next();
				session.delete(shoppingItemPrice);
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

	public int countByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice WHERE ");

			if (itemId == null) {
				query.append("itemId IS NULL");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
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

	private static Log _log = LogFactory.getLog(ShoppingItemPricePersistence.class);
}