/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.service.http;

import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.calendar.service.spring.CalEventServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="CalEventServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventServiceSoap {
	public static com.liferay.portlet.calendar.model.CalEventModel addEvent(
		java.lang.String plid, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.util.cal.Recurrence recurrence, java.lang.String remindBy,
		int firstReminder, int secondReminder, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.calendar.model.CalEvent returnValue = CalEventServiceUtil.addEvent(plid,
					title, description, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, durationHour,
					durationMinute, allDay, timeZoneSensitive, type, repeating,
					recurrence, remindBy, firstReminder, secondReminder,
					addCommunityPermissions, addGuestPermissions);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteEvent(java.lang.String eventId)
		throws RemoteException {
		try {
			CalEventServiceUtil.deleteEvent(eventId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.calendar.model.CalEventModel getEvent(
		java.lang.String eventId) throws RemoteException {
		try {
			com.liferay.portlet.calendar.model.CalEvent returnValue = CalEventServiceUtil.getEvent(eventId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.calendar.model.CalEventModel updateEvent(
		java.lang.String eventId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.util.cal.Recurrence recurrence, java.lang.String remindBy,
		int firstReminder, int secondReminder) throws RemoteException {
		try {
			com.liferay.portlet.calendar.model.CalEvent returnValue = CalEventServiceUtil.updateEvent(eventId,
					title, description, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, durationHour,
					durationMinute, allDay, timeZoneSensitive, type, repeating,
					recurrence, remindBy, firstReminder, secondReminder);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(CalEventServiceSoap.class);
}