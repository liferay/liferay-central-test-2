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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.compareversioneditwikifrontpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CompareVersionEditWikiFrontPageTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CompareVersionEditWikiFrontPageTest extends BaseTestCase {
	public void testCompareVersionEditWikiFrontPage() throws Exception {
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
		assertTrue(selenium.isTextPresent(
				"Comparing Versions 1.1 and 1.2 (Last Version)"));
		assertTrue(selenium.isTextPresent("Test Wiki Article Edited"));
		assertEquals(RuntimeVariables.replace("Test Wiki Article"),
			selenium.getText("//span[@id='changed-diff-0']"));
		assertEquals(RuntimeVariables.replace("Edited"),
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
		assertEquals(RuntimeVariables.replace("==Test Wiki Article=="),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[3]/td[1]/table/tbody/tr/td"));
		assertEquals(RuntimeVariables.replace("==Test Wiki Article Edited=="),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[3]/td[2]/table/tbody/tr/td"));
	}
}