/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddUser3Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddUser3Test extends BaseTestCase {
	public void testAddUser3() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Users"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_125_prefixId", RuntimeVariables.replace("label=Ms."));
		selenium.type("_125_screenName", RuntimeVariables.replace("selenium03"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("test03@selenium.com"));
		selenium.typeKeys("_125_firstName", RuntimeVariables.replace("selen03"));
		selenium.type("_125_firstName", RuntimeVariables.replace("selen03"));
		selenium.typeKeys("_125_middleName", RuntimeVariables.replace("lenn"));
		selenium.type("_125_middleName", RuntimeVariables.replace("lenn"));
		selenium.typeKeys("_125_lastName", RuntimeVariables.replace("nium03"));
		selenium.type("_125_lastName", RuntimeVariables.replace("nium03"));
		selenium.select("_125_suffixId", RuntimeVariables.replace("label=PhD."));
		selenium.select("_125_birthdayMonth",
			RuntimeVariables.replace("label=December"));
		selenium.select("_125_birthdayDay", RuntimeVariables.replace("label=25"));
		selenium.select("_125_birthdayYear",
			RuntimeVariables.replace("label=1986"));
		selenium.select("_125_male", RuntimeVariables.replace("label=Female"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("passwordLink");
		selenium.type("_125_password1", RuntimeVariables.replace("test"));
		selenium.type("_125_password2", RuntimeVariables.replace("test"));
		selenium.click("addressesLink");
		selenium.type("_125_addressStreet10",
			RuntimeVariables.replace("123 Liferay Ln."));
		selenium.select("_125_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_125_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.type("_125_addressZip0", RuntimeVariables.replace("91234"));
		selenium.type("_125_addressCity0",
			RuntimeVariables.replace("Ray of Light"));
		selenium.click("_125_addressPrimary0");
		selenium.click("phoneNumbersLink");
		selenium.type("_125_phoneNumber0",
			RuntimeVariables.replace("123-123-1234"));
		selenium.type("_125_phoneExtension0", RuntimeVariables.replace("123"));
		selenium.click("_125_phonePrimary0");
		selenium.click("additionalEmailAddressesLink");
		selenium.type("_125_emailAddressAddress0",
			RuntimeVariables.replace("test03@selenium.com"));
		selenium.click("_125_emailAddressPrimary0");
		selenium.click("websitesLink");
		selenium.type("_125_websiteUrl0",
			RuntimeVariables.replace("http://www.selenium03.com"));
		selenium.select("_125_websiteTypeId0",
			RuntimeVariables.replace("label=Personal"));
		selenium.click("_125_websitePrimary0");
		selenium.click("instantMessengerLink");
		selenium.type("_125_aimSn", RuntimeVariables.replace("Selenium03"));
		selenium.type("_125_icqSn", RuntimeVariables.replace("123123123"));
		selenium.type("_125_jabberSn", RuntimeVariables.replace("Selenium03"));
		selenium.type("_125_msnSn", RuntimeVariables.replace("Selenium03"));
		selenium.type("_125_skypeSn", RuntimeVariables.replace("Selenium03"));
		selenium.type("_125_ymSn", RuntimeVariables.replace("Selenium03"));
		selenium.click("socialNetworkLink");
		selenium.type("_125_facebookSn", RuntimeVariables.replace("Selenium03"));
		selenium.type("_125_mySpaceSn", RuntimeVariables.replace("Selenium03"));
		selenium.type("_125_twitterSn", RuntimeVariables.replace("Selenium03"));
		selenium.click("smsLink");
		selenium.type("_125_smsSn",
			RuntimeVariables.replace("Selenium03@selenium.com"));
		selenium.click("openIdLink");
		selenium.type("_125_openId", RuntimeVariables.replace("test"));
		selenium.click("announcementsLink");
		selenium.click("//td[2]/input[2]");
		selenium.click("//tr[4]/td[3]/input[2]");
		selenium.click("//tr[5]/td[2]/input[2]");
		selenium.click("displaySettingsLink");
		selenium.select("_125_timeZoneId",
			RuntimeVariables.replace("label=(UTC -08:00) Pacific Standard Time"));
		selenium.type("_125_greeting",
			RuntimeVariables.replace("Welcome Selenium03!"));
		selenium.click("commentsLink");
		selenium.type("_125_comments",
			RuntimeVariables.replace("This is a test comment."));
		selenium.click("//input[@value='Save']");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}