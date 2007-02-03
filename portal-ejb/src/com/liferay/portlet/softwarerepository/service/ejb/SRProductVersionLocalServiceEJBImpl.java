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

package com.liferay.portlet.softwarerepository.service.ejb;

import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalService;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SRProductVersionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductVersionLocalServiceEJBImpl
	implements SRProductVersionLocalService, SessionBean {
	public com.liferay.portlet.softwarerepository.model.SRProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
												  .addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
												  .addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
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
		SRProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntryId,
			productVersionId, addCommunityPermissions, addGuestPermissions);
	}

	public void addProductVersionResources(
		com.liferay.portlet.softwarerepository.model.SRProductEntry productEntry,
		com.liferay.portlet.softwarerepository.model.SRProductVersion productVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntry,
			productVersion, addCommunityPermissions, addGuestPermissions);
	}

	public void addProductVersionResources(long productEntryId,
		long productVersionId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntryId,
			productVersionId, communityPermissions, guestPermissions);
	}

	public void addProductVersionResources(
		com.liferay.portlet.softwarerepository.model.SRProductEntry productEntry,
		com.liferay.portlet.softwarerepository.model.SRProductVersion productVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalServiceFactory.getTxImpl()
										   .addProductVersionResources(productEntry,
			productVersion, communityPermissions, guestPermissions);
	}

	public void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalServiceFactory.getTxImpl().deleteProductVersion(productVersionId);
	}

	public void deleteProductVersion(
		com.liferay.portlet.softwarerepository.model.SRProductVersion productVersion)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalServiceFactory.getTxImpl().deleteProductVersion(productVersion);
	}

	public void deleteProductVersions(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalServiceFactory.getTxImpl().deleteProductVersions(productEntryId);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
												  .getProductVersion(productVersionId);
	}

	public java.util.List getProductVersions(long productEntryId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
												  .getProductVersions(productEntryId,
			begin, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
												  .getProductVersionsCount(productEntryId);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductVersionLocalServiceFactory.getTxImpl()
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