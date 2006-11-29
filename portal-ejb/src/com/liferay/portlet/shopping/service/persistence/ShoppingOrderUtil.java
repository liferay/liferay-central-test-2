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
 * <a href="ShoppingOrderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderUtil {
	public static com.liferay.portlet.shopping.model.ShoppingOrder create(
		java.lang.String orderId) {
		return getPersistence().create(orderId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder remove(
		java.lang.String orderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchOrderException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(orderId));
		}

		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder = getPersistence()
																			 .remove(orderId);

		if (listener != null) {
			listener.onAfterRemove(shoppingOrder);
		}

		return shoppingOrder;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder remove(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(shoppingOrder);
		}

		shoppingOrder = getPersistence().remove(shoppingOrder);

		if (listener != null) {
			listener.onAfterRemove(shoppingOrder);
		}

		return shoppingOrder;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder update(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = shoppingOrder.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingOrder);
			}
			else {
				listener.onBeforeUpdate(shoppingOrder);
			}
		}

		shoppingOrder = getPersistence().update(shoppingOrder);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingOrder);
			}
			else {
				listener.onAfterUpdate(shoppingOrder);
			}
		}

		return shoppingOrder;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder update(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = shoppingOrder.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingOrder);
			}
			else {
				listener.onBeforeUpdate(shoppingOrder);
			}
		}

		shoppingOrder = getPersistence().update(shoppingOrder, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingOrder);
			}
			else {
				listener.onAfterUpdate(shoppingOrder);
			}
		}

		return shoppingOrder;
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder findByPrimaryKey(
		java.lang.String orderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchOrderException {
		return getPersistence().findByPrimaryKey(orderId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder fetchByPrimaryKey(
		java.lang.String orderId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(orderId);
	}

	public static java.util.List findByG_U_PPPS(java.lang.String groupId,
		java.lang.String userId, java.lang.String ppPaymentStatus)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

	public static java.util.List findByG_U_PPPS(java.lang.String groupId,
		java.lang.String userId, java.lang.String ppPaymentStatus, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U_PPPS(groupId, userId,
			ppPaymentStatus, begin, end);
	}

	public static java.util.List findByG_U_PPPS(java.lang.String groupId,
		java.lang.String userId, java.lang.String ppPaymentStatus, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U_PPPS(groupId, userId,
			ppPaymentStatus, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_First(
		java.lang.String groupId, java.lang.String userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchOrderException {
		return getPersistence().findByG_U_PPPS_First(groupId, userId,
			ppPaymentStatus, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_Last(
		java.lang.String groupId, java.lang.String userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchOrderException {
		return getPersistence().findByG_U_PPPS_Last(groupId, userId,
			ppPaymentStatus, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder[] findByG_U_PPPS_PrevAndNext(
		java.lang.String orderId, java.lang.String groupId,
		java.lang.String userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchOrderException {
		return getPersistence().findByG_U_PPPS_PrevAndNext(orderId, groupId,
			userId, ppPaymentStatus, obc);
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
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByG_U_PPPS(java.lang.String groupId,
		java.lang.String userId, java.lang.String ppPaymentStatus)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

	public static int countByG_U_PPPS(java.lang.String groupId,
		java.lang.String userId, java.lang.String ppPaymentStatus)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ShoppingOrderPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ShoppingOrderPersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingOrderUtil _getUtil() {
		if (_util == null) {
			_util = (ShoppingOrderUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = ShoppingOrderUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingOrder"));
	private static Log _log = LogFactory.getLog(ShoppingOrderUtil.class);
	private static ShoppingOrderUtil _util;
	private ShoppingOrderPersistence _persistence;
}