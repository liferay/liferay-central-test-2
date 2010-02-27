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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.editwikifrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="EditWikiFrontPageMinorChangeTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditWikiFrontPageMinorChangeTest extends BaseTestCase {
	public void testEditWikiFrontPageMinorChange() throws Exception {
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
						if (selenium.isElementPresent(
									"link=Wiki Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Wiki Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Wiki Article"),
					selenium.getText("//div[@class='wiki-body']/h2"));
				assertEquals(RuntimeVariables.replace("this is italics"),
					selenium.getText("//i"));
				assertEquals(RuntimeVariables.replace("bold"),
					selenium.getText("//b"));
				assertTrue(selenium.isElementPresent("link=Link to website"));
				assertEquals(RuntimeVariables.replace(
						"this is a list item this is a sub list item"),
					selenium.getText("//div[@class='wiki-body']/ul/li"));
				selenium.clickAt("link=Edit", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("//textarea",
					RuntimeVariables.replace(
						"==Test Wiki Article==\n\n//this is italics//\n\n**bold**\n\n[[http://www.liferay.com|Link to website]]\n\n*this is a list item\n**this is a sub list item\n\nMinor Edit."));

				boolean minorEditChecked = selenium.isChecked("//span/input[2]");

				if (minorEditChecked) {
					label = 2;

					continue;
				}

				selenium.click("//span/input[2]");

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//span[@class='aui-icon-search aui-icon']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Wiki Article"),
					selenium.getText("//div[@class='wiki-body']/h2"));
				assertEquals(RuntimeVariables.replace("this is italics"),
					selenium.getText("//i"));
				assertEquals(RuntimeVariables.replace("bold"),
					selenium.getText("//b"));
				assertTrue(selenium.isElementPresent("link=Link to website"));
				assertEquals(RuntimeVariables.replace(
						"this is a list item this is a sub list item"),
					selenium.getText("//div[@class='wiki-body']/ul/li"));
				assertEquals(RuntimeVariables.replace("Minor Edit."),
					selenium.getText("//div[@class='wiki-body']/p[4]"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Wiki Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Wiki Display Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Details", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=History", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=1.2 (Minor Edit)"));
				assertTrue(selenium.isElementPresent("link=1.1"));
				assertTrue(selenium.isElementPresent("link=1.0 (Minor Edit)"));

			case 100:
				label = -1;
			}
		}
	}
}