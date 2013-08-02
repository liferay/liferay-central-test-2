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

package com.liferay.portalweb.socialofficesite.blogs.blogsentry.sousaddblogsentrysite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPermissionsTest extends BaseTestCase {
	public void testTearDownPermissions() throws Exception {
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
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Site Member"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Site Member"),
					selenium.getText("//tr/td[1]/a[contains(.,'Site Member')]"));
				selenium.clickAt("//tr/td[1]/a[contains(.,'Site Member')]",
					RuntimeVariables.replace("Site Member"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Blogs"));
				selenium.waitForText("//form/h3[contains(.,'Blogs')]", "Blogs");
				assertEquals(RuntimeVariables.replace("Blogs"),
					selenium.getText("//form/h3[contains(.,'Blogs')]"));
				selenium.clickAt("xPath=(//th[@class='col-1 col-rowChecker first']/input)[1]",
					RuntimeVariables.replace("Blogs All Actions"));

				boolean blogsFirstRowChecked = selenium.isChecked(
						"xPath=(//td[contains(@id,'SearchContainer_col-rowChecker_row-1')]/input)[1]");

				if (!blogsFirstRowChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("xPath=(//th[@class='col-1 col-rowChecker first']/input)[1]",
					RuntimeVariables.replace("Blogs All Actions"));

			case 2:
				assertFalse(selenium.isChecked(
						"xPath=(//td[contains(@id,'SearchContainer_col-rowChecker_row-1')]/input)[1]"));
				selenium.clickAt("xPath=(//th[@class='col-1 col-rowChecker first']/input)[2]",
					RuntimeVariables.replace("Blogs Entry All Actions"));

				boolean blogsEntryFirstRowChecked = selenium.isChecked(
						"xPath=(//td[contains(@id,'SearchContainer_col-rowChecker_row-1')]/input)[2]");

				if (!blogsEntryFirstRowChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("xPath=(//th[@class='col-1 col-rowChecker first']/input)[2]",
					RuntimeVariables.replace("Blogs All Actions"));

			case 3:
				assertFalse(selenium.isChecked(
						"xPath=(//td[contains(@id,'SearchContainer_col-rowChecker_row-1')]/input)[2]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}