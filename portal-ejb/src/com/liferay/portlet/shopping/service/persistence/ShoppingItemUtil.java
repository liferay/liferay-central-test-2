/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
	public static com.liferay.portlet.shopping.model.ShoppingItem create(
		java.lang.String itemId) {
		return getPersistence().create(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		java.lang.String itemId)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
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

	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(shoppingItem);
		}

		shoppingItem = getPersistence().remove(shoppingItem);

		if (listener != null) {
			listener.onAfterRemove(shoppingItem);
		}

		return shoppingItem;
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
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

	public static com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
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

		shoppingItem = getPersistence().update(shoppingItem, saveOrUpdate);

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

	public static com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		java.lang.String itemId)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByPrimaryKey(
		java.lang.String itemId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(itemId);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_First(
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_Last(
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] findByCategoryId_PrevAndNext(
		java.lang.String itemId, java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_PrevAndNext(itemId,
			categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		java.lang.String companyId, java.lang.String sku)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, sku);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		java.lang.String companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_S(companyId, sku);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static void removeByC_S(java.lang.String companyId,
		java.lang.String sku)
		throws com.liferay.portlet.shopping.NoSuchItemException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_S(companyId, sku);
	}

	public static int countByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static int countByC_S(java.lang.String companyId,
		java.lang.String sku) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, sku);
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

	public static boolean containsShoppingItemPrice(java.lang.String pk,
		java.lang.String shoppingItemPricePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsShoppingItemPrice(pk,
			shoppingItemPricePK);
	}

	public static boolean containsShoppingItemPrices(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsShoppingItemPrices(pk);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ShoppingItemPersistence getPersistence() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (ShoppingItemUtil)ctx.getBean(_UTIL);
		}

		return _util._persistence;
	}

	public void setPersistence(ShoppingItemPersistence persistence) {
		_persistence = persistence;
	}

	private static final String _UTIL = ShoppingItemUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItem"));
	private static Log _log = LogFactory.getLog(ShoppingItemUtil.class);
	private static ShoppingItemUtil _util;
	private ShoppingItemPersistence _persistence;
}