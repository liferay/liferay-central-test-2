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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.LiferayFilter;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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

	public void destroy() {
		for (Map.Entry<String, Filter> entry : _filters.entrySet()) {
			Filter filter = entry.getValue();

			try {
				filter.destroy();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_filterMappings.clear();
		_filters.clear();
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)servletRequest;

		Dispatcher dispatcher = Dispatcher.REQUEST;
		String uri = request.getRequestURI();

		String errorRequestURI = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_ERROR_REQUEST_URI);
		String forwardRequestURI = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI);
		String includeRequestURI = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		if (Validator.isNotNull(errorRequestURI)) {
			dispatcher = Dispatcher.ERROR;
			uri = errorRequestURI;
		}
		else if (Validator.isNotNull(forwardRequestURI)) {
			dispatcher = Dispatcher.FORWARD;
		}
		else if (Validator.isNotNull(includeRequestURI)) {
			dispatcher = Dispatcher.INCLUDE;
			uri = includeRequestURI;
		}

		InvokerFilterChain invokerFilterChain = new InvokerFilterChain(
			filterChain);

		for (FilterMapping filterMapping : _filterMappings) {
			if (filterMapping.isMatch(dispatcher, uri)) {
				Filter filter = _filters.get(filterMapping.getFilterName());

				boolean filterEnabled = true;

				if (filter instanceof LiferayFilter) {
					LiferayFilter liferayFilter = (LiferayFilter)filter;

					filterEnabled = liferayFilter.isFilterEnabled(request);
				}

				if (filterEnabled) {
					invokerFilterChain.addFilter(filter);
				}
			}
		}

		invokerFilterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			ServletContext servletContext = filterConfig.getServletContext();

			readLiferayFilterWebXML(servletContext, "/WEB-INF/liferay-web.xml");
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void initFilter(
			ServletContext servletContext, String filterName,
			String filterClassName, Map<String, String> initParameterMap)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Filter filter = (Filter)InstanceFactory.newInstance(
			contextClassLoader, filterClassName);

		FilterConfig filterConfig = new InvokerFilterConfig(
			servletContext, filterName, initParameterMap);

		filter.init(filterConfig);

		boolean filterEnabled = true;

		if (filter instanceof LiferayFilter) {
			LiferayFilter liferayFilter = (LiferayFilter)filter;

			filterEnabled = liferayFilter.isFilterEnabled();
		}

		if (filterEnabled) {
			_filters.put(filterName, filter);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Removing disabled filter " + filter.getClass());
			}
		}
	}

	protected void initFilterMapping(
		String filterName, List<String> urlPatterns, List<String> dispatchers) {

		if (_filters.containsKey(filterName)) {
			FilterMapping filterMapping = new FilterMapping(
				filterName, urlPatterns, dispatchers);

			_filterMappings.add(filterMapping);
		}
	}

	protected void readLiferayFilterWebXML(
			ServletContext servletContext, String path)
		throws Exception {

		InputStream inputStream = servletContext.getResourceAsStream(path);

		if (inputStream == null) {
			return;
		}

		Document document = SAXReaderUtil.read(inputStream, true);

		Element rootElement = document.getRootElement();

		List<Element> filterElements = rootElement.elements("filter");

		for (Element filterElement : filterElements) {
			String filterName = filterElement.elementText("filter-name");
			String filterClassName = filterElement.elementText("filter-class");

			Map<String, String> initParameterMap =
				new HashMap<String, String>();

			List<Element> initParamElements = filterElement.elements(
				"init-param");

			for (Element initParamElement : initParamElements) {
				String name = initParamElement.elementText("param-name");
				String value = initParamElement.elementText("param-value");

				initParameterMap.put(name, value);
			}

			initFilter(
				servletContext, filterName, filterClassName,
				initParameterMap);
		}

		List<Element> filterMappingElements = rootElement.elements(
			"filter-mapping");

		for (Element filterMappingElement : filterMappingElements) {
			String filterName = filterMappingElement.elementText("filter-name");

			List<String> urlPatterns = new ArrayList<String>();

			List<Element> urlPatternElements = filterMappingElement.elements(
				"url-pattern");

			for (Element urlPatternElement : urlPatternElements) {
				urlPatterns.add(urlPatternElement.getTextTrim());
			}

			List<String> dispatchers = new ArrayList<String>(4);

			List<Element> dispatcherElements = filterMappingElement.elements(
				"dispatcher");

			for (Element dispatcherElement : dispatcherElements) {
				String dispatcher =
					dispatcherElement.getTextTrim().toUpperCase();

				dispatchers.add(dispatcher);
			}

			initFilterMapping(filterName, urlPatterns, dispatchers);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(InvokerFilter.class);

	private List<FilterMapping> _filterMappings =
		new CopyOnWriteArrayList<FilterMapping>();
	private Map<String, Filter> _filters = new HashMap<String, Filter>();

}