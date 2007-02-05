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

package com.liferay.portal.service;

/**
 * <a href="WebsiteLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WebsiteLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.Website addWebsite(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String url, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.addWebsite(userId, className, classPK, url,
			typeId, primary);
	}

	public static void deleteWebsite(long websiteId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();
		websiteLocalService.deleteWebsite(websiteId);
	}

	public static void deleteWebsites(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();
		websiteLocalService.deleteWebsites(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.getWebsite(websiteId);
	}

	public static java.util.List getWebsites()
		throws com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.getWebsites();
	}

	public static java.util.List getWebsites(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.getWebsites(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Website updateWebsite(
		long websiteId, java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WebsiteLocalService websiteLocalService = WebsiteLocalServiceFactory.getService();

		return websiteLocalService.updateWebsite(websiteId, url, typeId, primary);
	}
}