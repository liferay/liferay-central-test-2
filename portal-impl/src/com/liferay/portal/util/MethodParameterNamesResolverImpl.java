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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodParameterNamesResolver;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import jodd.paramo.Paramo;
import jodd.paramo.ParamoException;

/**
 * @author Igor Spasic
 */
public class MethodParameterNamesResolverImpl
	implements MethodParameterNamesResolver {

	public String[] resolveParameterNames(Method method) {
		String[] parameterNames = _parameterNames.get(method);

		if (parameterNames == null) {
			try {
				parameterNames = Paramo.resolveParameterNames(method);
			}
			catch (ParamoException pe) {
				_log.error(pe, pe);

				return null;
			}

			_parameterNames.put(method, parameterNames);
		}

		return parameterNames;
	}

	private static Log _log = LogFactoryUtil.getLog(
		MethodParameterNamesResolverImpl.class);

	private Map<AccessibleObject, String[]> _parameterNames =
		new HashMap<AccessibleObject, String[]>();

}