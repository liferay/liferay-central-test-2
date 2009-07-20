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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;

import java.rmi.RemoteException;

public class DLFileShortcutServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap addFileShortcut(
		long folderId, long toFolderId, java.lang.String toName,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLFileShortcutServiceUtil.addFileShortcut(folderId, toFolderId,
					toName, addCommunityPermissions, addGuestPermissions);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap addFileShortcut(
		long folderId, long toFolderId, java.lang.String toName,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLFileShortcutServiceUtil.addFileShortcut(folderId, toFolderId,
					toName, communityPermissions, guestPermissions);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws RemoteException {
		try {
			DLFileShortcutServiceUtil.deleteFileShortcut(fileShortcutId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap getFileShortcut(
		long fileShortcutId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap updateFileShortcut(
		long fileShortcutId, long folderId, long toFolderId,
		java.lang.String toName) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLFileShortcutServiceUtil.updateFileShortcut(fileShortcutId,
					folderId, toFolderId, toName);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutServiceSoap.class);
}