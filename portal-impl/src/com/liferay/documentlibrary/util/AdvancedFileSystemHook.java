/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.documentlibrary.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

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
		if (fileNameFragment.length() <= 2) {
			return;
		}

		if (getDepth(sb.toString()) > 3) {
			return;
		}

		for (int i = 0;i < fileNameFragment.length();i += 2) {
			if ((i + 2) < fileNameFragment.length()) {
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
		long companyId, long repositoryId, String fileName, double version) {

		String ext = StringPool.PERIOD + FileUtil.getExtension(fileName);

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

	protected double getHeadVersionNumber(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return DEFAULT_VERSION;
		}

		String[] versionNumbers = FileUtil.listFiles(fileNameDir);

		double headVersionNumber = DEFAULT_VERSION;

		for (int i = 0; i < versionNumbers.length; i++) {
			String versionNumberFragment = versionNumbers[i];

			int pos = versionNumberFragment.lastIndexOf(StringPool.UNDERLINE);

			if (pos > -1) {
				versionNumberFragment = versionNumberFragment.substring(
					pos + 1);
			}

			double versionNumber = GetterUtil.getDouble(versionNumberFragment);

			if (versionNumber > headVersionNumber) {
				headVersionNumber = versionNumber;
			}
		}

		return headVersionNumber;
	}

}