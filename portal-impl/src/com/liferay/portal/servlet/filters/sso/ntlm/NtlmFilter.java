/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.sso.ntlm;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.filters.DynamicFilterConfig;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.Config;
import jcifs.UniAddress;

import jcifs.http.NtlmHttpFilter;
import jcifs.http.NtlmSsp;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbSession;

import jcifs.util.Base64;

/**
 * <a href="NtlmFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Marcus Schmidke
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
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

		_filterConfig = new DynamicFilterConfig(filterConfig);
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
			String domainController = _filterConfig.getInitParameter(
				"jcifs.http.domainController");
			String domain = _filterConfig.getInitParameter(
				"jcifs.smb.client.domain");

			String preferencesDomainController = PrefsPropsUtil.getString(
				companyId, PropsKeys.NTLM_DOMAIN_CONTROLLER,
				PropsValues.NTLM_DOMAIN_CONTROLLER);
			String preferencesDomain = PrefsPropsUtil.getString(
				companyId, PropsKeys.NTLM_DOMAIN, PropsValues.NTLM_DOMAIN);

			if (!Validator.equals(
					domainController, preferencesDomainController) ||
				!Validator.equals(domain, preferencesDomain)) {

				domainController = preferencesDomainController;
				domain = preferencesDomain;

				_filterConfig.addInitParameter(
					"jcifs.http.domainController", domainController);
				_filterConfig.addInitParameter(
					"jcifs.smb.client.domain", domain);

				super.init(_filterConfig);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Host " + domainController);
				_log.debug("Domain " + domain);
			}

			// Type 1 NTLM requests from browser can (and should) always
			// immediately be replied to with an Type 2 NTLM response, no
			// matter whether we're yet logging in or whether it is much
			// later in the session.

			String authorization = GetterUtil.getString(
				request.getHeader(HttpHeaders.AUTHORIZATION));

			if (authorization.startsWith("NTLM")) {
				byte[] src = Base64.decode(authorization.substring(5));

				if (src[8] == 1) {
					UniAddress dc = UniAddress.getByName(
						domainController, true);

					byte[] challenge = SmbSession.getChallenge(dc);

					Type1Message type1 = new Type1Message(src);
					Type2Message type2 = new Type2Message(
						type1, challenge, null);

					authorization = Base64.encode(type2.toByteArray());

					response.setHeader(
						HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentLength(0);

					response.flushBuffer();

					// Interrupt filter chain, send response. Browser will
					// immediately post a new request.

					return;
				}
			}

			String path = request.getPathInfo();

			if ((path != null) && path.endsWith("/login")) {
				NtlmPasswordAuthentication ntlm = negotiate(
					request, response, false);

				if (ntlm == null) {
					return;
				}

				String remoteUser = ntlm.getName();

				int pos = remoteUser.indexOf(StringPool.BACK_SLASH);

				if (pos != -1) {
					remoteUser = remoteUser.substring(pos + 1);
				}

				if (_log.isDebugEnabled()) {
					_log.debug("NTLM remote user " + remoteUser);
				}

				request.setAttribute(WebKeys.NTLM_REMOTE_USER, remoteUser);
			}
		}

		processFilter(NtlmPostFilter.class, request, response, filterChain);
	}

	protected NtlmPasswordAuthentication negotiate(
			HttpServletRequest request, HttpServletResponse response,
			boolean skipAuthentication)
		throws Exception {

		NtlmPasswordAuthentication ntlm = null;

		HttpSession session = request.getSession(false);

		String authorization = GetterUtil.getString(
			request.getHeader(HttpHeaders.AUTHORIZATION));

		if (_log.isDebugEnabled()) {
			_log.debug("Authorization header " + authorization);
		}

		if (authorization.startsWith("NTLM ")) {
			String domainController = _filterConfig.getInitParameter(
				"jcifs.http.domainController");

			UniAddress uniAddress = UniAddress.getByName(
				domainController, true);

			if (_log.isDebugEnabled()) {
				_log.debug("Address " + uniAddress);
			}

			byte[] challenge = SmbSession.getChallenge(uniAddress);

			ntlm = NtlmSsp.authenticate(request, response, challenge);

			try {
				SmbSession.logon(uniAddress, ntlm);
			}
			catch (Exception e) {
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentLength(0);

				response.flushBuffer();

				return null;
			}

			session.setAttribute("NtlmHttpAuth", ntlm);
		}
		else {
			if (session != null) {
				ntlm = (NtlmPasswordAuthentication)session.getAttribute(
					"NtlmHttpAuth");
			}

			if (ntlm == null) {
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentLength(0);

				response.flushBuffer();

				return null;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Password authentication " + ntlm);
		}

		return ntlm;
	}

	private static Log _log = LogFactoryUtil.getLog(NtlmFilter.class);

	private DynamicFilterConfig _filterConfig;

}