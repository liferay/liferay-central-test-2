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

package com.liferay.portal.kernel.security.auth.http;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthManagerUtil {

	public static void generateChallenge(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		HttpAuthorizationHeader httpAuthorizationHeader) {

		getHttpAuthManager().generateChallenge(
			httpServletRequest, httpServletResponse, httpAuthorizationHeader);
	}

	public static long getBasicUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpAuthorizationHeader httpAuthorizationHeader =
			HttpAuthManagerUtil.parse(httpServletRequest);

		if (httpAuthorizationHeader == null) {
			return 0;
		}

		String scheme = httpAuthorizationHeader.getScheme();

		if (!StringUtil.equalsIgnoreCase(
				scheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			return 0;
		}

		return getUserId(httpServletRequest, httpAuthorizationHeader);
	}

	public static long getDigestUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpAuthorizationHeader httpAuthorizationHeader =
			HttpAuthManagerUtil.parse(httpServletRequest);

		if (httpAuthorizationHeader == null) {
			return 0;
		}

		String scheme = httpAuthorizationHeader.getScheme();

		if (!StringUtil.equalsIgnoreCase(
				scheme, HttpAuthorizationHeader.SCHEME_DIGEST)) {

			return 0;
		}

		return getUserId(httpServletRequest, httpAuthorizationHeader);
	}

	public static long getUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		return getHttpAuthManager().getUserId(
			httpServletRequest, httpAuthorizationHeader);
	}

	public static HttpAuthorizationHeader parse(
		HttpServletRequest httpServletRequest) {

		return getHttpAuthManager().parse(httpServletRequest);
	}

	private static HttpAuthManager getHttpAuthManager() {
		return _instance._serviceTracker.getService();
	}

	private HttpAuthManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(HttpAuthManager.class);

		_serviceTracker.open();
	}

	private static final HttpAuthManagerUtil _instance =
		new HttpAuthManagerUtil();

	private final ServiceTracker<?, HttpAuthManager> _serviceTracker;

}