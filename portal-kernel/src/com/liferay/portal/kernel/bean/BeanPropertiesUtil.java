/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bean;

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

	public static double getDouble(Object bean, String param) {
		return getBeanProperties().getDouble(bean, param);
	}

	public static double getDouble(
		Object bean, String param, double defaultValue) {

		return getBeanProperties().getDouble(bean, param, defaultValue);
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

	public static String getString(Object bean, String param) {
		return getBeanProperties().getString(bean, param);
	}

	public static String getString(
		Object bean, String param, String defaultValue) {

		return getBeanProperties().getString(bean, param, defaultValue);
	}

	public void setBeanProperties(BeanProperties beanProperties) {
		_beanProperties = beanProperties;
	}

	private static BeanProperties _beanProperties;

}