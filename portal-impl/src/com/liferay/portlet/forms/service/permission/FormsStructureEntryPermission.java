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

package com.liferay.portlet.forms.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.forms.model.FormsStructureEntry;
import com.liferay.portlet.forms.service.FormsStructureEntryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class FormsStructureEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, FormsStructureEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
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
			String entryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, entryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, FormsStructureEntry entry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				entry.getCompanyId(), FormsStructureEntry.class.getName(),
				entry.getId(), entry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			entry.getGroupId(), FormsStructureEntry.class.getName(),
			entry.getId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long id, String actionId)
		throws PortalException, SystemException {

		FormsStructureEntry entry =
			FormsStructureEntryLocalServiceUtil.getEntry(id);

		return contains(permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			String entryId, String actionId)
		throws PortalException, SystemException {

		FormsStructureEntry entry =
			FormsStructureEntryLocalServiceUtil.getEntry(
				groupId, entryId);

		return contains(permissionChecker, entry, actionId);
	}

}