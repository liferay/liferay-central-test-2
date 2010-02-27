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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * <a href="ReflectionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReflectionUtil {

	public static Object getFieldValue(Class<?> classObj, String fieldName) {
		try {
			Field field = classObj.getDeclaredField(fieldName);

			field.setAccessible(true);

			return field.get(null);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	public static Object newInstance(String className, String p1) {
		try {
			Class<?> classObj = Class.forName(className);

			Constructor<?> classConstructor =
				classObj.getConstructor(new Class[] {String.class});

			Object[] args = new Object[] {p1};

			return classConstructor.newInstance(args);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ReflectionUtil.class);

}