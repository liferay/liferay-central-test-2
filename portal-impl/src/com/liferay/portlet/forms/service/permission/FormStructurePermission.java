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
import com.liferay.portlet.forms.model.FormStructure;
import com.liferay.portlet.forms.service.FormStructureLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class FormStructurePermission {

	public static void check(
			PermissionChecker permissionChecker, FormStructure formStructure,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, formStructure, actionId)) {
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
			String formStructureId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, formStructureId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, FormStructure formStructure,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				formStructure.getCompanyId(), FormStructure.class.getName(),
				formStructure.getId(), formStructure.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			formStructure.getGroupId(), FormStructure.class.getName(),
			formStructure.getId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long id, String actionId)
		throws PortalException, SystemException {

		FormStructure formStructure =
			FormStructureLocalServiceUtil.getFormStructure(id);

		return contains(permissionChecker, formStructure, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			String formStructureId, String actionId)
		throws PortalException, SystemException {

		FormStructure formStructure =
			FormStructureLocalServiceUtil.getFormStructure(
				groupId, formStructureId);

		return contains(permissionChecker, formStructure, actionId);
	}

}