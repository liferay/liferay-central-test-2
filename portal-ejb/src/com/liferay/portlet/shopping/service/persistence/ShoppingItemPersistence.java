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

import com.liferay.portlet.shopping.NoSuchItemException;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="ShoppingItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPersistence extends BasePersistence {
	public com.liferay.portlet.shopping.model.ShoppingItem create(String itemId) {
		ShoppingItemHBM shoppingItemHBM = new ShoppingItemHBM();
		shoppingItemHBM.setNew(true);
		shoppingItemHBM.setPrimaryKey(itemId);

		return ShoppingItemHBMUtil.model(shoppingItemHBM);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem remove(String itemId)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					itemId);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					itemId.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					itemId.toString());
			}

			session.delete(shoppingItemHBM);
			session.flush();

			return ShoppingItemHBMUtil.model(shoppingItemHBM);
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
			if (shoppingItem.isNew() || shoppingItem.isModified()) {
				session = openSession();

				if (shoppingItem.isNew()) {
					ShoppingItemHBM shoppingItemHBM = new ShoppingItemHBM();
					shoppingItemHBM.setItemId(shoppingItem.getItemId());
					shoppingItemHBM.setCompanyId(shoppingItem.getCompanyId());
					shoppingItemHBM.setUserId(shoppingItem.getUserId());
					shoppingItemHBM.setUserName(shoppingItem.getUserName());
					shoppingItemHBM.setCreateDate(shoppingItem.getCreateDate());
					shoppingItemHBM.setModifiedDate(shoppingItem.getModifiedDate());
					shoppingItemHBM.setCategoryId(shoppingItem.getCategoryId());
					shoppingItemHBM.setSku(shoppingItem.getSku());
					shoppingItemHBM.setName(shoppingItem.getName());
					shoppingItemHBM.setDescription(shoppingItem.getDescription());
					shoppingItemHBM.setProperties(shoppingItem.getProperties());
					shoppingItemHBM.setFields(shoppingItem.getFields());
					shoppingItemHBM.setFieldsQuantities(shoppingItem.getFieldsQuantities());
					shoppingItemHBM.setMinQuantity(shoppingItem.getMinQuantity());
					shoppingItemHBM.setMaxQuantity(shoppingItem.getMaxQuantity());
					shoppingItemHBM.setPrice(shoppingItem.getPrice());
					shoppingItemHBM.setDiscount(shoppingItem.getDiscount());
					shoppingItemHBM.setTaxable(shoppingItem.getTaxable());
					shoppingItemHBM.setShipping(shoppingItem.getShipping());
					shoppingItemHBM.setUseShippingFormula(shoppingItem.getUseShippingFormula());
					shoppingItemHBM.setRequiresShipping(shoppingItem.getRequiresShipping());
					shoppingItemHBM.setStockQuantity(shoppingItem.getStockQuantity());
					shoppingItemHBM.setFeatured(shoppingItem.getFeatured());
					shoppingItemHBM.setSale(shoppingItem.getSale());
					shoppingItemHBM.setSmallImage(shoppingItem.getSmallImage());
					shoppingItemHBM.setSmallImageURL(shoppingItem.getSmallImageURL());
					shoppingItemHBM.setMediumImage(shoppingItem.getMediumImage());
					shoppingItemHBM.setMediumImageURL(shoppingItem.getMediumImageURL());
					shoppingItemHBM.setLargeImage(shoppingItem.getLargeImage());
					shoppingItemHBM.setLargeImageURL(shoppingItem.getLargeImageURL());
					shoppingItemHBM.setShoppingItemPrices(new HashSet());
					session.save(shoppingItemHBM);
					session.flush();
				}
				else {
					ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
							shoppingItem.getPrimaryKey());

					if (shoppingItemHBM != null) {
						shoppingItemHBM.setCompanyId(shoppingItem.getCompanyId());
						shoppingItemHBM.setUserId(shoppingItem.getUserId());
						shoppingItemHBM.setUserName(shoppingItem.getUserName());
						shoppingItemHBM.setCreateDate(shoppingItem.getCreateDate());
						shoppingItemHBM.setModifiedDate(shoppingItem.getModifiedDate());
						shoppingItemHBM.setCategoryId(shoppingItem.getCategoryId());
						shoppingItemHBM.setSku(shoppingItem.getSku());
						shoppingItemHBM.setName(shoppingItem.getName());
						shoppingItemHBM.setDescription(shoppingItem.getDescription());
						shoppingItemHBM.setProperties(shoppingItem.getProperties());
						shoppingItemHBM.setFields(shoppingItem.getFields());
						shoppingItemHBM.setFieldsQuantities(shoppingItem.getFieldsQuantities());
						shoppingItemHBM.setMinQuantity(shoppingItem.getMinQuantity());
						shoppingItemHBM.setMaxQuantity(shoppingItem.getMaxQuantity());
						shoppingItemHBM.setPrice(shoppingItem.getPrice());
						shoppingItemHBM.setDiscount(shoppingItem.getDiscount());
						shoppingItemHBM.setTaxable(shoppingItem.getTaxable());
						shoppingItemHBM.setShipping(shoppingItem.getShipping());
						shoppingItemHBM.setUseShippingFormula(shoppingItem.getUseShippingFormula());
						shoppingItemHBM.setRequiresShipping(shoppingItem.getRequiresShipping());
						shoppingItemHBM.setStockQuantity(shoppingItem.getStockQuantity());
						shoppingItemHBM.setFeatured(shoppingItem.getFeatured());
						shoppingItemHBM.setSale(shoppingItem.getSale());
						shoppingItemHBM.setSmallImage(shoppingItem.getSmallImage());
						shoppingItemHBM.setSmallImageURL(shoppingItem.getSmallImageURL());
						shoppingItemHBM.setMediumImage(shoppingItem.getMediumImage());
						shoppingItemHBM.setMediumImageURL(shoppingItem.getMediumImageURL());
						shoppingItemHBM.setLargeImage(shoppingItem.getLargeImage());
						shoppingItemHBM.setLargeImageURL(shoppingItem.getLargeImageURL());
						session.flush();
					}
					else {
						shoppingItemHBM = new ShoppingItemHBM();
						shoppingItemHBM.setItemId(shoppingItem.getItemId());
						shoppingItemHBM.setCompanyId(shoppingItem.getCompanyId());
						shoppingItemHBM.setUserId(shoppingItem.getUserId());
						shoppingItemHBM.setUserName(shoppingItem.getUserName());
						shoppingItemHBM.setCreateDate(shoppingItem.getCreateDate());
						shoppingItemHBM.setModifiedDate(shoppingItem.getModifiedDate());
						shoppingItemHBM.setCategoryId(shoppingItem.getCategoryId());
						shoppingItemHBM.setSku(shoppingItem.getSku());
						shoppingItemHBM.setName(shoppingItem.getName());
						shoppingItemHBM.setDescription(shoppingItem.getDescription());
						shoppingItemHBM.setProperties(shoppingItem.getProperties());
						shoppingItemHBM.setFields(shoppingItem.getFields());
						shoppingItemHBM.setFieldsQuantities(shoppingItem.getFieldsQuantities());
						shoppingItemHBM.setMinQuantity(shoppingItem.getMinQuantity());
						shoppingItemHBM.setMaxQuantity(shoppingItem.getMaxQuantity());
						shoppingItemHBM.setPrice(shoppingItem.getPrice());
						shoppingItemHBM.setDiscount(shoppingItem.getDiscount());
						shoppingItemHBM.setTaxable(shoppingItem.getTaxable());
						shoppingItemHBM.setShipping(shoppingItem.getShipping());
						shoppingItemHBM.setUseShippingFormula(shoppingItem.getUseShippingFormula());
						shoppingItemHBM.setRequiresShipping(shoppingItem.getRequiresShipping());
						shoppingItemHBM.setStockQuantity(shoppingItem.getStockQuantity());
						shoppingItemHBM.setFeatured(shoppingItem.getFeatured());
						shoppingItemHBM.setSale(shoppingItem.getSale());
						shoppingItemHBM.setSmallImage(shoppingItem.getSmallImage());
						shoppingItemHBM.setSmallImageURL(shoppingItem.getSmallImageURL());
						shoppingItemHBM.setMediumImage(shoppingItem.getMediumImage());
						shoppingItemHBM.setMediumImageURL(shoppingItem.getMediumImageURL());
						shoppingItemHBM.setLargeImage(shoppingItem.getLargeImage());
						shoppingItemHBM.setLargeImageURL(shoppingItem.getLargeImageURL());
						session.save(shoppingItemHBM);
						session.flush();
					}
				}

				shoppingItem.setNew(false);
				shoppingItem.setModified(false);
			}

			return shoppingItem;
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
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT shoppingItemPriceHBM FROM ");
			query.append(com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM.class.getName());
			query.append(" shoppingItemHBM ");
			query.append(
				"JOIN shoppingItemHBM.shoppingItemPrices AS shoppingItemPriceHBM ");
			query.append("WHERE shoppingItemHBM.itemId = ? ");
			query.append("ORDER BY ");
			query.append("shoppingItemPriceHBM.itemId ASC").append(", ");
			query.append("shoppingItemPriceHBM.itemPriceId ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)itr.next();
				list.add(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBMUtil.model(
						shoppingItemPriceHBM));
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

	public List getShoppingItemPrices(String pk, int begin, int end)
		throws NoSuchItemException, SystemException {
		return getShoppingItemPrices(pk, begin, end, null);
	}

	public List getShoppingItemPrices(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT shoppingItemPriceHBM FROM ");
			query.append(com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM.class.getName());
			query.append(" shoppingItemHBM ");
			query.append(
				"JOIN shoppingItemHBM.shoppingItemPrices AS shoppingItemPriceHBM ");
			query.append("WHERE shoppingItemHBM.itemId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("shoppingItemPriceHBM.itemId ASC").append(", ");
				query.append("shoppingItemPriceHBM.itemPriceId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)itr.next();
				list.add(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBMUtil.model(
						shoppingItemPriceHBM));
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

	public int getShoppingItemPricesSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM.class.getName());
			query.append(" shoppingItemHBM ");
			query.append(
				"JOIN shoppingItemHBM.shoppingItemPrices AS shoppingItemPriceHBM ");
			query.append("WHERE shoppingItemHBM.itemId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

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

	public void setShoppingItemPrices(String pk, String[] pks)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			Set shoppingItemPricesSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
						pks[i]);

				if (shoppingItemPriceHBM == null) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						pks[i].toString());
				}

				shoppingItemPricesSet.add(shoppingItemPriceHBM);
			}

			shoppingItemHBM.setShoppingItemPrices(shoppingItemPricesSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setShoppingItemPrices(String pk, List shoppingItemPrices)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			Set shoppingItemPricesSet = new HashSet();
			Iterator itr = shoppingItemPrices.iterator();

			while (itr.hasNext()) {
				com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice =
					(com.liferay.portlet.shopping.model.ShoppingItemPrice)itr.next();
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
						shoppingItemPrice.getPrimaryKey());

				if (shoppingItemPriceHBM == null) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPrice.getPrimaryKey().toString());
					throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPrice.getPrimaryKey().toString());
				}

				shoppingItemPricesSet.add(shoppingItemPriceHBM);
			}

			shoppingItemHBM.setShoppingItemPrices(shoppingItemPricesSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addShoppingItemPrice(String pk, String shoppingItemPricePK)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
				null;
			shoppingItemPriceHBM = (com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
					shoppingItemPricePK);

			if (shoppingItemPriceHBM == null) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					shoppingItemPricePK.toString());
				throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					shoppingItemPricePK.toString());
			}

			boolean value = shoppingItemHBM.getShoppingItemPrices().add(shoppingItemPriceHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addShoppingItemPrice(String pk,
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
				null;
			shoppingItemPriceHBM = (com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
					shoppingItemPrice.getPrimaryKey());

			if (shoppingItemPriceHBM == null) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					shoppingItemPrice.getPrimaryKey().toString());
				throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					shoppingItemPrice.getPrimaryKey().toString());
			}

			boolean value = shoppingItemHBM.getShoppingItemPrices().add(shoppingItemPriceHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addShoppingItemPrices(String pk,
		String[] shoppingItemPricePKs)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < shoppingItemPricePKs.length; i++) {
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					null;
				shoppingItemPriceHBM = (com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
						shoppingItemPricePKs[i]);

				if (shoppingItemPriceHBM == null) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPricePKs[i].toString());
					throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPricePKs[i].toString());
				}

				if (shoppingItemHBM.getShoppingItemPrices().add(shoppingItemPriceHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addShoppingItemPrices(String pk, List shoppingItemPrices)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < shoppingItemPrices.size(); i++) {
				com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice =
					(com.liferay.portlet.shopping.model.ShoppingItemPrice)shoppingItemPrices.get(i);
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
						shoppingItemPrice.getPrimaryKey());

				if (shoppingItemPriceHBM == null) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPrice.getPrimaryKey().toString());
					throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPrice.getPrimaryKey().toString());
				}

				if (shoppingItemHBM.getShoppingItemPrices().add(shoppingItemPriceHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void clearShoppingItemPrices(String pk)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			shoppingItemHBM.getShoppingItemPrices().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeShoppingItemPrice(String pk, String shoppingItemPricePK)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
				null;
			shoppingItemPriceHBM = (com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
					shoppingItemPricePK);

			if (shoppingItemPriceHBM == null) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					shoppingItemPricePK.toString());
				throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					shoppingItemPricePK.toString());
			}

			boolean value = shoppingItemHBM.getShoppingItemPrices().remove(shoppingItemPriceHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeShoppingItemPrice(String pk,
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
				null;
			shoppingItemPriceHBM = (com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
					shoppingItemPrice.getPrimaryKey());

			if (shoppingItemPriceHBM == null) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					shoppingItemPrice.getPrimaryKey().toString());
				throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
					shoppingItemPrice.getPrimaryKey().toString());
			}

			boolean value = shoppingItemHBM.getShoppingItemPrices().remove(shoppingItemPriceHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeShoppingItemPrices(String pk,
		String[] shoppingItemPricePKs)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < shoppingItemPricePKs.length; i++) {
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					null;
				shoppingItemPriceHBM = (com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
						shoppingItemPricePKs[i]);

				if (shoppingItemPriceHBM == null) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPricePKs[i].toString());
					throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPricePKs[i].toString());
				}

				if (shoppingItemHBM.getShoppingItemPrices().remove(shoppingItemPriceHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeShoppingItemPrices(String pk, List shoppingItemPrices)
		throws NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					pk);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					pk.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < shoppingItemPrices.size(); i++) {
				com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice =
					(com.liferay.portlet.shopping.model.ShoppingItemPrice)shoppingItemPrices.get(i);
				com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM shoppingItemPriceHBM =
					(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM)session.get(com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceHBM.class,
						shoppingItemPrice.getPrimaryKey());

				if (shoppingItemPriceHBM == null) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPrice.getPrimaryKey().toString());
					throw new com.liferay.portlet.shopping.NoSuchItemPriceException(
						"No ShoppingItemPrice exists with the primary key " +
						shoppingItemPrice.getPrimaryKey().toString());
				}

				if (shoppingItemHBM.getShoppingItemPrices().remove(shoppingItemPriceHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		String itemId) throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)session.get(ShoppingItemHBM.class,
					itemId);

			if (shoppingItemHBM == null) {
				_log.warn("No ShoppingItem exists with the primary key " +
					itemId.toString());
				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " +
					itemId.toString());
			}

			return ShoppingItemHBMUtil.model(shoppingItemHBM);
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
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
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

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)itr.next();
				list.add(ShoppingItemHBMUtil.model(shoppingItemHBM));
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
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)itr.next();
				list.add(ShoppingItemHBMUtil.model(shoppingItemHBM));
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

	public com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_First(
		String categoryId, OrderByComparator obc)
		throws NoSuchItemException, SystemException {
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
			return (com.liferay.portlet.shopping.model.ShoppingItem)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_Last(
		String categoryId, OrderByComparator obc)
		throws NoSuchItemException, SystemException {
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
			return (com.liferay.portlet.shopping.model.ShoppingItem)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItem[] findByCategoryId_PrevAndNext(
		String itemId, String categoryId, OrderByComparator obc)
		throws NoSuchItemException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem = findByPrimaryKey(itemId);
		int count = countByCategoryId(categoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
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
					shoppingItem, ShoppingItemHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingItem[] array = new com.liferay.portlet.shopping.model.ShoppingItem[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingItem)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingItem)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingItem)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		String companyId, String sku)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (sku == null) {
				query.append("sku is null");
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

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No ShoppingItem exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "sku=";
				msg += sku;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchItemException(msg);
			}

			ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)itr.next();

			return ShoppingItemHBMUtil.model(shoppingItemHBM);
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
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM ");
			query.append("ORDER BY ");
			query.append("itemId ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)itr.next();
				list.add(ShoppingItemHBMUtil.model(shoppingItemHBM));
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

	public void removeByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
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

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)itr.next();
				session.delete(shoppingItemHBM);
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

	public void removeByC_S(String companyId, String sku)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (sku == null) {
				query.append("sku is null");
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

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingItemHBM shoppingItemHBM = (ShoppingItemHBM)itr.next();
				session.delete(shoppingItemHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No ShoppingItem exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "sku=";
				msg += sku;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchItemException(msg);
			}
			else {
				throw new SystemException(he);
			}
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
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
				"FROM ShoppingItem IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (sku == null) {
				query.append("sku is null");
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

	private static Log _log = LogFactory.getLog(ShoppingItemPersistence.class);
}