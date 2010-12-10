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

package com.liferay.portlet.documentlibrary.sharepoint;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.sharepoint.BaseSharepointStorageImpl;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portal.sharepoint.Tree;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Bruno Farache
 */
public class DLSharepointStorageImpl extends BaseSharepointStorageImpl {

	public void addDocumentElements(
			SharepointRequest sharepointRequest, Element element)
		throws Exception {

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		List<DLFileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
			groupId, parentFolderId);

		for (DLFileEntry fileEntry : fileEntries) {
			String documentPath = parentFolderPath.concat(
				StringPool.SLASH).concat(fileEntry.getTitle());

			addDocumentElement(
				element, documentPath, fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(), fileEntry.getUserName());
		}
	}

	public void createFolder(SharepointRequest sharepointRequest)
		throws Exception {

		String folderPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(folderPath);

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		String folderName = getResourceName(folderPath);
		String description = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		DLAppServiceUtil.addFolder(
			groupId, parentFolderId, folderName, description, serviceContext);
	}

	public InputStream getDocumentInputStream(
			SharepointRequest sharepointRequest)
		throws Exception {

		DLFileEntry fileEntry = getFileEntry(sharepointRequest);

		return fileEntry.getContentStream();
	}

	public Tree getDocumentTree(SharepointRequest sharepointRequest)
		throws Exception {

		String documentPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(documentPath);

		DLFileEntry fileEntry = getFileEntry(sharepointRequest);

		return getFileEntryTree(fileEntry, parentFolderPath);
	}

	public Tree getDocumentsTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree documentsTree = new Tree();

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			List<DLFileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
				groupId, parentFolderId);

			for (DLFileEntry fileEntry : fileEntries) {
				documentsTree.addChild(
					getFileEntryTree(fileEntry, parentFolderPath));
			}
		}

		return documentsTree;
	}

	public Tree getFolderTree(SharepointRequest sharepointRequest)
		throws Exception {

		String folderPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(folderPath);

		long groupId = SharepointUtil.getGroupId(folderPath);
		long folderId = getLastFolderId(
			groupId, folderPath, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DLFolder folder = DLAppServiceUtil.getFolder(folderId);

		return getFolderTree(folder, parentFolderPath);
	}

	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree foldersTree = new Tree();

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		List<DLFolder> folders = DLAppServiceUtil.getFolders(
			groupId, parentFolderId);

		for (DLFolder folder : folders) {
			foldersTree.addChild(getFolderTree(folder, parentFolderPath));
		}

		foldersTree.addChild(getFolderTree(parentFolderPath));

		return foldersTree;
	}

	public void getParentFolderIds(
			long groupId, String path, List<Long> folderIds)
		throws Exception {

		String[] pathArray = SharepointUtil.getPathArray(path);

		if (pathArray.length == 0) {
			return;
		}

		long parentFolderId = folderIds.get(folderIds.size() - 1);
		DLFolder folder = DLAppServiceUtil.getFolder(
			groupId, parentFolderId, pathArray[0]);

		folderIds.add(folder.getFolderId());

		if (pathArray.length > 1) {
			path = removeFoldersFromPath(path, 1);

			getParentFolderIds(groupId, path, folderIds);
		}
	}

	public Tree[] moveDocument(SharepointRequest sharepointRequest)
		throws Exception {

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);

		DLFolder folder = null;
		DLFileEntry fileEntry = null;

		try {
			long parentFolderId = getLastFolderId(
				groupId, parentFolderPath,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			folder = DLAppServiceUtil.getFolder(parentFolderId);
		}
		catch (Exception e1) {
			if (e1 instanceof NoSuchFolderException) {
				try {
					fileEntry = getFileEntry(sharepointRequest);
				}
				catch (Exception e2) {
				}
			}
		}

		Tree movedDocsTree = new Tree();
		Tree movedDirsTree = new Tree();

		String newPath = sharepointRequest.getParameterValue("newUrl");
		String newParentFolderPath = getParentFolderPath(newPath);

		long newGroupId = SharepointUtil.getGroupId(newParentFolderPath);

		long newParentFolderId = getLastFolderId(
			newGroupId, newParentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String newName = getResourceName(newPath);

		ServiceContext serviceContext = new ServiceContext();

		if (fileEntry != null) {
			long fileEntryId = fileEntry.getFileEntryId();

			long folderId = fileEntry.getFolderId();
			String description = fileEntry.getDescription();
			String changeLog = StringPool.BLANK;
			String extraSettings = fileEntry.getExtraSettings();

			InputStream is = fileEntry.getContentStream();

			byte[] bytes = FileUtil.getBytes(is);

			String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			serviceContext.setAssetTagNames(assetTagNames);

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntryId, newName, newName, description, changeLog, false,
				extraSettings, bytes, serviceContext);

			if (folderId != newParentFolderId) {
				fileEntry = DLAppServiceUtil.moveFileEntry(
					fileEntryId, newParentFolderId, serviceContext);
			}

			Tree documentTree = getFileEntryTree(
				fileEntry, newParentFolderPath);

			movedDocsTree.addChild(documentTree);
		}
		else if (folder != null) {
			long folderId = folder.getFolderId();
			String description = folder.getDescription();

			folder = DLAppServiceUtil.updateFolder(
				folderId, newParentFolderId, newName, description,
				serviceContext);

			Tree folderTree = getFolderTree(folder, newParentFolderPath);

			movedDirsTree.addChild(folderTree);
		}

		return new Tree[] {movedDocsTree, movedDirsTree};
	}

	public void putDocument(SharepointRequest sharepointRequest)
		throws Exception {

		String documentPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(documentPath);

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		String title = getResourceName(documentPath);
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		String extraSettings = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DLFileEntry fileEntry = getFileEntry(sharepointRequest);

			long fileEntryId = fileEntry.getFileEntryId();

			description = fileEntry.getDescription();
			extraSettings = fileEntry.getExtraSettings();

			String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			serviceContext.setAssetTagNames(assetTagNames);

			DLAppServiceUtil.updateFileEntry(
				fileEntryId, title, title, description, changeLog, false,
				extraSettings, sharepointRequest.getBytes(), serviceContext);
		}
		catch (NoSuchFileEntryException nsfee) {
			File file = FileUtil.createTempFile(FileUtil.getExtension(title));

			FileUtil.write(file, sharepointRequest.getBytes());

			DLAppServiceUtil.addFileEntry(
				groupId, parentFolderId, title, description, changeLog,
				extraSettings, file, serviceContext);
		}
	}

	public Tree[] removeDocument(SharepointRequest sharepointRequest) {
		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);

		DLFolder folder = null;
		DLFileEntry fileEntry = null;

		try {
			long parentFolderId = getLastFolderId(
				groupId, parentFolderPath,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			folder = DLAppServiceUtil.getFolder(parentFolderId);
		}
		catch (Exception e1) {
			if (e1 instanceof NoSuchFolderException) {
				try {
					fileEntry = getFileEntry(sharepointRequest);
				}
				catch (Exception e2) {
				}
			}
		}

		Tree documentTree = new Tree();

		Tree removedDocsTree = new Tree();
		Tree failedDocsTree = new Tree();

		Tree folderTree = new Tree();

		Tree removedDirsTree = new Tree();
		Tree failedDirsTree = new Tree();

		if (fileEntry != null) {
			try {
				documentTree = getFileEntryTree(fileEntry, parentFolderPath);

				DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

				removedDocsTree.addChild(documentTree);
			}
			catch (Exception e1) {
				try {
					failedDocsTree.addChild(documentTree);
				}
				catch (Exception e2) {
				}
			}
		}
		else if (folder != null) {
			try {
				folderTree = getFolderTree(folder, parentFolderPath);

				DLAppServiceUtil.deleteFolder(folder.getFolderId());

				removedDirsTree.addChild(folderTree);
			}
			catch (Exception e1) {
				try {
					failedDirsTree.addChild(folderTree);
				}
				catch (Exception e2) {
				}
			}
		}

		return new Tree[] {
			removedDocsTree, removedDirsTree, failedDocsTree, failedDirsTree};
	}

	protected Tree getFolderTree(DLFolder folder, String parentFolderPath) {
		String folderPath = parentFolderPath.concat(StringPool.SLASH).concat(
			folder.getName());

		return getFolderTree(
			folderPath, folder.getCreateDate(), folder.getModifiedDate(),
			folder.getLastPostDate());
	}

	protected DLFileEntry getFileEntry(SharepointRequest sharepointRequest)
		throws Exception {

		String documentPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(documentPath);

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		String title = getResourceName(documentPath);

		return DLAppServiceUtil.getFileEntryByTitle(
			groupId, parentFolderId, title);
	}

	protected Tree getFileEntryTree(
		DLFileEntry fileEntry, String parentFolderPath) {

		String documentPath = parentFolderPath.concat(StringPool.SLASH).concat(
			fileEntry.getTitle());

		return getDocumentTree(
			documentPath, fileEntry.getCreateDate(),
			fileEntry.getModifiedDate(), fileEntry.getSize(),
			fileEntry.getUserName(), fileEntry.getVersion());
	}

}