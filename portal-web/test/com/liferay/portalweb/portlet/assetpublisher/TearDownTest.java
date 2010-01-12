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

				selenium.clickAt("link=AP Setup Blogs Test Page",
					RuntimeVariables.replace(""));
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

			case 3:

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

			case 4:
			case 5:

				boolean APBookmarkPagePresent = selenium.isElementPresent(
						"link=AP Setup Bookmark Test Page");

				if (!APBookmarkPagePresent) {
					label = 8;

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

				selenium.clickAt("link=AP Setup Bookmark Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APBookmarkPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APBookmarkPortletPresent) {
					label = 7;

					continue;
				}

				boolean APBookmarkFolderPresent = selenium.isElementPresent(
						"//b");

				if (!APBookmarkFolderPresent) {
					label = 6;

					continue;
				}

				selenium.clickAt("//td[4]/ul/li/strong/span",
					RuntimeVariables.replace(""));

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

			case 6:

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

			case 7:
			case 8:

				boolean APDLPagePresent = selenium.isElementPresent(
						"link=AP Setup DL Test Page");

				if (!APDLPagePresent) {
					label = 12;

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

				selenium.clickAt("link=AP Setup DL Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APDLPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APDLPortletPresent) {
					label = 11;

					continue;
				}

				boolean APDocumentPresentA = selenium.isElementPresent(
						"//td[5]/ul/li/strong/span");

				if (!APDocumentPresentA) {
					label = 9;

					continue;
				}

				selenium.clickAt("//td[5]/ul/li/strong/span",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[5]/ul/li[5]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("//div[5]/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 9:

				boolean APDocumentPresentB = selenium.isElementPresent(
						"//td[5]/ul/li/strong/span");

				if (!APDocumentPresentB) {
					label = 10;

					continue;
				}

				selenium.clickAt("//td[5]/ul/li/strong/span",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}


					try {
						if (selenium.isElementPresent("//div[5]/ul/li[5]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("//div[5]/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 10:

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

			case 11:
			case 12:

				boolean APIGPagePresent = selenium.isElementPresent(
						"link=AP Setup IG Test Page");

				if (!APIGPagePresent) {
					label = 15;

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

				selenium.clickAt("link=AP Setup IG Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APIGPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APIGPortletPresent) {
					label = 14;

					continue;
				}

				boolean APIGFolderPresent = selenium.isElementPresent("//b");

				if (!APIGFolderPresent) {
					label = 13;

					continue;
				}

				selenium.clickAt("//td[4]/ul/li/strong/span",
					RuntimeVariables.replace(""));

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

			case 14:
			case 15:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_15_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected web content[\\s\\S]$"));
				selenium.clickAt("link=Back to My Community",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APMBPagePresent = selenium.isElementPresent(
						"link=AP Setup MB Test Page");

				if (!APMBPagePresent) {
					label = 18;

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

				selenium.clickAt("link=AP Setup MB Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APMBPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APMBPortletPresent) {
					label = 17;

					continue;
				}

				boolean APCategoryPresent = selenium.isElementPresent("//b");

				if (!APCategoryPresent) {
					label = 16;

					continue;
				}

				selenium.clickAt("//td[5]/ul/li/strong/span",
					RuntimeVariables.replace(""));

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

			case 16:

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

			case 17:
			case 18:

				boolean APWikiPagePresent = selenium.isElementPresent(
						"link=AP Setup Wiki Test Page");

				if (!APWikiPagePresent) {
					label = 22;

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

				selenium.clickAt("link=AP Setup Wiki Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APWikiPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APWikiPortletPresent) {
					label = 21;

					continue;
				}

				selenium.clickAt("link=Recent Changes",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APWikiPresentA = selenium.isElementPresent(
						"//td[6]/ul/li/strong/span");

				if (!APWikiPresentA) {
					label = 19;

					continue;
				}

				selenium.clickAt("//td[6]/ul/li/strong/span",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[5]/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("//div[5]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 19:

				boolean APWikiPresentB = selenium.isElementPresent(
						"//td[6]/ul/li/strong/span");

				if (!APWikiPresentB) {
					label = 20;

					continue;
				}

				selenium.clickAt("//td[6]/ul/li/strong/span",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[5]/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("//div[5]/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 20:

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

			case 21:
			case 22:

				boolean APPagePresent = selenium.isElementPresent(
						"link=Asset Publisher Test Page");

				if (!APPagePresent) {
					label = 24;

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

				selenium.clickAt("link=Asset Publisher Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean APPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!APPortletPresent) {
					label = 23;

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

				selenium.clickAt("link=Configuration",
					RuntimeVariables.replace(""));
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

				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

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
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Return to Full Page",
					RuntimeVariables.replace(""));
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

			case 23:
			case 24:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Home")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Home", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Manage Pages",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean BlogsPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!BlogsPagesPresent) {
					label = 25;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 25:

				boolean BookmarkPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!BookmarkPagesPresent) {
					label = 26;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 26:

				boolean DLPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!DLPagesPresent) {
					label = 27;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 27:

				boolean IGPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!IGPagesPresent) {
					label = 28;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 28:

				boolean MBPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!MBPagesPresent) {
					label = 29;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 29:

				boolean WikiPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!WikiPagesPresent) {
					label = 30;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 30:

				boolean AssetPagesPresent = selenium.isElementPresent(
						"//li[2]/ul/li[3]/a/span");

				if (!AssetPagesPresent) {
					label = 31;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li[2]/ul/li[3]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 31:

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

				selenium.clickAt("//div[2]/ul/li[1]/a/span",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}