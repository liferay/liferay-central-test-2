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

package com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletdocumentsperpage5;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMPortletDocumntsPerPage5Test extends BaseTestCase {
	public void testViewDMPortletDocumntsPerPage5() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Name")
										.equals(selenium.getText(
								"//li[@class='folder selected']/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[@class='folder selected']/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[6]"));
	}
}