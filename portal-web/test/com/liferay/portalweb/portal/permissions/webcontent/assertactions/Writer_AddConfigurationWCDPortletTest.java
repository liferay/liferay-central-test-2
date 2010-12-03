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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Writer_AddConfigurationWCDPortletTest extends BaseTestCase {
	public void testWriter_AddConfigurationWCDPortlet()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Web Content Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//img[@alt='Select Web Content']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_86_showAvailableLocalesCheckbox")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked("_86_showAvailableLocalesCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_86_showAvailableLocalesCheckbox",
			RuntimeVariables.replace(""));
		assertFalse(selenium.isChecked("_86_enablePrintCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_86_enablePrintCheckbox", RuntimeVariables.replace(""));
		assertFalse(selenium.isChecked("_86_enableRatingsCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_86_enableRatingsCheckbox",
			RuntimeVariables.replace(""));
		assertFalse(selenium.isChecked("_86_enableCommentsCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_86_enableCommentsCheckbox",
			RuntimeVariables.replace(""));
		assertFalse(selenium.isChecked("_86_enableCommentRatingsCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_86_enableCommentRatingsCheckbox",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@id='p_p_id_86_']/div/div"));
		assertTrue(selenium.isChecked("_86_showAvailableLocalesCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_86_enablePrintCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_86_enableRatingsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_86_enableCommentsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_86_enableCommentRatingsCheckbox"));
		selenium.saveScreenShotAndSource();
	}
}