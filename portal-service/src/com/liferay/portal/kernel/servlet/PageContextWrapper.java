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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;

import java.io.IOException;
import java.io.Writer;

import java.util.Enumeration;

import javax.el.ELContext;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.ErrorData;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PageContextWrapper extends PageContext {

	public PageContextWrapper(PageContext pageContext) {
		_pageContext = pageContext;
	}

	public Object findAttribute(String name) {
		return _pageContext.findAttribute(name);
	}

	public void forward(String relativeUrlPath)
		throws IOException, ServletException {

		_pageContext.forward(relativeUrlPath);
	}

	public Object getAttribute(String name) {
		return _pageContext.getAttribute(name);
	}

	public Object getAttribute(String name, int scope) {
		return _pageContext.getAttribute(name, scope);
	}

	public Enumeration<String> getAttributeNamesInScope(int scope) {
		return _pageContext.getAttributeNamesInScope(scope);
	}

	public int getAttributesScope(String name) {
		return _pageContext.getAttributesScope(name);
	}

	public ELContext getELContext() {
		return _pageContext.getELContext();
	}

	public ErrorData getErrorData() {
		return super.getErrorData();
	}

	public Exception getException() {
		return _pageContext.getException();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.jsp.el.ExpressionEvaluator getExpressionEvaluator() {
		return _pageContext.getExpressionEvaluator();
	}

	public JspWriter getOut() {
		return new PipingJspWriter(_pageContext.getOut());
	}

	public Object getPage() {
		return _pageContext.getPage();
	}

	public ServletRequest getRequest() {
		return _pageContext.getRequest();
	}

	public ServletResponse getResponse() {
		return _pageContext.getResponse();
	}

	public ServletConfig getServletConfig() {
		return _pageContext.getServletConfig();
	}

	public ServletContext getServletContext() {
		return _pageContext.getServletContext();
	}

	public HttpSession getSession() {
		return _pageContext.getSession();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.jsp.el.VariableResolver getVariableResolver() {
		return _pageContext.getVariableResolver();
	}

	public PageContext getWrappedPageContext() {
		return _pageContext;
	}

	public void handlePageException(Exception e)
		throws IOException, ServletException {

		_pageContext.handlePageException(e);
	}

	public void handlePageException(Throwable t)
		throws IOException, ServletException {

		_pageContext.handlePageException(t);
	}

	public void include(String relativeUrlPath)
		throws IOException, ServletException {

		_pageContext.include(relativeUrlPath);
	}

	public void include(String relativeUrlPath, boolean flush)
		throws IOException, ServletException {

		_pageContext.include(relativeUrlPath, flush);
	}

	public void initialize(
			Servlet servlet, ServletRequest request, ServletResponse response,
			String errorPageURL, boolean needsSession, int bufferSize,
			boolean autoFlush)
		throws IllegalArgumentException, IllegalStateException, IOException {

		_pageContext.initialize(
			servlet, request, response, errorPageURL, needsSession, bufferSize,
			autoFlush);
	}

	public JspWriter popBody() {
		return _pageContext.popBody();
	}

	public BodyContent pushBody() {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		BodyContent bodyContent = (BodyContent)_pageContext.pushBody(
			unsyncStringWriter);

		return new BodyContentWrapper(bodyContent, unsyncStringWriter);
	}

	public JspWriter pushBody(Writer writer) {
		return _pageContext.pushBody(new PipingJspWriter(writer));
	}

	public void release() {
		_pageContext.release();
	}

	public void removeAttribute(String name) {
		_pageContext.removeAttribute(name);
	}

	public void removeAttribute(String name, int scope) {
		_pageContext.removeAttribute(name, scope);
	}

	public void setAttribute(String name, Object value) {
		_pageContext.setAttribute(name, value);
	}

	public void setAttribute(String name, Object value, int scope) {
		_pageContext.setAttribute(name, value, scope);
	}

	private PageContext _pageContext;

}