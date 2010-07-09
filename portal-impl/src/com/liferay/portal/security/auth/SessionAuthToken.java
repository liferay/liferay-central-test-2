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
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;
import com.liferay.util.PwdGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Amos Fong
 */
public class SessionAuthToken implements AuthToken {

	public SessionAuthToken() {
		_ignoreActions = SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS));
	}

	public void check(HttpServletRequest request) throws PrincipalException {
		if (isIgnoreAction(request)) {
			return;
		}

		String requestAuthenticationToken = ParamUtil.getString(
			request, "p_auth");

		String sessionAuthenticationToken = getSessionAuthenticationToken(
			request, _PORTAL);

		String propertiesAuthenticatonTokenSharedSecret = Encryptor.digest(
			PropsValues.AUTH_TOKEN_SHARED_SECRET);

		String requestAuthenticatonTokenSharedSecret = ParamUtil.getString(
			request, "p_auth_secret");

		if (!requestAuthenticationToken.equals(sessionAuthenticationToken) &&
			!requestAuthenticatonTokenSharedSecret.equals(
				propertiesAuthenticatonTokenSharedSecret)) {

			throw new PrincipalException("Invalid authentication token");
		}
	}

	public String getToken(HttpServletRequest request) {
		return getSessionAuthenticationToken(request, _PORTAL);
	}

	public String getToken(
		HttpServletRequest request, long plid, String portletId) {

		return getSessionAuthenticationToken(
			request, PortletPermissionUtil.getPrimaryKey(plid, portletId));
	}

	protected String getSessionAuthenticationToken(
		HttpServletRequest request, String key) {

		Map<String, String> sessionAuthenticationTokensMap =
			getSessionAuthenticationTokensMap(request);

		String sessionAuthenticationToken = sessionAuthenticationTokensMap.get(
			key);

		if (Validator.isNull(sessionAuthenticationToken)) {
			sessionAuthenticationToken = PwdGenerator.getPassword();

			sessionAuthenticationTokensMap.put(key, sessionAuthenticationToken);
		}

		return sessionAuthenticationToken;
	}

	protected Map<String, String> getSessionAuthenticationTokensMap(
		HttpServletRequest request) {

		HttpSession session = request.getSession();

		Map<String, String> sessionAuthenticationTokensMap =
			(Map<String, String>)session.getAttribute(
				WebKeys.AUTHENTICATION_TOKEN);

		if (sessionAuthenticationTokensMap == null) {
			sessionAuthenticationTokensMap = new HashMap<String, String>();

			session.setAttribute(
				WebKeys.AUTHENTICATION_TOKEN, sessionAuthenticationTokensMap);
		}

		return sessionAuthenticationTokensMap;
	}

	protected boolean isIgnoreAction(HttpServletRequest request) {
		String ppid = ParamUtil.getString(request, "p_p_id");

		String portletNamespace = PortalUtil.getPortletNamespace(ppid);

		String strutsAction = ParamUtil.getString(
			request, portletNamespace + "struts_action");

		return isIgnoreAction(strutsAction);
	}

	protected boolean isIgnoreAction(String strutsAction) {
		return _ignoreActions.contains(strutsAction);
	}

	private static final String _PORTAL = "PORTAL";

	private Set<String> _ignoreActions;

}