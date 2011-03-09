/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.stagingcommunity.documentlibrary.document.publishtolivenowdlportletdocumentdocxdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureCommunityStagingTypeLocalLiveDLTest extends BaseTestCase {
	public void testConfigureCommunityStagingTypeLocalLiveDL()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace("Communities"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_134_name",
					RuntimeVariables.replace("Community Name"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//strong/a"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Manage Pages"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Settings"),
					selenium.getText("//ul[1]/li[3]/span/span/a"));
				selenium.click(RuntimeVariables.replace(
						"//ul[1]/li[3]/span/span/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace("link=Staging"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.select("_134_stagingType",
					RuntimeVariables.replace("label=Local Live"));

				boolean documentLibraryChecked = selenium.isChecked(
						"_134_staged-portlet_20Checkbox");

				if (documentLibraryChecked) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked("_134_staged-portlet_20Checkbox"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("_134_staged-portlet_20Checkbox",
					RuntimeVariables.replace("Document Library"));
				assertTrue(selenium.isChecked("_134_staged-portlet_20Checkbox"));
				selenium.saveScreenShotAndSource();

			case 2:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to activate local staging for Community Name[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked("_134_staged-portlet_20Checkbox"));
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}