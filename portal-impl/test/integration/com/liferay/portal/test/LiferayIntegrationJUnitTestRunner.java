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

package com.liferay.portal.test;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.log.CaptureAppender;
import com.liferay.portal.test.log.ConcurrentAssertUtil;
import com.liferay.portal.test.log.ExpectedLogsUtil;
import com.liferay.portal.test.log.LogAssertionUtil;
import com.liferay.portal.util.test.TestPropsValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.List;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra
 * @author Shuyang Zhou
 */
public class LiferayIntegrationJUnitTestRunner
	extends CustomizableSpringContextJUnitTestRunner {

	public LiferayIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void afterApplicationContextInit() {
	}

	@Override
	public List<String> getExtraConfigLocations() {
		return Collections.emptyList();
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		final Statement statement = super.classBlock(notifier);

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Thread currentThread = Thread.currentThread();

				Object inheritableThreadLocals =
					_inheritableThreadLocalsField.get(currentThread);

				if (inheritableThreadLocals != null) {
					_inheritableThreadLocalsField.set(
						currentThread,
						_createInheritedMapMethod.invoke(
							null, inheritableThreadLocals));
				}

				Object threadLocals = _threadLocalsField.get(currentThread);

				_threadLocalsField.set(currentThread, null);

				try {
					statement.evaluate();
				}
				finally {
					_inheritableThreadLocalsField.set(
						currentThread, inheritableThreadLocals);
					_threadLocalsField.set(currentThread, threadLocals);
				}
			}

		};
	}

	@Override
	protected Statement methodBlock(final FrameworkMethod frameworkMethod) {
		final Statement statement = super.methodBlock(frameworkMethod);

		if (!TestPropsValues.ASSERT_LOGS) {
			return statement;
		}

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				ConcurrentAssertUtil.startAssert();

				CaptureAppender captureAppender = ExpectedLogsUtil.startAssert(
					frameworkMethod.getMethod());

				try {
					LogAssertionUtil.enableLogAssertion();

					statement.evaluate();
				}
				finally {
					ExpectedLogsUtil.endAssert(
						frameworkMethod.getMethod(), captureAppender);

					ConcurrentAssertUtil.endAssert();
				}
			}

		};
	}

	private static final Method _createInheritedMapMethod;
	private static final Field _inheritableThreadLocalsField;
	private static final Class<?> _threadLocalMapClass;
	private static final Field _threadLocalsField;

	static {
		try {
			_threadLocalMapClass = Class.forName(
				ThreadLocal.class.getName().concat("$ThreadLocalMap"));

			_createInheritedMapMethod = ReflectionUtil.getDeclaredMethod(
				ThreadLocal.class, "createInheritedMap", _threadLocalMapClass);

			_inheritableThreadLocalsField = ReflectionUtil.getDeclaredField(
				Thread.class, "inheritableThreadLocals");
			_threadLocalsField = ReflectionUtil.getDeclaredField(
				Thread.class, "threadLocals");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}