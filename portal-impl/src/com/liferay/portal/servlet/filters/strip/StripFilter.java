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

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
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

			// The exclusive / resource state is used to stream binary content.
			// Compressing binary content through a servlet filter is bad on
			// performance because the user will not start downloading the
			// content until the entire content is compressed.

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
			FilterChain chain)
		throws IOException, ServletException {

		String completeURL = HttpUtil.getCompleteURL(request);

		if (isStrip(request) && !isInclude(request) &&
			!isAlreadyFiltered(request)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Stripping " + completeURL);
			}

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			StripResponse stripResponse = new StripResponse(response);

			processFilter(StripFilter.class, request, stripResponse, chain);

			String contentType = GetterUtil.getString(
				stripResponse.getContentType());

			byte[] oldByteArray = stripResponse.getData();

			if ((oldByteArray != null) && (oldByteArray.length > 0)) {
				byte[] newByteArray = new byte[oldByteArray.length];
				int newByteArrayPos = 0;

				if (_log.isDebugEnabled()) {
					_log.debug("Stripping content of type " + contentType);
				}

				if (contentType.toLowerCase().indexOf("text/") != -1) {
					boolean ignore = false;
					char prevChar = '\n';

					for (int i = 0; i < oldByteArray.length; i++) {
						byte b = oldByteArray[i];
						char c = (char)b;

						if (c == '<') {

							// Ignore text inside certain HTML tags.

							if (!ignore) {

								// Check for <pre>

								if ((i + 4) < oldByteArray.length) {
									char c1 = (char)oldByteArray[i + 1];
									char c2 = (char)oldByteArray[i + 2];
									char c3 = (char)oldByteArray[i + 3];
									char c4 = (char)oldByteArray[i + 4];

									if (((c1 == 'p') || (c1 == 'P')) &&
										((c2 == 'r') || (c2 == 'R')) &&
										((c3 == 'e') || (c3 == 'E')) &&
										((c4 == '>'))) {

										ignore = true;
									}
								}

								// Check for <textarea

								if (!ignore &&
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

									if (((c1 == 't') || (c1 == 'T')) &&
										((c2 == 'e') || (c2 == 'E')) &&
										((c3 == 'x') || (c3 == 'X')) &&
										((c4 == 't') || (c4 == 'T')) &&
										((c5 == 'a') || (c5 == 'A')) &&
										((c6 == 'r') || (c6 == 'R')) &&
										((c7 == 'e') || (c7 == 'E')) &&
										((c8 == 'a') || (c8 == 'A')) &&
										((c9 == ' '))) {

										ignore = true;
									}
								}
							}
							else if (ignore) {

								// Check for </pre>

								if ((i + 5) < oldByteArray.length) {
									char c1 = (char)oldByteArray[i + 1];
									char c2 = (char)oldByteArray[i + 2];
									char c3 = (char)oldByteArray[i + 3];
									char c4 = (char)oldByteArray[i + 4];
									char c5 = (char)oldByteArray[i + 5];

									if (((c1 == '/')) &&
										((c2 == 'p') || (c2 == 'P')) &&
										((c3 == 'r') || (c3 == 'R')) &&
										((c4 == 'e') || (c4 == 'E')) &&
										((c5 == '>'))) {

										ignore = false;
									}
								}

								// Check for </textarea>

								if (ignore &&
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

									if (((c1 == '/')) &&
										((c2 == 't') || (c2 == 'T')) &&
										((c3 == 'e') || (c3 == 'E')) &&
										((c4 == 'x') || (c4 == 'X')) &&
										((c5 == 't') || (c5 == 'T')) &&
										((c6 == 'a') || (c6 == 'A')) &&
										((c7 == 'r') || (c7 == 'R')) &&
										((c8 == 'e') || (c8 == 'E')) &&
										((c9 == 'a') || (c9 == 'A')) &&
										((c10 == '>'))) {

										ignore = false;
									}
								}
							}
						}

						if ((!ignore) &&
							((c == '\n') || (c == '\r') || (c == '\t'))) {

							if ((i + 1) == oldByteArray.length) {
							}

							if ((prevChar == '\n') || (prevChar == '\r')) {
							}
							else {
								if (c != '\t') {
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

			processFilter(StripFilter.class, request, response, chain);
		}
	}

	private static final String _STRIP = "strip";

	private static Log _log = LogFactoryUtil.getLog(StripFilter.class);

}