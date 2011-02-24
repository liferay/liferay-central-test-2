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
public class TearDownGmailServerTest extends BaseTestCase {
	public void testTearDownGmailServer() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				Thread.sleep(5000);

				boolean signedIn1 = selenium.isElementPresent(
						"//div[2]/div/nobr/a[2]");

				if (!signedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//div[2]/div/nobr/a[2]"));
				selenium.clickAt("//div[2]/div/nobr/a[2]",
					RuntimeVariables.replace("Sign out"));

			case 2:

				boolean signedIn2 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn2) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 3:

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
					RuntimeVariables.replace("liferay.qa.server.trunk"));
				selenium.saveScreenShotAndSource();
				selenium.type("Passwd",
					RuntimeVariables.replace("loveispatient"));
				selenium.saveScreenShotAndSource();

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("PersistentCookie",
					RuntimeVariables.replace(""));

			case 4:
				selenium.clickAt("signIn", RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@type='checkbox']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@type='checkbox']",
					RuntimeVariables.replace("All"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div[3]"));
				selenium.clickAt("//div/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div[3]",
					RuntimeVariables.replace("Delete"));
				Thread.sleep(5000);
				Thread.sleep(5000);

				boolean signedIn3 = selenium.isElementPresent(
						"//div[2]/div/nobr/a[2]");

				if (!signedIn3) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//div[2]/div/nobr/a[2]"));
				selenium.clickAt("//div[2]/div/nobr/a[2]",
					RuntimeVariables.replace("Sign out"));

			case 5:

				boolean signedIn4 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn4) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 6:
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