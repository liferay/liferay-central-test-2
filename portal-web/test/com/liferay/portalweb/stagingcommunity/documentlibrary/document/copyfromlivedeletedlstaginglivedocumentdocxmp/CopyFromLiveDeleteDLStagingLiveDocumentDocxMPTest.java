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

package com.liferay.portalweb.stagingcommunity.documentlibrary.document.copyfromlivedeletedlstaginglivedocumentdocxmp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CopyFromLiveDeleteDLStagingLiveDocumentDocxMPTest
	extends BaseTestCase {
	public void testCopyFromLiveDeleteDLStagingLiveDocumentDocxMP()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/community-name/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Document Library Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Document Library Test Page",
					RuntimeVariables.replace("Document Library Test Page"));
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
				assertEquals(RuntimeVariables.replace(
						"There are no documents in this folder."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("DL Document Title.docx"));
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div/div[3]/div/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Page"),
					selenium.getText("//div/div[3]/div/ul/li[1]/a"));
				selenium.clickAt("//div/div[3]/div/ul/li[1]/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Copy from Live']",
					RuntimeVariables.replace("Copy from Live"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[2]/div[1]/div/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Options")
												.equals(selenium.getText(
										"//div[2]/div[1]/div/span"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//div[2]/div[1]/div/span"));

				boolean dataDLVisible = selenium.isVisible(
						"_88_PORTLET_DATA_20Checkbox");

				if (dataDLVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_88_PORTLET_DATA_20Checkbox']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}