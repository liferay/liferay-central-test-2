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

package com.liferay.portal.convert.action;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.RolePermissionsException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPermissionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class EditPermissionsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("merge")) {
				merge(actionRequest, actionResponse);
			}
			else if (cmd.equals("reassign")) {
				reassign(actionRequest, actionResponse);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException ||
				e instanceof RolePermissionsException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.admin.error");
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

		return mapping.findForward(
			getForward(renderRequest, "portlet.admin.edit_permissions"));
	}

	protected void merge(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] roleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "roleIds"), 0L);

		long toRoleId = roleIds[0];

		for (int i = 1; i < roleIds.length; i++) {
			long fromRoleId = roleIds[i];

			ResourcePermissionLocalServiceUtil.mergePermissions(
				fromRoleId, toRoleId);
		}
	}

	protected void reassign(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long resourcePermissionId = ParamUtil.getLong(
			actionRequest, "resourcePermissionId");
		long toRoleId = ParamUtil.getLong(actionRequest, "toRoleId");

		ResourcePermissionLocalServiceUtil.reassignPermissions(
			resourcePermissionId, toRoleId);
	}

}