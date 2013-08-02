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

package com.liferay.portalweb.socialofficeprofile.profile.souseditphonenumberprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditPhoneNumberProfileTest extends BaseTestCase {
	public void testSOUs_EditPhoneNumberProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("Phones:"),
			selenium.getText("//div[@data-title='Phone Numbers']/h3"));
		assertEquals(RuntimeVariables.replace("Business"),
			selenium.getText("//div[@data-title='Phone Numbers']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("123-123-1234 123"),
			selenium.getText("//div[@data-title='Phone Numbers']/ul/li/span[2]"));
		selenium.clickAt("//div[@data-title='Phone Numbers']",
			RuntimeVariables.replace("Phones:"));
		selenium.waitForVisible("//input[contains(@id,'phoneNumber')]");
		selenium.type("//input[contains(@id,'phoneNumber')]",
			RuntimeVariables.replace("456-456-4567"));
		selenium.type("//input[contains(@id,'phoneExtension')]",
			RuntimeVariables.replace("456"));
		selenium.select("//select[contains(@id,'phoneType')]",
			RuntimeVariables.replace("Personal"));
		Thread.sleep(1000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//div[@data-title='Phone Numbers']/ul/li/span[2]",
			"456-456-4567 456");
		assertEquals(RuntimeVariables.replace("Phones:"),
			selenium.getText("//div[@data-title='Phone Numbers']/h3"));
		assertEquals(RuntimeVariables.replace("Personal"),
			selenium.getText("//div[@data-title='Phone Numbers']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("456-456-4567 456"),
			selenium.getText("//div[@data-title='Phone Numbers']/ul/li/span[2]"));
	}
}