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
 * <a href="SCProductEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCProductEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryService
 * @generated
 */
public class SCProductEntryServiceWrapper implements SCProductEntryService {
	public SCProductEntryServiceWrapper(
		SCProductEntryService scProductEntryService) {
		_scProductEntryService = scProductEntryService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		java.lang.String name, java.lang.String type, java.lang.String tags,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String author,
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		long[] licenseIds, java.util.List<byte[]> thumbnails,
		java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryService.addProductEntry(name, type, tags,
			shortDescription, longDescription, pageURL, author, repoGroupId,
			repoArtifactId, licenseIds, thumbnails, fullImages, serviceContext);
	}

	public void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryService.deleteProductEntry(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryService.getProductEntry(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryService.updateProductEntry(productEntryId, name,
			type, tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages);
	}

	public SCProductEntryService getWrappedSCProductEntryService() {
		return _scProductEntryService;
	}

	private SCProductEntryService _scProductEntryService;
}