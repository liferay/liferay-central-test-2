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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddFeedTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddFeedTest extends BaseTestCase {
	public void testAddFeed() throws Exception {
		selenium.clickAt("link=Feeds", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Feed']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_15_newFeedId",
			RuntimeVariables.replace("selenium-test-feed"));
		selenium.type("_15_name", RuntimeVariables.replace("Test Feed"));
		selenium.type("_15_description",
			RuntimeVariables.replace("This is a Test Feed"));
		selenium.type("_15_targetPortletId",
			RuntimeVariables.replace("Test-Portal-ID"));
		selenium.type("_15_targetLayoutFriendlyUrl",
			RuntimeVariables.replace("Test-URL"));
		selenium.select("_15_type", RuntimeVariables.replace("label=Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent("SELENIUM-TEST-FEED")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Return to Full Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}