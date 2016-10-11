/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.events.display.web.internal.util.comparator;

import com.liferay.calendar.model.CalendarBooking;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Matthew Kong
 */
public class CalendarBookingTimeComparator
	implements Comparator<CalendarBooking> {

	public CalendarBookingTimeComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		CalendarBooking calendarBooking1, CalendarBooking calendarBooking2) {

		if (calendarBooking1.isAllDay() && calendarBooking2.isAllDay()) {
			return compareTitle(calendarBooking1, calendarBooking2);
		}
		else if (calendarBooking1.isAllDay()) {
			return -1;
		}
		else if (calendarBooking2.isAllDay()) {
			return 1;
		}

		Long startTime1 = calendarBooking1.getStartTime();
		Long startTime2 = calendarBooking2.getStartTime();

		int value = startTime1.compareTo(startTime2);

		if (value != 0) {
			return value;
		}

		Long duration1 = calendarBooking1.getDuration();
		Long duration2 = calendarBooking2.getDuration();

		value = duration1.compareTo(duration2);

		if (value != 0) {
			return value;
		}

		return compareTitle(calendarBooking1, calendarBooking2);
	}

	protected int compareTitle(
		CalendarBooking calendarBooking1, CalendarBooking calendarBooking2) {

		String title1 = calendarBooking1.getTitle(_locale);
		String title2 = calendarBooking2.getTitle(_locale);

		return title1.compareToIgnoreCase(title2);
	}

	private final Locale _locale;

}