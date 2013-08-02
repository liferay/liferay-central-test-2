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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontenttemplatewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentTemplateWCDTest extends BaseTestCase {
	public void testAddWCWebContentTemplateWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Web Content Display Test Page");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@alt='Add Web Content']",
			RuntimeVariables.replace("Add Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[@id='_15_selectTemplateLink']",
			RuntimeVariables.replace("Select Template"));
		selenium.waitForVisible("//td[2]/a");
		assertEquals(RuntimeVariables.replace(
				"Web Content Localized Template Name"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Web Content Localized Template Name"));
		selenium.waitForConfirmation(
			"Selecting a template will change the structure, available input fields, and available templates? Do you want to proceed?");
		selenium.waitForText("//span[@class='structure-name-label']",
			"Web Content Localized Structure Name");
		assertEquals(RuntimeVariables.replace(
				"Web Content Localized Structure Name"),
			selenium.getText("//span[@class='structure-name-label']"));
		assertEquals(RuntimeVariables.replace(
				"Web Content Localized Template Name"),
			selenium.getText("//span[@class='template-name-label']"));
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Hello World Localized Article"));
		selenium.type("//input[@id='page-name']",
			RuntimeVariables.replace("Hello World Page Name"));
		selenium.type("//input[@id='page-description']",
			RuntimeVariables.replace("Hello World Page Description"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Hello World Page Name"),
			selenium.getText("//td[@class='page-name']"));
		assertEquals(RuntimeVariables.replace("Hello World Page Description"),
			selenium.getText("//td[@class='page-description']"));
	}
}