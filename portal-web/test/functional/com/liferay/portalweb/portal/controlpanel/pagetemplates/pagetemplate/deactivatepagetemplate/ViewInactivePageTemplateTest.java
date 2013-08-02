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

package com.liferay.portalweb.portal.controlpanel.pagetemplates.pagetemplate.deactivatepagetemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewInactivePageTemplateTest extends BaseTestCase {
	public void testViewInactivePageTemplate() throws Exception {
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
		selenium.clickAt("link=Page Templates",
			RuntimeVariables.replace("Page Templates"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Page Templates"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button view-button current']/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button add-button ']/a"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Active"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Page Template Name"),
			selenium.getText("//tr[contains(.,'Page Template Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//tr[contains(.,'Page Template Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Page Template Name')]/td/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Page Template Name')]/td[1]/a",
			RuntimeVariables.replace("Page Template Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Page Templates"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button view-button current']/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button add-button ']/a"));
		assertEquals(RuntimeVariables.replace("Page Template Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//a[@id='_146_TabsBack']"));
		assertEquals(RuntimeVariables.replace("Name (Required)"),
			selenium.getText("//span/label"));
		assertTrue(selenium.isVisible("//input[@id='_146_name_en_US']"));
		assertTrue(selenium.isVisible("//img[@alt='English (United States)']"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText("//span[@class='flag-selector nobr']/a"));
		assertEquals(RuntimeVariables.replace("Description"),
			selenium.getText("//span[2]/span/label"));
		assertTrue(selenium.isVisible("//textarea[@id='_146_description']"));
		assertFalse(selenium.isChecked("//input[@id='_146_activeCheckbox']"));
		assertEquals(RuntimeVariables.replace("Active"),
			selenium.getText("//label[@for='_146_activeCheckbox']"));
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText("//div/label"));
		assertEquals(RuntimeVariables.replace("Open Page Template"),
			selenium.getText(
				"//div[@class='aui-field aui-field-wrapper']/div/span/a/span"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}