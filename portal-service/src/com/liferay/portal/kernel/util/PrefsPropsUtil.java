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

package com.liferay.portal.kernel.util;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsUtil {

	public static String getString(long companyId, String key)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_METHOD_KEY, companyId,
				key);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getString(String key)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_METHOD_KEY2, key);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			long companyId, String name, String delimiter)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_ARRAY_METHOD_KEY,
				companyId, name, delimiter);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			long companyId, String name, String delimiter,
			String[] defaultValue)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_ARRAY_METHOD_KEY2,
				companyId, name, delimiter, defaultValue);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			PortletPreferences preferences, long companyId, String name,
			String delimiter)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_ARRAY_METHOD_KEY3,
				preferences, companyId, name, delimiter);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			PortletPreferences preferences, long companyId, String name,
			String delimiter, String[] defaultValue)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_ARRAY_METHOD_KEY4,
				preferences, companyId, name, delimiter, defaultValue);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(String name, String delimiter)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_ARRAY_METHOD_KEY5,
				name, delimiter);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(
			String name, String delimiter, String[] defaultValue)
		throws Exception {

		Object returnObj =
			PortalClassInvoker.invoke(false, _GET_STRING_ARRAY_METHOD_KEY6,
				name, delimiter, defaultValue);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS =
		"com.liferay.portal.util.PrefsPropsUtil";

	private static final MethodKey _GET_STRING_METHOD_KEY =
		new MethodKey(_CLASS, "getString", long.class, String.class);

	private static final MethodKey _GET_STRING_METHOD_KEY2 =
		new MethodKey(_CLASS, "getString", String.class);

	private static final MethodKey _GET_STRING_ARRAY_METHOD_KEY =
		new MethodKey(_CLASS, "getStringArray", long.class, String.class,
			String.class);

	private static final MethodKey _GET_STRING_ARRAY_METHOD_KEY2 =
		new MethodKey(_CLASS, "getStringArray", long.class, String.class,
			String.class, String[].class);

	private static final MethodKey _GET_STRING_ARRAY_METHOD_KEY3 =
		new MethodKey(_CLASS, "getStringArray", PortletPreferences.class,
			long.class, String.class, String.class);

	private static final MethodKey _GET_STRING_ARRAY_METHOD_KEY4 =
		new MethodKey(_CLASS, "getStringArray", PortletPreferences.class,
			long.class, String.class, String.class, String[].class);

	private static final MethodKey _GET_STRING_ARRAY_METHOD_KEY5 =
		new MethodKey(_CLASS, "getStringArray", String.class, String.class);

	private static final MethodKey _GET_STRING_ARRAY_METHOD_KEY6 =
		new MethodKey(_CLASS, "getStringArray", String.class, String.class,
			String[].class);

}