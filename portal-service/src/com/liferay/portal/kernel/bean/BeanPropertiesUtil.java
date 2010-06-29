/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bean;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BeanPropertiesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BeanPropertiesUtil {

	public static void copyProperties(Object source, Object target) {
		getBeanProperties().copyProperties(source, target);
	}

	public static void copyProperties(
		Object source, Object target, Class<?> editable) {

		getBeanProperties().copyProperties(source, target, editable);
	}

	public static void copyProperties(
		Object source, Object target, String[] ignoreProperties) {

		getBeanProperties().copyProperties(source, target, ignoreProperties);
	}

	public static BeanProperties getBeanProperties() {
		return _beanProperties;
	}

	public static boolean getBoolean(Object bean, String param) {
		return getBeanProperties().getBoolean(bean, param);
	}

	public static boolean getBoolean(
		Object bean, String param, boolean defaultValue) {

		return getBeanProperties().getBoolean(bean, param, defaultValue);
	}

	public static byte getByte(Object bean, String param) {
		return getBeanProperties().getByte(bean, param);
	}

	public static byte getByte(
		Object bean, String param, byte defaultValue) {

		return getBeanProperties().getByte(bean, param, defaultValue);
	}

	public static double getDouble(Object bean, String param) {
		return getBeanProperties().getDouble(bean, param);
	}

	public static double getDouble(
		Object bean, String param, double defaultValue) {

		return getBeanProperties().getDouble(bean, param, defaultValue);
	}

	public static float getFloat(Object bean, String param) {
		return getBeanProperties().getFloat(bean, param);
	}

	public static float getFloat(
		Object bean, String param, float defaultValue) {

		return getBeanProperties().getFloat(bean, param, defaultValue);
	}

	public static int getInteger(Object bean, String param) {
		return getBeanProperties().getInteger(bean, param);
	}

	public static int getInteger(
		Object bean, String param, int defaultValue) {

		return getBeanProperties().getInteger(bean, param, defaultValue);
	}

	public static long getLong(Object bean, String param) {
		return getBeanProperties().getLong(bean, param);
	}

	public static long getLong(
		Object bean, String param, long defaultValue) {

		return getBeanProperties().getLong(bean, param, defaultValue);
	}

	public static Object getObject(Object bean, String param) {
		return getBeanProperties().getObject(bean, param);
	}

	public static Object getObject(
		Object bean, String param, Object defaultValue) {

		return getBeanProperties().getObject(bean, param, defaultValue);
	}

	public static short getShort(Object bean, String param) {
		return getBeanProperties().getShort(bean, param);
	}

	public static short getShort(
		Object bean, String param, short defaultValue) {

		return getBeanProperties().getShort(bean, param, defaultValue);
	}

	public static String getString(Object bean, String param) {
		return getBeanProperties().getString(bean, param);
	}

	public static String getString(
		Object bean, String param, String defaultValue) {

		return getBeanProperties().getString(bean, param, defaultValue);
	}

	public static String getString(
		Object bean, String param, boolean logErrors) {

		return getBeanProperties().getString(bean, param, logErrors);
	}

	public static String getString(
		Object bean, String param, String defaultValue, boolean logErrors) {

		return getBeanProperties().getString(
			bean, param, defaultValue, logErrors);
	}

	public static void setProperties(Object bean, HttpServletRequest request) {
		getBeanProperties().setProperties(bean, request);
	}

	public static void setProperty(Object bean, String param, Object value) {
		getBeanProperties().setProperty(bean, param, value);
	}

	public void setBeanProperties(BeanProperties beanProperties) {
		_beanProperties = beanProperties;
	}

	private static BeanProperties _beanProperties;

}