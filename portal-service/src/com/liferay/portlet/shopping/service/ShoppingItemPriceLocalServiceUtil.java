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
 * <a href="ShoppingItemPriceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ShoppingItemPriceLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPriceLocalService
 * @generated
 */
public class ShoppingItemPriceLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice addShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShoppingItemPrice(shoppingItemPrice);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice createShoppingItemPrice(
		long itemPriceId) {
		return getService().createShoppingItemPrice(itemPriceId);
	}

	public static void deleteShoppingItemPrice(long itemPriceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingItemPrice(itemPriceId);
	}

	public static void deleteShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingItemPrice(shoppingItemPrice);
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

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice getShoppingItemPrice(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingItemPrice(itemPriceId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingItemPrices(start, end);
	}

	public static int getShoppingItemPricesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingItemPricesCount();
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingItemPrice(shoppingItemPrice);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingItemPrice(shoppingItemPrice, merge);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getItemPrices(
		long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getItemPrices(itemId);
	}

	public static ShoppingItemPriceLocalService getService() {
		if (_service == null) {
			_service = (ShoppingItemPriceLocalService)PortalBeanLocatorUtil.locate(ShoppingItemPriceLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ShoppingItemPriceLocalService service) {
		_service = service;
	}

	private static ShoppingItemPriceLocalService _service;
}