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

package com.liferay.portal.json.serializer;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Method;

import java.util.Date;

import jodd.introspector.ClassDescriptor;
import jodd.introspector.ClassIntrospector;

import jodd.util.ReflectUtil;

/**
 * @author Igor Spasic
 */
public class DefaultJSONSerializer implements JSONSerializer<Object> {

	public String toJSONString(Object object) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		ClassDescriptor cd = ClassIntrospector.lookup(object.getClass());

		Method[] getters = cd.getAllBeanGetters();

		for (Method getter : getters) {

			String propertyName = getter.getName();

			if (propertyName.startsWith("is")) {
				propertyName = propertyName.substring(2);
			}
			else {
				propertyName = propertyName.substring(3);
			}

			propertyName = jodd.util.StringUtil.uncapitalize(propertyName);

			Class propertyType = getter.getReturnType();

			if (propertyType.equals(boolean.class) ||
				propertyType.equals(Boolean.class)) {

				Boolean value = (Boolean)_invokeGetter(getter, object);

				if (value == null) {
					jsonObject.put(propertyName, "null");
				}
				else {
					jsonObject.put(propertyName, value.booleanValue());
				}
				continue;
			}

			if (propertyType.equals(long.class) ||
				propertyType.equals(Long.class)) {

				Long value = (Long)_invokeGetter(getter, object);

				if (value == null) {
					jsonObject.put(propertyName, "null");
				}
				else {
					jsonObject.put(propertyName, value.longValue());
				}
				continue;
			}

			if (propertyType.equals(int.class) ||
				propertyType.equals(Integer.class)) {

				Integer value = (Integer)_invokeGetter(getter, object);

				if (value == null) {
					jsonObject.put(propertyName, "null");
				}
				else {
					jsonObject.put(propertyName, value.intValue());
				}
				continue;
			}

			if (propertyType.equals(float.class) ||
				propertyType.equals(Float.class)) {

				Float value = (Float)_invokeGetter(getter, object);

				if (value == null) {
					jsonObject.put(propertyName, "null");
				}
				else {
					jsonObject.put(propertyName, value.floatValue());
				}
				continue;
			}

			if (propertyType.equals(double.class) ||
				propertyType.equals(Double.class)) {

				Double value = (Double)_invokeGetter(getter, object);

				if (value == null) {
					jsonObject.put(propertyName, "null");
				}
				else {
					jsonObject.put(propertyName, value.doubleValue());
				}
				continue;
			}

			if (propertyType.equals(String.class)) {
				String value = (String)_invokeGetter(getter, object);

				jsonObject.put(propertyName, value);

				continue;
			}

			if (ReflectUtil.isSubclass(propertyType, Date.class)) {
				Date value = (Date)_invokeGetter(getter, object);

				if (value == null) {
					jsonObject.put(propertyName, "null");
				}
				else {
					jsonObject.put(propertyName,
						String.valueOf(value.getTime()));
				}
				continue;
			}
		}

		return jsonObject.toString();
	}

	private Object _invokeGetter(Method getter, Object object) {
		try {
			return getter.invoke(object);
		}
		catch (Exception e) {
			_log.error(e, e);
			return e.toString();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultJSONSerializer.class);

}