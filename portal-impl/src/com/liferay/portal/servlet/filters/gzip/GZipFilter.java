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

package com.liferay.portal.servlet.filters.gzip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="GZipFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class GZipFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		GZipFilter.class.getName() + "SKIP_FILTER";

	public GZipFilter() {

		// The compression filter will work on JBoss, Jetty, JOnAS, OC4J, and
		// Tomcat, but may break on other servers

		if (super.isFilterEnabled()) {
			if (ServerDetector.isJBoss() || ServerDetector.isJetty() ||
				ServerDetector.isJOnAS() || ServerDetector.isOC4J() ||
				ServerDetector.isTomcat()) {

				_filterEnabled = true;
			}
			else {
				_filterEnabled = false;
			}
		}
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isCompress(HttpServletRequest request) {
		if (!ParamUtil.get(request, _COMPRESS, true)) {
			return false;
		}
		else {

			// Modifying binary content through a servlet filter under certain
			// conditions is bad on performance the user will not start
			// downloading the content until the entire content is modified.

			String lifecycle = ParamUtil.getString(request, "p_p_lifecycle");

			if ((lifecycle.equals("1") &&
				 LiferayWindowState.isExclusive(request)) ||
				lifecycle.equals("2")) {

				return false;
			}
			else {
				return true;
			}
		}
	}

	protected boolean isFilterEnabled() {
		return _filterEnabled;
	}

	protected boolean isInclude(HttpServletRequest request) {
		String uri = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		if (uri == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (isCompress(request) && !isInclude(request) &&
			BrowserSnifferUtil.acceptsGzip(request) &&
			!isAlreadyFiltered(request)) {

			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Compressing " + completeURL);
			}

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			GZipResponse gZipResponse = new GZipResponse(response);

			processFilter(GZipFilter.class, request, gZipResponse, filterChain);

			gZipResponse.finishResponse();
		}
		else {
			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Not compressing " + completeURL);
			}

			processFilter(
				GZipFilter.class, request, response, filterChain);
		}
	}

	private static final String _COMPRESS = "compress";

	private static Log _log = LogFactoryUtil.getLog(GZipFilter.class);

	private boolean _filterEnabled;

}