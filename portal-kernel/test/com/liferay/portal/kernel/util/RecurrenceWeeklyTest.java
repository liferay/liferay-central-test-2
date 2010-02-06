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
 * <a href="RecurrenceWeeklyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceWeeklyTest extends RecurrenceTestCase {

	public void testByDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		recurrence.setByDay(dayPos);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 5);
	}

	public void testByDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] months = {MARCH, MAY};

		recurrence.setByDay(dayPos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 3);
	}

	public void testByDayAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] months = {MARCH, MAY};

		// March 23, 26, 27, 28, 30, 31, April 3, 23, May 24

		int[] yearDays = {82, 85, 86, 87, 89, 90, 93, 113, 144};

		recurrence.setByDay(dayPos);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
	}

	public void testByDayAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] monthDays = {22, 23, 26, 28, 30};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
	}

	public void testByDayAndMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] monthDays = {22, 23, 26, 28, 30};
		int[] months = {MARCH};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
	}

	public void testByDayAndMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] monthDays = {11, 22, 23, 26, 28, 30};

		// March 23, 26, 27, 30, 31, April 3, 11, 23, May 23

		int[] yearDays = {82, 85, 86, 89, 90, 93, 101, 113, 143};

		recurrence.setByDay(dayPos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 11);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 11);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 11);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
	}

	public void testByDayAndWeekNo() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] weekNos = {13, 17, 21, 27, 30};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 19);
	}

	public void testByDayAndWeekNoAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] months = {MARCH, MAY};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
	}

	public void testByDayAndWeekNoAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {22, 23, 26, 28, 30};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
	}

	public void testByDayAndWeekNoAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		int[] weekNos = {13, 17, 21, 27, 30};

		// March 23, 26, 27, 30, 31, April 3, 25, May 23

		int[] yearDays = {82, 85, 86, 89, 90, 93, 115, 143};

		recurrence.setByDay(dayPos);
		recurrence.setByWeekNo(weekNos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2010, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2010, APRIL, 3);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2010, APRIL, 3, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2010, APRIL, 3);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
	}

	public void testByDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		DayAndPosition[] dayPos = {
			new DayAndPosition(SUNDAY, 0),
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		// March 23, 26, 27, 30, 31, April 3, 25, May 23

		int[] yearDays = {82, 85, 86, 89, 90, 93, 115, 143};

		recurrence.setByDay(dayPos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
}

	public void testByMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] months = {MARCH, MAY};

		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 21);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 21);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 21, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 21);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
	}

	public void testByMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] months = {MARCH, MAY};

		// March 23, 26, 27, 30, 31, April 3, 23, May 24

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 144};

		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		//assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 27);
	}

	public void testByMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] monthDays = {22, 23, 26, 28, 30};

		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 26);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 26);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
	}

	public void testByMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] monthDays = {22, 23, 26, 28, 30};
		int[] months = {MARCH, MAY};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 25);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
	}

	public void testByMonthDayAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] monthDays = {22, 23, 26, 28, 30};
		int[] months = {MARCH, MAY};

		// March 23, 26, 27, 30, 31, April 3, 23, May 22

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 142};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 22);
	}

	public void testByMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] monthDays = {22, 23, 26, 28, 30};

		// March 23, 26, 27, 30, 31, April 3, 23, May 22

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 142};

		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
	}

	public void testByWeekNo() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 21, 27, 30};

		recurrence.setByWeekNo(weekNos);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
	}

	public void testByWeekNoAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 20, 21, 27, 30};
		int[] months = {MARCH, MAY};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2010, MAY, 17);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2010, MAY, 17);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MAY, 17);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2010, MAY, 17);
	}

	public void testByWeekNoAndMonthAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 20, 21, 27, 30};
		int[] months = {MARCH, MAY};

		// March 23, 26, 27, 30, 31, April 3, 23, May 17, 18

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 137, 138};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonth(months);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2010, MAY, 17);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2010, MAY, 17);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MAY, 17);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2010, MAY, 17);
	}

	public void testByWeekNoAndMonthDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {18, 22, 23, 26, 28, 30};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
	}

	public void testByWeekNoAndMonthDayAndMonth() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {18, 22, 23, 26, 28, 30};
		int[] months = {MARCH, MAY};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByMonth(months);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		//assertIsInRecurrence(true, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		//assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
	}

	public void testByWeekNoAndMonthDayAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 21, 27, 30};
		int[] monthDays = {22, 23, 26, 28, 30};

		// March 23, 26, 27, 30, 31, April 3, 23, May 18

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 138};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByMonthDay(monthDays);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
	}

	public void testByWeekNoAndYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		int[] weekNos = {13, 17, 21, 27, 30};

		// March 23, 26, 27, 30, 31, April 3, 23, May 18

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 138};

		recurrence.setByWeekNo(weekNos);
		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 24);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2012, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
	}

	public void testByYearDay() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		// March 23, 26, 27, 30, 31, April 3, 23, May 22

		int[] yearDays = {82, 85, 86, 89, 90, 93, 113, 142};

		recurrence.setByYearDay(yearDays);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 3);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 28);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 31);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 23);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 22);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 30);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 28);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2011, MARCH, 26, 0, 0));

		assertIsInRecurrence(true, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);

		// Occurrence

		recurrence.setOccurrence(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 23);
	}

	public void testWeekly() throws Exception {
		Recurrence recurrence = new Recurrence(
			getCalendar(2009, MARCH, 23, HOUR_DURING, MINUTE_DURING),
			getDefaultDuration(), Recurrence.WEEKLY);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2009, MARCH, 30);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(true, recurrence, 2012, MARCH, 26);

		// Interval

		recurrence.setInterval(2);

		assertIsInRecurrence(true, recurrence, 2009, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2009, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 4);
		assertIsInRecurrence(true, recurrence, 2009, APRIL, 20);
		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2009, MAY, 23);
		assertIsInRecurrence(true, recurrence, 2010, MARCH, 22);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 29);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 23);
		assertIsInRecurrence(false, recurrence, 2011, MARCH, 26);
		assertIsInRecurrence(false, recurrence, 2012, MARCH, 26);

		// End date

		recurrence.setUntil(getCalendar(2010, MARCH, 22, 0, 0));

		assertIsInRecurrence(true, recurrence, 2009, MAY, 18);
		assertIsInRecurrence(false, recurrence, 2010, MARCH, 22);

		// Occurrence

		recurrence.setOccurrence(2);

		//assertIsInRecurrence(true, recurrence, 2009, APRIL, 6);
		assertIsInRecurrence(false, recurrence, 2009, APRIL, 20);
	}

}