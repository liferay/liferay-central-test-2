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

package com.liferay.portalweb.portlet.enterpriseadmin;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * <a href="AddRolePermissionsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddRolePermissionsTest extends BaseTestCase {
	public void testAddRolePermissions() throws Exception {
		selenium.click("link=Roles");
		selenium.waitForPageToLoad("30000");
		selenium.click(
			"//a[contains(@href, \"javascript: submitForm(document.hrefFm, 'http%3A%2F%2Flocalhost%3A8080%2Fuser%2Fjoebloggs%2F4%3Fp_p_id%3D79%26p_p_action%3D0%26p_p_state%3Dmaximized%26p_p_mode%3Dview%26_79_struts_action%3D%252Fenterprise_admin%252Fedit_role_permissions%26_79_cmd%3Dview%26_79_roleId%3D10734');\")]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add Portlet Permissions']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Blogs");
		selenium.waitForPageToLoad("30000");
		selenium.select("_79_scope33ADD_ENTRY", "label=Communities");
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("community", "30000");
		selenium.selectWindow("community");
		selenium.click("link=Guest");
		selenium.click("//input[@value='Close']");
		selenium.selectWindow("null");
		selenium.select("_79_scope33CONFIGURATION", "label=Enterprise");
		selenium.select("_79_scope33VIEW", "label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryADD_DISCUSSION",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryDELETE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryDELETE_DISCUSSION",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryPERMISSIONS",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryUPDATE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryUPDATE_DISCUSSION",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryVIEW",
			"label=Communities");
		selenium.click(
			"//input[@value='Select' and @type='button' and @onclick=\"var groupWindow = window.open('http://localhost:8080/user/joebloggs/4?p_p_id=79&p_p_action=0&p_p_state=pop_up&p_p_mode=view&_79_struts_action=%2Fenterprise_admin%2Fselect_community&_79_target=com.liferay.portlet.blogs.model.BlogsEntryVIEW', 'community', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); groupWindow.focus();\"]");
		selenium.waitForPopUp("community", "30000");
		selenium.selectWindow("community");
		selenium.click("link=Guest");
		selenium.click("//input[@value='Close']");
		selenium.selectWindow("null");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Delete Discussion"));
		selenium.click("//input[@value='Add Portlet Permissions']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Next");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Message Boards");
		selenium.waitForPageToLoad("30000");
		selenium.select("_79_scope19ADD_CATEGORY", "label=Enterprise");
		selenium.select("_79_scope19BAN_USER", "label=Enterprise");
		selenium.select("_79_scope19CONFIGURATION", "label=Enterprise");
		selenium.select("_79_scope19VIEW", "label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessageDELETE",
			"label=Communities");
		selenium.click(
			"//input[@value='Select' and @type='button' and @onclick=\"var groupWindow = window.open('http://localhost:8080/user/joebloggs/4?p_p_id=79&p_p_action=0&p_p_state=pop_up&p_p_mode=view&_79_struts_action=%2Fenterprise_admin%2Fselect_community&_79_target=com.liferay.portlet.messageboards.model.MBMessageDELETE', 'community', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); groupWindow.focus();\"]");
		selenium.waitForPopUp("community", "30000");
		selenium.selectWindow("community");
		selenium.click("link=Guest");
		selenium.click("//input[@value='Close']");
		selenium.selectWindow("null");
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessagePERMISSIONS",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessageUPDATE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessageVIEW",
			"label=Communities");
		selenium.click(
			"//input[@value='Select' and @type='button' and @onclick=\"var groupWindow = window.open('http://localhost:8080/user/joebloggs/4?p_p_id=79&p_p_action=0&p_p_state=pop_up&p_p_mode=view&_79_struts_action=%2Fenterprise_admin%2Fselect_community&_79_target=com.liferay.portlet.messageboards.model.MBMessageVIEW', 'community', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); groupWindow.focus();\"]");
		selenium.waitForPopUp("community", "30000");
		selenium.selectWindow("community");
		selenium.click("link=Guest");
		selenium.click("//input[@value='Close']");
		selenium.selectWindow("null");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Subscribe"));
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}