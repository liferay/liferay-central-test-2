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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureEntryLocalServiceUtil;

/**
 * @author Bruno Basto
 */
public class DDMStructureEntryPermission {

	public static void check(
			PermissionChecker permissionChecker,
			DDMStructureEntry structureEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, structureEntry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long id, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, id, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			String structureKey, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, structureKey, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDMStructureEntry structureEntry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				structureEntry.getCompanyId(),
				DDMStructureEntry.class.getName(),
				structureEntry.getStructureEntryId(),
				structureEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			structureEntry.getGroupId(), DDMStructureEntry.class.getName(),
			structureEntry.getStructureEntryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long id, String actionId)
		throws PortalException, SystemException {

		DDMStructureEntry entry =
			DDMStructureEntryLocalServiceUtil.getDDMStructureEntry(id);

		return contains(permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			String entryId, String actionId)
		throws PortalException, SystemException {

		DDMStructureEntry entry =
			DDMStructureEntryLocalServiceUtil.getStructureEntry(
				groupId, entryId);

		return contains(permissionChecker, entry, actionId);
	}

}