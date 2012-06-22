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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPublicPageArenasChildPageMaracanaSiteBWCTest
	extends BaseTestCase {
	public void testAddPublicPageArenasChildPageMaracanaSiteBWC()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("World Cup"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("World Cup - Brazil 2014"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a",
					RuntimeVariables.replace("World Cup - Brazil 2014"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Site Pages",
					RuntimeVariables.replace("Site Pages"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Public Pages",
					RuntimeVariables.replace("Public Pages"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Public Pages")
												.equals(selenium.getText(
										"//a[@class='layout-tree']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//a[@class='layout-tree']"));

				boolean home1Present = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (home1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='aui-tree-hitarea']",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Home")
												.equals(selenium.getText(
										"//li/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Arenas"),
					selenium.getText("//li/ul/li[2]/div/div[3]/a"));
				selenium.clickAt("//li/ul/li[2]/div/div[3]/a",
					RuntimeVariables.replace("Arenas"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Arenas")
												.equals(selenium.getValue(
										"//div[1]/fieldset/div/span[1]/span/span/span/input"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals("Arenas",
					selenium.getValue(
						"//div[1]/fieldset/div/span[1]/span/span/span/input"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div/span/button[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Add Child Page"),
					selenium.getText("//div/span/button[1]"));
				selenium.clickAt("//div/span/button[1]",
					RuntimeVariables.replace("Add Child Page"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_156_addLayoutName_en_US']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_156_addLayoutName_en_US']",
					RuntimeVariables.replace("Maracana"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean home2Present = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (home2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@class='aui-tree-hitarea']",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Home")
												.equals(selenium.getText(
										"//li/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Arenas"),
					selenium.getText("//li/ul/li[2]/div/div[3]/a"));

				boolean childPagePresent = selenium.isElementPresent(
						"//li[2]/ul/li[1]/div/div[3]/a");

				if (childPagePresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 4:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Arena Pernambuco")
												.equals(selenium.getText(
										"//li[2]/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Arena Pernambuco"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Arena da Baixada"),
					selenium.getText("//li[2]/ul/li[2]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Maracana"),
					selenium.getText("//li[2]/ul/li[3]/div/div[3]/a"));

			case 100:
				label = -1;
			}
		}
	}
}