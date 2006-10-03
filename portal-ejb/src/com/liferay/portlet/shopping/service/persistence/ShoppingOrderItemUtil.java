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
 * <a href="ShoppingOrderItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderItemUtil {
	public static final String CLASS_NAME = ShoppingOrderItemUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingOrderItem"));

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem create(
		com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK shoppingOrderItemPK) {
		return getPersistence().create(shoppingOrderItemPK);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem remove(
		com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK shoppingOrderItemPK)
		throws com.liferay.portlet.shopping.NoSuchOrderItemException, 
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
			listener.onBeforeRemove(findByPrimaryKey(shoppingOrderItemPK));
		}

		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem = getPersistence()
																					 .remove(shoppingOrderItemPK);

		if (listener != null) {
			listener.onAfterRemove(shoppingOrderItem);
		}

		return shoppingOrderItem;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem remove(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
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

		if (listener != null) {
			listener.onBeforeRemove(shoppingOrderItem);
		}

		shoppingOrderItem = getPersistence().remove(shoppingOrderItem);

		if (listener != null) {
			listener.onAfterRemove(shoppingOrderItem);
		}

		return shoppingOrderItem;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem update(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
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

		boolean isNew = shoppingOrderItem.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingOrderItem);
			}
			else {
				listener.onBeforeUpdate(shoppingOrderItem);
			}
		}

		shoppingOrderItem = getPersistence().update(shoppingOrderItem);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingOrderItem);
			}
			else {
				listener.onAfterUpdate(shoppingOrderItem);
			}
		}

		return shoppingOrderItem;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem update(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = shoppingOrderItem.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingOrderItem);
			}
			else {
				listener.onBeforeUpdate(shoppingOrderItem);
			}
		}

		shoppingOrderItem = getPersistence().update(shoppingOrderItem,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingOrderItem);
			}
			else {
				listener.onAfterUpdate(shoppingOrderItem);
			}
		}

		return shoppingOrderItem;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem findByPrimaryKey(
		com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK shoppingOrderItemPK)
		throws com.liferay.portlet.shopping.NoSuchOrderItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(shoppingOrderItemPK);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem fetchByPrimaryKey(
		com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK shoppingOrderItemPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(shoppingOrderItemPK);
	}

	public static java.util.List findByOrderId(java.lang.String orderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrderId(orderId);
	}

	public static java.util.List findByOrderId(java.lang.String orderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByOrderId(orderId, begin, end);
	}

	public static java.util.List findByOrderId(java.lang.String orderId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrderId(orderId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem findByOrderId_First(
		java.lang.String orderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchOrderItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOrderId_First(orderId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem findByOrderId_Last(
		java.lang.String orderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchOrderItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOrderId_Last(orderId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem[] findByOrderId_PrevAndNext(
		com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK shoppingOrderItemPK,
		java.lang.String orderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchOrderItemException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOrderId_PrevAndNext(shoppingOrderItemPK,
			orderId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByOrderId(java.lang.String orderId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOrderId(orderId);
	}

	public static int countByOrderId(java.lang.String orderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOrderId(orderId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ShoppingOrderItemPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ShoppingOrderItemUtil util = (ShoppingOrderItemUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ShoppingOrderItemPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ShoppingOrderItemUtil.class);
	private ShoppingOrderItemPersistence _persistence;
}