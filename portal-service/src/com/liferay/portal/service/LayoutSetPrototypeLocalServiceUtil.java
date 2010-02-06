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
 * <a href="LayoutSetPrototypeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link LayoutSetPrototypeLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototypeLocalService
 * @generated
 */
public class LayoutSetPrototypeLocalServiceUtil {
	public static com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.SystemException {
		return getService().addLayoutSetPrototype(layoutSetPrototype);
	}

	public static com.liferay.portal.model.LayoutSetPrototype createLayoutSetPrototype(
		long layoutSetPrototypeId) {
		return getService().createLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static void deleteLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.SystemException {
		getService().deleteLayoutSetPrototype(layoutSetPrototype);
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

	public static com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> getLayoutSetPrototypes(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getLayoutSetPrototypes(start, end);
	}

	public static int getLayoutSetPrototypesCount()
		throws com.liferay.portal.SystemException {
		return getService().getLayoutSetPrototypesCount();
	}

	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.SystemException {
		return getService().updateLayoutSetPrototype(layoutSetPrototype);
	}

	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateLayoutSetPrototype(layoutSetPrototype, merge);
	}

	public static com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addLayoutSetPrototype(userId, companyId, nameMap,
			description, active);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> search(
		long companyId, java.lang.Boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, active, start, end, obc);
	}

	public static int searchCount(long companyId, java.lang.Boolean active)
		throws com.liferay.portal.SystemException {
		return getService().searchCount(companyId, active);
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

	public static LayoutSetPrototypeLocalService getService() {
		if (_service == null) {
			_service = (LayoutSetPrototypeLocalService)PortalBeanLocatorUtil.locate(LayoutSetPrototypeLocalService.class.getName());
		}

		return _service;
	}

	public void setService(LayoutSetPrototypeLocalService service) {
		_service = service;
	}

	private static LayoutSetPrototypeLocalService _service;
}