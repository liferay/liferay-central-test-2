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
import com.liferay.util.servlet.GenericServletOutputStream;
import com.liferay.util.servlet.NullServletOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Locale;

import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletServletResponse extends HttpServletResponseWrapper {

	public PortletServletResponse(
		HttpServletResponse response, PortletResponse portletResponse,
		boolean include) {

		super(response);

		_portletResponse = portletResponse;
		_include = include;

		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		_lifecycle = portletResponseImpl.getLifecycle();
	}

	public void addCookie(Cookie cookie) {
		if (!_include) {
			_portletResponse.addProperty(cookie);
		}
	}

	public void addDateHeader(String name, long date) {
		addHeader(name, String.valueOf(date));
	}

	public void addHeader(String name, String value) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				_getMimeResponse().setProperty(name, value);
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
		return _portletResponse.encodeURL(url);
	}

	public String encodeUrl(String url) {
		return _portletResponse.encodeURL(url);
	}

	public void flushBuffer() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_getMimeResponse().flushBuffer();
		}
	}

	public int getBufferSize() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _getMimeResponse().getBufferSize();
		}
		else {
			return 0;
		}
	}

	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _getMimeResponse().getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _getMimeResponse().getContentType();
		}
		else {
			return null;
		}
	}

	public Locale getLocale() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _getMimeResponse().getLocale();
		}
		else {
			return null;
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			OutputStream portletOutputStream =
				_getMimeResponse().getPortletOutputStream();

			ServletOutputStream servletOutputStream =
				new GenericServletOutputStream(portletOutputStream);

			return servletOutputStream;
		}
		else {
			return new NullServletOutputStream();
		}
	}

	public PrintWriter getWriter() throws IOException {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _getMimeResponse().getWriter();
		}
		else {
			return new UnsyncPrintWriter(new NullServletOutputStream());
		}
	}

	public boolean isCommitted() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _getMimeResponse().isCommitted();
		}
		else if (!_include) {
			return false;
		}
		else {
			return true;
		}
	}

	public void reset() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_getMimeResponse().reset();
		}
	}

	public void resetBuffer() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_getMimeResponse().resetBuffer();
		}
	}

	public void sendError(int status) {
	}

	public void sendError(int status, String msg) {
	}

	public void sendRedirect(String location) throws IOException {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				_getActionResponse().sendRedirect(location);
			}
		}
	}

	public void setBufferSize(int bufferSize) {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_getMimeResponse().setBufferSize(bufferSize);
		}
	}

	public void setCharacterEncoding(String encoding) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_getResourceResponse().setCharacterEncoding(encoding);
			}
		}
	}

	public void setContentLength(int length) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_getResourceResponse().setContentLength(length);
			}
		}
	}

	public void setContentType(String contentType) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

				_getMimeResponse().setContentType(contentType);
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

				_getMimeResponse().setProperty(name, value);
			}
		}
	}

	public void setIntHeader(String name, int value) {
		setHeader(name, String.valueOf(value));
	}

	public void setLocale(Locale locale) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_getResourceResponse().setLocale(locale);
			}
		}
	}

	public void setStatus(int status) {
		if (!_include) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				_getResourceResponse().setProperty(
					ResourceResponse.HTTP_STATUS_CODE, String.valueOf(status));
			}
		}
	}

	public void setStatus(int status, String msg) {
		setStatus(status);
	}

	private ActionResponse _getActionResponse() {
		return (ActionResponse)_portletResponse;
	}

	private MimeResponse _getMimeResponse() {
		return (MimeResponse)_portletResponse;
	}

	private ResourceResponse _getResourceResponse() {
		return (ResourceResponse)_portletResponse;
	}

	private PortletResponse _portletResponse;
	private boolean _include;
	private String _lifecycle;

}