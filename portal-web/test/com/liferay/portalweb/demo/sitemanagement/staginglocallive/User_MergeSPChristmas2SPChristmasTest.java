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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_MergeSPChristmas2SPChristmasTest extends BaseTestCase {
	public void testUser_MergeSPChristmas2SPChristmas()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Site Name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace(
				"Christmas Site Pages Variation of Site Name"),
			selenium.getText("//span[@class='layout-set-branch-description']"));
		assertEquals(RuntimeVariables.replace("Manage Site Pages Variations"),
			selenium.getText("//a[@id='_170_manageLayoutSetBranches']"));
		selenium.clickAt("//a[@id='_170_manageLayoutSetBranches']",
			RuntimeVariables.replace("Manage Site Pages Variations"));
		Thread.sleep(5000);
		selenium.selectFrame(
			"//div[@class='yui3-widget-bd aui-panel-bd aui-dialog-bd aui-dialog-iframe-bd']/iframe");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
		selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Merge")
										.equals(selenium.getText(
								"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a[contains(.,'Merge')]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Merge"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a[contains(.,'Merge')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a[contains(.,'Merge')]",
			RuntimeVariables.replace("Merge"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tr[3]/td[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Christmas"),
			selenium.getText("//tr[3]/td[1]"));
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent(
				"//a[contains(@data-layoutsetbranchname,'Christmas')]"));
		selenium.clickAt("//a[contains(@data-layoutsetbranchname,'Christmas')]",
			RuntimeVariables.replace("Select"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("Are you sure you want to merge changes from Christmas?".equals(
							selenium.getConfirmation())) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Site page variation was merged."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}