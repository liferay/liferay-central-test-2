/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
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