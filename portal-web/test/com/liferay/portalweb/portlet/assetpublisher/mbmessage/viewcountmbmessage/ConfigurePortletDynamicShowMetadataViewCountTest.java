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

package com.liferay.portalweb.portlet.assetpublisher.mbmessage.viewcountmbmessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurePortletDynamicShowMetadataViewCountTest.java.html"><b>
 * <i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDynamicShowMetadataViewCountTest
	extends BaseTestCase {
	public void testConfigurePortletDynamicShowMetadataViewCount()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Asset Publisher Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Configuration", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_86_availableMetadataFields")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.addSelection("_86_availableMetadataFields",
			RuntimeVariables.replace("label=View Count"));
		selenium.clickAt("//fieldset[2]/div/div/table/tbody/tr/td[2]/a[2]/img",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("View Count")
										.equals(selenium.getText(
								"//fieldset[2]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[1]/select/option"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("View Count"),
			selenium.getText(
				"//fieldset[2]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[1]/select/option"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[3]/div/div/div/div/div"));
		assertEquals(RuntimeVariables.replace("View Count"),
			selenium.getText(
				"//fieldset[2]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[1]/select/option"));
	}
}