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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.compareversioneditwikifrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CompareVersionEditWikiFrontPageMinorChangeTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CompareVersionEditWikiFrontPageMinorChangeTest extends BaseTestCase {
	public void testCompareVersionEditWikiFrontPageMinorChange()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Details", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=History", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.check("//tr[3]/td[1]/input");
		selenium.check("//tr[4]/td[1]/input");
		selenium.uncheck("//tr[5]/td[1]/input");
		selenium.clickAt("//input[@value='Compare Versions']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Wiki Article"),
			selenium.getText("//div[@class='taglib-diff-html']/h2"));
		assertEquals(RuntimeVariables.replace("this is italics"),
			selenium.getText("//i"));
		assertEquals(RuntimeVariables.replace("bold"), selenium.getText("//b"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertEquals(RuntimeVariables.replace(
				"this is a list item this is a sub list item"),
			selenium.getText("//div[@class='taglib-diff-html']/ul/li"));
		assertEquals(RuntimeVariables.replace("Minor Edit."),
			selenium.getText("//span[@id='added-diff-0']"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Text Mode")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Text Mode", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[3]/td[1]/table/tbody/tr[4]/td"));
		assertEquals(RuntimeVariables.replace("Minor Edit."),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[3]/td[2]/table/tbody/tr[4]/td/ins"));
	}
}