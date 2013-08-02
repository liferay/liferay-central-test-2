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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.portletpermissions.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemovePortletPermissionsGuestViewTest extends BaseTestCase {
	public void testRemovePortletPermissionsGuestView()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home");
				selenium.waitForVisible("link=Blogs Portlet Permissions Page");
				selenium.clickAt("link=Blogs Portlet Permissions Page",
					RuntimeVariables.replace("Blogs Portlet Permissions Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				selenium.waitForVisible(
					"//iframe[contains(@id,'configurationIframe')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframe')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/hudcrumbs.js')]");
				selenium.waitForVisible(
					"//span[@class='aui-tab-content']/a[contains(.,'Permissions')]");
				selenium.clickAt("//span[@class='aui-tab-content']/a[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean guestViewChecked = selenium.isChecked(
						"//input[@name='16_ACTION_VIEW']");

				if (!guestViewChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='16_ACTION_VIEW']",
					RuntimeVariables.replace("Guest View"));

			case 2:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@name='16_ACTION_VIEW']"));

			case 100:
				label = -1;
			}
		}
	}
}