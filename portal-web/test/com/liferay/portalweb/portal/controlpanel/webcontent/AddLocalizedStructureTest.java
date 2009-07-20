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

public class AddLocalizedStructureTest extends BaseTestCase {
	public void testAddLocalizedStructure() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Structures"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Structure']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_15_newStructureId",
			RuntimeVariables.replace("LOCALIZED"));
		selenium.type("_15_newStructureId",
			RuntimeVariables.replace("LOCALIZED"));
		selenium.type("_15_name",
			RuntimeVariables.replace("Test Localized Structure"));
		selenium.type("_15_description",
			RuntimeVariables.replace("This is a test localized structure."));
		selenium.click("_15_editorButton");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_xsdContent")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_15_xsdContent",
			RuntimeVariables.replace(
				"<root> \n\n  <dynamic-element name='page-name' type='text'></dynamic-element> \n\n  <dynamic-element name='page-description' type='text'></dynamic-element> \n\n</root> "));
		selenium.click("//input[@value='Update']");
		Thread.sleep(5000);
		assertEquals("page-name", selenium.getValue("_15_structure_el0_name"));
		assertEquals("page-description",
			selenium.getValue("_15_structure_el1_name"));
		selenium.click(RuntimeVariables.replace("//input[@value=\"Save\"]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=LOCALIZED"));
		assertTrue(selenium.isTextPresent("Test Localized Structure"));
	}
}