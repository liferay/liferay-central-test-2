/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portal.controlpanel.portal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Users"));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_125_keywords",
					RuntimeVariables.replace("selenium"));
				selenium.type("_125_keywords",
					RuntimeVariables.replace("selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean UserPresent = selenium.isElementPresent("_125_rowIds");

				if (!UserPresent) {
					label = 2;

					continue;
				}

				selenium.click("_125_allRowIds");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.click("link=Advanced \u00bb");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_125_active")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_125_active",
					RuntimeVariables.replace("label=No"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");
				selenium.click("_125_allRowIds");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.click("link=\u00ab Basic");

			case 2:
				selenium.click(RuntimeVariables.replace("link=Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_126_keywords",
					RuntimeVariables.replace("selenium"));
				selenium.type("_126_keywords",
					RuntimeVariables.replace("selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean OrganizationPresent = selenium.isElementPresent(
						"_126_rowIds");

				if (!OrganizationPresent) {
					label = 3;

					continue;
				}

				selenium.click("_126_allRowIds");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:
				selenium.click(RuntimeVariables.replace("link=User Groups"));
				selenium.waitForPageToLoad("30000");

				boolean UserGroupPresent = selenium.isElementPresent(
						"_127_rowIds");

				if (!UserGroupPresent) {
					label = 4;

					continue;
				}

				selenium.click("_127_allRowIds");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:
				selenium.click(RuntimeVariables.replace(
						"link=Password Policies"));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_129_name", RuntimeVariables.replace("Test"));
				selenium.type("_129_name", RuntimeVariables.replace("Test"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean PasswordPolicyPresent = selenium.isElementPresent(
						"//strong/span");

				if (!PasswordPolicyPresent) {
					label = 5;

					continue;
				}

				selenium.click("//strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:
				selenium.click(RuntimeVariables.replace("link=Settings"));
				selenium.waitForPageToLoad("30000");
				selenium.type("_130_legalId", RuntimeVariables.replace(""));
				selenium.type("_130_sicCode", RuntimeVariables.replace(""));
				selenium.type("_130_tickerSymbol", RuntimeVariables.replace(""));
				selenium.type("_130_industry", RuntimeVariables.replace(""));
				selenium.type("_130_type", RuntimeVariables.replace(""));
				selenium.select("_130_timeZoneId",
					RuntimeVariables.replace(
						"label=(UTC ) Coordinated Universal Time"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				selenium.click("additionalEmailAddressesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='additionalEmailAddresses']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='additionalEmailAddresses']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (1)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("addressesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='addresses']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='addresses']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (2)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("websitesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='websites']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='websites']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (3)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("phoneNumbersLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='phoneNumbers']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='phoneNumbers']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (4)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				selenium.click("additionalEmailAddressesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='additionalEmailAddresses']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='additionalEmailAddresses']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (1)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("addressesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='addresses']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='addresses']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (2)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("websitesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='websites']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='websites']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (3)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("phoneNumbersLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='phoneNumbers']/fieldset/div[2]/div/span/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//div[@id='phoneNumbers']/fieldset/div[2]/div/span/a[2]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Undo (4)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				selenium.click("usersLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Reserved Credentials")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("link=Reserved Credentials");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"_130_settings(admin.reserved.screen.names)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_130_settings(admin.reserved.screen.names)",
					RuntimeVariables.replace(""));
				selenium.type("_130_settings(admin.reserved.email.addresses)",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("mailHostNamesLink")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("mailHostNamesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"_130_settings(admin.mail.host.names)")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_130_settings(admin.mail.host.names)",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}