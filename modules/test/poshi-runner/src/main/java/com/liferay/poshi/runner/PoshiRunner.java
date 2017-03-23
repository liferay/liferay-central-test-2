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

import com.liferay.poshi.runner.logger.CommandLoggerHandler;
import com.liferay.poshi.runner.logger.LoggerUtil;
import com.liferay.poshi.runner.logger.SummaryLoggerHandler;
import com.liferay.poshi.runner.logger.XMLLoggerHandler;
import com.liferay.poshi.runner.selenium.LiferaySeleniumHelper;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 * @author Leslie Wong
 */
@RunWith(Parameterized.class)
public class PoshiRunner {

	@Parameters(name = "{0}")
	public static List<String> getList() throws Exception {
		PoshiRunnerContext.readFiles();

		List<String> classCommandNames = new ArrayList<>();

		List<String> testNames = Arrays.asList(
			PropsValues.TEST_NAME.split("\\s*,\\s*"));

		for (String testName : testNames) {
			PoshiRunnerValidation.validate(testName);

			if (testName.contains("#")) {
				classCommandNames.add(testName);
			}
			else {
				String className = testName;

				Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
					className);

				List<Element> commandElements = rootElement.elements("command");

				for (Element commandElement : commandElements) {
					classCommandNames.add(
						className + "#" +
							commandElement.attributeValue("name"));
				}
			}
		}

		return classCommandNames;
	}

	public PoshiRunner(String classCommandName) throws Exception {
		_testClassCommandName = classCommandName;

		_testClassName = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
			_testClassCommandName);
	}

	@Before
	public void setUp() throws Exception {
		System.out.println();
		System.out.println("###");
		System.out.println("### " + _testClassCommandName);
		System.out.println("###");
		System.out.println();

		PoshiRunnerContext.setTestCaseCommandName(_testClassCommandName);
		PoshiRunnerContext.setTestCaseName(_testClassName);

		PoshiRunnerVariablesUtil.clear();

		try {
			XMLLoggerHandler.generateXMLLog(_testClassCommandName);

			LoggerUtil.startLogger();

			SeleniumUtil.startSelenium();

			_runSetUp();
		}
		catch (WebDriverException wde) {
			wde.printStackTrace();

			throw wde;
		}
		catch (Exception e) {
			LiferaySeleniumHelper.printJavaProcessStacktrace();

			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			e.printStackTrace();

			if (PropsValues.TEST_PAUSE_ON_FAILURE) {
				LoggerUtil.pauseFailedTest();
			}

			throw e;
		}
	}

	@After
	public void tearDown() throws Exception {
		LiferaySeleniumHelper.writePoshiWarnings();

		LoggerUtil.createSummary();

		try {
			if (!PropsValues.TEST_SKIP_TEAR_DOWN) {
				_runTearDown();
			}
		}
		catch (Exception e) {
			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			if (PropsValues.TEST_PAUSE_ON_FAILURE) {
				LoggerUtil.pauseFailedTest();
			}
		}
		finally {
			LoggerUtil.stopLogger();

			SeleniumUtil.stopSelenium();
		}
	}

	@Test
	public void test() throws Exception {
		try {
			_runCommand();

			LiferaySeleniumHelper.assertNoPoshiWarnings();
		}
		catch (Exception e) {
			LiferaySeleniumHelper.printJavaProcessStacktrace();

			PoshiRunnerStackTraceUtil.printStackTrace(e.getMessage());

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			e.printStackTrace();

			if (PropsValues.TEST_PAUSE_ON_FAILURE) {
				LoggerUtil.pauseFailedTest();
			}

			throw e;
		}
	}

	@Rule
	public Retry retry = new Retry(
		3, TimeoutException.class, UnreachableBrowserException.class);

	private void _runClassCommandName(String classCommandName)
		throws Exception {

		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			_testClassName);

		List<Element> varElements = rootElement.elements("var");

		for (Element varElement : varElements) {
			PoshiRunnerExecutor.runVarElement(varElement, false, false);
		}

		PoshiRunnerVariablesUtil.pushCommandMap(true);

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			classCommandName);

		if (commandElement != null) {
			PoshiRunnerStackTraceUtil.startStackTrace(
				classCommandName, "test-case");

			XMLLoggerHandler.updateStatus(commandElement, "pending");

			PoshiRunnerExecutor.parseElement(commandElement);

			XMLLoggerHandler.updateStatus(commandElement, "pass");

			PoshiRunnerStackTraceUtil.emptyStackTrace();
		}
	}

	private void _runCommand() throws Exception {
		CommandLoggerHandler.logClassCommandName(_testClassCommandName);

		_runClassCommandName(_testClassCommandName);
	}

	private void _runSetUp() throws Exception {
		CommandLoggerHandler.logClassCommandName(_testClassName + "#set-up");

		SummaryLoggerHandler.startMajorSteps();

		_runClassCommandName(_testClassName + "#set-up");
	}

	private void _runTearDown() throws Exception {
		CommandLoggerHandler.logClassCommandName(_testClassName + "#tear-down");

		SummaryLoggerHandler.startMajorSteps();

		_runClassCommandName(_testClassName + "#tear-down");
	}

	private final String _testClassCommandName;
	private final String _testClassName;

	private class Retry implements TestRule {

		public Retry(int retryCount, Class... retryClasses) {
			_retryCount = retryCount;
			_retryClasses = retryClasses;
		}

		public Statement apply(
			final Statement statement, final Description description) {

			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					for (int i = 0; i < _retryCount; i++) {
						try {
							statement.evaluate();

							return;
						}
						catch (Throwable t) {
							if (i == (_retryCount - 1)) {
								throw t;
							}

							boolean retry = false;

							List<Throwable> throwables = null;

							if (t instanceof MultipleFailureException) {
								MultipleFailureException mfe =
									(MultipleFailureException)t;

								throwables = mfe.getFailures();
							}
							else {
								throwables = new ArrayList<>(1);

								throwables.add(t);
							}

							for (Class retryClass : _retryClasses) {
								for (Throwable throwable : throwables) {
									if (retryClass.isInstance(throwable)) {
										retry = true;
									}
								}
							}

							if (retry == false) {
								throw t;
							}
						}
					}
				}

			};
		}

		private final Class[] _retryClasses;
		private final int _retryCount;

	}

}