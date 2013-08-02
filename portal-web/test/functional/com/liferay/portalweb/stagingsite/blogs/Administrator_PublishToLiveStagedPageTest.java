/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Administrator_PublishToLiveStagedPageTest extends BaseTestCase {
	public void testAdministrator_PublishToLiveStagedPage()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				assertTrue(selenium.isElementPresent(
						"//div[@class='staging-bar']"));
				assertEquals(RuntimeVariables.replace("Live"),
					selenium.getText("//span[contains(.,'Live')]"));
				assertEquals(RuntimeVariables.replace(
						"You are viewing the live version of Liferay and cannot make changes here. Make your changes in staging and publish them to Live afterwards to make them public."),
					selenium.getText("//span[@class='staging-live-help']"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText("//span/a[contains(.,'Staging')]"));
				selenium.clickAt("//span/a[contains(.,'Staging')]",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You are viewing the staged version of Liferay. You can make changes here and publish them to Live afterwards to make them public."),
					selenium.getText("//span[@class='staging-live-help']"));
				selenium.waitForVisible("link=Blogs Test Page");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible("//section"));
				Thread.sleep(5000);
				selenium.clickAt("//span[contains(.,'Staging')]/span/span/ul",
					RuntimeVariables.replace("Staging Drop Down"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Publish to Live Now')]");
				assertEquals(RuntimeVariables.replace("Publish to Live Now"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Publish to Live Now')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Publish to Live Now')]",
					RuntimeVariables.replace("Publish to Live Now"));
				Thread.sleep(5000);
				selenium.waitForVisible("//div[2]/div[1]/a");

				boolean blogsVisible = selenium.isVisible(
						"//input[@id='_88_PORTLET_DATA_33Checkbox']");

				if (blogsVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 2:
				selenium.waitForVisible("//input[@id='_88_rangeAll']");
				selenium.clickAt("//input[@id='_88_rangeAll']",
					RuntimeVariables.replace("All"));

				boolean blogsChecked = selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_33Checkbox']");

				if (blogsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_88_PORTLET_DATA_33Checkbox']",
					RuntimeVariables.replace("Blogs"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_33Checkbox']"));
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@value='Publish']");
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}