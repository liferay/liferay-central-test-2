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

package com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletelivepagestaginglocallivedldocumentdocxdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteLivePageStagingLocalLiveDLDocumentDocxTest
	extends BaseTestCase {
	public void testViewDeleteLivePageStagingLocalLiveDLDocumentDocx()
		throws Exception {
		selenium.open("/web/community-name/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Home")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue live-view controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue staging controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isTextPresent("Document Library Test Page"));
		assertFalse(selenium.isTextPresent("DL Document Title.docx"));
		selenium.clickAt("link=View Staged Page",
			RuntimeVariables.replace("View Staged Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Document Library Test Page",
			RuntimeVariables.replace("Document Library Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue staging controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue live-view controls-visible signed-in public-page dockbar-ready']"));
		assertFalse(selenium.isElementPresent("link=View Live Page"));
		assertEquals(RuntimeVariables.replace("DL Document Title.docx"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("9.6k"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("DL Document Title.docx"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("DL Document Title.docx"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("DL Document Title.docx"),
			selenium.getText("//div[@class='lfr-asset-name']"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//td[1]/a"));
		assertTrue(selenium.isElementPresent("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("9.6k"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("DOCX"),
			selenium.getText("//td[5]/span/a/span"));
	}
}