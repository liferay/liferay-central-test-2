/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

public class ShoppingCartLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		return getService().addShoppingCart(shoppingCart);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart createShoppingCart(
		long cartId) {
		return getService().createShoppingCart(cartId);
	}

	public static void deleteShoppingCart(long cartId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteShoppingCart(cartId);
	}

	public static void deleteShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		getService().deleteShoppingCart(shoppingCart);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getShoppingCart(
		long cartId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getShoppingCart(cartId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getShoppingCarts(start, end);
	}

	public static int getShoppingCartsCount()
		throws com.liferay.portal.SystemException {
		return getService().getShoppingCartsCount();
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		return getService().updateShoppingCart(shoppingCart);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateShoppingCart(shoppingCart, merge);
	}

	public static void deleteGroupCarts(long groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteGroupCarts(groupId);
	}

	public static void deleteUserCarts(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserCarts(userId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getCart(
		long userId, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCart(userId, groupId);
	}

	public static java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, Integer> getItems(
		long groupId, java.lang.String itemIds)
		throws com.liferay.portal.SystemException {
		return getService().getItems(groupId, itemIds);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateCart(userId, groupId, itemIds, couponCodes,
			altShipping, insure);
	}

	public static ShoppingCartLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("ShoppingCartLocalService is not set");
		}

		return _service;
	}

	public void setService(ShoppingCartLocalService service) {
		_service = service;
	}

	private static ShoppingCartLocalService _service;
}