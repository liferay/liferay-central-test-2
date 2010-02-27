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

package com.liferay.portalweb.portal.permissions.documentlibrary.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Portlet_AddShortcutTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Portlet_AddShortcutTest extends BaseTestCase {
	public void testPortlet_AddShortcut() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Document Library Permissions Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Document Library Permissions Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Portlet2 Temporary2 Folder2",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//li[4]/span/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Add Shortcut']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//li[5]/span/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace(""));
				selenium.waitForPopUp("toGroup",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=toGroup");
				Thread.sleep(5000);

				boolean CommunityPresent1 = selenium.isElementPresent(
						"link=My Community");

				if (CommunityPresent1) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 2:

				boolean CommunityPresent2 = selenium.isElementPresent(
						"link=My Community");

				if (!CommunityPresent2) {
					label = 3;

					continue;
				}

				selenium.click("link=My Community");
				selenium.selectWindow("null");

			case 3:
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("My Community"),
					selenium.getText("_20_toGroupName"));
				selenium.clickAt("_20_selectToFileEntryButton",
					RuntimeVariables.replace(""));
				selenium.waitForPopUp("toGroup",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=toGroup");
				Thread.sleep(5000);
				selenium.clickAt("link=Document Home",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean FolderPresent1 = selenium.isElementPresent(
						"link=My1 Community1 Folder1");

				if (FolderPresent1) {
					label = 4;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 4:

				boolean FolderPresent2 = selenium.isElementPresent(
						"link=My1 Community1 Folder1");

				if (!FolderPresent2) {
					label = 7;

					continue;
				}

				selenium.clickAt("link=My1 Community1 Folder1",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean DocumentPresent1 = selenium.isElementPresent(
						"link=My1 Community1 Document1.txt");
				Thread.sleep(5000);

				if (DocumentPresent1) {
					label = 5;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 5:

				boolean DocumentPresent2 = selenium.isElementPresent(
						"link=My1 Community1 Document1.txt");

				if (!DocumentPresent2) {
					label = 6;

					continue;
				}

				selenium.click("link=My1 Community1 Document1.txt");
				selenium.selectWindow("null");

			case 6:
			case 7:
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace(
						"My1 Community1 Document1.txt"),
					selenium.getText("_20_toFileEntryTitle"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				assertTrue(selenium.isElementPresent(
						"link=My1 Community1 Document1.txt"));

			case 100:
				label = -1;
			}
		}
	}
}