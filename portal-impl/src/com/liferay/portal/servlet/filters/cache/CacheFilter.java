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

package com.liferay.portal.servlet.filters.cache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.SystemProperties;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.servlet.filters.CacheResponse;

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="CacheFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 *
 */
public class CacheFilter extends BasePortalFilter {

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		FileUtil.deltree(_TEMP_DIR);
		FileUtil.mkdirs(_TEMP_DIR);
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		String requestURI = request.getRequestURI();

		CacheResponse cacheResponse = new CacheResponse(
			response, StringPool.UTF8);

		boolean retrieveFromCache = false;

		File tempDataFile = new File(_TEMP_DIR + requestURI + ".data");
		File tempEncodingFile = new File(_TEMP_DIR + requestURI + ".encoding");

		if (tempDataFile.exists() && tempEncodingFile.exists()) {
			FilterConfig filterConfig = getFilterConfig();

			ServletContext servletContext = filterConfig.getServletContext();

			String realPath = servletContext.getRealPath(requestURI);

			File realFile = new File(realPath);

			if (tempDataFile.lastModified() > realFile.lastModified()) {
				retrieveFromCache = true;
			}
		}

		byte[] bytes = null;
		String contentEncoding = null;

		if (_log.isDebugEnabled()) {
			if (retrieveFromCache) {
				_log.debug("Retrieve " + requestURI + " from the cache");
			}
			else {
				_log.debug("Store " + requestURI + " in the cache");
			}
		}

		if (retrieveFromCache) {
			bytes = FileUtil.getBytes(tempDataFile);
			contentEncoding = FileUtil.read(tempEncodingFile);
		}
		else {
			processFilter(
				CacheFilter.class, request, cacheResponse, filterChain);

			bytes = cacheResponse.getData();
			contentEncoding = GetterUtil.getString(
				cacheResponse.getHeader(HttpHeaders.CONTENT_ENCODING));

			FileUtil.write(tempDataFile, bytes);
			FileUtil.write(tempEncodingFile, contentEncoding);
		}

		if (Validator.isNotNull(contentEncoding)) {
			response.addHeader(HttpHeaders.CONTENT_ENCODING, contentEncoding);
		}

		ServletResponseUtil.write(response, bytes);
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/com/liferay/portal/servlet/filters/cache/data";

	private static Log _log = LogFactoryUtil.getLog(CacheFilter.class);

}