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

package com.liferay.portlet.imagegallery.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;

/**
 * <a href="IGFolderPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class IGFolderPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, IGFolder folder,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, folder, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (folderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return IGPermission.contains(permissionChecker, groupId, actionId);
		}
		else {
			IGFolder folder = IGFolderLocalServiceUtil.getFolder(folderId);

			return contains(permissionChecker, folder, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, IGFolder folder,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		long folderId = folder.getFolderId();

		if (actionId.equals(ActionKeys.VIEW)) {
			while (folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				folder = IGFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();

				if (!permissionChecker.hasOwnerPermission(
						folder.getCompanyId(), IGFolder.class.getName(),
						folder.getFolderId(), folder.getUserId(), actionId) &&
					!permissionChecker.hasPermission(
						folder.getGroupId(), IGFolder.class.getName(),
						folder.getFolderId(), actionId)) {

					return false;
				}

				if (!PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
					break;
				}
			}

			return true;
		}
		else {
			while (folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				if (permissionChecker.hasOwnerPermission(
						folder.getCompanyId(), IGFolder.class.getName(),
						folder.getFolderId(), folder.getUserId(), actionId)) {

					return true;
				}

				if (permissionChecker.hasPermission(
						folder.getGroupId(), IGFolder.class.getName(),
						folder.getFolderId(), actionId)) {

					return true;
				}

				if (actionId.equals(ActionKeys.VIEW)) {
					break;
				}

				folder = IGFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();
			}

			return false;
		}
	}

}