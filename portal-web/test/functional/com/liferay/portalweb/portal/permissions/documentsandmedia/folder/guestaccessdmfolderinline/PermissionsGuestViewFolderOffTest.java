/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.documentsandmedia.folder.guestaccessdmfolderinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsGuestViewFolderOffTest extends BaseTestCase {
	public void testPermissionsGuestViewFolderOff() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
				selenium.clickAt("xPath=(//span[contains(@class,'document-action')])[2]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Guest"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("View"),
					selenium.getText("//tr[1]/th[9]"));

				boolean viewFolderPermissionsChecked = selenium.isChecked(
						"//tr[3]/td[9]/input");

				if (!viewFolderPermissionsChecked) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isChecked("//tr[3]/td[9]/input"));
				selenium.clickAt("//tr[3]/td[9]/input",
					RuntimeVariables.replace("View Folder Permission"));

			case 2:
				assertFalse(selenium.isChecked("//tr[3]/td[9]/input"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked("//tr[3]/td[9]/input"));

			case 100:
				label = -1;
			}
		}
	}
}