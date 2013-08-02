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

package com.liferay.portalweb.plugins.stocks;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletStocksTest extends BaseTestCase {
	public void testConfigurePortletStocks() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.click(RuntimeVariables.replace("link=Stocks Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Preferences')]/a");
		assertEquals(RuntimeVariables.replace("Preferences"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Preferences')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Preferences')]/a"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.type("//section[@id='portlet_1_WAR_stocksportlet']/div/div/div/form/textarea",
			RuntimeVariables.replace("GOOG"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated your preferences."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("//a[@class='portlet-icon-back']",
			RuntimeVariables.replace("Return to Full Page"));
		selenium.waitForVisible(
			"//section[@id='portlet_1_WAR_stocksportlet']/div/div/div/form/table/tbody/tr/td[1]/a");
		assertEquals(RuntimeVariables.replace("GOOG"),
			selenium.getText(
				"//section[@id='portlet_1_WAR_stocksportlet']/div/div/div/form/table/tbody/tr/td[1]/a"));
		selenium.clickAt("//section[@id='portlet_1_WAR_stocksportlet']/div/div/div/form/table/tbody/tr/td[1]/a",
			RuntimeVariables.replace("GOOG"));
		selenium.waitForPageToLoad("30000");
		assertEquals("GOOG",
			selenium.getValue("//input[@name='_1_WAR_stocksportlet_symbol']"));
		assertTrue(selenium.isPartialText("//td", "Last Trade"));
		assertTrue(selenium.isPartialText("//td[2]", "Change"));
		assertTrue(selenium.isPartialText("//td[3]", "Day High"));
		assertTrue(selenium.isPartialText("//td[4]", "Day Low"));
		assertTrue(selenium.isPartialText("//td[5]", "Open"));
		assertTrue(selenium.isPartialText("//td[6]", "Previous Close"));
		assertTrue(selenium.isPartialText("//td[7]", "Volume"));
	}
}