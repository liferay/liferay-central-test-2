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
 * <a href="ListTypeServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ListTypeService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListTypeService
 * @generated
 */
public class ListTypeServiceUtil {
	public static com.liferay.portal.model.ListType getListType(int listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getListType(listTypeId);
	}

	public static java.util.List<com.liferay.portal.model.ListType> getListTypes(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getListTypes(type);
	}

	public static void validate(int listTypeId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().validate(listTypeId, type);
	}

	public static void validate(int listTypeId, long classNameId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().validate(listTypeId, classNameId, type);
	}

	public static ListTypeService getService() {
		if (_service == null) {
			_service = (ListTypeService)PortalBeanLocatorUtil.locate(ListTypeService.class.getName());
		}

		return _service;
	}

	public void setService(ListTypeService service) {
		_service = service;
	}

	private static ListTypeService _service;
}