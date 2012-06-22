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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.mergeorganizationpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditSiteTemplateLayoutsUpdateableTest extends BaseTestCase {
	public void testEditSiteTemplateLayoutsUpdateable()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Site Templates",
					RuntimeVariables.replace("Site Templates"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean layoutsUpdateableChecked = selenium.isChecked(
						"_149_layoutsUpdateableCheckbox");

				if (layoutsUpdateableChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_149_layoutsUpdateableCheckbox']",
					RuntimeVariables.replace(
						"Allow Site Administrators to Modify the Pages Associated with This Site Template Set this to allow site administrators to add, remove, or configure applications and change page properties. If site administrators modify a page, changes to the original site template page are no longer propagated. If this option is set, it is possible to disallow modifications from specific pages through the page management tool."));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_149_layoutsUpdateableCheckbox']"));
				assertEquals(RuntimeVariables.replace(
						"Allow Site Administrators to Modify the Pages Associated with This Site Template Set this to allow site administrators to add, remove, or configure applications and change page properties. If site administrators modify a page, changes to the original site template page are no longer propagated. If this option is set, it is possible to disallow modifications from specific pages through the page management tool."),
					selenium.getText(
						"//label[@for='_149_layoutsUpdateableCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}