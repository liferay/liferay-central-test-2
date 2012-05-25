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

package com.liferay.portalweb.socialofficehome.events.event.viewdeleteevented;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEventEDTest extends BaseTestCase {
	public void testViewEventED() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Events")
										.equals(selenium.getText(
								"xPath=(//span[@class='portlet-title-text'])[4]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		assertTrue(selenium.isPartialText("//h2[contains(.,'Events')]", "Events"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//span[@class='event-name']/a"));
		selenium.clickAt("//span[@class='event-name']/a",
			RuntimeVariables.replace("Calendar Event Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals("Add Event",
			selenium.getValue("//input[@value='Add Event']"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//div[@class='event-title']"));
	}
}