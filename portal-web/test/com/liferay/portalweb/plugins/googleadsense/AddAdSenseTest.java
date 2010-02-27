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

package com.liferay.portalweb.plugins.googleadsense;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddAdSenseTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddAdSenseTest extends BaseTestCase {
	public void testAddAdSense() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Google Adsense Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Google Adsense Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_86_adClient",
			RuntimeVariables.replace("pub-0000000000"));
		selenium.type("_86_adClient", RuntimeVariables.replace("pub-0000000000"));
		selenium.typeKeys("_86_adChannel", RuntimeVariables.replace("12345678"));
		selenium.type("_86_adChannel", RuntimeVariables.replace("12345678"));
		selenium.select("_86_adType", RuntimeVariables.replace("label=Text"));
		selenium.select("_86_adFormat",
			RuntimeVariables.replace("label=(728 x 90) - Leaderboard"));
		selenium.typeKeys("_86_colorBorder", RuntimeVariables.replace("FFFFFF"));
		selenium.type("_86_colorBorder", RuntimeVariables.replace("FFFFFF"));
		selenium.typeKeys("_86_colorBg", RuntimeVariables.replace("0000FF"));
		selenium.type("_86_colorBg", RuntimeVariables.replace("0000FF"));
		selenium.typeKeys("_86_colorLink", RuntimeVariables.replace("FFFFFF"));
		selenium.type("_86_colorLink", RuntimeVariables.replace("FFFFFF"));
		selenium.typeKeys("_86_colorText", RuntimeVariables.replace("000000"));
		selenium.type("_86_colorText", RuntimeVariables.replace("000000"));
		selenium.typeKeys("_86_colorUrl", RuntimeVariables.replace("008000"));
		selenium.type("_86_colorUrl", RuntimeVariables.replace("008000"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have successfully updated the setup."));
	}
}