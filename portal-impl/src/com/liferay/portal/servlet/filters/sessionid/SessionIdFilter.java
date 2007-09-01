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

package com.liferay.portal.servlet.filters.sessionid;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.SystemProperties;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SessionIdFilter.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * http://forum.java.sun.com/thread.jspa?threadID=197150.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SessionIdFilter implements Filter {

	public static final boolean USE_SESSION_ID_FILTER = GetterUtil.getBoolean(
		SystemProperties.get(SessionIdFilter.class.getName()), true);

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			if (USE_SESSION_ID_FILTER) {
				_log.debug(
					"Session id sharing between http and https is enabled");
			}
			else {
				_log.debug(
					"Session id sharing between http and https is disabled");
			}
		}

		if (USE_SESSION_ID_FILTER) {
			HttpServletRequest httpReq = (HttpServletRequest)req;
			HttpServletResponse httpRes = (HttpServletResponse)res;

			SessionIdServletRequest sessionIdReq =
				new SessionIdServletRequest(httpReq, httpRes);

			chain.doFilter(sessionIdReq, httpRes);
		}
		else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

	private static Log _log = LogFactory.getLog(SessionIdFilter.class);

}