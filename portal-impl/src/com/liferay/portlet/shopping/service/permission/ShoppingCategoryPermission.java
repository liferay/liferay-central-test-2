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

package com.liferay.portlet.shopping.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil;

public class ShoppingCategoryPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, ShoppingCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (categoryId == ShoppingCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {
			return ShoppingPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			return contains(permissionChecker, categoryId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		ShoppingCategory category =
			ShoppingCategoryLocalServiceUtil.getCategory(categoryId);

		return contains(permissionChecker, category, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, ShoppingCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		long categoryId = category.getCategoryId();

		if (actionId.equals(ActionKeys.VIEW)) {
			while (categoryId !=
					ShoppingCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {
				category = ShoppingCategoryLocalServiceUtil.getCategory(
					categoryId);

				categoryId = category.getParentCategoryId();

				if (!permissionChecker.hasOwnerPermission(
						category.getCompanyId(),
						ShoppingCategory.class.getName(),
						category.getCategoryId(), category.getUserId(),
						actionId) &&
					!permissionChecker.hasPermission(
						category.getGroupId(), ShoppingCategory.class.getName(),
						category.getCategoryId(), actionId)) {

					return false;
				}
			}

			return true;
		}
		else {
			while (categoryId !=
					ShoppingCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {
				if (permissionChecker.hasOwnerPermission(
					category.getCompanyId(), ShoppingCategory.class.getName(),
					category.getCategoryId(), category.getUserId(), actionId)) {

					return true;
				}

				if (permissionChecker.hasPermission(
					category.getGroupId(), ShoppingCategory.class.getName(),
					category.getCategoryId(), actionId)) {

					return true;
				}

				if (actionId.equals(ActionKeys.VIEW)) {
					break;
				}

				category = ShoppingCategoryLocalServiceUtil.getCategory(
					categoryId);

				categoryId = category.getParentCategoryId();
			}

			return false;
		}
	}

}