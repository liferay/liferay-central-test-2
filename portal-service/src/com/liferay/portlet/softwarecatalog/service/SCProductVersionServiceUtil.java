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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SCProductVersionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SCProductVersionService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersionService
 * @generated
 */
public class SCProductVersionServiceUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addProductVersion(productEntryId, version, changeLog,
			downloadPageURL, directDownloadURL, testDirectDownloadURL,
			repoStoreArtifact, frameworkVersionIds, serviceContext);
	}

	public static void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteProductVersion(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductVersion(productVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getProductVersions(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductVersions(productEntryId, start, end);
	}

	public static int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductVersionsCount(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateProductVersion(productVersionId, version, changeLog,
			downloadPageURL, directDownloadURL, testDirectDownloadURL,
			repoStoreArtifact, frameworkVersionIds);
	}

	public static SCProductVersionService getService() {
		if (_service == null) {
			_service = (SCProductVersionService)PortalBeanLocatorUtil.locate(SCProductVersionService.class.getName());
		}

		return _service;
	}

	public void setService(SCProductVersionService service) {
		_service = service;
	}

	private static SCProductVersionService _service;
}