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

import com.liferay.documentlibrary.service.impl.DLServiceImpl;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		Document doc = getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName);

		if (doc != null) {
			SearchEngineUtil.addDocument(companyId, doc);
		}
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, long[] assetCategoryIds, String[] assetTagNames)
		throws SearchException {

		Document doc = getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, assetCategoryIds, assetTagNames);

		if (doc != null) {
			SearchEngineUtil.addDocument(companyId, doc);
		}
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws SearchException {

		SearchEngineUtil.deleteDocument(
			companyId, getFileUID(portletId, repositoryId, fileName));
	}

	public static Document getFileDocument(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		try {
			DLFileEntry fileEntry = null;

			try {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					repositoryId, fileName);
			}
			catch (NoSuchFileEntryException nsfe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"File " + fileName + " in repository " +
							repositoryId + " exists in the JCR but does " +
								"not exist in the database");
				}

				return null;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(fileEntry.getTitle());
			sb.append(StringPool.SPACE);
			sb.append(fileEntry.getDescription());
			sb.append(StringPool.SPACE);

			Properties extraSettingsProps =
				fileEntry.getExtraSettingsProperties();

			Iterator<Map.Entry<Object, Object>> itr =
				extraSettingsProps.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<Object, Object> entry = itr.next();

				String value = GetterUtil.getString((String)entry.getValue());

				sb.append(value);
			}

			String properties = sb.toString();

			long[] assetCategoryIds =
				AssetCategoryLocalServiceUtil.getCategoryIds(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId());
			String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			return getFileDocument(
				companyId, portletId, groupId, repositoryId, fileName,
				fileEntry.getFileEntryId(), properties,
				fileEntry.getModifiedDate(), assetCategoryIds, assetTagNames);
		}
		catch (PortalException pe) {
			throw new SearchException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new SearchException(se.getMessage());
		}
	}

	public static Document getFileDocument(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, long[] assetCategoryIds, String[] assetTagNames)
		throws SearchException {

		if (fileEntryId <= 0) {
			_log.debug(
				"Not indexing document " + companyId + " " + portletId + " " +
					groupId + " " + repositoryId + " " + fileName + " " +
						fileEntryId);

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing document " + companyId + " " + portletId + " " +
					groupId + " " + repositoryId + " " + fileName + " " +
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
					"Document " + companyId + " " + portletId + " " + groupId +
						" " + repositoryId + " " + fileName + " " +
							fileEntryId + " does not have any content");
			}

			return null;
		}

		Document doc = new DocumentImpl();

		doc.addUID(portletId, repositoryId, fileName);

		doc.addModifiedDate(modifiedDate);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, portletId);
		doc.addKeyword(Field.GROUP_ID, groupId);

		try {
			doc.addFile(Field.CONTENT, is, fileExt);
		}
		catch (IOException ioe) {
			throw new SearchException(
				"Cannot extract text from file" + companyId + " " + portletId +
					" " + groupId + " " + repositoryId + " " + fileName);
		}

		doc.addText(Field.PROPERTIES, properties);
		doc.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		doc.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		doc.addKeyword("repositoryId", repositoryId);
		doc.addKeyword("path", fileName);
		doc.addKeyword(Field.ENTRY_CLASS_NAME, DLFileEntry.class.getName());
		doc.addKeyword(Field.ENTRY_CLASS_PK, fileEntryId);

		ExpandoBridge expandoBridge = new ExpandoBridgeImpl(
			DLFileEntry.class.getName(), fileEntryId);

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + companyId + " " + portletId + " " + groupId +
					" " + repositoryId + " " + fileName + " " + fileEntryId +
						" indexed successfully");
		}

		return doc;
	}

	public static String getFileUID(
			String portletId, long repositoryId, String fileName) {
		Document doc = new DocumentImpl();

		doc.addUID(portletId, repositoryId, fileName);

		return doc.get(Field.UID);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, long[] assetCategoryIds, String[] assetTagNames)
		throws SearchException {

		Document doc = getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, assetCategoryIds, assetTagNames);

		if (doc != null) {
			SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
		}
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public DocumentSummary getDocumentSummary(
		Document doc, String snippet, PortletURL portletURL) {

		return null;
	}

	public void reIndex(String className, long classPK) {
	}

	public void reIndex(String[] ids) throws SearchException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		Hook hook = HookFactory.getInstance();

		hook.reIndex(ids);
	}

	private static final String[] _CLASS_NAMES = new String[0];

	private static Log _log = LogFactoryUtil.getLog(Indexer.class);

}