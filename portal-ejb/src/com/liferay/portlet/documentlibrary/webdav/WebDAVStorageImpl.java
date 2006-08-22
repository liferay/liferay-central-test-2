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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.webdav.AbstractWebDAVStorage;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFolderLocalServiceUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebDAVStorageImpl extends AbstractWebDAVStorage {

	protected void addFolder(String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String folderName = folderNames[folderNames.length - 1];

		DLFolderLocalServiceUtil.addFolder(
			getUserId(), getPlid(groupId), parentFolderId, folderName, null,
			true, true);
	}

	protected void addResource(
			String companyId, String groupId, String uri, InputStream content,
			String contentType, String characterEncoding)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String fileName = folderNames[folderNames.length - 1];

		byte[] byteArray = FileUtil.getBytes(content);

		DLFileEntryLocalServiceUtil.addFileEntry(
			getUserId(), parentFolderId, fileName, fileName, null, byteArray,
			true, true);
	}

	protected void deleteObject(String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String name = folderNames[folderNames.length - 1];

		try {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(
				parentFolderId, name);

			DLFolderLocalServiceUtil.deleteFolder(folder);
		}
		catch (NoSuchFolderException nsfe) {
			DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				parentFolderId, name);

			DLFileEntryLocalServiceUtil.deleteFileEntry(fileEntry);
		}
	}

	protected Date getCreateDate(String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String name = folderNames[folderNames.length - 1];

		try {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(
				parentFolderId, name);

			return folder.getCreateDate();
		}
		catch (NoSuchFolderException nsfe) {
			//try {
				DLFileEntry fileEntry =
					DLFileEntryLocalServiceUtil.getFileEntry(
						parentFolderId, name);

				return fileEntry.getCreateDate();
			/*}
			catch (NoSuchFileEntryException nsfee) {
				return new Date();
			}*/
		}
	}

	protected Date getModifiedDate(String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String name = folderNames[folderNames.length - 1];

		try {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(
				parentFolderId, name);

			return folder.getModifiedDate();
		}
		catch (NoSuchFolderException nsfe) {
			try {
				DLFileEntry fileEntry =
					DLFileEntryLocalServiceUtil.getFileEntry(
						parentFolderId, name);

				return fileEntry.getModifiedDate();
			}
			catch (NoSuchFileEntryException nsfee) {
				return new Date(0);
			}
		}
	}

	protected String[] getObjects(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < folderNames.length; i++) {
			if (Validator.isNotNull(folderNames[i])) {
				try {
					parentFolder = DLFolderLocalServiceUtil.getFolder(
						parentFolderId, folderNames[i]);
				}
				catch (NoSuchFolderException nsfe) {
					return null;
				}

				parentFolderId = parentFolder.getFolderId();
			}
		}

		List foldersAndFiles = new ArrayList();

		List folders = DLFolderLocalServiceUtil.getFolders(
			groupId, parentFolderId);

		for (int i = 0; i < folders.size(); i++) {
			DLFolder folder = (DLFolder)folders.get(i);

			foldersAndFiles.add(folder.getName());
		}

		List fileEntries = DLFileEntryLocalServiceUtil.getFileEntries(
			parentFolderId);

		for (int i = 0; i < fileEntries.size(); i++) {
			DLFileEntry fileEntry = (DLFileEntry)fileEntries.get(i);

			foldersAndFiles.add(fileEntry.getName());
		}

		return (String[])foldersAndFiles.toArray(new String[0]);
	}

	protected InputStream getResource(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String fileName = folderNames[folderNames.length - 1];

		return DLFileEntryLocalServiceUtil.getFileAsStream(
			companyId, null, parentFolderId, fileName);
	}

	protected long getResourceSize(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String fileName = folderNames[folderNames.length - 1];

		try {
			DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				parentFolderId, fileName);

			return fileEntry.getSize();
		}
		catch (NoSuchFileEntryException nsfee) {
			return 0;
		}
	}

	protected boolean isAvailable(String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < (folderNames.length - 1); i++) {
			if (Validator.isNotNull(folderNames[i])) {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderNames[i]);

				parentFolderId = parentFolder.getFolderId();
			}
		}

		String name = folderNames[folderNames.length - 1];

		try {
			DLFolderLocalServiceUtil.getFolder(parentFolderId, name);

			return true;
		}
		catch (NoSuchFolderException nsfe) {
			try {
				DLFileEntryLocalServiceUtil.getFileEntry(parentFolderId, name);

				return true;
			}
			catch (NoSuchFileEntryException nsfee) {
				return false;
			}
		}
	}

	protected boolean isFolder(String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException {

		DLFolder parentFolder = null;

		String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = StringUtil.split(uri, StringPool.SLASH);

		for (int i = 0; i < folderNames.length; i++) {
			if (Validator.isNotNull(folderNames[i])) {
				try {
					parentFolder = DLFolderLocalServiceUtil.getFolder(
						parentFolderId, folderNames[i]);
				}
				catch (NoSuchFolderException nsfe) {
					return false;
				}

				parentFolderId = parentFolder.getFolderId();
			}
		}

		return true;
	}

	private static Log _log = LogFactory.getLog(WebDAVStorageImpl.class);

	private Principal _principal;
	private Hashtable _params;

}