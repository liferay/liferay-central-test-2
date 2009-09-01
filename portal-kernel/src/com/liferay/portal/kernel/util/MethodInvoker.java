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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="MethodInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Shuyang Zhou
 */
public class MethodInvoker {

	public static Object invoke(MethodWrapper methodWrapper)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException, InvocationTargetException,
			   NoSuchFieldException, NoSuchMethodException {

		return invoke(methodWrapper, true);
	}

	public static Object invoke(
			MethodWrapper methodWrapper, boolean newInstance)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException, InvocationTargetException,
			   NoSuchFieldException, NoSuchMethodException {

		Object targetObject = null;

		if (newInstance){
			Thread currentThread = Thread.currentThread();
			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();
			Class<?> classObj =
				contextClassLoader.loadClass(methodWrapper.getClassName());
			targetObject=classObj.newInstance();
		}

		Object[] methodAndArgs = lookupMethod(methodWrapper, targetObject);

		Object returnObj = null;

		if (methodAndArgs[0] != null) {
			Method method = (Method) methodAndArgs[0];
			Object[] args = (Object[]) methodAndArgs[1];
			returnObj = method.invoke(targetObject, args);
		}

		return returnObj;
	}

	public static Object invoke(
			MethodWrapper methodWrapper, Object targetObject)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException, InvocationTargetException,
			   NoSuchFieldException, NoSuchMethodException {

		Object[] methodAndArgs = lookupMethod(methodWrapper, targetObject);

		Object returnObj = null;

		if (methodAndArgs[0] != null) {
			Method method = (Method) methodAndArgs[0];
			Object[] args = (Object[]) methodAndArgs[1];
			returnObj = method.invoke(targetObject, args);
		}

		return returnObj;
	}

	private static Object[] lookupMethod(
			MethodWrapper methodWrapper, Object targetObject)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException, InvocationTargetException,
			   NoSuchFieldException, NoSuchMethodException {

		Object[] methodAndArgs = new Object[2];
		Thread currentThread = Thread.currentThread();
		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		String className = methodWrapper.getClassName();
		String methodName = methodWrapper.getMethodName();
		Object[] args = methodWrapper.getArgs();

		List<Class<?>> parameterTypes = new ArrayList<Class<?>>();

		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				_log.error(
					"Cannot invoke " + className + " " + methodName +
						" on position " + i + " because it is null");
			}

			Class<?> argClass = args[i].getClass();

			if (ClassUtil.isSubclass(argClass, PrimitiveWrapper.class)) {
				parameterTypes.add(
					(Class<?>)argClass.getField("TYPE").get(args[i]));

				MethodKey methodKey = new MethodKey(
					argClass.getName(), "getValue", null);

				Method method = MethodCache.get(methodKey);

				args[i] = method.invoke(args[i], (Object[])null);
			}
			else if (args[i] instanceof NullWrapper) {
				NullWrapper nullWrapper = (NullWrapper)args[i];

				String wrappedClassName = nullWrapper.getClassName();

				if (wrappedClassName.startsWith(StringPool.OPEN_BRACKET) &&
					wrappedClassName.endsWith(StringPool.SEMICOLON)) {

					wrappedClassName = wrappedClassName.substring(
						2, wrappedClassName.length() - 1);

					Class<?> wrappedClass = contextClassLoader.loadClass(
						wrappedClassName);

					parameterTypes.add(
						Array.newInstance(wrappedClass, 0).getClass());
				}
				else {
					Class<?> wrappedClass = contextClassLoader.loadClass(
						wrappedClassName);

					parameterTypes.add(wrappedClass);
				}

				args[i] = null;
			}
			else {
				parameterTypes.add(argClass);
			}
		}

		Method method = null;

		try {
			MethodKey methodKey = new MethodKey(
				methodWrapper.getClassName(), methodWrapper.getMethodName(),
				parameterTypes.toArray(new Class[parameterTypes.size()]));

			method = MethodCache.get(methodKey);
		}
		catch (NoSuchMethodException nsme) {

			Class<?> classObj = null;
			if (targetObject == null) {
				classObj = contextClassLoader.loadClass(className);
			} else {
				classObj = targetObject.getClass();
			}
			Method[] methods = classObj.getMethods();

			for (int i = 0; i < methods.length; i++) {
				Class<?>[] methodParameterTypes =
					methods[i].getParameterTypes();

				if (methods[i].getName().equals(methodName) &&
					methodParameterTypes.length == parameterTypes.size()) {

					boolean correctParams = true;

					for (int j = 0; j < parameterTypes.size(); j++) {
						Class<?> a = parameterTypes.get(j);
						Class<?> b = methodParameterTypes[j];

						if (!ClassUtil.isSubclass(a, b)) {
							correctParams = false;

							break;
						}
					}

					if (correctParams) {
						method = methods[i];

						break;
					}
				}
			}

			if (method == null) {
				throw nsme;
			}
		}
		methodAndArgs[0] = method;
		methodAndArgs[1] = args;
		return methodAndArgs;
	}

	private static Log _log = LogFactoryUtil.getLog(MethodInvoker.class);

}