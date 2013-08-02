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

package com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationSiteTest extends BaseTestCase {
	public void testViewOrganizationSite() throws Exception {
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
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//td[@id='_134_groupsSearchContainer_col-name_row--organization-name']/a",
				"Organization Name"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_134_groupsSearchContainer_col-name_row--organization-name']/a",
				"Belongs to an organization of type Regular Organization."));
		assertEquals(RuntimeVariables.replace("Private"),
			selenium.getText(
				"//td[@id='_134_groupsSearchContainer_col-type_row--organization-name']/a"));
		assertEquals(RuntimeVariables.replace("1 Organization"),
			selenium.getText(
				"//td[@id='_134_groupsSearchContainer_col-members_row--organization-name']"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//td[@id='_134_groupsSearchContainer_col-active_row--organization-name']"));
		selenium.clickAt("//td[@id='_134_groupsSearchContainer_col-name_row--organization-name']/a",
			RuntimeVariables.replace(
				"Organization Name Belongs to an organization of type Regular Organization."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Settings"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText(
				"//div[@id='_165_details']/fieldset/div/div/div",
				"Organization Name"));
		assertEquals("Private",
			selenium.getSelectedLabel("//select[@id='_165_type']"));
		assertTrue(selenium.isChecked("//input[@id='_165_activeCheckbox']"));
	}
}