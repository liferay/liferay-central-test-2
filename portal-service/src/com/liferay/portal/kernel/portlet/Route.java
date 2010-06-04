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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="Route.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class Route {

	public void addDefaultParameter(String name, String value) {
		_defaultParameters.put(name, value);
	}

	public Map<String, String> getDefaultParameters() {
		return _defaultParameters;
	}

	public String parametersToUrl(Map<String, String> parameters) {
		List<String> names = new ArrayList<String>();

		if (!_defaultParameters.isEmpty()) {
			for (Map.Entry<String, String> entry :
					_defaultParameters.entrySet()) {

				String name = entry.getKey();
				String value = entry.getValue();

				if (!value.equals(parameters.get(name))) {
					return null;
				}

				names.add(name);
			}
		}

		String url = _patternString;

		for (RoutePart routePart : _routeParts) {
			if (!routePart.matches(parameters)) {
				return null;
			}

			String name = routePart.getName();

			names.add(name);

			String value = parameters.get(name);

			value = HttpUtil.encodeURL(value);

			url = url.replace(routePart.getFragmentName(), value);
		}

		for (String name : names) {
			parameters.remove(name);
		}

		return url;
	}

	public void setPattern(String pattern) {
		_patternString = pattern;
		
		_routeParts = new LinkedList<RoutePart>();

		Matcher matcher = _fragmentPattern.matcher(_patternString);
		
		pattern = escapeRegex(pattern);
		
		while (matcher.find()) {
			String fragment = matcher.group();

			RoutePart routePart = new RoutePart();

			routePart.setFragment(fragment);

			_routeParts.add(routePart);

			_patternString = _patternString.replace(
				fragment, routePart.getFragmentName());

			pattern = pattern.replace(
				escapeRegex(fragment), "(" + routePart.getPattern() + ")");
		}
		
		System.out.println("Pattern: " + pattern);

		_pattern = Pattern.compile(pattern);
	}

	public Map<String, String> urlToParameters(String url) {
		Matcher matcher = _pattern.matcher(url);

		if (!matcher.matches()) {
			return null;
		}

		Map<String, String> parameters = new HashMap<String, String>(
			_defaultParameters);

		for (int i = 1; i <= _routeParts.size(); i++) {
			RoutePart routePart = _routeParts.get(i - 1);

			String value = matcher.group(i);

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
	private Pattern _pattern;
	private String _patternString;
	private List<RoutePart> _routeParts;

}