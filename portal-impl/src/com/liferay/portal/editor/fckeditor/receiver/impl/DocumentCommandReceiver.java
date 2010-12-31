/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

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

			Folder folder = _getFolder(
				group.getGroupId(), StringPool.SLASH + arg.getCurrentFolder());

			long parentFolderId = folder.getFolderId();
			String name = arg.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFolder(
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

			Folder folder = _getFolder(
				group.getGroupId(), arg.getCurrentFolder());

			long folderId = folder.getFolderId();
			String title = fileName;
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, title, description, changeLog,
				file, serviceContext);
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

	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY);
	}

	private void _getFiles(CommandArgument arg, Document doc, Node root)
		throws Exception {

		Element filesEl = doc.createElement("Files");

		root.appendChild(filesEl);

		if (Validator.isNull(arg.getCurrentGroupName())) {
			return;
		}

		Group group = arg.getCurrentGroup();

		Folder folder = _getFolder(
			group.getGroupId(), arg.getCurrentFolder());

		List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
			group.getGroupId(), folder.getFolderId());

		for (FileEntry fileEntry : fileEntries) {
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
			url.append(group.getGroupId());

			fileEl.setAttribute("url", url.toString());
		}
	}

	private Folder _getFolder(long groupId, String folderName)
		throws Exception {

		if (folderName.equals(StringPool.SLASH)) {
			DLFolder dolFolder = new DLFolderImpl();

			dolFolder.setFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			dolFolder.setGroupId(groupId);

			return new LiferayFolder(dolFolder);
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		Folder folder = null;

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<Folder> folders = DLAppServiceUtil.getFolders(
				groupId, folder.getFolderId());

			for (Folder curFolder : folders) {
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

			Folder folder = _getFolder(
				group.getGroupId(), arg.getCurrentFolder());

			List<Folder> folders = DLAppServiceUtil.getFolders(
				group.getGroupId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				Element folderEl = doc.createElement("Folder");

				foldersEl.appendChild(folderEl);

				folderEl.setAttribute("name", curFolder.getName());
			}
		}
	}

}