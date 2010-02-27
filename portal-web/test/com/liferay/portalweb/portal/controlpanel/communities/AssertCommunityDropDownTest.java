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

package com.liferay.portalweb.portal.controlpanel.communities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertCommunityDropDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertCommunityDropDownTest extends BaseTestCase {
	public void testAssertCommunityDropDown() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Back to My Community")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Back to My Community",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Community"),
			selenium.getText(
				"//div[@id='_145_myPlacesContainer']/ul/li[6]/a/span[1]"));
		assertEquals(RuntimeVariables.replace("Public"),
			selenium.getText(
				"//div[@id='_145_myPlacesContainer']/ul/li[6]/a/span[2]"));
		assertEquals(RuntimeVariables.replace("Test Community"),
			selenium.getText(
				"//div[@id='_145_myPlacesContainer']/ul/li[7]/a/span[1]"));
		assertEquals(RuntimeVariables.replace("Private"),
			selenium.getText(
				"//div[@id='_145_myPlacesContainer']/ul/li[7]/a/span[2]"));
	}
}