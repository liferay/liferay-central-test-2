/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCProductEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCProductEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryLocalService
 * @generated
 */
public class SCProductEntryLocalServiceWrapper
	implements SCProductEntryLocalService {
	public SCProductEntryLocalServiceWrapper(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.addSCProductEntry(scProductEntry);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry createSCProductEntry(
		long productEntryId) {
		return _scProductEntryLocalService.createSCProductEntry(productEntryId);
	}

	public void deleteSCProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteSCProductEntry(productEntryId);
	}

	public void deleteSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteSCProductEntry(scProductEntry);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getSCProductEntry(productEntryId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getSCProductEntries(start, end);
	}

	public int getSCProductEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getSCProductEntriesCount();
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.updateSCProductEntry(scProductEntry);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.updateSCProductEntry(scProductEntry,
			merge);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.addProductEntry(userId, name, type,
			tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages,
			serviceContext);
	}

	public void addProductEntryResources(long productEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(long productEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntryId,
			communityPermissions, guestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntry,
			communityPermissions, guestPermissions);
	}

	public void deleteProductEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteProductEntries(groupId);
	}

	public void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteProductEntry(productEntryId);
	}

	public void deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteProductEntry(productEntry);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getCompanyProductEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getCompanyProductEntries(companyId,
			start, end);
	}

	public int getCompanyProductEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getCompanyProductEntriesCount(companyId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, userId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, userId,
			start, end, obc);
	}

	public int getProductEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntriesCount(groupId);
	}

	public int getProductEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntriesCount(groupId,
			userId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntry(productEntryId);
	}

	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getRepositoryXML(groupId,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String version, java.lang.String baseImageURL,
		java.util.Date oldestDate, int maxNumOfVersions,
		java.util.Properties repoSettings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getRepositoryXML(groupId, version,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.updateProductEntry(productEntryId,
			name, type, tags, shortDescription, longDescription, pageURL,
			author, repoGroupId, repoArtifactId, licenseIds, thumbnails,
			fullImages);
	}

	public SCProductEntryLocalService getWrappedSCProductEntryLocalService() {
		return _scProductEntryLocalService;
	}

	private SCProductEntryLocalService _scProductEntryLocalService;
}