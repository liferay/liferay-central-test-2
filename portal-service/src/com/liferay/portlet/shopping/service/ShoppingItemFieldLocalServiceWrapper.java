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
 * <a href="ShoppingItemFieldLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingItemFieldLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemFieldLocalService
 * @generated
 */
public class ShoppingItemFieldLocalServiceWrapper
	implements ShoppingItemFieldLocalService {
	public ShoppingItemFieldLocalServiceWrapper(
		ShoppingItemFieldLocalService shoppingItemFieldLocalService) {
		_shoppingItemFieldLocalService = shoppingItemFieldLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField addShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.addShoppingItemField(shoppingItemField);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField createShoppingItemField(
		long itemFieldId) {
		return _shoppingItemFieldLocalService.createShoppingItemField(itemFieldId);
	}

	public void deleteShoppingItemField(long itemFieldId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemFieldLocalService.deleteShoppingItemField(itemFieldId);
	}

	public void deleteShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemFieldLocalService.deleteShoppingItemField(shoppingItemField);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField getShoppingItemField(
		long itemFieldId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.getShoppingItemField(itemFieldId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> getShoppingItemFields(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.getShoppingItemFields(start, end);
	}

	public int getShoppingItemFieldsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.getShoppingItemFieldsCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField updateShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.updateShoppingItemField(shoppingItemField);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField updateShoppingItemField(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.updateShoppingItemField(shoppingItemField,
			merge);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> getItemFields(
		long itemId) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemFieldLocalService.getItemFields(itemId);
	}

	public ShoppingItemFieldLocalService getWrappedShoppingItemFieldLocalService() {
		return _shoppingItemFieldLocalService;
	}

	private ShoppingItemFieldLocalService _shoppingItemFieldLocalService;
}