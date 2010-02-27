/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		java.lang.String versionDescription, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.addFileEntry(groupId, folderId, name, title,
			description, versionDescription, extraSettings, bytes,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String versionDescription, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.addFileEntry(groupId, folderId, name, title,
			description, versionDescription, extraSettings, file, serviceContext);
	}

	public void deleteFileEntry(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryService.deleteFileEntry(groupId, folderId, name);
	}

	public void deleteFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryService.deleteFileEntry(groupId, folderId, name, version);
	}

	public void deleteFileEntryByTitle(long groupId, long folderId,
		java.lang.String titleWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryService.deleteFileEntryByTitle(groupId, folderId,
			titleWithExtension);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.getFileEntries(groupId, folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.getFileEntry(groupId, folderId, name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.getFileEntryByTitle(groupId, folderId,
			titleWithExtension);
	}

	public boolean hasFileEntryLock(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.hasFileEntryLock(groupId, folderId, name);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long groupId,
		long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.lockFileEntry(groupId, folderId, name);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long groupId,
		long folderId, java.lang.String name, java.lang.String owner,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.lockFileEntry(groupId, folderId, name,
			owner, expirationTime);
	}

	public com.liferay.portal.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.refreshFileEntryLock(lockUuid, expirationTime);
	}

	public void unlockFileEntry(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryService.unlockFileEntry(groupId, folderId, name);
	}

	public void unlockFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryService.unlockFileEntry(groupId, folderId, name, lockUuid);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String versionDescription,
		boolean majorVersion, java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.updateFileEntry(groupId, folderId,
			newFolderId, name, sourceFileName, title, description,
			versionDescription, majorVersion, extraSettings, bytes,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String versionDescription,
		boolean majorVersion, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.updateFileEntry(groupId, folderId,
			newFolderId, name, sourceFileName, title, description,
			versionDescription, majorVersion, extraSettings, file,
			serviceContext);
	}

	public boolean verifyFileEntryLock(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryService.verifyFileEntryLock(groupId, folderId, name,
			lockUuid);
	}

	public DLFileEntryService getWrappedDLFileEntryService() {
		return _dlFileEntryService;
	}

	private DLFileEntryService _dlFileEntryService;
}