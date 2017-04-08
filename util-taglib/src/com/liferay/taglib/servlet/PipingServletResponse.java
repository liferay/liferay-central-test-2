/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.servlet;

import com.liferay.portal.kernel.io.WriterOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletOutputStreamAdapter;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Shuyang Zhou
 */
public class PipingServletResponse extends HttpServletResponseWrapper {

	public static HttpServletResponse createPipingServletResponse(
		PageContext pageContext) {

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		JspWriter jspWriter = pageContext.getOut();

		if (jspWriter instanceof BodyContent) {

			// Unable to unwrap page context with pushed body

			return new PipingServletResponse(httpServletResponse, jspWriter);
		}

		if (!ServerDetector.isTomcat()) {
			try {
				jspWriter.flush();
			}
			catch (IOException ioe) {
				ReflectionUtil.throwException(ioe);
			}
		}

		return httpServletResponse;
	}

	public PipingServletResponse(
		HttpServletResponse response, OutputStream outputStream) {

		super(response);

		if (outputStream == null) {
			throw new NullPointerException("Output stream is null");
		}

		_servletOutputStream = new ServletOutputStreamAdapter(outputStream);
	}

	public PipingServletResponse(
		HttpServletResponse response, PrintWriter printWriter) {

		super(response);

		if (printWriter == null) {
			throw new NullPointerException("Print writer is null");
		}

		_printWriter = printWriter;
	}

	public PipingServletResponse(
		HttpServletResponse response, ServletOutputStream servletOutputStream) {

		super(response);

		if (servletOutputStream == null) {
			throw new NullPointerException("Servlet output stream is null");
		}

		_servletOutputStream = servletOutputStream;
	}

	public PipingServletResponse(HttpServletResponse response, Writer writer) {
		super(response);

		if (writer == null) {
			throw new NullPointerException("Writer is null");
		}

		_printWriter = UnsyncPrintWriterPool.borrow(writer);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #createPipingServletResponseIfNeeded(PageContext)}
	 */
	@Deprecated
	public PipingServletResponse(PageContext pageContext) {
		this(
			(HttpServletResponse)pageContext.getResponse(),
			pageContext.getOut());
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (_servletOutputStream == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Getting an output stream when a writer is available is " +
						"not recommended because it is slow");
			}

			_servletOutputStream = new ServletOutputStreamAdapter(
				new WriterOutputStream(
					_printWriter, getCharacterEncoding(), getBufferSize(),
					true));
		}

		return _servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() {
		if (_printWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Getting a writer when an output stream is available is " +
						"not recommended because it is slow");
			}

			_printWriter = UnsyncPrintWriterPool.borrow(
				_servletOutputStream, getCharacterEncoding());
		}

		return _printWriter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PipingServletResponse.class);

	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;

}