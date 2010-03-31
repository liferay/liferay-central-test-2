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

package com.liferay.portal.kernel.servlet;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <a href="PipingServletResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class PipingServletResponse extends HttpServletResponseWrapper {

	public PipingServletResponse(
		HttpServletResponse response, OutputStream outputStream) {

		super(response);

		_servletOutputStream = new PipingServletOutputStream(outputStream);
	}

	public PipingServletResponse(
		HttpServletResponse response, PrintWriter printWriter) {

		super(response);

		_printWriter = printWriter;
	}

	public PipingServletResponse(
		HttpServletResponse response, ServletOutputStream servletOutputStream) {

		super(response);

		_servletOutputStream = servletOutputStream;
	}

	public PipingServletResponse(HttpServletResponse response, Writer writer) {
		super(response);

		_printWriter = new PrintWriter(writer, true);
	}

	public ServletOutputStream getOutputStream() {
		if (_servletOutputStream == null) {
			throw new IllegalStateException("Servlet output stream is null");
		}

		return  _servletOutputStream;
	}

	public PrintWriter getWriter() {
		if (_printWriter == null) {
			throw new IllegalStateException("Print writer is null");
		}

		return _printWriter;
	}

	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;

}