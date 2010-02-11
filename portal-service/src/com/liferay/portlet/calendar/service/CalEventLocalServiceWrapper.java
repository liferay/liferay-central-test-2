/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.service;


/**
 * <a href="CalEventLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link CalEventLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventLocalService
 * @generated
 */
public class CalEventLocalServiceWrapper implements CalEventLocalService {
	public CalEventLocalServiceWrapper(
		CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	public com.liferay.portlet.calendar.model.CalEvent addCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.addCalEvent(calEvent);
	}

	public com.liferay.portlet.calendar.model.CalEvent createCalEvent(
		long eventId) {
		return _calEventLocalService.createCalEvent(eventId);
	}

	public void deleteCalEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.deleteCalEvent(eventId);
	}

	public void deleteCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		_calEventLocalService.deleteCalEvent(calEvent);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.calendar.model.CalEvent getCalEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _calEventLocalService.getCalEvent(eventId);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEvents(
		int start, int end) throws com.liferay.portal.SystemException {
		return _calEventLocalService.getCalEvents(start, end);
	}

	public int getCalEventsCount() throws com.liferay.portal.SystemException {
		return _calEventLocalService.getCalEventsCount();
	}

	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.updateCalEvent(calEvent);
	}

	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent, boolean merge)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.updateCalEvent(calEvent, merge);
	}

	public com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String uuid, long userId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _calEventLocalService.addEvent(uuid, userId, title, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear,
			durationHour, durationMinute, allDay, timeZoneSensitive, type,
			repeating, recurrence, remindBy, firstReminder, secondReminder,
			serviceContext);
	}

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.addEventResources(event, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.addEventResources(event, communityPermissions,
			guestPermissions);
	}

	public void addEventResources(long eventId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.addEventResources(eventId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(long eventId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.addEventResources(eventId, communityPermissions,
			guestPermissions);
	}

	public void checkEvents()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.checkEvents();
	}

	public void deleteEvent(com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.deleteEvent(event);
	}

	public void deleteEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.deleteEvent(eventId);
	}

	public void deleteEvents(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.deleteEvents(groupId);
	}

	public java.io.File exportEvent(long userId, long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _calEventLocalService.exportEvent(userId, eventId);
	}

	public java.io.File exportGroupEvents(long userId, long groupId,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _calEventLocalService.exportGroupEvents(userId, groupId, fileName);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCompanyEvents(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getCompanyEvents(companyId, start, end);
	}

	public int getCompanyEventsCount(long companyId)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getCompanyEventsCount(companyId);
	}

	public com.liferay.portlet.calendar.model.CalEvent getEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _calEventLocalService.getEvent(eventId);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getEvents(groupId, cal);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getEvents(groupId, cal, type);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getEvents(groupId, type, start, end);
	}

	public int getEventsCount(long groupId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getEventsCount(groupId, type);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getNoAssetEvents()
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.getNoAssetEvents();
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId) throws com.liferay.portal.SystemException {
		return _calEventLocalService.getRepeatingEvents(groupId);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal)
		throws com.liferay.portal.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal, type);
	}

	public void importICal4j(long userId, long groupId, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_calEventLocalService.importICal4j(userId, groupId, file);
	}

	public com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long userId, long eventId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _calEventLocalService.updateEvent(userId, eventId, title,
			description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public CalEventLocalService getWrappedCalEventLocalService() {
		return _calEventLocalService;
	}

	private CalEventLocalService _calEventLocalService;
}