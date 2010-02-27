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
 * <a href="PhoneServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PhoneService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PhoneService
 * @generated
 */
public class PhoneServiceUtil {
	public static com.liferay.portal.model.Phone addPhone(
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPhone(className, classPK, number, extension, typeId,
			primary);
	}

	public static void deletePhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePhone(phoneId);
	}

	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhone(phoneId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPhones(className, classPK);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePhone(phoneId, number, extension, typeId, primary);
	}

	public static PhoneService getService() {
		if (_service == null) {
			_service = (PhoneService)PortalBeanLocatorUtil.locate(PhoneService.class.getName());
		}

		return _service;
	}

	public void setService(PhoneService service) {
		_service = service;
	}

	private static PhoneService _service;
}