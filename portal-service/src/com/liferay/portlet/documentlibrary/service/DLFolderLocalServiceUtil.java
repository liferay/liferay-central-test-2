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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="DLFolderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link DLFolderLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFolderLocalService
 * @generated
 */
public class DLFolderLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFolder addDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException {
		return getService().addDLFolder(dlFolder);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder createDLFolder(
		long folderId) {
		return getService().createDLFolder(folderId);
	}

	public static void deleteDLFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteDLFolder(folderId);
	}

	public static void deleteDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException {
		getService().deleteDLFolder(dlFolder);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDLFolder(folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getDLFolders(start, end);
	}

	public static int getDLFoldersCount()
		throws com.liferay.portal.SystemException {
		return getService().getDLFoldersCount();
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException {
		return getService().updateDLFolder(dlFolder);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateDLFolder(dlFolder, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		java.lang.String uuid, long userId, long groupId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFolder(uuid, userId, groupId, parentFolderId, name,
			description, serviceContext);
	}

	public static void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFolderResources(folder, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public static void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFolderResources(folderId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFolderResources(folderId, communityPermissions, guestPermissions);
	}

	public static void deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder folder)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFolder(folder);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFolders(groupId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyFolders(companyId, start, end);
	}

	public static int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyFoldersCount(companyId);
	}

	public static java.util.List<Object> getFileEntriesAndFileShortcuts(
		long groupId, java.util.List<Long> folderIds, int status, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderIds, status,
			start, end);
	}

	public static java.util.List<Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderId, status,
			start, end);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		java.util.List<Long> folderIds, int status)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderIds,
			status);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status) throws com.liferay.portal.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderId,
			status);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long companyId) throws com.liferay.portal.SystemException {
		return getService().getFolders(companyId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static java.util.List<Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, java.util.List<Long> folderIds, int status, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderIds, status, start, end);
	}

	public static java.util.List<Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			status, start, end);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, java.util.List<Long> folderIds, int status)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderIds, status);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static void getSubfolderIds(java.util.List<Long> folderIds,
		long groupId, long folderId) throws com.liferay.portal.SystemException {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			serviceContext);
	}

	public static DLFolderLocalService getService() {
		if (_service == null) {
			_service = (DLFolderLocalService)PortalBeanLocatorUtil.locate(DLFolderLocalService.class.getName());
		}

		return _service;
	}

	public void setService(DLFolderLocalService service) {
		_service = service;
	}

	private static DLFolderLocalService _service;
}