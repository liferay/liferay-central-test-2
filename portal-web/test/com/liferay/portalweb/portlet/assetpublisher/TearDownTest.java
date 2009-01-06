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

package com.liferay.portalweb.portlet.assetpublisher;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				boolean APBlogsPagePresent = selenium.isElementPresent(
						"link=AP Setup Blogs Test Page");

				if (!APBlogsPagePresent) {
					label = 5;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup Blogs Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup Blogs Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APBlogsPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APBlogsPortletPresent) {
					label = 4;

					continue;
				}

				boolean APBlogsPresentA = selenium.isElementPresent(
						"link=Delete");

				if (!APBlogsPresentA) {
					label = 2;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 2:

				boolean APBlogsPresentB = selenium.isElementPresent(
						"link=Delete");

				if (!APBlogsPresentB) {
					label = 3;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 3:
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Entry"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Entry 2"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Blog Entry']"));

			case 4:
			case 5:

				boolean APBookmarkPagePresent = selenium.isElementPresent(
						"link=AP Setup Bookmark Test Page");

				if (!APBookmarkPagePresent) {
					label = 10;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup Bookmark Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup Bookmark Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APBookmarkPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APBookmarkPortletPresent) {
					label = 9;

					continue;
				}

				boolean APBookmarkFolderPresent = selenium.isElementPresent(
						"//b");

				if (!APBookmarkFolderPresent) {
					label = 8;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");

				boolean APBookmarkEntryPresentA = selenium.isElementPresent(
						"//strong/span");

				if (!APBookmarkEntryPresentA) {
					label = 6;

					continue;
				}

				selenium.click("//strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 6:

				boolean APBookmarkEntryPresentB = selenium.isElementPresent(
						"//strong/span");

				if (!APBookmarkEntryPresentB) {
					label = 7;

					continue;
				}

				selenium.click("//strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 7:
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Bookmark"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Bookmark 2"));
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//td[4]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 8:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Folder']"));

			case 9:
			case 10:

				boolean APDLPagePresent = selenium.isElementPresent(
						"link=AP Setup DL Test Page");

				if (!APDLPagePresent) {
					label = 15;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup DL Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup DL Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APDLPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APDLPortletPresent) {
					label = 14;

					continue;
				}

				boolean APDLFolderPresent = selenium.isElementPresent(
						"link=AP Setup DL Test Folder");

				if (!APDLFolderPresent) {
					label = 13;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup DL Test Folder"));
				selenium.waitForPageToLoad("30000");

				boolean APDocumentPresentA = selenium.isElementPresent(
						"//td[5]/ul/li/strong/span");

				if (!APDocumentPresentA) {
					label = 11;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[4]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 11:

				boolean APDocumentPresentB = selenium.isElementPresent(
						"//td[5]/ul/li/strong/span");

				if (!APDocumentPresentB) {
					label = 12;

					continue;
				}

				selenium.click("//td[5]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[4]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 12:
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Document.txt"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Document 2.txt"));
				selenium.click(RuntimeVariables.replace("//span[1]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");

			case 13:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Folder']"));

			case 14:
			case 15:

				boolean APIGPagePresent = selenium.isElementPresent(
						"link=AP Setup IG Test Page");

				if (!APIGPagePresent) {
					label = 20;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup IG Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup IG Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APIGPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APIGPortletPresent) {
					label = 19;

					continue;
				}

				boolean APIGFolderPresent = selenium.isElementPresent("//b");

				if (!APIGFolderPresent) {
					label = 18;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");

				boolean APImagePresentA = selenium.isElementPresent("//a/img");

				if (!APImagePresentA) {
					label = 16;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Image']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Image']");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[3]/ul/li[3]/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[3]/ul/li[3]/a[2]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 16:

				boolean APImagePresentB = selenium.isElementPresent("//a/img");

				if (!APImagePresentB) {
					label = 17;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Image']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Image']");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[3]/ul/li[3]/a[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[3]/ul/li[3]/a[2]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 17:
				assertFalse(selenium.isTextPresent("AP Setup Test Image"));
				assertFalse(selenium.isTextPresent("AP Setup Test Image 2"));
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//td[4]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 18:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Folder']"));

			case 19:
			case 20:

				boolean APWebContentPagePresent = selenium.isElementPresent(
						"link=AP Setup Web Content Test Page");

				if (!APWebContentPagePresent) {
					label = 22;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup Web Content Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup Web Content Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APWebContentPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APWebContentPortletPresent) {
					label = 21;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Articles")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Articles"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_15_allRowIds")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("_15_allRowIds");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected articles[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Article"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Test Article 2"));
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Article']"));

			case 21:
			case 22:

				boolean APMBPagePresent = selenium.isElementPresent(
						"link=AP Setup MB Test Page");

				if (!APMBPagePresent) {
					label = 26;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup MB Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup MB Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APMBPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APMBPortletPresent) {
					label = 25;

					continue;
				}

				boolean APCategoryPresent = selenium.isElementPresent("//b");

				if (!APCategoryPresent) {
					label = 24;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");

				boolean APThreadPresent = selenium.isElementPresent(
						"//td[7]/ul/li/strong/span");

				if (!APThreadPresent) {
					label = 23;

					continue;
				}

				selenium.click("//td[7]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup MB Test Thread"));

			case 23:
				selenium.click(RuntimeVariables.replace("link=Categories"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//td[5]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 24:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Category']"));

			case 25:
			case 26:

				boolean APWikiPagePresent = selenium.isElementPresent(
						"link=AP Setup Wiki Test Page");

				if (!APWikiPagePresent) {
					label = 30;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=AP Setup Wiki Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=AP Setup Wiki Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APWikiPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APWikiPortletPresent) {
					label = 29;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Recent Changes"));
				selenium.waitForPageToLoad("30000");

				boolean APWikiPresentA = selenium.isElementPresent(
						"//td[6]/ul/li/strong/span");

				if (!APWikiPresentA) {
					label = 27;

					continue;
				}

				selenium.click("//td[6]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 27:

				boolean APWikiPresentB = selenium.isElementPresent(
						"//td[6]/ul/li/strong/span");

				if (!APWikiPresentB) {
					label = 28;

					continue;
				}

				selenium.click("//td[6]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 28:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//img[@alt='Manage Wikis']"));

			case 29:
			case 30:

				boolean APPagePresent = selenium.isElementPresent(
						"link=Asset Publisher Test Page");

				if (!APPagePresent) {
					label = 32;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Asset Publisher Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Asset Publisher Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean APPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APPortletPresent) {
					label = 31;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Configuration")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Configuration"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_86_selectionStyle")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_86_selectionStyle",
					RuntimeVariables.replace("label=Dynamic"));
				selenium.select("_86_classNameId",
					RuntimeVariables.replace("label=All"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//input[@value='Save']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"You have successfully updated the setup."));
				selenium.click("link=Display Settings");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_86_displayStyle")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_86_displayStyle",
					RuntimeVariables.replace("label=Abstracts"));
				selenium.select("_86_delta",
					RuntimeVariables.replace("label=20"));
				selenium.select("_86_paginationType",
					RuntimeVariables.replace("label=None"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"You have successfully updated the setup."));
				selenium.click(RuntimeVariables.replace(
						"link=Return to Full Page"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent("link=Configuration"));

			case 31:
			case 32:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[2]/ul/li[1]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[2]/ul/li[1]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Manage Pages"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean BlogsPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!BlogsPagesPresent) {
					label = 33;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 33:

				boolean BookmarkPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!BookmarkPagesPresent) {
					label = 34;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 34:

				boolean DLPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!DLPagesPresent) {
					label = 35;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 35:

				boolean IGPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!IGPagesPresent) {
					label = 36;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 36:

				boolean JournalPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!JournalPagesPresent) {
					label = 37;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 37:

				boolean MBPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!MBPagesPresent) {
					label = 38;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 38:

				boolean WikiPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!WikiPagesPresent) {
					label = 39;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 39:

				boolean AssetPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!AssetPagesPresent) {
					label = 40;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 40:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[2]/ul/li[1]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[2]/ul/li[1]/a/span"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Blogs Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Bookmark Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup DL Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup IG Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Web Content Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup MB Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=AP Setup Wiki Test Page"));
				assertFalse(selenium.isElementPresent(
						"link=Asset Publisher Test Page"));

			case 100:
				label = -1;
			}
		}
	}
}