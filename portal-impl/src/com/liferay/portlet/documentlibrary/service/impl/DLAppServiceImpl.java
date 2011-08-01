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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelModifiedDateComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * The document library remote service. All portlets should interact with the
 * document library through this class or through {@link DLAppLocalServiceImpl},
 * rather than through the individual document library service classes.
 *
 * <p>
 * This class provides a unified interface to all Liferay and third party
 * repositories. While the method signatures are universal for all repositories.
 * Additional implementation-specific parameters may be specified in the
 * serviceContext.
 * </p>
 *
 * <p>
 * The <code>repositoryId</code> parameter used by most of the methods is the
 * primary key of the specific repository. If the repository is a default
 * Liferay repository, the <code>repositoryId</code> is the <code>groupId</code>
 * or <code>scopeGroupId</code>. Otherwise, the <code>repositoryId</code> will
 * correspond to values obtained from {@link RepositoryServiceUtil}.
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @see    DLAppLocalServiceImpl
 */
public class DLAppServiceImpl extends DLAppServiceBaseImpl {

	/**
	 * Adds a file entry and associated metadata. It is created based on a byte
	 * array.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the file's original name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 * @return the file entry
	 * @throws PortalException if a parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		File file = null;

		try {
			if ((bytes != null) && (bytes.length > 0)) {
				file = FileUtil.createTempFile(bytes);
			}

			return addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	/**
	 * Adds a file entry and associated metadata. It is created based on a
	 * {@link File} object.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the file's original name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 * @return the file entry
	 * @throws PortalException if a parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file == null) || !file.exists()) {
				is = new UnsyncByteArrayInputStream(new byte[0]);
			}
			else {
				is = new FileInputStream(file);
				size = file.length();
			}

			return addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

	/**
	 * Adds a file entry and associated metadata. It is created based on a
	 * {@link InputStream} object.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the file's original name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  is the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 * @return the file entry
	 * @throws PortalException if a parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
			size = 0;
		}

		Repository repository = getRepository(repositoryId);

		FileEntry fileEntry = repository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			is, size, serviceContext);

		DLProcessorRegistryUtil.trigger(fileEntry);

		return fileEntry;
	}

	/**
	 * Adds a file shortcut to an existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file entry to point to
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes.
	 * @return the file shortcut
	 * @throws PortalException if a parent folder or file entry could not be
	 *         found or if the file shortcut's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut addFileShortcut(
			long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.addFileShortcut(
			repositoryId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Adds a folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @param  description the folder's description
	 * @param  serviceContext the folder's service context. In a Liferay
	 *         repository, it may include:
	 *         <ul>
	 *         <li>
	 *         mountPoint - boolean specifying whether folder is façade for
	 *         mounting a third-party repository
	 *         </li>
	 *         </ul>
	 * @return the folder
	 * @throws PortalException if a parent folder is not found or if the new
	 *         folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Folder addFolder(
			long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	/**
	 * Adds a temporary file entry.
	 *
	 * <p>
	 * This allows a client to upload a file into a temporary location and
	 * manipulate its metadata prior to making it available for public usage.
	 * This is different from checking in and checking out a file entry.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry will
	 *         eventually reside
	 * @param  fileName the file's original name
	 * @param  tempFolderName the temporary folder's name
	 * @param  file the file's data
	 * @return the file's name
	 * @throws IOException if a problem occurred in the access or storage of the
	 *         file
	 * @throws PortalException if the file name is invalid
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.kernel.util.TempFileUtil
	 */
	public String addTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName,
			File file)
		throws IOException, PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.addTempFile(
			getUserId(), fileName, tempFolderName, file);
	}

	/**
	 * Cancel the check out of a file entry. If a user has not checked out the
	 * specified file entry, invoking this method will result in no changes.
	 *
	 * <p>
	 * When a file entry is checked out, a PWC (private working copy) is created
	 * and the original file entry is locked. A client can make as many changes
	 * to the PWC as he desires without those changes being visible to other
	 * users. If the user is satisfied with the changes, he may elect to check
	 * in his changes, resulting in a new file version based on the PWC; the
	 * PWC will be removed and the file entry will be unlocked. If the user is
	 * not satisfied with the changes, he may elect to cancel his check out;
	 * this results in the deletion of the PWC and unlocking of the file entry.
	 * </p>
	 *
	 * @param  fileEntryId the file entry to cancel the checkout
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #checkInFileEntry(long, boolean, String, ServiceContext)
	 * @see    #checkOutFileEntry(long)
	 */
	public void cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.cancelCheckOut(fileEntryId);
	}

	/**
	 * Check in a file entry. If a user has not checked out the specified file
	 * entry, invoking this method will result in no changes.
	 *
	 * <p>
	 * When a file entry is checked out, a PWC (private working copy) is created
	 * and the original file entry is locked. A client can make as many changes
	 * to the PWC as he desires without those changes being visible to other
	 * users. If the user is satisfied with the changes, he may elect to check
	 * in his changes, resulting in a new file version based on the PWC; the
	 * PWC will be removed and the file entry will be unlocked. If the user is
	 * not satisfied with the changes, he may elect to cancel his check out;
	 * this results in the deletion of the PWC and unlocking of the file entry.
	 * </p>
	 *
	 * @param  fileEntryId the file entry to check in
	 * @param  majorVersion whether the new file version is a major version
	 * @param  changeLog the file's version change log
	 * @param  serviceContext the file entry's service context
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkOutFileEntry(long)
	 */
	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);
	}

	/**
	 * Check in a file entry. If a user has not checked out the specified file
	 * entry, invoking this method will result in no changes. This method is
	 * primarily used by WebDAV.
	 *
	 * <p>
	 * When a file entry is checked out, a PWC (private working copy) is created
	 * and the original file entry is locked. A client can make as many changes
	 * to the PWC as he desires without those changes being visible to other
	 * users. If the user is satisfied with the changes, he may elect to check
	 * in his changes, resulting in a new file version based on the PWC; the
	 * PWC will be removed and the file entry will be unlocked. If the user is
	 * not satisfied with the changes, he may elect to cancel his check out;
	 * this results in the deletion of the PWC and unlocking of the file entry.
	 * </p>
	 *
	 * @param  fileEntryId the file entry to check in
	 * @param  lockUuid the lock's universally unique identifier
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkOutFileEntry(long, String, long)
	 */
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkInFileEntry(fileEntryId, lockUuid);
	}

	/**
	 * Check out a file entry.
	 *
	 * <p>
	 * When a file entry is checked out, a PWC (private working copy) is created
	 * and the original file entry is locked. A client can make as many changes
	 * to the PWC as he desires without those changes being visible to other
	 * users. If the user is satisfied with the changes, he may elect to check
	 * in his changes, resulting in a new file version based on the PWC; the
	 * PWC will be removed and the file entry will be unlocked. If the user is
	 * not satisfied with the changes, he may elect to cancel his check out;
	 * this results in the deletion of the PWC and unlocking of the file entry.
	 * </p>
	 *
	 * @param  fileEntryId the file entry to check out
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkInFileEntry(long, boolean, String, ServiceContext)
	 */
	public void checkOutFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkOutFileEntry(fileEntryId);
	}

	/**
	 * Check out a file entry. This method is primarily used by WebDAV.
	 *
	 * <p>
	 * When a file entry is checked out, a PWC (private working copy) is created
	 * and the original file entry is locked. A client can make as many changes
	 * to the PWC as he desires without those changes being visible to other
	 * users. If the user is satisfied with the changes, he may elect to check
	 * in his changes, resulting in a new file version based on the PWC; the
	 * PWC will be removed and the file entry will be unlocked. If the user is
	 * not satisfied with the changes, he may elect to cancel his check out;
	 * this results in the deletion of the PWC and unlocking of the file entry.
	 * </p>
	 *
	 * @param  fileEntryId the file entry to check out
	 * @param  owner the owner string for the checkout (optionally
	 *         <code>null</code>).
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkInFileEntry(long, String)
	 */
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.checkOutFileEntry(fileEntryId, owner, expirationTime);
	}

	/**
	 * Performs a deep copy of a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  sourceFolderId the primary key of the folder to copy
	 * @param  parentFolderId the primary key of the new folder's parent folder
	 * @param  name the new folder's name
	 * @param  description the new folder's description
	 * @param  serviceContext the folder's service context
	 * @return the folder
	 * @throws PortalException if the source folder or the new folder's parent
	 *         folder are not found or if the new folder's information was
	 *         invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Folder copyFolder(
			long repositoryId, long sourceFolderId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		Folder srcFolder = repository.getFolder(sourceFolderId);

		Folder destFolder = repository.addFolder(
			parentFolderId, name,description, serviceContext);

		copyFolder(repository, srcFolder, destFolder, serviceContext);

		return destFolder;
	}

	/**
	 * Deletes a file entry.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if the file entry was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.deleteFileEntry(fileEntryId);
	}

	/**
	 * Deletes a file entry.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  title the title of the file entry
	 * @throws PortalException if the file entry was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFileEntryByTitle(
			long repositoryId, long folderId, String title)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFileEntry(folderId, title);
	}

	/**
	 * Deletes a file shortcut. This method is only supported by the Liferay
	 * repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @throws PortalException if the file shortcut was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	/**
	 * Deletes a folder and all of its subfolders and file entries.
	 *
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		repository.deleteFolder(folderId);
	}

	/**
	 * Deletes a folder and all of its subfolders and file entries.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  name the folder's name
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFolder(parentFolderId, name);
	}

	/**
	 * Deletes a temporary file entry.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry was
	 *         eventually to reside
	 * @param  fileName the file's original name
	 * @param  tempFolderName the temporary folder's name
	 * @throws PortalException if the file name is invalid
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.kernel.util.TempFileUtil
	 */
	public void deleteTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		TempFileUtil.deleteTempFile(getUserId(), fileName, tempFolderName);
	}

	/**
	 * Retrieves all file entries in a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the list of file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Retrieves a subset of file entries in a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end)
		throws PortalException, SystemException {

		return getFileEntries(repositoryId, folderId, start, end, null);
	}

	/**
	 * Retrieves a subset of file entries in a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the list of file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(folderId, start, end, obc);
	}

	/**
	 * Retrieves all file entries in a given folder of a given file entry type.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @return the list of file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	/**
	 * Retrieves a subset of file entries in a given folder of a given file
	 * entry type.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, fileEntryTypeId, start, end, null);
	}

	/**
	 * Retrieves a subset of file entries in a given folder of a given file
	 * entry type.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the list of file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

	/**
	 * Retrieves a subset of file entries and shortcuts in a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  status the workflow status
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of file entries and shortcuts
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	/**
	 * Retrieves a count of total file entries and shortcuts in a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  status the workflow status
	 * @return the count of total file entries and shortcuts
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	/**
	 * Retrieves a count of total file entries in a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the count of total file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFileEntriesCount(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId);
	}

	/**
	 * Retrieves a count of total file entries in a given folder of a given file
	 * entry type.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @return the count of total file entries
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFileEntriesCount(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId, fileEntryTypeId);
	}

	/**
	 * Retrieves a file entry.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry
	 * @throws PortalException if the file entry was not found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.getFileEntry(fileEntryId);
	}

	/**
	 * Retrieves a file entry.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder
	 * @param  title the title of the file entry
	 * @return the file entry
	 * @throws PortalException if the file entry was not found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Repository repository = getRepository(folderId, 0, 0);

				return repository.getFileEntry(folderId, title);
			}
			else {
				throw nsfee;
			}
		}
	}

	/**
	 * Retrieves a file entry.
	 *
	 * @param  uuid the file entry's universally unique identifier
	 * @param  groupId the primary key of the group
	 * @return the file entry
	 * @throws PortalException if the file entry was not found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException nsfee) {
			List<com.liferay.portal.model.Repository> repositories =
				repositoryPersistence.findByGroupId(groupId);

			for (int i = 0; i < repositories.size(); i++) {
				try {
					long repositoryId = repositories.get(i).getRepositoryId();

					Repository repository = getRepository(repositoryId);

					return repository.getFileEntryByUuid(uuid);
				}
				catch (NoSuchFileEntryException nsfee2) {
				}
			}
		}

		StringBundler msg = new StringBundler(6);

		msg.append("No DLFileEntry exists with the key {");
		msg.append("uuid=");
		msg.append(uuid);
		msg.append(", groupId=");
		msg.append(groupId);
		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Retrieve a file shortcut. This method is only supported by the Liferay
	 * repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut
	 * @throws PortalException if the file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	/**
	 * Retrieve a folder.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.getFolder(folderId);
	}

	/**
	 * Retrieve a folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder of the folder
	 * @param  name the name of the folder
	 * @return the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolder(parentFolderId, name);
	}

	/**
	 * Retrieve all immediate subfolders of a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Retrieve all immediate subfolders of a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, includeMountFolders,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Retrieves a subset of immediate subfolders of a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, includeMountFolders, start, end,
			null);
	}

	/**
	 * Retrieves a subset of immediate subfolders of a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end,	OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	/**
	 * Retrieves a subset of immediate subfolders of a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getFolders(repositoryId, parentFolderId, start, end, null);
	}

	/**
	 * Retrieves a subset of immediate subfolders of a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the folders by (optionally
	 *         <code>null</code>)
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(parentFolderId, true, start, end, obc);
	}

	/**
	 * Retrieves a subset of immediate subfolders, file entries, and file
	 * shortcuts of a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end)
		throws PortalException, SystemException {

		return getFoldersAndFileEntriesAndFileShortcuts(
			repositoryId, folderId, status, includeMountFolders, start, end,
			null);
	}

	/**
	 * Retrieves a subset of immediate subfolders, file entries, and file
	 * shortcuts of a given folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the list of immediate subfolders
	 * @throws PortalException if the folder could not be parent found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	/**
	 * Retrieves a count of total immediate subfolders, file entries, and
	 * file shortcuts in a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the count of immediate subfolders, file entries, and file
	 *         shortcuts
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	/**
	 * Retrieves a count of total immediate subfolders in a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @return the count of immediate subfolders
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFoldersCount(repositoryId, parentFolderId, true);
	}

	/**
	 * Retrieves a count of total immediate subfolders in a given folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the count of immediate subfolders
	 * @throws PortalException if the folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersCount(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersCount(parentFolderId, includeMountFolders);
	}

	/**
	 * Retrieves a count of total immediate subfolders and file entries across
	 * several folders.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderIds a list of primary keys for parent folders to search
	 * @param  status the workflow status
	 * @return the count of immediate subfolders and file entries
	 * @throws PortalException if the repository was not found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersFileEntriesCount(folderIds, status);
	}

	/**
	 * Retrieves a subset of file entries in a group that are stored within the
	 * Liferay repository. This method is primarily used to search for recently
	 * modified file entries. It can be limited to the file entries modified by
	 * a given user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the subset of file entries in the group
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, new RepositoryModelModifiedDateComparator());
	}

	/**
	 * Retrieves a subset of file entries in a group that are stored within the
	 * Liferay repository. This method is primarily used to search for recently
	 * modified file entries. It can be limited to the file entries modified by
	 * a given user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the subset of file entries in the group
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, obc);
	}

	/**
	 * Retrieves a subset of file entries in a group that are stored within the
	 * Liferay repository. This method is primarily used to search for recently
	 * modified file entries. It can be limited to the file entries modified by
	 * a given user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the subset of file entries in the group
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			groupId, userId, rootFolderId, start, end,
			new RepositoryModelModifiedDateComparator());
	}

	/**
	 * Retrieves a subset of file entries in a group that are stored within the
	 * Liferay repository. This method is primarily used to search for recently
	 * modified file entries. It can be limited to the file entries modified by
	 * a given user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the subset of file entries in the group
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	/**
	 * Retrieves a count of file entries in a group that are stored within the
	 * Liferay repository. This method is primarily used to search for recently
	 * modified file entries. It can be limited to the file entries modified by
	 * a given user.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @return the count of file entries in the group
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupFileEntriesCount(long groupId, long userId)
		throws PortalException, SystemException {

		return getGroupFileEntriesCount(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	/**
	 * Retrieves a count of file entries in a group that are stored within the
	 * Liferay repository. This method is primarily used to search for recently
	 * modified file entries. It can be limited to the file entries modified by
	 * a given user.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @return the count of file entries in the group
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntriesCount(userId, rootFolderId);
	}

	/**
	 * Retrieves all immediate subfolders used for mounting third-party
	 * repositories. This method is only supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @return the list of folders used for mounting third-party repositories
	 * @throws PortalException if the repository or parent folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getMountFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getMountFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Retrieves a subset of immediate subfolders used for mounting third-party
	 * repositories. This method is only supported by the Liferay repository.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the list of folders used for mounting third-party repositories
	 * @throws PortalException if the repository or parent folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getMountFolders(repositoryId, parentFolderId, start, end, null);
	}

	/**
	 * Retrieves a subset of immediate subfolders used for mounting third-party
	 * repositories. This method is only supported by the Liferay repository.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the list of folders used for mounting third-party repositories
	 * @throws PortalException if the repository or parent folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getMountFolders(parentFolderId, start, end, obc);
	}

	/**
	 * Retrieve all descendant folders of the current folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the list of primary keys for descendant folders
	 * @throws PortalException if the repository or parent folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Long> getSubfolderIds(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getSubfolderIds(repositoryId, folderId, true);
	}

	/**
	 * Retrieve descendant folders of the current folder. The method can be
	 * limited to one level deep.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  recurse whether to recurse through each subfolder
	 * @return the list of primary keys for descendant folders
	 * @throws PortalException if the repository or parent folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Long> getSubfolderIds(
			long repositoryId, long folderId, boolean recurse)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getSubfolderIds(folderId, recurse);
	}

	/**
	 * Retrieve all temporary file entry names.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry will
	 *         eventually reside
	 * @param  tempFolderName the temporary folder's name
	 * @return all the file names
	 * @throws PortalException if the folder is invalid
	 * @throws SystemException if a system exception occurred
	 * @see    #addTempFileEntry(long, long, String, String, File)
	 * @see    com.liferay.portal.kernel.util.TempFileUtil
	 */
	public String[] getTempFileEntryNames(
			long groupId, long folderId, String tempFolderName)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.getTempFileEntryNames(getUserId(), tempFolderName);
	}

	/**
	 * Locks a folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the lock object
	 * @throws PortalException if the repository or folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock lockFolder(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(folderId);
	}

	/**
	 * Locks a folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  owner the owner string for the checkout (optionally
	 *         <code>null</code>).
	 * @param  inheritable whether the lock must propagate to descendents
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the repository or folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock lockFolder(
			long repositoryId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	/**
	 * Moves a file entry to a new folder.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the file entry's service context
	 * @return the file entry
	 * @throws PortalException if the file entry or the new folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	/**
	 * Moves a folder to a new parent folder.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  parentFolderId the primary key of the new parent folder
	 * @param  serviceContext the folder's service context
	 * @return the folder
	 * @throws PortalException if the file entry or the new folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.moveFolder(folderId, parentFolderId, serviceContext);
	}

	/**
	 * Refreshes the lock for the file entry. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  lockUuid the lock's universally unique identifier
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the file entry or lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long fileEntryId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.refreshFileEntryLock(lockUuid, expirationTime);
	}

	/**
	 * Refreshes the lock for the folder. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  lockUuid the lock's universally unique identifier
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the folder or lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long folderId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, folderId, 0);

		return repository.refreshFolderLock(lockUuid, expirationTime);
	}

	/**
	 * Reverts a file entry to a previous version. A new version will be created
	 * based on the previous version and metadata.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  version the version to revert back to
	 * @param  serviceContext serviceContext the file entry's service context
	 * @throws PortalException if the file entry or version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.revertFileEntry(fileEntryId, version, serviceContext);
	}

	/**
	 * Unlocks a folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  lockUuid the lock's universally unique identifier
	 * @throws PortalException if the repository or folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void unlockFolder(long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(folderId, lockUuid);
	}

	/**
	 * Unlocks a folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  name the folder's name
	 * @param  lockUuid the lock's universally unique identifier
	 * @throws PortalException if the repository or folder was not found
	 * @throws SystemException if a system exception occurred
	 */
	public void unlockFolder(
			long repositoryId, long parentFolderId, String name,
			String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(parentFolderId, name, lockUuid);
	}

	/**
	 * Updates a file entry and associated metadata. It is updated based on a
	 * byte array object.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the file's original name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  majorVersion whether the new file version is a major version
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		File file = null;

		try {
			if ((bytes != null) && (bytes.length > 0)) {
				file = FileUtil.createTempFile(bytes);
			}

			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	/**
	 * Updates a file entry and associated metadata. It is updated based on a
	 * {@link File} object.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the file's original name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  majorVersion whether the new file version is a major version
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file != null) && file.exists()) {
				is = new FileInputStream(file);
				size = file.length();
			}

			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

	/**
	 * Updates a file entry and associated metadata. It is updated based on a
	 * {@link InputStream} object.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the file's original name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  majorVersion whether the new file version is a major version
	 * @param  is the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		DLProcessorRegistryUtil.trigger(fileEntry);

		return fileEntry;
	}

	/**
	 * Updates a file shortcut to an existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file entry to point to
	 * @param  serviceContext the file entry's service context. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes.
	 * @return the file shortcut
	 * @throws PortalException if the file shortcut, folder, or file entry could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.updateFileShortcut(
			fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Updates a folder.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  name the folder's new name
	 * @param  description the folder's new description
	 * @param  serviceContext the folder's service context. In a Liferay
	 *         repository, it may include:
	 *         <ul>
	 *         <li>
	 *         defaultFileEntryTypeId - the file entry type to default all
	 *         Liferay file entries to
	 *         </li>
	 *         <li>
	 *         fileEntryTypeSearchContainerPrimaryKeys - a comma-delimited list
	 *         of file entry type primary keys allowed in the given folder and
	 *         all descendants
	 *         </li>
	 *         <li>
	 *         overrideFileEntryTypes - boolean specifying whether to override
	 *         ancestral folder's restriction of file entry types allowed
	 *         </li>
	 *         </ul>
	 * @return the folder
	 * @throws PortalException if the current or parent folder is not found or
	 *         if the new folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.updateFolder(
			folderId, name, description, serviceContext);
	}

	/**
	 * Checks to see if a file is checked out. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  repositoryId the primary key for the repository
	 * @param  fileEntryId the primary key for the file entry
	 * @param  lockUuid the lock's universally unique identifier
	 * @return whether the file entry is checked out
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean verifyFileEntryCheckOut(
			long repositoryId, long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	/**
	 * Checks to see if an inheritable lock exists. This method is primarily
	 * used by WebDAV.
	 *
	 * @param  repositoryId the primary key for the repository
	 * @param  folderId the primary key for the folder
	 * @param  lockUuid the lock's universally unique identifier
	 * @return whether the file entry is checked out
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean verifyInheritableLock(
			long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyInheritableLock(
			folderId, lockUuid);
	}

	protected void copyFolder(
			Repository repository, Folder srcFolder, Folder destFolder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<FileEntry> srcFileEntries = repository.getFileEntries(
			srcFolder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		for (FileEntry srcFileEntry : srcFileEntries) {
			try {
				FileEntry fileEntry = repository.copyFileEntry(
					destFolder.getGroupId(), srcFileEntry.getFileEntryId(),
					destFolder.getFolderId(), serviceContext);

				DLProcessorRegistryUtil.trigger(fileEntry);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}
		}

		List<Folder> srcSubfolders = repository.getFolders(
			srcFolder.getFolderId(), false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (Folder srcSubfolder : srcSubfolders) {
			Folder destSubfolder = repository.addFolder(
				destFolder.getFolderId(), srcSubfolder.getName(),
				srcSubfolder.getDescription(), serviceContext);

			copyFolder(repository, srcSubfolder, destSubfolder, serviceContext);
		}
	}

	protected Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryService.getRepositoryImpl(repositoryId);
	}

	protected Repository getRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		return repositoryService.getRepositoryImpl(
			folderId, fileEntryId, fileVersionId);
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceImpl.class);

}