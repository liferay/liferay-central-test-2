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

package com.liferay.portlet.imagegallery.service;

/**
 * <p>
 * This class is a wrapper for {@link IGFolderService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolderService
 * @generated
 */
public class IGFolderServiceWrapper implements IGFolderService {
	public IGFolderServiceWrapper(IGFolderService igFolderService) {
		_igFolderService = igFolderService;
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.addFolder(parentFolderId, name, description,
			serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder copyFolder(
		long sourceFolderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.copyFolder(sourceFolderId, parentFolderId,
			name, description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderService.deleteFolder(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.getFolder(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.getFolder(groupId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.getFolders(groupId, parentFolderId, start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderService.updateFolder(folderId, parentFolderId, name,
			description, mergeWithParentFolder, serviceContext);
	}

	public IGFolderService getWrappedIGFolderService() {
		return _igFolderService;
	}

	public void setWrappedIGFolderService(IGFolderService igFolderService) {
		_igFolderService = igFolderService;
	}

	private IGFolderService _igFolderService;
}