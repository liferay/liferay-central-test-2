/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

public class WebsiteLocalServiceUtil {
	public static com.liferay.portal.model.Website addWebsite(
		com.liferay.portal.model.Website website)
		throws com.liferay.portal.SystemException {
		return getService().addWebsite(website);
	}

	public static com.liferay.portal.model.Website createWebsite(long websiteId) {
		return getService().createWebsite(websiteId);
	}

	public static void deleteWebsite(long websiteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWebsite(websiteId);
	}

	public static void deleteWebsite(com.liferay.portal.model.Website website)
		throws com.liferay.portal.SystemException {
		getService().deleteWebsite(website);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWebsite(websiteId);
	}

	public static java.util.List<com.liferay.portal.model.Website> getWebsites(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWebsites(start, end);
	}

	public static int getWebsitesCount()
		throws com.liferay.portal.SystemException {
		return getService().getWebsitesCount();
	}

	public static com.liferay.portal.model.Website updateWebsite(
		com.liferay.portal.model.Website website)
		throws com.liferay.portal.SystemException {
		return getService().updateWebsite(website);
	}

	public static com.liferay.portal.model.Website updateWebsite(
		com.liferay.portal.model.Website website, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateWebsite(website, merge);
	}

	public static com.liferay.portal.model.Website addWebsite(long userId,
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addWebsite(userId, className, classPK, url, typeId, primary);
	}

	public static void deleteWebsites(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		getService().deleteWebsites(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Website> getWebsites()
		throws com.liferay.portal.SystemException {
		return getService().getWebsites();
	}

	public static java.util.List<com.liferay.portal.model.Website> getWebsites(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getWebsites(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Website updateWebsite(
		long websiteId, java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateWebsite(websiteId, url, typeId, primary);
	}

	public static WebsiteLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("WebsiteLocalService is not set");
		}

		return _service;
	}

	public void setService(WebsiteLocalService service) {
		_service = service;
	}

	private static WebsiteLocalService _service;
}