/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Calendar;

/**
 * <a href="CronText.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class CronText {

	public final static int DAILY_FREQUENCY = 3;

	public final static int MINUTELY_FREQUENCY = 2;

	public final static int MONTHLY_FREQUENCY = 4;

	public final static int NO_FREQUENCY = 1;

	public final static int WEEKLY_FREQUENCY = 5;

	public final static int YEARLY_FREQUENCY = 6;

	public CronText(Calendar startDate) {
		this(startDate, CronText.NO_FREQUENCY, 0);
	}

	public CronText(Calendar startDate, int frequency, int interval) {
		_startDate = startDate;
		setFrequency(frequency);
		_interval = interval;
	}

	public int getFrequency() {
		return _frequency;
	}

	public int getInterval() {
		return _interval;
	}

	public Calendar getStartDate() {
		return _startDate;
	}

	public void setFrequency(int frequency) {
		if ((frequency != CronText.DAILY_FREQUENCY) &&
			(frequency != CronText.MINUTELY_FREQUENCY) &&
			(frequency != CronText.MONTHLY_FREQUENCY) &&
			(frequency != CronText.NO_FREQUENCY) &&
			(frequency != CronText.WEEKLY_FREQUENCY) &&
			(frequency != CronText.YEARLY_FREQUENCY)) {

			throw new IllegalArgumentException(String.valueOf(frequency));
		}

		_frequency = frequency;
	}

	public void setInterval(int interval) {
		_interval = interval;
	}
	public void setStartDate(Calendar startDate) {
		_startDate = startDate;
	}
	public String toString() {
		String second = String.valueOf(_startDate.get(Calendar.SECOND));
		String minute = String.valueOf(_startDate.get(Calendar.MINUTE));
		String hour = String.valueOf(_startDate.get(Calendar.HOUR_OF_DAY));
		String dayOfMonth = String.valueOf(
			_startDate.get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(_startDate.get(Calendar.MONTH) + 1);
		String dayOfWeek = String.valueOf(_startDate.get(Calendar.DAY_OF_WEEK));
		String year = String.valueOf(_startDate.get(Calendar.YEAR));

		if (_frequency == CronText.NO_FREQUENCY) {
			dayOfWeek = StringPool.QUESTION;
		}
		else if (_frequency == CronText.MINUTELY_FREQUENCY) {
			minute = StringPool.STAR + StringPool.FORWARD_SLASH + _interval;
			hour = StringPool.STAR;
			dayOfMonth = StringPool.STAR;
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == CronText.DAILY_FREQUENCY) {
			dayOfMonth += StringPool.FORWARD_SLASH + _interval;
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == CronText.WEEKLY_FREQUENCY) {
			dayOfMonth += StringPool.FORWARD_SLASH + (_interval * 7);
			month = StringPool.STAR;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == CronText.MONTHLY_FREQUENCY) {
			month += StringPool.FORWARD_SLASH + _interval;
			dayOfWeek = StringPool.QUESTION;
			year = StringPool.STAR;
		}
		else if (_frequency == CronText.YEARLY_FREQUENCY) {
			dayOfWeek = StringPool.QUESTION;
			year += StringPool.FORWARD_SLASH + _interval;
		}

		StringBundler sb = new StringBundler(13);

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

	private int _frequency;
	private int _interval;
	private Calendar _startDate;

}