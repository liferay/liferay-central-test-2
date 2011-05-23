/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher.wikipagecomment.ratewikipagecommentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWikiPageCommentAPTest extends BaseTestCase {
	public void testRateWikiPageCommentAP() throws Exception {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//div[@class='yui3-aui-rating-label-element']",
							"0 Votes")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText(
				"//div[@class='yui3-aui-rating-label-element']", "0 Votes"));
		assertEquals(RuntimeVariables.replace("Rate this as good."),
			selenium.getText("//div[3]/div/div[1]/div/div/div/div/a[1]"));
		selenium.clickAt("//div[3]/div/div[1]/div/div/div/div/a[1]",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='yui3-aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='yui3-aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText("//div[3]/div/div[1]/div/div/div/div/a[2]"));
		selenium.clickAt("//div[3]/div/div[1]/div/div/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='yui3-aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='yui3-aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText("//div[3]/div/div[1]/div/div/div/div/a[2]"));
		selenium.clickAt("//div[3]/div/div[1]/div/div/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));
		assertTrue(selenium.isElementPresent(
				"//div[3]/div/div[1]/div/div/div/div/a[1]"));
		assertTrue(selenium.isElementPresent(
				"//div[3]/div/div[1]/div/div/div/div/a[2]"));
	}
}