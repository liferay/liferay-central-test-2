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
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.security.permission.comparator.ActionComparator;
import com.liferay.portal.service.spring.PermissionServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
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
 * <a href="EditUserPermissionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class EditUserPermissionsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("actions")) {
				updateActions(req, res);
			}
			else if (cmd.equals("user_permissions")) {
				updateUserPermissions(req, res);
			}
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchUserException ||
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
			getForward(req, "portlet.communities.edit_user_permissions"));
	}

	protected void updateActions(ActionRequest req, ActionResponse res)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String groupId = ParamUtil.getString(req, "groupId");

		String portletResource = ParamUtil.getString(req, "portletResource");
		String modelResource = ParamUtil.getString(req, "modelResource");

		// Create community resource if it doesn't exist

		Resource resource = null;

		String selResource = modelResource;

		if (Validator.isNull(modelResource)) {
			selResource = portletResource;
		}

		resource = ResourceLocalServiceUtil.addResource(
			themeDisplay.getCompanyId(), selResource, Resource.TYPE_CLASS,
			Resource.SCOPE_GROUP, groupId);

		List actionIds = new ArrayList();

		List actions = ResourceActionsUtil.getResourceActions(
			themeDisplay.getCompanyId(), portletResource, modelResource);

		Collections.sort(
			actions,
			new ActionComparator(
				themeDisplay.getCompanyId(), themeDisplay.getLocale()));

		for (int i = 0; i < actions.size(); i++) {
			String actionId = (String)actions.get(i);

			String action = ParamUtil.getString(req, "action" + actionId);

			if (!action.equals("")) {
				actionIds.add(actionId);
			}
		}

		// Send redirect

		String redirect = ParamUtil.getString(req, "redirect");
		String assignmentType = ParamUtil.getString(req, "assignmentType");

		if (actionIds.size() == 0) {
			redirect += "&actionPos=-1";
		}
		else {
			redirect +=
				"&actionPos=0&actionIds=" +
					StringUtil.merge(actionIds);
		}

		redirect += "&assignmentType=" +
						assignmentType +
					"&resourceId=" +
						resource.getResourceId();

		res.sendRedirect(redirect);
	}

	protected void updateUserPermissions(ActionRequest req, ActionResponse res)
		throws Exception {

		String resourceId = ParamUtil.getString(req, "resourceId");

		String assignmentType = ParamUtil.getString(req, "assignmentType");

		String[] actionIds = StringUtil.split(
			ParamUtil.getString(req, "actionIds"));
		int actionPos = ParamUtil.getInteger(req, "actionPos");

		String[] addUserIds = StringUtil.split(
			ParamUtil.getString(req, "addUserIds"));
		String[] removeUserIds = StringUtil.split(
			ParamUtil.getString(req, "removeUserIds"));

		if (assignmentType.equals("individual")) {
			String actionId = actionIds[actionPos];
			actionIds = new String[] {actionId};
		}

		for (int i = 0; i < addUserIds.length; i++) {
			PermissionServiceUtil.setUserPermissions(
				addUserIds[i], actionIds, resourceId);
		}

		for (int i = 0; i < removeUserIds.length; i++) {
			PermissionServiceUtil.unsetUserPermissions(
				removeUserIds[i], actionIds, resourceId);
		}

		String redirect = ParamUtil.getString(req, "redirect");

		redirect += "&assignmentType=" +
						assignmentType +
					"&resourceId=" +
						resourceId;

		if (assignmentType.equals("individual") && redirect.indexOf("actionPos=" + actionPos + "&") != -1) {

			// Show message only if the user stayed on the same page

			SessionMessages.add(req, "request_processed");
		}

		res.sendRedirect(redirect);
	}

}