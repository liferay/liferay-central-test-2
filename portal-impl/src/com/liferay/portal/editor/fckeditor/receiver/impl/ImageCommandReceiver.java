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
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.IGFolderServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class ImageCommandReceiver extends BaseCommandReceiver {

	protected String createFolder(CommandArgument arg) {
		try {
			Group group = arg.getCurrentGroup();

			IGFolder folder = _getFolder(
				group.getGroupId(), StringPool.SLASH + arg.getCurrentFolder());

			long parentFolderId = folder.getFolderId();
			String name = arg.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setPlid(arg.getPlid());
			serviceContext.setScopeGroupId(group.getGroupId());

			IGFolderServiceUtil.addFolder(
				parentFolderId, name, description, serviceContext);
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

			long groupId = group.getGroupId();

			IGFolder folder = _getFolder(groupId, arg.getCurrentFolder());

			long folderId = folder.getFolderId();
			String name = fileName;
			String description = StringPool.BLANK;
			String contentType = extension.toLowerCase();

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			IGImageServiceUtil.addImage(
				groupId, folderId, name, description, file, contentType,
				serviceContext);
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
		return group.isStagedPortlet(PortletKeys.IMAGE_GALLERY);
	}

	private void _getFiles(CommandArgument arg, Document doc, Node root)
		throws Exception {

		Element filesEl = doc.createElement("Files");

		root.appendChild(filesEl);

		if (Validator.isNull(arg.getCurrentGroupName())) {
			return;
		}

		Group group = arg.getCurrentGroup();

		IGFolder folder = _getFolder(
			group.getGroupId(), arg.getCurrentFolder());

		List<IGImage> images = IGImageServiceUtil.getImages(
			folder.getGroupId(), folder.getFolderId());

		for (IGImage image : images) {
			long largeImageId = image.getLargeImageId();

			Image portalImage = ImageLocalServiceUtil.getImageOrDefault(
				largeImageId);

			Element fileEl = doc.createElement("File");

			filesEl.appendChild(fileEl);

			fileEl.setAttribute("name", image.getNameWithExtension());
			fileEl.setAttribute("desc", image.getNameWithExtension());
			fileEl.setAttribute("size", getSize(portalImage.getSize()));

			StringBundler url = new StringBundler(7);

			ThemeDisplay themeDisplay = arg.getThemeDisplay();

			url.append(themeDisplay.getPathImage());
			url.append("/image_gallery?uuid=");
			url.append(image.getUuid());
			url.append("&groupId=");
			url.append(folder.getGroupId());
			url.append("&t=");
			url.append(ImageServletTokenUtil.getToken(largeImageId));

			fileEl.setAttribute("url", url.toString());
		}
	}

	private IGFolder _getFolder(long groupId, String folderName)
		throws Exception {

		IGFolder folder = new IGFolderImpl();

		folder.setFolderId(IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		folder.setGroupId(groupId);

		if (folderName.equals(StringPool.SLASH)) {
			return folder;
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<IGFolder> folders = IGFolderServiceUtil.getFolders(
				groupId, folder.getFolderId());

			for (IGFolder curFolder : folders) {
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

			IGFolder folder = _getFolder(
				group.getGroupId(), arg.getCurrentFolder());

			List<IGFolder> folders = IGFolderServiceUtil.getFolders(
				group.getGroupId(), folder.getFolderId());

			for (IGFolder curFolder : folders) {
				Element folderEl = doc.createElement("Folder");

				foldersEl.appendChild(folderEl);

				folderEl.setAttribute("name", curFolder.getName());
			}
		}
	}

}