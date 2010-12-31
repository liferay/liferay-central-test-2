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

package com.liferay.portalweb.portal.dbupgrade.sampledata512.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress2MyAccountTest extends BaseTestCase {
	public void testAddAddress2MyAccount() throws Exception {
		selenium.open("/web/guest/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=My Account")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Addresses", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[7]/div[1]/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[7]/div[1]/input", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_2_street1", RuntimeVariables.replace("123 Lets"));
		selenium.saveScreenShotAndSource();
		selenium.select("_2_countryId", RuntimeVariables.replace("label=Canada"));
		selenium.type("_2_street2", RuntimeVariables.replace("897 Hope"));
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"AlbertaBritish ColumbiaManitobaNew BrunswickNewfoundland and LabradorNorthwest TerritoriesNova ScotiaNunavutOntarioPrince Edward IslandQuebecSaskatchewanYukon")
										.equals(selenium.getText("_2_regionId"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.select("_2_regionId", RuntimeVariables.replace("label=Ontario"));
		selenium.type("_2_street3", RuntimeVariables.replace("7896 This"));
		selenium.saveScreenShotAndSource();
		selenium.select("_2_typeId", RuntimeVariables.replace("label=Other"));
		selenium.type("_2_city", RuntimeVariables.replace("Works"));
		selenium.saveScreenShotAndSource();
		selenium.type("_2_zip", RuntimeVariables.replace("00000"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[2]/div/div/div"));
		assertEquals(RuntimeVariables.replace("123 Lets\n897 Hope\n7896 This"),
			selenium.getText("//div[7]/div[2]/table/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Works"),
			selenium.getText("//div[2]/table/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("00000"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Other"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//tr[3]/td[6]"));
	}
}