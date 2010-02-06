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

package com.liferay.portlet.mypages.action;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.communities.action.ActionUtil;
import com.liferay.util.servlet.DynamicServletRequest;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Amos Fong
 */
public class ViewAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		if (renderRequest.getRemoteUser() == null) {
			return mapping.findForward("portlet.my_pages.view");
		}

		if (!renderRequest.getWindowState().equals(WindowState.MAXIMIZED)) {
			return mapping.findForward("portlet.my_pages.view");
		}

		User user = PortalUtil.getUser(renderRequest);

		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)renderRequest;

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)renderRequestImpl.getHttpServletRequest();

		dynamicRequest.setParameter(
			"p_u_i_d", String.valueOf(user.getUserId()));

		String tabs1 = "public-pages";

		boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(
			user.getUserId(), user.getCompanyId(), RoleConstants.POWER_USER,
			true);

		if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE ||
			(PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED &&
			 !hasPowerUserRole)) {

			tabs1 = "private-pages";
		}

		dynamicRequest.setParameter("tabs1", tabs1);

		Group group = user.getGroup();

		dynamicRequest.setParameter(
			"groupId", String.valueOf(group.getGroupId()));

		ActionUtil.getGroup(renderRequest);

		return mapping.findForward("portlet.my_pages.edit_pages");
	}

}