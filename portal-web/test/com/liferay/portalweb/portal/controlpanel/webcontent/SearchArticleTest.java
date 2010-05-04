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
 * <a href="SearchArticleTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchArticleTest extends BaseTestCase {
	public void testSearchArticle() throws Exception {
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
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_15_searchArticleId")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.typeKeys("_15_searchArticleId",
					RuntimeVariables.replace("Test"));
				selenium.type("_15_searchArticleId",
					RuntimeVariables.replace("Test"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.type("_15_searchArticleId",
					RuntimeVariables.replace(""));
				selenium.type("_15_version", RuntimeVariables.replace("1.0"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"link=Test Web Content Article"));
				selenium.type("_15_version", RuntimeVariables.replace("1.1"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.type("_15_version", RuntimeVariables.replace(""));
				selenium.type("_15_title", RuntimeVariables.replace("Test"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"link=Test Web Content Article"));
				selenium.type("_15_title", RuntimeVariables.replace("Test1"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.type("_15_title", RuntimeVariables.replace(""));
				selenium.type("_15_description",
					RuntimeVariables.replace("test"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"link=Test Web Content Article"));
				selenium.type("_15_description",
					RuntimeVariables.replace("test1"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.type("_15_description", RuntimeVariables.replace(""));
				selenium.type("_15_content", RuntimeVariables.replace("test"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"link=Test Web Content Article"));
				selenium.type("_15_content", RuntimeVariables.replace("test1"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.type("_15_content", RuntimeVariables.replace(""));
				selenium.select("_15_type",
					RuntimeVariables.replace("label=Announcements"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"link=Test Web Content Article"));
				selenium.select("_15_type",
					RuntimeVariables.replace("label=Blogs"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.select("_15_type", RuntimeVariables.replace("label="));
				selenium.select("_15_status",
					RuntimeVariables.replace("label=Approved"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"link=Test Web Content Article"));
				selenium.select("_15_status",
					RuntimeVariables.replace("label=Not Approved"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("No Web Content was found."));
				selenium.select("_15_status", RuntimeVariables.replace("label="));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_15_keywords")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 100:
				label = -1;
			}
		}
	}
}