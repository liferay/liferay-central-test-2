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

package com.liferay.portlet.reverendfun.util;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="ReverendFunWebCacheItem.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReverendFunWebCacheItem implements WebCacheItem {

	public ReverendFunWebCacheItem(String date) {
		_date = date;
	}

	public Object convert(String key) throws WebCacheException {
		Set<String> dates = new TreeSet<String>(
			new StringComparator(false, true));

		try {
			DateFormat dateFormatYMD =
				DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMdd");
			DateFormat dateFormatYM =
				DateFormatFactoryUtil.getSimpleDateFormat("yyyyMM");

			Calendar cal = CalendarFactoryUtil.getCalendar();

			cal.setTime(dateFormatYMD.parse(_date));
			cal.set(Calendar.DATE, 1);

			Calendar now = CalendarFactoryUtil.getCalendar();

			String url = "http://www.reverendfun.com/artchives/?search=";

			while (cal.before(now)) {
				String text = HttpUtil.URLtoString(
					url + dateFormatYM.format(cal.getTime()));

				int x = text.indexOf("date=");
				int y = text.indexOf("\"", x);

				while (x != -1 && y != -1) {
					String fromDateString = text.substring(x + 5, y);

					dates.add(fromDateString);

					x = text.indexOf("date=", y);
					y = text.indexOf("\"", x);
				}

				cal.add(Calendar.MONTH, 1);
			}
		}
		catch (Exception e) {
			throw new WebCacheException(_date + " " + e.toString());
		}

		return dates;
	}

	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.DAY;

	private String _date;

}