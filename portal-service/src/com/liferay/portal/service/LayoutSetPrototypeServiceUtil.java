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

public class LayoutSetPrototypeServiceUtil {
	public static com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addLayoutSetPrototype(nameMap, description, active);
	}

	public static void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> search(
		long companyId, java.lang.Boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().search(companyId, active, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateLayoutSetPrototype(layoutSetPrototypeId, nameMap,
			description, active);
	}

	public static LayoutSetPrototypeService getService() {
		if (_service == null) {
			throw new RuntimeException("LayoutSetPrototypeService is not set");
		}

		return _service;
	}

	public void setService(LayoutSetPrototypeService service) {
		_service = service;
	}

	private static LayoutSetPrototypeService _service;
}