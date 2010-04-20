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

package com.liferay.portalweb.portlet.assetpublisher.webcontentcomment.ratewebcontentcomment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="RateWebContentCommentTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RateWebContentCommentTest extends BaseTestCase {
	public void testRateWebContentComment() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Asset Publisher Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"));
		selenium.clickAt("//table[1]/tbody/tr/td[1]/div/div/div/a[1]",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"));
		selenium.clickAt("//td[1]/div/div/div/a[2]",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1 (1 Vote)")
										.equals(selenium.getText(
								"//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"));
		selenium.clickAt("//td[1]/div/div/div/a[2]",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//td[2]/table[1]/tbody/tr/td[1]/div/div/div/div"));
	}
}