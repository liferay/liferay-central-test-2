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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.base.SCProductEntryServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.permission.SCPermission;
import com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission;

import java.util.List;

/**
 * <a href="SCProductEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCProductEntryServiceImpl extends SCProductEntryServiceBaseImpl {

	public SCProductEntry addProductEntry(
			String name, String type, String tags, String shortDescription,
			String longDescription, String pageURL, String author,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			List<byte[]> thumbnails, List<byte[]> fullImages,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		SCPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_PRODUCT_ENTRY);

		return scProductEntryLocalService.addProductEntry(
			getUserId(), name, type, tags, shortDescription,
			longDescription, pageURL, author, repoGroupId, repoArtifactId,
			licenseIds, thumbnails, fullImages, serviceContext);
	}

	public void deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.DELETE);

		scProductEntryLocalService.deleteProductEntry(productEntryId);
	}

	public SCProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.VIEW);

		return scProductEntryLocalService.getProductEntry(productEntryId);
	}

	public SCProductEntry updateProductEntry(
			long productEntryId, String name, String type, String tags,
			String shortDescription, String longDescription, String pageURL,
			String author, String repoGroupId, String repoArtifactId,
			long[] licenseIds, List<byte[]> thumbnails, List<byte[]> fullImages)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.UPDATE);

		return scProductEntryLocalService.updateProductEntry(
			productEntryId, name, type, tags, shortDescription, longDescription,
			pageURL, author, repoGroupId, repoArtifactId, licenseIds,
			thumbnails, fullImages);
	}

}