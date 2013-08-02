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

package com.liferay.portalweb.plugins.samplephp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletSPHPTest extends BaseTestCase {
	public void testViewPortletSPHP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.click(RuntimeVariables.replace("link=Sample PHP Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@value='']");
		assertEquals(RuntimeVariables.replace("Quercus"),
			selenium.getText("//a[contains(.,'Quercus')]"));
		selenium.clickAt("//a[contains(.,'Quercus')]",
			RuntimeVariables.replace("Quercus"));
		selenium.waitForVisible("//div[5]/a");
		assertEquals(RuntimeVariables.replace("http://quercus.caucho.com"),
			selenium.getText("//div[5]/a"));
		assertEquals(RuntimeVariables.replace("PHP Info"),
			selenium.getText("//a[contains(.,'PHP Info')]"));
		selenium.clickAt("//a[contains(.,'PHP Info')]",
			RuntimeVariables.replace("PHP Info"));
		selenium.waitForVisible("//div/div/h2");
		assertEquals(RuntimeVariables.replace("PHP Variables"),
			selenium.getText("//div/div/h2"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//a[contains(.,'Home')]"));
		selenium.clickAt("//a[contains(.,'Home')]",
			RuntimeVariables.replace("Home"));
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
	}
}