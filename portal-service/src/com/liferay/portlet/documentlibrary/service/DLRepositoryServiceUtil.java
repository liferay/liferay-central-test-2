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
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d l repository remote service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLRepositoryServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLRepositoryService
 * @see com.liferay.portlet.documentlibrary.service.base.DLRepositoryServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLRepositoryServiceImpl
 * @generated
 */
public class DLRepositoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLRepositoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(groupId, folderId, title, description,
			changeLog, extraSettings, bytes, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(groupId, folderId, title, description,
			changeLog, extraSettings, file, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(groupId, folderId, title, description,
			changeLog, extraSettings, is, size, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFolder(groupId, parentFolderId, name, description,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder copyFolder(
		long groupId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return getService()
				   .copyFolder(groupId, sourceFolderId, parentFolderId, name,
			description, serviceContext);
	}

	public static void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntry(fileEntryId);
	}

	public static void deleteFileEntryByTitle(long groupId, long folderId,
		java.lang.String titleWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteFileEntryByTitle(groupId, folderId, titleWithExtension);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		getService().deleteFolder(groupId, parentFolderId, name);
	}

	public static java.io.InputStream getFileAsStream(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileAsStream(fileEntryId);
	}

	public static java.io.InputStream getFileAsStream(long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileAsStream(fileEntryId, version);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntries(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntries(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntries(groupId, folderId, start, end, obc);
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, java.util.List<java.lang.Long> folderIds, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderIds, status,
			start, end);
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderId, status,
			start, end);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderIds,
			status);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderId,
			status);
	}

	public static int getFileEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntriesCount(groupId, folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntry(fileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntryByTitle(groupId, folderId, titleWithExtension);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.model.Lock getFileEntryLock(
		long fileEntryId) {
		return getService().getFileEntryLock(fileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static long getFolderId(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolderId(groupId, parentFolderId, name);
	}

	public static long[] getFolderIds(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolderIds(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, java.util.List<java.lang.Long> folderIds, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderIds, status, start, end);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			status, start, end);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderIds, status);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersFileEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersFileEntriesCount(groupId, folderIds, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntries(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntries(groupId, userId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupFileEntries(groupId, userId, rootFolderId, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupFileEntries(groupId, userId, rootFolderId, start,
			end, obc);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntriesCount(groupId, userId);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupFileEntriesCount(groupId, userId, rootFolderId);
	}

	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId,
		boolean recurse)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public static boolean hasFileEntryLock(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasFileEntryLock(fileEntryId);
	}

	public static boolean hasInheritableLock(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasInheritableLock(folderId);
	}

	public static boolean isFileEntryLocked(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isFileEntryLocked(fileEntryId);
	}

	public static boolean isFolderLocked(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isFolderLocked(folderId);
	}

	public static com.liferay.portal.model.Lock lockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().lockFileEntry(fileEntryId);
	}

	public static com.liferay.portal.model.Lock lockFileEntry(
		long fileEntryId, java.lang.String owner, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().lockFileEntry(fileEntryId, owner, expirationTime);
	}

	public static com.liferay.portal.model.Lock lockFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return getService().lockFolder(folderId);
	}

	public static com.liferay.portal.model.Lock lockFolder(long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return getService()
				   .lockFolder(folderId, owner, inheritable, expirationTime);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry moveFileEntry(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveFileEntry(fileEntryId, newFolderId, serviceContext);
	}

	public static com.liferay.portal.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().refreshFileEntryLock(lockUuid, expirationTime);
	}

	public static com.liferay.portal.model.Lock refreshFolderLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().refreshFolderLock(lockUuid, expirationTime);
	}

	public static void revertFileEntry(long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().revertFileEntry(fileEntryId, version, serviceContext);
	}

	public static void unlockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFileEntry(fileEntryId);
	}

	public static void unlockFileEntry(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFileEntry(fileEntryId, lockUuid);
	}

	public static void unlockFolder(long groupId, long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFolder(groupId, folderId, lockUuid);
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(fileEntryId, sourceFileName, title,
			description, changeLog, majorVersion, extraSettings, bytes,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(fileEntryId, sourceFileName, title,
			description, changeLog, majorVersion, extraSettings, file,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(fileEntryId, sourceFileName, title,
			description, changeLog, majorVersion, extraSettings, is, size,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateFileVersionDescription(
		long fileVersionId, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileVersionDescription(fileVersionId, description);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.rmi.RemoteException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			serviceContext);
	}

	public static boolean verifyFileEntryLock(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().verifyFileEntryLock(fileEntryId, lockUuid);
	}

	public static boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().verifyInheritableLock(folderId, lockUuid);
	}

	public static DLRepositoryService getService() {
		if (_service == null) {
			_service = (DLRepositoryService)PortalBeanLocatorUtil.locate(DLRepositoryService.class.getName());

			ReferenceRegistry.registerReference(DLRepositoryServiceUtil.class,
				"_service");
			MethodCache.remove(DLRepositoryService.class);
		}

		return _service;
	}

	public void setService(DLRepositoryService service) {
		MethodCache.remove(DLRepositoryService.class);

		_service = service;

		ReferenceRegistry.registerReference(DLRepositoryServiceUtil.class,
			"_service");
		MethodCache.remove(DLRepositoryService.class);
	}

	private static DLRepositoryService _service;
}