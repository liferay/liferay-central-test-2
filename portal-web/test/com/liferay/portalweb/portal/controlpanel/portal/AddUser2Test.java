/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.portal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddUser2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddUser2Test extends BaseTestCase {
	public void testAddUser2() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Users")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_125_prefixId", RuntimeVariables.replace("label=Mrs."));
		selenium.typeKeys("_125_screenName",
			RuntimeVariables.replace("selenium02"));
		selenium.type("_125_screenName", RuntimeVariables.replace("selenium02"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("test02@selenium.com"));
		selenium.type("_125_firstName", RuntimeVariables.replace("selen02"));
		selenium.type("_125_middleName", RuntimeVariables.replace("lenn"));
		selenium.type("_125_lastName", RuntimeVariables.replace("nium02"));
		selenium.select("_125_suffixId", RuntimeVariables.replace("label=PhD."));
		selenium.select("_125_birthdayMonth",
			RuntimeVariables.replace("label=September"));
		selenium.select("_125_birthdayDay", RuntimeVariables.replace("label=24"));
		selenium.select("_125_birthdayYear",
			RuntimeVariables.replace("label=1984"));
		selenium.select("_125_male", RuntimeVariables.replace("label=Female"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.clickAt("passwordLink", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_125_password1")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_125_password1", RuntimeVariables.replace("test"));
		selenium.type("_125_password1", RuntimeVariables.replace("test"));
		selenium.type("_125_password2", RuntimeVariables.replace("test"));
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.type("_125_addressStreet1_0",
			RuntimeVariables.replace("123 Liferay Ln."));
		selenium.select("_125_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_125_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.type("_125_addressZip0", RuntimeVariables.replace("91234"));
		selenium.type("_125_addressCity0",
			RuntimeVariables.replace("Ray of Light"));
		selenium.clickAt("_125_addressPrimary0", RuntimeVariables.replace(""));
		selenium.clickAt("phoneNumbersLink", RuntimeVariables.replace(""));
		selenium.type("_125_phoneNumber0",
			RuntimeVariables.replace("123-123-1234"));
		selenium.type("_125_phoneExtension0", RuntimeVariables.replace("123"));
		selenium.clickAt("_125_phonePrimary0", RuntimeVariables.replace(""));
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.type("_125_emailAddressAddress0",
			RuntimeVariables.replace("test02@selenium.com"));
		selenium.clickAt("_125_emailAddressPrimary0",
			RuntimeVariables.replace(""));
		selenium.clickAt("websitesLink", RuntimeVariables.replace(""));
		selenium.type("_125_websiteUrl0",
			RuntimeVariables.replace("http://www.selenium02.com"));
		selenium.select("_125_websiteTypeId0",
			RuntimeVariables.replace("label=Personal"));
		selenium.clickAt("_125_websitePrimary0", RuntimeVariables.replace(""));
		selenium.clickAt("instantMessengerLink", RuntimeVariables.replace(""));
		selenium.type("_125_aimSn", RuntimeVariables.replace("Selenium02"));
		selenium.type("_125_icqSn", RuntimeVariables.replace("123123123"));
		selenium.type("_125_jabberSn", RuntimeVariables.replace("Selenium02"));
		selenium.type("_125_msnSn", RuntimeVariables.replace("Selenium02"));
		selenium.type("_125_skypeSn", RuntimeVariables.replace("Selenium02"));
		selenium.type("_125_ymSn", RuntimeVariables.replace("Selenium02"));
		selenium.clickAt("socialNetworkLink", RuntimeVariables.replace(""));
		selenium.type("_125_facebookSn", RuntimeVariables.replace("Selenium02"));
		selenium.type("_125_mySpaceSn", RuntimeVariables.replace("Selenium02"));
		selenium.type("_125_twitterSn", RuntimeVariables.replace("Selenium02"));
		selenium.clickAt("smsLink", RuntimeVariables.replace(""));
		selenium.type("_125_smsSn",
			RuntimeVariables.replace("Selenium02@selenium.com"));
		selenium.clickAt("openIdLink", RuntimeVariables.replace(""));
		selenium.type("_125_openId", RuntimeVariables.replace("test"));
		selenium.clickAt("announcementsLink", RuntimeVariables.replace(""));
		selenium.clickAt("//td[2]/input[2]", RuntimeVariables.replace(""));
		selenium.clickAt("//tr[4]/td[3]/input[2]", RuntimeVariables.replace(""));
		selenium.clickAt("//tr[5]/td[2]/input[2]", RuntimeVariables.replace(""));
		selenium.clickAt("displaySettingsLink", RuntimeVariables.replace(""));
		selenium.select("_125_timeZoneId",
			RuntimeVariables.replace("label=(UTC -08:00) Pacific Standard Time"));
		selenium.type("_125_greeting",
			RuntimeVariables.replace("Welcome Selenium02!"));
		selenium.clickAt("commentsLink", RuntimeVariables.replace(""));
		selenium.type("_125_comments",
			RuntimeVariables.replace("This is a test comment."));
		assertTrue(selenium.isTextPresent("(Modified)"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}