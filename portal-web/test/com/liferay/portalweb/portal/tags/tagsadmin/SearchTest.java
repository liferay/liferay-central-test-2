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

package com.liferay.portalweb.portal.tags.tagsadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SearchTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchTest extends BaseTestCase {
	public void testSearch() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Tags")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Tags", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("tags-admin-search-input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("tags-admin-search-input", RuntimeVariables.replace(""));
		selenium.typeKeys("tags-admin-search-input",
			RuntimeVariables.replace("blue"));
		Thread.sleep(5000);
		assertTrue(selenium.isVisible("link=blue"));
		assertTrue(selenium.isVisible("link=blue car"));
		assertTrue(selenium.isVisible("link=blue green"));
		assertFalse(selenium.isVisible("link=green"));
		assertFalse(selenium.isVisible("link=green tree"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Tags")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Tags", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("tags-admin-search-input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("tags-admin-search-input", RuntimeVariables.replace(""));
		selenium.typeKeys("tags-admin-search-input",
			RuntimeVariables.replace("green"));
		Thread.sleep(5000);
		assertFalse(selenium.isVisible("link=blue"));
		assertFalse(selenium.isVisible("link=blue car"));
		assertTrue(selenium.isVisible("link=blue green"));
		assertTrue(selenium.isVisible("link=green"));
		assertTrue(selenium.isVisible("link=green tree"));
	}
}