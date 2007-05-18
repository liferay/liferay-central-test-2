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
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.impl.ShoppingItemImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
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
 * <a href="ShoppingItemPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemPersistenceImpl extends BasePersistence
	implements ShoppingItemPersistence {
	public ShoppingItem create(String itemId) {
		ShoppingItem shoppingItem = new ShoppingItemImpl();
		shoppingItem.setNew(true);
		shoppingItem.setPrimaryKey(itemId);

		return shoppingItem;
	}

	public ShoppingItem remove(String itemId)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItem shoppingItem = (ShoppingItem)session.get(ShoppingItemImpl.class,
					itemId);

			if (shoppingItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingItem exists with the primary key " +
						itemId);
				}

				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " + itemId);
			}

			return remove(shoppingItem);
		}
		catch (NoSuchItemException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			return shoppingItem;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws SystemException {
		return update(shoppingItem, false);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(shoppingItem);
			}
			else {
				if (shoppingItem.isNew()) {
					session.save(shoppingItem);
				}
			}

			session.flush();
			shoppingItem.setNew(false);

			return shoppingItem;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
					itemId);
			}

			throw new NoSuchItemException(
				"No ShoppingItem exists with the primary key " + itemId);
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByPrimaryKey(String itemId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingItem)session.get(ShoppingItemImpl.class, itemId);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
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
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
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
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem findByCategoryId_First(String categoryId,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		List list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No ShoppingItem exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("categoryId=");
			msg.append(categoryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchItemException(msg.toString());
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
			StringMaker msg = new StringMaker();
			msg.append("No ShoppingItem exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("categoryId=");
			msg.append(categoryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchItemException(msg.toString());
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

			StringMaker query = new StringMaker();
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
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItem);
			ShoppingItem[] array = new ShoppingItemImpl[3];
			array[0] = (ShoppingItem)objArray[0];
			array[1] = (ShoppingItem)objArray[1];
			array[2] = (ShoppingItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem findByC_S(long companyId, String sku)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByC_S(companyId, sku);

		if (shoppingItem == null) {
			StringMaker msg = new StringMaker();
			msg.append("No ShoppingItem exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("sku=");
			msg.append(sku);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByC_S(long companyId, String sku)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");
			query.append("companyId = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			if (sku != null) {
				q.setString(queryPos++, sku);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			ShoppingItem shoppingItem = (ShoppingItem)list.get(0);

			return shoppingItem;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

	public void removeByC_S(long companyId, String sku)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByC_S(companyId, sku);
		remove(shoppingItem);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((ShoppingItem)itr.next());
		}
	}

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
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
			q.setCacheable(true);

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_S(long companyId, String sku) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");

			if (sku == null) {
				query.append("sku IS NULL");
			}
			else {
				query.append("sku = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.shopping.model.ShoppingItem");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker sm = new StringMaker();
			sm.append(_SQL_GETSHOPPINGITEMPRICES);

			if (obc != null) {
				sm.append("ORDER BY ");
				sm.append(obc.getOrderBy());
			}
			else {
				sm.append("ORDER BY ");
				sm.append("ShoppingItemPrice.itemId ASC");
				sm.append(", ");
				sm.append("ShoppingItemPrice.itemPriceId ASC");
			}

			String sql = sm.toString();
			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("ShoppingItemPrice",
				com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl.class);

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
			q.setCacheable(false);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		protected ContainsShoppingItemPrice(
			ShoppingItemPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				_SQL_CONTAINSSHOPPINGITEMPRICE);
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

	private static final String _SQL_GETSHOPPINGITEMPRICES = "SELECT {ShoppingItemPrice.*} FROM ShoppingItemPrice INNER JOIN ShoppingItem ON (ShoppingItem.itemId = ShoppingItemPrice.itemId) WHERE (ShoppingItem.itemId = ?)";
	private static final String _SQL_GETSHOPPINGITEMPRICESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ShoppingItemPrice WHERE itemId = ?";
	private static final String _SQL_CONTAINSSHOPPINGITEMPRICE = "SELECT COUNT(*) AS COUNT_VALUE FROM ShoppingItemPrice WHERE itemId = ? AND itemPriceId = ?";
	private static Log _log = LogFactory.getLog(ShoppingItemPersistenceImpl.class);
}