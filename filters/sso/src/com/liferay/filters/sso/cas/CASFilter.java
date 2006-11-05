/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.filters.sso.cas;

import com.liferay.util.GetterUtil;
import com.liferay.util.SystemProperties;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CASFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Michael Young
 *
 */
public class CASFilter extends edu.yale.its.tp.cas.client.filter.CASFilter {

	public static final boolean USE_CAS_FILTER = GetterUtil.getBoolean(
		SystemProperties.get(CASFilter.class.getName()));

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			if (USE_CAS_FILTER) {
				_log.debug("CAS filter is enabled");
			}
			else {
				_log.debug("CAS filter is disabled");
			}
		}
		
		if (USE_CAS_FILTER) {
			super.doFilter(req, res, chain);
		}
		else {
			chain.doFilter(req, res);
		}
	}
	
	private static Log _log = LogFactory.getLog(CASFilter.class);

}