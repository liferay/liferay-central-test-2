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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.RolePermissionsException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.security.permission.comparator.ActionComparator;
import com.liferay.portal.service.PermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class EditRolePermissionsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("actions")) {
				updateActions(actionRequest, actionResponse);
			}
			else if (cmd.equals("delete_permission")) {
				deletePermission(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException ||
				e instanceof RolePermissionsException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getRole(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.enterprise_admin.edit_role_permissions"));
	}

	protected void deletePermission(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");
		long permissionId = ParamUtil.getLong(actionRequest, "permissionId");

		Role role = RoleLocalServiceUtil.getRole(roleId);

		if (role.getName().equals(RoleConstants.ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.OWNER) ||
			role.getName().equals(RoleConstants.COMMUNITY_ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.COMMUNITY_OWNER) ||
			role.getName().equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.ORGANIZATION_OWNER)) {

			throw new RolePermissionsException(role.getName());
		}

		PermissionServiceUtil.unsetRolePermission(
			roleId, themeDisplay.getScopeGroupId(), permissionId);

		// Send redirect

		SessionMessages.add(actionRequest, "permissionDeleted");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		actionResponse.sendRedirect(redirect);
	}

	protected void updateActions(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		Role role = RoleLocalServiceUtil.getRole(roleId);

		if (role.getName().equals(RoleConstants.ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.OWNER) ||
			role.getName().equals(RoleConstants.COMMUNITY_ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.COMMUNITY_OWNER) ||
			role.getName().equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.ORGANIZATION_OWNER)) {

			throw new RolePermissionsException(role.getName());
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");
		String[] modelResources = StringUtil.split(
			ParamUtil.getString(actionRequest, "modelResources"));

		Map<String, List<String>> resourceActionsMap =
			new HashMap<String, List<String>>();

		if (Validator.isNotNull(portletResource)) {
			resourceActionsMap.put(
				portletResource,
				ResourceActionsUtil.getResourceActions(portletResource, null));
		}

		for (String modelResource : modelResources) {
			resourceActionsMap.put(
				modelResource,
				ResourceActionsUtil.getResourceActions(null, modelResource));
		}

		for (Map.Entry<String, List<String>> entry :
				resourceActionsMap.entrySet()) {

			String selResource = entry.getKey();
			List<String> actions = entry.getValue();

			actions = ListUtil.sort(
				actions,
				new ActionComparator(
					themeDisplay.getCompanyId(), themeDisplay.getLocale()));

			for (String actionId : actions) {
				int scope = ParamUtil.getInteger(
					actionRequest, "scope" + selResource + actionId);

				if (scope == ResourceConstants.SCOPE_COMPANY) {
					PermissionServiceUtil.setRolePermission(
						roleId, themeDisplay.getScopeGroupId(), selResource,
						scope, String.valueOf(themeDisplay.getCompanyId()),
						actionId);
				}
				else if (scope == ResourceConstants.SCOPE_GROUP) {
					if ((role.getType() == RoleConstants.TYPE_COMMUNITY) ||
						(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

						PermissionServiceUtil.setRolePermission(
							roleId, themeDisplay.getScopeGroupId(), selResource,
							ResourceConstants.SCOPE_GROUP_TEMPLATE,
							String.valueOf(
								GroupConstants.DEFAULT_PARENT_GROUP_ID),
							actionId);
					}
					else {
						String[] groupIds = StringUtil.split(
							ParamUtil.getString(
								actionRequest,
								"groupIds" + selResource + actionId));

						if (groupIds.length == 0) {
							SessionErrors.add(
								actionRequest, "missingGroupIdsForAction");

							return;
						}

						groupIds = ArrayUtil.distinct(groupIds);

						PermissionServiceUtil.unsetRolePermissions(
							roleId, themeDisplay.getScopeGroupId(),
							selResource, ResourceConstants.SCOPE_GROUP,
							actionId);

						for (int j = 0; j < groupIds.length; j++) {
							PermissionServiceUtil.setRolePermission(
								roleId, themeDisplay.getScopeGroupId(),
								selResource, ResourceConstants.SCOPE_GROUP,
								groupIds[j], actionId);
						}
					}
				}
				else {

					// Remove company, group template, and group permissions

					PermissionServiceUtil.unsetRolePermissions(
						roleId, themeDisplay.getScopeGroupId(), selResource,
						ResourceConstants.SCOPE_COMPANY, actionId);

					PermissionServiceUtil.unsetRolePermissions(
						roleId, themeDisplay.getScopeGroupId(), selResource,
						ResourceConstants.SCOPE_GROUP_TEMPLATE, actionId);

					PermissionServiceUtil.unsetRolePermissions(
						roleId, themeDisplay.getScopeGroupId(), selResource,
						ResourceConstants.SCOPE_GROUP, actionId);
				}
			}
		}

		// Send redirect

		SessionMessages.add(actionRequest, "permissionsUpdated");

		String redirect =
			ParamUtil.getString(actionRequest, "redirect") + "&" +
				Constants.CMD + "=" + Constants.VIEW;

		actionResponse.sendRedirect(redirect);
	}

}