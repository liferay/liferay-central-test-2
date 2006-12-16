/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service;

/**
 * <a href="SRProductVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductVersionLocalServiceUtil {
	public static com.liferay.portlet.softwarerepository.model.SRProductVersion addProductVersion(
		java.lang.String userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, long[] frameworkVersionIds,
		java.lang.String downloadPageURL, java.lang.String directDownloadURL,
		boolean repoStoreArtifact)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();

		return srProductVersionLocalService.addProductVersion(userId,
			productEntryId, version, changeLog, frameworkVersionIds,
			downloadPageURL, directDownloadURL, repoStoreArtifact);
	}

	public static void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();
		srProductVersionLocalService.deleteProductVersion(productVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();

		return srProductVersionLocalService.getProductVersion(productVersionId);
	}

	public static java.util.List getProductVersions(long productEntryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();

		return srProductVersionLocalService.getProductVersions(productEntryId,
			begin, end);
	}

	public static int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();

		return srProductVersionLocalService.getProductVersionsCount(productEntryId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, long[] frameworkVersionIds,
		java.lang.String downloadPageURL, java.lang.String directDownloadURL,
		boolean repoStoreArtifact)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();

		return srProductVersionLocalService.updateProductVersion(productVersionId,
			version, changeLog, frameworkVersionIds, downloadPageURL,
			directDownloadURL, repoStoreArtifact);
	}

	public static java.util.List getSRFrameworkVersions(long productVersionId)
		throws com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portal.SystemException {
		SRProductVersionLocalService srProductVersionLocalService = SRProductVersionLocalServiceFactory.getService();

		return srProductVersionLocalService.getSRFrameworkVersions(productVersionId);
	}
}