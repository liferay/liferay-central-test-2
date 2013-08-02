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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteAdmin_RestoreGuestViewImageTest extends BaseTestCase {
	public void testSiteAdmin_RestoreGuestViewImage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Media Gallery Test Page",
					RuntimeVariables.replace("Media Gallery Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Media Gallery Permissions Test Folder 2 Edited"),
					selenium.getText(
						"//a[@title='Media Gallery Permissions Test Folder 2 Edited - ']"));
				selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder 2 Edited - ']",
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Folder 2 Edited"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Media Gallery Permissions Test Subfolder 2"),
					selenium.getText(
						"//a[@title='Media Gallery Permissions Test Subfolder 2 - ']"));
				selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder 2 - ']",
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Subfolder 2"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Permissions Image Test Edited"),
					selenium.getText(
						"//a[@title='Permissions Image Test Edited - ']"));
				selenium.clickAt("//a[@title='Permissions Image Test Edited - ']",
					RuntimeVariables.replace("Permissions Image Test Edited"));
				Thread.sleep(5000);
				selenium.waitForVisible("//img[@title='Permissions']");
				selenium.clickAt("//img[@title='Permissions']",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible("//input[@name='16_ACTION_VIEW']");

				boolean actionViewCheckbox = selenium.isChecked(
						"//input[@name='16_ACTION_VIEW']");

				if (actionViewCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='16_ACTION_VIEW']",
					RuntimeVariables.replace("Action View Checkbox"));

			case 2:
				assertTrue(selenium.isChecked("//input[@name='16_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}