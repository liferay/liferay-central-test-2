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

package com.liferay.portlet.documentlibrary.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.documentlibrary.service.spring.DLFolderLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="DLFolderLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFolderLocalServiceEJBImpl implements DLFolderLocalService,
	SessionBean {
	public static final String CLASS_NAME = DLFolderLocalService.class.getName() +
		".transaction";

	public static DLFolderLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (DLFolderLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addFolder(userId, plid, parentFolderId, name,
			description, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addFolder(userId, plid, parentFolderId, name,
			description, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addFolder(userId, plid, parentFolderId, name,
			description, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(java.lang.String folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addFolderResources(folderId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addFolderResources(folder, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFolderResources(java.lang.String folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addFolderResources(folderId, communityPermissions,
			guestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addFolderResources(folder, communityPermissions,
			guestPermissions);
	}

	public void deleteFolder(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFolder(folderId);
	}

	public void deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder folder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFolder(folder);
	}

	public void deleteFolders(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFolders(groupId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getFolder(folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		java.lang.String parentFolderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getFolder(parentFolderId, name);
	}

	public java.util.List getFolders(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getService().getFolders(companyId);
	}

	public java.util.List getFolders(java.lang.String groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public java.util.List getFolders(java.lang.String groupId,
		java.lang.String parentFolderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getFolders(groupId, parentFolderId, begin, end);
	}

	public int getFoldersCount(java.lang.String groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List folderIds,
		java.lang.String groupId, java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.util.lucene.Hits search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String[] folderIds,
		java.lang.String keywords)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().search(companyId, groupId, folderIds, keywords);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		java.lang.String companyId, java.lang.String folderId,
		java.lang.String parentFolderId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateFolder(companyId, folderId, parentFolderId,
			name, description);
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