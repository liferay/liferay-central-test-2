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

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery, start,
			end);
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