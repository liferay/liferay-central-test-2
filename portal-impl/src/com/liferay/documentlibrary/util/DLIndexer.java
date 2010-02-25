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

import com.liferay.documentlibrary.model.FileModel;
import com.liferay.documentlibrary.service.impl.DLServiceImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.BaseIndexer;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="DLIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class DLIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {FileModel.class.getName()};

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		return null;
	}

	protected void doDelete(Object obj) throws Exception {
		FileModel fileModel = (FileModel)obj;

		Document document = new DocumentImpl();

		document.addUID(
			fileModel.getPortletId(), fileModel.getRepositoryId(),
			fileModel.getFileName());

		SearchEngineUtil.deleteDocument(
			fileModel.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		FileModel fileModel = (FileModel)obj;

		long companyId = fileModel.getCompanyId();
		String portletId = fileModel.getPortletId();
		long groupId = getParentGroupId(fileModel.getGroupId());
		long scopeGroupId = fileModel.getGroupId();
		long repositoryId = fileModel.getRepositoryId();
		String fileName = fileModel.getFileName();
		long fileEntryId = fileModel.getFileEntryId();
		String properties = fileModel.getProperties();
		Date modifiedDate = fileModel.getModifiedDate();
		long[] assetCategoryIds = fileModel.getAssetCategoryIds();
		String[] assetTagNames = fileModel.getAssetTagNames();

		DLFileEntry fileEntry = null;

		if (fileEntryId <= 0) {
			try {
				long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				if (groupId != repositoryId) {
					folderId = repositoryId;
				}

				fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					groupId, folderId, fileName);
			}
			catch (NoSuchFileEntryException nsfe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Not indexing document " + companyId + " " + portletId +
							" " + scopeGroupId + " " + repositoryId + " " +
								fileName + " " + fileEntryId);
				}

				return null;
			}
		}

		if (properties == null) {
			properties = fileEntry.getLuceneProperties();
		}

		if (assetCategoryIds == null) {
			assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());
		}

		if (assetTagNames == null) {
			assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing document " + companyId + " " + portletId + " " +
					scopeGroupId + " " + repositoryId + " " + fileName + " " +
						fileEntryId);
		}

		String fileExt = StringPool.BLANK;

		int fileExtVersionPos = fileName.indexOf(DLServiceImpl.VERSION);

		if (fileExtVersionPos != -1) {
			int fileExtPos = fileName.lastIndexOf(
				StringPool.PERIOD, fileExtVersionPos);

			if (fileExtPos != -1) {
				fileExt = fileName.substring(fileExtPos, fileExtVersionPos);
			}
		}
		else {
			int fileExtPos = fileName.lastIndexOf(StringPool.PERIOD);

			if (fileExtPos != -1) {
				fileExt = fileName.substring(fileExtPos, fileName.length());
			}
		}

		InputStream is = null;

		try {
			Hook hook = HookFactory.getInstance();

			is = hook.getFileAsStream(companyId, repositoryId, fileName);
		}
		catch (Exception e) {
		}

		if (is == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Document " + companyId + " " + portletId + " " +
						scopeGroupId + " " + repositoryId + " " + fileName +
							" " + fileEntryId + " does not have any content");
			}

			return null;
		}

		Document document = new DocumentImpl();

		document.addUID(portletId, repositoryId, fileName);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, portletId);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);

		try {
			document.addFile(Field.CONTENT, is, fileExt);
		}
		catch (IOException ioe) {
			throw new SearchException(
				"Cannot extract text from file" + companyId + " " + portletId +
					" " + scopeGroupId + " " + repositoryId + " " + fileName);
		}

		document.addText(Field.PROPERTIES, properties);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword("repositoryId", repositoryId);
		document.addKeyword("path", fileName);
		document.addKeyword(
			Field.ENTRY_CLASS_NAME, DLFileEntry.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, fileEntryId);

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, DLFileEntry.class.getName(), fileEntryId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + companyId + " " + portletId + " " +
					scopeGroupId + " " + repositoryId + " " + fileName + " " +
						fileEntryId + " indexed successfully");
		}

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		FileModel fileModel = (FileModel)obj;

		Document document = getDocument(fileModel);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				fileModel.getCompanyId(), document.get(Field.UID), document);
		}
	}

	protected void doReindex(String className, long classPK) throws Exception {
	}

	protected void doReindex(String[] ids) throws Exception {
		Hook hook = HookFactory.getInstance();

		hook.reindex(ids);
	}

	protected String getPortletId(SearchContext searchContext) {
		return (String)searchContext.getAttribute("portletId");
	}

	private static Log _log = LogFactoryUtil.getLog(DLIndexer.class);

}