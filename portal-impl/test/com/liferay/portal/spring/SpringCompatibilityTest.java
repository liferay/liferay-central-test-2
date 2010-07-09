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

import com.liferay.portal.util.BaseTestCase;

import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Shuyang Zhou
 */
public class SpringCompatibilityTest extends BaseTestCase {

	public void testJdkDynamicAopProxy() {
		Class<?> jdkDynamicAopProxyClass = null;

		try {
			jdkDynamicAopProxyClass = Class.forName(
				"org.springframework.aop.framework.JdkDynamicAopProxy");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Field advisedField = null;

		try {
			advisedField = jdkDynamicAopProxyClass.getDeclaredField("advised");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Class<?> advisedSupportClass = advisedField.getType();

		if (!advisedSupportClass.equals(AdvisedSupport.class)) {
			fail(
				advisedSupportClass.getClass().getName() + " is not " +
					AdvisedSupport.class.getName());
		}
	}

}