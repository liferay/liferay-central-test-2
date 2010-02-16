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

package com.liferay.portal.servlet.filters.validhtml;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ValidHtmlFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class ValidHtmlFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		ValidHtmlFilter.class.getName() + "SKIP_FILTER";

	protected String getContent(HttpServletRequest request, String content) {
		content = StringUtil.replaceLast(
			content, _CLOSE_BODY, StringPool.BLANK);
		content = StringUtil.replaceLast(
			content, _CLOSE_HTML, _CLOSE_BODY + _CLOSE_HTML);

		return content;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (isAlreadyFiltered(request)) {
			processFilter(
				ValidHtmlFilter.class, request, response, filterChain);

			return;
		}

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);

			_log.debug("Ensuring valid HTML " + completeURL);
		}

		StringServletResponse stringServerResponse = new StringServletResponse(
			response);

		processFilter(
			ValidHtmlFilter.class, request, stringServerResponse, filterChain);

		String content = getContent(request, stringServerResponse.getString());

		ServletResponseUtil.write(response, content);
	}

	private static final String _CLOSE_BODY = "</body>";

	private static final String _CLOSE_HTML = "</html>";

	private static Log _log = LogFactoryUtil.getLog(ValidHtmlFilter.class);

}