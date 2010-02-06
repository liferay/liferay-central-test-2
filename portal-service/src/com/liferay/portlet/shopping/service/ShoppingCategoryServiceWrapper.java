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
 * <a href="ShoppingCategoryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingCategoryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategoryService
 * @generated
 */
public class ShoppingCategoryServiceWrapper implements ShoppingCategoryService {
	public ShoppingCategoryServiceWrapper(
		ShoppingCategoryService shoppingCategoryService) {
		_shoppingCategoryService = shoppingCategoryService;
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryService.addCategory(parentCategoryId, name,
			description, serviceContext);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryService.deleteCategory(categoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryService.getCategory(categoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryService.updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory,
			serviceContext);
	}

	public ShoppingCategoryService getWrappedShoppingCategoryService() {
		return _shoppingCategoryService;
	}

	private ShoppingCategoryService _shoppingCategoryService;
}