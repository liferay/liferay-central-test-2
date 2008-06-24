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

package com.liferay.portlet.calendar.util.comparator;

import com.liferay.portal.kernel.util.Time;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.util.CalUtil;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <a href="EventTimeComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Samuel Kong
 *
 */
public class EventTimeComparator implements Comparator {

	public EventTimeComparator(TimeZone timeZone, Locale locale) {
		this.timeZone = timeZone;
		this.locale = locale;
	}

	public int compare(Object o1, Object o2) {
		CalEvent event1 = (CalEvent) o1;
		CalEvent event2 = (CalEvent) o2;

		boolean event1IsAllDay = CalUtil.isAllDay(event1, timeZone, locale);
		boolean event2IsAllDay = CalUtil.isAllDay(event2, timeZone, locale);

		if (event1IsAllDay && event2IsAllDay) {
			return compareTitle(event1, event2);
		}
		else if (event1IsAllDay) {
			return -1;
		}
		else if (event2IsAllDay) {
			return 1;
		}

		int startDateValue = compareTime(
			getStartDate(event1, timeZone), getStartDate(event2, timeZone));

		if (startDateValue != 0) {
			return startDateValue;
		}

		int endDateValue = compareTime(
			getEndDate(event1, timeZone), getEndDate(event2, timeZone));

		if (endDateValue != 0) {
			return endDateValue;
		}

		return compareTitle(event1, event2);
	}

	protected int compareTitle(CalEvent event1, CalEvent event2) {
		return event1.getTitle().toLowerCase().compareTo(
			event2.getTitle().toLowerCase());
	}

	protected int compareTime(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance(timeZone, locale);
		Calendar calendar2 = Calendar.getInstance(timeZone, locale);

		calendar1.setTime(date1);
		calendar2.setTime(date2);

		calendar1.set(Calendar.YEAR, 2000);
		calendar1.set(Calendar.MONTH, 1);
		calendar1.set(Calendar.DAY_OF_YEAR, 1);

		calendar2.set(Calendar.YEAR, 2000);
		calendar2.set(Calendar.MONTH, 1);
		calendar2.set(Calendar.DAY_OF_YEAR, 1);

		return calendar1.compareTo(calendar2);
	}

	protected Date getEndDate(CalEvent event, TimeZone timeZone) {
		if (event.isTimeZoneSensitive()) {
			return Time.getDate(CalUtil.getEndTime(event), timeZone);
		}
		else {
			return CalUtil.getEndTime(event);
		}
	}

	protected Date getStartDate(CalEvent event, TimeZone timeZone) {
		if (event.isTimeZoneSensitive()) {
			return Time.getDate(event.getStartDate(), timeZone);
		}
		else {
			return event.getStartDate();
		}
	}

	protected TimeZone timeZone;
	protected Locale locale;

}