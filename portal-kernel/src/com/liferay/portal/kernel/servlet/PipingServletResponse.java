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
		HttpServletResponse response, Writer pipingWriter) {
		super(response);
		_pipingWriter = new PrintWriter(pipingWriter, true);
	}

	public PipingServletResponse(
		HttpServletResponse response, PrintWriter pipingWriter) {
		super(response);
		_pipingWriter = pipingWriter;
	}

	public PipingServletResponse(
		HttpServletResponse response, OutputStream pipingOutputStream) {
		super(response);
		_pipingOutputStream =
			new ServletOutputStreamAdapter(pipingOutputStream);
	}

	public PipingServletResponse(
		HttpServletResponse response, ServletOutputStream pipingOutputStream) {
		super(response);
		_pipingOutputStream = pipingOutputStream;
	}

	public ServletOutputStream getOutputStream() {
		if (_pipingOutputStream == null) {
			throw new IllegalStateException(
				"This piping servlet response is for Writer output!");
		}
		return  _pipingOutputStream;
	}

	public PrintWriter getWriter() {
		if (_pipingWriter == null) {
			throw new IllegalStateException(
				"This piping servlet response is for OutputStream output!");
		}
		return _pipingWriter;
	}

	private PrintWriter _pipingWriter;
	private ServletOutputStream _pipingOutputStream;

}