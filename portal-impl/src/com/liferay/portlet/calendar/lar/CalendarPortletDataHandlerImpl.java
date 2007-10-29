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

package com.liferay.portlet.calendar.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="CalendarPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class CalendarPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] { _enableExport };
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] { _enableImport };
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_CALENDAR_DATA,
			_enableExport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("calendar-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Events

			List events = CalEventUtil.findByGroupId(context.getGroupId());

			Iterator itr = events.iterator();

			while (itr.hasNext()) {
				CalEvent event = (CalEvent) itr.next();

				if (context.addPrimaryKey(
						CalEvent.class, event.getPrimaryKeyObj())) {

					itr.remove();
				}
			}

			String xml = xStream.toXML(events);

			Element el = root.addElement("calendar-events");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_CALENDAR_DATA,
			_enableImport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Events

			Element el = root.element("calendar-events").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List events = (List) xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = events.iterator();

			while (itr.hasNext()) {
				CalEvent event = (CalEvent) itr.next();

				importEvent(context, event);
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importEvent(PortletDataContext context, CalEvent event)
		throws Exception {

		Map parameterMap = context.getParameterMap();

		boolean mergeData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.MERGE_DATA,
			_enableExport.getDefaultState());

		CalEvent existingEvent = CalEventUtil.fetchByUUID_G(
			event.getUuid(), context.getGroupId());

		if (existingEvent == null) {
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

			if (mergeData) {
				CalEventLocalServiceUtil.addEvent(
					event.getUuid(), event.getUserId(), plid, event.getTitle(),
					event.getDescription(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear,
					event.getDurationHour(), event.getDurationMinute(),
					event.getAllDay(), event.getTimeZoneSensitive(),
					event.getType(), event.getRepeating(),
					event.getRecurrenceObj(), event.getRemindBy(),
					event.getFirstReminder(), event.getSecondReminder(),
					addCommunityPermissions, addGuestPermissions);
			}
			else {
				CalEventLocalServiceUtil.addEvent(
					event.getUserId(), plid, event.getTitle(),
					event.getDescription(), startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear,
					event.getDurationHour(), event.getDurationMinute(),
					event.getAllDay(), event.getTimeZoneSensitive(),
					event.getType(), event.getRepeating(),
					event.getRecurrenceObj(), event.getRemindBy(),
					event.getFirstReminder(), event.getSecondReminder(),
					addCommunityPermissions, addGuestPermissions);
			}
		}
		else {
			event.setGroupId(existingEvent.getGroupId());
			event.setPrimaryKey(existingEvent.getPrimaryKey());

			CalEventUtil.update(event, true);
		}
	}

	private static final String _EXPORT_CALENDAR_DATA =
		"export-" + PortletKeys.CALENDAR + "-data";

	private static final String _IMPORT_CALENDAR_DATA =
		"import-" + PortletKeys.CALENDAR + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_CALENDAR_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_CALENDAR_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(CalendarPortletDataHandlerImpl.class);

}