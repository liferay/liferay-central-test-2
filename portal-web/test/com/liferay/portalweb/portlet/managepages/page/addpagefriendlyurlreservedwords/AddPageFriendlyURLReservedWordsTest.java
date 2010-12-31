/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.managepages.page.addpagefriendlyurlreservedwords;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageFriendlyURLReservedWordsTest extends BaseTestCase {
	public void testAddPageFriendlyURLReservedWords() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div/div[3]/div/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div/div[3]/div/ul/li[1]/a",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Liferay")
												.equals(selenium.getText(
										"//div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/ul/li/div/div[1]",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("_88_friendlyURL", RuntimeVariables.replace("/c"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword c."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/combo"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword combo."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/delegate"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword delegate."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/display_chart"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword display_chart."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/dtd"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword dtd."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/facebook"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Facebook."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/google_gadget"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword google_gadget."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/group"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Group."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/html"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword HTML."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/image"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Image."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/language"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Language."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/netvibes"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Netvibes."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/page"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Page."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/pbhs"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword pbhs."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/poller"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword poller."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/public"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Public."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/private"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Private."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/rss"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword RSS."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/sharepoint"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword sharepoint."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/sitemap.xml"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword sitemap.xml."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/tags"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword Tags."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/software_catalog"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword software_catalog."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/_vti_"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword _vti_."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/wap"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword wap."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/web"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword web."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/widget"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword widget."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/wsrp"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword wsrp."),
					selenium.getText("//td[2]/div[2]"));
				selenium.type("_88_friendlyURL",
					RuntimeVariables.replace("/xmlrpc"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"You have entered invalid data. Please try again."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a friendly url that does not conflict with the keyword xmlrpc."),
					selenium.getText("//td[2]/div[2]"));

			case 100:
				label = -1;
			}
		}
	}
}