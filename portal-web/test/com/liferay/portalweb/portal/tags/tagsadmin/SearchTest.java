/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
 * @author Brian Wing Shun Chan
 */
public class SearchTest extends BaseTestCase {
	public void testSearch() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
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
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_99_tagsAdminSearchInput']",
			RuntimeVariables.replace("blue"));
		Thread.sleep(5000);
		assertTrue(selenium.isVisible("link=blue"));
		assertTrue(selenium.isVisible("link=blue car"));
		assertTrue(selenium.isVisible("link=blue green"));
		assertTrue(selenium.isElementNotPresent("link=green"));
		assertTrue(selenium.isElementNotPresent("link=green tree"));
		selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_99_tagsAdminSearchInput']",
			RuntimeVariables.replace("green"));
		Thread.sleep(5000);
		assertTrue(selenium.isElementNotPresent("link=blue"));
		assertTrue(selenium.isElementNotPresent("link=blue car"));
		assertTrue(selenium.isVisible("link=blue green"));
		assertTrue(selenium.isVisible("link=green"));
		assertTrue(selenium.isVisible("link=green tree"));
	}
}