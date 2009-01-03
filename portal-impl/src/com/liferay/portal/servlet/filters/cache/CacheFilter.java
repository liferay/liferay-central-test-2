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

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.SystemProperties;

/**
 * <a href="CacheFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 *
 */
public class CacheFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		String completeURL = HttpUtil.getCompleteURL(request);
		String requestURI = request.getRequestURI();

		File tempFile = new File(
			SystemProperties.get(SystemProperties.TMP_DIR) +
				"/liferay/filter_cache" + requestURI + ".gz");

		File realFile = new File(
			getFilterConfig().getServletContext().getRealPath(requestURI));

		if (!tempFile.exists() || 
			tempFile.lastModified() < realFile.lastModified()) {
			
			if (_log.isDebugEnabled()) {
				_log.debug("Compressing " + completeURL);
			}
			
			processFilter(
					CacheFilter.class, request, response, filterChain);
		}
		else {

			if (_log.isDebugEnabled()) {
				_log.debug("Not compressing " + completeURL);
			}
			
			byte[] compressedBytes = FileUtil.getBytes(tempFile);
			ServletOutputStream output = response.getOutputStream();
			
			response.setContentLength(compressedBytes.length);
			response.addHeader(_CONTENT_ENCODING, _GZIP);
			
			output.write(compressedBytes);
			output.flush();
			output.close();
		}
	
	}

	private static final String _CONTENT_ENCODING = "Content-Encoding";

	private static final String _GZIP = "gzip";
	
	private static Log _log = LogFactoryUtil.getLog(CacheFilter.class);

}