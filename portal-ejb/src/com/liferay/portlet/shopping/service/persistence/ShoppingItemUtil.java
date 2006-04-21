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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="ShoppingItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemUtil {
	public static final String CLASS_NAME = ShoppingItemUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItem"));

	public static com.liferay.portlet.shopping.model.ShoppingItem create(
		java.lang.String itemId) {
		return getPersistence().create(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		java.lang.String itemId)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(itemId));
		}

		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem = getPersistence()
																		   .remove(itemId);

		if (listener != null) {
			listener.onAfterRemove(shoppingItem);
		}

		return shoppingItem;
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = shoppingItem.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingItem);
			}
			else {
				listener.onBeforeUpdate(shoppingItem);
			}
		}

		shoppingItem = getPersistence().update(shoppingItem);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingItem);
			}
			else {
				listener.onAfterUpdate(shoppingItem);
			}
		}

		return shoppingItem;
	}

	public static java.util.List getShoppingItemPrices(java.lang.String pk)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk);
	}

	public static java.util.List getShoppingItemPrices(java.lang.String pk,
		int begin, int end)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk, begin, end);
	}

	public static java.util.List getShoppingItemPrices(java.lang.String pk,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk, begin, end, obc);
	}

	public static int getShoppingItemPricesSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPricesSize(pk);
	}

	public static void setShoppingItemPrices(java.lang.String pk,
		java.lang.String[] pks)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		getPersistence().setShoppingItemPrices(pk, pks);
	}

	public static void setShoppingItemPrices(java.lang.String pk,
		java.util.List shoppingItemPrices)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		getPersistence().setShoppingItemPrices(pk, shoppingItemPrices);
	}

	public static boolean addShoppingItemPrice(java.lang.String pk,
		java.lang.String shoppingItemPricePK)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().addShoppingItemPrice(pk, shoppingItemPricePK);
	}

	public static boolean addShoppingItemPrice(java.lang.String pk,
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().addShoppingItemPrice(pk, shoppingItemPrice);
	}

	public static boolean addShoppingItemPrices(java.lang.String pk,
		java.lang.String[] shoppingItemPricePKs)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().addShoppingItemPrices(pk, shoppingItemPricePKs);
	}

	public static boolean addShoppingItemPrices(java.lang.String pk,
		java.util.List shoppingItemPrices)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().addShoppingItemPrices(pk, shoppingItemPrices);
	}

	public static void clearShoppingItemPrices(java.lang.String pk)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		getPersistence().clearShoppingItemPrices(pk);
	}

	public static boolean removeShoppingItemPrice(java.lang.String pk,
		java.lang.String shoppingItemPricePK)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeShoppingItemPrice(pk, shoppingItemPricePK);
	}

	public static boolean removeShoppingItemPrice(java.lang.String pk,
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeShoppingItemPrice(pk, shoppingItemPrice);
	}

	public static boolean removeShoppingItemPrices(java.lang.String pk,
		java.lang.String[] shoppingItemPricePKs)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeShoppingItemPrices(pk,
			shoppingItemPricePKs);
	}

	public static boolean removeShoppingItemPrices(java.lang.String pk,
		java.util.List shoppingItemPrices)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeShoppingItemPrices(pk, shoppingItemPrices);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		java.lang.String itemId)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(itemId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] findByCompanyId_PrevAndNext(
		java.lang.String itemId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(itemId, companyId,
			obc);
	}

	public static java.util.List findBySupplierUserId(
		java.lang.String supplierUserId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySupplierUserId(supplierUserId);
	}

	public static java.util.List findBySupplierUserId(
		java.lang.String supplierUserId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySupplierUserId(supplierUserId, begin, end);
	}

	public static java.util.List findBySupplierUserId(
		java.lang.String supplierUserId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySupplierUserId(supplierUserId, begin,
			end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findBySupplierUserId_First(
		java.lang.String supplierUserId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findBySupplierUserId_First(supplierUserId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findBySupplierUserId_Last(
		java.lang.String supplierUserId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findBySupplierUserId_Last(supplierUserId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] findBySupplierUserId_PrevAndNext(
		java.lang.String itemId, java.lang.String supplierUserId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findBySupplierUserId_PrevAndNext(itemId,
			supplierUserId, obc);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, categoryId);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String categoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, categoryId, begin, end);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String categoryId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, categoryId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_C_First(
		java.lang.String companyId, java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_First(companyId, categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_C_Last(
		java.lang.String companyId, java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_Last(companyId, categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] findByC_C_PrevAndNext(
		java.lang.String itemId, java.lang.String companyId,
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_PrevAndNext(itemId, companyId,
			categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		java.lang.String companyId, java.lang.String sku)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, sku);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeBySupplierUserId(java.lang.String supplierUserId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeBySupplierUserId(supplierUserId);
	}

	public static void removeByC_C(java.lang.String companyId,
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(companyId, categoryId);
	}

	public static void removeByC_S(java.lang.String companyId,
		java.lang.String sku)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_S(companyId, sku);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countBySupplierUserId(java.lang.String supplierUserId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countBySupplierUserId(supplierUserId);
	}

	public static int countByC_C(java.lang.String companyId,
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(companyId, categoryId);
	}

	public static int countByC_S(java.lang.String companyId,
		java.lang.String sku) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, sku);
	}

	public static ShoppingItemPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ShoppingItemUtil util = (ShoppingItemUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ShoppingItemPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ShoppingItemUtil.class);
	private ShoppingItemPersistence _persistence;
}