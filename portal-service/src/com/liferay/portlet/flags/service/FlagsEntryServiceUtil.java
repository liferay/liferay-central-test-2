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

package com.liferay.portlet.flags.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="FlagsEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link FlagsEntryService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FlagsEntryService
 * @generated
 */
public class FlagsEntryServiceUtil {
	public static void addEntry(java.lang.String className, long classPK,
		java.lang.String reporterEmailAddress, long reportedUserId,
		java.lang.String contentTitle, java.lang.String contentURL,
		java.lang.String reason,
		com.liferay.portal.service.ServiceContext serviceContext) {
		getService()
			.addEntry(className, classPK, reporterEmailAddress, reportedUserId,
			contentTitle, contentURL, reason, serviceContext);
	}

	public static FlagsEntryService getService() {
		if (_service == null) {
			_service = (FlagsEntryService)PortalBeanLocatorUtil.locate(FlagsEntryService.class.getName());
		}

		return _service;
	}

	public void setService(FlagsEntryService service) {
		_service = service;
	}

	private static FlagsEntryService _service;
}