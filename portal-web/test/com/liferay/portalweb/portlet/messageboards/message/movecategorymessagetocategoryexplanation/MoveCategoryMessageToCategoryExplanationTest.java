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

package com.liferay.portalweb.portlet.messageboards.message.movecategorymessagetocategoryexplanation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="MoveCategoryMessageToCategoryExplanationTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MoveCategoryMessageToCategoryExplanationTest extends BaseTestCase {
	public void testMoveCategoryMessageToCategoryExplanation()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[7]/ul/li/strong/a", RuntimeVariables.replace(""));

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

		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st1 Cat\u00e9gory1"),
			selenium.getText("_19_categoryName"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace(""));
		selenium.waitForPopUp("category", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=category");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//tr[4]/td[5]/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//tr[4]/td[5]/input");
		selenium.selectWindow("null");
		assertEquals(RuntimeVariables.replace("T\u00e9st2 Cat\u00e9gory2"),
			selenium.getText("_19_categoryName"));
		assertFalse(selenium.isChecked("_19_addExplanationPostCheckbox"));
		selenium.clickAt("_19_addExplanationPostCheckbox",
			RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked("_19_addExplanationPostCheckbox"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_19_subject")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_19_subject",
			RuntimeVariables.replace("Move Thread Explanation Post"));
		selenium.type("_19_textArea",
			RuntimeVariables.replace("This is a move thread explanation post."));
		selenium.click(RuntimeVariables.replace("//input[@value='Move Thread']"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent(
				"link=T\u00e9st M\u00e9ssag\u00e9"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[4]/td[1]/a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Move Thread Explanation Post"),
			selenium.getText("//tr[2]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Move Thread Explanation Post"),
			selenium.getText("//div[5]/table/tbody/tr[1]/td[2]/div[1]/div/a/b"));
		assertEquals(RuntimeVariables.replace(
				"This is a move thread explanation post."),
			selenium.getText("//div[5]/table/tbody/tr[1]/td[2]/div[2]"));
	}
}