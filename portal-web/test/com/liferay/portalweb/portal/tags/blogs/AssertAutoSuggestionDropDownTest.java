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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertAutoSuggestionDropDownTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertAutoSuggestionDropDownTest extends BaseTestCase {
	public void testAssertAutoSuggestionDropDown() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Tags Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Tags Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Tags3 Blogs3 Test3 Entry3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Tags3 Blogs3 Test3 Entry3",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//li[5]/span/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Edit", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.type("//div[7]/div/input[2]", RuntimeVariables.replace(""));
		selenium.typeKeys("//div[7]/div/input[2]",
			RuntimeVariables.replace("selenium"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("selenium1 liferay1"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[1]"));
		assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[2]"));
		assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("selenium4 liferay4"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[4]"));
		selenium.typeKeys("//div[6]/input[2]", RuntimeVariables.replace("2"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[1]"));
		assertFalse(selenium.isVisible("//div[6]/div[1]/div[2]/ul/li[2]"));
		assertFalse(selenium.isVisible("//div[6]/div[1]/div[2]/ul/li[3]"));
		assertFalse(selenium.isVisible("//div[6]/div[1]/div[2]/ul/li[4]"));
	}
}