/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.kernel.concurrent.ConcurrentLRUCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.KMPSearch;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.io.Writer;

import java.nio.CharBuffer;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		for (String ignorePath : PropsValues.STRIP_IGNORE_PATHS) {
			_ignorePaths.add(ignorePath);
		}
	}

	protected boolean skipWhiteSpace(CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		boolean skipped = false;
		for(int i = oldCharBuffer.position(); i < oldCharBuffer.limit(); i++) {
			char c = oldCharBuffer.get();
			if ((c == CharPool.SPACE) || (c == CharPool.TAB) ||
				(c == CharPool.RETURN) || (c == CharPool.NEW_LINE)) {
				skipped = true;
				continue;
			}
			else {
				oldCharBuffer.position(i);
				break;
			}
		}
		if (skipped) {
			writer.write(CharPool.SPACE);
		}
		return skipped;
	}

	protected boolean hasMarker(CharBuffer oldCharBuffer, char[] marker) {
		int position = oldCharBuffer.position();

		if ((position + marker.length) >= oldCharBuffer.limit()) {
			return false;
		}

		for (int i = 0; i < marker.length; i++) {
			char c = marker[i];
			char oldC = oldCharBuffer.charAt(i);

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

		String path = request.getPathInfo();

		if (_ignorePaths.contains(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore path " + path);
			}

			return false;
		}

		// Modifying binary content through a servlet filter under certain
		// conditions is bad on performance the user will not start downloading
		// the content until the entire content is modified.

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

	protected void outputCloseTag(
			CharBuffer oldCharBuffer, Writer writer, String closeTag)
		throws IOException {

		writer.write(closeTag);
		oldCharBuffer.position(oldCharBuffer.position() + closeTag.length());
		skipWhiteSpace(oldCharBuffer, writer);
	}

	protected void outputOpenTag(
			CharBuffer oldCharBuffer, Writer writer, char[] openTag)
		throws IOException {

		writer.write(openTag);
		oldCharBuffer.position(oldCharBuffer.position() + openTag.length);
	}

	protected void processCSS(
			CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		outputOpenTag(oldCharBuffer, writer, _MARKER_STYLE_OPEN);

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_STYLE_CLOSE, _MARKER_STYLE_CLOSE_NEXTS);

		if (length == -1) {
			_log.error("Missing </style>");
			return;
		}

		if (length == 0) {
			outputCloseTag(oldCharBuffer, writer, _MARKER_STYLE_CLOSE);
			return;
		}

		String content = oldCharBuffer.subSequence(0, length).toString();

		int position = oldCharBuffer.position();
		oldCharBuffer.position(position + length);

		String minifiedContent = content;

		if (PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE > 0) {
			String key = String.valueOf(content.hashCode());

			minifiedContent = _minifierCache.get(key);

			if (minifiedContent == null) {
				minifiedContent = MinifierUtil.minifyCss(content);

				boolean skipCache = false;

				for (String skipCss :
						PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SKIP_CSS) {

					if (minifiedContent.contains(skipCss)) {
						skipCache = true;

						break;
					}
				}

				if (!skipCache) {
					_minifierCache.put(key, minifiedContent);
				}
			}
		}

		if (!Validator.isNull(minifiedContent)) {
			writer.write(minifiedContent);
		}

		outputCloseTag(oldCharBuffer, writer, _MARKER_STYLE_CLOSE);
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

			StringServletResponse stringResponse = new StringServletResponse(
				response);

			processFilter(
				StripFilter.class, request, stringResponse, filterChain);

			String contentType = GetterUtil.getString(
				stringResponse.getContentType()).toLowerCase();

			if (_log.isDebugEnabled()) {
				_log.debug("Stripping content of type " + contentType);
			}

			response.setContentType(contentType);

			if (contentType.startsWith(ContentTypes.TEXT_HTML)) {
				CharBuffer oldCharBuffer = CharBuffer.wrap(
					stringResponse.getString());
				strip(oldCharBuffer, response.getWriter());
			}
			else {
				ServletResponseUtil.write(response, stringResponse);
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

	protected void processJavaScript(
			CharBuffer oldCharBuffer, Writer writer, char[] openTag)
		throws IOException {

		outputOpenTag(oldCharBuffer, writer, openTag);

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_SCRIPT_CLOSE, _MARKER_SCRIPT_CLOSE_NEXTS);

		if (length == -1) {
			_log.error("Missing </script>");
			return;
		}

		if (length == 0) {
			outputCloseTag(oldCharBuffer, writer, _MARKER_SCRIPT_CLOSE);
			return;
		}

		String content = oldCharBuffer.subSequence(0, length).toString();

		int position = oldCharBuffer.position();
		oldCharBuffer.position(position + length);

		String minifiedContent = content;

		if (PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE > 0) {
			String key = String.valueOf(content.hashCode());

			minifiedContent = _minifierCache.get(key);

			if (minifiedContent == null) {
				minifiedContent = MinifierUtil.minifyJavaScript(content);

				boolean skipCache = false;

				for (String skipJavaScript :
						PropsValues.
							MINIFIER_INLINE_CONTENT_CACHE_SKIP_JAVASCRIPT) {

					if (minifiedContent.contains(skipJavaScript)) {
						skipCache = true;

						break;
					}
				}

				if (!skipCache) {
					_minifierCache.put(key, minifiedContent);
				}
			}
		}

		if (!Validator.isNull(minifiedContent)) {
			writer.write(_CDATA_OPEN);
			writer.write(minifiedContent);
			writer.write(_CDATA_CLOSE);
		}

		outputCloseTag(oldCharBuffer, writer, _MARKER_SCRIPT_CLOSE);
	}

	protected void processPre(
			CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		int position = oldCharBuffer.position();

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_PRE_OPEN.length + 1, _MARKER_PRE_CLOSE,
			_MARKER_PRE_CLOSE_NEXTS);

		if (length == -1) {
			_log.error("Missing </pre>");

			outputOpenTag(oldCharBuffer, writer, _MARKER_PRE_OPEN);
			return;
		}

		length += _MARKER_PRE_CLOSE.length();

		String content = oldCharBuffer.subSequence(0, length).toString();

		oldCharBuffer.position(position + length);

		writer.write(content);

		skipWhiteSpace(oldCharBuffer, writer);
	}

	protected void processTextArea(
			CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		int position = oldCharBuffer.position();

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_TEXTAREA_OPEN.length + 1,
			_MARKER_TEXTAREA_CLOSE, _MARKER_TEXTAREA_CLOSE_NEXTS);

		if (length == -1) {
			_log.error("Missing </textArea>");

			outputOpenTag(oldCharBuffer, writer, _MARKER_TEXTAREA_OPEN);
			return;
		}

		length += _MARKER_TEXTAREA_CLOSE.length();

		String content = oldCharBuffer.subSequence(0, length).toString();

		oldCharBuffer.position(position + length);

		writer.write(content);

		skipWhiteSpace(oldCharBuffer, writer);
	}

	protected void strip(
			CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		skipWhiteSpace(oldCharBuffer, writer);

		while (oldCharBuffer.hasRemaining()) {
			char c = oldCharBuffer.get();
			writer.write(c);

			if (c == CharPool.LESS_THAN) {
				if (hasMarker(oldCharBuffer, _MARKER_PRE_OPEN)) {
					processPre(oldCharBuffer, writer);

					continue;
				}
				else if (hasMarker(oldCharBuffer, _MARKER_TEXTAREA_OPEN)) {
					processTextArea(oldCharBuffer, writer);

					continue;
				}
				else if (hasMarker(oldCharBuffer, _MARKER_JS_OPEN)) {
					processJavaScript(oldCharBuffer, writer, _MARKER_JS_OPEN);

					continue;
				}
				else if (hasMarker(oldCharBuffer, _MARKER_SCRIPT_OPEN)) {
					processJavaScript(oldCharBuffer, writer,
						_MARKER_SCRIPT_OPEN);

					continue;
				}
				else if (hasMarker(oldCharBuffer, _MARKER_STYLE_OPEN)) {
					processCSS(oldCharBuffer, writer);

					continue;
				}
			}
			else if (c == CharPool.GREATER_THAN) {
				skipWhiteSpace(oldCharBuffer, writer);
			}
		}

		writer.flush();

	}

	private static final String _CDATA_CLOSE = "/*]]>*/";

	private static final String _CDATA_OPEN = "/*<![CDATA[*/";

	private static final char[] _MARKER_JS_OPEN =
		"script type=\"text/javascript\">".toCharArray();

	private static final String _MARKER_PRE_CLOSE = "/pre>";

	private static final int[] _MARKER_PRE_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_PRE_CLOSE);

	private static final char[] _MARKER_PRE_OPEN = "pre>".toCharArray();

	private static final String _MARKER_SCRIPT_CLOSE = "</script>";

	private static final int[] _MARKER_SCRIPT_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_SCRIPT_CLOSE);

	private static final char[] _MARKER_SCRIPT_OPEN = "script>".toCharArray();

	private static final String _MARKER_STYLE_CLOSE = "</style>";

	private static final int[] _MARKER_STYLE_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_STYLE_CLOSE);

	private static final char[] _MARKER_STYLE_OPEN =
		"style type=\"text/css\">".toCharArray();

	private static final String _MARKER_TEXTAREA_CLOSE = "/textarea>";

	private static final int[] _MARKER_TEXTAREA_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_TEXTAREA_CLOSE);

	private static final char[] _MARKER_TEXTAREA_OPEN =
		"textarea ".toCharArray();

	private static final String _STRIP = "strip";

	private static Log _log = LogFactoryUtil.getLog(StripFilter.class);

	private ConcurrentLRUCache<String, String> _minifierCache =
		new ConcurrentLRUCache<String, String>(
			PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE);
	private Set<String> _ignorePaths = new HashSet<String>();

}