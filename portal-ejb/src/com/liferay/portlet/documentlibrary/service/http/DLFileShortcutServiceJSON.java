/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;

import org.json.JSONObject;

/**
 * <a href="DLFileShortcutServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileShortcutServiceJSON {
	public static JSONObject addFileShortcut(java.lang.String folderId,
		java.lang.String toFolderId, java.lang.String toName,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue = DLFileShortcutServiceUtil.addFileShortcut(folderId,
				toFolderId, toName, addCommunityPermissions, addGuestPermissions);

		return DLFileShortcutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addFileShortcut(java.lang.String folderId,
		java.lang.String toFolderId, java.lang.String toName,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue = DLFileShortcutServiceUtil.addFileShortcut(folderId,
				toFolderId, toName, communityPermissions, guestPermissions);

		return DLFileShortcutJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		DLFileShortcutServiceUtil.deleteFileShortcut(fileShortcutId);
	}

	public static JSONObject getFileShortcut(long fileShortcutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue = DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

		return DLFileShortcutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateFileShortcut(long fileShortcutId,
		java.lang.String folderId, java.lang.String toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue = DLFileShortcutServiceUtil.updateFileShortcut(fileShortcutId,
				folderId, toFolderId, toName);

		return DLFileShortcutJSONSerializer.toJSONObject(returnValue);
	}
}