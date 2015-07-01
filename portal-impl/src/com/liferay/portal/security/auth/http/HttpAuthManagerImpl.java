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

package com.liferay.portal.security.auth.http;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManager;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.secure.NonceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthManagerImpl implements HttpAuthManager {

	@Override
	public void generateChallenge(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		HttpAuthorizationHeader httpAuthorizationHeader) {

		if (httpServletRequest == null) {
			throw new IllegalArgumentException(
				"HttpServletRequest is mandatory");
		}

		if (httpServletResponse == null) {
			throw new IllegalArgumentException(
				"HttpServletResponse is mandatory");
		}

		if ((httpAuthorizationHeader == null) ||
			Validator.isBlank(httpAuthorizationHeader.getScheme())) {

			throw new IllegalArgumentException(
				"AuthorizationHeader scheme is mandatory");
		}

		String realm = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_REALM);

		if (Validator.isBlank(realm)) {
			httpAuthorizationHeader.setAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_REALM,
				Portal.PORTAL_REALM);
		}

		String authorizationScheme = httpAuthorizationHeader.getScheme();

		if (StringUtil.equalsIgnoreCase(
				authorizationScheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			generateBasicChallenge(
				httpServletRequest, httpServletResponse,
				httpAuthorizationHeader);
		}
		else if (StringUtil.equalsIgnoreCase(
					authorizationScheme,
					HttpAuthorizationHeader.SCHEME_DIGEST)) {

			generateDigestChallenge(
				httpServletRequest, httpServletResponse,
				httpAuthorizationHeader);
		}
		else {
			throw new UnsupportedOperationException(
				"Authorization scheme " + authorizationScheme +
					" is not supported");
		}
	}

	@Override
	public long getUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		if (httpServletRequest == null) {
			throw new IllegalArgumentException(
				"HttpServletRequest is mandatory");
		}

		if ((httpAuthorizationHeader == null) ||
			Validator.isBlank(httpAuthorizationHeader.getScheme())) {

			throw new IllegalArgumentException(
				"AuthorizationHeader scheme is mandatory");
		}

		String authorizationScheme = httpAuthorizationHeader.getScheme();

		if (StringUtil.equalsIgnoreCase(
				authorizationScheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			return getBasicUserId(httpServletRequest, httpAuthorizationHeader);
		}
		else if (StringUtil.equalsIgnoreCase(
					authorizationScheme,
					HttpAuthorizationHeader.SCHEME_DIGEST)) {

			return getDigestUserId(httpServletRequest, httpAuthorizationHeader);
		}
		else {
			throw new UnsupportedOperationException(
				"Authorization scheme " + authorizationScheme +
					" is not supported");
		}
	}

	@Override
	public HttpAuthorizationHeader parse(
		HttpServletRequest httpServletRequest) {

		if (httpServletRequest == null) {
			throw new IllegalArgumentException(
				"HttpServletRequest is mandatory");
		}

		String authorizationHeader = httpServletRequest.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isBlank(authorizationHeader)) {
			return null;
		}

		String[] authorizationHeaderParts = authorizationHeader.split("\\s");

		String scheme = authorizationHeaderParts[0];

		if (StringUtil.equalsIgnoreCase(
				scheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			return parseBasic(
				httpServletRequest, authorizationHeader,
				authorizationHeaderParts);
		}
		else if (StringUtil.equalsIgnoreCase(
					scheme, HttpAuthorizationHeader.SCHEME_DIGEST)) {

			return parseDigest(
				httpServletRequest, authorizationHeader,
				authorizationHeaderParts);
		}
		else {
			throw new UnsupportedOperationException(
				"Authorization scheme " + scheme + " is not supported");
		}
	}

	protected void generateBasicChallenge(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		HttpAuthorizationHeader httpAuthorizationHeader) {

		httpServletResponse.setHeader(
			HttpHeaders.WWW_AUTHENTICATE, httpAuthorizationHeader.toString());

		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	protected void generateDigestChallenge(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		HttpAuthorizationHeader httpAuthorizationHeader) {

		// Must generate a new nonce for each 401 (RFC2617, 3.2.1)

		long companyId = PortalInstances.getCompanyId(httpServletRequest);

		String remoteAddress = httpServletRequest.getRemoteAddr();

		String nonce = NonceUtil.generate(companyId, remoteAddress);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NONCE, nonce);

		httpServletResponse.setHeader(
			HttpHeaders.WWW_AUTHENTICATE, httpAuthorizationHeader.toString());

		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	protected long getBasicUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		String login = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_USERNAME);

		String password = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_PASSWORD);

		// Strip @uid and @sn for backwards compatibility

		if (login.endsWith("@uid")) {
			int pos = login.indexOf("@uid");

			login = login.substring(0, pos);
		}
		else if (login.endsWith("@sn")) {
			int pos = login.indexOf("@sn");

			login = login.substring(0, pos);
		}

		try {
			return AuthenticatedSessionManagerUtil.getAuthenticatedUserId(
				httpServletRequest, login, password, null);
		}
		catch (AuthException ae) {
		}

		return 0;
	}

	protected long getDigestUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		long userId = 0;

		String username = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_USERNAME);
		String realm = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_REALM);
		String nonce = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NONCE);
		String uri = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_URI);
		String response = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_RESPONSE);

		if (Validator.isNull(username) || Validator.isNull(realm) ||
			Validator.isNull(nonce) || Validator.isNull(uri) ||
			Validator.isNull(response)) {

			return userId;
		}

		if (!realm.equals(Portal.PORTAL_REALM) ||
			!uri.equals(httpServletRequest.getRequestURI())) {

			return userId;
		}

		if (!NonceUtil.verify(nonce)) {
			return userId;
		}

		long companyId = PortalInstances.getCompanyId(httpServletRequest);

		userId = UserLocalServiceUtil.authenticateForDigest(
			companyId, username, realm, nonce, httpServletRequest.getMethod(),
			uri, response);

		return userId;
	}

	protected HttpAuthorizationHeader parseBasic(
		HttpServletRequest httpServletRequest, String authorizationHeader,
		String[] authorizationHeaderParts) {

		String credentials = new String(
			Base64.decode(authorizationHeaderParts[1]));

		String[] loginAndPassword = StringUtil.split(
			credentials, CharPool.COLON);

		String login = HttpUtil.decodeURL(loginAndPassword[0].trim());

		String password = null;

		if (loginAndPassword.length > 1) {
			password = loginAndPassword[1].trim();
		}

		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_BASIC);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_USERNAME, login);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_PASSWORD, password);

		return httpAuthorizationHeader;
	}

	protected HttpAuthorizationHeader parseDigest(
		HttpServletRequest httpServletRequest, String authorizationHeader,
		String[] authorizationHeaderParts) {

		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_DIGEST);

		authorizationHeader = authorizationHeader.substring(
			HttpAuthorizationHeader.SCHEME_DIGEST.length() + 1);

		authorizationHeader = StringUtil.replace(
			authorizationHeader, CharPool.COMMA, CharPool.NEW_LINE);

		UnicodeProperties authorizationProperties = new UnicodeProperties();

		authorizationProperties.fastLoad(authorizationHeader);

		for (Map.Entry<String, String> authorizationProperty :
				authorizationProperties.entrySet()) {

			String key = authorizationProperty.getKey();
			String value = StringUtil.unquote(
				authorizationProperties.getProperty(key));

			httpAuthorizationHeader.setAuthParameter(key, value);
		}

		return httpAuthorizationHeader;
	}

}