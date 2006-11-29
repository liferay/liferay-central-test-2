/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.autologin;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.AutoLoginException;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ProtectedServletRequest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AutoLoginFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AutoLoginFilter implements Filter {

	public void init(FilterConfig config) {
		ServletContext ctx = config.getServletContext();

		_companyId = ctx.getInitParameter("company_id");

		_rootPath = ctx.getInitParameter("root_path");

		if (_rootPath.equals(StringPool.SLASH)) {
			_rootPath = StringPool.BLANK;
		}
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpServletResponse httpRes = (HttpServletResponse)res;

		HttpSession ses = httpReq.getSession();

		String userId = httpReq.getRemoteUser();
		String jUserName = (String)ses.getAttribute("j_username");

		if ((userId == null) && (jUserName == null)) {
			req.setAttribute(WebKeys.COMPANY_ID, _companyId);

			String[] autoLogins = PropsUtil.getArray(
				PropsUtil.AUTO_LOGIN_HOOKS);

			for (int i = 0; i < autoLogins.length; i++) {
				AutoLogin autoLogin =
					(AutoLogin)InstancePool.get(autoLogins[i]);

				try {
					String loginUserId =
						login(httpReq, httpRes, ses, autoLogin);

					if (loginUserId != null) {
						req = new ProtectedServletRequest(httpReq, loginUserId);

						if (GetterUtil.getBoolean(
								PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE))) {

							return;
						}
					}
				}
				catch (AutoLoginException ale) {
					_log.warn(StackTraceUtil.getStackTrace(ale));

					_log.error(ale.getMessage());
				}
			}
		}

		chain.doFilter(req, res);
	}

	public void destroy() {
	}

	protected String login(
			HttpServletRequest req, HttpServletResponse res, HttpSession ses,
			AutoLogin autoLogin)
		throws AutoLoginException, IOException {

		String[] credentials = autoLogin.login(req, res);

		if ((credentials != null) && (credentials.length == 3)) {
			String jUsername = credentials[0];
			String jPassword = credentials[1];
			boolean encPwd = GetterUtil.getBoolean(credentials[2]);

			if (Validator.isNotNull(jUsername) &&
				Validator.isNotNull(jPassword)) {

				ses.setAttribute("j_username", jUsername);

				// Not having access to the unencrypted password
				// will not allow you to connect to external
				// resources that require it (mail server)

				if (encPwd) {
					ses.setAttribute("j_password", jPassword);
				}
				else {
					ses.setAttribute(
						"j_password", PwdEncryptor.encrypt(jPassword));

					ses.setAttribute(WebKeys.USER_PASSWORD, jPassword);
				}

				if (GetterUtil.getBoolean(
						PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE))) {

					res.sendRedirect(_rootPath + "/c/portal/touch_protected");
				}

				return jUsername;
			}
		}

		return null;
	}

	private static Log _log = LogFactory.getLog(AutoLoginFilter.class);

	private String _companyId;
	private String _rootPath;

}