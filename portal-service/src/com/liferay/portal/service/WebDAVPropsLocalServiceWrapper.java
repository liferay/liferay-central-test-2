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
 * <a href="WebDAVPropsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WebDAVPropsLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVPropsLocalService
 * @generated
 */
public class WebDAVPropsLocalServiceWrapper implements WebDAVPropsLocalService {
	public WebDAVPropsLocalServiceWrapper(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		_webDAVPropsLocalService = webDAVPropsLocalService;
	}

	public com.liferay.portal.model.WebDAVProps addWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.addWebDAVProps(webDAVProps);
	}

	public com.liferay.portal.model.WebDAVProps createWebDAVProps(
		long webDavPropsId) {
		return _webDAVPropsLocalService.createWebDAVProps(webDavPropsId);
	}

	public void deleteWebDAVProps(long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.deleteWebDAVProps(webDavPropsId);
	}

	public void deleteWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.deleteWebDAVProps(webDAVProps);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.WebDAVProps getWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVProps(webDavPropsId);
	}

	public java.util.List<com.liferay.portal.model.WebDAVProps> getWebDAVPropses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVPropses(start, end);
	}

	public int getWebDAVPropsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVPropsesCount();
	}

	public com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.updateWebDAVProps(webDAVProps);
	}

	public com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.updateWebDAVProps(webDAVProps, merge);
	}

	public void deleteWebDAVProps(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.deleteWebDAVProps(className, classPK);
	}

	public com.liferay.portal.model.WebDAVProps getWebDAVProps(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVProps(companyId, className,
			classPK);
	}

	public void storeWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDavProps)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.storeWebDAVProps(webDavProps);
	}

	public WebDAVPropsLocalService getWrappedWebDAVPropsLocalService() {
		return _webDAVPropsLocalService;
	}

	private WebDAVPropsLocalService _webDAVPropsLocalService;
}