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

package com.liferay.portal.osgi.web.servlet.context.helper.internal.definition;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.FilterDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.ListenerDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.ServletDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.JspServletWrapper;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.order.OrderImpl;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.order.OrderUtil;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order.Path;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebXMLDefinitionLoader extends DefaultHandler {

	public WebXMLDefinitionLoader(
		Bundle bundle, SAXParserFactory saxParserFactory, Logger logger) {

		_bundle = bundle;
		_saxParserFactory = saxParserFactory;
		_logger = logger;

		_webXMLDefinition = new WebXMLDefinition();
	}

	@Override
	public void characters(char[] c, int start, int length) {
		if (_stack.empty()) {
			return;
		}

		StringBuilder stringBuilder = _stack.peek();

		stringBuilder.append(c, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("absolute-ordering")) {
			if (_othersAbsoluteOrderingSet &&
				(_absoluteOrderingNames != null)) {

				_absoluteOrderingNames.add(Order.OTHERS);
			}

			List<String> absoluteOrderNames =
				_webXMLDefinition.getAbsoluteOrderNames();

			absoluteOrderNames.addAll(_absoluteOrderingNames);

			_absoluteOrderingNames = null;
			_othersAbsoluteOrderingSet = false;
		}
		else if (qName.equals("after")) {
			_after = false;
			_afterName = _name;

			_name = null;
		}
		else if (qName.equals("async-supported")) {
			boolean asyncSupported = GetterUtil.getBoolean(_stack.pop());

			if (_filterDefinition != null) {
				_filterDefinition.setAsyncSupported(asyncSupported);
			}
			else if (_servletDefinition != null) {
				_servletDefinition.setAsyncSupported(asyncSupported);
			}
		}
		else if (qName.equals("before")) {
			_before = false;
			_beforeName = _name;

			_name = null;
		}
		else if (qName.equals("context-param")) {
			_webXMLDefinition.setContextParameter(
				_parameterName, _parameterValue);

			_parameterName = null;
			_parameterValue = null;
		}
		else if (qName.equals("dispatcher")) {
			String dispatcher = String.valueOf(_stack.pop());

			dispatcher = StringUtil.toUpperCase(dispatcher);
			dispatcher = dispatcher.trim();

			_filterMapping.dispatchers.add(dispatcher);
		}
		else if (qName.equals("filter")) {
			if (_filterDefinition.getFilter() != null) {
				_webXMLDefinition.setFilterDefinition(
					_filterDefinition.getName(), _filterDefinition);
			}

			_filterDefinition = null;
		}
		else if (qName.equals("filter-class")) {
			String filterClassName = String.valueOf(_stack.pop());

			Filter filter = _getFilterInstance(filterClassName.trim());

			_filterDefinition.setFilter(filter);
		}
		else if (qName.equals("filter-mapping")) {
			Map<String, FilterDefinition> filterDefinitions =
				_webXMLDefinition.getFilterDefinitions();

			FilterDefinition filterDefinition = filterDefinitions.get(
				_filterMapping.filterName);

			if (filterDefinition != null) {
				filterDefinition.setDispatchers(_filterMapping.dispatchers);

				if (_filterMapping.servletName != null) {
					List<String> servletNames =
						filterDefinition.getServletNames();

					servletNames.add(_filterMapping.servletName);
				}

				filterDefinition.setURLPatterns(_filterMapping.urlPatterns);
			}

			_filterMapping = null;
		}
		else if (qName.equals("filter-name")) {
			if (_filterMapping != null) {
				String filterName = String.valueOf(_stack.pop());

				_filterMapping.filterName = filterName.trim();
			}
			else if (_filterDefinition != null) {
				String filterName = String.valueOf(_stack.pop());

				_filterDefinition.setName(filterName.trim());
			}
		}
		else if (qName.equals("init-param")) {
			if (_filterDefinition != null) {
				_filterDefinition.setInitParameter(
					_parameterName, _parameterValue);
			}
			else if (_servletDefinition != null) {
				_servletDefinition.setInitParameter(
					_parameterName, _parameterValue);
			}

			_parameterName = null;
			_parameterValue = null;
		}
		else if (qName.equals("jsp-config")) {
			_webXMLDefinition.setJspTaglibMappings(_jspConfig.mappings);

			_jspConfig = null;
		}
		else if (qName.equals("jsp-file")) {
			String jspFile = String.valueOf(_stack.pop());

			_servletDefinition.setJSPFile(jspFile);

			_servletDefinition.setServlet(new JspServletWrapper(jspFile));
		}
		else if (qName.equals("listener")) {
			if (_listenerDefinition.getEventListener() != null) {
				_webXMLDefinition.addListenerDefinition(_listenerDefinition);
			}

			_listenerDefinition = null;
		}
		else if (qName.equals("listener-class")) {
			String listenerClassName = String.valueOf(_stack.pop());

			EventListener eventListener = _getListenerInstance(
				listenerClassName);

			_listenerDefinition.setEventListener(eventListener);
		}
		else if (qName.equals("name")) {
			String name = String.valueOf(_stack.pop());

			if (_absoluteOrderingNames != null) {
				_absoluteOrderingNames.add(name);
			}
			else if (!_after && !_before) {
				_webXMLDefinition.setFragmentName(name);
			}
			else {
				_name = name;
			}
		}
		else if (qName.equals("ordering")) {
			if (_ordering == null) {
				return;
			}

			EnumMap<Path, String[]> routes = _ordering.getRoutes();

			List<String> beforeNames = new ArrayList<>(2);

			if (_beforeName != null) {
				beforeNames.add(_beforeName);
			}

			if (_othersBeforeSet) {
				beforeNames.add(Order.OTHERS);
			}

			if (ListUtil.isNotEmpty(beforeNames)) {
				routes.put(Path.BEFORE, beforeNames.toArray(new String[0]));
			}

			List<String> afterNames = new ArrayList<>(2);

			if (_afterName != null) {
				afterNames.add(_afterName);
			}

			if (_othersAfterSet) {
				afterNames.add(Order.OTHERS);
			}

			if (ListUtil.isNotEmpty(afterNames)) {
				routes.put(Path.AFTER, afterNames.toArray(new String[0]));
			}

			_ordering.setRoutes(routes);

			_webXMLDefinition.setOrdering(_ordering);

			_afterName = null;
			_beforeName = null;
			_ordering = null;
			_othersAfterSet = false;
			_othersBeforeSet = false;
		}
		else if (qName.equals("others")) {
			if (_absoluteOrderingNames != null) {
				_othersAbsoluteOrderingSet = true;
			}

			if (_after) {
				_othersAfterSet = true;
			}
			else if (_before) {
				_othersBeforeSet = true;
			}
		}
		else if (qName.equals("param-name")) {
			_parameterName = String.valueOf(_stack.pop());
			_parameterName = _parameterName.trim();
		}
		else if (qName.equals("param-value")) {
			_parameterValue = String.valueOf(_stack.pop());
			_parameterValue = _parameterValue.trim();
		}
		else if (qName.equals("servlet")) {
			_webXMLDefinition.setServletDefinition(
				_servletDefinition.getName(), _servletDefinition);

			_servletDefinition = null;
		}
		else if (qName.equals("servlet-class")) {
			String servletClassName = String.valueOf(_stack.pop());

			Servlet servlet = _getServletInstance(servletClassName.trim());

			_servletDefinition.setServlet(servlet);
		}
		else if (qName.equals("servlet-mapping")) {
			Map<String, ServletDefinition> servletDefinitions =
				_webXMLDefinition.getServletDefinitions();

			ServletDefinition servletDefinition = servletDefinitions.get(
				_servletMapping.servletName);

			if (servletDefinition != null) {
				servletDefinition.setURLPatterns(_servletMapping.urlPatterns);
			}

			_servletMapping = null;
		}
		else if (qName.equals("servlet-name")) {
			if (_filterMapping != null) {
				String servletName = String.valueOf(_stack.pop());

				_filterMapping.servletName = servletName.trim();
			}
			else if (_servletDefinition != null) {
				String servletName = String.valueOf(_stack.pop());

				_servletDefinition.setName(servletName.trim());
			}
			else if (_servletMapping != null) {
				String servletName = String.valueOf(_stack.pop());

				_servletMapping.servletName = servletName.trim();
			}
		}
		else if (qName.equals("taglib")) {
			_jspConfig.mappings.put(_taglibUri, _taglibLocation);

			_taglibUri = null;
			_taglibLocation = null;
		}
		else if (qName.equals("taglib-location")) {
			_taglibLocation = String.valueOf(_stack.pop());
		}
		else if (qName.equals("taglib-uri")) {
			_taglibUri = String.valueOf(_stack.pop());
		}
		else if (qName.equals("url-pattern")) {
			if (_filterMapping != null) {
				String urlPattern = String.valueOf(_stack.pop());

				_filterMapping.urlPatterns.add(urlPattern.trim());
			}
			else if (_servletMapping != null) {
				String urlPattern = String.valueOf(_stack.pop());

				_servletMapping.urlPatterns.add(urlPattern.trim());
			}
		}
	}

	@Override
	public void error(SAXParseException e) {
		_logger.log(Logger.LOG_ERROR, _bundle + ": " + e.getMessage(), e);
	}

	public WebXMLDefinition loadWebXML() throws Exception {
		WebXMLDefinition webXMLDefinition = loadWebXML(
			_bundle.getEntry("WEB-INF/web.xml"));

		if (webXMLDefinition.isMetadataComplete()) {
			return webXMLDefinition;
		}

		Enumeration<URL> enumeration = _bundle.getResources(
			"META-INF/web-fragment.xml");

		List<WebXMLDefinition> webXMLDefinitions = new ArrayList<>();

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				WebXMLDefinitionLoader webXMLDefinitionLoader =
					new WebXMLDefinitionLoader(
						_bundle, _saxParserFactory, _logger);

				webXMLDefinitions.add(webXMLDefinitionLoader.loadWebXML(url));
			}
		}

		List<WebXMLDefinition> orderedWebXMLDefinitions = new ArrayList<>();

		if (ListUtil.isNotEmpty(webXMLDefinitions)) {
			orderedWebXMLDefinitions = OrderUtil.getOrderedWebXMLDefinitions(
				webXMLDefinitions, webXMLDefinition.getAbsoluteOrderNames());
		}

		return _assembleWebXML(webXMLDefinition, orderedWebXMLDefinitions);
	}

	public WebXMLDefinition loadWebXML(URL url) throws Exception {
		if (url == null) {
			return _webXMLDefinition;
		}

		try (InputStream inputStream = url.openStream()) {
			SAXParser saxParser = _saxParserFactory.newSAXParser();

			XMLReader xmlReader = saxParser.getXMLReader();

			xmlReader.setContentHandler(this);

			xmlReader.parse(new InputSource(inputStream));

			return _webXMLDefinition;
		}
		catch (SAXParseException saxpe) {
			String message = saxpe.getMessage();

			if (message.contains("DOCTYPE is disallowed")) {
				throw new Exception(
					url + " must be updated to the Servlet 3.0 specification");
			}

			throw saxpe;
		}
	}

	@Override
	public void startElement(
		String uri, String localName, String qName, Attributes attributes) {

		if (qName.equals("absolute-ordering")) {
			_absoluteOrderingNames = new ArrayList<>();
		}
		else if (qName.equals("after")) {
			_after = true;
		}
		else if (qName.equals("before")) {
			_before = true;
		}
		else if (qName.equals("filter")) {
			_filterDefinition = new FilterDefinition();
		}
		else if (qName.equals("filter-mapping")) {
			_filterMapping = new FilterMapping();
		}
		else if (qName.equals("jsp-config")) {
			_jspConfig = new JSPConfig();
		}
		else if (qName.equals("listener")) {
			_listenerDefinition = new ListenerDefinition();
		}
		else if (qName.equals("ordering")) {
			_ordering = new OrderImpl();
		}
		else if (qName.equals("servlet")) {
			_servletDefinition = new ServletDefinition();
		}
		else if (qName.equals("servlet-mapping")) {
			_servletMapping = new ServletMapping();
		}
		else if (qName.equals("web-app")) {
			boolean metadataComplete = GetterUtil.getBoolean(
				attributes.getValue("metadata-complete"));

			_webXMLDefinition.setMetadataComplete(metadataComplete);
		}
		else if (Arrays.binarySearch(_LEAVES, qName) > -1) {
			_stack.push(new StringBuilder());
		}
	}

	private Filter _getFilterInstance(String filterClassName) {
		try {
			Class<?> clazz = _bundle.loadClass(filterClassName);

			Class<? extends Filter> filterClass = clazz.asSubclass(
				Filter.class);

			return filterClass.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load filter " +
					filterClassName);

			return null;
		}
	}

	private EventListener _getListenerInstance(String listenerClassName) {
		try {
			Class<?> clazz = _bundle.loadClass(listenerClassName);

			Class<? extends EventListener> eventListenerClass =
				clazz.asSubclass(EventListener.class);

			return eventListenerClass.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load listener " +
					listenerClassName);

			return null;
		}
	}

	private Servlet _getServletInstance(String servletClassName) {
		try {
			Class<?> clazz = _bundle.loadClass(servletClassName);

			Class<? extends Servlet> servletClass = clazz.asSubclass(
				Servlet.class);

			return servletClass.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				_bundle + " unable to load servlet " + servletClassName, e);

			return null;
		}
	}

	private void _assembleContextParameters(
		Map<String, String> assembledContextParameters,
		Map<String, String> fragmentContextParameters) {

		for (Entry<String, String> entry :
				fragmentContextParameters.entrySet()) {

			String name = entry.getKey();

			if (!assembledContextParameters.containsKey(name)) {
				assembledContextParameters.put(name, entry.getValue());
			}
		}
	}

	private void _assembleFilters(
			Map<String, FilterDefinition> webXMLFilterDefinitions,
			Map<String, FilterDefinition> assembledFilterDefinitions,
			Map<String, FilterDefinition> fragmentFilterDefinitions)
		throws Exception {

		for (Entry<String, FilterDefinition> entry :
				fragmentFilterDefinitions.entrySet()) {

			String filterName = entry.getKey();

			if (!assembledFilterDefinitions.containsKey(filterName)) {
				assembledFilterDefinitions.put(filterName, entry.getValue());
				
				continue;
			}

			FilterDefinition webXMLFilterDefinition =
				webXMLFilterDefinitions.get(filterName);

			Map<String, String> webXMLInitParameters = null;

			if (webXMLFilterDefinition != null) {
				webXMLInitParameters =
					webXMLFilterDefinition.getInitParameters();
			}

			FilterDefinition assembledFilterDefinition =
				assembledFilterDefinitions.get(filterName);

			Map<String, String> assembledInitParameters =
				assembledFilterDefinition.getInitParameters();

			FilterDefinition fragmentFilterDefinition = entry.getValue();

			Map<String, String> fragmentInitParameters =
				fragmentFilterDefinition.getInitParameters();

			for (Entry<String, String> initParametersEntry :
					fragmentInitParameters.entrySet()) {

				String initParameterName = initParametersEntry.getKey();

				String webXMLInitParameterValue = null;

				if (webXMLInitParameters != null) {
					webXMLInitParameterValue = webXMLInitParameters.get(
						initParameterName);
				}

				String assembledInitParameterValue =
					assembledInitParameters.get(initParameterName);

				if (Validator.isNull(assembledInitParameterValue)) {
					if ((webXMLInitParameterValue == null) &&
						!Validator.equals(
							assembledInitParameterValue,
							initParametersEntry.getValue())) {

						// Servlet 3 spec 8.2.3

						throw new Exception (
							"Init paramter name " + initParameterName +
								" conflicts with filter name " + filterName);
					}
					else {
						assembledInitParameters.put(
							initParameterName, initParametersEntry.getValue());
					}
				}
			}

			List<String> assembledDispatchers =
				assembledFilterDefinition.getDispatchers();

			List<String> fragmentDispatchers =
				fragmentFilterDefinition.getDispatchers();

			for (String dispatcher : fragmentDispatchers) {
				if (!assembledDispatchers.contains(dispatcher)) {
					assembledDispatchers.add(dispatcher);
				}
			}

			List<String> assembledServletNames =
				assembledFilterDefinition.getServletNames();

			List<String> fragmentServletNames =
				fragmentFilterDefinition.getServletNames();

			for (String servletName : fragmentServletNames) {
				if (!assembledServletNames.contains(servletName)) {
					assembledServletNames.add(servletName);
				}
			}

			List<String> assembledURLPatterns =
				assembledFilterDefinition.getURLPatterns();

			List<String> fragmentURLPatterns =
				fragmentFilterDefinition.getURLPatterns();

			for (String urlPattern : fragmentURLPatterns) {
				if (!assembledURLPatterns.contains(urlPattern)) {
					assembledURLPatterns.add(urlPattern);
				}
			}
		}
	}

	private void _assembleListeners(
		List<ListenerDefinition> assembledListenerDefinitions,
		List<ListenerDefinition> fragmentListenerDefinitions) {

		for (ListenerDefinition fragmentListenerDefinition :
				fragmentListenerDefinitions) {

			if (!assembledListenerDefinitions.contains(
					fragmentListenerDefinition)) {

				assembledListenerDefinitions.add(fragmentListenerDefinition);
			}
		}
	}

	private void _assembleServlets(
			Map<String, ServletDefinition> webXMLServlets,
			Map<String, ServletDefinition> assembledServlets,
			Map<String, ServletDefinition> fragmentServlets)
		throws Exception {

		for (Entry<String, ServletDefinition> entry :
				fragmentServlets.entrySet()) {

			String servletName = entry.getKey();

			if (!assembledServlets.containsKey(servletName)) {
				fragmentServlets.put(servletName, entry.getValue());
				
				continue;
			}

			ServletDefinition webXMLServletDefinition = webXMLServlets.get(
				servletName);

			ServletDefinition fragmentServletDefinition =
				entry.getValue();

			Map<String, String> webXMLServletParams = null;

			if (webXMLServletDefinition != null) {
				webXMLServletParams =
					webXMLServletDefinition.getInitParameters();
			}

			Map<String, String> fragmentServletParams =
				fragmentServletDefinition.getInitParameters();

			ServletDefinition assembledServletDefinition =
				assembledServlets.get(servletName);

			Map<String, String> assembledInitParams =
				assembledServletDefinition.getInitParameters();

			for (Entry<String, String> initParamEntry :
					fragmentServletParams.entrySet()) {

				String initParamName = initParamEntry.getKey();
				String initParameterValue = initParamEntry.getValue();

				String assembledInitParameterValue = assembledInitParams.get(
					initParamName);

				String webXMLInitParameterValue = null;

				if (webXMLServletParams != null) {
					webXMLInitParameterValue = webXMLServletParams.get(
						initParamName);
				}

				if (Validator.isNull(assembledInitParameterValue)) {
					if ((webXMLInitParameterValue == null) &&
						!Validator.equals(
							assembledInitParameterValue, initParameterValue)) {

						// Servlet 3 spec 8.2.3. If two web-fragments
						// with same param-name and different
						// param-value does not exist in web.xml,
						// throw an Exception

						throw new Exception (
							"Conflicts for " + initParamName +
								" in servlet "+ servletName);
					}
					else {
						assembledInitParams.put(
							initParamName, initParameterValue);
					}
				}
			}

			List<String> assembledServletURLPatterns =
				assembledServletDefinition.getURLPatterns();

			List<String> fragmentServletURLPatterns =
				fragmentServletDefinition.getURLPatterns();

			for (String urlPattern : fragmentServletURLPatterns) {
				if (!assembledServletURLPatterns.contains(urlPattern)) {
					assembledServletURLPatterns.add(urlPattern);
				}
			}

			if (Validator.isNull(assembledServletDefinition.getJspFile())) {
				assembledServletDefinition.setJSPFile(
					fragmentServletDefinition.getJspFile());
			}
		}
	}

	private WebXMLDefinition _assembleWebXML(
			WebXMLDefinition webXML, List<WebXMLDefinition> webFragments)
		throws Exception {

		WebXMLDefinition assembledWebXML = (WebXMLDefinition)webXML.clone();

		Map<String, String> assembledContextParameters =
			assembledWebXML.getContextParameters();

		List<ListenerDefinition> assembledListeners =
			assembledWebXML.getListenerDefinitions();

		Map<String, FilterDefinition> webXMLFilters =
			webXML.getFilterDefinitions();

		Map<String, FilterDefinition> assembledFilters =
			assembledWebXML.getFilterDefinitions();

		Map<String, ServletDefinition> webXMLServlets =
			webXML.getServletDefinitions();

		Map<String, ServletDefinition> assembledServlets =
			assembledWebXML.getServletDefinitions();

		for (WebXMLDefinition fragment : webFragments) {
			Map<String, String> fragmentContextParameters =
				fragment.getContextParameters();

			_assembleContextParameters(
				assembledContextParameters, fragmentContextParameters);

			List<ListenerDefinition> fragmentListeners =
				fragment.getListenerDefinitions();

			_assembleListeners(assembledListeners, fragmentListeners);

			Map<String, FilterDefinition> fragmentFilters =
				fragment.getFilterDefinitions();

			_assembleFilters(webXMLFilters, assembledFilters, fragmentFilters);

			Map<String, ServletDefinition> fragmentServlets =
				fragment.getServletDefinitions();

			_assembleServlets(
				webXMLServlets, assembledServlets, fragmentServlets);
		}

		return assembledWebXML;
	}

	private static final String[] _LEAVES = new String[] {
		"async-supported", "dispatcher", "error-code", "exception-type",
		"filter-class", "filter-name", "jsp-file", "listener-class", "location",
		"name", "param-name", "param-value", "servlet-class", "servlet-name",
		"taglib-location", "taglib-uri", "url-pattern"
	};

	private boolean _after;
	private boolean _before;
	private final Bundle _bundle;
	private FilterDefinition _filterDefinition;
	private FilterMapping _filterMapping;
	private JSPConfig _jspConfig;
	private ListenerDefinition _listenerDefinition;
	private final Logger _logger;
	private String _name;
	private String _afterName;
	private String _beforeName;
	private List<String> _absoluteOrderingNames;
	private Order _ordering;
	private boolean _othersAbsoluteOrderingSet;
	private boolean _othersAfterSet;
	private boolean _othersBeforeSet;
	private String _parameterName;
	private String _parameterValue;
	private final SAXParserFactory _saxParserFactory;
	private ServletDefinition _servletDefinition;
	private ServletMapping _servletMapping;
	private final Stack<StringBuilder> _stack = new Stack<>();
	private String _taglibLocation;
	private String _taglibUri;
	private final WebXMLDefinition _webXMLDefinition;

	private static class FilterMapping {

		protected List<String> dispatchers = new ArrayList<>();
		protected String filterName;
		protected String servletName;
		protected List<String> urlPatterns = new ArrayList<>();

	}

	private static class JSPConfig {

		protected Map<String, String> mappings = new HashMap<>();

	}

	private static class ServletMapping {

		protected String servletName;
		protected List<String> urlPatterns = new ArrayList<>();

	}

}