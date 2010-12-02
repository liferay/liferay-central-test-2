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

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 */
public class FileSystemHook extends BaseHook {

	public FileSystemHook() {
		_rootDir = new File(_ROOT_DIR);

		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (dirNameDir.exists()) {
			throw new DuplicateDirectoryException(dirNameDir.getPath());
		}

		dirNameDir.mkdirs();
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, DEFAULT_VERSION);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(fileNameVersionFile.getPath());
			}

			FileUtil.write(fileNameVersionFile, is);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setAssetCategoryIds(serviceContext.getAssetCategoryIds());
			fileModel.setAssetTagNames(serviceContext.getAssetTagNames());
			fileModel.setCompanyId(companyId);
			fileModel.setFileEntryId(fileEntryId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setModifiedDate(modifiedDate);
			fileModel.setPortletId(portletId);
			fileModel.setProperties(properties);
			fileModel.setRepositoryId(repositoryId);

			indexer.reindex(fileModel);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void checkRoot(long companyId) {
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException(dirNameDir.getPath());
		}

		FileUtil.deltree(dirNameDir);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException {

		File fileNameDir = getFileNameDir(
			companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			throw new NoSuchFileException(fileNameDir.getPath());
		}

		FileUtil.deltree(fileNameDir);

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(companyId);
		fileModel.setFileName(fileName);
		fileModel.setPortletId(portletId);
		fileModel.setRepositoryId(repositoryId);

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

		indexer.delete(fileModel);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionNumber);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		fileNameVersionFile.delete();
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		try {
			if (Validator.isNull(versionNumber)) {
				versionNumber = getHeadVersionNumber(
					companyId, repositoryId, fileName);
			}

			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionNumber);

			if (!fileNameVersionFile.exists()) {
				throw new NoSuchFileException(fileNameVersionFile.getPath());
			}

			return new FileInputStream(fileNameVersionFile);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException(dirNameDir.getPath());
		}

		String[] fileNames = FileUtil.listDirs(dirNameDir);

		Arrays.sort(fileNames);

		// Convert /${fileName} to /${dirName}/${fileName}

		for (int i = 0; i < fileNames.length; i++) {
			fileNames[i] =
				StringPool.SLASH + dirName + StringPool.SLASH + fileNames[i];
		}

		return fileNames;
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		String versionNumber = getHeadVersionNumber(
			companyId, repositoryId, fileName);

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionNumber);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		return fileNameVersionFile.length();
	}

	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionNumber) {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionNumber);

		if (fileNameVersionFile.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void move(String srcDir, String destDir) {
	}

	public void reindex(String[] ids) throws SearchException {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		long groupId = GetterUtil.getLong(ids[2]);
		long repositoryId = GetterUtil.getLong(ids[3]);

		Collection<Document> documents = new ArrayList<Document>();

		File repistoryDir = getRepositoryDir(companyId, repositoryId);

		String[] fileNames = FileUtil.listDirs(repistoryDir);

		for (int i = 0; i < fileNames.length; i++) {
			String fileName = fileNames[i];

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			Document document = indexer.getDocument(fileModel);

			if (document == null) {
				continue;
			}

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);
		File newFileNameDir = getFileNameDir(
			companyId, newRepositoryId, fileName);

		FileUtil.copyDirectory(fileNameDir, newFileNameDir);

		FileUtil.deltree(fileNameDir);

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			FileModel.class);

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(companyId);
		fileModel.setFileName(fileName);
		fileModel.setPortletId(portletId);
		fileModel.setRepositoryId(repositoryId);

		indexer.delete(fileModel);

		fileModel.setGroupId(groupId);
		fileModel.setRepositoryId(newRepositoryId);

		indexer.reindex(fileModel);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);
		File newFileNameDir = getFileNameDir(
			companyId, repositoryId, newFileName);

		FileUtil.copyDirectory(fileNameDir, newFileNameDir);

		FileUtil.deltree(fileNameDir);

		if (reindex) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(fileName);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			indexer.delete(fileModel);

			fileModel.setFileName(newFileName);
			fileModel.setGroupId(groupId);

			indexer.reindex(fileModel);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionNumber);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(fileNameVersionFile.getPath());
			}

			FileUtil.write(fileNameVersionFile, is);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setAssetCategoryIds(serviceContext.getAssetCategoryIds());
			fileModel.setAssetTagNames(serviceContext.getAssetTagNames());
			fileModel.setCompanyId(companyId);
			fileModel.setFileEntryId(fileEntryId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setModifiedDate(modifiedDate);
			fileModel.setPortletId(portletId);
			fileModel.setProperties(properties);
			fileModel.setRepositoryId(repositoryId);

			indexer.reindex(fileModel);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected File getCompanyDir(long companyId) {
		File companyDir = new File(_rootDir + StringPool.SLASH + companyId);

		if (!companyDir.exists()) {
			companyDir.mkdirs();
		}

		return companyDir;
	}

	protected File getDirNameDir(
		long companyId, long repositoryId, String dirName) {

		return getFileNameDir(companyId, repositoryId, dirName);
	}

	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		File fileNameDir = new File(
			repositoryDir + StringPool.SLASH + fileName);

		return fileNameDir;
	}

	protected File getFileNameVersionFile(
		long companyId, long repositoryId, String fileName, String version) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		File fileNameVersionFile = new File(
			fileNameDir + StringPool.SLASH + version);

		return fileNameVersionFile;
	}

	protected String getHeadVersionNumber(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return DEFAULT_VERSION;
		}

		String[] versionNumbers = FileUtil.listFiles(fileNameDir);

		String headVersionNumber = DEFAULT_VERSION;

		for (String versionNumber : versionNumbers) {
			if (DLUtil.compareVersions(versionNumber, headVersionNumber) > 0) {
				headVersionNumber = versionNumber;
			}
		}

		return headVersionNumber;
	}

	protected File getRepositoryDir(long companyId, long repositoryId) {
		File companyDir = getCompanyDir(companyId);

		File repositoryDir = new File(
			companyDir + StringPool.SLASH + repositoryId);

		if (!repositoryDir.exists()) {
			repositoryDir.mkdirs();
		}

		return repositoryDir;
	}

	private static final String _ROOT_DIR = PropsUtil.get(
		PropsKeys.DL_HOOK_FILE_SYSTEM_ROOT_DIR);

	private File _rootDir;

}