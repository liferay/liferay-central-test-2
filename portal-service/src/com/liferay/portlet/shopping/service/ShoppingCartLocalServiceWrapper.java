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


/**
 * <a href="ShoppingCartLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingCartLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCartLocalService
 * @generated
 */
public class ShoppingCartLocalServiceWrapper implements ShoppingCartLocalService {
	public ShoppingCartLocalServiceWrapper(
		ShoppingCartLocalService shoppingCartLocalService) {
		_shoppingCartLocalService = shoppingCartLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.addShoppingCart(shoppingCart);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart createShoppingCart(
		long cartId) {
		return _shoppingCartLocalService.createShoppingCart(cartId);
	}

	public void deleteShoppingCart(long cartId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingCartLocalService.deleteShoppingCart(cartId);
	}

	public void deleteShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingCartLocalService.deleteShoppingCart(shoppingCart);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart getShoppingCart(
		long cartId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.getShoppingCart(cartId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.getShoppingCarts(start, end);
	}

	public int getShoppingCartsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.getShoppingCartsCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.updateShoppingCart(shoppingCart);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.updateShoppingCart(shoppingCart, merge);
	}

	public void deleteGroupCarts(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingCartLocalService.deleteGroupCarts(groupId);
	}

	public void deleteUserCarts(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingCartLocalService.deleteUserCarts(userId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart getCart(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.getCart(userId, groupId);
	}

	public java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, Integer> getItems(
		long groupId, java.lang.String itemIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.getItems(groupId, itemIds);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCartLocalService.updateCart(userId, groupId, itemIds,
			couponCodes, altShipping, insure);
	}

	public ShoppingCartLocalService getWrappedShoppingCartLocalService() {
		return _shoppingCartLocalService;
	}

	private ShoppingCartLocalService _shoppingCartLocalService;
}