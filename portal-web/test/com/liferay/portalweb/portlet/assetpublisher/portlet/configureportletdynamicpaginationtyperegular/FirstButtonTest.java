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

package com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicpaginationtyperegular;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="FirstButtonTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FirstButtonTest extends BaseTestCase {
	public void testFirstButton() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Asset Publisher Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//div[2]/select", RuntimeVariables.replace("3"));
		selenium.waitForPageToLoad("30000");
		assertEquals("3", selenium.getSelectedLabel("//div[2]/select"));
		selenium.clickAt("link=First", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=First"));
		assertFalse(selenium.isElementPresent("link=Previous"));
		assertTrue(selenium.isElementPresent("link=Next"));
		assertTrue(selenium.isElementPresent("link=Last"));
		assertEquals("1", selenium.getSelectedLabel("//div[2]/select"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Asset Publisher Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//div[2]/select", RuntimeVariables.replace("2"));
		selenium.waitForPageToLoad("30000");
		assertEquals("2", selenium.getSelectedLabel("//div[2]/select"));
		selenium.clickAt("link=First", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=First"));
		assertFalse(selenium.isElementPresent("link=Previous"));
		assertTrue(selenium.isElementPresent("link=Next"));
		assertTrue(selenium.isElementPresent("link=Last"));
		assertEquals("1", selenium.getSelectedLabel("//div[2]/select"));
	}
}