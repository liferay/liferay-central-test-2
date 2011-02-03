/*
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

package com.liferay.portlet.directory.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Locale;

/**
 * @author Michael C. Han
 */
public class UserAssetRenderer extends BaseAssetRenderer {

	public UserAssetRenderer(User user) {
		_user = user;
	}

	public long getClassPK() {
		return _user.getPrimaryKey();
	}

	public String getDiscussionPath() {
		return null;
	}

	public long getGroupId() {
		//TBD
		return 0;
	}

	public String getSummary(Locale locale) {
		return _user.getFullName();
	}

	public String getTitle(Locale locale) {
		return _user.getFullName();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL editPortletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.ENTERPRISE_ADMIN_USERS);

		editPortletURL.setParameter(
			"struts_action", "/enterprise_admin_users/edit_user");
		editPortletURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));

		return editPortletURL;
	}

	public String getUrlTitle() {
		return _user.getFullName();
	}

	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
			"/directory/view_user?p_u_i_d=" +
					_user.getUserId();
	}

	public long getUserId() {
		return _user.getUserId();
	}

	public String getUuid() {
		return _user.getUuid();
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return UserPermissionUtil.contains(
			permissionChecker, _user.getUserId(), ActionKeys.UPDATE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return UserPermissionUtil.contains(
			permissionChecker, _user.getUserId(), ActionKeys.VIEW);
	}

	public boolean isPrintable() {
		return false;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(WebKeys.USER, _user);

			return "/html/portlet/directory/asset/abstract.jsp";
		}
		else {
			return null;
		}
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/user_icon.png";
	}

	private User _user;

}
