/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Calendar;

/**
 * <a href="SchedulerTriggerExpression.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Thiago Moreira
 */
public class SchedulerTriggerExpression {

	/**
	 * Field MINUTES_FREQUENCY
	 */
	public final static int MINUTES_FREQUENCY = 2;

	/**
	 * Field DAILY_FREQUENCY
	 */
	public final static int DAILY_FREQUENCY = 3;

	/**
	 * Field WEEKLY_FREQUENCY
	 */
	public final static int WEEKLY_FREQUENCY = 4;

	/**
	 * Field MONTHLY_FREQUENCY
	 */
	public final static int MONTHLY_FREQUENCY = 5;

	/**
	 * Field YEARLY_FREQUENCY
	 */
	public final static int YEARLY_FREQUENCY = 6;

	/**
	 * Field NO_FREQUENCY
	 */
	public final static int NO_FREQUENCY = 7;

	/**
	 * Field dtStart
	 */
	protected Calendar _startDate;

	/**
	 * Field frequency
	 */
	protected int _frequency;

	/**
	 * Field interval
	 */
	protected int _interval;

	public SchedulerTriggerExpression(Calendar startDate) {

		this(startDate, SchedulerTriggerExpression.NO_FREQUENCY, 0);
	}

	public SchedulerTriggerExpression(
		Calendar startDate, int frequency, int interval) {

		_startDate = startDate;
		_interval = interval;
		
		setFrequency(frequency);
	}

	public int getFrequency() {

		return _frequency;
	}

	public void setFrequency(int frequency) {

		if (frequency != SchedulerTriggerExpression.DAILY_FREQUENCY &&
			frequency != SchedulerTriggerExpression.MINUTES_FREQUENCY &&
			frequency != SchedulerTriggerExpression.MONTHLY_FREQUENCY &&
			frequency != SchedulerTriggerExpression.NO_FREQUENCY &&
			frequency != SchedulerTriggerExpression.WEEKLY_FREQUENCY &&
			frequency != SchedulerTriggerExpression.YEARLY_FREQUENCY) {
			throw new IllegalArgumentException(String.valueOf(frequency));
		}

		_frequency = frequency;
	}

	public int getInterval() {

		return _interval;
	}

	public void setInterval(int interval) {

		_interval = interval;
	}

	public Calendar getStartDate() {

		return _startDate;
	}

	public void setStartDate(Calendar startDate) {

		_startDate = startDate;
	}

	public String toCronText() {

		String second = String.valueOf(_startDate.get(Calendar.SECOND));
		String minute = String.valueOf(_startDate.get(Calendar.MINUTE));
		String hour = String.valueOf(_startDate.get(Calendar.HOUR_OF_DAY));
		String dayOfMonth =
			String.valueOf(_startDate.get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(_startDate.get(Calendar.MONTH) + 1);
		String dayOfWeek = String.valueOf(_startDate.get(Calendar.DAY_OF_WEEK));
		String year = String.valueOf(_startDate.get(Calendar.YEAR));

		if (_frequency == SchedulerTriggerExpression.NO_FREQUENCY) {
			dayOfWeek = StringPool.QUESTION;
		}
		else if (_frequency == SchedulerTriggerExpression.MINUTES_FREQUENCY) {
			minute += StringPool.FORWARD_SLASH + _interval;
			hour = StringPool.STAR;
			dayOfMonth = StringPool.STAR;
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == SchedulerTriggerExpression.DAILY_FREQUENCY) {
			dayOfMonth += StringPool.FORWARD_SLASH + _interval;
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == SchedulerTriggerExpression.WEEKLY_FREQUENCY) {
			dayOfMonth += StringPool.FORWARD_SLASH + (_interval * 7);
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == SchedulerTriggerExpression.MONTHLY_FREQUENCY) {
			month += StringPool.FORWARD_SLASH + _interval;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == SchedulerTriggerExpression.YEARLY_FREQUENCY) {
			dayOfWeek = StringPool.QUESTION;
			year += StringPool.FORWARD_SLASH + _interval;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(second);
		sb.append(StringPool.SPACE);
		sb.append(minute);
		sb.append(StringPool.SPACE);
		sb.append(hour);
		sb.append(StringPool.SPACE);
		sb.append(dayOfMonth);
		sb.append(StringPool.SPACE);
		sb.append(month);
		sb.append(StringPool.SPACE);
		sb.append(dayOfWeek);
		sb.append(StringPool.SPACE);
		sb.append(year);

		return sb.toString();
	}

}
