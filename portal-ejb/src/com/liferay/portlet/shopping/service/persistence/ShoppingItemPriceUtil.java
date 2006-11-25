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
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingItemPriceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPriceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice create(
		java.lang.String itemPriceId) {
		return getPersistence().create(itemPriceId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice remove(
		java.lang.String itemPriceId)
		throws com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(itemPriceId));
		}

		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice = getPersistence()
																					 .remove(itemPriceId);

		if (listener != null) {
			listener.onAfterRemove(shoppingItemPrice);
		}

		return shoppingItemPrice;
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice remove(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(shoppingItemPrice);
		}

		shoppingItemPrice = getPersistence().remove(shoppingItemPrice);

		if (listener != null) {
			listener.onAfterRemove(shoppingItemPrice);
		}

		return shoppingItemPrice;
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice update(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = shoppingItemPrice.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingItemPrice);
			}
			else {
				listener.onBeforeUpdate(shoppingItemPrice);
			}
		}

		shoppingItemPrice = getPersistence().update(shoppingItemPrice);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingItemPrice);
			}
			else {
				listener.onAfterUpdate(shoppingItemPrice);
			}
		}

		return shoppingItemPrice;
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice update(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = shoppingItemPrice.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingItemPrice);
			}
			else {
				listener.onBeforeUpdate(shoppingItemPrice);
			}
		}

		shoppingItemPrice = getPersistence().update(shoppingItemPrice,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingItemPrice);
			}
			else {
				listener.onAfterUpdate(shoppingItemPrice);
			}
		}

		return shoppingItemPrice;
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByPrimaryKey(
		java.lang.String itemPriceId)
		throws com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(itemPriceId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice fetchByPrimaryKey(
		java.lang.String itemPriceId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(itemPriceId);
	}

	public static java.util.List findByItemId(java.lang.String itemId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByItemId(itemId);
	}

	public static java.util.List findByItemId(java.lang.String itemId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByItemId(itemId, begin, end);
	}

	public static java.util.List findByItemId(java.lang.String itemId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByItemId(itemId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_First(
		java.lang.String itemId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByItemId_First(itemId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_Last(
		java.lang.String itemId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByItemId_Last(itemId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice[] findByItemId_PrevAndNext(
		java.lang.String itemPriceId, java.lang.String itemId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchItemPriceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByItemId_PrevAndNext(itemPriceId, itemId,
			obc);
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

	public static void removeByItemId(java.lang.String itemId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByItemId(itemId);
	}

	public static int countByItemId(java.lang.String itemId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByItemId(itemId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ShoppingItemPricePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ShoppingItemPricePersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingItemPriceUtil _getUtil() {
		if (_util == null) {
			_util = (ShoppingItemPriceUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = ShoppingItemPriceUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItemPrice"));
	private static Log _log = LogFactory.getLog(ShoppingItemPriceUtil.class);
	private static ShoppingItemPriceUtil _util;
	private ShoppingItemPricePersistence _persistence;
}