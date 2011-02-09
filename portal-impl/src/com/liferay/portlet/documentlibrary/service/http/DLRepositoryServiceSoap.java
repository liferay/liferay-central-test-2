/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.documentlibrary.service.DLRepositoryServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLRepositoryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLRepositoryServiceHttp
 * @see       com.liferay.portlet.documentlibrary.service.DLRepositoryServiceUtil
 * @generated
 */
public class DLRepositoryServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap addFolder(
		long groupId, long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLRepositoryServiceUtil.addFolder(groupId,
					repositoryId, parentFolderId, name, description,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap copyFolder(
		long groupId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLRepositoryServiceUtil.copyFolder(groupId,
					sourceFolderId, parentFolderId, name, description,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLRepositoryServiceUtil.deleteFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long groupId, long folderId,
		java.lang.String title) throws RemoteException {
		try {
			DLRepositoryServiceUtil.deleteFileEntry(groupId, folderId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			DLRepositoryServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name) throws RemoteException {
		try {
			DLRepositoryServiceUtil.deleteFolder(groupId, parentFolderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLRepositoryServiceUtil.getFileEntries(groupId, folderId,
					start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = DLRepositoryServiceUtil.getFileEntriesAndFileShortcutsCount(groupId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(long groupId, long folderId)
		throws RemoteException {
		try {
			int returnValue = DLRepositoryServiceUtil.getFileEntriesCount(groupId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntry(
		long fileEntryId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLRepositoryServiceUtil.getFileEntry(fileEntryId);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLRepositoryServiceUtil.getFileEntry(groupId,
					folderId, title);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLRepositoryServiceUtil.getFileEntryByUuidAndGroupId(uuid,
					groupId);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersionSoap getFileVersion(
		long fileVersionId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileVersion returnValue = DLRepositoryServiceUtil.getFileVersion(fileVersionId);

			return com.liferay.portlet.documentlibrary.model.DLFileVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap getFolder(
		long folderId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLRepositoryServiceUtil.getFolder(folderId);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLRepositoryServiceUtil.getFolder(groupId,
					parentFolderId, name);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> returnValue =
				DLRepositoryServiceUtil.getFolders(groupId, parentFolderId,
					start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = DLRepositoryServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws RemoteException {
		try {
			int returnValue = DLRepositoryServiceUtil.getFoldersCount(groupId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersFileEntriesCount(long groupId,
		Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = DLRepositoryServiceUtil.getFoldersFileEntriesCount(groupId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLRepositoryServiceUtil.getGroupFileEntries(groupId, userId,
					rootFolderId, start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId) throws RemoteException {
		try {
			int returnValue = DLRepositoryServiceUtil.getGroupFileEntriesCount(groupId,
					userId, rootFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubfolderIds(long groupId, long folderId,
		boolean recurse) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLRepositoryServiceUtil.getSubfolderIds(groupId,
					folderId, recurse);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFileEntryLock(long fileEntryId)
		throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.hasFileEntryLock(fileEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFolderLock(long folderId)
		throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.hasFolderLock(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasInheritableLock(long folderId)
		throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.hasInheritableLock(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isFileEntryLocked(long fileEntryId)
		throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.isFileEntryLocked(fileEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isFolderLocked(long folderId)
		throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.isFolderLocked(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap moveFileEntry(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLRepositoryServiceUtil.moveFileEntry(fileEntryId,
					newFolderId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revertFileEntry(long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLRepositoryServiceUtil.revertFileEntry(fileEntryId, version,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLRepositoryServiceUtil.unlockFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFileEntry(long fileEntryId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLRepositoryServiceUtil.unlockFileEntry(fileEntryId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long groupId, long folderId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLRepositoryServiceUtil.unlockFolder(groupId, folderId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLRepositoryServiceUtil.unlockFolder(groupId, parentFolderId, name,
				lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLRepositoryServiceUtil.updateFolder(folderId,
					parentFolderId, name, description, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryLock(long fileEntryId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.verifyFileEntryLock(fileEntryId,
					lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLRepositoryServiceUtil.verifyInheritableLock(folderId,
					lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLRepositoryServiceSoap.class);
}