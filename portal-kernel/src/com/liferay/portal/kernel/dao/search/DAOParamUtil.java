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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;

import java.util.Calendar;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="DAOParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DAOParamUtil {

	public static boolean getBoolean(HttpServletRequest request, String param) {
		return GetterUtil.getBoolean(getString(request, param));
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getBoolean(getString(portletRequest, param));
	}

	public static int getInteger(HttpServletRequest request, String param) {
		return GetterUtil.getInteger(getString(request, param));
	}

	public static int getInteger(PortletRequest portletRequest, String param) {
		return GetterUtil.getInteger(getString(portletRequest, param));
	}

	public static String getISODate(HttpServletRequest request, String param) {
		int month = ParamUtil.getInteger(request, param + "Month");
		int day = ParamUtil.getInteger(request, param + "Day");
		int year = ParamUtil.getInteger(request, param + "Year");
		int hour = ParamUtil.getInteger(request, param + "Hour", -1);
		int minute = ParamUtil.getInteger(request, param + "Minute", -1);
		int amPm = ParamUtil.getInteger(request, param + "AmPm");

		if ((month >= 0) && (day > 0) && (year > 0)) {
			Calendar cal = CalendarFactoryUtil.getCalendar();

			if ((hour == -1) || (minute == -1)) {
				cal.set(year, month, day);
			}
			else {
				if (amPm == Calendar.PM) {
					hour += 12;
				}

				cal.set(year, month, day, hour, minute, 0);
			}

			DateFormat isoFormat = DateUtil.getISOFormat();

			return isoFormat.format(cal.getTime());
		}
		else {
			return null;
		}
	}

	public static String getISODate(
		PortletRequest portletRequest, String param) {

		int month = ParamUtil.getInteger(portletRequest, param + "Month");
		int day = ParamUtil.getInteger(portletRequest, param + "Day");
		int year = ParamUtil.getInteger(portletRequest, param + "Year");
		int hour = ParamUtil.getInteger(portletRequest, param + "Hour", -1);
		int minute = ParamUtil.getInteger(portletRequest, param + "Minute", -1);
		int amPm = ParamUtil.getInteger(portletRequest, param + "AmPm");

		if ((month >= 0) && (day > 0) && (year > 0)) {
			Calendar cal = CalendarFactoryUtil.getCalendar();

			if ((hour == -1) || (minute == -1)) {
				cal.set(year, month, day);
			}
			else {
				if (amPm == Calendar.PM) {
					hour += 12;
				}

				cal.set(year, month, day, hour, minute, 0);
			}

			DateFormat isoFormat = DateUtil.getISOFormat();

			return isoFormat.format(cal.getTime());
		}
		else {
			return null;
		}
	}

	public static String getLike(HttpServletRequest request, String param) {
		return getLike(request, param, null, true);
	}

	public static String getLike(
		HttpServletRequest request, String param, boolean toLowerCase) {

		return getLike(request, param, null, toLowerCase);
	}

	public static String getLike(
		HttpServletRequest request, String param, String defaultValue) {

		return getLike(request, param, defaultValue, true);
	}

	public static String getLike(
		HttpServletRequest request, String param, String defaultValue,
		boolean toLowerCase) {

		String value = request.getParameter(param);

		if (value != null) {
			value = value.trim();

			if (toLowerCase) {
				value = value.toLowerCase();
			}
		}

		if (Validator.isNull(value)) {
			value = defaultValue;
		}
		else {
			value = StringPool.PERCENT + value + StringPool.PERCENT;
		}

		return value;
	}

	public static String getLike(PortletRequest portletRequest, String param) {
		return getLike(portletRequest, param, null, true);
	}

	public static String getLike(
		PortletRequest portletRequest, String param, boolean toLowerCase) {

		return getLike(portletRequest, param, null, toLowerCase);
	}

	public static String getLike(
		PortletRequest portletRequest, String param, String defaultValue) {

		return getLike(portletRequest, param, defaultValue, true);
	}

	public static String getLike(
		PortletRequest portletRequest, String param, String defaultValue,
		boolean toLowerCase) {

		String value = portletRequest.getParameter(param);

		if (value != null) {
			value = value.trim();

			if (toLowerCase) {
				value = value.toLowerCase();
			}
		}

		if (Validator.isNull(value)) {
			value = defaultValue;
		}
		else {
			value = StringPool.PERCENT + value + StringPool.PERCENT;
		}

		return value;
	}

	public static long getLong(HttpServletRequest request, String param) {
		return GetterUtil.getLong(getString(request, param));
	}

	public static long getLong(PortletRequest portletRequest, String param) {
		return GetterUtil.getLong(getString(portletRequest, param));
	}

	public static short getShort(HttpServletRequest request, String param) {
		return GetterUtil.getShort(getString(request, param));
	}

	public static short getShort(PortletRequest portletRequest, String param) {
		return GetterUtil.getShort(getString(portletRequest, param));
	}

	public static String getString(HttpServletRequest request, String param) {
		String value = ParamUtil.getString(request, param);

		if (Validator.isNull(value)) {
			return null;
		}
		else {
			return value;
		}
	}

	public static String getString(
		PortletRequest portletRequest, String param) {

		String value = ParamUtil.getString(portletRequest, param);

		if (Validator.isNull(value)) {
			return null;
		}
		else {
			return value;
		}
	}

}