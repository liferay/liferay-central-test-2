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

package com.liferay.portalweb.properties.mailintegration.messageboards.replymessageemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ReplyMessageEmailTest extends BaseTestCase {
	public void testReplyMessageEmail() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				selenium.saveScreenShotAndSource();
				Thread.sleep(60000);

				boolean signedIn = selenium.isElementPresent(
						"//div[4]/div/nobr/a[2]");

				if (!signedIn) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[4]/div/nobr/a[2]",
					RuntimeVariables.replace("Sign Out"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("Email")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("Email",
					RuntimeVariables.replace("liferay.qa.testing.trunk"));
				selenium.saveScreenShotAndSource();
				selenium.type("Passwd",
					RuntimeVariables.replace("loveispatient"));
				selenium.saveScreenShotAndSource();

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("PersistentCookie",
					RuntimeVariables.replace(""));

			case 3:
				selenium.clickAt("signIn", RuntimeVariables.replace("Sign In"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Administrator")
												.equals(selenium.getText(
										"//td[3]/div/span"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Administrator"),
					selenium.getText("//td[3]/div/span"));
				selenium.clickAt("//td[3]/div/span",
					RuntimeVariables.replace("Administrator"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"[MB Category Name] MB Message Subject")
												.equals(selenium.getText(
										"//h1/span[1]"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"),
					selenium.getText("//h1/span[1]"));
				assertTrue(selenium.isPartialText(
						"//div/div[2]/div/div/div[2]/div[5]", "MB Message Body"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div/table/tbody/tr/td[1]/div/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div/table/tbody/tr/td[1]/div/span",
					RuntimeVariables.replace("Reply"));
				Thread.sleep(5000);
				selenium.selectFrame("//iframe[@id='canvas_frame']");
				selenium.selectFrame("//iframe[@class='Am Al editable']");
				selenium.type("//body",
					RuntimeVariables.replace("MB Message Email Reply"));
				selenium.selectFrame("relative=top");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Send"),
					selenium.getText("//div[3]/div/div/div/div[1]/b"));
				selenium.clickAt("//div[3]/div/div/div/div[1]/b",
					RuntimeVariables.replace("Send"));
				Thread.sleep(5000);
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[4]/div/nobr/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[4]/div/nobr/a[2]"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}