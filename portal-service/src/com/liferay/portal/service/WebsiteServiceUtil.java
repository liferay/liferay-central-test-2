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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WebsiteServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WebsiteService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebsiteService
 * @generated
 */
public class WebsiteServiceUtil {
	public static com.liferay.portal.model.Website addWebsite(
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addWebsite(className, classPK, url, typeId, primary);
	}

	public static void deleteWebsite(long websiteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWebsite(websiteId);
	}

	public static com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWebsite(websiteId);
	}

	public static java.util.List<com.liferay.portal.model.Website> getWebsites(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWebsites(className, classPK);
	}

	public static com.liferay.portal.model.Website updateWebsite(
		long websiteId, java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateWebsite(websiteId, url, typeId, primary);
	}

	public static WebsiteService getService() {
		if (_service == null) {
			_service = (WebsiteService)PortalBeanLocatorUtil.locate(WebsiteService.class.getName());
		}

		return _service;
	}

	public void setService(WebsiteService service) {
		_service = service;
	}

	private static WebsiteService _service;
}