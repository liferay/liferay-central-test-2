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

package com.liferay.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class SafeFileNameHookWrapper implements Hook {

	public SafeFileNameHookWrapper(Hook hook) {
		_hook = hook;
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_hook.move(dirName, safeDirName);
			}
			catch (Exception e) {
			}
		}

		_hook.addDirectory(companyId, repositoryId, safeDirName);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);

		_hook.addFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			fileEntryId, properties, modifiedDate, serviceContext, bytes);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);

		_hook.addFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			fileEntryId, properties, modifiedDate, serviceContext, file);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);

		_hook.addFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			fileEntryId, properties, modifiedDate, serviceContext, is);
	}

	public void checkRoot(long companyId) throws SystemException {
		_hook.checkRoot(companyId);
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_hook.deleteDirectory(
					companyId, portletId, repositoryId, dirName);

				return;
			}
			catch (Exception e) {
			}
		}

		_hook.deleteDirectory(companyId, portletId, repositoryId, safeDirName);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(
				companyId, repositoryId, fileName,
				DLFileEntryConstants.DEFAULT_VERSION)) {

			_hook.deleteFile(companyId, portletId, repositoryId, fileName);

			return;
		}

		_hook.deleteFile(companyId, portletId, repositoryId, safeFileName);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(
				companyId, repositoryId, fileName, versionNumber)) {

			_hook.deleteFile(
				companyId, portletId, repositoryId, fileName, versionNumber);

			return;
		}

		_hook.deleteFile(
			companyId, portletId, repositoryId, safeFileName, versionNumber);
	}

	public byte[] getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(
				companyId, repositoryId, fileName,
				DLFileEntryConstants.DEFAULT_VERSION)) {

			return _hook.getFile(companyId, repositoryId, fileName);
		}

		return _hook.getFile(companyId, repositoryId, safeFileName);
	}

	public byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(companyId, repositoryId, fileName, versionNumber)) {

			return _hook.getFile(
				companyId, repositoryId, fileName, versionNumber);
		}

		return _hook.getFile(
			companyId, repositoryId, safeFileName, versionNumber);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(
				companyId, repositoryId, fileName,
				DLFileEntryConstants.DEFAULT_VERSION)) {

			return _hook.getFileAsStream(companyId, repositoryId, fileName);
		}

		return _hook.getFileAsStream(companyId, repositoryId, safeFileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(
				companyId, repositoryId, fileName, versionNumber)) {

			return _hook.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);
		}

		return _hook.getFileAsStream(
			companyId, repositoryId, safeFileName, versionNumber);
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_hook.move(dirName, safeDirName);
			}
			catch (Exception e) {
			}
		}

		String[] fileNames = _hook.getFileNames(
			companyId, repositoryId, safeDirName);

		String[] decodedFileNames = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			decodedFileNames[i] = FileUtil.decodeSafeFileName(fileNames[i]);
		}

		return decodedFileNames;
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(
				companyId, repositoryId, fileName,
				DLFileEntryConstants.DEFAULT_VERSION)) {

			return _hook.getFileSize(companyId, repositoryId, fileName);
		}

		return _hook.getFileSize(companyId, repositoryId, safeFileName);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_hook.hasFile(companyId, repositoryId, fileName, versionNumber)) {

			return true;
		}

		return _hook.hasFile(
			companyId, repositoryId, safeFileName, versionNumber);
	}

	public void move(String srcDir, String destDir) throws SystemException {
		_hook.move(srcDir, destDir);
	}

	public void reindex(String[] ids) throws SearchException {
		_hook.reindex(ids);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);

		_hook.updateFile(
			companyId, portletId, groupId, repositoryId, newRepositoryId,
			safeFileName, fileEntryId);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);
		String safeNewFileName = FileUtil.encodeSafeFileName(newFileName);

		if (!safeFileName.equals(fileName)) {
			if (_hook.hasFile(
					companyId, repositoryId, fileName,
					DLFileEntryConstants.DEFAULT_VERSION)) {

				safeFileName = fileName;
			}
		}

		_hook.updateFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			safeNewFileName, reindex);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);
		String safeSourceFileName = FileUtil.encodeSafeFileName(sourceFileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);
		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, sourceFileName,
			safeSourceFileName);

		_hook.updateFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			versionNumber, safeSourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, bytes);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);
		String safeSourceFileName = FileUtil.encodeSafeFileName(sourceFileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);
		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, sourceFileName,
			safeSourceFileName);

		_hook.updateFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			versionNumber, safeSourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, file);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);
		String safeSourceFileName = FileUtil.encodeSafeFileName(sourceFileName);

		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, fileName,
			safeFileName);
		renameUnsafeFile(
			companyId, portletId, groupId, repositoryId, sourceFileName,
			safeSourceFileName);

		_hook.updateFile(
			companyId, portletId, groupId, repositoryId, safeFileName,
			versionNumber, safeSourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, is);
	}

	protected void renameUnsafeFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String safeFileName)
		throws PortalException, SystemException {

		if (!safeFileName.equals(fileName)) {
			if (_hook.hasFile(
					companyId, repositoryId, fileName,
					DLFileEntryConstants.DEFAULT_VERSION)) {

				_hook.updateFile(
					companyId, portletId, groupId, repositoryId, fileName,
					safeFileName, true);
			}
		}
	}

	private Hook _hook;

}