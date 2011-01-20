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

package com.liferay.portal.servlet.filters.delegate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.servlet.filters.FilterMapping;
import com.liferay.portal.servlet.filters.FilterRegistry;
import com.liferay.portal.servlet.filters.LiferayFilterChain;
import com.liferay.portal.servlet.filters.LiferayFilterConfig;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 */
public class DelegateFilter implements Filter {

	public void destroy() {
		FilterRegistry.destroy();
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		String errorRequestURI = (String)servletRequest.getAttribute(
			JavaConstants.JAVAX_SERVLET_ERROR_REQUEST_URI);

		String forwardRequestURI = (String)servletRequest.getAttribute(
			JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI);

		String includeRequestURI = (String)servletRequest.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		String requestURI =
			((HttpServletRequest)servletRequest).getRequestURI();

		int dispatcherType = FilterMapping.REQUEST;
		String uri = requestURI;

		if (Validator.isNotNull(errorRequestURI)) {
			dispatcherType = FilterMapping.ERROR;
			uri = errorRequestURI;
		}
		else if (Validator.isNotNull(forwardRequestURI)) {
			dispatcherType = FilterMapping.FORWARD;
			uri = requestURI;
		}
		else if (Validator.isNotNull(includeRequestURI)) {
			dispatcherType = FilterMapping.INCLUDE;
			uri = includeRequestURI;
		}

		LiferayFilterChain liferayFilterChain =
			new LiferayFilterChain(filterChain);

		for (FilterMapping filterMapping : FilterRegistry.getFilterMappings()) {
			if (!filterMapping.isDispatcherEnabled(dispatcherType)) {
				continue;
			}
			else if (filterMapping.matchesURI(uri)) {
				Filter filter = FilterRegistry.getFilter(
					filterMapping.getFilterName());

				liferayFilterChain.addFilter(filter);
			}
		}

		liferayFilterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig)
		throws ServletException {

		try {
			ServletContext servletContext = filterConfig.getServletContext();

			InputStream inputStream = servletContext.getResourceAsStream(
				"/WEB-INF/liferay-filter-web.xml");

			Document doc = SAXReaderUtil.read(inputStream, true);

			Element rootElement = doc.getRootElement();

			List<Element> filterElements = rootElement.elements("filter");

			for (Element filterElement : filterElements) {
				String filterName =
					filterElement.elementText("filter-name");
				String filterClassName =
					filterElement.elementText("filter-class");

				Map<String, String> initParams = new HashMap<String, String>();

				List<Element> initParamElements =
					filterElement.elements("init-param");

				for (Element initParamElement : initParamElements) {
					String name = initParamElement.elementText("param-name");
					String value = initParamElement.elementText("param-value");

					initParams.put(name, value);
				}

				try {
					initFilter(
						servletContext, filterName, filterClassName,
						initParams);
				}
				catch (Exception e) {
					_log.error(
						"Filter " + filterName + " initialization failed", e);
				}
			}

			List<Element> filterMappingElements =
				rootElement.elements("filter-mapping");

			for (Element filterMappingElement : filterMappingElements) {
				String filterName =
					filterMappingElement.elementText("filter-name");
				String servletName =
					filterMappingElement.elementText("servlet-name");

				List<String> urlPatterns = new ArrayList<String>();

				List<Element> urlPatternElements =
					filterMappingElement.elements("url-pattern");

				for (Element urlPatternElement : urlPatternElements) {
					urlPatterns.add(urlPatternElement.getTextTrim());
				}

				List<String> dispatchers = new ArrayList<String>(4);

				List<Element> dispatcherElements =
					filterMappingElement.elements("dispatcher");

				for (Element dispatcherElement : dispatcherElements) {
					String dispatcher =
						dispatcherElement.getTextTrim().toUpperCase();

					dispatchers.add(dispatcher);
				}

				initFilterMapping(filterName, urlPatterns, dispatchers);
			}
		}
		catch (DocumentException de) {
			throw new ServletException(de);
		}
	}

	protected void initFilter(
			ServletContext servletContext, String filterName,
			String filterClassName, Map<String, String> initParams)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		Filter filter = (Filter)InstanceFactory.newInstance(
			classLoader, filterClassName);

		FilterConfig filterConfig = new LiferayFilterConfig(
			filterName, initParams, servletContext);

		filter.init(filterConfig);

		FilterRegistry.registerFilter(filterName, filter);
	}

	protected void initFilterMapping(
		String filterName, List<String> urlPatterns, List<String> dispatchers) {

		if (FilterRegistry.getFilter(filterName) == null) {
			_log.warn("No such filter " + filterName);

			return;
		}

		FilterMapping filterMapping = new FilterMapping(
			filterName, urlPatterns, dispatchers);

		FilterRegistry.registerFilterMapping(filterMapping);
	}

	private static Log _log = LogFactoryUtil.getLog(DelegateFilter.class);

}