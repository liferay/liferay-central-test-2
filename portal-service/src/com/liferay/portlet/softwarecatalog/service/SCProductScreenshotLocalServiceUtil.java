/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.softwarecatalog.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SCProductScreenshotLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SCProductScreenshotLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshotLocalService
 * @generated
 */
public class SCProductScreenshotLocalServiceUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot addSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSCProductScreenshot(scProductScreenshot);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot createSCProductScreenshot(
		long productScreenshotId) {
		return getService().createSCProductScreenshot(productScreenshotId);
	}

	public static void deleteSCProductScreenshot(long productScreenshotId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCProductScreenshot(productScreenshotId);
	}

	public static void deleteSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCProductScreenshot(scProductScreenshot);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getSCProductScreenshot(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductScreenshot(productScreenshotId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getSCProductScreenshots(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductScreenshots(start, end);
	}

	public static int getSCProductScreenshotsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductScreenshotsCount();
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSCProductScreenshot(scProductScreenshot);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSCProductScreenshot(scProductScreenshot, merge);
	}

	public static void deleteProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot productScreenshot)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteProductScreenshot(productScreenshot);
	}

	public static void deleteProductScreenshots(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteProductScreenshots(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshot(
		long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductScreenshot(productEntryId, priority);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByFullImageId(
		long fullImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductScreenshotByFullImageId(fullImageId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByThumbnailId(
		long thumbnailId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductScreenshotByThumbnailId(thumbnailId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getProductScreenshots(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductScreenshots(productEntryId);
	}

	public static SCProductScreenshotLocalService getService() {
		if (_service == null) {
			_service = (SCProductScreenshotLocalService)PortalBeanLocatorUtil.locate(SCProductScreenshotLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SCProductScreenshotLocalService service) {
		_service = service;
	}

	private static SCProductScreenshotLocalService _service;
}