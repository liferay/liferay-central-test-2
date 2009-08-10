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

package com.liferay.portalweb.portal.permissions.documentlibrary.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Portlet_AddShortcutTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Portlet_AddShortcutTest extends BaseTestCase {
	public void testPortlet_AddShortcut() throws Exception {
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
									"link=Document Library Permissions Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Document Library Permissions Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"link=Portlet2 Temporary2 Folder2"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Add Shortcut']"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//input[@value='Select']");
				selenium.waitForPopUp("toGroup",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=toGroup");
				Thread.sleep(5000);

				boolean CommunityPresent1 = selenium.isElementPresent(
						"link=My Community");

				if (CommunityPresent1) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 2:

				boolean CommunityPresent2 = selenium.isElementPresent(
						"link=My Community");

				if (!CommunityPresent2) {
					label = 3;

					continue;
				}

				selenium.click("link=My Community");
				selenium.selectWindow("null");

			case 3:
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("My Community"),
					selenium.getText("_20_toGroupName"));
				selenium.click("_20_selectToFileEntryButton");
				selenium.waitForPopUp("toGroup",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=toGroup");
				Thread.sleep(5000);
				selenium.click(RuntimeVariables.replace("link=Document Home"));
				selenium.waitForPageToLoad("30000");

				boolean FolderPresent1 = selenium.isElementPresent(
						"link=My1 Community1 Folder1");

				if (FolderPresent1) {
					label = 4;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 4:

				boolean FolderPresent2 = selenium.isElementPresent(
						"link=My1 Community1 Folder1");

				if (!FolderPresent2) {
					label = 7;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=My1 Community1 Folder1"));
				selenium.waitForPageToLoad("30000");

				boolean DocumentPresent1 = selenium.isElementPresent(
						"link=My1 Community1 Document1.txt");

				if (DocumentPresent1) {
					label = 5;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 5:

				boolean DocumentPresent2 = selenium.isElementPresent(
						"link=My1 Community1 Document1.txt");

				if (!DocumentPresent2) {
					label = 6;

					continue;
				}

				selenium.click("link=My1 Community1 Document1.txt");
				selenium.selectWindow("null");

			case 6:
			case 7:
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace(
						"My1 Community1 Document1.txt"),
					selenium.getText("_20_toFileEntryTitle"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				assertTrue(selenium.isElementPresent(
						"link=My1 Community1 Document1.txt"));

			case 100:
				label = -1;
			}
		}
	}
}