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

package com.liferay.portalweb.plugins.googlemaps;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="DisableInputTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DisableInputTest extends BaseTestCase {
	public void testDisableInput() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Google Maps Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Google Maps Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Configuration"));
				selenium.waitForPageToLoad("30000");

				boolean MapChecked = selenium.isChecked(
						"_86_mapInputEnabledCheckbox");

				if (!MapChecked) {
					label = 2;

					continue;
				}

				selenium.click("_86_mapInputEnabledCheckbox");

			case 2:
				Thread.sleep(500);

				boolean DirectionChecked = selenium.isChecked(
						"_86_directionsInputEnabledCheckbox");

				if (!DirectionChecked) {
					label = 3;

					continue;
				}

				selenium.click("_86_directionsInputEnabledCheckbox");

			case 3:
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"You have successfully updated the setup."));

			case 100:
				label = -1;
			}
		}
	}
}