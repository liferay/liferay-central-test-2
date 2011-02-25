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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.MethodParamNamesResolver;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import jodd.paramo.Paramo;

/**
 * <a href="MethodParamNamesResolverUtilImpl.java.html"><b><i>View
 * Source</i></b></a>
 */
public class MethodParamNamesResolverImpl implements MethodParamNamesResolver {

	public String[] resolveParamNames(Method method) {

		String[] names = _cache.get(method);

		if (names == null) {

			names = Paramo.resolveParameterNames(method);

			_cache.put(method, names);
		}

		return names;
	}

	private final Map<AccessibleObject, String[]> _cache =
		new HashMap<AccessibleObject, String[]>();

}