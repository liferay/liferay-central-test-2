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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BrowserSnifferImpl.java.html"><b><i>View Source</i></b></a>
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BrowserSnifferImpl implements BrowserSniffer {

	public boolean acceptsGzip(HttpServletRequest request) {
		String acceptEncoding = request.getHeader(HttpHeaders.ACCEPT_ENCODING);

		if ((acceptEncoding != null) &&
			(acceptEncoding.indexOf(_GZIP) != -1)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (agent.indexOf("msie") != -1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie_4(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_ie(request) && (agent.indexOf("msie 4") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie_5(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_ie(request) && (agent.indexOf("msie 5.0") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie_5_5(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_ie(request) && (agent.indexOf("msie 5.5") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie_5_5_up(HttpServletRequest request) {
		if (is_ie(request) && !is_ie_4(request) && !is_ie_5(request)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie_6(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_ie(request) && (agent.indexOf("msie 6.0") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_ie_7(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_ie(request) && (agent.indexOf("msie 7.0") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_linux(HttpServletRequest request) {
		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (agent.matches(".*linux.*")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_mozilla(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if ((agent.indexOf("mozilla") != -1) &&
			(agent.indexOf("spoofer") == -1) &&
			(agent.indexOf("compatible") == -1) &&
			(agent.indexOf("opera") == -1) &&
			(agent.indexOf("webtv") == -1) &&
			(agent.indexOf("hotjava") == -1)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_mozilla_1_3_up(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_mozilla(request)) {
			int pos = agent.indexOf("gecko/");

			if (pos == -1) {
				return false;
			}
			else {
				String releaseDate = agent.substring(pos + 6, agent.length());

				if (releaseDate.compareTo("20030210") > 0) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean is_ns_4(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (!is_ie(request) && (agent.indexOf("mozilla/4.") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_rtf(HttpServletRequest request) {
		if (is_ie_5_5_up(request) || is_mozilla_1_3_up(request) ||
			(is_safari_3(request) && !is_safari_mobile(request))) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_safari(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (agent.indexOf("safari") != -1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_safari_3(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_safari(request) && (agent.indexOf("version/3.") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_safari_mobile(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);

		if (agent == null) {
			return false;
		}

		agent = agent.toLowerCase();

		if (is_safari(request) && (agent.indexOf("mobile") != -1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_wap(HttpServletRequest request) {
		return is_wap_xhtml(request);
	}

	public boolean is_wap_xhtml(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String accept = request.getHeader(HttpHeaders.ACCEPT);

		if (accept == null) {
			return false;
		}

		accept = accept.toLowerCase();

		if (accept.indexOf("wap.xhtml") != -1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean is_wml(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String accept = request.getHeader(HttpHeaders.ACCEPT);

		if (accept == null) {
			return false;
		}

		accept = accept.toLowerCase();

		if (accept.indexOf("wap.wml") != -1) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final String _GZIP = "gzip";

}