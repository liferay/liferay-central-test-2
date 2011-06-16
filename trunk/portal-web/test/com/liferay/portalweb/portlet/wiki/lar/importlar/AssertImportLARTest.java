/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.wiki.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertImportLARTest extends BaseTestCase {
	public void testAssertImportLAR() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent("Test Wiki Article"));
		assertTrue(selenium.isTextPresent("this is italics"));
		assertTrue(selenium.isTextPresent("bold"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertTrue(selenium.isTextPresent("this is a list item"));
		assertTrue(selenium.isTextPresent("this is a sub list item"));
		assertTrue(selenium.isElementPresent("link=Test"));
		selenium.clickAt("link=Test", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent("Test Child Article"));
		assertTrue(selenium.isTextPresent("this is italics"));
		assertTrue(selenium.isTextPresent("bold"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertTrue(selenium.isTextPresent("this is a list item"));
		assertTrue(selenium.isTextPresent("this is a sub list item"));
		selenium.clickAt("link=Second Edited Wiki Test",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent("Yes this is a second test article"));
		assertTrue(selenium.isTextPresent(
				"I love Liferay! This Wiki has been EDITED!"));
		assertTrue(selenium.isElementPresent("link=Link Me 1"));
		assertTrue(selenium.isElementPresent("link=Link Me 2"));
		selenium.clickAt("link=Link Me 1", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Hi Administrator! Hope you are well! Please link me to another page!"));
		selenium.clickAt("link=Link Me 2", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent("Hi Administrator!"));
		assertTrue(selenium.isTextPresent(
				"I made another mistake! Oh me. Please link this article to another!"));
	}
}