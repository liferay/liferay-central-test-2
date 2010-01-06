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

import com.liferay.portal.kernel.concurrent.ConcurrentLRUCache;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.KMPSearch;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.etag.ETagUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.util.ThreadLocalRandomWordGenerator;
import com.liferay.util.servlet.ServletResponseUtil;

import java.security.MessageDigest;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="StripFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class StripFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		StripFilter.class.getName() + "SKIP_FILTER";

	protected int countContinuousWhiteSpace(byte[] oldByteArray, int offset) {
		int count = 0;

		for (int i = offset ; i < oldByteArray.length ; i++) {
			char c = (char)oldByteArray[i];

			if ((c == CharPool.SPACE) || (c == CharPool.TAB) ||
				(c == CharPool.RETURN) || (c == CharPool.NEW_LINE)) {

				count++;
			}
			else{
				return count;
			}
		}

		return count;
	}

	protected boolean hasMarker(byte[] oldByteArray, int pos, byte[] marker) {
		if ((pos + marker.length) >= oldByteArray.length) {
			return false;
		}

		for (int i = 0; i < marker.length; i++) {
			byte c = marker[i];

			byte oldC = oldByteArray[pos + i + 1];

			if ((c != oldC) && (Character.toUpperCase(c) != oldC)) {
				return false;
			}
		}

		return true;
	}

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

	protected int processCSS(
		byte[] oldByteArray, UnsyncByteArrayOutputStream newBytes,
		int currentIndex) {

		int beginIndex = currentIndex + _MARKER_STYLE_OPEN.length + 1;

		int endIndex = KMPSearch.search(
			oldByteArray, beginIndex, _MARKER_STYLE_CLOSE,
			_MARKER_STYLE_CLOSE_NEXTS);

		if (endIndex == -1) {
			_log.error("Missing </style>");

			return currentIndex + 1;
		}

		int newBeginIndex = endIndex + _MARKER_STYLE_CLOSE.length;

		newBeginIndex += countContinuousWhiteSpace(oldByteArray, newBeginIndex);

		String content = new String(
			oldByteArray, beginIndex, endIndex - beginIndex);

		if (Validator.isNull(content)) {
			return newBeginIndex;
		}

		String key;
		MessageDigest messageDigest = _THREAD_LOCAL_MESSAGE_DIGEST.get();
		if (messageDigest == null) {
			key = Integer.toString(content.hashCode());
		}
		else {
			key = new String(messageDigest.digest(content.getBytes()));
		}
		String cachedContent = _concurrentLRUCache.get(key);
		if (cachedContent == null) {
			// Multiple threads may arrive here at same time,
			// we simply let them go, this may waste a few CPU cycles,
			// but can improve concurrency.
			// Hopefully this won't happen that often
			cachedContent = MinifierUtil.minifyCss(content);
			_concurrentLRUCache.put(key, cachedContent);
		}
		if (Validator.isNull(cachedContent)) {
			return newBeginIndex;
		}

		newBytes.write(_STYLE_TYPE_CSS);
		newBytes.write(cachedContent.getBytes());
		newBytes.write(_MARKER_STYLE_CLOSE);

		return newBeginIndex;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (isStrip(request) && !isInclude(request) &&
			!isAlreadyFiltered(request)) {

			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Stripping " + completeURL);
			}

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			StripResponse stripResponse = new StripResponse(response);
			ThreadLocalRandomWordGenerator.reset();
			processFilter(
				StripFilter.class, request, stripResponse, filterChain);

			String contentType = GetterUtil.getString(
				stripResponse.getContentType()).toLowerCase();

			byte[] oldByteArray = stripResponse.getData();

			if ((oldByteArray != null) && (oldByteArray.length > 0)) {
				byte[] newByteArray = null;

				if (_log.isDebugEnabled()) {
					_log.debug("Stripping content of type " + contentType);
				}

				if (contentType.indexOf("text/") != -1) {
					newByteArray = strip(oldByteArray);
				}
				else {
					newByteArray = oldByteArray;
				}

				if (!ETagUtil.processETag(request, response, newByteArray)) {
					response.setContentType(contentType);

					ServletResponseUtil.write(response, newByteArray);
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Not stripping " + completeURL);
			}

			processFilter(StripFilter.class, request, response, filterChain);
		}
	}

	protected int processJavaScript(
		byte[] oldByteArray, UnsyncByteArrayOutputStream newBytes,
		int currentIndex, byte[] openTag) {

		int beginIndex = currentIndex + openTag.length + 1;

		int endIndex = KMPSearch.search(
			oldByteArray, beginIndex, _MARKER_SCRIPT_CLOSE,
			_MARKER_SCRIPT_CLOSE_NEXTS);

		if (endIndex == -1) {
			_log.error("Missing </script>");

			return currentIndex + 1;
		}

		int newBeginIndex = endIndex + _MARKER_SCRIPT_CLOSE.length;

		newBeginIndex += countContinuousWhiteSpace(oldByteArray, newBeginIndex);

		String content = new String(
			oldByteArray, beginIndex, endIndex - beginIndex);

		if (Validator.isNull(content)) {
			return newBeginIndex;
		}

		String key;
		MessageDigest messageDigest = _THREAD_LOCAL_MESSAGE_DIGEST.get();
		if (messageDigest == null) {
			key = Integer.toString(content.hashCode());
		}
		else {
			key = new String(messageDigest.digest(content.getBytes()));
		}
		String cachedContent = _concurrentLRUCache.get(key);
		if (cachedContent == null) {
			// Multiple threads may arrive here at same time,
			// we simply let them go, this may waste a few CPU cycles,
			// but can improve concurrency.
			// Hopefully this won't happen that often
			cachedContent = MinifierUtil.minifyJavaScript(content);
			_concurrentLRUCache.put(key, cachedContent);
		}
		if (Validator.isNull(cachedContent)) {
			return newBeginIndex;
		}

		newBytes.write(_SCRIPT_TYPE_JAVASCRIPT);
		newBytes.write(_CDATA_OPEN);
		newBytes.write(cachedContent.getBytes());
		newBytes.write(_CDATA_CLOSE);
		newBytes.write(_MARKER_SCRIPT_CLOSE);

		return newBeginIndex;
	}

	protected int processPre(
		byte[] oldByteArray, UnsyncByteArrayOutputStream newBytes,
		int currentIndex) {

		int beginIndex = currentIndex + _MARKER_PRE_OPEN.length + 1;

		int endIndex = KMPSearch.search(
			oldByteArray, beginIndex, _MARKER_PRE_CLOSE,
			_MARKER_PRE_CLOSE_NEXTS);

		if (endIndex == -1) {
			_log.error("Missing </pre>");

			return currentIndex + 1;
		}

		int newBeginIndex = endIndex + _MARKER_PRE_CLOSE.length;

		newBytes.write(
			oldByteArray, currentIndex, newBeginIndex - currentIndex);

		newBeginIndex += countContinuousWhiteSpace(oldByteArray, newBeginIndex);

		return newBeginIndex;
	}

	protected int processTextArea(
		byte[] oldByteArray, UnsyncByteArrayOutputStream newBytes,
		int currentIndex) {

		int beginIndex = currentIndex + _MARKER_TEXTAREA_OPEN.length + 1;

		int endIndex = KMPSearch.search(
			oldByteArray, beginIndex, _MARKER_TEXTAREA_CLOSE,
			_MARKER_TEXTAREA_CLOSE_NEXTS);

		if (endIndex == -1) {
			_log.error("Missing </textArea>");

			return currentIndex + 1;
		}

		int newBeginIndex = endIndex + _MARKER_TEXTAREA_CLOSE.length;

		newBytes.write(
			oldByteArray, currentIndex, newBeginIndex - currentIndex);

		newBeginIndex += countContinuousWhiteSpace(oldByteArray, newBeginIndex);

		return newBeginIndex;
	}

	protected byte[] strip(byte[] oldByteArray) {
		UnsyncByteArrayOutputStream newBytes = new UnsyncByteArrayOutputStream(
			(int)(oldByteArray.length * _COMPRESSION_RATE));

		int count = countContinuousWhiteSpace(oldByteArray, 0);

		for (int i = count; i < oldByteArray.length; i++) {
			byte b = oldByteArray[i];

			if (b == CharPool.LESS_THAN) {
				if (hasMarker(oldByteArray, i, _MARKER_PRE_OPEN)) {
					i = processPre(oldByteArray, newBytes, i) - 1;

					continue;
				}
				else if (hasMarker(oldByteArray, i, _MARKER_TEXTAREA_OPEN)) {
					i = processTextArea(oldByteArray, newBytes, i) - 1;

					continue;
				}
				else if (hasMarker(oldByteArray, i, _MARKER_JS_OPEN)) {
					i = processJavaScript(
							oldByteArray, newBytes, i, _MARKER_JS_OPEN) - 1;

					continue;
				}
				else if (hasMarker(oldByteArray, i, _MARKER_SCRIPT_OPEN)) {
					i = processJavaScript(
							oldByteArray, newBytes, i, _MARKER_SCRIPT_OPEN) - 1;

					continue;
				}
				else if (hasMarker(oldByteArray, i, _MARKER_STYLE_OPEN)) {
					i = processCSS(oldByteArray, newBytes, i) - 1;

					continue;
				}
			}
			else if (b == CharPool.GREATER_THAN) {
				newBytes.write(b);

				int spaceCount = countContinuousWhiteSpace(oldByteArray, i + 1);

				if (spaceCount > 0) {
					i = i + spaceCount;

					newBytes.write(CharPool.SPACE);
				}

				continue;
			}

			int spaceCount = countContinuousWhiteSpace(oldByteArray, i);

			if (spaceCount > 0) {
				newBytes.write(CharPool.SPACE);

				i = i + spaceCount - 1;
			}
			else {
				newBytes.write(b);
			}
		}

		return newBytes.toByteArray();
	}

	private final ConcurrentLRUCache<String, String> _concurrentLRUCache =
		new ConcurrentLRUCache<String, String>(_MAX_CACHE_SIZE);

	private static final ThreadLocal<MessageDigest>
		_THREAD_LOCAL_MESSAGE_DIGEST = new ThreadLocal<MessageDigest>() {

		protected MessageDigest initialValue() {
			try {
				return (MessageDigest) MessageDigest.getInstance("MD5").clone();
			}
			catch(Exception e) {
				_log.warn("Fail to load MD5 MessageDigest, " +
					"will use String.hashCode() instead.");
				return null;
			}
		}

	};

	private static final int _MAX_CACHE_SIZE = 1000;

	private static final byte[] _CDATA_CLOSE = "/*]]>*/".getBytes();

	private static final byte[] _CDATA_OPEN = "/*<![CDATA[*/".getBytes();

	private static final double _COMPRESSION_RATE = 0.7;

	private static final byte[] _MARKER_JS_OPEN =
		"script type=\"text/javascript\">".getBytes();

	private static final byte[] _MARKER_PRE_CLOSE = "/pre>".getBytes();

	private static final int[] _MARKER_PRE_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_PRE_CLOSE);

	private static final byte[] _MARKER_PRE_OPEN = "pre>".getBytes();

	private static final byte[] _MARKER_SCRIPT_CLOSE = "</script>".getBytes();

	private static final int[] _MARKER_SCRIPT_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_SCRIPT_CLOSE);

	private static final byte[] _MARKER_SCRIPT_OPEN = "script>".getBytes();

	private static final byte[] _MARKER_STYLE_CLOSE = "</style>".getBytes();

	private static final int[] _MARKER_STYLE_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_STYLE_CLOSE);

	private static final byte[] _MARKER_STYLE_OPEN =
		"style type=\"text/css\">".getBytes();

	private static final byte[] _MARKER_TEXTAREA_CLOSE =
		"/textarea>".getBytes();

	private static final int[] _MARKER_TEXTAREA_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_TEXTAREA_CLOSE);

	private static final byte[] _MARKER_TEXTAREA_OPEN =
		"textarea ".getBytes();

	private static final byte[] _SCRIPT_TYPE_JAVASCRIPT =
		"<script type=\"text/javascript\">".getBytes();

	private static final String _STRIP = "strip";

	private static final byte[] _STYLE_TYPE_CSS =
		"<style type=\"text/css\">".getBytes();

	private static Log _log = LogFactoryUtil.getLog(StripFilter.class);

}