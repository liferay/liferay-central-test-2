/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.sso.facebook.connect.portlet.action;

import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"mvc.command.name=/login/associate_facebook_user",
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
	},
	service = MVCRenderCommand.class
)
public class AssociateFacebookUserMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_facebookConnect.isEnabled(themeDisplay.getCompanyId())) {
			throw new PortletException(
				new PrincipalException.MustBeEnabled(
					themeDisplay.getCompanyId(),
					FacebookConnect.class.getName()));
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				PortalUtil.getHttpServletRequest(renderRequest));

		HttpSession session = httpServletRequest.getSession(true);

		long facebookIncompleteUserId = ParamUtil.getLong(
			renderRequest, "userId");

		if (!Validator.isNull(facebookIncompleteUserId)) {
			User user = _userLocalService.fetchUser(facebookIncompleteUserId);

			return renderUpdateAccount(renderRequest, user);
		}

		// This situation might happen if the browser back button is used

		return "/login.jsp";
	}

	protected String renderUpdateAccount(
			PortletRequest portletRequest, User user)
		throws PortletException {

		portletRequest.setAttribute("selUser", user);

		return "/update_account.jsp";
	}

	@Reference(unbind = "-")
	protected void setFacebookConnect(FacebookConnect facebookConnect) {
		_facebookConnect = facebookConnect;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile FacebookConnect _facebookConnect;
	private volatile UserLocalService _userLocalService;

}