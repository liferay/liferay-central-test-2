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

import com.liferay.poshi.runner.logger.LoggerUtil;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 */
@RunWith(Parameterized.class)
public class PoshiRunner {

	@Parameters(name = "{0}")
	public static List<String> getList() throws PoshiRunnerException {
		List<String> classCommandNames = new ArrayList<>();

		String testName = PropsValues.TEST_NAME;

		if (testName.contains("#")) {
			classCommandNames.add(testName);
		}
		else {
			String className = testName;

			Element rootElement = PoshiRunnerContext.getTestcaseRootElement(
				className);

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				classCommandNames.add(
					className + "#" + commandElement.attributeValue("name"));
			}
		}

		return classCommandNames;
	}

	public PoshiRunner(String classCommandName) throws PoshiRunnerException {
		LoggerUtil.startLogger();

		SeleniumUtil.startSelenium();

		System.out.println("\nRunning " + classCommandName);

		_testClassCommandName = classCommandName;
		_testClassName = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
			_testClassCommandName);
	}

	@Test
	public void test() throws Exception {
		try {
			_runSetUp();

			_runCommand();
		}
		finally {
			_runTearDown();
		}
	}

	private void _runClassCommandName(String classCommandName)
		throws PoshiRunnerException {

		Element rootElement = PoshiRunnerContext.getTestcaseRootElement(
			_testClassName);

		List<Element> varElements = rootElement.elements("var");

		for (Element varElement : varElements) {
			String name = varElement.attributeValue("name");
			String value = varElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element commandElement = PoshiRunnerContext.getTestcaseCommandElement(
			classCommandName);

		if (commandElement != null) {
			PoshiRunnerStackTraceUtil.pushFilePath(
				classCommandName, "testcase");

			PoshiRunnerExecutor.parseElement(commandElement);

			PoshiRunnerStackTraceUtil.popFilePath();
		}
	}

	private void _runCommand() throws PoshiRunnerException {
		_runClassCommandName(_testClassCommandName);
	}

	private void _runSetUp() throws PoshiRunnerException {
		_runClassCommandName(_testClassName + "#set-up");
	}

	private void _runTearDown() throws PoshiRunnerException {
		try {
			_runClassCommandName(_testClassName + "#tear-down");
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			LoggerUtil.stopLogger();

			SeleniumUtil.stopSelenium();
		}
	}

	private final String _testClassCommandName;
	private final String _testClassName;

}