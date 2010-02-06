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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.TimeZone;

/**
 * <a href="DateFormatFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DateFormatFactoryImpl implements DateFormatFactory {

	public DateFormat getDate(Locale locale) {
		return getDate(locale, null);
	}

	public DateFormat getDate(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateInstance(
			DateFormat.SHORT, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public DateFormat getDate(TimeZone timeZone) {
		return getDate(LocaleUtil.getDefault(), timeZone);
	}

	public DateFormat getDateTime(Locale locale) {
		return getDateTime(locale, null);
	}

	public DateFormat getDateTime(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.SHORT, DateFormat.SHORT, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public DateFormat getDateTime(TimeZone timeZone) {
		return getDateTime(LocaleUtil.getDefault(), timeZone);
	}

	public DateFormat getSimpleDateFormat(String pattern) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), null);
	}

	public DateFormat getSimpleDateFormat(String pattern, Locale locale) {
		return getSimpleDateFormat(pattern, locale, null);
	}

	public DateFormat getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone) {

		DateFormat dateFormat = new SimpleDateFormat(pattern, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public DateFormat getSimpleDateFormat(String pattern, TimeZone timeZone) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), timeZone);
	}

	public DateFormat getTime(Locale locale) {
		return getTime(locale, null);
	}

	public DateFormat getTime(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getTimeInstance(
			DateFormat.SHORT, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public DateFormat getTime(TimeZone timeZone) {
		return getTime(LocaleUtil.getDefault(), timeZone);
	}

}