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

import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="DLFileEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryLocalServiceEJBImpl implements DLFileEntryLocalService,
	SessionBean {
	public static final String CLASS_NAME = DLFileEntryLocalService.class.getName() +
		".transaction";

	public static DLFileEntryLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (DLFileEntryLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] byteArray, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addFileEntry(userId, folderId, name, title,
			description, extraSettings, byteArray, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFileEntryResources(java.lang.String folderId,
		java.lang.String name, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addFileEntryResources(folderId, name,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addFileEntryResources(folder, fileEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteFileEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFileEntries(folderId);
	}

	public void deleteFileEntry(java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(folderId, name);
	}

	public void deleteFileEntry(java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(folderId, name, version);
	}

	public void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteFileEntry(fileEntry);
	}

	public java.io.InputStream getFileAsStream(java.lang.String companyId,
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getFileAsStream(companyId, userId, folderId, name);
	}

	public java.io.InputStream getFileAsStream(java.lang.String companyId,
		java.lang.String userId, java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getFileAsStream(companyId, userId, folderId, name,
			version);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getFileEntry(folderId, name);
	}

	public java.util.List getFileEntries(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntries(folderId);
	}

	public java.util.List getFileEntries(java.lang.String folderId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getFileEntries(folderId, begin, end);
	}

	public java.util.List getFileEntriesAndShortcuts(
		java.lang.String folderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntriesAndShortcuts(folderId, begin, end);
	}

	public java.util.List getFileEntriesAndShortcuts(java.util.List folderIds,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getService().getFileEntriesAndShortcuts(folderIds, begin, end);
	}

	public int getFileEntriesAndShortcutsCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntriesAndShortcutsCount(folderId);
	}

	public int getFileEntriesAndShortcutsCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntriesAndShortcutsCount(folderIds);
	}

	public int getFileEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getService().getFileEntriesCount(folderId);
	}

	public int getFoldersFileEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		return getService().getFoldersFileEntriesCount(folderIds);
	}

	public java.util.List getGroupFileEntries(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntries(groupId, begin, end);
	}

	public java.util.List getGroupFileEntries(java.lang.String groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntries(groupId, userId, begin, end);
	}

	public int getGroupFileEntriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(java.lang.String groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		return getService().getGroupFileEntriesCount(groupId, userId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] byteArray)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateFileEntry(userId, folderId, newFolderId,
			name, sourceFileName, title, description, extraSettings, byteArray);
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