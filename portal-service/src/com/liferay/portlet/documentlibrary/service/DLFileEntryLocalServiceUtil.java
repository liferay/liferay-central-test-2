/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="DLFileEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService
 * @see com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceFactory
 *
 */
public class DLFileEntryLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addDLFileEntry(dlFileEntry);
	}

	public static void deleteDLFileEntry(long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.deleteDLFileEntry(fileEntryId);
	}

	public static void deleteDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.deleteDLFileEntry(dlFileEntry);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getDLFileEntry(fileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, tagsEntries, extraSettings, file,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		byte[] bytes, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, tagsEntries, extraSettings, bytes,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, byte[] bytes,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(uuid, userId, folderId,
			name, title, description, tagsEntries, extraSettings, bytes,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, tagsEntries, extraSettings, file,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		byte[] bytes, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, tagsEntries, extraSettings, bytes,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
			title, description, tagsEntries, extraSettings, file,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, byte[] bytes,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(uuid, userId, folderId,
			name, title, description, tagsEntries, extraSettings, bytes,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String uuid, long userId, long folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, java.io.InputStream is, long size,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addFileEntry(uuid, userId, folderId,
			name, title, description, tagsEntries, extraSettings, is, size,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addFileEntryResources(long folderId,
		java.lang.String name, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.addFileEntryResources(folderId, name,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.addFileEntryResources(folder, fileEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFileEntryResources(long folderId,
		java.lang.String name, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.addFileEntryResources(folderId, name,
			communityPermissions, guestPermissions);
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.addFileEntryResources(folder, fileEntry,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addOrOverwriteFileEntry(
		long userId, long folderId, java.lang.String name,
		java.lang.String sourceName, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, java.io.File file,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.addOrOverwriteFileEntry(userId,
			folderId, name, sourceName, title, description, tagsEntries,
			extraSettings, file, addCommunityPermissions, addGuestPermissions);
	}

	public static void deleteFileEntries(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.deleteFileEntries(folderId);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.deleteFileEntry(folderId, name);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.deleteFileEntry(folderId, name, version);
	}

	public static void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.deleteFileEntry(fileEntry);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getCompanyFileEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getCompanyFileEntries(companyId, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getCompanyFileEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getCompanyFileEntries(companyId, start,
			end, obc);
	}

	public static int getCompanyFileEntriesCount(long companyId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getCompanyFileEntriesCount(companyId);
	}

	public static java.io.InputStream getFileAsStream(long companyId,
		long userId, long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileAsStream(companyId, userId,
			folderId, name);
	}

	public static java.io.InputStream getFileAsStream(long companyId,
		long userId, long folderId, java.lang.String name, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileAsStream(companyId, userId,
			folderId, name, version);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId) throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntries(folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntries(folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntries(folderId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntriesAndShortcuts(
		long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcuts(folderId,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntriesAndShortcuts(
		java.util.List<Long> folderIds, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcuts(folderIds,
			start, end);
	}

	public static int getFileEntriesAndShortcutsCount(long folderId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcutsCount(folderId);
	}

	public static int getFileEntriesAndShortcutsCount(
		java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesAndShortcutsCount(folderIds);
	}

	public static int getFileEntriesCount(long folderId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntriesCount(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntry(fileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntry(folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFileEntryByTitle(folderId,
			titleWithExtension);
	}

	public static int getFoldersFileEntriesCount(java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getFoldersFileEntriesCount(folderIds);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntries(groupId, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			start, end, obc);
	}

	public static int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntriesCount(groupId);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getGroupFileEntriesCount(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries()
		throws com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.getNoAssetFileEntries();
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.updateFileEntry(userId, folderId,
			newFolderId, name, sourceFileName, title, description, tagsEntries,
			extraSettings, file);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.updateFileEntry(userId, folderId,
			newFolderId, name, sourceFileName, title, description, tagsEntries,
			extraSettings, bytes);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, java.io.InputStream is, long size)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		return dlFileEntryLocalService.updateFileEntry(userId, folderId,
			newFolderId, name, sourceFileName, title, description, tagsEntries,
			extraSettings, is, size);
	}

	public static void updateTagsAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

		dlFileEntryLocalService.updateTagsAsset(userId, fileEntry, tagsEntries);
	}
}