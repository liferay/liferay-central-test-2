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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryService;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

/**
 * <a href="MBCategoryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBCategoryServiceImpl
	extends PrincipalBean implements MBCategoryService {

	public MBCategory addCategory(
			long plid, long parentCategoryId, String name, String description,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), plid, parentCategoryId,
			ActionKeys.ADD_CATEGORY);

		return MBCategoryLocalServiceUtil.addCategory(
			getUserId(), plid, parentCategoryId, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public MBCategory addCategory(
			long plid, long parentCategoryId, String name, String description,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), plid, parentCategoryId,
			ActionKeys.ADD_CATEGORY);

		return MBCategoryLocalServiceUtil.addCategory(
			getUserId(), plid, parentCategoryId, name, description,
			communityPermissions, guestPermissions);
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.DELETE);

		MBCategoryLocalServiceUtil.deleteCategory(categoryId);
	}

	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.VIEW);

		return MBCategoryLocalServiceUtil.getCategory(categoryId);
	}

	public void subscribeCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.SUBSCRIBE);

		MBCategoryLocalServiceUtil.subscribeCategory(getUserId(), categoryId);
	}

	public void unsubscribeCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.SUBSCRIBE);

		MBCategoryLocalServiceUtil.unsubscribeCategory(getUserId(), categoryId);
	}

	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.UPDATE);

		return MBCategoryLocalServiceUtil.updateCategory(
			categoryId, parentCategoryId, name, description,
			mergeWithParentCategory);
	}

}