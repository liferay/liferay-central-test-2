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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="RouteImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouteImpl implements Route {

	public RouteImpl(String pattern) {
		_urlPattern = pattern;

		String regex = escapeRegex(pattern);

		Matcher matcher = _fragmentPattern.matcher(pattern);

		while (matcher.find()) {
			String fragment = matcher.group();

			RoutePart routePart = new RoutePart(fragment);

			_routeParts.add(routePart);

			_urlPattern = _urlPattern.replace(
				fragment, routePart.getFragmentName());

			regex = regex.replace(
				escapeRegex(fragment), "(" + routePart.getPattern() + ")");
		}

		_regexPattern = Pattern.compile(regex);
	}

	public void addDefaultParameter(String name, String value) {
		_defaultParameters.put(name, value);
	}

	public void addIgnoredParameter(String name) {
		_ignoredParameters.add(name);
	}

	public Map<String, String> getDefaultParameters() {
		return _defaultParameters;
	}

	public List<String> getIgnoredParameters() {
		return _ignoredParameters;
	}

	public String parametersToUrl(Map<String, ?> parameters) {
		List<String> names = new ArrayList<String>();

		if (!_defaultParameters.isEmpty()) {
			for (Map.Entry<String, String> entry :
					_defaultParameters.entrySet()) {

				String name = entry.getKey();
				String value = entry.getValue();

				if (!value.equals(MapUtil.getString(parameters, name))) {
					return null;
				}

				names.add(name);
			}
		}

		String url = _urlPattern;

		for (RoutePart routePart : _routeParts) {
			String name = routePart.getName();

			String value = MapUtil.getString(parameters, name);

			if (value == null) {
				return null;
			}

			value = HttpUtil.encodeURL(value);

			if (!routePart.matches(value)) {
				return null;
			}

			names.add(name);

			url = url.replace(routePart.getFragmentName(), value);
		}

		for (String name : names) {
			parameters.remove(name);
		}

		for (String name : _ignoredParameters) {
			parameters.remove(name);
		}

		return url;
	}

	public Map<String, String> urlToParameters(String url) {
		Matcher matcher = _regexPattern.matcher(url);

		if (!matcher.matches()) {
			return null;
		}

		Map<String, String> parameters = new HashMap<String, String>(
			_defaultParameters);

		for (int i = 1; i <= _routeParts.size(); i++) {
			RoutePart routePart = _routeParts.get(i - 1);

			String value = matcher.group(i);

			value = HttpUtil.decodeURL(value);

			parameters.put(routePart.getName(), value);
		}

		return parameters;
	}

	protected String escapeRegex(String s) {
		Matcher matcher = _escapeRegexPattern.matcher(s);

		return matcher.replaceAll("\\\\$0");
	}

	private static Pattern _escapeRegexPattern = Pattern.compile(
		"[\\{\\}\\(\\)\\[\\]\\*\\+\\?\\$\\^\\.\\#\\\\]");
	private static Pattern _fragmentPattern = Pattern.compile("\\{.+?\\}");

	private Map<String, String> _defaultParameters =
		new HashMap<String, String>();
	private List<String> _ignoredParameters = new ArrayList<String>();
	private Pattern _regexPattern;
	private List<RoutePart> _routeParts = new ArrayList<RoutePart>();
	private String _urlPattern;

}