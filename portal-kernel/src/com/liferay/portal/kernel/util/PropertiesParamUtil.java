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

package com.liferay.portal.kernel.util;

import java.util.Properties;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PropertiesParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PropertiesParamUtil {

	public static boolean getBoolean(
		Properties properties, HttpServletRequest request, String param) {

		return getBoolean(
			properties, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Properties properties, HttpServletRequest request, String param,
		boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static boolean getBoolean(
		Properties properties, PortletRequest portletRequest, String param) {

		return getBoolean(
			properties, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Properties properties, PortletRequest portletRequest, String param,
		boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getBoolean(
			properties, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, HttpServletRequest request, String param,
		boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getBoolean(
			properties, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, boolean defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		boolean getterUtilValue = GetterUtil.getBoolean(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		Properties properties, HttpServletRequest request, String param) {

		return getDouble(properties, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Properties properties, HttpServletRequest request, String param,
		double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static double getDouble(
		Properties properties, PortletRequest portletRequest, String param) {

		return getDouble(
			properties, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Properties properties, PortletRequest portletRequest, String param,
		double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static double getDouble(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getDouble(properties, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		UnicodeProperties properties, HttpServletRequest request, String param,
		double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static double getDouble(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getDouble(
			properties, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, double defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		double getterUtilValue = GetterUtil.getDouble(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static int getInteger(
		Properties properties, HttpServletRequest request, String param) {

		return getInteger(
			properties, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Properties properties, HttpServletRequest request, String param,
		int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static int getInteger(
		Properties properties, PortletRequest portletRequest, String param) {

		return getInteger(
			properties, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Properties properties, PortletRequest portletRequest, String param,
		int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static int getInteger(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getInteger(
			properties, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		UnicodeProperties properties, HttpServletRequest request, String param,
		int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static int getInteger(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getInteger(
			properties, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, int defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		int getterUtilValue = GetterUtil.getInteger(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		Properties properties, HttpServletRequest request, String param) {

		return getLong(properties, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Properties properties, HttpServletRequest request, String param,
		long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static long getLong(
		Properties properties, PortletRequest portletRequest, String param) {

		return getLong(
			properties, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Properties properties, PortletRequest portletRequest, String param,
		long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static long getLong(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getLong(properties, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		UnicodeProperties properties, HttpServletRequest request, String param,
		long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static long getLong(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getLong(
			properties, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, long defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		long getterUtilValue = GetterUtil.getLong(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static UnicodeProperties getProperties(
		PortletRequest portletRequest, String prefix) {

		UnicodeProperties properties = new UnicodeProperties(true);

		for (String param : portletRequest.getParameterMap().keySet()) {
			if (param.startsWith(prefix) && !param.endsWith(")Checkbox")) {
				String key = param.substring(
					prefix.length(), param.length() - 1);
				String value = portletRequest.getParameter(param);

				properties.setProperty(key, value);
			}
		}

		return properties;
	}

	public static String getString(
		Properties properties, HttpServletRequest request, String param) {

		return getString(properties, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Properties properties, HttpServletRequest request, String param,
		String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static String getString(
		Properties properties, PortletRequest portletRequest, String param) {

		return getString(
			properties, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Properties properties, PortletRequest portletRequest, String param,
		String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

	public static String getString(
		UnicodeProperties properties, HttpServletRequest request,
		String param) {

		return getString(properties, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		UnicodeProperties properties, HttpServletRequest request, String param,
		String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(request, param, getterUtilValue);
	}

	public static String getString(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param) {

		return getString(
			properties, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		UnicodeProperties properties, PortletRequest portletRequest,
		String param, String defaultValue) {

		String propertiesValue = properties.getProperty(param, null);

		String getterUtilValue = GetterUtil.getString(
			propertiesValue, defaultValue);

		return ParamUtil.get(portletRequest, param, getterUtilValue);
	}

}