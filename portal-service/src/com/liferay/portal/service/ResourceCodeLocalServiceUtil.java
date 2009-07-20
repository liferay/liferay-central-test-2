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

public class ResourceCodeLocalServiceUtil {
	public static com.liferay.portal.model.ResourceCode addResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		return getService().addResourceCode(resourceCode);
	}

	public static com.liferay.portal.model.ResourceCode createResourceCode(
		long codeId) {
		return getService().createResourceCode(codeId);
	}

	public static void deleteResourceCode(long codeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteResourceCode(codeId);
	}

	public static void deleteResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		getService().deleteResourceCode(resourceCode);
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

	public static com.liferay.portal.model.ResourceCode getResourceCode(
		long codeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getResourceCode(codeId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> getResourceCodes(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getResourceCodes(start, end);
	}

	public static int getResourceCodesCount()
		throws com.liferay.portal.SystemException {
		return getService().getResourceCodesCount();
	}

	public static com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		return getService().updateResourceCode(resourceCode);
	}

	public static com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateResourceCode(resourceCode, merge);
	}

	public static com.liferay.portal.model.ResourceCode addResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.SystemException {
		return getService().addResourceCode(companyId, name, scope);
	}

	public static void checkResourceCodes()
		throws com.liferay.portal.SystemException {
		getService().checkResourceCodes();
	}

	public static void checkResourceCodes(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		getService().checkResourceCodes(companyId, name);
	}

	public static com.liferay.portal.model.ResourceCode getResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.SystemException {
		return getService().getResourceCode(companyId, name, scope);
	}

	public static ResourceCodeLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("ResourceCodeLocalService is not set");
		}

		return _service;
	}

	public void setService(ResourceCodeLocalService service) {
		_service = service;
	}

	private static ResourceCodeLocalService _service;
}