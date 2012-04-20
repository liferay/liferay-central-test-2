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

package com.liferay.portalweb.portlet.messageboards.message.gmailviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Gmail_ViewMBThreadMessageGmailMailingListTest extends BaseTestCase {
	public void testGmail_ViewMBThreadMessageGmailMailingList()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				Thread.sleep(80000);

				boolean usernamePresent = selenium.isElementPresent(
						"//input[@id='Email']");

				if (usernamePresent) {
					label = 4;

					continue;
				}

				boolean signedIn1 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 2:
				Thread.sleep(5000);

				boolean signInToGmailPresent = selenium.isTextPresent(
						"Sign in to Gmail");

				if (!signInToGmailPresent) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign in to Gmail"),
					selenium.getText("//a[@id='button']"));
				selenium.clickAt("//a[@id='button']",
					RuntimeVariables.replace("Sign in to Gmail"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 3:
			case 4:

				boolean signInAsADifferentUserPresent = selenium.isElementPresent(
						"//a[@id='link-force-reauth']");

				if (!signInAsADifferentUserPresent) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"Sign out and sign in as a different user"),
					selenium.getText("//a[@id='link-force-reauth']"));
				selenium.clickAt("//a[@id='link-force-reauth']",
					RuntimeVariables.replace(
						"Sign out and sign in as a different user"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 5:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='Email']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='Email']",
					RuntimeVariables.replace("liferay.qa.testing.trunk"));
				selenium.type("//input[@id='Passwd']",
					RuntimeVariables.replace("loveispatient"));

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='PersistentCookie']",
					RuntimeVariables.replace("Stay signed in"));

			case 6:
				assertTrue(selenium.isChecked("//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='signIn']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				Thread.sleep(10000);
				selenium.openWindow("http://groups.google.com/",
					RuntimeVariables.replace("Google Groups"));
				selenium.waitForPopUp("Google Groups",
					RuntimeVariables.replace(""));
				selenium.selectWindow("Google Groups");
				Thread.sleep(10000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//td/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("My groups"),
					selenium.getText("//td/a/span"));
				selenium.click("//td/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[@href='#!forum/liferay-mailinglist']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectWindow("name=Google Groups");
				selenium.clickAt("link=liferay-mailinglist",
					RuntimeVariables.replace("liferay-mailinglist"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[3]/div/div[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isElementPresent("//div[3]/div/div[2]/a"));
				selenium.click("//div[3]/div/div[2]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText(
									"//td[2]/span/span[contains(.,'Liferay QA')]",
									"Liferay QA")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isPartialText(
						"//td[2]/span/span[contains(.,'Liferay QA')]",
						"Liferay QA"));
				assertTrue(selenium.isPartialText(
						"//div[contains(.,'MB Message Body')]",
						"MB Message Body"));
				assertEquals(RuntimeVariables.replace("me"),
					selenium.getText("//td[2]/span/span[.='me']"));
				assertTrue(selenium.isPartialText(
						"//div[contains(.,'MB Message Email Reply')]",
						"MB Message Email Reply"));

				boolean SignedIn2 = selenium.isElementPresent("link=Sign out");

				if (!SignedIn2) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 7:
				Thread.sleep(10000);
				selenium.close();
				selenium.selectWindow("null");

			case 100:
				label = -1;
			}
		}
	}
}