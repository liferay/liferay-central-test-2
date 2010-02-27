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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ViewQuestionGraphsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewQuestionGraphsTest extends BaseTestCase {
	public void testViewQuestionGraphs() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Polls")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Polls", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Test Poll Question", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Area", RuntimeVariables.replace(""));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Horizontal Bar")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Horizontal Bar", RuntimeVariables.replace(""));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Line")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Line", RuntimeVariables.replace(""));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Pie")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Pie", RuntimeVariables.replace(""));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Vertical Bar")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Vertical Bar", RuntimeVariables.replace(""));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");
	}
}