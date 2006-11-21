/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.bookmarks.service.spring.BookmarksEntryLocalService;
import com.liferay.portlet.bookmarks.service.spring.BookmarksEntryLocalServiceFactory;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="BookmarksEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryLocalServiceEJBImpl
	implements BookmarksEntryLocalService, SessionBean {
	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().addEntry(userId,
			folderId, name, url, comments, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().addEntry(userId,
			folderId, name, url, comments, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().addEntry(userId,
			folderId, name, url, comments, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addEntryResources(java.lang.String folderId,
		java.lang.String entryId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().addEntryResources(folderId,
			entryId, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().addEntryResources(folder,
			entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(java.lang.String folderId,
		java.lang.String entryId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().addEntryResources(folderId,
			entryId, communityPermissions, guestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().addEntryResources(folder,
			entry, communityPermissions, guestPermissions);
	}

	public void deleteEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().deleteEntries(folderId);
	}

	public void deleteEntry(java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().deleteEntry(entryId);
	}

	public void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksEntryLocalServiceFactory.getTxImpl().deleteEntry(entry);
	}

	public java.util.List getEntries(java.lang.String folderId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().getEntries(folderId,
			begin, end);
	}

	public int getEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().getEntriesCount(folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().getEntry(entryId);
	}

	public int getFoldersEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl()
												.getFoldersEntriesCount(folderIds);
	}

	public java.util.List getGroupEntries(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().getGroupEntries(groupId,
			begin, end);
	}

	public java.util.List getGroupEntries(java.lang.String groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().getGroupEntries(groupId,
			userId, begin, end);
	}

	public int getGroupEntriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl()
												.getGroupEntriesCount(groupId);
	}

	public int getGroupEntriesCount(java.lang.String groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl()
												.getGroupEntriesCount(groupId,
			userId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().openEntry(entryId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		java.lang.String companyId, java.lang.String entryId,
		java.lang.String folderId, java.lang.String name, java.lang.String url,
		java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksEntryLocalServiceFactory.getTxImpl().updateEntry(companyId,
			entryId, folderId, name, url, comments);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}