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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class DocumentCommandReceiver extends BaseCommandReceiver {

	protected String createFolder(CommandArgument arg) {
		try {
			Group group = arg.getCurrentGroup();

			DLFolder folder = _getFolder(
				group.getGroupId(), StringPool.SLASH + arg.getCurrentFolder());

			long parentFolderId = folder.getFolderId();
			String name = arg.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLFolderServiceUtil.addFolder(
				group.getGroupId(), parentFolderId, name, description,
				serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected String fileUpload(
		CommandArgument arg, String fileName, File file, String extension) {

		try {
			Group group = arg.getCurrentGroup();

			DLFolder folder = _getFolder(
				group.getGroupId(), arg.getCurrentFolder());

			long folderId = folder.getFolderId();
			String name = fileName;
			String title = fileName;
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;
			String extraSettings = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLFileEntryServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, title, description,
				changeLog, extraSettings, file, serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected void getFolders(CommandArgument arg, Document doc, Node root) {
		try {
			_getFolders(arg, doc, root);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	protected void getFoldersAndFiles(
		CommandArgument arg, Document doc, Node root) {

		try {
			_getFolders(arg, doc, root);
			_getFiles(arg, doc, root);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private void _getFiles(CommandArgument arg, Document doc, Node root)
		throws Exception {

		Element filesEl = doc.createElement("Files");

		root.appendChild(filesEl);

		if (Validator.isNull(arg.getCurrentGroupName())) {
			return;
		}

		Group group = arg.getCurrentGroup();

		DLFolder folder = _getFolder(
			group.getGroupId(), arg.getCurrentFolder());

		List<DLFileEntry> fileEntries = DLFileEntryServiceUtil.getFileEntries(
			folder.getGroupId(), folder.getFolderId());

		for (DLFileEntry fileEntry : fileEntries) {
			Element fileEl = doc.createElement("File");

			filesEl.appendChild(fileEl);

			fileEl.setAttribute("name", fileEntry.getTitle());
			fileEl.setAttribute("desc", fileEntry.getTitle());
			fileEl.setAttribute("size", getSize(fileEntry.getSize()));

			StringBundler url = new StringBundler(5);

			ThemeDisplay themeDisplay = arg.getThemeDisplay();

			url.append(themeDisplay.getPathMain());
			url.append("/document_library/get_file?uuid=");
			url.append(fileEntry.getUuid());
			url.append("&groupId=");
			url.append(folder.getGroupId());

			fileEl.setAttribute("url", url.toString());
		}
	}

	private DLFolder _getFolder(long groupId, String folderName)
		throws Exception {

		DLFolder folder = new DLFolderImpl();

		folder.setFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		folder.setGroupId(groupId);

		if (folderName.equals(StringPool.SLASH)) {
			return folder;
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<DLFolder> folders = DLFolderServiceUtil.getFolders(
				groupId, folder.getFolderId());

			for (DLFolder curFolder : folders) {
				if (curFolder.getName().equals(curFolderName)) {
					folder = curFolder;

					break;
				}
			}
		}

		return folder;
	}

	private void _getFolders(CommandArgument arg, Document doc, Node root)
		throws Exception {

		Element foldersEl = doc.createElement("Folders");

		root.appendChild(foldersEl);

		if (arg.getCurrentFolder().equals(StringPool.SLASH)) {
			getRootFolders(arg, doc, foldersEl);
		}
		else {
			Group group = arg.getCurrentGroup();

			DLFolder folder = _getFolder(
				group.getGroupId(), arg.getCurrentFolder());

			List<DLFolder> folders = DLFolderServiceUtil.getFolders(
				group.getGroupId(), folder.getFolderId());

			for (DLFolder curFolder : folders) {
				Element folderEl = doc.createElement("Folder");

				foldersEl.appendChild(folderEl);

				folderEl.setAttribute("name", curFolder.getName());
			}
		}
	}

}