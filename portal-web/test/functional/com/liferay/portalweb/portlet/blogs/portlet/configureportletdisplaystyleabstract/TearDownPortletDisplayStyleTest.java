/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.blogs.portlet.configureportletdisplaystyleabstract;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletDisplayStyleTest extends BaseTestCase {
	public void testTearDownPortletDisplayStyle() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible(
					"//iframe[@id='_33_configurationIframeDialog']");
				selenium.selectFrame(
					"//iframe[@id='_33_configurationIframeDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Display Settings");
				selenium.clickAt("link=Display Settings",
					RuntimeVariables.replace("Display Settings"));
				selenium.waitForVisible("//select[@id='_86_pageDelta']");
				selenium.select("//select[@id='_86_pageDelta']",
					RuntimeVariables.replace("5"));
				selenium.select("//select[@id='_86_pageDisplayStyle']",
					RuntimeVariables.replace("Full Content"));

				boolean enableFlagsChecked = selenium.isChecked(
						"_86_enableFlagsCheckbox");

				if (enableFlagsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableFlagsCheckbox']",
					RuntimeVariables.replace("Enable Flags"));

			case 2:

				boolean enableRatingsChecked = selenium.isChecked(
						"_86_enableRatingsCheckbox");

				if (enableRatingsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableRatingsCheckbox']",
					RuntimeVariables.replace("Enable Ratings"));

			case 3:

				boolean enableCommentsChecked = selenium.isChecked(
						"_86_enableCommentsCheckbox");

				if (enableCommentsChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableCommentsCheckbox']",
					RuntimeVariables.replace("Enable Comments"));

			case 4:

				boolean enableCommentRatingsChecked = selenium.isChecked(
						"_86_enableCommentRatingsCheckbox");

				if (enableCommentRatingsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableCommentRatingsCheckbox']",
					RuntimeVariables.replace("Enable Comment Ratings"));

			case 5:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}