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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.PwdGenerator;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="SessionAuthToken.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
 */
public class SessionAuthToken implements AuthToken {

	public SessionAuthToken() {
		_ignoreActions = SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS));
	}

	public void check(HttpServletRequest request) throws PrincipalException {
		String ppid = ParamUtil.getString(request, "p_p_id");

		String portletNamespace = PortalUtil.getPortletNamespace(ppid);

		String strutsAction = ParamUtil.getString(
			request, portletNamespace + "struts_action");

		if (isIgnoreAction(strutsAction)) {
			return;
		}

		String requestAuthenticationToken = ParamUtil.getString(
			request, "p_auth");

		HttpSession session = request.getSession();

		String sessionAuthenticationToken = (String)session.getAttribute(
			WebKeys.AUTHENTICATION_TOKEN);

		if (!requestAuthenticationToken.equals(sessionAuthenticationToken)) {
			throw new PrincipalException("Invalid authentication token");
		}
	}

	public String getToken(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String sessionAuthenticationToken = (String)session.getAttribute(
			WebKeys.AUTHENTICATION_TOKEN);

		if (Validator.isNull(sessionAuthenticationToken)) {
			sessionAuthenticationToken = PwdGenerator.getPassword();

			session.setAttribute(
				WebKeys.AUTHENTICATION_TOKEN, sessionAuthenticationToken);
		}

		return sessionAuthenticationToken;
	}

	protected boolean isIgnoreAction(String strutsAction) {
		return _ignoreActions.contains(strutsAction);
	}

	private Set<String> _ignoreActions;

}