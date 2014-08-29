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

package com.liferay.portal.kernel.test;

import static com.liferay.portal.kernel.util.ReflectionUtil.getDeclaredField;
import static com.liferay.portal.kernel.util.ReflectionUtil.getDeclaredMethod;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class ReflectionTestUtil {

	public static Method getBridgeMethod(
		Class<?> clazz, String methodName, Class<?>... parameterTypes) {

		Method method = getMethod(clazz, methodName, parameterTypes);

		if (method.isBridge()) {
			return method;
		}

		Method bridgeMethod = _findBridgeMethod(clazz.getMethods(), method);

		if (bridgeMethod != null) {
			return bridgeMethod;
		}

		while (clazz != null) {
			bridgeMethod = _findBridgeMethod(
				clazz.getDeclaredMethods(), method);

			if (bridgeMethod != null) {
				return bridgeMethod;
			}

			clazz =  clazz.getSuperclass();
		}

		ReflectionUtil.throwException(
			new NoSuchMethodException(
				"No bridge method on " + clazz + " with name " + methodName +
					" and parameter types " + Arrays.toString(parameterTypes)));

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getField(fieldName);

			field.setAccessible(true);

			_unfinalField(field);

			return field;
		}
		catch (NoSuchFieldException nsfe) {
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);

				field.setAccessible(true);

				_unfinalField(field);

				return field;
			}
			catch (NoSuchFieldException nsfe) {
				clazz = clazz.getSuperclass();
			}
			catch (Exception e) {
				ReflectionUtil.throwException(e);
			}
		}

		ReflectionUtil.throwException(
			new NoSuchFieldException(
				"No field on " + clazz + " with name " + fieldName));

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Object getFieldValue(Class<?> clazz, String fieldName) {
		Field field = getField(clazz, fieldName);

		try {
			return field.get(null);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Object getFieldValue(Object instance, String fieldName) {
		Field field = getField(instance.getClass(), fieldName);

		try {
			return field.get(instance);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Method getMethod(
		Class<?> clazz, String methodName, Class<?>... parameterTypes) {

		try {
			Method method = clazz.getMethod(methodName, parameterTypes);

			method.setAccessible(true);

			return method;
		}
		catch (NoSuchMethodException nsme) {
		}

		while (clazz != null) {
			try {
				Method method = clazz.getDeclaredMethod(
					methodName, parameterTypes);

				method.setAccessible(true);

				return method;
			}
			catch (NoSuchMethodException nsme) {
				clazz = clazz.getSuperclass();
			}
		}

		ReflectionUtil.throwException(
			new NoSuchMethodException(
				"No method on " + clazz + " with name " + methodName +
					" and parameter types " + Arrays.toString(parameterTypes)));

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Object invoke(
		Class<?> clazz, String methodName, Class<?>[] parameterTypes,
		Object... parameters) {

		Method method = getMethod(clazz, methodName, parameterTypes);

		try {
			return method.invoke(null, parameters);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Object invoke(
		Object instance, String methodName, Class<?>[] parameterTypes,
		Object... parameters) {

		Method method = getMethod(
			instance.getClass(), methodName, parameterTypes);

		try {
			return method.invoke(instance, parameters);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static Object invokeBridge(
		Object instance, String methodName, Class<?>[] parameterTypes,
		Object... parameters) {

		Method method = getBridgeMethod(
			instance.getClass(), methodName, parameterTypes);

		try {
			return method.invoke(instance, parameters);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static <T extends Enum<T>> T newEnumElement(
		Class<T> enumClass, Class<?>[] constructorParameterTypes, String name,
		int ordinal, Object... constructorParameters) {

		Class<?>[] parameterTypes = null;

		if ((constructorParameterTypes != null) &&
			(constructorParameterTypes.length != 0)) {

			parameterTypes = new Class<?>[constructorParameterTypes.length + 2];

			parameterTypes[0] = String.class;
			parameterTypes[1] = int.class;

			System.arraycopy(
				constructorParameterTypes, 0, parameterTypes, 2,
				constructorParameterTypes.length);
		}
		else {
			parameterTypes = new Class<?>[2];

			parameterTypes[0] = String.class;
			parameterTypes[1] = int.class;
		}

		try {
			Constructor<T> constructor = enumClass.getDeclaredConstructor(
				parameterTypes);

			Method acquireConstructorAccessorMethod = getDeclaredMethod(
				Constructor.class, "acquireConstructorAccessor");

			acquireConstructorAccessorMethod.invoke(constructor);

			Field constructorAccessorField = getDeclaredField(
				Constructor.class, "constructorAccessor");

			Object constructorAccessor = constructorAccessorField.get(
				constructor);

			Method newInstanceMethod = getDeclaredMethod(
				constructorAccessor.getClass(), "newInstance", Object[].class);

			Object[] parameters = null;

			if ((constructorParameters != null) &&
				(constructorParameters.length != 0)) {

				parameters = new Object[constructorParameters.length + 2];

				parameters[0] = name;
				parameters[1] = ordinal;

				System.arraycopy(
					constructorParameters, 0, parameters, 2,
					constructorParameters.length);
			}
			else {
				parameters = new Object[2];

				parameters[0] = name;
				parameters[1] = ordinal;
			}

			return (T)newInstanceMethod.invoke(
				constructorAccessor, new Object[] {parameters});
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		throw ReflectionUtil.SHOULD_NEVER_HAPPEN_ERROR;
	}

	public static <T extends Enum<T>> T newEnumElement(
		Class<T> enumClass, String name, int ordinal) {

		return newEnumElement(enumClass, null, name, ordinal, (Object[])null);
	}

	public static void setFieldValue(
		Class<?> clazz, String fieldName, Object value) {

		Field field = getField(clazz, fieldName);

		try {
			field.set(null, value);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	public static void setFieldValue(
		Object instance, String fieldName, Object value) {

		Field field = getField(instance.getClass(), fieldName);

		try {
			field.set(instance, value);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	private static Method _findBridgeMethod(Method[] methods, Method method) {
		String name = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();

		bridge:
		for (Method currentMethod : methods) {
			if (!currentMethod.isBridge() ||
				!name.equals(currentMethod.getName())) {

				continue;
			}

			Class<?>[] currentParameterTypes =
				currentMethod.getParameterTypes();

			if (currentParameterTypes.length != parameterTypes.length) {
				continue;
			}

			for (int i = 0; i < currentParameterTypes.length; i++) {
				if (!currentParameterTypes[i].isAssignableFrom(
						parameterTypes[i])) {

					continue bridge;
				}
			}

			currentMethod.setAccessible(true);

			return currentMethod;
		}

		return null;
	}

	private static void _unfinalField(Field field) throws Exception {
		int modifiers = field.getModifiers();

		Field modifiersField = ReflectionUtil.getDeclaredField(
			Field.class, "modifiers");

		modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
	}

}