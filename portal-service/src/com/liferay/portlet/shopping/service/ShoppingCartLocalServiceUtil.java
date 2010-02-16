/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ShoppingCartLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ShoppingCartLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCartLocalService
 * @generated
 */
public class ShoppingCartLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShoppingCart(shoppingCart);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart createShoppingCart(
		long cartId) {
		return getService().createShoppingCart(cartId);
	}

	public static void deleteShoppingCart(long cartId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingCart(cartId);
	}

	public static void deleteShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingCart(shoppingCart);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getShoppingCart(
		long cartId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCart(cartId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCarts(start, end);
	}

	public static int getShoppingCartsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCartsCount();
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingCart(shoppingCart);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingCart(shoppingCart, merge);
	}

	public static void deleteGroupCarts(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteGroupCarts(groupId);
	}

	public static void deleteUserCarts(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserCarts(userId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getCart(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCart(userId, groupId);
	}

	public static java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, Integer> getItems(
		long groupId, java.lang.String itemIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getItems(groupId, itemIds);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCart(userId, groupId, itemIds, couponCodes,
			altShipping, insure);
	}

	public static ShoppingCartLocalService getService() {
		if (_service == null) {
			_service = (ShoppingCartLocalService)PortalBeanLocatorUtil.locate(ShoppingCartLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ShoppingCartLocalService service) {
		_service = service;
	}

	private static ShoppingCartLocalService _service;
}