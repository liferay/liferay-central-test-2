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

import java.text.DateFormat;
import java.text.Format;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <a href="DateUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DateUtil {

	public static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

	public static int compareTo(Date date1, Date date2) {
		return compareTo(date1, date2, false);
	}

	public static int compareTo(
		Date date1, Date date2, boolean ignoreMilliseconds) {

		// Workaround for bug in JDK 1.5.x. This bug is fixed in JDK 1.5.07. See
		// http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=6207898 for
		// more information.

		if ((date1 != null) && (date2 == null)) {
			return -1;
		}
		else if ((date1 == null) && (date2 != null)) {
			return 1;
		}
		else if ((date1 == null) && (date2 == null)) {
			return 0;
		}

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		if (ignoreMilliseconds) {
			time1 = time1 / Time.SECOND;
			time2 = time2 / Time.SECOND;
		}

		if (time1 == time2) {
			return 0;
		}
		else if (time1 < time2) {
			return -1;
		}
		else {
			return 1;
		}
	}

	public static boolean equals(Date date1, Date date2) {
		if (compareTo(date1, date2) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean equals(
		Date date1, Date date2, boolean ignoreMilliseconds) {

		if (!ignoreMilliseconds) {
			return equals(date1, date2);
		}

		long time1 = 0;

		if (date1 != null) {
			time1 = date1.getTime() / Time.SECOND;
		}

		long time2 = 0;

		if (date2 != null) {
			time2 = date2.getTime() / Time.SECOND;
		}

		if (time1 == time2) {
			return true;
		}
		else {
			return false;
		}
	}

	public static String getCurrentDate(String pattern, Locale locale) {
		return getDate(new Date(), pattern, locale);
	}

	public static String getCurrentDate(
		String pattern, Locale locale, TimeZone timeZone) {

		return getDate(new Date(), pattern, locale, timeZone);
	}

	public static String getDate(Date date, String pattern, Locale locale) {
		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			pattern, locale);

		return dateFormat.format(date);
	}

	public static String getDate(
		Date date, String pattern, Locale locale, TimeZone timeZone) {

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			pattern, locale, timeZone);

		return dateFormat.format(date);
	}

	public static int getDaysBetween(
		Date startDate, Date endDate, TimeZone timeZone) {

		int offset = timeZone.getRawOffset();

		Calendar startCal = new GregorianCalendar(timeZone);

		startCal.setTime(startDate);
		startCal.add(Calendar.MILLISECOND, offset);

		Calendar endCal = new GregorianCalendar(timeZone);

		endCal.setTime(endDate);
		endCal.add(Calendar.MILLISECOND, offset);

		int daysBetween = 0;

		while (CalendarUtil.beforeByDay(startCal.getTime(), endCal.getTime())) {
			startCal.add(Calendar.DAY_OF_MONTH, 1);

			daysBetween++;
		}

		return daysBetween;
	}

	public static DateFormat getISOFormat() {
		return getISOFormat(StringPool.BLANK);
	}

	public static DateFormat getISOFormat(String text) {
		String pattern = StringPool.BLANK;

		if (text.length() == 8) {
			pattern = "yyyyMMdd";
		}
		else if (text.length() == 12) {
			pattern = "yyyyMMddHHmm";
		}
		else if (text.length() == 13) {
			pattern = "yyyyMMdd'T'HHmm";
		}
		else if (text.length() == 14) {
			pattern = "yyyyMMddHHmmss";
		}
		else if (text.length() == 15) {
			pattern = "yyyyMMdd'T'HHmmss";
		}
		else if ((text.length() > 8) && (text.charAt(8) == 'T')) {
			pattern = "yyyyMMdd'T'HHmmssz";
		}
		else {
			pattern = "yyyyMMddHHmmssz";
		}

		return DateFormatFactoryUtil.getSimpleDateFormat(pattern);
	}

	public static DateFormat getISO8601Format() {
		return DateFormatFactoryUtil.getSimpleDateFormat(ISO_8601_PATTERN);
	}

	public static DateFormat getUTCFormat() {
		return getUTCFormat(StringPool.BLANK);
	}

	public static DateFormat getUTCFormat(String text) {
		String pattern = StringPool.BLANK;

		if (text.length() == 8) {
			pattern = "yyyyMMdd";
		}
		else if (text.length() == 12) {
			pattern = "yyyyMMddHHmm";
		}
		else if (text.length() == 13) {
			pattern = "yyyyMMdd'T'HHmm";
		}
		else if (text.length() == 14) {
			pattern = "yyyyMMddHHmmss";
		}
		else if (text.length() == 15) {
			pattern = "yyyyMMdd'T'HHmmss";
		}
		else {
			pattern = "yyyyMMdd'T'HHmmssz";
		}

		return DateFormatFactoryUtil.getSimpleDateFormat(
			pattern, TimeZone.getTimeZone(StringPool.UTC));
	}

	public static Date newDate() {
		return new Date();
	}

	public static Date newDate(long date) {
		return new Date(date);
	}

}