/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddLocalizedArticleTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddLocalizedArticleTest extends BaseTestCase {
	public void testAddLocalizedArticle() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Web Content']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[2]/input[5]");
		assertTrue(selenium.getConfirmation()
						   .matches("^Selecting a template will change the structure, available input fields, and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
		selenium.waitForPopUp("template", RuntimeVariables.replace("30000"));
		selenium.selectWindow("template");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=LOCALIZED")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=LOCALIZED");
		Thread.sleep(5000);
		selenium.selectWindow("null");
		assertTrue(selenium.isElementPresent("link=Test Localized Structure"));
		assertTrue(selenium.isElementPresent("link=Test Localized Template"));
		selenium.type("_15_structure_el0_content",
			RuntimeVariables.replace("Hello World Page Name"));
		selenium.click("//input[@type='checkbox']");
		selenium.type("_15_structure_el1_content",
			RuntimeVariables.replace("Hello World Page Description"));
		selenium.click(
			"//tr[2]/td/table/tbody/tr/td/fieldset/table[2]/tbody/tr/td[1]/input");
		selenium.type("_15_title",
			RuntimeVariables.replace("Hello World Localized Article"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value=\"Save\"]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//input[@value=\"Save and Continue\"]");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent(
							"Your request processed successfully.")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_15_languageId",
			RuntimeVariables.replace("label=Chinese (China)"));
		Thread.sleep(5000);
		selenium.type("_15_structure_el0_content",
			RuntimeVariables.replace("\u4e16\u754c\u60a8\u597d Page Name"));
		selenium.type("_15_structure_el1_content",
			RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"));
		selenium.click("//input[@value=\"Save and Approve\"]");
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent(
				"link=Hello World Localized Article"));
	}
}