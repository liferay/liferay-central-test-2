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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

public class AssertDeleteChoiceTest extends BaseTestCase {
	public void testAssertDeleteChoice() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Question']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_25_title_en_US",
			RuntimeVariables.replace("Delete Choice Title Test"));
		selenium.type("_25_title_en_US",
			RuntimeVariables.replace("Delete Choice Title Test"));
		selenium.type("_25_description_en_US",
			RuntimeVariables.replace("Delete Choice Description Test"));
		selenium.type("_25_choiceDescriptiona_en_US",
			RuntimeVariables.replace("Delete Choice A"));
		selenium.type("_25_choiceDescriptionb_en_US",
			RuntimeVariables.replace("Delete Choice B"));
		selenium.click(RuntimeVariables.replace("//input[@value='Add Choice']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_25_choiceDescriptionc_en_US",
			RuntimeVariables.replace("Delete Choice C"));
		selenium.type("_25_choiceDescriptionc_en_US",
			RuntimeVariables.replace("Delete Choice C"));
		selenium.click(RuntimeVariables.replace("//input[@value='Add Choice']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_25_choiceDescriptiond_en_US",
			RuntimeVariables.replace("Delete Choice D"));
		selenium.type("_25_choiceDescriptiond_en_US",
			RuntimeVariables.replace("Delete Choice D"));
		selenium.click(RuntimeVariables.replace("//input[@value='Add Choice']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_25_choiceDescriptione_en_US",
			RuntimeVariables.replace("Delete Choice E"));
		selenium.type("_25_choiceDescriptione_en_US",
			RuntimeVariables.replace("Delete Choice E"));
		assertEquals("Delete Choice C",
			selenium.getValue("_25_choiceDescriptionc_en_US"));
		assertEquals("Delete Choice D",
			selenium.getValue("_25_choiceDescriptiond_en_US"));
		assertEquals("Delete Choice E",
			selenium.getValue("_25_choiceDescriptione_en_US"));
		selenium.click(RuntimeVariables.replace("//input[@value='Delete']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("_25_choiceDescriptionc_en_US"));
		assertEquals("Delete Choice D",
			selenium.getValue("_25_choiceDescriptiond_en_US"));
		assertEquals("Delete Choice E",
			selenium.getValue("_25_choiceDescriptione_en_US"));
	}
}