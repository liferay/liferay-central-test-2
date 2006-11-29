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

package com.liferay.portlet.shopping.service;

/**
 * <a href="ShoppingCartLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartLocalServiceUtil {
	public static void deleteGroupCarts(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();
		shoppingCartLocalService.deleteGroupCarts(groupId);
	}

	public static void deleteUserCarts(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();
		shoppingCartLocalService.deleteUserCarts(userId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getCart(
		java.lang.String cartId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.getCart(cartId);
	}

	public static java.util.Map getItems(java.lang.String groupId,
		java.lang.String itemIds) throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.getItems(groupId, itemIds);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		java.lang.String userId, java.lang.String groupId,
		java.lang.String cartId, java.lang.String itemIds,
		java.lang.String couponIds, int altShipping, boolean insure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.updateCart(userId, groupId, cartId,
			itemIds, couponIds, altShipping, insure);
	}
}