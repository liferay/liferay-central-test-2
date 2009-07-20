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

public class ShoppingItemFieldLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingItemField addShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws com.liferay.portal.SystemException {
		return getService().addShoppingItemField(shoppingItemField);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField createShoppingItemField(
		long itemFieldId) {
		return getService().createShoppingItemField(itemFieldId);
	}

	public static void deleteShoppingItemField(long itemFieldId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteShoppingItemField(itemFieldId);
	}

	public static void deleteShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws com.liferay.portal.SystemException {
		getService().deleteShoppingItemField(shoppingItemField);
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

	public static com.liferay.portlet.shopping.model.ShoppingItemField getShoppingItemField(
		long itemFieldId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getShoppingItemField(itemFieldId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> getShoppingItemFields(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getShoppingItemFields(start, end);
	}

	public static int getShoppingItemFieldsCount()
		throws com.liferay.portal.SystemException {
		return getService().getShoppingItemFieldsCount();
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField updateShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws com.liferay.portal.SystemException {
		return getService().updateShoppingItemField(shoppingItemField);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField updateShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateShoppingItemField(shoppingItemField, merge);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> getItemFields(
		long itemId) throws com.liferay.portal.SystemException {
		return getService().getItemFields(itemId);
	}

	public static ShoppingItemFieldLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"ShoppingItemFieldLocalService is not set");
		}

		return _service;
	}

	public void setService(ShoppingItemFieldLocalService service) {
		_service = service;
	}

	private static ShoppingItemFieldLocalService _service;
}