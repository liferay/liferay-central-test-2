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

import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.test.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <a href="RecurrenceTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public abstract class RecurrenceTestCase extends TestCase {

	public static final int APRIL = Calendar.APRIL;

	public static final int AUGUST = Calendar.AUGUST;

	public static final int DECEMBER = Calendar.DECEMBER;

	public static final int FEBRUARY = Calendar.FEBRUARY;

	public static final int FRIDAY = Calendar.FRIDAY;

	public static final int HOUR_AFTER = 12;

	public static final int HOUR_BEFORE = 11;

	public static final int HOUR_DURING = 11;

	public static final int JANUARY = Calendar.JANUARY;

	public static final int JULY = Calendar.JULY;

	public static final int JUNE = Calendar.JUNE;

	public static final int MARCH = Calendar.MARCH;

	public static final int MAY = Calendar.MAY;

	public static final int MINUTE_AFTER = 22;

	public static final int MINUTE_BEFORE = 20;

	public static final int MINUTE_DURING = 21;

	public static final int MONDAY = Calendar.MONDAY;

	public static final int NOVEMBER = Calendar.NOVEMBER;

	public static final int OCTOBER = Calendar.OCTOBER;

	public static final int SATURDAY = Calendar.SATURDAY;

	public static final int SEPTEMBER = Calendar.SEPTEMBER;

	public static final int SUNDAY = Calendar.SUNDAY;

	public static final int THURSDAY = Calendar.THURSDAY;

	public static final int TUESDAY = Calendar.TUESDAY;

	public static final int WEDNESDAY = Calendar.WEDNESDAY;

	public abstract void testByDay() throws Exception;

	public abstract void testByDayAndMonth() throws Exception;

	public abstract void testByDayAndMonthAndYearDay() throws Exception;

	public abstract void testByDayAndMonthDay() throws Exception;

	public abstract void testByDayAndMonthDayAndMonth() throws Exception;

	public abstract void testByDayAndMonthDayAndYearDay() throws Exception;

	public abstract void testByDayAndWeekNo() throws Exception;

	public abstract void testByDayAndWeekNoAndMonth() throws Exception;

	public abstract void testByDayAndWeekNoAndMonthDay() throws Exception;

	public abstract void testByDayAndWeekNoAndYearDay() throws Exception;

	public abstract void testByDayAndYearDay() throws Exception;

	public abstract void testByMonth() throws Exception;

	public abstract void testByMonthAndYearDay() throws Exception;

	public abstract void testByMonthDay() throws Exception;

	public abstract void testByMonthDayAndMonth() throws Exception;

	public abstract void testByMonthDayAndMonthAndYearDay() throws Exception;

	public abstract void testByMonthDayAndYearDay() throws Exception;

	public abstract void testByWeekNo() throws Exception;

	public abstract void testByWeekNoAndMonth() throws Exception;

	public abstract void testByWeekNoAndMonthAndYearDay() throws Exception;

	public abstract void testByWeekNoAndMonthDay() throws Exception;

	public abstract void testByWeekNoAndMonthDayAndMonth() throws Exception;

	public abstract void testByWeekNoAndMonthDayAndYearDay()
		throws Exception;

	public abstract void testByWeekNoAndYearDay() throws Exception;

	public abstract void testByYearDay() throws Exception;

	protected void assertIsInRecurrence(
			boolean expected, Recurrence recurrence, int year, int month,
			int date)
		throws Exception {

		assertEquals(
			false,
			recurrence.isInRecurrence(
				getCalendarBeforeTime(year, month, date)));
		assertEquals(
			expected,
			recurrence.isInRecurrence(
				getCalendarDuringTime(year, month, date)));
		assertEquals(
			false,
			recurrence.isInRecurrence(
				getCalendarAfterTime(year, month, date)));
	}

	protected Calendar getCalendar(
		int year, int month, int date, int hour, int minute) {

		Calendar calendar = new GregorianCalendar();

		calendar.set(year, month, date, hour, minute);

		return calendar;
	}

	protected Duration getDefaultDuration() {
		return getDuration(0, 0, 1, 0, 0);
	}

	private Calendar getCalendarAfterTime(int year, int month, int date) {
		return getCalendar(year, month, date, HOUR_AFTER, MINUTE_AFTER);
	}

	private Calendar getCalendarBeforeTime(int year, int month, int date) {
		return getCalendar(year, month, date, HOUR_BEFORE, MINUTE_BEFORE);
	}

	private Calendar getCalendarDuringTime(int year, int month, int date) {
		return getCalendar(year, month, date, HOUR_DURING, MINUTE_DURING);
	}

	private Duration getDuration(
		int weeks, int days, int hours, int minutes, int seconds) {

		Duration duration = new Duration(days, hours, minutes, seconds);

		duration.setWeeks(weeks);

		return duration;
	}

}