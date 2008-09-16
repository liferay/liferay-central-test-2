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
import com.liferay.portal.kernel.util.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BrowserSnifferImpl.java.html"><b><i>View Source</i></b></a>
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Eduardo Lundgren
 * @author Nate Cavanaugh
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

	public boolean isAir(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("adobeair") != -1) {
			return true;
		}

		return false;
	}

	public boolean isChrome(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("chrome") != -1) {
			return true;
		}

		return false;
	}

	public boolean isFirefox(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (isMozilla(request)) {
			String uaPattern = "(firefox|minefield|granparadiso|bonecho|" +
				"firebird|phoenix|camino)";

			Pattern pattern = Pattern.compile(uaPattern);
			Matcher match = pattern.matcher(agent);

			if (match.find()) {
				return true;
			}
		}

		return false;
	}

	public boolean isGecko(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("gecko") != -1) {
			return true;
		}

		return false;
	}

	public boolean isIE(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if ((agent.indexOf("msie") != -1) && (agent.indexOf("opera") == -1)) {
			return true;
		}

		return false;
	}

	public boolean isIPhone(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("iphone") != -1) {
			return true;
		}

		return false;
	}

	public boolean isLinux(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("linux") != -1) {
			return true;
		}

		return false;
	}

	public boolean isMobile(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("mobile") != -1) {
			return true;
		}

		return false;
	}

	public boolean isMozilla(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if ((agent.indexOf("mozilla") != -1) &&
				!agent.matches("compatible|webkit")) {
			return true;
		}

		return false;
	}

	public boolean isMac(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("mac") != -1) {
			return true;
		}

		return false;
	}

	public boolean isOpera(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("opera") != -1) {
			return true;
		}

		return false;
	}

	public boolean isRTF(HttpServletRequest request) {
		if ((isIE(request) && majorVersion(request) >= 5.5) ||
			(isMozilla(request) && majorVersion(request) >= 1.3) ||
			(isSafari(request) && majorVersion(request) >= 3.0 &&
			!isMobile(request))) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSafari(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (isWebkit(request) && (agent.indexOf("safari") != -1)) {
			return true;
		}

		return false;
	}

	public boolean isSun(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("sunos") != -1) {
			return true;
		}

		return false;
	}

	public boolean isWebkit(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		String uaPattern = "(khtml|applewebkit)";
		Pattern pattern = Pattern.compile(uaPattern);
		Matcher match = pattern.matcher(agent);

		if (match.find()) {
			return true;
		}

		return false;
	}

	public boolean isWap(HttpServletRequest request) {
		return isWapXHTML(request);
	}

	public boolean isWapXHTML(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("wap.xhtml") != -1) {
			return true;
		}

		return false;
	}

	public boolean isWindows(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		String uaPattern = "(windows|win32|16bit)";
		Pattern pattern = Pattern.compile(uaPattern);
		Matcher match = pattern.matcher(agent);

		if (match.find()) {
			return true;
		}

		return false;
	}

	public boolean isWML(HttpServletRequest request) {
		String agent = _getUserAgent(request);

		if (agent.indexOf("wap.wml") != -1) {
			return true;
		}

		return false;
	}

	public float majorVersion(HttpServletRequest request) {
		float _majorVersion = 0;
		String version = version(request);

		Pattern pattern = Pattern.compile("(\\d+[.]\\d+)");
		Matcher matcher = pattern.matcher(version);

		if (matcher.find()) {
			_majorVersion = Float.parseFloat(matcher.group(1));
		}

		return _majorVersion;
	}

	public String revision(HttpServletRequest request) {
		String _revision = null;
		String agent = _getUserAgent(request);
		Pattern pattern = Pattern.compile(".+(?:rv|it|ra|ie)[\\/: ]([\\d.]+)");
		Matcher matcher = pattern.matcher(agent);

		while (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); ++i) {
				_revision = matcher.group(i);
			}
		}

		return _revision;
	}

	public String version(HttpServletRequest request) {
		String agent = _getUserAgent(request);
		String _version = StringPool.BLANK;

		Pattern versionPattern = Pattern.compile("(?:version)[\\/]([\\d.]+)");
		Matcher varsionMatcher = versionPattern.matcher(agent);

		if (varsionMatcher.find()) {
			_version = varsionMatcher.group(1);
		}
		else if (isFirefox(request)) {
			Pattern pattern =
				Pattern.compile("(?:firefox|minefield)[\\/]([\\d.]+)");

			Matcher matcher = pattern.matcher(agent);

			if (matcher.find()) {
				_version = matcher.group(1);
			}
		}
		else if (isChrome(request)) {
			Pattern pattern = Pattern.compile("(?:chrome)[\\/]([\\d.]+)");
			Matcher matcher = pattern.matcher(agent);

			if (matcher.find()) {
				_version = matcher.group(1);
			}
		}
		else {
			_version = revision(request);
		}

		return _version;
	}

	private String _getUserAgent(HttpServletRequest request) {
		String agent = StringPool.BLANK;

		if (request != null) {
			String agentHeader = request.getHeader(HttpHeaders.USER_AGENT);

			if (agentHeader != null) {
				agent = agentHeader.toLowerCase();
			}
		}

		return agent;
	}

	private static final String _GZIP = "gzip";
}