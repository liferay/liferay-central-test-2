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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionService;
import com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission;
import com.liferay.portlet.softwarecatalog.service.permission.SCProductVersionPermission;

/**
 * <a href="SCProductVersionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductVersionServiceImpl extends PrincipalBean
	implements SCProductVersionService {

	public SCProductVersion addProductVersion(
			long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId,
			ActionKeys.ADD_PRODUCT_VERSION);

		return SCProductVersionLocalServiceUtil.addProductVersion(
			getUserId(), productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions);
	}

	public SCProductVersion addProductVersion(
			long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId,
			ActionKeys.ADD_PRODUCT_VERSION);

		return SCProductVersionLocalServiceUtil.addProductVersion(
			getUserId(), productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			communityPermissions, guestPermissions);
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SCProductVersionPermission.check(
			getPermissionChecker(), productVersionId, ActionKeys.DELETE);

		SCProductVersionLocalServiceUtil.deleteProductVersion(productVersionId);
	}

	public SCProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SCProductVersionPermission.check(
			getPermissionChecker(), productVersionId, ActionKeys.VIEW);

		return SCProductVersionLocalServiceUtil.getProductVersion(
			productVersionId);
	}

	public SCProductVersion updateProductVersion(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws PortalException, SystemException {

		SCProductVersionPermission.check(
			getPermissionChecker(), productVersionId, ActionKeys.UPDATE);

		return SCProductVersionLocalServiceUtil.updateProductVersion(
			productVersionId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds);
	}

}