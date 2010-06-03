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
 * <a href="RecurrenceWeeklyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceWeeklyTest extends RecurrenceTestCase {

	// Test Recurrence By Day

	public void testByDayBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayAfterDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 12, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayBeforeFirstRecurrenceFirstDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringFirstRecurrenceFirstDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrenceFirstDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayBeforeFirstRecurrenceSecondDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringFirstRecurrenceSecondDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrenceSecondDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testByDayDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrenceCrossDayLength() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceFirstDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceSecondDay() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	// Test Interval

	public void testByDayDuringFirstRecurrenceFirstDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringFirstRecurrenceSecondDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 9, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceFirstDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceSecondDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringThirdRecurrenceFirstDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 22, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringThirdRecurrenceSecondDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringFourthRecurrenceFirstDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 29, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringFourthRecurrenceSecondDayWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 2);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testByDayDuringFirstRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 15, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 8, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, 1);

		recurrence.setUntil(getCalendar(2008, FEBRUARY, 15, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	private static Recurrence _getRecurrenceByDay(
		Calendar dtStart, Duration duration, int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.WEEKLY);

		DayAndPosition[] days = {
			new DayAndPosition(FRIDAY, 0),
			new DayAndPosition(SATURDAY, 0)};

		recurrence.setByDay(days);
		recurrence.setInterval(interval);

		return recurrence;
	}

	private static final Calendar _DT_START = getCalendar(
		2008, FEBRUARY, 5, 22, 10);

}