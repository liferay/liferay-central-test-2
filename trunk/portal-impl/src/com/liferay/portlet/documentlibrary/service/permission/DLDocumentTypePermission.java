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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class DLDocumentTypePermission {

	public static void check(
			PermissionChecker permissionChecker, DLDocumentType documentType,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, documentType, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long documentTypeId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, documentTypeId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DLDocumentType documentType,
		String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, documentType.getGroupId(),
			DLDocumentType.class.getName(), documentType.getDocumentTypeId(),
			PortletKeys.DOCUMENT_LIBRARY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				documentType.getCompanyId(), DLDocumentType.class.getName(),
				documentType.getDocumentTypeId(), documentType.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			documentType.getGroupId(), DLDocumentType.class.getName(),
			documentType.getDocumentTypeId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long documentTypeId,
			String actionId)
		throws PortalException, SystemException {

		DLDocumentType documentType =
			DLDocumentTypeLocalServiceUtil.getDocumentType(documentTypeId);

		return contains(permissionChecker, documentType, actionId);
	}

}