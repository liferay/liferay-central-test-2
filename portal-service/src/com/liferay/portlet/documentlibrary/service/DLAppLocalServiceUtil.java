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
 * The utility for the d l app local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLAppLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl
 * @generated
 */
public class DLAppLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(userId, repositoryId, folderId, title,
			description, changeLog, extraSettings, bytes, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(userId, repositoryId, folderId, title,
			description, changeLog, extraSettings, file, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntry(userId, repositoryId, folderId, title,
			description, changeLog, extraSettings, is, size, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank addFileRank(
		long repositoryId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileRank(repositoryId, companyId, userId, fileEntryId,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileShortcut(userId, repositoryId, folderId,
			toFileEntryId, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.Folder addFolder(
		long userId, long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFolder(userId, repositoryId, parentFolderId, name,
			description, serviceContext);
	}

	public static void deleteAll(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAll(repositoryId);
	}

	public static void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntry(fileEntryId);
	}

	public static void deleteFileRanksByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileRanksByFileEntryId(fileEntryId);
	}

	public static void deleteFileRanksByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileRanksByUserId(userId);
	}

	public static void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcut(dlFileShortcut);
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcut(fileShortcutId);
	}

	public static void deleteFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcuts(toFileEntryId);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFolder(folderId);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntries(repositoryId, folderId);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntries(repositoryId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntries(repositoryId, folderId, start, end, obc);
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(repositoryId, folderIds,
			status, start, end);
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(repositoryId, folderId,
			status, start, end);
	}

	public static int getFileEntriesAndFileShortcutsCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(repositoryId,
			folderIds, status);
	}

	public static int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(repositoryId, folderId,
			status);
	}

	public static int getFileEntriesCount(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntriesCount(repositoryId, folderId);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntry(fileEntryId);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long repositoryId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntry(repositoryId, folderId, title);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry getFileEntryByUuidAndRepositoryId(
		java.lang.String uuid, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryByUuidAndRepositoryId(uuid, repositoryId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long repositoryId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileRanks(repositoryId, userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long repositoryId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileRanks(repositoryId, userId, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcut(fileShortcutId);
	}

	public static com.liferay.portal.kernel.repository.model.FileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileVersion(fileVersionId);
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
		long repositoryId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(repositoryId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(repositoryId, parentFolderId);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(repositoryId, parentFolderId, start, end);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(repositoryId,
			folderIds, status, start, end);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(repositoryId,
			folderId, status, start, end);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
			folderIds, status);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
			folderId, status);
	}

	public static int getFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoldersCount(repositoryId, parentFolderId);
	}

	public static int getFoldersFileEntriesCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersFileEntriesCount(repositoryId, folderIds, status);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntries(repositoryId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntries(repositoryId, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntries(repositoryId, userId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long repositoryId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupFileEntries(repositoryId, userId, start, end, obc);
	}

	public static int getGroupFileEntriesCount(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntriesCount(repositoryId);
	}

	public static int getGroupFileEntriesCount(long repositoryId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFileEntriesCount(repositoryId, userId);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntry(
		long userId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveFileEntry(userId, fileEntryId, newFolderId,
			serviceContext);
	}

	public static void updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, fileEntry, fileVersion, assetCategoryIds,
			assetTagNames);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(userId, fileEntryId, sourceFileName, title,
			description, changeLog, majorVersion, extraSettings, bytes,
			serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(userId, fileEntryId, sourceFileName, title,
			description, changeLog, majorVersion, extraSettings, file,
			serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(userId, fileEntryId, sourceFileName, title,
			description, changeLog, majorVersion, extraSettings, is, size,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long repositoryId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileRank(repositoryId, companyId, userId,
			fileEntryId, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileShortcut(userId, fileShortcutId, folderId,
			toFileEntryId, serviceContext);
	}

	public static void updateFileShortcuts(long torepositoryId,
		long oldToFileEntryId, long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileShortcuts(torepositoryId, oldToFileEntryId,
			newToFileEntryId);
	}

	public static com.liferay.portal.kernel.repository.model.Folder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			serviceContext);
	}

	public static DLAppLocalService getService() {
		if (_service == null) {
			_service = (DLAppLocalService)PortalBeanLocatorUtil.locate(DLAppLocalService.class.getName());

			ReferenceRegistry.registerReference(DLAppLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DLAppLocalService.class);
		}

		return _service;
	}

	public void setService(DLAppLocalService service) {
		MethodCache.remove(DLAppLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DLAppLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DLAppLocalService.class);
	}

	private static DLAppLocalService _service;
}