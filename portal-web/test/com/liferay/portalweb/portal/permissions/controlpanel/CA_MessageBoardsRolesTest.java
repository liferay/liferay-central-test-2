/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CA_MessageBoardsRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CA_MessageBoardsRolesTest extends BaseTestCase {
	public void testCA_MessageBoardsRoles() throws Exception {
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=Message Boards"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboardsBAN_USER']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryADD_SUBCATEGORY']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryMOVE_THREAD']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryUPDATE_THREAD_PRIORITY']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessagePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}