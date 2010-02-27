/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.wiki.portlet.configureportletvisiblewikismultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurePortletVisibleWikisMultipleTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletVisibleWikisMultipleTest extends BaseTestCase {
	public void testConfigurePortletVisibleWikisMultiple()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Main"),
			selenium.getText("//div[@class='top-links-nodes']/a"));
		assertEquals(RuntimeVariables.replace("WikiA NodeA TestA"),
			selenium.getText("//div[@class='top-links-nodes']/a[2]"));
		assertEquals(RuntimeVariables.replace("WikiB NodeB TestB"),
			selenium.getText("//div[@class='top-links-nodes']/a[3]"));
		assertEquals(RuntimeVariables.replace("WikiC NodeC TestC"),
			selenium.getText("//div[@class='top-links-nodes']/a[4]"));
		selenium.clickAt("link=Configuration", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Display Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Main WikiA NodeA TestA WikiB NodeB TestB WikiC NodeC TestC"),
			selenium.getText("_86_currentVisibleNodes"));
		selenium.addSelection("_86_currentVisibleNodes",
			RuntimeVariables.replace("label=Main"));
		selenium.clickAt("//div/table/tbody/tr/td[2]/a[1]/img",
			RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@id='p_p_id_86_']/div/div"));
		assertEquals(RuntimeVariables.replace(
				"WikiA NodeA TestA WikiB NodeB TestB WikiC NodeC TestC"),
			selenium.getText("_86_currentVisibleNodes"));
		assertEquals(RuntimeVariables.replace("Main"),
			selenium.getText("_86_availableVisibleNodes"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WikiA NodeA TestA"),
			selenium.getText("//div[@class='top-links-nodes']/a[1]"));
		assertEquals(RuntimeVariables.replace("WikiB NodeB TestB"),
			selenium.getText("//div[@class='top-links-nodes']/a[2]"));
		assertEquals(RuntimeVariables.replace("WikiC NodeC TestC"),
			selenium.getText("//div[@class='top-links-nodes']/a[3]"));
		assertFalse(selenium.isTextPresent("Main"));
	}
}