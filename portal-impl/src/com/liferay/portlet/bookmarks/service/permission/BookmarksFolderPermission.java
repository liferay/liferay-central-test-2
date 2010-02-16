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

package com.liferay.portlet.bookmarks.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

/**
 * <a href="BookmarksFolderPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class BookmarksFolderPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, BookmarksFolder folder,
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

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return BookmarksPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			BookmarksFolder folder =
				BookmarksFolderLocalServiceUtil.getBookmarksFolder(folderId);

			return contains(permissionChecker, folder, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, BookmarksFolder folder,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		long folderId = folder.getFolderId();

		if (actionId.equals(ActionKeys.VIEW)) {
			while (folderId !=
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folder = BookmarksFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();

				if (!permissionChecker.hasOwnerPermission(
						folder.getCompanyId(), BookmarksFolder.class.getName(),
						folder.getFolderId(), folder.getUserId(), actionId) &&
					!permissionChecker.hasPermission(
						folder.getGroupId(), BookmarksFolder.class.getName(),
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
			while (folderId !=
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				if (permissionChecker.hasOwnerPermission(
						folder.getCompanyId(), BookmarksFolder.class.getName(),
						folder.getFolderId(), folder.getUserId(), actionId)) {

					return true;
				}

				if (permissionChecker.hasPermission(
						folder.getGroupId(), BookmarksFolder.class.getName(),
						folder.getFolderId(), actionId)) {

					return true;
				}

				if (actionId.equals(ActionKeys.VIEW)) {
					break;
				}

				folder = BookmarksFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();
			}

			return false;
		}
	}

}