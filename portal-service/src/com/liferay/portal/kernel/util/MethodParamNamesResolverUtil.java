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

import java.lang.reflect.Method;

/**
 * <a href="MethodParamNameResolverUtil.java.html"><b><i>View
 * Source</i></b></a>
 */
public class MethodParamNamesResolverUtil {

	public static MethodParamNamesResolver getMethodParamNamesResolver() {
		return _methodParamNamesResolver;
	}

	public static String[] resolveParamNames(Method method) {
		return getMethodParamNamesResolver().resolveParamNames(method);
	}

	public void setMethodParamNamesResolver
		(MethodParamNamesResolver methodParamNameResolver) {

		_methodParamNamesResolver = methodParamNameResolver;
	}

	private static MethodParamNamesResolver _methodParamNamesResolver;

}