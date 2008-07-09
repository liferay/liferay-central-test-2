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

package com.liferay.portalweb.portlet.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="MoveThreadTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MoveThreadTest extends BaseTestCase {
	public void testMoveThread() throws Exception {
		selenium.click("link=Move Thread");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("category", RuntimeVariables.replace("30000"));
		selenium.selectWindow("category");
		selenium.click(
			"//div[@id=\"p_p_id_19_\"]/div/form/div[3]/table/tbody/tr[3]/td[5]/input");
		selenium.selectWindow("null");
		selenium.click("_19_addExplanationPost");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_19_subject")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_19_subject",
			RuntimeVariables.replace(
				"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d - MOVED"));
		selenium.type("_19_subject",
			RuntimeVariables.replace(
				"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d - MOVED"));
		selenium.typeKeys("_19_textArea",
			RuntimeVariables.replace(
				"This m\u00e9ssag\u00e9 has b\u00e9\u00e9n mov\u00e9d."));
		selenium.type("_19_textArea",
			RuntimeVariables.replace(
				"This m\u00e9ssag\u00e9 has b\u00e9\u00e9n mov\u00e9d."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Move Thread']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//input[@value='Move Thread']");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=T\u00e9st Subcat\u00e9gory")) {
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
				if (selenium.isTextPresent(
							"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d - MOVED")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=Move Thread");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("category", RuntimeVariables.replace("30000"));
		selenium.selectWindow("category");
		selenium.click("//input[@value='Choose']");
		selenium.selectWindow("null");
		selenium.click("_19_addExplanationPost");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_19_textArea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_19_subject",
			RuntimeVariables.replace("Mov\u00e9 it back!"));
		selenium.type("_19_subject",
			RuntimeVariables.replace("Mov\u00e9 it back!"));
		selenium.typeKeys("_19_textArea",
			RuntimeVariables.replace(
				"This thr\u00e9ad has b\u00e9\u00e9n mov\u00e9d back!"));
		selenium.type("_19_textArea",
			RuntimeVariables.replace(
				"This thr\u00e9ad has b\u00e9\u00e9n mov\u00e9d back!"));
		selenium.click("//input[@value='Move Thread']");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=S\u00e9cond T\u00e9st Subcat\u00e9gory")) {
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
				if (selenium.isTextPresent("Mov\u00e9 it back!")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}
}