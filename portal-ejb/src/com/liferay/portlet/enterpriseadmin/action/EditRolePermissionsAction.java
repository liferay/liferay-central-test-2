/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.security.permission.comparator.ActionComparator;
import com.liferay.portal.service.spring.PermissionServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditRolePermissionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditRolePermissionsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("actions")) {
				updateActions(req, res);
			}
			else if (cmd.equals("group_permissions")) {
				updateGroupPermissions(req, res);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.enterprise_admin.error");
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
			ActionUtil.getRole(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.enterprise_admin.edit_role_permissions"));
	}

	protected void updateActions(ActionRequest req, ActionResponse res)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String roleId = ParamUtil.getString(req, "roleId");

		String portletResource = ParamUtil.getString(req, "portletResource");
		String modelResource = ParamUtil.getString(req, "modelResource");

		String selResource = modelResource;

		if (Validator.isNull(modelResource)) {
			selResource = portletResource;
		}

		List groupScopeActionIds = new ArrayList();

		List actions = ResourceActionsUtil.getResourceActions(
			themeDisplay.getCompanyId(), portletResource, modelResource);

		Collections.sort(
			actions,
			new ActionComparator(
				themeDisplay.getCompanyId(), themeDisplay.getLocale()));

		for (int i = 0; i < actions.size(); i++) {
			String actionId = (String)actions.get(i);

			String scope = ParamUtil.getString(req, "scope" + actionId);

			if (scope.equals(Resource.SCOPE_COMPANY)) {
				PermissionServiceUtil.setRolePermission(
					roleId, themeDisplay.getPortletGroupId(), selResource,
					Resource.TYPE_CLASS, scope, themeDisplay.getCompanyId(),
					actionId);
			}
			else if (scope.equals(Resource.SCOPE_GROUP)) {
				groupScopeActionIds.add(actionId);
			}
			else {

				// Remove company and group permissions

				PermissionServiceUtil.unsetRolePermissions(
					roleId, themeDisplay.getPortletGroupId(), selResource,
					Resource.TYPE_CLASS, Resource.SCOPE_COMPANY, actionId);

				PermissionServiceUtil.unsetRolePermissions(
					roleId, themeDisplay.getPortletGroupId(), selResource,
					Resource.TYPE_CLASS, Resource.SCOPE_GROUP, actionId);
			}
		}

		// Send redirect

		String redirect = ParamUtil.getString(req, "redirect");

		if (groupScopeActionIds.size() == 0) {
			redirect += "&groupScopePos=-1";
		}
		else {
			redirect +=
				"&groupScopePos=0&groupScopeActionIds=" +
					StringUtil.merge(groupScopeActionIds);
		}

		res.sendRedirect(redirect);
	}

	protected void updateGroupPermissions(ActionRequest req, ActionResponse res)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String roleId = ParamUtil.getString(req, "roleId");

		String portletResource = ParamUtil.getString(req, "portletResource");
		String modelResource = ParamUtil.getString(req, "modelResource");

		String selResource = modelResource;
		if (Validator.isNull(modelResource)) {
			selResource = portletResource;
		}

		int groupScopePos = ParamUtil.getInteger(req, "groupScopePos");
		String[] groupScopeActionIds = StringUtil.split(
			ParamUtil.getString(req, "groupScopeActionIds"));

		String actionId = groupScopeActionIds[groupScopePos];

		String[] addGroupIds = StringUtil.split(
			ParamUtil.getString(req, "addGroupIds"));
		String[] removeGroupIds = StringUtil.split(
			ParamUtil.getString(req, "removeGroupIds"));

		for (int i = 0; i < addGroupIds.length; i++) {
			PermissionServiceUtil.setRolePermission(
				roleId, themeDisplay.getPortletGroupId(), selResource,
				Resource.TYPE_CLASS, Resource.SCOPE_GROUP, addGroupIds[i],
				actionId);
		}

		for (int i = 0; i < removeGroupIds.length; i++) {
			PermissionServiceUtil.unsetRolePermission(
				roleId, themeDisplay.getPortletGroupId(), selResource,
				Resource.TYPE_CLASS, Resource.SCOPE_GROUP, removeGroupIds[i],
				actionId);
		}

		String redirect = ParamUtil.getString(req, "redirect");

		if (redirect.indexOf("groupScopePos=" + groupScopePos + "&") != -1) {

			// Show message only if the user stayed on the same page

			SessionMessages.add(req, "request_processed");
		}

		res.sendRedirect(redirect);
	}

}