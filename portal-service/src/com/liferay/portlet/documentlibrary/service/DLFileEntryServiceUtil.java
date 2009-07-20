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

public class DLFileEntryServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFileEntry(folderId, name, title, description,
			extraSettings, file, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFileEntry(folderId, name, title, description,
			extraSettings, bytes, serviceContext);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(folderId, name);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(folderId, name, version);
	}

	public static void deleteFileEntryByTitle(long folderId,
		java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFileEntryByTitle(folderId, titleWithExtension);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntries(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntry(folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileEntryByTitle(folderId, titleWithExtension);
	}

	public static boolean hasFileEntryLock(long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException {
		return getService().hasFileEntryLock(folderId, name);
	}

	public static com.liferay.lock.model.Lock lockFileEntry(long folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().lockFileEntry(folderId, name);
	}

	public static com.liferay.lock.model.Lock lockFileEntry(long folderId,
		java.lang.String name, java.lang.String owner, long expirationTime)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().lockFileEntry(folderId, name, owner, expirationTime);
	}

	public static com.liferay.lock.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.PortalException {
		return getService().refreshFileEntryLock(lockUuid, expirationTime);
	}

	public static void unlockFileEntry(long folderId, java.lang.String name) {
		getService().unlockFileEntry(folderId, name);
	}

	public static void unlockFileEntry(long folderId, java.lang.String name,
		java.lang.String lockUuid) throws com.liferay.portal.PortalException {
		getService().unlockFileEntry(folderId, name, lockUuid);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFileEntry(folderId, newFolderId, name,
			sourceFileName, title, description, extraSettings, bytes,
			serviceContext);
	}

	public static boolean verifyFileEntryLock(long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.PortalException {
		return getService().verifyFileEntryLock(folderId, name, lockUuid);
	}

	public static DLFileEntryService getService() {
		if (_service == null) {
			throw new RuntimeException("DLFileEntryService is not set");
		}

		return _service;
	}

	public void setService(DLFileEntryService service) {
		_service = service;
	}

	private static DLFileEntryService _service;
}