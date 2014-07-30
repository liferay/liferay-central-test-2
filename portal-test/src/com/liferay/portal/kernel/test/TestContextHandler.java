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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Miguel Pastor
 */
public class TestContextHandler {

	public TestContextHandler(Class<?> clazz) {
		_testContext = new TestContext(clazz);

		registerExecutionListeners(getExecutionTestListeners(clazz));
	}

	public void registerExecutionListeners(
		ExecutionTestListener ... executionTestListeners) {

		for (ExecutionTestListener executionTestListener :
				executionTestListeners) {

			_executionTestListeners.add(executionTestListener);
		}
	}

	public void runAfterTestClasses() {
		for (ExecutionTestListener executionTestListener :
				_executionTestListeners) {

			executionTestListener.runAfterClass(_testContext);
		}
	}

	public void runAfterTestMethod(Object instance, Method method) {
		for (ExecutionTestListener executionTestListener :
				_executionTestListeners) {

			executionTestListener.runAfterTest(_testContext);
		}
	}

	public void runBeforeTestClasses() {
		for (ExecutionTestListener executionTestListener :
				_executionTestListeners) {

			executionTestListener.runBeforeClass(_testContext);
		}
	}

	public void runBeforeTestMethod(Object instance, Method method) {
		_testContext.setInstance(instance);
		_testContext.setMethod(method);

		for (ExecutionTestListener executionTestListener :
				_executionTestListeners) {

			executionTestListener.runBeforeTest(_testContext);
		}
	}

	protected ExecutionTestListener[] getExecutionTestListeners(
		Class<?> clazz) {

		Class<?> declaringClass = ReflectionUtil.getAnnotationDeclaringClass(
			ExecutionTestListeners.class, clazz);

		Set<Class<? extends ExecutionTestListener>>
			executionTestListenerClasses =
				new LinkedHashSet<Class<? extends ExecutionTestListener>>();

		while (declaringClass != null) {
			ExecutionTestListeners executionTestListeners =
				declaringClass.getAnnotation(ExecutionTestListeners.class);

			executionTestListenerClasses.addAll(
				ListUtil.toList(executionTestListeners.listeners()));

			declaringClass = ReflectionUtil.getAnnotationDeclaringClass(
				ExecutionTestListeners.class, declaringClass.getSuperclass());
		}

		int i = 0;

		ExecutionTestListener[] executionTestListeners =
			new ExecutionTestListener[executionTestListenerClasses.size()];

		for (Class<? extends ExecutionTestListener> executionTestListenerClass :
				executionTestListenerClasses) {

			try {
				executionTestListeners[i++] =
					executionTestListenerClass.newInstance();
			}
			catch (Exception e) {
				_log.error(
					"Unable to instantiate " + executionTestListenerClass, e);
			}
		}

		return executionTestListeners;
	}

	private static Log _log = LogFactoryUtil.getLog(TestContextHandler.class);

	private List<ExecutionTestListener> _executionTestListeners =
		new ArrayList<ExecutionTestListener>();
	private TestContext _testContext;

}