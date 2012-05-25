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

package com.liferay.portalweb.socialofficehome.events.event.configureportletdisplaydays2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEventEDDisplayDays1Test extends BaseTestCase {
	public void testViewEventEDDisplayDays1() throws Exception {
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
		assertEquals(RuntimeVariables.replace("There are no more events today."),
			selenium.getText("//div[2]/div/div[2]/div/section/div/div/div"));
		assertFalse(selenium.isElementPresent("//h2[contains(.,'Events')]"));
		assertFalse(selenium.isElementPresent("//span[@class='event-name']/a"));
		assertFalse(selenium.isTextPresent("Calendar Event Title"));
	}
}