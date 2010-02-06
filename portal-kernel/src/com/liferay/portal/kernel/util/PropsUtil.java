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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Properties;

/**
 * <a href="PropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PropsUtil {

	public static String get(String key) {
		String value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_CLASS, _METHOD_GET, key, false);

			if (returnObj != null) {
				value = (String)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	public static String get(String key, Filter filter) {
		String value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_CLASS, _METHOD_GET, key, filter, false);

			if (returnObj != null) {
				value = (String)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	public static String[] getArray(String key) {
		String[] value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_CLASS, _METHOD_GET_ARRAY, key, false);

			if (returnObj != null) {
				value = (String[])returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	public static Properties getProperties() {
		Properties properties = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_CLASS, _METHOD_GET_PROPERTIES, false);

			if (returnObj != null) {
				properties = (Properties)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return properties;
	}

	public static Properties getProperties(
		String prefix, boolean removePrefix) {

		Properties properties = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_CLASS, _METHOD_GET_PROPERTIES, prefix,
				new BooleanWrapper(removePrefix), false);

			if (returnObj != null) {
				properties = (Properties)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return properties;
	}

	private static final String _CLASS = "com.liferay.portal.util.PropsUtil";

	private static final String _METHOD_GET = "get";

	private static final String _METHOD_GET_ARRAY = "getArray";

	private static final String _METHOD_GET_PROPERTIES = "getProperties";

	private static Log _log = LogFactoryUtil.getLog(PropsUtil.class);

}