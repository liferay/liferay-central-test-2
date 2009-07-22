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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

public class DLFileEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, long folderId, String name,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, folderId, name, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, DLFileEntry fileEntry,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, fileEntry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long folderId, String name,
			String actionId)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			folderId, name);

		return contains(permissionChecker, fileEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileEntry fileEntry,
			String actionId)
		throws PortalException, SystemException {

		DLFolder folder = DLFolderLocalServiceUtil.getFolder(
			fileEntry.getFolderId());

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			if (!DLFolderPermission.contains(
					permissionChecker, folder, ActionKeys.VIEW) &&
				!DLFolderPermission.contains(
					permissionChecker, folder, ActionKeys.ACCESS)) {

				return false;
			}
		}

		if (permissionChecker.hasOwnerPermission(
				fileEntry.getCompanyId(), DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), fileEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			fileEntry.getGroupId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), actionId);
	}

}