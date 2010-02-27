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

import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.util.ContentUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="ReverendFunUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReverendFunUtil {

	public static String getCurrentDate() {
		return _instance._getCurrentDate();
	}

	public static String getNextDate(String date) {
		return _instance._getNextDate(date);
	}

	public static String getPreviousDate(String date) {
		return _instance._getPreviousDate(date);
	}

	public static boolean hasDate(String date) {
		return _instance._hasDate(date);
	}

	private ReverendFunUtil() {
		_dates = new ArrayList<String>();

		String[] dates = StringUtil.split(ContentUtil.get(
			"com/liferay/portlet/reverendfun/dependencies/dates.txt"), "\n");

		for (int i = 0; i < dates.length; i++) {
			_dates.add(dates[i]);
		}
	}

	private String _getCurrentDate() {
		String firstDates = _dates.get(0);

		try {
			Set<String> moreDates = _getMoreDates(firstDates);

			if (moreDates.size() > 0) {
				String firstMoreDates = moreDates.iterator().next();

				if (!firstMoreDates.equals(firstDates)) {
					_dates.addAll(0, moreDates);

					// Trim duplicate dates

					Set<String> datesSet = new HashSet<String>();

					Iterator<String> itr = _dates.iterator();

					while (itr.hasNext()) {
						String date = itr.next();

						if (datesSet.contains(date)) {
							itr.remove();
						}
						else {
							datesSet.add(date);
						}
					}
				}
			}
		}
		catch (Exception e) {
		}

		String currentDate = _dates.get(0);

		return currentDate;
	}

	private Set<String> _getMoreDates(String date) {
		WebCacheItem wci = new ReverendFunWebCacheItem(date);

		return (Set<String>)WebCachePoolUtil.get(
			ReverendFunUtil.class.getName() + StringPool.PERIOD + date, wci);
	}

	private String _getNextDate(String date) {
		int pos = Collections.binarySearch(
			_dates, date, new StringComparator(false, true));

		if (pos >= 1) {
			return _dates.get(pos - 1);
		}

		return null;
	}

	private String _getPreviousDate(String date) {
		int pos = Collections.binarySearch(
			_dates, date, new StringComparator(false, true));

		if (pos > -1 && pos < _dates.size() - 1) {
			return _dates.get(pos + 1);
		}

		return null;
	}

	private boolean _hasDate(String date) {
		int pos = Collections.binarySearch(
			_dates, date, new StringComparator(false, true));

		if (pos >= 1) {
			return true;
		}
		else {
			return false;
		}
	}

	private static ReverendFunUtil _instance = new ReverendFunUtil();

	private List<String> _dates;

}