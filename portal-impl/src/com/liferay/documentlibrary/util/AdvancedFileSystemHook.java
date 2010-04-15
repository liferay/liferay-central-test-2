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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;

/**
 * <a href="AdvancedFileSystemHook.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-1976.
 * </p>
 *
 * @author Jorge Ferrer
 */
public class AdvancedFileSystemHook extends FileSystemHook {

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

	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		String ext = StringPool.PERIOD + FileUtil.getExtension(fileName);

		if (ext.equals(StringPool.PERIOD)) {
			ext = StringPool.BLANK;
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
			ext = StringPool.BLANK;
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

			int pos = versionNumberFragment.lastIndexOf(StringPool.UNDERLINE);

			if (pos > -1) {
				versionNumberFragment = versionNumberFragment.substring(
					pos + 1);
			}

			String versionNumber = versionNumberFragment;

			if (DLUtil.compareVersions(versionNumber, headVersionNumber) > 0) {
				headVersionNumber = versionNumber;
			}
		}

		return headVersionNumber;
	}

}