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

package com.liferay.portlet.documentlibrary.service;

public class DLFolderServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFolder(groupId, parentFolderId, name, description,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder copyFolder(
		long groupId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .copyFolder(groupId, sourceFolderId, parentFolderId, name,
			description, serviceContext);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		getService().deleteFolder(groupId, parentFolderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static long getFolderId(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolderId(groupId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static boolean hasInheritableLock(long folderId)
		throws com.liferay.portal.PortalException {
		return getService().hasInheritableLock(folderId);
	}

	public static com.liferay.lock.model.Lock lockFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService().lockFolder(folderId);
	}

	public static com.liferay.lock.model.Lock lockFolder(long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .lockFolder(folderId, owner, inheritable, expirationTime);
	}

	public static com.liferay.lock.model.Lock refreshFolderLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.PortalException {
		return getService().refreshFolderLock(lockUuid, expirationTime);
	}

	public static void reIndexSearch(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().reIndexSearch(companyId);
	}

	public static void unlockFolder(long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.PortalException {
		getService().unlockFolder(folderId, lockUuid);
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			serviceContext);
	}

	public static boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid) throws com.liferay.portal.PortalException {
		return getService().verifyInheritableLock(folderId, lockUuid);
	}

	public static DLFolderService getService() {
		if (_service == null) {
			throw new RuntimeException("DLFolderService is not set");
		}

		return _service;
	}

	public void setService(DLFolderService service) {
		_service = service;
	}

	private static DLFolderService _service;
}