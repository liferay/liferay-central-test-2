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
 * <a href="ResourceCodeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceCodeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCodeLocalService
 * @generated
 */
public class ResourceCodeLocalServiceWrapper implements ResourceCodeLocalService {
	public ResourceCodeLocalServiceWrapper(
		ResourceCodeLocalService resourceCodeLocalService) {
		_resourceCodeLocalService = resourceCodeLocalService;
	}

	public com.liferay.portal.model.ResourceCode addResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.addResourceCode(resourceCode);
	}

	public com.liferay.portal.model.ResourceCode createResourceCode(long codeId) {
		return _resourceCodeLocalService.createResourceCode(codeId);
	}

	public void deleteResourceCode(long codeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceCodeLocalService.deleteResourceCode(codeId);
	}

	public void deleteResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		_resourceCodeLocalService.deleteResourceCode(resourceCode);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.ResourceCode getResourceCode(long codeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _resourceCodeLocalService.getResourceCode(codeId);
	}

	public java.util.List<com.liferay.portal.model.ResourceCode> getResourceCodes(
		int start, int end) throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.getResourceCodes(start, end);
	}

	public int getResourceCodesCount()
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.getResourceCodesCount();
	}

	public com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.updateResourceCode(resourceCode);
	}

	public com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.updateResourceCode(resourceCode, merge);
	}

	public com.liferay.portal.model.ResourceCode addResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.addResourceCode(companyId, name, scope);
	}

	public void checkResourceCodes() throws com.liferay.portal.SystemException {
		_resourceCodeLocalService.checkResourceCodes();
	}

	public void checkResourceCodes(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		_resourceCodeLocalService.checkResourceCodes(companyId, name);
	}

	public com.liferay.portal.model.ResourceCode getResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.SystemException {
		return _resourceCodeLocalService.getResourceCode(companyId, name, scope);
	}

	public ResourceCodeLocalService getWrappedResourceCodeLocalService() {
		return _resourceCodeLocalService;
	}

	private ResourceCodeLocalService _resourceCodeLocalService;
}