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

package com.liferay.portalweb.portlet.webcontentdisplay.wcstructure.addwcstructure2parentstructurestructure1wcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCStructure2ParentStructureStructure1WCDTest
	extends BaseTestCase {
	public void testAddWCStructure2ParentStructureStructure1WCD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@alt='Add Web Content']",
			RuntimeVariables.replace("Add Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_15_changeStructureButton']");
		selenium.clickAt("//a[@id='_15_changeStructureButton']",
			RuntimeVariables.replace("Change"));
		selenium.waitForVisible("//iframe[@name='_15_changeStruture']");
		selenium.selectFrame("//iframe[@name='_15_changeStruture']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//input[@value='Add Structure']");
		selenium.clickAt("//input[@value='Add Structure']",
			RuntimeVariables.replace("Add Structure"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='Select']");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[@name='_15_parentStructureSelector']");
		selenium.selectFrame("//iframe[@name='_15_parentStructureSelector']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//td[1]/a");
		assertTrue(selenium.isVisible("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure1 Description"),
			selenium.getText("//td[3]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC Structure1 Name"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[@name='_15_changeStruture']");
		selenium.selectFrame("//iframe[@name='_15_changeStruture']");
		selenium.type("//input[@id='_15_name_en_US']",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.type("//textarea[@id='_15_description_en_US']",
			RuntimeVariables.replace("WC Structure2 Description"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el0_type']",
			RuntimeVariables.replace("Text Area (HTML)"));
		selenium.type("//input[@id='_15_structure_el0_name']",
			RuntimeVariables.replace("text 1"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Description"),
			selenium.getText("//tr[4]/td[3]/a"));
		selenium.selectFrame("relative=top");
	}
}