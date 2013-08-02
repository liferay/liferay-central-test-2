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

package com.liferay.portalweb.portal.tags.tagsadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchTagsTest extends BaseTestCase {
	public void testSearchTags() throws Exception {
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
		selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_99_tagsAdminSearchInput']",
			RuntimeVariables.replace("blue"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("blue"),
			selenium.getText("link=blue"));
		assertEquals(RuntimeVariables.replace("blue car"),
			selenium.getText("link=blue car"));
		assertEquals(RuntimeVariables.replace("blue green"),
			selenium.getText("link=blue green"));
		assertFalse(selenium.isTextPresent("link=green"));
		assertFalse(selenium.isTextPresent("link=green tree"));
		selenium.type("//input[@id='_99_tagsAdminSearchInput']",
			RuntimeVariables.replace("green"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("blue green"),
			selenium.getText("link=blue green"));
		assertEquals(RuntimeVariables.replace("green"),
			selenium.getText("link=green"));
		assertEquals(RuntimeVariables.replace("green tree"),
			selenium.getText("link=green tree"));
		assertFalse(selenium.isTextPresent("link=blue"));
		assertFalse(selenium.isTextPresent("link=blue car"));
	}
}