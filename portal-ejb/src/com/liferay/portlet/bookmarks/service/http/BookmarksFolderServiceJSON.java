/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.http;

import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import org.json.JSONObject;

/**
 * <a href="BookmarksFolderServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksFolderServiceJSON {
	public static JSONObject addFolder(java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.addFolder(plid,
				parentFolderId, name, description, addCommunityPermissions,
				addGuestPermissions);

		return BookmarksFolderJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addFolder(java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.addFolder(plid,
				parentFolderId, name, description, communityPermissions,
				guestPermissions);

		return BookmarksFolderJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteFolder(java.lang.String folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		BookmarksFolderServiceUtil.deleteFolder(folderId);
	}

	public static JSONObject getFolder(java.lang.String folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.getFolder(folderId);

		return BookmarksFolderJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateFolder(java.lang.String folderId,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.updateFolder(folderId,
				parentFolderId, name, description, mergeWithParentFolder);

		return BookmarksFolderJSONSerializer.toJSONObject(returnValue);
	}
}