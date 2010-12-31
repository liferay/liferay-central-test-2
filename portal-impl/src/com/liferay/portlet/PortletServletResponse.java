/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
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
 * @author Brian Wing Shun Chan
 */
public class PortletServletResponse extends HttpServletResponseWrapper {

	public PortletServletResponse(
		HttpServletResponse response, PortletResponseImpl portletResponseImpl,
		boolean include) {

		super(response);

		_response = response;
		_portletResponseImpl = portletResponseImpl;
		_include = include;
		_lifecycle = _portletResponseImpl.getLifecycle();
	}

	public void addCookie(Cookie cookie) {
		if (!_include) {
			_portletResponseImpl.addProperty(cookie);
		}
	}

	public void addDateHeader(String name, long date) {
		addHeader(name, String.valueOf(date));
	}

	public void addHeader(String name, String value) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				HttpServletResponse response =
					_portletResponseImpl.getHttpServletResponse();

				response.addHeader(name, value);
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
		return _portletResponseImpl.encodeURL(url);
	}

	public String encodeUrl(String url) {
		return _portletResponseImpl.encodeURL(url);
	}

	public void flushBuffer() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_response.flushBuffer();
		}
	}

	public int getBufferSize() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _response.getBufferSize();
		}
		else {
			return 0;
		}
	}

	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _response.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return ((MimeResponse)_portletResponseImpl).getContentType();
		}
		else {
			return null;
		}
	}

	public Locale getLocale() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _response.getLocale();
		}
		else {
			return null;
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _response.getOutputStream();
		}
		else {
			return new NullServletOutputStream();
		}
	}

	public PrintWriter getWriter() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _response.getWriter();
		}
		else {
			return new UnsyncPrintWriter(new NullServletOutputStream());
		}
	}

	public boolean isCommitted() {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				return _response.isCommitted();
			}
			else {
				return false;
			}
		}
		else {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				return _response.isCommitted();
			}
			else {
				return true;
			}
		}
	}

	public void reset() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_response.reset();
		}
	}

	public void resetBuffer() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_response.resetBuffer();
		}
	}

	public void sendError(int status) {
	}

	public void sendError(int status, String msg) {
	}

	public void sendRedirect(String location) throws IOException {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				((ActionResponse)_portletResponseImpl).sendRedirect(location);
			}
		}
	}

	public void setBufferSize(int bufferSize) {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_response.setBufferSize(bufferSize);
		}
	}

	public void setCharacterEncoding(String encoding) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_response.setCharacterEncoding(encoding);
			}
		}
	}

	public void setContentLength(int length) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_response.setContentLength(length);
			}
		}
	}

	public void setContentType(String contentType) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				((MimeResponse)_portletResponseImpl).setContentType(
					contentType);
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

				HttpServletResponse response =
					_portletResponseImpl.getHttpServletResponse();

				response.setHeader(name, value);
			}
		}
	}

	public void setIntHeader(String name, int value) {
		setHeader(name, String.valueOf(value));
	}

	public void setLocale(Locale locale) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_response.setLocale(locale);
			}
		}
	}

	public void setStatus(int status) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_response.setStatus(status);
			}
		}
	}

	public void setStatus(int status, String msg) {
		setStatus(status);
	}

	private HttpServletResponse _response;
	private PortletResponseImpl _portletResponseImpl;
	private boolean _include;
	private String _lifecycle;

}