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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ReleaseLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ReleaseLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReleaseLocalService
 * @generated
 */
public class ReleaseLocalServiceUtil {
	public static com.liferay.portal.model.Release addRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRelease(release);
	}

	public static com.liferay.portal.model.Release createRelease(long releaseId) {
		return getService().createRelease(releaseId);
	}

	public static void deleteRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelease(releaseId);
	}

	public static void deleteRelease(com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelease(release);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelease(releaseId);
	}

	public static java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReleases(start, end);
	}

	public static int getReleasesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReleasesCount();
	}

	public static com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRelease(release);
	}

	public static com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRelease(release, merge);
	}

	public static com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRelease(servletContextName, buildNumber);
	}

	public static void createTablesAndPopulate()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().createTablesAndPopulate();
	}

	public static int getBuildNumberOrCreate()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBuildNumberOrCreate();
	}

	public static com.liferay.portal.model.Release getRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelease(servletContextName, buildNumber);
	}

	public static com.liferay.portal.model.Release updateRelease(
		long releaseId, int buildNumber, java.util.Date buildDate,
		boolean verified)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRelease(releaseId, buildNumber, buildDate, verified);
	}

	public static ReleaseLocalService getService() {
		if (_service == null) {
			_service = (ReleaseLocalService)PortalBeanLocatorUtil.locate(ReleaseLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ReleaseLocalService service) {
		_service = service;
	}

	private static ReleaseLocalService _service;
}