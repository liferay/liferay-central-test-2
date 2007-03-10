/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service;

/**
 * <a href="BookmarksEntryLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface BookmarksEntryLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEntryResources(long folderId, long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEntryResources(long folderId, long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteEntries(long folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteEntry(long entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getEntries(long folderId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getEntriesCount(long folderId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		long entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int getFoldersEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException;

	public java.util.List getGroupEntries(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List getGroupEntries(long groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.SystemException;

	public int getGroupEntriesCount(long groupId, java.lang.String userId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		long entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		java.lang.String companyId, long entryId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}