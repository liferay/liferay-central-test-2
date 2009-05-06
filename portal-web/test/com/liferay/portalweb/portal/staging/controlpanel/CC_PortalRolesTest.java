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

package com.liferay.portalweb.portal.staging.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CC_PortalRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CC_PortalRolesTest extends BaseTestCase {
	public void testCC_PortalRoles() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Portal Permissions']"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_scope90ADD_COMMUNITY",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope90ADD_LICENSE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope90ADD_ORGANIZATION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope90ADD_PASSWORD_POLICY",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope90ADD_ROLE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope90ADD_USER",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope90ADD_USER_GROUP",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupAPPROVE_PROPOSAL",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupASSIGN_MEMBERS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupASSIGN_REVIEWER",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupMANAGE_ANNOUNCEMENTS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupMANAGE_ARCHIVED_SETUPS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupMANAGE_LAYOUTS",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portal.model.GroupMANAGE_STAGING",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupPUBLISH_STAGING",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.GroupUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationAPPROVE_PROPOSAL",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationASSIGN_MEMBERS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationASSIGN_REVIEWER",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationMANAGE_ANNOUNCEMENTS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationMANAGE_ARCHIVED_SETUPS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationMANAGE_LAYOUTS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationMANAGE_STAGING",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationMANAGE_SUBORGANIZATIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationMANAGE_USERS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationPUBLISH_STAGING",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.OrganizationVIEW",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.PasswordPolicyASSIGN_MEMBERS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.PasswordPolicyDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.PasswordPolicyPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.PasswordPolicyUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.PasswordPolicyVIEW",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RoleASSIGN_MEMBERS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RoleDEFINE_PERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RoleDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RoleMANAGE_ANNOUNCEMENTS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RolePERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RoleUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.RoleVIEW",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.tasks.model.TasksProposalADD_DISCUSSION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.tasks.model.TasksProposalDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.tasks.model.TasksProposalUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.tasks.model.TasksProposalVIEW",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserIMPERSONATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserVIEW",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupASSIGN_MEMBERS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupMANAGE_ANNOUNCEMENTS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupMANAGE_LAYOUTS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portal.model.UserGroupVIEW",
			RuntimeVariables.replace("label="));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}