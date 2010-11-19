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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.util.PwdGenerator;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class DLPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportFileEntry(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileEntriesElement, Element fileRanksElement,
			DLFileEntry fileEntry, boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(
				fileEntry.getModifiedDate())) {

			return;
		}

		DLFileVersion fileVersion = fileEntry.getFileVersion();

		if (fileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(
				portletDataContext, foldersElement, fileEntry.getFolderId());
		}

		String path = getFileEntryPath(portletDataContext, fileEntry);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element fileEntryElement = fileEntriesElement.addElement(
				"file-entry");

			fileEntryElement.addAttribute("path", path);

			String binPath = getFileEntryBinPath(portletDataContext, fileEntry);

			fileEntryElement.addAttribute("bin-path", binPath);

			fileEntry.setUserUuid(fileEntry.getUserUuid());

			portletDataContext.addLocks(
				DLFileEntry.class,
				DLUtil.getLockId(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName()));

			portletDataContext.addPermissions(
				DLFileEntry.class, fileEntry.getFileEntryId());

			if (portletDataContext.getBooleanParameter(
					_NAMESPACE, "categories")) {

				portletDataContext.addAssetCategories(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (portletDataContext.getBooleanParameter(
					_NAMESPACE, "comments")) {

				portletDataContext.addComments(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings")) {
				portletDataContext.addRatingsEntries(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
				portletDataContext.addAssetTags(
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

				fileEntryElement.detach();

				return;
			}

			try {
				portletDataContext.addZipEntry(
					getFileEntryBinPath(portletDataContext, fileEntry), is);
			}
			finally {
				try {
					is.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}

			portletDataContext.addZipEntry(path, fileEntry);

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "ranks")) {
				List<DLFileRank> fileRanks = DLFileRankUtil.findByF_N(
					fileEntry.getFolderId(), fileEntry.getName());

				for (DLFileRank fileRank : fileRanks) {
					exportFileRank(
						portletDataContext, fileRanksElement, fileRank);
				}
			}
		}
	}

	public static String getFileEntryPath(
		PortletDataContext portletDataContext, DLFileEntry fileEntry) {

		StringBundler sb = new StringBundler(6);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/file-entries/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());
		sb.append(".xml");

		return sb.toString();
	}

	public static void importFileEntry(
			PortletDataContext portletDataContext, Element fileEntryElement)
		throws Exception {

		String path = fileEntryElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileEntry fileEntry =
			(DLFileEntry)portletDataContext.getZipEntryAsObject(path);

		String binPath = fileEntryElement.attributeValue("bin-path");

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = portletDataContext.getAssetTagNames(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(fileEntry.getCreateDate());
		serviceContext.setModifiedDate(fileEntry.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		InputStream is = portletDataContext.getZipEntryAsInputStream(binPath);

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == fileEntry.getFolderId())) {

			String folderPath = getImportFolderPath(
				portletDataContext, folderId);

			DLFolder folder = (DLFolder)portletDataContext.getZipEntryAsObject(
				folderPath);

			importFolder(portletDataContext, folder);

			folderId = MapUtil.getLong(
				folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());
		}

		fileEntry.setFolderId(folderId);

		DLFileEntry importedFileEntry = null;

		String nameWithExtension =
			fileEntry.getName().concat(StringPool.PERIOD).concat(
				fileEntry.getExtension());

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileEntry existingFileEntry = DLFileEntryUtil.fetchByUUID_G(
				fileEntry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFileEntry == null) {
				String fileEntryTitle = fileEntry.getTitle();

				DLFileEntry existingTitleFileEntry =
					DLFileEntryUtil.fetchByG_F_T(
						portletDataContext.getScopeGroupId(), folderId,
						fileEntry.getTitle());

				if (existingTitleFileEntry != null) {
					if (portletDataContext.
							isDataStrategyMirrorWithOverwritting()) {

						DLAppLocalServiceUtil.deleteFileEntry(
							existingTitleFileEntry);
					}
					else {
						String originalTitle = 	fileEntryTitle;

						for (int i = 1;; i++) {
							fileEntryTitle =
								originalTitle + StringPool.SPACE + i;

							existingTitleFileEntry =
								DLFileEntryUtil.findByG_F_T(
									portletDataContext.getScopeGroupId(),
									folderId, fileEntry.getTitle());

							if (existingTitleFileEntry == null) {
								break;
							}
						}
					}
				}

				serviceContext.setUuid(fileEntry.getUuid());

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					nameWithExtension, fileEntryTitle,
					fileEntry.getDescription(), null,
					fileEntry.getExtraSettings(), is, fileEntry.getSize(),
					serviceContext);
			}
			else if (!isDuplicateFileEntry(fileEntry, existingFileEntry)) {
				importedFileEntry = DLAppLocalServiceUtil.updateFileEntry(
					userId, portletDataContext.getScopeGroupId(),
					existingFileEntry.getFolderId(),
					existingFileEntry.getName(), fileEntry.getTitle(),
					fileEntry.getTitle(), fileEntry.getDescription(), null,
					true, fileEntry.getExtraSettings(), is, fileEntry.getSize(),
					serviceContext);
			}
			else {
				DLFileVersion latestFileVersion =
					DLAppLocalServiceUtil.getLatestFileVersion(
						portletDataContext.getScopeGroupId(), folderId,
						existingFileEntry.getName());

				DLAppLocalServiceUtil.updateAsset(
					userId, existingFileEntry, latestFileVersion,
					assetCategoryIds, assetTagNames);

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					DLFileEntry.class);

				indexer.reindex(existingFileEntry);

				importedFileEntry = existingFileEntry;
			}
		}
		else {
			String title = fileEntry.getTitle();

			try {
				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					nameWithExtension, title, fileEntry.getDescription(), null,
					fileEntry.getExtraSettings(), is, fileEntry.getSize(),
					serviceContext);
			}
			catch (DuplicateFileException dfe) {
				String[] titleParts = title.split("\\.", 2);

				title = titleParts[0] + PwdGenerator.getPassword();

				if (titleParts.length > 1) {
					title += StringPool.PERIOD + titleParts[1];
				}

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					nameWithExtension, title, fileEntry.getDescription(), null,
					fileEntry.getExtraSettings(), is, fileEntry.getSize(),
					serviceContext);
			}
		}

		Map<Long, Long> fileEntryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		fileEntryPKs.put(
			fileEntry.getFileEntryId(), importedFileEntry.getFileEntryId());

		Map<String, String> fileEntryNames =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".name");

		fileEntryNames.put(fileEntry.getName(), importedFileEntry.getName());

		String lockKey = DLUtil.getLockId(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		String newLockKey = DLUtil.getLockId(
			importedFileEntry.getGroupId(), importedFileEntry.getFolderId(),
			importedFileEntry.getName());

		portletDataContext.importLocks(DLFileEntry.class, lockKey, newLockKey);

		portletDataContext.importPermissions(
			DLFileEntry.class, fileEntry.getFileEntryId(),
			importedFileEntry.getFileEntryId());

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "comments")) {
			portletDataContext.importComments(
				DLFileEntry.class, fileEntry.getFileEntryId(),
				importedFileEntry.getFileEntryId(),
				portletDataContext.getScopeGroupId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings")) {
			portletDataContext.importRatingsEntries(
				DLFileEntry.class, fileEntry.getFileEntryId(),
				importedFileEntry.getFileEntryId());
		}
	}

	public static void importFileRank(
			PortletDataContext portletDataContext, Element fileRankElement)
		throws Exception {

		String path = fileRankElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileRank fileRank =
			(DLFileRank)portletDataContext.getZipEntryAsObject(path);

		importFileRank(portletDataContext, fileRank);
	}

	public static void importFolder(
			PortletDataContext portletDataContext, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFolder folder = (DLFolder)portletDataContext.getZipEntryAsObject(
			path);

		importFolder(portletDataContext, folder);
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

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isPublishToLiveByDefault() {
		return PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void exportFileRank(
			PortletDataContext portletDataContext, Element fileRanksElement,
			DLFileRank fileRank)
		throws Exception {

		String path = getFileRankPath(portletDataContext, fileRank);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element fileRankElement = fileRanksElement.addElement("file-rank");

		fileRankElement.addAttribute("path", path);

		fileRank.setUserUuid(fileRank.getUserUuid());

		portletDataContext.addZipEntry(path, fileRank);
	}

	protected static void exportFileShortcut(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileShortcutsElement, DLFileShortcut fileShortcut)
		throws Exception {

		exportParentFolder(
			portletDataContext, foldersElement, fileShortcut.getFolderId());

		String path = getFileShortcutPath(portletDataContext, fileShortcut);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element fileShortcutElement = fileShortcutsElement.addElement(
				"file-shortcut");

			fileShortcutElement.addAttribute("path", path);

			fileShortcut.setUserUuid(fileShortcut.getUserUuid());

			portletDataContext.addPermissions(
				DLFileShortcut.class, fileShortcut.getFileShortcutId());

			portletDataContext.addZipEntry(path, fileShortcut);
		}
	}

	protected static void exportFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileEntriesElement, Element fileShortcutsElement,
			Element fileRanksElement, DLFolder folder, boolean recurse)
		throws Exception {

		if (portletDataContext.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				portletDataContext, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(portletDataContext, folder);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				folderElement.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				portletDataContext.addPermissions(
					DLFolder.class, folder.getFolderId());

				portletDataContext.addZipEntry(path, folder);
			}
		}

		if (recurse) {
			List<DLFolder> folders = DLFolderUtil.findByG_P(
				folder.getGroupId(), folder.getFolderId());

			for (DLFolder curFolder : folders) {
				exportFolder(
					portletDataContext, foldersElement, fileEntriesElement,
					fileShortcutsElement, fileRanksElement, curFolder, recurse);
			}
		}

		List<DLFileEntry> fileEntries = DLFileEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (DLFileEntry fileEntry : fileEntries) {
			exportFileEntry(
				portletDataContext, foldersElement, fileEntriesElement,
				fileRanksElement, fileEntry, true);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F(
				folder.getGroupId(), folder.getFolderId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					portletDataContext, foldersElement, fileShortcutsElement,
					fileShortcut);
			}
		}
	}

	protected static void exportParentFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			long folderId)
		throws Exception {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			folderElement.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			portletDataContext.addPermissions(
				DLFolder.class, folder.getFolderId());

			portletDataContext.addZipEntry(path, folder);
		}
	}

	protected static String getFileEntryBinPath(
		PortletDataContext portletDataContext, DLFileEntry fileEntry) {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());

		return sb.toString();
	}

	protected static String getFileRankPath(
		PortletDataContext portletDataContext, DLFileRank fileRank) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/ranks/");
		sb.append(fileRank.getFileRankId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileShortcutPath(
		PortletDataContext portletDataContext, DLFileShortcut fileShortcut) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/shortcuts/");
		sb.append(fileShortcut.getFileShortcutId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFolderName(
			String uuid, long companyId, long groupId, long parentFolderId,
			String name, int count)
		throws Exception {

		DLFolder folder = DLFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(count);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();

		return getFolderName(
			uuid, companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext portletDataContext, DLFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(
				PortletKeys.DOCUMENT_LIBRARY));
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

	protected static void importFileRank(
			PortletDataContext portletDataContext, DLFileRank rank)
		throws Exception {

		long userId = portletDataContext.getUserId(rank.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, rank.getFolderId(), rank.getFolderId());

		Map<String, String> fileEntryNames =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".name");

		String name = fileEntryNames.get(rank.getName());

		if (name == null) {
			name = rank.getName();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(rank.getCreateDate());

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == rank.getFolderId())) {

			String path = getImportFolderPath(portletDataContext, folderId);

			DLFolder folder = (DLFolder)portletDataContext.getZipEntryAsObject(
				path);

			importFolder(portletDataContext, folder);

			folderId = MapUtil.getLong(
				folderPKs, rank.getFolderId(), rank.getFolderId());
		}

		DLAppLocalServiceUtil.updateFileRank(
			portletDataContext.getScopeGroupId(),
			portletDataContext.getCompanyId(), userId, folderId, name,
			serviceContext);
	}

	protected static void importFileShortcut(
			PortletDataContext portletDataContext, DLFileShortcut fileShortcut)
		throws Exception {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, fileShortcut.getFolderId(), fileShortcut.getFolderId());

		long groupId = portletDataContext.getScopeGroupId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

			groupId = folder.getGroupId();
		}

		long toFolderId = MapUtil.getLong(
			folderPKs, fileShortcut.getToFolderId(),
			fileShortcut.getToFolderId());

		Map<String, String> fileEntryNames =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".name");

		String toName = MapUtil.getString(
			fileEntryNames, fileShortcut.getToName(), fileShortcut.getToName());

		DLFileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, toFolderId, toName);

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = portletDataContext.getAssetTagNames(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(fileShortcut.getCreateDate());
		serviceContext.setModifiedDate(fileShortcut.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		DLFileShortcut importedFileShortcut = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileShortcut existingFileShortcut =
				DLFileShortcutUtil.fetchByUUID_G(
					fileShortcut.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingFileShortcut == null) {
				serviceContext.setUuid(fileShortcut.getUuid());

				importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
					userId, groupId, folderId, toFolderId, toName,
					serviceContext);
			}
			else {
				importedFileShortcut = DLAppLocalServiceUtil.updateFileShortcut(
					userId, existingFileShortcut.getFileShortcutId(), folderId,
					toFolderId, toName, serviceContext);
			}
		}
		else {
			importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
				userId, groupId, folderId, toFolderId, toName,
				serviceContext);
		}

		portletDataContext.importPermissions(
			DLFileShortcut.class, fileShortcut.getPrimaryKey(),
			importedFileShortcut.getPrimaryKey());
	}

	protected static void importFileShortcut(
			PortletDataContext portletDataContext, Element fileShortcutElement)
		throws Exception {

		String path = fileShortcutElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileShortcut fileShortcut =
			(DLFileShortcut)portletDataContext.getZipEntryAsObject(path);

		importFileShortcut(portletDataContext, fileShortcut);
	}

	protected static void importFolder(
			PortletDataContext portletDataContext, DLFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(folder.getCreateDate());
		serviceContext.setModifiedDate(folder.getModifiedDate());

		if ((parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			DLFolder parentFolder =
				(DLFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		DLFolder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFolder existingFolder = DLFolderUtil.fetchByUUID_G(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					null, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = DLAppLocalServiceUtil.addFolder(
					userId, portletDataContext.getScopeGroupId(),
					parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				importedFolder = DLAppLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId(), parentFolderId,
				folder.getName(), 2);

			importedFolder = DLAppLocalServiceUtil.addFolder(
				userId, portletDataContext.getScopeGroupId(), parentFolderId,
				name, folder.getDescription(), serviceContext);
		}

		folderPKs.put(folder.getFolderId(), importedFolder.getFolderId());

		portletDataContext.importPermissions(
			DLFolder.class, folder.getFolderId(), importedFolder.getFolderId());
	}

	protected static boolean isDuplicateFileEntry(
		DLFileEntry fileEntry1, DLFileEntry fileEntry2) {

		try {
			DLFolder folder1 = fileEntry1.getFolder();
			DLFolder folder2 = fileEntry2.getFolder();

			if ((folder1.getUuid().equals(folder2.getUuid())) &&
				(fileEntry1.getSize() == fileEntry2.getSize()) &&
				(DLUtil.compareVersions(
					fileEntry1.getVersion(), fileEntry2.getVersion()) == 0) &&
				(fileEntry1.getVersionUserUuid().equals(
					fileEntry2.getVersionUserUuid()))) {

				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				DLPortletDataHandlerImpl.class, "deleteData")) {

			DLAppLocalServiceUtil.deleteFolders(
				portletDataContext.getScopeGroupId());

			DLAppLocalServiceUtil.deleteFileEntries(
				portletDataContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.documentlibrary",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("documentlibrary-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			rootElement.addAttribute(
				"root-folder-id", String.valueOf(rootFolderId));
		}

		Element foldersElement = rootElement.addElement("folders");
		Element fileEntriesElement = rootElement.addElement("file-entries");
		Element fileShortcutsElement = rootElement.addElement("file-shortcuts");
		Element fileRanksElement = rootElement.addElement("file-ranks");

		List<DLFolder> folders = DLFolderUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (DLFolder folder : folders) {
			exportFolder(
				portletDataContext, foldersElement, fileEntriesElement,
				fileShortcutsElement, fileRanksElement, folder, false);
		}

		List<DLFileEntry> fileEntries = DLFileEntryUtil.findByG_F(
			portletDataContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFileEntry fileEntry : fileEntries) {
			exportFileEntry(
				portletDataContext, foldersElement, fileEntriesElement,
				fileRanksElement, fileEntry, true);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F(
				portletDataContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					portletDataContext, foldersElement, fileShortcutsElement,
					fileShortcut);
			}
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.documentlibrary",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			importFolder(portletDataContext, folderElement);
		}

		Element fileEntriesElement = rootElement.element("file-entries");

		List<Element> fileEntryElements = fileEntriesElement.elements(
			"file-entry");

		for (Element fileEntryElement : fileEntryElements) {
			importFileEntry(portletDataContext, fileEntryElement);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<Element> fileShortcutElements = rootElement.element(
				"file-shortcuts").elements("file-shortcut");

			for (Element fileShortcutElement : fileShortcutElements) {
				importFileShortcut(portletDataContext, fileShortcutElement);
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ranks")) {
			Element fileRanksElement = rootElement.element("file-ranks");

			List<Element> fileRankElements = fileRanksElement.elements(
				"file-rank");

			for (Element fileRankElement : fileRankElements) {
				importFileRank(portletDataContext, fileRankElement);
			}
		}

		long rootFolderId = GetterUtil.getLong(
			rootElement.attributeValue("root-folder-id"));

		if (rootFolderId > 0) {
			Map<Long, Long> folderPKs =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFolder.class);

			rootFolderId = MapUtil.getLong(
				folderPKs, rootFolderId, rootFolderId);

			portletPreferences.setValue(
				"rootFolderId", String.valueOf(rootFolderId));
		}

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "document_library";

	private static Log _log = LogFactoryUtil.getLog(
		DLPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}