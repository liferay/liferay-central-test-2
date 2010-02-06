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

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PrefsParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PrefsParamUtil {

	public static boolean getBoolean(
		PortletPreferences preferences, HttpServletRequest request,
		String param) {

		return getBoolean(
			preferences, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, HttpServletRequest request,
		String param, boolean defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			preferencesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getBoolean(
			preferences, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, boolean defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		PortletPreferences preferences, HttpServletRequest request,
		String param) {

		return getDouble(
			preferences, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletPreferences preferences, HttpServletRequest request,
		String param, double defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			preferencesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static double getDouble(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getDouble(
			preferences, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, double defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static int getInteger(
		PortletPreferences preferences, HttpServletRequest request,
		String param) {

		return getInteger(
			preferences, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletPreferences preferences, HttpServletRequest request,
		String param, int defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			preferencesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static int getInteger(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getInteger(
			preferences, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, int defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		PortletPreferences preferences, HttpServletRequest request,
		String param) {

		return getLong(preferences, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletPreferences preferences, HttpServletRequest request,
		String param, long defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		long getterUtilValue = GetterUtil.getLong(
			preferencesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static long getLong(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getLong(
			preferences, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, long defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		long getterUtilValue = GetterUtil.getLong(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static String getString(
		PortletPreferences preferences, HttpServletRequest request,
		String param) {

		return getString(
			preferences, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletPreferences preferences, HttpServletRequest request,
		String param, String defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		String getterUtilValue = GetterUtil.getString(
			preferencesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static String getString(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param) {

		return getString(
			preferences, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletPreferences preferences, PortletRequest portletRequest,
		String param, String defaultValue) {

		String preferencesValue = preferences.getValue(param, null);

		String getterUtilValue = GetterUtil.getString(
			preferencesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

}