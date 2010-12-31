/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the message boards message flag remote service. This utility wraps {@link com.liferay.portlet.messageboards.service.impl.MBMessageFlagServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageFlagService
 * @see com.liferay.portlet.messageboards.service.base.MBMessageFlagServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBMessageFlagServiceImpl
 * @generated
 */
public class MBMessageFlagServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBMessageFlagServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addAnswerFlag(messageId);
	}

	public static void deleteAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnswerFlag(messageId);
	}

	public static MBMessageFlagService getService() {
		if (_service == null) {
			_service = (MBMessageFlagService)PortalBeanLocatorUtil.locate(MBMessageFlagService.class.getName());

			ReferenceRegistry.registerReference(MBMessageFlagServiceUtil.class,
				"_service");
			MethodCache.remove(MBMessageFlagService.class);
		}

		return _service;
	}

	public void setService(MBMessageFlagService service) {
		MethodCache.remove(MBMessageFlagService.class);

		_service = service;

		ReferenceRegistry.registerReference(MBMessageFlagServiceUtil.class,
			"_service");
		MethodCache.remove(MBMessageFlagService.class);
	}

	private static MBMessageFlagService _service;
}