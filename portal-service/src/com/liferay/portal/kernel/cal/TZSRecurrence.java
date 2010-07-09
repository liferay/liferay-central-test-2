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

package com.liferay.portal.kernel.cal;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Samuel Kong
 */
public class TZSRecurrence extends Recurrence {

	public TZSRecurrence() {
	}

	public TZSRecurrence(Calendar start, Duration duration) {
		super(start, duration);
	}

	public TZSRecurrence(Calendar start, Duration duration, int frequency) {
		super(start, duration, frequency);
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	protected boolean matchesByField(
		int[] array, int field, Calendar candidate, boolean allowNegative,
		TimeZone timeZone) {

		Calendar adjustedCandidate = candidate;

		if (Validator.isNotNull(timeZone)) {
			adjustedCandidate = CalendarFactoryUtil.getCalendar(timeZone);

			adjustedCandidate.setTime(candidate.getTime());
		}

		return matchesByField(array, field, adjustedCandidate, allowNegative);
	}

	protected boolean matchesIndividualByDay(
		Calendar candidate, DayAndPosition pos) {

		Calendar adjustedCandidate = candidate;

		if (Validator.isNotNull(_timeZone)) {
			adjustedCandidate = CalendarFactoryUtil.getCalendar(_timeZone);

			adjustedCandidate.setTime(candidate.getTime());
		}

		return super.matchesIndividualByDay(adjustedCandidate, pos);
	}

	protected boolean matchesByMonthDay(Calendar candidate) {
		return matchesByField(
			byMonthDay, Calendar.DATE, candidate, true, _timeZone);
	}

	protected boolean matchesByYearDay(Calendar candidate) {
		return matchesByField(
			byYearDay, Calendar.DAY_OF_YEAR, candidate, true, _timeZone);
	}

	protected boolean matchesByWeekNo(Calendar candidate) {
		return matchesByField(
			byWeekNo, Calendar.WEEK_OF_YEAR, candidate, true, _timeZone);
	}

	protected boolean matchesByMonth(Calendar candidate) {
		return matchesByField(
			byMonth, Calendar.MONTH, candidate, false, _timeZone);
	}

	private TimeZone _timeZone;

}