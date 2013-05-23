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
import com.liferay.portalweb.portal.BaseTestCase;

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
		window.setSize(new Dimension(600, 700));

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript("window.name = 'Log Window';");
	}

	public void logCommand(Method method, Object[] arguments) {
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

		log(sb.toString());
	}

	public void logError(Method method, Object[] arguments) {
		StringBundler sb = new StringBundler();

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

		log(sb.toString());

		sb = new StringBundler();

		sb.append("Command failure ");
		sb.append(method.getName());

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append(String.valueOf(argument));
				sb.append(" ");
			}
		}

		BaseTestCase.fail(sb.toString());
	}

	public void send(Object[] arguments) {
		String id = (String)arguments[0];
		String status = (String)arguments[1];

		if (status.equals("pending")) {
			_xpathIdStack.push(id);
		}
		else if (status.equals("start")) {
			_xpathIdStack = new Stack<String>();

			_xpathIdStack.push(id);

			return;
		}

		StringBundler sb = new StringBundler();

		sb.append("/");

		for (String xpathId : _xpathIdStack) {
			sb.append("/ul/li[@id='");
			sb.append(xpathId);
			sb.append("']");
		}

		sb.append("/div");

		List<WebElement> webElements = _webDriver.findElements(
			By.xpath(sb.toString()));

		if (status.equals("pass")) {
			_xpathIdStack.pop();
		}

		sb = new StringBundler();

		sb.append("var element = arguments[0];");
		sb.append("element.className = \"");
		sb.append(status);
		sb.append("\";");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		for (WebElement webElement : webElements) {
			javascriptExecutor.executeScript(sb.toString(), webElement);
		}
	}

	public void start() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		String htmlFileName =
			"portal-web/test/functional-generated/" +
				StringUtil.replace(primaryTestSuiteName, ".", "/") + ".html";

		if (_loggerStarted) {
			return;
		}

		if (FileUtil.exists(_liferaySelenium.getProjectDir() + htmlFileName)) {
			_webDriver.get(
				"file:///" + _liferaySelenium.getProjectDir() + htmlFileName);
		}
		else {
			_webDriver.get(
				"file:///" + _liferaySelenium.getProjectDir() +
					"portal-web/test/functional/com/liferay/portalweb/portal/" +
						"util/liferayselenium/dependencies/Logger.html");
		}

		_loggerStarted = true;
	}

	public void stop() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		if (primaryTestSuiteName != null) {
			JavascriptExecutor javascriptExecutor =
				(JavascriptExecutor)_webDriver;

			String content = (String)javascriptExecutor.executeScript(
				"return window.document.getElementsByTagName('html')[0]." +
					"outerHTML;");

			String fileName =
				_liferaySelenium.getProjectDir() +
					"portal-web\\test-results\\functional\\TEST-" +
						primaryTestSuiteName + ".html";

			File file = new File(fileName);

			try {
				FileUtil.write(file, content);
			}
			catch (Exception e) {
			}
		}

		_webDriver.quit();
	}

	protected void log(String message) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		StringBundler sb = new StringBundler();

		String formattedMessage = StringEscapeUtils.escapeJava(message);

		formattedMessage = formattedMessage.replace("'", "\\'");

		sb.append("logger = window.document.getElementById('log');");
		sb.append("var newLine = window.document.createElement('div');");
		sb.append("newLine.setAttribute('class', 'line');");
		sb.append("newLine.innerHTML = '");
		sb.append(formattedMessage);
		sb.append("';");
		sb.append("logger.appendChild(newLine);");
		sb.append("logger.scrollTop = logger.scrollHeight;");

		javascriptExecutor.executeScript(sb.toString());
	}

	private LiferaySelenium _liferaySelenium;
	private boolean _loggerStarted;
	private WebDriver _webDriver = new FirefoxDriver();
	private Stack<String> _xpathIdStack = new Stack<String>();

}