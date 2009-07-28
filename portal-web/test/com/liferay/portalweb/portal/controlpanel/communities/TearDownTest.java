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

package com.liferay.portalweb.portal.controlpanel.communities;

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
				selenium.click(RuntimeVariables.replace(
						"link=Back to My Community"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//div[@id='_145_myPlacesContainer']/ul/li[8]/a/span"));
				selenium.waitForPageToLoad("30000");

				boolean PagePresent = selenium.isElementPresent(
						"link=Community LAR Import Test Page");

				if (!PagePresent) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Community LAR Import Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean PortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!PortletPresent) {
					label = 3;

					continue;
				}

				boolean CategoryPresent = selenium.isElementPresent(
						"//td[5]/ul/li/strong");

				if (!CategoryPresent) {
					label = 2;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong");

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

			case 2:
				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));

			case 3:
				selenium.click(RuntimeVariables.replace("link=New Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[2]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//li[@id='_88_tabs3pageTabsId']/a"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");

			case 4:

				boolean MyCommunityPage = selenium.isElementPresent(
						"//div[4]/ul/li[2]/a/span[1]");

				if (!MyCommunityPage) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Control Panel"));
				selenium.waitForPageToLoad("30000");

			case 5:
				selenium.click(RuntimeVariables.replace("link=Users"));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_125_keywords",
					RuntimeVariables.replace("communit"));
				selenium.type("_125_keywords",
					RuntimeVariables.replace("community"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean UserPresent = selenium.isElementPresent("_125_rowIds");

				if (!UserPresent) {
					label = 6;

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

			case 6:
				selenium.click(RuntimeVariables.replace("link=Communities"));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_134_name",
					RuntimeVariables.replace("communit"));
				selenium.type("_134_name", RuntimeVariables.replace("community"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean CommunityAPresent = selenium.isElementPresent(
						"//strong/span");

				if (!CommunityAPresent) {
					label = 7;

					continue;
				}

				selenium.click("//strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 7:

				boolean CommunityBPresent = selenium.isElementPresent(
						"//strong/span");

				if (!CommunityBPresent) {
					label = 8;

					continue;
				}

				selenium.click("//strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 8:
			case 100:
				label = -1;
			}
		}
	}
}