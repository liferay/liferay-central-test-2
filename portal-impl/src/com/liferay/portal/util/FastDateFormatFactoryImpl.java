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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.FastDateFormatFactory;

import java.text.Format;

import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * <a href="FastDateFormatFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FastDateFormatFactoryImpl implements FastDateFormatFactory {

	public Format getDate(Locale locale) {
		return FastDateFormat.getDateInstance(FastDateFormat.SHORT, locale);
	}

	public Format getDate(Locale locale, TimeZone timeZone) {
		return FastDateFormat.getDateInstance(
			FastDateFormat.SHORT, timeZone, locale);
	}

	public Format getDate(TimeZone timeZone) {
		return FastDateFormat.getDateInstance(FastDateFormat.SHORT, timeZone);
	}

	public Format getDateTime(Locale locale) {
		return FastDateFormat.getDateTimeInstance(
			FastDateFormat.SHORT, FastDateFormat.SHORT, locale);
	}

	public Format getDateTime(Locale locale, TimeZone timeZone) {
		return FastDateFormat.getDateTimeInstance(
			FastDateFormat.SHORT, FastDateFormat.SHORT, timeZone, locale);
	}

	public Format getDateTime(TimeZone timeZone) {
		return FastDateFormat.getDateTimeInstance(
			FastDateFormat.SHORT, FastDateFormat.SHORT, timeZone);
	}

	public Format getSimpleDateFormat(String pattern) {
		return FastDateFormat.getInstance(pattern);
	}

	public Format getSimpleDateFormat(String pattern, Locale locale) {
		return FastDateFormat.getInstance(pattern, locale);
	}

	public Format getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone) {

		return FastDateFormat.getInstance(pattern, timeZone, locale);
	}

	public Format getSimpleDateFormat(String pattern, TimeZone timeZone) {
		return FastDateFormat.getInstance(pattern, timeZone);
	}

	public Format getTime(Locale locale) {
		return FastDateFormat.getTimeInstance(FastDateFormat.SHORT, locale);
	}

	public Format getTime(Locale locale, TimeZone timeZone) {
		return FastDateFormat.getTimeInstance(
			FastDateFormat.SHORT, timeZone, locale);
	}

	public Format getTime(TimeZone timeZone) {
		return FastDateFormat.getTimeInstance(FastDateFormat.SHORT, timeZone);
	}

}