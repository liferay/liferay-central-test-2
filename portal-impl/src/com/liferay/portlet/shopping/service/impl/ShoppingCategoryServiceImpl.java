/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.service.base.ShoppingCategoryServiceBaseImpl;
import com.liferay.portlet.shopping.service.permission.ShoppingCategoryPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCategoryServiceImpl
	extends ShoppingCategoryServiceBaseImpl {

	public ShoppingCategory addCategory(
			long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return shoppingCategoryLocalService.addCategory(
			getUserId(), parentCategoryId, name, description, serviceContext);
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		ShoppingCategory category = shoppingCategoryLocalService.getCategory(
			categoryId);

		ShoppingCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.DELETE);

		shoppingCategoryLocalService.deleteCategory(categoryId);
	}

	public ShoppingCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		ShoppingCategory category = shoppingCategoryLocalService.getCategory(
			categoryId);

		ShoppingCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.VIEW);

		return category;
	}

	public ShoppingCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		ShoppingCategory category = shoppingCategoryLocalService.getCategory(
			categoryId);

		ShoppingCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.UPDATE);

		return shoppingCategoryLocalService.updateCategory(
			categoryId, parentCategoryId, name, description,
			mergeWithParentCategory, serviceContext);
	}

}