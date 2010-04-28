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

package com.liferay.portlet.imagegallery.service;


/**
 * <a href="IGFolderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link IGFolderLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolderLocalService
 * @generated
 */
public class IGFolderLocalServiceWrapper implements IGFolderLocalService {
	public IGFolderLocalServiceWrapper(
		IGFolderLocalService igFolderLocalService) {
		_igFolderLocalService = igFolderLocalService;
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.addIGFolder(igFolder);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder createIGFolder(
		long folderId) {
		return _igFolderLocalService.createIGFolder(folderId);
	}

	public void deleteIGFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteIGFolder(folderId);
	}

	public void deleteIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteIGFolder(igFolder);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getIGFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFolder(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getIGFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFolderByUuidAndGroupId(uuid, groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getIGFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFolders(start, end);
	}

	public int getIGFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFoldersCount();
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.updateIGFolder(igFolder);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.updateIGFolder(igFolder, merge);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		java.lang.String uuid, long userId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.addFolder(uuid, userId, parentFolderId,
			name, description, serviceContext);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folder, communityPermissions,
			guestPermissions);
	}

	public void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(
		com.liferay.portlet.imagegallery.model.IGFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getCompanyFolders(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getCompanyFoldersCount(companyId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolder(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolders(groupId, parentFolderId, start,
			end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<Long> folderIds, long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.updateFolder(folderId, parentFolderId,
			name, description, mergeWithParentFolder, serviceContext);
	}

	public IGFolderLocalService getWrappedIGFolderLocalService() {
		return _igFolderLocalService;
	}

	private IGFolderLocalService _igFolderLocalService;
}