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

package com.liferay.portlet.documentlibrary.service.ejb;

import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="DLFileShortcutLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService
 * @see com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.ejb.DLFileShortcutLocalServiceEJB
 * @see com.liferay.portlet.documentlibrary.service.ejb.DLFileShortcutLocalServiceHome
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl
 *
 */
public class DLFileShortcutLocalServiceEJBImpl
	implements DLFileShortcutLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, java.lang.String folderId, java.lang.String toFolderId,
		java.lang.String toName, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcut(userId,
			folderId, toFolderId, toName, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, java.lang.String folderId, java.lang.String toFolderId,
		java.lang.String toName, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcut(userId,
			folderId, toFolderId, toName, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, java.lang.String folderId, java.lang.String toFolderId,
		java.lang.String toName, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcut(userId,
			folderId, toFolderId, toName, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addFileShortcutResources(long fileShortcutId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcutResources(fileShortcutId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcutResources(folder,
			fileShortcut, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(long fileShortcutId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcutResources(fileShortcutId,
			communityPermissions, guestPermissions);
	}

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().addFileShortcutResources(folder,
			fileShortcut, communityPermissions, guestPermissions);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().deleteFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().deleteFileShortcut(fileShortcut);
	}

	public void deleteFileShortcuts(java.lang.String toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().deleteFileShortcuts(toFolderId,
			toName);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().getFileShortcut(fileShortcutId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, java.lang.String folderId,
		java.lang.String toFolderId, java.lang.String toName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileShortcutLocalServiceFactory.getTxImpl().updateFileShortcut(userId,
			fileShortcutId, folderId, toFolderId, toName);
	}

	public void updateFileShortcuts(java.lang.String oldToFolderId,
		java.lang.String oldToName, java.lang.String newToFolderId,
		java.lang.String newToName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileShortcutLocalServiceFactory.getTxImpl().updateFileShortcuts(oldToFolderId,
			oldToName, newToFolderId, newToName);
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