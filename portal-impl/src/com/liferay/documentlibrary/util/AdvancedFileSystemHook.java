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

import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-1976.
 * </p>
 *
 * @author Jorge Ferrer
 * @author Ryan Park
 * @author Brian Wing Shun Chan
 */
public class AdvancedFileSystemHook extends FileSystemHook {

	public void reindex(String[] ids) throws SearchException {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		long groupId = GetterUtil.getLong(ids[2]);
		long repositoryId = GetterUtil.getLong(ids[3]);

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		String[] fileNames = FileUtil.listDirs(repositoryDir);

		for (String fileName : fileNames) {
			Collection<Document> documents = getDocuments(
				companyId, portletId, groupId, repositoryId,
				repositoryDir.getPath() + StringPool.SLASH + fileName);

			SearchEngineUtil.updateDocuments(companyId, documents);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException {

		super.updateFile(
			companyId, portletId, groupId, repositoryId, fileName, newFileName,
			reindex);

		File newFileNameDir = getFileNameDir(
			companyId, repositoryId, newFileName);

		String[] fileNameVersions = FileUtil.listFiles(newFileNameDir);

		for (String fileNameVersion : fileNameVersions) {
			String ext = FileUtil.getExtension(fileNameVersion);

			if (ext.equals(_HOOK_EXTENSION)) {
				continue;
			}

			File fileNameVersionFile = new File(
				newFileNameDir + StringPool.SLASH + fileNameVersion);
			File newFileNameVersionFile = new File(
				newFileNameDir + StringPool.SLASH +
					FileUtil.stripExtension(fileNameVersion) +
						StringPool.PERIOD + _HOOK_EXTENSION);

			fileNameVersionFile.renameTo(newFileNameVersionFile);
		}
	}

	protected void buildPath(StringBundler sb, String fileNameFragment) {
		int fileNameFragmentLength = fileNameFragment.length();

		if ((fileNameFragmentLength <= 2) || (getDepth(sb.toString()) > 3)) {
			return;
		}

		for (int i = 0;i < fileNameFragmentLength;i += 2) {
			if ((i + 2) < fileNameFragmentLength) {
				sb.append(fileNameFragment.substring(i, i + 2));
				sb.append(StringPool.SLASH);

				if (getDepth(sb.toString()) > 3) {
					return;
				}
			}
		}

		return;
	}

	protected int getDepth(String path) {
		String[] fragments = StringUtil.split(path, StringPool.SLASH);

		return fragments.length;
	}

	protected File getDirNameDir(
		long companyId, long repositoryId, String dirName) {

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		return new File(repositoryDir + StringPool.SLASH + dirName);
	}

	protected Collection<Document> getDocuments(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		Collection<Document> documents = new ArrayList<Document>();

		String shortFileName = FileUtil.getShortFileName(fileName);

		if (shortFileName.equals("DLFE") ||
			Validator.isNumber(shortFileName)) {

			String[] curFileNames = FileUtil.listDirs(fileName);

			for (String curFileName : curFileNames) {
				documents.addAll(
					getDocuments(
						companyId, portletId, groupId, repositoryId,
						fileName + StringPool.SLASH + curFileName));
			}
		}
		else {
			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			FileModel fileModel = new FileModel();

			if (shortFileName.endsWith(_HOOK_EXTENSION)) {
				shortFileName = FileUtil.stripExtension(shortFileName);
			}

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(shortFileName);
			fileModel.setGroupId(groupId);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			Document document = indexer.getDocument(fileModel);

			if (document != null) {
				documents.add(document);
			}
		}

		return documents;
	}

	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		String ext = StringPool.PERIOD + FileUtil.getExtension(fileName);

		if (ext.equals(StringPool.PERIOD)) {
			ext += _HOOK_EXTENSION;
		}

		StringBundler sb = new StringBundler();

		String fileNameFragment = FileUtil.stripExtension(fileName);

		if (fileNameFragment.startsWith("DLFE-")) {
			fileNameFragment = fileNameFragment.substring(5);

			sb.append("DLFE" + StringPool.SLASH);
		}

		buildPath(sb, fileNameFragment);

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		File fileNameDir = new File(
			repositoryDir + StringPool.SLASH + sb.toString() +
				StringPool.SLASH + fileNameFragment + ext);

		return fileNameDir;
	}

	protected File getFileNameVersionFile(
		long companyId, long repositoryId, String fileName, String version) {

		String ext = StringPool.PERIOD + FileUtil.getExtension(fileName);

		if (ext.equals(StringPool.PERIOD)) {
			ext += _HOOK_EXTENSION;
		}

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		if (pos == -1) {
			StringBundler sb = new StringBundler();

			String fileNameFragment = FileUtil.stripExtension(fileName);

			if (fileNameFragment.startsWith("DLFE-")) {
				fileNameFragment = fileNameFragment.substring(5);

				sb.append("DLFE" + StringPool.SLASH);
			}

			buildPath(sb, fileNameFragment);

			File repositoryDir = getRepositoryDir(companyId, repositoryId);

			return new File(
				repositoryDir + StringPool.SLASH + sb.toString() +
					StringPool.SLASH + fileNameFragment + ext +
						StringPool.SLASH + fileNameFragment +
							StringPool.UNDERLINE + version + ext);
		}
		else {
			File fileNameDir = getDirNameDir(companyId, repositoryId, fileName);

			String fileNameFragment = FileUtil.stripExtension(
				fileName.substring(pos + 1));

			return new File(
				fileNameDir + StringPool.SLASH + fileNameFragment +
					StringPool.UNDERLINE + version + ext);
		}
	}

	protected String getHeadVersionNumber(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return DEFAULT_VERSION;
		}

		String[] versionNumbers = FileUtil.listFiles(fileNameDir);

		String headVersionNumber = DEFAULT_VERSION;

		for (int i = 0; i < versionNumbers.length; i++) {
			String versionNumberFragment = versionNumbers[i];

			int x = versionNumberFragment.lastIndexOf(StringPool.UNDERLINE);
			int y = versionNumberFragment.lastIndexOf(StringPool.PERIOD);

			if (x > -1) {
				versionNumberFragment = versionNumberFragment.substring(
					x + 1, y);
			}

			String versionNumber = versionNumberFragment;

			if (DLUtil.compareVersions(versionNumber, headVersionNumber) > 0) {
				headVersionNumber = versionNumber;
			}
		}

		return headVersionNumber;
	}

	private static final String _HOOK_EXTENSION = "afsh";

}