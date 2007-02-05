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

import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalService;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SRProductEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductEntryLocalServiceEJBImpl
	implements SRProductEntryLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry addProductEntry(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String type, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		long[] licenseIds, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().addProductEntry(userId,
			plid, name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry addProductEntry(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String type, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		long[] licenseIds, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().addProductEntry(userId,
			plid, name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry addProductEntry(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String type, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		long[] licenseIds, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().addProductEntry(userId,
			plid, name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addProductEntryResources(long productEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarerepository.model.SRProductEntry productEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(long productEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntryId,
			communityPermissions, guestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarerepository.model.SRProductEntry productEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntry,
			communityPermissions, guestPermissions);
	}

	public void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().deleteProductEntry(productEntryId);
	}

	public void deleteProductEntry(
		com.liferay.portlet.softwarerepository.model.SRProductEntry productEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().deleteProductEntry(productEntry);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().getProductEntry(productEntryId);
	}

	public java.util.List getProductEntries(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().getProductEntries(groupId,
			begin, end);
	}

	public java.util.List getProductEntries(long groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().getProductEntries(groupId,
			userId, begin, end);
	}

	public int getProductEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl()
												.getProductEntriesCount(groupId);
	}

	public int getProductEntriesCount(long groupId, java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl()
												.getProductEntriesCount(groupId,
			userId);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		SRProductEntryLocalServiceFactory.getTxImpl().reIndex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(
		java.lang.String companyId, long groupId, java.lang.String type,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().search(companyId,
			groupId, type, keywords);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SRProductEntryLocalServiceFactory.getTxImpl().updateProductEntry(productEntryId,
			name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds);
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