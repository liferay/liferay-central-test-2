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

package com.liferay.portalweb.portal.dbupgrade.sampledata6120.webcontent.journalfeed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCContentTest extends BaseTestCase {
	public void testAddWCContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/wc-journal-feed-community/");
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
		assertEquals(RuntimeVariables.replace("WC Journal Feed Community"),
			selenium.getText(
				"//span[@title='WC Journal Feed Community']/ul/li/strong/a"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add']",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/textarea");
		selenium.type("//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/textarea",
			RuntimeVariables.replace("Web Content Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.waitForText("//body", "Web Content Content");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[4]/a"));
	}
}