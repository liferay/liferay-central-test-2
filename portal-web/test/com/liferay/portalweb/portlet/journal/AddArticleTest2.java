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

package com.liferay.portalweb.portlet.journal;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * <a href="AddArticleTest2.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddArticleTest2 extends BaseTestCase {
	public void testAddArticleT() throws Exception {
		selenium.click("//input[@value='Add Article']");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_15_title", "Test Journal Article 2");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_editor")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("FCKeditor1___Frame")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe[@id=\"_15_editor\"]");
		selenium.selectFrame("//iframe[@id=\"FCKeditor1___Frame\"]");
		selenium.selectFrame("//iframe");
		selenium.typeKeys("//body", "This is a test Journal Article 2!");
		selenium.selectFrame("relative=top");
		selenium.select("_15_type", "label=Announcements");
		selenium.select("_15_displayDateMonth", "label=April");
		selenium.select("_15_displayDateDay", "label=10");
		selenium.select("_15_displayDateYear", "label=2010");
		selenium.select("_15_displayDateHour", "label=12");
		selenium.select("_15_displayDateMinute", "label=:00");
		selenium.click("_15_neverExpireCheckbox");
		selenium.select("_15_expirationDateMonth", "label=January");
		selenium.select("_15_expirationDateDay", "label=27");
		selenium.select("_15_expirationDateYear", "label=2011");
		selenium.select("_15_expirationDateHour", "label=12");
		selenium.select("_15_expirationDateMinute", "label=:00");
		selenium.click("_15_neverReviewCheckbox");
		selenium.select("_15_reviewDateMonth", "label=September");
		selenium.select("_15_reviewDateDay", "label=16");
		selenium.select("_15_reviewDateYear", "label=2011");
		selenium.select("_15_reviewDateHour", "label=12");
		selenium.select("_15_reviewDateMinute", "label=:00");
		selenium.click("//input[@value='Save and Approve']");
		selenium.waitForPageToLoad("30000");
	}
}