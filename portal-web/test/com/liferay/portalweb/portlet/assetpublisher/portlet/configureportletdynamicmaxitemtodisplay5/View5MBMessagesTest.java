/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicmaxitemtodisplay5;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class View5MBMessagesTest extends BaseTestCase {
	public void testView5MBMessages() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Asset Publisher Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("AP6 MB6 Message6 Subject6"),
			selenium.getText("//div[1]/h3/a"));
		assertEquals(RuntimeVariables.replace("AP5 MB5 Message5 Subject5"),
			selenium.getText("//div[2]/h3/a"));
		assertEquals(RuntimeVariables.replace("AP4 MB4 Message4 Subject4"),
			selenium.getText("//div[3]/h3/a"));
		assertEquals(RuntimeVariables.replace("AP3 MB3 Message3 Subject3"),
			selenium.getText("//div[4]/h3/a"));
		assertEquals(RuntimeVariables.replace("AP2 MB2 Message2 Subject2"),
			selenium.getText("//div[5]/h3/a"));
		assertFalse(selenium.isElementPresent("//div[6]/h3/a"));
		assertFalse(selenium.isTextPresent("AP1 MB1 Message1 Subject1"));
	}
}