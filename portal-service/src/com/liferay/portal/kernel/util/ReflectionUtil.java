/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 */
public class ReflectionUtil {

	public static Field getDeclaredField(Class<?> classObj, String name)
		throws Exception {

		Field field = classObj.getDeclaredField(name);

		field.setAccessible(true);

		return field;
	}

	public static Method getDeclaredMethod(
			Class<?> classObj, String name, Class<?> ... parameterTypes)
		throws Exception {

		Method method = classObj.getDeclaredMethod(name, parameterTypes);

		method.setAccessible(true);

		return method;
	}

}