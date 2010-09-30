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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayDefaultSelenium
	extends DefaultSelenium implements LiferaySelenium {

	public LiferayDefaultSelenium(CommandProcessor processor) {
		super(processor);
	}

	public LiferayDefaultSelenium(
		String serverHost, int serverPort, String browserStartCommand,
		String browserURL) {

		super(serverHost, serverPort, browserStartCommand, browserURL);
	}

	public String getCurrentDay() {
		return commandProcessor.getString(
			"getCurrentDay", new String[0]);
	}

	public String getCurrentMonth() {
		return commandProcessor.getString(
			"getCurrentMonth", new String[0]);
	}

	public String getCurrentYear() {
		return commandProcessor.getString(
			"getCurrentYear", new String[0]);
	}

	public String getIncrementedText(String locator) {
		return commandProcessor.getString(
			"getIncrementedText", new String[] {locator,});
	}

	public boolean isPartialText(String locator, String value) {
		return commandProcessor.getBoolean(
			"isPartialText", new String[] {locator, value,});
	}

	public void saveScreenShot() {
		if (!TestPropsValues.SAVE_SCREENSHOT) {
			return;
		}

		windowMaximize();

		FileUtil.mkdirs(_OUTPUT_SCREENSHOTS_DIR);

		String screenShotName = getScreenshotFileName();

		captureEntirePageScreenshot(
			_OUTPUT_SCREENSHOTS_DIR + screenShotName + ".jpg", "");
	}

	public void saveSource() throws Exception {
		if (!TestPropsValues.SAVE_SOURCE) {
			return;
		}

		String content = HttpUtil.URLtoString(getLocation());

		FileUtil.mkdirs(_OUTPUT_SCREENSHOTS_DIR);

		String screenShotName = getScreenshotFileName();

		FileUtil.write(
			_OUTPUT_SCREENSHOTS_DIR + screenShotName + ".html", content);
	}

	protected String getScreenshotFileName() {
		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		for (int i = 1; i < stackTraceElements.length; i++) {
			StackTraceElement stackTraceElement = stackTraceElements[i];

			String className = stackTraceElement.getClassName();

			if ((className.startsWith("com.liferay.portalweb.plugins") ||
				 className.startsWith("com.liferay.portalweb.portal") ||
				 className.startsWith("com.liferay.portalweb.portlet") ||
				 className.startsWith("com.liferay.portalweb.properties")) &&
				 !className.startsWith("com.liferay.portalweb.portal.util") &&
				 className.endsWith("Test")) {

				String fileName = stackTraceElement.getFileName();
				int lineNumber = stackTraceElement.getLineNumber();

				return fileName + "-" + lineNumber;
			}
		}

		throw new RuntimeException("Unable to find screenshot file name");
	}

	private static final String _OUTPUT_SCREENSHOTS_DIR =
		TestPropsValues.OUTPUT_DIR + "screenshots/";

}