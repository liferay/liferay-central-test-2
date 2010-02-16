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
 * <a href="ShoppingOrderItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingOrderItemLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderItemLocalService
 * @generated
 */
public class ShoppingOrderItemLocalServiceWrapper
	implements ShoppingOrderItemLocalService {
	public ShoppingOrderItemLocalServiceWrapper(
		ShoppingOrderItemLocalService shoppingOrderItemLocalService) {
		_shoppingOrderItemLocalService = shoppingOrderItemLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem addShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.addShoppingOrderItem(shoppingOrderItem);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem createShoppingOrderItem(
		long orderItemId) {
		return _shoppingOrderItemLocalService.createShoppingOrderItem(orderItemId);
	}

	public void deleteShoppingOrderItem(long orderItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderItemLocalService.deleteShoppingOrderItem(orderItemId);
	}

	public void deleteShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderItemLocalService.deleteShoppingOrderItem(shoppingOrderItem);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem getShoppingOrderItem(
		long orderItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.getShoppingOrderItem(orderItemId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> getShoppingOrderItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.getShoppingOrderItems(start, end);
	}

	public int getShoppingOrderItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.getShoppingOrderItemsCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem updateShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.updateShoppingOrderItem(shoppingOrderItem);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem updateShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.updateShoppingOrderItem(shoppingOrderItem,
			merge);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> getOrderItems(
		long orderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderItemLocalService.getOrderItems(orderId);
	}

	public ShoppingOrderItemLocalService getWrappedShoppingOrderItemLocalService() {
		return _shoppingOrderItemLocalService;
	}

	private ShoppingOrderItemLocalService _shoppingOrderItemLocalService;
}