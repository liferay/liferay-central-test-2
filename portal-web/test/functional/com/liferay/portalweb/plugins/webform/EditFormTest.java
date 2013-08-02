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

package com.liferay.portalweb.plugins.webform;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFormTest extends BaseTestCase {
	public void testEditForm() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Form Test Page",
			RuntimeVariables.replace("Web Form Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Suggestions"),
			selenium.getText("//legend/span"));
		assertEquals(RuntimeVariables.replace(
				"Your input is valuable to us. Please send us your suggestions."),
			selenium.getText("//em"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//div/span[1]/span/label"));
		assertEquals(RuntimeVariables.replace("Rating"),
			selenium.getText("//span[2]/span/label"));
		assertEquals(RuntimeVariables.replace(
				"Excellent Good Satisfactory Poor"),
			selenium.getText("//select"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//span[3]/span/label"));
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
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//input[@id='_86_title_en_US']");
		selenium.type("//input[@id='_86_title_en_US']",
			RuntimeVariables.replace("Feed Back"));
		selenium.type("//textarea[@name='_86_description_en_US']",
			RuntimeVariables.replace("Please let us know what you think!"));
		selenium.type("//input[@id='_86_fieldLabel1_en_US']",
			RuntimeVariables.replace("Your Name"));
		selenium.type("//input[@id='_86_fieldLabel2_en_US']",
			RuntimeVariables.replace("Rate Us!"));
		selenium.type("//input[@id='_86_fieldLabel3_en_US']",
			RuntimeVariables.replace("Additional Comments"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Form Test Page",
			RuntimeVariables.replace("Web Form Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Feed Back"),
			selenium.getText("//legend/span"));
		assertEquals(RuntimeVariables.replace(
				"Please let us know what you think!"), selenium.getText("//em"));
		assertEquals(RuntimeVariables.replace("Your Name"),
			selenium.getText("//div/span/span/label"));
		assertEquals(RuntimeVariables.replace("Rate Us!"),
			selenium.getText("//span[2]/span/label"));
		assertEquals(RuntimeVariables.replace(
				"Excellent Good Satisfactory Poor"),
			selenium.getText("//select"));
		assertEquals(RuntimeVariables.replace("Additional Comments"),
			selenium.getText("//span[3]/span/label"));
	}
}