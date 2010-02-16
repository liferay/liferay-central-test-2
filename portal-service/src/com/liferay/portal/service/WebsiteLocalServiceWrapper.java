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

package com.liferay.portal.service;


/**
 * <a href="WebsiteLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WebsiteLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebsiteLocalService
 * @generated
 */
public class WebsiteLocalServiceWrapper implements WebsiteLocalService {
	public WebsiteLocalServiceWrapper(WebsiteLocalService websiteLocalService) {
		_websiteLocalService = websiteLocalService;
	}

	public com.liferay.portal.model.Website addWebsite(
		com.liferay.portal.model.Website website)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.addWebsite(website);
	}

	public com.liferay.portal.model.Website createWebsite(long websiteId) {
		return _websiteLocalService.createWebsite(websiteId);
	}

	public void deleteWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_websiteLocalService.deleteWebsite(websiteId);
	}

	public void deleteWebsite(com.liferay.portal.model.Website website)
		throws com.liferay.portal.kernel.exception.SystemException {
		_websiteLocalService.deleteWebsite(website);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.getWebsite(websiteId);
	}

	public java.util.List<com.liferay.portal.model.Website> getWebsites(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.getWebsites(start, end);
	}

	public int getWebsitesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.getWebsitesCount();
	}

	public com.liferay.portal.model.Website updateWebsite(
		com.liferay.portal.model.Website website)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.updateWebsite(website);
	}

	public com.liferay.portal.model.Website updateWebsite(
		com.liferay.portal.model.Website website, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.updateWebsite(website, merge);
	}

	public com.liferay.portal.model.Website addWebsite(long userId,
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.addWebsite(userId, className, classPK, url,
			typeId, primary);
	}

	public void deleteWebsites(long companyId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_websiteLocalService.deleteWebsites(companyId, className, classPK);
	}

	public java.util.List<com.liferay.portal.model.Website> getWebsites()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.getWebsites();
	}

	public java.util.List<com.liferay.portal.model.Website> getWebsites(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.getWebsites(companyId, className, classPK);
	}

	public com.liferay.portal.model.Website updateWebsite(long websiteId,
		java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteLocalService.updateWebsite(websiteId, url, typeId,
			primary);
	}

	public WebsiteLocalService getWrappedWebsiteLocalService() {
		return _websiteLocalService;
	}

	private WebsiteLocalService _websiteLocalService;
}