/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;
import com.liferay.util.xml.XMLFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="CalendarPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class CalendarPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {

			// Events

			if (!context.addPrimaryKey(
					CalendarPortletDataHandlerImpl.class, "deleteData")) {

				CalEventLocalServiceUtil.deleteEvents(context.getGroupId());
			}
			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("calendar-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Events

			List<CalEvent> events = CalEventUtil.findByGroupId(
				context.getGroupId());

			for (CalEvent event : events) {
				exportEvent(context, root, event);
			}

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_events};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_events};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Events

			List<Element> events = root.elements("event");

			for (Element el : events) {
				String path = el.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					CalEvent event =
						(CalEvent)context.getZipEntryAsObject(path);

					importEvent(context, event);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	protected void exportEvent(
			PortletDataContext context, Element el, CalEvent event)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(event.getModifiedDate())) {
			String path = getEventPath(context, event);

			el.addElement("event").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				event.setUserUuid(event.getUserUuid());

				context.addZipEntry(path, event);
			}
		}
	}

	protected void importEvent(PortletDataContext context, CalEvent event)
		throws Exception {

		long userId = context.getUserId(event.getUserUuid());
		long plid = context.getPlid();

		Date startDate = event.getStartDate();

		int startDateMonth = 0;
		int startDateDay = 0;
		int startDateYear = 0;
		int startDateHour = 0;
		int startDateMinute = 0;

		if (startDate != null) {
			Calendar startCal = CalendarFactoryUtil.getCalendar();

			startCal.setTime(startDate);

			startDateMonth = startCal.get(Calendar.MONTH);
			startDateDay = startCal.get(Calendar.DATE);
			startDateYear = startCal.get(Calendar.YEAR);
			startDateHour = startCal.get(Calendar.HOUR);
			startDateMinute = startCal.get(Calendar.MINUTE);

			if (startCal.get(Calendar.AM_PM) == Calendar.PM) {
				startDateHour += 12;
			}
		}

		Date endDate = event.getEndDate();

		int endDateMonth = 0;
		int endDateDay = 0;
		int endDateYear = 0;

		if (endDate != null) {
			Calendar endCal = CalendarFactoryUtil.getCalendar();

			endCal.setTime(endDate);

			endDateMonth = endCal.get(Calendar.MONTH);
			endDateDay = endCal.get(Calendar.DATE);
			endDateYear = endCal.get(Calendar.YEAR);
		}

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		CalEvent existingEvent = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingEvent = CalEventUtil.fetchByUUID_G(
				event.getUuid(), context.getGroupId());

			if (existingEvent == null) {
				CalEventLocalServiceUtil.addEvent(
					event.getUuid(), userId, plid, event.getTitle(),
					event.getDescription(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute, endDateMonth,
					endDateDay, endDateYear, event.getDurationHour(),
					event.getDurationMinute(), event.getAllDay(),
					event.getTimeZoneSensitive(), event.getType(),
					event.getRepeating(), event.getRecurrenceObj(),
					event.getRemindBy(), event.getFirstReminder(),
					event.getSecondReminder(), addCommunityPermissions,
					addGuestPermissions);
			}
			else {
				CalEventLocalServiceUtil.updateEvent(
					userId, existingEvent.getEventId(), event.getTitle(),
					event.getDescription(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute, endDateMonth,
					endDateDay, endDateYear, event.getDurationHour(),
					event.getDurationMinute(), event.getAllDay(),
					event.getTimeZoneSensitive(), event.getType(),
					event.getRepeating(), event.getRecurrenceObj(),
					event.getRemindBy(), event.getFirstReminder(),
					event.getSecondReminder());
			}
		}
		else {
			CalEventLocalServiceUtil.addEvent(
				userId, plid, event.getTitle(), event.getDescription(),
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, endDateMonth, endDateDay, endDateYear,
				event.getDurationHour(), event.getDurationMinute(),
				event.getAllDay(), event.getTimeZoneSensitive(),
				event.getType(), event.getRepeating(), event.getRecurrenceObj(),
				event.getRemindBy(), event.getFirstReminder(),
				event.getSecondReminder(), addCommunityPermissions,
				addGuestPermissions);
		}
	}

	protected String getEventPath(
			PortletDataContext context, CalEvent event) {
		return context.getPortletPath(PortletKeys.CALENDAR) + "/events/" +
			event.getEventId() + ".xml";
	}

	private static final String _NAMESPACE = "calendar";

	private static final PortletDataHandlerBoolean _events =
		new PortletDataHandlerBoolean(_NAMESPACE, "events", true, true);

}