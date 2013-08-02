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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructures;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCSubstructure2Test extends BaseTestCase {
	public void testAddWCSubstructure2() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Structures",
			RuntimeVariables.replace("Structures"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Structure']",
			RuntimeVariables.replace("Add Structure"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_name_en_US']",
			RuntimeVariables.replace("WC Substructure2 Name"));
		selenium.type("//textarea[@id='_15_description_en_US']",
			RuntimeVariables.replace("WC Substructure2 Description"));
		selenium.waitForVisible("//input[@value='Select']");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//iframe[@id='_15_parentStructureSelector']");
		selenium.selectFrame("//iframe[@id='_15_parentStructureSelector']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible("//input[@name='_15_keywords']");
		selenium.type("//input[@name='_15_keywords']",
			RuntimeVariables.replace("WC Structure1 Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure1 Description"),
			selenium.getText("//td[3]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC Structure1 Name"));
		selenium.selectFrame("relative=top");
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//a[@id='_15_parentStructureName']"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForVisible("//input[@id='_15_structure_el0_name']");
		selenium.type("//input[@id='_15_structure_el0_name']",
			RuntimeVariables.replace("Extra"));
		selenium.select("//select[@id='_15_structure_el0_type']",
			RuntimeVariables.replace("Text"));
		selenium.waitForVisible("//select[@id='_15_structure_el0_type']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}