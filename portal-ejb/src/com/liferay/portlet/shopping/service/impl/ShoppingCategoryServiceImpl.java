/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCategoryService;
import com.liferay.portlet.shopping.service.permission.ShoppingCategoryPermission;

/**
 * <a href="ShoppingCategoryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryServiceImpl
	extends PrincipalBean implements ShoppingCategoryService {

	public ShoppingCategory addCategory(
			String plid, String parentCategoryId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), plid, parentCategoryId,
			ActionKeys.ADD_CATEGORY);

		return ShoppingCategoryLocalServiceUtil.addCategory(
			getUserId(), plid, parentCategoryId, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public ShoppingCategory addCategory(
			String plid, String parentCategoryId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), plid, parentCategoryId,
			ActionKeys.ADD_CATEGORY);

		return ShoppingCategoryLocalServiceUtil.addCategory(
			getUserId(), plid, parentCategoryId, name, description,
			communityPermissions, guestPermissions);
	}

	public void deleteCategory(String categoryId)
		throws PortalException, SystemException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.DELETE);

		ShoppingCategoryLocalServiceUtil.deleteCategory(categoryId);
	}

	public ShoppingCategory getCategory(String categoryId)
		throws PortalException, SystemException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.VIEW);

		return ShoppingCategoryLocalServiceUtil.getCategory(categoryId);
	}

	public ShoppingCategory updateCategory(
			String categoryId, String parentCategoryId, String name,
			String description, boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.UPDATE);

		return ShoppingCategoryLocalServiceUtil.updateCategory(
			categoryId, parentCategoryId, name, description,
			mergeWithParentCategory);
	}

}