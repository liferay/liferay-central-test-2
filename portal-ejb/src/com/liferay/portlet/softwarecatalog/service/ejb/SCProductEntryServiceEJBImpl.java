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

package com.liferay.portlet.softwarecatalog.service.ejb;

import com.liferay.portal.service.impl.PrincipalSessionBean;

import com.liferay.portlet.softwarecatalog.service.SCProductEntryService;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SCProductEntryServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductEntryServiceEJBImpl implements SCProductEntryService,
	SessionBean {
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		java.lang.String plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.Map images, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SCProductEntryServiceFactory.getTxImpl().addProductEntry(plid,
			name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, images,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		java.lang.String plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.Map images, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SCProductEntryServiceFactory.getTxImpl().addProductEntry(plid,
			name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, images,
			communityPermissions, guestPermissions);
	}

	public void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		SCProductEntryServiceFactory.getTxImpl().deleteProductEntry(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SCProductEntryServiceFactory.getTxImpl().getProductEntry(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds, java.util.Map images)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SCProductEntryServiceFactory.getTxImpl().updateProductEntry(productEntryId,
			name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, images);
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