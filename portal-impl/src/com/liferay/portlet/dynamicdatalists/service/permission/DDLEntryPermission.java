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

package com.liferay.portlet.dynamicdatalists.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatalists.model.DDLEntry;
import com.liferay.portlet.dynamicdatalists.service.DDLEntryLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class DDLEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, DDLEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String entryKey,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, entryKey, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDLEntry entry, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				entry.getCompanyId(), DDLEntry.class.getName(),
				entry.getEntryId(), entry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			entry.getGroupId(), DDLEntry.class.getName(), entry.getEntryId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		DDLEntry entry = DDLEntryLocalServiceUtil.getEntry(entryId);

		return contains(permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String entryKey,
			String actionId)
		throws PortalException, SystemException {

		DDLEntry entry = DDLEntryLocalServiceUtil.getEntry(groupId, entryKey);

		return contains(permissionChecker, entry, actionId);
	}

}