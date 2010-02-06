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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.base.MBCategoryServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBCategoryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBCategoryServiceImpl extends MBCategoryServiceBaseImpl {

	public MBCategory addCategory(
			long parentCategoryId, String name, String description,
			String emailAddress, String inProtocol, String inServerName,
			int inServerPort, boolean inUseSSL, String inUserName,
			String inPassword, int inReadInterval, String outEmailAddress,
			boolean outCustom, String outServerName, int outServerPort,
			boolean outUseSSL, String outUserName, String outPassword,
			boolean mailingListActive, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return mbCategoryLocalService.addCategory(
			getUserId(), parentCategoryId, name, description,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, serviceContext);
	}

	public void deleteCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.DELETE);

		mbCategoryLocalService.deleteCategory(categoryId);
	}

	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryLocalService.getCategory(categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.VIEW);

		return category;
	}

	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws PortalException, SystemException {

		List<MBCategory> categories = mbCategoryLocalService.getCategories(
			groupId, parentCategoryId, start, end);

		categories = ListUtil.copy(categories);

		Iterator<MBCategory> itr = categories.iterator();

		while (itr.hasNext()) {
			MBCategory category = itr.next();

			if (!MBCategoryPermission.contains(
					getPermissionChecker(), category, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return categories;
	}

	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryLocalService.getCategoriesCount(
			groupId, parentCategoryId);
	}

	public void subscribeCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.SUBSCRIBE);

		mbCategoryLocalService.subscribeCategory(
			getUserId(), groupId, categoryId);
	}

	public void unsubscribeCategory(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.SUBSCRIBE);

		mbCategoryLocalService.unsubscribeCategory(
			getUserId(), groupId, categoryId);
	}

	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive,
			boolean mergeWithParentCategory, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryLocalService.getCategory(categoryId);

		MBCategoryPermission.check(
			getPermissionChecker(), category, ActionKeys.UPDATE);

		return mbCategoryLocalService.updateCategory(
			categoryId, parentCategoryId, name, description, emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, mergeWithParentCategory, serviceContext);
	}

}