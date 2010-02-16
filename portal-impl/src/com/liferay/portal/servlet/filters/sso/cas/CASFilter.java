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

package com.liferay.portal.servlet.filters.sso.cas;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;

/**
 * <a href="CASFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 */
public class CASFilter extends BasePortalFilter {

	public static String CAS_SERVICE_PARAMETER = "service";
	public static String CAS_TICKET_PARAMETER = "ticket";
	public static String LOGIN = CASFilter.class.getName() + "LOGIN";

	public static void reload(long companyId) {
		_casTicketValidators.remove(companyId);
	}

	protected TicketValidator getCASTicketValidator(long companyId)
		throws Exception {

		TicketValidator validator = _casTicketValidators.get(companyId);

		if (validator == null) {
			String serverUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_URL,
				PropsValues.CAS_SERVER_URL);
			String serverName = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_NAME,
				PropsValues.CAS_SERVER_NAME);
			String loginUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL);

			Cas20ProxyTicketValidator cas20ProxyTicketValidator =
				new Cas20ProxyTicketValidator(serverUrl);

			Map<String, String> parameters = new HashMap<String, String>();

			parameters.put("serverName", serverName);
			parameters.put("casServerUrlPrefix", serverUrl);
			parameters.put("casServerLoginUrl", loginUrl);
			parameters.put("redirectAfterValidation", "false");

			cas20ProxyTicketValidator.setCustomParameters(parameters);

			_casTicketValidators.put(companyId, cas20ProxyTicketValidator);

			return cas20ProxyTicketValidator;
		}

		return validator;
	}

	protected Log getLog() {
		return _log;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.CAS_AUTH_ENABLED,
				PropsValues.CAS_AUTH_ENABLED)) {

			HttpSession session = request.getSession();

			String pathInfo = request.getPathInfo();

			if (pathInfo.indexOf("/portal/logout") != -1) {
				session.invalidate();

				String logoutUrl = PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_LOGOUT_URL,
					PropsValues.CAS_LOGOUT_URL);

				response.sendRedirect(logoutUrl);
			}
			else {
				String login = (String)session.getAttribute(LOGIN);

				String loginUrl = PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_LOGIN_URL,
					PropsValues.CAS_LOGIN_URL);
				String serviceUrl = PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_SERVICE_URL,
					PropsValues.CAS_SERVICE_URL);

				String ticket = request.getParameter(CAS_TICKET_PARAMETER);

				if (Validator.isNull(ticket)) {
					if (Validator.isNotNull(login)) {
						processFilter(
							CASFilter.class, request, response, filterChain);
					}
					else {
						loginUrl = HttpUtil.addParameter(
							loginUrl, CAS_SERVICE_PARAMETER, serviceUrl);

						response.sendRedirect(loginUrl);
					}

					return;
				}

				TicketValidator validator = getCASTicketValidator(companyId);

				Assertion assertion = validator.validate(ticket, serviceUrl);

				if (assertion != null) {
					AttributePrincipal attributePrincipal =
						assertion.getPrincipal();

					login = attributePrincipal.getName();

					session.setAttribute(LOGIN, login);
				}
			}
		}

		processFilter(CASFilter.class, request, response, filterChain);
	}

	private static Log _log = LogFactoryUtil.getLog(CASFilter.class);

	private static Map<Long, TicketValidator> _casTicketValidators =
		new ConcurrentHashMap<Long, TicketValidator>();

}