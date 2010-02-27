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

package com.liferay.portlet.announcements.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AnnouncementsDeliveryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AnnouncementsDeliveryService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryService
 * @generated
 */
public class AnnouncementsDeliveryServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDelivery(userId, type, email, sms, website);
	}

	public static AnnouncementsDeliveryService getService() {
		if (_service == null) {
			_service = (AnnouncementsDeliveryService)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryService.class.getName());
		}

		return _service;
	}

	public void setService(AnnouncementsDeliveryService service) {
		_service = service;
	}

	private static AnnouncementsDeliveryService _service;
}