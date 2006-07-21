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
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPersistence extends BasePersistence {
	public ShoppingItem create(String itemId) {
		ShoppingItem shoppingItem = new ShoppingItem();
		shoppingItem.setNew(true);
		shoppingItem.setPrimaryKey(itemId);

		return shoppingItem;
	}

	public ShoppingItem remove(String itemId)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItem shoppingItem = (ShoppingItem)session.get(ShoppingItem.class,
					itemId);

			if (shoppingItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingItem exists with the primary key " +
						itemId.toString());
				}

				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					itemId.toString());
			}

			return remove(shoppingItem);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem remove(ShoppingItem shoppingItem)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(shoppingItem);
			session.flush();
			ShoppingItemPool.removeByC_S(shoppingItem.getCompanyId(),
				shoppingItem.getSku());

			return shoppingItem;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.saveOrUpdate(shoppingItem);
			session.flush();
			shoppingItem.setNew(false);

			return shoppingItem;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem findByPrimaryKey(String itemId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByPrimaryKey(itemId);

		if (shoppingItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingItem exists with the primary key " +
					itemId.toString());
			}

			throw new NoSuchItemException(
				"No ShoppingItem exists with the primary key " +
				itemId.toString());
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByPrimaryKey(String itemId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingItem)session.get(ShoppingItem.class, itemId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("itemId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public List findByCategoryId(String categoryId, int begin, int end)
		throws SystemException {
		return findByCategoryId(categoryId, begin, end, null);
	}

	public List findByCategoryId(String categoryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public ShoppingItem findByCategoryId_First(String categoryId,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		List list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingItem exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchItemException(msg);
		}
		else {
			return (ShoppingItem)list.get(0);
		}
	}

	public ShoppingItem findByCategoryId_Last(String categoryId,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		int count = countByCategoryId(categoryId);
		List list = findByCategoryId(categoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingItem exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchItemException(msg);
		}
		else {
			return (ShoppingItem)list.get(0);
		}
	}

	public ShoppingItem[] findByCategoryId_PrevAndNext(String itemId,
		String categoryId, OrderByComparator obc)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByPrimaryKey(itemId);
		int count = countByCategoryId(categoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItem);
			ShoppingItem[] array = new ShoppingItem[3];
			array[0] = (ShoppingItem)objArray[0];
			array[1] = (ShoppingItem)objArray[1];
			array[2] = (ShoppingItem)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem findByC_S(String companyId, String sku)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByC_S(companyId, sku);

		if (shoppingItem == null) {
			String msg = "No ShoppingItem exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "sku=";
			msg += sku;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchItemException(msg);
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByC_S(String companyId, String sku)
		throws SystemException {
		String pk = ShoppingItemPool.getByC_S(companyId, sku);

		if (pk != null) {
			ShoppingItem shoppingItem = fetchByPrimaryKey(pk);

			if (shoppingItem != null) {
				return shoppingItem;
			}
		}

		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (sku == null) {
				query.append("sku IS NULL");
			}
			else {
				query.append("sku = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("itemId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (sku != null) {
				q.setString(queryPos++, sku);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			ShoppingItem shoppingItem = (ShoppingItem)list.get(0);
			ShoppingItemPool.putByC_S(companyId, sku,
				shoppingItem.getPrimaryKey());

			return shoppingItem;
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
				"FROM com.liferay.portlet.shopping.model.ShoppingItem ");
			query.append("ORDER BY ");
			query.append("itemId ASC");

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

	public void removeByCategoryId(String categoryId) throws SystemException {
		Iterator itr = findByCategoryId(categoryId).iterator();

		while (itr.hasNext()) {
			ShoppingItem shoppingItem = (ShoppingItem)itr.next();
			remove(shoppingItem);
		}
	}

	public void removeByC_S(String companyId, String sku)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByC_S(companyId, sku);
		remove(shoppingItem);
	}

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public int countByC_S(String companyId, String sku)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (sku == null) {
				query.append("sku IS NULL");
			}
			else {
				query.append("sku = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (sku != null) {
				q.setString(queryPos++, sku);
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

	public List getShoppingItemPrices(String pk)
		throws NoSuchItemException, SystemException {
		return getShoppingItemPrices(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getShoppingItemPrices(String pk, int begin, int end)
		throws NoSuchItemException, SystemException {
		return getShoppingItemPrices(pk, begin, end, null);
	}

	public List getShoppingItemPrices(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETSHOPPINGITEMPRICES;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "ShoppingItemPrice.itemId ASC";
				sql += ", ";
				sql += "ShoppingItemPrice.itemPriceId ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("ShoppingItemPrice",
				com.liferay.portlet.shopping.model.ShoppingItemPrice.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int getShoppingItemPricesSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETSHOPPINGITEMPRICESSIZE);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

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

	public boolean containsShoppingItemPrice(String pk,
		String shoppingItemPricePK) throws SystemException {
		try {
			return containsShoppingItemPrice.contains(pk, shoppingItemPricePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsShoppingItemPrices(String pk)
		throws SystemException {
		if (getShoppingItemPricesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void initDao() {
		containsShoppingItemPrice = new ContainsShoppingItemPrice(this);
	}

	protected ContainsShoppingItemPrice containsShoppingItemPrice;

	protected class ContainsShoppingItemPrice extends MappingSqlQuery {
		protected ContainsShoppingItemPrice(ShoppingItemPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSSHOPPINGITEMPRICE);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String itemId, String itemPriceId) {
			List results = execute(new Object[] { itemId, itemPriceId });

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	private static final String _SQL_GETSHOPPINGITEMPRICES = "SELECT {ShoppingItemPrice.*} FROM ShoppingItemPrice INNER JOIN ShoppingItem ON (ShoppingItem.itemId = ?) AND (ShoppingItem.itemPriceId = ShoppingItemPrice.itemPriceId)";
	private static final String _SQL_GETSHOPPINGITEMPRICESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ShoppingItemPrice WHERE itemId = ?";
	private static final String _SQL_CONTAINSSHOPPINGITEMPRICE = "SELECT COUNT(*) AS COUNT_VALUE FROM ShoppingItemPrice WHERE itemId = ? AND itemPriceId = ?";
	private static Log _log = LogFactory.getLog(ShoppingItemPersistence.class);
}