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

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="StripFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class StripFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		StripFilter.class.getName() + "SKIP_FILTER";

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
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

	protected boolean isStrip(HttpServletRequest request) {
		if (!ParamUtil.getBoolean(request, _STRIP, true)) {
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

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		String completeURL = HttpUtil.getCompleteURL(request);

		if (isStrip(request) && !isInclude(request) &&
			!isAlreadyFiltered(request)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Stripping " + completeURL);
			}

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			StripResponse stripResponse = new StripResponse(response);

			processFilter(
				StripFilter.class, request, stripResponse, filterChain);

			String contentType = GetterUtil.getString(
				stripResponse.getContentType()).toLowerCase();

			byte[] oldByteArray = stripResponse.getData();

			if ((oldByteArray != null) && (oldByteArray.length > 0)) {
				byte[] newByteArray = new byte[oldByteArray.length];
				int newByteArrayPos = 0;

				if (_log.isDebugEnabled()) {
					_log.debug("Stripping content of type " + contentType);
				}

				if (contentType.indexOf("text/") != -1) {
					int state = 0;
					char prevChar = CharPool.LESS_THAN;

					for (int i = 0; i < oldByteArray.length; i++) {
						byte b = oldByteArray[i];
						char c = (char)b;

						if (c == CharPool.LESS_THAN) {

							// Ignore text inside certain HTML tags.

							if (state == 0) {

								// Check for <pre>

								if ((i + 4) < oldByteArray.length) {
									char c1 = (char)oldByteArray[i + 1];
									char c2 = (char)oldByteArray[i + 2];
									char c3 = (char)oldByteArray[i + 3];
									char c4 = (char)oldByteArray[i + 4];

									if (((c1 == CharPool.LOWER_CASE_P) ||
										 (c1 == CharPool.UPPER_CASE_P)) &&
										((c2 == CharPool.LOWER_CASE_R) ||
										 (c2 == CharPool.UPPER_CASE_R)) &&
										((c3 == CharPool.LOWER_CASE_E) ||
										 (c3 == CharPool.UPPER_CASE_E)) &&
										((c4 == CharPool.GREATER_THAN))) {

										state = 1;
									}
								}

								// Check for <textarea

								if ((state == 0) &&
									((i + 9) < oldByteArray.length)) {

									char c1 = (char)oldByteArray[i + 1];
									char c2 = (char)oldByteArray[i + 2];
									char c3 = (char)oldByteArray[i + 3];
									char c4 = (char)oldByteArray[i + 4];
									char c5 = (char)oldByteArray[i + 5];
									char c6 = (char)oldByteArray[i + 6];
									char c7 = (char)oldByteArray[i + 7];
									char c8 = (char)oldByteArray[i + 8];
									char c9 = (char)oldByteArray[i + 9];

									if (((c1 == CharPool.LOWER_CASE_T) ||
										 (c1 == CharPool.UPPER_CASE_T)) &&
										((c2 == CharPool.LOWER_CASE_E) ||
										 (c2 == CharPool.UPPER_CASE_E)) &&
										((c3 == CharPool.LOWER_CASE_X) ||
										 (c3 == CharPool.UPPER_CASE_X)) &&
										((c4 == CharPool.LOWER_CASE_T) ||
										 (c4 == CharPool.UPPER_CASE_T)) &&
										((c5 == CharPool.LOWER_CASE_A) ||
										 (c5 == CharPool.UPPER_CASE_A)) &&
										((c6 == CharPool.LOWER_CASE_R) ||
										 (c6 == CharPool.UPPER_CASE_R)) &&
										((c7 == CharPool.LOWER_CASE_E) ||
										 (c7 == CharPool.UPPER_CASE_E)) &&
										((c8 == CharPool.LOWER_CASE_A) ||
										 (c8 == CharPool.UPPER_CASE_A)) &&
										((c9 == CharPool.SPACE))) {

										state = 1;
									}
								}
							}
							else if (state == 1) {

								// Check for </pre>

								if ((i + 5) < oldByteArray.length) {
									char c1 = (char)oldByteArray[i + 1];
									char c2 = (char)oldByteArray[i + 2];
									char c3 = (char)oldByteArray[i + 3];
									char c4 = (char)oldByteArray[i + 4];
									char c5 = (char)oldByteArray[i + 5];

									if (((c1 == CharPool.SLASH)) &&
										((c2 == CharPool.LOWER_CASE_P) ||
										 (c2 == CharPool.UPPER_CASE_P)) &&
										((c3 == CharPool.LOWER_CASE_R) ||
										 (c3 == CharPool.UPPER_CASE_R)) &&
										((c4 == CharPool.LOWER_CASE_E) ||
										 (c4 == CharPool.UPPER_CASE_E)) &&
										((c5 == CharPool.GREATER_THAN))) {

										state = 0;
									}
								}

								// Check for </textarea>

								if ((state == 1) &&
									((i + 10) < oldByteArray.length)) {

									char c1 = (char)oldByteArray[i + 1];
									char c2 = (char)oldByteArray[i + 2];
									char c3 = (char)oldByteArray[i + 3];
									char c4 = (char)oldByteArray[i + 4];
									char c5 = (char)oldByteArray[i + 5];
									char c6 = (char)oldByteArray[i + 6];
									char c7 = (char)oldByteArray[i + 7];
									char c8 = (char)oldByteArray[i + 8];
									char c9 = (char)oldByteArray[i + 9];
									char c10 = (char)oldByteArray[i + 10];

									if (((c1 == CharPool.SLASH)) &&
										((c2 == CharPool.LOWER_CASE_T) ||
										 (c2 == CharPool.UPPER_CASE_T)) &&
										((c3 == CharPool.LOWER_CASE_E) ||
										 (c3 == CharPool.UPPER_CASE_E)) &&
										((c4 == CharPool.LOWER_CASE_X) ||
										 (c4 == CharPool.UPPER_CASE_X)) &&
										((c5 == CharPool.LOWER_CASE_T) ||
										 (c5 == CharPool.UPPER_CASE_T)) &&
										((c6 == CharPool.LOWER_CASE_A) ||
										 (c6 == CharPool.UPPER_CASE_A)) &&
										((c7 == CharPool.LOWER_CASE_R) ||
										 (c7 == CharPool.UPPER_CASE_R)) &&
										((c8 == CharPool.LOWER_CASE_E) ||
										 (c8 == CharPool.UPPER_CASE_E)) &&
										((c9 == CharPool.LOWER_CASE_A) ||
										 (c9 == CharPool.UPPER_CASE_A)) &&
										((c10 == CharPool.GREATER_THAN))) {

										state = 0;
									}
								}
							}
						}

						if ((state == 0) &&
							((c == CharPool.NEW_LINE) ||
							 (c == CharPool.RETURN) ||
							 (c == CharPool.TAB))) {

							if ((i + 1) == oldByteArray.length) {
							}

							if ((prevChar == CharPool.NEW_LINE) ||
								(prevChar == CharPool.RETURN)) {
							}
							else {
								if (c != CharPool.TAB) {
									prevChar = c;
								}

								newByteArray[newByteArrayPos++] = b;
							}
						}
						else {
							prevChar = c;

							newByteArray[newByteArrayPos++] = b;
						}
					}
				}
				else {
					newByteArray = oldByteArray;
					newByteArrayPos = oldByteArray.length;
				}

				ServletResponseUtil.write(
					response, newByteArray, newByteArrayPos);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Not stripping " + completeURL);
			}

			processFilter(StripFilter.class, request, response, filterChain);
		}
	}

	private static final String _STRIP = "strip";

	private static Log _log = LogFactoryUtil.getLog(StripFilter.class);

}