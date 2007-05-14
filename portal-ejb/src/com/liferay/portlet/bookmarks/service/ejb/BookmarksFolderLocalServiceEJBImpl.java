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

package com.liferay.portlet.bookmarks.service.ejb;

import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="BookmarksFolderLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.bookmarks.service.BookmarksFolderLocalService
 * @see com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil
 * @see com.liferay.portlet.bookmarks.service.ejb.BookmarksFolderLocalServiceEJB
 * @see com.liferay.portlet.bookmarks.service.ejb.BookmarksFolderLocalServiceHome
 * @see com.liferay.portlet.bookmarks.service.impl.BookmarksFolderLocalServiceImpl
 *
 */
public class BookmarksFolderLocalServiceEJBImpl
	implements BookmarksFolderLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long userId, long plid, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().addFolder(userId,
			plid, parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long userId, long plid, long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().addFolder(userId,
			plid, parentFolderId, name, description, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long userId, long plid, long parentFolderId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().addFolder(userId,
			plid, parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().addFolderResources(folder,
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().deleteFolder(folderId);
	}

	public void deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().deleteFolders(groupId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().getFolder(folderId);
	}

	public java.util.List getFolders(long groupId, long parentFolderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().getFolders(groupId,
			parentFolderId, begin, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().getFoldersCount(groupId,
			parentFolderId);
	}

	public void getSubfolderIds(java.util.List folderIds, long groupId,
		long folderId) throws com.liferay.portal.SystemException {
		BookmarksFolderLocalServiceFactory.getTxImpl().getSubfolderIds(folderIds,
			groupId, folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BookmarksFolderLocalServiceFactory.getTxImpl().updateFolder(folderId,
			parentFolderId, name, description, mergeWithParentFolder);
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