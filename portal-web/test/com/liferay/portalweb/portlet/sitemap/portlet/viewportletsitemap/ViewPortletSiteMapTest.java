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

package com.liferay.portalweb.portlet.sitemap.portlet.viewportletsitemap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ViewPortletSiteMapTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewPortletSiteMapTest extends BaseTestCase {
	public void testViewPortletSiteMap() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Site Map Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Site Map Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("//div[2]/div/div/ul/li[1]/a"));
		selenium.clickAt("//div[2]/div/div/ul/li[1]/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Sign In"),
			selenium.getText("//td[1]/div/div/div/div[1]/span"));
		assertEquals(RuntimeVariables.replace("Hello World"),
			selenium.getText("//td[2]/div/div/div/div[1]/span"));
		assertTrue(selenium.isElementPresent("//td[1]/div[1]/div"));
		assertTrue(selenium.isElementPresent("//td[2]/div[1]/div"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Site Map Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Site Map Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Map Test Page"),
			selenium.getText("//div[2]/div/div/ul/li[2]/a"));
		selenium.clickAt("//div[2]/div/div/ul/li[2]/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Map"),
			selenium.getText("//td[1]/div/div/div/div[1]/span"));
		assertTrue(selenium.isElementPresent("//td[1]/div[1]/div"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Site Map Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Site Map Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Child Test Page"),
			selenium.getText("//div[2]/div/div/ul/li[2]/ul/li/a"));
		selenium.clickAt("//div[2]/div/div/ul/li[2]/ul/li/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("//td[1]/div[1]/div"));
	}
}