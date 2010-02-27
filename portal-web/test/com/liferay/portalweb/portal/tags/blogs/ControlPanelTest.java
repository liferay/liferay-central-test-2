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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ControlPanelTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ControlPanelTest extends BaseTestCase {
	public void testControlPanel() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				Thread.sleep(500);

				boolean InControlPanel = selenium.isElementPresent(
						"//div[4]/ul/li[2]/a/span[1]");

				if (!InControlPanel) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 2:
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}