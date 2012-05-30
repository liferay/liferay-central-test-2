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

package com.liferay.portalweb.stagingsite.documentlibrary.document.publishtolivenowdldocumentnodatadock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPublishToLiveNowDLDocumentNoDataDockTest extends BaseTestCase {
	public void testViewPublishToLiveNowDLDocumentNoDataDock()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Site Name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Document Library Test Page",
			RuntimeVariables.replace("Document Library Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("DL Document Title"));
		assertTrue(selenium.isPartialText("//li[2]/span/a", "Staging"));
		selenium.clickAt("//li[2]/span/a", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertEquals(RuntimeVariables.replace("DL Document Title"),
			selenium.getText("//a[@class='document-link']"));
		selenium.clickAt("//a[@class='document-link']",
			RuntimeVariables.replace("DL Document Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DL Document Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DL Document Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (12.4k)"),
			selenium.getText("//span[@class='download-document']"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[3]/td[1]"));
		assertTrue(selenium.isElementPresent("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("12.4k"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[3]/td[4]"));
	}
}