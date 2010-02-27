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

package com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.removebookmarksentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurePortletAssetSelectionManualTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAssetSelectionManualTest extends BaseTestCase {
	public void testConfigurePortletAssetSelectionManual()
		throws Exception {
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

		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Configuration", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_86_selectionStyle",
			RuntimeVariables.replace("label=Manual"));
		Thread.sleep(5000);
		assertEquals("Manual", selenium.getSelectedLabel("_86_selectionStyle"));
		assertEquals(RuntimeVariables.replace("Selection"),
			selenium.getText(
				"//div[@id='assetPublisherSelection']/div[1]/div/span"));
		assertEquals(RuntimeVariables.replace("Display Settings"),
			selenium.getText(
				"//div[@id='assetPublisherDisplaySettings']/div[1]/div/span"));
	}
}