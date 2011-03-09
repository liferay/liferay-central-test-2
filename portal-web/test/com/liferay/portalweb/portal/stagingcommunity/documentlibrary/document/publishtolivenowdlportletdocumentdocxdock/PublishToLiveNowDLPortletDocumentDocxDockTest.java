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

package com.liferay.portalweb.portal.stagingcommunity.documentlibrary.document.publishtolivenowdlportletdocumentdocxdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveNowDLPortletDocumentDocxDockTest extends BaseTestCase {
	public void testPublishToLiveNowDLPortletDocumentDocxDock()
		throws Exception {
		selenium.open("/web/community-name/public-page/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Public Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Public Page",
			RuntimeVariables.replace("Public Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue live-view controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue staging controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent("//section"));
		assertFalse(selenium.isTextPresent("DL Document Title.docx"));
		selenium.open("/web/community-name-staging/public-page/");
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue staging controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue live-view controls-visible signed-in public-page dockbar-ready']"));
		assertEquals(RuntimeVariables.replace("DL Document Title.docx"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("9.6k"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("link=Publish to Live Now",
			RuntimeVariables.replace("Publish to Live Now"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@value='Publish']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace("//input[@value='Publish']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		selenium.open("/web/community-name/public-page/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Public Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Public Page",
			RuntimeVariables.replace("Public Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue live-view controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue staging controls-visible signed-in public-page dockbar-ready']"));
		assertEquals(RuntimeVariables.replace("DL Document Title.docx"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("9.6k"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//td[4]/a"));
	}
}