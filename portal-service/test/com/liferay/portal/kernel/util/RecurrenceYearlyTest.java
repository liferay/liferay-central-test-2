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
 * <a href="RecurrenceYearlyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Douglas Wong
 */
public class RecurrenceYearlyTest extends RecurrenceTestCase {

	// Test Recurrence By Month and Day

	public void testByMonthAndDayBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndDayBeforeFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testByMonthAndDayDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Weeks

	public void testByMonthAndDayDuringFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_CROSS_WEEK, FEBRUARY, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_CROSS_WEEK, FEBRUARY, FRIDAY, 3, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Years

	public void testByMonthAndDayDuringRecurrenceCrossYearLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_CROSS_WEEK, DECEMBER, FRIDAY, 4, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 3, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterRecurrenceCrossYearLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_CROSS_WEEK, DECEMBER, FRIDAY, 4, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 3, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringSecondRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndDay();

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 20, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	// Test Interval

	public void testByMonthAndDayDuringFirstRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringSecondRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 20, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringThirdRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2010, FEBRUARY, 19, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringFourthRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 2);

		Calendar testCalendar = getCalendar(2011, FEBRUARY, 18, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testByMonthAndDayDuringFirstRecurrenceWithUntil()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 10));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringSecondRecurrenceWithUntil()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 10));

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 20, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Leap Year Handling

	public void testByMonthAndDayDuringFirstRecurrenceWithLeapYear1()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, FRIDAY, -1, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 29, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayDuringFirstRecurrenceWithLeapYear2()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, FRIDAY, -1, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndDayAfterFirstRecurrenceWithLeapYear1()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, FRIDAY, -1, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Recurrence By Month and Month Day

	public void testByMonthAndMonthDayBeforeDtStart() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterDtStart1() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 5, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterDtStart2() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 5, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayBeforeFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 9);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterFirstRecurrence() throws Exception {
		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 23, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Dates

	public void testByMonthAndMonthDayDuringFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterFirstRecurrenceCrossDayLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 16, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Weeks

	public void testByMonthAndMonthDayDuringFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_CROSS_WEEK, FEBRUARY, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterFirstRecurrenceCrossWeekLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_CROSS_WEEK, FEBRUARY, 15, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 23, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Events Crossing Years

	public void testByMonthAndMonthDayDuringRecurrenceCrossYearLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_CROSS_WEEK, DECEMBER, 29, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 6, 22, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterRecurrenceCrossYearLength()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_CROSS_WEEK, DECEMBER, 29, 1);

		Calendar testCalendar = getCalendar(2009, JANUARY, 6, 22, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringSecondRecurrence()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay();

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 15, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	// Test Interval

	public void testByMonthAndMonthDayDuringFirstRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 2);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringSecondRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 2);

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringThirdRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 2);

		Calendar testCalendar = getCalendar(2010, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringFourthRecurrenceWithInterval()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 2);

		Calendar testCalendar = getCalendar(2011, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Until

	public void testByMonthAndMonthDayDuringFirstRecurrenceWithUntil()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 10));

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringSecondRecurrenceWithUntil()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 1);

		recurrence.setUntil(getCalendar(2008, MARCH, 15, 22, 10));

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 15, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	// Test Leap Year Handling

	public void testByMonthAndMonthDayDuringFirstRecurrenceWithLeapYear1()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 29, 1);

		Calendar testCalendar = getCalendar(2008, FEBRUARY, 29, 22, 10);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringFirstRecurrenceWithLeapYear2()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 29, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 9);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterFirstRecurrenceWithLeapYear1()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 29, 1);

		Calendar testCalendar = getCalendar(2008, MARCH, 1, 0, 10);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterFirstRecurrenceWithLeapYear2()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 29, 1);

		Calendar testCalendar = getCalendar(2009, FEBRUARY, 28, 22, 15);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayAfterFirstRecurrenceWithLeapYear3()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 29, 1);

		Calendar testCalendar = getCalendar(2009, MARCH, 1, 0, 0);

		assertRecurrenceEquals(false, recurrence, testCalendar);
	}

	public void testByMonthAndMonthDayDuringSecondRecurrenceWithLeapYear()
		throws Exception {

		Recurrence recurrence = _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_TWO_HOURS, FEBRUARY, 29, 1);

		Calendar testCalendar = getCalendar(2012, FEBRUARY, 29, 22, 15);

		assertRecurrenceEquals(true, recurrence, testCalendar);
	}

	private static Recurrence _getRecurrenceByMonthAndDay() {
		return _getRecurrenceByMonthAndDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, FRIDAY, 3, 1);
	}

	private static Recurrence _getRecurrenceByMonthAndDay(
		Calendar dtStart, Duration duration, int month, int day, int position,
		int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.YEARLY);

		DayAndPosition[] days = {new DayAndPosition(day, position)};
		int[] months = {month};

		recurrence.setByDay(days);
		recurrence.setByMonth(months);
		recurrence.setInterval(interval);

		return recurrence;
	}

	private static Recurrence _getRecurrenceByMonthAndMonthDay() {
		return _getRecurrenceByMonthAndMonthDay(
			_DT_START, DURATION_ONE_HOUR, FEBRUARY, 15, 1);
	}

	private static Recurrence _getRecurrenceByMonthAndMonthDay(
		Calendar dtStart, Duration duration, int month, int monthDay,
		int interval) {

		Recurrence recurrence = new Recurrence(
			dtStart, duration, Recurrence.YEARLY);

		int[] monthDays = {monthDay};
		int[] months = {month};

		recurrence.setByMonth(months);
		recurrence.setByMonthDay(monthDays);
		recurrence.setInterval(interval);

		return recurrence;
	}

	private static final Calendar _DT_START = getCalendar(
		2008, FEBRUARY, 5, 22, 10);

}