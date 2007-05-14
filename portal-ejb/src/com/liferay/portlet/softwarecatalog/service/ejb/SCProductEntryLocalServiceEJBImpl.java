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

import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SCProductEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService
 * @see com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil
 * @see com.liferay.portlet.softwarecatalog.service.ejb.SCProductEntryLocalServiceEJB
 * @see com.liferay.portlet.softwarecatalog.service.ejb.SCProductEntryLocalServiceHome
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCProductEntryLocalServiceImpl
 *
 */
public class SCProductEntryLocalServiceEJBImpl
	implements SCProductEntryLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, long plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.Map images, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().addProductEntry(userId,
			plid, name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, images,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, long plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.Map images, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().addProductEntry(userId,
			plid, name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, images,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, long plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.Map images, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().addProductEntry(userId,
			plid, name, type, shortDescription, longDescription, pageURL,
			repoGroupId, repoArtifactId, licenseIds, images,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addProductEntryResources(long productEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(long productEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntryId,
			communityPermissions, guestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().addProductEntryResources(productEntry,
			communityPermissions, guestPermissions);
	}

	public void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().deleteProductEntry(productEntryId);
	}

	public void deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().deleteProductEntry(productEntry);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().getProductEntry(productEntryId);
	}

	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().getRepositoryXML(groupId,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	public java.util.List getProductEntries(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().getProductEntries(groupId,
			begin, end);
	}

	public java.util.List getProductEntries(long groupId, long userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().getProductEntries(groupId,
			userId, begin, end);
	}

	public int getProductEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl()
												.getProductEntriesCount(groupId);
	}

	public int getProductEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl()
												.getProductEntriesCount(groupId,
			userId);
	}

	public java.lang.String getProductEntryImageId(long productEntryId,
		java.lang.String imageName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl()
												.getProductEntryImageId(productEntryId,
			imageName);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalServiceFactory.getTxImpl().reIndex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.lang.String type, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().search(companyId,
			groupId, type, keywords);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds, java.util.Map images)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCProductEntryLocalServiceFactory.getTxImpl().updateProductEntry(productEntryId,
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