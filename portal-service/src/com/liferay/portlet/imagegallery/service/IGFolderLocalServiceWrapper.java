/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.addIGFolder(igFolder);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder createIGFolder(
		long folderId) {
		return _igFolderLocalService.createIGFolder(folderId);
	}

	public void deleteIGFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.deleteIGFolder(folderId);
	}

	public void deleteIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.SystemException {
		_igFolderLocalService.deleteIGFolder(igFolder);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getIGFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igFolderLocalService.getIGFolder(folderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getIGFolders(
		int start, int end) throws com.liferay.portal.SystemException {
		return _igFolderLocalService.getIGFolders(start, end);
	}

	public int getIGFoldersCount() throws com.liferay.portal.SystemException {
		return _igFolderLocalService.getIGFoldersCount();
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.updateIGFolder(igFolder);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.updateIGFolder(igFolder, merge);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		java.lang.String uuid, long userId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igFolderLocalService.addFolder(uuid, userId, parentFolderId,
			name, description, serviceContext);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.addFolderResources(folder, communityPermissions,
			guestPermissions);
	}

	public void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(
		com.liferay.portlet.imagegallery.model.IGFolder folder)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igFolderLocalService.deleteFolders(groupId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igFolderLocalService.getFolder(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId) throws com.liferay.portal.SystemException {
		return _igFolderLocalService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.getFolders(groupId, parentFolderId, start,
			end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return _igFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<Long> folderIds, long groupId,
		long folderId) throws com.liferay.portal.SystemException {
		_igFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		_igFolderLocalService.reIndex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, long[] folderIds, java.lang.String keywords,
		int start, int end) throws com.liferay.portal.SystemException {
		return _igFolderLocalService.search(companyId, groupId, userId,
			folderIds, keywords, start, end);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igFolderLocalService.updateFolder(folderId, parentFolderId,
			name, description, mergeWithParentFolder, serviceContext);
	}

	public IGFolderLocalService getWrappedIGFolderLocalService() {
		return _igFolderLocalService;
	}

	private IGFolderLocalService _igFolderLocalService;
}