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

package com.liferay.portal.servlet.filters.sso.cas;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.filters.DynamicFilterConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

/**
 * <a href="CASFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 */
public class CASFilter extends BasePortalFilter {

	public static String LOGIN =
		CASFilter.class.getName() + "LOGIN";

	public static void reload(long companyId) {
		_casAuthenticationFilters.remove(companyId);
		_casTicketValidationFilters.remove(companyId);
	}

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = getFilterConfig().getServletContext();
	}

	protected Filter getCASAuthenticationFilter(long companyId)
		throws Exception {

		Filter casAuthenticationFilter = _casAuthenticationFilters.get(
			companyId);

		if (casAuthenticationFilter == null) {
			casAuthenticationFilter = new AuthenticationFilter();

			DynamicFilterConfig dynamicFilterConfig = new DynamicFilterConfig(
				_filterName, _servletContext);

			String serverName = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_NAME,
				PropsValues.CAS_SERVER_NAME);
			String loginUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL);

			dynamicFilterConfig.addInitParameter("serverName", serverName);
			dynamicFilterConfig.addInitParameter("casServerLoginUrl", loginUrl);

			casAuthenticationFilter.init(dynamicFilterConfig);

			_casAuthenticationFilters.put(companyId, casAuthenticationFilter);
		}

		return casAuthenticationFilter;
	}

	protected Filter getCASTicketValidationFilter(long companyId)
		throws Exception {

		Filter casTicketValidationFilter = _casTicketValidationFilters.get(
			companyId);

		if (casTicketValidationFilter == null) {
			casTicketValidationFilter =
				new Cas20ProxyReceivingTicketValidationFilter();

			DynamicFilterConfig dynamicFilterConfig = new DynamicFilterConfig(
				_filterName, _servletContext);

			String serverName = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_NAME,
				PropsValues.CAS_SERVER_NAME);
			String serverUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_URL,
				PropsValues.CAS_SERVER_URL);
			String loginUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL);

			dynamicFilterConfig.addInitParameter("serverName", serverName);
			dynamicFilterConfig.addInitParameter(
				"casServerUrlPrefix", serverUrl);
			dynamicFilterConfig.addInitParameter("casServerLoginUrl", loginUrl);
			dynamicFilterConfig.addInitParameter(
				"redirectAfterValidation", "false");

			casTicketValidationFilter.init(dynamicFilterConfig);

			_casTicketValidationFilters.put(
				companyId, casTicketValidationFilter);
		}

		return casTicketValidationFilter;
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
				Filter casAuthenticationFilter = getCASAuthenticationFilter(
					companyId);

				casAuthenticationFilter.doFilter(
					request, response, filterChain);

				Filter casTicketValidationFilter = getCASTicketValidationFilter(
					companyId);

				casTicketValidationFilter.doFilter(
					request, response, filterChain);

				Assertion assertion = (Assertion)session.getAttribute(
					AbstractCasFilter.CONST_CAS_ASSERTION);

				if (assertion != null) {
					AttributePrincipal attributePrincipal =
						assertion.getPrincipal();

					String loginName = attributePrincipal.getName();

					session.setAttribute(LOGIN, loginName);
				}
			}
		}
		else {
			processFilter(CASFilter.class, request, response, filterChain);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CASFilter.class);

	private static Map<Long, Filter> _casAuthenticationFilters =
		new ConcurrentHashMap<Long, Filter>();
	private static Map<Long, Filter> _casTicketValidationFilters =
		new ConcurrentHashMap<Long, Filter>();

	private String _filterName;
	private ServletContext _servletContext;

}