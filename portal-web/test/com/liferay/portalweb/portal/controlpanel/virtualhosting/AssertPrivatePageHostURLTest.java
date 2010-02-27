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

package com.liferay.portalweb.portal.controlpanel.virtualhosting;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertPrivatePageHostURLTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertPrivatePageHostURLTest extends BaseTestCase {
	public void testAssertPrivatePageHostURL() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("http://www.baker.com:8080/");
				Thread.sleep(5000);

				boolean LoggedOut = selenium.isElementPresent("_58_login");

				if (!LoggedOut) {
					label = 2;

					continue;
				}

				selenium.typeKeys("_58_login",
					RuntimeVariables.replace("test@lifera.com"));
				selenium.type("_58_login",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.typeKeys("_58_password",
					RuntimeVariables.replace("test"));
				selenium.type("_58_password", RuntimeVariables.replace("test"));
				selenium.clickAt("_58_rememberMeCheckbox",
					RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals("http://www.baker.com:8080/",
					selenium.getLocation());
				assertEquals(RuntimeVariables.replace(
						"Virtual Hosting Community"),
					selenium.getText("//h1/span"));
				assertTrue(selenium.isElementPresent("link=Private Page"));
				selenium.clickAt("link=Private Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals("http://www.baker.com:8080/private-page",
					selenium.getLocation());

			case 100:
				label = -1;
			}
		}
	}
}