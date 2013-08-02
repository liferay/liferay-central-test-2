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

package com.liferay.portalweb.socialoffice.users.sites.assignrolesosites;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveRoleSOSitesTest extends BaseTestCase {
	public void testRemoveRoleSOSites() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Roles"),
					selenium.getText("//h1[@id='cpPortletTitle']/span"));
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Social Office"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean roleTitlePresent = selenium.isElementPresent(
						"//td[@id='_128_ocerSearchContainer_col-title_row-1']");

				if (!roleTitlePresent) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText(
						"//td[@id='_128_ocerSearchContainer_col-title_row-1']"));

			case 2:

				boolean roleNamePresent = selenium.isElementPresent(
						"//td[@id='_128_ocerSearchContainer_col-name_row-1']");

				if (!roleNamePresent) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText(
						"//td[@id='_128_ocerSearchContainer_col-name_row-1']"));

			case 3:
				assertEquals(RuntimeVariables.replace("Regular"),
					selenium.getText(
						"//td[@id='_128_ocerSearchContainer_col-type_row-1']"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Assign Members')]/a");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Assign Members')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Assign Members')]/a",
					RuntimeVariables.replace("Assign Members"));
				selenium.waitForVisible("//div[2]/h1/span");
				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText("//div[2]/h1/span"));
				selenium.waitForVisible("//form/ul/li[2]/span/a");
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//form/ul/li[2]/span/a"));
				selenium.clickAt("//form/ul/li[2]/span/a",
					RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Available",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@id='_128_name']");
				selenium.type("//input[@id='_128_name']",
					RuntimeVariables.replace("Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText("//tr[3]/td[2]"));
				assertTrue(selenium.isChecked("//td/input"));
				selenium.uncheck("//td/input");
				assertFalse(selenium.isChecked("//td/input"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked("//td/input"));

			case 100:
				label = -1;
			}
		}
	}
}