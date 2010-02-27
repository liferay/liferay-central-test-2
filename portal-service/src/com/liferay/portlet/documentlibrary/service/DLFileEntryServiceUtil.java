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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="DLFileEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link DLFileEntryService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryService
 * @generated
 */
public class DLFileEntryServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String versionDescription, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(groupId, folderId, name, title, description,
			versionDescription, extraSettings, bytes, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String versionDescription, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(groupId, folderId, name, title, description,
			versionDescription, extraSettings, file, serviceContext);
	}

	public static void deleteFileEntry(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntry(groupId, folderId, name);
	}

	public static void deleteFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntry(groupId, folderId, name, version);
	}

	public static void deleteFileEntryByTitle(long groupId, long folderId,
		java.lang.String titleWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteFileEntryByTitle(groupId, folderId, titleWithExtension);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntries(groupId, folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntry(groupId, folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntryByTitle(groupId, folderId, titleWithExtension);
	}

	public static boolean hasFileEntryLock(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasFileEntryLock(groupId, folderId, name);
	}

	public static com.liferay.portal.model.Lock lockFileEntry(long groupId,
		long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().lockFileEntry(groupId, folderId, name);
	}

	public static com.liferay.portal.model.Lock lockFileEntry(long groupId,
		long folderId, java.lang.String name, java.lang.String owner,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .lockFileEntry(groupId, folderId, name, owner, expirationTime);
	}

	public static com.liferay.portal.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().refreshFileEntryLock(lockUuid, expirationTime);
	}

	public static void unlockFileEntry(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFileEntry(groupId, folderId, name);
	}

	public static void unlockFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFileEntry(groupId, folderId, name, lockUuid);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String versionDescription,
		boolean majorVersion, java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(groupId, folderId, newFolderId, name,
			sourceFileName, title, description, versionDescription,
			majorVersion, extraSettings, bytes, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String versionDescription,
		boolean majorVersion, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(groupId, folderId, newFolderId, name,
			sourceFileName, title, description, versionDescription,
			majorVersion, extraSettings, file, serviceContext);
	}

	public static boolean verifyFileEntryLock(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .verifyFileEntryLock(groupId, folderId, name, lockUuid);
	}

	public static DLFileEntryService getService() {
		if (_service == null) {
			_service = (DLFileEntryService)PortalBeanLocatorUtil.locate(DLFileEntryService.class.getName());
		}

		return _service;
	}

	public void setService(DLFileEntryService service) {
		_service = service;
	}

	private static DLFileEntryService _service;
}