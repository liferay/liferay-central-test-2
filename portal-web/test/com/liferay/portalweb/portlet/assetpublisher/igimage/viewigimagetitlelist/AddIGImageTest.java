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

package com.liferay.portalweb.portlet.assetpublisher.igimage.viewigimagetitlelist;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddIGImageTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddIGImageTest extends BaseTestCase {
	public void testAddIGImage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Asset Publisher Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Asset Publisher Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select",
					RuntimeVariables.replace("label=Image Gallery Image"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace(""));
				selenium.waitForPopUp("folder",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=folder");
				Thread.sleep(5000);

				boolean choose1Present = selenium.isElementPresent(
						"//td[4]/input");

				if (choose1Present) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 2:

				boolean choose2Present = selenium.isElementPresent(
						"//td[4]/input");

				if (!choose2Present) {
					label = 3;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Choose']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Choose']");

			case 3:
				selenium.selectWindow("null");
				Thread.sleep(5000);
				selenium.type("_31_file",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\assetpublisher\\igimage\\viewigimagetitlelist\\dependencies\\AP_test_image.jpg"));
				selenium.type("_31_name",
					RuntimeVariables.replace("AP IG Image Name"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("AP IG Image Name")
												.equals(selenium.getText(
										"//h3/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("AP IG Image Name"),
					selenium.getText("//h3/a"));
				assertTrue(selenium.isElementPresent("//div[1]/a/img"));

			case 100:
				label = -1;
			}
		}
	}
}