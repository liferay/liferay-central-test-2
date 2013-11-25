/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.io.File;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringEscapeUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class Logger {

	public Logger(LiferaySelenium liferaySelenium) {
		_liferaySelenium = liferaySelenium;

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1000, 50));
		window.setSize(new Dimension(650, 850));

		_javascriptExecutor = (JavascriptExecutor)_webDriver;

		_javascriptExecutor.executeScript("window.name = 'Log Window';");
	}

	public void logActionCommand(Object[] arguments) {
		StringBundler sb = new StringBundler();

		String command = (String)arguments[0];
		String[] params = (String[])arguments[1];

		sb.append("Running <b>");
		sb.append(command);
		sb.append("</b>");

		int paramsLength = params.length / 3;

		for (int i = 0; i < paramsLength; i++) {
			String locator = params[i];

			if (Validator.isNotNull(locator)) {
				sb.append(" with locator <b>");
				sb.append(locator);
				sb.append("</b>");
			}

			String locatorKey = params[i + 1];

			if (Validator.isNotNull(locatorKey)) {
				sb.append(" with locator-key <b>");
				sb.append(locatorKey);
				sb.append("</b>");
			}

			String value = params[i + 2];

			if (Validator.isNotNull(value)) {
				sb.append(" value <b>");
				sb.append(value);
				sb.append("</b>");
			}
		}

		log("actionCommandLog", sb.toString());
	}

	public void logError(
		Method method, Object[] arguments, Throwable throwable) {

		send("", "fail");

		_errorCount++;

		String thowableMessage = throwable.getMessage();

		StringBundler sb = new StringBundler();

		sb.append("errorCount = window.document.getElementById('errorCount');");
		sb.append("errorCount.innerHTML = '");
		sb.append(_errorCount);
		sb.append("';");
		sb.append("errorList = window.document.getElementById('errorList');");
		sb.append("var newLine = window.document.createElement('div');");
		sb.append("newLine.innerHTML = '");

		String stackTrace = generateStackTrace(throwable);

		stackTrace = StringEscapeUtils.escapeEcmaScript(stackTrace);

		sb.append(stackTrace);
		sb.append("';");
		sb.append("errorList.appendChild(newLine);");

		_javascriptExecutor.executeScript(sb.toString());

		sb = new StringBundler();

		sb.append("<font color=\"red\">");
		sb.append("Command failure <b>");
		sb.append(method.getName());
		sb.append("</b>");

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append("<b>");
				sb.append(String.valueOf(argument));
				sb.append("</b> ");
			}
		}

		sb.append(": ");
		sb.append(thowableMessage);

		log("seleniumCommandLog", sb.toString());

		sb = new StringBundler();

		sb.append("Command failure \"");
		sb.append(method.getName());
		sb.append("\"");

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append("\"");
				sb.append(String.valueOf(argument));
				sb.append("\" ");
			}
		}

		sb.append(": ");
		sb.append(thowableMessage);

		BaseTestCase.fail(sb.toString());
	}

	public void logSeleniumCommand(Method method, Object[] arguments) {
		StringBundler sb = new StringBundler();

		sb.append("Running <b>");
		sb.append(method.getName());
		sb.append("</b>");

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append("<b>");
				sb.append(String.valueOf(argument));
				sb.append("</b> ");
			}
		}

		log("seleniumCommandLog", sb.toString());
	}

	public void send(Object[] arguments) {
		String id = (String)arguments[0];
		String status = (String)arguments[1];

		send(id, status);
	}

	public void send(String id, String status) {
		if (status.equals("pending")) {
			_xpathIdStack.push(id);
		}
		else if (status.equals("start")) {
			_xpathIdStack = new Stack<String>();

			return;
		}

		String xpath = generateXpath(_xpathIdStack);

		List<WebElement> webElements = _webDriver.findElements(By.xpath(xpath));

		if (status.equals("pass")) {
			_xpathIdStack.pop();
		}

		StringBundler sb = new StringBundler();

		sb.append("var element = arguments[0];");
		sb.append("element.className = \"");
		sb.append(status);
		sb.append("\";");

		for (WebElement webElement : webElements) {
			_javascriptExecutor.executeScript(sb.toString(), webElement);
		}
	}

	public void start() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		String htmlFileName =
			_TEST_BASEDIR + "/test/functional-generated/" +
				StringUtil.replace(primaryTestSuiteName, ".", "/") + ".html";

		if (_loggerStarted) {
			return;
		}

		if (FileUtil.exists(htmlFileName)) {
			_webDriver.get("file:///" + htmlFileName);
		}
		else {
			_webDriver.get(
				"file:///" + _TEST_BASEDIR + "/test/functional/com/liferay/" +
					"portalweb/portal/util/liferayselenium/dependencies/" +
					"Logger.html");
		}

		_loggerStarted = true;
	}

	public void stop() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		if (primaryTestSuiteName != null) {
			String content = (String)_javascriptExecutor.executeScript(
				"return window.document.getElementsByTagName('html')[0]." +
					"outerHTML;");

			String fileName =
				_TEST_BASEDIR + "/test-results/functional/report.html";

			File file = new File(fileName);

			try {
				FileUtil.write(file, content);
			}
			catch (Exception e) {
			}
		}

		_webDriver.quit();
	}

	protected String generateStackTrace(Throwable throwable) {
		Stack<String> ids = (Stack<String>)_xpathIdStack.clone();

		String currentCommand = null;
		String currentLine = null;
		String parentCommand = null;
		String parentLine = null;
		String testCaseCommand = null;

		StringBundler sb = new StringBundler();

		sb.append("<p>");

		while (ids.size() > 1) {
			String xpath = generateXpath(ids);

			String command = getLogElementText(
				xpath + "/div/span[@class='quote'][1]");

			command = StringUtil.replace(command, "\"", "");

			String line = getLogElementText(
				xpath + "/div/div[@class='line-number']");

			if ((parentCommand == null) || (parentLine == null)) {
				parentCommand = command;
				parentLine = line;
			}
			else {
				currentCommand = parentCommand;
				currentLine = parentLine;

				parentCommand = command;
				parentLine = line;

				String parentFileName = "";

				if (ids.size() > 2) {
					int x = parentCommand.indexOf("#");

					parentFileName = parentCommand.substring(0, x) + ".macro";
				}
				else {
					ids.pop();

					String testCaseXpath = generateXpath(ids);

					testCaseCommand = getLogElementText(
						testCaseXpath + "/div/h3");

					int x = testCaseCommand.lastIndexOf("#");

					parentFileName =
						testCaseCommand.substring(0, x) + ".testcase";
				}

				sb.append("Failed Line: <b>");
				sb.append(currentCommand);
				sb.append("</b> (");
				sb.append(parentFileName);
				sb.append(":");
				sb.append(currentLine);
				sb.append(")<br />");
			}

			ids.pop();
		}

		sb.append("in test case command <b>");
		sb.append(testCaseCommand.trim());
		sb.append("</b><br />");
		sb.append("<textarea cols=\"85\" rows=\"7\" wrap=\"off\">");
		sb.append(throwable.getMessage());

		StackTraceElement[] stackTraceElements = throwable.getStackTrace();

		for (StackTraceElement stackTraceElement : stackTraceElements) {
			String className = stackTraceElement.getClassName();

			if (className.startsWith("com.liferay")) {
				sb.append("\\n\\t");
				sb.append(stackTraceElement.toString());
			}
		}

		sb.append("</textarea>");
		sb.append("</p>");

		return sb.toString();
	}

	protected String generateXpath(Stack<String> ids) {
		StringBundler sb = new StringBundler();

		sb.append("/");

		for (String id : ids) {
			sb.append("/ul/li[@id='");
			sb.append(id);
			sb.append("']");
		}

		sb.append("/div");

		return sb.toString();
	}

	protected String getLogElementText(String xpath) {
		try {
			WebElement webElement = _webDriver.findElement(By.xpath(xpath));

			return (String)_javascriptExecutor.executeScript(
				"var element = arguments[0]; return element.innerHTML;",
				webElement);
		}
		catch (Exception e) {
			return null;
		}
	}

	protected void log(String log, String message) {
		StringBundler sb = new StringBundler();

		sb.append("logger = window.document.getElementById('");
		sb.append(log);
		sb.append("');");
		sb.append("var newLine = window.document.createElement('div');");
		sb.append("newLine.setAttribute('class', 'line');");
		sb.append("newLine.innerHTML = '");
		sb.append(StringEscapeUtils.escapeEcmaScript(message));
		sb.append("';");
		sb.append("logger.appendChild(newLine);");
		sb.append("logger.scrollTop = logger.scrollHeight;");

		_javascriptExecutor.executeScript(sb.toString());
	}

	private static final String _TEST_BASEDIR = TestPropsValues.TEST_BASEDIR;

	private int _errorCount;
	private JavascriptExecutor _javascriptExecutor;
	private LiferaySelenium _liferaySelenium;
	private boolean _loggerStarted;
	private WebDriver _webDriver = new FirefoxDriver();
	private Stack<String> _xpathIdStack = new Stack<String>();

}