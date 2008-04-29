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

package com.liferay.portlet;

import com.liferay.util.servlet.NullServletOutputStream;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <a href="PortletServletResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletServletResponse extends HttpServletResponseWrapper {

	public PortletServletResponse(
		HttpServletResponse res, PortletResponseImpl portletRes,
		boolean include) {

		super(res);

		_res = res;
		_portletRes = portletRes;
		_include = include;
		_lifecycle = _portletRes.getLifecycle();
	}

	public void addCookie(Cookie cookie) {
		if (!_include) {
			_portletRes.addProperty(cookie);
		}
	}

	public void addDateHeader(String name, long date) {
		addHeader(name, String.valueOf(date));
	}

	public void addHeader(String name, String value) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				HttpServletResponse res = _portletRes.getHttpServletResponse();

				res.addHeader(name, value);
			}
		}
	}

	public void addIntHeader(String name, int value) {
		addHeader(name, String.valueOf(value));
	}

	public boolean containsHeader(String name) {
		return false;
	}

	public String encodeRedirectURL(String url) {
		return null;
	}

	public String encodeRedirectUrl(String url) {
		return null;
	}

	public String encodeURL(String url) {
		return _portletRes.encodeURL(url);
	}

	public String encodeUrl(String url) {
		return _portletRes.encodeURL(url);
	}

	public void flushBuffer() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_res.flushBuffer();
		}
	}

	public int getBufferSize() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _res.getBufferSize();
		}
		else {
			return 0;
		}
	}

	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _res.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return ((MimeResponse)_portletRes).getContentType();
		}
		else {
			return null;
		}
	}

	public Locale getLocale() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _res.getLocale();
		}
		else {
			return null;
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _res.getOutputStream();
		}
		else {
			return new NullServletOutputStream();
		}
	}

	public PrintWriter getWriter() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _res.getWriter();
		}
		else {
			return new PrintWriter(new NullServletOutputStream());
		}
	}

	public boolean isCommitted() {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				return _res.isCommitted();
			}
			else {
				return false;
			}
		}
		else {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				return _res.isCommitted();
			}
			else {
				return true;
			}
		}
	}

	public void reset() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_res.reset();
		}
	}

	public void resetBuffer() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_res.resetBuffer();
		}
	}

	public void sendError(int sc) throws IOException {
	}

	public void sendError(int sc, String msg) throws IOException {
	}

	public void sendRedirect(String location) throws IOException {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				((ActionResponse)_portletRes).sendRedirect(location);
			}
		}
	}

	public void setBufferSize(int size) {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_res.setBufferSize(size);
		}
	}

	public void setCharacterEncoding(String encoding) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_res.setCharacterEncoding(encoding);
			}
		}
	}

	public void setContentLength(int length) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_res.setContentLength(length);
			}
		}
	}

	public void setContentType(String contentType) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				((MimeResponse)_portletRes).setContentType(contentType);
			}
		}
	}

	public void setDateHeader(String name, long date) {
		setHeader(name, String.valueOf(date));
	}

	public void setHeader(String name, String value) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				HttpServletResponse res = _portletRes.getHttpServletResponse();

				res.setHeader(name, value);
			}
		}
	}

	public void setIntHeader(String name, int value) {
		setHeader(name, String.valueOf(value));
	}

	public void setLocale(Locale locale) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_res.setLocale(locale);
			}
		}
	}

	public void setStatus(int sc) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_portletRes.setProperty("status.", String.valueOf("sc"));
			}
		}
	}

	public void setStatus(int sc, String msg) {
		setStatus(sc);
	}

	private HttpServletResponse _res;
	private PortletResponseImpl _portletRes;
	private boolean _include;
	private String _lifecycle;

}