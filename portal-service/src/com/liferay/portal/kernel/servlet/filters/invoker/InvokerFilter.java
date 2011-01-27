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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.concurrent.ConcurrentLRUCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class InvokerFilter implements Filter {

	public InvokerFilter() {
		if (_INVOKER_FILTER_CHAIN_SIZE > 0) {
			_filterChains = new ConcurrentLRUCache<Integer, InvokerFilterChain>(
				_INVOKER_FILTER_CHAIN_SIZE);
		}
	}

	public void destroy() {
		ServletContext servletContext = _filterConfig.getServletContext();

		InvokerFilterHelper invokerFilterHelper =
			(InvokerFilterHelper)servletContext.getAttribute(
				InvokerFilterHelper.class.getName());

		if (invokerFilterHelper != null) {
			servletContext.removeAttribute(InvokerFilterHelper.class.getName());

			_invokerFilterHelper.destroy();
		}
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)servletRequest;

		String uri = getURI(request);

		request.setAttribute(WebKeys.INVOKER_FILTER_URI, uri);

		InvokerFilterChain invokerFilterChain = getInvokerFilterChain(
			request, uri, filterChain);

		invokerFilterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			_filterConfig = filterConfig;

			ServletContext servletContext = _filterConfig.getServletContext();

			InvokerFilterHelper invokerFilterHelper =
				(InvokerFilterHelper)servletContext.getAttribute(
					InvokerFilterHelper.class.getName());

			if (invokerFilterHelper == null) {
				invokerFilterHelper = new InvokerFilterHelper();

				invokerFilterHelper.readLiferayFilterWebXML(
					servletContext, "/WEB-INF/liferay-web.xml");

				servletContext.setAttribute(
					InvokerFilterHelper.class.getName(), invokerFilterHelper);
			}

			_invokerFilterHelper = invokerFilterHelper;

			_invokerFilterHelper.addInvokerFilter(this);

			String dispatcher = GetterUtil.getString(
				_filterConfig.getInitParameter("dispatcher"));

			if (dispatcher.equals("ERROR")) {
				_dispatcher = Dispatcher.ERROR;
			}
			else if (dispatcher.equals("FORWARD")) {
				_dispatcher = Dispatcher.FORWARD;
			}
			else if (dispatcher.equals("INCLUDE")) {
				_dispatcher = Dispatcher.INCLUDE;
			}
			else if (dispatcher.equals("REQUEST")) {
				_dispatcher = Dispatcher.REQUEST;
			}
			else {
				throw new IllegalArgumentException(
					"Invalid dispatcher " + dispatcher);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ServletException(e);
		}
	}

	protected void clearFilterChainsCache() {
		if (_filterChains != null) {
			_filterChains.clear();
		}
	}

	protected InvokerFilterChain getInvokerFilterChain(
		HttpServletRequest request, String uri, FilterChain filterChain) {

		if (_filterChains == null) {
			return _invokerFilterHelper.createInvokerFilterChain(
				request, _dispatcher, uri, filterChain);
		}

		Integer key = uri.hashCode();

		InvokerFilterChain invokerFilterChain = _filterChains.get(key);

		if (invokerFilterChain == null) {
			invokerFilterChain = _invokerFilterHelper.createInvokerFilterChain(
				request, _dispatcher, uri, filterChain);

			_filterChains.put(key, invokerFilterChain);
		}

		return invokerFilterChain.clone(filterChain);
	}

	protected String getURI(HttpServletRequest request) {
		if (_dispatcher == Dispatcher.ERROR) {
			return (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_ERROR_REQUEST_URI);
		}
		else if (_dispatcher == Dispatcher.FORWARD) {
			return (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI);
		}
		else if (_dispatcher == Dispatcher.INCLUDE) {
			return (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);
		}
		else {
			return request.getRequestURI();
		}
	}

	private static final int _INVOKER_FILTER_CHAIN_SIZE = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INVOKER_FILTER_CHAIN_SIZE));

	private static Log _log = LogFactoryUtil.getLog(InvokerFilter.class);

	private Dispatcher _dispatcher;
	private ConcurrentLRUCache<Integer, InvokerFilterChain> _filterChains;
	private FilterConfig _filterConfig;
	private InvokerFilterHelper _invokerFilterHelper;

}