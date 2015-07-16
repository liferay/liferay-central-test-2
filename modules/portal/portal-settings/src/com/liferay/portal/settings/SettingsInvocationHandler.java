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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Iván Zaera
 */
public class SettingsInvocationHandler<S> implements InvocationHandler {

	public SettingsInvocationHandler(
		Class<S> clazz, Object settingsOverrideInstance,
		TypedSettings typedSettings) {

		_clazz = clazz;
		_settingsOverrideInstance = settingsOverrideInstance;
		_typedSettings = typedSettings;
	}

	public S createProxy() {
		return (S)ProxyUtil.newProxyInstance(
			_clazz.getClassLoader(), new Class[] {_clazz},
			this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws InvocationTargetException {

		if (_settingsOverrideInstance != null) {
			try {
				return _invokeSettingsOverride(method, args);
			}
			catch (InvocationTargetException ite) {
				throw ite;
			}
			catch (Exception e) {
			}
		}

		try {
			return _invokeTypedSettings(method);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object _invokeSettingsOverride(Method method, Object[] args)
		throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Class<?> clazz = _settingsOverrideInstance.getClass();

		method = clazz.getMethod(method.getName(), method.getParameterTypes());

		return method.invoke(_settingsOverrideInstance, args);
	}

	private Object _invokeTypedSettings(Method method)
		throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {

		Class<?> returnType = method.getReturnType();

		if (returnType.equals(boolean.class)) {
			return _typedSettings.getBooleanValue(method.getName());
		}
		else if (returnType.equals(double.class)) {
			return _typedSettings.getDoubleValue(method.getName());
		}
		else if (returnType.equals(float.class)) {
			return _typedSettings.getFloatValue(method.getName());
		}
		else if (returnType.equals(int.class)) {
			return _typedSettings.getIntegerValue(method.getName());
		}
		else if (returnType.equals(LocalizedValuesMap.class)) {
			return _typedSettings.getLocalizedValuesMap(method.getName());
		}
		else if (returnType.equals(long.class)) {
			return _typedSettings.getLongValue(method.getName());
		}
		else if (returnType.equals(String.class)) {
			return _typedSettings.getValue(method.getName());
		}
		else if (returnType.equals(String[].class)) {
			return _typedSettings.getValues(method.getName());
		}

		Constructor<?> constructor = returnType.getConstructor(String.class);

		return constructor.newInstance(
			_typedSettings.getValue(method.getName()));
	}

	private final Class<S> _clazz;
	private final Object _settingsOverrideInstance;
	private final TypedSettings _typedSettings;

}