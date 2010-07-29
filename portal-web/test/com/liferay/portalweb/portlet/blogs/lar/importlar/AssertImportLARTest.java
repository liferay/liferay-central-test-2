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

package com.liferay.portalweb.portlet.blogs.lar.importlar;

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
				if (selenium.isElementPresent("link=Blogs Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry Title")
										.equals(selenium.getText(
								"//div[2]/div[1]/div[1]/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[2]/div[1]/div[1]/a"));
		assertTrue(selenium.isPartialText("//form/div[2]/div[3]",
				"Blogs Entry Content"));
		assertTrue(selenium.isPartialText("//div[2]/div[3]/a", "Read More"));
		selenium.clickAt("//div[2]/div[3]/a",
			RuntimeVariables.replace("Read More \u00bb"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry Title")
										.equals(selenium.getText(
								"//div[1]/h1/span"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//p"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Comment Body"),
			selenium.getText("//td[2]/div[1]"));
		assertFalse(selenium.isTextPresent("Read More"));
	}
}