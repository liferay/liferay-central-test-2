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
 * <a href="PrefsPropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsUtil {

	public static String getString(long companyId, String key)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING, new LongWrapper(companyId), key, false);

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

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING_ARRAY, new LongWrapper(companyId), name,
			delimiter, false);

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

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING_ARRAY, new LongWrapper(companyId), name,
			delimiter, defaultValue, false);

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

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING_ARRAY, preferences,
			new LongWrapper(companyId), name, delimiter, false);

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

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING_ARRAY, preferences,
			new LongWrapper(companyId), name, delimiter, defaultValue, false);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getStringArray(String name, String delimiter)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING_ARRAY, name, delimiter, false);

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

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GET_STRING_ARRAY, name, delimiter, defaultValue,
			false);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS =
		"com.liferay.portal.util.PrefsPropsUtil";

	private static final String _METHOD_GET_STRING = "getString";

	private static final String _METHOD_GET_STRING_ARRAY = "getStringArray";

}