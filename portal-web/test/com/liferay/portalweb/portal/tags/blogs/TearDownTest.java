/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.tags.blogs;

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
						if (selenium.isElementPresent(
									"link=Blogs Tags Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Blogs Tags Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean BlogsPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!BlogsPortletPresent) {
					label = 5;

					continue;
				}

				boolean EntryAPresent = selenium.isElementPresent("link=Delete");

				if (!EntryAPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Delete", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:

				boolean EntryBPresent = selenium.isElementPresent("link=Delete");

				if (!EntryBPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Delete", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:

				boolean EntryCPresent = selenium.isElementPresent("link=Delete");

				if (!EntryCPresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("link=Delete", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//img[@alt='Remove']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));

			case 5:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Tags")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Tags", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean Tag1Present = selenium.isElementPresent(
						"link=selenium1 liferay1");

				if (!Tag1Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("link=selenium1 liferay1",
					RuntimeVariables.replace(""));
				Thread.sleep(500);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));

			case 6:
				Thread.sleep(500);

				boolean Tag2Present = selenium.isElementPresent(
						"link=selenium2 liferay2");

				if (!Tag2Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("link=selenium2 liferay2",
					RuntimeVariables.replace(""));
				Thread.sleep(500);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));

			case 7:
				Thread.sleep(500);

				boolean Tag3Present = selenium.isElementPresent(
						"link=selenium3 liferay3");

				if (!Tag3Present) {
					label = 8;

					continue;
				}

				selenium.clickAt("link=selenium3 liferay3",
					RuntimeVariables.replace(""));
				Thread.sleep(500);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));

			case 8:
				Thread.sleep(500);

				boolean Tag4Present = selenium.isElementPresent(
						"link=selenium4 liferay4");

				if (!Tag4Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("link=selenium4 liferay4",
					RuntimeVariables.replace(""));
				Thread.sleep(500);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));

			case 9:

				boolean Tag5Present = selenium.isElementPresent(
						"link=s\u00e9l\u00e9nium1 lif\u00e9ray1");

				if (!Tag5Present) {
					label = 10;

					continue;
				}

				selenium.clickAt("link=s\u00e9l\u00e9nium1 lif\u00e9ray1",
					RuntimeVariables.replace(""));
				Thread.sleep(500);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));

			case 10:
				Thread.sleep(500);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Back to My Community")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Back to My Community",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Home")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Home", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Manage Pages",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@id='_88_layoutsTreeOutput']/ul/li/ul/li[3]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.clickAt("link=Return to Full Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}