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
		throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.addShoppingCart(shoppingCart);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart createShoppingCart(
		long cartId) {
		return _shoppingCartLocalService.createShoppingCart(cartId);
	}

	public void deleteShoppingCart(long cartId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCartLocalService.deleteShoppingCart(cartId);
	}

	public void deleteShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		_shoppingCartLocalService.deleteShoppingCart(shoppingCart);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart getShoppingCart(
		long cartId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCartLocalService.getShoppingCart(cartId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end) throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.getShoppingCarts(start, end);
	}

	public int getShoppingCartsCount()
		throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.getShoppingCartsCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.updateShoppingCart(shoppingCart);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge) throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.updateShoppingCart(shoppingCart, merge);
	}

	public void deleteGroupCarts(long groupId)
		throws com.liferay.portal.SystemException {
		_shoppingCartLocalService.deleteGroupCarts(groupId);
	}

	public void deleteUserCarts(long userId)
		throws com.liferay.portal.SystemException {
		_shoppingCartLocalService.deleteUserCarts(userId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart getCart(
		long userId, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCartLocalService.getCart(userId, groupId);
	}

	public java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, Integer> getItems(
		long groupId, java.lang.String itemIds)
		throws com.liferay.portal.SystemException {
		return _shoppingCartLocalService.getItems(groupId, itemIds);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCartLocalService.updateCart(userId, groupId, itemIds,
			couponCodes, altShipping, insure);
	}

	public ShoppingCartLocalService getWrappedShoppingCartLocalService() {
		return _shoppingCartLocalService;
	}

	private ShoppingCartLocalService _shoppingCartLocalService;
}