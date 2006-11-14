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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.mail.service.spring.MailServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.im.AIMConnector;
import com.liferay.portal.im.ICQConnector;
import com.liferay.portal.im.MSNConnector;
import com.liferay.portal.im.YMConnector;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.DateFormats;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.EventDurationException;
import com.liferay.portlet.calendar.EventEndDateException;
import com.liferay.portlet.calendar.EventStartDateException;
import com.liferay.portlet.calendar.EventTitleException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.persistence.CalEventFinder;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;
import com.liferay.portlet.calendar.service.spring.CalEventLocalService;
import com.liferay.portlet.calendar.util.CalUtil;
import com.liferay.util.Base64;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.cal.CalendarUtil;
import com.liferay.util.cal.Recurrence;
import com.liferay.util.mail.MailMessage;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CalEventLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventLocalServiceImpl implements CalEventLocalService {

	public CalEvent addEvent(
			String userId, String plid, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, Recurrence recurrence,
			String remindBy, int firstReminder, int secondReminder,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addEvent(
			userId, plid, title, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public CalEvent addEvent(
			String userId, String plid, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, Recurrence recurrence,
			String remindBy, int firstReminder, int secondReminder,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addEvent(
			userId, plid, title, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, null, null, communityPermissions,
			guestPermissions);
	}

	public CalEvent addEvent(
			String userId, String plid, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, Recurrence recurrence,
			String remindBy, int firstReminder, int secondReminder,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Event

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = user.getLocale();
			timeZone = user.getTimeZone();
		}
		else {
			locale = Locale.getDefault();
			timeZone = TimeZone.getDefault();
		}

		Calendar startDate = new GregorianCalendar(timeZone, locale);

		startDate.set(Calendar.MONTH, startDateMonth);
		startDate.set(Calendar.DATE, startDateDay);
		startDate.set(Calendar.YEAR, startDateYear);
		startDate.set(Calendar.HOUR_OF_DAY, startDateHour);
		startDate.set(Calendar.MINUTE, startDateMinute);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		Calendar endDate = new GregorianCalendar(timeZone, locale);

		endDate.set(Calendar.MONTH, endDateMonth);
		endDate.set(Calendar.DATE, endDateDay);
		endDate.set(Calendar.YEAR, endDateYear);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);

		if (allDay) {
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);

			durationHour = 24;
			durationMinute = 0;
		}

		validate(
			title, startDateMonth, startDateDay, startDateYear, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay);

		String eventId = Long.toString(CounterLocalServiceUtil.increment(
			CalEvent.class.getName()));

		CalEvent event = CalEventUtil.create(eventId);

		event.setGroupId(groupId);
		event.setCompanyId(user.getCompanyId());
		event.setUserId(user.getUserId());
		event.setUserName(user.getFullName());
		event.setCreateDate(now);
		event.setModifiedDate(now);
		event.setTitle(title);
		event.setDescription(description);
		event.setStartDate(startDate.getTime());
		event.setEndDate(endDate.getTime());
		event.setDurationHour(durationHour);
		event.setDurationMinute(durationMinute);
		event.setAllDay(allDay);
		event.setTimeZoneSensitive(timeZoneSensitive);
		event.setType(type);
		event.setRepeating(repeating);
		event.setRecurrence(Base64.objectToString(recurrence));
		event.setRemindBy(remindBy);
		event.setFirstReminder(firstReminder);
		event.setSecondReminder(secondReminder);

		CalEventUtil.update(event);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addEventResources(
				event, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addEventResources(event, communityPermissions, guestPermissions);
		}

		// Pool

		CalEventLocalUtil.clearEventsPool(event.getGroupId());

		return event;
	}

	public void addEventResources(
			String eventId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		CalEvent event = CalEventUtil.findByPrimaryKey(eventId);

		addEventResources(
			event, addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(
			CalEvent event, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			event.getCompanyId(), event.getGroupId(), event.getUserId(),
			CalEvent.class.getName(), event.getPrimaryKey().toString(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(
			String eventId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		CalEvent event = CalEventUtil.findByPrimaryKey(eventId);

		addEventResources(event, communityPermissions, guestPermissions);
	}

	public void addEventResources(
			CalEvent event, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			event.getCompanyId(), event.getGroupId(), event.getUserId(),
			CalEvent.class.getName(), event.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void checkEvents() throws PortalException, SystemException {
		Iterator itr = CalEventFinder.findByRemindBy().iterator();

		while (itr.hasNext()) {
			CalEvent event = (CalEvent)itr.next();

			User user = UserUtil.findByPrimaryKey(event.getUserId());

			Calendar now = new GregorianCalendar(
				user.getTimeZone(), user.getLocale());

			if (!event.isTimeZoneSensitive()) {
				Calendar temp = new GregorianCalendar();

				temp.setTime(Time.getDate(now));

				now = temp;
			}

			Calendar startDate = null;

			if (event.isTimeZoneSensitive()) {
				startDate = new GregorianCalendar(
					user.getTimeZone(), user.getLocale());
			}
			else {
				startDate = new GregorianCalendar();
			}

			startDate.setTime(event.getStartDate());

			long diff =
				(startDate.getTime().getTime() - now.getTime().getTime()) /
				Time.MINUTE;

			if ((diff == (event.getFirstReminder() / Time.MINUTE)) ||
				(diff == (event.getSecondReminder() / Time.MINUTE))) {

				remindUser(event, user);
			}
		}
	}

	public void deleteEvent(String eventId)
		throws PortalException, SystemException {

		CalEvent event = CalEventUtil.findByPrimaryKey(eventId);

		deleteEvent(event);
	}

	public void deleteEvent(CalEvent event)
		throws PortalException, SystemException {

		// Pool

		CalEventLocalUtil.clearEventsPool(event.getGroupId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			event.getCompanyId(), CalEvent.class.getName(), Resource.TYPE_CLASS,
			Resource.SCOPE_INDIVIDUAL, event.getPrimaryKey().toString());

		// Event

		CalEventUtil.remove(event.getEventId());
	}

	public void deleteEvents(String groupId)
		throws PortalException, SystemException {

		Iterator itr = CalEventUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			CalEvent event = (CalEvent)itr.next();

			deleteEvent(event);
		}
	}

	public CalEvent getEvent(String eventId)
		throws PortalException, SystemException {

		return CalEventUtil.findByPrimaryKey(eventId);
	}

	public List getEvents(String groupId, String type, int begin, int end)
		throws SystemException {

		if (Validator.isNull(type)) {
			return CalEventUtil.findByGroupId(groupId, begin, end);
		}
		else {
			return CalEventUtil.findByG_T(groupId, type, begin, end);
		}
	}

	public List getEvents(String groupId, Calendar cal) throws SystemException {
		Map eventsPool = CalEventLocalUtil.getEventsPool(groupId);

		String key = CalUtil.toString(cal);

		List events = (List)eventsPool.get(key);

		if (events == null) {

			// Time zone sensitive

			Collection eventsCol1 = CalEventFinder.findByG_SD(
				groupId, CalendarUtil.getGTDate(cal),
				CalendarUtil.getLTDate(cal), true);

			// Time zone insensitive

			Calendar tzICal = new GregorianCalendar(
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));

			Collection eventsCol2 = CalEventFinder.findByG_SD(
				groupId, CalendarUtil.getGTDate(tzICal),
				CalendarUtil.getLTDate(tzICal), false);

			// Create new list

			events = new ArrayList();
			events.addAll(eventsCol1);
			events.addAll(eventsCol2);

			// Add repeating events

			Iterator itr = getRepeatingEvents(groupId).iterator();

			while (itr.hasNext()) {
				CalEvent event = (CalEvent)itr.next();

				Recurrence recurrence = event.getRecurrenceObj();

				if (recurrence.isInRecurrence(
						getRecurrenceCal(cal, tzICal, event))) {

					events.add(event);
				}
			}

			eventsPool.put(key, events);
		}

		return events;
	}

	public List getEvents(String groupId, Calendar cal, String type)
		throws SystemException {

		List events = getEvents(groupId, cal);

		if (Validator.isNull(type)) {
			return events;
		}
		else {
			events = new ArrayList(events);

			Iterator itr = events.iterator();

			while (itr.hasNext()) {
				CalEvent event = (CalEvent)itr.next();

				if (!event.getType().equals(type)) {
					itr.remove();
				}
			}

			return events;
		}
	}

	public int getEventsCount(String groupId, String type)
		throws SystemException {

		if (Validator.isNull(type)) {
			return CalEventUtil.countByGroupId(groupId);
		}
		else {
			return CalEventUtil.countByG_T(groupId, type);
		}
	}

	public List getRepeatingEvents(String groupId) throws SystemException {
		Map eventsPool = CalEventLocalUtil.getEventsPool(groupId);

		String key = "recurrence";

		List events = (List)eventsPool.get(key);

		if (events == null) {
			events = CalEventUtil.findByG_R(groupId, true);

			eventsPool.put(key, events);
		}

		return events;
	}

	public boolean hasEvents(String groupId, Calendar cal)
		throws SystemException {

		return hasEvents(groupId, cal, null);
	}

	public boolean hasEvents(String groupId, Calendar cal, String type)
		throws SystemException {

		if (getEvents(groupId, cal, type).size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public CalEvent updateEvent(
			String userId, String eventId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, Recurrence recurrence,
			String remindBy, int firstReminder, int secondReminder)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = user.getLocale();
			timeZone = user.getTimeZone();
		}
		else {
			locale = Locale.getDefault();
			timeZone = TimeZone.getDefault();
		}

		Calendar startDate = new GregorianCalendar(timeZone, locale);

		startDate.set(Calendar.MONTH, startDateMonth);
		startDate.set(Calendar.DATE, startDateDay);
		startDate.set(Calendar.YEAR, startDateYear);
		startDate.set(Calendar.HOUR_OF_DAY, startDateHour);
		startDate.set(Calendar.MINUTE, startDateMinute);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		Calendar endDate = new GregorianCalendar(timeZone, locale);

		endDate.set(Calendar.MONTH, endDateMonth);
		endDate.set(Calendar.DATE, endDateDay);
		endDate.set(Calendar.YEAR, endDateYear);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);

		if (allDay) {
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);

			durationHour = 24;
			durationMinute = 0;
		}

		validate(
			title, startDateMonth, startDateDay, startDateYear, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay);

		CalEvent event = CalEventUtil.findByPrimaryKey(eventId);

		event.setModifiedDate(new Date());
		event.setTitle(title);
		event.setDescription(description);
		event.setStartDate(startDate.getTime());
		event.setEndDate(endDate.getTime());
		event.setDurationHour(durationHour);
		event.setDurationMinute(durationMinute);
		event.setAllDay(allDay);
		event.setTimeZoneSensitive(timeZoneSensitive);
		event.setType(type);
		event.setRepeating(repeating);
		event.setRecurrence(Base64.objectToString(recurrence));
		event.setRemindBy(remindBy);
		event.setFirstReminder(firstReminder);
		event.setSecondReminder(secondReminder);

		CalEventUtil.update(event);

		CalEventLocalUtil.clearEventsPool(event.getGroupId());

		return event;
	}

	protected Calendar getRecurrenceCal(
		Calendar cal, Calendar tzICal, CalEvent event) {

		Calendar eventCal = new GregorianCalendar();
		eventCal.setTime(event.getStartDate());

		Calendar recurrenceCal = (Calendar)tzICal.clone();
		recurrenceCal.set(
			Calendar.HOUR_OF_DAY, eventCal.get(Calendar.HOUR_OF_DAY));
		recurrenceCal.set(
			Calendar.MINUTE, eventCal.get(Calendar.MINUTE));
		recurrenceCal.set(Calendar.SECOND, 0);
		recurrenceCal.set(Calendar.MILLISECOND, 0);

		if (event.isTimeZoneSensitive()) {
			int gmtDate = eventCal.get(Calendar.DATE);
			long gmtMills = eventCal.getTimeInMillis();

			eventCal.setTimeZone(cal.getTimeZone());

			int tziDate = eventCal.get(Calendar.DATE);
			long tziMills = Time.getDate(eventCal).getTime();

			if (gmtDate != tziDate) {
				int diffDate = 0;

				if (gmtMills > tziMills) {
					diffDate = (int)Math.ceil(
						(double)(gmtMills - tziMills) / Time.DAY);
				}
				else {
					diffDate = (int)Math.floor(
						(double)(gmtMills - tziMills) / Time.DAY);
				}

				recurrenceCal.add(Calendar.DATE, diffDate);
			}
		}

		return recurrenceCal;
	}

	protected void remindUser(CalEvent event, User user) {
		String remindBy = event.getRemindBy();

		if (remindBy.equals(CalEvent.REMIND_BY_NONE)) {
			return;
		}

		try {
			PortletPreferencesPK prefsPK = new PortletPreferencesPK(
				PortletKeys.CALENDAR, PortletKeys.PREFS_LAYOUT_ID_SHARED,
				PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
					event.getGroupId());

			PortletPreferences prefs =
				PortletPreferencesLocalServiceUtil.getPreferences(
					event.getCompanyId(), prefsPK);

			Company company = CompanyUtil.findByPrimaryKey(user.getCompanyId());

			Contact contact = user.getContact();

			String portletName = PortalUtil.getPortletTitle(
				PortletKeys.CALENDAR, user);

			String fromName = CalUtil.getEmailFromName(prefs);
			String fromAddress = CalUtil.getEmailFromAddress(prefs);

			String toName = user.getFullName();
			String toAddress = user.getEmailAddress();

			if (remindBy.equals(CalEvent.REMIND_BY_SMS)) {
				toAddress = contact.getSmsSn();
			}

			String subject = CalUtil.getEmailEventReminderSubject(prefs);
			String body = CalUtil.getEmailEventReminderBody(prefs);

			DateFormat dateFormatDateTime = DateFormats.getDateTime(
				user.getLocale(), user.getTimeZone());

			subject = StringUtil.replace(
				subject,
				new String[] {
					"[$EVENT_START_DATE$]",
					"[$EVENT_TITLE$]",
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]",
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[] {
					dateFormatDateTime.format(event.getStartDate()),
					event.getTitle(),
					fromAddress,
					fromName,
					company.getPortalURL(),
					portletName,
					toAddress,
					toName,
				});

			body = StringUtil.replace(
				body,
				new String[] {
					"[$EVENT_START_DATE$]",
					"[$EVENT_TITLE$]",
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]",
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[] {
					dateFormatDateTime.format(event.getStartDate()),
					event.getTitle(),
					fromAddress,
					fromName,
					company.getPortalURL(),
					portletName,
					toAddress,
					toName,
				});

			if (remindBy.equals(CalEvent.REMIND_BY_EMAIL) ||
				remindBy.equals(CalEvent.REMIND_BY_SMS)) {

				InternetAddress from = new InternetAddress(
					fromAddress, fromName);

				InternetAddress to = new InternetAddress(toAddress, toName);

				MailMessage message = new MailMessage(
					from, to, subject, body, true);

				MailServiceUtil.sendEmail(message);
			}
			else if (remindBy.equals(CalEvent.REMIND_BY_AIM) &&
					 Validator.isNotNull(contact.getAimSn())) {

				AIMConnector.send(contact.getAimSn(), body);
			}
			else if (remindBy.equals(CalEvent.REMIND_BY_ICQ) &&
					 Validator.isNotNull(contact.getIcqSn())) {

				ICQConnector.send(contact.getIcqSn(), body);
			}
			else if (remindBy.equals(CalEvent.REMIND_BY_MSN) &&
					 Validator.isNotNull(contact.getMsnSn())) {

				MSNConnector.send(contact.getMsnSn(), body);
			}
			else if (remindBy.equals(CalEvent.REMIND_BY_YM) &&
					 Validator.isNotNull(contact.getYmSn())) {

				YMConnector.send(contact.getYmSn(), body);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	protected void validate(
			String title, int startDateMonth, int startDateDay,
			int startDateYear, int endDateMonth, int endDateDay,
			int endDateYear, int durationHour, int durationMinute,
			boolean allDay)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new EventTitleException();
		}
		else if (!Validator.isDate(
				startDateMonth, startDateDay, startDateYear)) {

			throw new EventStartDateException();
		}
		else if (!Validator.isDate(endDateMonth, endDateDay, endDateYear)) {
			throw new EventEndDateException();
		}

		if (!allDay && durationHour <= 0 && durationMinute <= 0) {
			throw new EventDurationException();
		}

		Calendar startDate = new GregorianCalendar(
			startDateYear, startDateMonth, startDateDay);

		Calendar endDate = new GregorianCalendar(
			endDateYear, endDateMonth, endDateDay);

		if (startDate.after(endDate)) {
			throw new EventEndDateException();
		}
	}

	private static Log _log = LogFactory.getLog(CalEventLocalServiceImpl.class);

}