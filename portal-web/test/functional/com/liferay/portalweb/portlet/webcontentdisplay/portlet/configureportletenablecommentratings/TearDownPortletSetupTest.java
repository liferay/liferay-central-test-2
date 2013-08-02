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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenablecommentratings;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletSetupTest extends BaseTestCase {
	public void testTearDownPortletSetup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
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
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Setup");
				selenium.clickAt("link=Setup", RuntimeVariables.replace("Setup"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//input[@id='_86_showAvailableLocalesCheckbox']"));

				boolean showAvailableLocalesChecked = selenium.isChecked(
						"//input[@id='_86_showAvailableLocalesCheckbox']");

				if (!showAvailableLocalesChecked) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isChecked(
						"//input[@id='_86_showAvailableLocalesCheckbox']"));
				selenium.clickAt("//input[@id='_86_showAvailableLocalesCheckbox']",
					RuntimeVariables.replace("Show Available Locales"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_showAvailableLocalesCheckbox']"));

			case 2:
				assertTrue(selenium.isVisible(
						"//input[@id='_86_enablePrintCheckbox']"));

				boolean enablePrintChecked = selenium.isChecked(
						"//input[@id='_86_enablePrintCheckbox']");

				if (!enablePrintChecked) {
					label = 3;

					continue;
				}

				assertTrue(selenium.isChecked(
						"//input[@id='_86_enablePrintCheckbox']"));
				selenium.clickAt("//input[@id='_86_enablePrintCheckbox']",
					RuntimeVariables.replace("Enable Print"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enablePrintCheckbox']"));

			case 3:
				assertTrue(selenium.isVisible(
						"//input[@id='_86_enableRatingsCheckbox']"));

				boolean enableRatingsChecked = selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']");

				if (!enableRatingsChecked) {
					label = 4;

					continue;
				}

				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']"));
				selenium.clickAt("//input[@id='_86_enableRatingsCheckbox']",
					RuntimeVariables.replace("Enable Ratings"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']"));

			case 4:
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_enableCommentsCheckbox']"));

				boolean enableCommentsChecked = selenium.isChecked(
						"//input[@id='_86_enableCommentsCheckbox']");

				if (!enableCommentsChecked) {
					label = 5;

					continue;
				}

				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableCommentsCheckbox']"));
				selenium.clickAt("//input[@id='_86_enableCommentsCheckbox']",
					RuntimeVariables.replace("Enable Comments"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableCommentsCheckbox']"));

			case 5:
				assertTrue(selenium.isVisible(
						"//input[@id='_86_enableCommentRatingsCheckbox']"));

				boolean enableCommentRatingsChecked = selenium.isChecked(
						"//input[@id='_86_enableCommentRatingsCheckbox']");

				if (!enableCommentRatingsChecked) {
					label = 6;

					continue;
				}

				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableCommentRatingsCheckbox']"));
				selenium.clickAt("//input[@id='_86_enableCommentRatingsCheckbox']",
					RuntimeVariables.replace("Enable Comment Ratings"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableCommentRatingsCheckbox']"));

			case 6:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_showAvailableLocalesCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enablePrintCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableCommentsCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableCommentRatingsCheckbox']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}