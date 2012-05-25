/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficehome.notifications.notification.requestccaddasconnection;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class IgnoreNotificationsAddConnnectionTest extends BaseTestCase {
	public void testIgnoreNotificationsAddConnnection()
		throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//li[@id='_145_notificationsMenu']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='title']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Social01 would like to add you as a connection."),
			selenium.getText("//div[@class='title']"));
		assertEquals(RuntimeVariables.replace("Ignore"),
			selenium.getText(
				"//span[@class='lfr-user-action-item lfr-user-action-ignore']/a"));
		selenium.clickAt("//span[@class='lfr-user-action-item lfr-user-action-ignore']/a",
			RuntimeVariables.replace("Ignore"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//li[@id='_145_notificationsMenu']"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		assertFalse(selenium.isTextPresent(
				"Social01 would like to add you as a connection."));
	}
}