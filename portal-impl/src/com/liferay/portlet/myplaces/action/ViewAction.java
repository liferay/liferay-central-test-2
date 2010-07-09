/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.myplaces.action;

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAction extends PortletAction {

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(request, "groupId");
		String privateLayoutParam = request.getParameter("privateLayout");

		String redirect = getRedirect(
			themeDisplay, groupId, privateLayoutParam);

		if (redirect == null) {
			redirect = ParamUtil.getString(request, "redirect");

			SessionErrors.add(
				request, NoSuchLayoutSetException.class.getName(),
				new NoSuchLayoutSetException(
					"{groupId=" + groupId + ",privateLayout=" +
						privateLayoutParam + "}"));
		}

		response.sendRedirect(redirect);

		return null;
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String privateLayoutParam = actionRequest.getParameter("privateLayout");

		String redirect = getRedirect(
			themeDisplay, groupId, privateLayoutParam);

		if (redirect == null) {
			redirect = ParamUtil.getString(actionRequest, "redirect");

			SessionErrors.add(
				actionRequest, NoSuchLayoutSetException.class.getName(),
				new NoSuchLayoutSetException(
					"{groupId=" + groupId + ",privateLayout=" +
						privateLayoutParam + "}"));
		}

		actionResponse.sendRedirect(redirect);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward("portlet.my_places.view");
	}

	protected List<Layout> getLayouts(long groupId, boolean privateLayout)
		throws Exception {

		return LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	protected String getRedirect(
			ThemeDisplay themeDisplay, long groupId, String privateLayoutParam)
		throws Exception {

		List<Layout> layouts = null;

		if (privateLayoutParam == null) {
			layouts = getLayouts(groupId, false);

			if (layouts.size() == 0) {
				layouts = getLayouts(groupId, true);
			}
		}
		else {
			boolean privateLayout = GetterUtil.getBoolean(privateLayoutParam);

			layouts = getLayouts(groupId, privateLayout);
		}

		String redirect = null;

		for (Layout layout : layouts) {
			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)){

				redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

				break;
			}
		}

		return redirect;
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}