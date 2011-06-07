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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.canceladdwdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
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
						if (selenium.isElementPresent("link=Wiki Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Wiki Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//img[@alt='Manage Wikis']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean wikiNode1Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!wikiNode1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 2:

				boolean wikiNode2Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!wikiNode2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean wikiNode3Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!wikiNode3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean wikiNode4Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!wikiNode4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean wikiNode5Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!wikiNode5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 6:
				selenium.open("/web/guest/home/");

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

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//li[@class='first manage-page']/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

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

				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[@class='lfr-tree-controls-label']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//a[@class='lfr-tree-controls-label']",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean page1Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page1Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 7:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean page2Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page2Present) {
					label = 8;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 8:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean page3Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page3Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 9:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean page4Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page4Present) {
					label = 10;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 10:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean page5Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page5Present) {
					label = 11;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 11:
			case 100:
				label = -1;
			}
		}
	}
}