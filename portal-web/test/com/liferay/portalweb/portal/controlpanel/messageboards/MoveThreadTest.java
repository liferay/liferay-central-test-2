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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveThreadTest extends BaseTestCase {
	public void testMoveThread() throws Exception {
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
				selenium.clickAt("link=Message Boards",
					RuntimeVariables.replace("Message Boards"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
					selenium.getText("//tr[4]/td[2]/a[1]/strong"));
				selenium.clickAt("//tr[4]/td[2]/a[1]/strong",
					RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"T\u00e9st Subcat\u00e9gory"),
					selenium.getText("//td[2]/a/strong"));
				selenium.clickAt("//td[2]/a/strong",
					RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"S\u00e9cond T\u00e9st Subcat\u00e9gory"),
					selenium.getText("//td[2]/a/strong"));
				selenium.clickAt("//td[2]/a/strong",
					RuntimeVariables.replace(
						"S\u00e9cond T\u00e9st Subcat\u00e9gory"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d",
					RuntimeVariables.replace(
						"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isTextPresent(
						"This m\u00e9ssag\u00e9 will b\u00e9 d\u00e9l\u00e9t\u00e9d!"));
				selenium.clickAt("link=Move Thread",
					RuntimeVariables.replace("Move Thread"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertFalse(selenium.isChecked(
						"//input[@id='_162_addExplanationPostCheckbox']"));
				selenium.clickAt("//input[@id='_162_addExplanationPostCheckbox']",
					RuntimeVariables.replace("Add post explanation."));
				assertTrue(selenium.isChecked(
						"//input[@id='_162_addExplanationPostCheckbox']"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='_162_subject']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_162_subject']",
					RuntimeVariables.replace("Moved to Sujr"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//textarea[@id='_162_editor' and @style='display: none;']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText(
						"//span[@id='cke_34_label' and .='Source']"));
				selenium.clickAt("//span[@id='cke_34_label' and .='Source']",
					RuntimeVariables.replace("Source"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//td[@id='cke_contents__162_editor']/textarea")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//td[@id='cke_contents__162_editor']/textarea",
					RuntimeVariables.replace(
						"Trust and paths will be straightened."));
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText(
						"//span[@id='cke_34_label' and .='Source']"));
				selenium.clickAt("//span[@id='cke_34_label' and .='Source']",
					RuntimeVariables.replace("Source"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//textarea[@id='_162_editor' and @style='display: none;']")) {
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
									"//td[@id='cke_contents__162_editor']/iframe")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame(
					"//td[@id='cke_contents__162_editor']/iframe");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Trust and paths will be straightened.")
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
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				selenium.waitForPopUp("category",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("category");
				Thread.sleep(5000);

				boolean CategoriesPresent = selenium.isElementPresent(
						"link=Categories");

				if (!CategoriesPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Categories",
					RuntimeVariables.replace("Categories"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 2:
				selenium.click("//input[@value='Choose']");
				selenium.selectWindow("null");
				Thread.sleep(5000);
				assertTrue(selenium.isElementPresent("link=Sujr"));
				selenium.clickAt("//input[@value='Move Thread']",
					RuntimeVariables.replace("Move Thread"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isElementPresent("link=Sujr"));
				assertTrue(selenium.isElementPresent(
						"link=T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"));
				assertTrue(selenium.isTextPresent(
						"This m\u00e9ssag\u00e9 will b\u00e9 d\u00e9l\u00e9t\u00e9d!"));
				assertTrue(selenium.isTextPresent(
						"Trust and paths will be straightened."));
				assertFalse(selenium.isElementPresent(
						"link=T\u00e9st Subcat\u00e9gory"));
				assertFalse(selenium.isElementPresent(
						"link=T\u00e9st Cat\u00e9gory"));

			case 100:
				label = -1;
			}
		}
	}
}