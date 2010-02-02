/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;

import jcifs.util.Base64;

/**
 * <a href="NtlmPostFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class NtlmPostFilter extends BasePortalFilter {

	protected Log getLog() {
		return _log;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalInstances.getCompanyId(request);

		if (LDAPSettingsUtil.isNtlmEnabled(companyId) &&
			BrowserSnifferUtil.isIe(request) &&
			request.getMethod().equals(HttpMethods.POST)) {

			String authorization = GetterUtil.getString(
				request.getHeader(HttpHeaders.AUTHORIZATION));

			if (authorization.startsWith("NTLM ")) {
				byte[] src = Base64.decode(authorization.substring(5));

				if (src[8] == 1) {
					Type1Message type1 = new Type1Message(src);
					Type2Message type2 = new Type2Message(
						type1, new byte[8], null);

					authorization = Base64.encode(type2.toByteArray());

					response.setHeader(
						HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentLength(0);

					response.flushBuffer();

					return;
				}
			}
		}

		processFilter(NtlmPostFilter.class, request, response, filterChain);
	}

	private static Log _log = LogFactoryUtil.getLog(NtlmPostFilter.class);

}