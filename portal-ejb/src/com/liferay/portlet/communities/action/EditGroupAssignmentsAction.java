/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.communities.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.spring.OrganizationServiceUtil;
import com.liferay.portal.service.spring.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.servlet.SessionErrors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditGroupAssignmentsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditGroupAssignmentsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("group_organizations")) {
				updateGroupOrganizations(req);
			}
			else if (cmd.equals("group_users")) {
				updateGroupUsers(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.communities.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			ActionUtil.getGroup(req);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.communities.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.communities.edit_community_assignments"));
	}

	protected void updateGroupOrganizations(ActionRequest req)
		throws Exception {

		String groupId = ParamUtil.getString(req, "groupId");

		String[] addOrganizationIds = StringUtil.split(
			ParamUtil.getString(req, "addOrganizationIds"));
		String[] removeOrganizationIds = StringUtil.split(
			ParamUtil.getString(req, "removeOrganizationIds"));

		OrganizationServiceUtil.addGroupOrganizations(
			groupId, addOrganizationIds);
		OrganizationServiceUtil.unsetGroupOrganizations(
			groupId, removeOrganizationIds);
	}

	protected void updateGroupUsers(ActionRequest req) throws Exception {
		String groupId = ParamUtil.getString(req, "groupId");

		String[] addUserIds = StringUtil.split(
			ParamUtil.getString(req, "addUserIds"));
		String[] removeUserIds = StringUtil.split(
			ParamUtil.getString(req, "removeUserIds"));

		UserServiceUtil.addGroupUsers(groupId, addUserIds);
		UserServiceUtil.unsetGroupUsers(groupId, removeUserIds);
	}

}