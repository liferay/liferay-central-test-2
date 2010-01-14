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

package com.liferay.portal.servlet.filters.etag;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.filters.CacheResponse;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ETagFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ETagFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		boolean etag = ParamUtil.getBoolean(request, _ETAG, true);

		if (etag) {
			CacheResponse cacheResponse = new CacheResponse(
				response, StringPool.UTF8);

			processFilter(
				ETagFilter.class, request, cacheResponse, filterChain);

			CacheResponseData cacheResponseData = new CacheResponseData(
				cacheResponse.unsafeGetData(), cacheResponse.getContentLength(),
				cacheResponse.getContentType(), cacheResponse.getHeaders());

			if (!ETagUtil.processETag(
					request, response, cacheResponse.unsafeGetData(),
					cacheResponse.getContentLength())) {

				CacheResponseUtil.write(response, cacheResponseData);
			}
		}
		else {
			processFilter(
				ETagFilter.class, request, response, filterChain);
		}
	}

	private static final String _ETAG = "etag";

}