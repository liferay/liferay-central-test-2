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

package com.liferay.util.servlet.filters;

import com.liferay.util.servlet.Header;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="CacheResponseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CacheResponseUtil {

	public static void addHeaders(
		HttpServletResponse response, Map<String, List<Header>> headers) {

		for (Map.Entry<String, List<Header>> entry : headers.entrySet()) {
			String headerKey = entry.getKey();
			List<Header> headerValues = entry.getValue();

			for (Header header : headerValues) {
				int type = header.getType();

				if (type == Header.DATE_TYPE) {
					response.addDateHeader(headerKey, header.getDateValue());
				}
				else if (type == Header.INTEGER_TYPE) {
					response.addIntHeader(headerKey, header.getIntValue());
				}
				else if (type == Header.STRING_TYPE) {
					response.addHeader(headerKey, header.getStringValue());
				}
			}
		}
	}

	public static void write(
			HttpServletResponse response, CacheResponseData cacheResponseData)
		throws IOException {

		addHeaders(response, cacheResponseData.getHeaders());

		response.setContentType(cacheResponseData.getContentType());

		ServletResponseUtil.write(response, cacheResponseData.getContent(),
			cacheResponseData.getContentLength());
	}

}