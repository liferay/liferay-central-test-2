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

package com.liferay.portalweb.plugins.samplelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurationTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurationTest extends BaseTestCase {
	public void testConfiguration() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Sample LAR Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Sample LAR Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"This is the Sample LAR Portlet. This was made to demonstrate the portlet LAR plugin feature."));
		selenium.click(RuntimeVariables.replace("//img[@alt='Configuration']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//strong/span");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Export / Import")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Export / Import"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("_86_exportFileName"));
		assertTrue(selenium.isElementPresent("//input[@value='Export']"));
		selenium.click(RuntimeVariables.replace("link=Import"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("_86_importFileName"));
		assertTrue(selenium.isElementPresent("//input[@value='Import']"));
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
	}
}