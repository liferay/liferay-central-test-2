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

package com.liferay.portlet.softwarerepository.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalServiceUtil;
import com.liferay.portlet.softwarerepository.service.SRProductVersionService;

import java.util.List;

/**
 * <a href="SRProductVersionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductVersionServiceImpl extends PrincipalBean
	implements SRProductVersionService {

	public SRProductVersion addProductVersion(
		long productEntryId, String version,
		String changeLog, long[] frameworkVersionIds,
		String downloadPageURL, String directDownloadURL,
		boolean repoStoreArtifact)
		throws PortalException, SystemException {

		return SRProductVersionLocalServiceUtil.addProductVersion(
			getUserId(), productEntryId, version, changeLog,
			frameworkVersionIds, downloadPageURL, directDownloadURL,
			repoStoreArtifact);
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {
	 	SRProductVersionLocalServiceUtil.deleteProductVersion(productVersionId);
	}

	public SRProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {
		return SRProductVersionLocalServiceUtil.
			getProductVersion(productVersionId);
	}

	public List getProductVersions(
			long productEntryId, int begin, int end)
		throws SystemException {
		return SRProductVersionLocalServiceUtil.getProductVersions(
			productEntryId, begin, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws SystemException {
		return SRProductVersionLocalServiceUtil.
			getProductVersionsCount(productEntryId);
	}

	public SRProductVersion updateProductVersion(
		long productVersionId, String version,
		String changeLog, long[] frameworkVersionIds,
		String downloadPageURL, String directDownloadURL,
		boolean repoStoreArtifact)
		throws PortalException, SystemException {
		return SRProductVersionLocalServiceUtil.updateProductVersion(
			productVersionId, version, changeLog, frameworkVersionIds,
			downloadPageURL, directDownloadURL, repoStoreArtifact);
	}

}