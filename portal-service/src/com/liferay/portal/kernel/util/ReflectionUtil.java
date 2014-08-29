/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class ReflectionUtil {

	public static final Error SHOULD_NEVER_HAPPEN_ERROR = new AssertionError();

	public static Class<?> getAnnotationDeclaringClass(
		Class<? extends Annotation> annotationClass, Class<?> clazz) {

		if ((clazz == null) || clazz.equals(Object.class)) {
			return null;
		}

		if (isAnnotationDeclaredInClass(annotationClass, clazz)) {
			return clazz;
		}
		else {
			return getAnnotationDeclaringClass(
				annotationClass, clazz.getSuperclass());
		}
	}

	public static Field getDeclaredField(Class<?> clazz, String name)
		throws Exception {

		Field field = clazz.getDeclaredField(name);

		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		int modifiers = field.getModifiers();

		if ((modifiers & Modifier.FINAL) == Modifier.FINAL) {
			Field modifiersField = getDeclaredField(Field.class, "modifiers");

			modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
		}

		return field;
	}

	public static Method getDeclaredMethod(
			Class<?> clazz, String name, Class<?> ... parameterTypes)
		throws Exception {

		Method method = clazz.getDeclaredMethod(name, parameterTypes);

		if (!method.isAccessible()) {
			method.setAccessible(true);
		}

		return method;
	}

	public static Class<?> getGenericSuperType(Class<?> clazz) {
		try {
			ParameterizedType parameterizedType =
				(ParameterizedType)clazz.getGenericSuperclass();

			Type[] types = parameterizedType.getActualTypeArguments();

			if (types.length > 0) {
				return (Class<?>)types[0];
			}
		}
		catch (Throwable t) {
		}

		return null;
	}

	public static Class<?>[] getInterfaces(Object object) {
		return getInterfaces(object, null);
	}

	public static Class<?>[] getInterfaces(
		Object object, ClassLoader classLoader) {

		Set<Class<?>> interfaceClasses = new LinkedHashSet<Class<?>>();

		Class<?> clazz = object.getClass();

		_getInterfaces(interfaceClasses, clazz, classLoader);

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			_getInterfaces(interfaceClasses, superClass, classLoader);

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[interfaceClasses.size()]);
	}

	public static Class<?>[] getParameterTypes(Object[] arguments) {
		if (arguments == null) {
			return null;
		}

		Class<?>[] parameterTypes = new Class<?>[arguments.length];

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				parameterTypes[i] = null;
			}
			else if (arguments[i] instanceof Boolean) {
				parameterTypes[i] = Boolean.TYPE;
			}
			else if (arguments[i] instanceof Byte) {
				parameterTypes[i] = Byte.TYPE;
			}
			else if (arguments[i] instanceof Character) {
				parameterTypes[i] = Character.TYPE;
			}
			else if (arguments[i] instanceof Double) {
				parameterTypes[i] = Double.TYPE;
			}
			else if (arguments[i] instanceof Float) {
				parameterTypes[i] = Float.TYPE;
			}
			else if (arguments[i] instanceof Integer) {
				parameterTypes[i] = Integer.TYPE;
			}
			else if (arguments[i] instanceof Long) {
				parameterTypes[i] = Long.TYPE;
			}
			else if (arguments[i] instanceof Short) {
				parameterTypes[i] = Short.TYPE;
			}
			else {
				parameterTypes[i] = arguments[i].getClass();
			}
		}

		return parameterTypes;
	}

	public static Set<Method> getVisibleMethods(Class<?> clazz) {
		Set<Method> visibleMethods = new HashSet<Method>(
			Arrays.asList(clazz.getMethods()));

		visibleMethods.addAll(Arrays.asList(clazz.getDeclaredMethods()));

		while ((clazz = clazz.getSuperclass()) != null) {
			for (Method method : clazz.getDeclaredMethods()) {
				int modifiers = method.getModifiers();

				if (!Modifier.isPrivate(modifiers) &
					!Modifier.isPublic(modifiers)) {

					visibleMethods.add(method);
				}
			}
		}

		return visibleMethods;
	}

	public static boolean isAnnotationDeclaredInClass(
		Class<? extends Annotation> annotationClass, Class<?> clazz) {

		if ((annotationClass == null) || (clazz == null)) {
			throw new IllegalArgumentException();
		}

		Annotation[] annotations = clazz.getAnnotations();

		for (Annotation annotation : annotations) {
			if (annotationClass.equals(annotation.annotationType())) {
				return true;
			}
		}

		return false;
	}

	public static void throwException(Throwable throwable) {
		ReflectionUtil.<RuntimeException>_doThrowException(throwable);
	}

	@SuppressWarnings("unchecked")
	private static <E extends Throwable> void _doThrowException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private static void _getInterfaces(
		Set<Class<?>> interfaceClasses, Class<?> clazz,
		ClassLoader classLoader) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			try {
				if (classLoader != null) {
					interfaceClasses.add(
						classLoader.loadClass(interfaceClass.getName()));
				}
				else {
					interfaceClasses.add(interfaceClass);
				}
			}
			catch (ClassNotFoundException cnfe) {
			}
		}
	}

}