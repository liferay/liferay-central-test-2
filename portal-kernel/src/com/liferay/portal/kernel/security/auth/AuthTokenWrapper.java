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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="AuthTokenWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
 */
public class AuthTokenWrapper implements AuthToken {

	public AuthTokenWrapper(AuthToken authToken) {
		_authToken = authToken;
	}

	public void check(HttpServletRequest request) throws PortalException {
		_authToken.check(request);
	}

	public String getToken(HttpServletRequest request) {
		return _authToken.getToken(request);
	}

	private AuthToken _authToken;

}