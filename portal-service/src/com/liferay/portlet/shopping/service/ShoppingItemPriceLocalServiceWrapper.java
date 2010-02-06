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
		throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.addShoppingItemPrice(shoppingItemPrice);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice createShoppingItemPrice(
		long itemPriceId) {
		return _shoppingItemPriceLocalService.createShoppingItemPrice(itemPriceId);
	}

	public void deleteShoppingItemPrice(long itemPriceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingItemPriceLocalService.deleteShoppingItemPrice(itemPriceId);
	}

	public void deleteShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.SystemException {
		_shoppingItemPriceLocalService.deleteShoppingItemPrice(shoppingItemPrice);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice getShoppingItemPrice(
		long itemPriceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.getShoppingItemPrice(itemPriceId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		int start, int end) throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.getShoppingItemPrices(start, end);
	}

	public int getShoppingItemPricesCount()
		throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.getShoppingItemPricesCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.updateShoppingItemPrice(shoppingItemPrice);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge) throws com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.updateShoppingItemPrice(shoppingItemPrice,
			merge);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getItemPrices(
		long itemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingItemPriceLocalService.getItemPrices(itemId);
	}

	public ShoppingItemPriceLocalService getWrappedShoppingItemPriceLocalService() {
		return _shoppingItemPriceLocalService;
	}

	private ShoppingItemPriceLocalService _shoppingItemPriceLocalService;
}