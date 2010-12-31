/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * @author Brian Wing Shun Chan
 */
public class SetDisplayOrderTest extends BaseTestCase {
	public void testSetDisplayOrder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Manage Pages Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Manage Pages Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
					selenium.getText(
						"//nav[@id='navigation']/ul/li[2]/ul/li[1]"));
				assertEquals(RuntimeVariables.replace("Child2 Test2 Page2"),
					selenium.getText(
						"//nav[@id='navigation']/ul/li[2]/ul/li[2]"));
				assertEquals(RuntimeVariables.replace("Child3 Test3 Page3"),
					selenium.getText(
						"//nav[@id='navigation']/ul/li[2]/ul/li[3]"));
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));
				selenium.clickAt("//div/div[3]/div/ul/li[1]/a",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Liferay")
												.equals(selenium.getText(
										"//div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//li/div/div[1]", RuntimeVariables.replace(""));

			case 2:

				boolean childPagePresent = selenium.isElementPresent(
						"//li[2]/ul/li[1]/div/div[3]/a");

				if (childPagePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace(""));

			case 3:
				selenium.clickAt("link=Children", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Display Order",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Child1 Test1 Page1 Child2 Test2 Page2 Child3 Test3 Page3")
												.equals(selenium.getText(
										"_88_layoutIdsBox"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("_88_layoutIdsBox",
					RuntimeVariables.replace("label=Child2 Test2 Page2"));
				selenium.clickAt("//td[2]/a[1]/img",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Child2 Test2 Page2 Child1 Test1 Page1 Child3 Test3 Page3")
												.equals(selenium.getText(
										"_88_layoutIdsBox"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Child2 Test2 Page2 Child1 Test1 Page1 Child3 Test3 Page3"),
					selenium.getText("_88_layoutIdsBox"));
				selenium.clickAt("//input[@value='Update Display Order']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace("Child2 Test2 Page2"),
					selenium.getText("//nav/ul/li[2]/ul/li[1]"));
				assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
					selenium.getText("//nav/ul/li[2]/ul/li[2]"));
				assertEquals(RuntimeVariables.replace("Child3 Test3 Page3"),
					selenium.getText("//nav/ul/li[2]/ul/li[3]"));
				assertEquals(RuntimeVariables.replace("Child2 Test2 Page2"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
					selenium.getText("//li[2]/ul/li[2]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child3 Test3 Page3"),
					selenium.getText("//li[2]/ul/li[3]/div/div[3]/a"));

			case 100:
				label = -1;
			}
		}
	}
}