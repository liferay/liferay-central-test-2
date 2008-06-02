/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.shopping.service.ShoppingCartLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.shopping.service.ShoppingCartLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingCartLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingCartLocalServiceFactory
 *
 */
public class ShoppingCartLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.addShoppingCart(shoppingCart);
	}

	public static void deleteShoppingCart(long cartId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		shoppingCartLocalService.deleteShoppingCart(cartId);
	}

	public static void deleteShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		shoppingCartLocalService.deleteShoppingCart(shoppingCart);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.dynamicQuery(queryInitializer, start,
			end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getShoppingCart(
		long cartId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.getShoppingCart(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.updateShoppingCart(shoppingCart);
	}

	public static void deleteGroupCarts(long groupId)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		shoppingCartLocalService.deleteGroupCarts(groupId);
	}

	public static void deleteUserCarts(long userId)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		shoppingCartLocalService.deleteUserCarts(userId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart getCart(
		long userId, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.getCart(userId, groupId);
	}

	public static java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, Integer> getItems(
		long groupId, java.lang.String itemIds)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.getItems(groupId, itemIds);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCartLocalService shoppingCartLocalService = ShoppingCartLocalServiceFactory.getService();

		return shoppingCartLocalService.updateCart(userId, groupId, itemIds,
			couponCodes, altShipping, insure);
	}
}