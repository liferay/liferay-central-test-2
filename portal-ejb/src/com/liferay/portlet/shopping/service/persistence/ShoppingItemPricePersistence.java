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
 * <a href="ShoppingItemPricePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPricePersistence extends BasePersistence {
	public com.liferay.portlet.shopping.model.ShoppingItemPrice create(
		String itemPriceId) {
		ShoppingItemPriceHBM shoppingItemPriceHBM = new ShoppingItemPriceHBM();
		shoppingItemPriceHBM.setNew(true);
		shoppingItemPriceHBM.setPrimaryKey(itemPriceId);

		return ShoppingItemPriceHBMUtil.model(shoppingItemPriceHBM);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice remove(
		String itemPriceId) throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)session.get(ShoppingItemPriceHBM.class,
					itemPriceId);

			if (shoppingItemPriceHBM == null) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					itemPriceId.toString());
				throw new NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					itemPriceId.toString());
			}

			session.delete(shoppingItemPriceHBM);
			session.flush();

			return ShoppingItemPriceHBMUtil.model(shoppingItemPriceHBM);
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
					ShoppingItemPriceHBM shoppingItemPriceHBM = new ShoppingItemPriceHBM();
					shoppingItemPriceHBM.setItemPriceId(shoppingItemPrice.getItemPriceId());
					shoppingItemPriceHBM.setItemId(shoppingItemPrice.getItemId());
					shoppingItemPriceHBM.setMinQuantity(shoppingItemPrice.getMinQuantity());
					shoppingItemPriceHBM.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
					shoppingItemPriceHBM.setPrice(shoppingItemPrice.getPrice());
					shoppingItemPriceHBM.setDiscount(shoppingItemPrice.getDiscount());
					shoppingItemPriceHBM.setTaxable(shoppingItemPrice.getTaxable());
					shoppingItemPriceHBM.setShipping(shoppingItemPrice.getShipping());
					shoppingItemPriceHBM.setUseShippingFormula(shoppingItemPrice.getUseShippingFormula());
					shoppingItemPriceHBM.setStatus(shoppingItemPrice.getStatus());
					session.save(shoppingItemPriceHBM);
					session.flush();
				}
				else {
					ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)session.get(ShoppingItemPriceHBM.class,
							shoppingItemPrice.getPrimaryKey());

					if (shoppingItemPriceHBM != null) {
						shoppingItemPriceHBM.setItemId(shoppingItemPrice.getItemId());
						shoppingItemPriceHBM.setMinQuantity(shoppingItemPrice.getMinQuantity());
						shoppingItemPriceHBM.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
						shoppingItemPriceHBM.setPrice(shoppingItemPrice.getPrice());
						shoppingItemPriceHBM.setDiscount(shoppingItemPrice.getDiscount());
						shoppingItemPriceHBM.setTaxable(shoppingItemPrice.getTaxable());
						shoppingItemPriceHBM.setShipping(shoppingItemPrice.getShipping());
						shoppingItemPriceHBM.setUseShippingFormula(shoppingItemPrice.getUseShippingFormula());
						shoppingItemPriceHBM.setStatus(shoppingItemPrice.getStatus());
						session.flush();
					}
					else {
						shoppingItemPriceHBM = new ShoppingItemPriceHBM();
						shoppingItemPriceHBM.setItemPriceId(shoppingItemPrice.getItemPriceId());
						shoppingItemPriceHBM.setItemId(shoppingItemPrice.getItemId());
						shoppingItemPriceHBM.setMinQuantity(shoppingItemPrice.getMinQuantity());
						shoppingItemPriceHBM.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
						shoppingItemPriceHBM.setPrice(shoppingItemPrice.getPrice());
						shoppingItemPriceHBM.setDiscount(shoppingItemPrice.getDiscount());
						shoppingItemPriceHBM.setTaxable(shoppingItemPrice.getTaxable());
						shoppingItemPriceHBM.setShipping(shoppingItemPrice.getShipping());
						shoppingItemPriceHBM.setUseShippingFormula(shoppingItemPrice.getUseShippingFormula());
						shoppingItemPriceHBM.setStatus(shoppingItemPrice.getStatus());
						session.save(shoppingItemPriceHBM);
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

	public com.liferay.portlet.shopping.model.ShoppingItemPrice findByPrimaryKey(
		String itemPriceId) throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)session.get(ShoppingItemPriceHBM.class,
					itemPriceId);

			if (shoppingItemPriceHBM == null) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					itemPriceId.toString());
				throw new NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					itemPriceId.toString());
			}

			return ShoppingItemPriceHBMUtil.model(shoppingItemPriceHBM);
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
				"FROM ShoppingItemPrice IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)itr.next();
				list.add(ShoppingItemPriceHBMUtil.model(shoppingItemPriceHBM));
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
				"FROM ShoppingItemPrice IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)itr.next();
				list.add(ShoppingItemPriceHBMUtil.model(shoppingItemPriceHBM));
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

	public com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_First(
		String itemId, OrderByComparator obc)
		throws NoSuchItemPriceException, SystemException {
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
			return (com.liferay.portlet.shopping.model.ShoppingItemPrice)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_Last(
		String itemId, OrderByComparator obc)
		throws NoSuchItemPriceException, SystemException {
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
			return (com.liferay.portlet.shopping.model.ShoppingItemPrice)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice[] findByItemId_PrevAndNext(
		String itemPriceId, String itemId, OrderByComparator obc)
		throws NoSuchItemPriceException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice = findByPrimaryKey(itemPriceId);
		int count = countByItemId(itemId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItemPrice IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
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
					shoppingItemPrice, ShoppingItemPriceHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingItemPrice[] array = new com.liferay.portlet.shopping.model.ShoppingItemPrice[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingItemPrice)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingItemPrice)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingItemPrice)objArray[2];

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
				"FROM ShoppingItemPrice IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("itemPriceId ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)itr.next();
				list.add(ShoppingItemPriceHBMUtil.model(shoppingItemPriceHBM));
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

	public void removeByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItemPrice IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
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
				ShoppingItemPriceHBM shoppingItemPriceHBM = (ShoppingItemPriceHBM)itr.next();
				session.delete(shoppingItemPriceHBM);
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
				"FROM ShoppingItemPrice IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
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

	private static Log _log = LogFactory.getLog(ShoppingItemPricePersistence.class);
}