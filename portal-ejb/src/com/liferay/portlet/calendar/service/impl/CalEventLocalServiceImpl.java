/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.im.AIMConnector;
import com.liferay.portal.im.ICQConnector;
import com.liferay.portal.im.MSNConnector;
import com.liferay.portal.im.YMConnector;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.DateFormats;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portlet.calendar.EventDurationException;
import com.liferay.portlet.calendar.EventEndDateException;
import com.liferay.portlet.calendar.EventStartDateException;
import com.liferay.portlet.calendar.EventTitleException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.calendar.service.base.CalEventLocalServiceBaseImpl;
import com.liferay.portlet.calendar.service.persistence.CalEventFinder;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;
import com.liferay.portlet.calendar.util.CalUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.cal.CalendarUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Comment;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

import org.apache.commons.id.uuid.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CalEventLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CalEventLocalServiceImpl extends CalEventLocalServiceBaseImpl {

	public CalEvent addEvent(
			long userId, String plid, String title, String description,
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
			long userId, String plid, String title, String description,
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
			long userId, String plid, String title, String description,
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
		long groupId = PortalUtil.getPortletGroupId(plid);
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

		long eventId = CounterLocalServiceUtil.increment();

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
			long eventId, boolean addCommunityPermissions,
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
			CalEvent.class.getName(), event.getEventId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(
			long eventId, String[] communityPermissions,
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
			CalEvent.class.getName(), event.getEventId(), communityPermissions,
			guestPermissions);
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

	public void deleteEvent(long eventId)
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
			event.getCompanyId(), CalEvent.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, event.getEventId());

		// Event

		CalEventUtil.remove(event.getEventId());
	}

	public void deleteEvents(long groupId)
		throws PortalException, SystemException {

		Iterator itr = CalEventUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			CalEvent event = (CalEvent)itr.next();

			deleteEvent(event);
		}
	}

	public File export(long eventId) throws PortalException, SystemException {
		net.fortuna.ical4j.model.Calendar cal = exportICal4j(eventId);

		OutputStream os = null;

		try {
			File file = File.createTempFile("CalEvent." + eventId, ".ics");

			os = new BufferedOutputStream(new FileOutputStream(file.getPath()));

			CalendarOutputter calOutput = new CalendarOutputter();

			calOutput.output(cal, os);

			return file;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
		finally {
			ServletResponseUtil.cleanUp(os);
		}
	}

	public CalEvent getEvent(long eventId)
		throws PortalException, SystemException {

		return CalEventUtil.findByPrimaryKey(eventId);
	}

	public List getEvents(long groupId, String type, int begin, int end)
		throws SystemException {

		if (Validator.isNull(type)) {
			return CalEventUtil.findByGroupId(groupId, begin, end);
		}
		else {
			return CalEventUtil.findByG_T(groupId, type, begin, end);
		}
	}

	public List getEvents(long groupId, Calendar cal) throws SystemException {
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

	public List getEvents(long groupId, Calendar cal, String type)
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

	public int getEventsCount(long groupId, String type)
		throws SystemException {

		if (Validator.isNull(type)) {
			return CalEventUtil.countByGroupId(groupId);
		}
		else {
			return CalEventUtil.countByG_T(groupId, type);
		}
	}

	public List getRepeatingEvents(long groupId) throws SystemException {
		Map eventsPool = CalEventLocalUtil.getEventsPool(groupId);

		String key = "recurrence";

		List events = (List)eventsPool.get(key);

		if (events == null) {
			events = CalEventUtil.findByG_R(groupId, true);

			eventsPool.put(key, events);
		}

		return events;
	}

	public boolean hasEvents(long groupId, Calendar cal)
		throws SystemException {

		return hasEvents(groupId, cal, null);
	}

	public boolean hasEvents(long groupId, Calendar cal, String type)
		throws SystemException {

		if (getEvents(groupId, cal, type).size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public CalEvent updateEvent(
			long userId, long eventId, String title, String description,
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

	protected net.fortuna.ical4j.model.Calendar exportICal4j(long eventId)
		throws PortalException, SystemException {

		net.fortuna.ical4j.model.Calendar iCal =
			new net.fortuna.ical4j.model.Calendar();

		ProdId prodId = new ProdId(
			"-//Liferay Inc//Liferay Portal " + ReleaseInfo.getVersion() +
			"//EN");

		PropertyList props = iCal.getProperties();

		props.add(prodId);
		props.add(Version.VERSION_2_0);
		props.add(CalScale.GREGORIAN);

		CalEvent event = CalEventUtil.findByPrimaryKey(eventId);

		VEvent vEvent = new VEvent();

		PropertyList eventProps = vEvent.getProperties();

		// UID

		Uid uid = new Uid(UUID.timeUUID().toString());

		eventProps.add(uid);

		iCal.getComponents().add(vEvent);

		// Start date

		DateTime dateTime = new DateTime(event.getStartDate());

		DtStart dtStart = new DtStart(dateTime);

		eventProps.add(dtStart);

		// Duration

		Calendar cal = Calendar.getInstance();

		Date start = cal.getTime();

		cal.add(Calendar.HOUR, event.getDurationHour());
		cal.add(Calendar.MINUTE, event.getDurationHour());

		Date end = cal.getTime();

		Duration duration = new Duration(start, end);

		eventProps.add(duration);

		// Summary

		Summary summary = new Summary(event.getTitle());

		eventProps.add(summary);

		// Description

		Description description = new Description(event.getDescription());

		eventProps.add(description);

		// Comment

		Comment comment = new Comment(event.getType());

		eventProps.add(comment);

		// Recurrence rule

		if (event.isRepeating()) {
			Recur recur = toRecur(event.getRecurrenceObj());

			RRule rRule = new RRule(recur);

			eventProps.add(rRule);
		}

		return iCal;
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

		if (remindBy.equals(CalEventImpl.REMIND_BY_NONE)) {
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

			if (remindBy.equals(CalEventImpl.REMIND_BY_SMS)) {
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

			if (remindBy.equals(CalEventImpl.REMIND_BY_EMAIL) ||
				remindBy.equals(CalEventImpl.REMIND_BY_SMS)) {

				InternetAddress from = new InternetAddress(
					fromAddress, fromName);

				InternetAddress to = new InternetAddress(toAddress, toName);

				MailMessage message = new MailMessage(
					from, to, subject, body, true);

				MailServiceUtil.sendEmail(message);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_AIM) &&
					 Validator.isNotNull(contact.getAimSn())) {

				AIMConnector.send(contact.getAimSn(), body);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_ICQ) &&
					 Validator.isNotNull(contact.getIcqSn())) {

				ICQConnector.send(contact.getIcqSn(), body);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_MSN) &&
					 Validator.isNotNull(contact.getMsnSn())) {

				MSNConnector.send(contact.getMsnSn(), body);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_YM) &&
					 Validator.isNotNull(contact.getYmSn())) {

				YMConnector.send(contact.getYmSn(), body);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	protected Recur toRecur(Recurrence recurrence) {
		Recur recur = null;

		int recurrenceType = recurrence.getFrequency();

		int interval = recurrence.getInterval();

		if (recurrenceType == Recurrence.DAILY) {
			recur = new Recur(Recur.DAILY, -1);

			if (interval >= 0) {
				recur.setInterval(interval);
			}
			else {
				DayAndPosition[] byDay = recurrence.getByDay();

				for (int i = 0; i < byDay.length; i++) {
					WeekDay weekDay = toWeekDay(byDay[i].getDayOfWeek());

					recur.getDayList().add(weekDay);
				}
			}
		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			recur = new Recur(Recur.WEEKLY, -1);

			recur.setInterval(interval);

			DayAndPosition[] byDay = recurrence.getByDay();

			for (int i = 0; i < byDay.length; i++) {
				WeekDay weekDay = toWeekDay(byDay[i].getDayOfWeek());

				recur.getDayList().add(weekDay);
			}
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			recur = new Recur(Recur.MONTHLY, -1);

			recur.setInterval(interval);

			int[] byMonthDay = recurrence.getByMonthDay();

			if (byMonthDay != null) {
				Integer monthDay = new Integer(byMonthDay[0]);

				recur.getMonthDayList().add(monthDay);
			}
			else {
				DayAndPosition[] byDay = recurrence.getByDay();

				WeekDay weekDay = toWeekDay(byDay[0].getDayOfWeek());

				recur.getDayList().add(weekDay);

				Integer position = new Integer(byDay[0].getDayPosition());

				recur.getSetPosList().add(position);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			recur = new Recur(Recur.YEARLY, -1);

			recur.setInterval(interval);

			int[] byMonthDay = recurrence.getByMonthDay();

			Integer month = new Integer(recurrence.getByMonth()[0]);

			recur.getMonthList().add(month);

			if (byMonthDay != null) {
				Integer monthDay = new Integer(byMonthDay[0]);

				recur.getMonthDayList().add(monthDay);
			}
			else {
				DayAndPosition[] byDay = recurrence.getByDay();

				WeekDay weekDay = toWeekDay(byDay[0].getDayOfWeek());

				recur.getDayList().add(weekDay);

				Integer position = new Integer(byDay[0].getDayPosition());

				recur.getSetPosList().add(position);
			}
		}

		Calendar until = recurrence.getUntil();

		if (until != null) {
			DateTime dateTime = new DateTime(until.getTime());

			recur.setUntil(dateTime);
		}

		return recur;
	}

	protected WeekDay toWeekDay(int dayOfWeek) {
		WeekDay weekDay = null;

		if (dayOfWeek == Calendar.SUNDAY) {
			weekDay = WeekDay.SU;
		}
		else if (dayOfWeek == Calendar.MONDAY){
			weekDay = WeekDay.MO;
		}
		else if (dayOfWeek == Calendar.TUESDAY){
			weekDay = WeekDay.TU;
		}
		else if (dayOfWeek == Calendar.WEDNESDAY){
			weekDay = WeekDay.WE;
		}
		else if (dayOfWeek == Calendar.THURSDAY){
			weekDay = WeekDay.TH;
		}
		else if (dayOfWeek == Calendar.FRIDAY){
			weekDay = WeekDay.FR;
		}
		else if (dayOfWeek == Calendar.SATURDAY){
			weekDay = WeekDay.SA;
		}

		return weekDay;
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