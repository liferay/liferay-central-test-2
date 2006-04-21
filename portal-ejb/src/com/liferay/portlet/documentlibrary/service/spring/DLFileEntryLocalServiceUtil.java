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

package com.liferay.portlet.documentlibrary.service.spring;

/**
 * <a href="DLFileEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, byte[] byteArray,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.addFileEntry(userId, folderId, name,
				title, description, byteArray, addCommunityPermissions,
				addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addFileEntryResources(java.lang.String folderId,
		java.lang.String name, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
			dlFileEntryLocalService.addFileEntryResources(folderId, name,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
			dlFileEntryLocalService.addFileEntryResources(folder, fileEntry,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFileEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
			dlFileEntryLocalService.deleteFileEntries(folderId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFileEntry(java.lang.String folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
			dlFileEntryLocalService.deleteFileEntry(folderId, name);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFileEntry(java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
			dlFileEntryLocalService.deleteFileEntry(folderId, name, version);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();
			dlFileEntryLocalService.deleteFileEntry(fileEntry);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.io.InputStream getFileAsStream(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.getFileAsStream(companyId, userId,
				folderId, name);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.io.InputStream getFileAsStream(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String folderId, java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.getFileAsStream(companyId, userId,
				folderId, name, version);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.getFileEntry(folderId, name);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getFileEntries(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.getFileEntries(folderId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getFileEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.getFileEntriesCount(folderId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getFoldersFileEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.getFoldersFileEntriesCount(folderIds);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.updateFileEntry(folderId, name,
				title, description);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String sourceFileName, byte[] byteArray)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			DLFileEntryLocalService dlFileEntryLocalService = DLFileEntryLocalServiceFactory.getService();

			return dlFileEntryLocalService.updateFileEntry(userId, folderId,
				name, sourceFileName, byteArray);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}