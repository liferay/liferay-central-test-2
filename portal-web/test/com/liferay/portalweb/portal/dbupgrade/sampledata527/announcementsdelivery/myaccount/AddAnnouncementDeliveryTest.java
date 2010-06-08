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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddAnnouncementDeliveryTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddAnnouncementDeliveryTest extends BaseTestCase {
	public void testAddAnnouncementDelivery() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home");
				selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div[4]/ul/li[1]/a",
					RuntimeVariables.replace(""));

				boolean generalEmailChecked = selenium.isChecked(
						"//td[2]/input[2]");

				if (generalEmailChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[2]/input[2]",
					RuntimeVariables.replace(""));

			case 2:

				boolean newsEmailChecked = selenium.isChecked(
						"//tr[4]/td[2]/input[2]");

				if (newsEmailChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//tr[4]/td[2]/input[2]",
					RuntimeVariables.replace(""));

			case 3:

				boolean testEmailChecked = selenium.isChecked(
						"//tr[5]/td[2]/input[2]");

				if (testEmailChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//tr[5]/td[2]/input[2]",
					RuntimeVariables.replace(""));

			case 4:

				boolean generalSmsChecked = selenium.isChecked(
						"//td[3]/input[2]");

				if (generalSmsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[3]/input[2]",
					RuntimeVariables.replace(""));

			case 5:

				boolean newsSmsChecked = selenium.isChecked(
						"//tr[4]/td[3]/input[2]");

				if (newsSmsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//tr[4]/td[3]/input[2]",
					RuntimeVariables.replace(""));

			case 6:

				boolean testSmsChecked = selenium.isChecked(
						"//tr[5]/td[3]/input[2]");

				if (testSmsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//tr[5]/td[3]/input[2]",
					RuntimeVariables.replace(""));

			case 7:

				boolean generalWebsiteChecked = selenium.isChecked(
						"//td[4]/input[2]");

				if (generalWebsiteChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//td[4]/input[2]",
					RuntimeVariables.replace(""));

			case 8:

				boolean newsWebsiteChecked = selenium.isChecked(
						"//tr[4]/td[4]/input[2]");

				if (newsWebsiteChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//tr[4]/td[4]/input[2]",
					RuntimeVariables.replace(""));

			case 9:

				boolean testWebsiteChecked = selenium.isChecked(
						"//tr[5]/td[4]/input[2]");

				if (testWebsiteChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//tr[5]/td[4]/input[2]",
					RuntimeVariables.replace(""));

			case 10:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//div[2]/div/div/div"));
				assertTrue(selenium.isChecked("//td[2]/input[2]"));
				assertTrue(selenium.isChecked("//td[3]/input[2]"));
				assertTrue(selenium.isChecked("//tr[4]/td[2]/input[2]"));
				assertTrue(selenium.isChecked("//tr[5]/td[2]/input[2]"));
				assertTrue(selenium.isChecked("//tr[4]/td[3]/input[2]"));
				assertTrue(selenium.isChecked("//tr[5]/td[3]/input[2]"));
				assertTrue(selenium.isChecked("//td[4]/input[2]"));
				assertTrue(selenium.isChecked("//tr[4]/td[4]/input[2]"));
				assertTrue(selenium.isChecked("//tr[5]/td[4]/input[2]"));

			case 100:
				label = -1;
			}
		}
	}
}