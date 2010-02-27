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

package com.liferay.portalweb.portal.tags.tagsadmin;

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
						if (selenium.isElementPresent("link=Tags")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Tags", RuntimeVariables.replace(""));
				Thread.sleep(500);

				boolean BluePresent = selenium.isElementPresent("link=blue");

				if (!BluePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=blue", RuntimeVariables.replace(""));
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

			case 2:
				Thread.sleep(500);

				boolean BlueCarPresent = selenium.isElementPresent(
						"link=blue car");

				if (!BlueCarPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=blue car", RuntimeVariables.replace(""));
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

			case 3:
				Thread.sleep(500);

				boolean BlueGreenPresent = selenium.isElementPresent(
						"link=blue green");

				if (!BlueGreenPresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("link=blue green", RuntimeVariables.replace(""));
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

			case 4:
				Thread.sleep(500);

				boolean GreenPresent = selenium.isElementPresent("link=green");

				if (!GreenPresent) {
					label = 5;

					continue;
				}

				selenium.clickAt("link=green", RuntimeVariables.replace(""));
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

			case 5:
				Thread.sleep(500);

				boolean GreenTreePresent = selenium.isElementPresent(
						"link=green tree");

				if (!GreenTreePresent) {
					label = 6;

					continue;
				}

				selenium.clickAt("link=green tree", RuntimeVariables.replace(""));
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

				boolean SeleniumPresent = selenium.isElementPresent(
						"link=selenium");

				if (!SeleniumPresent) {
					label = 7;

					continue;
				}

				selenium.clickAt("link=selenium", RuntimeVariables.replace(""));
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

				boolean SeleniumIDEPresent = selenium.isElementPresent(
						"link=selenium ide");

				if (!SeleniumIDEPresent) {
					label = 8;

					continue;
				}

				selenium.clickAt("link=selenium ide",
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
			case 100:
				label = -1;
			}
		}
	}
}