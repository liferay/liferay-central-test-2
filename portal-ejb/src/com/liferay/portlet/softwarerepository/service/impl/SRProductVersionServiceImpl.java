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

package com.liferay.portlet.softwarerepository.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalServiceUtil;
import com.liferay.portlet.softwarerepository.service.SRProductVersionService;
import com.liferay.portlet.softwarerepository.service.permission.SRProductEntryPermission;
import com.liferay.portlet.softwarerepository.service.permission.SRProductVersionPermission;

/**
 * <a href="SRProductVersionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductVersionServiceImpl extends PrincipalBean
	implements SRProductVersionService {

	public SRProductVersion addProductVersion(
			long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		SRProductEntryPermission.check(
			getPermissionChecker(), productEntryId,
			ActionKeys.ADD_PRODUCT_VERSION);

		return SRProductVersionLocalServiceUtil.addProductVersion(
			getUserId(), productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions);
	}

	public SRProductVersion addProductVersion(
			long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		SRProductEntryPermission.check(
			getPermissionChecker(), productEntryId,
			ActionKeys.ADD_PRODUCT_VERSION);

		return SRProductVersionLocalServiceUtil.addProductVersion(
			getUserId(), productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			communityPermissions, guestPermissions);
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SRProductVersionPermission.check(
			getPermissionChecker(), productVersionId, ActionKeys.DELETE);

		SRProductVersionLocalServiceUtil.deleteProductVersion(productVersionId);
	}

	public SRProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SRProductVersionPermission.check(
			getPermissionChecker(), productVersionId, ActionKeys.VIEW);

		return SRProductVersionLocalServiceUtil.getProductVersion(
			productVersionId);
	}

	public SRProductVersion updateProductVersion(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws PortalException, SystemException {

		SRProductVersionPermission.check(
			getPermissionChecker(), productVersionId, ActionKeys.UPDATE);

		return SRProductVersionLocalServiceUtil.updateProductVersion(
			productVersionId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds);
	}

}