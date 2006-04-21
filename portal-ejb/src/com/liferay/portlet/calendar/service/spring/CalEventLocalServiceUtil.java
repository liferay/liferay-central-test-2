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

package com.liferay.portlet.calendar.service.spring;

/**
 * <a href="CalEventLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventLocalServiceUtil {
	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.util.cal.Recurrence recurrence, java.lang.String remindBy,
		int firstReminder, int secondReminder, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.addEvent(userId, plid, title,
				description, startDateMonth, startDateDay, startDateYear,
				startDateHour, startDateMinute, endDateMonth, endDateDay,
				endDateYear, durationHour, durationMinute, allDay,
				timeZoneSensitive, type, repeating, recurrence, remindBy,
				firstReminder, secondReminder, addCommunityPermissions,
				addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addEventResources(java.lang.String eventId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();
			calEventLocalService.addEventResources(eventId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();
			calEventLocalService.addEventResources(event,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void checkEvents()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();
			calEventLocalService.checkEvents();
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteEvent(java.lang.String eventId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();
			calEventLocalService.deleteEvent(eventId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteEvent(
		com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();
			calEventLocalService.deleteEvent(event);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteEvents(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();
			calEventLocalService.deleteEvents(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent getEvent(
		java.lang.String eventId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.getEvent(eventId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getEvents(java.lang.String groupId,
		java.lang.String type, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.getEvents(groupId, type, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getEvents(java.lang.String groupId,
		java.util.Calendar cal) throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.getEvents(groupId, cal);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getEvents(java.lang.String groupId,
		java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.getEvents(groupId, cal, type);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getEventsCount(java.lang.String groupId,
		java.lang.String type) throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.getEventsCount(groupId, type);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getRepeatingEvents(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.getRepeatingEvents(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasEvents(java.lang.String groupId,
		java.util.Calendar cal) throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.hasEvents(groupId, cal);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasEvents(java.lang.String groupId,
		java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.hasEvents(groupId, cal, type);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		java.lang.String userId, java.lang.String eventId,
		java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating, com.liferay.util.cal.Recurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			CalEventLocalService calEventLocalService = CalEventLocalServiceFactory.getService();

			return calEventLocalService.updateEvent(userId, eventId, title,
				description, startDateMonth, startDateDay, startDateYear,
				startDateHour, startDateMinute, endDateMonth, endDateDay,
				endDateYear, durationHour, durationMinute, allDay,
				timeZoneSensitive, type, repeating, recurrence, remindBy,
				firstReminder, secondReminder);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}