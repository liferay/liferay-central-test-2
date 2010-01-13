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

package com.liferay.portal.kernel.util;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="MethodCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MethodCache {

	public static Method get(String className, String methodName)
		throws ClassNotFoundException, NoSuchMethodException {

		return get(null, null, className, methodName);
	}

	public static Method get(
			String className, String methodName, Class<?>[] parameterTypes)
		throws ClassNotFoundException, NoSuchMethodException {

		return get(null, null, className, methodName, parameterTypes);
	}

	public static Method get(
			Map<String, Class<?>> classesMap, Map<MethodKey, Method> methodsMap,
			String className, String methodName)
		throws ClassNotFoundException, NoSuchMethodException {

		return get(className, methodName, new Class[0]);
	}

	public static Method get(
			Map<String, Class<?>> classesMap, Map<MethodKey, Method> methodsMap,
			String className, String methodName, Class<?>[] parameterTypes)
		throws ClassNotFoundException, NoSuchMethodException {

		MethodKey methodKey = new MethodKey(
			classesMap, methodsMap, className, methodName, parameterTypes);

		return get(methodKey);
	}

	public static Method get(MethodKey methodKey)
		throws ClassNotFoundException, NoSuchMethodException {

		return _instance._get(methodKey);
	}

	public static Method put(MethodKey methodKey, Method method) {
		return _instance._put(methodKey, method);
	}

	private MethodCache() {
		_classesMap = new HashMap<String, Class<?>>();
		_methodsMap = new HashMap<MethodKey, Method>();
	}

	private Method _get(MethodKey methodKey)
		throws ClassNotFoundException, NoSuchMethodException {

		Map<String, Class<?>> classesMap = methodKey.getClassesMap();

		if (classesMap == null) {
			classesMap = _classesMap;
		}

		Map<MethodKey, Method> methodsMap = methodKey.getMethodsMap();

		if (methodsMap == null) {
			methodsMap = _methodsMap;
		}

		Method method = methodsMap.get(methodKey);

		if (method == null) {
			String className = methodKey.getClassName();
			String methodName = methodKey.getMethodName();
			Class<?>[] types = methodKey.getTypes();

			Class<?> classObj = classesMap.get(className);

			if (classObj == null) {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				classObj = contextClassLoader.loadClass(className);

				classesMap.put(className, classObj);
			}

			method = classObj.getMethod(methodName, types);

			methodsMap.put(methodKey, method);
		}

		return method;
	}

	public Method _put(MethodKey methodKey, Method method) {
		return _methodsMap.put(methodKey, method);
	}

	private static MethodCache _instance = new MethodCache();

	private Map<String, Class<?>> _classesMap;
	private Map<MethodKey, Method> _methodsMap;

}