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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Recurrence;

/**
 * <a href="RecurrenceMonthlyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceMonthlyTest extends RecurrenceTestCase {

	public void testByDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		recurrence.setByDay(dayPos);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
	}

	public void testByDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] months = {MARCH, APRIL};

		recurrence.setByDay(dayPos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
	}

	public void testByDayAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] months = {MARCH, APRIL};

		// March 23, 25, 27, 30, 31, April 3, 24, May 22

		int[] yearDays = {82, 84, 86, 89, 90, 93, 114, 142};

		recurrence.setByDay(dayPos);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
	}

	public void testByDayAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] monthDays = {22, 25, 26, 27};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 29);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 29);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 27);
	}

	public void testByDayAndMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] monthDays = {22, 23, 25, 26, 27};
		int[] months = {MARCH, APRIL};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 22, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
}

	public void testByDayAndMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] monthDays = {22, 23, 25, 26, 27};

		// March 23, 25, 27, 30, 31, April 3, 24, May 22

		int[] yearDays = {82, 84, 86, 89, 90, 93, 114, 142};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
	}

	public void testByDayAndWeekNo() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] weekNos = {13, 17, 21, 27, 30};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, JULY, 20);
	}

	public void testByDayAndWeekNoAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] months = {MARCH, APRIL};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 28);
	}

	public void testByDayAndWeekNoAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {22, 25, 26, 27, 29};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2010, JULY, 26);
	}

	public void testByDayAndWeekNoAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		int[] weekNos = {13, 17, 21, 27, 30};

		// March 23, 25, 27, 29, 31, April 3, 22, May 22, 23, 25

		int[] yearDays = {82, 84, 86, 88, 90, 93, 112, 142, 143, 145};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 23);
	}

	public void testByDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(FRIDAY, 4)};

		// March 23, 25, 27, 30, 31, April 3, 24, May 22

		int[] yearDays = {82, 84, 86, 89, 90, 93, 114, 142};

		recurrence.setByDay(dayPos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);
	}

	public void testByMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] months = {MARCH, APRIL};

		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 23, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);
	}

	public void testByMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] months = {MARCH, APRIL};

		// March 23, 31, April 1, 23, May 2, 22, June 3

		int[] yearDays = {82, 90, 91, 113, 122, 142, 154};

		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 3);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2010, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 3);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2010, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 23, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
	}

	public void testByMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] monthDays = {22, 23};

		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 21);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2011, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2011, APRIL, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 21);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2011, APRIL, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 23, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
	}

	public void testByMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] monthDays = {22, 23};
		int[] months = {MARCH, APRIL, JUNE};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 21);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 21);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 22, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 22);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
	}

	public void testByMonthDayAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] monthDays = {22, 23};
		int[] months = {MARCH, MAY, JUNE};

		// March 23, 24, April 22, May 22, June 23

		int[] yearDays = {82, 83, 112, 142, 174};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// End date

		recurrence.setUntil(getCalendar(2011, MAY, 22, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 22);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
	}

	public void testByMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] monthDays = {22, 23};

		// March 23, 24, April 22, May 22, June 23

		int[] yearDays = {82, 83, 112, 142, 174};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2011, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 22);

		// End date

		recurrence.setUntil(getCalendar(2011, MAY, 22, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MAY, 22);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
	}

	public void testByWeekNo() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};

		recurrence.setByWeekNo(weekNos);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, JULY, 20);
	}

	public void testByWeekNoAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] months = {MARCH, APRIL};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 28);
	}

	public void testByWeekNoAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] months = {MARCH, APRIL};

		// March 23, 25, 27, 29, 31, April 3, 22, May 22, 23, 25

		int[] yearDays = {82, 84, 86, 88, 90, 93, 112, 142, 143, 145};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
}

	public void testByWeekNoAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {22, 25, 26, 27, 29};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
	}

	public void testByWeekNoAndMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {22, 25, 26, 27, 29};
		int[] months = {MARCH, APRIL};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
	}

	public void testByWeekNoAndMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {22, 25, 26, 27, 29};

		// March 23, 25, 27, 29, 31, April 3, 22, May 22, 23, 25

		int[] yearDays = {82, 84, 86, 88, 90, 93, 112, 142, 143, 145};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByWeekNoAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		int[] weekNos = {13, 17, 21, 27, 30};

		// March 23, 25, 27, 29, 31, April 3, 22, May 22, 23, 25

		int[] yearDays = {82, 84, 86, 88, 90, 93, 112, 142, 143, 145};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2013, APRIL, 22);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2013, APRIL, 22);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		// March 23, 25, 27, 30, 31, April 3, 24, May 22

		int[] yearDays = {82, 84, 86, 89, 90, 93, 114, 142};

		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testMonthly() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.MONTHLY);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, JUNE, 23);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 29);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, JUNE, 23);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2013, MARCH, 23);

		// End date

		recurrence.setUntil(getCalendar(2013, MARCH, 23, 0, 0));

		assertIsInRecurrence(true, recurrence, 2012, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2013, MARCH, 23);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, JULY, 23);
	}

}