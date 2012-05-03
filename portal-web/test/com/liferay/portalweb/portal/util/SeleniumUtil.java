/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portalweb.portal.util.TestPropsValues;
import com.liferay.portalweb.portal.util.liferayselenium.ChromeWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.DefaultSeleniumImpl;
import com.liferay.portalweb.portal.util.liferayselenium.FirefoxWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.InternetExplorerWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import com.thoughtworks.selenium.Selenium;

import java.io.File;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumUtil extends TestPropsValues {

	public static LiferaySelenium getSelenium() {
		return _instance._getSelenium();
	}

	public static String getTimestamp() {
		return _instance._getTimestamp();
	}

	public static void startSelenium() {
		_instance._startSelenium();
	}

	public static void stopSelenium() {
		_instance._stopSelenium();
	}

	private SeleniumUtil() {
		_timestamp = Time.getTimestamp();
	}

	private LiferaySelenium _getSelenium() {
		if (_selenium == null) {
			_startSelenium();
		}

		return _selenium;
	}

	private String _getTimestamp() {
		return _timestamp;
	}

	private void _startSelenium() {
		File file = new File(StringPool.PERIOD);

		String absolutePath = file.getAbsolutePath();

		String projectDir = absolutePath.substring(
			0, absolutePath.length() - 1);

		if (SELENIUM_IMPLEMENTATION.equals(Selenium.class.getName())) {
			_selenium = new DefaultSeleniumImpl(projectDir, PORTAL_URL);

			Class<?> clazz = getClass();

			_selenium.setContext(clazz.getName());
		}
		else if (SELENIUM_IMPLEMENTATION.equals(WebDriver.class.getName())) {
			if (BROWSER_TYPE.contains("chrome")) {
				_selenium = new ChromeWebDriverImpl(projectDir, PORTAL_URL);
			}
			else if (BROWSER_TYPE.contains("firefox")) {
				_selenium = new FirefoxWebDriverImpl(projectDir, PORTAL_URL);
			}
			else if (BROWSER_TYPE.contains("iexplore")) {
				_selenium = new InternetExplorerWebDriverImpl(
					projectDir, PORTAL_URL);
			}
			else {
				throw new RuntimeException(
					"Invalid browser type " + BROWSER_TYPE);
			}
		}
	}

	private void _stopSelenium() {
		if (_selenium != null) {
			_selenium.stop();
		}

		_selenium = null;
	}

	private static SeleniumUtil _instance = new SeleniumUtil();

	private LiferaySelenium _selenium;
	private String _timestamp;

}