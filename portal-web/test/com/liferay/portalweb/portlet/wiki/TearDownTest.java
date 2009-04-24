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

package com.liferay.portalweb.portlet.wiki;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
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
						if (selenium.isElementPresent("link=Wiki Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean SecondWikiPresent = selenium.isElementPresent(
						"link=Second Edited Wiki Test");

				if (!SecondWikiPresent) {
					label = 11;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Second Edited Wiki Test"));
				selenium.waitForPageToLoad("30000");

				boolean FirstPresent = selenium.isElementPresent("link=First");

				if (!FirstPresent) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=First"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:

				boolean LinkMe1Present = selenium.isElementPresent(
						"link=Link Me 1");

				if (!LinkMe1Present) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Link Me 1"));
				selenium.waitForPageToLoad("30000");

				boolean LinkMe1Filled = selenium.isElementPresent(
						"//h1/div/span[2]/a[1]/img");

				if (!LinkMe1Filled) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:
			case 4:

				boolean LinkMe2Present = selenium.isElementPresent(
						"link=Link Me 2");

				if (!LinkMe2Present) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Link Me 2"));
				selenium.waitForPageToLoad("30000");

				boolean LinkMe2Filled = selenium.isElementPresent(
						"//h1/div/span[2]/a[1]/img");

				if (!LinkMe2Filled) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:
			case 6:

				boolean RenameToSecondPresent = selenium.isElementPresent(
						"link=Rename to Second");

				if (!RenameToSecondPresent) {
					label = 7;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Rename to Second"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 7:

				boolean TestPageFormatPresent = selenium.isElementPresent(
						"link=This is Test Page Format");

				if (!TestPageFormatPresent) {
					label = 8;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=This is Test Page Format"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 8:
				selenium.click(RuntimeVariables.replace("link=All Pages"));
				selenium.waitForPageToLoad("30000");

				boolean PageAPresent = selenium.isElementPresent(
						"//td[5]/ul/li/strong");

				if (!PageAPresent) {
					label = 9;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong");
				selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 9:

				boolean PageBPresent = selenium.isElementPresent(
						"//td[5]/ul/li/strong");

				if (!PageBPresent) {
					label = 10;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong");
				selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 10:
				selenium.click(RuntimeVariables.replace("link=Main"));
				selenium.waitForPageToLoad("30000");

			case 11:

				boolean TestChildPagePresent = selenium.isElementPresent(
						"link=Test Child Page");

				if (!TestChildPagePresent) {
					label = 12;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Test Child Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 12:

				boolean TestSecondChildPagePresent = selenium.isElementPresent(
						"link=Test Second Child Page");

				if (!TestSecondChildPagePresent) {
					label = 13;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Test Second Child Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 13:
				selenium.click(RuntimeVariables.replace("link=All Pages"));
				selenium.waitForPageToLoad("30000");

				boolean PageCPresent = selenium.isElementPresent(
						"//td[5]/ul/li/strong/span");

				if (!PageCPresent) {
					label = 14;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong/span");
				selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 14:

				boolean PageDPresent = selenium.isElementPresent(
						"//td[5]/ul/li/strong/span");

				if (!PageDPresent) {
					label = 15;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong/span");
				selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 15:
				selenium.click(RuntimeVariables.replace(
						"//img[@alt='Manage Wikis']"));
				selenium.waitForPageToLoad("30000");

				boolean WikiNodeAPresent = selenium.isElementPresent(
						"//td[4]/ul/li/strong/span");

				if (!WikiNodeAPresent) {
					label = 16;

					continue;
				}

				selenium.click("//td[4]/ul/li/strong/span");
				selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 16:

				boolean WikiNodeBPresent = selenium.isElementPresent(
						"//td[4]/ul/li/strong/span");

				if (!WikiNodeBPresent) {
					label = 17;

					continue;
				}

				selenium.click("//td[4]/ul/li/strong/span");
				selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 17:
				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				selenium.click(RuntimeVariables.replace("//div[2]/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//li[2]/a[2]"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Display Order"));
				selenium.waitForPageToLoad("30000");
				selenium.select("_2_layoutIdsBox",
					RuntimeVariables.replace("label=Wiki Test Page"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//a[3]/img")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//a[3]/img");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Update Display Order']"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}