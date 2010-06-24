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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringEncoder;
import com.liferay.portal.kernel.util.StringParser;
import com.liferay.portal.util.URLStringEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="RouteImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouteImpl implements Route {

	public RouteImpl(String pattern) {
		_parser = new StringParser(pattern);
		_parser.setEncoder(_urlEncoder);
	}

	public void addDefaultParameter(String name, String value) {
		_defaultParameters.put(name, value);
	}

	public void addGeneratedParameter(String name, String pattern) {
		StringParser parser = new StringParser(pattern);
		_generatedParameters.put(name, parser);
	}

	public void addIgnoredParameter(String name) {
		_ignoredParameters.add(name);
	}

	public void addOverriddenParameter(String name, String value) {
		_overriddenParameters.put(name, value);
	}

	public Map<String, String> getDefaultParameters() {
		return _defaultParameters;
	}

	public Map<String, StringParser> getGeneratedParameters() {
		return _generatedParameters;
	}

	public List<String> getIgnoredParameters() {
		return _ignoredParameters;
	}

	public Map<String, String> getOverriddenParameters() {
		return _overriddenParameters;
	}

	public String parametersToUrl(Map<String, String> parameters) {
		InheritableMap<String, String> allParameters =
			new InheritableMap<String, String>();
		
		allParameters.setParentMap(parameters);

		// Virtual parameters may sometimes be checked by default parameters,
		// so the order here is important.

		for (Map.Entry<String, StringParser> entry :
				_generatedParameters.entrySet()) {

			String name = entry.getKey();
			StringParser parser = entry.getValue();
			String value = MapUtil.getString(allParameters, name);

			if (!parser.parse(value, allParameters)) {
				return null;
			}
		}
		
		for (Map.Entry<String, String> entry : _defaultParameters.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			if (!value.equals(MapUtil.getString(allParameters, name))) {
				return null;
			}
		}

		String url = _parser.build(allParameters);

		if (url == null) {
			return null;
		}

		for (String name : _generatedParameters.keySet()) {
			// Virtual parameters will never be placed in the query string,
			// so parameters is modified directly instead of allParameters.
			parameters.remove(name);
		}

		for (String name : _defaultParameters.keySet()) {
			parameters.remove(name);
		}

		for (String name : _ignoredParameters) {
			parameters.remove(name);
		}

		return url;
	}

	public boolean urlToParameters(
			String url, Map<String, String> parameters) {

		if (!_parser.parse(url, parameters)) {
			return false;
		}

		parameters.putAll(_defaultParameters);
		parameters.putAll(_overriddenParameters);

		// Generated parameters may be dependent on default parameters or
		// overridden parameters, so the order here is important.

		for (Map.Entry<String, StringParser> entry :
				_generatedParameters.entrySet()) {

			String name = entry.getKey();
			StringParser parser = entry.getValue();

			String value = parser.build(parameters);

			// Generated parameters are not guaranteed to be created. The format
			// of the virtual parameters in the route pattern must match their
			// format in the generated parameter.
			if (value != null) {
				parameters.put(name, value);
			}
		}

		return true;
	}

	private static StringEncoder _urlEncoder = new URLStringEncoder();

	private Map<String, String> _defaultParameters =
		new HashMap<String, String>();
	private List<String> _ignoredParameters = new ArrayList<String>();
	private Map<String, String> _overriddenParameters =
		new HashMap<String, String>();
	private StringParser _parser;
	private Map<String, StringParser> _generatedParameters =
		new HashMap<String, StringParser>();

}