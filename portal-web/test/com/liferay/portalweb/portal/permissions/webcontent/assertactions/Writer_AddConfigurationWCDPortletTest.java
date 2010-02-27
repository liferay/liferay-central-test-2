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
 * <a href="Writer_AddConfigurationWCDPortletTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Writer_AddConfigurationWCDPortletTest extends BaseTestCase {
	public void testWriter_AddConfigurationWCDPortlet()
		throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Web Content Display Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Web Content Display Permissions Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@alt='Select Web Content']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("_86_showAvailableLocalesCheckbox",
			RuntimeVariables.replace(""));
		Thread.sleep(500);
		selenium.clickAt("_86_enablePrintCheckbox", RuntimeVariables.replace(""));
		Thread.sleep(500);
		selenium.clickAt("_86_enableRatingsCheckbox",
			RuntimeVariables.replace(""));
		Thread.sleep(500);
		selenium.clickAt("_86_enableCommentsCheckbox",
			RuntimeVariables.replace(""));
		Thread.sleep(500);
		selenium.clickAt("_86_enableCommentRatingsCheckbox",
			RuntimeVariables.replace(""));
	}
}