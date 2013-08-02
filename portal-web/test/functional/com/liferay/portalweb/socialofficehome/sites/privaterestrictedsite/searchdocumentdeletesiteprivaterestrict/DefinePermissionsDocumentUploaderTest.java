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

package com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.searchdocumentdeletesiteprivaterestrict;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsDocumentUploaderTest extends BaseTestCase {
	public void testDefinePermissionsDocumentUploader()
		throws Exception {
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
			RuntimeVariables.replace("Uploader"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Document Uploader Name"),
			selenium.getText("//tr[contains(.,'Document Uploader')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Document Uploader')]/td[1]/a",
			RuntimeVariables.replace("Document Uploader Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForText("//form/h3[contains(.,'Documents and Media Document')]",
			"Documents and Media Document");
		assertEquals(RuntimeVariables.replace("Documents and Media Document"),
			selenium.getText(
				"//form/h3[contains(.,'Documents and Media Document')]"));
		assertFalse(selenium.isChecked(
				"xPath=(//th[@class='col-1 col-rowChecker first']/input)[2]"));
		selenium.clickAt("xPath=(//th[@class='col-1 col-rowChecker first']/input)[2]",
			RuntimeVariables.replace("Documents and Media Document All Actions"));
		assertTrue(selenium.isChecked(
				"xPath=(//th[@class='col-1 col-rowChecker first']/input)[2]"));
		assertFalse(selenium.isChecked(
				"xPath=(//th[@class='col-1 col-rowChecker first']/input)[3]"));
		selenium.clickAt("xPath=(//th[@class='col-1 col-rowChecker first']/input)[3]",
			RuntimeVariables.replace("Documents and Media Document All Actions"));
		assertTrue(selenium.isChecked(
				"xPath=(//th[@class='col-1 col-rowChecker first']/input)[3]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}