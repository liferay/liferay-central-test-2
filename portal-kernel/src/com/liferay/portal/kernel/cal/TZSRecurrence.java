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

package com.liferay.portal.kernel.cal;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * <a href="TZSRecurrence.java.html"><b><i>View Source</i></b></a>
 *
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