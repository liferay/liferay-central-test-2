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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SiteMinderLogoutAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class SiteMinderLogoutAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response) {
		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!LDAPSettingsUtil.isSiteMinderEnabled(companyId)) {
				return;
			}

			String domain = CookieKeys.getDomain(request);

			Cookie smSessionCookie = new Cookie(_SMSESSION, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				smSessionCookie.setDomain(domain);
			}

			smSessionCookie.setMaxAge(0);
			smSessionCookie.setPath(StringPool.SLASH);

			Cookie smIdentityCookie = new Cookie(_SMIDENTITY, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				smIdentityCookie.setDomain(domain);
			}

			smIdentityCookie.setMaxAge(0);
			smIdentityCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, smSessionCookie);
			CookieKeys.addCookie(request, response, smIdentityCookie);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final String _SMSESSION = "SMSESSION";

	private static final String _SMIDENTITY = "SMIDENTITY";

	private static Log _log =
		LogFactoryUtil.getLog(SiteMinderLogoutAction.class);

}