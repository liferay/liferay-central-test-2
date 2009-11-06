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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 */
public class CASFilter extends BasePortalFilter {

	public static void reload(long companyId) {
		_casAuthenticationFilters.remove(companyId);
		_casTicketValidationFilters.remove(companyId);
	}

	protected Filter getCASAuthenticationFilter(long companyId)
		throws Exception {

		AuthenticationFilter casAuthenticationFilter =
			_casAuthenticationFilters.get(companyId);

		if (casAuthenticationFilter == null) {
			casAuthenticationFilter = new AuthenticationFilter ();
			DynamicFilterConfig config = new DynamicFilterConfig(
				_filterName, _servletContext);

			String serverName = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_NAME,
				PropsValues.CAS_SERVER_NAME);

			config.addInitParameter("serverName", serverName);
			config.addInitParameter("casServerLoginUrl",
				PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_LOGIN_URL,
					PropsValues.CAS_LOGIN_URL));

			casAuthenticationFilter.init(config);
			_casAuthenticationFilters.put(companyId, casAuthenticationFilter);
		}

		return casAuthenticationFilter;
	}

	protected Filter getCASTicketValidationFilter(long companyId)
		throws Exception {

		Cas20ProxyReceivingTicketValidationFilter casTicketValidationFilter =
			_casTicketValidationFilters.get(companyId);

		if (casTicketValidationFilter == null) {
			casTicketValidationFilter =
				new Cas20ProxyReceivingTicketValidationFilter ();

			DynamicFilterConfig config = new DynamicFilterConfig(
				_filterName, _servletContext);
			String serverName = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_NAME,
				PropsValues.CAS_SERVER_NAME);
				config.addInitParameter("serverName", serverName);
			config.addInitParameter("casServerLoginUrl",
				PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_LOGIN_URL,
					PropsValues.CAS_LOGIN_URL));

			config.addInitParameter("casServerUrlPrefix",
				PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_SERVER_URL_PREFIX,
					PropsValues.CAS_SERVER_URL_PREFIX));
			config.addInitParameter("redirectAfterValidation", "false");

			casTicketValidationFilter.init(config);
			_casTicketValidationFilters.put(companyId,
				casTicketValidationFilter);
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
		_servletContext = getFilterConfig().getServletContext();
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.CAS_AUTH_ENABLED,
				PropsValues.CAS_AUTH_ENABLED)) {

			String pathInfo = request.getPathInfo();

			if (pathInfo.indexOf("/portal/logout") != -1) {
				HttpSession session = request.getSession();

				session.invalidate();

				String logoutUrl = PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_LOGOUT_URL,
					PropsValues.CAS_LOGOUT_URL);

				response.sendRedirect(logoutUrl);
			}
			else {
				Filter casAuthenticationFilter =
					getCASAuthenticationFilter(companyId);
				casAuthenticationFilter.doFilter(request, response,
					filterChain);
				Filter Cas20ProxyReceivingTicketValidationFilter =
					getCASTicketValidationFilter(companyId);
				Cas20ProxyReceivingTicketValidationFilter.doFilter(request,
					response, filterChain);

				Assertion assertion =
					(Assertion) request.getSession().getAttribute(
					AbstractCasFilter.CONST_CAS_ASSERTION);
				if (assertion != null) {
					String userName = assertion.getPrincipal().getName();
					request.getSession().setAttribute(CAS_FILTER_USER,
						userName);
				}
			}
		}
		else {
			processFilter(CASFilter.class, request, response, filterChain);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CASFilter.class);
	public static String CAS_FILTER_USER =
		"com.liferay.portal.servlet.filters.sso.cas";

	private static Map<Long, AuthenticationFilter> _casAuthenticationFilters =
		new ConcurrentHashMap<Long, AuthenticationFilter>();

	private static Map<Long, Cas20ProxyReceivingTicketValidationFilter>
		_casTicketValidationFilters = new ConcurrentHashMap
			<Long, Cas20ProxyReceivingTicketValidationFilter>();

	private String _filterName;
	private ServletContext _servletContext;

}