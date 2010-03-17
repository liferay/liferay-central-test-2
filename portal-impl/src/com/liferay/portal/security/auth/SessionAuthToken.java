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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.PwdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="SessionAuthToken.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
 */
public class SessionAuthToken implements AuthToken {

	public void check(HttpServletRequest request) throws PrincipalException {
		HttpSession session = request.getSession();

		String authenticationToken = (String)session.getAttribute(
			WebKeys.AUTHENTICATION_TOKEN);

		if (!authenticationToken.equals(
				ParamUtil.getString(request, Constants.AUTHENTICATION_TOKEN))) {

			String strutsAction = ParamUtil.getString(request, "struts_action");

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Authentication token does not validate for action: " +
						strutsAction);
			}

			throw new PrincipalException();
		}
	}

	public String getToken(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String authenticationToken = (String)session.getAttribute(
			WebKeys.AUTHENTICATION_TOKEN);

		if (Validator.isNull(authenticationToken)) {
			authenticationToken = PwdGenerator.getPassword();

			session.setAttribute(
				WebKeys.AUTHENTICATION_TOKEN, authenticationToken);
		}

		return authenticationToken;
	}

	private static Log _log = LogFactoryUtil.getLog(SessionAuthToken.class);

}