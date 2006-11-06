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

package com.liferay.portlet.documentlibrary.service.spring;

/**
 * <a href="DLFileEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, byte[] byteArray,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();

		return dlFileEntryService.addFileEntry(folderId, name, title,
			description, extraSettings, byteArray, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void deleteFileEntry(java.lang.String folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();
		dlFileEntryService.deleteFileEntry(folderId, name);
	}

	public static void deleteFileEntry(java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();
		dlFileEntryService.deleteFileEntry(folderId, name, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();

		return dlFileEntryService.getFileEntry(folderId, name);
	}

	public static void lockFileEntry(java.lang.String folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();
		dlFileEntryService.lockFileEntry(folderId, name);
	}

	public static void unlockFileEntry(java.lang.String folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();
		dlFileEntryService.unlockFileEntry(folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		java.lang.String folderId, java.lang.String newFolderId,
		java.lang.String name, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, byte[] byteArray)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		DLFileEntryService dlFileEntryService = DLFileEntryServiceFactory.getService();

		return dlFileEntryService.updateFileEntry(folderId, newFolderId, name,
			sourceFileName, title, description, extraSettings, byteArray);
	}
}