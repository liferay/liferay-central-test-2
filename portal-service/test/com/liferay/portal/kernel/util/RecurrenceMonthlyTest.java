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
 * <a href="RecurrenceMonthlyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceMonthlyTest extends RecurrenceTestCase {

	// Test Recurrence By Day

	public void testByDayBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayAfterDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, MARCH, 5, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayBeforeFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testByDayDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrenceCrossDayLength() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Weeks

	public void testByDayDuringFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_CROSS_WEEK, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_CROSS_WEEK, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Years

	public void testByDayDuringRecurrenceCrossYearLength() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_CROSS_WEEK, FRIDAY, 4, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 3, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterRecurrenceCrossYearLength() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_CROSS_WEEK, FRIDAY, 4, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 3, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay();

		Calendar testCalendar = getCalendar(2008, MARCH, 21, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	// Test Interval

	public void testByDayDuringFirstRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2008, MARCH, 21, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByDayDuringThirdRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2008, APRIL, 18, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringFourthRecurrenceWithInterval() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2008, MAY, 16, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testByDayDuringFirstRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 10));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringSecondRecurrenceWithUntil() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 10));

		Calendar testCalendar = getCalendar(2008, MARCH, 21, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Leap Year Handling

	public void testByDayDuringFirstRecurrenceWithLeapYear1() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, FRIDAY, -1, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 29, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayDuringFirstRecurrenceWithLeapYear2() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, FRIDAY, -1, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByDayAfterFirstRecurrenceWithLeapYear1() throws Exception {
		Recurrence recurrence = _getRecurrenceByDay(
			_DT_START, DURATION_TWO_HOURS, FRIDAY, -1, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Recurrence By Month Day

	public void testByMonthDayBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayAfterDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayAfterDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayBeforeFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayDuringFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayAfterFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testByMonthDayDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayAfterFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Weeks

	public void testByMonthDayDuringFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_CROSS_WEEK, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayAfterFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_CROSS_WEEK, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Years

	public void testByMonthDayDuringRecurrenceCrossYearLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_CROSS_WEEK, 29, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 6, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayAfterRecurrenceCrossYearLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_CROSS_WEEK, 29, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 6, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayDuringSecondRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthDay();

		Calendar testCalendar = getCalendar(2008, MARCH, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	// Test Interval

	public void testByMonthDayDuringFirstRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_ONE_HOUR, 15, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayDuringSecondRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_ONE_HOUR, 15, 2);

		Calendar testCalendar = getCalendar(2008, MARCH, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayDuringThirdRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_ONE_HOUR, 15, 2);

		Calendar testCalendar = getCalendar(2008, APRIL, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayDuringFourthRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_ONE_HOUR, 15, 2);

		Calendar testCalendar = getCalendar(2008, MAY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testByMonthDayDuringFirstRecurrenceWithUntil()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_ONE_HOUR, 15, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 0));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayDuringSecondRecurrenceWithUntil()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_ONE_HOUR, 15, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 0));

		Calendar testCalendar = getCalendar(2008, MARCH, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Leap Year Handling

	public void testByMonthDayDuringFirstRecurrenceWithLeapYear1()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 29, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 29, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayDuringFirstRecurrenceWithLeapYear2()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 29, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthDayAfterFirstRecurrenceWithLeapYear1()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 29, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayAfterFirstRecurrenceWithLeapYear2()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 29, 1);

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 28, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayAfterFirstRecurrenceWithLeapYear3()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 29, 1);

		Calendar testCalendar = getCalendar(2009, MARCH, 1, 0, 0);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthDayDuringSecondRecurrenceWithLeapYear()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthDay(
			_DT_START, DURATION_TWO_HOURS, 29, 1);

		Calendar testCalendar = getCalendar(2012, FEBRUARY, 29, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	private static Recurrence _getRecurrenceByDay() {
		return _getRecurrenceByDay(_DT_START, DURATION_ONE_HOUR, FRIDAY, 3, 1);
	}

	private static Recurrence _getRecurrenceByDay(
		Calendar dtStart, Duration duration, int day, int position,
		int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.MONTHLY);

		DayAndPosition[] days = {new DayAndPosition(day, position)};

		recurrence.setByDay(days);
		recurrence.setInterval(interval);

		return recurrence;
	}

	private static Recurrence _getRecurrenceByMonthDay() {
		return _getRecurrenceByMonthDay(_DT_START, DURATION_ONE_HOUR, 15, 1);
	}

	private static Recurrence _getRecurrenceByMonthDay(
		Calendar dtStart, Duration duration, int monthDay, int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.MONTHLY);

		int[] monthDays = {monthDay};

		recurrence.setByMonthDay(monthDays);
		recurrence.setInterval(interval);

		return recurrence;
	}

	private static final Calendar _DT_START = getCalendar(
		2008, FEBRUARY, 5, 22, 10);

}