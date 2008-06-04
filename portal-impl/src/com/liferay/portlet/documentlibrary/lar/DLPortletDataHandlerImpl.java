/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinderUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutFinderUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="DLPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class DLPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
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
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			Document doc = DocumentHelper.createDocument();

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

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _comments, _ratings, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			List<Element> folderEls = root.element("folders").elements(
				"folder");

			Map<Long, Long> folderPKs = context.getNewPrimaryKeysMap(
				DLFolder.class);

			for (Element folderEl : folderEls) {
				String path = folderEl.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					DLFolder folder = (DLFolder)context.getZipEntryAsObject(
						path);

					importFolder(context, folderPKs, folder);
				}
			}

			List<Element> fileEntryEls = root.element("file-entries").elements(
				"file-entry");

			Map<String, String> fileEntryNames = context.getNewPrimaryKeysMap(
				DLFileEntry.class);

			for (Element fileEntryEl : fileEntryEls) {
				String path = fileEntryEl.attributeValue("path");
				String binPath = fileEntryEl.attributeValue("bin-path");

				if (context.isPathNotProcessed(path)) {
					DLFileEntry fileEntry =
						(DLFileEntry)context.getZipEntryAsObject(path);

					importFileEntry(
						context, folderPKs, fileEntryNames, fileEntry, binPath);
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
				List<Element> fileShortcutEls = root.element(
					"file-shortcuts").elements("file-shortcut");

				for (Element fileShortcutEl : fileShortcutEls) {
					String path = fileShortcutEl.attributeValue("path");

					if (context.isPathNotProcessed(path)) {
						DLFileShortcut fileShortcut =
							(DLFileShortcut)context.getZipEntryAsObject(path);

						importFileShortcut(
							context, folderPKs, fileEntryNames, fileShortcut);
					}
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
				List<Element> fileRankEls = root.element("file-ranks").elements(
					"file-rank");

				for (Element fileRankEl : fileRankEls) {
					String path = fileRankEl.attributeValue("path");

					if (context.isPathNotProcessed(path)) {
						DLFileRank fileRank =
							(DLFileRank)context.getZipEntryAsObject(path);

						importFileRank(
							context, folderPKs, fileEntryNames, fileRank);
					}
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	protected void exportFileEntry(
			PortletDataContext context, Element foldersEl,
			Element fileEntriesEl, Element fileRanksEl, DLFileEntry fileEntry)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(fileEntry.getModifiedDate())) {
			return;
		}

		String path = getFileEntryPath(context, fileEntry);
		String binPath = getFileEntryBinPath(context, fileEntry);

		Element fileEntryEl = fileEntriesEl.addElement("file-entry");

		fileEntryEl.addAttribute("path", path);
		fileEntryEl.addAttribute("bin-path", binPath);

		if (context.isPathNotProcessed(path)) {
			fileEntry.setUserUuid(fileEntry.getUserUuid());

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.addComments(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addTagsEntries(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
				fileEntry.getCompanyId(), fileEntry.getUserId(),
				fileEntry.getFolderId(), fileEntry.getName());

			try {
				context.addZipEntry(
					getFileEntryBinPath(context, fileEntry),
					FileUtil.getBytes(is));
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
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

		exportParentFolder(context, foldersEl, fileEntry.getFolderId());
	}

	protected void exportFileRank(
			PortletDataContext context, Element fileRanksEl,
			DLFileRank fileRank)
		throws PortalException, SystemException {

		String path = getFileRankPath(context, fileRank);

		Element fileRankEl = fileRanksEl.addElement("file-rank");

		fileRankEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			fileRank.setUserUuid(fileRank.getUserUuid());

			context.addZipEntry(path, fileRank);
		}
	}

	protected void exportFileShortcut(
			PortletDataContext context, Element foldersEl,
			Element fileShortcutsEl, DLFileShortcut fileShortcut)
		throws PortalException, SystemException {

		String path = getFileShortcutPath(context, fileShortcut);

		Element fileShortcutEl = fileShortcutsEl.addElement("file-shortcut");

		fileShortcutEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			fileShortcut.setUserUuid(fileShortcut.getUserUuid());

			context.addZipEntry(path, fileShortcut);
		}

		exportParentFolder(context, foldersEl, fileShortcut.getFolderId());
	}

	protected void exportFolder(
			PortletDataContext context, Element foldersEl,
			Element fileEntriesEl, Element fileShortcutsEl, Element fileRanksEl,
			DLFolder folder)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			String path = getFolderPath(context, folder);

			Element folderEl = foldersEl.addElement("folder");

			folderEl.addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				folder.setUserUuid(folder.getUserUuid());

				context.addZipEntry(path, folder);
			}

			exportParentFolder(context, foldersEl, folder.getParentFolderId());
		}

		List<DLFileEntry> fileEntries = DLFileEntryUtil.findByFolderId(
		folder.getFolderId());

		for (DLFileEntry fileEntry : fileEntries) {
			exportFileEntry(
				context, foldersEl, fileEntriesEl, fileRanksEl, fileEntry);
		}

		if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts =
				DLFileShortcutUtil.findByFolderId(folder.getFolderId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					context, foldersEl, fileShortcutsEl, fileShortcut);
			}
		}
	}

	protected void exportParentFolder(
			PortletDataContext context, Element foldersEl, long folderId)
		throws PortalException, SystemException {

		if ((!context.hasDateRange()) ||
			(folderId == DLFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {

			return;
		}

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		String path = getFolderPath(context, folder);

		Element folderEl = foldersEl.addElement("folder");

		folderEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			folder.setUserUuid(folder.getUserUuid());

			context.addZipEntry(path, folder);
		}

		exportParentFolder(context, foldersEl, folder.getParentFolderId());
	}

	protected String getFileEntryBinPath(
		PortletDataContext context, DLFileEntry fileEntry) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sm.append("/bin/");
		sm.append(fileEntry.getFileEntryId());
		sm.append(StringPool.SLASH);
		sm.append(fileEntry.getVersion());
		sm.append(StringPool.SLASH);
		sm.append(fileEntry.getTitleWithExtension());

		return sm.toString();
	}

	protected String getFileEntryPath(
		PortletDataContext context, DLFileEntry fileEntry) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sm.append("/entries/");
		sm.append(fileEntry.getFileEntryId());
		sm.append(StringPool.SLASH);
		sm.append(fileEntry.getVersion());
		sm.append(".xml");

		return sm.toString();
	}

	protected String getFolderName(
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

		StringMaker sm = new StringMaker();

		sm.append(name);
		sm.append(StringPool.SPACE);
		sm.append(StringPool.OPEN_PARENTHESIS);
		sm.append(count);
		sm.append(StringPool.CLOSE_PARENTHESIS);

		name = sm.toString();

		return getFolderName(companyId, groupId, parentFolderId, name, ++count);
	}

	protected String getFolderPath(
		PortletDataContext context, DLFolder folder) {

		return context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY) +
			"/folders/" + folder.getFolderId() + ".xml";
	}

	protected String getFileRankPath(
		PortletDataContext context, DLFileRank fileRank) {

		return context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY) +
			"/ranks/" + fileRank.getFileRankId() + ".xml";
	}

	protected String getFileShortcutPath(
		PortletDataContext context, DLFileShortcut fileShortcut) {

		return context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY) +
			"/shortcut/" + fileShortcut.getFileShortcutId() + ".xml";
	}

	protected void importFileEntry(
			PortletDataContext context, Map<Long, Long> folderPKs,
			Map<String, String> fileEntryNames, DLFileEntry fileEntry,
			String binPath)
		throws Exception {

		long userId = context.getUserId(fileEntry.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		byte[] bytes = context.getZipEntryAsByteArray(binPath);

		DLFileEntry existingFileEntry = null;

		try {
			DLFolderUtil.findByPrimaryKey(folderId);

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingFileEntry = DLFileEntryFinderUtil.findByUuid_G(
						fileEntry.getUuid(), context.getGroupId());

					existingFileEntry =
						DLFileEntryLocalServiceUtil.updateFileEntry(
							userId, existingFileEntry.getFolderId(), folderId,
							existingFileEntry.getName(), fileEntry.getName(),
							fileEntry.getTitle(), fileEntry.getDescription(),
							tagsEntries, fileEntry.getExtraSettings(), bytes);
				}
				catch (NoSuchFileEntryException nsfee) {
					existingFileEntry =
					DLFileEntryLocalServiceUtil.addFileEntry(
						fileEntry.getUuid(), userId, folderId,
						fileEntry.getName(), fileEntry.getTitle(),
						fileEntry.getDescription(), tagsEntries,
						fileEntry.getExtraSettings(), bytes,
						addCommunityPermissions, addGuestPermissions);
				}
			}
			else {
				existingFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
					userId, folderId, fileEntry.getName(), fileEntry.getTitle(),
					fileEntry.getDescription(), tagsEntries,
					fileEntry.getExtraSettings(), bytes,
					addCommunityPermissions, addGuestPermissions);
			}

			fileEntryNames.put(
				fileEntry.getName(), existingFileEntry.getName());

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.importComments(
					DLFileEntry.class, fileEntry.getFileEntryId(),
					existingFileEntry.getFileEntryId(), context.getGroupId());
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

	protected void importFileRank(
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

	protected void importFileShortcut(
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

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		try {
			DLFolderUtil.findByPrimaryKey(folderId);
			DLFolderUtil.findByPrimaryKey(toFolderId);

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					DLFileShortcut existingFileShortcut =
						DLFileShortcutFinderUtil.findByUuid_G(
							fileShortcut.getUuid(), context.getGroupId());

					DLFileShortcutLocalServiceUtil.updateFileShortcut(
						userId, existingFileShortcut.getFileShortcutId(),
						folderId, toFolderId, toName);
				}
				catch (NoSuchFileShortcutException nsfse) {
					DLFileShortcutLocalServiceUtil.addFileShortcut(
						fileShortcut.getUuid(), userId, folderId, toFolderId,
						toName, addCommunityPermissions, addGuestPermissions);
				}
			}
			else {
				DLFileShortcutLocalServiceUtil.addFileShortcut(
					userId, folderId, toFolderId, toName,
					addCommunityPermissions, addGuestPermissions);
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the folder for shortcut " +
					fileShortcut.getFileShortcutId());
		}
	}

	protected void importFolder(
			PortletDataContext context, Map<Long, Long> folderPKs,
			DLFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());
		long plid = context.getPlid();
		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		DLFolder existingFolder = null;

		try {
			if (parentFolderId != DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
				DLFolderUtil.findByPrimaryKey(parentFolderId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				existingFolder = DLFolderUtil.fetchByUUID_G(
					folder.getUuid(), context.getGroupId());

				if (existingFolder == null) {
					String name = getFolderName(
						context.getCompanyId(), context.getGroupId(),
						parentFolderId, folder.getName(), 2);

					existingFolder = DLFolderLocalServiceUtil.addFolder(
						folder.getUuid(), userId, plid, parentFolderId,
						name, folder.getDescription(), addCommunityPermissions,
						addGuestPermissions);
				}
				else {
					existingFolder = DLFolderLocalServiceUtil.updateFolder(
						existingFolder.getFolderId(), parentFolderId,
						folder.getName(), folder.getDescription());
				}
			}
			else {
				String name = getFolderName(
					context.getCompanyId(), context.getGroupId(),
					parentFolderId, folder.getName(), 2);

				existingFolder = DLFolderLocalServiceUtil.addFolder(
					userId, plid, parentFolderId, name, folder.getDescription(),
					addCommunityPermissions, addGuestPermissions);
			}

			folderPKs.put(folder.getFolderId(), existingFolder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for folder " +
					folder.getFolderId());
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

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log = LogFactory.getLog(DLPortletDataHandlerImpl.class);

}