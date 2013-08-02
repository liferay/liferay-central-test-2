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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_AddSPVariationValentinesCopyFromNoneTest extends BaseTestCase {
	public void testUser_AddSPVariationValentinesCopyFromNone()
		throws Exception {
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
					"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean christmas2Present = selenium.isElementPresent(
						"link=Christmas 2");

				if (!christmas2Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Christmas 2",
					RuntimeVariables.replace("Christmas 2"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Christmas 2 Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				assertEquals(RuntimeVariables.replace(
						"Manage Site Pages Variations"),
					selenium.getText("//a[@id='_170_manageLayoutSetBranches']"));
				selenium.clickAt("//a[@id='_170_manageLayoutSetBranches']",
					RuntimeVariables.replace("Manage Site Pages Variations"));
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@id='_170_addBranchButton']");
				selenium.clickAt("//input[@id='_170_addBranchButton']",
					RuntimeVariables.replace("Add Site Pages Variation"));
				selenium.waitForVisible("//input[@id='_170_name']");
				selenium.type("//input[@id='_170_name']",
					RuntimeVariables.replace("Valentines"));
				selenium.type("//textarea[@id='_170_description']",
					RuntimeVariables.replace(
						"Valentines Site Pages Variation of Site Name"));
				selenium.select("//select[@id='_170_copyLayoutSetBranchId']",
					RuntimeVariables.replace(
						"None (Empty Site Pages Variation)"));
				selenium.clickAt("//input[@value='Add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Site page variation was added."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}