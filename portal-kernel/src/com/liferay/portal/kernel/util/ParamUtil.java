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

import java.text.DateFormat;

import java.util.Date;
import java.util.Enumeration;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ParamUtil {

	public static boolean get(
		HttpServletRequest request, String param, boolean defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static Date get(
		HttpServletRequest request, String param, DateFormat df,
		Date defaultValue) {

		return GetterUtil.get(request.getParameter(param), df, defaultValue);
	}

	public static double get(
		HttpServletRequest request, String param, double defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static float get(
		HttpServletRequest request, String param, float defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static int get(
		HttpServletRequest request, String param, int defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static long get(
		HttpServletRequest request, String param, long defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static short get(
		HttpServletRequest request, String param, short defaultValue) {

		return GetterUtil.get(request.getParameter(param), defaultValue);
	}

	public static String get(
		HttpServletRequest request, String param, String defaultValue) {

		String returnValue =
			GetterUtil.get(request.getParameter(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	public static boolean get(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static Date get(
		PortletRequest portletRequest, String param, DateFormat df,
		Date defaultValue) {

		return GetterUtil.get(
			portletRequest.getParameter(param), df, defaultValue);
	}

	public static double get(
		PortletRequest portletRequest, String param, double defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static float get(
		PortletRequest portletRequest, String param, float defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static int get(
		PortletRequest portletRequest, String param, int defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static long get(
		PortletRequest portletRequest, String param, long defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static short get(
		PortletRequest portletRequest, String param, short defaultValue) {

		return GetterUtil.get(portletRequest.getParameter(param), defaultValue);
	}

	public static String get(
		PortletRequest portletRequest, String param, String defaultValue) {

		String returnValue =
			GetterUtil.get(portletRequest.getParameter(param), defaultValue);

		if (returnValue != null) {
			return returnValue.trim();
		}

		return null;
	}

	public static boolean getBoolean(HttpServletRequest request, String param) {
		return GetterUtil.getBoolean(request.getParameter(param));
	}

	public static boolean getBoolean(
		HttpServletRequest request, String param, boolean defaultValue) {

		return get(request, param, defaultValue);
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getBoolean(portletRequest.getParameter(param));
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static boolean[] getBooleanValues(
		HttpServletRequest request, String param) {

		return getBooleanValues(request, param, new boolean[0]);
	}

	public static boolean[] getBooleanValues(
		HttpServletRequest request, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			request.getParameterValues(param), defaultValue);
	}

	public static boolean[] getBooleanValues(
		PortletRequest portletRequest, String param) {

		return getBooleanValues(portletRequest, param, new boolean[0]);
	}

	public static boolean[] getBooleanValues(
		PortletRequest portletRequest, String param, boolean[] defaultValue) {

		return GetterUtil.getBooleanValues(
			portletRequest.getParameterValues(param), defaultValue);
	}

	public static Date getDate(
		HttpServletRequest request, String param, DateFormat df) {

		return GetterUtil.getDate(request.getParameter(param), df);
	}

	public static Date getDate(
		HttpServletRequest request, String param, DateFormat df,
		Date defaultValue) {

		return get(request, param, df, defaultValue);
	}

	public static Date getDate(
		PortletRequest portletRequest, String param, DateFormat df) {

		return GetterUtil.getDate(portletRequest.getParameter(param), df);
	}

	public static Date getDate(
		PortletRequest portletRequest, String param, DateFormat df,
		Date defaultValue) {

		return get(portletRequest, param, df, defaultValue);
	}

	public static double getDouble(HttpServletRequest request, String param) {
		return GetterUtil.getDouble(request.getParameter(param));
	}

	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue) {

		return get(request, param, defaultValue);
	}

	public static double getDouble(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getDouble(portletRequest.getParameter(param));
	}

	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static double[] getDoubleValues(
		HttpServletRequest request, String param) {

		return getDoubleValues(request, param, new double[0]);
	}

	public static double[] getDoubleValues(
		HttpServletRequest request, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			request.getParameterValues(param), defaultValue);
	}

	public static double[] getDoubleValues(
		PortletRequest portletRequest, String param) {

		return getDoubleValues(portletRequest, param, new double[0]);
	}

	public static double[] getDoubleValues(
		PortletRequest portletRequest, String param, double[] defaultValue) {

		return GetterUtil.getDoubleValues(
			portletRequest.getParameterValues(param), defaultValue);
	}

	public static float getFloat(HttpServletRequest request, String param) {
		return GetterUtil.getFloat(request.getParameter(param));
	}

	public static float getFloat(
		HttpServletRequest request, String param, float defaultValue) {

		return get(request, param, defaultValue);
	}

	public static float getFloat(PortletRequest portletRequest, String param) {
		return GetterUtil.getFloat(portletRequest.getParameter(param));
	}

	public static float getFloat(
		PortletRequest portletRequest, String param, float defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static float[] getFloatValues(
		HttpServletRequest request, String param) {

		return getFloatValues(request, param, new float[0]);
	}

	public static float[] getFloatValues(
		HttpServletRequest request, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			request.getParameterValues(param), defaultValue);
	}

	public static float[] getFloatValues(
		PortletRequest portletRequest, String param) {

		return getFloatValues(portletRequest, param, new float[0]);
	}

	public static float[] getFloatValues(
		PortletRequest portletRequest, String param, float[] defaultValue) {

		return GetterUtil.getFloatValues(
			portletRequest.getParameterValues(param), defaultValue);
	}

	public static int getInteger(HttpServletRequest request, String param) {
		return GetterUtil.getInteger(request.getParameter(param));
	}

	public static int getInteger(
		HttpServletRequest request, String param, int defaultValue) {

		return get(request, param, defaultValue);
	}

	public static int getInteger(PortletRequest portletRequest, String param) {
		return GetterUtil.getInteger(portletRequest.getParameter(param));
	}

	public static int getInteger(
		PortletRequest portletRequest, String param, int defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static int[] getIntegerValues(
		HttpServletRequest request, String param) {

		return getIntegerValues(request, param, new int[0]);
	}

	public static int[] getIntegerValues(
		HttpServletRequest request, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			request.getParameterValues(param), defaultValue);
	}

	public static int[] getIntegerValues(
		PortletRequest portletRequest, String param) {

		return getIntegerValues(portletRequest, param, new int[0]);
	}

	public static int[] getIntegerValues(
		PortletRequest portletRequest, String param, int[] defaultValue) {

		return GetterUtil.getIntegerValues(
			portletRequest.getParameterValues(param), defaultValue);
	}

	public static long getLong(HttpServletRequest request, String param) {
		return GetterUtil.getLong(request.getParameter(param));
	}

	public static long getLong(
		HttpServletRequest request, String param, long defaultValue) {

		return get(request, param, defaultValue);
	}

	public static long getLong(PortletRequest portletRequest, String param) {
		return GetterUtil.getLong(portletRequest.getParameter(param));
	}

	public static long getLong(
		PortletRequest portletRequest, String param, long defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static long[] getLongValues(
		HttpServletRequest request, String param) {

		return getLongValues(request, param, new long[0]);
	}

	public static long[] getLongValues(
		HttpServletRequest request, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			request.getParameterValues(param), defaultValue);
	}

	public static long[] getLongValues(
		PortletRequest portletRequest, String param) {

		return getLongValues(portletRequest, param, new long[0]);
	}

	public static long[] getLongValues(
		PortletRequest portletRequest, String param, long[] defaultValue) {

		return GetterUtil.getLongValues(
			portletRequest.getParameterValues(param), defaultValue);
	}

	public static short getShort(HttpServletRequest request, String param) {
		return GetterUtil.getShort(request.getParameter(param));
	}

	public static short getShort(
		HttpServletRequest request, String param, short defaultValue) {

		return get(request, param, defaultValue);
	}

	public static short getShort(PortletRequest portletRequest, String param) {
		return GetterUtil.getShort(portletRequest.getParameter(param));
	}

	public static short getShort(
		PortletRequest portletRequest, String param, short defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static short[] getShortValues(
		HttpServletRequest request, String param) {

		return getShortValues(request, param, new short[0]);
	}

	public static short[] getShortValues(
		HttpServletRequest request, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			request.getParameterValues(param), defaultValue);
	}

	public static short[] getShortValues(
		PortletRequest portletRequest, String param) {

		return getShortValues(portletRequest, param, new short[0]);
	}

	public static short[] getShortValues(
		PortletRequest portletRequest, String param, short[] defaultValue) {

		return GetterUtil.getShortValues(
			portletRequest.getParameterValues(param), defaultValue);
	}

	public static String getString(HttpServletRequest request, String param) {
		return GetterUtil.getString(request.getParameter(param));
	}

	public static String getString(
		HttpServletRequest request, String param, String defaultValue) {

		return get(request, param, defaultValue);
	}

	public static String getString(
		PortletRequest portletRequest, String param) {

		return GetterUtil.getString(portletRequest.getParameter(param));
	}

	public static String getString(
		PortletRequest portletRequest, String param, String defaultValue) {

		return get(portletRequest, param, defaultValue);
	}

	public static void print(HttpServletRequest request) {
		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			String[] values = request.getParameterValues(param);

			for (int i = 0; i < values.length; i++) {
				System.out.println(param + "[" + i + "] = " + values[i]);
			}
		}
	}

	public static void print(PortletRequest portletRequest) {
		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			String[] values = portletRequest.getParameterValues(param);

			for (int i = 0; i < values.length; i++) {
				System.out.println(param + "[" + i + "] = " + values[i]);
			}
		}
	}

}