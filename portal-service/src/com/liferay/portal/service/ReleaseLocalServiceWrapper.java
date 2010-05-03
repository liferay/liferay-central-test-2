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

package com.liferay.portal.service;


/**
 * <a href="ReleaseLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ReleaseLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReleaseLocalService
 * @generated
 */
public class ReleaseLocalServiceWrapper implements ReleaseLocalService {
	public ReleaseLocalServiceWrapper(ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	public com.liferay.portal.model.Release addRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.addRelease(release);
	}

	public com.liferay.portal.model.Release createRelease(long releaseId) {
		return _releaseLocalService.createRelease(releaseId);
	}

	public void deleteRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_releaseLocalService.deleteRelease(releaseId);
	}

	public void deleteRelease(com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		_releaseLocalService.deleteRelease(release);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getRelease(releaseId);
	}

	public java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getReleases(start, end);
	}

	public int getReleasesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getReleasesCount();
	}

	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.updateRelease(release);
	}

	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.updateRelease(release, merge);
	}

	public com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.addRelease(servletContextName, buildNumber);
	}

	public void createTablesAndPopulate()
		throws com.liferay.portal.kernel.exception.SystemException {
		_releaseLocalService.createTablesAndPopulate();
	}

	public int getBuildNumberOrCreate()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getBuildNumberOrCreate();
	}

	public com.liferay.portal.model.Release getRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getRelease(servletContextName, buildNumber);
	}

	public com.liferay.portal.model.Release updateRelease(long releaseId,
		int buildNumber, java.util.Date buildDate, boolean verified)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.updateRelease(releaseId, buildNumber,
			buildDate, verified);
	}

	public ReleaseLocalService getWrappedReleaseLocalService() {
		return _releaseLocalService;
	}

	private ReleaseLocalService _releaseLocalService;
}