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
 * <a href="Writer_PortalRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Writer_PortalRolesTest extends BaseTestCase {
	public void testWriter_PortalRoles() throws Exception {
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.uncheck("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupASSIGN_MEMBERS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupASSIGN_REVIEWER']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ANNOUNCEMENTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ARCHIVED_SETUPS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_STAGING']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupPERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupPUBLISH_STAGING']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupUPDATE']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.uncheck("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationASSIGN_MEMBERS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationASSIGN_REVIEWER']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationMANAGE_ANNOUNCEMENTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationMANAGE_ARCHIVED_SETUPS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationMANAGE_LAYOUTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationMANAGE_STAGING']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationMANAGE_SUBORGANIZATIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationMANAGE_USERS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationPERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationPUBLISH_STAGING']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.OrganizationVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.uncheck("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleDEFINE_PERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleMANAGE_ANNOUNCEMENTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RolePERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=User Groups"));
		selenium.waitForPageToLoad("30000");
		selenium.uncheck("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupMANAGE_ANNOUNCEMENTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupPERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupMANAGE_LAYOUTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}