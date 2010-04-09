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

package com.liferay.portal.spring;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.springframework.aop.framework.AdvisedSupport;

/**
 * We are using a few Spring internal things which may change in Spring's future
 * release without any notification. To prevent the incompatible break, this
 * unit test tests all Spring's internal logic we are depending on.
 *
 * We should run this unit test everytime when we are trying to upgrade Spring.
 *
 * <a href="SpringCompatibilityTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class SpringCompatibilityTest extends TestCase {

	/**
	 * Checking for existence of
	 * org.springframework.aop.framework.JdkDynamicAopProxy and
	 * its advised field
	 */
	public void testJdkDynamicProxyCompatibility() {
		Class<?> proxyClass = null;
		try {
			proxyClass = Class.forName(
				"org.springframework.aop.framework.JdkDynamicAopProxy");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(
				"Class org.springframework.aop.framework.JdkDynamicAopProxy " +
				"does not exist anymore");
		}

		Field advisedSupportField = null;
		try {
			advisedSupportField = proxyClass.getDeclaredField("advised");
		} catch (Exception ex) {
			throw new RuntimeException("Field advised does not exist anymore");
		}

		Class<?> advisedSupportClass = advisedSupportField.getType();
		if (!advisedSupportClass.equals(AdvisedSupport.class)) {
			throw new RuntimeException("Field advised's type is no longer " +
				AdvisedSupport.class.getName() + ", but " +
				advisedSupportClass.getName());
		}
	}

}