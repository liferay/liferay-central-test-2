/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileShortcutLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileShortcutLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutLocalService
 * @generated
 */
public class DLFileShortcutLocalServiceWrapper
	implements DLFileShortcutLocalService {
	public DLFileShortcutLocalServiceWrapper(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.addDLFileShortcut(dlFileShortcut);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut createDLFileShortcut(
		long fileShortcutId) {
		return _dlFileShortcutLocalService.createDLFileShortcut(fileShortcutId);
	}

	public void deleteDLFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcutId);
	}

	public void deleteDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteDLFileShortcut(dlFileShortcut);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcut(fileShortcutId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcuts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcuts(start, end);
	}

	public int getDLFileShortcutsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcutsCount();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.updateDLFileShortcut(dlFileShortcut);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.updateDLFileShortcut(dlFileShortcut,
			merge);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		java.lang.String uuid, long userId, long groupId, long folderId,
		long toFolderId, java.lang.String toName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.addFileShortcut(uuid, userId,
			groupId, folderId, toFolderId, toName, serviceContext);
	}

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcut,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcut,
			communityPermissions, guestPermissions);
	}

	public void addFileShortcutResources(long fileShortcutId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(long fileShortcutId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			communityPermissions, guestPermissions);
	}

	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcuts(long groupId, long toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteFileShortcuts(groupId, toFolderId,
			toName);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.updateAsset(userId, fileShortcut,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFolderId,
		java.lang.String toName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.updateFileShortcut(userId,
			fileShortcutId, folderId, toFolderId, toName, serviceContext);
	}

	public void updateFileShortcuts(long groupId, long oldToFolderId,
		java.lang.String oldToName, long newToFolderId,
		java.lang.String newToName)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.updateFileShortcuts(groupId, oldToFolderId,
			oldToName, newToFolderId, newToName);
	}

	public DLFileShortcutLocalService getWrappedDLFileShortcutLocalService() {
		return _dlFileShortcutLocalService;
	}

	private DLFileShortcutLocalService _dlFileShortcutLocalService;
}