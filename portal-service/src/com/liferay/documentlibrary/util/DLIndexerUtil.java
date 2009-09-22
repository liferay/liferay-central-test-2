/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchException;

import java.util.Date;

/**
 * <a href="DLIndexerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLIndexerUtil {

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		getDLIndexer().addFile(
			companyId, portletId, groupId, repositoryId, fileName);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, long[] assetCategoryIds, String[] assetTagNames)
		throws SearchException {

		getDLIndexer().addFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, assetCategoryIds, assetTagNames);
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws SearchException {

		getDLIndexer().deleteFile(companyId, portletId, repositoryId, fileName);
	}

	public static DLIndexer getDLIndexer() {
		return _dlIndexer;
	}

	public static Document getFileDocument(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		return getDLIndexer().getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName);
	}

	public static Document getFileDocument(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, long[] assetCategoryIds, String[] assetTagNames)
		throws SearchException {

		return getDLIndexer().getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, assetCategoryIds, assetTagNames);
	}

	public static String getFileUID(
		String portletId, long repositoryId, String fileName) {

		return getDLIndexer().getFileUID(portletId, repositoryId, fileName);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, long[] assetCategoryIds, String[] assetTagNames)
		throws SearchException {

		getDLIndexer().updateFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, assetCategoryIds, assetTagNames);
	}

	public void setDLIndexer(DLIndexer dlIndexer) {
		_dlIndexer = dlIndexer;
	}

	private static DLIndexer _dlIndexer;

}