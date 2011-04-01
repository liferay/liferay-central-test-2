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

package com.liferay.portlet.dynamicdatamapping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.service.DDMListLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class DDMListPermission {

	public static void check(
			PermissionChecker permissionChecker, DDMList list, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, list, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long listId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, listId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String listKey,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, listKey, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDMList list, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				list.getCompanyId(), DDMList.class.getName(), list.getListId(),
				list.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			list.getGroupId(), DDMList.class.getName(), list.getListId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long listId, String actionId)
		throws PortalException, SystemException {

		DDMList list = DDMListLocalServiceUtil.getList(listId);

		return contains(permissionChecker, list, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String listKey,
			String actionId)
		throws PortalException, SystemException {

		DDMList list = DDMListLocalServiceUtil.getList(groupId, listKey);

		return contains(permissionChecker, list, actionId);
	}

}