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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CA_AddWCDArticleTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CA_AddWCDArticleTest extends BaseTestCase {
	public void testCA_AddWCDArticle() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				boolean InControlPanel = selenium.isElementPresent(
						"link=Back to Guest");

				if (!InControlPanel) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Back to Guest",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 2:
				selenium.clickAt("link=Web Content Display Permissions Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//img[@alt='Add Web Content']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_15_title",
					RuntimeVariables.replace("CA Permissions WCD Article"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_15_editor")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("FCKeditor1___Frame")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//textarea")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame("//iframe[@id=\"_15_editor\"]");
				selenium.selectFrame("//iframe[@id=\"FCKeditor1___Frame\"]");
				selenium.selectFrame("//iframe");
				selenium.type("//body",
					RuntimeVariables.replace(
						"This is a ca permissions wcd article!"));
				selenium.selectFrame("relative=top");
				selenium.clickAt("//input[@value='Save and Approve']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"This is a ca permissions wcd article!"));

			case 100:
				label = -1;
			}
		}
	}
}