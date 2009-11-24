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

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BeanParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BeanParamUtil {

	public static boolean getBoolean(
		Object bean, HttpServletRequest request, String param) {

		return getBoolean(bean, request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Object bean, HttpServletRequest request, String param,
		boolean defaultValue) {

		defaultValue = BeanPropertiesUtil.getBoolean(bean, param, defaultValue);

		return ParamUtil.get(request, param, defaultValue);
	}

	public static boolean getBoolean(
		Object bean, PortletRequest portletRequest, String param) {

		return getBoolean(
			bean, portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Object bean, PortletRequest portletRequest, String param,
		boolean defaultValue) {

		defaultValue = BeanPropertiesUtil.getBoolean(bean, param, defaultValue);

		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public static double getDouble(
		Object bean, HttpServletRequest request, String param) {

		return getDouble(bean, request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Object bean, HttpServletRequest request, String param,
		double defaultValue) {

		defaultValue = BeanPropertiesUtil.getDouble(bean, param, defaultValue);

		return ParamUtil.get(request, param, defaultValue);
	}

	public static double getDouble(
		Object bean, PortletRequest portletRequest, String param) {

		return getDouble(
			bean, portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Object bean, PortletRequest portletRequest, String param,
		double defaultValue) {

		defaultValue = BeanPropertiesUtil.getDouble(bean, param, defaultValue);

		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public static int getInteger(
		Object bean, HttpServletRequest request, String param) {

		return getInteger(bean, request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Object bean, HttpServletRequest request, String param,
		int defaultValue) {

		defaultValue = BeanPropertiesUtil.getInteger(bean, param, defaultValue);

		return ParamUtil.get(request, param, defaultValue);
	}

	public static int getInteger(
		Object bean, PortletRequest portletRequest, String param) {

		return getInteger(
			bean, portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Object bean, PortletRequest portletRequest, String param,
		int defaultValue) {

		defaultValue = BeanPropertiesUtil.getInteger(bean, param, defaultValue);

		return ParamUtil.get(portletRequest, param, defaultValue);
	}

	public static long getLong(
		Object bean, HttpServletRequest request, String param) {

		return getLong(bean, request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Object bean, HttpServletRequest request, String param,
		long defaultValue) {

		defaultValue = BeanPropertiesUtil.getLong(bean, param, defaultValue);

		return ParamUtil.get(request, param, defaultValue);

	}

	public static long getLong(
		Object bean, PortletRequest portletRequest, String param) {

		return getLong(bean, portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Object bean, PortletRequest portletRequest, String param,
		long defaultValue) {

		defaultValue = BeanPropertiesUtil.getLong(bean, param, defaultValue);

		return ParamUtil.get(portletRequest, param, defaultValue);

	}

	public static String getString(
		Object bean, HttpServletRequest request, String param) {

		return getString(bean, request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Object bean, HttpServletRequest request, String param,
		String defaultValue) {

		defaultValue = BeanPropertiesUtil.getString(bean, param, defaultValue);

		return ParamUtil.get(request, param, defaultValue);

	}

	public static String getString(
		Object bean, PortletRequest portletRequest, String param) {

		return getString(
			bean, portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Object bean, PortletRequest portletRequest, String param,
		String defaultValue) {

		defaultValue = BeanPropertiesUtil.getString(bean, param, defaultValue);

		return ParamUtil.get(portletRequest, param, defaultValue);

	}

}