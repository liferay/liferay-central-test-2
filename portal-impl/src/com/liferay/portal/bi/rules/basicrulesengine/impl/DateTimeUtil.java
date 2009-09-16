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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.bi.rules.basicrulesengine.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="DateTimeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class DateTimeUtil {

	public static final String DATE_ATTRIBUTE = "date";

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public static final String DAY_ATTRIBUTE = "day";

	public static final String TIME_ATTRIBUTE = "time";

	public static final String TIME_FORMAT = "HH:mm";

	public static int compareDateWithToday(String timeZoneId, String mmddyyyy) {
		int result;

		try {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

			Calendar today = Calendar.getInstance(timeZone);
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);

			DateFormat dateFormat = getDateFormat(timeZoneId);

			if (mmddyyyy.indexOf("*") != -1) {
				String todayStr = dateFormat.format(today.getTime());

				String[] todayParts = todayStr.split("/");

				String[] parts = mmddyyyy.split("/");

				if (parts[0].equals("*")) {
					parts[0] = todayParts[0];
				}

				if (parts[1].equals("*")) {
					parts[1] = todayParts[1];
				}

				if (parts[2].equals("*")) {
					parts[2] = todayParts[2];
				}

				mmddyyyy = new StringBuffer()
					.append(parts[0])
					.append('/')
					.append(parts[1])
					.append('/')
					.append(parts[2])
					.toString();
			}

			Date date = dateFormat.parse(mmddyyyy);

			Calendar test = Calendar.getInstance(timeZone);

			test.setTime(date);

			int tempResult = today.compareTo(test);

			if (tempResult < 0) {
				result = -1;
			}
			else if (tempResult > 0) {
				result = 1;
			}
			else {
				result = 0;
			}
		}
		catch (Exception ex) {
			if (_log.isDebugEnabled()) {
				_log.debug(ex);
			}

			result = Integer.MIN_VALUE;
		}

		return result;
	}

	public static int compareDayWithToday(String timeZoneId, String dayNumber) {
		int result;

		try {
			if (dayNumber.equals("*")) {
				result = 0;
			}
			else {
				int day = Integer.parseInt(dayNumber);

				if (day >= 1 && day <= 7) {

					TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

					Calendar today = Calendar.getInstance(timeZone);

					int tempResult = today.get(Calendar.DAY_OF_WEEK) - day;

					if (tempResult < 0) {
						result = -1;
					}
					else if (tempResult > 0) {
						result = 1;
					}
					else {
						result = 0;
					}
				}
				else {
					result = Integer.MIN_VALUE;;
				}
			}
		}
		catch (Exception ex) {
			if (_log.isDebugEnabled()) {
				_log.debug(ex);
			}

			result = Integer.MIN_VALUE;
		}

		return result;
	}

	public static int compareTimeWithNow(String timeZoneId, String hhmm) {
		int result;

		try {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

			Calendar now = Calendar.getInstance(timeZone);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MILLISECOND, 0);

			DateFormat timeFormat = getTimeFormat(timeZoneId);

			if (hhmm.indexOf("*") != -1) {
				String[] parts = hhmm.split(":");

				if (parts[0].equals("*")) {
					now.set(Calendar.HOUR_OF_DAY, 0);
				}

				if (parts[1].equals("*")) {
					now.set(Calendar.MINUTE, 0);
				}

				hhmm = hhmm.replace('*', '0');
			}

			Date time = timeFormat.parse(hhmm);

			Calendar test = Calendar.getInstance(timeZone);

			test.setTime(time);
			test.set(
				now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DAY_OF_MONTH));

			int tempResult = now.compareTo(test);

			if (tempResult < 0) {
				result = -1;
			}
			else if (tempResult > 0) {
				result = 1;
			}
			else {
				result = 0;
			}
		}
		catch (Exception ex) {
			if (_log.isDebugEnabled()) {
				_log.debug(ex);
			}

			result = Integer.MIN_VALUE;
		}

		return result;
	}

	public static String formatDate(Date date) {
		return _defaultDateFormat.format(date);
	}

	public static DateFormat getDateFormat(String timeZoneId) {
		DateFormat dateFormat = _dateFormatPool.get(timeZoneId);

		if (dateFormat == null) {
			dateFormat =
				new SimpleDateFormat(DATE_FORMAT, Locale.US);

			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

			dateFormat.setTimeZone(timeZone);

			_dateFormatPool.put(timeZoneId, dateFormat);
		}

		return dateFormat;
	}

	public static DateFormat getDefaultDateFormat() {
		return _defaultDateFormat;
	}

	public static DateFormat getTimeFormat(String timeZoneId) {
		DateFormat timeFormat = _timeFormatPool.get(timeZoneId);

		if (timeFormat == null) {
			timeFormat =
				new SimpleDateFormat(TIME_FORMAT, Locale.US);

			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

			timeFormat.setTimeZone(timeZone);

			_timeFormatPool.put(timeZoneId, timeFormat);
		}

		return timeFormat;
	}

	private static DateFormat _defaultDateFormat =
		new SimpleDateFormat(DATE_FORMAT, Locale.US);

	private static Map<String,DateFormat> _dateFormatPool =
		new ConcurrentHashMap<String, DateFormat>();

	private static Map<String,DateFormat> _timeFormatPool =
		new ConcurrentHashMap<String, DateFormat>();

	private static Log _log =
		 LogFactoryUtil.getLog(DateTimeUtil.class);

}