/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.secure;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="SecureFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Aug�
 * @author Alexander Chow
 *
 */
public class SecureFilter extends BaseFilter {

	public void init(FilterConfig config) throws ServletException {
		super.init(config);

		_basicAuthEnabled = GetterUtil.getBoolean(
			config.getInitParameter("basic_auth"));

		String propertyPrefix =
			config.getInitParameter("portal_property_prefix");

		String[] hostsAllowedArray = null;

		if (Validator.isNull(propertyPrefix)) {
			hostsAllowedArray = StringUtil.split(
				config.getInitParameter("hosts.allowed"));
			_httpsRequired = GetterUtil.getBoolean(
				config.getInitParameter("https.required"));
		}
		else {
			hostsAllowedArray = PropsUtil.getArray(
				propertyPrefix + "hosts.allowed");
			_httpsRequired = GetterUtil.getBoolean(
				PropsUtil.get(propertyPrefix + "https.required"));
		}

		for (int i = 0; i < hostsAllowedArray.length; i++) {
			_hostsAllowed.add(hostsAllowedArray[i]);
		}
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpServletResponse httpRes = (HttpServletResponse)res;

		String remoteAddr = httpReq.getRemoteAddr();

		if (isAccessAllowed(httpReq)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Access allowed for " + remoteAddr);
			}
		}
		else {
			if (_log.isErrorEnabled()) {
				_log.error("Access denied for " + remoteAddr);
			}

			httpRes.sendError(
				HttpServletResponse.SC_FORBIDDEN,
				"Access denied for " + remoteAddr);

			return;
		}

		if (_log.isDebugEnabled()) {
			if (_httpsRequired) {
				_log.debug("https is required");
			}
			else {
				_log.debug("https is not required");
			}
		}

		String completeURL = HttpUtil.getCompleteURL(httpReq);

		if (_httpsRequired && !httpReq.isSecure()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Securing " + completeURL);
			}

			StringMaker redirectURL = new StringMaker();

			redirectURL.append(Http.HTTPS_WITH_SLASH);
			redirectURL.append(httpReq.getServerName());
			redirectURL.append(httpReq.getServletPath());

			String queryString = httpReq.getQueryString();

			if (Validator.isNotNull(queryString)) {
				redirectURL.append(StringPool.QUESTION);
				redirectURL.append(httpReq.getQueryString());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect to " + redirectURL);
			}

			httpRes.sendRedirect(redirectURL.toString());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Not securing " + completeURL);
			}

			// This basic authentication should only be run if specified by
			// web.xml and JAAS is disabled. Make sure to run this once per
			// session and wrap the request if necessary.

			HttpSession ses = httpReq.getSession();

			long userId = GetterUtil.getLong(
				(String)ses.getAttribute(_AUTHENTICATED_USER));

			if (_basicAuthEnabled && !PropsValues.PORTAL_JAAS_ENABLE) {
				if (userId > 0) {
					req = new ProtectedServletRequest(
						httpReq, String.valueOf(userId));
				}
				else {
					try {
						userId = getBasicAuthUserId(httpReq);
					}
					catch (Exception e) {
						_log.error(e);
					}

					if (userId > 0) {
						String userIdString = String.valueOf(userId);

						req = new ProtectedServletRequest(
							httpReq, userIdString);

						ses.setAttribute(_AUTHENTICATED_USER, userIdString);
					}
					else {
						httpRes.setHeader(
							HttpHeaders.WWW_AUTHENTICATE, _PORTAL_REALM);
				    	httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				    	return;
					}
				}
			}

		    doFilter(SecureFilter.class, req, res, chain);
		}
	}

	protected long getBasicAuthUserId(HttpServletRequest req) throws Exception {
		long userId = 0;

		String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorizationHeader)) {
			return userId;
		}

		String[] authorizationArray = authorizationHeader.split("\\s+");

		String authorization = authorizationArray[0];
		String credentials = new String(Base64.decode(authorizationArray[1]));

		if (!authorization.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
			return userId;
		}

		long companyId = PortalInstances.getCompanyId(req);

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		String authType = company.getAuthType();

		String[] loginAndPassword = StringUtil.split(
			credentials, StringPool.COLON);

		String login = loginAndPassword[0].trim();
		String password = loginAndPassword[1].trim();

		if (login.endsWith("@uid")) {
			authType = CompanyImpl.AUTH_TYPE_ID;

			int pos = login.indexOf("@uid");

			login = login.substring(0, pos);
		}
		else if (login.endsWith("@sn")) {
			authType = CompanyImpl.AUTH_TYPE_SN;

			int pos = login.indexOf("@sn");

			login = login.substring(0, pos);
		}

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, authType, login, password);

		return userId;
	}

	protected boolean isAccessAllowed(HttpServletRequest req) {
		String remoteAddr = req.getRemoteAddr();
		String serverIp = req.getServerName();

		if ((_hostsAllowed.size() > 0) &&
			(!_hostsAllowed.contains(remoteAddr))) {

			if ((serverIp.equals(remoteAddr)) &&
				(_hostsAllowed.contains(_SERVER_IP))) {

				return true;
			}

			return false;
		}
		else {
			return true;
		}
	}

	private static final String _SERVER_IP = "SERVER_IP";

	private static final String _PORTAL_REALM = "Basic realm=\"PortalRealm\"";

	private static final String _AUTHENTICATED_USER =
		SecureFilter.class + "_AUTHENTICATED_USER";

	private static Log _log = LogFactoryUtil.getLog(SecureFilter.class);

	private boolean _basicAuthEnabled;
	private Set<String> _hostsAllowed = new HashSet<String>();
	private boolean _httpsRequired;

}