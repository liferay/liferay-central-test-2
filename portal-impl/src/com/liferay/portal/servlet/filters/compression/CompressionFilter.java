/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.compression;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="CompressionFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class CompressionFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		CompressionFilter.class.getName() + "SKIP_FILTER";

	public CompressionFilter() {

		// The compression filter will work on JBoss, Jetty, JOnAS, OC4J, Orion,
		// and Tomcat, but may break on other servers

		if (super.isFilterEnabled()) {
			if (ServerDetector.isJBoss() || ServerDetector.isJetty() ||
				ServerDetector.isJOnAS() || ServerDetector.isOC4J() ||
				ServerDetector.isOrion() || ServerDetector.isTomcat()) {

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

			// The exclusive state is used to stream binary content.
			// Compressing binary content through a servlet filter is bad on
			// performance because the user will not start downloading the
			// content until the entire content is compressed.

			String windowState = ParamUtil.getString(request, "p_p_state");

			if (windowState.equals("exclusive")) {
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
			FilterChain chain)
		throws IOException, ServletException {

		String completeURL = HttpUtil.getCompleteURL(request);

		if (isCompress(request) && !isInclude(request) &&
			BrowserSnifferUtil.acceptsGzip(request) &&
			!isAlreadyFiltered(request)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Compressing " + completeURL);
			}

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			CompressionResponse compressionResponse =
				new CompressionResponse(response);

			processFilter(
				CompressionFilter.class, request, compressionResponse, chain);

			compressionResponse.finishResponse();
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Not compressing " + completeURL);
			}

			processFilter(CompressionFilter.class, request, response, chain);
		}
	}

	private static final String _COMPRESS = "compress";

	private static Log _log = LogFactoryUtil.getLog(CompressionFilter.class);

	private boolean _filterEnabled;

}