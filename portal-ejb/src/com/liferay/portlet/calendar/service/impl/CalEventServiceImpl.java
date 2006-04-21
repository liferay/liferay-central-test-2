/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;
import com.liferay.portlet.calendar.service.spring.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.spring.CalEventService;
import com.liferay.util.cal.Recurrence;

/**
 * <a href="CalEventServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventServiceImpl
	extends PrincipalBean implements CalEventService {

	public CalEvent addEvent(
			String plid, String title, String description, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, int endDateMonth, int endDateDay,
			int endDateYear, int durationHour, int durationMinute,
			boolean allDay, boolean timeZoneSensitive, String type,
			boolean repeating, Recurrence recurrence, String remindBy,
			int firstReminder, int secondReminder,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.CALENDAR,
			ActionKeys.ADD_EVENT);

		return CalEventLocalServiceUtil.addEvent(
			getUserId(), plid, title, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteEvent(String eventId)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.DELETE);

		CalEventLocalServiceUtil.deleteEvent(eventId);
	}

	public CalEvent getEvent(String eventId)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.VIEW);

		return CalEventLocalServiceUtil.getEvent(eventId);
	}

	public CalEvent updateEvent(
			String eventId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, Recurrence recurrence,
			String remindBy, int firstReminder, int secondReminder)
		throws PortalException, SystemException {

		CalEventPermission.check(
			getPermissionChecker(), eventId, ActionKeys.UPDATE);

		return CalEventLocalServiceUtil.updateEvent(
			getUserId(), eventId, title, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour, durationMinute,
			allDay, timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder);
	}

}