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

package com.liferay.poshi.runner;

import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.selenium.SeleniumUtil;

import java.lang.reflect.Method;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerExecutor {

	public static Object invoke(Method method, Object obj, String[] parameters)
		throws Exception {

		return method.invoke(obj);
	}

	public static void runSeleniumElement(Element element) throws Exception {
		String argument1 = element.attributeValue("argument1");
		String argument2 = element.attributeValue("argument2");
		String argument3 = element.attributeValue("argument3");
		String selenium = element.attributeValue("selenium");

		int x = PoshiRunnerContext.getSeleniumParameterCount(selenium);

		Class clazz = _liferaySelenium.getClass();

		if (x == 0) {
			Method method = clazz.getMethod(selenium, null);

			invoke(method, _liferaySelenium, new String[] {});
		}
		else if (x == 1) {
			Method method = clazz.getMethod(
				selenium, new Class[] {String.class});

			invoke(method, _liferaySelenium, new String[] {argument1});
		}
		else if (x == 2) {
			Method method = clazz.getMethod(
				selenium, new Class[] {String.class, String.class});

			invoke(
				method, _liferaySelenium, new String[] {argument1, argument2});
		}
		else if (x == 3) {
			Method method = clazz.getMethod(
				selenium,
				new Class[] {String.class, String.class, String.class});

			invoke(
				method, _liferaySelenium,
				new String[] {argument1, argument2, argument3});
		}
	}

	private static final LiferaySelenium _liferaySelenium =
		SeleniumUtil.getSelenium();

}