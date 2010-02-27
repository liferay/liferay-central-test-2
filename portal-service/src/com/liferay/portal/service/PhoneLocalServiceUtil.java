/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PhoneLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PhoneLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PhoneLocalService
 * @generated
 */
public class PhoneLocalServiceUtil {
	public static com.liferay.portal.model.Phone addPhone(
		com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPhone(phone);
	}

	public static com.liferay.portal.model.Phone createPhone(long phoneId) {
		return getService().createPhone(phoneId);
	}

	public static void deletePhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePhone(phoneId);
	}

	public static void deletePhone(com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePhone(phone);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhone(phoneId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhones(start, end);
	}

	public static int getPhonesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhonesCount();
	}

	public static com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePhone(phone);
	}

	public static com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePhone(phone, merge);
	}

	public static com.liferay.portal.model.Phone addPhone(long userId,
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPhone(userId, className, classPK, number, extension,
			typeId, primary);
	}

	public static void deletePhones(long companyId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePhones(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhones();
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhones(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePhone(phoneId, number, extension, typeId, primary);
	}

	public static PhoneLocalService getService() {
		if (_service == null) {
			_service = (PhoneLocalService)PortalBeanLocatorUtil.locate(PhoneLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PhoneLocalService service) {
		_service = service;
	}

	private static PhoneLocalService _service;
}