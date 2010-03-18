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
 * <a href="ShoppingItemPriceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingItemPriceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPriceLocalService
 * @generated
 */
public class ShoppingItemPriceLocalServiceWrapper
	implements ShoppingItemPriceLocalService {
	public ShoppingItemPriceLocalServiceWrapper(
		ShoppingItemPriceLocalService shoppingItemPriceLocalService) {
		_shoppingItemPriceLocalService = shoppingItemPriceLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice addShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.addShoppingItemPrice(shoppingItemPrice);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice createShoppingItemPrice(
		long itemPriceId) {
		return _shoppingItemPriceLocalService.createShoppingItemPrice(itemPriceId);
	}

	public void deleteShoppingItemPrice(long itemPriceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemPriceLocalService.deleteShoppingItemPrice(itemPriceId);
	}

	public void deleteShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemPriceLocalService.deleteShoppingItemPrice(shoppingItemPrice);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice getShoppingItemPrice(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.getShoppingItemPrice(itemPriceId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.getShoppingItemPrices(start, end);
	}

	public int getShoppingItemPricesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.getShoppingItemPricesCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.updateShoppingItemPrice(shoppingItemPrice);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.updateShoppingItemPrice(shoppingItemPrice,
			merge);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getItemPrices(
		long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemPriceLocalService.getItemPrices(itemId);
	}

	public ShoppingItemPriceLocalService getWrappedShoppingItemPriceLocalService() {
		return _shoppingItemPriceLocalService;
	}

	private ShoppingItemPriceLocalService _shoppingItemPriceLocalService;
}