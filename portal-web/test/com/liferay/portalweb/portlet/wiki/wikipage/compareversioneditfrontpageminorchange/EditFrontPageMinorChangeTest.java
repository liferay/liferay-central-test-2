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

package com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFrontPageMinorChangeTest extends BaseTestCase {
	public void testEditFrontPageMinorChange() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Wiki Test Page",
					RuntimeVariables.replace("Wiki Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
					selenium.getText("//div[@class='wiki-body']/p"));
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//span[contains(.,'Edit')]/a/span"));
				selenium.clickAt("//span[contains(.,'Edit')]/a/span",
					RuntimeVariables.replace("Edit"));

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
						if (selenium.isVisible(
									"//a[@class='cke_button_source cke_on']")) {
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
					RuntimeVariables.replace("Wiki FrontPage Content Edit"));
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
				selenium.selectFrame(
					"//td[@id='cke_contents__36_editor']/iframe");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Wiki FrontPage Content Edit")
												.equals(selenium.getText(
										"//body"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame("relative=top");

				boolean minorEditChecked = selenium.isChecked(
						"//input[@id='_36_minorEditCheckbox']");

				if (minorEditChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_36_minorEditCheckbox']",
					RuntimeVariables.replace("This is a minor edit."));

			case 2:
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"Wiki FrontPage Content Edit"),
					selenium.getText("//div[@class='wiki-body']/p"));
				assertNotEquals(RuntimeVariables.replace(
						"Wiki FrontPage Content"),
					selenium.getText("//div[@class='wiki-body']/p"));
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Wiki Test Page",
					RuntimeVariables.replace("Wiki Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText("//div[3]/span[2]/a/span"));
				selenium.clickAt("//div[3]/span[2]/a/span",
					RuntimeVariables.replace("Details"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=History",
					RuntimeVariables.replace("History"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("1.2 (Minor Edit)"),
					selenium.getText("//tr[3]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("1.1"),
					selenium.getText("//tr[4]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("1.0 (Minor Edit)"),
					selenium.getText("//tr[5]/td[4]/a"));

			case 100:
				label = -1;
			}
		}
	}
}