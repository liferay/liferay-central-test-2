/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.Config;

import jcifs.http.NtlmHttpFilter;

import jcifs.smb.NtlmPasswordAuthentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="NtlmFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class NtlmFilter extends NtlmHttpFilter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		try {
			HttpServletRequest httpReq = (HttpServletRequest)req;
			HttpServletResponse httpRes = (HttpServletResponse)res;

			long companyId = PortalInstances.getCompanyId(httpReq);

			if (PortalLDAPUtil.isNtlmEnabled(companyId)) {
				String domainController = PrefsPropsUtil.getString(
					companyId, PropsUtil.LDAP_BASE_PROVIDER_URL);

				// Remove leading ldap://

				domainController = domainController.substring(
					7, domainController.length());

				// Remove port

				int pos = domainController.lastIndexOf(StringPool.COLON);

				if (pos != -1) {
					domainController = domainController.substring(0, pos);
				}

				Config.setProperty(
					"jcifs.http.domainController", domainController);

				if (_log.isDebugEnabled()) {
					_log.debug("Host " + domainController);
				}

				NtlmPasswordAuthentication ntlm = negotiate(
					httpReq, httpRes, false);

				if (ntlm == null) {
					return;
				}

				String remoteUser = ntlm.getName();

				pos = remoteUser.indexOf(StringPool.BACK_SLASH);

				if (pos != -1) {
					remoteUser = remoteUser.substring(pos + 1);
				}

				if (_log.isDebugEnabled()) {
					_log.debug("NTLM remote user " + remoteUser);
				}

				req.setAttribute(WebKeys.NTLM_REMOTE_USER, remoteUser);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			chain.doFilter(req, res);
		}
	}

	private static Log _log = LogFactory.getLog(NtlmFilter.class);

}