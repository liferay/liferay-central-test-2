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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddArticleTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddArticleTest extends BaseTestCase {
	public void testAddArticle() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Web Content")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_15_title",
			RuntimeVariables.replace("Test Web Content Article"));
		selenium.type("_15_title",
			RuntimeVariables.replace("Test Web Content Article"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_editor")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("FCKeditor1___Frame")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe[@id=\"_15_editor\"]");
		selenium.selectFrame("//iframe[@id=\"FCKeditor1___Frame\"]");
		selenium.selectFrame("//iframe");
		selenium.typeKeys("//body",
			RuntimeVariables.replace("This is a test Web Content Article"));
		selenium.type("//body",
			RuntimeVariables.replace("This is a test Web Content Article!"));
		selenium.selectFrame("relative=top");
		selenium.select("_15_type",
			RuntimeVariables.replace("label=Announcements"));
		selenium.select("_15_displayDateMonth",
			RuntimeVariables.replace("label=April"));
		selenium.select("_15_displayDateDay",
			RuntimeVariables.replace("label=10"));
		selenium.select("_15_displayDateYear",
			RuntimeVariables.replace("label=2010"));
		selenium.select("_15_displayDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_displayDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.clickAt("_15_neverExpireCheckbox", RuntimeVariables.replace(""));
		selenium.select("_15_expirationDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_15_expirationDateDay",
			RuntimeVariables.replace("label=27"));
		selenium.select("_15_expirationDateYear",
			RuntimeVariables.replace("label=2011"));
		selenium.select("_15_expirationDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_expirationDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.clickAt("_15_neverReviewCheckbox", RuntimeVariables.replace(""));
		selenium.select("_15_reviewDateMonth",
			RuntimeVariables.replace("label=September"));
		selenium.select("_15_reviewDateDay",
			RuntimeVariables.replace("label=16"));
		selenium.select("_15_reviewDateYear",
			RuntimeVariables.replace("label=2011"));
		selenium.select("_15_reviewDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_reviewDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.type("_15_description",
			RuntimeVariables.replace("Test Description!"));
		selenium.clickAt("//input[@value='Save and Approve']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//li[@id='_15_tabs1web-contentTabsId']/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//li[@id='_15_tabs1web-contentTabsId']/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
	}
}