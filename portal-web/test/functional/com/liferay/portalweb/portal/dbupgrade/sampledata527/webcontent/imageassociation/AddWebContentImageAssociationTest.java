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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentImageAssociationTest extends BaseTestCase {
	public void testAddWebContentImageAssociation() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Web Content Image Association Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
			RuntimeVariables.replace("Public Pages - Live (1)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace("Add Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_title']",
			RuntimeVariables.replace("Image Web Content Test"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		assertTrue(selenium.getConfirmation()
						   .matches("^Selecting a new structure will change the available input fields and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
		selenium.waitForPopUp("null", RuntimeVariables.replace("30000"));
		selenium.selectWindow("title=Web Content");
		selenium.waitForVisible("link=TEST_IMAGE");
		selenium.click("link=TEST_IMAGE");
		selenium.selectWindow("null");
		selenium.waitForText("//a[@id='_15_structureName']",
			"Image Structure Test");
		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("//a[@id='_15_structureName']"));
		selenium.type("//input[@id='_15_structure_el0_content']",
			RuntimeVariables.replace("Text Test"));
		selenium.uploadFile("//input[@id='_15_structure_el1_content']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata527\\webcontent\\imageassociation\\dependencies\\Image.jpg"));
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("null", RuntimeVariables.replace("30000"));
		selenium.selectWindow("title=Web Content");
		selenium.waitForVisible(
			"//tr[@class='portlet-section-body results-row last']/td/a");
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td/a",
			RuntimeVariables.replace("Folder Test"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Choose']");
		selenium.selectWindow("null");
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save and Approve']",
			RuntimeVariables.replace("Save and Approve"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Image Web Content Test"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
	}
}