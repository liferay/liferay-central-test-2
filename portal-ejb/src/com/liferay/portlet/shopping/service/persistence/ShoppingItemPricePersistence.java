/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.NoSuchItemPriceException;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl;

import com.liferay.util.dao.DynamicQuery;
import com.liferay.util.dao.DynamicQueryInitializer;
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
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemPricePersistence extends BasePersistence {
	public ShoppingItemPrice create(String itemPriceId) {
		ShoppingItemPrice shoppingItemPrice = new ShoppingItemPriceImpl();
		shoppingItemPrice.setNew(true);
		shoppingItemPrice.setPrimaryKey(itemPriceId);

		return shoppingItemPrice;
	}

	public ShoppingItemPrice remove(String itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
					itemPriceId);

			if (shoppingItemPrice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						itemPriceId);
				}

				throw new NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					itemPriceId);
			}

			return remove(shoppingItemPrice);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItemPrice remove(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
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
		return update(shoppingItemPrice, false);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice update(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(shoppingItemPrice);
			}
			else {
				if (shoppingItemPrice.isNew()) {
					session.save(shoppingItemPrice);
				}
			}

			session.flush();
			shoppingItemPrice.setNew(false);

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
		ShoppingItemPrice shoppingItemPrice = fetchByPrimaryKey(itemPriceId);

		if (shoppingItemPrice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					itemPriceId);
			}

			throw new NoSuchItemPriceException(
				"No ShoppingItemPrice exists with the primary key " +
				itemPriceId);
		}

		return shoppingItemPrice;
	}

	public ShoppingItemPrice fetchByPrimaryKey(String itemPriceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
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
			q.setCacheable(true);

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
			q.setCacheable(true);

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
			q.setCacheable(true);

			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItemPrice);
			ShoppingItemPrice[] array = new ShoppingItemPriceImpl[3];
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

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC").append(", ");
				query.append("itemPriceId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByItemId(String itemId) throws SystemException {
		Iterator itr = findByItemId(itemId).iterator();

		while (itr.hasNext()) {
			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)itr.next();
			remove(shoppingItemPrice);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((ShoppingItemPrice)itr.next());
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
			q.setCacheable(true);

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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemPrice");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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