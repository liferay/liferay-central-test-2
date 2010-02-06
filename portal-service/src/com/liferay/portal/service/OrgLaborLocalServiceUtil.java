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
 * <a href="OrgLaborLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link OrgLaborLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborLocalService
 * @generated
 */
public class OrgLaborLocalServiceUtil {
	public static com.liferay.portal.model.OrgLabor addOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		return getService().addOrgLabor(orgLabor);
	}

	public static com.liferay.portal.model.OrgLabor createOrgLabor(
		long orgLaborId) {
		return getService().createOrgLabor(orgLaborId);
	}

	public static void deleteOrgLabor(long orgLaborId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteOrgLabor(orgLaborId);
	}

	public static void deleteOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		getService().deleteOrgLabor(orgLabor);
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

	public static com.liferay.portal.model.OrgLabor getOrgLabor(long orgLaborId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getOrgLabor(orgLaborId);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getOrgLabors(start, end);
	}

	public static int getOrgLaborsCount()
		throws com.liferay.portal.SystemException {
		return getService().getOrgLaborsCount();
	}

	public static com.liferay.portal.model.OrgLabor updateOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		return getService().updateOrgLabor(orgLabor);
	}

	public static com.liferay.portal.model.OrgLabor updateOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateOrgLabor(orgLabor, merge);
	}

	public static com.liferay.portal.model.OrgLabor addOrgLabor(
		long organizationId, int typeId, int sunOpen, int sunClose,
		int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
		int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
		int satOpen, int satClose)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addOrgLabor(organizationId, typeId, sunOpen, sunClose,
			monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
			thuClose, friOpen, friClose, satOpen, satClose);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		long organizationId) throws com.liferay.portal.SystemException {
		return getService().getOrgLabors(organizationId);
	}

	public static com.liferay.portal.model.OrgLabor updateOrgLabor(
		long orgLaborId, int typeId, int sunOpen, int sunClose, int monOpen,
		int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose,
		int thuOpen, int thuClose, int friOpen, int friClose, int satOpen,
		int satClose)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateOrgLabor(orgLaborId, typeId, sunOpen, sunClose,
			monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
			thuClose, friOpen, friClose, satOpen, satClose);
	}

	public static OrgLaborLocalService getService() {
		if (_service == null) {
			_service = (OrgLaborLocalService)PortalBeanLocatorUtil.locate(OrgLaborLocalService.class.getName());
		}

		return _service;
	}

	public void setService(OrgLaborLocalService service) {
		_service = service;
	}

	private static OrgLaborLocalService _service;
}