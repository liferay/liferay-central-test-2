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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LogAssertionAppender;
import com.liferay.portal.test.rule.LogAssertionHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class LogAssertionTestCallback
	extends BaseTestCallback<CaptureAppender, CaptureAppender> {

	public static final LogAssertionTestCallback INSTANCE =
		new LogAssertionTestCallback();

	public static void caughtFailure(Error error) {
		Thread currentThread = Thread.currentThread();

		if (currentThread != _thread) {
			_concurrentFailures.put(currentThread, error);

			_thread.interrupt();
		}
		else {
			throw error;
		}
	}

	@Override
	public void doAfterClass(
		Description description, CaptureAppender captureAppender) {

		ExpectedLogs expectedLogs = description.getAnnotation(
			ExpectedLogs.class);

		endAssert(expectedLogs, captureAppender);
	}

	@Override
	public void doAfterMethod(
		Description description, CaptureAppender captureAppender,
		Object target) {

		doAfterClass(description, captureAppender);
	}

	@Override
	public CaptureAppender doBeforeClass(Description description) {
		ExpectedLogs expectedLogs = description.getAnnotation(
			ExpectedLogs.class);

		return startAssert(expectedLogs);
	}

	@Override
	public CaptureAppender doBeforeMethod(
		Description description, Object target) {

		return doBeforeClass(description);
	}

	protected static void endAssert(
		ExpectedLogs expectedLogs, CaptureAppender captureAppender) {

		if (expectedLogs != null) {
			try {
				for (LoggingEvent loggingEvent :
						captureAppender.getLoggingEvents()) {

					String renderedMessage = loggingEvent.getRenderedMessage();

					if (!isExpected(expectedLogs, renderedMessage)) {
						Assert.fail(renderedMessage);
					}
				}
			}
			finally {
				captureAppender.close();
			}
		}

		_thread = null;

		try {
			for (Map.Entry<Thread, Error> entry :
					_concurrentFailures.entrySet()) {

				Thread thread = entry.getKey();
				Error error = entry.getValue();

				Assert.fail(
					"Thread " + thread + " caught concurrent failure: " +
						error);

				throw error;
			}
		}
		finally {
			_concurrentFailures.clear();
		}
	}

	protected static void installJdk14Handler() {
		Logger logger = Logger.getLogger(StringPool.BLANK);

		logger.removeHandler(LogAssertionHandler.INSTANCE);

		logger.addHandler(LogAssertionHandler.INSTANCE);
	}

	protected static void installLog4jAppender() {
		org.apache.log4j.Logger logger =
			org.apache.log4j.Logger.getRootLogger();

		logger.removeAppender(LogAssertionAppender.INSTANCE);

		logger.addAppender(LogAssertionAppender.INSTANCE);
	}

	protected static boolean isExpected(
		ExpectedLogs expectedLogs, String renderedMessage) {

		for (ExpectedLog expectedLog : expectedLogs.expectedLogs()) {
			ExpectedType expectedType = expectedLog.expectedType();

			if (expectedType == ExpectedType.EXACT) {
				if (renderedMessage.equals(expectedLog.expectedLog())) {
					return true;
				}
			}
			else if (expectedType == ExpectedType.POSTFIX) {
				if (renderedMessage.endsWith(expectedLog.expectedLog())) {
					return true;
				}
			}
			else if (expectedType == ExpectedType.PREFIX) {
				if (renderedMessage.startsWith(expectedLog.expectedLog())) {
					return true;
				}
			}
		}

		return false;
	}

	protected static CaptureAppender startAssert(ExpectedLogs expectedLogs) {
		_thread = Thread.currentThread();

		CaptureAppender captureAppender = null;

		if (expectedLogs != null) {
			Class<?> clazz = expectedLogs.loggerClass();

			captureAppender = Log4JLoggerTestUtil.configureLog4JLogger(
				clazz.getName(), Level.toLevel(expectedLogs.level()));
		}

		installJdk14Handler();
		installLog4jAppender();

		return captureAppender;
	}

	private LogAssertionTestCallback() {
	}

	private static final Map<Thread, Error> _concurrentFailures =
		new ConcurrentHashMap<>();
	private static volatile Thread _thread;

}