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

import java.util.Locale;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.ServletOutputStream;
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
		HttpServletResponse res, PortletResponseImpl portletRes) {

		super(res);

		_res = res;
		_portletRes = portletRes;
		_lifecycle = _portletRes.getLifecycle();
	}

	public int getBufferSize() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.EVENT_PHASE)) {

			return 0;
		}
		else {
			return _res.getBufferSize();
		}
	}

	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.EVENT_PHASE)) {

			return null;
		}
		else {
			return _res.getCharacterEncoding();
		}
	}

	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.EVENT_PHASE)) {

			return null;
		}
		else {
			return ((MimeResponse)_portletRes).getContentType();
		}
	}

	public String encodeRedirectUrl(String url) {
		return encodeRedirectURL(url);
	}

	public String encodeRedirectURL(String url) {
		return null;
	}

	public String encodeUrl(String path) {
		return encodeURL(path);
	}

	public String encodeURL(String path) {
		return _res.encodeURL(path);
	}

	public Locale getLocale() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.EVENT_PHASE)) {

			return null;
		}
		else {
			return _res.getLocale();
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.EVENT_PHASE)) {

			return new NullServletOutputStream();
		}
		else {
			return super.getOutputStream();
		}
	}

	public boolean isCommitted() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.EVENT_PHASE)) {

			return true;
		}
		else {
			return _res.isCommitted();
		}
	}

	private HttpServletResponse _res;
	private PortletResponseImpl _portletRes;
	private String _lifecycle;

}