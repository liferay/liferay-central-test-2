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

package com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiPageChildPageTest extends BaseTestCase {
	public void testAddWikiPageChildPage() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//tr[4]/td[1]/a"));
		selenium.clickAt("//tr[4]/td[1]/a",
			RuntimeVariables.replace("Wiki Page Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText("//div[6]/div[1]/span[1]/a/span"));
		selenium.clickAt("//div[6]/div[1]/span[1]/a/span",
			RuntimeVariables.replace("Add Child Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_36_title']",
			RuntimeVariables.replace("Wiki Page ChildPage Title"));

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

		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@class='cke_button_source cke_on']")) {
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
				if (selenium.isVisible(
							"//td[@id='cke_contents__36_editor']/textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//td[@id='cke_contents__36_editor']/textarea",
			RuntimeVariables.replace("Wiki Page ChildPage Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));

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

		assertTrue(selenium.isVisible(
				"//td[@id='cke_contents__36_editor']/iframe"));
		selenium.selectFrame("//td[@id='cke_contents__36_editor']/iframe");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Wiki Page ChildPage Content")
										.equals(selenium.getText("//body"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki Page ChildPage Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki Page ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Wiki Page ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Wiki Page ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}