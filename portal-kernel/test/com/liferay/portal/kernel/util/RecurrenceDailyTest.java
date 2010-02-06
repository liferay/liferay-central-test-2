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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Recurrence;

/**
 * <a href="RecurrenceDailyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceDailyTest extends RecurrenceTestCase {

	public void testByDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		recurrence.setByDay(dayPos);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 2, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] months = {MARCH, MAY};

		recurrence.setByDay(dayPos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 4);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 1);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 4);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 28);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 1);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 6, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 4);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByDayAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] months = {MARCH, MAY};

		// March 25, 28, April 1, May 6, 7

		int[] yearDays = {84, 87, 91, 126, 127};

		recurrence.setByDay(dayPos);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2010, MAY, 7);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(true, recurrence, 2010, MAY, 7);

		// End date

		recurrence.setUntil(getCalendar(2010, MAY, 7, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2010, MAY, 7);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);
	}

	public void testByDayAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] monthDays = {2, 5, 20, 25, 30};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 1);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 1);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 30, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
	}

	public void testByDayAndMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] monthDays = {2, 5, 20, 25, 30};

		int[] months = {MARCH, MAY};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 20);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 20, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 20);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 20);
	}

	public void testByDayAndMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] monthDays = {1, 2, 5, 6, 20, 25, 30};

		// March 25, 28, April 1, May 6, 7

		int[] yearDays = {84, 87, 91, 126, 127};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 25);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 25, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 25);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);
	}

	public void testByDayAndWeekNo() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] weekNos = {13, 15};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 8);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 9);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 11);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 8);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 9);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 11);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 8, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 8);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
	}

	public void testByDayAndWeekNoAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] weekNos = {13, 15, 20, 22};

		int[] months = {MARCH, MAY};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 13);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 26);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 27);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 13);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 26);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 27);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 14);
	}

	public void testByDayAndWeekNoAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] weekNos = {13, 15, 18, 20};

		int[] monthDays = {6, 12, 15, 22, 27, 28};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 15);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 21);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 27);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 28);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 15);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 28);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 15);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 21);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 22);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 27);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 28);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 15);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 28);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 12, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 28);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 12);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
	}

	public void testByDayAndWeekNoAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		int[] weekNos = {13, 15, 19};

		// March 25, 27, 28, April 1, May 6, 7

		int[] yearDays = {84, 86, 87, 91, 126, 127};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 6, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		// March 25, 27, 28, April 1, May 6, 7

		int[] yearDays = {84, 86, 87, 91, 126, 127};

		recurrence.setByDay(dayPos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 6, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] months = {MARCH, MAY};

		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 1);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 1);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 1);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 2);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 1);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 2, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 2);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] months = {MARCH, MAY};

		// March 25, 27, 28, April 1, May 6, 7

		int[] yearDays = {84, 86, 87, 91, 126, 127};

		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 1);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 1);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 6, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] monthDays = {2, 5, 20, 25, 30};

		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 30, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
	}

	public void testByMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] monthDays = {2, 5, 20, 25, 30};
		int[] months = {APRIL};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 30, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
	}

	public void testByMonthDayAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] monthDays = {2, 5, 20, 25, 30};
		int[] months = {APRIL};

		// March 25, 28, April 1, 2, 5, 20, 30

		int[] yearDays = {84, 87, 91, 92, 95, 110, 120};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 30, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
	}

	public void testByMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] monthDays = {2, 5, 20, 25, 30};

		// March 25, 28, April 1, 5, 20, 30

		int[] yearDays = {84, 87, 91, 95, 110, 120};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 2);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 30);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 30, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 30);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
	}

	public void testByWeekNo() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};

		recurrence.setByWeekNo(weekNos);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 7);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 7);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, JUNE, 8, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
	}

	public void testByWeekNoAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};
		int[] months = {MARCH, MAY};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 14, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 14);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 12);
	}

	public void testByWeekNoAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};
		int[] months = {MARCH, MAY};

		// March 24, 27, April 1, 5, 12, May 11, 12, 14, 31, June 5

		int[] yearDays = {83, 86, 91, 95, 102, 131, 132, 134, 151, 156};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 11);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 14, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 14);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 12);
	}

	public void testByWeekNoAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};
		int[] monthDays = {5, 6, 12, 18, 24, 27, 30, 31};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, JUNE, 5, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
	}

	public void testByWeekNoAndMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};
		int[] monthDays = {5, 6, 12, 14, 18, 24, 27, 30, 31};
		int[] months = {MARCH, MAY};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 14, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 14);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 12);
	}

	public void testByWeekNoAndMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};
		int[] monthDays = {5, 6, 12, 18, 24, 27, 30, 31};

		// March 24, 27, April 1, 5, 12, May 12, 14, 31, June 5

		int[] yearDays = {83, 86, 91, 95, 102, 132, 134, 151, 156};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, JUNE, 5, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 12);
	}

	public void testByWeekNoAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		int[] weekNos = {13, 15, 20, 23};

		// March 24, 27, April 1, 5, 12, May 12, 14, 31, June 5

		int[] yearDays = {83, 86, 91, 95, 102, 132, 134, 151, 156};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		//assertIsInRecurrence(true, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 6);
		//assertIsInRecurrence(false, recurrence, 2009, APRIL, 12);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 12);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 31);
		assertIsInRecurrence(true, recurrence, 2009, JUNE, 5);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, JUNE, 5, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 14);
		assertIsInRecurrence(false, recurrence, 2009, JUNE, 5);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 12);
	}

	public void testByYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		// March 25, 28, April 1, May 4, 6, 7

		int[] yearDays = {84, 87, 91, 124, 126, 127};

		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 4);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 4);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 6);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 7);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 8);

		// End date

		recurrence.setUntil(getCalendar(2009, MAY, 6, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 4);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 6);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 4);
	}

	public void testDaily() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.DAILY);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);

		// Interval

		recurrence.setInterval(3);

		assertIsInRecurrence(false, recurrence, 2009, MARCH, 24);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 25);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 29);

		// End date

		recurrence.setUntil(getCalendar(2009, APRIL, 4, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 1);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
	}

}