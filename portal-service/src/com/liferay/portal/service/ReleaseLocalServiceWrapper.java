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
		throws com.liferay.portal.SystemException {
		return _releaseLocalService.addRelease(release);
	}

	public com.liferay.portal.model.Release createRelease(long releaseId) {
		return _releaseLocalService.createRelease(releaseId);
	}

	public void deleteRelease(long releaseId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_releaseLocalService.deleteRelease(releaseId);
	}

	public void deleteRelease(com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		_releaseLocalService.deleteRelease(release);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _releaseLocalService.getRelease(releaseId);
	}

	public java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end) throws com.liferay.portal.SystemException {
		return _releaseLocalService.getReleases(start, end);
	}

	public int getReleasesCount() throws com.liferay.portal.SystemException {
		return _releaseLocalService.getReleasesCount();
	}

	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		return _releaseLocalService.updateRelease(release);
	}

	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.SystemException {
		return _releaseLocalService.updateRelease(release, merge);
	}

	public com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.SystemException {
		return _releaseLocalService.addRelease(servletContextName, buildNumber);
	}

	public void createTablesAndPopulate()
		throws com.liferay.portal.SystemException {
		_releaseLocalService.createTablesAndPopulate();
	}

	public int getBuildNumberOrCreate()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _releaseLocalService.getBuildNumberOrCreate();
	}

	public com.liferay.portal.model.Release getRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _releaseLocalService.getRelease(servletContextName, buildNumber);
	}

	public com.liferay.portal.model.Release updateRelease(long releaseId,
		int buildNumber, java.util.Date buildDate, boolean verified)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _releaseLocalService.updateRelease(releaseId, buildNumber,
			buildDate, verified);
	}

	public ReleaseLocalService getWrappedReleaseLocalService() {
		return _releaseLocalService;
	}

	private ReleaseLocalService _releaseLocalService;
}