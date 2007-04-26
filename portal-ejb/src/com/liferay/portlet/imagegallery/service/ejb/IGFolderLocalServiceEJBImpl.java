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

package com.liferay.portlet.imagegallery.service.ejb;

import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="IGFolderLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.imagegallery.service.IGFolderLocalService
 * @see com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil
 * @see com.liferay.portlet.imagegallery.service.ejb.IGFolderLocalServiceEJB
 * @see com.liferay.portlet.imagegallery.service.ejb.IGFolderLocalServiceHome
 * @see com.liferay.portlet.imagegallery.service.impl.IGFolderLocalServiceImpl
 *
 */
public class IGFolderLocalServiceEJBImpl implements IGFolderLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long userId, java.lang.String plid, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().addFolder(userId, plid,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long userId, java.lang.String plid, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().addFolder(userId, plid,
			parentFolderId, name, description, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long userId, java.lang.String plid, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().addFolder(userId, plid,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addFolderResources(java.lang.String folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(java.lang.String folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().addFolderResources(folder,
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().deleteFolder(folderId);
	}

	public void deleteFolder(
		com.liferay.portlet.imagegallery.model.IGFolder folder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().deleteFolders(groupId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().getFolder(folderId);
	}

	public java.util.List getFolders(long groupId)
		throws com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().getFolders(groupId);
	}

	public java.util.List getFolders(long groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().getFolders(groupId,
			parentFolderId);
	}

	public java.util.List getFolders(long groupId,
		java.lang.String parentFolderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().getFolders(groupId,
			parentFolderId, begin, end);
	}

	public int getFoldersCount(long groupId, java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().getFoldersCount(groupId,
			parentFolderId);
	}

	public void getSubfolderIds(java.util.List folderIds, long groupId,
		java.lang.String folderId) throws com.liferay.portal.SystemException {
		IGFolderLocalServiceFactory.getTxImpl().getSubfolderIds(folderIds,
			groupId, folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		java.lang.String folderId, java.lang.String parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentFolder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGFolderLocalServiceFactory.getTxImpl().updateFolder(folderId,
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