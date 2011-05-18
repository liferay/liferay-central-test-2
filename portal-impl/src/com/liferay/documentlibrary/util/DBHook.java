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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.model.Content;
import com.liferay.documentlibrary.model.FileModel;
import com.liferay.documentlibrary.service.ContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.FileChannel;

import java.sql.SQLException;

import java.util.Date;

/**
 * @author Shuyang Zhou
 * @author Michael Chen
 */
public class DBHook implements Hook {

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			Hook.DEFAULT_VERSION, StringPool.BLANK, fileEntryId, properties,
			modifiedDate, serviceContext, bytes);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			Hook.DEFAULT_VERSION, StringPool.BLANK, fileEntryId, properties,
			modifiedDate, serviceContext, file);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			Hook.DEFAULT_VERSION, StringPool.BLANK, fileEntryId, properties,
			modifiedDate, serviceContext, is);
	}

	public void checkRoot(long companyId) throws SystemException {
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		ContentLocalServiceUtil.removeByC_R_P(companyId, repositoryId, dirName);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		if (ContentLocalServiceUtil.removeByC_P_R_P(
			companyId, portletId, repositoryId, fileName)) {

			deleteIndex(companyId, fileName, portletId, repositoryId);
		}
		else {
			throw new NoSuchFileException(fileName);
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException {

		if (ContentLocalServiceUtil.removeByC_R_P_V(
			companyId, repositoryId, fileName, versionNumber)) {

			deleteIndex(companyId, fileName, portletId, repositoryId);
		}
		else {
			throw new NoSuchFileException(fileName);
		}
	}

	public byte[] getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		InputStream inputStream = getFileAsStream(
			companyId, repositoryId, fileName);

		try {
			return FileUtil.getBytes(inputStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		InputStream inputStream = getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);

		try {
			return FileUtil.getBytes(inputStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Content fileContent = ContentLocalServiceUtil.getContent(
			companyId, repositoryId, fileName);

		try {
			return fileContent.getData().getBinaryStream();
		}
		catch (SQLException sqle) {
			throw new SystemException(sqle);
		}
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		Content fileContent = ContentLocalServiceUtil.getContent(
			companyId, repositoryId, fileName, versionNumber);

		try {
			return fileContent.getData().getBinaryStream();
		}
		catch (SQLException sqle) {
			throw new SystemException(sqle);
		}
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return ContentLocalServiceUtil.getContentNames(
			companyId, repositoryId, dirName);
	}

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return ContentLocalServiceUtil.getContentSize(
			companyId, repositoryId, fileName);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return ContentLocalServiceUtil.hasContent(
			companyId, repositoryId, fileName, versionNumber);
	}

	public void move(String srcDir, String destDir) throws SystemException {
	}

	public void reindex(String[] ids) throws SearchException {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		String fileName = ids[2];
		long repositoryId = GetterUtil.getLong(ids[3]);

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(companyId);
		fileModel.setFileName(fileName);
		fileModel.setPortletId(portletId);
		fileModel.setRepositoryId(repositoryId);

		_indexer.reindex(fileModel);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException, SystemException {

		ContentLocalServiceUtil.updateContent(
			companyId, portletId, groupId, repositoryId, newRepositoryId,
			fileName);

		reindex(
			new String[] {
				String.valueOf(companyId), portletId, fileName,
				String.valueOf(newRepositoryId)});
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException, SystemException {

		ContentLocalServiceUtil.updateContent(
			companyId, portletId, groupId, repositoryId, fileName, newFileName);

		if (reindex) {
			reindex(
				new String[] {
					String.valueOf(companyId), portletId, newFileName,
					String.valueOf(repositoryId)});
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		if (ContentLocalServiceUtil.hasContent(
			companyId, repositoryId, fileName, versionNumber)) {

			throw new DuplicateFileException(fileName);
		}

		ContentLocalServiceUtil.addContent(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, bytes);

		reindex(
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), companyId, fileEntryId, fileName,
			groupId, modifiedDate, portletId, properties, repositoryId);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		if (ContentLocalServiceUtil.hasContent(
			companyId, repositoryId, fileName, versionNumber)) {

			throw new DuplicateFileException(fileName);
		}

		InputStream inputStream = null;

		try {
			 inputStream = new FileInputStream(file);
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}

		ContentLocalServiceUtil.addContent(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, inputStream, file.length());

		reindex(
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), companyId, fileEntryId, fileName,
			groupId, modifiedDate, portletId, properties, repositoryId);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream inputStream)
		throws PortalException, SystemException {

		if (ContentLocalServiceUtil.hasContent(
			companyId, repositoryId, fileName, versionNumber)) {

			throw new DuplicateFileException(fileName);
		}

		long length = -1;

		if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;
			FileChannel fileChannel = fileInputStream.getChannel();

			try {
				length = fileChannel.size();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Failed to detect file size from FileChannel," +
							"fallback to memory buffer adding.", ioe);
				}
			}
		}
		else if (inputStream instanceof UnsyncByteArrayInputStream) {
			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				(UnsyncByteArrayInputStream)inputStream;

			length = unsyncByteArrayInputStream.available();
		}
		else if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;
			length = byteArrayInputStream.available();
		}

		if (length >= 0) {
			ContentLocalServiceUtil.addContent(
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, inputStream, length);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to detect given InputStream's data length :" +
						inputStream + ", have to buffer the whole data into " +
							"memory before flush to database, this could be " +
								"a memory hog when the inputing file is large");
			}

			byte[] bytes = null;

			try {
				bytes = FileUtil.getBytes(inputStream);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}

			ContentLocalServiceUtil.addContent(
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, bytes);
		}

		reindex(
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), companyId, fileEntryId, fileName,
			groupId, modifiedDate, portletId, properties, repositoryId);
	}

	protected void deleteIndex(
			long companyId, String fileName, String portletId,
			long repositoryId)
		throws SearchException {

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(companyId);
		fileModel.setFileName(fileName);
		fileModel.setPortletId(portletId);
		fileModel.setRepositoryId(repositoryId);

		_indexer.delete(fileModel);
	}

	protected void reindex(
			long[] assetCategoryIds, String[] assetTagNames, long companyId,
			long fileEntryId, String fileName, long groupId, Date modifiedDate,
			String portletId, String properties, long repositoryId)
		throws SearchException {

		FileModel fileModel = new FileModel();

		fileModel.setAssetCategoryIds(assetCategoryIds);
		fileModel.setAssetTagNames(assetTagNames);
		fileModel.setCompanyId(companyId);
		fileModel.setFileEntryId(fileEntryId);
		fileModel.setFileName(fileName);
		fileModel.setGroupId(groupId);
		fileModel.setModifiedDate(modifiedDate);
		fileModel.setPortletId(portletId);
		fileModel.setProperties(properties);
		fileModel.setRepositoryId(repositoryId);

		_indexer.reindex(fileModel);
	}

	private static Log _log = LogFactoryUtil.getLog(DBHook.class);

	private Indexer _indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

}