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

package com.liferay.portal.servlet.filters.virtualhost;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="VirtualHostFilter.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This filter is used to provide virtual host functionality. However, this
 * filter is still required even if you do not use virtual hosting because this
 * filter sets the company id in the request so that subsequent calls in the
 * thread have the company id properly set. This filter must also always be the
 * first filter in the list of filters.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 *
 */
public class VirtualHostFilter implements Filter {

	public static final boolean USE_VIRTUAL_HOST_FILTER = GetterUtil.getBoolean(
		SystemProperties.get(VirtualHostFilter.class.getName()), true);

	public void init(FilterConfig config) throws ServletException {
		_ctx = config.getServletContext();
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			if (USE_VIRTUAL_HOST_FILTER) {
				_log.debug("Virtual host is enabled");
			}
			else {
				_log.debug("Virtual host is disabled");
			}
		}

		HttpServletRequest httpReq = (HttpServletRequest)req;

		// Company id needs to always be called here so that it's properly set
		// in subsequent calls

		long companyId = PortalInstances.getCompanyId(httpReq);

		if (_log.isDebugEnabled()) {
			_log.debug("Company id " + companyId);
		}

		if (!USE_VIRTUAL_HOST_FILTER) {
			chain.doFilter(req, res);

			return;
		}

		StringBuffer requestURL = httpReq.getRequestURL();

		if (_log.isDebugEnabled()) {
			_log.debug("Received " + requestURL);
		}

		if (!isValidRequestURL(requestURL)) {
			chain.doFilter(req, res);

			return;
		}

		String contextPath = PortalUtil.getPathContext();

		String friendlyURL = httpReq.getRequestURI().toLowerCase();

		if ((!contextPath.equals(StringPool.SLASH)) &&
			(friendlyURL.indexOf(contextPath) != -1)) {

			friendlyURL = friendlyURL.substring(
				contextPath.length(), friendlyURL.length());
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (_log.isDebugEnabled()) {
			_log.debug("Friendly URL " + friendlyURL);
		}

		if (!isValidFriendlyURL(friendlyURL)) {
			chain.doFilter(req, res);

			return;
		}

		LayoutSet layoutSet = (LayoutSet)req.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (layoutSet != null) {
			try {
				String mainPath = PortalUtil.PATH_MAIN;

				String redirect = PortalUtil.getLayoutActualURL(
					layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
					mainPath, friendlyURL);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect to " + redirect);
				}

				RequestDispatcher rd = _ctx.getRequestDispatcher(redirect);

				rd.forward(req, res);

				return;
			}
			catch (NoSuchLayoutException nsle) {
				nsle.printStackTrace();
				if (_log.isWarnEnabled()) {
					_log.warn(nsle.getMessage());
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		chain.doFilter(req, res);
	}

	public void destroy() {
	}

	protected boolean isValidFriendlyURL(String friendlyURL) {
		if (PortalInstances.isIgnorePath(friendlyURL) ||
			friendlyURL.startsWith(_PATH_C) ||
			friendlyURL.startsWith(_PATH_HTML) ||
			friendlyURL.startsWith(_PATH_IMAGE) ||
			friendlyURL.startsWith(_PATH_WAP)) {

			return false;
		}

		int code = LayoutImpl.validateFriendlyURL(friendlyURL);

		if ((code > -1) &&
			(code != LayoutFriendlyURLException.ENDS_WITH_SLASH)) {

			return false;
		}

		return true;
	}

	protected boolean isValidRequestURL(StringBuffer requestURL) {
		if (requestURL == null) {
			return false;
		}

		String url = requestURL.toString();

		if (url.endsWith(_EXT_C) || url.endsWith(_EXT_CSS) ||
			url.endsWith(_EXT_GIF) || url.endsWith(_EXT_IMAGE_COMPANY_LOGO) ||
			url.endsWith(_EXT_ICO) || url.endsWith(_EXT_JS) ||
			url.endsWith(_EXT_JPEG) || url.endsWith(_EXT_PORTAL_CSS_CACHED) ||
			url.endsWith(_EXT_PORTAL_JAVASCRIPT_CACHED) ||
			url.endsWith(_EXT_PORTAL_LAYOUT) ||
			url.endsWith(_EXT_PORTAL_LOGIN) ||
			url.endsWith(_EXT_PORTAL_LOGOUT) || url.endsWith(_EXT_PNG)) {

			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactory.getLog(VirtualHostFilter.class);

	private static String _EXT_C = "/c";

	private static String _EXT_CSS = ".css";

	private static String _EXT_GIF = ".gif";

	private static String _EXT_IMAGE_COMPANY_LOGO = "/image/company_logo";

	private static String _EXT_ICO = ".ico";

	private static String _EXT_JS = ".js";

	private static String _EXT_JPEG = ".jpeg";

	private static String _EXT_PORTAL_CSS_CACHED = "/portal/css_cached";

	private static String _EXT_PORTAL_JAVASCRIPT_CACHED =
		"/portal/javascript_cached";

	private static String _EXT_PORTAL_LAYOUT = "/portal/layout";

	private static String _EXT_PORTAL_LOGIN = "/portal/login";

	private static String _EXT_PORTAL_LOGOUT = "/portal/logout";

	private static String _EXT_PNG = ".png";

	private static String _PATH_C = "/c/";

	private static String _PATH_HTML = "/html/";

	private static String _PATH_IMAGE = "/image/";

	private static String _PATH_WAP = "/wap/";

	private ServletContext _ctx;

}