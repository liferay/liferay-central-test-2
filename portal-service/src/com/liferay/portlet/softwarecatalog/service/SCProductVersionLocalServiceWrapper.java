/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCProductVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCProductVersionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersionLocalService
 * @generated
 */
public class SCProductVersionLocalServiceWrapper
	implements SCProductVersionLocalService {
	public SCProductVersionLocalServiceWrapper(
		SCProductVersionLocalService scProductVersionLocalService) {
		_scProductVersionLocalService = scProductVersionLocalService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.addSCProductVersion(scProductVersion);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion createSCProductVersion(
		long productVersionId) {
		return _scProductVersionLocalService.createSCProductVersion(productVersionId);
	}

	public void deleteSCProductVersion(long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductVersionLocalService.deleteSCProductVersion(productVersionId);
	}

	public void deleteSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductVersionLocalService.deleteSCProductVersion(scProductVersion);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getSCProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getSCProductVersion(productVersionId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getSCProductVersions(start, end);
	}

	public int getSCProductVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getSCProductVersionsCount();
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.updateSCProductVersion(scProductVersion);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.updateSCProductVersion(scProductVersion,
			merge);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, testDirectDownloadURL, repoStoreArtifact,
			frameworkVersionIds, serviceContext);
	}

	public void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductVersionLocalService.deleteProductVersion(productVersionId);
	}

	public void deleteProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion productVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductVersionLocalService.deleteProductVersion(productVersion);
	}

	public void deleteProductVersions(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductVersionLocalService.deleteProductVersions(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getProductVersion(productVersionId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersionByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getProductVersionByDirectDownloadURL(directDownloadURL);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getProductVersions(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getProductVersions(productEntryId,
			start, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.getProductVersionsCount(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.updateProductVersion(productVersionId,
			version, changeLog, downloadPageURL, directDownloadURL,
			testDirectDownloadURL, repoStoreArtifact, frameworkVersionIds);
	}

	public SCProductVersionLocalService getWrappedSCProductVersionLocalService() {
		return _scProductVersionLocalService;
	}

	private SCProductVersionLocalService _scProductVersionLocalService;
}