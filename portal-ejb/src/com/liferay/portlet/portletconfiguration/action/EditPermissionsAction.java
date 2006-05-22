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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.UserPermission;
import com.liferay.portal.service.spring.PermissionServiceUtil;
import com.liferay.portal.service.spring.PortletServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPermissionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditPermissionsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("group_permissions")) {
				updateGroupPermissions(req);
			}
			else if (cmd.equals("organization_permissions")) {
				updateOrganizationPermissions(req);
			}
			else if (cmd.equals("user_permissions")) {
				updateUserPermissions(req);
			}

			String redirect = ParamUtil.getString(req, "permissionsRedirect");

			sendRedirect(req, res, redirect);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.portlet_configuration.error");
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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String ownerId = Layout.getOwnerId(themeDisplay.getPlid());
		String groupId = Layout.getGroupId(ownerId);

		String portletResource = ParamUtil.getString(req, "portletResource");
		String modelResource = ParamUtil.getString(req, "modelResource");
		String resourcePrimKey = ParamUtil.getString(req, "resourcePrimKey");

		String selResource = portletResource;

		if (Validator.isNotNull(modelResource)) {
			selResource = modelResource;
		}

		if (selResource.equals(Group.class.getName())) {
			if (!GroupPermission.contains(
					permissionChecker, resourcePrimKey,
					ActionKeys.PERMISSIONS)) {

				SessionErrors.add(req, PrincipalException.class.getName());

				setForward(req, "portlet.portlet_configuration.error");
			}
		}
		else if (selResource.equals(Layout.class.getName())) {
			String layoutGroupId = StringUtil.split(resourcePrimKey, ".")[1];

			layoutGroupId = StringUtil.replace(layoutGroupId, "}", "");

			if (!GroupPermission.contains(
					permissionChecker, layoutGroupId,
					ActionKeys.MANAGE_LAYOUTS)) {

				SessionErrors.add(req, PrincipalException.class.getName());

				setForward(req, "portlet.portlet_configuration.error");
			}
		}
		else if (selResource.equals(User.class.getName())) {
			User user = UserLocalServiceUtil.getUserById(resourcePrimKey);

			if (!UserPermission.contains(
					permissionChecker, resourcePrimKey,
					user.getOrganization().getOrganizationId(),
					user.getLocation().getOrganizationId(),
					ActionKeys.PERMISSIONS)) {

				SessionErrors.add(req, PrincipalException.class.getName());

				setForward(req, "portlet.portlet_configuration.error");
			}
		}
		else if (!permissionChecker.hasPermission(
					groupId, selResource, resourcePrimKey,
					ActionKeys.PERMISSIONS)) {

			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.portlet_configuration.error");
		}

		Portlet portlet = PortletServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		ServletContext ctx =
			(ServletContext)req.getAttribute(WebKeys.CTX);

		res.setTitle(
			PortalUtil.getPortletTitle(portlet, ctx, themeDisplay.getLocale()));

		return mapping.findForward(
			getForward(req, "portlet.portlet_configuration.edit_permissions"));
	}

	protected void updateGroupPermissions(ActionRequest req) throws Exception {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		String resourceId = ParamUtil.getString(req, "resourceId");
		String groupId = ParamUtil.getString(req, "groupId");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(req, "groupIdActionIds"));

		PermissionServiceUtil.setGroupPermissions(
			groupId, actionIds, resourceId);
	}

	protected void updateOrganizationPermissions(ActionRequest req)
		throws Exception {

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		String resourceId = ParamUtil.getString(req, "resourceId");
		String organizationId = ParamUtil.getString(
			req, "organizationIdsPosValue");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(req, "organizationIdActionIds"));
		boolean organizationIntersection = ParamUtil.getBoolean(
			req, "organizationIntersection");

		if (!organizationIntersection) {
			PermissionServiceUtil.setGroupPermissions(
				organizationId, layout.getGroupId(), actionIds, resourceId);
		}
		else {
			PermissionServiceUtil.setOrgGroupPermissions(
				organizationId, layout.getGroupId(), actionIds, resourceId);
		}
	}

	protected void updateUserPermissions(ActionRequest req) throws Exception {
		String resourceId = ParamUtil.getString(req, "resourceId");
		String userId = ParamUtil.getString(req, "userIdsPosValue");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(req, "userIdActionIds"));

		PermissionServiceUtil.setUserPermissions(userId, actionIds, resourceId);
	}

}