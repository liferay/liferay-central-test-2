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

package com.liferay.portalweb.portlet.sunjsr286test;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="PublicRenderParameters.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PublicRenderParameters extends BaseTestCase {
	public void testPublicRenderParame() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=PublicRenderParameters")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=PublicRenderParameters"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Render in PE1"));
		verifyTrue(selenium.isTextPresent("Render in PE2"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[text()='SetRenderinPE']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//a[text()='SetRenderinPE']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("1234567"));
		verifyTrue(selenium.isTextPresent("number4"));
		verifyTrue(selenium.isTextPresent("DefaultNamespace1"));
		verifyTrue(selenium.isTextPresent("DefaultNamespace2"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='SendDefaultNamespaceEvent']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='SendDefaultNamespaceEvent']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(
				"EventValue:EventingUsesDefaultNamespace"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='SetDefaultNameSpaceRender']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='SetDefaultNameSpaceRender']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(
				"PublicRenderValue:RenderUsesDefaultNamespace"));
		verifyTrue(selenium.isTextPresent("NoNameSpace1"));
		verifyTrue(selenium.isTextPresent("NoNameSpace2"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='SendNoNamespaceEvent']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='SendNoNamespaceEvent']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("EventValue:EventUsesNoNameSpace"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='SetNoNameSpaceRender']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='SetNoNameSpaceRender']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(
				"PublicRenderValue:RenderUsesNoNamespace"));
		verifyTrue(selenium.isTextPresent("RenderAlias1"));
		verifyTrue(selenium.isTextPresent("RenderAlias2"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[text()='RenderQnameAlias']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='RenderQnameAlias']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("RenderAlias : prpAliasValue"));
		verifyTrue(selenium.isTextPresent("QNameURINull1"));
		verifyTrue(selenium.isTextPresent("QNameURINull2"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='SetQNameNullNameSpaceRender']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='SetQNameNullNameSpaceRender']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("RenderUsesDefaultNamespace"));
	}
}