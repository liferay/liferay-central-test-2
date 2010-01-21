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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * <a href="DLPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class DLPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportFileEntry(
			PortletDataContext context, Element foldersEl,
			Element fileEntriesEl, Element fileRanksEl, DLFileEntry fileEntry)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(fileEntry.getModifiedDate())) {
			return;
		}

		if (foldersEl != null) {
			exportParentFolder(context, foldersEl, fileEntry.getFolderId());
		}

		String path = getFileEntryPath(context, fileEntry);

		if (context.isPathNotProcessed(path)) {
			Element fileEntryEl = fileEntriesEl.addElement("file-entry");

			fileEntryEl.addAttribute("path", path);

			String binPath = getFileEntryBinPath(context, fileEntry);

			fileEntryEl.addAttribute("bin-path", binPath);

			fileEntry.setUserUuid(fileEntry.getUserUuid());

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				context.addAssetCategories(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.addComments(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			long repositoryId = getRepositoryId(
				fileEntry.getGroupId(), fileEntry.getFolderId());

			InputStream is = DLLocalServiceUtil.getFileAsStream(
				fileEntry.getCompanyId(), repositoryId, fileEntry.getName(),
				fileEntry.getVersion());

			if (is == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No file found for file entry " +
							fileEntry.getFileEntryId());
				}

				fileEntryEl.detach();

				return;
			}

			try {
				context.addZipEntry(
					getFileEntryBinPath(context, fileEntry), is);
			}
			finally {
				try {
					is.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}

			context.addZipEntry(path, fileEntry);

			if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
				List<DLFileRank> fileRanks = DLFileRankUtil.findByF_N(
					fileEntry.getFolderId(), fileEntry.getName());

				for (DLFileRank fileRank : fileRanks) {
					exportFileRank(context, fileRanksEl, fileRank);
				}
			}
		}
	}

	public static void exportFolder(
			PortletDataContext context, Element foldersEl,
			Element fileEntriesEl, Element fileShortcutsEl, Element fileRanksEl,
			DLFolder folder)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(context, foldersEl, folder.getParentFolderId());

			String path = getFolderPath(context, folder);

			if (context.isPathNotProcessed(path)) {
				Element folderEl = foldersEl.addElement("folder");

				folderEl.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				context.addZipEntry(path, folder);
			}
		}

		List<DLFileEntry> fileEntries = DLFileEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (DLFileEntry fileEntry : fileEntries) {
			exportFileEntry(
				context, foldersEl, fileEntriesEl, fileRanksEl, fileEntry);
		}

		if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F(
				folder.getGroupId(), folder.getFolderId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					context, foldersEl, fileShortcutsEl, fileShortcut);
			}
		}
	}

	public static void importFileEntry(
			PortletDataContext context, Map<Long, Long> folderPKs,
			Map<String, String> fileEntryNames, DLFileEntry fileEntry,
			String binPath)
		throws Exception {

		long userId = context.getUserId(fileEntry.getUserUuid());
		long groupId = context.getGroupId();
		long folderId = MapUtil.getLong(
			folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setScopeGroupId(groupId);

		InputStream is = context.getZipEntryAsInputStream(binPath);

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == fileEntry.getFolderId())) {

			String path = getImportFolderPath(context, folderId);

			DLFolder folder = (DLFolder)context.getZipEntryAsObject(path);

			importFolder(context, folderPKs, folder);

			folderId = MapUtil.getLong(
				folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());
		}

		DLFileEntry existingFileEntry = null;

		try {
			if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
				(folderId > 0)) {

				DLFolderUtil.findByPrimaryKey(folderId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingFileEntry = DLFileEntryUtil.findByUUID_G(
						fileEntry.getUuid(), groupId);

					existingFileEntry =
						DLFileEntryLocalServiceUtil.updateFileEntry(
							userId, groupId, existingFileEntry.getFolderId(),
							folderId, existingFileEntry.getName(),
							fileEntry.getTitle(), fileEntry.getTitle(),
							fileEntry.getDescription(),
							fileEntry.getExtraSettings(), is,
							fileEntry.getSize(), serviceContext);
				}
				catch (NoSuchFileEntryException nsfee) {
					existingFileEntry =
						DLFileEntryLocalServiceUtil.addFileEntry(
							fileEntry.getUuid(), userId, groupId, folderId,
							fileEntry.getName(), fileEntry.getTitle(),
							fileEntry.getDescription(),
							fileEntry.getExtraSettings(), is,
							fileEntry.getSize(), serviceContext);
				}
			}
			else {
				existingFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
					null, userId, groupId, folderId, fileEntry.getName(),
					fileEntry.getTitle(), fileEntry.getDescription(),
					fileEntry.getExtraSettings(), is, fileEntry.getSize(),
					serviceContext);
			}

			fileEntryNames.put(
				fileEntry.getName(), existingFileEntry.getName());

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.importComments(
					DLFileEntry.class, fileEntry.getFileEntryId(),
					existingFileEntry.getFileEntryId(), groupId);
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.importRatingsEntries(
					DLFileEntry.class, fileEntry.getFileEntryId(),
					existingFileEntry.getFileEntryId());
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for entry " +
					fileEntry.getFileEntryId());
		}
	}

	public static void importFileRank(
			PortletDataContext context, Map<Long, Long> folderPKs,
			Map<String, String> fileEntryNames, DLFileRank rank)
		throws Exception {

		long userId = context.getUserId(rank.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, rank.getFolderId(), rank.getFolderId());

		String name = fileEntryNames.get(rank.getName());

		if (name == null) {
			name = rank.getName();
		}

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == rank.getFolderId())) {

			String path = getImportFolderPath(context, folderId);

			DLFolder folder = (DLFolder)context.getZipEntryAsObject(path);

			importFolder(context, folderPKs, folder);

			folderId = MapUtil.getLong(
				folderPKs, rank.getFolderId(), rank.getFolderId());
		}

		try {
			DLFolderUtil.findByPrimaryKey(folderId);

			DLFileRankLocalServiceUtil.updateFileRank(
				context.getGroupId(), context.getCompanyId(), userId, folderId,
				name);
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the folder for rank " + rank.getFileRankId());
		}
	}

	public static void importFolder(
			PortletDataContext context, Map<Long, Long> folderPKs,
			DLFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());
		long groupId = context.getGroupId();
		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if ((parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(context, parentFolderId);

			DLFolder parentFolder = (DLFolder)context.getZipEntryAsObject(path);

			importFolder(context, folderPKs, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		DLFolder existingFolder = null;

		try {
			if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				DLFolderUtil.findByPrimaryKey(parentFolderId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				existingFolder = DLFolderUtil.fetchByUUID_G(
					folder.getUuid(), groupId);

				if (existingFolder == null) {
					String name = getFolderName(
						context.getCompanyId(), groupId, parentFolderId,
						folder.getName(), 2);

					existingFolder = DLFolderLocalServiceUtil.addFolder(
						folder.getUuid(), userId, groupId, parentFolderId,
						name, folder.getDescription(), serviceContext);
				}
				else {
					existingFolder = DLFolderLocalServiceUtil.updateFolder(
						existingFolder.getFolderId(), parentFolderId,
						folder.getName(), folder.getDescription(),
						serviceContext);
				}
			}
			else {
				String name = getFolderName(
					context.getCompanyId(), groupId, parentFolderId,
					folder.getName(), 2);

				existingFolder = DLFolderLocalServiceUtil.addFolder(
					null, userId, groupId, parentFolderId, name,
					folder.getDescription(), serviceContext);
			}

			folderPKs.put(folder.getFolderId(), existingFolder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for folder " +
					folder.getFolderId());
		}
	}

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					DLPortletDataHandlerImpl.class, "deleteData")) {

				DLFolderLocalServiceUtil.deleteFolders(context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("documentlibrary-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element foldersEl = root.addElement("folders");
			Element fileEntriesEl = root.addElement("file-entries");
			Element fileShortcutsEl = root.addElement("file-shortcuts");
			Element fileRanksEl = root.addElement("file-ranks");

			List<DLFolder> folders = DLFolderUtil.findByGroupId(
				context.getGroupId());

			for (DLFolder folder : folders) {
				exportFolder(
					context, foldersEl, fileEntriesEl, fileShortcutsEl,
					fileRanksEl, folder);
			}

			List<DLFileEntry> fileEntries = DLFileEntryUtil.findByG_F(
				context.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			for (DLFileEntry fileEntry : fileEntries) {
				exportFileEntry(
					context, foldersEl, fileEntriesEl, fileRanksEl, fileEntry);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _categories, _comments,
			_ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _categories, _comments,
			_ratings, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> folderEls = root.element("folders").elements(
				"folder");

			Map<Long, Long> folderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

			for (Element folderEl : folderEls) {
				String path = folderEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				DLFolder folder = (DLFolder)context.getZipEntryAsObject(path);

				importFolder(context, folderPKs, folder);
			}

			List<Element> fileEntryEls = root.element("file-entries").elements(
				"file-entry");

			Map<String, String> fileEntryNames =
				(Map<String, String>)context.getNewPrimaryKeysMap(
					DLFileEntry.class);

			for (Element fileEntryEl : fileEntryEls) {
				String path = fileEntryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				DLFileEntry fileEntry =
					(DLFileEntry)context.getZipEntryAsObject(path);

				String binPath = fileEntryEl.attributeValue("bin-path");

				importFileEntry(
					context, folderPKs, fileEntryNames, fileEntry, binPath);
			}

			if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
				List<Element> fileShortcutEls = root.element(
					"file-shortcuts").elements("file-shortcut");

				for (Element fileShortcutEl : fileShortcutEls) {
					String path = fileShortcutEl.attributeValue("path");

					if (!context.isPathNotProcessed(path)) {
						continue;
					}

					DLFileShortcut fileShortcut =
						(DLFileShortcut)context.getZipEntryAsObject(path);

					importFileShortcut(
						context, folderPKs, fileEntryNames, fileShortcut);
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
				List<Element> fileRankEls = root.element("file-ranks").elements(
					"file-rank");

				for (Element fileRankEl : fileRankEls) {
					String path = fileRankEl.attributeValue("path");

					if (!context.isPathNotProcessed(path)) {
						continue;
					}

					DLFileRank fileRank =
						(DLFileRank)context.getZipEntryAsObject(path);

					importFileRank(
						context, folderPKs, fileEntryNames, fileRank);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected static void exportFileRank(
			PortletDataContext context, Element fileRanksEl,
			DLFileRank fileRank)
		throws SystemException {

		String path = getFileRankPath(context, fileRank);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element fileRankEl = fileRanksEl.addElement("file-rank");

		fileRankEl.addAttribute("path", path);

		fileRank.setUserUuid(fileRank.getUserUuid());

		context.addZipEntry(path, fileRank);
	}

	protected static void exportFileShortcut(
			PortletDataContext context, Element foldersEl,
			Element fileShortcutsEl, DLFileShortcut fileShortcut)
		throws PortalException, SystemException {

		exportParentFolder(context, foldersEl, fileShortcut.getFolderId());

		String path = getFileShortcutPath(context, fileShortcut);

		if (context.isPathNotProcessed(path)) {
			Element fileShortcutEl = fileShortcutsEl.addElement(
				"file-shortcut");

			fileShortcutEl.addAttribute("path", path);

			fileShortcut.setUserUuid(fileShortcut.getUserUuid());

			context.addZipEntry(path, fileShortcut);
		}
	}

	protected static void exportParentFolder(
			PortletDataContext context, Element foldersEl, long folderId)
		throws PortalException, SystemException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(context, foldersEl, folder.getParentFolderId());

		String path = getFolderPath(context, folder);

		if (context.isPathNotProcessed(path)) {
			Element folderEl = foldersEl.addElement("folder");

			folderEl.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			context.addZipEntry(path, folder);
		}
	}

	protected static String getFileEntryBinPath(
		PortletDataContext context, DLFileEntry fileEntry) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getTitle());

		return sb.toString();
	}

	protected static String getFileEntryPath(
		PortletDataContext context, DLFileEntry fileEntry) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/file-entries/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFolderName(
			long companyId, long groupId, long parentFolderId, String name,
			int count)
		throws SystemException {

		DLFolder folder = DLFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(count);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();

		return getFolderName(companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext context, DLFolder folder) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileRankPath(
		PortletDataContext context, DLFileRank fileRank) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/ranks/");
		sb.append(fileRank.getFileRankId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileShortcutPath(
		PortletDataContext context, DLFileShortcut fileShortcut) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/shortcuts/");
		sb.append(fileShortcut.getFileShortcutId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext context, long folderId) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getSourcePortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static long getRepositoryId(long groupId, long folderId) {
		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return groupId;
		}
		else {
			return folderId;
		}
	}

	protected static void importFileShortcut(
			PortletDataContext context, Map<Long, Long> folderPKs,
			Map<String, String> fileEntryNames, DLFileShortcut fileShortcut)
		throws Exception {

		long userId = context.getUserId(fileShortcut.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, fileShortcut.getFolderId(), fileShortcut.getFolderId());
		long toFolderId = MapUtil.getLong(
			folderPKs, fileShortcut.getToFolderId(),
			fileShortcut.getToFolderId());
		String toName = MapUtil.getString(
			fileEntryNames, fileShortcut.getToName(), fileShortcut.getToName());

		try {
			DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);
			DLFolderUtil.findByPrimaryKey(toFolderId);

			long groupId = folder.getGroupId();

			DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				groupId, toFolderId, toName);

			long[] assetCategoryIds = null;
			String[] assetTagNames = null;

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				assetCategoryIds = context.getAssetCategoryIds(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				assetTagNames = context.getAssetTagNames(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAssetCategoryIds(assetCategoryIds);
			serviceContext.setAssetTagNames(assetTagNames);
			serviceContext.setScopeGroupId(context.getGroupId());

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					DLFileShortcut existingFileShortcut =
						DLFileShortcutUtil.findByUUID_G(
							fileShortcut.getUuid(), context.getGroupId());

					DLFileShortcutLocalServiceUtil.updateFileShortcut(
						userId, existingFileShortcut.getFileShortcutId(),
						folderId, toFolderId, toName, serviceContext);
				}
				catch (NoSuchFileShortcutException nsfse) {
					DLFileShortcutLocalServiceUtil.addFileShortcut(
						fileShortcut.getUuid(), userId, groupId, folderId,
						toFolderId, toName, serviceContext);
				}
			}
			else {
				DLFileShortcutLocalServiceUtil.addFileShortcut(
					null, userId, groupId, folderId, toFolderId, toName,
					serviceContext);
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the folder for shortcut " +
					fileShortcut.getFileShortcutId());
		}
	}

	private static final String _NAMESPACE = "document_library";

	private static final PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static final PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static final PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

	private static final PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log =
		LogFactoryUtil.getLog(DLPortletDataHandlerImpl.class);

}