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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiFrontPageChildPageTag2Test extends BaseTestCase {
	public void testAddWikiFrontPageChildPageTag2() throws Exception {
		selenium.open("/web/wiki-use-case-community/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//div[3]/span[1]/a/span"));
		selenium.clickAt("//div[3]/span[1]/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//textarea[@id='_36_editor' and @style='display: none;']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@class='lfr-tag-selector-input aui-field-input-text']",
			RuntimeVariables.replace("Wiki Tag 2"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//button[@id='add']"));
		selenium.clickAt("//button[@id='add']", RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Wiki Tag 2")
										.equals(selenium.getText(
								"//span/div/div/ul/li[2]/span/span[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Wiki Tag 2"),
			selenium.getText("//span/div/div/ul/li[2]/span/span[1]"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("wiki tag 1"),
			selenium.getText("xPath=(//a[@class='tag'])[1]"));
		assertEquals(RuntimeVariables.replace("wiki tag 2"),
			selenium.getText("xPath=(//a[@class='tag'])[2]"));
	}
}