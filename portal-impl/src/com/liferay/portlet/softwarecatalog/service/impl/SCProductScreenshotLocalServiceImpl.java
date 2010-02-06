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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.service.base.SCProductScreenshotLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="SCProductScreenshotLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SCProductScreenshotLocalServiceImpl
	extends SCProductScreenshotLocalServiceBaseImpl {

	public void deleteProductScreenshot(SCProductScreenshot productScreenshot)
		throws PortalException, SystemException {

		// Product screenshot

		scProductScreenshotPersistence.remove(productScreenshot);

		// Images

		imageLocalService.deleteImage(productScreenshot.getThumbnailId());
		imageLocalService.deleteImage(productScreenshot.getFullImageId());
	}

	public void deleteProductScreenshots(long productEntryId)
		throws PortalException, SystemException {

		List<SCProductScreenshot> productScreenshots =
			scProductScreenshotPersistence.findByProductEntryId(productEntryId);

		for (SCProductScreenshot productScreenshot : productScreenshots) {
			deleteProductScreenshot(productScreenshot);
		}
	}

	public SCProductScreenshot getProductScreenshot(
			long productEntryId, int priority)
		throws PortalException, SystemException {

		return scProductScreenshotPersistence.findByP_P(
			productEntryId, priority);
	}

	public SCProductScreenshot getProductScreenshotByFullImageId(
			long fullImageId)
		throws PortalException, SystemException {

		return scProductScreenshotPersistence.findByFullImageId(fullImageId);
	}

	public SCProductScreenshot getProductScreenshotByThumbnailId(
			long thumbnailId)
		throws PortalException, SystemException {

		return scProductScreenshotPersistence.findByThumbnailId(thumbnailId);
	}

	public List<SCProductScreenshot> getProductScreenshots(long productEntryId)
		throws SystemException {

		return scProductScreenshotPersistence.findByProductEntryId(
			productEntryId);
	}

}