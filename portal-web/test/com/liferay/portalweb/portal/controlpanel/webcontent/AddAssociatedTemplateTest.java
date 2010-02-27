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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddAssociatedTemplateTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddAssociatedTemplateTest extends BaseTestCase {
	public void testAddAssociatedTemplate() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Web Content")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Templates", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Template']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_15_newTemplateId",
					RuntimeVariables.replace("testb"));
				selenium.type("_15_newTemplateId",
					RuntimeVariables.replace("testb"));
				selenium.type("_15_name",
					RuntimeVariables.replace("Test Web Content TemplateB"));
				selenium.type("_15_description",
					RuntimeVariables.replace(
						"This is a test web content templateB!"));
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace(""));
				selenium.waitForPopUp("structure",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=structure");
				Thread.sleep(5000);

				boolean TemplateRestoredA = selenium.isElementPresent(
						"link=TEST");

				if (TemplateRestoredA) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 2:

				boolean TemplateRestoredB = selenium.isElementPresent(
						"link=TEST");

				if (!TemplateRestoredB) {
					label = 3;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=TEST")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("link=TEST");
				selenium.selectWindow("null");

			case 3:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Test Web Content Structure")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_15_xsl",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\controlpanel\\webcontent\\dependencies\\Template.htm"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				assertTrue(selenium.isTextPresent("Test Web Content TemplateB"));

			case 100:
				label = -1;
			}
		}
	}
}