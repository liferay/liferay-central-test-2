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

package com.liferay.portlet.calendar.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="CalendarPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class CalendarPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
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
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			context.addPermissions(
				"com.liferay.portlet.calendar", context.getGroupId());

			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("calendar-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			List<CalEvent> events = CalEventUtil.findByGroupId(
				context.getGroupId());

			for (CalEvent event : events) {
				exportEvent(context, root, event);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_events, _categories, _comments, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_events, _categories, _comments, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			context.importPermissions(
				"com.liferay.portlet.calendar", context.getSourceGroupId(),
				context.getGroupId());

			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> eventsEl = root.elements("event");

			for (Element eventEl : eventsEl) {
				String path = eventEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				CalEvent event = (CalEvent)context.getZipEntryAsObject(path);

				importEvent(context, event);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void exportEvent(
			PortletDataContext context, Element root, CalEvent event)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(event.getModifiedDate())) {
			return;
		}

		String path = getEventPath(context, event);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element eventEl = root.addElement("event");

		eventEl.addAttribute("path", path);

		context.addPermissions(CalEvent.class, event.getEventId());

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			context.addAssetCategories(CalEvent.class, event.getEventId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(CalEvent.class, event.getEventId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addAssetTags(CalEvent.class, event.getEventId());
		}

		event.setUserUuid(event.getUserUuid());

		context.addZipEntry(path, event);
	}

	protected String getEventPath(PortletDataContext context, CalEvent event) {
		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.CALENDAR));
		sb.append("/events/");
		sb.append(event.getEventId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEvent(PortletDataContext context, CalEvent event)
		throws Exception {

		long userId = context.getUserId(event.getUserUuid());

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

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				CalEvent.class, event.getEventId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				CalEvent.class, event.getEventId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(event.getCreateDate());
		serviceContext.setModifiedDate(event.getModifiedDate());
		serviceContext.setScopeGroupId(context.getGroupId());

		CalEvent importedEvent = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			CalEvent existingEvent = CalEventUtil.fetchByUUID_G(
				event.getUuid(), context.getGroupId());

			if (existingEvent == null) {
				importedEvent = CalEventLocalServiceUtil.addEvent(
					event.getUuid(), userId, event.getTitle(),
					event.getDescription(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute, endDateMonth,
					endDateDay, endDateYear, event.getDurationHour(),
					event.getDurationMinute(), event.getAllDay(),
					event.getTimeZoneSensitive(), event.getType(),
					event.getRepeating(), event.getRecurrenceObj(),
					event.getRemindBy(), event.getFirstReminder(),
					event.getSecondReminder(), serviceContext);
			}
			else {
				importedEvent = CalEventLocalServiceUtil.updateEvent(
					userId, existingEvent.getEventId(), event.getTitle(),
					event.getDescription(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute, endDateMonth,
					endDateDay, endDateYear, event.getDurationHour(),
					event.getDurationMinute(), event.getAllDay(),
					event.getTimeZoneSensitive(), event.getType(),
					event.getRepeating(), event.getRecurrenceObj(),
					event.getRemindBy(), event.getFirstReminder(),
					event.getSecondReminder(), serviceContext);
			}
		}
		else {
			importedEvent = CalEventLocalServiceUtil.addEvent(
				null, userId, event.getTitle(), event.getDescription(),
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, endDateMonth, endDateDay, endDateYear,
				event.getDurationHour(), event.getDurationMinute(),
				event.getAllDay(), event.getTimeZoneSensitive(),
				event.getType(), event.getRepeating(), event.getRecurrenceObj(),
				event.getRemindBy(), event.getFirstReminder(),
				event.getSecondReminder(), serviceContext);
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				CalEvent.class, event.getEventId(),
				importedEvent.getEventId(), context.getGroupId());
		}

		context.importPermissions(
			CalEvent.class, event.getEventId(), importedEvent.getEventId());
	}

	private static final String _NAMESPACE = "calendar";

	private static final PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _events =
		new PortletDataHandlerBoolean(_NAMESPACE, "events", true, true);

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}