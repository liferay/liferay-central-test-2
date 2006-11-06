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

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="DLFileEntryServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryServiceEJBImpl implements DLFileEntryService,
	SessionBean {
	public static final String CLASS_NAME = DLFileEntryService.class.getName() +
		".transaction";

	public static DLFileEntryService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (DLFileEntryService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, byte[] byteArray,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addFileEntry(folderId, name, title, description,
			extraSettings, byteArray, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteFileEntry(java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteFileEntry(folderId, name);
	}

	public void deleteFileEntry(java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteFileEntry(folderId, name, version);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getFileEntry(folderId, name);
	}

	public void lockFileEntry(java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().lockFileEntry(folderId, name);
	}

	public void unlockFileEntry(java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().unlockFileEntry(folderId, name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		java.lang.String folderId, java.lang.String newFolderId,
		java.lang.String name, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String extraSettings, byte[] byteArray)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException, 
			com.liferay.portal.SystemException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateFileEntry(folderId, newFolderId, name,
			sourceFileName, title, description, extraSettings, byteArray);
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