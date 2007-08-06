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

package com.liferay.filters.doubleclick;

import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DoubleClickFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 *
 */
public class DoubleClickFilter implements Filter {

	public static final boolean USE_DOUBLE_CLICK_FILTER = GetterUtil.getBoolean(
		SystemProperties.get(DoubleClickFilter.class.getName()), true);

	public static final String ENCODING = GetterUtil.getString(
			SystemProperties.get("file.encoding"), "UTF-8");

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			if (USE_DOUBLE_CLICK_FILTER) {
				_log.debug("Double click prevention is enabled");
			}
			else {
				_log.debug("Double click prevention is disabled");
			}
		}

		if (USE_DOUBLE_CLICK_FILTER) {
			HttpServletRequest httpReq = (HttpServletRequest)req;
			HttpServletResponse httpRes = (HttpServletResponse)res;

			StopWatch stopWatch = null;

			if (_log.isDebugEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();
			}

			HttpSession ses = httpReq.getSession(false);

			if (ses == null) {
				chain.doFilter(req, res);
			}
			else {
				DoubleClickController controller = null;

				synchronized (ses) {
					controller = (DoubleClickController)ses.getAttribute(
						_CONTROLLER_KEY);

					if (controller == null) {
						controller = new DoubleClickController();

						ses.setAttribute(_CONTROLLER_KEY, controller);
					}
				}

				boolean ok = false;

				try {
					controller.control(httpReq, httpRes, chain);

					ok = true;
				}
				finally {
					if (_log.isDebugEnabled()) {
						String completeURL = Http.getCompleteURL(httpReq);

						if (ok) {
							_log.debug(
								"Double click prevention succeded in " +
									stopWatch.getTime() + " ms for " +
										completeURL);
						}
						else {
							_log.debug(
								"Double click prevention failed in " +
									stopWatch.getTime() + " ms for " +
										completeURL);
						}
					}
				}
			}
		}
		else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

	private static final String _CONTROLLER_KEY =
		DoubleClickFilter.class.getName();

	private static Log _log = LogFactory.getLog(DoubleClickFilter.class);

}