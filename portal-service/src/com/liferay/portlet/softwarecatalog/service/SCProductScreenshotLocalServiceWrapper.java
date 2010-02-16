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
 * <a href="SCProductScreenshotLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCProductScreenshotLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshotLocalService
 * @generated
 */
public class SCProductScreenshotLocalServiceWrapper
	implements SCProductScreenshotLocalService {
	public SCProductScreenshotLocalServiceWrapper(
		SCProductScreenshotLocalService scProductScreenshotLocalService) {
		_scProductScreenshotLocalService = scProductScreenshotLocalService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot addSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.addSCProductScreenshot(scProductScreenshot);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot createSCProductScreenshot(
		long productScreenshotId) {
		return _scProductScreenshotLocalService.createSCProductScreenshot(productScreenshotId);
	}

	public void deleteSCProductScreenshot(long productScreenshotId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductScreenshotLocalService.deleteSCProductScreenshot(productScreenshotId);
	}

	public void deleteSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductScreenshotLocalService.deleteSCProductScreenshot(scProductScreenshot);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getSCProductScreenshot(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getSCProductScreenshot(productScreenshotId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getSCProductScreenshots(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getSCProductScreenshots(start,
			end);
	}

	public int getSCProductScreenshotsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getSCProductScreenshotsCount();
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.updateSCProductScreenshot(scProductScreenshot);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.updateSCProductScreenshot(scProductScreenshot,
			merge);
	}

	public void deleteProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot productScreenshot)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductScreenshotLocalService.deleteProductScreenshot(productScreenshot);
	}

	public void deleteProductScreenshots(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductScreenshotLocalService.deleteProductScreenshots(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshot(
		long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getProductScreenshot(productEntryId,
			priority);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByFullImageId(
		long fullImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getProductScreenshotByFullImageId(fullImageId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByThumbnailId(
		long thumbnailId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getProductScreenshotByThumbnailId(thumbnailId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getProductScreenshots(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductScreenshotLocalService.getProductScreenshots(productEntryId);
	}

	public SCProductScreenshotLocalService getWrappedSCProductScreenshotLocalService() {
		return _scProductScreenshotLocalService;
	}

	private SCProductScreenshotLocalService _scProductScreenshotLocalService;
}