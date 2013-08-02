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

package com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletshowfoldersearch;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureDMPortletShowFolderSearchTest extends BaseTestCase {
	public void testConfigureDMPortletShowFolderSearch()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
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
				selenium.waitForVisible(
					"//iframe[@id='_20_configurationIframeDialog']");
				selenium.selectFrame(
					"//iframe[@id='_20_configurationIframeDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible(
					"//input[@id='_86_showFoldersSearchCheckbox']");

				boolean showSearchChecked = selenium.isChecked(
						"_86_showFoldersSearchCheckbox");

				if (showSearchChecked) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_showFoldersSearchCheckbox']"));
				selenium.clickAt("//input[@id='_86_showFoldersSearchCheckbox']",
					RuntimeVariables.replace("Show Search"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_showFoldersSearchCheckbox']"));

			case 2:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_showFoldersSearchCheckbox']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}