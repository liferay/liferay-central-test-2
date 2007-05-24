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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
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

		if (_log.isDebugEnabled()) {
			_log.debug("Received " + httpReq.getRequestURL());
		}

		long companyId = PortalInstances.getCompanyId(httpReq);

		if (_log.isDebugEnabled()) {
			_log.debug("Company id " + companyId);
		}

		String contextPath = PortalUtil.getPathContext();

		String friendlyURL = httpReq.getRequestURI().toLowerCase();

		if ((!contextPath.equals(StringPool.SLASH)) &&
			(friendlyURL.indexOf(contextPath) != -1)) {

			friendlyURL = friendlyURL.substring(
				contextPath.length(), friendlyURL.length());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Friendly URL " + friendlyURL);
		}

		String redirect = null;

		if (USE_VIRTUAL_HOST_FILTER && isValidFriendlyURL(friendlyURL)) {
			String mainPath = PortalUtil.PATH_MAIN;

			LayoutSet layoutSet = (LayoutSet)req.getAttribute(
				WebKeys.VIRTUAL_HOST_LAYOUT_SET);

			if (layoutSet != null) {
				try {
					redirect = PortalUtil.getLayoutActualURL(
						layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
						mainPath, friendlyURL);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}

		if (redirect != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Redirect to " + redirect);
			}

			RequestDispatcher rd = _ctx.getRequestDispatcher(redirect);

			rd.forward(req, res);
		}
		else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

	protected boolean isValidFriendlyURL(String friendlyURL) {
		if (PortalInstances.isIgnorePath(friendlyURL) ||
			friendlyURL.startsWith(_PATH_IMAGE)) {

			return false;
		}

		int code = LayoutImpl.validateFriendlyURL(friendlyURL);

		if ((code > -1) &&
			(code != LayoutFriendlyURLException.ENDS_WITH_SLASH)) {

			return false;
		}

		return true;
	}

	private static Log _log = LogFactory.getLog(VirtualHostFilter.class);

	private static String _PATH_IMAGE = "/image/";

	private ServletContext _ctx;

}