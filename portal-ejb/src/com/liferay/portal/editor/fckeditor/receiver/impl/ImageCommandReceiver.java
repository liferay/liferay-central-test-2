/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.spring.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.spring.IGFolderServiceUtil;
import com.liferay.portlet.imagegallery.service.spring.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.spring.IGImageServiceUtil;
import com.liferay.util.StringPool;

import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <a href="ImageCommandReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 *
 */
public class ImageCommandReceiver extends BaseCommandReceiver {

	protected String createFolder(CommandArgument arg) {
		try {
			IGFolder folder = _getFolder(
				arg.getGroupId(), "/" + arg.getCurrentFolder());

			IGFolderServiceUtil.addFolder(
				arg.getPlid(), folder.getFolderId(), arg.getNewFolder(),
				StringPool.BLANK, true, true);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected String fileUpload(
		CommandArgument arg, String fileName, File file, String extension) {

		try {
			IGFolder folder = _getFolder(
				arg.getGroupId(), arg.getCurrentFolder());

			IGImageServiceUtil.addImage(
				folder.getFolderId(), fileName, file, extension, true, true);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected void getFolders(CommandArgument arg, Node root, Document doc) {
		try {
			_getFolders(arg, root, doc);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	protected void getFoldersAndFiles(
		CommandArgument arg, Node root, Document doc) {

		try {
			_getFolders(arg, root, doc);
			_getFiles(arg, root, doc);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private void _getFiles(CommandArgument arg, Node root, Document doc)
		throws Exception {

		Element filesEl = doc.createElement("Files");

		root.appendChild(filesEl);

		IGFolder folder = _getFolder(
			arg.getGroupId(), arg.getCurrentFolder());

		List images = IGImageLocalServiceUtil.getImages(
			folder.getFolderId());

		for (int i = 0; i < images.size(); i++) {
			IGImage image = (IGImage)images.get(i);

			Element fileEl = doc.createElement("File");

			filesEl.appendChild(fileEl);

			fileEl.setAttribute("name", image.getImageId());
			fileEl.setAttribute("desc", image.getDescription());
			fileEl.setAttribute("size", getSize(image.getSize()));
			fileEl.setAttribute(
				"url", "/image/image_gallery?img_id=" + image.getImageId());
		}
	}

	private IGFolder _getFolder(String groupId, String folderName)
		throws Exception {

		IGFolder folder = new IGFolder();

		folder.setFolderId(IGFolder.DEFAULT_PARENT_FOLDER_ID);

		if (!folderName.equals("/")) {
			StringTokenizer st = new StringTokenizer(folderName, "/");

			while (st.hasMoreTokens()) {
				String curFolderName = (String)st.nextToken();

				List folders = IGFolderLocalServiceUtil.getFolders(
					groupId, folder.getFolderId());

				for (int i = 0; i < folders.size(); i++) {
					IGFolder curFolder = (IGFolder)folders.get(i);

					if (curFolder.getName().equals(curFolderName)) {
						folder = curFolder;

						break;
					}
				}
			}
		}

		return folder;
	}

	private void _getFolders(CommandArgument arg, Node root, Document doc)
		throws Exception {

		Element foldersEl = doc.createElement("Folders");

		root.appendChild(foldersEl);

		IGFolder folder = _getFolder(
			arg.getGroupId(), arg.getCurrentFolder());

		List folders = IGFolderLocalServiceUtil.getFolders(
			arg.getGroupId(), folder.getFolderId());

		for (int i = 0; i < folders.size(); i++) {
			folder = (IGFolder)folders.get(i);

			Element folderEl = doc.createElement("Folder");

			foldersEl.appendChild(folderEl);

			folderEl.setAttribute("name", folder.getName());
		}
	}

	private static Log _log = LogFactory.getLog(ImageCommandReceiver.class);

}