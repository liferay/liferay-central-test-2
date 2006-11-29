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

package com.liferay.portlet.bookmarks.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="BookmarksFolderServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksFolderServiceSoap {
	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap addFolder(
		java.lang.String plid, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.addFolder(plid,
					parentFolderId, name, description, addCommunityPermissions,
					addGuestPermissions);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap addFolder(
		java.lang.String plid, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.addFolder(plid,
					parentFolderId, name, description, communityPermissions,
					guestPermissions);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteFolder(java.lang.String folderId)
		throws RemoteException {
		try {
			BookmarksFolderServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap getFolder(
		java.lang.String folderId) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.getFolder(folderId);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap updateFolder(
		java.lang.String folderId, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentFolder) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.updateFolder(folderId,
					parentFolderId, name, description, mergeWithParentFolder);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksFolderServiceSoap.class);
}