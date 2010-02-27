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

package com.liferay.portlet.calendar.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="CalEventServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link CalEventService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventService
 * @generated
 */
public class CalEventServiceUtil {
	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEvent(title, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public static void deleteEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEvent(eventId);
	}

	public static java.io.File exportEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().exportEvent(eventId);
	}

	public static java.io.File exportGroupEvents(long groupId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().exportGroupEvents(groupId, fileName);
	}

	public static com.liferay.portlet.calendar.model.CalEvent getEvent(
		long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEvent(eventId);
	}

	public static void importICal4j(long groupId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().importICal4j(groupId, file);
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long eventId, java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEvent(eventId, title, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour,
			durationMinute, allDay, timeZoneSensitive, type, repeating,
			recurrence, remindBy, firstReminder, secondReminder, serviceContext);
	}

	public static CalEventService getService() {
		if (_service == null) {
			_service = (CalEventService)PortalBeanLocatorUtil.locate(CalEventService.class.getName());
		}

		return _service;
	}

	public void setService(CalEventService service) {
		_service = service;
	}

	private static CalEventService _service;
}