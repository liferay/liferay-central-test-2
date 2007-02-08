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

import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SCProductVersionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductVersionLocalServiceEJBImpl
	implements SCProductVersionLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addProductVersionResources(long productEntryId,
		long productVersionId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntryId,
			productVersionId, addCommunityPermissions, addGuestPermissions);
	}

	public void addProductVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion productVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntry,
			productVersion, addCommunityPermissions, addGuestPermissions);
	}

	public void addProductVersionResources(long productEntryId,
		long productVersionId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntryId,
			productVersionId, communityPermissions, guestPermissions);
	}

	public void addProductVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion productVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntry,
			productVersion, communityPermissions, guestPermissions);
	}

	public void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl().deleteProductVersion(productVersionId);
	}

	public void deleteProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion productVersion)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl().deleteProductVersion(productVersion);
	}

	public void deleteProductVersions(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductVersionLocalServiceFactory.getTxImpl().deleteProductVersions(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .getProductVersion(productVersionId);
	}

	public java.util.List getProductVersions(long productEntryId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .getProductVersions(productEntryId,
			begin, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .getProductVersionsCount(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductVersionLocalServiceFactory.getTxImpl()
												  .updateProductVersion(productVersionId,
			version, changeLog, downloadPageURL, directDownloadURL,
			repoStoreArtifact, frameworkVersionIds);
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