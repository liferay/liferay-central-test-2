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

package com.liferay.portal.servlet.filters.sso.ntlm;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.ntlm.NtlmManager;
import com.liferay.portal.security.ntlm.NtlmUserAccount;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.Config;

import jcifs.http.NtlmHttpFilter;

import jcifs.util.Base64;

/**
 * <a href="NtlmFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Marcus Schmidke
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Marcellus Tavares
 */
public class NtlmFilter extends BasePortalFilter {

	public void init(FilterConfig filterConfig) {
		try {
			NtlmHttpFilter ntlmFilter = new NtlmHttpFilter();

			ntlmFilter.init(filterConfig);

			Properties properties = PropsUtil.getProperties("jcifs.", false);

			Iterator<Map.Entry<Object, Object>> itr =
				properties.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<Object, Object> entry = itr.next();

				String key = (String)entry.getKey();
				String value = (String)entry.getValue();

				Config.setProperty(key, value);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected Log getLog() {
		return _log;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalInstances.getCompanyId(request);

		if (LDAPSettingsUtil.isNtlmEnabled(companyId)) {
			// Type 1 NTLM requests from browser can (and should) always
			// immediately be replied to with an Type 2 NTLM response, no
			// matter whether we're yet logging in or whether it is much
			// later in the session.

			HttpSession session = request.getSession(false);

			String authorization = GetterUtil.getString(
				request.getHeader(HttpHeaders.AUTHORIZATION));

			if (authorization.startsWith("NTLM")) {
				byte[] src = Base64.decode(authorization.substring(5));

				if (src[8] == 1) {
					byte[] challengeMessage = _ntlmManager.negotiate(src);

					authorization = Base64.encode(challengeMessage);

					response.setHeader(
						HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentLength(0);

					response.flushBuffer();

					String remoteAddr = request.getRemoteAddr();

					_serverChallengesMap.put(
						remoteAddr, _ntlmManager.getServerChallenge());

					// Interrupt filter chain, send response. Browser will
					// immediately post a new request.

					return;
				}
				else {
					byte[] serverChallenge = _serverChallengesMap.get(
						request.getRemoteAddr());

					_ntlmManager.setServerChallenge(serverChallenge);

					NtlmUserAccount account = _ntlmManager.authenticate(src);

					if (account == null) {
						return;
					}

					if (_log.isDebugEnabled()) {
						_log.debug("NTLM remote user " + account.getUserName());
					}

					_serverChallengesMap.remove(request.getRemoteAddr());

					request.setAttribute(
						WebKeys.NTLM_REMOTE_USER, account.getUserName());

					if (session != null) {
						session.setAttribute("NtlmUserAccount", account);
					}
				}
			}

			String path = request.getPathInfo();

			if ((path != null) && path.endsWith("/login")) {
				NtlmUserAccount account = null;

				if (session != null) {
					account = (NtlmUserAccount)session.getAttribute(
						"NtlmUserAccount");
				}

				if (account == null) {
					response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "NTLM");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentLength(0);

					response.flushBuffer();

					return;
				}
			}
		}

		processFilter(NtlmPostFilter.class, request, response, filterChain);
	}

	private static Log _log = LogFactoryUtil.getLog(NtlmFilter.class);

	private NtlmManager _ntlmManager = new NtlmManager();

	private Map<String, byte[]> _serverChallengesMap =
		new HashMap<String, byte[]>();

}