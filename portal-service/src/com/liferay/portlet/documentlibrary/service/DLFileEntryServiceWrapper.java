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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryService
 * @generated
 */
public class DLFileEntryServiceWrapper implements DLFileEntryService {
	public DLFileEntryServiceWrapper(DLFileEntryService dlFileEntryService) {
		_dlFileEntryService = dlFileEntryService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.addFileEntry(groupId, folderId, name, title,
			description, extraSettings, bytes, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.addFileEntry(groupId, folderId, name, title,
			description, extraSettings, file, serviceContext);
	}

	public void deleteFileEntry(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_dlFileEntryService.deleteFileEntry(groupId, folderId, name);
	}

	public void deleteFileEntry(long groupId, long folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_dlFileEntryService.deleteFileEntry(groupId, folderId, name, version);
	}

	public void deleteFileEntryByTitle(long groupId, long folderId,
		java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_dlFileEntryService.deleteFileEntryByTitle(groupId, folderId,
			titleWithExtension);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.getFileEntries(groupId, folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.getFileEntry(groupId, folderId, name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.getFileEntryByTitle(groupId, folderId,
			titleWithExtension);
	}

	public boolean hasFileEntryLock(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.hasFileEntryLock(groupId, folderId, name);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long groupId,
		long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.lockFileEntry(groupId, folderId, name);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long groupId,
		long folderId, java.lang.String name, java.lang.String owner,
		long expirationTime)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.lockFileEntry(groupId, folderId, name,
			owner, expirationTime);
	}

	public com.liferay.portal.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.refreshFileEntryLock(lockUuid, expirationTime);
	}

	public void unlockFileEntry(long groupId, long folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		_dlFileEntryService.unlockFileEntry(groupId, folderId, name);
	}

	public void unlockFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_dlFileEntryService.unlockFileEntry(groupId, folderId, name, lockUuid);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.updateFileEntry(groupId, folderId,
			newFolderId, name, sourceFileName, title, description,
			extraSettings, bytes, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.updateFileEntry(groupId, folderId,
			newFolderId, name, sourceFileName, title, description,
			extraSettings, file, serviceContext);
	}

	public boolean verifyFileEntryLock(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFileEntryService.verifyFileEntryLock(groupId, folderId, name,
			lockUuid);
	}

	public DLFileEntryService getWrappedDLFileEntryService() {
		return _dlFileEntryService;
	}

	private DLFileEntryService _dlFileEntryService;
}