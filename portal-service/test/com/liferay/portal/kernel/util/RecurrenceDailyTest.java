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
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;

import java.util.Calendar;

/**
 * <a href="RecurrenceDailyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceDailyTest extends RecurrenceTestCase {

	// Test Recurrence Daily

	public void testDailyBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testDailyDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyAfterDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testDailyBeforeFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testDailyDuringFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyAfterFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testDailyDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_TWO_HOURS, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyAfterFirstRecurrenceCrossDayLength() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_TWO_HOURS, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testDailyDuringSecondRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	// Test Interval

	public void testDailyDuringFirstRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyDuringSecondRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testDailyDuringThirdRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 7, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyDuringFourthRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testDailyDuringFirstRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 8, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 7, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testDailyDuringSecondRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceDaily(
			_DT_START, DURATION_ONE_HOUR, 1);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 8, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Recurrence Weekday

	public void testWeekdayBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayAfterDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayBeforeFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayDuringFirstRecurrence1() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayAfterFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayDuringFirstRecurrence2() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringFirstRecurrence3() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringFirstWeekend1() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayDuringFirstWeekend2() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 10, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testWeekdayDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_TWO_HOURS);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayAfterFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_TWO_HOURS);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayDuringSecondRecurrence1() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 11, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringSecondRecurrence2() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 12, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringSecondRecurrence3() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringSecondWeekend1() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testWeekdayDuringSecondWeekend2() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 17, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testWeekdayDuringFirstRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 6, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testWeekdayDuringSecondRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceWeekday(
			_DT_START, DURATION_ONE_HOUR);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 6, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 6, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	private static Recurrence _getRecurrenceDaily(
		Calendar dtStart, Duration duration, int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.DAILY);

		recurrence.setInterval(interval);

		return recurrence;
	}

	private static Recurrence _getRecurrenceWeekday(
		Calendar dtStart, Duration duration) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.DAILY);

		DayAndPosition[] days = {
			new DayAndPosition(MONDAY, 0),
			new DayAndPosition(TUESDAY, 0),
			new DayAndPosition(WEDNESDAY, 0),
			new DayAndPosition(THURSDAY, 0),
			new DayAndPosition(FRIDAY, 0)};

		recurrence.setByDay(days);

		return recurrence;
	}

	private static final Calendar _DT_START = getCalendar(
			2008, FEBRUARY, 5, 22, 10);

}