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

package com.liferay.portlet.bookmarks.service.spring;

/**
 * <a href="BookmarksEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryLocalServiceUtil {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.addEntry(userId, folderId, name,
				url, comments, addCommunityPermissions, addGuestPermissions);
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

	public static void addEntryResources(java.lang.String folderId,
		java.lang.String entryId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();
			bookmarksEntryLocalService.addEntryResources(folderId, entryId,
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

	public static void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();
			bookmarksEntryLocalService.addEntryResources(folder, entry,
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

	public static void deleteEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();
			bookmarksEntryLocalService.deleteEntries(folderId);
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

	public static void deleteEntry(java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();
			bookmarksEntryLocalService.deleteEntry(entryId);
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

	public static void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();
			bookmarksEntryLocalService.deleteEntry(entry);
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

	public static java.util.List getEntries(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.getEntries(folderId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.getEntriesCount(folderId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.getEntry(entryId);
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

	public static int getFoldersEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.getFoldersEntriesCount(folderIds);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.openEntry(entryId);
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

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		java.lang.String companyId, java.lang.String entryId,
		java.lang.String folderId, java.lang.String name, java.lang.String url,
		java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BookmarksEntryLocalService bookmarksEntryLocalService = BookmarksEntryLocalServiceFactory.getService();

			return bookmarksEntryLocalService.updateEntry(companyId, entryId,
				folderId, name, url, comments);
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