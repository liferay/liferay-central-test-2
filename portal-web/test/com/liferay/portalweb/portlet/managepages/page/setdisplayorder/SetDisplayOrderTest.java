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

package com.liferay.portalweb.portlet.managepages.page.setdisplayorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SetDisplayOrderTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SetDisplayOrderTest extends BaseTestCase {
	public void testSetDisplayOrder() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Manage Pages Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Manage Pages Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
			selenium.getText("//div[2]/ul/li[2]/ul/li[1]/a"));
		assertEquals(RuntimeVariables.replace("Child2 Test2 Page2"),
			selenium.getText("//div[2]/ul/li[2]/ul/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Child3 Test3 Page3"),
			selenium.getText("//div[2]/ul/li[2]/ul/li[3]/a"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//li[@class='first manage-page']/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//li[@class='first manage-page']/a"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Guest")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Children", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//ul[@class='aui-tree-container']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Display Order", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//ul[@class='aui-tree-container']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_88_layoutIdsBox",
			RuntimeVariables.replace("label=Child2 Test2 Page2"));
		selenium.clickAt("//td[2]/a[1]/img", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Update Display Order']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals(RuntimeVariables.replace("Child2 Test2 Page2"),
			selenium.getText("//div[2]/ul/li[2]/ul/li[1]/a"));
		assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
			selenium.getText("//div[2]/ul/li[2]/ul/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Child3 Test3 Page3"),
			selenium.getText("//div[2]/ul/li[2]/ul/li[3]/a"));
	}
}