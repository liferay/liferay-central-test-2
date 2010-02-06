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

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="SessionParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SessionParamUtil {

	public static boolean getBoolean(HttpServletRequest request, String param) {
		return getBoolean(request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		HttpServletRequest request, String param, boolean defaultValue) {

		HttpSession session = request.getSession();

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			boolean value = GetterUtil.getBoolean(requestValue);

			session.setAttribute(param, value);

			return value;
		}

		Boolean sessionValue = (Boolean)session.getAttribute(param);

		if (sessionValue != null) {
			return sessionValue;
		}

		return defaultValue;
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param) {

		return getBoolean(portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession();

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			boolean value = GetterUtil.getBoolean(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		Boolean portletSessionValue = (Boolean)portletSession.getAttribute(
			param);

		if (portletSessionValue != null) {
			return portletSessionValue;
		}

		return defaultValue;
	}

	public static double getDouble(HttpServletRequest request, String param) {
		return getDouble(request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue) {

		HttpSession session = request.getSession();

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			double value = GetterUtil.getDouble(requestValue);

			session.setAttribute(param, value);

			return value;
		}

		Double sessionValue = (Double)session.getAttribute(param);

		if (sessionValue != null) {
			return sessionValue;
		}

		return defaultValue;
	}

	public static double getDouble(
		PortletRequest portletRequest, String param) {

		return getDouble(portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession();

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			double value = GetterUtil.getDouble(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		Double portletSessionValue = (Double)portletSession.getAttribute(param);

		if (portletSessionValue != null) {
			return portletSessionValue;
		}

		return defaultValue;
	}

	public static int getInteger(HttpServletRequest request, String param) {
		return getInteger(request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		HttpServletRequest request, String param, int defaultValue) {

		HttpSession session = request.getSession();

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			int value = GetterUtil.getInteger(requestValue);

			session.setAttribute(param, value);

			return value;
		}

		Integer sessionValue = (Integer)session.getAttribute(param);

		if (sessionValue != null) {
			return sessionValue;
		}

		return defaultValue;
	}

	public static int getInteger(PortletRequest portletRequest, String param) {
		return getInteger(portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletRequest portletRequest, String param, int defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession();

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			int value = GetterUtil.getInteger(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		Integer portletSessionValue = (Integer)portletSession.getAttribute(
			param);

		if (portletSessionValue != null) {
			return portletSessionValue;
		}

		return defaultValue;
	}

	public static long getLong(HttpServletRequest request, String param) {
		return getLong(request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		HttpServletRequest request, String param, long defaultValue) {

		HttpSession session = request.getSession();

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			long value = GetterUtil.getLong(requestValue);

			session.setAttribute(param, value);

			return value;
		}

		Long sessionValue = (Long)session.getAttribute(param);

		if (sessionValue != null) {
			return sessionValue;
		}

		return defaultValue;
	}

	public static long getLong(PortletRequest portletRequest, String param) {
		return getLong(portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletRequest portletRequest, String param, long defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession();

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			long value = GetterUtil.getLong(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		Long portletSessionValue = (Long)portletSession.getAttribute(param);

		if (portletSessionValue != null) {
			return portletSessionValue;
		}

		return defaultValue;
	}

	public static short getShort(HttpServletRequest request, String param) {
		return getShort(request, param, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		HttpServletRequest request, String param, short defaultValue) {

		HttpSession session = request.getSession();

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			short value = GetterUtil.getShort(requestValue);

			session.setAttribute(param, value);

			return value;
		}

		Short sessionValue = (Short)session.getAttribute(param);

		if (sessionValue != null) {
			return sessionValue;
		}

		return defaultValue;
	}

	public static short getShort(PortletRequest portletRequest, String param) {
		return getShort(portletRequest, param, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		PortletRequest portletRequest, String param, short defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession();

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			short value = GetterUtil.getShort(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		Short portletSessionValue = (Short)portletSession.getAttribute(param);

		if (portletSessionValue != null) {
			return portletSessionValue;
		}

		return defaultValue;
	}

	public static String getString(HttpServletRequest request, String param) {
		return getString(request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		HttpServletRequest request, String param, String defaultValue) {

		HttpSession session = request.getSession();

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			String value = GetterUtil.getString(requestValue);

			session.setAttribute(param, value);

			return value;
		}

		String sessionValue = (String)session.getAttribute(param);

		if (sessionValue != null) {
			return sessionValue;
		}

		return defaultValue;
	}

	public static String getString(
		PortletRequest portletRequest, String param) {

		return getString(portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletRequest portletRequest, String param, String defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession();

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			String value = GetterUtil.getString(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		String portletSessionValue = (String)portletSession.getAttribute(param);

		if (portletSessionValue != null) {
			return portletSessionValue;
		}

		return defaultValue;
	}

}